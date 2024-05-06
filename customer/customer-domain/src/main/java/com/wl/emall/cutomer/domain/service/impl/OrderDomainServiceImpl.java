package com.wl.emall.cutomer.domain.service.impl;


import com.wl.emall.common.exception.util.AssertionUtil;
import com.wl.emall.cutomer.domain.converter.OrderConverter;
import com.wl.emall.cutomer.domain.model.Order;
import com.wl.emall.cutomer.domain.service.OrderDomainService;
import com.wl.emall.cutomer.infra.dal.dataobject.OrderDO;
import com.wl.emall.cutomer.infra.dal.repo.OrderRepository;
import com.wl.emall.cutomer.infra.exception.CustomerErrorCodeEnum;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * @author wanglu
 */
@Service
public class OrderDomainServiceImpl implements OrderDomainService {

    @Resource
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        AssertionUtil.assertAllNotNull(new Object[]{order.getSku(), order.getCustomerId(), order.getMerchantId(),
                order.getQuantity(), order.getPrice(), order.getPaidAmount()}, CustomerErrorCodeEnum.INVALID_PARAM);
        OrderDO orderDO = orderRepository.save(OrderConverter.convert(order));
        order.setOrderId(orderDO.getId());
        order.setOrderCreatedTime(orderDO.getCreatedTime());
        return order;
    }

}
