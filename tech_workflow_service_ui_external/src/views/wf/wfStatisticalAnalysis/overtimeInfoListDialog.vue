<template>
  <div>
    <el-table :data="tableData" style="width: 100%" size="mini" height="400">
      <el-table-column prop="procInstId" label="流程显示名称" v-if="type === 1"></el-table-column>
      <el-table-column prop="modelName" label="流程实例名称" align="center"></el-table-column>
      <el-table-column prop="actName" label="活动名称" align="center" v-if="type === 2"></el-table-column>
      <el-table-column prop="instUser" label="发起人" align="center" v-if="type === 1"></el-table-column>
      <el-table-column prop="instStartTime" label="发起时间" align="center" v-if="type === 1"></el-table-column>
      <el-table-column prop="instTimeoutDiff" label="超时时长(分)" align="center" v-if="type === 1">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="formatOvertime(scope.row.instTimeoutDiff)">
            <span>{{ scope.row.instTimeoutDiff }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="actUser" label="参与人" align="center" v-if="type === 2"></el-table-column>
      <el-table-column prop="actStartTime" label="创建时间" align="center" v-if="type === 2"></el-table-column>
      <el-table-column prop="actTimeoutDiff" label="超时时长(分)" align="center" v-if="type === 2">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="formatOvertime(scope.row.actTimeoutDiff)">
            <span>{{ scope.row.actTimeoutDiff }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 15px;text-align:right;">
      <el-pagination
          @current-change="currentChange"
          :current-page="pageNum"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<script>

export default {
  props: {
    initData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      type: '',
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0
    }
  },
  mounted() {
    this.getInitData()
  },
  methods: {
    getInitData() {
      // 通过props属性获取
      this.type = this.initData.type
      this.getList(this.initData)
    },
    getList() {
      let param = {
        searchTimeBegin: this.initData.searchTimeBegin, // 入口为总查询/流程版本分片查询列表时时间填这里
        searchTimeEnd: this.initData.searchTimeEnd,
        searchTimeDetail: this.initData.searchTimeDetail, // 入口为按月查询列表时时间输入在这里
        qtype: this.initData.type, // 1查询实例 2查询活动
        entity: {
          modelName: this.initData.modelName,
          modelKey: this.initData.modelKey,
          modelVersion: this.initData.modelVersion// 入口为流程版本分片查询/按月查询列表时必填
        },
        page: this.pageNum, // 分页参数
        pageSize: this.pageSize
      }
      this.$request({
        url: this.$api.overtimeDuration, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post' // [选填] String 请求方式 get|post 默认post
      }).then((response) => {
        this.total = response.data.grid.total
        this.tableData = response.data.grid.list
      })
    },
    currentChange(number) {
      this.pageNum = number
      this.getList()
    },
    formatOvertime(value) {
      let time = parseInt(value)
      let day = parseInt(time / (60 * 24))
      let hour = parseInt(time % (60 * 24) / 60)
      let minute = time - day * 60 * 24 - hour * 60
      return day + '天' + hour + '时' + minute + '分'
    }
  }
}
</script>
<style scoped>

</style>

