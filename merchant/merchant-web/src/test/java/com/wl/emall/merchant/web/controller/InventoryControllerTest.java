package com.wl.emall.merchant.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wl.emall.merchant.domain.model.Inventory;
import com.wl.emall.merchant.domain.service.InventoryDomainService;
import com.wl.emall.merchant.web.dto.req.CreateInventoryReq;

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
class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    private MockMvc mockMvc;

    @Mock
    private InventoryDomainService inventoryDomainService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }


    @Test
    void create() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setMerchantId(1L);
        when(inventoryDomainService.createInventory(any())).thenReturn(inventory);
        mockMvc.perform(post("/inventory").characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(new CreateInventoryReq())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchantId").value("1"));
    }

    @Test
    void query() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setMerchantId(1L);
        when(inventoryDomainService.queryInventory(any())).thenReturn(inventory);
        mockMvc.perform(get("/inventory").characterEncoding("UTF-8")
                        .param("sku", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchantId").value("1"));
    }

    @Test
    void updateInventory() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setMerchantId(1L);
        when(inventoryDomainService.updateInventory(any(), any())).thenReturn(inventory);
        mockMvc.perform(post("/inventory/update").characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(new CreateInventoryReq())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void increaseInventory() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setMerchantId(1L);
        when(inventoryDomainService.increaseInventory(any(), any())).thenReturn(inventory);
        mockMvc.perform(post("/inventory/increase").characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(new CreateInventoryReq())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}