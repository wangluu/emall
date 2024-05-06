package com.wl.emall.customer.biz.service.impl;

import com.wl.emall.common.exception.EmallCommonException;
import com.wl.emall.cutomer.domain.model.CustomerAccount;
import com.wl.emall.cutomer.domain.model.Order;
import com.wl.emall.cutomer.domain.service.CustomerAccountDomainService;
import com.wl.emall.cutomer.domain.service.OrderDomainService;
import com.wl.emall.merchant.domain.model.Inventory;
import com.wl.emall.merchant.domain.service.InventoryDomainService;
import com.wl.emall.merchant.domain.service.MerchantDomainService;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author wanglu
 */
@ExtendWith(MockitoExtension.class)
class OrderBizServiceImplTest {

    @InjectMocks
    OrderBizServiceImpl orderBizService;

    @Mock
    private OrderDomainService orderDomainService;

    @Mock
    private CustomerAccountDomainService customerAccountDomainService;

    @Mock
    private InventoryDomainService inventoryDomainService;

    @Mock
    private MerchantDomainService merchantDomainService;

    @Test
    void createOrder() {
        Inventory inventory = new Inventory();
        inventory.setInventory(100L);
        inventory.setPrice(new BigDecimal("50"));
        when(inventoryDomainService.queryInventory(any())).thenReturn(inventory);

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setBalance(new BigDecimal("100"));
        when(customerAccountDomainService.queryBalance(any())).thenReturn(customerAccount);

        Order order = new Order();
        order.setOrderId(1L);
        when(orderDomainService.createOrder(any())).thenReturn(order);
        Order result = orderBizService.createOrder(1L, 1L, 2L);
        verify(orderDomainService).createOrder(any());
        verify(customerAccountDomainService).deductBalance(any(), any());
        verify(merchantDomainService).issueOrder(any());
        Assertions.assertEquals(1L, result.getOrderId());

        //库存不足
        Assertions.assertThrows(EmallCommonException.class, () -> orderBizService.createOrder(1L, 1L, 110L));
        //余额不足
        Assertions.assertThrows(EmallCommonException.class, () -> orderBizService.createOrder(1L, 1L, 3L));

    }

}