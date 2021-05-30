package com.shirleydl.mtest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shirleydl.mtest.entity.TestCase;
import com.shirleydl.mtest.entity.TestContent;

import java.util.List;

public interface ITestContentService extends IService<TestContent> {
    List<TestContent> queryTestContent(int testPointId);
    int updateTestContent(TestContent testContent);
    void addOrFindTestContent(String pre,String step,String expect, int testPointId);
    List<TestCase> queryTestContentToTestCase(int testPointId,int demandId,int maxSortId);
}
