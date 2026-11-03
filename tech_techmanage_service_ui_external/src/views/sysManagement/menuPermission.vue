<template>
  <div class="app-container">
    <!-- page title -->
    <el-row style="margin-bottom:10px;">
      <el-col :span="12">
        <el-switch
          size="mini"
          v-model="editable"
          active-text="编辑"
          inactive-text="查看"
          :active-value="true"
          :inactive-value="false"
          :disabled="canSwitch"
          ></el-switch>
          <span class="current_role">当前角色：{{curRoleName}}</span>
      </el-col>
      <el-col :span="12" style="text-align:right;">
        <el-button type="primary" @click="handleSubmit">提交</el-button>         
      </el-col>
    </el-row>
    <!-- menu tree -->
    <tree-table :data="menuData" :columns="tableColumns" border expandAll >
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
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.flag ? 'success' : 'info'">
            {{ scope.row.flag ? '开启' : '关闭' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="editable" label="是否启用" width="100" align="center">
        <template slot-scope="scope">
          <el-switch v-if="scope.row.type != '-1'" @change="stateChange(scope.row)" v-model="scope.row.flag"></el-switch>
        </template>
      </el-table-column>
    </tree-table>

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
</style>

<script>
  import treeTable from '@/components/TreeTable'
  import permissApi from '@/api/permissionsApi'
  import { mapGetters } from 'vuex'

  export default {
    name: 'menuPermission',
    components: { treeTable },
    data () {
      return {
        editable: true, // 表格是否可编辑 false时表格只展示信息不能修改 true可编辑
        tableColumns: [
          {
            text: '菜单名称',
            value: 'name'
          }
        ],
        menuData: [], // 菜单权限数据
        treeDefaultProps: {
          children: 'children',
          label: 'roleName'
        },
        dialogVisible: false, // 表格是否显示
        curRow: '',
        curRoleId: '',
        curRoleName: '',
        submitData: [],
        canSwitch: false
      }
    },
    created () {
      this.curRoleId = this.$route.query.roleId
      this.curRoleName = this.$route.query.roleName
      this.editable = this.$route.query.editable
      this.loadPermiss();
      let roleIndex = this.$store.getters.roleIdx
      let roles = this.$store.getters.roles
      if (roles[roleIndex].roleId == this.curRoleId && this.curRoleId != '00000001') {
        this.editable = false
        this.canSwitch = true
      } else {
        this.editable = true
        this.canSwitch = false
      }
    },
    computed: {
      ...mapGetters([
        'sidebar',
        'name',
        'avatar',
        'device',
        'roles'
      ])
    },
    methods: {
      /**
       * 获取当前角色对应菜单权限
       */
      loadPermiss() {
         let roleId = this.curRoleId
         console.log('roleId=====>', roleId)
         permissApi.loadPermissions(roleId).then( res => {
          this.menuData = res.data.results;
        }).catch( error => {
          console.log(error);
        })
      },

      /**
       * 是否开启开关的改变事件
       */
      stateChange(row){
        if(row.flag){
          if(row.parent && row.parentID != '-1'){
            row.parent.flag = true;
          }
        }else {
          if(row.parent && row.parentID != '-1'){
            let children = row.parent.children;
            let num = 0;
            for(let i in children){
              if(children[i].mid == row.mid){
                continue;
              }else {
                if(children[i].flag){
                  num ++;
                }
              }
            }
            if(num == 0){
              row.parent.flag = false;
            }
          }
        }
      },

      /** 
       * 提交按钮
      */
      handleSubmit() {
        this.$confirm('确认提交该角色权限分配？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.submit()
        }).catch(() => {})
      },
      /**
       * 提交角色权限
       */
      submit() {
        this.submitData = []
        this.getSubmitData(this.menuData);
        let roleItemRelList = this.submitData;
        permissApi.permissionsSubmit(roleItemRelList,this.curRoleId).then(res => {
          if(res.data.code == '200'){
            this.$message({
              message: res.data.message,
              type: 'success'
            })
          this.$store.dispatch('delView', this.$route)
          this.$router.push({ path: '/sysManagement/roleManagement'})
          }else {
            this.$message({
              message: res.data.message,
              type: 'error'
            })
          }
        }).catch(error => {
          console.log(error)
        })
      },

       /**
       * 递归获取提交数据
       */
      getSubmitData(menu){
        let childrenList = menu.children;
        this.findNodeFlag(childrenList);
      },

      findNodeFlag(arrays){
        for(let i = 0; i < arrays.length;i++){
          if(arrays[i].flag){
            let obj = {
                mid: arrays[i].mid,
                rid: this.curRoleId,
              }
            this.submitData.push(obj);
            let array = arrays[i].children;
            if(array.length != 0){
              this.findNodeFlag(array);
            }
          }
        }
      },
    },
  }
</script>
