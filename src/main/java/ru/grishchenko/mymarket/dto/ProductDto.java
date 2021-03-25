package ru.grishchenko.mymarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.grishchenko.mymarket.models.Product;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private Integer price;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
    }

}
