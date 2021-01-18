package ru.grishchenko.mymarket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.grishchenko.mymarket.dto.ProductDto;
import ru.grishchenko.mymarket.repositories.CartRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    public List<ProductDto> getCartProducts() {
        return Collections.unmodifiableList(cartRepository.getProducts());
    }

    public void removeProductByIndex(int index) {
        cartRepository.removeProduct(index);
    }

    public void addProductById(Long id) {
        cartRepository.addProduct(productService.getProductDtoById(id).get());
    }

    public void clear() {
        cartRepository.clear();
    }

}
