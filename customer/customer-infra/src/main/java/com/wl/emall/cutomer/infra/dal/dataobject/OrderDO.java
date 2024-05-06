package com.wl.emall.cutomer.infra.dal.dataobject;

import com.wl.emall.common.data.BaseEntity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author wanglu
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "emall_order")
public class OrderDO extends BaseEntity {

    private Long customerId;

    private Long merchantId;

    private Long sku;

    private BigDecimal price;

    private Long quantity;

    private BigDecimal paidAmount;

}
