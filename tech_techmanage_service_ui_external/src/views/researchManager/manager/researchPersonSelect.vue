
<template>
  <el-col
    v-loading="loading"
    :span="6"
    style="padding:10px; background:white;"
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
          <buttonOrange v-hasFunc="['1323_LICENCE']" v-if="queryType == '0'" :fns="submitBtnInfo" style="margin-left: 10px" button-name="导出" size="mini"/>
        </el-row>
      </el-form>
    </el-row>
    <el-row style="padding:10px">
      <span class="el-icon-warning red" style="margin-right:10px" > 待分配</span>
      <span v-if="queryType!='0'" class="el-icon-warning orange" style="margin-right:10px"> 待提交</span>
      <span v-if="queryType=='2'" class="el-icon-warning blue" style="margin-right:10px"> 待审核</span>
      <span v-if="queryType!='0'" class="el-icon-warning gray" style="margin-right:10px"> 被驳回</span>
      <span v-if="queryType=='0'" class="el-icon-success green" style="margin-right:10px"> 已分配</span>
      <span v-if="queryType=='1'" class="el-icon-success green" style="margin-right:10px"> 已提交</span>
      <span v-if="queryType=='2'" class="el-icon-success green" style="margin-right:10px"> 审核通过</span>
    </el-row>
    <el-row>
      <el-tree
        ref="orgTree"
        :data="orgTreeData"
        :show-checkbox="multipleChoice"
        :key="treeKey"
        :props="defaultProps"
        :default-expanded-keys="depid"
        :default-checked-keys="localList"
        :render-after-expand="expandFlag"
        style="height: 750px; overflow: auto"
        node-key="badge">

        <span slot-scope="{node,data}">
          <span @click="handleNodeClick(node)">
            <span :class="personClass(data)">{{ node.label }}</span>
            <span v-if="showGreen(node.data.isProject) && showRed" class="el-icon-success green"/>
            <span v-else-if="node.data.isProject !=undefined && showRed" :class="potClass(data)" class="el-icon-warning"/>

          </span>

        </span>

      </el-tree>
    </el-row>
  </el-col>
</template>

<script>
import buttonOrange from '@/components/eleButton/buttonOrange'
import cleanBtn from '@/components/CleanBtn'
import PersonTable from '@/api/researchManager/rpersonTable'
import common from '@/api/common'
export default {

  name: 'ResearchPersonSelect',
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
      required: false,
      default: false
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
      localList: [],
      loading: false,
      searchForm: {
        chargeName: '', // 部门
        empname: '', // 姓名
        rdQueryType: '', // 类型
        attendanceDate: '' // 请求日期
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
    // list: {
    //   handler(newVal) {
    //     this.localList = [...newVal]
    //   },
    //   immediate: true
    // }
  },
  mounted() {
    this.$nextTick(function() {
      this.$nextTick(() => {
        if (this.data && this.data.length > 0) {
          this.$refs.orgTree.setCurrentKey(this.data[0].badge)
        }
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
      this.$set(this, 'list', [])
      const params = this.searchForm
      // for (var k in params) {
      //     if (params[k] && this.index > 1) {
      //     this.index = 1
      //     }
      // }

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

            // if (this.date != undefined || this.queryType == '0') {
            // console.log(this.orgAllData);
            this.requestPersonData(this.date)
            // }
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
          console.log('depid', this.depid)
          // self.totalNumber = res.data.total
          this.personData = res.data.results
          if (this.personData.length >= 0) {
            var orDepartment = JSON.parse(this.orgDepartment)
            this.formatOrgDepartment(orDepartment)
            this.orgTreeData = orDepartment.filter(e => e.children !== undefined && e.children.length > 0)
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
      // this.localList = [...this.list]
      this.localList = JSON.parse(JSON.stringify(this.list))
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

            // var orgList = JSON.parse(this.orgAllData)
            // this.formatOrgList(orgList[0],'')
            // this.orgTreeData = orgList[0].children.filter(e=> e.isShow)
            this.orgTreeData = orDepartment.filter(e => e.children !== undefined && e.children.length > 0)
            // if (cal.size === 0) return
            // if (this.queryType === 1) {
            //   this.list = cal
            //   this.depid = cal
            //   console.log(this.personData, '**-**')
            // }
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
            if (this.queryType == 1) {
              if (item.isProject != 1 && item.isProject != 3) {
                item.disabled = true
                if (this.localList !== undefined && this.localList.length !== 0) {
                  // this.$set(this, 'depid', this.list)
                  this.localList = this.localList.filter(iten => iten !== item.badge)
                }
              }
              if (this.list !== undefined && this.list.length !== 0) {
                this.depid = JSON.parse(JSON.stringify(this.list))
              }
            } else if (this.queryType != item.isProject) {
              item.disabled = true
              if (this.localList !== undefined && this.localList.length !== 0) {
                this.$set(this, 'depid', this.list)
                this.localList = this.localList.filter(iten => iten !== item.badge)
              }
            }
            e.children.push(item)
          }
        })
      })
    },
    // 将研发人员列表合到机构树里
    // formatOrgList(node,path){

    //     if(node.children && node.children.length > 0){

    //         node.children.forEach(e=>{

    //             this.formatOrgList(e,path+'/'+e.id)
    //             node.isShow = e.isShow === true ? e.isShow : node.isShow
    //             //如果子节点有多个红点状态，则父节点红点显示优先级是被退回，待提交，待分配
    //             if (e.isProject != undefined) {

    //                 // if (node.isProject == undefined || node.isProject > e.isProject) {
    //                 //     node.isProject = e.isProject
    //                 // }

    //                 if(node.isProject==undefined){
    //                     node.isProject = e.isProject
    //                 }else if(node.isProject==3 && e.isProject==2 ){
    //                     node.isProject==3
    //                 }else if(node.isProject==2 && e.isProject==3){
    //                     node.isProject = e.isProject
    //                 }else if (node.isProject > e.isProject){
    //                     node.isProject = e.isProject
    //                 }

    //             }

    //         })
    //         node.children = node.children.filter(e=> e.isShow)
    //     }

    //     this.personData.forEach(e=>{

    //         if (e.depid.toString() == node.id) {

    //             if(!node.children){
    //                 node.children = []
    //             }
    //             e.text = e.empname

    //             //考勤页已提交和已审批通过的数据不打标识
    //             // if (this.queryType == 1 && (e.isProject == 2 || e.isProject == 4)) {
    //             //     e.isProject = undefined
    //             // }

    //             if (e.isProject != undefined ) {

    //                 if(node.isProject==undefined){
    //                     node.isProject = e.isProject
    //                 }else if(node.isProject==3 && e.isProject==2 ){
    //                     node.isProject==3
    //                 }else if(node.isProject==2 && e.isProject==3){
    //                     node.isProject = e.isProject
    //                 }else if (node.isProject > e.isProject){
    //                     node.isProject = e.isProject
    //                 }

    //             }

    //             //考勤页，未提交的和被退回的都能编辑
    //             if (this.queryType == 1) {

    //                 if (e.isProject != 1 && e.isProject != 3) {
    //                     e.disabled = true
    //                 }

    //             }
    //             else if (this.queryType != e.isProject) {
    //                 e.disabled = true
    //             }
    //             node.children.push(e)

    //             node.isShow = true
    //             this.orgPath.push(path)

    //         }
    //     })

    // },
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
