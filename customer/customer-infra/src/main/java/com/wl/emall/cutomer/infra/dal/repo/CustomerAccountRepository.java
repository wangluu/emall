package com.wl.emall.cutomer.infra.dal.repo;

import com.wl.emall.cutomer.infra.dal.dataobject.CustomerAccountDO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author wanglu
 */
@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccountDO, Long>, JpaSpecificationExecutor<CustomerAccountDO> {

}
