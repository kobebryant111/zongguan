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
            <el-form-item label="测试编号" prop="planCode">
                <el-input  size="mini" v-model.trim="model.planCode"></el-input>
            </el-form-item>
            <el-form-item label="测试名称" prop="planName">
                <el-input size="mini" v-model.trim="model.planName"></el-input>
            </el-form-item>
            <el-form-item label="关联的流程键值" prop="processDefKey">
                <ts-multi-select v-model="model.processDefKey" :params="selectParam6" ></ts-multi-select>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
  name: 'addTestFormDialog',
  data() {
    return {
      model: {
          planCode: '',
          planName: '',
          processDefKey: ''
      },
      rules: {
          planCode: [
          { required: true, message: '请输入测试编号', trigger: 'blur' }
        ],
          planName: [
          { required: true, message: '请输入测试名称', trigger: 'blur' }
        ],
          processDefKey: [
          { required: true, message: '请输入关联的流程键值', trigger: 'blur' }
        ]
      },
      selectParam6: {
          exactBtn:true,//选填是否需要精确查询的按钮，默认不需要
          visibeInit: true,
          method: 'get',
          request: true, // 非必填 是否通过接口获取数据
          paramMode: 'params', // 非必填 传递参数方式 data|params 默认data
          pagination: true, // 非必填 是否分页
          selectName: '', // 分页情况下 如果有赋默认值时需要手动配置默认选中的name
          selectList: [{ roleName: '经办岗', roleCode: 'RL0001', id: '111' }], // 分页多选情况下 默认选中数据 如有用到其他属性请自行配置已选list 否则只会返回带 key\value 对象的list
          clearable: true,
          filterable: true,
          remote: true, // 非必填 是否通过远程搜索
          optionWidth: 600, // 非必填 宽度默认400
          header: ['流程键值', '流程名称','租户名称'], // 表格的头部
          body: ['modelKey', 'modelName','tenantName'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
          formatter:{
               createTime:this.formatDate //自定义 字段格式化方法，如果有取字典值会被覆盖
          },
          text: 'modelName', //拉框显示内容，根据需要修改字段名称
          value: 'modelKey', // 下拉框对应文本的值，根据需要修改字段名称
          pageNum: 1, // 非必填 当前页 分页有效
          pageSize: 5, // 非必填 每页数量 分页有效
          param: {}, // 追加参数
          multiple: false,
          alias: 'modelKey', // 查询条件的别名
          api: this.$api.processList, // 接口
          // 非必填 下拉数据默认取 res.data.grid.list 为数据源,如果不满足 自行处理好 options 数据源
          resHandle: (res, callback) => {
                console.log(res)
                const options = res.data.grid.list// 数据源
                const hasNextPage = res.data.grid.hasNextPage// 是否有下一页
                callback(options, hasNextPage)
          }

  }
    }
  }
}
</script>
