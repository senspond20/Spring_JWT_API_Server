package com.sens.pot.web.domain.content;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Getter
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "reply_id")
    private Long id;


    // 태그명
    @Column(name ="tag_name")
    private String name;

}
