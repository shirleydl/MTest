/**
 * @description 需求-测试用例
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.controller;

import com.alibaba.excel.EasyExcel;
import com.shirleydl.mtest.entity.Demand;
import com.shirleydl.mtest.entity.TestCase;
import com.shirleydl.mtest.method.CheckFileUtil;
import com.shirleydl.mtest.method.ExcelUtils;
import com.shirleydl.mtest.service.IDemandService;
import com.shirleydl.mtest.service.ITestCaseService;
import com.shirleydl.mtest.service.ITestContentService;
import com.shirleydl.mtest.vo.ExportInfo;
import com.shirleydl.mtest.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/testCase")
public class TestCaseController {
    @Autowired
    private ITestCaseService testCaseService;
    @Autowired
    private IDemandService demandService;
    @Autowired
    private ITestContentService testContentService;

    @GetMapping(value = "/list")
    public List<TestCase> queryTestCase(@RequestParam("demandId") int demandId) {
        return testCaseService.queryTestCase(demandId);
    }


    @PostMapping(value = "/addOrUpdateBatch")
    public ResponseVO addOrUpdateTestCaseBatch(@RequestBody List<TestCase> testCases) {
        if (testCaseService.saveOrUpdateBatch(testCases)) {
            return ResponseVO.success("批量更新成功");
        }
        return ResponseVO.failure("更新失败，请检查数据是否正确！");
    }

    @PostMapping(value = "/addOrUpdate")
    public ResponseVO addOrUpdateTestCase(@RequestBody TestCase testCase) {
        if (testCaseService.saveOrUpdate(testCase)) {
            return ResponseVO.success("更新成功");
        }
        return ResponseVO.failure("更新失败，请检查数据是否正确！");
    }

    @PostMapping(value = "/deleteBatch")
    public ResponseVO deleteTestCaseBatch(@RequestBody List<Integer> ids) {
        if (testCaseService.removeByIds(ids)) {
            return ResponseVO.success("批量删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否正确！");
    }


    @GetMapping(value = "/deleteByDemandId")
    public ResponseVO deleteTestCaseByDemandId(@RequestParam("demandId") int demandId) {
        if (testCaseService.deleteCaseByDemandId(demandId) > 0) {
            return ResponseVO.success("批量删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否正确！");
    }

    @GetMapping(value = "/delete")
    public ResponseVO deleteTestCase(@RequestParam("id") int id) {
        if (testCaseService.removeById(id)) {
            return ResponseVO.success("删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否正确！");
    }

    @GetMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response, @RequestParam("demandId") int demandId) throws IOException {
        Demand demand = demandService.getById(demandId);
        String name = "测试用例";
        if (demand != null) {
            name = demand.getName();
        }
        List<TestCase> testCaseList = testCaseService.queryTestCase(demandId);
        // 这里需要设置不关闭流
        String fileName = URLEncoder.encode(name, "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ExportInfo.class).sheet("测试用例").doWrite(data(testCaseList));

    }

    @PostMapping("/exportFile")
    public ResponseVO exportFileToTestCase(@RequestParam("file") MultipartFile file, int demandId) {
        try {
            List<TestCase> testCaseList = new ArrayList<>();
            //获取文件类型
            Integer type = new CheckFileUtil().getFileType(file);
            if (type == 1) {
                if (demandService.getById(demandId) != null) {
                    testCaseList = new ExcelUtils().readExcelToTestCase(file, demandId, testCaseService.queryTestCaseCountByDemandId(demandId));
                }
            } else {
                return ResponseVO.failure("导入失败，格式不支持！");
            }
            if (testCaseService.saveOrUpdateBatch(testCaseList)) {
                return ResponseVO.success("导入成功");
            }
            return ResponseVO.failure("导入失败，请检查数据是否正确！");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseVO.failure("导入失败");
        }

    }

    @PostMapping("/exportPointIdToTestCase")
    public ResponseVO exportPointIdToTestCase(@RequestParam("testPointId") int testPointId, int demandId) {
        if (demandService.getById(demandId) != null) {
            List<TestCase> testCaseList = testContentService.queryTestContentToTestCase(testPointId, demandId, testCaseService.queryTestCaseCountByDemandId(demandId));
            if (testCaseService.saveOrUpdateBatch(testCaseList)) {
                return ResponseVO.success("导入成功");
            }
        }
        return ResponseVO.failure("导入失败，请检查数据是否正确！");
    }

    private List<ExportInfo> data(List<TestCase> testCaseList) {
        List<ExportInfo> list = new ArrayList<ExportInfo>();
        for (TestCase testCase : testCaseList) {
            ExportInfo exportInfo = new ExportInfo();
            exportInfo.setSystemsName(testCase.getSystemsName());
            exportInfo.setPageModulesName(testCase.getPageModulesName());
            exportInfo.setFunctionsName(testCase.getFunctionsName());
            exportInfo.setTestPointName(testCase.getTestPointName());
            exportInfo.setPre(testCase.getPre());
            exportInfo.setStep(testCase.getStep());
            exportInfo.setExpect(testCase.getExpect());
            list.add(exportInfo);
        }
        return list;
    }
}
