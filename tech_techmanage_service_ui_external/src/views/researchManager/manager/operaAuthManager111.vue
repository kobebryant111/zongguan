<!-- 操作权限管理 -->
<template>
  <!-- bin改 -->
  <div class="powContent">
  <div class="pow_left">
    <about-layout :roleData="roleData" :defaultProp="defaultProp" :nodeKey="nodeKey" :expandedKeys="[1]" v-model="$attrs"
      :fns="getCurrentNode" :filterNodeMethod="getFilterNode">
    </about-layout>
  </div>
  <div class="pow_right">
      <!-- 人员分配 -->
      <!-- <el-tab-pane label="人员分配" name="user">-->
          <div>
            <el-form :model="paramForm" inline>
              <el-form-item prop="badge" label="人员员工">
                <el-input v-model="paramForm.badge" placeholder="人员员工" />
              </el-form-item>
              <el-form-item prop="empname" label="人员名称">
                <el-input v-model="paramForm.empname" placeholder="人员名称" />
              </el-form-item>
              <el-form-item label="所属机构">
                <el-input v-model="paramForm.compName" placeholder="所属机构" suffix-icon="el-icon-search" @focus="showOrg" />
                <organization :roleData="orgData" ref="organization" :fns="getOrgNode"></organization>
              </el-form-item>
              <el-form-item label="所属部门">
                <el-input v-model="paramForm.depName" placeholder="所属部门" suffix-icon="el-icon-search" readonly @focus="showDep" />
                <department ref="department" :fns="getDeptNode"></department>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitBtn">查询</el-button>
                <clean-btn :forms="this.paramForm" />
              </el-form-item>
            </el-form>
          </div>
          <el-table :data="userData" fit border height="300" stripe highlight-current-row>
            <el-table-column label="人员员工" prop="badge" align="center"></el-table-column>
            <el-table-column label="人员名称" prop="empname" align="center"></el-table-column>
            <el-table-column label="所属机构" prop="compName" align="center"></el-table-column>
            <el-table-column label="所属部门" prop="chargeName" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="所属岗位" prop="comorgName" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="danger" @click="delRoleUserRel(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" />
          <div class="footer-container-div">
            <template>
              <el-button type="primary" @click="showUserDialog">选择人员</el-button>
            </template>
          </div>
        <!-- dialog begin -->
        <div class="dialog-user-container-div">
          <template>
            <el-dialog title="用户列表" :visible.sync="dialogVisible" width="85%" top="10vh">
              <employee :roleData="dialogUserData" :resetPwdVisible="dialogResetPwd" @childevent="getDialogUserRow" ref="employee"></employee>
              <div>
                <el-button type="primary" @click="selectedUser">确定</el-button>
                <el-button @click="dialogCancel">取消</el-button>
              </div>
            </el-dialog>
          </template>
        </div>
        <!-- dialog end -->
  </div>
  </div>
