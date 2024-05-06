package com.wl.emall.merchant.web.controller;


import com.wl.emall.merchant.domain.service.InventoryDomainService;
import com.wl.emall.merchant.web.converter.InventoryConverter;
import com.wl.emall.merchant.web.dto.req.CreateInventoryReq;
import com.wl.emall.merchant.web.dto.req.IncreaseInventoryReq;
import com.wl.emall.merchant.web.dto.req.UpdateInventoryReq;
import com.wl.emall.merchant.web.dto.resp.InventoryResp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Resource
    private InventoryDomainService inventoryDomainService;

    @PostMapping
    public InventoryResp create(@RequestBody CreateInventoryReq req) {
        return InventoryConverter.convertResp(inventoryDomainService.createInventory(InventoryConverter.convert(req)));
    }

    @GetMapping
    public InventoryResp query(@RequestParam("sku") Long sku) {
        return InventoryConverter.convertResp(inventoryDomainService.queryInventory(sku));
    }

    @PostMapping("/update")
    public InventoryResp updateInventory(@RequestBody UpdateInventoryReq req) {
        return InventoryConverter.convertResp(inventoryDomainService.updateInventory(req.getSku(), req.getInventory()));
    }

    @PostMapping("/increase")
    public InventoryResp increaseInventory(@RequestBody IncreaseInventoryReq req) {
        return InventoryConverter.convertResp(inventoryDomainService.increaseInventory(req.getSku(), req.getInventory()));
    }

}
