package ru.grishchenko.mymarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.grishchenko.mymarket.models.Order;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private List<OrderItemDto> items;
    private int totalPrice;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        for (OrderItemDto item : this.items) {
            totalPrice += item.getPricePerProduct() * item.getQuantity();
        }
    }

}
