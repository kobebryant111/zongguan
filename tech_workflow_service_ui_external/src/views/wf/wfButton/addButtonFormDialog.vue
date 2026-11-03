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
        <el-form ref="formRef" :model="model" :rules="rules" label-width="100px" style="height: 180px">
            <div class="col_td">
                <el-form-item label="按钮编号" prop="key">
                    <el-input  class="buttonClass" size="mini" v-model.trim="model.key"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="按钮名称" prop="name">
                    <el-input class="buttonClass" size="mini" v-model.trim="model.name"></el-input>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="按钮别名" prop="alias">
                    <el-input type="textarea" class="buttonClass" placeholder="多个别名用逗号，分割" size="mini" v-model.trim="model.alias"></el-input>
                </el-form-item>
            </div>
            <div class="col_td"  >
                <el-form-item label="类型" prop="status">
                    <el-select class="buttonClass" size="mini" v-model="model.status" placeholder="请选择类型">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="col_td"  style="margin-top: 40px;">
                <el-form-item label="排序" prop="buttonSort">
                    <el-input class="buttonClass" size="mini" type="number" v-model.trim="model.buttonSort"></el-input>
                </el-form-item>
            </div>
        </el-form>
    </div>
</template>
<script>
export default {
  name: 'AddButtonFormDialog',
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
        key: '',
        name: '',
        alias: '',
        status: '',
        buttonSort: ''
      },
      rules: {
        key: [
          { required: true, message: '请输入按钮编号', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入按钮名称', trigger: 'blur' }
        ],
        alias: [
          { required: true, message: '请输入按钮别名', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请输入按钮状态', trigger: 'blur' }
        ],
        buttonSort: [
          { required: true, message: '请输入按钮排序', trigger: 'blur' }
        ]
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
        margin-top: 22px;
    }
    .buttonClass{
        width:95%;
    }
</style>

