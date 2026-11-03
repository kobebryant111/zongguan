<template>
  <div class="employee-container">
    <div class="param-form-container">
      <el-form :model="paramForm" inline>
        <el-form-item prop="badge" label="人员员工">
          <el-input v-model="paramForm.badge" placeholder="人员员工"/>
        </el-form-item>
        <el-form-item prop="empname" label="人员名称">
          <el-input v-model="paramForm.empname" placeholder="人员名称"/>
        </el-form-item>
        <el-form-item label="所属机构">
          <el-input
            v-model="paramForm.compName"
            placeholder="所属机构"
            suffix-icon="el-icon-search"
            @focus="showOrg"
          />
          <organization :roleData="roleData" ref="organization" :fns="getOrgNode"></organization>
        </el-form-item>
        <el-form-item label="所属部门">
          <el-input
            v-model="paramForm.depName"
            placeholder="所属部门"
            suffix-icon="el-icon-search"
            readonly
            @focus="showDep"
          />
          <department ref="department" :fns="getDeptNode" ></department>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="this.paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%">
      <table border="0"  class="jtk-table" >
        
      <tr >
       <td class="td-right" >

       <el-table ref="multipleTable" :data="employData" fit border  height="400" @selection-change="addSelect"   > 
          <el-table-column type="selection" width="55" />             
          <el-table-column label="人员员工"  prop="badge" align="center" ></el-table-column>
          <el-table-column label="人员名称"  prop="empname" align="center"></el-table-column>
          <el-table-column label="所属部门"  prop="chargeName" align="center" show-overflow-tooltip></el-table-column>     
        </el-table>
        <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"  />
       </td> 
         
        
        <td class="td-right">
          <div style="width:100%">
            <el-table :data="employSelectData" fit border height="470"    >
              <el-table-column label="人员员工"  width="200" prop="badge" align="center" ></el-table-column>
              <el-table-column label="人员名称"  width="200"  prop="empname" align="center"></el-table-column>
              <el-table-column width="100" label="操作" align="center">
              <template slot-scope="scope">
                
                <el-button
                  type="danger"
                  class="el-icon-delete"
                  size="mini"
                  @click="onDelete(scope.row)"
                ></el-button>
              </template>
            </el-table-column>     
            </el-table>         
          </div>
        </td>
        
        
          
      </tr>
      
      </table>
    </div>
   
  </div>
</template>
<script>
import pager from "@/components/Pager";
import request from "@/utils/request";
import common from "@/api/common";
import organization from "@/views/plugins/systemPlug/Organization";
import department from "@/views/plugins/systemPlug/Department";
import cleanBtn from '@/components/CleanBtn'
export default {
  components: { pager, organization, department,cleanBtn },
  props: {
    roleData: {
      type: [Array, Object],
      required: true
    } 
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      dialogClose : true,         //dialog 显示
      paramForm: {
        badge: "",
        empname: "",
        compid: '',
        depid: "",
        depName: "",
        compName: ""
      },
      employData: [],
      employSelectData: [],
      olderSelectData: [],
      dialogVisible: false // dailog 是否显示
    };
  },
  mounted() {
    this.init({ size: this.size, index: this.index });
    //this.$refs.multipleTable.clearSelection();
  },
  methods: {
    // 初始化
    init: function(param) {
      let data = common.paramsFormat(param);
      request({
        url: "/commEmployee/queryPager",
        method: "post",
        data: data
      }).then(res => {
        this.employData = res.data.results;
        this.totalNumber = res.data.total;       
      });    
    },
    setSelectData: function(name) {
      
      if (name) {
        var namestr = name.split(',');
        this.employSelectData = [];
        console.log(namestr.length);
        for(var i=0;i<namestr.length;i++){
          var userid = namestr[i].substring(namestr[i].indexOf("(")+1,namestr[i].indexOf(")")) ;
          var username = namestr[i].substring(0,namestr[i].indexOf("("));
          let user = {};
          user.badge = userid;
          user.empname = username;
          //this.$refs.multipleTable.toggleRowSelection(user);
          this.employSelectData.push(user);
        }

      }
    },
    // 分页触发
    getPager: function(pageSize, currentPage) {
      let params = {};
      params.size = pageSize;
      params.index = currentPage;
      params.badge = this.paramForm.badge;
      params.empname = this.paramForm.empname;
      params.compid = this.paramForm.compid;
      params.depid = this.paramForm.depid;
      this.init(params);
    },
    // 搜索
    submitBtn: function() {
       let params = {};
      params.size = this.size;
      params.index = this.index;
      params.badge = this.paramForm.badge;
      params.empname = this.paramForm.empname;
      params.compid = this.paramForm.compid;
      params.depid = this.paramForm.depid;
      this.init(params);
    },
    // 机构列表
    showOrg: function() {
      this.$refs.organization.falg = true;
      this.$refs.organization.filterText = "";
    },
    // 部门列表
    showDep: function() {
      if (this.paramForm.compid ) {
        this.$refs.department.dialogClose = true;
        this.$refs.department.filterText = "";
        this.$refs.department.init({'compid' : this.paramForm.compid,'size':this.$refs.department.size,'index':this.$refs.department.index});
      }
    },
    // 获取机构节点信息
    getOrgNode: function(node) {
      this.paramForm.compName = node.location;
      this.paramForm.compid = node.compid;
      this.$refs.department.init({'compid' : node.compid,'size':this.$refs.department.size,'index':this.$refs.department.index});
    },
    // 获取部门节点信息
    getDeptNode: function(node) {
      this.paramForm.depName = node.location;
      this.paramForm.depid = node.depid;
    },
    //
    addSelect: function(){
     
       var selectdata = this.$refs.multipleTable.selection;
       var oldSelectData = this.employSelectData;
       var newData = new Array;
       if(oldSelectData.length>0 && selectdata.length > 0){
         for(var i =0;i < oldSelectData.length; i++){
           newData.push(oldSelectData[i]);
         }
         for(var i=0;i<selectdata.length;i++){
           var flag = true;
           for(var j=0; j<oldSelectData.length;j++){
             if(selectdata[i].badge == oldSelectData[j].badge){
               flag = false;
               break;
             }
           }
           if(flag){
             newData.push(selectdata[i]);
           }
         }
       }else if(oldSelectData.length>0){
         newData = oldSelectData;
       }else{
         newData = selectdata;
       }

       

       this.employSelectData = newData;
    },
    /**
     * 删除
     */
    onDelete: function(row) {
      var badge = row.badge;
      var employSelectData = this.employSelectData;
      var newdata = new Array;
      if(employSelectData.length>0){
        for(var i=0;i<employSelectData.length;i++){
          if(employSelectData[i].badge != badge){
            newdata.push(employSelectData[i]);
          }
        }
      }
      this.employSelectData = newdata;
      //this.$refs.multipleTable.clearSelection(row);
      //this.$refs.multipleTable.toggleRowSelection(row);
      
    },
    removeAllSelect: function(){
       
       this.employSelectData = null;
    },
  }
};
</script>

