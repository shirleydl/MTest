/**
 * @description 测试点
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class TestPoint implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;
    protected String name;
    protected Integer functionsId;
    @TableField(exist = false)
    protected String systemsName;
    @TableField(exist = false)
    protected String pageModulesName;
    @TableField(exist = false)
    protected String functionsName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFunctionsId() {
        return functionsId;
    }

    public void setFunctionsId(Integer functionsId) {
        this.functionsId = functionsId;
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
}
