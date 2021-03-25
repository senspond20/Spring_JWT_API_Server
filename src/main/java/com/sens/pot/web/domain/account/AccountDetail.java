package com.sens.pot.web.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @Getter
// @EqualsAndHashCode(of = "id")
// @Table(name = "account_detail")
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity
// public class AccountDetail {
    
//     @Id
//     @GeneratedValue
//     @Column(name = "account_detail_id",length = 11)
//     private Long id;
// @Column
// private String username;

// @Column
// private char   gendar;

// @Column
// private String aboutMe;
//     @Column
//     private boolean status;

//     @OneToOne
//     @JoinColumn(name="account_id")
//     private Account account;
// }
