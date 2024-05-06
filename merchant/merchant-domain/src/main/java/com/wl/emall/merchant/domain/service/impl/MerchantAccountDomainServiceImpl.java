package com.wl.emall.merchant.domain.service.impl;

import com.wl.emall.common.exception.EmallCommonException;
import com.wl.emall.common.exception.util.AssertionUtil;
import com.wl.emall.common.util.DateTimeUtil;
import com.wl.emall.merchant.domain.converter.MerchantAccountConverter;
import com.wl.emall.merchant.domain.model.MerchantAccount;
import com.wl.emall.merchant.domain.model.MerchantOrder;
import com.wl.emall.merchant.domain.service.MerchantAccountDomainService;
import com.wl.emall.merchant.infra.dal.dataobject.MerchantAccountDO;
import com.wl.emall.merchant.infra.dal.dataobject.MerchantBalanceHistoryDO;
import com.wl.emall.merchant.infra.dal.dataobject.MerchantDailyIncomeStatisticDO;
import com.wl.emall.merchant.infra.dal.repo.MerchantAccountRepository;
import com.wl.emall.merchant.infra.dal.repo.MerchantBalanceHistoryRepository;
import com.wl.emall.merchant.infra.dal.repo.MerchantDailyIncomeStatisticRepository;
import com.wl.emall.merchant.infra.exception.MerchantErrorCodeEnum;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * @author wanglu
 */
@Service
public class MerchantAccountDomainServiceImpl implements MerchantAccountDomainService {

    @Resource
    private MerchantAccountRepository merchantAccountRepository;

    @Resource
    private MerchantDailyIncomeStatisticRepository merchantDailyIncomeStatisticRepository;

    @Resource
    private MerchantBalanceHistoryRepository merchantBalanceHistoryRepository;

    @Override
    public MerchantAccount createMerchantAccount(Long merchantId) {
        AssertionUtil.assertNotNull(merchantId, MerchantErrorCodeEnum.BAD_REQUEST);
        AssertionUtil.assertTrue(optionalFindByMerchantId(merchantId).isEmpty(), MerchantErrorCodeEnum.ACCOUNT_ALREADY_EXISTS);
        MerchantAccountDO merchantAccountDO = new MerchantAccountDO();
        merchantAccountDO.setMerchantId(merchantId);
        merchantAccountDO.setBalance(BigDecimal.ZERO);
        return MerchantAccountConverter.convert(merchantAccountRepository.save(merchantAccountDO));
    }

    @Override
    public MerchantAccount queryBalance(Long merchantId) {
        AssertionUtil.assertNotNull(merchantId, MerchantErrorCodeEnum.BAD_REQUEST);
        MerchantAccountDO merchantAccountDO = findByMerchantId(merchantId);
        return MerchantAccountConverter.convert(merchantAccountDO);
    }

    @Override
    public void merchantCollect(Long merchantId, BigDecimal amountToCollect) {
        AssertionUtil.assertTrue(merchantId != null && amountToCollect != null && amountToCollect.compareTo(BigDecimal.ZERO) >= 0,
                MerchantErrorCodeEnum.INVALID_PARAM);
        MerchantAccountDO merchantAccountDO = findByMerchantId(merchantId);
        merchantAccountDO.setBalance(Optional.ofNullable(merchantAccountDO.getBalance()).orElse(BigDecimal.ZERO).add(amountToCollect));
        merchantAccountRepository.save(merchantAccountDO);
    }

    @Override
    public void issueOrder(MerchantOrder order) {
        merchantCollect(order.getMerchantId(), order.getAmountToCollect());

        saveMerchantBalanceHistory(order);

        updateMerchantDailyIncomeStatistic(order);

    }

    private synchronized void updateMerchantDailyIncomeStatistic(MerchantOrder order) {
        MerchantDailyIncomeStatisticDO condition = new MerchantDailyIncomeStatisticDO();
        condition.setMerchantId(order.getSku());
        condition.setBizDate(DateTimeUtil.convertToLocalDate(order.getOrderCreatedTime()));
        MerchantDailyIncomeStatisticDO dailyStatisticDO = merchantDailyIncomeStatisticRepository.findOne(Example.of(condition))
                .orElseGet(() -> {
                    MerchantDailyIncomeStatisticDO statisticDO = new MerchantDailyIncomeStatisticDO();
                    statisticDO.setMerchantId(order.getMerchantId());
                    statisticDO.setBizDate(DateTimeUtil.convertToLocalDate(order.getOrderCreatedTime()));
                    return statisticDO;
                });
        dailyStatisticDO.setAmount(Optional.ofNullable(dailyStatisticDO.getAmount()).orElse(BigDecimal.ZERO).add(order.getAmountToCollect()));
        merchantDailyIncomeStatisticRepository.save(dailyStatisticDO);
    }

    private void saveMerchantBalanceHistory(MerchantOrder order) {
        MerchantBalanceHistoryDO merchantBalanceHistoryDO = new MerchantBalanceHistoryDO();
        merchantBalanceHistoryDO.setMerchantId(order.getMerchantId());
        merchantBalanceHistoryDO.setAmount(order.getAmountToCollect());
        merchantBalanceHistoryDO.setBizId(order.getOrderId());
        merchantBalanceHistoryDO.setBizTime(order.getOrderCreatedTime());
        merchantBalanceHistoryRepository.save(merchantBalanceHistoryDO);
    }


    private Optional<MerchantAccountDO> optionalFindByMerchantId(Long merchantId) {
        MerchantAccountDO condition = new MerchantAccountDO();
        condition.setMerchantId(merchantId);
        return merchantAccountRepository.findOne(Example.of(condition));
    }

    private MerchantAccountDO findByMerchantId(Long merchantId) {
        return optionalFindByMerchantId(merchantId).orElseThrow(() ->
                new EmallCommonException(MerchantErrorCodeEnum.RESOURCE_NOT_FOUND, "account with merchantId " + merchantId));
    }

}
