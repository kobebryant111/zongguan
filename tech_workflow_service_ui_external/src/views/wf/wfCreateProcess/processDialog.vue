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
        <el-form ref="formRef" :model="model" :rules="rules" label-width="170px" style="height: 250px">
            <div class="col_td1" v-show="model.isCreated">
                <el-form-item label="从模板创建："  prop="templateModelId" >
                    <ts-multi-select  style="width: 50%" v-model="model.templateModelId" :params="selectTemplateParam"></ts-multi-select>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="租户："  >
                  <el-input    v-model="model.tenantName"  :readonly="true" ></el-input>
                   <!--
                    <ts-multi-select  style="width: 64%" v-model="model.tenantId" :params="selectParam"></ts-multi-select>
                 -->

                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="流程分类："   prop="classifyId">
                  <workflow-class-multi-tree
                            style="width: 64%"
                            ref="tree"
                            :isMultiSelect = "isMultiSelect"
                            :tenant="tenantId"
                            :fatherValue.sync="model.category"/>
                </el-form-item>
            </div>
            <div class="col_td" >
                <el-form-item label="流程键值："  prop="modelKey">
                    <el-input  size="mini" style="width: 64%" v-model="model.modelKey" ></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="流程名称："  prop="modelName">
                    <el-input size="mini" style="width: 64%" v-model="model.modelName" ></el-input>
                </el-form-item>
            </div>
            <div class="col_td1"  >
                <el-form-item label="流程描述" >
                    <el-input  :autosize="{ minRows: 4, maxRows: 10}" type="textarea" size="mini" v-model.trim="model.description"></el-input>
                </el-form-item>
            </div>
        </el-form>
    </div>
</template>
<script>

import WorkflowClassMultiTree from '../components/WorkflowClassMultiTree/index'
export default {
  components: { WorkflowClassMultiTree },

  data() {
    return {
      isMultiSelect: false,
      rules: {
        modelKey: [
          { required: true, message: '请输入流程键值', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              let text = value.trim()
              let reg = /^[0-9]/
              if(reg.test(text)){
                callback(new Error('流程键值不能以数字开头'))
              } else {
                callback()
              }
            }, trigger: 'blur'
          }
        ],
        modelName: [{ required: true, message: '请输入流程名称', trigger: 'blur' }],
        // tenantId: [{ required: true, message: '请选择租户', trigger: 'blur' }],
        category: [{ required: true, message: '请选择流程分类', trigger: 'blur' }]
      },
      tenantId: '',
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
      selectTemplateParam: {
        request: true, // 非必填 是否通过接口获取数据
        clearable: true,
        size: 'mini',
        paramMode:'params',
        filterable: true,
        text: 'modelName', // 下拉框显示内容，根据需要修改字段名称
        value: 'id', // 下拉框对应文本的值，根据需要修改字段名称
        param: { tenantId: this.$store.state.user.userInfo.tenantId}, // 追加参数
        multiple: false,
        api: this.$api.templateList, // 接口 传字符串url
        method: 'get', // 非必填 请求方式 默认post
        // 非必填 下拉数据默认取 res.data 为数据源,如果不满足 自行处理好 options 数据源
        resHandle: (res, callback) => {
          console.log(res)
          callback(res.data.grid.list)
        }
      },

      model: {
        isCreated: true,
        modelKey: '',
        modelName: '',
        tenantId: '',
        tenantName: '',
        classify: '',
        description: '',
        category: ''
      },
      options: [{
        value: '1',
        label: '待办'
      }, {
        value: '2',
        label: '已办'
      }]

    }
  },
  methods: {
  },

  mounted() {
    // 通过公共方法获取初始化数据
    const dialogObj = this.$tsVue.tansunDialogData(this)
    const { initData } = dialogObj
    const _this = this
    this.$nextTick(() => {
      _this.tenantId = initData.tenantId
    })

    console.log('通过公共方法获取初始化数据:', initData)
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
        width:90%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 22px;
    }
</style>

