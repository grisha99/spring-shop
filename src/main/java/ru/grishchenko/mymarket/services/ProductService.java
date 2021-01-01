package ru.grishchenko.mymarket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.grishchenko.mymarket.models.Product;
import ru.grishchenko.mymarket.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getProductPage(int page, int count) {

        return productRepository.findAll(PageRequest.of(page, count));
    }

    public Page<Product> getProductByPrice(int min,int max, int page, int count) {
        return productRepository.findAllByPriceBetween(min, max, PageRequest.of(page, count));
    }


    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
