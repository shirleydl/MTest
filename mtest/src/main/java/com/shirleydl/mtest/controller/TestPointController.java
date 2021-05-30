/**
 * @description 测试点
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shirleydl.mtest.entity.TestContent;
import com.shirleydl.mtest.entity.TestPoint;
import com.shirleydl.mtest.service.ITestContentService;
import com.shirleydl.mtest.service.ITestPointService;
import com.shirleydl.mtest.vo.PageHelper;
import com.shirleydl.mtest.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testPoint")
public class TestPointController {

    @Autowired
    private ITestPointService testPointService;
    @Autowired
    private ITestContentService testContentService;

    /**
     * 测试点列表
     * @param name
     * @param systemsName
     * @param pageModulesName
     * @param functionsName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public PageHelper<TestPoint> queryTestPoint(@RequestParam("name") String name, @RequestParam("systemsName") String systemsName, @RequestParam("pageModulesName") String pageModulesName, @RequestParam("functionsName") String functionsName, @RequestParam("pageIndex") Integer pageCurrent, @RequestParam("pageSize") Integer pageSize) {
        Page<TestPoint> page = new Page<>(pageCurrent, pageSize);
        testPointService.queryTestPoint(page, name, systemsName, pageModulesName, functionsName);
        PageHelper<TestPoint> pageHelper = new PageHelper<>();
        pageHelper.setTotal(page.getTotal());
        pageHelper.setData(page.getRecords());
        pageHelper.setSize(page.getSize());
        pageHelper.setCurrent(page.getCurrent());
        pageHelper.setPages(page.getPages());
        return pageHelper;
    }

    /**
     * 删除测试点
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    public ResponseVO deleteTestPoint(@RequestParam("id") int id) {
        if (testPointService.deleteTestPoint(id)) {
            return ResponseVO.success("删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否存在！");
    }

    /**
     * 更新测试点
     * @param testPoint
     * @return
     */
    @PostMapping(value = "/update")
    public ResponseVO updateTestPoint(@RequestBody TestPoint testPoint) {
        if (testPointService.updateTestPoint(testPoint) > 0) {
            return ResponseVO.success("更新成功");
        }
        return ResponseVO.failure("更新失败，请检查数据是否重复！");
    }

    /**
     * 测试点内容（前置、步骤、期望）
     * @param testPointId
     * @return
     */
    @GetMapping(value = "/content")
    public List<TestContent> queryTestContent(@RequestParam("testPointId") int testPointId) {
        return testContentService.queryTestContent(testPointId);
    }

    /**
     * 删除测试点内容
     * @param id
     * @return
     */
    @GetMapping(value = "/deleteContent")
    public ResponseVO deleteContent(@RequestParam("id") int id) {
        if (testContentService.removeById(id)) {
            return ResponseVO.success("删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否存在！");
    }

    /**
     * 更新测试点内容
     * @param testContent
     * @return
     */
    @PostMapping(value = "/updateContent")
    public ResponseVO updateContent(@RequestBody TestContent testContent) {
        if (testContentService.updateTestContent(testContent) > 0) {
            return ResponseVO.success("更新成功");
        }
        return ResponseVO.failure("更新失败，请检查数据是否重复或数据是否存在！");
    }
}
