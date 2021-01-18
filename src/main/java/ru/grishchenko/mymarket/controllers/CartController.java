package ru.grishchenko.mymarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.grishchenko.mymarket.dto.ProductDto;
import ru.grishchenko.mymarket.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<ProductDto> getCart() {
        return cartService.getCartProducts();
    }

    @GetMapping("/add/{id}")
    public void addProductById(@PathVariable Long id) {
        cartService.addProductById(id);
    }

    @GetMapping("/delete/{index}")
    public void delProductById(@PathVariable int index) {
        cartService.removeProductByIndex(index);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }


}
