<!--
 * @Author: zhuofushen
 * @Date: 2020-03-13 15:45:43
 * @LastEditTime: 2020-04-09 21:01:29
 * @LastEditors: OBKoro1
 * @Description: 传递给弹框的表单组件
 * 注意点：
 * 1.el-form 标签中一定要设置ref名称，model数据，以及rules校验规则
 * 2.el-form-item 标签中必须定义prop属性来匹配校验的字段
 * 3.如果要自动过滤用户输入的首尾空白字符，可以给v-model添加trim修饰符：<input v-model.trim="msg">
 * 4.如果想自动将用户的输入值转为数值类型，可以给v-model添加number修饰符：<input v-model.number="age" type="number">
 -->
<template>
    <div>
        <el-form ref="formRef" :model="model" :rules="rules" label-width="160px" style="height: 350px">
            <div class="col_td">
                <el-form-item label="流程分类：">
                    <workflow-class-multi-tree
                    style="width: 100%"
                    ref="tree"
                    :notShow = "treeIsShow"
                    :tenant="model.tenantId"
                    :isMultiSelect = "isMultiSelect"
                    :fatherValue.sync = "model.classifyId"
                    :checkLabel.sync = "model.classifyName"/>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="流程实例id：" >
                    <el-input class="buttonClass" style="width:100%" :disabled="isShow" size="mini" v-model.trim="model.procInstId"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="移交人：" prop="userId" >
                    <SelectUserInput :fatherUserId.sync="model.userId" />
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="交接人：" prop="transferId" >
                    <SelectUserInput :fatherUserId.sync="model.transferId" />
                </el-form-item>
            </div>
            <div class="col_td1">
                <el-form-item label="交接日期：" >
                <ts-multi-date-picker
                        v-model="time"
                        :params="params2"
                />
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="是否永久移交：" >
                    <ts-multi-select v-model="model.isTransfer" :params="selectParam"></ts-multi-select>
                </el-form-item>
            </div>
            <div class="col_td" >
                <el-form-item label="移交人是否还有权限：" >
                    <ts-multi-select v-model="model.isPower" :params="selectParam"></ts-multi-select>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="是否转办历史任务："  >
                    <ts-multi-select v-model="model.isTransferHistoricalTasks" :params="selectParam"></ts-multi-select>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="状态：">
                    <ts-multi-select v-model="model.status" :params="selectStatusParam"></ts-multi-select>
                </el-form-item>
            </div>
            <div class="col_td1"  >
            <el-form-item label="描述" >
                <el-input class="buttonClass1"  type="textarea" size="mini" v-model.trim="model.describe"></el-input>
            </el-form-item>
            </div>
        </el-form>
    </div>
</template>
<script>

import WorkflowClassMultiTree from '../components/WorkflowClassMultiTree/index'
import SelectUserInput from '../components/SelectUserInput/index.vue'
export default {
  name: 'AddButtonFormDialog',
  components: { WorkflowClassMultiTree, SelectUserInput },
  data() {
    return {
      // 这里取出来后需要装换成Boolean
      isShow: eval(sessionStorage.getItem('isUpdate').toLowerCase()),

      treeIsShow: eval(sessionStorage.getItem('isUpdate').toLowerCase()),
      isMultiSelect: true,
      rules: {
        userId: [{ required: true, message: '请选择移交人', trigger: 'blur' }],
        transferId: [{ required: true, message: '请选择交接人', trigger: 'blur' }]
      },
      selectParam: {
        size: 'mini',
        options: [{ text: '是', value: '1' }, { text: '否', value: '0' }]
      },
      selectStatusParam: {
        size: 'mini',
        options: [{ text: '生效', value: '1' }, { text: '失效', value: '0' }]
      },
      params: {
        valueFormat: 'yyyy-MM-dd HH:MM:SS',
        type: 'datetime',
        size: 'mini',
        placeholder: '选择日期时间'
      },
      time: '',
      params2: {
        size: 'mini',
        type: 'datetimerange',
        placeholder: '选择日期时间',
        // format: 'yyyy/MM/dd',
        // valueFormat: 'yyyy-MM-dd',
        defaultTime: ['00:00:00', '00:00:00'],
        rangeSeparator: '-',
        startPlaceholder: '开始日期',
        endPlaceholder: '结束日期',
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() <= new Date().getTime() - 86400000// 当天之后的时间可选
          }
        }
      },
      model: {
        id: '',
        status: '',
        procInstId: '',
        classifyName: '',
        classifyId: '',
        transferId: '',
        userId: '',
        isTransferHistoricalTasks: '',
        alias: '',
        describe: '',
        startTime: '',
        endTime: '',
        isTransfer: '',
        tenantId: '',
        isPower: ''
      },
      options: [{ value: '1', label: '待办' }, { value: '2', label: '已办' }]
    }
  },
  methods: {
  },
  beforeMount() {
    const dialogObj = this.$tsVue.tansunDialogData(this)
    const { initData } = dialogObj
    const _this = this
    this.$nextTick(() => {
      _this.model.tenantId = initData.tenantId
    })
  },
  watch: {
    // 反显时间
    'model.startTime': function(newValue) {
      const timeArr = []
      timeArr[0] = this.model.startTime
      timeArr[1] = this.model.endTime
      this.time = timeArr
    },
    'model.procInstId': function(newValue) {
      if (newValue !== '') {
        this.treeIsShow = false
      } else {
        this.treeIsShow = true
      }
    },
    'time': function(newValue) {
      console.log(newValue)
      if (newValue != null) {
        this.model.startTime = newValue[0]
        this.model.endTime = newValue[1]
      }
    },
    'model.classifyId': function(newValue) {
      // 分类选中后要清空置灰流程实例id输入框
      if (this.model.classifyId != undefined) {
        if (this.model.classifyId.length !== 0) {
          this.model.procInstId = ''
          this.isShow = true
        } else {
          this.isShow = false
        }
      }
    }
  }
}
</script>
<style scoped>
    .col_td {
        width:50%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 15px;
    }
    .col_td1 {
        width:100%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 22px;
    }
    .buttonClass1{
        width:98%;
    }
    .buttonClass{
        width:75%;
    }
    /*.labelClass{*/
        /*width:25%;*/
    /*}*/
</style>

