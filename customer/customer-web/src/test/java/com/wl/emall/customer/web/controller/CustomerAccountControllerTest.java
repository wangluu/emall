package com.wl.emall.customer.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wl.emall.customer.web.dto.req.CreateCustomerAccountReq;
import com.wl.emall.customer.web.dto.req.RechargeReq;
import com.wl.emall.cutomer.domain.model.CustomerAccount;
import com.wl.emall.cutomer.domain.service.CustomerAccountDomainService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wanglu
 */
@ExtendWith(MockitoExtension.class)
class CustomerAccountControllerTest {

    @InjectMocks
    CustomerAccountController customerAccountController;

    private MockMvc mockMvc;

    @Mock
    private CustomerAccountDomainService customerAccountDomainService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerAccountController).build();
    }

    @Test
    void create() throws Exception {
        CustomerAccount account = new CustomerAccount();
        account.setCustomerId(1L);
        when(customerAccountDomainService.createCustomerAccount(any())).thenReturn(account);
        mockMvc.perform(post("/customer-account").characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(new CreateCustomerAccountReq())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("1"));
    }

    @Test
    void recharge() throws Exception {
        CustomerAccount account = new CustomerAccount();
        account.setCustomerId(1L);
        when(customerAccountDomainService.recharge(any(), any())).thenReturn(account);
        mockMvc.perform(post("/customer-account/recharge").characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(new RechargeReq())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("1"));
    }

    @Test
    void query() throws Exception {
        CustomerAccount account = new CustomerAccount();
        account.setCustomerId(1L);
        when(customerAccountDomainService.queryBalance(any())).thenReturn(account);
        mockMvc.perform(get("/customer-account").characterEncoding("UTF-8")
                        .param("customerId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("1"));
    }

    private String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}