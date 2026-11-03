<!-- 外包账号关联-->
<template>
  <div class="app-container">
    <!-- 列表分页 -->
    <div class="table-container">
      <div>
        <el-button type="primary" @click="addBtn">添 加</el-button>
        
      </div>
      <el-table :data="data" border stripe>
        <el-table-column label="工号(科技管理平台工号)" prop="userName"></el-table-column>
        <el-table-column label="工号(OA工号)" prop="realteUserName"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="onEdit(scope.row)" size="mini">编辑</el-button>
            <el-button type="danger" @click="onDelete(scope.row)" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>
    <!-- 编辑 -->
    <div class="edit-container">
      <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="30%"
        @close="dialogClose"
        v-if="dialogVisible"
      >
        <div class="dialog-container">
          <el-form :model="formData" ref="formData" label-width="150px">
            <el-form-item label="科技管理平台工号">
              <el-input v-model="formData.userName" placeholder size="mini">
                <el-button slot="append" @click="showUserTree(1)">...</el-button>
              </el-input>
            </el-form-item>
            <el-form-item label="OA工号">
              <el-input v-model="formData.realteUserName" placeholder size="mini">
                <el-button slot="append" @click="showUserTree(2)">...</el-button>
              </el-input>
            </el-form-item>
          </el-form>
        </div>

        <span slot="footer">
          <el-button @click="dialogClose">取 消</el-button>
          <el-button type="primary" @click="submitFormBtn">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 用户树 -->
      <el-dialog
        title="选择列表"
        :visible.sync="treeVisible"
        width="30%"
        @close="treeDialogClose"
        v-if="treeVisible"
      >
        <table>
          <tbody>
            <tr>
              <td>
                <el-input v-model="searchUser" placeholder="员工号/员工名称/员工名称拼音"></el-input>
              </td>
              <td>
                <el-button type="primary" @click="searchUserBtn">搜索</el-button>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="tree">
          <el-tree
            :data="treeData"
            :props="defaultProps"
            lazy
            show-change
            node-key="id"
            check-strictly
            :load="loadnode"
            @node-click="nodeClick"
          ></el-tree>
        </div>
        <span slot="footer">
          <el-button @click="treeDialogClose">取 消</el-button>
          <el-button type="primary" @click="treeUserBtn">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import common from "@/api/common";
