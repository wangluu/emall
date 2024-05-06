package com.wl.emall.customer.web.dto.resp;

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
public class CustomerAccountResp {

    private Long customerAccountId;

    private Long customerId;

    private BigDecimal balance;

}
