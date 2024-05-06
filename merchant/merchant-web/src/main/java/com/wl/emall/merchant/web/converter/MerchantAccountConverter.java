package com.wl.emall.merchant.web.converter;

import com.wl.emall.merchant.domain.model.MerchantAccount;
import com.wl.emall.merchant.web.dto.resp.MerchantAccountResp;

/**
 * @author wanglu
 */
public class MerchantAccountConverter {

    public static MerchantAccountResp convertResp(MerchantAccount merchantAccount) {
        MerchantAccountResp resp = new MerchantAccountResp();
        resp.setMerchantId(merchantAccount.getMerchantId());
        resp.setBalance(merchantAccount.getBalance());
        return resp;
    }

}
