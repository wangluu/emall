package com.wl.emall.customer.biz.service.impl;

import com.wl.emall.common.exception.util.AssertionUtil;
import com.wl.emall.customer.biz.converter.OrderBizConverter;
import com.wl.emall.customer.biz.service.OrderBizService;
import com.wl.emall.cutomer.domain.model.CustomerAccount;
import com.wl.emall.cutomer.domain.model.Order;
import com.wl.emall.cutomer.domain.service.CustomerAccountDomainService;
import com.wl.emall.cutomer.domain.service.OrderDomainService;
import com.wl.emall.cutomer.infra.exception.CustomerErrorCodeEnum;
import com.wl.emall.merchant.domain.model.Inventory;
import com.wl.emall.merchant.domain.service.InventoryDomainService;
import com.wl.emall.merchant.domain.service.MerchantDomainService;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

/**
 * @author wanglu
 */
@Service
public class OrderBizServiceImpl implements OrderBizService {

    @Resource
    private OrderDomainService orderDomainService;

    @Resource
    private CustomerAccountDomainService customerAccountDomainService;

    @Resource
    private InventoryDomainService inventoryDomainService;

    @Resource
    private MerchantDomainService merchantDomainService;

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public Order createOrder(Long customerId, Long sku, Long quantity) {
        AssertionUtil.assertAllNotNull(new Object[]{customerId, sku, quantity}, CustomerErrorCodeEnum.BAD_REQUEST);

        Inventory inventory = inventoryDomainService.queryInventory(sku);
        AssertionUtil.assertTrue(inventory.getInventory() >= quantity, CustomerErrorCodeEnum.INVENTORY_NOT_ENOUGH);

        Order order = new Order();
        order.setMerchantId(inventory.getMerchantId());
        order.setPrice(inventory.getPrice());
        order.setQuantity(quantity);
        order.setSku(sku);
        order.setCustomerId(customerId);
        order.setPaidAmount(inventory.getPrice().multiply(BigDecimal.valueOf(quantity)));

        //check customer balance
        CustomerAccount customerAccount = customerAccountDomainService.queryBalance(order.getCustomerId());
        AssertionUtil.assertTrue(customerAccount.getBalance().compareTo(order.getPaidAmount()) >= 0, CustomerErrorCodeEnum.BALANCE_NOT_ENOUGH);

        //1. 创建订单
        order = orderDomainService.createOrder(order);
        //2. 扣减账户余额
        customerAccountDomainService.deductBalance(customerAccount.getCustomerId(), order.getPaidAmount());

        //商户出单 扣减库存&增加商户账户余额
        merchantDomainService.issueOrder(OrderBizConverter.convert2MerchantOrder(order));
        return order;

    }

}
