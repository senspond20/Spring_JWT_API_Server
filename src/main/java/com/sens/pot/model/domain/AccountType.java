package com.sens.pot.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 사용자 계정 유형 Table / 자사회원/네이버/카카오/구글/페이스북...
 */
@Getter
@EqualsAndHashCode(of = "code")
@Table(name = "account_type")
@Entity
public class AccountType {
    @Id
    @Column(name = "account_code",length = 5)
    private String code;

    @Column(name="type_name", length = 10)
    private String typeName;

    public AccountType(){ }

    public AccountType(String code, String typeName){
        this.code = code;
        this.typeName = typeName;
    }

}
