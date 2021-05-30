package com.shirleydl.mtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirleydl.mtest.entity.Functions;
import com.shirleydl.mtest.entity.PageModules;
import com.shirleydl.mtest.mapper.FunctionsMapper;
import com.shirleydl.mtest.mapper.PageModulesMapper;
import com.shirleydl.mtest.mapper.SystemsMapper;
import com.shirleydl.mtest.service.IFunctionsService;
import com.shirleydl.mtest.service.IPageModulesService;
import com.shirleydl.mtest.service.ISystemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageModulesServiceImpl extends ServiceImpl<PageModulesMapper, PageModules> implements IPageModulesService {
    @Autowired
    private PageModulesMapper pageModulesMapper;
    @Autowired
    private FunctionsMapper functionsMapper;
    @Autowired
    private SystemsMapper systemsMapper;
    @Autowired
    private IFunctionsService functionsService;
    @Autowired
    private ISystemsService systemsService;

    @Override
    public int addOrFindPageModules(String name, int systemsId) {
        QueryWrapper<PageModules> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", name).eq("systems_id", systemsId);
        PageModules pageModules = pageModulesMapper.selectOne(queryWrapper);
        if (pageModules != null) {
            return pageModules.getId();
        }
        pageModules = new PageModules();
        pageModules.setName(name);
        pageModules.setSystemsId(systemsId);
        pageModulesMapper.insert(pageModules);
        return pageModules.getId();
    }


    @Override
    public int deletePageModules(int id) {
        QueryWrapper<Functions> queryWrapper = new QueryWrapper();
        queryWrapper.eq("page_modules_id", id);
        if (functionsMapper.selectCount(queryWrapper) > 0) {
            return -1;
        }
        PageModules pageModules = pageModulesMapper.selectById(id);
        if (pageModules != null) {
            if (pageModulesMapper.deleteById(id) > 0) {
                systemsService.deleteSystems(pageModules.getSystemsId());
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean deleteForcePageModules(int id) {
        PageModules pageModules = pageModulesMapper.selectById(id);
        if (pageModules != null) {
            if (pageModulesMapper.deleteById(id) > 0) {
                functionsService.deleteFunctionsByPageModulesId(id);
                systemsService.deleteSystems(pageModules.getSystemsId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletePageModulesBySystemsId(int systemsId) {
        QueryWrapper<PageModules> queryPageModulesWrapper = new QueryWrapper();
        queryPageModulesWrapper.eq("systems_id", systemsId);
        List<Integer> ids = pageModulesMapper.queryPageModulesIds(queryPageModulesWrapper);
        if (ids.size() > 0) {
            for (int id : ids) {
                deleteForcePageModules(id);
            }
            return true;
        }
        return false;
    }


    @Override
    public int updatePageModules(PageModules pageModules) {
        QueryWrapper<PageModules> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", pageModules.getName()).eq("systems_id", pageModules.getSystemsId());
        if (null == systemsMapper.selectById(pageModules.getSystemsId())) {
            return -1;
        }
        PageModules pageModules2 = pageModulesMapper.selectOne(queryWrapper);
        if (pageModules2 != null && !pageModules2.getId().equals(pageModules.getId())) {
            return -1;
        }
        return pageModulesMapper.updateById(pageModules);
    }

    @Override
    public IPage<PageModules> queryPageModules(Page<PageModules> page, String name, String systemsName) {

        QueryWrapper<PageModules> queryWrapper = new QueryWrapper();
        if (name != null && !name.isEmpty()) {
            queryWrapper = queryWrapper.like("page_modules.name", name);
        }
        if (systemsName != null && !systemsName.isEmpty()) {

            queryWrapper = queryWrapper.like("systems.name", systemsName);
        }
        queryWrapper.orderByAsc("page_modules.id");
        return pageModulesMapper.queryPageModules(page, queryWrapper);
    }
}
