<!-- 研发项目管理列表 -->
<template>
  <div class="list-container interviewList">
    <el-form inline>
      <div class="interviewList_head">
        <div>
          <!-- <el-row>
            <el-col :span="12">
              <el-form-item prop="" label="">
                <el-input v-model="searchForm.name" placeholder="请输入">
                  <template slot="prefix">项目名称：</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="" label="">
                <el-input v-model="searchForm.badge" placeholder="请输入">
                  <template slot="prefix">系统名称：</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row> -->
          <el-form :inline="true" :model="searchForm" >
            <el-form-item label="项目编号：">
              <el-input v-model="searchForm.projectCode" placeholder="请填入" style="width: 130px;"/>
            </el-form-item>
            <el-form-item label="项目名称：">
              <el-input v-model="searchForm.projectName" placeholder="请填入" style="width: 130px;"/>
            </el-form-item>
            <el-form-item label="主管部门：">
              <!-- <el-input v-model="searchForm.competentDept" placeholder="请填入" style="width: 130px;"/> -->
              <el-select v-model="searchForm.competentDept" filterable clearable placeholder="请选择">
                  <el-option
                    v-for="item in orgDepartment"
                    :key="item.depid"
                    :label="item.location"
                    :value="item.depid">
                  </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="关联系统：">
              <el-input v-model="searchForm.sysName" placeholder="请填入" style="width: 130px;"/>
            </el-form-item>
             <el-form-item>
              <el-button :size="sizes" type="primary"   @click="submitBtn">查 询</el-button>
              <el-button :size="sizes" @click="clearSearch">重 置</el-button>
              <el-button :size="sizes" type="primary"   v-hasFunc="['116_LICENCE']"  @click="creatNewProject">+ 新 建</el-button>
              <el-button :size="sizes" type="primary"   v-hasFunc="['114_LICENCE']"  @click="submitBtnInfo">导 出</el-button>
            </el-form-item>
          </el-form>
         
        </div>
        

      </div>
    </el-form>

    <div class="projectContainer">
      <el-table :data="projectData" highlight-current-row height="710" style="width:100%" @sort-change = "handleSortChange">
        <el-table-column :show-overflow-tooltip="true" sortable = "custom" label="项目编号" prop="projectCode" width="200"/>
        <el-table-column :show-overflow-tooltip="true" label="项目名称" prop="projectName" width="250"/>
        <el-table-column :show-overflow-tooltip="true" label="主管部门" sortable = "custom" prop="competentName" width="200"/>
        <el-table-column :show-overflow-tooltip="true" label="关联系统" prop="sysName" width="250"/>
        <el-table-column :show-overflow-tooltip="true" label="项目开始时间" sortable = "custom" prop="startDate" width="250"/>
        <el-table-column :show-overflow-tooltip="true" label="项目状态" sortable = "custom" prop="projectState" width="150" 
        :formatter="(row, column, cellValue, index)=>getDictName('RD_PROJECT_STATE',cellValue)" />
        <el-table-column label="操作" v-if="checkFunc(['113_LICENCE'])">
          <template slot-scope="scope">
            <el-link :underline="false" type="warning" v-hasFunc="['111_LICENCE']" @click="delRow(scope.row)">删除</el-link>
            <el-link :underline="false" type="warning" v-hasFunc="['112_LICENCE']" @click="editProject(scope.row.projectCode)">编辑</el-link>
            <el-link :underline="false" type="warning" v-hasFunc="['113_LICENCE']" @click="detailProject(scope.row.projectCode)">详情</el-link>
          </template>
        </el-table-column>
      </el-table>

      <div class="pager_zb">
        <pager ref="pager" :fns="getPager" :total="totalNumber" :page-size="size" v-model="$attrs" />
      </div>
    </div>
  </div>
