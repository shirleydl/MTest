package com.shirleydl.mtest.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shirleydl.mtest.entity.Functions;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FunctionsMapper extends BaseMapper<Functions> {
    @Select("SELECT functions.*,systems.name as systems_name,page_modules.name as page_modules_name FROM functions LEFT JOIN page_modules ON functions.page_modules_id = page_modules.id LEFT JOIN systems ON page_modules.systems_id = systems.id ${ew.customSqlSegment}")
    IPage<Functions> queryFunctions(IPage<Functions> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("SELECT id FROM functions ${ew.customSqlSegment}")
    List<Integer> queryFunctionsIds(@Param(Constants.WRAPPER) Wrapper wrapper);
}
