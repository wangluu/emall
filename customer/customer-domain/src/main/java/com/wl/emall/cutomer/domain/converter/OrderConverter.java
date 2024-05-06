package com.wl.emall.cutomer.domain.converter;

import com.wl.emall.cutomer.domain.model.Order;
import com.wl.emall.cutomer.infra.dal.dataobject.OrderDO;

/**
 * @author wanglu
 */
public interface OrderConverter {

    static OrderDO convert(Order order) {
        OrderDO orderDO = new OrderDO();
        orderDO.setMerchantId(order.getMerchantId());
        orderDO.setCustomerId(order.getCustomerId());
        orderDO.setSku(order.getSku());
        orderDO.setPrice(order.getPrice());
        orderDO.setQuantity(order.getQuantity());
        orderDO.setPaidAmount(order.getPaidAmount());
        return orderDO;
    }

}
