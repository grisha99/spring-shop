package ru.grishchenko.mymarket.tests.controllers;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.grishchenko.mymarket.dto.ProductDto;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;


    private MultiValueMap<String, String> params(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "");
        params.add("min_price", "");
        params.add("max_price", "");
        params.add("page", "0");
        params.add("count", "7");
        return params;
    }

    @Test
    @Order(1)
    public void getAllProductsNoParams() throws Exception {
            mvc.perform(get("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content", hasSize(3)))
                    .andExpect(jsonPath("$.content[0].title", is("Milk")));
    }

    @Test
    @Order(2)
    public void getAllProductsInPageCountTest() throws Exception {
        MultiValueMap<String, String> mvm = params();
        for (int i = 1; i < 8; i++) {
            mvm.set("count", String.valueOf(i));
            mvc.perform(get("/api/v1/products")
                    .params(mvm)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content", hasSize(Integer.parseInt(mvm.getFirst("count")))))
                    .andExpect(jsonPath("$.content[0].title", is("Milk")));
        }
    }
    @Test
    @Order(3)
    public void getAllProductsPageTest() throws Exception {
        MultiValueMap<String, String> mvm = params();
        String[] titles = {"Milk", "Meat", "Oil"};
        mvm.set("count", "2");
        for (int i = 0; i < 3; i++) {
            mvm.set("page", String.valueOf(i));
            mvc.perform(get("/api/v1/products")
                    .params(mvm)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.content[0].title", is(titles[i])));
        }
    }

    @Test
    @Order(4)
    public void getProductByIdTest() throws Exception {
        mvc.perform(get("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id", is(1)));
        mvc.perform(get("/api/v1/products/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    public void addNewProductTest() throws Exception {
        String p = "{\"id\": \"\", \"title\": \"NewProduct\", \"price\": 1010}";
        mvc.perform(post("/api/v1/products")
                .content(p)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @Order(6)
    public void editProductTest() throws Exception {
        String existProduct = "{\"id\": 1, \"title\": \"EditedProduct\", \"price\": 101}";
        String notExistProduct = "{\"id\": 100, \"title\": \"InsertedProduct\", \"price\": 1010}";
        mvc.perform(put("/api/v1/products")
                .content(existProduct)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.title", is("EditedProduct")))
                .andExpect(jsonPath("$.id", is(1)));
        mvc.perform(put("/api/v1/products")
                .content(notExistProduct)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.message", is("Product by ID: 100 not found")));
    }

}
