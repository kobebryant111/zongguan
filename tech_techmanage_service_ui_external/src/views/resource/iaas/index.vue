<!-- iaas资源 -->
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
                        <el-form-item label="预计上线时间：" prop="expectedOnlineTimeRangDate">
                            <el-date-picker v-model="form.expectedOnlineTimeRangDate" type="daterange" size="mini" range-separator="-" style="width:65%" value-format="yyyy-MM-dd" start-placeholder="开始日期" end-placeholder="结束日期" @change="dataChange">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="项目名称：" prop="projectName">
                            <el-input v-model="form.projectName" clearable size="mini" style="width: 100%"> </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="序列号：" prop="sn">
                            <el-input v-model="form.sn" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="IP：" prop="ip">
                            <el-input v-model="form.ip" size="mini" style="width:240px" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-button icon="el-icon-refresh" @click="onReset" size="mini">重置</el-button>
                        <el-button icon="el-icon-search" type="primary" @click="onSearch" size="mini">搜索</el-button>
                        <el-button v-if="noticeFlag" icon="el-icon-circle-plus-outline" type="success" size="mini" @click="noticeSetting">通知设置</el-button>
                    </el-col>
                </el-row>
            </el-form>
            <el-table :data="tempData" :expand-row-keys="expands" row-key="id" :height="tableHeight" border stripe style="width:100%;" @expand-change="handledetail">
                <el-table-column type="expand">
                    <template>
                        <item-ext-table :itemData="itemData" :saveSuccess="onSearch" :isXtb="'0' == userDept" :isJcsc="'1' == userDept" :isWlb="'2' == userDept" />
                    </template>
                </el-table-column>
                <el-table-column prop="priceReqNo" label="价格申请单号" align="center" width="200"></el-table-column>
                <el-table-column prop="appName" label="应用名称" align="center"></el-table-column>
                <el-table-column prop="projectName" label="项目名称" align="center"></el-table-column>
                <el-table-column prop="amount" label="总价（元）" align="center"></el-table-column>
                <el-table-column prop="expectedOnlineTime" label="预计上线时间" align="center"></el-table-column>
                <el-table-column label="操作" width="300" align="center">
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini" @click="view(scope.row)">查 看</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs" />
        </div>
        <!-- 查看页面 -->
        <data-view ref="dataView" />
        <notice-config ref="noticeConfig" :isManage="isManage" />
    </div>
</template>
<script>
import cleanBtn from '@/components/CleanBtn'
import dataView from '@/views/resource/iaas/dataView'
import common from '@/api/common'
import pager from '@/components/Pager'
import noticeConfig from '@/views/resource/iaas/noticeConfig'
import itemExtTable from '@/views/resource/iaas/itemExtTable'
import { getDicEntry } from '@/api/eosDictEntry'
import { loginUserIdentity } from '@/api/resource/iaas/notice'
import { queryIaasPager, checkLoginUserDept, queryIaasResourcePagerOhter } from '@/api/resource/iaas/iaas'
import { Alert } from 'element-ui'
export default {
    components: { cleanBtn, pager, noticeConfig, itemExtTable, dataView },
    data() {
        return {
            expands: [],
            // 通知设置的管理员或者设置的用户
            noticeFlag: false,
            // 是否通知设置的管理员
            isManage: false,
            // 登录用户所属部门：0-系统部，1-基础设施部
            userDept: '',
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            page: {
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },
            form: {
                priceReqNo: '',
                appName: '',
                expectedOnlineTimeRangDate: '',
                expectedOnlineStartTime: '',
                expectedOnlineEndTime: '',
                projectName: '',
                sn: '',
                ip: '',
                sourceType: '0'
            },
            tempData: [], // 集合数据
            tempData1: [], // 集合数据
            itemData: [], // 集合数据
            statusData: []
        }
    },
    created() {
        this.getDictData()
        this.onSearch()
        // 判断当前登录者是否为配置的管理员或配置的通知用户
        this.loginUserIdentity()
        // 检查当前登录用户所属部门
        this.checkLoginUserDept()
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
        dataChange(value) {
            if (null == value || '' == value || undefined == value) {
                this.form.expectedOnlineStartTime = ''
                this.form.expectedOnlineEndTime = ''
                return false
            }

            if (2 == value.length) {
                this.form.expectedOnlineStartTime = value[0]
                this.form.expectedOnlineEndTime = value[1]
            }
        },
        // 通知设置
        noticeSetting() {
            this.$refs.noticeConfig.changeShowFlag()
        },
        /**
         * 查询条件清除
         */
        onReset() {
            //this.$refs['form'].resetFields()
            Object.keys(this.form).forEach((key) => (this.form[key] = ''))
        },
        handledetail(row, expandedRows) {
            //默认一次只打开一个
            if (expandedRows.length) {
                this.expands = [row.id]
            } else {
                this.expands = []
            }
            this.itemData = []
            this.tempData1.forEach((item, index) => {
                if (item.addedid == row.id) {
                    this.itemData.push(item)
                }
            })
        },
        /**
         * 初始化
         */
        init() {
            let param = {
                ...this.form,
                ...this.page
            }
            let data = common.paramsFormat(param)
            queryIaasPager(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }

                this.tempData = res.data.results
                this.page.totalNumber = res.data.total
                this.initLoad()
            })
        },

        initLoad() {
            let param = {
                ...this.form,
                ...this.page
            }
            let data = common.paramsFormat(param)
            queryIaasResourcePagerOhter(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.tempData1 = res.data.results
            })
        },
        // 检查登录用户身份
        loginUserIdentity() {
            loginUserIdentity().then((res) => {
                if (res.data.status != 200) {
                    console.log('检查登录用户身份出错!')
                    return false
                }
                if ('1' == res.data.results) {
                    this.isManage = true
                    this.noticeFlag = true
                } else if ('2' == res.data.results) {
                    this.isManage = false
                    this.noticeFlag = true
                } else {
                    this.isManage = false
                    this.noticeFlag = false
                }
            })
        },
        // 检查当前登录用户所属部门
        checkLoginUserDept() {
            checkLoginUserDept().then((res) => {
                if (res.data.status != 200) {
                    console.log('检查登录用户身份出错!')
                    return false
                }
                this.userDept = res.data.results
            })
        },
        // 分页
        getPager(pageSize, currentPage) {
            this.page.size = pageSize
            this.page.index = currentPage
            this.onSearch()
        },
        /**
         * 搜索
         */
        onSearch: function () {
            this.init()
        },
        // 查看详细
        view(row) {
            this.$refs.dataView.edit(row.id)
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
                this.statusData = data.filter((item) => {
                    return item.dicttypeid == 'RES_STATUS'
                })
                return data
            })
        },
        button_add() {
            this.$refs.child.add()
        }
    }
}
</script>
<style lang="scss">
.el-form-item {
    margin-bottom: 5px;
}
</style>