</template>
<script>
  import request from "@/utils/request";
  import aboutLayout from "@/components/AboutLayout";
  import subordingateDepartment from "../../systemManager/common/SubordingateDepartment";
  import parttimejob from "../../systemManager/common/Parttimejob";
  import employee from "../../systemManager/common/Employee";
  import organization from "@/views/plugins/systemPlug/Organization";
  import department from "@/views/plugins/systemPlug/Department";
  import pager from "@/components/Pager";
  import cleanBtn from "@/components/CleanBtn";
  import common from "@/api/common";


  export default {
    components: {
      aboutLayout,
      employee,
      subordingateDepartment,
      parttimejob,
      organization,
      department,
      pager,
      cleanBtn
    },
    data() {
      return {
        // aboutLayout 插件
        roleData: [], // 角色数据
        orgData: [], // 机构数据
        userData: [], // 员工分配数据
        mData: [], // 菜单数据
        dialogUserData: [], // dialog 用户数据
        defaultProp: {
          children: "children",
          label: "rolName"
        },
        mProps: {
          children: "children",
          label: "name"
        },
        nodeKey: "rolId",
        activeName: "menu", // tab 默认选中
        checkedNode: {}, //左边树 选中的节点对象
        paramForm: {
          // 人员分配对象
          badge: "", // 工号
          empname: "", // 名称
          compid: "",
          depid: "",
          depName: "",
          compName: ""
        },
        /**
         * 分页属性
         */
        totalNumber: 0,
        size: 50, // 初始值 默认：50
        index: 1, // 初始值 默认：1

        defaultExpandedKeys: [], // 菜单默认打开的节点
        showTabs: false, // tab 是否显示

        dialogVisible: false, // dialog 显示 默认不显示
        dialogBadge: "", // 用于dialog用户选中用户后储存用户id
        orgProps: {}, //props属性默认必填项（避免控制台报错，对程序无影响）
        dialogResetPwd: true
      };
    },
    mounted() {
      this.init();
    },
    methods: {
      // 页面初始化
      init: function () {       
        // 角色数据
        request({
          url: "/imRole/operaRoleTree",
          method: "get"
        }).then(res => {
          console.log('登录用户信息', this.$store.getters.userId)
          this.roleData = res.data.results;
        });
        
        // dialog 用户数据
        request({
          url: "/commOrganization/queryOrgTree",
          method: "get"
        }).then(res => {
          console.log('用户数据', res.data.results)
          this.dialogUserData = res.data.results;
        });
      }, // 页面初始化 end
      
      
      /**
       * tab选中触发
       */
      handleClick: function (tab, event) {
        console.log(tab, event);
      }, // tab选中触发 end
      
      // 机构树选中触发
      getCurrentNode: function (item) {
        this.showTabs = true;
        this.checkedNode = item;
        this.queryUserRoleRel();
      },
      /**
       * 查询用户角色关联表信息
       */
    queryUserRoleRel: function () {
      this.checkedNode.size = this.size;
      this.checkedNode.index = this.index;
      let newData = Object.assign(this.paramForm, this.checkedNode);
      console.log("newData", newData);
      let param = common.paramsFormat(newData);
      request({
        url: "/commEmployee/queryOperaRoleUserRel",
        method: "post",
        data: param
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        console.log("res.data.total", res.data);
        this.userData = res.data.results;
        this.totalNumber = res.data.total;
      });
    }, // 查询用户角色关联表信息 end
    /**
       * 角色过滤
       */
      getFilterNode: function (val, data) {
        return data.rolName.indexOf(val) !== -1;
      },
      
      /**
       * 员工搜索
       */
      submitBtn: function () {
        this.queryUserRoleRel();
      }, // 员工搜索 end
      /**
       *  分页触发
       */
      getPager: function (pageSize, currentPage) {
        let params = {};
        this.size = pageSize;
        this.index = currentPage;
        this.queryUserRoleRel();
      }, // 分页触发 end
      /**
       * 机构列表
       */
      showOrg: function () {
        this.$refs.organization.falg = true;
        this.$refs.organization.filterText = "";
      },
      /**
       * 部门列表
       */
      showDep: function () {
        if (this.paramForm.compid && this.paramForm.compid != "-1") {
          this.$refs.department.dialogClose = true;
          this.$refs.department.filterText = "";
          this.$refs.department.init({
            compid: this.paramForm.compid,
            size: this.$refs.department.size,
            index: this.$refs.department.index
          });
        }
      },
      /**
       * 获取机构节点信息
       */
      getOrgNode: function (node) {
        this.paramForm.compName = node.location;
        this.paramForm.compid = node.compid;
        this.$refs.department.init({
          compid: node.compid,
          size: this.$refs.department.size,
          index: this.$refs.department.index
        });
      },
      /**
       * 获取部门节点信息
       */
      getDeptNode: function (node) {
        this.paramForm.depName = node.location;
        this.paramForm.depid = node.depid;
      },
      /**
       * 显示用户信息
       */
      showUserDialog: function () {
        this.dialogVisible = true;
      }, // 显示用户信息 end
      /**
       *  选中dialog 用户 表行
       */
      getDialogUserRow: function (data) {
        console.log(data,"dialogBadge")
        this.dialogBadge = data;
      }, // 选中dialog 用户 表行 end
      /**
       * 选择dialog 用户
       */
      selectedUser: function () {
        let _obj = {};
        _obj.urrRoleid = this.checkedNode.rolId;
        _obj.urrUserid = this.dialogBadge;
        if (_obj.urrUserid === '') {
          this.$message.error('请选择用户');
          return false
        }
        let _data = common.paramsFormat(_obj);
        request({
          url: "/imUserRoleRel/addOperaUserRole",
          method: "post",
          data: _data
        }).then(res => {
          if (res.data.status !== 200) {
            this.$alert(res.data.message, { type: "error" });
          } else {
            this.$message({
              message: res.data.message,
              type: "success",
              center: "true"
            });

            this.queryUserRoleRel();
          }
        });
        this.dialogCancel();
      }, // 选择dialog 用户 end
      /**
       *
       */
      dialogCancel: function () {
        this.dialogVisible = false;
        let userForm = this.$refs.employee.paramForm;
        Object.keys(userForm).forEach(key => {
          userForm[key] = "";
        });
      },
      /**
       * 删除角色用户关联
       */
      delRoleUserRel: function (row) {
        console.log("row", row);
        this.$confirm("确定删除吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(_ => {
            request({
              url:
                "/imUserRoleRel/delete?urrUserid=" +
                row.badge +
                "&urrRoleid=" +
                this.checkedNode.rolId,
              method: "delete"
            }).then(res => {
              if (res.data.status !== 200) {
                this.$alert(res.data.message, { type: "error" });
                return false;
              }
              // 刷新数据
              this.queryUserRoleRel();
              this.$message({
                message: res.data.message,
                type: "success",
                center: "true"
              });
            });
          })
          .catch(_ => { });
      } // 删除角色用户关联 end
    }
  };
</script>
<style scoped>
  .footer-container-div {
    margin-top: 10px;
  }

  .powContent {
    display: flex;
    flex-direction: row;
    padding: 15px;
  }

  .pow_left {
    margin-right: 15px;
    width: 500px;
  }
</style>