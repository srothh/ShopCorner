package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CartEndpointTest implements TestData {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private ObjectMapper objectMapper;


    private final Cart cart = new Cart();
    private final CartItem cartItem = new CartItem();

    @BeforeEach
    public void beforeEach() {
        this.cartItemRepository.deleteAll();
        this.cartRepository.deleteAll();

        cart.setCreatedAt(LocalDateTime.now());

        Set<CartItem> itemSet = new HashSet<>();

        cartItem.setProductId(1L);
        cartItem.setQuantity(5);
        itemSet.add(cartItem);
        cart.setItems(itemSet);
    }

    @Test
    public void givenAllPropertiesWithCookie_DeletePostFirstCartItem_thenCart() throws Exception {
        UUID sessionId = UUID.randomUUID();
        cart.setSessionId(sessionId);
        this.cartRepository.save(cart);
        Long id = this.cartItemRepository.save(cartItem).getId();

        MvcResult mvcResult = this.mockMvc.perform(delete(CART_BASE_URI + "/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .cookie(new Cookie("sessionId", sessionId.toString())))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }


    @Test
    public void givenAllPropertiesWithCookie_GetPostFirstCartItem_thenCart() throws Exception {
        UUID sessionId = UUID.randomUUID();
        cart.setSessionId(sessionId);
        this.cartRepository.save(cart);
        this.cartItemRepository.save(cartItem);

        MvcResult mvcResult = this.mockMvc.perform(get(CART_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .cookie(new Cookie("sessionId", sessionId.toString())))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        CartDto cartDto = objectMapper.readValue(response.getContentAsString(), CartDto.class);

        CartItemDto itemDto = new CartItemDto();
        for (CartItemDto item : cartDto.getCartItems()) {
            itemDto = item;
        }
        CartItemDto finalItemDto = itemDto;
        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertNotNull(cartDto.getId()),
            () -> assertNotNull(cartDto.getCartItems()),
            () -> assertEquals(cartItem.getProductId(), finalItemDto.getProductId())
        );
    }


    @Test
    public void givenAllPropertiesWithCookie_whenPutCartItem_thenCart() throws Exception {
        UUID sessionId = UUID.randomUUID();
        cart.setSessionId(sessionId);
        this.cartRepository.save(cart);
        this.cartItemRepository.save(cartItem);
        cartItem.setQuantity(5);
        CartItemDto cartItemDto = this.cartItemMapper.cartItemToCartItemDto(cartItem);
        String body = objectMapper.writeValueAsString(cartItemDto);

        MvcResult mvcResult = this.mockMvc.perform(put(CART_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body).cookie(new Cookie("sessionId", sessionId.toString())))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        CartDto cartDto = objectMapper.readValue(response.getContentAsString(), CartDto.class);

        CartItemDto itemDto = new CartItemDto();
        for (CartItemDto item : cartDto.getCartItems()) {
            itemDto = item;
        }
        CartItemDto finalItemDto = itemDto;
        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertNotNull(cartDto.getId()),
            () -> assertNotNull(cartDto.getCartItems()),
            () -> assertEquals(cartItem.getProductId(), finalItemDto.getProductId()),
            () -> assertEquals(cartItem.getQuantity(), finalItemDto.getQuantity())
        );
    }

    @Test
    public void givenAllPropertiesWithCookie_whenPostFirstCartItem_thenCart() throws Exception {
        UUID sessionId = UUID.randomUUID();
        cart.setSessionId(sessionId);
        this.cartRepository.save(cart);
        this.cartItemRepository.save(cartItem);
        CartItemDto cartItemDto = this.cartItemMapper.cartItemToCartItemDto(cartItem);

        String body = objectMapper.writeValueAsString(cartItemDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CART_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body).cookie(new Cookie("sessionId", sessionId.toString())))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        CartDto cartDto = objectMapper.readValue(response.getContentAsString(), CartDto.class);

        CartItemDto itemDto = new CartItemDto();
        for (CartItemDto item : cartDto.getCartItems()) {
            itemDto = item;
        }
        CartItemDto finalItemDto = itemDto;
        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertNotNull(cartDto.getId()),
            () -> assertNotNull(cartDto.getCartItems()),
            () -> assertEquals(cartItem.getProductId(), finalItemDto.getProductId())
        );
    }


    @Test
    public void givenAllPropertiesWithoutCookie_whenPostFirstCartItem_thenCartAndCookie() throws Exception {
        CartItemDto cartItemDto = this.cartItemMapper.cartItemToCartItemDto(cartItem);
        String body = objectMapper.writeValueAsString(cartItemDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CART_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        CartDto cartDto = objectMapper.readValue(response.getContentAsString(), CartDto.class);

        CartItemDto itemDto = new CartItemDto();
        for (CartItemDto item : cartDto.getCartItems()) {
            itemDto = item;
        }
        CartItemDto finalItemDto = itemDto;
        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertNotNull(response.getCookie("sessionId")),
            () -> assertNotNull(cartDto.getId()),
            () -> assertNotNull(cartDto.getCartItems()),
            () -> assertEquals(cartItem.getProductId(), finalItemDto.getProductId())
        );
    }

    @Test
    public void givenNothing_whenPost_then400() throws Exception {

        String body = objectMapper.writeValueAsString(new CartItemDto());

        MvcResult mvcResult = this.mockMvc.perform(post(INVOICE_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(3, errors.length);
            }
        );
    }

    @Test
    public void givenNothing_whenPut_then400() throws Exception {
        CartItemDto cartItemDto = new CartItemDto();
        String body = objectMapper.writeValueAsString(cartItemDto);

        MvcResult mvcResult = this.mockMvc.perform(put(CART_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(2, errors.length);
            }
        );
    }

    @Test
    public void givenNothing_whenGet_thenEmptyCartAndCookie() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(CART_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        CartDto cartDto = objectMapper.readValue(response.getContentAsString(), CartDto.class);
        assertNotNull(cartDto.getCartItems());
        assertNotNull(cartDto.getId());
        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType()),
            () -> assertNotNull(response.getCookie("sessionId"))
        );

    }

    @Test
    public void givenWrongId_whenDelete_then200() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(delete(CART_BASE_URI + "/" + 0L)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

    }




}
