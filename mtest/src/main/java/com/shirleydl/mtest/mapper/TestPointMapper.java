package com.shirleydl.mtest.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shirleydl.mtest.entity.TestPoint;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TestPointMapper extends BaseMapper<TestPoint> {
    @Select("SELECT test_point.*,systems.name as systems_name,page_modules.name as page_modules_name,functions.name as functions_name FROM test_point LEFT JOIN functions ON test_point.functions_id = functions.id LEFT JOIN page_modules ON functions.page_modules_id = page_modules.id LEFT JOIN systems ON page_modules.systems_id = systems.id ${ew.customSqlSegment}")
    IPage<TestPoint> queryTestPoint(IPage<TestPoint> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("SELECT id FROM test_point ${ew.customSqlSegment}")
    List<Integer> queryTestPointIds( @Param(Constants.WRAPPER) Wrapper wrapper);
}
