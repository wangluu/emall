package com.wl.emall.cutomer.domain.service.impl;

import com.wl.emall.cutomer.domain.model.Order;
import com.wl.emall.cutomer.infra.dal.dataobject.OrderDO;
import com.wl.emall.cutomer.infra.dal.repo.OrderRepository;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * @author wanglu
 */
@ExtendWith(MockitoExtension.class)
class OrderDomainServiceImplTest {

    @InjectMocks
    OrderDomainServiceImpl orderDomainService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void createOrder() {

        Mockito.when(orderRepository.save(any())).thenAnswer(x -> {
            OrderDO orderDO = x.getArgument(0);
            orderDO.setId(1L);
            orderDO.setCreatedTime(new Date());
            return orderDO;
        });

        Order order = new Order();
        order.setSku(1L);
        order.setPrice(new BigDecimal("50"));
        order.setPaidAmount(new BigDecimal("100"));
        order.setQuantity(2L);
        order.setMerchantId(1L);
        order.setCustomerId(1L);
        Order result = orderDomainService.createOrder(order);
        verify(orderRepository).save(any());
        Assertions.assertEquals(1L, order.getOrderId());
    }

}