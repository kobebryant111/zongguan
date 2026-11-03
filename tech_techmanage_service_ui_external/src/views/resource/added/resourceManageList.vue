<!-- 资源配置列表界面 -->
<template>
    <div class="app-container">
        <div class="app-main-container">
            <el-form :model="form" ref="form" inline label-width="120px">
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="价格申请单号：" prop="priceReqNo">
                            <el-input v-model="form.priceReqNo" size="mini" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="应用名称：" prop="appName">
                            <el-input v-model="form.appName" size="mini" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="申请时间：" prop="startTime">
                            <el-date-picker v-model="form.rangeDate" type="daterange" size="mini" range-separator="-" style="width:65%" start-placeholder="开始日期" end-placeholder="结束日期" value-format="yyyy-MM-dd">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="项目名称：" prop="priceReqNo">
                            <el-input v-model="form.projectName" size="mini" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="状态：" prop="status">
                            <el-select placeholder="请选择" v-model="form.status" size="mini" clearable>
                                <el-option v-for="item in statusData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="申请人：" prop="creator">
                            <el-input v-model="form.creator" clearable size="mini" style="width:240px"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-form-item>
                            <clean-btn size="mini" :forms="this.form" value="重置" />
                            <el-button icon="el-icon-search" size="mini" type="primary" @click="onSearch">搜索</el-button>
                            <el-button icon="el-icon-plus" type="success" size="mini" @click="button_add">添加</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-table :data="tempData" border stripe :height="tableHeight" style="width:100%;" @row-dblclick="button_view">
                <el-table-column type="index" label="序号" align="center" width="50"></el-table-column>
                <el-table-column prop="priceReqNo" label="价格申请单号" align="center" width="200"></el-table-column>
                <el-table-column prop="appName" label="应用名称" align="left" width="100"></el-table-column>
                <el-table-column prop="projectName" label="项目名称" align="left" width="150"></el-table-column>
                <el-table-column prop="status" label="状态" align="center" width="80">
                    <template slot-scope="scope">
                        <span v-for="item of statusData" :key="item.dictid">
                            <span v-if="item.dictid == scope.row.status">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="creator" label="申请人" align="center" width="130"></el-table-column>
                <el-table-column prop="createTime" label="申请时间" align="center" width="120"></el-table-column>
                <el-table-column prop="expectedOnlineTime" label="预计上线时间" align="center" width="120"></el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button icon="el-icon-edit" type="success" size="mini" @click="button_edit(scope.row)" v-if="scope.row.status==1">编辑</el-button>
                        <el-button icon="el-icon-delete" type="danger" size="mini" @click="button_deal(scope.row)" v-if="scope.row.status==1 || scope.row.status==5">刪除</el-button>
                        <el-button icon="el-icon-download" type="success" size="mini" @click="button_exportExcel(scope.row)" v-if="scope.row.status!='1'&& scope.row.status!='2'&& scope.row.status!='5'">导出</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs" />
        </div>
        <!-- 子组件 -->
        <resource-manage ref="child"></resource-manage>
    </div>
</template>
<script>
import cleanBtn from '@/components/CleanBtn'
import resourceManage from '@/views/resource/added/resourceManage'
import resourceAddFlow from '@/views/resource/flow/resourceAddFlow'
import common from '@/api/common'
import pager from '@/components/Pager'
import { getDicEntry } from '@/api/eosDictEntry'
import { queryResAddedList, deleteResourceAdded, terminateProcess, exportExcel } from '@/api/resource/added/resourceManager'
export default {
    components: { cleanBtn, pager, resourceManage, resourceAddFlow },
    data() {
        return {
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            page: {
                totalNumber: 0,
                size: 10, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },
            form: {
                priceReqNo: '',
                appName: '',
                projectName: '',
                status: '',
                creator: '',
                rangeDate: ''
            },
            tempData: [], // 集合数据
            statusData: [], //状态数据字典
            flDialog: false
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {
        this.packageInit()
        //表格高度重新设置
        window.onresize = () => {
            return (() => {
                this.tableHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205
            })()
        }
    },
    methods: {
        init(param) {
            console.log('param', param)
            param.sourceType = 0
            let data = common.paramsFormat(param)
            queryResAddedList(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.tempData = res.data.results
                this.page.totalNumber = res.data.total
            })
        },
        packageInit: function (param) {
            if (param == undefined) {
                param = {}
            }
            //初始化方法
            param.size = this.page.size
            param.index = this.page.index
            param.sourceType = 0
            this.init(param)
        },
        getPager: function (size, index) {
            //分页
            let params = this.form
            params.size = size
            params.index = index
            this.page.size = size
            this.page.index = index
            this.init(params)
        },
        onSearch: function () {
            //搜索
            let params = this.form
            //初始化方法
            params.size = this.page.size
            params.index = 1
            this.init(params)
        },
        button_deal(row) {
            if (row.status == 1) {
                this.button_del(row)
            } else if (row.status == 5) {
                this.button_stop(row)
            } else {
                this.$alert('无法获取当前记录的状态,请刷新后尝试！', {
                    type: 'error'
                })
            }
        },
        button_del(row) {
            //删除
            this.$confirm('此操作为永久删除数据，确定继续吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then((res) => {
                    let param = {}
                    param.id = row.id
                    let data = common.paramsFormat(param)
                    deleteResourceAdded(data).then((res) => {
                        if (res.data.status != 200) {
                            this.$message.error(res.data.message)
                            return false
                        } else {
                            this.$message.success('删除成功！')
                            console.log(this.form)
                            this.packageInit(this.form)
                        }
                    })
                })
                .catch((error) => {})
        },
        getDictData() {
            // 查询业务字典
            getDicEntry({
                dicttypeid: 'RES_STATUS'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //状态
                this.statusData = data.filter((item) => {
                    return item.dicttypeid == 'RES_STATUS'
                })
                return data
            })
        },
        button_add() {
            this.$refs.child.add()
        },
        button_edit(row) {
            var id = row.id
            console.log(id)
            this.$nextTick(() => {
                this.$refs.child.edit(id)
            })
        },
        button_view(row) {
            this.$nextTick(() => {
                this.$refs.child.view(row)
            })
        },
        button_stop(row) {
            this.$confirm('此操作为终止当前流程且永久删除数据，确定继续吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then((res) => {
                    let param = {}
                    param.id = row.id
                    param.processInstID = row.processInstID
                    let data = common.paramsFormat(param)
                    terminateProcess(data).then((res) => {
                        if (res.data.status != 200) {
                            this.$message.error(res.data.message)
                            return false
                        } else {
                            this.$message.success('删除成功！')
                            this.packageInit(this.form)
                        }
                    })
                })
                .catch((error) => {})
        },
        button_exportExcel(row) {
            let param = {}
            param.id = row.id
            let data = common.paramsFormat(param)
            exportExcel(data).then((res) => {
                common.download(res.data, self, '资源新增技评总办报表.xls')
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
