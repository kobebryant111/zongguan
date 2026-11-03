<template>
    <div class="wrapper">
        <!-- 表格部分 -->
        <el-card>
            <el-table :data="tableData" class="margin_bottom">
                <el-table-column prop="name" label="考勤机名称"></el-table-column>
                <el-table-column prop="type" label="是否下发">
                    <template slot-scope="scope">
                        {{ scope.row.type === '0' ? '否' : '是' }}
                    </template>
                </el-table-column>
                <el-table-column prop="deviceip" label="IP"></el-table-column>
                <el-table-column prop="deviceport" label="PORT"></el-table-column>
                <el-table-column prop="deviceaddress" label="考勤机位置"></el-table-column>
                <el-table-column prop="deleted" label="是否可用">
                    <template slot-scope="scope">
                        {{ scope.row.deleted === '0' ? '是' : '否' }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <!-- <el-link type="warning" :underline="false" @click="uploadPhoto(scope.row)">同步</el-link> -->
                        <el-link type="warning" :underline="false" @click="toFix(scope.row)">修改</el-link>
                        <!-- <el-link type="warning" :underline="false" @click="toDelete(scope.row)">删除</el-link> -->
                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <div class="pager_zb">
                <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
            </div>
            <!-- 修改dialog -->
            <el-dialog
                :visible.sync="dislogVisible"
                :before-close="handleClose"
            >
               <el-form label-width="100px" :model="formData" :rules="rules" ref="formRef">
                    <el-form-item label="考勤机名称" prop="name">
                        <el-input v-model="formData.name"></el-input>
                    </el-form-item>
                    <el-form-item label="是否下发" prop="type">
                        <el-select v-model="formData.type">
                            <el-option
                                v-for="item in options"
                                :key="item.id"
                                :value="item.value"
                                :label="item.label"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="IP">
                        <el-input disabled v-model="formData.deviceip"></el-input>
                    </el-form-item>
                    <el-form-item label="PORT">
                        <el-input disabled v-model="formData.deviceport"></el-input>
                    </el-form-item>
                    <el-form-item label="考勤机位置">
                        <el-input disabled v-model="formData.deviceaddress"></el-input>
                    </el-form-item>
                    <el-form-item label="是否可用">
                        <el-select disabled v-model="formData.deleted">
                            <el-option
                                v-for="item in isOKoptions"
                                :key="item.id"
                                :value="item.value"
                                :label="item.label"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="handleClose">取 消</el-button>
                    <el-button @click="btnOk">确 定</el-button>
                </span>
            </el-dialog>
        </el-card>
    </div>
</template>

<script>
import pager from "@/components/Pager";
import common from "@/api/common";
import { queryFaceDevicePager, updateFaceDevice } from "@/api/kqManager/kqMachineConfig"
export default {
    components: {
        pager
    },
    data() {
        return {
            totalNumber: 1,
            size: 50, // 初始值 默认：50
            index: 1, // 初始值 默认：1
            tableData: [],
            dislogVisible: false,
            formData: {
                name: "",
                ip: "",
                prot: "",
                location: "",
                active: "",
                type: ""
            },
            options: [{
                id: '0',
                label: "否",
                value: '0'
            },
            {
                id: '1',
                label: "是",
                value: '1'
            }],
            isOKoptions: [{
                id: '0',
                label: "是",
                value: '0'
            },
            {
                id: '1',
                label: "否",
                value: '1'
            }],
            rules: {
                name: [
                    {required: true, message: "请输入活动名称", trigger: 'blur'}
                ]
            }
        }
    },
    created() {
        this.init();
    },
    methods: {
        // 页面初始化
        init() {
            let params = {}
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        // 获取数据列表
        getPagerList(params) {
            let data = common.paramsFormat(params);
            let self = this;
            queryFaceDevicePager(data).then(res => {
                console.log(res)
                this.tableData = res.data.results
                this.totalNumber = res.data.total
            })
        },
        // 分页
        getPager: function(size, index) {
            console.log(getPager,'分页')
            let params = {};
            params.size = size;
            params.index = index;
            this.size = size;
            this.index = index;
            this.getPagerList(params);
        },
        // 修改
        toFix(row) {
            console.log(row, 'kpMachineConfigFix');
            this.formData = JSON.parse(JSON.stringify(row));
            this.dislogVisible = true;
        },
        // 确认按钮
        btnOk() {
            console.log('btnOk')
            let params = this.formData;
            let data = common.paramsFormat(params);
            this.$refs.formRef.validate((valid) => {
                console.log(valid)
                if(valid) {
                    updateFaceDevice(data).then(res => {
                    console.log(res)
                    if(res.data.status == 200) {
                        this.$message.success('修改成功')
                        this.handleClose()
                        this.init()
                    } else {
                        this.$message.success('修改失败，请稍后重试')
                    }
                })
                } else {
                    return false
                }
            })
        },
        // 关闭
        handleClose() {
            this.dislogVisible = false;
        },
        // 删除
        // toDelete(row) {
        //     console.log(row,'delete')
        // },
    },
}
</script>

<style scoped>
.wrapper {
    padding: 15px;
}
.margin_bottom {
    margin-bottom: 15px
}
/* 分页组件位置 */
.pager_zb {
    width: 100%;
    display: flex;
    flex-direction: row-reverse;
}
</style>
