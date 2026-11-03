
<template>
    <div class="wfd-container">
        <el-form inline label-width="100px" style="margin-top: 30px">
            <el-form-item label="流程版本" >
                <ts-multi-select v-model="processDefineId" :params="selectParam" @change="onchange" ></ts-multi-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="showBranchScenario" icon="el-icon-search" >分支轨迹图</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="back" icon="el-icon-back" >返回</el-button>

            </el-form-item>
        </el-form>
        <div >
            <ts-multi-table  ref="list"  :options="options">
                <el-table-column prop="index" label="索引" width="80" />
                <el-table-column  prop="pathStr" label="轨迹路线" >
                </el-table-column>
            </ts-multi-table>
        </div>
        <ts-multi-dialog :visible.sync="trajectoryDialogVisible" :options="processTrajectoryOptions"></ts-multi-dialog>
    </div>

</template>
<script>
import processBranchScenarioDialog from './processBranchScenarioDialog.vue'
export default {
  data() {
    return {
      processDefineId: '',
      selectParam: { options: [], text: 'text', value: 'value' },
      trajectoryDialogVisible: false,
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.getBranchScenarioList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        initTable: false,
        apiRequiredParams: {}, // 选填 表示接口的必需恒定不变的参数
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true // [可不填 | Boolean] 默认值true 显示确认按钮
      },
      processTrajectoryOptions: {
        title: '流程分支场景轨迹图', // [必填]
        resource: processBranchScenarioDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '70%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        callbacks: [null, null], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      }
    }
  },
  methods: {

    back() {
      // 因为是新开的页面所以不能直接用back
      // this.$tsVue.tansunBack(this, { 'needupdate': 'yes' })
      this.$tsVue.tansunPushAddTag(this, '/wf/testPro')
    },
    onchange(value) {
      this.options.apiRequiredParams['processDefineId'] = value
      this.$refs['list'].serchHandel()
    },
    showBranchScenario() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      } else {
        // 构建高亮节点数据
        // 第一次进来是字符串，后面进行操作后就是一个对象，就不需要在进来了
        if (typeof object.highlightedNodeId == 'string') {
          const list = object.highlightedNodeId.split(',')
          const flows = []
          const activities = []
          for (let i = 0; i < list.length; i++) {
            if (list[i].split('_')[0] === 'SequenceFlow') {
              flows.push(list[i])
            } else {
              activities.push(list[i])
            }
          }
          object.highlightedNodeId = { 'activities': activities, 'flows': flows }
        }
        object['processDefinitionId'] = this.processDefineId
        this.processTrajectoryOptions.data = object
        this.trajectoryDialogVisible = true
      }
    },
    initProcessDefineIdList(processDefKey) {
      this.$request({
        url: this.$api.versionList, // [必填] 请求地址
        data: { 'key': processDefKey }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        const list = response.data.grid.list
        for (let i = 0; i < list.length; i++) {
          const node = {
            'text': list[i].processDefineId,
            'value': list[i].processDefineId
          }
          this.selectParam.options.push(node)
        }
      })
    }
  },
  mounted() {
    // 从上一个上一个页面的路由数据中获取数据做查询初始化列表
    this.initProcessDefineIdList(this.$router.currentRoute.query.processDefKey)
  }
}
</script>
<style lang="scss" scoped>
.wfd-container {
  position: relative;
  margin-left: 30px;
  margin-right: 30px;
}

/*.el-table__body tr,*/
/*/deep/.el-table__body td {*/
    /*padding: 0;*/
    /*height: 300px;*/
    /*line-height: 30px;*/
/*}*/
</style>

