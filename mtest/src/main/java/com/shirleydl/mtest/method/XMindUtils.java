/**
 * @description TODO
 * @author 371683941@qq.com
 * @date 2021/4/26
 */
package com.shirleydl.mtest.method;

import com.alibaba.fastjson.JSON;
import com.shirleydl.mtest.vo.ExportInfo;
import com.shirleydl.mtest.vo.KityMinderData;
import com.shirleydl.mtest.vo.KityMinderRoot;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xmind.core.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * xmind解析工具类
 */
public class XMindUtils {

    /**
     * 读入xmind文件，解析后返回导入信息
     * @param mfile
     * @return
     */
    public List<ExportInfo> readXMindToExportInfo(MultipartFile mfile) {
        List<ExportInfo> list = new ArrayList<>();
        //检查文件
        Integer type = new CheckFileUtil().getFileType(mfile);
        if (type != 2) {
            return list;
        }

        try {
            File file = new File(mfile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(mfile.getInputStream(), file);
            IWorkbookBuilder builder = Core.getWorkbookBuilder();//初始化builder
            IWorkbook workbook = builder.loadFromFile(file);
            if (file.exists()) {
                file.delete();
            }
            ISheet defSheet = workbook.getPrimarySheet();//获取主Sheet
            ITopic rootTopic = defSheet.getRootTopic(); // 获取根Topic
            List<ITopic> systemsChild = rootTopic.getAllChildren();
            for (int systemsIndex = 0; systemsIndex < systemsChild.size(); systemsIndex++) {
                ITopic systemsTopic = systemsChild.get(systemsIndex);
                for (int pageModulesIndex = 0; pageModulesIndex < systemsTopic.getAllChildren().size(); pageModulesIndex++) {
                    ITopic pageModulesTopic = systemsTopic.getAllChildren().get(pageModulesIndex);
                    for (int functionsIndex = 0; functionsIndex < pageModulesTopic.getAllChildren().size(); functionsIndex++) {
                        ITopic functionsTopic = pageModulesTopic.getAllChildren().get(functionsIndex);
                        for (int testPointIndex = 0; testPointIndex < functionsTopic.getAllChildren().size(); testPointIndex++) {
                            ITopic testPointTopic = functionsTopic.getAllChildren().get(testPointIndex);
                            for (int testPreIndex = 0; testPreIndex < testPointTopic.getAllChildren().size(); testPreIndex++) {
                                ITopic testPreTopic = testPointTopic.getAllChildren().get(testPreIndex);
                                if (testPreTopic.getAllChildren().size() > 0) {
                                    for (int testStepIndex = 0; testStepIndex < testPreTopic.getAllChildren().size(); testStepIndex++) {
                                        ITopic testStepTopic = testPreTopic.getAllChildren().get(testStepIndex);
                                        ExportInfo exportInfo = new ExportInfo();
                                        exportInfo.setSystemsName(systemsTopic.getTitleText());
                                        exportInfo.setPageModulesName(pageModulesTopic.getTitleText());
                                        exportInfo.setFunctionsName(functionsTopic.getTitleText());
                                        exportInfo.setTestPointName(testPointTopic.getTitleText());
                                        if (testStepTopic.getAllChildren().size() > 0) {
                                            exportInfo.setPre(testPreTopic.getTitleText());
                                            exportInfo.setStep(testStepTopic.getTitleText());
                                            ITopic testExpectTopic = testStepTopic.getAllChildren().get(0);
                                            exportInfo.setExpect(testExpectTopic.getTitleText());
                                        } else {
                                            exportInfo.setStep(testPreTopic.getTitleText());
                                            exportInfo.setExpect(testStepTopic.getTitleText());
                                        }
                                        list.add(exportInfo);
                                    }
                                } else {
                                    ExportInfo exportInfo = new ExportInfo();
                                    exportInfo.setSystemsName(systemsTopic.getTitleText());
                                    exportInfo.setPageModulesName(pageModulesTopic.getTitleText());
                                    exportInfo.setFunctionsName(functionsTopic.getTitleText());
                                    exportInfo.setTestPointName(testPointTopic.getTitleText());
                                    exportInfo.setStep(testPreTopic.getTitleText());
                                    list.add(exportInfo);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读入xmind文件，解析后返回kityMinder的json格式信息
     * @param mfile
     * @return
     */
    public String readXMindToKityMinderJson(MultipartFile mfile) {
        KityMinderRoot kityMinderRoot = new KityMinderRoot();
        //检查文件
        Integer type = new CheckFileUtil().getFileType(mfile);
        if (type != 2) {
            JSON.toJSON(kityMinderRoot).toString();
        }
        try {
            File file = new File(mfile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(mfile.getInputStream(), file);
            IWorkbookBuilder builder = Core.getWorkbookBuilder();//初始化builder
            IWorkbook workbook = builder.loadFromFile(file);
            if (file.exists()) {
                file.delete();
            }
            ISheet defSheet = workbook.getPrimarySheet();//获取主Sheet
            ITopic rootTopic = defSheet.getRootTopic(); // 获取根Topic
            KityMinderData data = new KityMinderData();
            data.setText(rootTopic.getTitleText());
            kityMinderRoot.setData(data);
            kityMinderRoot.setChildren(loop(rootTopic.getAllChildren()));
            System.out.println(JSON.toJSON(kityMinderRoot));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return JSON.toJSON(kityMinderRoot).toString();
    }

    private List<KityMinderRoot> loop(List<ITopic> topicList) {
        List<KityMinderRoot> kityMinderChildrenList = new ArrayList<>();

        for (ITopic topic : topicList) {
            KityMinderRoot kityMinderRoot = new KityMinderRoot();
            KityMinderData data = new KityMinderData();
            data.setText(topic.getTitleText());
            kityMinderRoot.setData(data);
            if (topic.getAllChildren() != null) {
                kityMinderRoot.setChildren(loop(topic.getAllChildren()));
            }
            kityMinderChildrenList.add(kityMinderRoot);
        }

        return kityMinderChildrenList;
    }


}
