package com.shirleydl.mtest.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shirleydl.mtest.entity.TestPoint;

public interface ITestPointService extends IService<TestPoint> {
    int addOrFindTestPoint(String name, int functionsId);

    IPage<TestPoint> queryTestPoint(Page<TestPoint> page, String name, String systemsName, String pageModulesName, String functionsName);

    int updateTestPoint(TestPoint testPoint);

    boolean deleteTestPoint(int id);

    boolean deleteTestPointByFunctionsId(int functionsId);
}
