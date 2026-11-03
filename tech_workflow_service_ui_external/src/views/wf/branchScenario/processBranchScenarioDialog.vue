<template>
    <div class="process-dialog">
        <wfd-vue
                v-if="show"
                workFlowMode = "preview"
                :baseApi = baseApi
                :processInstanceId = 'processInstanceId'
                :processDefineId = "processDefinitionId"
                :highlightedNodeId = "highlightedNodeId"
                :isSimpleMode = true
                :tokenInfo = tokenInfo
                :extraData="extraData"
                lang="zh" />
        <div>
            <el-table :data="table" style="width: 100%;margin-top: 20px">
                <el-table-column prop="nodeId" label="节点id" width="280"/>
                <el-table-column prop="nodeName" label="节点名称" width="180"/>
                <el-table-column prop="nodeType" label="节点类型"/>
            </el-table>
        </div>
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
      processDefinitionId: '',
      DialogInitData: '',
      // 高亮节点数据 格式 {'activities',[],'flows': []} 节点id 和 线条id 的数组
      highlightedNodeId: {},
      baseApi: process.env.VUE_APP_BASE_API,
      show: false,
      pathStr: '',
      table: []
    }
  },
  created() {
    this.getInitData()
  },
  methods: {
    initTable(data) {
      const table = []
      const list = data.split('-->')
      // 做一个映射集
      const map = {
        'userTask': '用户任务',
        'exclusiveGateway': '排他网关',
        'inclusiveGateway': '包容网关',
        'parallelGateway': '并行网关',
        'subProcess': '子任务',
        'startEvent': '开始节点',
        'serviceTask': '任务任务',
        'endEvent': '结束节点',
        'callActivity': '外部节点',
        'scriptTask': '脚本任务'
      }
      let index = 0
      const nodeIdList = this.highlightedNodeId.activities
      list.forEach(object => {
        const leftIndex = object.indexOf('(')
        const rightIndex = object.indexOf(')')
        const nodeName = object.substring(0, leftIndex)
        const nodeType = object.substring(leftIndex + 1, rightIndex)
        if (nodeType !== 'sequenceFlow') {
          const nodeId = nodeIdList[index]
          const row = {
            'nodeName': nodeName,
            'nodeType': map[nodeType],
            'nodeId': nodeId
          }
          index++
          table.push(row)
        }
      })
      this.table = table
    },
    getInitData() {
      // 通过公共方法获取初始化数据
      const dialogObj = this.$tsVue.tansunDialogData(this)
      const { initData } = dialogObj
      this.DialogInitData = initData
      console.log('通过公共方法获取初始化数据:', this.DialogInitData)
      this.processDefinitionId = this.DialogInitData.processDefinitionId
      // 只有从分支场景页面过来才会有这个值
      this.highlightedNodeId = this.DialogInitData.highlightedNodeId
      // 子组件需要在父组件数据都赋值完后在打开
      this.show = true
      // console.log('processDefinitionId:', this.processDefinitionId)
      this.initTable(this.DialogInitData.pathStr)
    }
  }
}
</script>

<style scoped>

</style>
