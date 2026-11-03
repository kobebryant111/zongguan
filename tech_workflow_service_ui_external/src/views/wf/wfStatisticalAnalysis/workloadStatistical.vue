<!-- 工作量统计 -->
<template>
  <div style="margin-left: 30px;margin-right: 30px">
    <div class="layout" style="margin-top: 20px">
      <el-form ref="searchForm" inline>
        <el-form-item label="组织机构:">
          <el-input size="mini" @clear="onclear" @input="oninput" v-model="query.orgValue" type="text" clearable>
            <el-button slot="append" icon="el-icon-search" @click="selectOrg"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="时间段:">
          <el-date-picker
              style="width: 160px"
              v-model="query.startYear"
              value-format="yyyy"
              type="year"
              placeholder="开始年">
          </el-date-picker>
          <span> - </span>
          <el-date-picker
              style="width: 160px"
              v-model="query.endYear"
              value-format="yyyy"
              type="year"
              placeholder="结束年">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="24" style="padding-bottom: 10px">
      <el-col :span="14">
        <div class="ts-tabs-box">
          <el-tabs>
            <el-tab-pane label="工作量统计结果">
              <div>
                <div style="float: left">{{
                    orgValue + ' ' + query.startYear + '年 - ' + query.endYear + '年 工作量统计'
                  }}
                </div>
                <el-button @click="backLast" style="float: right;margin-bottom: 10px" size="mini">返回上一级</el-button>
              </div>
              <el-table :data="tableData" style="width: 100%" size="mini" height="400">
                <el-table-column prop="name" label="组织名称" v-if="qtype == 1">
                  <template slot-scope="scope">
                    <el-button @click="queryNext(scope.row)" size="mini" type="text">{{ scope.row.name }}</el-button>
                  </template>
                </el-table-column>
                <el-table-column prop="name" label="年份" v-if="qtype == 2"></el-table-column>
                <el-table-column prop="name" label="月份" v-if="qtype == 3"></el-table-column>
                <el-table-column prop="createCnt" label="创建任务数"></el-table-column>
                <el-table-column prop="handleCnt" label="处理任务数"></el-table-column>
                <el-table-column label="操作" align="center" v-if="qtype == 1">
                  <template slot-scope="scope">
                    <el-button @click="queryByYear(scope.row)" size="mini" type="text">按年</el-button>
                    <el-button @click="queryByMonth(scope.row)" size="mini" type="text">按月</el-button>
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
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="ts-tabs-box">
          <el-tabs>
            <el-tab-pane label="工作量统计图表">
              <div id="workload" class="chart"></div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
    <el-dialog title="组织机构" :visible.sync="dialogVisible" width="500px">
      <tree height="330px" @node-click="nodeClick" type="orguser"></tree>
    </el-dialog>
    <tree height="330px" @treeData="(tree,treeList)=>{ this.treeData = this.keyChange(tree) }" type="orguser"
          style="display: none"></tree>
  </div>
</template>

<script>
import echarts from 'echarts'
import tree from '@/components/Tree'

