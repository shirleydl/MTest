<template>
    <div style="float: left;" class="handle-box">
        <el-upload :action="uploadUrl()" multiple name="file" :on-error="handleError" :on-success="handleSuccess" :data="updateData()" :show-file-list="false">
            <el-button type="primary" size="mini">上传文件</el-button>
        </el-upload>
    </div>
    <div style="float: left;">
        <el-button type="primary" size="mini" @click="download">下载文件</el-button>
    </div>
    <div style="float: right;">
    <el-button type="primary" size="mini" @click="close">关闭</el-button>
        <el-button type="primary" size="mini" @click="big">放大</el-button>
        <el-button type="primary" size="mini" @click="small">缩小</el-button>
    </div>
    <div>
        <div style="width: 100%; height: 800px" id="minder-container"></div>
    </div>
</template>
<script>
import kity from "kity";
import kityminder from "kityminder-core";
import { fetchDemandFileData } from "../api/index";
import { uploadDemandFileUrl } from "../api/index";
import { downloadDemandFileUrl } from "../api/index";

export default {
    data() {
        return {
            demandFile: {
                id: '',
                demandId: '',
                fileName: '',
                fileUrl: '',
                type: '',
                isCase: '',
                content: ''
            },
            demandId: this.$route.query.demandId
        };
    },
    created() {
        this.getDemandFileData();
    },
    methods: {
        uploadUrl() {
            return uploadDemandFileUrl;
        },
        updateData() {
            let updateData = {
                demandId: this.demandId,
                isCase: 1
            }
            return updateData;
        },
        getDemandFileData() {
            let data = { demandId: this.demandId };
            fetchDemandFileData(data).then(res => {
                this.demandFile = res;
                this.naotu();
            });
        },
        upload() {
            let data = { id: this.demandId };
            fetchDemandDetailData(data).then(res => {
                this.demandFile = res;
            });
        },
        download() {
            let data = { id: this.demandId };
            fetchDemandFileData(data).then(res => {
                this.demandFile = res;
            });
        },
        naotu() {
            this.km = new window.kityminder.Minder({
                renderTo: "#minder-container",
            });
            if (this.demandFile.content != null) {
                this.km.importJson(JSON.parse(this.demandFile.content))
            }
        },
        big() {
            this.km.execCommand('ZoomIn')
        },
        small() {
            this.km.execCommand('ZoomOut')
        },
        handleError() {
            this.$notify.error({
                title: "导入失败",
                message: "请检查导入数据或联系管理员"
            });
        },
        handleSuccess(res) {
            if (res.code !== 200) {
                this.handleError();

            } else {
                this.$notify.success({
                    title: "导入成功",
                    message: "导入数据成功"
                });
                this.getDemandFileData();
            }

        },
        download() {
            window.location.href = downloadDemandFileUrl + "?id=" + this.demandFile.id;
        }

    },
};
</script>
<style scoped>
.handle-box {
    margin-bottom: 5px;
    margin-right: 10px;
}

.handle-span {
    word-break: normal;
    width: auto;
    display: block;
    white-space: pre-wrap;
    word-wrap: break-word;
    overflow: hidden;
    margin-bottom: 5px;
}
</style>