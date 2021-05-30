<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.nameOrTag" placeholder="需求名或标记" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-lx-add" @click="handleAdd" style="float: right;">新增需求</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header" @selection-change="handleSelectionChange">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="需求名"></el-table-column>
                <el-table-column prop="tags" label="Tag"></el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-lx-text" @click="show(scope.$index, scope.row)">用例</el-button>
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
                <el-form-item label="需求ID">
                    <el-input v-model="form.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="需求名">
                    <el-input v-model="form.name" type="textarea" :autosize="{ minRows: 1, maxRows: 4}"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.detail" type="textarea" :autosize="{ minRows: 1, maxRows: 4}"></el-input>
                </el-form-item>
                <el-form-item label="Tag">
                    <el-input v-model="form.tags" type="textarea" :autosize="{ minRows: 1, maxRows: 4}"></el-input>
                </el-form-item>
                <el-form-item label="用例类型">
                    <el-radio-group v-model="form.caseType">
                        <el-radio :label="1">表格用例</el-radio>
                        <el-radio :label="2">文件用例</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 新增弹出框 -->
        <el-dialog title="新增需求" v-model="addVisible" width="30%">
            <el-form ref="addform" :model="addform" label-width="100px">
                <el-form-item label="需求名">
                    <el-input v-model="addform.name" type="textarea" :autosize="{ minRows: 1, maxRows: 4}"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="addform.detail" type="textarea" :autosize="{ minRows: 1, maxRows: 4}"></el-input>
                </el-form-item>
                <el-form-item label="Tag">
                    <el-input v-model="addform.tags" type="textarea" :autosize="{ minRows: 1, maxRows: 4}"></el-input>
                </el-form-item>
                <el-form-item label="用例类型">
                    <el-radio-group v-model="addform.caseType">
                        <el-radio :label="1">表格用例</el-radio>
                        <el-radio :label="2">文件用例</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveAdd">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
import { fetchDemandData } from "../api/index";
import { updateDemandData } from "../api/index";
import { deleteDemandData } from "../api/index";
import { addDemandData } from "../api/index";
export default {
    name: "systems",
    data() {
        return {
            query: {
                nameOrTag: "",
                pageIndex: 1,
                pageSize: 15
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            addVisible: false,
            pageTotal: 0,
            form: {},
            addform: {caseType: 1},
            idx: -1,
            id: -1
        };
    },
    created() {
        this.getData();
    },
    methods: {
        getData() {
            fetchDemandData(this.query).then(res => {
                this.tableData = res.data;
                this.pageTotal = res.total || 50;
            });
        },
        // 触发搜索按钮
        handleSearch() {
            this.query.pageIndex = 1;
            this.getData();
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm("确定要删除及其关联信息吗？", "提示", {
                    type: "warning"
                })
                .then(() => {
                    let data = { id: row.id };
                    deleteDemandData(data).then(res => {
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
        // 查看操作
        show(index, row) {
            if (row.caseType == 2) {
                this.$router.push({
                    path: '/demandMindDetail',
                    query: {
                        demandId: row.id
                    }
                })

            } else {
                this.$router.push({
                    path: '/demandDetail',
                    query: {
                        demandId: row.id
                    }
                })
            }
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            updateDemandData(this.form).then(res => {
                if (res.code === 200) {
                    this.editVisible = false;
                    this.$message.success(res.message);
                } else {
                    this.$message.error(res.message);
                }

            });
        },
        // 新增操作
        handleAdd() {
            this.addVisible = true;
        },
        // 保存新增
        saveAdd() {
            addDemandData(this.addform).then(res => {
                if (res.code === 200) {
                    this.addVisible = false;
                    this.$message.success(res.message);
                    this.getData();
                } else {
                    this.$message.error(res.message);
                }

            });
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