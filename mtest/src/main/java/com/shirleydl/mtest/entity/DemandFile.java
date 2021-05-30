/**
 * @description 需求-文件
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class DemandFile implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;
    protected Integer demandId;
    protected String fileName;
    protected String content;
    protected String fileUrl;
    protected Integer type; //0: 其他文件；1：xmind 2：图片
    protected Integer isCase;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsCase() {
        return isCase;
    }

    public void setIsCase(Integer isCase) {
        this.isCase = isCase;
    }
}
