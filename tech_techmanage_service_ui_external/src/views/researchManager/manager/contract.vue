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

          <el-form :inline="true" :model="searchForm" class="contract-search-inline">
            <el-row :gutter="10">
              <el-form-item label="合同编号：">
                <el-input v-model="searchForm.contractCode" placeholder="请填入"/>
              </el-form-item>
              <el-form-item label="合同名称：">
                <el-input v-model="searchForm.contractName" placeholder="请填入"/>
              </el-form-item>
              <el-form-item label="关联研发项目：">
                <el-input v-model="searchForm.projectName" placeholder="请填入"/>
              </el-form-item>
              <el-form-item>
                <el-button :size="sizes" type="primary"  @click="submitBtn">查 询</el-button>
                <el-button :size="sizes" type="primary"   @click="clearSearch">重 置</el-button>
                <el-button :size="sizes"  @click="creatNewContract" v-hasFunc="['126_LICENCE']">+ 新 建</el-button>
                <el-button :size="sizes" type="primary"   @click="submitBtnInfo" v-hasFunc="['125_LICENCE']">导 出</el-button>
                <el-upload action="Fake Action" :show-file-list="false" :http-request="changeUpload" style="display: inline-block;">
                    <el-button :size="sizes" v-hasFunc="['126_LICENCE']" >批 量 新 建</el-button>
                </el-upload>
              </el-form-item>
            </el-row>
            <el-row style="margin-bottom: 20px;"/>
            <el-row :gutter="10">
              <el-form-item label="预算编号：">
                <el-input v-model="searchForm.budgetCode" placeholder="请填入"/>
              </el-form-item>
              <el-form-item label="预算名称：">
                <el-input v-model="searchForm.budgetName" placeholder="请填入"/>
              </el-form-item>
            </el-row>
          </el-form>
        </div>

      </div>
    </el-form>

    <div class="contractContainer">
      <el-table :data="contractData" highlight-current-row height="670" style="width:100%" @sort-change = "handleSortChange">
        <el-table-column :show-overflow-tooltip="true" sortable = "custom" label="预算编号" prop="budgetCode" width="200" />
        <el-table-column :show-overflow-tooltip="true" label="预算名称" prop="budgetName" width="250"/>
        <el-table-column :show-overflow-tooltip="true" sortable = "custom" label="合同编号" prop="contractCode" width="200"/>
        <el-table-column :show-overflow-tooltip="true" label="合同名称" prop="contractName" width="250"/>
        <el-table-column :show-overflow-tooltip="true" sortable = "custom" label="合同金额(万元)" prop="contractAmount" width="150"/>
        <el-table-column :show-overflow-tooltip="true" sortable = "custom" label="关联研发项目" prop="projectName" width="200" show-overflow-tooltip>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" sortable = "custom" label="支付进度(%)" prop="paymentScheduling" width="150">
          <template slot-scope="scope" v-if="scope.row.paymentScheduling">
            {{scope.row.paymentScheduling}}%
          </template>
        </el-table-column>
        <el-table-column label="操作" v-if="checkFunc(['124_LICENCE'])">
          <template slot-scope="scope">
            <el-link :underline="false" type="warning" @click="delRow(scope.row)" 
              v-hasFunc="['122_LICENCE']">删除</el-link>
            <el-link :underline="false" type="warning" @click="editContract(scope.row.budgetCode)"
              v-hasFunc="['121_LICENCE']">编辑</el-link>
            <el-link :underline="false" type="warning" @click="detailContract(scope.row.budgetCode)"
              v-hasFunc="['124_LICENCE']">详情</el-link>
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
import { saveRdProjectContract,deleteRdProjectContract,queryPager,queryPagerInfo,importContract } from '@/api/rdmanager/rdProjectContract'
import pager from '@/components/Pager'


