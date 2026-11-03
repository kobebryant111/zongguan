<!-- 套餐选择列表 -->
<template>
    <div>
        <el-dialog :title="titleName" :visible.sync="additionalDialog" append-to-body width="60%">
            <el-scrollbar style="height:100%">
                <div style="height:500px">
                    <el-form :model="form" ref="form" inline label-width="120px">
                        <el-form-item label="编号：" prop="applyCode">
                            <el-input v-model="form.applyCode" clearable size="mini" style="width: 100%"> </el-input>
                        </el-form-item>
                        <el-form-item label="流程标题：" prop="flowTitle">
                            <el-input v-model="form.flowTitle" clearable size="mini" style="width: 100%"> </el-input>
                        </el-form-item>
                        <el-button icon="el-icon-search" type="primary" @click="onSearch" size="mini">搜索</el-button>
                    </el-form>
                    <el-table :data="additionalData" border stripe style="width:100%" @row-click="rowClick" highlight-current-row size="mini">
                        <el-table-column prop="APPLYCODE" label="补录编号" align="center" width="15"></el-table-column>
                        <el-table-column prop="FLOWTITLE" label="流程标题" align="center" width="30"></el-table-column>
                        <el-table-column prop="FLOWSTATUS" label="状态" align="center" width="30"></el-table-column>
                    </el-table>
                    <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs" />
                </div>
            </el-scrollbar>
        </el-dialog>
    </div>
</template>
<script>
import common from '@/api/common'
import pager from '@/components/Pager'
import { queryAdditionalList } from '@/api/resource/added/resourceManager'
import { Alert } from 'element-ui'
export default {
    components: { pager },
    data() {
        return {
            page: {
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },
            additionalData: [],
            inputContent: this.input,
            additionalDialog: false, //选择补录编号弹窗
            titleName: '补录编号选择', //大标题名字
            form: {
                applyCode: '',
                flowTitle: ''
            }
        }
    },
    mounted() {},
    methods: {
        rowClick(row) {
            let chooseData = {}
            chooseData.id = row.APPLYCODE
            this.$parent.getChildrenData(chooseData, 'additionalList')
            this.additionalDialog = false
        },
        toSelect() {
            let params = this.form
            params.size = this.page.size
            params.index = this.page.index
            console.log('param', params)
            let data = common.paramsFormat(params)
            queryAdditionalList(data)
                .then((res) => {
                    if (res.data.status == 200) {
                        this.additionalData = res.data.results
                        this.page.totalNumber = res.data.total
                    }
                })
                .catch((error) => {
                    this.$message.error(error)
                })
            this.additionalDialog = true
        },

        onSearch() {
            //搜索
            let params = this.form
            params.size = this.page.size
            params.index = this.page.index
            console.log('param', params)
            let data = common.paramsFormat(params)
            queryAdditionalList(data)
                .then((res) => {
                    if (res.data.status == 200) {
                        this.additionalData = res.data.results
                        this.page.totalNumber = res.data.total
                    }
                })
                .catch((error) => {
                    this.$message.error(error)
                })
        },
        getPager(size, index) {
            this.page.size = size
            this.page.index = index
            this.onSearch()
        }
    }
}
</script>