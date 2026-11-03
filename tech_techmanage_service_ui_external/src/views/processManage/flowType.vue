<!-- 流程调度管理维护 -->
<template>
  <div>
    <table-tree
      :data="mData"
      :columns="tableColumns"
      :expandAll="true"
      fit
      border
      :show-header="false"
    >
      <el-table-column prop="username" label="流程管理员" show-overflow-tooltip></el-table-column>

      <el-table-column label="操作" width="230">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="addMenu(scope.row)"
            v-if="scope.row.children != undefined"
          >添加</el-button>
          <el-button
            type="warning"
            size="mini"
            @click="updateMenu(scope.row)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            @click="deleteConfirmTip(scope.row)"
            v-if="scope.row.type != '-1' && scope.row.children == null"
          >删除</el-button>
        </template>
      </el-table-column>
    </table-tree>
    <!-- dialog -->
    <div>
      <template>
        <el-dialog :title="title" :visible.sync="dialogClose" width="30%">
          <el-form ref="menuForm" :model="menuForm" :rules="rules">
            <el-form-item label="流程类型" prop="flowname">
              <el-input v-model="menuForm.flowname"/>
            </el-form-item>
            <el-form-item label="流程编号" prop="flowdraftno">
              <el-input v-model="menuForm.flowdraftno" maxlength="50"/>
            </el-form-item>
            <el-form-item label="流程管理员" prop="username">
              <el-input v-model="menuForm.username">
                <el-button slot="append" @click="showClerk()" class="el-icon-more"></el-button>
              </el-input>
            </el-form-item>
          </el-form>
          <div class="dialog-footer">
            <el-button @click="dialogCancel">取消</el-button>
            <el-button type="primary" @click="dialogAddSubmit">确定</el-button>
          </div>
        </el-dialog>
      </template>
    </div>

    <!-- 穿梭框 -->
    <div>
      <template>
        <el-dialog
          title="人员列表"
          :visible.sync="dialogTrClose"
          width="80%"
          :before-close="transferColse"
        >
          <elt-transfer
            ref="eltTransfer"
            :show-query="true"
            :left-columns="leftColums"
            :right-columns="rightColumns"
            :table-row-key="row=> row.badge"
            :pagination-call-back="pages"
            :show-pagination="true"
            v-model="userData"
          >
            <template slot="leftCondition" slot-scope="{scope}">
              <el-form-item label="工号">
                <el-input v-model="scope.badge" placeholder="工号" size="mini"></el-input>
              </el-form-item>
              <el-form-item label="姓名">
                <el-input v-model="scope.empname" placeholder="姓名" size="mini"></el-input>
              </el-form-item>
              <el-form-item label="部门">
                <el-input v-model="scope.chargeName" placeholder="部门" size="mini">
                  <template slot="append">
                    <el-button class="el-icon-more" @click="clickDept(scope)"></el-button>
                  </template>
                </el-input>
              </el-form-item>
            </template>
          </elt-transfer>

          <div>
            <organization
              ref="organization"
              :role-data="orgData"
              :modal="false"
              :fns="orgCallBack"
              node-keys="id"
              :default-props="props"
            ></organization>
          </div>
          <span slot="footer">
            <el-button @click="dialogTrCancel">取 消</el-button>
            <el-button type="primary" @click="dialogTrConfrim">确 定</el-button>
          </span>
        </el-dialog>
      </template>
    </div>
  </div>
