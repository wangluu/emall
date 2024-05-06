package com.wl.emall.customer.biz.service;


import com.wl.emall.cutomer.domain.model.Order;

/**
 * @author wanglu
 */
public interface OrderBizService {

    Order createOrder(Long customerId, Long sku, Long quantity);

}
