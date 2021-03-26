package com.sens.pot.web.domain.account;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sens.pot.common.anotation.ColumnComment;
// import com.sens.pot.common.anotation.TableComment;

import javax.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 계정 Table
 */
@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "account")
@NoArgsConstructor
@Entity
@DynamicInsert
public class Account {
    
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    // @Column(name = "username")
    // private String username;

    @Column(length = 255, nullable = false)
    @ColumnComment("'패스워드'")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "account_roles",
            joinColumns = {
            @JoinColumn(name = "account_id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;


    // @Column(name = "is_active", columnDefinition = "TINYINT(1)")
    // private Boolean iaActive;

    // @Column(name ="create_at")
    // @CreationTimestamp 
    // private Date createAt;
    
    @Builder
    public Account(String email, String password, Set<Role> roles) {
        this.email = email;
        // this.iaActive = true;
        this.password = password;
        this.roles = roles;
    }

    public void updateRoles(Set<Role> roleSet) {
        this.roles = roleSet;
    }


        // @OneToOne
    // @JoinColumn(name = "account_detail_id")
    // private AccountDetail accountDetail;

    // @Column(name="status", columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1 COMMENT '계정 활성(1)/비활성(0)'")
    // private Boolean isActivation;

    // columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입날짜(UTC)'")
}
