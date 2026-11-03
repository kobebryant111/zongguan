<!-- 资产统计报表 -->
<template>
    <div class="app-container">
        <div class="app-main-container">
            <el-form :model="form" ref="form" inline label-width="120px">
                <el-input v-model="form.appNo" class="hidden"></el-input> <!-- 应用名称编号 -->
                <el-input v-model="form.busiDeptId" class="hidden"></el-input> <!-- 所属业务部ID -->
                <el-input v-model="form.techDeptId" class="hidden"></el-input> <!-- 所属技术部ID -->
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="应用名称：" prop="appName">
                            <rela-sys-tree ref="relasystree" v-model="form.appName" @change="getChildrenData" size="mini"></rela-sys-tree>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="项目名称：" prop="projectName">
                            <el-input v-model="form.projectName" clearable size="mini" style="width: 100%"> </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="申请时间：" prop="rangeDate">
                            <el-date-picker v-model="form.rangeDate" type="daterange" size="mini" style="width:60%" value-format="yyyy-MM-dd" start-placeholder="开始日期" end-placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="所属业务部门：" prop="busiDeptName">
                            <rep-dept-tree ref="repBusiDeptTree" v-model="form.busiDeptName" @change="getBusiDeptId" size="mini"></rep-dept-tree>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="申请人：" prop="creator">
                            <el-input v-model="form.creator" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="预计上线时间：" prop="expectedOnlineTimeRangDate">
                            <el-date-picker v-model="form.expectedOnlineTimeRangDate" type="daterange" size="mini" range-separator="-" style="width:60%" value-format="yyyy-MM-dd" start-placeholder="开始日期" end-placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="所属技术部门：" prop="techDeptName">
                            <rep-dept-tree ref="repTechDeptTree" v-model="form.techDeptName" @change="getTechDepId" size="mini"></rep-dept-tree>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="申请单号：" prop="priceReqNo">
                            <el-input v-model="form.priceReqNo" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-button icon="el-icon-refresh" @click="onReset" size="mini">重置</el-button>
                        <el-button icon="el-icon-search" type="primary" @click="onSearch" size="mini">搜索</el-button>
                        <el-button icon="el-icon-download" type="success" @click="downloadFile" size="mini">Excel下载</el-button>
                    </el-col>
                </el-row>
            </el-form>
            <el-table :data="tempData" height="300" border stripe style="width:100%;">
                <el-table-column type="index" label="序号" width="60"></el-table-column>
                <el-table-column prop="priceReqNo" label="申请单号" :show-overflow-tooltip="true" width="150"></el-table-column>
                <el-table-column prop="appName" label="应用名称" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="projectName" label="项目名称" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="amount" label="订单总价"></el-table-column>
                <el-table-column prop="busiDeptName" label="所属业务部门" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="techDeptName" label="所属科技部门" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="budgetNo" label="预算编号"></el-table-column>
                <el-table-column prop="creator" label="申请人"></el-table-column>
                <el-table-column prop="createTime" label="申请时间"></el-table-column>
                <el-table-column prop="expectedOnlineTime" label="预计上线时间"></el-table-column>
                <el-table-column label="操作" width="100" v-if="deptFlag">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="onDelete(scope.row)">刪 除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs" />
            <!-- 
							重要参数：解决由于表格合并导致的鼠标hover不准确问题 但是貌似并没有解决
							:row-class-name="rowClassName"
							@cell-mouse-enter="enter"
							@cell-mouse-leave="leave"
						-->
            <div class="tempSubDataDiv">
                <el-table :data="tempSubData" :span-method="objectSpanMethod" height="500" border stripe style="width:100%;">
                    <el-table-column prop="ownCenter" label="所属中心" align="left"></el-table-column>
                    <el-table-column prop="resType" label="资源类型" align="left"></el-table-column>
                    <el-table-column prop="describe" label="配置" align="left"></el-table-column>
                    <el-table-column prop="num" label="数量" align="left"></el-table-column>
                    <el-table-column prop="price" label="单价(元)" align="left"></el-table-column>
                    <el-table-column prop="totalPrice" label="总价(元)" align="left"></el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
