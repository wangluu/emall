package com.wl.emall.cutomer.domain.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wanglu
 */
@Getter
@Setter
public class CustomerAccount {

    private Long customerAccountId;

    private Long customerId;

    private BigDecimal balance;

}
