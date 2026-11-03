<template>
  <el-dialog title="部门列表" width="50%" :before-close="handleClose" :visible.sync="dialogClose">
    <div class="form-container">
      <el-form inline ref="deptForm" :model="deptForm">
        <el-form-item label="部门编号" prop="depid">
          <el-input v-model="deptForm.depid" placeholder="部门编号"></el-input>
        </el-form-item>
        <el-form-item v-model="deptForm" label="部门名称" prop="location">
          <el-input v-model="deptForm.location" placeholder="部门名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="submitSearchBtn">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--   树 -->
    <div class="dept-table-container">
      <el-table
        :data="deptData"
        fit
        border
        height="400"
        @current-change="handleTable"
        :row-style="rowClass"
        :row-class-name="tableRowClassName"
      >
        <el-table-column label="部门编号" prop="depid" align="center" ></el-table-column>
        <el-table-column label="部门名称" prop="location" align="center" ></el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>
    <div class="button-container">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="submitBtn">确定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import request from "@/utils/request";
import common from "@/api/common";
import pager from "@/components/Pager";
export default {
  name: "Department",
  components: { pager },
  props: {
    fns: {
      type: Function
    }
  },
  data() {
    return {
      // 分页信息
      totalNumber: 0,
      size: 50,
      index: 1,

      deptForm: {
        depid: "",
        location: ""
      },
      filterText: "",
      deptData: [],
      defaultProp: {
        children: "children",
        label: "location"
      },
      nodeKey: "depid",
      defaultExpandAll: false,
      expandedKeys: [],
      dialogClose: false,
      checkedNode: {},
      selectIndex: 0
    };
  },
  watch: {
    filterText: function(vla) {
      this.$refs.tree.filter(vla);
    }
  },
  methods: {
    // 初始化
    init: function(param) {
      let data = common.paramsFormat(param);

      request({
        url: "/commDepartment/queryPager",
        method: "post",
        data: data
      }).then(res => {
        console.log(res.data);
        this.deptData = res.data.results;
        this.totalNumber = res.data.total;
      });
    },
    // 节点过滤
    filterNode: function() {
      if (!value) {
        return true;
      }
      return data.location.indexOf(value) !== -1;
    },
    // 关闭dailog
    handleClose: function() {
      this.dialogClose = false;
    },
    // 确定事件
    submitBtn: function() {
      this.handleClose();
      this.fns(this.checkedNode);
    },
    // 分页触发
    getPager: function(pageSize, currentPage) {
      let params = {};
      params.size = pageSize;
      params.index = currentPage;
      params.compid = this.deptForm.compid;
      params.location = this.deptForm.location;
      this.init(params);
    },
    // 搜索
    submitSearchBtn: function() {
        let params = {};
      params.size = this.size;
      params.index = this.index;
      params.depid = this.deptForm.depid;
      params.location = this.deptForm.location;
        this.init(params);
    },
    // table 选中触发
    handleTable: function(row, column, event) {
      this.selectIndex = row.depid;
      this.checkedNode = row;
    },
    // 当前选中行的样式
    rowClass: function({ row, index }) {
      if (this.selectIndex === row.depid) {
        return { "background-color": "rgba(185,221,249,0.75)" };
      }
    },
    // 把每一行的索引放进row
    tableRowClassName: function({ row, rowIndex }) {
      row.index = rowIndex;
    }
  }
};
</script>
<style lang="scss" scoped>
.form-container {
  margin-bottom: 10px;
}
.el-dialog__body {
  padding: 10px 20px;
}
.selected-row {
  background: #f2f2f2;
}
</style>
