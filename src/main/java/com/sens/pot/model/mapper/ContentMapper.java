package com.sens.pot.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMapper {
    List<Map<String,Object>> select_PostsList();
}

