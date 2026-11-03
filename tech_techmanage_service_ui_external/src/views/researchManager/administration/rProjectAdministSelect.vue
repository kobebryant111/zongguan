
<template>
  <el-col
    v-loading="loading"
    :span="6"
    style="padding:10px;background:white"
    element-loading-text="部门数据加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="gba(0,0,0,0.8)">
    <el-row style="height">
      <el-form :model="searchForm">
        <el-row :gutter="10" style="margin-bottom:10px">
          <el-col :span="12">
            <el-input v-model="searchForm.chargeName" placeholder="请输入研发部门" />
          </el-col>
          <el-col :span="12">
            <el-input v-model="searchForm.empname" placeholder="请输入员工姓名" />
          </el-col>

        </el-row>
        <el-row style="text-align:right">
          <clean-btn :forms="searchForm" size="mini" />
          <buttonOrange :fns="searchBtn" style="margin-left: 10px" button-name="查询" size="mini"/>
          <buttonOrange :fns="submitBtnInfo" style="margin-left: 10px" button-name="导出" size="mini"/>
        </el-row>
      </el-form>
    </el-row>
    <el-row style="padding:10px">
      <el-col>
        <span class="el-icon-warning gray" style="margin-right:10px">研发项目待分配</span>
      </el-col>
      <el-col>
        <span class="el-icon-warning red" style="margin-right:10px">[已完结项目]上传未完成</span>
        <span class="el-icon-warning orange" style="margin-right:10px">[未完结项目]上传未完成</span>
      </el-col>
      <el-col>
        <span class="el-icon-success green" style="margin-right:10px">[已完结项目]上传已完成</span>
        <span class="el-icon-success blue" style="margin-right:10px" >[未完结项目]上传已完成</span>
      </el-col>

    </el-row>
    <el-row>
      <el-tree
        ref="orgTree"
        :data="orgTreeData"
        :key="treeKey"
        :props="defaultProps"
        :default-expanded-keys="depid"
        :render-after-expand="expandFlag"
        node-key="depid" >
        <span slot-scope="{node,data}" class="custom-tree-node" >
          <span @click="handleNodeClick(node)">
            <span :class="personClass(data)">{{ node.label }}</span>
            <span v-if="showGreen(node.data.finDocState)" :class="potClass(node.data)"/>
            <span v-if="showGreen(node.data.unfinDocState)" :class="potUnfinClass(node.data)"/>
          </span>
        </span>
      </el-tree>
    </el-row>
    <div>
      <template>
        <el-dialog :title="title" :visible.sync="dialogClose" width="30%">
          <el-form ref="menuForm" :model="menuForm" :rules="exportParamRules" status-icon label-width="100px" >
            <el-form-item label="研发项目开始时间:" prop="entryTime" label-width="150px">
              <el-date-picker
                v-model="menuForm.entryTime"
                :picker-options="pickerOptions"
                type="daterange"
                range-separator="-"
                value-format="yyyy-MM-dd"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                style="width: 95%"/>
            </el-form-item>
            <el-form-item label="研 发 项 目:" prop="projectCode" label-width="150px">
              <label slot="label">研&nbsp;&nbsp;&nbsp;&nbsp;
              发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项&nbsp;&nbsp;&nbsp;&nbsp;目:</label>
              <el-select
                v-model="menuForm.projectCode"
                filterable
                clearable
                multiple
                placeholder="请选择"
                style="width: 95%">
                <el-option
                  v-for="item in radProject"
                  :key="item.projectCode"
                  :label="'[' + item.projectCode + ']' + item.projectName"
                  :value="item.projectCode"/>
              </el-select>
            </el-form-item>
            <el-form-item label="研 发 部 门:" prop="competentDept" label-width="150px">
              <label slot="label">研&nbsp;&nbsp;&nbsp;&nbsp;
              发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;部&nbsp;&nbsp;&nbsp;&nbsp;门:</label>
              <el-select
                v-model="menuForm.competentDept"
                filterable
                clearable
                multiple
                placeholder="请选择"
                style="width: 95%">
                <el-option
                  v-for="item in radDepartment"
                  :key="item.depid"
                  :label="item.location"
                  :value="item.depid"/>
              </el-select>
            </el-form-item>
          </el-form>
          <div class="dialog-footer" style="width: 96%">
            <el-button type="primary" @click="exportFile">确定</el-button>
            <el-button @click="dialogCancel">取消</el-button>
          </div>
        </el-dialog>
      </template>
    </div>
  </el-col>
</template>

<script>
import buttonOrange from '@/components/eleButton/buttonOrange'
import cleanBtn from '@/components/CleanBtn'
import PersonTable from '@/api/researchManager/rpersonTable'
import { queryOrgPersonNew, exportFileMsg,
  quertOrgDepartment, quertOrgProject } from '@/api/researchManager/rprojextmess'
