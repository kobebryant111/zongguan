<!--
 * @Author: zhuofushen
 -->
<template>
    <div>
        <el-form ref="formRef" :model="model" :rules="rules" label-width="150px" style="height: 250px">
            <div class="col_td">
                <el-form-item label="流程分类：" prop="classifyId" style="width: 83%">
                    <workflow-class-multi-tree
                    :fatherValue.sync="model.classifyId"
                     :checkLabel.sync="model.classifyName"
                     :tenant="model.tenantId"
                     :isMultiSelect = "isMultiSelect" />
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="租户："  style="width: 83%">
                   <el-input    v-model="model.tenantName"  :readonly="true" ></el-input>
                    <!-- <ts-multi-select  style="width: 100%" v-model="model.tenantId" :params="selectParam"></ts-multi-select> -->
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="流程变量编码：" prop="varCode">
                    <el-input  class="buttonClass"  size="mini" v-model="model.varCode"></el-input>
                </el-form-item>
            </div>
          <div class="col_td">
            <el-form-item  label="流程变量名称：" prop="varName">
              <el-input  class="buttonClass"  size="mini" v-model="model.varName"></el-input>
            </el-form-item>
          </div>
            <div class="col_td"  >
                <el-form-item label="流程变量类型：" prop="varType">
                    <el-select class="buttonClass" size="mini" v-model="model.varType" >
                        <el-option v-for="item in optionsValue" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="流程变量分类：" prop="varClass">
                    <el-select class="buttonClass" size="mini" v-model="model.varClass" >
                        <el-option v-for="item in optionsType" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
        </el-form>
    </div>
</template>
<script>
import WorkflowClassMultiTree from '../components/WorkflowClassMultiTree/index'
export default {
  name: 'AddTaskExpandPlanDialog',
  components: { WorkflowClassMultiTree },
  data() {
    return {
      selectParam: {
        request: true, // 非必填 是否通过接口获取数据
        clearable: true,
        size: 'mini',
        filterable: true,
        text: 'name', // 下拉框显示内容，根据需要修改字段名称
        value: 'tenantId', // 下拉框对应文本的值，根据需要修改字段名称
        param: {}, // 追加参数
        multiple: false,
        api: this.$api.queryWfTenant, // 接口 传字符串url
        method: 'get', // 非必填 请求方式 默认post
        // 非必填 下拉数据默认取 res.data 为数据源,如果不满足 自行处理好 options 数据源
        resHandle: (res, callback) => {
          console.log(res)
          callback(res.data.grid.list)
        }
      },
      isMultiSelect: false,
      options: [{ value: '1', label: '显示' }, { value: '0', label: '隐藏' }],
      optionsQuery: [{ value: 'Y', label: '是' }, { value: 'N', label: '否' }],
      optionsValue:
          [
            { value: 'String', label: '字符串' }, { value: 'Boolean', label: '布尔类型' }, { value: 'date', label: '时间' },
            { value: 'Array', label: '数组' }, { value: 'Integer', label: '整数' }, { value: 'Long', label: '长整形' },
            { value: 'Double', label: '浮点型' }
          ],
      optionsType: [{ value: '1', label: '系统变量' }, { value: '0', label: '业务变量' }],
      model: {
        varCode: '',
        varName: '',
        varType: '',
        varClass: '',
        classifyId: '',
        classifyName: '',
        id: '',
        tenantId: '',
        tenantName: ''
      },
      rules: {
        varCode: [{ required: true, message: '请输入流程变量编码', trigger: 'blur' }],
        varName: [{ required: true, message: '请输入流程变量名称', trigger: 'blur' }],
        varType: [{ required: true, message: '请选择流程变量分类', trigger: 'blur' }],
        varClass: [{ required: true, message: '请选择流程变量值类型', trigger: 'blur' }],
        classifyId: [{ required: true, message: '请选择流程分类', trigger: ['blur', 'change'] }]
        // tenantId: [{ required: true, message: '请选择流程租户', trigger: 'blur' }]
      }
    }
  },
  methods: {
  },
  mounted() {
    const dialogObj = this.$tsVue.tansunDialogData(this)
    const { initData } = dialogObj
    const _this = this
    this.$nextTick(() => {
      _this.model.tenantId = initData.tenantId
      _this.model.tenantName = initData.tenantName
    })
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

