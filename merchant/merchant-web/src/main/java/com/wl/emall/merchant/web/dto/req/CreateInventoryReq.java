package com.wl.emall.merchant.web.dto.req;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wanglu
 */
@Getter
@Setter
@ToString
public class CreateInventoryReq {

    private Long merchantId;

    private Long sku;

    private BigDecimal price;

    private Long inventory;
}
