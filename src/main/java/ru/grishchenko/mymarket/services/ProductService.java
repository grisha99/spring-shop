package ru.grishchenko.mymarket.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.grishchenko.mymarket.dto.ProductDto;
import ru.grishchenko.mymarket.models.Product;
import ru.grishchenko.mymarket.repositories.ProductRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Optional<ProductDto> getProductDtoById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public Page<ProductDto> getProductPage(int page, int count) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, count));
        return new PageImpl<>(productPage.getContent().stream().map(ProductDto::new).collect(Collectors.toList()),
                productPage.getPageable(),
                productPage.getTotalElements());
    }

    public Page<ProductDto> getProductByPrice(int min,int max, int page, int count) {
        Page<Product> productPage = productRepository.findAllByPriceBetween(min, max, PageRequest.of(page, count));
        return new PageImpl<>(productPage.getContent().stream().map(ProductDto::new).collect(Collectors.toList()),
                productPage.getPageable(),
                productPage.getTotalElements());
    }


    public ProductDto saveOrUpdate(ProductDto productDto) {
        Product p = modelMapper.map(productDto, Product.class);
        return modelMapper.map(productRepository.save(p), ProductDto.class);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
