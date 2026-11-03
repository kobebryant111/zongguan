<!-- 分行外包采购审批部门 -->
<template>
  <div class="app-container">
    <div>
      <button-orange :buttonName="addText" :fns="handleAdd"></button-orange>
    </div>
    <el-form :model="form" ref="form" label-width="80px">
      <el-table
        :data="data"
        ref="pzTable"
        border
        stripe
        highlight-current-row
        @cell-click="handleRowClick"
        @current-change="handleCurrentRow"
      >
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column prop="deptname" label="部门" >
            <template slot-scope="scope">
              <el-form-item prop="newDeptname">
              <span v-if="scope.row.current && scope.row.ctype == '部门'">
                <el-input v-model="scope.row.deptname" disabled>
                  <template slot="append">
                    <el-button type="primary" @click="handleOrg()" icon="el-icon-more"></el-button>
                  </template>
                </el-input>
              </span>
              <span v-else>{{scope.row.deptname || scope.row.newDeptname}}</span>
              </el-form-item>
            </template>
        </el-table-column>
        <el-table-column prop="hgbzjlname" label="合规部总经理">
          <template slot-scope="scope">
            <span v-if="scope.row.current && scope.row.ctype == '合规部总经理'">
              <el-input v-model="scope.row.hgbzjlname" disabled>
                <template slot="append">
                  <el-button type="primary" @click="handleUser()" icon="el-icon-more"></el-button>
                </template>
              </el-input>
            </span>
            <span v-else>{{scope.row.hgbzjlname || scope.row.newHgbzjlname}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="rlzybzjlname" label="人力资源部总经理">
          <template slot-scope="scope">
            <span v-if="scope.row.current && scope.row.ctype == '人力资源部总经理'">
              <el-input v-model="scope.row.rlzybzjlname" disabled>
                <template slot="append">
                  <el-button type="primary" @click="handleUser()" icon="el-icon-more"></el-button>
                </template>
              </el-input>
            </span>
            <span v-else>{{scope.row.rlzybzjlname || form.newRlzybzjlname}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="fhyfjlName" label="分行研发经理">
          <template slot-scope="scope">
            <span v-if="scope.row.current && scope.row.ctype == '分行研发经理'">
              <el-input v-model="scope.row.fhyfjlName" disabled>
                <template slot="append">
                  <el-button type="primary" @click="handleUser()" icon="el-icon-more"></el-button>
                </template>
              </el-input>
            </span>
            <span v-else>{{scope.row.fhyfjlName || form.newFhyfjlName}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="handleUpdate(scope.row)">保存</el-button>
            <el-button type="error" @click="handleDel(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <!-- dialog -->
    <div>
      <el-dialog
        :title="title"
        :visible.sync="visibleClose"
        :width="width"
        :lock-scroll="false"
        @close="dialogClose"
      >
        <!-- 机构树 -->
        <el-tree
          :data="orgData"
          :props="defaultProps"
          @current-change="getCurrentNode"
          highlight-current
          v-if="type == 1"
        ></el-tree>
        <div v-if="type == 2">
          <el-input v-model="filterText" placeholder="请输入名称或工号"></el-input>
          <!-- 员工树 -->
          <el-tree
            ref="empTree"
            :data="empData"
            :props="defaultProps"
            @current-change="getCurrentNode"
            highlight-current
            :filter-node-method="filterNode"
          ></el-tree>
        </div>

        <span slot="footer">
          <el-button @click="dialogClose">取 消</el-button>
          <button-orange :buttonName="handleOkText" :fns="handleOk"></button-orange>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {
  getList,
  queryBranchThree,
  getEmpTree,
  deleteById,
  updateOrAdd
} from "@/api/branchManager/fhwbcgspsz";
import common from "@/api/common";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon";
export default {
  components: {
    buttonOrange,
    buttonCommon
  },
  data() {
    return {
      data: [], // 后台数据
      visibleClose: false,
      title: "", // dialog 标题
      width: "30%", // dialog 宽度
      handleOkText: "确 定",
      form: {
        newDeptname: "", // 新部门
        newDeptno: "", // 新部门编号
        newHgbzjlname: "", // 新合规
        newHgbzjl: "", // 新合规编号
        newRlzybzjlname: "", // 新人力资源
        newRlzybzjl: "", // 新人力资源编号
        newFhyfjlName: "", // 新分行研发
        newFhyfjl: "" // 新分行研发编号
      }, // 表单
      type: 1, // 弹框类型 1：部门，2：人员机构树
      defaultProps: {
        label: "text",
        children: "children"
      },

      orgData: [], // 机构数据
      selectedData: {}, // dialog弹框树选中的数据
      moData: [], // 修改过的数据
      empData: [], // 员工机构树
      rowData: {}, // table选中行
      filterText: "", // 过滤文本
      addText: "添 加" // 文本值
    };
  },
  watch: {
    filterText(val) {
      this.$refs.empTree.filter(val);
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    /**
     * 页面加载
     */
    init() {
      let self = this;
      getList().then(res => {
        if (res.data.status != 200) {
          self.$message.warning(res.data.message);
          return false;
        }
        self.data = res.data.results;
      });
      // 机构树
      queryBranchThree().then(res => {
        if (res.data.status != 200) {
          self.$message.warning(res.data.message);
          return false;
        }
        self.orgData = res.data.results;
        console.log("orgData", self.orgData);
      });
    }, // init end
    /**
     * dialog 关闭
     */
    dialogClose() {
      this.visibleClose = false;
    },
    /**
     * dialog 确定事件
     */
    handleOk() {
      let self = this;
      if (self.type === 1) {
        if (self.selectedData.children || self.selectedData.id === undefined) {
          this.$message.warning("请选择分行");
          return false;
        }
      } else {
        if (self.selectedData.children || self.selectedData.id === undefined) {
          this.$message.warning("请选择分行员工");
          return false;
        }
      }
      self.data.forEach((item, key) => {
        console.log("item", item);
        if (item.current) {
          self.moData.push(item);
          if (item.ctype == "部门") {
            item.newDeptno = self.selectedData.id.replace("org", "");
            item.newDeptname = self.selectedData.text;
            item.deptname = item.newDeptname;
          } else if (item.ctype == "合规部总经理") {
            item.newHgbzjl = self.selectedData.pcode;
            item.newHgbzjlname = self.selectedData.text;
            item.hgbzjlname = item.newHgbzjlname;
          } else if (item.ctype == "人力资源部总经理") {
            item.newRlzybzjl = self.selectedData.pcode;
            item.newRlzybzjlname = self.selectedData.text;
            item.rlzybzjlname = item.newRlzybzjlname;
          } else {
            // 分行研发经理
            item.newFhyfjl = self.selectedData.pcode;
            item.newFhyfjlName = self.selectedData.text;
            item.fhyfjlName = item.newFhyfjlName;
          }
          self.form = item;
        }
        return false;
      });
      // 点击确定后，清空当前选中行数据
      self.selectedData = {};
      self.dialogClose();
      self.data.splice(0, 0);
      console.log("form", self.form);
    }, // handleOk end
    /**
     * table 高亮行事件
     */
    handleCurrentRow(currentRow, oldRow) {
      console.log("currentRow", currentRow, oldRow);
    },
    /**
     * 点击table行时，触发事件
     */
    handleRowClick(row, column, cell, event) {
      console.log("row:", row, column, cell, event);
      this.data.forEach((item, key) => {
        if (item.id == row.id) {
          item.current = true;
          item.ctype = column.label;
        } else {
          item.current = false;
        }
      });
      console.log("row:", this.data);
      this.data.splice(0, 0);
      this.rowData = row;
    }, // handleRowClick end
    /**
     * 点击机构树节点事件
     */
    getCurrentNode(node) {
      console.log("node", node);
      this.selectedData = node;
    }, //getCurrentNode end
    /**
     * 点击机构事件
     */
    handleOrg() {
      let self = this;
      self.type = 1;
      self.visibleClose = true;
    },
    /**
     * 点击员工弹框事件
     */
    handleUser() {
      let self = this;
      self.type = 2;
      self.filterText = "";
      let orgId = self.rowData.deptno || self.rowData.newDeptno;
      self.empData = [];
      getEmpTree(orgId).then(res => {
        console.log("emp:", res.data);
        if (res.data.status != 200) {
          self.$message.warning(res.data.message);
          return false;
        }
        self.empData = res.data.results;
      });
      self.visibleClose = true;
    }, // handleUser end
    /**
     * 过滤节点
     */
    filterNode: function(value, data) {
      if (!value) {
        return true;
      }
      return data.text.indexOf(value) !== -1;
    },
    /**
     * 更新数据
     */
    handleUpdate(row) {
      let self = this;
      let data = {};
      if (row.id) {
        data.id = row.id;
      }

      // 针对添加需要验证
      if (!row.deptno && !row.newDeptno && row.newDeptno !== undefined) {
        self.$message.warning("部门不能为空");
        return false;
      } else {
        data.deptno = row.newDeptno || row.deptno;
        data.deptname = row.newDeptname || row.deptname;
      }

      if (row.hgbzjl !== row.newHgbzjl && row.newHgbzjl !== undefined) {
        data.hgbzjl = row.newHgbzjl;
        data.hgbzjlname = row.newHgbzjlname;
      } else {
        data.hgbzjl = "";
        data.hgbzjlname = "";
      }

      if (row.rlzybzjl !== row.newRlzybzjl && row.newRlzybzjl !== undefined) {
        data.rlzybzjl = row.newRlzybzjl;
        data.rlzybzjlname = row.newRlzybzjlname;
      } else {
        data.rlzybzjl = "";
        data.rlzybzjlname = "";
      }

      if (row.fhyfjl !== row.newFhyfjl && row.newFhyfjl !== undefined) {
        data.fhyfjl = row.newFhyfjl;
        data.fhyfjlName = row.newFhyfjlName;
      } else {
        data.fhyfjl = "";
        data.fhyfjlName = "";
      }
      console.log("表单", data);
      data = common.paramsFormat(data);
      updateOrAdd(data).then(res => {
        if (res.data.status != 200) {
          self.$message.error(res.data.message);
          return false;
        }
        self.$message.success(res.data.message);
        self.refreshData();
      });
    },
    /**
     * 删除
     */
    handleDel(row) {
      console.log("删除", row, this.data);
      this.$confirm("确定删除吗?", { type: "warning" }).then(res => {
        if(!row.id) {
          let index = this.data.findIndex(i => i.current)
          this.data.splice(index,1)
          return false
        }
        deleteById(row.id).then(res => {
          if (res.data.status != 200) {
            this.$message.error(res.data.message);
            return false;
          }
          this.refreshData();
          this.$message.success(res.data.message);
        });
      });
    },
    /**
     * 刷新数据
     */
    refreshData() {
      getList().then(res => {
        if (res.data.status != 200) {
          self.$message.warning(res.data.message);
          return false;
        }
        self.data = res.data.results;
        self.data.splice(0, 0);
      });
    },
    /**
     * 添加行
     */
    handleAdd() {
      this.$message.warning("已在表格下方增加一行");
      this.data.push([]);
    },
    
  }
};
</script>
<style scoped>
.red-border {
  border: 1px solid red
}
</style>

