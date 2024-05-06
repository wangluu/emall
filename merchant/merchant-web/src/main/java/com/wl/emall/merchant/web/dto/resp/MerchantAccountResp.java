package com.wl.emall.merchant.web.dto.resp;

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
public class MerchantAccountResp {

    private Long merchantId;

    private BigDecimal balance;

}
