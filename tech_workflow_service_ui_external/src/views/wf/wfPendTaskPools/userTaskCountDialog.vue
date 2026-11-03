<template>
    <div>
        <div>
            <ts-multi-table ref="list" :options="options">
                <el-table-column prop="orgName" min-width="80"  label="机构"/>
                <el-table-column prop="roleName" min-width="80"  label="角色"/>
                <el-table-column prop="deptName" min-width="80"  label="部门"/>
                <el-table-column prop="loginUserName" label="登陆名" min-width="80" show-overflow-tooltip/>
                <el-table-column prop="realUserName" label="真实姓名" min-width="80"/>
                <el-table-column prop="pendTaskCount" min-width="80"  label="待办数量"/>
                <el-table-column prop="completeTaskCount" label="已办数量"/>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
export default {
  name: 'WorkFlowInterveneDialog',
  props: {
    initData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      query: {},
      options: { // 必填
        initTable: false,
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.getUserTaskByProcessId,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: {}, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      }
    }
  },
  mounted() {
    this.getInitData()
  },
  methods: {
    getInitData() {
      // 通过props属性获取
      this.options.apiRequiredParams['processInstanceId'] = this.initData.processInstanceId
      if (this.$refs['list']) {
        this.$refs['list'].refreshDataSource()
      }
    }
  },
  watch: {
  }
}
</script>

<style scoped>

</style>
