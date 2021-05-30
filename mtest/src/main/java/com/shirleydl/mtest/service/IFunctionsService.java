package com.shirleydl.mtest.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shirleydl.mtest.entity.Functions;

public interface IFunctionsService extends IService<Functions> {
    int addOrFindFunctions(String name, int pageModulesId);

    int deleteFunctions(int id);

    boolean deleteForceFunctions(int id);

    boolean deleteFunctionsByPageModulesId(int pageModulesId);

    int updateFunctions(Functions functions);

    IPage<Functions> queryFunctions(Page<Functions> page, String name, String systemsName, String pageModulesName);
}
