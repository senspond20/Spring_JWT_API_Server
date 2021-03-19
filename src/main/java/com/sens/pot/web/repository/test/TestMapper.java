package com.sens.pot.web.repository.test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {

    // @Select("SELECT * FROM TEST")
    public List<Map<String,Object>> selectTestAll();
}
