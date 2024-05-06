package com.wl.emall.merchant.domain.service.impl;

import com.wl.emall.common.exception.EmallCommonException;
import com.wl.emall.merchant.domain.model.MerchantAccount;
import com.wl.emall.merchant.domain.model.MerchantOrder;
import com.wl.emall.merchant.infra.dal.dataobject.MerchantAccountDO;
import com.wl.emall.merchant.infra.dal.repo.MerchantAccountRepository;
import com.wl.emall.merchant.infra.dal.repo.MerchantBalanceHistoryRepository;
import com.wl.emall.merchant.infra.dal.repo.MerchantDailyIncomeStatisticRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class MerchantAccountDomainServiceImplTest {

    @InjectMocks
    MerchantAccountDomainServiceImpl merchantAccountDomainService;

    @Mock
    private MerchantAccountRepository merchantAccountRepository;

    @Mock
    private MerchantDailyIncomeStatisticRepository merchantDailyIncomeStatisticRepository;

    @Mock
    private MerchantBalanceHistoryRepository merchantBalanceHistoryRepository;


    @Test
    void createMerchantAccount() {
        when(merchantAccountRepository.save(any())).thenAnswer(x -> {
            MerchantAccountDO merchantAccountDO = x.getArgument(0);
            merchantAccountDO.setId(1L);
            return merchantAccountDO;
        });
        MerchantAccount merchantAccount = merchantAccountDomainService.createMerchantAccount(1L);
        Assertions.assertEquals(1L, merchantAccount.getMerchantId());
        Assertions.assertEquals(1L, merchantAccount.getMerchantAccountId());
        verify(merchantAccountRepository).save(any());
    }

    @Test
    void queryBalance() {
        Assertions.assertThrows(EmallCommonException.class, () -> merchantAccountDomainService.queryBalance(1L));

        reset(merchantAccountRepository);

        when(merchantAccountRepository.findOne(any(Example.class))).thenAnswer(x -> {
            Example<MerchantAccountDO> param = x.getArgument(0);
            MerchantAccountDO merchantAccountDO = param.getProbe();
            merchantAccountDO.setId(1L);
            merchantAccountDO.setBalance(BigDecimal.ONE);
            return Optional.of(merchantAccountDO);
        });
        MerchantAccount merchantAccount = merchantAccountDomainService.queryBalance(1L);
        Assertions.assertEquals(0, BigDecimal.ONE.compareTo(merchantAccount.getBalance()));
        verify(merchantAccountRepository).findOne(any(Example.class));
    }

    @Test
    void merchantCollect() {
        when(merchantAccountRepository.findOne(any(Example.class))).thenAnswer(x -> {
            Example<MerchantAccountDO> param = x.getArgument(0);
            MerchantAccountDO merchantAccountDO = param.getProbe();
            merchantAccountDO.setId(1L);
            merchantAccountDO.setBalance(BigDecimal.ONE);
            return Optional.of(merchantAccountDO);
        });
        merchantAccountDomainService.merchantCollect(1L, BigDecimal.ONE);

        ArgumentCaptor<MerchantAccountDO> argumentCaptor = ArgumentCaptor.forClass(MerchantAccountDO.class);
        verify(merchantAccountRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(0, new BigDecimal("2").compareTo(argumentCaptor.getValue().getBalance()));
    }

    @Test
    void issueOrder() {
        when(merchantAccountRepository.findOne(any(Example.class))).thenAnswer(x -> {
            Example<MerchantAccountDO> param = x.getArgument(0);
            MerchantAccountDO merchantAccountDO = param.getProbe();
            merchantAccountDO.setId(1L);
            merchantAccountDO.setBalance(new BigDecimal("100"));
            return Optional.of(merchantAccountDO);
        });

        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setOrderId(1L);
        merchantOrder.setQuantity(2L);
        merchantOrder.setPrice(new BigDecimal("50"));
        merchantOrder.setSku(1L);
        merchantOrder.setMerchantId(1L);
        merchantOrder.setAmountToCollect(new BigDecimal("100"));
        merchantOrder.setOrderCreatedTime(new Date());
        merchantAccountDomainService.issueOrder(merchantOrder);

        verify(merchantAccountRepository).save(any());
        verify(merchantDailyIncomeStatisticRepository).save(any());
        verify(merchantBalanceHistoryRepository).save(any());
    }

}