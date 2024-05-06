package com.wl.emall.merchant.web.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wanglu
 */
@Getter
@Setter
@ToString
public class UpdateInventoryReq {

    private Long sku;

    private Long inventory;

}
