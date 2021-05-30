/**
 * @description 测试页面模块
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shirleydl.mtest.entity.PageModules;
import com.shirleydl.mtest.service.IPageModulesService;
import com.shirleydl.mtest.vo.PageHelper;
import com.shirleydl.mtest.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pageModules")
public class PageModulesController {

    @Autowired
    private IPageModulesService pageModulesService;

    /**
     * 页面模块列表
     * @param name
     * @param systemsName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public PageHelper<PageModules> queryPageModules(@RequestParam("name") String name, @RequestParam("systemsName") String systemsName, @RequestParam("pageIndex") Integer pageCurrent, @RequestParam("pageSize") Integer pageSize) {
        Page<PageModules> page = new Page<>(pageCurrent, pageSize);
        pageModulesService.queryPageModules(page, name, systemsName);
        PageHelper<PageModules> pageHelper = new PageHelper<>();
        pageHelper.setTotal(page.getTotal());
        pageHelper.setData(page.getRecords());
        pageHelper.setSize(page.getSize());
        pageHelper.setCurrent(page.getCurrent());
        pageHelper.setPages(page.getPages());
        return pageHelper;
    }

    /**
     * 删除页面模块
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    public ResponseVO deletePageModules(@RequestParam("id") int id) {
        if (pageModulesService.deleteForcePageModules(id)) {
            return ResponseVO.success("删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否正确！");
    }

    /**
     * 更新页面模块
     * @param pageModules
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseVO updatePageModules(@RequestBody PageModules pageModules) {
        if (pageModulesService.updatePageModules(pageModules) > 0) {
            return ResponseVO.success("更新成功");
        }
        return ResponseVO.failure("更新失败，请检查数据是否重复或数据是否存在！");
    }

}
