<template>
  <!-- 研发人员添加项目弹框     -->
  <el-dialog
    v-loading="loading"
    :visible="isShow"
    width="70%"
    title="添加项目"
    element-loading-text="项目数据加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="gba(0,0,0,0.8)"
    @close="close">
    <p>{{ deptName }}主管的研发项目</p>
    <el-table ref="inChargeTable" :data="inChargeProjectData" style="width:100%;" max-height="400px" @selection-change="handleInChargeChange">
      <el-table-column :selectable="handleDisable" type="selection" width="55"/>
      <el-table-column :show-overflow-tooltip="true" label="项目名称" prop="projectName" width="200" />
      <el-table-column label="项目关联合同预算名称" align="left" width="500" >
        <template slot-scope="scope">
          <el-tooltip placement="top">
            <div slot="content" class="tooltip-content">{{ scope.row.budgetName }}</div>
            <span class="mutiline-column">{{ scope.row.budgetName }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column :formatter="formatAmount" label="项目合同预算总金额(万元)" align="center" prop="budgetAmount" width="120" />
      <el-table-column :formatter="(row,column,cellvalue,index)=>(getDictName('RD_PROJECT_STATE',cellvalue))" label="项目状态" align="center" prop="projectState" width="180" />
    </el-table>
    <p>{{ deptName }}参与的研发项目</p>
    <el-table ref="invlvedTable" :data="invlvedProjectData" style="width:100%" max-height="400px" @selection-change="handleInvlvedChange" >
      <el-table-column :selectable="handleDisable" type="selection" width="55" />
      <el-table-column :show-overflow-tooltip="true" label="项目名称" prop="projectName" width="200" />
      <el-table-column label="项目关联合同预算名称" align="left" width="500" >
        <template slot-scope="scope">
          <el-tooltip placement="top">
            <div slot="content" class="tooltip-content">{{ scope.row.budgetName }}</div>
            <span class="mutiline-column">{{ scope.row.budgetName }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column :formatter="formatAmount" label="项目合同预算总金额(万元)" align="center" prop="budgetAmount" width="120" />
      <el-table-column :formatter="(row,column,cellvalue,index)=>(getDictName('RD_PROJECT_STATE',cellvalue))" label="项目状态" align="center" prop="projectState" width="180" />
    </el-table>
    <el-button size="small" class="lastTopRightButton" @click="close">取消</el-button>
    <buttonOrange v-loading.fullscreen.lock="fullScreenLoading" :fns="submitBtn" style="margin-left: 10px" button-name="确认" size="mini"/>
  </el-dialog>
</template>

<script>
import buttonOrange from '@/components/eleButton/buttonOrange'
import PersonTable from '@/api/researchManager/rpersonTable'
import pager from '@/components/Pager'

export default {
    name: 'dialogAddProject',
    components: {
        buttonOrange,
        pager
    },
    props:{
        isShow: {
            type: Boolean,
            required:true,
            default:false
        },
        person:{
            queryType : Object,
            required:true,
        }
    },
    data() {
        return {
            test: '张三\n李四\n王五',
            deptName:'',
            inChargeProjectData:[],
            invlvedProjectData:[],
            selectedInChargeProjectData:[],
            selectedInvlvedProjectData:[],
            loading:false,
            fullScreenLoading:false
        }
    },
    created(){
        
    },
    watch:{
    "isShow":{
      handler(n,o){
        
        if (n) {
            this.deptName = this.person.chargeName
            this.requestData()      
        }
      }
    }
  },
  methods: {
    // 四舍五入显示
    formatAmount(row, column, cellValue) {
      return Number(cellValue).toFixed(2)
    },
        close() {
            this.$emit('update:isShow',false)
            this.$emit('refrushData')
        },
        submitBtn(){
            let selectedList = this.selectedInChargeProjectData.concat(this.selectedInvlvedProjectData)
            selectedList = selectedList.filter(item=>{
                return item.isChosen == '0'
            })
            const params = {hrProjectList:selectedList,commRdEmployeeDto:{eid:this.person.eid}}
            
            this.fullScreenLoading = true
            PersonTable.addResource(params).then(res=>{
                this.fullScreenLoading = false
                if (res.data.status == 200) {
                    this.$message.success('添加成功')
                } else {
                    this.$message.error(res.data.message)
                }
            })

        },
        requestData(){

            this.inChargeProjectData.length = 0
            this.invlvedProjectData.length = 0
            this.selectedInChargeProjectData = []
            this.selectedInvlvedProjectData = []

            this.loading = true
            PersonTable.queryResourceByDept(this.person).then(res=>{
                this.loading = false
                if (res.data.status == 200) {
                    
                    res.data.results.forEach((res,i)=>{
                        if (res.competentDept == this.person.depid.toString()) {
                            this.inChargeProjectData.push(res)
                            if (res.isChosen == '1') {
                                this.$refs.inChargeTable.toggleRowSelection(res)
                            }
                        } else {
                            this.invlvedProjectData.push(res)
                            if (res.isChosen == '1') {
                                this.$refs.invlvedTable.toggleRowSelection(res)
                            }
                        }
                    })
                }
            })
        },
        handleInChargeChange(val){
            this.selectedInChargeProjectData = val
        },
        handleInvlvedChange(val){
            this.selectedInvlvedProjectData = val
        },
        handleDisable(row){
            console.log(row);
            if (row.isChosen == '1') {
                return false
            } else {
                return true
            }
        }

    }
}
</script>

<style scoped>
.container {
  padding: 20px;
}
.mutiline-column {
    /* white-space: pre-line; /** 保留换行符并自动换行 */
    display: -webkit-box;
    -webkit-line-clamp: 2; /**限制显示两行 */
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis; /**超出部分显示省略号 */
}
.tooltip-content{
    white-space: pre-line; /** 保留换行符并自动换行 */
}
</style>
