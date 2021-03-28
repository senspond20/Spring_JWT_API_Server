package com.sens.pot.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "code")
@Table(name = "role")
@Getter
@ToString
public class Role {
    @Id
    @Column(name="role_code", length = 7)
    @GeneratedValue(generator = "RoleGenerator")  
    @GenericGenerator(name = "RoleGenerator", strategy = "com.sens.pot.common.helper.RoleGenerator")  
    private String code;

    @Column(name = "role_name", length = 60, unique = true)
    private String roleName;

    @Column(length = 155)
    private String description;
    
    @Builder
    public Role(String roleName, String description){
        this.roleName = roleName;
        this.description =description;
    }
    
}
