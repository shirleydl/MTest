/**
 * @description 需求
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shirleydl.mtest.entity.Demand;
import com.shirleydl.mtest.service.IDemandService;
import com.shirleydl.mtest.vo.PageHelper;
import com.shirleydl.mtest.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demand")
public class DemandController {
    @Autowired
    private IDemandService demandService;

    /**
     * 增加需求
     *
     * @param demand
     * @return
     */
    @PostMapping(value = "/add")
    public ResponseVO addDemand(@RequestBody Demand demand) {
        if (demandService.addDemand(demand) > 0) {
            return ResponseVO.success("新增成功");
        }
        return ResponseVO.failure("新增失败，请检查数据是否重复或数据是否正确！");
    }

    /**
     * 需求列表
     *
     * @param nameOrTag
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public PageHelper<Demand> queryDemand(@RequestParam("nameOrTag") String nameOrTag, @RequestParam("pageIndex") Integer pageCurrent, @RequestParam("pageSize") Integer pageSize) {
        Page<Demand> page = new Page<>(pageCurrent, pageSize);
        demandService.queryDemand(page, nameOrTag);
        PageHelper<Demand> pageHelper = new PageHelper<>();
        pageHelper.setTotal(page.getTotal());
        pageHelper.setData(page.getRecords());
        pageHelper.setSize(page.getSize());
        pageHelper.setCurrent(page.getCurrent());
        pageHelper.setPages(page.getPages());
        return pageHelper;
    }

    /**
     * 需求详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/detail")
    public Demand queryDemandDetail(@RequestParam("id") int id) {
        return demandService.getById(id);
    }

    /**
     * 删除需求
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    public ResponseVO deleteDemand(@RequestParam("id") int id) {
        if (demandService.deleteDemand(id) > 0) {
            return ResponseVO.success("删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否有关联或是否正确！");
    }


    /**
     * 更新需求
     *
     * @param demand
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseVO updateDemand(@RequestBody Demand demand) {
        if (demandService.updateDemand(demand) > 0) {
            return ResponseVO.success("更新成功");
        }
        return ResponseVO.failure("更新失败，请检查数据是否重复或数据是否存在！");
    }
}
