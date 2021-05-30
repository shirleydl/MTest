package com.shirleydl.mtest.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shirleydl.mtest.entity.PageModules;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface PageModulesMapper extends BaseMapper<PageModules> {
    @Select("SELECT page_modules.*,systems.name as systems_name FROM page_modules LEFT JOIN systems ON page_modules.systems_id = systems.id ${ew.customSqlSegment}")
    IPage<PageModules> queryPageModules(IPage<PageModules> page, @Param(Constants.WRAPPER) Wrapper wrapper);


    @Select("SELECT id FROM page_modules ${ew.customSqlSegment}")
    List<Integer> queryPageModulesIds(@Param(Constants.WRAPPER) Wrapper wrapper);

}
