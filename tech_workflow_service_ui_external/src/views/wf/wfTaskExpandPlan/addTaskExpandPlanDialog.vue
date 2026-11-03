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
        <el-form ref="formRef" :model="model" :rules="rules" label-width="150px" style="height: 250px">
            <div class="col_td">
                <el-form-item label="流程分类：" prop="wfClassId" style="width: 83%">
                    
                    <!-- <WorkflowClassMultiTree
                            style="width: 64%"
                            ref="tree"
                            :isMultiSelect = "isMultiSelect"
                            :tenant="tenantId"
                            :fatherValue.sync="model.category"/> -->
                    <WorkflowClassMultiTree :fatherValue.sync="model.wfClassId" :checkValue.sync="model.wfClassId" :checkLabel.sync="model.wfClassName" :isMultiSelect = "isMultiSelect" />
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item  label="拓展字段键值：" prop="fieldKey">
                    <el-input  class="buttonClass"  size="mini" v-model="model.fieldKey"></el-input>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="拓展字段名称：" prop="fieldName">
                    <el-input  class="buttonClass"  size="mini" v-model="model.fieldName"></el-input>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="拓展字段数值：" prop="fieldValue">
                    <el-select class="buttonClass" size="mini" v-model="model.fieldValue" >
                        <el-option v-for="item in optionsValue" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="类型：" prop="fieldType">
                    <el-select class="buttonClass" size="mini" v-model="model.fieldType" >
                        <el-option v-for="item in optionsType" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="是否作为查询条件：" prop="isQueryCondition">
                    <el-select class="buttonClass" size="mini" v-model="model.isQueryCondition" >
                        <el-option v-for="item in optionsQuery" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="链接：">
                    <el-input  class="buttonClass" size="mini" v-model.trim="model.fieldUrl"></el-input>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="状态：" prop="fieldStatus">
                    <el-select class="buttonClass" size="mini" v-model="model.fieldStatus" >
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
        </el-form>
    </div>
</template>
<script>
import WorkflowClassMultiTree from '@/views/wf/components/WorkflowClassMultiTree/index.vue'
export default {
  components:{
    WorkflowClassMultiTree
  },
  name: 'AddTaskExpandPlanDialog',
  data() {
    return {
      isMultiSelect: false,
      options: [{ value: '1', label: '显示' }, { value: '0', label: '隐藏' }],
      optionsQuery: [{ value: 'Y', label: '是' }, { value: 'N', label: '否' }],
      optionsValue:
          [
            { value: 'field01', label: '拓展字段01' }, { value: 'field02', label: '拓展字段02' }, { value: 'field03', label: '拓展字段03' },
            { value: 'field04', label: '拓展字段04' }, { value: 'field05', label: '拓展字段05' }, { value: 'field06', label: '拓展字段06' },
            { value: 'field07', label: '拓展字段07' }, { value: 'field08', label: '拓展字段08' }, { value: 'field09', label: '拓展字段10' },
            { value: 'field11', label: '拓展字段11' }, { value: 'field12', label: '拓展字段12' }, { value: 'field13', label: '拓展字段13' },
            { value: 'field14', label: '拓展字段14' }, { value: 'field15', label: '拓展字段15' }, { value: 'field16', label: '拓展字段16' },
            { value: 'field17', label: '拓展字段17' }, { value: 'field18', label: '拓展字段18' }, { value: 'field19', label: '拓展字段19' },
            { value: 'field20', label: '拓展字段20' }
          ],
      optionsType: [{ value: '0', label: '字典' }, { value: '1', label: '字符串' }, { value: '2', label: '日期' }],
      model: {
        isQueryCondition: '',
        fieldType: '',
        fieldStatus: '',
        fieldValue: '',
        wfClassId: '',
        wfClassName: '',
        fieldName: '',
        fieldKey: '',
        fieldUrl: '',
        id: ''
      },
      rules: {
        wfClassId: [{ required: true, message: '请选择流程分类', trigger: ['blur', 'change'] }],
        fieldKey: [{ required: true, message: '请输入拓展字段键值', trigger: 'blur' }],
        fieldName: [{ required: true, message: '请输入拓展字段名称', trigger: 'blur' }],
        fieldValue: [{ required: true, message: '拓展字段数值', trigger: 'blur' }],
        fieldType: [{ required: true, message: '请选择类型', trigger: 'blur' }],
        isQueryCondition: [{ required: true, message: '请选择是否作为查询条件', trigger: 'blur' }],
        fieldStatus: [{ required: true, message: '请选择类型', trigger: 'blur' }]
      }
    }
  },
  methods: {
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
        margin-top: 22px;
    }
    .buttonClass{
        width:75%;
    }
</style>

