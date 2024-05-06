package com.wl.emall.merchant.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wl.emall.merchant.domain.model.MerchantAccount;
import com.wl.emall.merchant.domain.service.MerchantAccountDomainService;
import com.wl.emall.merchant.web.dto.req.CreateMerchantAccountReq;

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
public class MerchantAccountControllerTest {

    @InjectMocks
    private MerchantAccountController merchantAccountController;

    private MockMvc mockMvc;

    @Mock
    private MerchantAccountDomainService merchantAccountDomainService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(merchantAccountController).build();
    }

    @Test
    void create() throws Exception {
        MerchantAccount merchantAccount = new MerchantAccount();
        merchantAccount.setMerchantAccountId(1L);
        merchantAccount.setMerchantId(1L);
        when(merchantAccountDomainService.createMerchantAccount(any())).thenReturn(merchantAccount);
        mockMvc.perform(post("/merchant-account").characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(new CreateMerchantAccountReq())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchantId").value("1"));
    }

    @Test
    void query() throws Exception {
        MerchantAccount merchantAccount = new MerchantAccount();
        merchantAccount.setMerchantAccountId(1L);
        merchantAccount.setMerchantId(1L);
        when(merchantAccountDomainService.queryBalance(any())).thenReturn(merchantAccount);
        mockMvc.perform(get("/merchant-account").characterEncoding("UTF-8")
                        .param("merchantId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchantId").value("1"));
    }

    private String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}