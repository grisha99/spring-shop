package ru.grishchenko.mymarket.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grishchenko.mymarket.dto.OrderDto;
import ru.grishchenko.mymarket.exception_handling.ResourceNotFoundException;
import ru.grishchenko.mymarket.models.Cart;
import ru.grishchenko.mymarket.models.Order;
import ru.grishchenko.mymarket.models.User;
import ru.grishchenko.mymarket.repositories.OrderRepository;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public Order createOrderFormCart(User user, String address, UUID uuid) {
        Cart cart = cartService.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Unable to find cart with id: " + uuid));
        Order newOrder = new Order(cart, user);
        newOrder.setDelivery_addr(address);
        orderRepository.save(newOrder);
        cartService.clear(uuid);
        return newOrder;
    }

    public List<Order> findAllOrdersByOwnerName(String userName) {
        return orderRepository.findAllByUserUsername(userName);
    }

    public OrderDto findOrderDtoById(Long id, Principal principal) {
        List<Order> orderList = findAllOrdersByOwnerName(principal.getName());
        for (Order o : orderList) {
            if (o.getId() == id) {
                return new OrderDto(o);
            }
        }
        return null;    // todo переделать на Optional
    }
}