</template>
<script>
import TableTree from "@/components/TableTree";
import common from "@/api/common";
import { queryPager } from "@/api/userApi/bankUser";
import EltTransfer from "@/components/eltTransfer/eltTransfer";
import Organization from "@/views/plugins/systemPlug/Organization";
import {
  getFlowTree,
  deleteFlowTpye,
  updateFlowType
} from "@/api/processApi/notice";
import { getDeptTwo } from "@/api/systemApi/department";
export default {
  components: { TableTree, EltTransfer, Organization },
  data() {
    return {
      title: "", // dialog 标题
      mData: [],
      tableColumns: [
        {
          text: "流程列表",
          value: "flowname",
          width: "250"
        }
      ],
      dialogClose: false, //dialog 显示
      dialogTrClose: false, // 穿梭框 dialog 显示
      menuForm: {
        // 表单
        id: "", // id
        flowname: "", // 流程类型
        flowdraftno: "", // 流程编号
        parentid: "", // 父id
        userid: "", // 管理员id
        username: "" // 管理员名称
      },
      emData: [], // 行员数据
      // 左侧列
      leftColums: [
        { label: "工号", id: "badge", width: "120px" },
        { label: "姓名", id: "empname", width: "120px" },
        { label: "部门", id: "chargeName", width: "200px" }
      ],
      rightColumns: [
        { label: "工号", id: "badge" },
        { label: "姓名", id: "empname" }
      ],
      userData: [], // 已选择的行员信息 - 穿梭框
      orgData: [], // 机构数据集合
      props: { children: "children", label: "text" }, // 机构字段绑定
      rules: {
        flowname: [
          { required: true, message: "请输入流程类型", trigger: "blur" }
        ],
        flowdraftno: [
          { required: true, message: "请输入流程编号", trigger: "blur" }
        ],
        username: [
          { required: true, message: "请选择流程管理员", trigger: "blur" }
        ]
      },
      tempFlowTypeData : {},      // 临时数据 - 获取当前选中的数据
      tempStatus : '',      // add or update
    };
  },
  mounted() {
    let self = this;
    self.init();
    getDeptTwo({ objectcode: "9999999999", objectgrade: "2" }).then(res => {
      if (200 !== res.data.status) {
        self.$alert(res.data.message, { type: "error" });
        return false;
      }
      self.orgData = res.data.results;
    });
  },
  methods: {
    // 页面初始化
    init: function() {
      let self = this;
      getFlowTree().then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.mData = res.data.results;
        console.log("菜单管理数据", res.data.results);
      });

      let data = {};
      queryPager(data).then(res => {
        if (res.data.status != 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        self.emData = res.data.results;
      });
    }, // 页面初始化 end
    /**
     * 添加菜单
     */
    addMenu: function(item) {
      this.title = "添加流程";
      this.dialogClose = true;
      console.log('添加流程',item)
      this.tempFlowTypeData = item
      this.tempStatus = 'add'
    }, // 添加菜单 end
    /**
     * 编辑菜单
     */
    updateMenu: function(item) {
      const self = this;
      self.title = "编辑流程";
      self.dialogClose = true;
      self.menuForm.id = item.id;
      self.menuForm.flowname = item.flowname;
      self.menuForm.flowdraftno = item.flowdraftno;
      self.menuForm.userid = item.userid;
      self.menuForm.username = item.username;
      this.tempStatus = 'update'
      console.log("item", item);
    }, // 编辑菜单 end
    /**
     * 删除
     */
    deleteMenu: function(item) {
      deleteFlowTpye({ id: item.id }).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.$message.success(res.data.message);
        this.init()
      });
    }, // 删除
    /**
     * 删除提示
     */
    deleteConfirmTip: function(item) {
      this.$confirm("确认删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteMenu(item);
        })
        .catch(() => {});
    }, // 删除提示 end
    /**
     *  提交保存
     */
    dialogAddSubmit: function() {
      this.$refs.menuForm.validate(vaild => {
        if (vaild) {
          if(this.tempStatus === 'add') {
            this.menuForm.parentid = this.tempFlowTypeData.flowdraftno
          }
          console.log('menuForm', this.menuForm)
          let datas = common.paramsFormat(this.menuForm);
          updateFlowType(datas).then(res => {
            if (res.data.status != 200) {
              this.$alert(res.data.message, { type: "error" });
            } else {
              this.dialogCancel();
              this.$message({
                type: "success",
                message: res.data.message
              });
              this.init();
            }
          });
        }
      });
    }, // 提交保存 end
    /**
     * 关闭dialog
     */
    dialogCancel: function() {
      this.dialogClose = false;
      Object.keys(this.menuForm).forEach(key => {
        this.menuForm[key] = "";
      });
    }, // 关闭dialog end
    /**
     * 穿梭框 分页
     */
    pages(item) {
      let params = {};
      params = item;
      params.size = item.pageSize;
      params.index = item.pageIndex;

      let data = common.paramsFormat(params);
      return queryPager(data).then(res => {
        let pagerData = {};
        pagerData.data = res.data.results;
        pagerData.total = res.data.total;
        return pagerData;
      });
    },
    /**
     * 关闭穿梭框dialog
     */
    dialogTrCancel() {
      this.transferColse();
    },
    /**
     * 显示dialog 所有行员
     */
    showClerk() {
      let self = this;
      self.dialogTrClose = true;
      console.log('self.menuForm.userid',self.menuForm.userid.length)
      if (self.menuForm.userid.length > 0 || "" != self.menuForm.userid) {
        let userids = self.menuForm.userid.match(/[0-9]+/g);
        let userNames = self.menuForm.username.match(/[\u4e00-\u9fa5]+/g);
        let epmData = [];
        for (let i = 0; i < userids.length; i++) {
          let user = {};
          user.badge = userids[i];
          user.empname = userNames[i];
          epmData.push(user);
        }
        console.log("epmData::", epmData);
        self.userData = epmData;
      }
    },
    dialogTrConfrim() {
      console.log("userData", this.userData);
      let tempUserids = []
      let tempNames = []
      this.userData.forEach((v,k) => {
        tempUserids.push(v.badge)
        tempNames.push(v.empname + '(' + v.badge + ')')
      })
      this.menuForm.username = tempNames.join(',')
      this.menuForm.userid = tempUserids.join(',')
      this.transferColse()
    },
    /**
     * 选择部门
     */
    clickDept(row) {
      let self = this;
      self.$refs.organization.falg = true;
    }, // end
    /**
    机构回调函数
   */
    orgCallBack(item) {
      console.log("机构回调函数", item);
      let query = this.$refs.eltTransfer.leftQueryCondition;

      this.$set(query, "chargeName", item.text);
      this.$set(query, "depid", item.id.replace(item.attr, ""));
    },
    transferColse() {
      this.$refs.eltTransfer.clear();
      this.dialogTrClose = false;
    }
  }
};
</script>
<style >
.dialog-footer {
  margin-top: 10px;
  text-align: right;
}
.td-right {
  text-align: right;
}
.td-left {
  text-align: left;
}
.dialog-error-div {
  background-color: #f1b1e369;
  text-align: center;
}
.dialog-error {
  color: red;
  font-size: 2em;
}
.required {
  color: red;
}
</style>



