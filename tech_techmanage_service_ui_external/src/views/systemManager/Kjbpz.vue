<!-- 分行科技部配置 -->
<template>
  <div class="app-container">
      <div class="button-container">
        <el-button type="primary" class="el-icon-plus" @click="handelToAdd">新增</el-button>
        <el-button type="danger"  class="el-icon-delete"   @click="onDelete()">删除</el-button>
        
      </div>
      <div class="data-container">
        <el-table ref="multipleTable" :data="kjpzData" stripe highlight-current-row border>
        <el-table-column type="selection" width="55" />
        <el-table-column prop="deptname" label="部门" align="center"/>
        
        <el-table-column width="150" prop="status" label="类型" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == '0'">分行</el-tag>
            <el-tag v-if="scope.row.status == '1'" type="success">子公司</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="250" label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              type="warning"
              class="el-icon-edit"
              size="mini"
              @click="onShowUpdate(scope.row.id)"
            >修改</el-button>
            <el-button
              type="danger"
              class="el-icon-delete"
              size="mini"
              @click="onDelete(scope.row.id)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>
    <!-- 添加 修改 dialog-->
    <div>
      <el-dialog
        width="35%"
        :title="title"
        :visible.sync="visibleClose"
        destroy-on-close
        :before-close="onCancel"
      >
        <el-form v-model="kjbpzForm" ref="kjbpzForm">
          <table width="100%" border="0" class="jtk-table">
            <tr>
              
              <td class="td-right">
                <span class="jkt-tag-red">*</span>类型：
              </td>
              <td class="td-left">
                <el-select 
                  v-model="kjbpzForm.status"
                  placeholder="请选择"
                  :value-key="kjbpzForm.status"
                  class="el-input-cus"
                >
                  <el-option
                    v-for="item of lxData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
            </tr>
            
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>部门：
              </td>
              <td class="td-left">
               <el-input
                  v-model="kjbpzForm.deptname"
                  placeholder="所属部门"
                  suffix-icon="el-icon-search"
                  readonly
                  @focus="showDep"
                />
                <department ref="department" :fns="getDeptNode"></department>
              </td>
            </tr>
          </table>
          <div class="dialog-buttion-container">
            <el-button type="primary" @click="onSave()">确定</el-button>
            <el-button @click="onCancel()">取消</el-button>
          </div>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import request from "@/utils/request";
import common from "@/api/common";
import department from "@/views/plugins/systemPlug/Department";
import subordingateDepartment from "./common/SubordingateDepartment";
import { getDicEntry } from "@/api/eosDictEntry";
import {
  getPagerData,
  getDateil,
  doUpdate,
  doDelete
} from "@/api/kjbpz";
import eosSelect from "@/components/EosSelect";
import cleanBtn from "@/components/CleanBtn";
export default {
  components:{
    pager,
    eosSelect,
    cleanBtn,
    department,
    subordingateDepartment
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      kjbpzForm: {
        // 添加||修改表单
        style: "",
        deptname: "",
        deptid: "",
        id: ""
      },
      kjpzData: [], // 列表数据
      lxData: [], // 类型数据
      visibleClose: false, // dialog 是否关闭
      title: "", // dialog 标题
      eosClear: false // eos-select 是否清空
    }
  },
  mounted() {
    this.packageInit();
  },
  methods: {
    /**
     * 初始化
     */
    init: function(param) {
      console.log("param", param);
      let data = common.paramsFormat(param);
      getPagerData(data).then(res => {
        this.kjpzData = res.data.results;
        this.totalNumber = res.data.total;
      });
      // 业务字典查询
      getDicEntry({
        dicttypeid: "FHKJLB"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        this.initData = data;
        // 类型
        this.lxData = data.filter(item => {
          return item.dicttypeid == "FHKJLB";
        });
        
      }).catch(err => {
        this.$message.error("连接超时，请刷新页面")
      }); // 业务字段查询 end
    }, // 初始 end
    packageInit: function() {
      let param = {};
      param.size = this.size;
      param.index = this.index;
      this.init(param);
    },
    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params.content = this.paramsForm.content;
      if (this.paramsForm.style === "全部") {
        params.style = "";
      } else {
        params.style = this.paramsForm.style;
      }
      params.size = size;
      params.index = index;
      this.init(params);
    },
     /**
     * 显示添加dialog
     */
    handelToAdd: function() {
      this.visibleClose = true;
      this.eosClear = true;
    }, // 显示添加dialog end
    /**
     * dialog 确定事件
     */
    onSave: function() {
      let falg = this.vaildate();
      if (!falg) {
        return false;
      }
      let params = common.paramsFormat(this.kjbpzForm);
      doUpdate(params).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: "error" });
        } else {
          this.$message.success(res.data.message);
          this.packageInit();
          this.onCancel();
        }
      });
      
    }, // dialog 确定事件 end
    /**
     * 表单验证
     */
    vaildate: function() {
      if (this.kjbpzForm.status == "") {
        this.$message.error("类型不能为空");
        return false;
      }
      
      if (this.kjbpzForm.deptname == "") {
        this.$message.error("部门不能为空");
        return false;
      }
      return true;
    }, // 表单验证 end
    /**
     * dialog 取消事件
     */
    onCancel: function() {
      common.cleanFrom(this.kjbpzForm);
      this.visibleClose = false;
      this.eosClear = false;
    }, // dialog 取消事件 end
    /**
     * 部门列表
     */
    showDep: function() {
      //if (this.paramForm.compid && this.paramForm.compid != "-1") {
        this.$refs.department.dialogClose = true;
        this.$refs.department.filterText = "";
        setTimeout(()=>{
            this.$refs.department.init({
              compid: '',
              size: this.$refs.department.size,
              index: this.$refs.department.index
            });
        },10)
       
     // }
    },
    /**
     * 获取部门节点信息
     */
    getDeptNode: function(node) {
      this.kjbpzForm.deptname = node.location;
      this.kjbpzForm.depid = node.depid;
    },
     /**
     * 更新
     */
    onShowUpdate: function(id) {
      getDateil({ id: id }).then(res => {
        if (res.data.status != 200) {
          this.$message.error(res.data.message);
          return false;
        }
        this.kjbpzForm = res.data.results;
      });
      this.visibleClose = true;
    },
    /**
     * 删除
     */
    onDelete: function(id) {
      if(!id){
         id ="";
        var pzdata = this.$refs.multipleTable.selection;
        for(var i =0;i<pzdata.length;i++){
          id += pzdata[i].id+",";
        }
        if(id.indexOf(",")>-1){
          id = id.substring(0,id.length-1);
        }
      }
      
      
      this.$confirm("此操作为永久删除数据，确定继续吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        doDelete({ id: id }).then(res => {
          if (res.data.status != 200) {
            this.$message.error(res.data.message);
            return false;
          } else {
            this.$message.success(res.data.message);
            this.packageInit();
          }
        });
      });
    }
  }
}
</script>
<style lang="scss" scoped>
.button-container {
  margin-bottom: 10px;
}

.jkt-input {
  width: 40%;
}
.jkt-tag-red {
  color: red;
}
.dialog-buttion-container {
  margin-top: 10px;
  text-align: right;
}
.excel-import {
  float: left;
  margin: 0 10px 0 3px;
}
</style>

