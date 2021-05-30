package com.shirleydl.mtest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shirleydl.mtest.entity.DemandFile;

import java.util.List;

public interface IDemandFileService extends IService<DemandFile> {
    List<DemandFile> queryDemandFileList(int demandId);

    int deleteDemandFileByDemandId(int demandId);

    DemandFile queryCaseFileByDemandId(int demandId);

    DemandFile queryCaseFileInfoByDemandId(int demandId);

    DemandFile queryDemandFileInfo(int id);

}
