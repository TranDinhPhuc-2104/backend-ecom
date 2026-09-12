package com.example.projectJavaEE.Service;


import com.example.projectJavaEE.DTO.Request.UIRequest;
import com.example.projectJavaEE.DTO.Response.UIResponse;
import com.example.projectJavaEE.Entites.Login;
import com.example.projectJavaEE.Entites.UI;
import com.example.projectJavaEE.Repository.UiRepo;
import com.example.projectJavaEE.mapper.UIMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UIService {
    UIMapper mapper;
    UiRepo repo;

    public UIResponse createUI(UIRequest request){
        UI ui = new UI();
        ui.setDob(request.getDob());
        ui.setAddress(request.getAddress());
        ui.setFirstName(request.getFirstName());
        ui.setLastName(request.getLastName());

        Login login = new Login();
        login.setIdAccount(request.getIdAccount());

        ui.setLogin(login);
        repo.save(ui);
        return mapper.toUIResponse(ui);
    }


    public List<UIResponse> getAllUi(){
        List<UI> list = repo.findAll();
        return list.stream().map(mapper::toUIResponse).toList();

    }

    public UIResponse getByIdAccount(int id){
        UI ui = repo.getByLogin_IdAccount(id);
        return mapper.toUIResponse(ui);
    }

    public UIResponse getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idAccount = authentication.getName();
        int id = Integer.parseInt(idAccount);
       UI ui =  repo.getByLogin_IdAccount(id);

        if(ui == null){
            ui = new UI();
            Login login = new Login();
            login.setIdAccount(id);
            ui.setLogin(login);
            ui = repo.save(ui);
        }
        return mapper.toUIResponse(ui);
    }

    public UIResponse updateUI(int idAccount, UIRequest request) {
        // Tìm bản ghi hiện tại dựa vào idAccount
        UI ui = repo.getByLogin_IdAccount(idAccount);

        if(ui == null){
            ui = new UI();
            Login login = new Login();
            login.setIdAccount(idAccount);
            ui.setLogin(login);

        }
        // Map dữ liệu từ request sang ui (Đã đảo tham số)
        mapper.toUIUpdate(ui,request);

        // Lưu và trả về
        return mapper.toUIResponse(repo.save(ui));
    }

}
