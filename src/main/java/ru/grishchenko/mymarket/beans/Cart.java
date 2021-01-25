package ru.grishchenko.mymarket.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.grishchenko.mymarket.exception_handling.ResourceNotFoundException;
import ru.grishchenko.mymarket.models.OrderItem;
import ru.grishchenko.mymarket.models.Product;
import ru.grishchenko.mymarket.services.ProductService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Data
public class Cart {
    private final ProductService productService;
    private List<OrderItem> items;
    private int totalPrice;

    @PostConstruct
    public void init() {
        this.items = new ArrayList<>();
    }

    public void addToCart(Long id) {
        for (OrderItem o : items) {
            if (o.getProduct().getId().equals(id)) {
                o.incrementQuantity();
                recalculate();
                return;
            }
        }
        Product p = productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find product with id: " + id + " (add to cart)"));
        OrderItem orderItem = new OrderItem(p);
        items.add(orderItem);
        recalculate();
    }

    public void removeFromCart(Long id) {
        for (OrderItem o : items) {
            if (o.getProduct().getId().equals(id)) {
                o.decrementQuantity();
                if (o.getQuantity() <= 0) {
                    items.remove(o);
                }
                recalculate();
                return;
            }
        }
    }

    public void removeAllProductFromCartById(Long id) {
        for (OrderItem o : items) {
            if (o.getProduct().getId().equals(id)) {
                items.remove(o);
                recalculate();
                return;
            }
        }
    }

    public void clear() {
        items.clear();
        recalculate();
    }

    public void recalculate() {
        totalPrice = 0;
        for (OrderItem o : items) {
            totalPrice += o.getPrice();
        }
    }
}
