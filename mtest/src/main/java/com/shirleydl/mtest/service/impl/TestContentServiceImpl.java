/**
 * @description TODO
 * @author 371683941@qq.com
 * @date 2021/5/7
 */
package com.shirleydl.mtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirleydl.mtest.entity.TestCase;
import com.shirleydl.mtest.entity.TestContent;
import com.shirleydl.mtest.mapper.TestCaseMapper;
import com.shirleydl.mtest.mapper.TestContentMapper;
import com.shirleydl.mtest.mapper.TestPointMapper;
import com.shirleydl.mtest.service.ITestContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestContentServiceImpl extends ServiceImpl<TestContentMapper, TestContent> implements ITestContentService {
    @Autowired
    private TestContentMapper testContentMapper;
    @Autowired
    private TestPointMapper testPointMapper;
    @Autowired
    private TestCaseMapper testCaseMapper;

    @Override
    public List<TestContent> queryTestContent(int testPointId) {
        QueryWrapper<TestContent> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("test_point_id", testPointId);
        return testContentMapper.selectList(queryWrapper);
    }

    @Override
    public List<TestCase> queryTestContentToTestCase(int testPointId, int demandId, int maxSortId) {
        QueryWrapper<TestContent> queryWrapper = new QueryWrapper();
        queryWrapper = queryWrapper.eq("test_point_id", testPointId);
        List<TestCase> testCaseList = testContentMapper.queryTestContentToTestCase(queryWrapper);

        for (int i = 0; i < testCaseList.size(); i++) {
            testCaseList.get(i).setDemandId(demandId);
            testCaseList.get(i).setSortId(maxSortId + i);
        }

        return testCaseList;
    }

    @Override
    public void addOrFindTestContent(String pre, String step, String expect, int testPointId) {
        QueryWrapper<TestContent> queryWrapper = new QueryWrapper();
        queryWrapper.eq("pre", pre).eq("step", step).eq("test_point_id", testPointId);
        int count = testContentMapper.selectCount(queryWrapper);
        if (count != 0) {
            return;
        }
        TestContent testContent = new TestContent();
        testContent.setPre(pre);
        testContent.setStep(step);
        testContent.setExpect(expect);
        testContent.setTestPointId(testPointId);
        testContentMapper.insert(testContent);
    }

    @Override
    public int updateTestContent(TestContent testContent) {
        QueryWrapper<TestContent> queryWrapper = new QueryWrapper();
        queryWrapper.eq("pre", testContent.getPre()).eq("step", testContent.getStep()).eq("test_point_id", testContent.getTestPointId());
        if (null == testPointMapper.selectById(testContent.getTestPointId())) {
            return -1;
        }
        TestContent testContent2 = testContentMapper.selectOne(queryWrapper);
        if (testContent2 != null && !testContent2.getId().equals(testContent.getId())) {
            return -1;
        }
        return testContentMapper.updateById(testContent);
    }
}
