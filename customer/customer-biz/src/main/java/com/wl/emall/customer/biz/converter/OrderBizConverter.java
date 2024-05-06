package com.wl.emall.customer.biz.converter;

import com.wl.emall.cutomer.domain.model.Order;
import com.wl.emall.merchant.domain.model.MerchantOrder;

/**
 * @author wanglu
 */
public interface OrderBizConverter {

    static MerchantOrder convert2MerchantOrder(Order order) {
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setOrderId(order.getOrderId());
        merchantOrder.setSku(order.getSku());
        merchantOrder.setPrice(order.getPrice());
        merchantOrder.setQuantity(order.getQuantity());
        merchantOrder.setAmountToCollect(order.getPaidAmount());
        merchantOrder.setMerchantId(order.getMerchantId());
        merchantOrder.setOrderCreatedTime(order.getOrderCreatedTime());
        return merchantOrder;
    }

}
