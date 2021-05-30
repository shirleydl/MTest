package com.shirleydl.mtest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shirleydl.mtest.entity.TestCase;

import java.util.List;

public interface ITestCaseService  extends IService<TestCase> {
    List<TestCase> queryTestCase(int demandId);
    int queryTestCaseCountByDemandId(int demandId);
    int deleteCaseByDemandId(int demandId);
}
