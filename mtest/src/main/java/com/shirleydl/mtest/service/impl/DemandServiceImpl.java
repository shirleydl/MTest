/**
 * @description TODO
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirleydl.mtest.entity.Demand;
import com.shirleydl.mtest.entity.TestCase;
import com.shirleydl.mtest.mapper.DemandMapper;
import com.shirleydl.mtest.mapper.TestCaseMapper;
import com.shirleydl.mtest.service.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements IDemandService {
    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private TestCaseMapper testCaseMapper;

    @Override
    public int addDemand(Demand demand) {
        QueryWrapper<Demand> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", demand.getName());
        if (demandMapper.selectCount(queryWrapper) > 0) {
            return -1;
        }
        return demandMapper.insert(demand);
    }

    @Override
    public int deleteDemand(int id) {
        QueryWrapper<TestCase> queryWrapper = new QueryWrapper();
        queryWrapper.eq("demand_id", id);
        if (testCaseMapper.selectCount(queryWrapper) > 0) {
            return -1;
        }
        return demandMapper.deleteById(id);
    }

    @Override
    public int updateDemand(Demand demand) {
        QueryWrapper<Demand> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", demand.getName());
        Demand demand2 = demandMapper.selectOne(queryWrapper);
        if (demand2 != null && !demand2.getId().equals(demand.getId())) {
            return -1;
        }
        return demandMapper.updateById(demand);
    }

    @Override
    public IPage<Demand> queryDemand(Page<Demand> page, String nameOrTag) {
        QueryWrapper<Demand> queryWrapper = null;
        if (nameOrTag != null && !nameOrTag.isEmpty()) {
            queryWrapper = new QueryWrapper();
            queryWrapper.like("name", nameOrTag).or().like("tags", nameOrTag);
        }
        return demandMapper.selectPage(page, queryWrapper);
    }
}
