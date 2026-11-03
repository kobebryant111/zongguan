<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <div class="layout" style="margin-top: 20px">
            <el-form inline>
                <el-form-item label="步骤名:">
                    <el-input  size="mini" v-model="query.name"></el-input>
                </el-form-item>
                <el-form-item label="流程名：">
                    <el-input size="mini" v-model="query.procDefName"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="buttonDiv">
            <el-button  type="primary" size="mini" icon="el-icon-s-promotion" @click="switchList">{{title}}</el-button>
        </div>
        <div>
            <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery">
                <el-table-column prop="procDefName" label="流程名" min-width="120" show-overflow-tooltip/>
                <el-table-column prop="name" label="步骤名"/>
                <el-table-column prop="avgTimeStr" label="平均耗时/工作时间"/>
                <el-table-column prop="maxTimeStr" label="最大耗时/工作时间"/>
                <el-table-column prop="assignee" label="处理人"/>
                <el-table-column prop="countNum" label="处理次数"/>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
export default {
  data() {
    return {
      title: '按步骤统计',
      isMultiSelect: false,
      icon: 'el-icon-arrow-down',
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      query: {}, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.wfProEffCount,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: { 'orderBy': 'a.assignee_ desc' }, // 选填 表示接口的必需恒定不变的参数
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
  methods: {
    switchList() {
      if (this.title === '按步骤统计') {
        // 切换按钮标题同时清空查询数据列表重新向别的接口发送请求数据
        this.title = '按员工统计'
        this.$refs['list'].resetData()
        this.$refs['list'].optionsSet.apiRequiredParams.orderBy = ''
        this.$refs['list'].serchHandel()
      } else {
        this.title = '按步骤统计'
        this.$refs['list'].resetData()
        this.$refs['list'].optionsSet.apiRequiredParams.orderBy = 'a.assignee_ desc'
        this.$refs['list'].serchHandel()
      }
    },
    beforeClose(data, done) {
      data = ''
      done()
    },
    // 弹窗清空数据清除
    cancel(data, done) {
      data = ''
      done()
    },
    // 重置
    reset() {
      this.$refs['list'].resetData()
      this.$refs.tree.resetChecked()
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    // 查询
    search() {
      this.$refs['list'].serchHandel()
    }
  }
}
</script>

<style lang="scss" scoped>
    .layout {
        margin-left: 10px;
        margin-top: 10px;
    }
    .buttonDiv{
        margin-bottom: 10px;
    }
    .mright_10 {
        margin-right: 10px;
    }

    .text_color {
        color: #3B8EDA
    }

    .funBox {
        display: flex;
        justify-content: flex-start;
    }
    .moneyCon {
        margin-top: 20px;
        display: flex;
        .item {
            margin-left: auto;
        }
    }
</style>
