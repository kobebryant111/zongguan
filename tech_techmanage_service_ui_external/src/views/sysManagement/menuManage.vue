<template>
  <div class="app-container">
    <!-- menu tree -->
    <tree-table :data="menuData" :columns="tableColumns" border expandAll >
      <el-table-column label="路由ID" prop="routeID"> </el-table-column>
      <el-table-column label="菜单Code" width="160px" prop="code"> </el-table-column>
      <el-table-column label="菜单类型" align="center" prop="type">
        <template slot-scope="scope">
          <el-tag type="'success'" v-if="scope.row.type == '-1'">
             根目录
          </el-tag>
          <el-tag type="'success'" v-if="scope.row.type == '0'">
             一级菜单
          </el-tag>
          <el-tag type="warning" v-if="scope.row.type == '1'">
             子菜单
          </el-tag>
        </template>
       </el-table-column>
       <el-table-column label="菜单url" prop="url"> </el-table-column>
       <el-table-column label="排序" width="60px" prop="orderNum" align="center"> </el-table-column>
      <el-table-column
        label="操作"
        width="260">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="addMenu(scope.row)" v-if="scope.row.type != '1'">添加</el-button>
          <el-button type="warning" size="mini" @click="updateMenu(scope.row)" v-if="scope.row.type != '-1'">修改</el-button>
          <el-button type="danger" size="mini" @click="deleteConfirmTip(scope.row)" v-if="scope.row.type != '-1'">删除</el-button>
        </template>
      </el-table-column>
    </tree-table>

    <!-- 新增菜单 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="60%">
    <el-form :model="dialogForm" label-width="100px" label-position="right">
      <div style="text-align:center;">
      <table border="0px" style="margin:auto;width:100%">
        <tr>
          <td class="td1"><span style="color:red;">*</span> 菜单名称:</td>
          <td class="td2" cellspancing="12">
              <el-input 
                v-model="dialogForm.name"
                placeholder="请输入菜单名称<必填>"
                clearable
                style="width: 250px;"
                maxlength="30"  
              ></el-input>
            </td>
            <td class="td1"> 路由ID:</td>
            <td class="td2">
                <el-input 
                  v-model="dialogForm.routeID"
                  placeholder="路由ID须和前端路由ID一致<必填>"
                  clearable
                  style="width: 250px;" 
                  maxlength="100" 
                ></el-input>
            </td>
          </tr>
          <tr>
          <td class="td1"><span style="color:red;"></span> 父节点名称:</td>
          <td class="td2" cellspancing="12">
              <el-input 
                v-model="dialogForm.currentName"
                clearable :disabled="true"
                style="width: 250px;" 
              ></el-input>
            </td>
            <td class="td1"> 菜单Code:</td>
            <td class="td2">
                <el-input 
                  v-model="dialogForm.code"
                  placeholder="请输入菜单Code"
                  clearable
                  style="width: 250px;" 
                  maxlength="100" 
                ></el-input>
            </td>
          </tr>
          <tr>
          <td class="td1"> 菜单类型:</td>
          <td class="td2">
            <el-select v-model="dialogForm.type" clerable placeholder="请选择" :disabled="true">
              <el-option label="一级菜单" value="0"/>
              <el-option label="子菜单" value="1"/>
              </el-select>
            </td>
            <td class="td1"><span style="color:red;">*</span> 排序</td>
            <td class="td2">
                <el-input 
                  v-model="dialogForm.orderNum"
                  type="number"
                  placeholder="请输入排序号<必填>"
                  clearable
                  style="width: 250px;" 
                  maxlength="100" 
                ></el-input>
            </td>
          </tr>

          <tr>
          <td class="td1"><span style="color:red;">*</span> 菜单URL:</td>
          <td class="td2" colspan="3">
              <el-input 
                v-model="dialogForm.url"
                placeholder="请输入菜单路径<必填>"
                clearable
                style="width: 100%;"
                maxlength="50"  
              ></el-input>
            </td>
          </tr>
        </table>
        </div>
         <span style="color:red;align:left;">{{tip}}</span>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="addMenuSubmit">确认</el-button>
    </div>
  </el-dialog>


   <!-- 修改菜单 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogUpdateVisible" width="60%">
    <el-form :model="updateDialogForm" label-width="100px" label-position="right">
      <div style="text-align:center;">
      <table border="0px" style="margin:auto;width:100%">
        <tr>
          <td class="td1"><span style="color:red;">*</span> 菜单名称:</td>
          <td class="td2" cellspancing="12">
              <el-input 
                v-model="updateDialogForm.name"
                placeholder="请输入菜单名称<必填>"
                clearable
                style="width: 250px;"
                maxlength="30"  
              ></el-input>
            </td>
            <td class="td1"> 路由ID:</td>
            <td class="td2">
                <el-input 
                  v-model="updateDialogForm.routeID"
                  placeholder="路由ID须和前端路由ID一致<必填>"
                  clearable
                  style="width: 250px;" 
                  maxlength="100" 
                ></el-input>
            </td>
          </tr>
          <tr>
          <td class="td1"> 菜单Code:</td>
          <td class="td2">
              <el-input 
                v-model="updateDialogForm.code"
                placeholder="请输入菜单Code"
                clearable
                style="width: 250px;" 
                maxlength="100" >
              </el-input>
          </td>
          <td class="td1"> 菜单类型:</td>
          <td class="td2">
              <el-select v-model="updateDialogForm.type" clerable placeholder="请选择">
                <el-option label="一级菜单" value="0"/>
                <el-option label="子菜单" value="1"/>
              </el-select>
            </td>
          </tr>

          <tr>
          <td class="td1"><span style="color:red;">*</span> 排序</td>
          <td class="td2">
              <el-input 
                v-model="updateDialogForm.orderNum"
                type="number"
                placeholder="请输入排序号<必填>"
                clearable
                style="width: 250px;" 
                maxlength="2">
                </el-input>
          </td>
          <td class="td1"><span style="color:red;">*</span> 菜单URL:</td>
          <td class="td2" colspan="0">
              <el-input 
                v-model="updateDialogForm.url"
                placeholder="请输入菜单路径<必填>"
                clearable
                style="width: 100%;"
                maxlength="50"  
              ></el-input>
            </td>
          </tr>
        </table>
        </div>
         <span style="color:red;align:left;">{{tip}}</span>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogUpdateVisible = false">取消</el-button>
      <el-button type="primary" @click="updateMenuSubmit">更新</el-button>
    </div>
  </el-dialog>

  </div>
