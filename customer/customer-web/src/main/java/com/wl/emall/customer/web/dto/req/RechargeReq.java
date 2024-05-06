package com.wl.emall.customer.web.dto.req;

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
public class RechargeReq {

    private Long customerId;

    private BigDecimal amount;

}
