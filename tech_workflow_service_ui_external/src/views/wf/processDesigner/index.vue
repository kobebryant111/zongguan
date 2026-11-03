
<template>
    <div class="flow-container">
        <wfd-vue
                ref="wfdVue"
                editMode="dialog"
                :workFlowMode = "workFlowMode"
                :modelId = "model.id"
                :processDefineId = "processDefinitionId"
                :deploymentId = "deploymentId"
                :isSimpleMode=true
                :baseApi = this.baseApi
                :tokenInfo = tokenInfo
                :extraData="extraData"
                lang="zh"  />
    </div>
</template>
<script>
import WfdVue from 'tansun-wf-designer'
export default {
  components: { WfdVue },
  data() {
    return {
      // 配置接口额外公共参数
      extraData: { tenantId: this.$router.currentRoute.query.tenantId },
      tokenInfo: this.$tsVue.tansunGetToken(),
      isWorkFlowMode: true,
      baseApi: process.env.VUE_APP_BASE_API,
      workFlowMode: 'editDraw',
      model: this.$router.currentRoute.query,
      processDefinitionId:this.$router.currentRoute.query.processDefineId,
      deploymentId:'',
      showAlert:true
    }
  },
  created() {
    console.log('---------------')
    console.log(this.$router.currentRoute.query)
    if(this.$router.currentRoute.query.isUpdate){
      this.deploymentId = this.$router.currentRoute.query.deploymentId
    }
    const self = this
    self.showAlert = true
    window.onbeforeunload = function (event) {
      if(self.showAlert && self.$refs.wfdVue && !self.$refs.wfdVue.getIsSave()){
        event.returnValue = '您的编辑还未保存，是否离开？'
        return false
      }
    }
  },
  activated() {
    this.showAlert = true
  },
  deactivated() {
    this.showAlert = false
  },
  beforeDestroy() {
    this.showAlert = false
  },
  beforeRouteLeave (to, from, next) {
    if(this.$refs.wfdVue && !this.$refs.wfdVue.getIsSave()){
      const answer = window.confirm('您的编辑还未保存，是否离开？')
      if (answer) {
        next()
      } else {
        next(false)
        this.$store.dispatch('tagsView/addView', this.$route)
      }
    } else {
      next()
    }
  },
  methods: {
  },
}
</script>
<style lang="scss">
.flow-container {
  min-height: 800px;
  position: sticky;
  padding: 5px;
  width: 100%;
  background: #fff;
}
</style>

