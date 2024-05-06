package com.wl.emall.merchant.infra.dal.dataobject;

import com.wl.emall.common.data.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

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
@Table(name = "inventory_daily_statistic")
public class InventoryDailyStatisticDO extends BaseEntity {

    private Long sku;

    private Long merchantId;

    private Long quantity;

    private BigDecimal amount;

    private LocalDate bizDate;

}
