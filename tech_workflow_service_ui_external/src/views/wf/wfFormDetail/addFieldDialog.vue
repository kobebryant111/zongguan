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
                <el-form-item label="字段名称：" prop="formCode">
                    <el-input  class="buttonClass" size="mini" v-model.trim="model.name"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item  label="字段描述：">
                    <el-input class="buttonClass" size="mini" v-model.trim="model.comments"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="字段类型：" >
                    <ts-multi-select class="buttonClass" v-model="model.fieldType" :params="selectParam"></ts-multi-select>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item  label="默认值：">
                    <el-input class="buttonClass" size="mini" v-model.trim="model.defaultValue"></el-input>
                </el-form-item>
            </div>
            <div class="col_td2"  >
                <el-form-item label="备注信息：">
                    <el-input type="textarea" class="buttonClass" placeholder="请输入内容" size="mini" v-model.trim="model.remarks"></el-input>
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
        name: '',
        comments: '',
        defaultValue: '',
        fieldType: '',
        remarks: ''
      },
      selectParam: {
        options: [{ text: '字符串', value: 'String' }, { text: '长整型', value: 'Long' },
          { text: '整型', value: 'Integer' }, { text: '浮点型', value: 'Double' }, { text: '数组', value: 'java.util.List' }] },
      rules: {
        name: [{ required: true, message: '请输入字段名称', trigger: 'blur' }]
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

