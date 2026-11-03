<!-- 流程通知管理 -->
<template>
    <div class="app-container">
        <div class="app-main-container">
            <el-form :model="form" ref="form" inline label-width="120px">
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="价格申请单号：" prop="priceReqNo">
                            <el-input v-model="form.priceReqNo" clearable size="mini" style="width: 100%"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="应用名称：" prop="appName">
                            <el-input v-model="form.appName" clearable size="mini" style="width: 100%"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="申请时间：" prop="rangeDate">
                            <el-date-picker v-model="form.rangeDate" type="daterange" size="mini" range-separator="-" style="width:65%" value-format="yyyy-MM-dd" start-placeholder="开始日期" end-placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>

                </el-row>
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="项目名称：" prop="priceReqNo">
                            <el-input v-model="form.projectName" clearable size="mini" style="width: 100%"> </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="状态：" prop="status">
                            <el-select placeholder="请选择" v-model="form.status" clearable size="mini">
                                <el-option v-for="item in statusData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="申请人：" prop="creator">
                            <el-input v-model="form.creator" size="mini" style="width:240px" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">

                        <clean-btn icon="el-icon-refresh" :forms="this.form" size="mini" />
                        <el-button icon="el-icon-search" type="primary" @click="onSearch" size="mini">查询</el-button>
                        <el-button icon="el-icon-plus" type="success" size="mini" @click="button_add">添加</el-button>
                    </el-col>
                </el-row>
            </el-form>
            <el-table :data="tempData" :height="tableHeight" border stripe style="width:100%;" @row-dblclick="viewResouce">
                <el-table-column type="index" align="left" label="序号" width="60"></el-table-column>
                <el-table-column prop="priceReqNo" label="价格申请单号" align="left"></el-table-column>
                <el-table-column prop="appName" label="应用名称" align="left"></el-table-column>
                <el-table-column prop="projectName" label="项目名称" align="left"></el-table-column>
                <el-table-column prop="amount" label="总价（元）" align="right" width="100"></el-table-column>
                <el-table-column prop="status" label="状态" align="left" width="80">
                    <template slot-scope="scope">
                        <span v-for="item in statusData" :key="item.dictid" v-if="item.dictid == scope.row.status">{{item.dictname}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="creator" label="申请人" align="left" width="130"></el-table-column>
                <el-table-column prop="createTime" label="申请时间" align="left" width="120"></el-table-column>
                <el-table-column prop="expectedOnlineTime" label="预计上线时间" align="left" width="120"></el-table-column>
                <el-table-column label="操作" width="200" align="left">
                    <template slot-scope="scope">
                        <el-button type="primary" v-if="scope.row.status=='1'" size="mini" @click="onUpdate(scope.row)">编 辑</el-button>
                        <el-button type="danger" size="mini" @click="button_deal(scope.row)" v-if="scope.row.status==1 || scope.row.status==5">刪 除</el-button>
                        <el-button type="success" v-if="scope.row.status!='1'&& scope.row.status!='2'&& scope.row.status!='5'" size="mini" @click="downloadFile(scope.row)">导 出</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs" />
        </div>
        <resource-expand-add ref="child"></resource-expand-add>
    </div>
</template>
<script>
import cleanBtn from '@/components/CleanBtn'
import resourceExpandAdd from '@/views/resource/expand/resourceExpandAdd'
import common from '@/api/common'
import pager from '@/components/Pager'
import { getDicEntry } from '@/api/eosDictEntry'
import { deleteResourceExpand, exportResAuditReport } from '@/api/resource/expand/resourceExpand'
import { queryResAddedList, terminateProcess } from '@/api/resource/added/resourceManager'
import { Alert } from 'element-ui'
export default {
    components: { cleanBtn, pager, resourceExpandAdd },
    data() {
        return {
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            page: {
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },
            form: {
                priceReqNo: '',
                appName: '',
                rangeDate: '',
                projectName: '',
                status: '',
                creator: '',
                sourceType: '1'
            },
            tempData: [], // 集合数据
            visibleClose: false, // dialog 默认关闭
            statusData: []
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
            console.log('param', param)
            param.sourceType = '1'
            let data = common.paramsFormat(param)
            queryResAddedList(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.tempData = res.data.results
                this.page.totalNumber = res.data.total
            })
        }, // 初始化 end
        /**
         * 公共初始方法
         */
        packageInit: function (param) {
            //let param = {}
            param.size = this.page.size
            param.index = this.page.index
            this.init(param)
        },
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
        button_deal(row) {
            if (row.status == 1) {
                this.onDelete(row)
            } else if (row.status == 5) {
                this.button_stop(row)
            } else {
                this.$alert('无法获取当前记录的状态,请刷新后尝试！', { type: 'error' })
            }
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
        /**
         * 刪除
         */
        onDelete(row) {
            this.$confirm('此操作为永久删除数据，确定继续吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then((res) => {
                    let param = {}
                    param.id = row.id
                    let data = common.paramsFormat(param)
                    deleteResourceExpand(data).then((res) => {
                        if (res.data.status != 200) {
                            this.$message.error(res.data.message)
                            return false
                        } else {
                            this.$message.success(res.data.message)
                            this.packageInit(this.form)
                        }
                    })
                })
                .catch(() => {
                    //添加catch，确认取消会走这里，不加报异常
                })
        }, // 刪除end
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
                this.statusData = data.filter((item) => {
                    return item.dicttypeid == 'RES_STATUS'
                })
                return data
            })
        },
        button_add() {
            this.$nextTick(() => {
                this.$refs.child.add()
            })
        },
        //编辑
        onUpdate(row) {
            this.$nextTick(() => {
                this.$refs.child.initEdit(row.id, '0')
            })
        },
        //编辑
        afterSave() {
            this.$message.success('操作成功')
            this.packageInit(this.form)
        },
        //查看
        viewResouce(row) {
            this.$nextTick(() => {
                this.$refs.child.initEdit(row.id, '1')
            })
        },
        /**
         * Excel下载
         */
        downloadFile(row) {
            let self = this
            var param = { id: row.id }
            let params = common.paramsFormat(param)
            exportResAuditReport(params).then((res) => {
                common.download(res.data, self, '资源扩容技评总办报表.xls')
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

