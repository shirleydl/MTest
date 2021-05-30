/**
 * @description TODO
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirleydl.mtest.entity.DemandFile;
import com.shirleydl.mtest.mapper.DemandFileMapper;
import com.shirleydl.mtest.service.IDemandFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandFileServiceImpl extends ServiceImpl<DemandFileMapper, DemandFile> implements IDemandFileService {
    @Autowired
    private DemandFileMapper demandFileMapper;

    @Override
    public List<DemandFile> queryDemandFileList(int demandId) {
        QueryWrapper<DemandFile> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("demand_id", demandId);
        return demandFileMapper.queryDemandFileList(queryWrapper);
    }

    @Override
    public int deleteDemandFileByDemandId(int demandId) {
        QueryWrapper<DemandFile> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("demand_id", demandId);
        return demandFileMapper.delete(queryWrapper);
    }

    @Override
    public DemandFile queryCaseFileByDemandId(int demandId) {
        QueryWrapper<DemandFile> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("demand_id", demandId).eq("is_case", 1);
        return demandFileMapper.selectOne(queryWrapper);
    }

    /**
     * 查询文件名字地址，方便删除
     *
     * @param id
     * @return
     */
    @Override
    public DemandFile queryDemandFileInfo(int id) {
        QueryWrapper<DemandFile> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("id", id);
        return demandFileMapper.queryDemandFile(queryWrapper);
    }

    /**
     * 查询用例文件信息，方便删除
     *
     * @param demandId
     * @return
     */
    @Override
    public DemandFile queryCaseFileInfoByDemandId(int demandId) {
        QueryWrapper<DemandFile> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("demand_id", demandId).eq("is_case", 1);
        return demandFileMapper.queryDemandFile(queryWrapper);
    }


}
