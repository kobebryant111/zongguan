<template>
    <div class="app-container">
        <el-row :gutter="24">
            <el-col :span="6"><div class="ts-pane-box" style="margin-top: 20px"><process-state-report></process-state-report></div></el-col>
            <el-col :span="6"><div class="ts-pane-box" style="margin-top: 20px"><process-number-report ></process-number-report></div></el-col>
            <el-col :span="12"><div class="ts-pane-box"><user-process-report></user-process-report></div></el-col>
        </el-row>
        <el-row :gutter="24">
            <el-col :span="24"><div class="ts-pane-box"><process-instance-report></process-instance-report></div></el-col>
        </el-row>
        <el-row :gutter="24">
            <el-col :span="12"><div class="ts-pane-box"><personnel-report></personnel-report></div></el-col>
            <el-col :span="12"><div class="ts-pane-box"><process-report></process-report></div></el-col>
        </el-row>
        <el-row :gutter="24">
            <el-col :span="12"><div class="ts-pane-box"><organization-report></organization-report></div></el-col>
        </el-row>
    </div>
</template>

<script>
import ProcessStateReport from '../components/Report/ProcessStateReport/index'
import ProcessNumberReport from '../components/Report/ProcessNumberReport/index'
import UserProcessReport from '../components/Report/UserProcessReport/index'
import ProcessInstanceReport from '../components/Report/ProcessInstanceReport/index'
import PersonnelReport from '../components/Report/PersonnelReport/index'
import OrganizationReport from '../components/Report/OrganizationReport/index'
import ProcessReport from '../components/Report/ProcessReport/index'
export default {
  components: { ProcessReport, OrganizationReport, PersonnelReport, ProcessInstanceReport, UserProcessReport, ProcessNumberReport, ProcessStateReport },
  data() {
    return {
    }
  },
  mounted() {
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
</style>
