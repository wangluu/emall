package com.wl.emall.customer.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wl.emall.customer.biz.service.OrderBizService;
import com.wl.emall.customer.web.dto.req.CreateOrderReq;
import com.wl.emall.cutomer.domain.model.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wanglu
 */
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    private MockMvc mockMvc;

    @Mock
    private OrderBizService orderBizService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void create() throws Exception {

        Order order = new Order();
        order.setOrderId(1L);
        when(orderBizService.createOrder(any(), any(), any())).thenReturn(order);
        mockMvc.perform(post("/order").characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("customer-id", "1")
                        .content(toJsonString(new CreateOrderReq())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("1"));
    }

    private String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}