<template>
<!-- 人力资源分配 -->
  <div class="typeset-container">
    <el-row :gutter="20">
      <research-person-select :showRed="true" :treeItemClick.sync="handleNodeClick" :queryType="0" />
      <el-col :span="18" >
        <div class="user-data" v-if="showUserData">
          <p>姓名：{{userData.empname}}</p>
          <p>工号：{{userData.badge}}</p>
          <p>所属部门：{{userData.chargeName}}</p>
          <p v-if="!checkFunc(['00000_HIDDEN'])">研发人员性质：{{getDictName('NEWKG_RD_NATURE',userData.nature)}}</p>
          <p>在岗状态：{{getDictName('NEWKG_RD_EMPSTATUS',userData.empstatus)}}</p>
          <div style="margin-bottom:20px;">
            <span>参与项目：</span>
            <el-button size="small" @click="showDialog">添加项目</el-button>
          </div>
          <!-- 已完结项目 -->
          <div style="border:1px solid #EBEEF5;padding:3px; margin-top: 10px">
            <el-button :icon="clickDiolog1 ? 'el-icon-arrow-down' : 'el-icon-arrow-right' " type="text" @click="clickDiolog"/> 已完结项目
            <el-table
              v-loading="loading"
              v-if="clickDiolog1"
              :data="projectData1"
              element-loading-text="数据加载中"
              style="width: 100%;"
            >
              <el-table-column :formatter="getIndex" label="序号" align="center" width="60"/>
              <el-table-column prop="projectCode" label="研发项目编号" width="120" align="center"/>
              <el-table-column prop="projectName" label="项目名称" width="120" align="center" />
              <el-table-column :formatter="formatAmount" prop="budgetAmount" label="项目合同预算总金额(万元)" width="180" align="center"/>
              <el-table-column prop="no" label="项目研发人员数" align="center" width="120" />
              <el-table-column prop="projectState" label="项目状态" align="center" width="120" :formatter="(row,column,cellvalue,index)=>(getDictName('RD_PROJECT_STATE',cellvalue))" />
              <el-table-column prop="remark" label="备注" align="center" width="100%" />
              <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                  <el-link
                    v-hasFunc="['1321_LICENCE']"
                    :underline="false"
                    type="warning"
                    @click="handleUpdate(scope.row)"
                  >查看</el-link
                  >
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!-- 未完结项目 -->
          <div style="border:1px solid #EBEEF5;padding:3px; margin-top: 10px">
            <el-button :icon="clickDiolog2 ? 'el-icon-arrow-down' : 'el-icon-arrow-right' " type="text" @click="clickDiologTwo"/> 未完结项目
            <el-table
              v-loading="loading"
              v-if="clickDiolog2"
              :data="projectData2"
              element-loading-text="数据加载中"
              style="width: 100%;"
            >
              <el-table-column :formatter="getIndex" label="序号" align="center" width="60" />
              <el-table-column prop="projectCode" label="研发项目编号" width="120" align="center"/>
              <el-table-column prop="projectName" label="项目名称" width="120" align="center" />
              <el-table-column :formatter="formatAmount" prop="budgetAmount" label="项目合同预算总金额(万元)" width="180" align="center"/>
              <el-table-column prop="no" label="项目研发人员数" align="center" width="120" />
              <el-table-column prop="projectState" label="项目状态" align="center" width="120" :formatter="(row,column,cellvalue,index)=>(getDictName('RD_PROJECT_STATE',cellvalue))" />
              <el-table-column prop="remark" label="备注" align="center" width="100%" />
              <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                  <el-link
                    v-hasFunc="['1321_LICENCE']"
                    :underline="false"
                    type="warning"
                    @click="handleUpdate(scope.row)"
                  >查看</el-link
                  >
                  <el-link
                    v-hasFunc="['1322_LICENCE']"
                    :underline="false"
                    style="margin-left:10px"
                    type="warning"
                    @click="handleDel(scope.row)"
                  >删除</el-link>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
    </el-row>

    <dialog-add-project ref="addProject" :isShow.sync="dialogIsShow"  :person.sync="dialogData" @refrushData="refrushData" />

  </div>

