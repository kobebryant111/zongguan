<template>
<!--  eslint -disable -->
<!-- 研发人员考勤 -->
  <div class="typeset-container" v-loading >
    <el-row :gutter="20" >
        <research-person-select ref="resPersonSelect" :multipleChoice="true" :showRed="true" :queryType="2" :list="selectedDepid" :date="attendanceMonth" />
        <el-col :span="18"  >
            <el-row style="background:white;padding:10px;">
                <span>考勤周期：</span>
                <el-select style="display:inline-block;" v-model="attendanceMonth">
                  <el-option
                    v-for="item in monthList"
                    :key="item.attendanceDate"
                    :value="item.attendanceDate"
                    :label="item.attendanceDate"
                  />
                </el-select>
                <div style="float:right;display:inline-block;">
                    <buttonOrange style="margin-left:10px;" button-name="获取考勤" :fns="getResearchAttendance" size="small" v-hasFunc="['1352_LICENCE']"/>
                    <buttonOrange v-if="!editable" style="margin-left:10px;" button-name="修改" :fns="()=>{editable=true}" size="small" v-hasFunc="['1356_LICENCE']"/>
                    <buttonOrange style="margin-left:10px;" button-name="驳回" :fns="cancelAttendance" size="small" v-hasFunc="['1353_LICENCE']"/>
                    <buttonOrange style="margin-left:10px;" button-name="通过" :fns="passAttendance" size="small" v-hasFunc="['1354_LICENCE']"/>
                    <buttonOrange style="margin-left:10px;" button-name="提交造册" v-loading.fullscreen.lock="submitEndLoading" :fns="endAttendance" size="small" v-hasFunc="['1355_LICENCE']"/>
                    <el-button style="margin-left:10px;" size="small" v-if="showBackBtn" class="lastTopRightButton" @click="$router.back()">返回</el-button>
                    <el-dialog 
                    title="考勤导出"
                    :visible.sync="dialogVisible"
                    width="30%"
                    :before-close="handleClose">
                    <el-select v-model="monthDataMonth" placeholder="请选择月份">
                     <el-option
                    v-for="item in monthData"
                    :key="item.attendanceDate"
                    :value="item.attendanceDate"
                    :label="item.attendanceDate"
                  />
                    </el-select>
                    <span slot="footer" class="dialog-footer">
                      <el-button @click="dialogVisible = false">取消</el-button>
                      <el-button type="primary" @click="outputAttendance">导出</el-button>
                    </span>
                    </el-dialog>
                </div>
            </el-row>
            <el-row style="background:white;padding:10px;">
                <el-table
                    v-loading="loading"
                    element-loading-text='考勤数据加载中'
                    element-loading-spinner='el-icon-loading'
                    element-loading-background='gba(0,0,0,0.8)'
                    ref="projectTable"
                    :data="attendanceData"
                    style="width: 100%;"
                    stripe
                    max-height="800"
                    border
                    align="center"
                    :cell-style="cellFormat"
                    default-expand-all
                    row-key="id"
                    :tree-props="{children:'projects',hasChildren:'hasChildren'}"
                    >
                        <el-table-column label="" fixed="left" width="300" >
                            <template slot-scope="scope"> 
                                <span v-if="!scope.row.projectCode">{{scope.row.username + '('+ scope.row.badge+')'}}{{ checkFunc(['00000_HIDDEN']) === false ? '['+getDictName('NEWKG_RD_NATURE',scope.row.nature)+']' : ''}}</span>
                                <span v-if="scope.row.projectCode">[{{getDictName('RD_PROJECT_STATE',scope.row.projectState)}}]
                                  {{scope.row.projectName}}(<span style="text-size:10px">{{scope.row.projectCode}})<br>项目合同预算总金额(万元):{{formatAmount(scope.row.budgetAmount)}}</span></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="proportion" fixed="left" label="占比" v-if="attendanceData != undefined && attendanceData.length > 0" align="center" width="60" :formatter="(row, column, cellValue, index)=>(cellValue?(cellValue*100).toFixed(2)+'%':'')"  >
                            <template slot-scope="scope">
                                {{ calculate(scope.row) }}
                            </template>
                        </el-table-column>
                        <!-- <el-table-column prop="badge" label="工号" width="100" align="center"/>
                        <el-table-column prop="nature" label="研发人员性质" width="120" align="center" :formatter="(row, column, cellValue, index)=>getDictName('NEWKG_RD_NATURE',cellValue)"/>
                        <el-table-column prop="projectCode" label="研发项目编号" width="120" align="center"/>
                        <el-table-column prop="projectName" label="项目名称" width="120" align="center" />
                        <el-table-column prop="projectState" label="项目状态" align="center" width="120" :formatter="(row, column, cellValue, index)=>getDictName('RD_PROJECT_STATE',cellValue)" /> -->
                        <template v-if="attendanceData != undefined && attendanceData.length > 0">
                            <el-table-column v-for="(data,idx) in attendanceData[0]['rdCalendarList']" :key="idx" align="center" :label="formatDateColumnTitle(data['dayOfMonth'])" width="65"  >
                            <template slot-scope="scope">
                                <span>
                                    <el-select v-if="!scope.row.projectCode && scope.row.rdCalendarList[idx]['editing']"
                                      v-model="scope.row.rdCalendarList[idx]['attendanceOrno']"
                                      :disabled="isDisabled(scope.row,scope.row.rdCalendarList[idx])"
                                      size="mini" style="width: 60px" 
                                      class="left-align_select"
                                      @change="attendanceOrnoChange(scope.row.rdCalendarList[idx])">
                                        <el-option label="非工作日" value="2" disabled/>
                                        <el-option label="出勤" value="1"/>
                                        <el-option label="未出勤" value="0"/>
                                    </el-select>
                    <span
                      v-if="!scope.row.projectCode && !scope.row.rdCalendarList[idx]['editing'] "
                      :disabled="isDisabled(scope.row,scope.row.rdCalendarList[idx])"
                      class="span-wrapper"
                      @mouseenter="startEidt(scope.row.rdCalendarList,idx)"
                    >
                      <span :class="{'not-editable':!editable || isDisabled(scope.row,scope.row.rdCalendarList[idx])}" class="inline-span">
                        {{ attendanceText(scope.row.rdCalendarList[idx]['attendanceOrno']) }}
                      </span>
                    </span>
     
                                    <div v-if="scope.row.rdCalendarList[idx]['attendanceOrno'] === '1'">
                                    <span v-if="(!editable || scope.row.auditStatus != '2') && scope.row.projectCode ">
                                      {{scope.row.rdCalendarList[idx]['attendanceTime']}}
                                    </span>
                                    <el-popover
                                        v-if ="(!(!editable || scope.row.auditStatus != '2')&& scope.row.projectCode) && scope.row.rdCalendarList[idx]['editing'] "
                                        placement="bottom"
                                        title="编辑"
                                        trigger="click"
                                        v-hasFunc="['1351_LICENCE']"
                                    >
                                        <div v-if="scope.row.projectCode">
                                            <el-button size="mini" round :type="checkedBtn(scope,idx,'1')" style="display:inline-block" @click="choseWorkload(scope,idx,'1')" >1</el-button>
                                            <el-button size="mini" round :type="checkedBtn(scope,idx,'0')" style="display:inline-block" @click="choseWorkload(scope,idx,'0')" >0</el-button>
                                            <el-button size="mini" round :type="checkedBtn(scope,idx,'0.2')" style="display:inline-block" @click="choseWorkload(scope,idx,'0.2')" >0.2</el-button>
                                            <el-button size="mini" round :type="checkedBtn(scope,idx,'0.5')" style="display:inline-block" @click="choseWorkload(scope,idx,'0.5')" >0.5</el-button>
                                            <el-button size="mini" round :type="checkedBtn(scope,idx,'0.8')" style="display:inline-block" @click="choseWorkload(scope,idx,'0.8')" >0.8</el-button>
                                        </div>
                                        <!-- <div v-if="!scope.row.projectCode">
                                            <el-button size="mini" round :type="checkedBtn(scope,idx,'1')" style="display:inline-block" @click="choseWorkload(scope,idx,'1')" >出勤</el-button>
                                            <el-button size="mini" round :type="checkedBtn(scope,idx,'0')" style="display:inline-block" @click="choseWorkload(scope,idx,'0')" >请假</el-button>
                                            <el-button size="mini" round :type="checkedBtn(scope,idx,'0.5')" style="display:inline-block" @click="choseWorkload(scope,idx,'0.5')" >半天假</el-button>
                                        </div> -->
                                        <el-input slot="reference" class="date-input" v-model="scope.row.rdCalendarList[idx]['attendanceTime']" @click="this.select()"  />
                                    </el-popover>
                      <span
                        v-hasFunc="['1351_LICENCE']"
                        v-if ="(!(!editable || scope.row.auditStatus != '2')&& scope.row.projectCode) && !scope.row.rdCalendarList[idx]['editing'] "
                        class="input-span-wrapper"
                        @mouseenter="startEidt(scope.row.rdCalendarList,idx)">
                        <span
                          class="input-span"
                        >
                          {{ scope.row.rdCalendarList[idx]['attendanceTime'] }}
                        </span>
                      </span>
                                    </div>
                                </span>
                            </template>
                            </el-table-column>
                        </template>   
                        
                </el-table>
            </el-row>
        </el-col>
    </el-row>

  </div>

