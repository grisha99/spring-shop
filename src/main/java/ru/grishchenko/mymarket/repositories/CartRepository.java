package ru.grishchenko.mymarket.repositories;

import org.springframework.stereotype.Repository;
import ru.grishchenko.mymarket.dto.ProductDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartRepository {

    // т.к. нет пользователей и другой информации, это упрощенный вид репозитория
    private List<ProductDto> products;

    public List<ProductDto> getProducts() {
        return products;
    }

    public void removeProduct(int index) {
        products.remove(index);
    }

    public void addProduct(ProductDto p) {
        products.add(p);
    }

    public void clear() {
        products.clear();
    }

    @PostConstruct
    private void initProductList() {
        products = new ArrayList<>();
    }
}
