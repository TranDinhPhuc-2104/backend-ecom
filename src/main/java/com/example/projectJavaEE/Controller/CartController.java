package com.example.projectJavaEE.Controller;

import com.example.projectJavaEE.Entites.Cart;
import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.Service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000"}, allowCredentials = "true")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ApiResponse<Cart> addProductToCart(
            @RequestBody Cart requestCart,
            @AuthenticationPrincipal Jwt jwt
    ) {
        int idAccount = extractIdAccount(jwt);

        Cart updatedCart = cartService.addToCart(idAccount, requestCart);

        return ApiResponse.<Cart>builder()
                .code(0)
                .result(updatedCart)
                .build();
    }

    @GetMapping
    public ApiResponse<Cart> getMyCart(@AuthenticationPrincipal Jwt jwt) {
        // Gọi hàm helper để lấy ID tài khoản một cách an toàn
        int idAccount = extractIdAccount(jwt);

        Cart myCart = cartService.getCartByAccount(idAccount);

        return ApiResponse.<Cart>builder()
                .code(0)
                .result(myCart)
                .build();
    }


    private int extractIdAccount(Jwt jwt) {
        try {
            if (jwt.getClaim("id") != null) {
                return Integer.parseInt(jwt.getClaim("id").toString());
            }

            if (jwt.getClaim("idAccount") != null) {
                return Integer.parseInt(jwt.getClaim("idAccount").toString());
            }

            if (jwt.getSubject() != null) {
                // Phòng hờ nếu subject là ID số, nếu là chữ (username) thì khối catch sẽ xử lý
                return Integer.parseInt(jwt.getSubject());
            }
        } catch (NumberFormatException e) {
            System.err.println("Cảnh báo: Trường Subject chứa ký tự chữ, không phải ID số: " + jwt.getSubject());
        }

        // Nếu tất cả các cổng bóc tách đều thất bại, ném ra thông báo lỗi rõ ràng
        throw new RuntimeException("Lỗi xác thực: Token hợp lệ nhưng hệ thống không tìm thấy trường số định danh (id/idAccount) của người dùng!");
    }

    // API DELETE: http://localhost:8080/cart/items/{idCI} (Xóa món hàng khỏi giỏ)
    @DeleteMapping("/items/{idCI}")
    public ApiResponse<String> removeCartItem(
            @PathVariable int idCI,
            @AuthenticationPrincipal Jwt jwt
    ) {
        // Sử dụng hàm helper bóc tách idAccount an toàn của bạn
        int idAccount = extractIdAccount(jwt);

        // Gọi dịch vụ xóa dữ liệu dưới DBS
        cartService.deleteCartItem(idAccount, idCI);

        return ApiResponse.<String>builder()
                .code(0)
                .result("Xóa sản phẩm khỏi giỏ hàng thành công!")
                .build();
    }


    // API PUT: http://localhost:8080/cart/items/{idCI}?quantity=5
    @PutMapping("/items/{idCI}")
    public ApiResponse<Cart> updateItemQuantity(
            @PathVariable int idCI,
            @RequestParam int quantity,
            @AuthenticationPrincipal Jwt jwt
    ) {
        int idAccount = extractIdAccount(jwt);

        Cart updatedCart = cartService.updateCartItemQuantity(idAccount, idCI, quantity);

        return ApiResponse.<Cart>builder()
                .code(0)
                .result(updatedCart)
                .build();
    }


    @DeleteMapping("/clear")
    public ApiResponse<String> clearCart() {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        int idAccount = Integer.parseInt(currentPrincipalName);
        cartService.clearMyCart(idAccount);
        return ApiResponse.<String>builder()
                .result("Đã xóa toàn bộ giỏ hàng thành công")
                .build();
    }
}