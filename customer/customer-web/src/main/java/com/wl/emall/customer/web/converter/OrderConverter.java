package com.wl.emall.customer.web.converter;


import com.wl.emall.customer.web.dto.resp.OrderResp;
import com.wl.emall.cutomer.domain.model.Order;

/**
 * @author wanglu
 */
public interface OrderConverter {

    static OrderResp convert(Order order) {
        OrderResp resp = new OrderResp();
        resp.setOrderId(order.getOrderId());
        return resp;
    }

}
