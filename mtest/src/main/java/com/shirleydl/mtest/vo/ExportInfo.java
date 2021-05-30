/**
 * @description TODO
 * @author 371683941@qq.com
 * @date 2021/4/22
 */
package com.shirleydl.mtest.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

@ContentRowHeight(15)
@HeadRowHeight(15)
@ColumnWidth(20)
public class ExportInfo {
    @ColumnWidth(15)
    @ExcelProperty(value = "用例编号", index = 0)
    protected String no;
    @ExcelProperty(value = "项目系统名", index = 1)
    protected String systemsName;
    @ExcelProperty(value = "页面模块名", index = 2)
    protected String pageModulesName;
    @ExcelProperty(value = "功能名", index = 3)
    protected String functionsName;
    @ExcelProperty(value = "测试点", index = 4)
    protected String testPointName;
    @ColumnWidth(30)
    @ExcelProperty(value = "前置条件", index = 5)
    protected String pre;
    @ColumnWidth(30)
    @ExcelProperty(value = "测试步骤", index = 6)
    protected String step;
    @ColumnWidth(30)
    @ExcelProperty(value = "预期结果", index = 7)
    protected String expect;
    @ColumnWidth(15)
    @ExcelProperty(value = "实际结果", index = 8)
    protected String result;
    @ColumnWidth(15)
    @ExcelProperty(value = "测试时间", index = 9)
    protected String testTime;
    @ColumnWidth(15)
    @ExcelProperty(value = "备注", index = 10)
    protected String remark;

    public String getSystemsName() {
        return systemsName;
    }

    public void setSystemsName(String systemsName) {
        this.systemsName = systemsName;
    }

    public String getPageModulesName() {
        return pageModulesName;
    }

    public void setPageModulesName(String pageModulesName) {
        this.pageModulesName = pageModulesName;
    }

    public String getFunctionsName() {
        return functionsName;
    }

    public void setFunctionsName(String functionsName) {
        this.functionsName = functionsName;
    }

    public String getTestPointName() {
        return testPointName;
    }

    public void setTestPointName(String testPointName) {
        this.testPointName = testPointName;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
