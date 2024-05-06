package com.wl.emall.merchant.domain.service;

import com.wl.emall.merchant.domain.model.MerchantAccount;
import com.wl.emall.merchant.domain.model.MerchantOrder;

import java.math.BigDecimal;

/**
 * @author wanglu
 */
public interface MerchantAccountDomainService {

    MerchantAccount createMerchantAccount(Long merchantId);

    MerchantAccount queryBalance(Long merchantId);

    void merchantCollect(Long merchantId, BigDecimal amountToCollect);

    void issueOrder(MerchantOrder order);

}
