/**
 * @description 测试系统
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shirleydl.mtest.entity.Systems;
import com.shirleydl.mtest.service.ISystemsService;
import com.shirleydl.mtest.vo.PageHelper;
import com.shirleydl.mtest.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/systems")
public class SystemsController {

    @Autowired
    private ISystemsService systemsService;

    /**
     * 系统列表
     * @param name
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public PageHelper<Systems> querySystems(@RequestParam("name") String name, @RequestParam("pageIndex") Integer pageCurrent, @RequestParam("pageSize") Integer pageSize) {
        Page<Systems> page = new Page<>(pageCurrent, pageSize);
        systemsService.querySystems(page, name);
        PageHelper<Systems> pageHelper = new PageHelper<>();
        pageHelper.setTotal(page.getTotal());
        pageHelper.setData(page.getRecords());
        pageHelper.setSize(page.getSize());
        pageHelper.setCurrent(page.getCurrent());
        pageHelper.setPages(page.getPages());
        return pageHelper;
    }

    /**
     * 删除系统
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    public ResponseVO deleteSystems(@RequestParam("id") int id) {
        if (systemsService.deleteForceSystems(id)) {
            return ResponseVO.success("删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否正确！");
    }

    /**
     * 更新系统
     * @param systems
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseVO updateSystems(@RequestBody Systems systems) {
        if (systemsService.updateSystems(systems) > 0) {
            return ResponseVO.success("更新成功");
        }
        return ResponseVO.failure("更新失败，请检查数据是否重复或数据是否存在！");
    }

}
