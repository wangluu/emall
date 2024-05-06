package com.wl.emall.cutomer.domain.model;

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
public class Order {

    private Long orderId;

    private Long customerId;

    private Long merchantId;

    private Long sku;

    private BigDecimal price;

    private Long quantity;

    private BigDecimal paidAmount;

    private Date orderCreatedTime;

}
