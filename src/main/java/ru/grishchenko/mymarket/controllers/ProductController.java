package ru.grishchenko.mymarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.grishchenko.mymarket.dto.ProductDto;
import ru.grishchenko.mymarket.services.ProductService;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(required = false, name = "min_price") Integer minCost,
                                           @RequestParam(required = false, name = "max_price") Integer maxCost,
                                           @RequestParam(defaultValue = "0", name = "page") Integer page,
                                           @RequestParam(defaultValue = "5", name = "count") Integer count) {
        if ((minCost != null) || maxCost != null) {
            if (minCost == null) {
                minCost = 0;
            }
            if (maxCost == null) {
                maxCost = Integer.MAX_VALUE;
            }
            return productService.getProductByPrice(minCost, maxCost, page, count);
        }
        return productService.getProductPage(page, count);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.saveOrUpdate(productDto);
    }

    @PutMapping
    public ProductDto modifiedProduct(@RequestBody ProductDto productDto) {
        return productService.saveOrUpdate(productDto);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductDtoById(id).get();
    }


    @DeleteMapping
    public void deleteProductById(@RequestParam Long id) {
        productService.deleteProductById(id);
    }
}