</template>
<script>
import common from '@/api/common'
import { queryPager,queryPagerInfo,doAdd ,doDelete,getDetails} from '@/api/rdmanager/rdProject'
import pager from '@/components/Pager'
import PersonTable from '@/api/researchManager/rpersonTable'
export default {
  name: 'ResearchProject',
  components: { pager },
  data() {
    return {
      orgDepartment:[],
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      searchForm: {
        projectCode: '', // 项目编号
        projectName: '', // 项目名称
        competentDept: '', // 主管部门
        sysName: '' // 关联系统
      },
      sortInfo: {
        prop: '',
        order: ''
      },
      projectData: [
        // {
        //   projectCode: 'RD2024001', // 项目编号
        //   projectName: 'XXX的研发', // 项目名称
        //   competentDept: '金融科技部', // 主管部门
        //   sysName: '消金XX系统', // 关联系统
        //   startDate: '2024-01-01', // 项目开始时间
        //   projectState: '进行中' // 项目状态
        // }
      ], // 列表分页数据
      buttonStart: '+ 新建', // 新建按钮
      sizes: 'small', // 按钮大小
      buttonSearch: '查 询', // 查询按钮
    }
  },
  created() {
    this.loadInit()
    PersonTable.quertOrgDepartment().then(res=>{
      if(res.data.status == 200){
            this.orgDepartment = res.data.results
        } else {
            this.$message.error(res.data.message)
        }
    })
  },
  methods: {
    /**
             * 分页初始化方法
             */
    loadInit: function() {
      const params = {}
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
    },
    // 获取列表数据
    getPagerList(params) {
      params.prop = this.sortInfo.prop
      params.order = this.sortInfo.order
      const data = common.paramsFormat(params)
      this.totalNumber = 4
      queryPager(data).then(res => {
        if (res.data.status === 200) {
          this.projectData = res.data.results
          this.totalNumber = res.data.total
          if (this.totalNumber <= 0) {
            return false
          }
          // const type = this.projectData[0].roleType
          // if (type === 1 || type === 4) {
          //   this.isAuthView = true
          // }
        }
      })
    },
    // 新建
    creatNewProject() {
      this.$router.push({
        name: 'researchProjectEdit',
        query: {
          isEdit: 2
        }
      })
    },
    // 修改
    editProject(params) {
      const routeData = this.$router.resolve({
        name: 'researchProjectEdit',
        query: {
          isEdit: 1,
          projectParams: JSON.stringify(params)
        }
      })
      // console.log(routeData.href)
      window.open(routeData.href, '_blank')
      // this.$router.push({
      //   name: 'researchProjectEdit',
      //   params: {
      //     isEdit: 1,
      //     projectParams: params
      //   }
      // })
    },
    // 详情
    detailProject(params) {
      const routeData = this.$router.resolve({
        name: 'researchProjectEdit',
        query: {
          isEdit: 0,
          projectParams: JSON.stringify(params)
        }
      })
      // console.log(routeData.href)
      window.open(routeData.href, '_blank')

      // this.$router.push({
      //   name: 'researchProjectEdit',
      //   params: {
      //     isEdit: 0,
      //     projectParams: params
      //   }
      // })
    },
    // 删除行
    delRow(val) {
      this.$confirm('确认删除该研发项目的全部记录吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        doDelete({ pkId: val.pkId }).then(res => {
          loading.close()
          if (res.data.status === 200) {
            this.$message.success('删除成功')
            this.submitBtn()
          } else {
            this.$message.error('删除失败')
          }
        }).catch(error => {
          loading.close()
          this.$message.error(error)
        })
      }).catch(error => { console.log(error) })
    },
    // 排序后端排序
    handleSortChange({ prop, order }) {
      console.log(`排序字段：${prop}`, `排序顺序：${order}`)
      const sortProp = this.camelToSnake(prop)
      let sortOrder = ''
      if (order === 'ascending') {
        sortOrder = 'asc'
      } else if (order === 'descending') {
        sortOrder = 'desc'
      } else {
        sortOrder = ''
      }
      this.sortInfo = {
        prop: sortProp,
        order: sortOrder
      }
      console.log(this.sortInfo)
      this.submitBtn()
    },
    // 驼峰映射
    camelToSnake(str) {
      return str.replace(/[A-Z]/g, function(match) {
        return '_' + match
      })
    },
    /**
             * 搜索事件
             */
    submitBtn: function() {
      const params = {}
      // const depid = this.searchForm.depId
      // if (depid) {
      //   params.depId = depid.replace('dep', '')
      // }
      params.projectCode = this.searchForm.projectCode
      params.projectName = this.searchForm.projectName
      params.competentDept = this.searchForm.competentDept
      params.sysName = this.searchForm.sysName
      params.size = this.size
      params.index = this.index
      this.getPagerList(params) // bin
    },
    //导出事件
    submitBtnInfo: function(){
      this.$confirm('是否项目表？','是否导出项目表？',{confirmButtonText:'是',cancelButtonText:'否',type:'warning'}).then(_=>{
        const params = {size: 5000, index: 1,_isDown:'Y'}
        // const data = common.paramsFormat(params)
        queryPagerInfo(params).then(res => {
          common.download(res.data,self,"项目配表.xls")
        })
      })
    },

    // 重置按钮
    clearSearch() {
      this.searchForm.projectCode = ''
      this.searchForm.projectName = ''
      this.searchForm.competentDept = ''
      this.searchForm.sysName = ''
      this.submitBtn();
    },
    /**
    /**
             * 分页触发
             */
    getPager: function(size, index) {
      const params = {}
      // const depid = this.searchForm.depId
      // if (depid) {
      //   params.depId = depid.replace('dep', '')
      // }
      params.projectCode = this.searchForm.projectCode
      params.projectName = this.searchForm.projectName
      params.competentDept = this.searchForm.competentDept
      params.sysName = this.searchForm.sysName
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
      console.log(this.size, this.index)
    }
  }
}
</script>
<style scoped>
.interviewList {
  padding: 15px;
}

.interviewList_head {
  background-color: #ffffff;
  padding: 18px;
  display: flex;
  justify-content: space-between;
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
.tooltips-width{
    max-width: 900px !important;
  }
/* .projectContainer_head_en {
  width: 160px;
} */

/* .requireList_head_right {
  float: right;
}

.requireList_head_right div {
  display: inline-block;
}

.requireList_head_right_clean {
  margin-right: 10px;
} */

/* .interviewList_head_right_orange {
  margin: 0 6px 0 10px;
} */

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
/* el-input修改 */
.interviewList .el-input--prefix .el-input__inner {
  padding-left: 80px;
}

.interviewList .el-input__prefix {
  left: 5px;
  color: #262626;
  font-size: 14px;
}

/* element表单修改 */
.interviewList .el-form-item {
  margin-bottom: 0px;
}

/* 表头背景色 */
.interviewList .el-table th {
  background: #FAFAFA;
}

.interviewList_head_right_buttonBox{
  display: flex;
}

.interviewList_head_right_button{
  margin-left:10px;
}
</style>

