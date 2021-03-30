package com.sens.pot.model.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class CuTimeEntity {

    @Column(name ="create_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name="update_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updateAt;

}
