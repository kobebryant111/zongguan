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
            <div class="col_td"  v-show="isShow">
                <el-form-item label="租户："  >
                   <el-input    v-model="model.tenantName"  :readonly="true" ></el-input>
                    <!-- <ts-multi-select  style="width: 70%"  v-model="model.tenantId" :params="selectParam"></ts-multi-select> -->
                </el-form-item>
            </div>
            <div class="col_td"  v-show="isShow">
                <el-form-item label="流程分类：" prop="classifyId" >
                   <workflow-class-multi-tree
                            style="width: 70%"
                            ref="tree"
                            :tenant="tenantId"
                            :isMultiSelect = "isMultiSelect"
                            :fatherValue.sync="model.classifyId"/>
                </el-form-item>
            </div>
            <div class="col_td" >
                <el-form-item label="流程键值：" prop="key"  >
                    <el-input  size="mini" style="width: 70%" v-model="model.key" :disabled="isDisabled"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="流程名称：" prop="name"  >
                    <el-input size="mini" style="width: 70%" v-model="model.name" :disabled="isDisabled" ></el-input>
                </el-form-item>
            </div>
<!--            <div class="col_td1"  >-->
<!--                <el-form-item label="流程描述" >-->
<!--                    <el-input  :autosize="{ minRows: 4, maxRows: 10}" type="textarea" size="mini" v-model.trim="model.description" :disabled="isDisabled"></el-input>-->
<!--                </el-form-item>-->
<!--            </div>-->
            <div class="col_td" style="margin-top: 70px">
                <el-form-item label="文件名称：" >
                    <ts-multi-upload ref="upload" :params="params" style="float: left">
                   <el-button slot="trigger" size="mini" type="primary"><i class="el-icon-upload el-icon--right"></i>选取文件</el-button>
                    </ts-multi-upload>
                   <div slot="tip" class="el-upload__tip">上传xml文件，批量上传zip压缩包</div>
                </el-form-item>
            </div>
            <div class="col_td" style="margin-top: 70px">
                <el-form-item label="是否批量："  >
                    <el-radio v-model="model.batch" label="1">是</el-radio>
                    <el-radio v-model="model.batch" label="0">否</el-radio>
                </el-form-item>
            </div>

        </el-form>
    </div>
</template>
<script>
import WorkflowClassMultiTree from '../components/WorkflowClassMultiTree/index'
import common from 'tsvue/lib/utils/ts-common'
export default {
  components: { WorkflowClassMultiTree },
  data() {
    return {
      params: {
        action: process.env.VUE_APP_BASE_API + this.$api.uploadBpmn, // 非必填 默认系统文件上传地址
        exts: 'zip|xml|bpmn', // 限制的文件内容
        autoUpload: false, // 自动上传 默认true
        showFileList: true, // 是否显示上传文件列表
        headers:{
          token: common.tansunGetToken()
        },
        limit: 1, // 上传数量限制
        // 上传附带参数
        data: {},
        onChange:(file)=>{
          console.log('file');
          console.log(file);
          this.model.hasFile='1'
        },
        onRemove:(file)=>{
          console.log('file');
          console.log(file);
          this.model.hasFile='0'
        },
        // 上传成功回调
        onSuccess: (response, file, fileList) => {
          if (response.status === 200) {
            this.$message.success(response.description)
            this.$refs.upload.clearFiles()
            const dialogData = this.$tsVue.tansunDialogData(this)
            // 关闭弹窗，刷新列表
            dialogData.close()
            this.$tsVue.loopfindDialog(this).$refs.parentRef.$parent.$parent.$parent.$refs.list.refreshDataSource()
          } else {
            this.$message.error(response.description)
          }
        },
        // 上传失败回调
        onError: (err, file, fileList) => {
          this.$message.error('上传失败')
        }
      },
      tenantId: '',
      // 这里取出来后需要装换成Boolean
      isMultiSelect: false,
      isShow: true,
      isDisabled: false,
      rules: {
        key: [{ required: true, message: '请输入流程键值', trigger: 'blur' }],
        name: [{ required: true, message: '请输入流程名称', trigger: 'blur' }],
        classifyId: [{ required: true, message: '请选择流程分类', trigger: ['blur', 'change'] }]
      },
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
          callback(res.data.grid.list)
        }
      },
      model: {
        key: '',
        name: '',
        tenantId: '',
        tenantName: '',
        description: '',
        classifyId: '',
        batch: '0',
        hasFile:'0'
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
    const dialogObj = this.$tsVue.tansunDialogData(this)
    const { initData } = dialogObj
    const _this = this
    this.$nextTick(() => {
      _this.tenantId = initData.tenantId
    })
  },
  watch: {
    'model.batch'(newValue, oldValue) {
      console.log("当前值")
      console.log(newValue)
      if (newValue === '1') {
        this.isShow = false
        this.isDisabled = true
        this.model.key = ''
        this.model.name = ''
        this.model.description = ''
        this.$refs.tree.resetChecked()
        // 改变校验规则 批量不做校验
        this.rules.key[0].required = false
        this.rules.name[0].required = false
        // this.rules.tenantId[0].required = false
        this.rules.classifyId[0].required = false
      } else {
        this.isShow = true
        this.isDisabled = false
        this.rules.key[0].required = true
        this.rules.name[0].required = true
        // this.rules.tenantId[0].required = true
        this.rules.classifyId[0].required = true
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
        width:90%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 22px;
    }
</style>

