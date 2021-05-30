/**
 * @description 功能
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class Functions implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;
    protected String name;
    protected Integer pageModulesId;
    @TableField(exist = false)
    protected String systemsName;
    @TableField(exist = false)
    protected String pageModulesName;

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

    public Integer getPageModulesId() {
        return pageModulesId;
    }

    public void setPageModulesId(Integer pageModulesId) {
        this.pageModulesId = pageModulesId;
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
}
