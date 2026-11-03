<!-- 添加研发人员 -->
<template>
  <div style="padding:10px">
    <el-row style="background:white;padding:0px 10px">
        <p class="nav_name">添加研发人员</p>
    </el-row>
    <el-row style="background:white;padding:10px">
        <div class="param-form-container">
            <el-form :model="paramForm" inline style="float:left;">
                <el-form-item prop="badge" label="员工工号">
                <el-input v-model="paramForm.badge" placeholder="请输入"/>
                </el-form-item>
                <el-form-item prop="empName" label="员工姓名">
                <el-input v-model="paramForm.empname" placeholder="请输入"/>
                </el-form-item>
                <el-form-item label="所属部门">
                    <el-cascader clearable :options="orgTreeData" :props="defaultProp" @change="orgChange"/>
                </el-form-item>
                <el-form-item>
                <buttonOrange :fns="submitBtn" style="margin-left: 10px" button-name="查询" size="mini"/>
                <clean-btn :forms="this.paramForm" size="mini" style="margin-left:10px"/>
                </el-form-item>
            </el-form>
            <el-button style="float:right;margin-right:10px" size="mini" class="lastTopRightButton" @click="$router.back()">返回</el-button>

        <el-button size="mini" style="float:right;margin-right:10px" type="warning" @click="showDialog">添加</el-button>
        <el-dialog
          :before-close="handClose"
          :visible.sync="dialogVisible"
          title="选择到岗日期"
          width="25%"
        >
          <el-row :gutter="20" class="align-row">
            <el-col :span="5" class="align-col">员工姓名</el-col>
            <el-col :span="5" class="align-col">工号</el-col>
            <el-col :span="10" class="align-col">
              请选择到岗日期
              <span style="color: red">*</span>
            </el-col>
          </el-row>
          <hr style="border: none; border-top: 1px solid #ddd;">
          <div v-for = "(column,index) in selectedData" :key="index">
            <el-row :gutter="20" class="align-row">
              <el-col :span="5" class="align-col"> {{ column.empname }} </el-col>
              <el-col :span="5" class="align-col"> {{ column.badge }} </el-col>
              <el-col :span="10" class="align-col">
                <el-date-picker v-model="column.arrivalTime" type="date" style="width:170px" value-format="yyyy-MM-dd"/>
              </el-col>
            </el-row>
          </div>
          <el-row style="margin-top: 30px;">
            <el-col>
              <el-button size="mini" type="warning" style="float:right;margin-right:135px" @click="addData">确认</el-button>
              <el-button size="mini" style="float:right;margin-right:10px" @click="handClose">取消</el-button>
            </el-col>
          </el-row>
        </el-dialog>

            </div>
            <div>
            <el-table :data="employData" fit height="400" highlight-current-row stripe @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" />
                <el-table-column label="工号" prop="badge" align="center" ></el-table-column>
                <el-table-column label="姓名" prop="empname" align="center"></el-table-column>
                <el-table-column label="所属部门" prop="chargeName" width="300" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column label="所属岗位" prop="comorgName" align="center"></el-table-column>
                <!-- <el-table-column label="所属条线" prop="comorgName" align="center"></el-table-column> -->
                <!-- <el-table-column label="职务" prop="headship" align="center"></el-table-column> -->
            </el-table>
            <el-row type="flex" justify="space-between">
                <el-col :span="12">
                <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
                </el-col>
            </el-row>
        </div>
    </el-row>
  </div>
</template>
<script>
import request from "@/utils/request";
import subordingateDepartment from "@/views/systemManager/common/SubordingateDepartment";
import parttimejob from "@/views/systemManager/common/Parttimejob";
import pager from "@/components/Pager";
import common from "@/api/common";
import organization from "@/views/plugins/systemPlug/Organization";
import department from "@/views/plugins/systemPlug/Department";
import cleanBtn from '@/components/CleanBtn'
import buttonOrange from '@/components/eleButton/buttonOrange'
import PersonTable from '@/api/researchManager/rpersonTable'

  export default {
    components: {
      buttonOrange,
      subordingateDepartment,
      parttimejob,
      organization, 
      department,
      cleanBtn,
      pager,

    },
    data() {
      return {
        // aboutLayout 插件
        arrivalTime: '',
        dialogVisible: false,
        totalNumber: 0,
        size: 50, // 初始值 默认：50
        paramForm: {},
        orgTreeData:[],
        selectedData:[],
        employData: [],
        defaultProp: {
          expandTrigger:'hover',
          value:'id',
          label: "text"
        },
        activeName: "user", // tab 默认选中
        index:1
      };
    },
    mounted() {
      this.init();
      this.requestData({ size: this.size, index: 1 });
    },
    methods: {

        requestData(param){
            
            PersonTable.queryAddList(param).then(res =>{
                if(res.data.status == 200){
                    this.employData = res.data.results;
                    this.totalNumber = res.data.total;
                } else {
                    this.$alert.error('获取数据异常')
                }
            })
        },
        // 分页触发
        getPager: function(pageSize, currentPage) {
            let params = {...this.paramForm}
            params.size = pageSize
            params.index = currentPage
            this.requestData(params);
        },
        // 搜索
        submitBtn: function() {
            
            this.getPager(this.size,1);
        },
       
        // 页面初始化
        init: function () {

            let param = {}
            PersonTable.queryOrgTree(param).then(res =>{

                if(res.data.status == 200){
                    this.orgTreeData = res.data.results[0].children
                } else {
                    this.$message.error(res.data.message)
                }

            })
            
        }, 

        handleSelectionChange(datas){
            console.log(datas)
            this.selectedData = datas
        },

    addData() {
      console.log(this.selectedData, this.selectedData.length)
      if (!this.selectedData.every(item => item.hasOwnProperty('arrivalTime') && item.arrivalTime !== null)) {
        this.$message.error('请选择到岗日期')
        return
      }
      if (this.selectedData != null && this.selectedData.length > 0) {
        PersonTable.addPerson(this.selectedData).then(res => {
          if (res.data.status === 200) {
            this.$message({
              message: '添加成功',
              type: 'success'
            })
            history.go(-1)
          } else {
            this.$message.error(res.data.message)
          }
        })
      } else {
        this.$message.error('请至少选择一条数据')
      }
    },
    handClose(done) {
      this.arrivalTime = ''
      this.dialogVisible = false
    },
    // 显示弹窗
    showDialog() {
      if (this.selectedData != null && this.selectedData.length > 0) {
        this.dialogVisible = true
      } else {
        this.$message.error('请至少选择一条数据')
      }
    },
        orgChange(value){

            if(value != null){

                this.paramForm.depid = value[value.length-1]
            }

            else {
                this.paramForm.depid = ''
            }
            
            console.log(this.paramForm);

        }
    }
  };
</script>

<style>
.align-col{
  display: flex;
  align-items: center;
  justify-content: center
}
.align-row{
  display: flex;
  align-items: center;
}
.nav_name {
  width: 180px;
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
}
</style>