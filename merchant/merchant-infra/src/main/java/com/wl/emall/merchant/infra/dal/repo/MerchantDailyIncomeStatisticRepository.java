package com.wl.emall.merchant.infra.dal.repo;

import com.wl.emall.merchant.infra.dal.dataobject.MerchantDailyIncomeStatisticDO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wanglu
 */
@Repository
public interface MerchantDailyIncomeStatisticRepository extends JpaRepository<MerchantDailyIncomeStatisticDO, Long> {

}