import cleanBtn from '@/components/CleanBtn'
import resourceManage from '@/views/resource/added/resourceManage'
import common from '@/api/common'
import pager from '@/components/Pager'
import { getDicEntry } from '@/api/eosDictEntry'
import relaSysTree from '@/views/resource/added/relaSysTree'
import repDeptTree from '@/views/resource/resourceReport/repDeptTree'
import { deleteResourceAdded } from '@/api/resource/added/resourceManager'
import { queryResAddedListAndStatisticReportInfo, exportResourceReportList, getDeptIdLogin } from '@/api/resource/report/resourceReport'
import { Alert } from 'element-ui'
export default {
    components: { cleanBtn, pager, resourceManage, relaSysTree, repDeptTree },
    data() {
        return {
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            page: {
                totalNumber: 0,
                size: 10, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },
            form: {
                appNo: '',
                appName: '',
                busiDeptId: '',
                techDeptId: '',
                projectName: '',
                busiDeptName: '',
                techDeptName: '',
                creator: '',
                rangeDate: [],
                expectedOnlineTimeRangDate: '',
                templatename: '' // 模板名称
            },
            deptFlag: false,
            show: false, //
            tempData: [], // 集合数据--上半部分主数据
            tempSubData: [], // 集合数据--下半部分统计数据
            visibleClose: false, // dialog 默认关闭
            statusData: [],
            //statisticIsShow: false,
            positionOwnCenterArr: [], // 合共所属中心用
            positionResTypeArr: [], // 合共资源类型
            positionOwnCenter: 0, // 合共所属中心用--合并相同列起始坐标
            positionResType: 0 // 合共资源类型--合并相同列起始坐标
        }
    },
    created() {
        this.getDictData()
        this.getCurrentDate()
    },
    mounted() {
        this.init()
        this.getDeptId()
    },
    methods: {
        init() {
            let param = {
                ...this.form,
                ...this.page
            }
            console.log('param', param)
            let data = common.paramsFormat(param)
            queryResAddedListAndStatisticReportInfo(data).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                this.tempData = res.data.results.mainList
                console.log('列表', this.tempData)
                // 给统计信息赋值
                this.tempSubData = res.data.results.subList
                console.log('报表', this.tempSubData)
                this.page.totalNumber = res.data.total
                this.positionOwnCenterArr = []
                this.positionResTypeArr = []
                this.rowspan(this.positionOwnCenterArr, this.positionOwnCenter, 'ownCenter')
                this.rowspan(this.positionResTypeArr, this.positionResType, 'resType')
                //this.statisticIsShow = true
            })
        },

        getDeptId() {
            getDeptIdLogin().then((res) => {
                if (res.data.results == '1') {
                    this.deptFlag = true
                }
            })
        },

        // 获取当前月日期
        getCurrentDate() {
            var myDate = new Date()
            var year = myDate.getFullYear() // 获取当前年份
            var month = myDate.getMonth() + 1 // 获取当前月份(0-11，0代表1月份所以要+1)
            var day = myDate.getDate() // 获取当前日
            if (day == 1) {
                day = myDate.getDate() // 获取当前日
            } else {
                day = myDate.getDate() - 1 // 获取当前日
            }
            this.form.rangeDate = [`${year}-${month}-${1}`, `${year}-${month}-${day}`]
            console.log(this.form.rangeDate, '当前日期')
        },
        /**
         * 合并统计信息相同的行
         */
        rowspan(spanArr, position, spanName) {
            if (this.tempSubData != undefined) {
                this.tempSubData.forEach((item, index) => {
                    if (index == 0) {
                        spanArr.push(1)
                        position = 0
                    } else {
                        if (this.tempSubData[index][spanName] == this.tempSubData[index - 1][spanName]) {
                            spanArr[position] += 1
                            spanArr.push(0)
                        } else {
                            spanArr.push(1)
                            position = index
                        }
                    }
                })
            }
        },
        getPager(size, index) {
            this.page.size = size
            this.page.index = index
            this.onSearch()
        },
        /**
         * 表格实现合并行
         */
        objectSpanMethod({ row, column, rowIndex, columnIndex }) {
            if (columnIndex == 0) {
                const _row = this.positionOwnCenterArr[rowIndex]
                const _col = _row > 0 ? 1 : 0
                return {
                    rowspan: _row,
                    colspan: _col
                }
            }
            if (columnIndex == 1) {
                const _row = this.positionResTypeArr[rowIndex]
                const _col = _row > 0 ? 1 : 0
                return {
                    rowspan: _row,
                    colspan: _col
                }
            }
        },
        /**
         * 重置
         */
        onReset() {
            // 清空应用系统
            this.$refs.relasystree.clearValue()

            // 清空所属业务部门
            this.$refs.repBusiDeptTree.clearValue()

            // 清空所属业务部门
            this.$refs.repTechDeptTree.clearValue()

            // 清空form表单
            //this.$refs['form'].resetFields()
            Object.keys(this.form).forEach((key) => (this.form[key] = ''))
            //this.statisticIsShow = false
            this.tableHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205
        },
        /**
         * 搜索
         */
        onSearch() {
            this.init()
            //this.statisticIsShow = true
            //this.tableHeight = ((window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205) / 2
        },
        /**
         * Excel下载
         */
        downloadFile() {
            let self = this
            let params = common.paramsFormat(this.form)
            exportResourceReportList(params).then((res) => {
                common.download(res.data, self, '资产统计报表.xls')
            })
        },
        /**
         * 刪除
         */
        onDelete(row) {
            this.$confirm('此操作为永久删除数据，确定继续吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then((res) => {
                let param = {}
                param.id = row.id
                param.sourceType = row.sourceType
                param.iaasFlag = '1'
                let data = common.paramsFormat(param)
                deleteResourceAdded(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$message.error(res.data.message)
                        return false
                    } else {
                        this.$message.success(res.data.message)
                        this.onSearch()
                    }
                })
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
        getChildrenData(args, flag) {
            console.log(args, flag)
            //关联项目树
            this.form.appNo = args.id //系统ID
        },
        getBusiDeptId(args, flag) {
            console.log(args, flag)
            //关联项目树
            this.form.busiDeptId = args.deptId //业务部id
        },
        getTechDepId(args, flag) {
            console.log(args, flag)
            //关联项目树
            this.form.techDeptId = args.deptId //技术部id
        },
        button_add() {
            this.$refs.child.add()
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
.hidden {
    display: none;
}
.tempSubDataDiv {
    margin-top: 10px;
}
</style>

