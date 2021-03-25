package com.sens.pot.web.domain.account;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "account_status")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountStatus {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   private int warnCount; // 경고 누적 횟수

   @Column
   @UpdateTimestamp
   private Date stampTime; // 경고를 받은 날짜를 기록한다.

//    @OneToOne
//    @JoinColumn(name = "account_id")
//    private Account account;

}
