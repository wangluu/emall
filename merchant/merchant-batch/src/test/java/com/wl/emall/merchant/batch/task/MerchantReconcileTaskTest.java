package com.wl.emall.merchant.batch.task;

import com.wl.emall.merchant.infra.dal.dataobject.InventoryDailyStatisticDO;
import com.wl.emall.merchant.infra.dal.dataobject.MerchantAccountDO;
import com.wl.emall.merchant.infra.dal.dataobject.MerchantDailyIncomeStatisticDO;
import com.wl.emall.merchant.infra.dal.repo.InventoryDailyStatisticRepository;
import com.wl.emall.merchant.infra.dal.repo.MerchantAccountRepository;
import com.wl.emall.merchant.infra.dal.repo.MerchantDailyIncomeStatisticRepository;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author wanglu
 */
@ExtendWith(MockitoExtension.class)
public class MerchantReconcileTaskTest {

    @InjectMocks
    private MerchantReconcileTask merchantReconcileTask;

    @Mock
    private MerchantDailyIncomeStatisticRepository merchantDailyIncomeStatisticRepository;

    @Mock
    private InventoryDailyStatisticRepository inventoryDailyStatisticRepository;

    @Mock
    private MerchantAccountRepository merchantAccountRepository;

    @Test
    public void run() {
        when(merchantAccountRepository.findAll()).thenReturn(Lists.newArrayList(mockAccount(1L), mockAccount(2L)));
        when(merchantDailyIncomeStatisticRepository.findOne(any(Example.class)))
                .thenAnswer(invoke -> {
                    Example<MerchantDailyIncomeStatisticDO> param = invoke.getArgument(0);
                    MerchantDailyIncomeStatisticDO statisticDO = param.getProbe();
                    statisticDO.setAmount(new BigDecimal("100"));
                    return Optional.of(statisticDO);
                });
        when(inventoryDailyStatisticRepository.findAll(any(Example.class))).thenAnswer(invoke -> {
            Example<InventoryDailyStatisticDO> param = invoke.getArgument(0);
            InventoryDailyStatisticDO statisticDO = param.getProbe();
            return Lists.newArrayList(mockInventoryDailyStatisticDO(statisticDO.getMerchantId(),
                    new BigDecimal("100").multiply(BigDecimal.valueOf(statisticDO.getMerchantId()))));
        });

        merchantReconcileTask.run();
        verify(merchantAccountRepository).findAll();
        verify(merchantDailyIncomeStatisticRepository, times(2)).findOne(any(Example.class));
        verify(inventoryDailyStatisticRepository, times(2)).findAll(any(Example.class));
    }

    private MerchantAccountDO mockAccount(Long merchantId) {
        MerchantAccountDO accountDO = new MerchantAccountDO();
        accountDO.setMerchantId(merchantId);
        return accountDO;
    }

    private InventoryDailyStatisticDO mockInventoryDailyStatisticDO(Long merchantId, BigDecimal amount) {
        InventoryDailyStatisticDO statisticDO = new InventoryDailyStatisticDO();
        statisticDO.setMerchantId(merchantId);
        statisticDO.setAmount(amount);
        return statisticDO;
    }

}