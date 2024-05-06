package com.wl.emall.customer.web.controller;

import com.wl.emall.customer.biz.service.OrderBizService;
import com.wl.emall.customer.web.converter.OrderConverter;
import com.wl.emall.customer.web.dto.req.CreateOrderReq;
import com.wl.emall.customer.web.dto.resp.OrderResp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderBizService orderBizService;

    @PostMapping
    public OrderResp create(@RequestHeader("customer-id") Long customerId, @RequestBody CreateOrderReq req) {
        return OrderConverter.convert(orderBizService.createOrder(customerId, req.getSku(), req.getQuantity()));
    }

}
