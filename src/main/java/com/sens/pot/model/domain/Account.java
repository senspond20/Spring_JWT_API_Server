package com.sens.pot.model.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.sens.pot.common.helper.SocialType;

import javax.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import io.jsonwebtoken.lang.Assert;
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
@DynamicUpdate
public class Account {
    
    /**
     * ID 식별자(PK)
     */
    @Id
    @Column(name = "account_id", columnDefinition = "INT(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이메일(유니크)
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * 닉네임
     */
    @Column(name = "nickname", nullable = false)
    private String nickname;

    /**
     * 패스워드
     */
    @Column(length = 255, nullable = false)
    private String password;

    /**
     * 권한 테이블과 연결한다.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "account_roles",
            joinColumns = {
            @JoinColumn(name = "account_id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "role_code") })
    private Set<Role> roles = new HashSet<>();

    /**
     * "'가입유형(일반/구글/네이버/카카오 ....)'"
     */
    @Column(name ="social_type", columnDefinition = "VARCHAR(7) NOT NULL")
    private String socialType;
    
    /**
     *  계정 활성(1)/ 비활성(0) 상태 
     */
    @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isActive;

    /**
     * 계정 생성시간
     */
    @Column(name ="create_at")
    @CreationTimestamp 
    private Date createAt;
    
    @Builder
    public Account(String email, String username, String password, Set<Role> roles, SocialType socialType) {
        Assert.hasText(email,    "@@@@@@@ :  email   must not be empty");
        Assert.hasText(password, "@@@@@@@ : password must not be empty");

        this.email = email;
        this.nickname = (username != null) ? username
                                           : UUID.randomUUID().toString().substring(0, 12);
        if(socialType == null){
            this.socialType = SocialType.NORMAL.name();
        }else{
            this.socialType = socialType.name();
        }
        this.isActive = true;
        this.password = password;
        this.roles = roles;

    }

    public void setRoles(Set<Role> roleSet) {
        this.roles = roleSet;
    }
    public void addRole(Role role){
        this.roles.add(role);
    }
    


        // @OneToOne
    // @JoinColumn(name = "account_detail_id")
    // private AccountDetail accountDetail;

    // @Column(name="status", columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1 COMMENT '계정 활성(1)/비활성(0)'")
    // private Boolean isActivation;

    // columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입날짜(UTC)'")
}
