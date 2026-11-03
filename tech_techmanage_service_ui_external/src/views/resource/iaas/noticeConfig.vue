<template>
    <div>
        <el-dialog title="通知设置" :visible.sync="showFlag" center width="65%">
            <el-button icon="el-icon-plus" v-if="isManage" type="primary" @click="showAdd" size="mini">添加</el-button>
            <el-table :data="data" border stripe height="400" style="width:98%;">
                <el-table-column type="index" align="center" label="序号" width="60"></el-table-column>
                <el-table-column prop="user" label="通知人员" align="center" :formatter="showCloumnValue" />
                <el-table-column prop="msg" label="推送短信" align="center" :formatter="showCloumnValue" />
                <el-table-column prop="webchat"  width="150" label="推送手机平台" align="center" :formatter="showCloumnValue" />
                <el-table-column prop="email" label="推送邮件" align="center" :formatter="showCloumnValue" />
                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini" @click="onUpdate(scope.row)">编辑</el-button>
                        <el-button v-if="isManage" type="primary" size="mini" @click="onDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs" />
        </el-dialog>
        <el-dialog :title="addOrEditTitle" :visible.sync="addOrEditFlag" center width="30%">
            <el-form :model="form" ref="form" :rules="formRules" inline label-width="120px">
                <el-row>
                    <el-col :span="24" v-if="isAdd">
                        <el-form-item label="通知人员: " prop="user">
                            <el-input v-model="form.user" placeholder size="mini" readonly>
                                <el-button slot="append" @click="showUserTree()">...</el-button>
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="推送短信: " prop="msg">
                            <el-radio v-model="form.msg" label="1">是</el-radio>
                            <el-radio v-model="form.msg" label="0">否</el-radio>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="推送手机平台: " prop="webchat">
                            <el-radio v-model="form.webchat" label="1">是</el-radio>
                            <el-radio v-model="form.webchat" label="0">否</el-radio>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="推送邮件: " prop="email">
                            <el-radio v-model="form.email" label="1">是</el-radio>
                            <el-radio v-model="form.email" label="0">否</el-radio>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="save" size="mini">确 定</el-button>
                <el-button type="primary" @click="cancel" size="mini">取 消</el-button>
            </div>
        </el-dialog>
        <userSelect ref="userSelect"></userSelect>
    </div>
</template>
<script>
    import common from '@/api/common'
    import pager from '@/components/Pager'
    import userSelect from '@/views/resource/flow/userSelect'
    import { queryPageConfig, saveConfig, deleteConfig, queryList } from '@/api/resource/iaas/notice'
    import { Alert } from 'element-ui'
    export default {
        components: { pager, userSelect },
        props: {
            isManage: {
                // 是否管理员
                type: Boolean
            },
        },
        data() {
            return {
                // 是否新增
                isAdd: true,
                // 新增编辑弹窗标题
                addOrEditTitle: "",
                // 新增编辑弹窗展示标识
                addOrEditFlag: false,
                // 展示标识
                showFlag: false,
                // 通知人员信息
                form: {
                    id: "",
                    user: "",
                    userId: "",
                    userName: "",
                    msg: "1",
                    webchat: "1",
                    email: "1",
                },
                formRules: {
                    user: [
                        { required: true, message: '请选择通知人员', trigger: 'blur' },
                    ],
                    msg: [
                        { required: true, message: '请选择是否推送短信', trigger: 'change' }
                    ],
                    webchat: [
                        { required: true, message: '请选择是否推送手机平台', trigger: 'change' }
                    ],
                    email: [
                        { required: true, message: '请选择是否推送邮件', trigger: 'change' }
                    ],
                },
                // 分页参数
                page: {
                    totalNumber: 0,
                    size: 50, // 初始值 默认：50
                    index: 1 // 初始值 默认：1
                },
                // 列表数据集合
                data: [],
            }
        },
        created() {
            this.init();
        },
        mounted() {
        },
        methods: {
            // 本组件展示状态调整
            changeShowFlag() {
                this.showFlag = !this.showFlag;
                if (this.showFlag) {
                    this.init();
                }
            },
            /**
             * 初始化
             */
            init() {
                let params = {
                    ...this.page
                };
                // 处理管理员
                if (!this.isManage) {
                    params = {
                        ...params,
                        selectCurrentUser: "1",
                    }
                }
                let data = common.paramsFormat(params);
                queryPageConfig(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$alert(res.data.message, { type: 'error' })
                        return false
                    }
                    this.data = res.data.results
                    this.page.totalNumber = res.data.total
                })
            },
            // 列表列内容展示
            showCloumnValue(row, column) {
                if ('user' == column.property) {
                    return row.userName + "(" + row.userId + ")";
                }
                if ('msg' == column.property) {
                    if ('1' == row.msg) {
                        return "是";
                    }
                    return "否";
                }
                if ('webchat' == column.property) {
                    if ('1' == row.webchat) {
                        return "是";
                    }
                    return "否";
                }
                if ('email' == column.property) {
                    if ('1' == row.email) {
                        return "是";
                    }
                    return "否";
                }
                return "";
            },
            // 展示新增配置弹窗
            showAdd() {
                this.addOrEditFlag = !this.addOrEditFlag;
                this.addOrEditTitle = "新增";
                this.isAdd = true;
            },
            // 展示编辑配置弹窗
            onUpdate(row) {
                this.addOrEditFlag = !this.addOrEditFlag;
                this.addOrEditTitle = "编辑";
                this.isAdd = false;
                let data = {
                    id: row.id
                }
                data = common.paramsFormat(data);
                queryList(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$alert(res.data.message, { type: 'error' })
                        return false
                    }
                    this.form = {
                        ...this.form,
                        ...res.data.results[0]
                    }
                })
            },
            // 删除
            onDelete(row) {
                this.$confirm('此操作为永久删除数据，确定继续吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then((res) => {
                    let param = {
                        id: row.id
                    };
                    let data = common.paramsFormat(param)
                    deleteConfig(data).then((res) => {
                        if (res.data.status != 200) {
                            this.$message.error(res.data.message)
                            return false
                        } else {
                            this.$message.success(res.data.message)
                            this.init();
                        }
                    })
                })
            },
            // 分页
            getPager(size, index) {
                this.page.size = size;
                this.page.index = index;
                this.init();
            },
            // 保存/更新
            save() {
                let data = common.paramsFormat(this.form);
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        saveConfig(data).then((res) => {
                            if (res.data.status != 200) {
                                this.$alert(res.data.message, { type: 'error' })
                                return false
                            }
                            this.addOrEditFlag = !this.addOrEditFlag;
                            this.$refs.form.resetFields();
                            this.init();
                        })
                    } else {
                        return false;
                    }
                });
            },
            // 取消
            cancel() {
                this.addOrEditFlag = !this.addOrEditFlag;
                this.$refs.form.resetFields();
            },
            // 显示用户树
            showUserTree(index) {
                this.$refs.userSelect.showUserTree(1);
            },
            // 用户树确定事件
            saveAuditUser(userNo, userName) {
                let index = userName.lastIndexOf("(") == '-1' ? userName.lastIndexOf("[") : userName.lastIndexOf("(");
                if ('-1' != index) {
                    userName = userName.substring(0, index);
                }
                this.form.user = userName;
                this.form.userId = userNo;
                this.form.userName = userName;
            },
        }
    }
</script>
<style lang="scss">
    .el-dialog__header {
        padding: 10px 10px 10px;
    }
</style>