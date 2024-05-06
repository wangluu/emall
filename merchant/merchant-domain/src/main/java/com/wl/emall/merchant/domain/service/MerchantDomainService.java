package com.wl.emall.merchant.domain.service;

import com.wl.emall.merchant.domain.model.MerchantOrder;

/**
 * @author wanglu
 */
public interface MerchantDomainService {

    void issueOrder(MerchantOrder order);

}
