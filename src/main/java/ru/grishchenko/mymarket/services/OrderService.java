package ru.grishchenko.mymarket.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.grishchenko.mymarket.beans.Cart;
import ru.grishchenko.mymarket.dto.OrderDto;
import ru.grishchenko.mymarket.models.Order;
import ru.grishchenko.mymarket.models.User;
import ru.grishchenko.mymarket.repositories.OrderRepository;

import java.security.Principal;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final Cart cart;

    public Order createOrderFormCart(User user, String address) {
            Order newOrder = new Order(cart, user);
            newOrder.setDelivery_addr(address);
            orderRepository.save(newOrder);
            cart.clear();
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