</template>

<script>
/* eslint-disable */
import common from "@/api/common";
import PersonTable from '@/api/researchManager/rpersonTable'
import pager from '@/components/Pager'
import ResearchPersonSelect from './researchPersonSelect.vue'
import buttonOrange from '@/components/eleButton/buttonOrange'
import {groupBy} from '@/utils/index'
export default {
  name: 'attendanceFillin',
  components: {
    pager,
    ResearchPersonSelect,
    buttonOrange,
  },
  data() {
    return {
      size: 10, // 初始值 默认：10
      index: 1, // 初始值 默认：1
      monthDataMonth:'',
      monthData:[],
      dialogVisible:false,
      submitEndLoading:false,
      loading:false,
      attendanceMonth:'',
      monthList:[],
      attendanceData:[],    //考勤数据
      attendancePerson:[],  //考勤人员列表
      dialogIsShow:false,
      showBackBtn:false,
      fullScreenLoading:false,
      editable:false,
      selectedDepid: []
    }
  },
  created() {

    if (this.$route.params.date != undefined) {
        this.attendanceMonth = this.$route.params.date  
        this.showBackBtn = true
    }

    const params = {}
    params.size = 24
    params.index = 1
    PersonTable.queryMonthList(params).then(res => {
        
        if (res.data.status == 200) {
          this.monthData = res.data.results
          this.monthList = res.data.results.filter(res=>{
            return res.attendanceStatus == '1'
          })
          if (this.monthDataMonth == '') {
              this.monthDataMonth = this.monthData[0]['attendanceDate']
          }
          if (this.attendanceMonth == '') {
              this.attendanceMonth = this.monthList[0]['attendanceDate']
          }

        } else {
          this.$message.error(res.data.message)
        }
      })

  },
  computed:{

    formatDateColumnTitle(){

        return (date) => {
            return date+"日"        
        }
        
    },
    checkedBtn(){

        return (scope,colIndex,value) => {

            if (scope.row.rdCalendarList[colIndex]['attendanceTime'] == value) {
                return 'primary'
            } else {
                return ''
            }

        }
    },

  },
  watch:{
    "attendanceMonth":{
        handler(n,o){
          
            if (n) {
              this.attendanceData = []
              this.$refs.resPersonSelect.requestPersonData(n)
            }
        }
    }
    
  },
  methods: {
     // 四舍五入显示
    formatAmount(cellValue) {
      return Number(cellValue).toFixed(2)
    },
    // 禁用下拉框
    isDisabled(data,params) {
      if (params['isHoliday'] === 'Y') return true
        const today = new Date(params['calendarDate'].split('T')[0])
        const arrivalTime = new Date(data.arrivalTime)
        if (!this.editable) {
          return true
        }
        if (data.empstatus !== '0' && data.leaveTime ) {
            const leaveTime = new Date(data.leaveTime)
            return !(today < leaveTime && today >= arrivalTime )
        }
        return today < arrivalTime
    },
    //处理单元格样式
    cellFormat({row,column,rowIndex,columnIndex}){

        if(columnIndex >=2){
            var cellData = row.rdCalendarList[columnIndex-2]
            if( !this.editable || (cellData != null && (cellData.isHoliday == 'Y' || row.auditStatus != '2')) ){
                return 'background: #f5f5f5;'    
            }
            if (cellData != null && cellData.attendanceOrno != '1') {
                return 'background: #f5f5f5;'  
            }
        }
    },
    //选择工作量
    choseWorkload(scope,colIndex,value){

        this.$set(scope.row.rdCalendarList[colIndex],'attendanceTime',value)
      
    },
    // 是否出勤
    attendanceOrnoChange(data) {
        // debugger
        console.log(data)
        PersonTable.hrResourcesAllocation(data).then(res=>{
          if (res.data.status == 200) {
            this.$message.success('出勤状态修改成功')
            this.getResearchAttendance()
          } else {
            this.$message.error('出勤状态修改失败')
          }
        })
    },
    //关闭弹窗
    handleClose(done){
      this.$confirm('确认关闭').then(_=>{
        done();
      }).catch(_=>{});
    },
    // 获取考勤数据
    getResearchAttendance() {
        
        const selectedData = this.$refs.resPersonSelect.getCheckNodes()
        this.selectedDepid = selectedData.map(item => item.badge)
        if (selectedData.length == 0) {
            this.$message.error('请选择至少一位员工')
            return false
        }
        const params = {commRdEmployeeDtoList:selectedData,ppmKqRdCalendar:{attendanceDate:this.attendanceMonth}}
        params.index = 1
        params.size = 1000
        this.loading = true
        //获取研发项目考勤数据和人员考勤数据
        // Promise.all([PersonTable.getApproveAttendance(params),PersonTable.queryPersonAttendance(params)]).then(datas => {
        //     this.loading = false
        //     if (datas[0].data.status == 200 && datas[1].data.status == 200) {
        //         const attendance = datas[0].data.results
                
        //         let personAttendance = datas[1].data.results
                
        //         //将研发项目考勤数据按人员进行分组
        //         let researchAttendance  = groupBy(attendance, person => {
        //             return [person.badge];
        //         });
                
        //         //将研发项目考勤数据拼入员工考勤数据中
        //         let rid= 0 
        //         personAttendance.forEach((item,index) => {
        //             // item.id = rid++
        //             // item.rdCalendarList.forEach(rd=>{
        //             //     if (rd.attendanceTime == '1') {
        //             //         rd.text = '出勤'
        //             //     }
        //             //     else if(rd.attendanceTime == '0.5'){
        //             //         rd.text = '半天假'
        //             //     }
        //             //     else if (rd.attendanceTime == '0') {
        //             //         rd.text = '请假'
        //             //     }
                        
        //             // })
                    
        //             researchAttendance.forEach(person => {
                        
        //                 if(item.badge == person[0].badge){
        //                     person.forEach(p=>{
        //                         p.id = rid++
        //                     })

        //                     item.projects = person
        //                     return
        //                 }
        //             })
        //         })
                
        //         this.attendanceData = personAttendance
                
        //     } else {

        //         this.$message.error("获取数据异常")
        //     }
            
        // })
      PersonTable.getApproveAttendance(params).then(datas => {
            this.loading = false
            if (datas.data.status == 200 ) {
                const attendance = datas.data.results
                // let personAttendance = datas[1].data.results
                let personAttendance = []
                //将研发项目考勤数据按人员进行分组
                let researchAttendance  = groupBy(attendance, person => {
                    return [person.badge];
                });
                //将研发项目考勤数据拼入员工考勤数据中
                let rid= 0 
                researchAttendance.forEach(person => {
                    let informat = {}
                    informat.id = rid++
                    // informat.eid = person[0].eid
                    informat.badge = person[0].badge
                    informat.nature = person[0].nature
                    informat.username = person[0].username
                    informat.rdCalendarList = person[0].rdCalendarList
                    informat.projects = person
                    personAttendance.push(informat)
                            person.forEach(p=>{
                                p.id = rid++
                            })
                })
                this.attendanceData = personAttendance
            } else {
                this.$message.error("获取数据异常")
            }
            
        })
    },
    // 占比计算
    calculate(val) {
        if (val.eidCode === undefined) return null
        let days = 0
        let time = 0
        val.rdCalendarList.forEach(val => {
            if (val.isHoliday !== 'Y' && val.attendanceOrno == '1')  {
                time = time + ((typeof val.attendanceTime === 'number' && !isNaN(val.attendanceTime)) ? val.attendanceTime : Number(val.attendanceTime))
                days++
            }
        })
        return time === 0 ? null : (time/days*100).toFixed(2)+'%'
    },
    // 点击输入
    startEidt(row, idx) {
      if (this.editable) {
        this.$set(row[idx], 'editing', true)
      }
    },
    attendanceText(val) {
      if (val === '1') {
        return '出勤'
      } else if (val === '0') {
        return '未出勤 '
      } else if (val === '2') {
        return '非工作日'
      }
    },
    //导出考勤信息
    outputAttendance(){
      // const selectedData = this.$refs.resPersonSelect.getCheckNodes()
      //   if (selectedData.length == 0) {
      //       this.$message.error('请选择至少一位员工')
      //       return false
      //   }
      
        const list= this.monthData.filter(res=>{
          return res.attendanceDate==this.monthDataMonth
        })
        const params = {ppmKqRdCalendar:{attendanceDate:this.monthDataMonth,attendanceStatus:list[0]['attendanceStatus']},_isDown:'Y'}       
        this.$confirm('是否导出'+this.monthDataMonth+'考勤表？','考勤导出确认',
        {confirmButtonText:'是',cancelButtonText:'否',type:'warning'})
      .then(_=>{
          console.log(params,"==========")
          const self = this;
          PersonTable.infoApproveAttendance(params).then(res=>{
          common.download(res.data,self,this.monthDataMonth+"考勤信息导出文件.xls")
          })
      })
      this.dialogVisible = false
    },

    outputAllAttendance(){
      this.dialogVisible = true
    },

    //通过
    passAttendance(){

      if (this.attendanceData.length == 0) {
          this.$message.error('当前无考勤可以审批')
          return false
      }

      this.$confirm('是否通过这'+this.attendanceData.length+'名员工的考勤表？','考勤通过确认',{confirmButtonText:'是',cancelButtonText:'否',type:'warning'})
      .then(_=>{

        let reqData = []
        this.attendanceData.forEach(item =>{

            item.projects.forEach(pro=>{
              pro.rdCalendarList = pro.rdCalendarList.map(c=>{
                if (c.attendanceTime == undefined) {
                  c.attendanceTime == 0
                }
                return c
              })
            })

            reqData = reqData.concat(item.projects)
        })
        // console.log(reqData);
        
        this.loading = true
        
        PersonTable.submitAttendance(reqData).then(res=>{
            
            if (res.data.status == 200) {
              let param = {commRdEmployeeDtoList:reqData,ppmKqRdCalendar:{attendanceDate:this.attendanceMonth}}      
              PersonTable.approvePass(param).then(res=>{
                this.loading = false
                if (res.data.status == 200) {
                  this.$message.success("考勤审核通过")
                  this.$refs.resPersonSelect.requestPersonData(this.attendanceMonth)
                  this.editable = false
                } else {
                  this.$message.error(res.data.message)
                }
              })

            } else {
                this.loading = false
                this.$message.error(res.data.message)
            }
            this.getResearchAttendance()
        })
      })
    },
    //驳回
    cancelAttendance(){

      if (this.attendanceData.length == 0) {
          this.$message.error('当前无考勤可以审批')
          return false
      }

      this.$confirm('是否驳回这'+this.attendanceData.length+'名员工的考勤表？','考勤驳回确认',{confirmButtonText:'是',cancelButtonText:'否',type:'warning'})
      .then(_=>{
        
        let reqData = []
        this.attendanceData.forEach(item =>{
            reqData = reqData.concat(item.projects)
        })
        
        this.loading = true
        let param = {commRdEmployeeDtoList:reqData,ppmKqRdCalendar:{attendanceDate:this.attendanceMonth}}
        PersonTable.approveCancel(param).then(res=>{
            this.loading = false
            if (res.data.status == 200) {
                this.$message.success("考勤已驳回")
                this.$refs.resPersonSelect.requestPersonData(this.attendanceMonth)
            } else {
                this.$message.error(res.data.message)
            }
        })
      })
    },
    //提交造册
    endAttendance(){
        
      this.$confirm('是否确定将'+this.attendanceMonth+'的所有考勤提交造册？','提交造册确认',{confirmButtonText:'是',cancelButtonText:'否',type:'warning'})
      .then(_=>{

          this.submitEndLoading = true
          let param = {attendanceDate:this.attendanceMonth,attendanceStatus:'2'}
          PersonTable.submitEndAttendance(param).then(res=>{
            this.submitEndLoading = false
            if (res.data.status == 200) {
              this.$message.success("提交造册完成")
            } else {
              this.$message.error(res.data.message)
            }
          })
      })
    }

  }

}
</script>

