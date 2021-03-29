package ru.grishchenko.mymarket.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.grishchenko.mymarket.dto.CartDto;
import ru.grishchenko.mymarket.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @GetMapping("{uuid}")
    public CartDto getCartDto(@PathVariable UUID uuid) {
        return new CartDto(cartService.findById(uuid).get());
    }

    @GetMapping("{uuid}/add/{id}")
    public void addToUUIDCart(@PathVariable UUID uuid, @PathVariable(name = "id") Long productId) {
        cartService.addProductToCart(uuid, productId);

    }

    @GetMapping("{uuid}/delete/{id}")
    public void delItemFromUUIDCart(@PathVariable UUID uuid, @PathVariable(name = "id") Long productId) {
        cartService.removeFromCart(uuid, productId);

    }

    @GetMapping("{uuid}/delete/all/{id}")
    public void delAllItemFromUUIDCart(@PathVariable UUID uuid, @PathVariable(name = "id") Long productId) {
        cartService.deleteFromCart(uuid, productId);

    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@PathVariable UUID uuid) {
        cartService.clear(uuid);
    }

    @GetMapping("/new")
    public UUID getNewUUIDCart() {
        return cartService.getNewCart().getId();
    }

}
