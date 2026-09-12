package com.example.projectJavaEE.Service;

import com.example.projectJavaEE.Entites.Cart;
import com.example.projectJavaEE.Entites.CartItem;
import com.example.projectJavaEE.Entites.Login;
import com.example.projectJavaEE.Entites.Product;
import com.example.projectJavaEE.Repository.CartRepo;
import com.example.projectJavaEE.Repository.LoginRepository;
import com.example.projectJavaEE.Repository.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CartService {
    CartRepo cartRepo;
    ProductRepo productRepo;
    LoginRepository loginRepository;

    @Transactional
    public Cart addToCart(int idAccount, Cart requestCart) {
        Cart activeCart = cartRepo.findByLogin_IdAccount(idAccount)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    Login login = loginRepository.findById(idAccount)
                            .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));
                    newCart.setLogin(login);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepo.save(newCart);
                });

        if (requestCart.getCartItems() == null || requestCart.getCartItems().isEmpty()) {
            throw new RuntimeException("Dữ liệu sản phẩm thêm vào trống!");
        }

        CartItem requestItem = requestCart.getCartItems().get(0);
        int targetProductId = requestItem.getProduct().getId();
        int targetQuantity = requestItem.getQuantity();
        double targetPrice = requestItem.getPrice();
        if (activeCart.getCartItems() == null) {
            activeCart.setCartItems(new ArrayList<>());
        }

        CartItem existingItem = null;
        for (CartItem item : activeCart.getCartItems()) {
            if (item.getProduct().getId() == targetProductId) {
                existingItem = item;
                break;
            }
        }
        Product product = productRepo.findById(targetProductId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trên hệ thống!"));
        int stockQuantity = product.getQuantity();

        if (existingItem != null) {
            int totalQuantityAfterAdd = existingItem.getQuantity() + targetQuantity;

            if (totalQuantityAfterAdd > stockQuantity) {
                throw new RuntimeException("Số lượng sản phẩm trong giỏ hàng sau khi cộng dồn sẽ vượt quá số lượng tồn kho hiện có (" + stockQuantity + " cái)!");
            }
            existingItem.setQuantity(totalQuantityAfterAdd);
        } else {
            if (targetQuantity > stockQuantity) {
                throw new RuntimeException("Số lượng yêu cầu vượt quá số lượng tồn kho hiện có (" + stockQuantity + " cái)!");
            }

            CartItem newItem = new CartItem();
            newItem.setCart(activeCart);
            newItem.setProduct(product);
            newItem.setQuantity(targetQuantity);
            newItem.setPrice(product.getPrice());
            activeCart.getCartItems().add(newItem);
        }

        return cartRepo.save(activeCart);
    }

    public Cart getCartByAccount(int idAccount) {
        return cartRepo.findByLogin_IdAccount(idAccount)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng chưa được khởi tạo!"));
    }

    @Transactional
    public void deleteCartItem(int idAccount, int idCI) {
        Cart activeCart = cartRepo.findByLogin_IdAccount(idAccount)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại!"));

        CartItem targetItem = null;
        for (CartItem item : activeCart.getCartItems()) {
            if (item.getIdCI() == idCI) {
                targetItem = item;
                break;
            }
        }

        if (targetItem != null) {
            activeCart.getCartItems().remove(targetItem);
            cartRepo.save(activeCart);
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại trong giỏ hàng của bạn!");
        }
    }

    @Transactional
    public Cart updateCartItemQuantity(int idAccount, int idCI, int newQuantity) {
        if (newQuantity < 1) {
            throw new RuntimeException("Số lượng sản phẩm tối thiểu phải là 1!");
        }

        Cart activeCart = cartRepo.findByLogin_IdAccount(idAccount)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại!"));

        CartItem targetItem = activeCart.getCartItems().stream()
                .filter(item -> item.getIdCI() == idCI)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sản phẩm không có trong giỏ hàng!"));
        int stockQuantity = targetItem.getProduct().getQuantity();
        if (newQuantity > stockQuantity) {
            throw new RuntimeException("Số lượng yêu cầu (" + newQuantity + ") vượt quá số lượng tồn kho hiện có (" + stockQuantity + " cái)!");
        }
        targetItem.setQuantity(newQuantity);

        return cartRepo.save(activeCart);
    }


    @Transactional
    public void clearMyCart(int idAccount) {
        Cart activeCart = cartRepo.findByLogin_IdAccount(idAccount)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại!"));

        if (activeCart.getCartItems() != null) {
            activeCart.getCartItems().clear();
            cartRepo.save(activeCart);
        }
    }
}