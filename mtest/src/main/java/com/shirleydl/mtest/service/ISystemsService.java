package com.shirleydl.mtest.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shirleydl.mtest.entity.Systems;

public interface ISystemsService extends IService<Systems> {
    int addOrFindSystems(String name);

    int deleteSystems(int id);

    boolean deleteForceSystems(int id);

    int updateSystems(Systems systems);

    IPage<Systems> querySystems(Page<Systems> page, String name);
}