</template>

<script>
import PersonTable from '@/api/researchManager/rpersonTable'
import pager from '@/components/Pager'
import ResearchPersonSelect from './researchPersonSelect.vue'
import DialogAddProject from './dialogAddProject.vue'


export default {
  name: 'RProjectAllocation',
  components: {

    pager,
    DialogAddProject,
    ResearchPersonSelect
  },
  data() {
    return {
      deptno_options: [], // 部门数据
      showUserData: false,
      userData: {},
      projectData1: [], // 已完成的项目集合
      projectData2: [], // 未完成的项目集合
      clickDiolog1: false, // 已完成的项目‘>’状态
      clickDiolog2: false, // 未完成的项目‘>’状态
      projectData: [],
      size: 10, // 初始值 默认：10
      totalNumber: 0,
      dialogIsShow: false,
      dialogData: {},
      loading: false,
      index: 1
    }
  },
  created() {
    const contractParams = this.$route.query.params
    if (contractParams != undefined) {
      const data = contractParams ? JSON.parse(contractParams) : null
      console.log(data, '=======================+++')
      this.handleNodeClick(data)
    }
  },
  methods: {
    // 获取序号
    getIndex(row, column, cellvalue, i) {
      return this.size * (this.index - 1) + i + 1
    },
    // 分页
    getPager: function(size, index) {
      const params = {}
      params.size = size
      params.index = index
      this.getResource(params)
    },
    // 刷新数据
    refrushData() {
      this.getPager(this.size, 1)
    },
    // >(已完成的项目)点击事件
    clickDiolog() {
      this.clickDiolog1 = !this.clickDiolog1
    },
    // >(未完成的项目)点击事件
    clickDiologTwo() {
      this.clickDiolog2 = !this.clickDiolog2
    },
    // 修改研发项目信息
    handleUpdate(data) {
      const routeData = this.$router.resolve({
        name: 'researchProjectEdit',
        query: {
          isEdit: 0,
          projectParams: JSON.stringify(data)
        }
      })
      window.open(routeData.href, '_blank')
      // this.$router.push({
      //   name: 'researchProjectEdit',
      //   params: {
      //     isEdit: 0,
      //     projectParams: data
      //   }
      // })
    },
    // 四舍五入显示
    formatAmount(row, column, cellValue) {
      return Number(cellValue).toFixed(2)
    },
    handleDel(val) {
      this.$confirm('是否要删除项目“'+val.projectName+'”吗？','删除确认',{confirmButtonText:'确定',cancelButtonText:'取消',type:'warning'})
      .then(_=>{
        this.loading = true
        PersonTable.deleteResourceByPerson({eid:this.userData.eid,projectCode:val.projectCode}).then(res=>{
          
          if (res.data.status == 200) {
              this.$message({type:'success',message:'删除成功！'})
              this.refrushData()
          } else {
            this.loading = false
          }
          
        }).catch(()=>{
          this.$message({type:'error',message:'删除失败！'})
          this.loading = false
        })

      })
    },

    handleNodeClick(itemData){
      if (itemData == undefined) {
          this.showUserData = false
          this.userData = {}
          return
      }
        
        if (itemData.badge != undefined) {
            // console.log(itemData)
            this.showUserData = true
            this.userData = itemData
            this.refrushData()
        }
    },

    getResource(params) {
      this.loading = true
      params.eid = this.userData.eid
      PersonTable.queryResourceByPerson(params).then(res=>{
        this.loading = false
        if (res.data.status === 200) {
          // console.log(res.data.results)
          // this.projectData = res.data.results
          this.projectData1 = res.data.results.filter(item => item.projectState === '9')
          this.projectData2 = res.data.results.filter(item => item.projectState === '1' || item.projectState === '2')
          this.totalNumber = res.data.total
        }
      })
    },
    showDialog() {
      this.dialogData = this.userData
      this.dialogIsShow = true
    }
  }

}
</script>

<style scoped>
.typeset-container {
  padding: 20px;
}

.user-data{
    background: white;
    padding: 10px 20px;
}

</style>