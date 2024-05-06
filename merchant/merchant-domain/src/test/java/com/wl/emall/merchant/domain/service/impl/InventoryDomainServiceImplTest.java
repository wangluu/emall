package com.wl.emall.merchant.domain.service.impl;

import com.wl.emall.common.exception.EmallCommonException;
import com.wl.emall.merchant.domain.model.Inventory;
import com.wl.emall.merchant.domain.model.MerchantOrder;
import com.wl.emall.merchant.infra.dal.dataobject.InventoryDO;
import com.wl.emall.merchant.infra.dal.repo.InventoryDailyStatisticRepository;
import com.wl.emall.merchant.infra.dal.repo.InventoryHistoryRepository;
import com.wl.emall.merchant.infra.dal.repo.InventoryRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author wanglu
 */
@ExtendWith(MockitoExtension.class)
class InventoryDomainServiceImplTest {

    @InjectMocks
    InventoryDomainServiceImpl inventoryDomainService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryHistoryRepository inventoryHistoryRepository;

    @Mock
    private InventoryDailyStatisticRepository inventoryDailyStatisticRepository;

    @Test
    void createInventory() {
        when(inventoryRepository.save(any(InventoryDO.class))).thenAnswer(invoke -> {
            InventoryDO inventory = invoke.getArgument(0);
            inventory.setId(1L);
            return inventory;
        });

        Inventory inventory = new Inventory();
        inventory.setSku(1L);
        inventory.setPrice(BigDecimal.ONE);
        inventory.setMerchantId(1L);
        inventory.setInventory(100L);
        Inventory result = inventoryDomainService.createInventory(inventory);

        Assertions.assertEquals(1L, result.getInventoryId());
        verify(inventoryRepository).save(any());
    }

    @Test
    void queryInventory() {
        Assertions.assertThrows(EmallCommonException.class, () -> inventoryDomainService.queryInventory(1L));

        reset(inventoryRepository);
        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setId(1L);
        when(inventoryRepository.findOne(any(Example.class))).thenReturn(Optional.of(inventoryDO));
        Inventory inventory = inventoryDomainService.queryInventory(1L);

        Assertions.assertEquals(1L, inventory.getInventoryId());
        verify(inventoryRepository).findOne(any(Example.class));
    }

    @Test
    void updateInventory() {
        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setId(1L);
        inventoryDO.setInventory(10L);
        when(inventoryRepository.findOne(any(Example.class))).thenReturn(Optional.of(inventoryDO));
        when(inventoryRepository.save(any())).thenAnswer(x -> x.getArgument(0));
        Inventory inventory = inventoryDomainService.updateInventory(1L, 100L);

        Assertions.assertEquals(100L, inventory.getInventory());
        verify(inventoryRepository).findOne(any(Example.class));
        verify(inventoryRepository).save(any());
    }

    @Test
    void increaseInventory() {
        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setId(1L);
        inventoryDO.setInventory(10L);
        when(inventoryRepository.findOne(any(Example.class))).thenReturn(Optional.of(inventoryDO));
        when(inventoryRepository.save(any())).thenAnswer(x -> x.getArgument(0));
        Inventory inventory = inventoryDomainService.increaseInventory(1L, 100L);

        Assertions.assertEquals(110L, inventory.getInventory());
        verify(inventoryRepository).findOne(any(Example.class));
        verify(inventoryRepository).save(any());
    }

    @Test
    void deductInventory() {
        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setId(1L);
        inventoryDO.setInventory(100L);
        when(inventoryRepository.findOne(any(Example.class))).thenReturn(Optional.of(inventoryDO));
        when(inventoryRepository.save(any())).thenAnswer(x -> x.getArgument(0));
        Inventory inventory = inventoryDomainService.deductInventory(1L, 10L);

        Assertions.assertEquals(90L, inventory.getInventory());
        verify(inventoryRepository).findOne(any(Example.class));
        verify(inventoryRepository).save(any());
    }

    @Test
    void issueOrder() {

        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setId(1L);
        inventoryDO.setInventory(100L);
        when(inventoryRepository.findOne(any(Example.class))).thenReturn(Optional.of(inventoryDO));
        when(inventoryRepository.save(any())).thenAnswer(x -> x.getArgument(0));

        when(inventoryDailyStatisticRepository.findOne(any(Example.class))).thenReturn(Optional.empty());

        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setOrderId(1L);
        merchantOrder.setQuantity(2L);
        merchantOrder.setPrice(new BigDecimal("50"));
        merchantOrder.setSku(1L);
        merchantOrder.setMerchantId(1L);
        merchantOrder.setAmountToCollect(new BigDecimal("100"));
        merchantOrder.setOrderCreatedTime(new Date());
        inventoryDomainService.issueOrder(merchantOrder);

        verify(inventoryRepository).save(any());
        verify(inventoryDailyStatisticRepository).save(any());
        verify(inventoryHistoryRepository).save(any());

    }

}