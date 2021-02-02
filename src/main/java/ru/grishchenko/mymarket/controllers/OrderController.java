package ru.grishchenko.mymarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.grishchenko.mymarket.dto.OrderDto;
import ru.grishchenko.mymarket.services.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders(Principal principal) {
        return orderService.getOrdersList(principal);
    }

    @GetMapping("/{id}")
    public Optional<OrderDto> getOrders(@PathVariable Integer id, Principal principal) {
        return orderService.getOrderDto(id, principal);
    }

    @PostMapping
    public List<OrderDto> addOrderFormCart(Principal principal) {
        return orderService.createOrderFormCart(principal);
    }

}
