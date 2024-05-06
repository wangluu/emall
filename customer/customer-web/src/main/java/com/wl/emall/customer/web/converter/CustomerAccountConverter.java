package com.wl.emall.customer.web.converter;

import com.wl.emall.customer.web.dto.resp.CustomerAccountResp;
import com.wl.emall.cutomer.domain.model.CustomerAccount;

/**
 * @author wanglu
 */
public interface CustomerAccountConverter {

    static CustomerAccountResp convertResp(CustomerAccount account) {
        CustomerAccountResp resp = new CustomerAccountResp();
        resp.setCustomerId(account.getCustomerId());
        resp.setCustomerAccountId(account.getCustomerAccountId());
        resp.setBalance(account.getBalance());
        return resp;
    }

}