import common from '@/api/common'
export default {

  name: 'RProjectAdministSelect',
  components: {
    buttonOrange,
    cleanBtn
  },
  props: {
    showRed: {
      type: Boolean,
      required: false,
      default: false
    },
    // eslint-disable-next-line
    treeItemClick: {
      type: Function,
      required: false
    },
    // eslint-disable-next-line
    onSelected: {
      type: Function,
      required: false
    },
    // eslint-disable-next-line
    date: String,
    // eslint-disable-next-line
    queryType: Number
  },
  data() {
    return {
      radDepartment: [],
      radProject: [],
      title: '', // dialog 标题
      dialogClose: false, // dialog 显示
      menuForm: { // 表单
        entryTime: null, // 文件上传时间
        projectCode: [], // 研发项目
        competentDept: [] // 研发部门
      },
      pickerMinDate: '',
      pickerOptions: {
        onPick: obj => {
          this.pickerMinDate = new Date(obj.minDate).getTime()
        },
        disabledDate: time => {
          if (this.pickerMinDate !== '') {
            const day1 = 365 * 24 * 3600 * 1000
            const maxTime = this.pickerMinDate + day1
            const minTime = this.pickerMinDate - day1
            return time.getTime() > maxTime || time.getTime() < minTime
          }

          // // 获取当前时间
          // const now = new Date()
          // // 创建一个新的日期对象,设施为当前日期前一年同一天
          // const oneYearBefore = new Date()
          // oneYearBefore.setFullYear(now.getFullYear() - 1)
          // // 返回小于一年前的日期为不可选
          // return time.getTime() > now || time.getTime() < oneYearBefore.getTime()
        }
      },
      treeKey: 0,
      expandFlag: false,
      depid: [],
      loading: false,
      searchForm: {
        chargeName: '', // 部门
        empname: '' // 姓名
      },
      defaultProps: {
        children: 'children',
        label: 'text'
      },
      orgTreeData: [], // 展示的机构树
      orgAllData: String, // 所有机构数据
      personData: [], // 所有人员数据
      orgPath: [], // 机构层级路径
      orgDepartment: String,
      exportParamRules: {
        entryTime: [
          { required: true, message: '请选择研发项目开始时间', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'menuForm.entryTime'(val) {
      if (!val || val.length === 0) {
        this.pickerMinDate = ''
      }
    }
  },
  created() {
    quertOrgDepartment().then(res => {
      if (res.data.status === 200) {
        this.radDepartment = res.data.results
        this.orgDepartment = JSON.stringify(res.data.results)
      } else {
        this.$message.error(res.data.message)
      }
    })
    quertOrgProject().then(res => {
      if (res.data.status === 200) {
        this.radProject = res.data.results
      } else {
        this.$message.error(res.data.message)
      }
    })

    this.loading = true
    PersonTable.queryOrgTree().then(res => {
      if (res.data.status === 200) {
        this.orgAllData = JSON.stringify(res.data.results)
        this.requestPersonData(this.date)
      } else {
        this.loading = false
        this.$message.error(res.data.message)
      }
    })
  },
  methods: {
    exportFile: function() {
      const self = this
      const datas = common.paramsFormat(this.menuForm)
      datas._isDown = 'Y'

      this.$refs.menuForm.validate((valid) => {
        if (valid) {
          exportFileMsg(datas).then(res => {
            common.download(res.data, self, '研发文档上传记录.xls')
            this.dialogClose = false
          })
        } else {
          this.$message({
            showClose: true,
            message: '操作失败,请填写必填项',
            type: 'error'
          })
          return false
        }
      })
    },

    // 导出
    submitBtnInfo() {
      Object.keys(this.menuForm).forEach(key => { this.menuForm[key] = '' })
      this.title = '上传记录导出'
      this.dialogClose = true
    },
    /**
     * 取消按钮,关闭dialog
     */
    dialogCancel: function() {
      this.dialogClose = false
      Object.keys(this.menuForm).forEach(key => { this.menuForm[key] = '' })
    },
    // 查询
    searchBtn() {
      this.treeKey += 1
      this.$set(this, 'depid', [])
      const params = this.searchForm
      // eslint-disable-next-line
      if (params.chargeName == '' && params.empname == '') {
        quertOrgDepartment().then(res => {
          if (res.data.status === 200) {
            this.radDepartment = res.data.results
            this.orgDepartment = JSON.stringify(res.data.results)
          } else {
            this.$message.error(res.data.message)
          }
        })
        quertOrgProject().then(res => {
          if (res.data.status === 200) {
            this.radProject = res.data.results
          } else {
            this.$message.error(res.data.message)
          }
        })

        this.loading = true
        PersonTable.queryOrgTree().then(res => {
          if (res.data.status === 200) {
            this.orgAllData = JSON.stringify(res.data.results)
            this.requestPersonData(this.date)
          } else {
            this.loading = false
            this.$message.error(res.data.message)
          }
        })
        this.handleNodeClick('')
        return
      }
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
    },
    getPagerList(params) {
      this.loading = true
      const self = this

      PersonTable.queryAdministrationList(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          const expandeKeys = res.data.results.map(res => res.depid + '')
          this.$set(this, 'depid', expandeKeys)
          // eslint-disable-next-line
          const i = 0
          self.totalNumber = res.data.total
          this.personData = res.data.results
          if (this.personData.length > 0) {
            var orDepartment = JSON.parse(this.orgDepartment)
            this.formatOrgDepartment(orDepartment)
            this.orgTreeData = orDepartment.filter(e => e.children !== undefined && e.children.length > 0)
          }
          if (self.totalNumber <= 0) {
            this.$message.warning('无数据')
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },

    // 列表点击事件
    handleNodeClick(node) {
      // eslint-disable-next-line
      if (this.treeItemClick != undefined) {
        this.treeItemClick(node.data)
      }
    },
    // 请求研发人员数据
    requestPersonData(date) {
      if (this.orgAllData.length === 0) {
        this.loading = false
        return false
      }

      this.orgTreeData = []

      this.loading = true
      const param = { rdQueryType: this.queryType, attendanceDate: date }
      queryOrgPersonNew(param).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          this.personData = res.data.results

          if (this.personData.length > 0) {
            var orDepartment = JSON.parse(this.orgDepartment)
            this.formatOrgDepartment(orDepartment)
            this.orgTreeData = orDepartment.filter(e => e.children !== undefined && e.children.length > 0)
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 取得所有选中的研发人员
    getCheckNodes() {
      const checkedData = this.$refs.orgTree.getCheckedNodes()
      return checkedData.filter(item => {
        return item.badge !== undefined
      })
    },
    // 将研发人员列表合到部门表中
    formatOrgDepartment(node) {
      if (node.length <= 0) return
      const grouped = this.personData.reduce((groups, item) => {
        const depid = item.depid
        if (!groups[depid]) {
          groups[depid] = []
        }
        groups[depid].push(item)
        return groups
      }, {})
      node.forEach(e => {
        e.text = e.location
        if (!e.children) {
          e.children = []
        }
        for (const [key, value] of Object.entries(grouped)) {
          if (e.depid === key) {
            value.forEach(item => {
              item.text = item.empname
            })
            e.children = value
            if ((value.every(item => item.unfinDocState === 2)) ||
            (value.some(item => item.unfinDocState === 2) &&
             (value.every(item => item.unfinDocState === 2 || (item.unfinDocState === 0 && item.empstatus !== '0'))))) {
              e.unfinDocState = 2
            } else if ((value.some(item => item.unfinDocState === 1)) &&
              (value.every(item => item.unfinDocState !== 0 || (item.unfinDocState === 0 && item.empstatus !== '0')))) {
              e.unfinDocState = 1
            } else if (value.some(item => item.unfinDocState === 0 && item.empstatus === '0')) {
              e.unfinDocState = 0
            }
            if ((value.every(item => item.finDocState === 2)) ||
            ((value.some(item => item.finDocState === 2)) && (value.every(item => item.finDocState !== 1)))) {
              e.finDocState = 2
            } else if (value.some(item => item.finDocState === 1)) {
              e.finDocState = 1
            }
          }
        }
      })
    },

    showGreen(data) {
      switch (data) {
        // 分配页面下，已分配显示绿色图标
        case 0:
          return true
        // 考勤填报页面下，已提交和已审批显示绿色图标
        case 1:
          return true
        case 2:
          return true
        default:
          return false
      }
    },
    personClass(data) {
      // eslint-disable-next-line
      if (data.badge != undefined) {
        return 'personNode'
      } else {
        return '-'
      }
    },
    potClass(data) {
      if (data.finDocState === 1) {
        return 'el-icon-warning red'
      } else if (data.finDocState === 2) {
        return 'el-icon-success green'
      }
    },
    potUnfinClass(data) {
      // debugger
      if (data.unfinDocState === 1) {
        return 'el-icon-warning orange'
      } else if (data.unfinDocState === 2) {
        return 'el-icon-success blue'
      } else if (data.unfinDocState === 0 && (data.empstatus === '0' || data.empstatus === undefined)) {
        return 'el-icon-warning gray'
      }
    }

  }

}
</script>

<style>
.dialog-footer {
    margin-top: 10px;
    text-align: right;
}
.personNode{
    color: #0a54f3;;
}
.red{
    color:rgb(253, 124, 124);
}
.orange{
    color:rgb(255, 162, 76);
}
.blue{
    color:rgb(48, 162, 255)
}
.gray{
    color:rgb(187, 187, 187)
}
.green{
    color:rgb(50, 174, 50)
}
</style>
