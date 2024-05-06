package com.wl.emall.cutomer.infra.dal.dataobject;

import com.wl.emall.common.data.BaseEntity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author wanglu
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "customer_account")
public class CustomerAccountDO extends BaseEntity {

    private Long customerId;

    private BigDecimal balance;

}
