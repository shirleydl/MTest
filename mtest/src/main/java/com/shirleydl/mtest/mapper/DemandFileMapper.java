package com.shirleydl.mtest.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shirleydl.mtest.entity.DemandFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DemandFileMapper extends BaseMapper<DemandFile> {
    @Select("SELECT id,demand_id,file_name,type,file_url FROM demand_file ${ew.customSqlSegment}")
    List<DemandFile> queryDemandFileList(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("SELECT id,demand_id,file_name,file_url FROM demand_file ${ew.customSqlSegment}")
    DemandFile queryDemandFile(@Param(Constants.WRAPPER) Wrapper wrapper);
}
