package com.wl.emall.merchant.domain.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wanglu
 */
@Getter
@Setter
@ToString
public class MerchantOrder {

    private Long orderId;

    private Long merchantId;

    private BigDecimal amountToCollect;

    private Long sku;

    private BigDecimal price;

    private Long quantity;

    private Date orderCreatedTime;

}
