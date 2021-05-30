/**
 * @description 测试功能
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shirleydl.mtest.entity.Functions;
import com.shirleydl.mtest.service.IFunctionsService;
import com.shirleydl.mtest.vo.PageHelper;
import com.shirleydl.mtest.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/functions")
public class FunctionsController {

    @Autowired
    private IFunctionsService functionsService;

    /**
     * 功能列表
     * @param name
     * @param systemsName
     * @param pageModulesName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public PageHelper<Functions> queryFunctions(@RequestParam("name") String name, @RequestParam("systemsName") String systemsName, @RequestParam("pageModulesName") String pageModulesName, @RequestParam("pageIndex") Integer pageCurrent, @RequestParam("pageSize") Integer pageSize) {
        Page<Functions> page = new Page<>(pageCurrent, pageSize);
        functionsService.queryFunctions(page, name, systemsName, pageModulesName);
        PageHelper<Functions> pageHelper = new PageHelper<>();
        pageHelper.setTotal(page.getTotal());
        pageHelper.setData(page.getRecords());
        pageHelper.setSize(page.getSize());
        pageHelper.setCurrent(page.getCurrent());
        pageHelper.setPages(page.getPages());
        return pageHelper;
    }

    /**
     * 删除功能
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    public ResponseVO deleteFunctions(@RequestParam("id") int id) {
        if (functionsService.deleteForceFunctions(id)) {
            return ResponseVO.success("删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否正确！");
    }

    /**
     * 更新功能
     * @param functions
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseVO updateFunctions(@RequestBody Functions functions) {
        if (functionsService.updateFunctions(functions) > 0) {
            return ResponseVO.success("更新成功");
        }
        return ResponseVO.failure("更新失败，请检查数据是否重复或数据是否存在！");
    }
}
