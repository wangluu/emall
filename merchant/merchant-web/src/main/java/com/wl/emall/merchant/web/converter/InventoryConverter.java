package com.wl.emall.merchant.web.converter;

import com.wl.emall.merchant.domain.model.Inventory;
import com.wl.emall.merchant.web.dto.req.CreateInventoryReq;
import com.wl.emall.merchant.web.dto.resp.InventoryResp;

/**
 * @author wanglu
 */
public interface InventoryConverter {

    static Inventory convert(CreateInventoryReq req) {
        Inventory inventory = new Inventory();
        inventory.setMerchantId(req.getMerchantId());
        inventory.setSku(req.getSku());
        inventory.setPrice(req.getPrice());
        inventory.setInventory(req.getInventory());
        return inventory;
    }

    static InventoryResp convertResp(Inventory inventory) {
        InventoryResp resp = new InventoryResp();
        resp.setMerchantId(inventory.getMerchantId());
        resp.setSku(inventory.getSku());
        resp.setPrice(inventory.getPrice());
        resp.setInventory(inventory.getInventory());
        return resp;
    }

}
