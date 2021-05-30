package com.shirleydl.mtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirleydl.mtest.entity.PageModules;
import com.shirleydl.mtest.entity.Systems;
import com.shirleydl.mtest.mapper.PageModulesMapper;
import com.shirleydl.mtest.mapper.SystemsMapper;
import com.shirleydl.mtest.service.IPageModulesService;
import com.shirleydl.mtest.service.ISystemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemsServiceImpl extends ServiceImpl<SystemsMapper, Systems> implements ISystemsService {

    @Autowired
    private SystemsMapper systemsMapper;
    @Autowired
    private PageModulesMapper pageModulesMapper;
    @Autowired
    private IPageModulesService pageModulesService;

    public int addOrFindSystems(String name) {
        QueryWrapper<Systems> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", name);
        Systems systems = systemsMapper.selectOne(queryWrapper);
        if (systems != null) {
            return systems.getId();
        }
        systems = new Systems();
        systems.setName(name);
        systemsMapper.insert(systems);
        return systems.getId();
    }

    @Override
    public int deleteSystems(int id) {
        QueryWrapper<PageModules> queryWrapper = new QueryWrapper();
        queryWrapper.eq("systems_id", id);
        if (pageModulesMapper.selectCount(queryWrapper) > 0) {
            return -1;
        }
        return systemsMapper.deleteById(id);
    }

    @Override
    public boolean deleteForceSystems(int id) {
        if (systemsMapper.deleteById(id) > 0) {
            pageModulesService.deletePageModulesBySystemsId(id);
            return true;
        }
        return false;
    }


    @Override
    public int updateSystems(Systems systems) {
        QueryWrapper<Systems> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", systems.getName());
        Systems systems2 = systemsMapper.selectOne(queryWrapper);
        if (systems2 != null && !systems2.getId().equals(systems.getId())) {
            return -1;
        }
        return systemsMapper.updateById(systems);
    }

    public IPage<Systems> querySystems(Page<Systems> page, String name) {
        QueryWrapper<Systems> queryWrapper = null;
        if (name != null && !name.isEmpty()) {
            queryWrapper = new QueryWrapper();
            queryWrapper.like("name", name);
        }
        return systemsMapper.selectPage(page, queryWrapper);
    }

}
