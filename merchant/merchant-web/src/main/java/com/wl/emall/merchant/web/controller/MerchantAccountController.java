package com.wl.emall.merchant.web.controller;


import com.wl.emall.merchant.domain.service.MerchantAccountDomainService;
import com.wl.emall.merchant.web.converter.MerchantAccountConverter;
import com.wl.emall.merchant.web.dto.req.CreateMerchantAccountReq;
import com.wl.emall.merchant.web.dto.resp.MerchantAccountResp;

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
@RequestMapping("/merchant-account")
public class MerchantAccountController {

    @Resource
    private MerchantAccountDomainService merchantAccountDomainService;

    @PostMapping
    public MerchantAccountResp create(@RequestBody CreateMerchantAccountReq req) {
        return MerchantAccountConverter.convertResp(merchantAccountDomainService.createMerchantAccount(req.getMerchantId()));
    }

    @GetMapping
    public MerchantAccountResp query(@RequestParam(name = "merchantId") Long merchantId) {
        return MerchantAccountConverter.convertResp(merchantAccountDomainService.queryBalance(merchantId));
    }

}