</template>


<style>
  .app-container .el-table--medium td{
    padding: 6px 0;
  }
  .app-container .el-tag{
    margin: 0 3px 0 0;
  }
  .current_role {
    font-size: 14px;
    color: #303133;
    padding-left: 20px;
  }
  .td1{
    text-align : center;
    width: 15%;
    border:1px solid #e6d8d8;
    font-weight: bold;
    background-color: aliceblue;
  }
  .td2{
    text-align:left;
    border:1px solid #e6d8d8;
    padding: 15px 15px 15px 15px
  }
</style>

<script>
  import treeTable from '@/components/TreeTable'
  import menuApi from '@/api/menuApi'
  import { mapGetters } from 'vuex'

  export default {
    name: 'menuManage',
    components: { treeTable },
    data () {
      return {
        editable: true, // 表格是否可编辑 false时表格只展示信息不能修改 true可编辑
        menuData: [], // 菜单权限数据
        treeDefaultProps: {
          children: 'children',
          label: 'roleName'
        },
        tableColumns :[
          {
          text: '菜单名称',
          value: 'name',
          width : "250"
          }
        ],
        tip: "",
        curRow: '',
        curRoleId: '',
        curRoleName: '',
        checkedKeys: [],
        submitData: [],
        canSwitch: false,
        dialogForm: { // 新增Menu弹框表单数据
          name: '',
          routeID : '',
          code: '',
          type: '',
          parentID: '',
          currentName : '',
          orderNum : ''
      }, 
        updateDialogForm: { // 修改Menu弹框表单数据
          name: '',
          mid : '',
          code: '',
          type: '',
          parentID: '',
          orderNum : '',
          routeID : ''
        }, 
          dialogTitle: '', // 弹框标题
          dialogVisible: false, // 弹框显示
          dialogUpdateVisible : false,
        }
      },
    created () {

    },
    mounted(){
      this.loadMenuTree();
    },
    methods: {
       /**
       * 获得菜单树
       */
      loadMenuTree() {
        menuApi.loadMenu().then( res => {
          this.menuData = res.data.results;
        }).catch( error => {
          console.log(error);
        })
      },

      addMenu(row){
        this.dialogForm.parentID = row.mid;
        this.dialogForm.mid = '';
        this.dialogForm.routeID = '',
        this.dialogForm.name = '';
        this.dialogForm.code = '';
        this.dialogForm.url = '';
        this.dialogForm.orderNum = ''
        this.dialogTitle = '新增菜单';
        this.dialogVisible = true;
        if(row.type == '-1'){
          this.dialogForm.currentName = '根目录';
        }else{
          this.dialogForm.currentName = row.name; 
        }
        if(row.type == '-1'){
         this.dialogForm.type = "0";
        }else {
          this.dialogForm.type = "1";
        }
      },

      updateMenu(row){
        menuApi.getMenuByID(row.mid).then(res =>{
          let data = res.data.results;
          this.updateDialogForm.parentID = data.parentID;
          this.updateDialogForm.mid = data.mid;
          this.updateDialogForm.name = data.name;
          this.updateDialogForm.type = data.type + "";
          this.updateDialogForm.code = data.code;
          this.updateDialogForm.url = data.url;
          this.updateDialogForm.orderNum = data.orderNum;
          this.updateDialogForm.routeID = data.routeID;
          this.dialogTitle = '修改菜单';
          this.dialogUpdateVisible = true;
        }).catch( error => {
          console.log(error);
        })
      },


      /**
       * 添加菜单
       */
      addMenuSubmit(){
        let flag = this.dialogFormDataCheck(this.dialogForm);
        if(flag){
          this.tip = "";
          let data = this.dialogForm;
          menuApi.addMenu(data).then(res =>{
            if(res.data.code == '200'){
                this.$message({
                message: res.data.message,
                type: 'success'
              })
              this.dialogVisible = false;
              this.loadMenuTree();
            }else{
                this.$message({
                message: res.data.message,
                type: 'error'
              })
            }
          }).catch( error => {
            console.log(error);
          })
        }
      },

      /**
       * 更新菜单
       */
      updateMenuSubmit(){
        let flag = this.dialogFormDataCheck(this.updateDialogForm);
        if(flag){
          this.tip = "";
          let data = this.updateDialogForm;
          menuApi.updateMenu(data).then(res =>{
            if(res.data.code == '200'){
              this.$message({
              message: res.data.message,
              type: 'success'
            })
            this.dialogUpdateVisible = false;
              this.loadMenuTree();
            }else {
              this.$message({
              message: res.data.message,
              type: 'error'
            })
            }
          })
        }
      },

      /**
       * 表单验证
       */
      dialogFormDataCheck(object){
        if(!object.name){
          this.tip = "* 提示：菜单名称不能为空！";
          return false;
        }if(!object.type){
          this.tip = "* 提示：菜单类型不能为空！";
          return false;
        }if(!object.url){
          this.tip = "* 提示：菜单路劲不能为空！";
          return false;
        }if(!object.orderNum){
          this.tip = "* 提示：菜单排序不能为空！";
          return false;
        }
        return true;
      },


       /** 
       * 提交按钮
      */
      deleteConfirmTip(row) {
        this.$confirm('确认删除当前菜单吗？删除后将会同时删除该菜单下所有子菜单', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteMenu(row);
        }).catch(() => {})
      },


      /**
       * 删除菜单
       */
      deleteMenu(row){
        menuApi.deleteMenu(row.mid).then(res =>{
          if(res.data.code == '200'){
              this.$message({
              message: res.data.message,
              type: 'success'
            })
            this.loadMenuTree();
          }else {
            this.$message({
              message: res.data.message,
              type: 'error'
            })
          }
        })
      }
    },

  }
</script>
