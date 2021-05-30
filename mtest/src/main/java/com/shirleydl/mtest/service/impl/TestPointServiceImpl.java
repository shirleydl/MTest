package com.shirleydl.mtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirleydl.mtest.entity.TestContent;
import com.shirleydl.mtest.entity.TestPoint;
import com.shirleydl.mtest.mapper.FunctionsMapper;
import com.shirleydl.mtest.mapper.TestContentMapper;
import com.shirleydl.mtest.mapper.TestPointMapper;
import com.shirleydl.mtest.service.IFunctionsService;
import com.shirleydl.mtest.service.ITestPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPointServiceImpl extends ServiceImpl<TestPointMapper, TestPoint> implements ITestPointService {
    @Autowired
    private TestPointMapper testPointMapper;
    @Autowired
    private FunctionsMapper functionsMapper;
    @Autowired
    private TestContentMapper testContentMapper;
    @Autowired
    private IFunctionsService functionsService;

    @Override
    public int addOrFindTestPoint(String name, int functionsId) {
        QueryWrapper<TestPoint> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", name).eq("functions_id", functionsId);
        TestPoint testPoint = testPointMapper.selectOne(queryWrapper);
        if (testPoint != null) {
            return testPoint.getId();
        }
        testPoint = new TestPoint();
        testPoint.setName(name);
        testPoint.setFunctionsId(functionsId);
        testPointMapper.insert(testPoint);
        return testPoint.getId();
    }


    @Override
    public int updateTestPoint(TestPoint testPoint) {
        QueryWrapper<TestPoint> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", testPoint.getName()).eq("functions_id", testPoint.getFunctionsId());
        if (null == functionsMapper.selectById(testPoint.getFunctionsId())) {
            return -1;
        }
        TestPoint testPoint2 = testPointMapper.selectOne(queryWrapper);
        if (testPoint2 != null && !testPoint2.getId().equals(testPoint.getId())) {
            return -1;
        }
        return testPointMapper.updateById(testPoint);
    }

    @Override
    public IPage<TestPoint> queryTestPoint(Page<TestPoint> page, String name, String systemsName, String pageModulesName, String functionsName) {

        QueryWrapper<TestPoint> queryWrapper = new QueryWrapper();
        if (name != null && !name.isEmpty()) {
            queryWrapper = queryWrapper.like("test_point.name", name);
        }
        if (systemsName != null && !systemsName.isEmpty()) {

            queryWrapper = queryWrapper.like("systems.name", systemsName);
        }
        if (pageModulesName != null && !pageModulesName.isEmpty()) {

            queryWrapper = queryWrapper.like("page_modules.name", pageModulesName);
        }
        if (functionsName != null && !functionsName.isEmpty()) {

            queryWrapper = queryWrapper.like("functions.name", functionsName);
        }
        queryWrapper.orderByAsc("test_point.id");
        return testPointMapper.queryTestPoint(page, queryWrapper);
    }

    @Override
    public boolean deleteTestPoint(int id) {
        TestPoint testPoint = testPointMapper.selectById(id);
        if (testPoint != null) {
            if (testPointMapper.deleteById(id) > 0) {
                QueryWrapper<TestContent> queryWrapper = new QueryWrapper();
                queryWrapper.eq("test_Point_id", id);
                testContentMapper.delete(queryWrapper);
                functionsService.deleteFunctions(testPoint.getFunctionsId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTestPointByFunctionsId(int functionsId) {
        QueryWrapper<TestPoint> queryTestPointWrapper = new QueryWrapper();
        queryTestPointWrapper.eq("functions_id", functionsId);
        List<Integer> ids = testPointMapper.queryTestPointIds(queryTestPointWrapper);
        if (ids.size() > 0) {
            for (int id : ids) {
                deleteTestPoint(id);
            }
            return true;
        }
        return false;
    }


}
