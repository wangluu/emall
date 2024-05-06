package com.wl.emall.merchant.domain.service.impl;

import com.wl.emall.common.exception.EmallCommonException;
import com.wl.emall.common.exception.util.AssertionUtil;
import com.wl.emall.common.util.DateTimeUtil;
import com.wl.emall.merchant.domain.converter.InventoryConverter;
import com.wl.emall.merchant.domain.model.Inventory;
import com.wl.emall.merchant.domain.model.MerchantOrder;
import com.wl.emall.merchant.domain.service.InventoryDomainService;
import com.wl.emall.merchant.infra.dal.dataobject.InventoryDO;
import com.wl.emall.merchant.infra.dal.dataobject.InventoryDailyStatisticDO;
import com.wl.emall.merchant.infra.dal.dataobject.InventoryHistoryDO;
import com.wl.emall.merchant.infra.dal.repo.InventoryDailyStatisticRepository;
import com.wl.emall.merchant.infra.dal.repo.InventoryHistoryRepository;
import com.wl.emall.merchant.infra.dal.repo.InventoryRepository;
import com.wl.emall.merchant.infra.exception.MerchantErrorCodeEnum;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

/**
 * @author wanglu
 */
@Service
public class InventoryDomainServiceImpl implements InventoryDomainService {

    @Resource
    private InventoryRepository inventoryRepository;

    @Resource
    private InventoryHistoryRepository inventoryHistoryRepository;

    @Resource
    private InventoryDailyStatisticRepository inventoryDailyStatisticRepository;

    @Override
    public synchronized Inventory createInventory(Inventory inventory) {
        AssertionUtil.assertTrue(inventory.getSku() != null && inventory.getMerchantId() != null
                && inventory.getPrice() != null && inventory.getPrice().compareTo(BigDecimal.ZERO) >= 0
                && inventory.getInventory() != null && inventory.getInventory() >= 0, MerchantErrorCodeEnum.BAD_REQUEST);
        AssertionUtil.assertTrue(optionalFindBySku(inventory.getSku()).isEmpty(), MerchantErrorCodeEnum.RESOURCE_ALREADY_EXISTS);
        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setSku(inventory.getSku());
        inventoryDO.setMerchantId(inventory.getMerchantId());
        inventoryDO.setPrice(inventory.getPrice());
        inventoryDO.setInventory(inventory.getInventory());
        return InventoryConverter.convert(inventoryRepository.save(inventoryDO));
    }

    @Override
    public Inventory queryInventory(Long sku) {
        AssertionUtil.assertNotNull(sku, MerchantErrorCodeEnum.BAD_REQUEST);
        InventoryDO inventoryDO = findBySku(sku);
        return InventoryConverter.convert(inventoryDO);
    }

    @Override
    public Inventory updateInventory(Long sku, Long inventory) {
        AssertionUtil.assertTrue(sku != null && inventory != null && inventory >= 0, MerchantErrorCodeEnum.BAD_REQUEST);
        InventoryDO inventoryDO = findBySku(sku);
        inventoryDO.setInventory(inventory);
        return InventoryConverter.convert(inventoryRepository.save(inventoryDO));
    }

    @Override
    public Inventory increaseInventory(Long sku, Long inventoryToIncrease) {
        AssertionUtil.assertTrue(sku != null && inventoryToIncrease != null && inventoryToIncrease >= 0, MerchantErrorCodeEnum.BAD_REQUEST);
        InventoryDO inventoryDO = findBySku(sku);
        inventoryDO.setInventory(inventoryDO.getInventory() + inventoryToIncrease);
        return InventoryConverter.convert(inventoryRepository.save(inventoryDO));
    }

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public Inventory deductInventory(Long sku, Long inventoryToDeduct) {
        AssertionUtil.assertTrue(sku != null && inventoryToDeduct != null && inventoryToDeduct >= 0, MerchantErrorCodeEnum.INVALID_PARAM);
        InventoryDO inventoryDO = findBySku(sku);
        inventoryDO.setInventory(inventoryDO.getInventory() - inventoryToDeduct);
        AssertionUtil.assertTrue(inventoryDO.getInventory() >= 0, MerchantErrorCodeEnum.INVENTORY_NOT_ENOUGH);
        return InventoryConverter.convert(inventoryRepository.save(inventoryDO));
    }

    @Override
    public void issueOrder(MerchantOrder order) {
        deductInventory(order.getSku(), order.getQuantity());

        saveInventoryHistory(order);

        updateInventoryDailyStatistic(order);
    }

    private synchronized void updateInventoryDailyStatistic(MerchantOrder order) {
        InventoryDailyStatisticDO condition = new InventoryDailyStatisticDO();
        condition.setSku(order.getSku());

        condition.setBizDate(DateTimeUtil.convertToLocalDate(order.getOrderCreatedTime()));
        InventoryDailyStatisticDO dailyStatisticDO = inventoryDailyStatisticRepository.findOne(Example.of(condition))
                .orElseGet(() -> {
                    InventoryDailyStatisticDO statisticDO = new InventoryDailyStatisticDO();
                    statisticDO.setSku(order.getSku());
                    statisticDO.setBizDate(DateTimeUtil.convertToLocalDate(order.getOrderCreatedTime()));
                    statisticDO.setMerchantId(order.getMerchantId());
                    return statisticDO;
                });
        dailyStatisticDO.setQuantity(Optional.ofNullable(dailyStatisticDO.getQuantity()).orElse(0L) + order.getQuantity());
        dailyStatisticDO.setAmount(Optional.ofNullable(dailyStatisticDO.getAmount()).orElse(BigDecimal.ZERO).add(order.getAmountToCollect()));
        inventoryDailyStatisticRepository.save(dailyStatisticDO);
    }

    private void saveInventoryHistory(MerchantOrder order) {
        InventoryHistoryDO inventoryHistoryDO = new InventoryHistoryDO();
        inventoryHistoryDO.setSku(order.getSku());
        inventoryHistoryDO.setMerchantId(order.getMerchantId());
        inventoryHistoryDO.setAmount(order.getAmountToCollect());
        inventoryHistoryDO.setBizId(order.getOrderId());
        inventoryHistoryDO.setPrice(order.getPrice());
        inventoryHistoryDO.setQuantity(order.getQuantity());
        inventoryHistoryDO.setBizTime(order.getOrderCreatedTime());
        inventoryHistoryRepository.save(inventoryHistoryDO);
    }

    private Optional<InventoryDO> optionalFindBySku(Long sku) {
        InventoryDO condition = new InventoryDO();
        condition.setSku(sku);
        return inventoryRepository.findOne(Example.of(condition));
    }

    private InventoryDO findBySku(Long sku) {
        return optionalFindBySku(sku).orElseThrow(() ->
                new EmallCommonException(MerchantErrorCodeEnum.RESOURCE_NOT_FOUND, "inventory with sku " + sku));
    }

}
