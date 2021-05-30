package com.shirleydl.mtest.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shirleydl.mtest.entity.TestCase;
import com.shirleydl.mtest.entity.TestContent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TestContentMapper extends BaseMapper<TestContent> {
    @Select("SELECT test_content.pre,test_content.step,test_content.expect,test_point.name as test_point_name,systems.name as systems_name,page_modules.name as page_modules_name,functions.name as functions_name FROM test_content LEFT JOIN test_point ON test_content.test_point_id = test_point.id LEFT JOIN functions ON test_point.functions_id = functions.id LEFT JOIN page_modules ON functions.page_modules_id = page_modules.id LEFT JOIN systems ON page_modules.systems_id = systems.id ${ew.customSqlSegment}")
    List<TestCase> queryTestContentToTestCase(@Param(Constants.WRAPPER) Wrapper wrapper);
}
