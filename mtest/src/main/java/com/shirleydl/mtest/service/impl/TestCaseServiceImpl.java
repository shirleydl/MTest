/**
 * @description TODO
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirleydl.mtest.entity.TestCase;
import com.shirleydl.mtest.mapper.TestCaseMapper;
import com.shirleydl.mtest.service.ITestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCase> implements ITestCaseService {
    @Autowired
    private TestCaseMapper testCaseMapper;

    @Override
    public List<TestCase> queryTestCase(int demandId) {
        QueryWrapper<TestCase> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("demand_id", demandId);
        queryWrapper.orderByAsc("sort_id");
        return testCaseMapper.selectList(queryWrapper);
    }

    @Override
    public int queryTestCaseCountByDemandId(int demandId) {
        QueryWrapper<TestCase> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("demand_id", demandId);
        return testCaseMapper.selectCount(queryWrapper);
    }

    @Override
    public int deleteCaseByDemandId(int demandId) {
        QueryWrapper<TestCase> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("demand_id", demandId);
        return testCaseMapper.delete(queryWrapper);
    }
}
