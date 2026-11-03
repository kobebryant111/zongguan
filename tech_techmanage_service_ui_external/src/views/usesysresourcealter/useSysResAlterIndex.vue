<!-- 流程通知管理 -->
<template>
    <div class="app-container">
        <div class="app-main-container">
            <el-form :model="form" ref="form" inline label-width="120px">
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="流程编号：" prop="priceReqNo">
                            <el-input v-model="form.flowno" clearable size="mini" style="width: 100%"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="应用系统名称：" prop="appName">
                            <el-input v-model="form.appsysname" clearable size="mini" style="width: 100%"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="创建时间：" prop="rangeDate">
                            <el-date-picker v-model="form.rangeDate" type="date" size="mini" placeholder="选择日期" style="width:60%" value-format="yyyy-MM-dd">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="流程状态：" prop="status">
                            <el-select placeholder="请选择" v-model="form.status" clearable size="mini">
                                <el-option v-for="item in statusData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-button icon="el-icon-refresh" @click="onReset" size="mini">重置</el-button>
                        <el-button icon="el-icon-search" type="primary" @click="onSearch" size="mini">搜索</el-button>
                    </el-col>
                    <el-col :span="9">
                    </el-col>
                </el-row>
            </el-form>

            <el-button-group style="margin-bottom:15px">
                <el-button type="success" size="mini" @click="openFlowPage">发起流程</el-button>
            </el-button-group>
            <el-table :data="tempData" :height="tableHeight" border stripe style="width:100%;">
                <el-table-column type="index" align="left" label="序号" width="50"></el-table-column>
                <el-table-column prop="id" label="主键" v-if="show" align="left"></el-table-column>
                <el-table-column prop="processInstID" label="流程实例ID" v-if="show" align="left"></el-table-column>
                <el-table-column prop="flowno" label="流程编号" align="left" width="150"></el-table-column>
                <el-table-column prop="appsysname" label="应用系统" align="left"></el-table-column>
                <el-table-column prop="activityname" label="流程节点" align="left" width="150"></el-table-column>
                <el-table-column prop="status" label="状态" align="center" width="100">
                    <template slot-scope="scope">
                        <span v-for="item of statusData" :key="item.dictid">
                            <span v-if="item.dictid == scope.row.status">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="createtime" label="创建时间" align="left" :formatter="createtimeFormat"></el-table-column>
                <el-table-column label="操作" width="200" align="left">
                    <template slot-scope="scope">
                        <el-button type="primary" v-if="scope.row.status=='1'" size="mini" @click="onUpdate(scope.row)">编 辑</el-button>
                        <el-button type="primary" v-if="scope.row.status!='1'" size="mini" @click="viewResouce(scope.row)">查 看</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs" />
        </div>
        <use-sys-res-alter-flow-yb ref="useSysResAlterFlowYb"></use-sys-res-alter-flow-yb>
        <use-sys-res-alter-flow ref="useSysResAlterFlow"></use-sys-res-alter-flow>
    </div>
</template>
<script>
import cleanBtn from '@/components/CleanBtn'
import common from '@/api/common'
import pager from '@/components/Pager'
import { getDicEntry } from '@/api/eosDictEntry'
import { queryResUseSysList } from '@/api/usesysresourcealter/usesysresourcealter'
import useSysResAlterFlowYb from '@/views/usesysresourcealter/useSysResAlterFlowYb'
import useSysResAlterFlow from '@/views/usesysresourcealter/useSysResAlterFlow'
import { Alert } from 'element-ui'
export default {
    components: { cleanBtn, pager, useSysResAlterFlow, useSysResAlterFlowYb },
    data() {
        return {
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            page: {
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },
            form: {
                flowno: '',
                appsysname: '',
                rangeDate: '',
                status: '',
                creator: ''
            },
            show: false,
            tempData: [], // 集合数据
            visibleClose: false, // dialog 默认关闭
            statusData: [],
            statusData1: []
        }
    },
    created() {
        this.getDictData()
        let params = this.form
        params.size = this.page.size
        params.index = this.page.index
        this.init(params)
    },
    mounted() {
        //表格高度重新设置
        window.onresize = () => {
            return (() => {
                this.tableHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205
            })()
        }
    },
    methods: {
        /**
         * 初始化
         */
        init(param) { 
            let data = common.paramsFormat(param)
            queryResUseSysList(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.tempData = res.data.results
                this.page.totalNumber = res.data.total
            })
        }, // 初始化 end

        /**
         * 分页触发
         */
        getPager: function (size, index) {
            var param = this.form
            param.size = size
            param.index = index
            this.page.size = size
            this.page.index = index
            this.init(param)
        }, // 分页触发 end
        /**
         * 搜索
         */
        onSearch: function () {
            let params = this.form
            params.size = this.page.size
            params.index = 1
            this.page.index = 1
            this.init(params)
        },

        // 查询业务字典
        getDictData() {
            getDicEntry({
                dicttypeid: 'RES_STATUS'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //状态
                this.statusData1 = data.filter((item) => {
                    return item.dicttypeid == 'RES_STATUS'
                })
                this.statusData = this.statusData1.filter((item) => {
                    return item.dictid != '2'
                })
                return data
            })
        },

        //编辑
        onUpdate(row) {
            this.$nextTick(() => {
                this.$refs.useSysResAlterFlow.init(row.id, row.flowno)
            })
        },
        openFlowPage() {
            this.$nextTick(() => {
                this.$refs.useSysResAlterFlow.init()
            })
        },
        /**
         * 查询条件清除
         */
        onReset() {
            //this.$refs['form'].resetFields()
            Object.keys(this.form).forEach((key) => (this.form[key] = ''))
        },
        createtimeFormat: function (row, cloumn) {
            let time = common.dateFormat(row.createtime, 'yyyy-MM-dd HH:mm:ss')
            return time
        },
        //刷新页面
        afterSave() {
            this.$message.success('操作成功')
            this.onSearch()
        },
        //查看
        viewResouce(row) { 
            this.$nextTick(() => {
                this.$refs.useSysResAlterFlowYb.init(row)
            })
        }
    }
}
</script>
<style lang="scss">
.el-form-item {
    margin-bottom: 5px;
}
</style>

