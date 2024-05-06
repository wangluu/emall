package com.wl.emall.customer.web.controller;

import com.wl.emall.customer.web.converter.CustomerAccountConverter;
import com.wl.emall.customer.web.dto.req.CreateCustomerAccountReq;
import com.wl.emall.customer.web.dto.req.RechargeReq;
import com.wl.emall.customer.web.dto.resp.CustomerAccountResp;
import com.wl.emall.cutomer.domain.service.CustomerAccountDomainService;

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
@RequestMapping(path = "/customer-account")
public class CustomerAccountController {

    @Resource
    private CustomerAccountDomainService customerAccountDomainService;

    @PostMapping
    public CustomerAccountResp create(@RequestBody CreateCustomerAccountReq req) {
        return CustomerAccountConverter.convertResp(customerAccountDomainService.createCustomerAccount(req.getCustomerId()));
    }

    @PostMapping("/recharge")
    public CustomerAccountResp recharge(@RequestBody RechargeReq req) {
        return CustomerAccountConverter.convertResp(customerAccountDomainService.recharge(req.getCustomerId(), req.getAmount()));
    }

    @GetMapping
    public CustomerAccountResp query(@RequestParam(name = "customerId") Long customerId) {
        return CustomerAccountConverter.convertResp(customerAccountDomainService.queryBalance(customerId));
    }

}
