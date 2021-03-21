package com.sens.pot.web.repository.test;

import java.util.List;

import com.sens.pot.web.domain.test.Test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {

    @Select("SELECT * FROM test")
    public List<Test> select_TestAll();
}
