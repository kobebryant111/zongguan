<!-- 菜单管理 -->
<template>
    <div>
        <table-tree :data="mData" :columns="tableColumns" :expandAll="true" fit border>
            <!-- <table-tree :data="mData" :columns="tableColumns"   > -->
            <el-table-column prop="routeid" label="前端路由ID" width="100"></el-table-column>
            <el-table-column prop="code" label="权限CODE" width="180"></el-table-column>
            <el-table-column prop="type" label="菜单类型" align="center" width="100">
                <template slot-scope="scope">
                    <el-tag type="info" v-if="scope.row.type == '-1'">
                        根目录
                    </el-tag>
                    <el-tag type="success" v-if="scope.row.type == '0'">
                        一级菜单
                    </el-tag>
                    <el-tag type="primary" v-if="scope.row.type == '1'">
                        二级菜单
                    </el-tag>
                    <el-tag type="warning" v-if="scope.row.type == '2'">
                        子菜单
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="url" label="菜单URL"></el-table-column>
            <el-table-column prop="ordernum" label="排序" width="60"></el-table-column>
            <el-table-column label="操作" width="230">
                <template slot-scope="scope">
                    <!-- <el-button type="primary" size="mini" @click="addMenu(scope.row)" v-if="scope.row.type != '2' && !scope.row.authRel">添加</el-button> -->
                    <el-button type="primary" size="mini" @click="addMenu(scope.row)" v-if="scope.row.type != '2'">添加</el-button>
                    <el-button type="warning" size="mini" @click="updateMenu(scope.row)" v-if="scope.row.type != '-1'">修改</el-button>
                    <el-button type="danger" size="mini" @click="deleteConfirmTip(scope.row)" v-if="scope.row.type != '-1' && scope.row.children == null">删除</el-button>
                </template>
            </el-table-column>
        </table-tree>
        <!-- dialog -->
        <div>
            <template>
                <el-dialog :title="title" :visible.sync="dialogClose" width="30%">
                    <el-form ref="menuForm" :model="menuForm">
                        <table border="0" style="margin:auto;width:100%">
                            <tr>
                                <td class="td-right">上级菜单名称：</td>
                                <td class="td-left">
                                    <el-input v-model="menuForm.parentName" disabled />
                                </td>
                            </tr>
                            <tr>
                                <td class="td-right"><span class="required">*</span>菜单名称：</td>
                                <td class="td-left">
                                    <el-input v-model="menuForm.name" maxlength="30" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td-right">前端路由ID：</td>
                                <td class="td-left">
                                    <el-input v-model="menuForm.routeid" maxlength="50" />
                                </td>
                            </tr>

                            <tr>
                                <td class="td-right">菜单URL：</td>
                                <td class="td-left">
                                    <el-input v-model="menuForm.url" maxlength="200" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td-right">菜单代码：</td>
                                <td class="td-left">
                                    <el-input v-model="menuForm.code" maxlength="20" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td-right"><span class="required">*</span>排序：</td>
                                <td class="td-left">
                                    <el-input v-model="menuForm.ordernum" maxlength="9" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td-right">备注：</td>
                                <td class="td-left">
                                    <el-input type="textarea" v-model="menuForm.readme" maxlength="200" />
                                </td>
                            </tr>
                        </table>
                    </el-form>
                    <div class="dialog-error-div">
                        <samp class="dialog-error">{{error}}</samp>
                    </div>
                    <div class="dialog-footer">
                        <el-button @click="dialogCancel">取消</el-button>
                        <el-button type="primary" @click="dialogAddSubmit">确定</el-button>
                    </div>
                </el-dialog>
            </template>
        </div>
    </div>
