<!-- 套餐选择列表 -->
<template>
    <div>
        <el-dialog :title="titleName" :visible.sync="flDialog" append-to-body width="60%">
            <el-scrollbar style="height:100%">
                <div style="height:500px">
                    <el-table :data="tempData" border stripe style="width:100%" @row-click="rowClick" highlight-current-row size="mini">
                        <el-table-column prop="CATEGORY" label="套餐分类" align="center" width="15"></el-table-column>
                        <el-table-column prop="APPLYSCENE" label="适用场景" align="center" width="30"></el-table-column>
                        <el-table-column prop="DESCRIBE" label="配置信息" align="center" width="40"></el-table-column>
                        <el-table-column prop="PRICE" label="价格（元）" align="center" width="15"></el-table-column>
                    </el-table>
                </div>
            </el-scrollbar>
        </el-dialog>
    </div>
</template>
<script>
import common from '@/api/common'
import { queryRelaComboList } from '@/api/resource/added/resourceManager'
export default {
    data() {
        return {
            index: '',
            tempData: [],
            inputContent: this.input,
            flDialog: false, //是否选择变更人员弹窗
            titleName: '套餐选择' //大标题名字
        }
    },
    mounted() {},
    methods: {
        showDialog() {
            // 弹窗显示
            this.packageInit()
            this.flDialog = true
        },
        packageInit: function () {
            this.init()
        },
        init() {},
        rowClick(row) {
            let chooseData = {}
            chooseData.id = row.ID
            chooseData.comboPrice = row.PRICE
            chooseData.cpuNum = row.CPUNUM
            chooseData.cpuCode = row.CPUCORE
            chooseData.describe = row.DESCRIBE
            chooseData.dbPrice = row.DISKCAPACITY
            this.$parent.getChildrenData(chooseData, 'comboList', this.index)
            this.flDialog = false
        },
        toSelect(nodeType, comboType, index) {
            this.index = index
            let data = common.paramsFormat({ comboType: comboType, nodeType: nodeType })
            queryRelaComboList(data)
                .then((res) => {
                    if (res.data.status == 200) {
                        this.tempData = res.data.results.filter((item) => {
                            return item.ISCANCEL == 0
                        })
                    } else {
                        this.tempData = []
                        this.$message.error('请先选择正确的节点类型和选型类型')
                    }
                })
                .catch((error) => {
                    this.$message.error(error)
                })
            this.flDialog = true
        }
    }
}
</script>