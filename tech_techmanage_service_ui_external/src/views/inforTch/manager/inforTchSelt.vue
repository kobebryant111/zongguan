<!-- 信息科技外包申请 -->
<template>
  <div>
    <div class="typeset-container">
      <div class="typeset-head">
        <el-form ref="form" :model="form">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="外包需求编号" label-width="120px">
                <el-input v-model="form.outReqNum" placeholder="请输入"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="申请人" label-width="120px">
                <el-input v-model="form.empname" placeholder="请输入"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="申请部门" label-width="120px">
                <!-- <el-input v-model="form.alctionLocation" placeholder="请输入"/> -->
                <el-select v-model="form.alctionDepid" filterable placeholder="请输入" @change="orgTreeDataChange()">
                  <el-option
                    v-for="item of orgTreeData"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="外包类别" prop="outType" label-width="120px">
                <el-select v-model="form.outType" placeholder="请输入">
                  <el-option label="咨询规划类" value="zxgf"/>
                  <el-option label="开发测试类" value="kfcs"/>
                  <el-option label="运行维护类" value="ywwh"/>
                  <el-option label="安全服务类" value="aqfw"/>
                  <el-option label="业务支持类" value="ywzc"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="外包需求名称" prop="outreqName" label-width="120px">
                <el-input v-model="form.outreqName" placeholder="请输入"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="外包用工部门" prop="outDepid" label-width="120px">
                <el-select v-model="form.outDepid" placeholder="请输入" @change="orgTreeDataChange()">
                  <el-option
                    v-for="item of orgTreeData"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="button_zb">
          <el-button slot="trigger" type="primary" @click="submitBtn">查询</el-button>
          <el-button slot="trigger" type="primary" @click="clearSearch">重置</el-button>
          <el-button slot="trigger" type="primary" @click="submitBtnInfo">导出</el-button>
          <!-- <el-button v-show="ture" slot="trigger" type="primary">提交</el-button> -->
        </div>
        <div class="projectContainer">
          <el-table
            :data="crewData"
            border
            style="width:100%"
            height="550"
          >
            <el-table-column prop="outReqNum" align="center" label="外包需求编号" width="180"/>
            <el-table-column prop="outreqName" align="center" label="外包需求名称" width="150"/>
            <el-table-column prop="outType" align="center" label="外包类别" width="150">
              <template slot-scope="scope">
                <span>{{ getDictName(personAttrDictId,scope.row.outType) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="empname" align="center" label="申请人" width="150"/>
            <el-table-column prop="alctionLocation" align="center" label="申请部门" width="150"/>
            <el-table-column :formatter="formatDate" prop="alctionTime" align="center" label="申请时间" width="150"/>
            <el-table-column prop="outLocation" align="center" label="外包用工部门" width="150"/>
            <el-table-column prop="flag" align="center" label="审批状态" width="150">
              <template slot-scope="scope">
                <span>{{ dictName( scope.row.flag) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  size="small"
                  @click="aplyDetail(scope.row,scope.$index)">申请详情</el-button>
                <el-button
                  type="text"
                  size="small"
                  @click="signProvide(scope.row,scope.$index)">审批详情</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pager_zj">
            <pager
              ref="pager"
              :fns="getPager"
              :total="totalNumber"
              :page-size="size"
              :current-page="index"
              v-model="$attrs"
            />
          </div>
        </div>
      </div>

    </div>
  </div>
</template>
<script>
import PersonTable from '@/api/researchManager/rpersonTable'
import { queryList, queryListInfo } from '@/api/inforTch/inforTchArvl.js'
import pager from '@/components/Pager'
import common from '@/api/common'
// eslint-disable-next-line
import { getDictName } from '@/utils/dict'
export default {
  name: 'InforTchSelt',
  components: {
    pager
  },
  data() {
    return {
      pagers: this.$route.query,
      form: {},
      orgTreeData: [],
      crewData: [],
      personAttrDictId: 'XXJK_ARVL_OUTTYPE',
      flagId: 'XXJK_FLAG_ID',
      totalNumber: 0,
      size: 10, // 初始值 默认：10
      index: 1 // 初始值 默认：1
    }
  },
  created() {
    if (this.pagers.size) {
      this.size = this.pagers.size
      this.index = this.pagers.index
      this.form = this.pagers.form
      this.initData()
    } else {
      this.initData()
    }
  },
  mounted() {
    this.getInit()
  },
  methods: {
    // 初始化
    initData() {
      PersonTable.queryOrgTree().then(res => {
        if (res.data.status === 200) {
          this.orgTreeData = res.data.results[0].children
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 重置按钮
    clearSearch() {
      this.form = {}
    },
    // 分页初始化
    getInit() {
      this.getPager(this.size, this.index)
    },
    // 查询
    submitBtn() {
      this.index = 1
      this.getPager(this.size, this.index)
    },
    // daochu
    submitBtnInfo() {
      this.$confirm('是否导出？', '导出确认', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' }).then(_ => {
        const params = { ...this.form }
        params.size = this.size
        params.index = this.index
        params._isDown = 'Y'
        queryListInfo(params).then(res => {
          common.download(res.data, self, '信息科技外包立项查询表.xls')
        })
      })
    },
    // 分页
    getPager: function(size, index) {
      const params = { ...this.form }
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    // 获取列表数据
    getPagerList(params) {
      this.loading = true
      const self = this
      const data = common.paramsFormat(params)
      queryList(data).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          self.crewData = res.data.results
          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            return false
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 日期格式
    formatDate(row, column, cellValue) {
      if (!cellValue) {
        return ''
      }
      const date = new Date(cellValue)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    },
    aplyDetail(row, index) {
      row.buttonStutas = 'xq'
      row.size = this.size
      row.index = this.index
      row.form = this.form
      this.$router.push({
        name: 'TchSeltDetail',
        query: row
      })
    },
    signProvide(row, index) {
      row.buttonStutas = 'sp'
      row.size = this.size
      row.index = this.index
      row.form = this.form
      this.$router.push({
        name: 'TchSeltDetail',
        query: row
      })
    },
    dictName(val) {
      if (val === 0) {
        return '退回申请人'
      } else if (val === 1) {
        return '审批中'
      } else if (val === 2) {
        return '审批完成'
      } else {
        return '草稿'
      }
    }
  }
}
</script>

<style scoped>

.typeset-container {
  padding: 10px;
}

.typeset-head {
  width: 100%;
  height: 100%;
  background-color: #ffffff;
  padding: 10px;
  padding-top: 20px;
}

.button_zb {
  background-color: #ffffff;
  padding-top: 20px;
  width: 85%;
  display: flex;
  justify-content: flex-end;
  gap: 5px;
}
/* 分页组件位置 */
.pager_zj {
  width: 100%;
  display: flex;
  padding: 10px;
  flex-direction: row-reverse;
}
.projectContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 15px;
}

.projectContainer_head {
  float: right;
  margin-bottom: 10px;
}
</style>
