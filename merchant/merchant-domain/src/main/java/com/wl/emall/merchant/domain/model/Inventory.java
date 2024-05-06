package com.wl.emall.merchant.domain.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wanglu
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Inventory {

    private Long inventoryId;

    private Long sku;

    private Long merchantId;

    private BigDecimal price;

    private Long inventory;

}
