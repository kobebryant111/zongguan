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
        <el-form ref="formRef" :model="model" :rules="rules" label-width="100px" style="height: 250px">
            <div class="col_td">
                <el-form-item label="表单编号：" prop="formCode">
                    <el-input  class="buttonClass" size="mini" v-model.trim="model.formCode"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item  label="表单名称：" prop="formName">
                    <el-input class="buttonClass" size="mini" v-model.trim="model.formName"></el-input>
                </el-form-item>
            </div>
            <div class="col_td2"  >
            <el-form-item label="表单地址：" >
                <el-input class="buttonClass" size="mini" v-model.trim="model.formUrl"></el-input>
            </el-form-item>
            </div>
            <div class="col_td2"  >
                <el-form-item label="备注信息：">
                    <el-input type="textarea" class="buttonClass" placeholder="多个别名用逗号，分割" size="mini" v-model.trim="model.remarks"></el-input>
                </el-form-item>
            </div>
        </el-form>
    </div>
</template>
<script>
export default {
  name: 'AddFormDialog',
  data() {
    return {
      options: [{
        value: '1',
        label: '待办'
      }, {
        value: '2',
        label: '已办'
      }],
      model: {
        id: '',
        formCode: '',
        formName: '',
        formUrl: '',
        remarks: ''
      },
      rules: {
        formCode: [
          { required: true, message: '请输入表单编号', trigger: 'blur' }
        ],
        formName: [
          { required: true, message: '请输入表单名称', trigger: 'blur' }
        ]
      }
    }
  }
}
</script>
<style scoped>
    .col_td {
        width:48%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 22px;
    }

    .col_td2{
        width:100%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 22px;
    }

    .buttonClass{
        width:94%;
    }
</style>

