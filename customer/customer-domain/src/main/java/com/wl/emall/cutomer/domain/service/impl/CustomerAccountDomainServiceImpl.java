package com.wl.emall.cutomer.domain.service.impl;

import com.wl.emall.common.exception.EmallCommonException;
import com.wl.emall.common.exception.util.AssertionUtil;
import com.wl.emall.cutomer.domain.converter.CustomerAccountConverter;
import com.wl.emall.cutomer.domain.model.CustomerAccount;
import com.wl.emall.cutomer.domain.service.CustomerAccountDomainService;
import com.wl.emall.cutomer.infra.dal.dataobject.CustomerAccountDO;
import com.wl.emall.cutomer.infra.dal.repo.CustomerAccountRepository;
import com.wl.emall.cutomer.infra.exception.CustomerErrorCodeEnum;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

/**
 * @author wanglu
 */
@Service
@Slf4j
public class CustomerAccountDomainServiceImpl implements CustomerAccountDomainService {

    @Resource
    private CustomerAccountRepository customerAccountRepository;


    @Override
    public synchronized CustomerAccount createCustomerAccount(Long customerId) {
        AssertionUtil.assertNotNull(customerId, CustomerErrorCodeEnum.BAD_REQUEST);
        CustomerAccountDO condition = new CustomerAccountDO();
        condition.setCustomerId(customerId);
        AssertionUtil.assertTrue(customerAccountRepository.findOne(Example.of(condition)).isEmpty(), CustomerErrorCodeEnum.ACCOUNT_ALREADY_EXISTS);
        CustomerAccountDO merchantAccountDO = new CustomerAccountDO();
        merchantAccountDO.setCustomerId(customerId);
        merchantAccountDO.setBalance(BigDecimal.ZERO);
        return CustomerAccountConverter.convert(customerAccountRepository.save(merchantAccountDO));
    }

    @Override
    public CustomerAccount recharge(Long customerId, BigDecimal amount) {
        AssertionUtil.assertTrue(customerId != null && amount != null && amount.compareTo(BigDecimal.ZERO) > 0,
                CustomerErrorCodeEnum.BAD_REQUEST);
        CustomerAccountDO account = findByCustomerId(customerId);
        account.setBalance(Optional.ofNullable(account.getBalance()).orElse(BigDecimal.ZERO).add(amount));
        customerAccountRepository.save(account);
        return CustomerAccountConverter.convert(account);
    }

    @Override
    public CustomerAccount queryBalance(Long customerId) {
        AssertionUtil.assertNotNull(customerId, CustomerErrorCodeEnum.BAD_REQUEST);
        CustomerAccountDO account = findByCustomerId(customerId);
        return CustomerAccountConverter.convert(account);
    }

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public CustomerAccount deductBalance(Long customerId, BigDecimal amountToDeduct) {
        AssertionUtil.assertTrue(customerId != null && amountToDeduct != null && amountToDeduct.compareTo(BigDecimal.ZERO) >= 0,
                CustomerErrorCodeEnum.INVALID_PARAM);
        CustomerAccountDO account = findByCustomerId(customerId);
        account.setBalance(account.getBalance().subtract(amountToDeduct));
        AssertionUtil.assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) >= 0, CustomerErrorCodeEnum.BALANCE_NOT_ENOUGH);
        return CustomerAccountConverter.convert(account);
    }

    private Optional<CustomerAccountDO> optionalFindByCustomerId(Long customerId) {
        CustomerAccountDO condition = new CustomerAccountDO();
        condition.setCustomerId(customerId);
        return customerAccountRepository.findOne(Example.of(condition));
    }

    private CustomerAccountDO findByCustomerId(Long customerId) {
        return optionalFindByCustomerId(customerId).orElseThrow(() -> new EmallCommonException(CustomerErrorCodeEnum.ACCOUNT_NOT_EXISTS));
    }

}