export default {
  name: 'workloadStatistical',
  components: {
    tree
  },
  data() {
    return {
      dialogVisible: false,
      entity: null,
      orgValue: '',
      query: {
        orgValue: '',
        startYear: '',
        endYear: ''
      },
      qtype: 1,//查询类型：1总体 2按年 3按月
      tableData: [],
      originTableData:[],
      pageSize: 10,
      pageNum: 0,
      total: 0,
      chartOption: {},
      workloadChart: null,
      treeData: [],
      treeList: []
    }
  },
  mounted() {
    this.initChart()
  },
  methods: {
    selectOrg() {
      this.dialogVisible = true
    },
    nodeClick(data) {
      this.entity = data
      this.entity = this.keyChange(this.entity)
      this.selectEntity = this.entity
      this.orgValue = data.name
      this.query.orgValue = data.name
      this.dialogVisible = false
    },
    search() {
      this.orgValue = this.query.orgValue
      if (!this.query.startYear) {
        this.$tsVue.warn('起始年份不能为空')
        return
      }
      if (!this.query.endYear) {
        this.$tsVue.warn('结束年份不能为空')
        return
      }
      if (parseInt(this.query.startYear) > parseInt(this.query.endYear)) {
        this.$tsVue.warn('起始年份不能大于结束年份')
        return
      }
      if (parseInt(this.query.endYear) - parseInt(this.query.startYear) > 11) {
        this.$tsVue.warn('起止年份不能超过12年')
        return
      }
      this.qtype = 1
      this.queryWorkload(this.entity)
    },
    reset() {
      this.query = {
        orgValue: '',
        startYear: '',
        endYear: ''
      }
      this.orgValue = ''
      this.qtype = ''
      this.entity = null
    },
    queryWorkload(entity) {
      this.tableData = []
      let param = {
        searchTimeBegin: this.query.startYear,
        searchTimeEnd: this.query.endYear,
        qtype: this.qtype,
        entity: entity
      }
      this.$request({
        url: this.$api.workloadStatistics, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post' // [选填] String 请求方式 get|post 默认post
      }).then((response) => {
        if (this.qtype === 1) {
          this.pageSize = 10
        }
        //按年查询 按月查询
        if (this.qtype === 2 || this.qtype === 3) {
          this.pageSize = 12
        }

        this.pageNum = 1
        this.total = response.data.length
        this.originTableData = this.setColumnList(response.data,12)
        this.tableData = this.originTableData[0]

        //设置图表数据
        this.updateBarChart()
      })
    },
    queryNext(row) {
      // 查询下一级
      this.treeFind(this.treeData, row.id)
      if (this.selectEntity.subs && this.selectEntity.subs.length > 0) {
        this.orgValue = row.name
        this.qtype = 1
        this.queryWorkload(this.selectEntity)
      }
    },
    queryByYear(row) {
      //按年查询
      this.orgValue = row.name
      this.qtype = 2
      this.treeFind(this.treeData, row.id)
      this.queryWorkload(this.selectEntity)
    },
    queryByMonth(row) {
      //按月查询
      this.orgValue = row.name
      this.qtype = 3
      this.treeFind(this.treeData, row.id)
      this.queryWorkload(this.selectEntity)
    },
    currentChange(number) {
      //切换页数
      this.pageNum = number
      this.tableData = this.originTableData[this.pageNum-1]
      //设置图表数据
      this.updateBarChart()
    },
    backLast() {
      //回退到上一级
      if (this.qtype === 1 && this.selectEntity) {
        let pid = this.selectEntity.pid
        this.treeFind(this.treeData, pid)
        this.queryWorkload(this.selectEntity)
      }

      if (this.qtype === 2 || this.qtype === 3) {
        this.qtype = 1
        // if(this.selectEntity.type === 'user'){
        //   this.queryWorkload(this.selectEntity)
        // } else {
        //   this.treeFind(this.treeData, this.selectEntity.pid)
        //   this.queryWorkload(this.selectEntity)
        // }
        this.treeFind(this.treeData, this.selectEntity.pid)
        this.queryWorkload(this.selectEntity)
      }
      this.orgValue = this.selectEntity.name
    },
    updateBarCHart(){
      this.chartOption.xAxis[0].data = []
      this.chartOption.series[0].data = []
      this.chartOption.series[1].data = []
      this.tableData.forEach((item) => {
        this.chartOption.xAxis[0].data.push(item.name)
        this.chartOption.series[0].data.push(item.createCnt)
        this.chartOption.series[1].data.push(item.handleCnt)
      })
      this.workloadChart.setOption(this.chartOption)
    },
    initChart() {
      this.workloadChart = echarts.init(document.getElementById('workload'))

      this.chartOption = {
        color: ['#007BFF', '#4BDAFF', '#FFC336'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter(params) {
            let data =
                params[0].name +
                '</br>' +
                '创建：' +
                params[0].data +
                '件' +
                '</br>' +
                '处理：' +
                params[1].data +
                '件' +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          // itemWidth: 10,
          // itemHeight: 10,
          // icon:'circle',
          data: ['创建', '处理']
        },
        xAxis: [
          {
            // name: "(组织)",
            // nameLocation: "end", //可选：'start'，'middle'，'end'
            type: 'category',
            axisTick: { show: false },
            data: []
          }
        ],
        yAxis: [
          {
            name: '(件)',
            nameLocation: 'end', //可选：'start'，'middle'，'end'
            nameGap: 8,
            nameTextStyle: { padding: [5, 40, 0, 0] },
            type: 'value',
            axisLabel: {
              show: true
            },
            splitLine: {
              show: true,
              lineStyle: {
                width: 0.5
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            name: '创建',
            type: 'bar',
            data: []
          },
          {
            name: '处理',
            type: 'bar',
            data: []
          }
        ]
      }
      this.workloadChart.setOption(this.chartOption)
      window.addEventListener('resize', function() {
        if (this.workloadChart) {
          this.workloadChart.resize()
        }
      })
    },
    oninput() {
      this.query.orgValue = ''
    },
    onclear() {
      this.entity = null
    },
    keyChange(objTree) {
      //把children 换成 subs
      for (let key in objTree) {
        if (objTree.hasOwnProperty(key)) {
          const current = objTree[key]
          if (current instanceof Object) {
            this.keyChange(current)
          }
          if (key === 'children') {
            objTree['subs'] = current
            delete objTree['children']
          }
        }
      }
      return objTree
    },
    treeFind(data, id) {
      //查找指定节点
      if(id){
        this.selectEntity = null
        data.map(item => {
          if (item.id == id) {
            this.selectEntity = item	// 结果赋值
          } else {
            if (item.subs) {
              this.treeFind(item.subs, id)
            }
          }
        })
      }
    },
    setColumnList(rows, num) {
      let ArrayResult = []
      if (rows.length > 0) {
        let rowArray = []
        for (let i = 0; i < rows.length; i++) {
          rowArray.push(rows[i])
          if (rowArray.length === num && i !== 0) {
            ArrayResult.push(rowArray)
            rowArray = []
          }
          if ((rows.length - 1) === i && rowArray.length !== num) {
            ArrayResult.push(rowArray)
          }
        }
      }
      return ArrayResult
    }
  }
}
</script>

<style lang="scss" scoped>
.ts-tabs-box {
  /deep/ .el-tabs__content {
    padding-top: 0 !important;
  }
}

.chart {
  width: 500px;
  height: 480px;
}

.layout {
  padding: 10px;
}
</style>