</template>
<script>
    import TableTree from '@/components/TableTree'
    import request from '@/utils/request'
    import common from '@/api/common'

    export default {
        components: { TableTree },
        data() {
            return {
                title: '',             // dialog 标题
                mData: [],
                tableColumns: [{
                    text: '菜单名称',
                    value: 'name',
                    width: "250"
                }],
                dialogClose: false,         //dialog 显示
                menuForm: {                // 表单
                    name: '',              // 名称
                    code: '',               // 菜单代码
                    url: '',                // 菜单url
                    type: '',               // 类型
                    ordernum: '',           // 序号
                    readme: '',             // 备注
                    parentName: '',        // 上级菜单名称
                    parentid: '',          // 上级菜单id
                    routeid: '',           // 前端路由id
                    mid: '',                 // id

                },
                error: '',                 // 错误提示     
            }
        },
        mounted() {
            this.init();
        },
        methods: {
            // 页面初始化
            init: function () {
                request({
                    url: '/imMenus/loadMenu',
                    method: 'get',
                    data: {}
                }).then(res => {
                    if (res.data.status != 200) {
                        this.$alter(res.data.message, { type: 'error' });
                        return false;
                    }
                    this.mData = res.data.results;
                    console.log("菜单管理数据", res.data.results);
                })
            },      // 页面初始化 end
            /**
             * 添加菜单
             */
            addMenu: function (item) {
                console.log("添加菜单", item);
                this.title = "添加菜单"
                this.dialogClose = true
                this.menuForm.parentid = item.mid;
                this.menuForm.parentName = item.name;
                this.menuForm.type = item.type + 1;
            },          // 添加菜单 end
            /**
             * 添加菜单
             */
            updateMenu: function (item) {
                this.title = "更新菜单"
                this.dialogClose = true
                this.menuForm.parentName = item.parent.name;
                this.menuForm.name = item.name
                this.menuForm.url = item.url
                this.menuForm.code = item.code
                this.menuForm.ordernum = item.ordernum
                this.menuForm.readme = item.readme
                this.menuForm.mid = item.mid
                this.menuForm.routeid = item.routeid
            },          // 添加菜单 end
            /**
             * 删除 
             */
            deleteMenu: function (item) {
                console.log("菜单删除2", item);
                request({
                    url: '/imMenus/delete?mid=' + item.mid,
                    method: 'delete',
                }).then(res => {
                    if (res.data.status != 200) {
                        this.$alert(res.data.message, { type: 'error' });
                    } else {
                        this.init();
                        this.$alert(res.data.message, { type: 'success' });
                    }
                });
            },  // 删除

            /**
             * 删除提示
             */
            deleteConfirmTip: function (item) {
                this.$confirm('确认删除当前菜单吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // this.deleteMenu(row);
                    this.deleteMenu(item);
                }).catch(() => { })
            },           // 删除提示 end
            /**
             *  提交保存
             */
            dialogAddSubmit: function () {
                let falg = this.vaildateDialogForm(this.menuForm);
                if (!falg) {
                    return false;
                }
                this.error = '';
                let datas = common.paramsFormat(this.menuForm);
                request({
                    url: '/imMenus/update',
                    method: 'post',
                    data: datas
                }).then(res => {
                    if (res.data.status != 200) {
                        this.$alert(res.data.message, { type: 'error' });
                    } else {
                        this.dialogCancel()
                        this.$message({
                            type: 'success',
                            message: res.data.message
                        })
                        this.init();
                    }
                })
            },          // 提交保存 end
            /**
             * 关闭dialog
             */
            dialogCancel: function () {
                this.dialogClose = false;
                Object.keys(this.menuForm).forEach(key => { this.menuForm[key] = '' });
                this.error = '';
            },              // 关闭dialog end
            /**
             * 验证form表单
             */
            vaildateDialogForm: function (object) {
                if (!object.name) {
                    this.error = '* 提示：菜单名称不能为空'
                    return false;
                }
                if (!object.ordernum) {
                    this.error = '* 提示：排序不能为空'
                    return fasle;
                }
                return true;
            },          // 验证form表单 end
        }
    }
</script>
<style>
    .dialog-footer {
        margin-top: 10px;
        text-align: right;
    }

    .td-right {
        text-align: right;
    }

    .td-left {
        text-align: left;
    }

    .dialog-error-div {
        background-color: #f1b1e369;
        text-align: center
    }

    .dialog-error {
        color: red;
        font-size: 2em;
    }

    .required {
        color: red;
    }
</style>