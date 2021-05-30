package com.shirleydl.mtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirleydl.mtest.entity.Functions;
import com.shirleydl.mtest.entity.TestPoint;
import com.shirleydl.mtest.mapper.FunctionsMapper;
import com.shirleydl.mtest.mapper.PageModulesMapper;
import com.shirleydl.mtest.mapper.TestPointMapper;
import com.shirleydl.mtest.service.IFunctionsService;
import com.shirleydl.mtest.service.IPageModulesService;
import com.shirleydl.mtest.service.ITestPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunctionsServiceImpl extends ServiceImpl<FunctionsMapper, Functions> implements IFunctionsService {
    @Autowired
    private FunctionsMapper functionsMapper;
    @Autowired
    private TestPointMapper testPointMapper;
    @Autowired
    private PageModulesMapper pageModulesMapper;
    @Autowired
    private ITestPointService testPointService;
    @Autowired
    private IPageModulesService pageModulesService;


    @Override
    public int addOrFindFunctions(String name, int pageModulesId) {
        QueryWrapper<Functions> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", name);
        queryWrapper.eq("page_modules_id", pageModulesId);
        Functions functions = functionsMapper.selectOne(queryWrapper);
        if (functions != null) {
            return functions.getId();
        }
        functions = new Functions();
        functions.setName(name);
        functions.setPageModulesId(pageModulesId);
        functionsMapper.insert(functions);
        return functions.getId();
    }

    @Override
    public int deleteFunctions(int id) {
        QueryWrapper<TestPoint> queryWrapper = new QueryWrapper();
        queryWrapper.eq("functions_id", id);
        if (testPointMapper.selectCount(queryWrapper) > 0) {
            return -1;
        }
        Functions functions = functionsMapper.selectById(id);
        if (functions != null) {
            if (functionsMapper.deleteById(id) > 0) {
                pageModulesService.deletePageModules(functions.getPageModulesId());
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean deleteForceFunctions(int id) {
        Functions functions = functionsMapper.selectById(id);
        if (functions != null) {
            if (functionsMapper.deleteById(id) > 0) {
                testPointService.deleteTestPointByFunctionsId(id);
                pageModulesService.deletePageModules(functions.getPageModulesId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteFunctionsByPageModulesId(int pageModulesId) {
        QueryWrapper<Functions> queryFunctionsWrapper = new QueryWrapper();
        queryFunctionsWrapper.eq("page_modules_id", pageModulesId);
        List<Integer> ids = functionsMapper.queryFunctionsIds(queryFunctionsWrapper);
        if (ids.size() > 0) {
            for (int id : ids) {
                deleteForceFunctions(id);
            }
            return true;
        }
        return false;
    }


    @Override
    public int updateFunctions(Functions functions) {
        QueryWrapper<Functions> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", functions.getName());
        queryWrapper.eq("page_modules_id", functions.getPageModulesId());
        if (null == pageModulesMapper.selectById(functions.getPageModulesId())) {
            return -1;
        }
        Functions functions2 = functionsMapper.selectOne(queryWrapper);
        if (functions2 != null && !functions2.getId().equals(functions.getId())) {
            return -1;
        }
        return functionsMapper.updateById(functions);
    }

    @Override
    public IPage<Functions> queryFunctions(Page<Functions> page, String name, String systemsName, String pageModulesName) {

        QueryWrapper<Functions> queryWrapper = new QueryWrapper();
        if (name != null && !name.isEmpty()) {
            queryWrapper = queryWrapper.like("functions.name", name);
        }
        if (systemsName != null && !systemsName.isEmpty()) {

            queryWrapper = queryWrapper.like("systems.name", systemsName);
        }
        if (pageModulesName != null && !pageModulesName.isEmpty()) {

            queryWrapper = queryWrapper.like("page_modules.name", pageModulesName);
        }
        queryWrapper.orderByAsc("functions.id");
        return functionsMapper.queryFunctions(page, queryWrapper);
    }

}
