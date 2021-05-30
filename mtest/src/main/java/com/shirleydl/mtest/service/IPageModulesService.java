package com.shirleydl.mtest.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shirleydl.mtest.entity.PageModules;

public interface IPageModulesService extends IService<PageModules> {
    int addOrFindPageModules(String name, int systemsId);

    int deletePageModules(int id);

    boolean deleteForcePageModules(int id);

    boolean deletePageModulesBySystemsId(int systemsId);

    int updatePageModules(PageModules pageModules);

    IPage<PageModules> queryPageModules(Page<PageModules> page, String name, String systemsName);
}
