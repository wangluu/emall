package com.wl.emall.merchant.domain.converter;

import com.wl.emall.merchant.domain.model.Inventory;
import com.wl.emall.merchant.infra.dal.dataobject.InventoryDO;

/**
 * @author wanglu
 */
public interface InventoryConverter {

    static Inventory convert(InventoryDO inventoryDO) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryDO.getId());
        inventory.setSku(inventoryDO.getSku());
        inventory.setMerchantId(inventoryDO.getMerchantId());
        inventory.setPrice(inventoryDO.getPrice());
        inventory.setInventory(inventoryDO.getInventory());
        return inventory;
    }

    static InventoryDO convert(Inventory inventory) {
        InventoryDO inventoryDO = new InventoryDO();
        inventoryDO.setId(inventory.getInventoryId());
        inventoryDO.setSku(inventory.getSku());
        inventoryDO.setMerchantId(inventory.getMerchantId());
        inventoryDO.setPrice(inventory.getPrice());
        inventoryDO.setInventory(inventory.getInventory());
        return inventoryDO;
    }

}
