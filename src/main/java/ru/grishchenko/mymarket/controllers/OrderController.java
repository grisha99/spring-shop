package ru.grishchenko.mymarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.grishchenko.mymarket.dto.OrderDto;
import ru.grishchenko.mymarket.exception_handling.ResourceNotFoundException;
import ru.grishchenko.mymarket.models.User;
import ru.grishchenko.mymarket.services.OrderService;
import ru.grishchenko.mymarket.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public List<OrderDto> getOrders(Principal principal) {
        return orderService.findAllOrdersByOwnerName(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }

//    @GetMapping("/{id}")
//    public Optional<OrderDto> getOrders(@PathVariable Integer id, Principal principal) {
//        return orderService.getOrderDto(id, principal);
//    }

    @PostMapping
    public void addOrderFormCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        orderService.createOrderFormCart(user);
    }

}
