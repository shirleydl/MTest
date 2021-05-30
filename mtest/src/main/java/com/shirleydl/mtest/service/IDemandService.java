package com.shirleydl.mtest.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shirleydl.mtest.entity.Demand;

public interface IDemandService extends IService<Demand> {
    int addDemand(Demand demand);

    int deleteDemand(int id);

    int updateDemand(Demand demand);

    IPage<Demand> queryDemand(Page<Demand> page, String nameOrTag);
}
