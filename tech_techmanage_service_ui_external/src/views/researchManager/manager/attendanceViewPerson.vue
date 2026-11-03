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
        <el-row style="text-align:right;margin-bottom:10px">
          <clean-btn :forms="searchForm" size="mini" />
          <buttonOrange :fns="searchBtn" style="margin-left: 10px" button-name="查询" size="mini"/>
          <buttonOrange v-hasFunc="['1323_LICENCE']" v-if="queryType == '0'" :fns="submitBtnInfo" style="margin-left: 10px" button-name="导出" size="mini"/>
        </el-row>
      </el-form>
    </el-row>
    <el-row>
      <el-tree
        ref="orgTree"
        :data="orgTreeData"
        :show-checkbox="true"
        :key="treeKey"
        :props="defaultProps"
        :default-expanded-keys="depid"
        :default-checked-keys="localList"
        :render-after-expand="expandFlag"
        style="height: 770px; overflow: auto"
        node-key="badge"/>
    </el-row>
  </el-col>
</template>

<script>
import buttonOrange from '@/components/eleButton/buttonOrange'
import cleanBtn from '@/components/CleanBtn'
import PersonTable from '@/api/researchManager/rpersonTable'
import common from '@/api/common'
export default {

  name: 'AttendanceViewPerson',
  components: {
    buttonOrange,
    cleanBtn
  },
  props: {
    list: {
      type: Array,
      required: false,
      default: () => []
    },
    multipleChoice: {
      type: Boolean,
      required: true,
      default: true
    },
    showRed: {
      type: Boolean,
      required: false,
      default: false
    },
    treeItemClick: {
      type: Function,
      required: false
    },
    onSelected: {
      type: Function,
      required: false
    },
    date: String,
    queryType: Number
  },
  data() {
    return {
      isHighlighted: false,
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
      orgDepartment: String

    }
  },
  watch: {
    list: {
      handler(newVal) {
        this.localList = [...newVal]
      },
      immediate: true
    }
  },
  mounted() {
    this.$nextTick(function() {
      this.$nextTick(() => {
        this.$refs.orgTree.setCurrentKey(this.data[0].badge)
      })
    })
  },
  created() {
    PersonTable.quertOrgDepartment().then(res => {
      if (res.data.status == 200) {
        this.orgDepartment = JSON.stringify(res.data.results)
      } else {
        this.$message.error(res.data.message)
      }
    })

    this.loading = true
    PersonTable.queryOrgTree().then(res => {
      if (res.data.status == 200) {
        this.orgAllData = JSON.stringify(res.data.results)

        // if (this.date != undefined || this.queryType == '0') {
        // console.log(this.orgAllData);
        this.requestPersonData(this.date)
        // }
      } else {
        this.loading = false
        this.$message.error(res.data.message)
      }
    })

    // Promise.all([PersonTable.queryOrgTree(),PersonTable.queryOrgPerson(param)]).then(datas => {
    //     console.log(datas)
    //     if (datas[0].data.status == 200 && datas[1].data.status == 200) {
    //         this.orgAllData = datas[0].data.results
    //         this.personData = datas[1].data.results
    //         this.formatOrgList(this.orgAllData[0],'')
    //         this.orgTreeData = this.orgAllData = this.orgAllData[0].children.filter(e=> e.isShow)
    //         console.log(this.orgPath)
    //     }

    // })
  },
  methods: {
    // 查询
    searchBtn() {
      this.treeKey += 1
      this.$set(this, 'depid', [])
      this.$set(this, 'localList', [])
      const params = this.searchForm

      if (params.chargeName == '' && params.empname == '') {
        PersonTable.quertOrgDepartment().then(res => {
          if (res.data.status == 200) {
            this.orgDepartment = JSON.stringify(res.data.results)
          } else {
            this.$message.error(res.data.message)
          }
        })

        this.loading = true
        PersonTable.queryOrgTree().then(res => {
          if (res.data.status == 200) {
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
      params.rdQueryType = this.queryType
      params.attendanceDate = this.date
      this.getPagerList(params)
    },
    getPagerList(params) {
      this.loading = true
      const self = this

      PersonTable.queryList(params).then(res => {
        this.loading = false
        if (res.data.status == 200) {
          const expandeKeys = res.data.results.map(res => res.badge + '')
          this.$set(this, 'depid', expandeKeys)
          // self.totalNumber = res.data.total
          this.personData = res.data.results
          if (this.personData.length >= 0) {
            var orDepartment = JSON.parse(this.orgDepartment)
            this.formatOrgDepartment(orDepartment)
            this.orgTreeData = orDepartment.filter(e => e.children !== undefined && e.children.length > 0)
            console.log(this.orgTreeData, '===========+++===')
          }
          if (this.personData.length <= 0) {
            this.$message.warning('无数据')
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },

    // 导出
    submitBtnInfo() {
      this.$confirm('是否导出全员项目分配表？', '是否导出全员项目分配表？', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' }).then(_ => {
        const self = this
        const data = { size: 10, index: 1, _isDown: 'Y' }
        PersonTable.queryResourceByPersonInfo(data).then(res => {
          common.download(res.data, self, '全员项目分配表.xls')
        })
      })
    },
    // 列表点击事件
    handleNodeClick(node) {
      if (this.treeItemClick != undefined) {
        this.treeItemClick(node.data)
      }
    },
    // 请求研发人员数据
    requestPersonData(date) {
      this.treeKey += 1
      this.$set(this, 'depid', [])

      if (this.orgAllData.length == 0) {
        this.loading = false
        return false
      }

      this.orgTreeData = []

      this.loading = true
      const param = { rdQueryType: this.queryType, attendanceDate: date }
      PersonTable.queryOrgPerson(param).then(res => {
        this.loading = false
        if (res.data.status == 200) {
          this.personData = res.data.results

          if (this.personData.length > 0) {
            var orDepartment = JSON.parse(this.orgDepartment)
            this.formatOrgDepartment(orDepartment)

            this.orgTreeData = orDepartment.filter(e => e.children !== undefined && e.children.length > 0)
            console.log(this.orgTreeData, '===========+++===')
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
        return item.badge != undefined
      })
    },
    // 将研发人员列表合到部门表中
    formatOrgDepartment(node) {
      if (this.localList !== undefined && this.localList.length !== 0) {
        this.depid = JSON.parse(JSON.stringify(this.localList))
      }
      if (node.length <= 0) return
      node.forEach(e => {
        e.text = e.location
        if (!e.children) {
          e.children = []
        }
        this.personData.forEach(item => {
          if (item.depid.toString() === e.depid) {
            item.text = item.empname

            if (e.isProject == undefined) {                                                                           
              e.isProject = item.isProject
            } else if (e.isProject == 3 && item.isProject == 2) {
              e.isProject == 3
            } else if (e.isProject == 2 && item.isProject == 3) {
              e.isProject = item.isProject
            } else if (e.isProject > item.isProject) {
              e.isProject = item.isProject
            }
            // 考勤页，未提交的和被退回的都能编辑
            // if (this.queryType == 1) {
            //   if (item.isProject != 1 && item.isProject != 3) {
            //     item.disabled = true
            //   }
            // } else if (this.queryType != item.isProject) {
            //   item.disabled = true
            // }
            e.children.push(item)
          }
        })
      })
    },

    showGreen(isProject) {
      switch (this.queryType) {
        // 分配页面下，已分配显示绿色图标
        case 0:
          if (isProject == 1) {
            return true
          }
          break
          // 考勤填报页面下，已提交和已审批显示绿色图标
        case 1:
          if (isProject == 2 || isProject == 4) {
            return true
          }
          // 审批页面下，已审批显示绿色
        case 2:
          if (isProject == 4) {
            return true
          }
        default:
          return false
          break
      }

      return false
    },
    queryFrom(data) {
      // 查询到数据可进行高亮显示
      var empname = this.searchForm.empname
      if (data.empname == empname) {
        return true
      } else {
        return false
      }
    },
    personClass(data) {
      if (data.badge != undefined) {
        return 'personNode'
      } else {
        return '-'
      }
    },
    potClass(data) {
      switch (data.isProject) {
        case 3:
          return 'gray'
          break
        case 2:
          return 'blue'
          break
        case 0:
          return 'red'
          break
        case 1:
          return 'orange'
          break
        default:
          return ''
          break
      }
    }

  }

}
</script>

<style>
.highlight-node {
    color: #C0C4CC;
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
