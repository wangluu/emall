package com.wl.emall.merchant.infra.dal.dataobject;

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
@Table(name = "inventory")
public class InventoryDO extends BaseEntity {

    private Long sku;

    private Long merchantId;

    private BigDecimal price;

    private Long inventory;

}
