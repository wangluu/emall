package com.wl.emall.merchant.domain.service.impl;

import com.wl.emall.merchant.domain.model.MerchantOrder;
import com.wl.emall.merchant.domain.service.InventoryDomainService;
import com.wl.emall.merchant.domain.service.MerchantAccountDomainService;
import com.wl.emall.merchant.domain.service.MerchantDomainService;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

/**
 * @author wanglu
 */
@Service
public class MerchantDomainServiceImpl implements MerchantDomainService {

    @Resource
    private InventoryDomainService inventoryDomainService;

    @Resource
    private MerchantAccountDomainService merchantAccountDomainService;

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public void issueOrder(MerchantOrder order) {
        inventoryDomainService.issueOrder(order);
        merchantAccountDomainService.issueOrder(order);
    }

}
