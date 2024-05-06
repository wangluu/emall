
package com.wl.emall.merchant.infra.dal.dataobject;

import com.wl.emall.common.data.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "merchant_daily_income_statistic")
public class MerchantDailyIncomeStatisticDO extends BaseEntity {

    private Long merchantId;

    private BigDecimal amount;

    private LocalDate bizDate;

}
