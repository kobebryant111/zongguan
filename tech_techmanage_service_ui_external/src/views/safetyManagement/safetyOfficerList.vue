<!-- 安全专员清单  -->
<template>
  <div class="typeset-container">
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClickHard">
      <el-tab-pane label="总部" name="first">
        <head-office ref="headOffice"/>
      </el-tab-pane>
      <el-tab-pane label="业务部" name="second">
        <business-unit ref="businessUnit"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import common from '@/api/common'
import headOffice from '@/views/safetyManagement/views/headOffice'
import businessUnit from '@/views/safetyManagement/views/BusinessUnit'
import cleanBtn from '@/components/CleanBtn'
import pager from '@/components/Pager'
import buttonOrange from '@/components/eleButton/buttonOrange'
export default {
  name: 'SafetyOfficerList',
  components: {
    headOffice,
    businessUnit,
    buttonOrange,
    cleanBtn,
    pager
  },
  data() {
    return {
      activeName: 'first',
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      form: {
      }
    }
  },
  mounted() {
    this.loadInit()
  },
  methods: {
    /**
     * 初始化方法
     */
    loadInit: function() {
      this.$refs.headOffice.loadInit()
    },
    handleClickHard: function(tab, event) {
      if (tab.index === '0') {
        this.$refs.headOffice.loadInit()
      } else if (tab.index === '1') {
        this.$refs.businessUnit.loadInit()
      }
    }
  }
}
</script>

<style scoped>
.typeset-container {
  padding: 10px;
  background-color: #ffffff;
}
.pager-zj {
  text-align: center;
}
.border-card {
  padding: 10px;
  background-color: #ffffff;
}
</style>
