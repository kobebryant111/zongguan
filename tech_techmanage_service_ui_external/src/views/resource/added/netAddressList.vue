<!-- 网络区域选择列表 -->
<template>
    <div>
        <el-dialog :title="titleName" :visible.sync="flDialog" append-to-body>
            <el-scrollbar style="height:100%">
                <div style="height:500px">
                    <el-table :data="tempData" border stripe style="width:100%" @row-click="rowClick" highlight-current-row>
                        <el-table-column type="index" align="center"></el-table-column>
                        <el-table-column prop="PRODUCTIONNETWORK" label="生产网络" align="center"></el-table-column>
                        <el-table-column prop="NETWORKAREA" label="网络区域" align="center"></el-table-column>
                    </el-table>
                </div>
            </el-scrollbar>
        </el-dialog>
    </div>
</template>
<script>
import common from '@/api/common'
import { queryIpApplyNetAddress } from '@/api/resource/added/resourceManager'
export default {
    data() {
        return {
            index: '',
            tempData: [],
            inputContent: this.input,
            flDialog: false, //是否选择变更人员弹窗
            titleName: '关联系统' //大标题名字
        }
    },
    mounted() {
        this.packageInit()
    },
    methods: {
        // 弹窗显示
        showDialog() {
            this.flDialog = true
        },
        packageInit: function () {
            this.init()
        },
        init() {},
        rowClick(row) {
            let chooseData = {}
            chooseData.id = row.ID
            chooseData.netWorkArea = row.NETWORKAREA
            this.$parent.getChildrenData(chooseData, 'netList', this.index)
            this.flDialog = false
        },
        toSelect(index) {
            this.index = index
            queryIpApplyNetAddress()
                .then((res) => {
                    if (res.data.status == 200) {
                        this.tempData = res.data.results
                    } else {
                        this.$message.error('')
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