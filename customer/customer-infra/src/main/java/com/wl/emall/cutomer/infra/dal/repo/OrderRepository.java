package com.wl.emall.cutomer.infra.dal.repo;

import com.wl.emall.cutomer.infra.dal.dataobject.OrderDO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wanglu
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderDO, Long> {

}
