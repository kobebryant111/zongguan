<!--
 * @Author: zhuofushen
 * @Date: 2020-05-15
 * @LastEditors: zhuofushen
 * @LastEditTime: 2020-05-15
 * @Description: 工作流类型选择框组件
 -->
<template>
  <div>
    <ts-multi-select v-model="userId" :params="selectUserParam" @change="onchange"></ts-multi-select>
  </div>
</template>

<script>
export default {
  name: 'SelectUserInput',
  components: {},
  props: { 'fatherUserId': String },
  data() {
    return {
      userId: this.fatherUserId,
      selectUserParam: {
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        pagination: true, // 非必填 是否分页
        // 分页多选情况下 如果有赋默认值时需要手动配置默认选中的options
        selectOptions: [],
        clearable: true,
        filterable: true,
        size: 'mini',
        paramMode: 'params', // 非必填 传递参数方式 data|params 默认data
        remote: true, // 非必填 是否通过远程搜索
        optionWidth: 250, // 非必填 宽度默认400
        header: ['账号', '用户名'], // 表格的头部
        body: ['userLoginName', 'userName'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'userLoginName', // 下拉框显示内容，根据需要修改字段名称
        value: 'userLoginName', // 下拉框对应文本的值，根据需要修改字段名称
        pageNum: 1, // 非必填 当前页 分页有效
        pageSize: 5, // 非必填 每页数量 分页有效
        param: {}, // 追加参数
        multiple: false, // 是否多选
        api: this.$api.userList// 接口
        // 非必填 下拉数据默认取 res.data.grid.list 为数据源,如果不满足 自行处理好 options 数据源
        // resHandle: (res, callback) => {
        //   // console.log(res)
        //   const options = res.data.grid.list// 数据源
        //   const hasNextPage = res.data.grid.hasNextPage// 是否有下一页
        //   callback(options, hasNextPage)
        // }
      }
    }
  },
  mounted() {
  },
  methods: {
    onchange(value, selects) {
      console.log(value)
    }
  },
  watch: {
    userId: function(newValue) {
      // 点击选中后数据变化把值给父组件送出去
      this.$emit('update:fatherUserId', newValue)
    },
    fatherUserId: function(newValue) {
      // 父组件数据变化了给子组件更新下
      this.userId = newValue
    }
  }
}
</script>

<style lang='scss' scoped >

</style>
