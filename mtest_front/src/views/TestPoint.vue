<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.systemsName" placeholder="项目系统名" class="handle-input mr10"></el-input>
                <el-input v-model="query.pageModulesName" placeholder="页面模块名" class="handle-input mr10"></el-input>
                <el-input v-model="query.functionsName" placeholder="功能名" class="handle-input mr10"></el-input>
                <el-input v-model="query.name" placeholder="测试点" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header" @selection-change="handleSelectionChange" @expand-change="getContentData" :row-key="getRowKeys" :expand-row-keys="expands">
                <el-table-column type="expand">
                    <div>
                        <el-table :data="contentData" border class="table" header-cell-class-name="table-header" height="500">
                            <el-table-column prop="pre" label="前置">
                                <template #default="scope2">
                                    <el-input v-if=" isEdit == scope2.$index " type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope2.row.pre"></el-input>
                                    <span v-if=" isEdit != scope2.$index ">{{ scope2.row.pre }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="step" label="步骤">
                                <template #default="scope2">
                                    <el-input v-if=" isEdit == scope2.$index " type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope2.row.step"></el-input>
                                    <span v-if=" isEdit != scope2.$index ">{{ scope2.row.step }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="expect" label="期望">
                                <template #default="scope2">
                                    <el-input v-if=" isEdit == scope2.$index " type="textarea" :autosize="{ minRows: 1, maxRows: 4}" v-model="scope2.row.expect"></el-input>
                                    <span v-if=" isEdit != scope2.$index ">{{ scope2.row.expect }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" width="180" align="center">
                                <template #default="scope2">
                                    <el-button v-if=" isEdit != scope2.$index " type="text" icon="el-icon-edit" @click="handleContentEdit(scope2.$index, scope2.row)">编辑</el-button>
                                    <el-button v-if=" isEdit != scope2.$index " type="text" icon="el-icon-delete" class="red" @click="handleContentDelete(scope2.$index, scope2.row)">删除</el-button>
                                    <el-button v-if=" isEdit == scope2.$index " type="text" icon="el-icon-edit" @click="saveContentEdit(scope2.$index, scope2.row)">保存</el-button>
                                    <el-button v-if=" isEdit == scope2.$index " type="text" icon="el-icon-edit" class="red" @click="cancelContentEdit()">取消</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-table-column>
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="systemsName" label="项目系统名"></el-table-column>
                <el-table-column prop="pageModulesName" label="页面模块名"></el-table-column>
                <el-table-column prop="functionsName" label="功能名"></el-table-column>
                <el-table-column prop="name" label="测试点"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex" :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="测试点ID">
                    <el-input v-model="form.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="项目系统名">
                    <el-input v-model="form.systemsName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="页面模块名">
                    <el-input v-model="form.pageModulesName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="功能名">
                    <input type="hidden" name="form.functionsId" :vaule="form.functionsId">
                    <el-input v-model="form.functionsName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="测试点">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
import { fetchTestPointData } from "../api/index";
import { updateTestPointData } from "../api/index";
import { deleteTestPointData } from "../api/index";
import { fetchTestContentData } from "../api/index";
import { updateTestContentData } from "../api/index";
import { deleteTestContentData } from "../api/index";

export default {
    name: "pageMoudles",
    data() {
        return {
            query: {
                name: "",
                functionsName: "",
                pageModulesName: "",
                systemsName: "",
                pageIndex: 1,
                pageSize: 15
            },
            tableData: [],
            contentData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            editContentVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,
            isEdit: -1,
            expands: [],
            getRowKeys(row) {
                return row.id;
            },
            getContentKeys(row) {
                return row.testPointId;
            }
        };
    },
    created() {
        this.getData();
    },
    methods: {
        getData() {
            fetchTestPointData(this.query).then(res => {
                this.tableData = res.data;
                this.pageTotal = res.total || 50;
            });
        },
        async getContentData(data) {
            if (this.expands.indexOf(data.id) < 0) {
                this.expands = [];
                this.isEdit = -1;
                this.expands.push(data.id);
                let testPointId = { testPointId: data.id };
                fetchTestContentData(testPointId).then(res => {
                    this.contentData = res;
                });
            }
        },
        // 触发搜索按钮
        handleSearch() {
            this.query.pageIndex = 1;
            this.getData();
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm("确定要删除测试点以及包含的测试步骤吗？", "提示", {
                    type: "warning"
                })
                .then(() => {
                    let data = { id: row.id };
                    deleteTestPointData(data).then(res => {
                        if (res.code === 200) {
                            this.$message.success(res.message);
                            this.tableData.splice(index, 1);
                        } else {
                            this.$message.error(res.message);
                        }

                    });

                })
                .catch(() => {});
        },
        // 删除内容操作
        handleContentDelete(index, row) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                    type: "warning"
                })
                .then(() => {
                    let data = { id: row.id };
                    deleteTestContentData(data).then(res => {
                        if (res.code === 200) {
                            this.$message.success(res.message);
                            this.contentData.splice(index, 1);
                        } else {
                            this.$message.error(res.message);
                        }

                    });

                })
                .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = "";
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + " ";
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 编辑内容操作
        handleContentEdit(index) {
            if (this.isEdit == -1) {
                this.isEdit = index;
            } else {
                this.$message.error("请保存或取消当前编辑内容！");
            }

        },
        // 保存编辑
        saveEdit() {
            updateTestPointData(this.form).then(res => {
                if (res.code === 200) {
                    this.editVisible = false;
                    this.$message.success(res.message);
                } else {
                    this.$message.error(res.message);
                }

            });
        },
        // 保存步骤内容编辑
        saveContentEdit(index, row) {
            updateTestContentData(row).then(res => {
                if (res.code === 200) {
                    this.isEdit = -1;
                    this.$message.success(res.message);
                } else {
                    this.$message.error(res.message);
                }

            });
        },
        // 取消步骤内容编辑
        cancelContentEdit() {
            this.isEdit = -1
        },
        // 分页导航
        handlePageChange(val) {
            this.query.pageIndex = val;
            this.getData();
            //this.$set(this.query, "pageIndex", val);
        }
    }
};
</script>
<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
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
    height: 40px;
}
</style>