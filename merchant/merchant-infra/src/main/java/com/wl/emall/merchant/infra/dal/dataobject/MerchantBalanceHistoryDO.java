
package com.wl.emall.merchant.infra.dal.dataobject;

import com.wl.emall.common.data.BaseEntity;

import java.math.BigDecimal;
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
@Table(name = "merchant_balance_history")
public class MerchantBalanceHistoryDO extends BaseEntity {

    private Long merchantId;

    private BigDecimal amount;

    private Date bizTime;

    private Long bizId;

}
