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
        <el-form ref="formRef" :model="model" :rules="rules" label-width="100px">
            <el-form-item label="流程键值" prop="processDefKey">
               <ts-multi-select v-model="model.processDefKey" :params="selectParam6" @change="onchange6"></ts-multi-select>
            </el-form-item>
            <el-form-item label="流程名称" prop="processName">
                <el-input  size="mini" v-model.trim="model.processName" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="租户编码" prop="tenantId">
                <el-input size="mini" v-model.trim="model.tenantId" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="租户名称" prop="tenantName">
                <el-input  size="mini" v-model.trim="model.tenantName" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="是否开启" prop="isOpen">
                <ts-multi-select v-model="model.isOpen" :params="selectParam2"></ts-multi-select>
            </el-form-item>
            <el-form-item label="任务处理人" prop="taskAssignee">
                <el-input size="mini" v-model.trim="model.taskAssignee" ></el-input>
            </el-form-item>
            <el-form-item label="跳转任务节点id" prop="targetNodeId">
                <el-input size="mini" v-model.trim="model.targetNodeId" ></el-input>
            </el-form-item>
            <el-form-item label="跳转任务节点名称" prop="targetNodeName">
                <el-input size="mini" v-model.trim="model.targetNodeName" ></el-input>
            </el-form-item>
            <el-form-item type="hidden" prop="modify">
                <el-input type="hidden" size="mini" v-model.trim="model.targetNodeName" ></el-input>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
  name: 'addPlanFormDialog',
  data() {
    return {
      model: {
          processDefKey: '',
          processName: '',
          tenantId: '',
          tenantName:'',
          isOpen: null,
          taskAssignee:'',
          targetNodeId:'',
          targetNodeName:''
      },
      rules: {
          planCode: [
          { required: true, message: '请选择流程', trigger: 'blur' }
        ],
          modelKey: [
          { required: true, message: '选择是否开启', trigger: 'blur' }
        ]
      },
      selectParam2: {options: [{text: '是', value: 'Y'}, {text: '否', value: 'N'}]},
        selectParam6: {
            request: true, // 非必填 是否通过接口获取数据
            clearable: true,
            size: 'mini',
            filterable: true,
            text: 'modelKey', // 下拉框显示内容，根据需要修改字段名称
            value: 'modelKey', // 下拉框对应文本的值，根据需要修改字段名称
            param: {}, // 追加参数
            multiple: false,
            api: this.$api.processList, // 接口 传字符串url
            method: 'get', // 非必填 请求方式 默认post
            // 非必填 下拉数据默认取 res.data 为数据源,如果不满足 自行处理好 options 数据源
            resHandle: (res, callback) => {
                console.log(res)
                callback(res.data.grid.list)
            }
        }
    }
  },
  methods:{
      onchange6(value, selects) {
          // 选中的value值
          console.log(value)
          // 选中的options数组,单选时取 selects[0]
          console.log(selects)
          console.log(selects[0])
         if(selects[0]!==undefined) {
             this.model.processName = selects[0].modelName
             this.model.tenantId = selects[0].tenantId
             this.model.tenantName = selects[0].tenantName
         }
      }
  }
}
</script>
