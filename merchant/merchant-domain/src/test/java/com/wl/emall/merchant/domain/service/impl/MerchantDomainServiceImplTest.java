package com.wl.emall.merchant.domain.service.impl;

import com.wl.emall.merchant.domain.model.MerchantOrder;
import com.wl.emall.merchant.domain.service.InventoryDomainService;
import com.wl.emall.merchant.domain.service.MerchantAccountDomainService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * @author wanglu
 */
@ExtendWith(MockitoExtension.class)
class MerchantDomainServiceImplTest {

    @InjectMocks
    MerchantDomainServiceImpl merchantDomainService;

    @Mock
    private InventoryDomainService inventoryDomainService;

    @Mock
    private MerchantAccountDomainService merchantAccountDomainService;

    @Test
    void issueOrder() {

        merchantDomainService.issueOrder(new MerchantOrder());

        verify(inventoryDomainService).issueOrder(any());
        verify(merchantAccountDomainService).issueOrder(any());
    }

}