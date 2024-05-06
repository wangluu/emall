package com.wl.emall.cutomer.domain.service;

import com.wl.emall.cutomer.domain.model.CustomerAccount;

import java.math.BigDecimal;

/**
 * @author wanglu
 */
public interface CustomerAccountDomainService {

    CustomerAccount createCustomerAccount(Long customerId);

    CustomerAccount recharge(Long customerId, BigDecimal amount);

    CustomerAccount queryBalance(Long customerId);

    CustomerAccount deductBalance(Long customerId, BigDecimal amountToDeduct);

}
