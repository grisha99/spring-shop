package ru.grishchenko.mymarket.tests.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.grishchenko.mymarket.dto.ProductDto;
import ru.grishchenko.mymarket.models.Product;
import ru.grishchenko.mymarket.repositories.ProductRepository;
import ru.grishchenko.mymarket.repositories.specifications.ProductSpecification;
import ru.grishchenko.mymarket.services.ProductService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(classes = {ProductService.class, ModelMapper.class})
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private MultiValueMap<String, String> params(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "");
        params.add("min_price", "");
        params.add("max_price", "");
        params.add("page", "0");
        params.add("count", "7");
        return params;
    }

    private Product newProduct(Long id, String title, int price) {
        Product p = new Product();
        p.setTitle(title);
        p.setPrice(price);
        p.setId(id);
        return p;
    }

    @Test
    public void getProductByIdTest() {
        Product demoProduct = new Product();
        demoProduct.setTitle("Marlboro");
        demoProduct.setPrice(150);
        demoProduct.setId(101L);

        Mockito
                .doReturn(Optional.of(demoProduct))
                .when(productRepository)
                .findById(101L);

        Product p = productService.getProductById(101L).get();
        Mockito.verify(productRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(101L));
        Assertions.assertEquals("Marlboro", p.getTitle());
    }

    @Test
    public void getProductDtoByIdTest() {
        Product demoProduct = new Product();
        demoProduct.setTitle("Marlboro");
        demoProduct.setPrice(150);
        demoProduct.setId(101L);

        Mockito
                .doReturn(Optional.of(demoProduct))
                .when(productRepository)
                .findById(101L);

        ProductDto productDto = productService.getProductDtoById(101L).get();
        Mockito.verify(productRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(101L));
        Assertions.assertEquals("Marlboro", productDto.getTitle());
    }

    @Test
    public void saveOrUpdate() {
        Product demoProduct = new Product();
        demoProduct.setTitle("Marlboro");
        demoProduct.setPrice(150);
        demoProduct.setId(101L);

        Mockito
                .doReturn(demoProduct)
                .when(productRepository)
                .save(demoProduct);

        ProductDto productDto = productService.saveOrUpdate(new ProductDto(demoProduct));
//        Mockito.verify(productRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(101L));
        Assertions.assertEquals("Marlboro", productDto.getTitle());
    }

}
