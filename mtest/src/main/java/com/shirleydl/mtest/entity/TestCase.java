/**
 * @description 需求-测试用例
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class TestCase implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;
    protected Integer demandId;
    protected Integer sortId;
    protected String systemsName;
    protected String pageModulesName;
    protected String functionsName;
    protected String testPointName;
    protected String pre;
    protected String step;
    protected String expect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

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
}
