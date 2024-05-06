package com.wl.emall.merchant.domain.converter;

import com.wl.emall.merchant.domain.model.MerchantAccount;
import com.wl.emall.merchant.infra.dal.dataobject.MerchantAccountDO;

/**
 * @author wanglu
 */
public interface MerchantAccountConverter {

    static MerchantAccount convert(MerchantAccountDO accountDO) {
        MerchantAccount account = new MerchantAccount();
        account.setMerchantAccountId(accountDO.getId());
        account.setMerchantId(accountDO.getMerchantId());
        account.setBalance(accountDO.getBalance());
        return account;
    }

}
