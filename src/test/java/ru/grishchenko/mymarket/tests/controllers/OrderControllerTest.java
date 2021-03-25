package ru.grishchenko.mymarket.tests.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.grishchenko.mymarket.controllers.OrderController;
import ru.grishchenko.mymarket.models.Cart;
import ru.grishchenko.mymarket.models.CartItem;
import ru.grishchenko.mymarket.models.Product;
import ru.grishchenko.mymarket.models.User;
import ru.grishchenko.mymarket.repositories.OrderRepository;
import ru.grishchenko.mymarket.services.CartService;
import ru.grishchenko.mymarket.services.OrderService;
import ru.grishchenko.mymarket.services.UserService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// не получается использовать classes постоянно требует другие зависимости,
// прописываю эти зависимости, но спринг говорит что таких бинов нет

@SpringBootTest(/*classes = {OrderRepository.class, OrderService.class, OrderController.class}*/)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest {

//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private OrderController orderController;

    @Autowired
    MockMvc mvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "Ilay", roles = "TESTER")
    public void CreateOrderFromCartTest() throws Exception {

        User demoUser = new User();
        demoUser.setId(1L);

        int totalPrice;


        Cart demoCart = new Cart();
        demoCart.setItems(new ArrayList<CartItem>());
        for (int i = 0; i < 3; i++) {
            Product product = new Product();
            product.setId((long)i + 1);
            product.setTitle("Product" + i);
            product.setPrice(100 * (i + 1));
            CartItem item = new CartItem(product);
            item.setCart(demoCart);
            demoCart.getItems().add(item);
            demoCart.recalculate();
        }

        totalPrice = demoCart.getPrice();

        Mockito
                .doReturn(Optional.of(demoCart))
                .when(cartService)
                .findById(Mockito.any(UUID.class));
        Mockito
                .doReturn(Optional.of(demoUser))
                .when(userService)
                .findByUsername(Mockito.any(String.class));

        mvc.perform(post("/api/v1/orders/" + UUID.randomUUID())
                .param("address", "testAddress")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.totalPrice", is(totalPrice)))
                .andExpect(jsonPath("$.items.length()", is(3)))
                .andExpect(jsonPath("$.items[0].productTitle", is("Product0")))
                .andExpect(jsonPath("$.items[0].quantity", is(1)))
                .andExpect(jsonPath("$.items[1].quantity", is(1)));



    }
}
