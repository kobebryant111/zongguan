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
            <el-form-item label="租户编号" prop="tenantId">
                <el-input  size="mini" v-model.trim="model.tenantId"></el-input>
            </el-form-item>
            <el-form-item label="租户名称" prop="name">
                <el-input size="mini" v-model.trim="model.name"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remarks">
                <el-input  size="mini" type="textarea" placeholder="请输入内容" v-model.trim="model.remarks" maxlength="30" show-word-limit/>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
  name: 'AddTenantFormDialog',
  data() {
    return {
      model: {
        tenantId: '',
        remarks: '',
        name: '',
        id: ''
      },
      rules: {
        tenantId: [
          { required: true, message: '请输入租户编号', trigger: 'blur' }
        ],
        remarks: [
          { required: true, message: '请输入备注', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入租户名称', trigger: 'blur' }
        ]
      }
    }
  }
}
</script>
