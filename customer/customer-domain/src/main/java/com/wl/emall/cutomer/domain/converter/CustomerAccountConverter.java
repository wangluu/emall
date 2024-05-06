package com.wl.emall.cutomer.domain.converter;

import com.wl.emall.cutomer.domain.model.CustomerAccount;
import com.wl.emall.cutomer.infra.dal.dataobject.CustomerAccountDO;

/**
 * @author wanglu
 */
public interface CustomerAccountConverter {

    static CustomerAccount convert(CustomerAccountDO accountDO) {
        CustomerAccount account = new CustomerAccount();
        account.setCustomerAccountId(accountDO.getId());
        account.setCustomerId(accountDO.getCustomerId());
        account.setBalance(accountDO.getBalance());
        return account;
    }

}