<style>

.typeset-container {
  padding: 20px;
}

.date-input .el-input__inner{
    border:0px;
    padding:0px;
    text-align: center;
}
.left-align_select .el-input--suffix{
    padding:0px;
}
.left-align_select .el-input__inner{
    padding:3px;
}
.left-align_select .el-select__wrapper {
    border: none;
    box-shadow: none;
}

.span-wrapper{
  display:inline-block;
  margin-top: 8px;
  width: 100%;
  box-sizing: border-box
}
.inline-span{
  display: inline-block;
  box-sizing: border-box;
  height: 29px;
  width: 57px;
  line-height: 30px;

  align-items: center;
  padding: 0 11px;
  padding-left: 0px;

  background-color: #ffffff;
  border: 1px solid #dcdfe6;
  border-radius: 4px;

  color: #606266;
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;

  transition: border-color .2s cubic-bezier(0.645, 0.045, 0.355, 1);
  font-size: 12px;

}
.not-editable {
  background-color: #f5f7fa;
  cursor: default
}

.input-span-wrapper{
  display:inline-block;
  margin-top: 8px;
  width: 100%;
  box-sizing: border-box
}
.input-span{
  box-sizing: border-box;
  display: inline-block;
  background-color: #ffffff;
  height: 36px;
  width: 44px;
  line-height: 35px;
  border-radius: 4px;
  font-size: 15px;
  white-space: nowrap;
  overflow: hidden;
}
</style>
