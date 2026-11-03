<!-- 用户角色 -->
<template>
  <div>
    <tree-table :data="roleData" :columns="tableColumns" fit border :expandAll="true">
      <el-table-column prop="rolDesc" align="center" label="角色描述"></el-table-column>
      <el-table-column prop="rolId" align="center" label="角色编号" show-overflow-tooltip></el-table-column>
      <el-table-column prop="falgSpecialRole" align="center" label="是否特殊角色" show-overflow-tooltip>
        <template slot-scope="scope">
          <label v-if="scope.row.falgSpecialRole == 0" style="color:red">特殊角色</label>
          <label v-else>正常角色</label>
        </template>
      </el-table-column>
      <el-table-column prop="rolCreateUser" align="center" label="创建人"></el-table-column>
      <el-table-column prop="rolCreateTime" align="center" label="创建时间" :formatter="dateFormat"></el-table-column>
      <el-table-column align="center" label="操作" width="300">
        <template slot-scope="scope">
          <el-button @click="addUserRole(scope.row)" type="primary" icon="el-icon-plus" size="mini" v-if="scope.row.rolLevel != 3">添加</el-button>
          <el-button @click="updateUserRole(scope.row)" type="success" icon="el-icon-edit" size="mini">编辑</el-button>
          <el-button @click="delUserRole(scope.row)" type="danger" icon="el-icon-delete" size="mini" v-if="scope.row.rolLevel != 1 && scope.row.children == null">删除</el-button>
        </template>
      </el-table-column>
    </tree-table>

    <!-- dialog begin -->
    <template>
    <el-dialog title="用户角色" 
    width="30%" 
    v-if="dialogClose"
    :before-close="handleClose" 
    :visible.sync="dialogClose">
        <div class="user-role-container">
            <el-form ref="roleForm" :model="roleForm">
              <table border="0" style="width:100%">
                <tr>
                  <td class="td-right">上级角色：</td>
                  <td class="td-left">
                     <el-input placeholder="上级角色" v-model="roleForm.rolParentName" disabled />
                  </td>
                </tr>
                <tr>
                  <td class="td-right"><span class='requried'>*</span>角色编号：</td>
                  <td class="td-left">
                     <el-input placeholder="角色编号,必须唯一 <必填>" v-model="roleForm.rolId" :disabled="roleForm.sqlStatus === 'add' ? false : true" />
                  </td>
                </tr>
                <tr>
                  <td class="td-right"><span class='requried'>*</span>角色名称：</td>
                  <td class="td-left">
                     <el-input placeholder="角色名称,字数长度不得超过30 <必填>" v-model="roleForm.rolName" maxlength="30" />
                  </td>
                </tr>
                <tr>
                  <td class="td-right"><span class='requried'>*</span>是否特殊角色：</td>
                  <td class="td-left">
                     <el-radio-group v-model="roleForm.falgSpecialRole">
                       <el-radio  label="0">
                         是
                       </el-radio>
                       <el-radio  label="1">
                         否
                       </el-radio>
                     </el-radio-group>
                     
                  </td>
                </tr>
                <tr>
                  <td class="td-right">角色描述：</td>
                  <td class="td-left">
                     <el-input type="textarea" placeholder="角色描述,字数长度不得超过100" v-model="roleForm.rolDesc" maxlength="100" />
                  </td>
                </tr>
              </table>   
              <div class="dilog-error-div">
                <span>{{error}}</span>
              </div>             
              <div class="dialog-footer">
                  <el-button @click="handleClose">取消</el-button>
                  <el-button type="primary" @click="submitBtn">确定</el-button>
              </div>
            </el-form>
        </div>
    </el-dialog>
</template>
<!-- dialog end -->
  </div>
</template>


