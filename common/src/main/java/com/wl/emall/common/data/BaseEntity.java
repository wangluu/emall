package com.wl.emall.common.data;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.Version;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;
import static lombok.AccessLevel.NONE;

/**
 * @author wanglu
 */
@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /**
     * 实体标识
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * 乐观锁
     */
    @Getter(NONE)
    @Version
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer version;

    /**
     * 创建时间戳
     */
    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdTime;

    /**
     * 最后更新时间戳
     */
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(nullable = false)
    private Date updatedTime;

}
