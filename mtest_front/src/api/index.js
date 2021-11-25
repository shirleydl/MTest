import request from '../utils/request';
import {baseUrl} from '../utils/request';

export const fetchSystemsData = query => {
    return request({
        url: '/systems/list',
        method: 'get',
        params: query
    });
};

export const updateSystemsData= data => {
    return request({
        url: '/systems/update',
        method: 'post',
        data: data
    });
};

export const deleteSystemsData= query => {
    return request({
        url: '/systems/delete',
        method: 'get',
        params: query
    });
};

export const fetchPageModulesData = query => {
    return request({
        url: '/pageModules/list',
        method: 'get',
        params: query
    });
};


export const updatePageModulesData= data => {
    return request({
        url: '/pageModules/update',
        method: 'post',
        data: data
    });
};

export const deletePageModulesData= query => {
    return request({
        url: '/pageModules/delete',
        method: 'get',
        params: query
    });
};

export const fetchFunctionsData = query => {
    return request({
        url: '/functions/list',
        method: 'get',
        params: query
    });
};

export const updateFunctionsData= data => {
    return request({
        url: '/functions/update',
        method: 'post',
        data: data
    });
};

export const deleteFunctionsData= query => {
    return request({
        url: '/functions/delete',
        method: 'get',
        params: query
    });
};

export const fetchTestPointData = query => {
    return request({
        url: '/testPoint/list',
        method: 'get',
        params: query
    });
};

export const updateTestPointData= data => {
    return request({
        url: '/testPoint/update',
        method: 'post',
        data: data
    });
};

export const deleteTestPointData= query => {
    return request({
        url: '/testPoint/delete',
        method: 'get',
        params: query
    });
};

export const fetchTestContentData = query => {
    return request({
        url: '/testPoint/content',
        method: 'get',
        params: query
    });
};

export const updateTestContentData= data => {
    return request({
        url: '/testPoint/updateContent',
        method: 'post',
        data: data
    });
};

export const deleteTestContentData= query => {
    return request({
        url: '/testPoint/deleteContent',
        method: 'get',
        params: query
    });
};

export const fetchDemandData = query => {
    return request({
        url: '/demand/list',
        method: 'get',
        params: query
    });
};

export const updateDemandData= data => {
    return request({
        url: '/demand/update',
        method: 'post',
        data: data
    });
};

export const deleteDemandData= query => {
    return request({
        url: '/demand/delete',
        method: 'get',
        params: query
    });
};


export const addDemandData= data => {
    return request({
        url: '/demand/add',
        method: 'post',
        data: data
    });
};

export const fetchDemandDetailData = query => {
    return request({
        url: '/demand/detail',
        method: 'get',
        params: query
    });
};

export const fetchTestCaseData = query => {
    return request({
        url: '/testCase/list',
        method: 'get',
        params: query
    });
};

export const addOrUpdateTestCaseData = data => {
    return request({
        url: '/testCase/addOrUpdate',
        method: 'post',
        data: data
    });
};

export const deleteTestCaseData = query => {
    return request({
        url: '/testCase/delete',
        method: 'get',
        params: query
    });
};


export const addOrUpdateBatchTestCaseData = data => {
    return request({
        url: '/testCase/addOrUpdateBatch',
        method: 'post',
        data: data
    });
};


export const deleteTestCaseDataByDemandId = query => {
    return request({
        url: '/testCase/deleteByDemandId',
        method: 'get',
        params: query
    });
};


export const deleteBatchTestCaseData = data => {
    return request({
        url: '/testCase/deleteBatch',
        method: 'post',
        data: data
    });
};


export const exportExcel = query => {
    return request({
        url: '/testCase/exportExcel',
        method: 'get',
        params: query
    });
};


export const fetchDemandFileData = query => {
    return request({
        url: '/demandFile/case',
        method: 'get',
        params: query
    });
};


export const importCaseLibrary = query => {
    return request({
        url: '/caseLibrary/importByTestCase',
        method: 'post',
        params: query
    });
};

export const exportExcelUrl = baseUrl+'/testCase/exportExcel';

export const uploadUrl = baseUrl+'/caseLibrary/upload';

export const exportFileToTestCaseUrl = baseUrl+'/testCase/exportFile';

export const uploadDemandFileUrl = baseUrl+'/demandFile/upload';

export const downloadDemandFileUrl = baseUrl+'/demandFile/download';

export const fetchData = query => {
    return request({
        url: './table.json',
        method: 'get',
        params: query
    });
};