import {
  pageList,
  edit,
  userTreeList,
  deleteItem
} from "@/api/systemApi/relateUser";
export default {
  components: {
    pager
  },
  data() {
    return {
      data: [], // 分页集合数据
      /**
       * 分页属性
       */
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      //=======================
      dialogTitle: "", // dialog 标题
      dialogVisible: false, // dialog 默认显示隐藏
      treeVisible: false, // dialog 默认显示隐藏
      formData: {
        id: "",
        userno: "",
        relateUserno: "",
        userName: "",
        realteUserName: ""
      }, // 表单
      formDataTemp: {
        id: "",
        userno: "",
        relateUserno: "",
        userName: "",
        realteUserName: ""
      }, // 临时表单
      // ============ 用户树
      treeData: [], // 树对象数据
      defaultProps: {
        // 树对象属性对应关系
        children: "children",
        label: "text"
      },
      tag: 0, // 标记当前点击的是 1：科技管理平台 还是:2：OA
      searchUser: "" // 用户树搜索用户的条件
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    /**
     * 初始化加载
     */
    init() {
      pageList().then(res => {
        this.data = res.data.results;
      });
    }, // 初始化加载 end
    /**
     *  分页触发
     */
    getPager: function(pageSize, currentPage) {
      let params = {};
      this.size = pageSize;
      this.index = currentPage;
      this.queryUserRoleRel();
    }, // 分页触发 end
    /**
     * 跳转添加
     */
    addBtn() {
      this.dialogVisible = true
      
    },    // end
    /**
     * dialog 关闭
     */
    dialogClose() {
      let self = this;
      self.dialogVisible = false;
      common.cleanFrom(self.formData);
      common.cleanFrom(self.formDataTemp);
    }, // end
    /**
     * 用户树dialog 关闭
     */
    treeDialogClose() {
      let self = this;
      self.treeVisible = false;
      self.searchUser = "";
    }, // end
    /**
     * 提交
     */
    submitFormBtn() {
      let self = this;
      let data = common.paramsFormat(self.formData);
      edit(data).then(res => {
        if (res.status !== 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        self.$message.success(res.data.message);
        self.dialogClose();
        self.init();
      });
    }, // end
    /**
     * 用户树-确定事件
     */
    treeUserBtn() {
      let self = this;
      self.formData.userno = self.formDataTemp.userno || self.formData.userno;
      self.formData.userName =
        self.formDataTemp.userName || self.formData.userName;
      self.formData.relateUserno =
        self.formDataTemp.relateUserno || self.formData.relateUserno;
      self.formData.realteUserName =
        self.formDataTemp.realteUserName || self.formData.realteUserName;
      self.treeVisible = false;
      self.searchUser = "";
    }, // end

    /**
     * 详情
     */
    onEdit(item) {
      let self = this;
      self.formData.id = item.id;
      self.formData.userno = item.userno;
      self.formData.userName = item.userName;
      self.formData.relateUserno = item.relateUserno;
      self.formData.realteUserName = item.realteUserName;
      self.dialogVisible = true;
    }, // END
    /**
     * 删除
     */
    onDelete(item) {
      this.$confirm("此操作为永久删除数据，确定继续吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        deleteItem({ id: item.id }).then(res => {
          if (res.data.status !== 200) {
            self.$alert(res.data.message, { type: "error" });
            return false;
          }
          this.$message.success(res.data.message)
          this.init()
        });
      });
    }, // END
    /**
     * 显示用户树
     */
    showUserTree(index) {
      this.treeVisible = true;
      this.tag = index;
    }, // end
    /**
     * 加载节点
     */
    loadnode(node, resolve) {
      // 如果展开第一级节点， 从后台加载一级节点列表
      if (node.level === 0) {
        this.loadFirstNode(resolve);
      }
      // 如果展开节第二节点，从treeData数据直接取children
      if (node.level === 1) {
        if (undefined != this.treeData[0].children) {
          resolve(this.treeData[0].children);
        } else {
          resolve([]);
        }
      }
      // 如果展开其他节点， 动态从后台加载下一级节点列表
      if (node.level > 1) {
        this.loadChildNode(node, resolve);
      }
    }, // end
    /**
     * 第一次加载节点
     */
    loadFirstNode(resolve) {
      userTreeList().then(res => {
        this.treeData = res.data.results;
      });
    }, // end
    /**
     *  刷新树组件
     */
    refreshTree() {
      let self = this;
      userTreeList().then(res => {
        this.treeData = res.data.results;
      });
    }, // end
    /**
     * 加载节点的子节点集合
     */
    loadChildNode(node, resolve) {
      let params = node.data.attr.split("-");
      if (params.length < 3) {
        resolve([]);
        return false;
      }
      let data = {};
      data.id = params[0];
      data.current = params[1];
      data.next = params[2];
      userTreeList(data).then(res => {
        resolve(res.data.results);
      });
    }, // end
    /**
     * 点击节点上触发事件，传递三个参数，数据对象使用一个参数
     */
    nodeClick(data, dataObj, self1) {
      let self = this;
      if (data.attr !== "emp") {
        return false;
      }
      if (self.tag === 1) {
        self.formDataTemp.userno = data.id;
        self.formDataTemp.userName = data.text;
      } else {
        self.formDataTemp.relateUserno = data.id;
        self.formDataTemp.realteUserName = data.text;
      }
    }, // end
    /**
     * 搜索用户树列表
     */
    searchUserBtn() {
      let self = this;
      if (self.searchUser == null || self.searchUser.length <= 0) {
        self.$message.error("搜索条件不能为空");
        return false;
      }
      userTreeList({ searchUser: self.searchUser }).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        self.treeData = res.data.results;
      });
    } // end
  }
};
</script>
<style scoped>
.tree {
  overflow: auto;
  max-height: 400px;
}
</style>


