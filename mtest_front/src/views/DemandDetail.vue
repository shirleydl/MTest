<template>
    <div>
        <div class="container">
            <div class="handle-span">
                <span>{{demand.name}}</span>
            </div>
            <div class="handle-span">
                <span>描述：{{demand.detail}}</span>
            </div>
            <div class="handle-span">
                <span>Tag：{{demand.tags}}</span>
            </div>
            <div>
                <div style="float: left;" class="handle-box">
                    <el-upload :action="uploadUrl()" multiple name="file" :on-error="handleError" :on-success="handleSuccess" :data="updateData()" :show-file-list="false">
                        <el-button type="primary" size="mini">导入Excel</el-button>
                    </el-upload>
                </div>
                <div style="float: left;" class="handle-box">
                    <el-button type="primary" @click="exportExcel" size="mini">导出EXCEL</el-button>
                    <el-button type="primary" @click="onSubmit" size="mini">用例库导入</el-button>
                    <el-button type="primary" @click="onSubmit" size="mini">用例入库</el-button>
                </div>
                <div v-if="isEdit === -1" style="float: right;" class="handle-box">
                    <el-button type="primary" @click="editBatch" size="mini">批量编辑</el-button>
                    <el-button type="danger" @click="deleteAll" size="mini">全部删除</el-button>
                </div>
                <div v-if="isEdit === true" style="float: right;" class="handle-box">
                    <el-button type="primary" @click="add" size="mini">新增行</el-button>
                    <el-button type="primary" @click="saveAll" size="mini">保存</el-button>
                    <el-button type="danger" @click="cancelBatch" size="mini">取消</el-button>
                </div>
            </div>
            <div>
                <el-table :data="testCaseData" border class="table" header-cell-class-name="table-header" height="780" :cell-style="{padding:'5px 0'}">
                    <el-table-column prop="systemsName" label="项目系统名" width="110">
                        <template #default="scope">
                            <el-input v-if=" isEdit === scope.$index|| isEdit === true" type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope.row.systemsName"></el-input>
                            <span v-else>{{ scope.row.systemsName}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="pageModulesName" label="页面模块名" width="120">
                        <template #default="scope">
                            <el-input v-if=" isEdit === scope.$index|| isEdit === true" type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope.row.pageModulesName"></el-input>
                            <span v-else>{{ scope.row.pageModulesName}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="functionsName" label="功能名" width="130">
                        <template #default="scope">
                            <el-input v-if=" isEdit === scope.$index|| isEdit === true" type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope.row.functionsName"></el-input>
                            <span v-else>{{ scope.row.functionsName}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="testPointName" label="测试点">
                        <template #default="scope">
                            <el-input v-if=" isEdit === scope.$index|| isEdit === true" type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope.row.testPointName"></el-input>
                            <span v-else>{{ scope.row.testPointName}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="pre" label="前置">
                        <template #default="scope">
                            <el-input v-if=" isEdit === scope.$index||isEdit === true" type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope.row.pre"></el-input>
                            <span v-else>{{ scope.row.pre}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="step" label="步骤">
                        <template #default="scope">
                            <el-input v-if=" isEdit === scope.$index||isEdit === true" type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope.row.step"></el-input>
                            <span v-else>{{ scope.row.step}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="expect" label="期望">
                        <template #default="scope">
                            <el-input v-if="isEdit === scope.$index||isEdit === true" type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope.row.expect"></el-input>
                            <span v-else>{{ scope.row.expect}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150" align="center">
                        <template #default="scope">
                            <div v-if=" isEdit === scope.$index">
                                <!-- 单个编辑模式 -->
                                <el-button type="text" icon="el-icon-edit" @click="saveEdit(scope.$index, scope.row)">保存</el-button>
                                <el-button type="text" icon="el-icon-edit" class="red" @click="cancelEdit()">取消</el-button>
                            </div>
                            <div v-else-if="isEdit === true">
                                <!-- 批量编辑模式 -->
                                <el-button type="text" icon="el-icon-edit" @click="insert(scope.$index)">插入</el-button>
                                <el-button type="text" icon="el-icon-delete" class="red" @click="deleteList(scope.$index, scope.row)">删除</el-button>
                            </div>
                            <div v-else>
                                <!-- 普通模式 -->
                                <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index)">编辑</el-button>
                                <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
import { fetchDemandDetailData } from "../api/index";
import { fetchTestCaseData } from "../api/index";
import { addOrUpdateTestCaseData } from "../api/index";
import { deleteTestCaseData } from "../api/index";
import { addOrUpdateBatchTestCaseData } from "../api/index";
import { deleteTestCaseDataByDemandId } from "../api/index";
import { deleteBatchTestCaseData } from "../api/index";
import { exportFileToTestCaseUrl } from "../api/index";
import { exportExcelUrl } from "../api/index";

export default {
    name: "demandDetail",
    watch:{
     $route(to,from){
     this.$router.go(0)
     }
    },
    data() {
        return {
            demand: {
                name: '',
                detail: '',
                tags: ''
            },
            isEdit: -1,
            demandId: this.$route.query.demandId,
            testCaseData: [],
            isDelId: []
        };
    },
    created() {
        this.getDemandData();
        this.getTestCaseData();
    },
    methods: {
        updateData() {
            let updateData = { demandId: this.demandId }
            return updateData;
        },
        uploadUrl() {
            return exportFileToTestCaseUrl;
        },
        getTestCaseData() {
            let data = { demandId: this.demandId };
            fetchTestCaseData(data).then(res => {
                this.testCaseData = res;
            });
        },
        getDemandData() {
            let data = { id: this.demandId };
            fetchDemandDetailData(data).then(res => {
                this.demand = res;
            });
        },
        // 编辑操作
        handleEdit(index) {
            if (this.isEdit == -1) {
                this.isEdit = index;
            } else {
                this.$message.error("请保存或取消当前编辑内容！");
            }
        },
        // 批量编辑
        editBatch() {
            if (this.testCaseData.length < 31) {
                this.isEdit = true
            } else {
                this.$message.error("批量编辑限制30条以下，超过请使用文件导入或单条编辑！");
            }

        },
        // 取消编辑
        cancelEdit() {
            this.isEdit = -1
        },
        // 取消批量编辑
        cancelBatch() {
            this.isEdit = -1
            this.getTestCaseData();
        },
        // 保存编辑
        saveEdit(index, row) {
            addOrUpdateTestCaseData(row).then(res => {
                if (res.code === 200) {
                    this.isEdit = -1;
                    this.$message.success(res.message);
                } else {
                    this.$message.error(res.message);
                }

            });
        },
        // 保存批量
        saveAll() {
            for (let i = 0; i < this.testCaseData.length; i++) {
                this.testCaseData[i].sortId = i;

            }
            if (this.isDelId.length > 0) {
                deleteBatchTestCaseData(this.isDelId).then(res => {
                    if (res.code === 200) {
                        this.$message.success(res.message);
                    } else {
                        this.$message.error(res.message);
                    }

                });
            }

            addOrUpdateBatchTestCaseData(this.testCaseData).then(res => {
                if (res.code === 200) {
                    this.isEdit = -1;
                    this.$message.success(res.message);
                    this.getTestCaseData();
                } else {
                    this.$message.error(res.message);
                }

            });
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                    type: "warning"
                })
                .then(() => {
                    let data = { id: row.id };
                    deleteTestCaseData(data).then(res => {
                        if (res.code === 200) {
                            this.$message.success(res.message);
                            this.testCaseData.splice(index, 1);
                        } else {
                            this.$message.error(res.message);
                        }

                    });

                })
                .catch(() => {});
        },
        // 全部删除操作
        deleteAll() {
            // 二次确认删除
            this.$confirm("确定要全部删除吗？", "提示", {
                    type: "warning"
                })
                .then(() => {
                    let data = { demandId: this.demandId };
                    deleteTestCaseDataByDemandId(data).then(res => {
                        if (res.code === 200) {
                            this.$message.success(res.message);
                            this.getTestCaseData();
                        } else {
                            this.$message.error(res.message);
                        }

                    });

                })
                .catch(() => {});
        },
        add() {
            let i = this.testCaseData.length;
            let testCase = {
                systemsName: i > 0 ? this.testCaseData[i - 1].systemsName : '',
                pageModulesName: i > 0 ? this.testCaseData[i - 1].pageModulesName : '',
                functionsName: i > 0 ? this.testCaseData[i - 1].functionsName : '',
                testPointName: i > 0 ? this.testCaseData[i - 1].testPointName : '',
                pre: i > 0 ? this.testCaseData[i - 1].pre : '',
                step: '',
                expect: '',
                sortId: '',
                demandId: this.demandId
            }
            this.testCaseData.push(testCase);
        },
        insert(index) {
            let testCase = {
                systemsName: this.testCaseData[index].systemsName,
                pageModulesName: this.testCaseData[index].pageModulesName,
                functionsName: this.testCaseData[index].functionsName,
                testPointName: this.testCaseData[index].testPointName,
                pre: this.testCaseData[index].pre,
                step: '',
                expect: '',
                sortId: '',
                demandId: this.demandId
            }
            this.testCaseData.splice(index, 0, testCase);
        },
        deleteList(index, row) {
            if (row.id != undefined && row.id != 0) {
                this.isDelId.push(row.id);
            }
            this.testCaseData.splice(index, 1);
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
                this.getTestCaseData();
            }

        },
        exportExcel() {
           window.location.href = exportExcelUrl+"?demandId="+this.demandId;
        },
        onSubmit(){
        this.$message.error("唉，功能还没做，别急");
        }

    }
};
</script>
<style scoped>
.handle-box {
    margin-bottom: 1px;
    margin-right: 10px;
}

.handle-span {
    word-break: normal;
    width: auto;
    display: block;
    white-space: pre-wrap;
    word-wrap: break-word;
    overflow: hidden;
    margin-bottom: 8px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
}

.redButton {
    background-color: #ff0000;
}


.red {
    color: #ff0000;
}

.mr10 {
    margin-right: 10px;
}

.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 30px;
}
</style>