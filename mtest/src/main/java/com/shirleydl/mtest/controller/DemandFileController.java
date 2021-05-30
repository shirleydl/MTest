/**
 * @description 需求-文件
 * @author 371683941@qq.com
 * @date 2021/5/10
 */
package com.shirleydl.mtest.controller;

import com.shirleydl.mtest.entity.DemandFile;
import com.shirleydl.mtest.method.CheckFileUtil;
import com.shirleydl.mtest.method.RootPath;
import com.shirleydl.mtest.method.XMindUtils;
import com.shirleydl.mtest.service.IDemandFileService;
import com.shirleydl.mtest.vo.ResponseVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/demandFile")
public class DemandFileController {
    @Autowired
    private IDemandFileService demandFileService;

    /**
     * 需求用例文件详情
     *
     * @param demandId
     * @return
     */
    @GetMapping(value = "/case")
    public DemandFile queryCaseFile(@RequestParam("demandId") int demandId) {
        return demandFileService.queryCaseFileByDemandId(demandId);
    }

    /**
     * 需求文件列表
     *
     * @param demandId
     * @return
     */
    @GetMapping(value = "/list")
    public List<DemandFile> queryDemandFileList(@RequestParam("demandId") int demandId) {
        return demandFileService.queryDemandFileList(demandId);
    }

    /**
     * 需求文件详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/content")
    public DemandFile queryDemandFile(@RequestParam("id") int id) {
        return demandFileService.getById(id);
    }

    /**
     * 删除需求文件
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    public ResponseVO deleteDemandFile(@RequestParam("id") int id) {
        DemandFile demandFile = demandFileService.queryDemandFileInfo(id);
        if (demandFileService.removeById(id)) {
            File file = new File(demandFile.getFileUrl());
            if (file.exists()) {
                file.delete();
            }
            return ResponseVO.success("删除成功");
        }
        return ResponseVO.failure("删除失败，请检查数据是否正确！");
    }

    /**
     * 删除需求全部文件
     *
     * @param demandId
     * @return
     */
    @GetMapping(value = "/deleteAllByDemandId")
    public ResponseVO deleteAllByDemandId(@RequestParam("demandId") int demandId) {
        if (demandFileService.deleteDemandFileByDemandId(demandId) > 0) {
            FileSystemUtils.deleteRecursively(new File("doc/" + demandId));
            return ResponseVO.success("全部删除成功");
        }
        return ResponseVO.failure("全部删除失败，请检查数据是否正确！");
    }

    /**
     * 上传需求文件
     *
     * @param file
     * @param demandId
     * @param isCase
     * @return
     */
    @PostMapping(value = "/upload")
    public ResponseVO upload(@RequestParam("file") MultipartFile file, int demandId, int isCase) {
        //获取文件类型
        Integer type = new CheckFileUtil().getFileType(file);
        if (type == null) {
            return ResponseVO.failure("上传失败，请检查数据是否正确！");
        }

        DemandFile demandFile;
        // 用例文件上传先删除旧用例文件
        if (isCase == 1) {
            if (type != 2) {
                return ResponseVO.failure("用例文件暂不支持该文件类型！");
            }
            demandFile = demandFileService.queryCaseFileInfoByDemandId(demandId);
            if (demandFile != null) {
                File caseFile = new File(RootPath.getPath(), demandFile.getFileUrl());
                if (caseFile.exists()) {
                    caseFile.delete();
                } else {
                    return ResponseVO.failure("旧用例文件查找失败，请检查文件路径！");
                }
            } else {
                demandFile = new DemandFile();
            }
        } else {
            demandFile = new DemandFile();
        }

        String fileName = file.getOriginalFilename();
        demandFile.setFileName(fileName);
        demandFile.setDemandId(demandId);
        demandFile.setIsCase(isCase);
        demandFile.setType(type);
        demandFile.setFileUrl("doc/" + demandId + "/" + fileName);

        if (type == 2) {
            // 解析xmind，换成kityminder json格式存储
            demandFile.setContent(new XMindUtils().readXMindToKityMinderJson(file));
        }

        if (demandFileService.saveOrUpdate(demandFile)) {
            //写入文件
            try {
                File dest = new File(RootPath.getPath(), demandFile.getFileUrl());
                FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseVO.failure("数据创建后上传失败！");
            }
            return ResponseVO.success("上传成功");
        }
        return ResponseVO.failure("上传失败，请检查数据是否正确！");
    }

    /**
     * 下载需求文件
     *
     * @param id
     * @param response
     */
    @GetMapping(value = "/download")
    public void download(@RequestParam("id") int id, HttpServletResponse response) {
        DemandFile demandFile = demandFileService.queryDemandFileInfo(id);
        if (demandFile != null) {
            try (InputStream inputStream = new FileInputStream(new File(RootPath.getPath(), demandFile.getFileUrl()));
                 OutputStream outputStream = response.getOutputStream()) {
                String fileName = URLEncoder.encode(demandFile.getFileName(), "UTF-8");
                response.setContentType("application/x-download");
                response.setCharacterEncoding("utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                IOUtils.copy(inputStream, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
