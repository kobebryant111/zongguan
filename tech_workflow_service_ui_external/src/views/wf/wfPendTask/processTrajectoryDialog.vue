<template>
    <div class="process-dialog">
        <wfd-vue
                v-if="show"
                workFlowMode = "preview"
                :baseApi = baseApi
                :processInstanceId = 'processInstanceId'
                :processDefineId = "processDefinitionId"
                :isSimpleMode = true
                :tokenInfo = tokenInfo
                :extraData="extraData"
                lang="zh" />
    </div>
</template>

<script>
import WfdVue from 'tansun-wf-designer'
export default {
  components: { WfdVue },
  data() {
    return {
      extraData: { tenantId: this.$store.state.user.userInfo.tenantId },
      tokenInfo: this.$tsVue.tansunGetToken(),
      processInstanceId: '',
      processDefinitionId: '',
      DialogInitData: '',
      baseApi: process.env.VUE_APP_BASE_API,
      show: false,
    }
  },
  created() {
    this.getInitData()
  },
  methods: {
    getInitData() {
      // 通过公共方法获取初始化数据
      const dialogObj = this.$tsVue.tansunDialogData(this)
      const { initData } = dialogObj
      this.DialogInitData = initData
      this.processInstanceId = this.DialogInitData.processInstanceId
      this.processDefinitionId = this.DialogInitData.processDefinitionId
      // 子组件需要在父组件数据都赋值完后在打开
      this.show = true
    }
  }
}
</script>

<style scoped>

</style>