export default {
  name: 'Contract',
  components: { pager },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      searchForm: {
        contractCode: '', // 合同编号
        contractName: '', // 合同名称
        projectName: '', // 关联研发项目
        budgetCode: '', // 项目关联编号
        budgetName: '' // 项目关联名称
      },
      sortInfo: {
        prop: '',
        order: ''
      },
      contractData: [
        // {
        //   budgetCode: 'YS2023Q302', // 预算编号
        //   budgetName: 'XXX预算', // 预算名称
        //   contractCode: 'NYXJCGHT2023063', // 合同编号
        //   contractName: '消金XX系统-第一期', // 合同名称
        //   contractAmount: '20', // 合同金额
        //   projectName: 'XXX的研发', // 关联研发项目
        //   paymentSche: '90%' // 支付进度
        // },
        // {
        //   budgetCode: 'YS2023Q303', // 预算编号
        //   budgetName: 'XXX预算', // 预算名称
        //   contractCode: 'NYXJCGHT2023064', // 合同编号
        //   contractName: '消金XX系统-第二期', // 合同名称
        //   contractAmount: '50', // 合同金额
        //   projectName: 'XXX的研发', // 关联研发项目
        //   paymentSche: '90%' // 支付进度
        // },
        // {
        //   budgetCode: 'YS2023Q304', // 预算编号
        //   budgetName: 'XXX预算', // 预算名称
        //   contractCode: 'NYXJCGHT2023065', // 合同编号
        //   contractName: '消金XX系统-第三期', // 合同名称
        //   contractAmount: '77', // 合同金额
        //   projectName: 'XXX的研发', // 关联研发项目
        //   paymentSche: '20%' // 支付进度
        // },
        // {
        //   budgetCode: 'YS2023Q305', // 预算编号
        //   budgetName: 'XXX预算', // 预算名称
        //   contractCode: 'NYXJCGHT2023066', // 合同编号
        //   contractName: '消金XX系统-第四期', // 合同名称
        //   contractAmount: '192', // 合同金额
        //   projectName: 'XXX的研发', // 关联研发项目
        //   paymentSche: '0%' // 支付进度
        // }
      ], // 列表分页数据
      buttonStart: '+ 新建', // 新建按钮
      sizes: 'small', // 按钮大小
      buttonSearch: '查 询' // 查询按钮
    }
  },
  created() {
    this.loadInit()
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
      queryPager(data).then(res => {
        if (res.data.status === 200) {
          this.contractData = res.data.results
          this.totalNumber = res.data.total
          if (this.totalNumber <= 0) {
            return false
          }
         
        }
      })
    },
    // 新建
    creatNewContract() {
      this.$router.push({
        name: 'contractManagementEdit'
      })
    },
    // 修改
    editContract(params) {
      const routeData = this.$router.resolve({
        name: 'contractManagementEdit',
        query: {
          isEdit: 1,
          contractParams: JSON.stringify(params)
        }
      })
      // console.log(routeData.href)
      window.open(routeData.href, '_blank')
      // this.$router.push({
      //   name: 'contractManagementEdit',
      //   params: {
      //     isEdit: 1,
      //     contractParams: params
      //   }
      // })
    },
    // 详情
    detailContract(params) {
      const routeData = this.$router.resolve({
        name: 'contractManagementEdit',
        query: {
          isEdit: 0,
          contractParams: JSON.stringify(params)
        }
      })
      window.open(routeData.href, '_blank')
      // this.$router.push({
      //   name: 'contractManagementEdit',
      //   params: {
      //     isEdit: 0,
      //     contractParams: params
      //   }
      // })
    },
    // 删除行
    delRow(val) {
      this.$confirm('确认删除该合同的全部记录吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = common.loading(this)
        deleteRdProjectContract({ pkId: val.pkId }).then(res => {
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
    // 驼峰映射
    camelToSnake(str) {
      return str.replace(/[A-Z]/g, function(match) {
        return '_' + match
      })
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
    /**
             * 搜索事件
             */
    submitBtn: function() {
      const params = {}
      // const depid = this.searchForm.depId
      // if (depid) {
      //   params.depId = depid.replace('dep', '')
      // }
      params.contractCode = this.searchForm.contractCode
      params.contractName = this.searchForm.contractName
      params.projectName = this.searchForm.projectName
      params.budgetCode = this.searchForm.budgetCode
      params.budgetName = this.searchForm.budgetName
      params.size = this.size
      params.index = this.index
      this.getPagerList(params) // bin
    },
    // 重置按钮
    clearSearch() {
      this.searchForm.contractCode = ''
      this.searchForm.contractName = ''
      this.searchForm.projectName = ''
      this.searchForm.budgetCode = ''
      this.searchForm.budgetName = ''
      this.submitBtn();
    },

    //导出事件
    submitBtnInfo: function(){
      this.$confirm('是否合同表？','是否导出合同表？',{confirmButtonText:'是',cancelButtonText:'否',type:'warning'}).then(_=>{
        const params = {size: 5000, index: 1,_isDown:'Y'}
        // const data = common.paramsFormat(params)
        queryPagerInfo(params).then(res => {
          common.download(res.data,self,"合同表.xls")
        })
      })
    },
    /** 导入 */
    changeUpload(file) {
      console.log("导入", file);
      var formData = new FormData();
      formData.append('file', file.file);
      let data = formData;
      importContract(data).then(res => {
        console.log('导入的响应数据', res);
        if(res.data.status === 200) {
          this.$message.success('导入成功');
          this.loadInit();
        } else {
          this.$message.error(res.data.message);
        }
      })
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
      params.contractCode = this.searchForm.contractCode
      params.contractName = this.searchForm.contractName
      params.projectName = this.searchForm.projectName
      params.budgetCode = this.searchForm.budgetCode
      params.budgetName = this.searchForm.budgetName
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
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
.contractContainer_head {
  float: right;
  margin-bottom: 10px;
}
.contractContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 15px;
}
.tooltips-width{
    max-width: 900px !important;
  }
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

