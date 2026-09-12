package com.example.projectJavaEE.Service;

import com.example.projectJavaEE.DTO.Request.AuthenticationRequest;
import com.example.projectJavaEE.DTO.Request.LoginRequest;
import com.example.projectJavaEE.DTO.Request.RefreshTokenRequest;
import com.example.projectJavaEE.DTO.Response.AuthenticationResponse;
import com.example.projectJavaEE.DTO.Response.LoginResponse;
import com.example.projectJavaEE.Entites.InvalidatedToken;
import com.example.projectJavaEE.Entites.Login;
import com.example.projectJavaEE.Repository.InvalidatedRepository;
import com.example.projectJavaEE.Repository.LoginRepository;
import com.example.projectJavaEE.mapper.LogInMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    LoginRepository loginRepository;
    LogInMapper mapper;
    InvalidatedRepository invalidatedRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    String signKey;

    public LoginResponse createAccount(LoginRequest request){
        Login login = new Login();
        login.setGmail(request.getGmail());
        login.setRole("user");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        login.setPassword(passwordEncoder.encode(request.getPassword()));

        loginRepository.save(login);
        return mapper.toLoginResponse(login);
    }

    public List<LoginResponse> getAllAccount(){
        List<Login> logins = loginRepository.findAll();
        return logins.stream().map(mapper::toLoginResponse).toList();
    }

    public AuthenticationResponse Authen(AuthenticationRequest request){
        var au = loginRepository.findByGmail(request.getGmail()).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        boolean result = encoder.matches(request.getPassword(), au.getPassword());

        if (!result) {
            throw new RuntimeException("sai mat khau");
        }
        var token = generateToken(au);

        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if (!isRefresh && !(verified && expiryTime.after(new Date()))) {
            throw new RuntimeException("Token đã hết hạn hoặc không hợp lệ!");
        }
        if (isRefresh && !verified) {
            throw new RuntimeException("Chữ ký Token không đúng!");
        }
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        if (jit == null) {
            throw new RuntimeException("Lỗi xác thực: Token không có cấu trúc JWT ID hợp lệ!");
        }
        if (invalidatedRepository.existsById(jit)) {
            throw new RuntimeException("Token này đã bị vô hiệu hóa trước đó!");
        }
        return signedJWT;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedRepository.save(invalidatedToken);

        var idAccountStr = signedJWT.getJWTClaimsSet().getSubject();
        int idAccount = Integer.parseInt(idAccountStr);

        var user = loginRepository.findById(idAccount)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        var newToken = generateToken(user);

        return AuthenticationResponse.builder()
                .token(newToken)
                .authenticated(true)
                .build();
    }

    public String generateToken(Login login){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .subject(String.valueOf(login.getIdAccount()))
                .jwtID(java.util.UUID.randomUUID().toString())
                .claim("ROLE_" ,login.getRole().trim())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthenticationResponse adminAuth(AuthenticationRequest request){
        var au = loginRepository.findByGmail(request.getGmail()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean result = passwordEncoder.matches(request.getPassword(), au.getPassword());

        String role = au.getRole();
        if (role == null || !role.toLowerCase().contains("admin")) {
            throw new RuntimeException("Ko co quyen vao page nay");
        }

        if (!result) {
            throw new RuntimeException("Sai mat khau");
        }
        var token = generateToken(au);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
}