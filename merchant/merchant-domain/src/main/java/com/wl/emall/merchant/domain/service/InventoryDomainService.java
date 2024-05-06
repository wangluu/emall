package com.wl.emall.merchant.domain.service;

import com.wl.emall.merchant.domain.model.Inventory;
import com.wl.emall.merchant.domain.model.MerchantOrder;

/**
 * @author wanglu
 */
public interface InventoryDomainService {

    Inventory createInventory(Inventory inventory);

    Inventory queryInventory(Long sku);

    Inventory updateInventory(Long sku, Long inventoryToDeduct);

    Inventory increaseInventory(Long sku, Long inventoryToIncrease);

    Inventory deductInventory(Long sku, Long inventoryToDeduct);

    void issueOrder(MerchantOrder order);

}
