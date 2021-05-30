<template>
    <div>
        <div class="container">
            <el-upload
                class="upload-demo"
                drag
                :action="uploadUrl()"
                name="file"
                :multiple="multiple"
                :on-success="handleSuccess"
                :on-error="handleError"
            >
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                    将文件拖到此处，或
                    <em>点击上传excel/xmind8</em>
                </div>
                <template #tip>

                    <div class="el-upload__tip"> 
                    <el-link href="caseTemplate.xltx" download="测试用例模板.xltx"><el-button type="primary">下载Excel用例模板</el-button></el-link>
                    
                     <el-link href="caseTemplate.xmind" download="测试用例模板.xmind" style="margin-left:20px"><el-button type="primary" >下载XMind用例模板</el-button></el-link>
                    </div>
                    
                     <div class="el-upload__tip">推荐使用excel，xmind导入只支持xmind8以下旧版</div>
                </template>
            </el-upload>
        </div>
        
    </div>
</template>

<script>
import "cropperjs/dist/cropper.css";
import {uploadUrl} from "../api/index";
export default {
    name: "uploadData",
    fileName: "测试用例模板.xltx",
    data() {
        return {
            query: {
                    fileName: ""
                },
            defaultSrc: require("../assets/img/img.jpg"),
            fileList: [],
            imgSrc: "",
            cropImg: "",
            dialogVisible: false
        };
    },
    methods: {
        uploadUrl(){
            return uploadUrl
        },
        setImage(e) {
            const file = e.target.files[0];
            if (!file.type.includes("image/")) {
                return;
            }
            const reader = new FileReader();
            reader.onload = event => {
                this.dialogVisible = true;
                this.imgSrc = event.target.result;
                this.$refs.cropper &&
                    this.$refs.cropper.replace(event.target.result);
            };
            reader.readAsDataURL(file);
        },
        cropImage() {
            this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
        },
        cancelCrop() {
            this.dialogVisible = false;
            this.cropImg = this.defaultSrc;
        },
        imageuploaded(res) {
            console.log(res);
        },
        handleError() {
            this.$notify.error({
                title: "导入失败",
                message: "请检查导入数据或联系管理员"
            });
        },
        handleSuccess(res) {
            if(res.code!==200){
                this.handleError();    
            }else{
                this.$notify.success({
                    title: "导入成功",
                    message: "导入数据成功"
                });
            }
        }
    },
    created() {
        this.cropImg = this.defaultSrc;
    }
};
</script>

<style scoped>
.content-title {
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
}

.pre-img {
    width: 100px;
    height: 100px;
    background: #f8f8f8;
    border: 1px solid #eee;
    border-radius: 5px;
}
.crop-demo {
    display: flex;
    align-items: flex-end;
}
.crop-demo-btn {
    position: relative;
    width: 100px;
    height: 40px;
    line-height: 40px;
    padding: 0 20px;
    margin-left: 30px;
    background-color: #409eff;
    color: #fff;
    font-size: 14px;
    border-radius: 4px;
    box-sizing: border-box;
}
.crop-input {
    position: absolute;
    width: 100px;
    height: 40px;
    left: 0;
    top: 0;
    opacity: 0;
    cursor: pointer;
}
</style>