<script>
import common from "@/api/common";
import treeTable from "@/components/TreeTable";
import request from "@/utils/request";
export default {
  components: { treeTable },
  data() {
    return {
      tableColumns: [
        {
          text: "角色名称",
          value: "rolName",
          width: "250"
        }
      ],
      roleData: [],
      roleForm : {
          rolParentId : '',         // 上级角色编号
          rolName : '',             // 角色名称
          rolDesc : '',             // 角色描述
          rolParentName :'',         // 上级角色
          rolLevel : -1,            // 角色深度
          rolCerateUser:'',         //创建用户
          rolId :'',                // 角色编号
          sqlStatus : '',           // sql状态 add 添加 update 修改
          falgSpecialRole : '1'       // 是否特殊角色 0：特殊，1：正常
      },
      dialogClose : false,
      error :'',                    // 错误提示
    };
  },
  mounted() {
    this.init();
  },
  methods: {
      
    // 页面初始化
    init: function() {
      request({
        url: "/imRole/roleTree",
        method: "get",
        data: {}
      }).then(res => {
        
        if (res.status != 200) {
            this.$alter(res.data.message,{type:'error'});
          return false;
        }
        this.roleData = res.data.results;
      });
    },
    // 添加
    addUserRole : function(item){
        this.dialogClose = true;
        this.roleForm.rolParentName = item.rolName;
        this.roleForm.rolParentId = item.rolId;
        this.roleForm.rolLevel = item.rolLevel + 1;
        this.roleForm.sqlStatus = 'add'
    },   // 添加 end
    /**
     * 编辑
     */
    updateUserRole : function(item) {
        this.roleForm.rolParentName = item.parent.rolName;
        this.roleForm.rolName = item.rolName
        this.roleForm.rolDesc = item.rolDesc
        this.roleForm.rolId = item.rolId
        this.roleForm.falgSpecialRole = item.falgSpecialRole + ''
        this.dialogClose = true;
        this.roleForm.sqlStatus = 'update'
    },   // 编辑 end
    /**
     * 删除
     */
    delUserRole : function(item) {
        let form = this.roleForm;
        this.$confirm('确定删除吗？', {type:'info',
            distinguishCancelAndClose:true,
        }).then((action)=>{
             request({
                    url: "/imRole/delete?rolId=" + item.rolId,
                    method: "delete",
                    data: {}
                }).then(res => {
                    
                    if (res.status != 200) {
                        this.$alert(res.data.message,{type:'error'});
                    return false;
                    }
                    this.$message({
                        type : 'success',
                        message : res.data.message
                    })
                    this.init();
                });
        });
    },
    /**
     * 提交
     */
    submitBtn : function() {
        let falg = this.vaildateDialogForm(this.roleForm);
        if(!falg) {
          return false;
        }
        this.error = '';
        let params = common.paramsFormat(this.roleForm);
        request({
            url : '/imRole/endit',
            method : 'post',
            data : params
        }).then(res =>{
            if(res.data.status != 200) {
                this.$alert(res.data.message,{type:'error'});
                return false;
            }
           this.$message({
                type : 'success',
                message : res.data.message
            })
            // 清空表单
           this.handleClose();
            this.init();
        });
    },  // 提交 end
    /**
     * 表单验证
     */
    vaildateDialogForm : function(object) {
      if(!object.rolName) {
        this.error = '* 提示：角色名称不能为空'
        return false;
      }
      return true;
    },  // 表单验证 end
    /**
     * 时间格式化
     *  
     * */ 
    dateFormat: function(row, column) {
      let str = common.dateFormatOv(row.rolCreateTime);
      return str;
    },
    // 关闭dailog
    handleClose: function() {
      this.dialogClose = false;
      Object.keys(this.roleForm).forEach(key=>(this.roleForm[key]= ''));
      this.error = '';
    },
  }
};
</script>
<style >
.td-right {
  text-align: right;
}
.td-left{
  text-align: left;
}
.requried{
  color: red;
}
.dilog-error-div {
  font-size: 2em;
  background-color: #f1b1e369;
  color: red;
  text-align: center
}
.dialog-footer {
  margin-top: 10px;
  text-align: right;
}
</style>
