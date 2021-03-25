package com.sens.pot.web.domain.account;

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
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.DynamicInsert;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(length = 120, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "account_roles",
            joinColumns = {
            @JoinColumn(name = "account_id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    
    @Builder
    public Account(String email, String password, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void updateRoles(Set<Role> roleSet) {
        this.roles = roleSet;
    }
}
