package com.wl.emall.merchant.domain.model;

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
public class MerchantAccount {

    private Long merchantAccountId;

    private Long merchantId;

    private BigDecimal balance;

}
