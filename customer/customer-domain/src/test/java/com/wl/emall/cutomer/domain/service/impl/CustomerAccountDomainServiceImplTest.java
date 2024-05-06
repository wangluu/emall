package com.wl.emall.cutomer.domain.service.impl;

import com.wl.emall.common.exception.EmallCommonException;
import com.wl.emall.cutomer.domain.model.CustomerAccount;
import com.wl.emall.cutomer.infra.dal.dataobject.CustomerAccountDO;
import com.wl.emall.cutomer.infra.dal.repo.CustomerAccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author wanglu
 */
@ExtendWith(MockitoExtension.class)
class CustomerAccountDomainServiceImplTest {

    @InjectMocks
    CustomerAccountDomainServiceImpl customerAccountDomainService;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @Test
    void createCustomerAccount() {
        when(customerAccountRepository.save(any())).thenAnswer(x -> {
            CustomerAccountDO accountDO = x.getArgument(0);
            accountDO.setId(1L);
            return accountDO;
        });
        CustomerAccount customerAccount = customerAccountDomainService.createCustomerAccount(1L);
        verify(customerAccountRepository).save(any());
        Assertions.assertEquals(1L, customerAccount.getCustomerAccountId());


        reset(customerAccountRepository);
        when(customerAccountRepository.findOne(any(Example.class))).thenReturn(Optional.of(new CustomerAccountDO()));
        Assertions.assertThrows(EmallCommonException.class, () -> customerAccountDomainService.createCustomerAccount(1L));
    }

    @Test
    void recharge() {
        CustomerAccountDO customerAccountDO = new CustomerAccountDO();
        customerAccountDO.setCustomerId(1L);
        customerAccountDO.setBalance(BigDecimal.ONE);
        when(customerAccountRepository.findOne(any(Example.class))).thenReturn(Optional.of(customerAccountDO));
        CustomerAccount recharge = customerAccountDomainService.recharge(1L, BigDecimal.ONE);

        verify(customerAccountRepository).save(any());
        Assertions.assertEquals(0, new BigDecimal("2").compareTo(recharge.getBalance()));
    }

    @Test
    void queryBalance() {
        CustomerAccountDO customerAccountDO = new CustomerAccountDO();
        customerAccountDO.setCustomerId(1L);
        customerAccountDO.setBalance(BigDecimal.ONE);
        when(customerAccountRepository.findOne(any(Example.class))).thenReturn(Optional.of(customerAccountDO));
        CustomerAccount customerAccount = customerAccountDomainService.queryBalance(1L);

        verify(customerAccountRepository).findOne(any(Example.class));
        Assertions.assertEquals(0, BigDecimal.ONE.compareTo(customerAccount.getBalance()));
    }

    @Test
    void deductBalance() {
        CustomerAccountDO customerAccountDO = new CustomerAccountDO();
        customerAccountDO.setCustomerId(1L);
        customerAccountDO.setBalance(BigDecimal.ONE);
        when(customerAccountRepository.findOne(any(Example.class))).thenReturn(Optional.of(customerAccountDO));
        CustomerAccount customerAccount = customerAccountDomainService.deductBalance(1L, BigDecimal.ONE);
        verify(customerAccountRepository).findOne(any(Example.class));
        Assertions.assertEquals(0, BigDecimal.ZERO.compareTo(customerAccount.getBalance()));

        reset(customerAccountRepository);
        when(customerAccountRepository.findOne(any(Example.class))).thenReturn(Optional.of(customerAccountDO));
        Assertions.assertThrows(EmallCommonException.class, () -> customerAccountDomainService.deductBalance(1L, BigDecimal.TEN));
    }

}