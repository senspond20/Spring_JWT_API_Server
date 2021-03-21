package com.sens.pot.web.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "role")
@Getter
@ToString
public class Role {
    @Id
    @Column(name = "role_id", columnDefinition = "INT(11)" )
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name", length = 60)
    private String name;

    @Column(length = 155)
    private String description;
    
    @Builder
    public Role(String name, String description){
        this.name = name;
        this.description =description;
    }
}
