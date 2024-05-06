package com.wl.emall.merchant.batch.task;

import com.wl.emall.common.batch.CornJob;
import com.wl.emall.merchant.infra.dal.dataobject.InventoryDailyStatisticDO;
import com.wl.emall.merchant.infra.dal.dataobject.MerchantDailyIncomeStatisticDO;
import com.wl.emall.merchant.infra.dal.repo.InventoryDailyStatisticRepository;
import com.wl.emall.merchant.infra.dal.repo.MerchantAccountRepository;
import com.wl.emall.merchant.infra.dal.repo.MerchantDailyIncomeStatisticRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @author wanglu
 */
@Component
@Slf4j
public class MerchantReconcileTask implements CornJob {

    @Value("${app.merchant.job.reconcile.corn:0 0 1 * * *}")
    private String corn;

    @Resource
    private MerchantDailyIncomeStatisticRepository merchantDailyIncomeStatisticRepository;

    @Resource
    private InventoryDailyStatisticRepository inventoryDailyStatisticRepository;

    @Resource
    private MerchantAccountRepository merchantAccountRepository;

    @Override
    public String corn() {
        return corn;
    }

    @Override
    public void run() {
        log.info("Start MerchantReconcileTask...");
        reconcile(LocalDate.now().plusDays(-1L));
    }

    private void reconcile(LocalDate bizDate) {
        //todo pageable
        merchantAccountRepository.findAll().forEach(x -> {
            BigDecimal merchantDailyIncome = getMerchantDailyIncome(x.getMerchantId(), bizDate);

            BigDecimal merchantInventoryDailySold = getMerchantInventoryDailySold(x.getMerchantId(), bizDate);

            if (merchantDailyIncome.compareTo(merchantInventoryDailySold) == 0) {
                log.info("Merchant {} date {} income {}", x.getMerchantId(), bizDate, merchantDailyIncome);
            } else {
                log.warn("Merchant {} date {} , daily income {}, daily sold: {}, please check!",
                        x.getMerchantId(), bizDate, merchantDailyIncome, merchantInventoryDailySold);
            }
        });
    }

    private BigDecimal getMerchantInventoryDailySold(Long merchantId, LocalDate bizDate) {
        //todo pageable
        InventoryDailyStatisticDO condition = new InventoryDailyStatisticDO();
        condition.setMerchantId(merchantId);
        condition.setBizDate(bizDate);
        return inventoryDailyStatisticRepository.findAll(Example.of(condition))
                .stream().map(InventoryDailyStatisticDO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getMerchantDailyIncome(Long merchantId, LocalDate bizDate) {
        MerchantDailyIncomeStatisticDO condition = new MerchantDailyIncomeStatisticDO();
        condition.setMerchantId(merchantId);
        condition.setBizDate(bizDate);
        return merchantDailyIncomeStatisticRepository.findOne(Example.of(condition)).map(MerchantDailyIncomeStatisticDO::getAmount).orElse(BigDecimal.ZERO);
    }

}
