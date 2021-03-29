package ru.grishchenko.mymarket.tests.controllers;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.grishchenko.mymarket.models.Cart;
import ru.grishchenko.mymarket.repositories.CartRepository;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private CartRepository cartRepository;

    private UUID uuid;

    @BeforeEach
    public void getUUID() {
        uuid = cartRepository.save(new Cart()).getId();
    }

    @Test
    public void getNewUUIDCartTest() throws Exception{
        MvcResult result = mvc.perform(get("/api/v1/cart/new")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertDoesNotThrow(() -> UUID.fromString(result.getResponse().getContentAsString().replace("\"", "")));
    }

    @Test
    public void getCartWithAddProductTest() throws Exception{
        mvc.perform(get("/api/v1/cart/" + uuid + "/add/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/api/v1/cart/" + uuid + "/add/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/api/v1/cart/" + uuid + "/add/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/cart/" + uuid + "/add/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(get("/api/v1/cart/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()", is(2)))
                .andExpect(jsonPath("$.items[0].productTitle", is("Milk")))
                .andExpect(jsonPath("$.items[0].quantity", is(2)))
                .andExpect(jsonPath("$.items[1].quantity", is(1)));
    }

    @Test
    public void deleteProductFromCartTest() throws Exception {

        mvc.perform(get("/api/v1/cart/" + uuid + "/add/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/api/v1/cart/" + uuid + "/add/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/cart/" + uuid + "/add/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/cart/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", is(2)))
                .andExpect(jsonPath("$.items[0].quantity", is(2)))
                .andExpect(jsonPath("$.items[1].quantity", is(1)));

        mvc.perform(get("/api/v1/cart/" + uuid + "/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/cart/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", is(2)))
                .andExpect(jsonPath("$.items[0].quantity", is(1)))
                .andExpect(jsonPath("$.items[1].quantity", is(1)));

        mvc.perform(get("/api/v1/cart/" + uuid + "/delete/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/cart/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", is(1)))
                .andExpect(jsonPath("$.items[0].quantity", is(1)))
                .andExpect(jsonPath("$.items[0].productTitle", is("Milk")));
    }




}
