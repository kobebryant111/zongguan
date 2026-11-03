<!-- 资源配置 -->
<template>
    <div class="workPlatform_left_middle">
        <el-tabs v-model="activeTabName" @tab-click="onTabsChange">
            <el-tab-pane label="物理机套餐" name="physicalMachineCombo">
                <physical-machine-combo ref="physicalMachineCombo" :viewFlag="'1'"></physical-machine-combo>
            </el-tab-pane>
            <el-tab-pane label="物理机配件" name="physicalMachineParts">
                <physical-machine-parts ref="physicalMachineParts" :viewFlag="'1'"></physical-machine-parts>
            </el-tab-pane>
            <el-tab-pane label="虚拟机套餐" name="vmCombo">
                <vm-combo ref="vmCombo" :viewFlag="'1'"></vm-combo>
            </el-tab-pane>
            <el-tab-pane label="虚拟机配件" name="vmParts">
                <vm-parts ref="vmParts" :viewFlag="'1'"></vm-parts>
            </el-tab-pane>
            <el-tab-pane label="存储套餐" name="memoryCombo">
                <memory-combo ref="memoryCombo" :viewFlag="'1'"></memory-combo>
            </el-tab-pane>
            <el-tab-pane label="PaaS套餐" name="paasCombo">
                <paas-combo ref="paasCombo" :viewFlag="'1'"></paas-combo>
            </el-tab-pane>
            <el-tab-pane label="操作系统报价" name="operationSysSoft">
                <operation-sys-soft ref="operationSysSoft" :viewFlag="'1'"></operation-sys-soft>
            </el-tab-pane>
            <el-tab-pane label="数据库报价" name="dbSoft">
                <db-soft ref="dbSoft" :viewFlag="'1'"></db-soft>
            </el-tab-pane>
            <el-tab-pane label="中间件报价" name="middlewareSoft">
                <middleware-soft ref="middlewareSoft" :viewFlag="'1'"></middleware-soft>
            </el-tab-pane>
            <!-- <el-tab-pane label="消息中间件报价" name="msgMiddlewareSoft">
                <msg-middleware-soft ref="msgMiddlewareSoft" :viewFlag="'1'"></msg-middleware-soft>
            </el-tab-pane> -->
        </el-tabs>
    </div>
</template>
<script>
import physicalMachineCombo from '@/views/resource/config/physicalMachineCombo'
import physicalMachineParts from '@/views/resource/config/physicalMachineParts'
import vmCombo from '@/views/resource/config/vmCombo'
import vmParts from '@/views/resource/config/vmParts'
import memoryCombo from '@/views/resource/config/memoryCombo'
import paasCombo from '@/views/resource/config/paasCombo'
import operationSysSoft from '@/views/resource/config/operationSysSoft'
import dbSoft from '@/views/resource/config/dbSoft'
import middlewareSoft from '@/views/resource/config/middlewareSoft'
import msgMiddlewareSoft from '@/views/resource/config/msgMiddlewareSoft'

export default {
    name: 'ConfigManageList',
    components: {
        physicalMachineCombo,
        physicalMachineParts,
        vmCombo,
        vmParts,
        memoryCombo,
        paasCombo,
        operationSysSoft,
        dbSoft,
        middlewareSoft,
        msgMiddlewareSoft
    },
    data() {
        return {
            activeTabName: 'physicalMachineCombo', // 选中tab的name
            dbNumber: ''
        }
    },
    mounted() {},
    methods: {
        /**
         * tabs 点击触发
         */
        onTabsChange(tab) {
            var tabid = tab.index
            if (tabid == 0) {
                this.$refs.physicalMachineCombo.loadInit()
            } else if (tabid == 1) {
                this.$refs.physicalMachineParts.loadInit()
            } else if (tabid == 2) {
                this.$refs.vmCombo.loadInit()
            } else if (tabid == 3) {
                this.$refs.vmParts.loadInit()
            } else if (tabid == 4) {
                this.$refs.memoryCombo.loadInit()
            } else if (tabid == 5) {
                this.$refs.paasCombo.loadInit()
            } else if (tabid == 6) {
                this.$refs.operationSysSoft.loadInit()
            } else if (tabid == 7) {
                this.$refs.dbSoft.loadInit()
            } else if (tabid == 8) {
                this.$refs.middlewareSoft.loadInit()
            } else if (tabid == 9) {
                this.$refs.msgMiddlewareSoft.loadInit()
            }
        }
    }
}
</script>
<style scoped>
.workPlatform_left_middle {
    background: #ffffff;
    padding: 15px;
    margin: 15px 0;
}
</style>

