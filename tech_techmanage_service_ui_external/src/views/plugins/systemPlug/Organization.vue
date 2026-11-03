<template>
  <el-dialog
    title="机构列表"
    width="20%"
    :before-close="handleClose"
    :visible.sync="falg"
    :modal="modal"
  >
    <el-form v-model="treeForm" inline>
      <el-form-item label="名称" prop="name">
        <el-input v-model="filterText" size="mini"></el-input>
      </el-form-item>
    </el-form>
    <!--   树 -->
    <div class="tree-container">
      <el-tree
        ref="tree"
        :data="roleData"
        :props="defaultProp"
        :node-key="nodeKey"
        :default-expand-all="defaultExpandAll"
        @current-change="getCurrentNode"
        :filter-node-method="filterNode"
        :default-expanded-keys="expandedKeys"
      />
    </div>
    <div class="button-container">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="submitBtn">确定</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: "Organization",
  props: {
    roleData: {
      type: [Array, Object],
      required: true
    },
    fns: {
      type: Function
    },
    defaultProps: {
      type: [Object, Array],
      default: _ => {
        return {
          children: "children",
          label: "location"
        };
      }
    },
    nodeKeys: {
      type: String,
      default: "compid"
    },
    /**
    是否显示遮罩层
     */
    modal: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      filterText: "",
      treeForm: {
        name: ""
      },
      defaultProp: this.defaultProps,
      nodeKey: this.nodeKeys,
      defaultExpandAll: false,
      expandedKeys: [1],
      falg: false,
      checkedNode: {} // 选中的节点对象
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 选中节点
    getCurrentNode: function(node) {
      if (!node.children) {
        this.checkedNode = node;
      }
    },
    // 过滤节点
    filterNode: function(value, data) {
      if (!value) {
        return true;
      }
      return data.location.indexOf(value) !== -1;
    },
    // 关闭dialog
    handleClose: function() {
      this.falg = false;
    },
    // 确定触发
    submitBtn: function() {
      this.fns(this.checkedNode);
      this.falg = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.tree-container {
  height: 300px;
  overflow: auto;
}
.el-dialog__body {
  padding: 10px 20px;
}
</style>