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
    private int totalPrice;
    private String delivery_addr;
    private String creationDateTime;
    private List<OrderItemDto> items;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getPrice();
        this.delivery_addr = order.getDelivery_addr();
        this.creationDateTime = order.getCreatedAt().toString();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }

}
