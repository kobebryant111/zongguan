<template>
  <div class="single-choice">
    <el-input v-model="inputContent" :disabled="disabled">
      <el-button slot="append" icon="el-icon-more" @click="showDialog" :disabled="ifClick"></el-button>
    </el-input>
    <el-dialog :title="titleName" :visible.sync="flDialog" class="flDialogCon">
      <div class="flDialogBody">
        <div class="flDialogBody_title">{{subheadingName}}</div>
        <div class="flDialogBody_con">
          <el-row>
            <el-col :span="12">
              <div class="flDialogBody_con_left">
                <div class="flDialogBody_con_left_input">
                  <el-input
                    placeholder="搜索名称"
                    v-model="filterText"
                    prefix-icon="el-icon-search"
                    class="searchText"
                  ></el-input>
                </div>
                <el-scrollbar style="height:100%">
                  <div class="flDialogBody_con_tree">
                    <el-tree
                      ref="tree"
                      :data="treeData"
                      :props="treeProps"
                      :node-key="nodeKey"
                      :default-expand-all="defaultExpandAll"
                      @current-change="getCurrentNode"
                      :filter-node-method="filterNode"
                      :default-expanded-keys="expandedKeys"
                      highlight-current
                    />
                  </div>
                </el-scrollbar>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="flDialogBody_con_right">
                <div class="flDialogBody_con_right_title">{{flRightTitleName}}</div>
                <el-scrollbar style="height:100%">
                  <div class="flDialogBody_con_right_main" v-if="ifwbtcRight">
                    <ul class="flDialogBody_ul">
                      <div class="flList">
                        <div>
                          <li class="flDialogBody_ul_up">{{flrcUpName}}{{treeNodeData[flrcUpField]}}</li>
                          <li
                            class="flDialogBody_ul_low"
                          >{{flrcLowerName}}{{treeNodeData[flrcLowerField]}}</li>
                        </div>
                      </div>
                    </ul>
                  </div>
                </el-scrollbar>
              </div>
            </el-col>
          </el-row>
        </div>
        <div class="wrapper_bottom">
          <el-row :gutter="10" type="flex" justify="end">
            <el-col :span="1.5">
              <button-common :buttonName="buttonCancel" :size="size" :fns="flCancel"></button-common>
            </el-col>
            <el-col :span="1.5">
              <button-orange :buttonName="buttonSure" :size="size" :fns="flSure"></button-orange>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
export default {
  name: "singleChoice",
  components: { buttonOrange, buttonCommon },
  data() {
    return {
      inputContent: this.input,
      flDialog: false, //是否选择变更人员弹窗
      size: "mini", //按钮大小
      buttonCancel: "取 消",
      buttonSure: "确 定",
      groupid_options: [],
      filterText: "", //过滤机构树
      treeNodeData: {}, //点击的树节点数据
      ifwbtcRight: false, //是否显示弹窗右边
      contentField: this.flrcLowerField, //内容字段
      ifClick: this.disablBtn  // 是否可点击 默认：不可点击
    };
  },
  props: {
    input: {
      //输入框内容
      type: String
    },
    disabled: {
      //是否可点击
      type: Boolean,
      default: true
    },
    titleName: {
      //大标题名字
      type: String
    },
    subheadingName: {
      //次标题名字
      type: String
    },
    treeProps: {
      //配置信息
      type: Object,
      required: true
    },
    flRightTitleName: {
      //分栏右边标题名字
      type: String
    },
    flrcUpName: {
      //分栏右边内容上边名字
      type: String
    },
    flrcLowerName: {
      //分栏右边内容下边名字
      type: String
    },
    flrcUpField: {
      //分栏右边内容上边字段
      type: String
    },
    flrcLowerField: {
      //分栏右边内容下边字段
      type: String
    },
    nodeKey: {
      //节点唯一标识
      type: String,
      required: true
    },
    treeData: {
      //tree数据
      type: [Array, Object],
      required: true
    },
    defaultExpandAll: {
      //是否展开全部节点
      type: Boolean,
      default: () => false
    },
    expandedKeys: {
      //默认展开节点的数组
      type: Array
    },
    fns: {
      type: Function
    },
    filterNodeMethod: {
      //搜索过滤
      type: Function,
      required: false
    },
    rowId: {
      type: [String, Number],
      required: false
    },
    /**
     * 按钮可否点击
     */
    disablBtn : {
      type : Boolean,
      default : false
    },
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  computed: {},
  methods: {
    // 弹窗显示
    showDialog() {
      this.flDialog = true;
    },

    //弹窗确认
    flSure() {
      this.inputContent = this.treeNodeData[this.flrcLowerField];
      // if (this.treeNodeData.text != undefined) {
      //     this.inputContent = this.treeNodeData.text;
      // } else if (this.treeNodeData.projectname != undefined) {
      //     this.inputContent = this.treeNodeData.projectname;
      // }
      this.fns(this.treeNodeData, this.rowId);
      this.flDialog = false;
    },

    //弹窗取消
    flCancel() {
      this.flDialog = false;
    },

    //选中节点变化时触发的事件
    getCurrentNode: function(node) {
      console.log("节点信息", node);
      if (node.children != undefined) {
        return false;
      } else {
        this.ifwbtcRight = true;
        this.treeNodeData = node;
      }
    },

    //节点筛选是执行的方法
    filterNode: function(value, data) {
      return this.filterNodeMethod(value, data);
    }
  }
};
</script>

<style scoped>
.flDialogBody_con {
  border: 1px solid #d9d9d9;
  margin: 15px 0;
}

.flDialogBody_con_left {
  border-right: 1px solid #d9d9d9;
  padding: 10px 0 10px 10px;
}

.flDialogBody_con_left_input {
  padding-right: 10px;
}

.flDialogBody_con_right_title {
  font-size: 16px;
  font-weight: 550;
  color: #303133;
}

.flDialogBody_con_right_title span {
  margin: 0 5px;
}

.flDialogBody_con_tree {
  min-height: 300px;
  max-height: 300px;
  padding-top: 10px;
}

.flDialogBody_con_right_main {
  min-height: 310px;
  max-height: 310px;
  /* padding-top: 5px; */
}

.flDialogBody_con_right {
  padding: 18px 0 0 15px;
}

.flDialogBody_ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

/* .flDialogBody_ul div {
        margin-top: 8px;
    } */

.flDialogBody_ul_up {
  color: #303133;
  font-size: 14px;
  font-weight: 400;
}

.flDialogBody_ul_low {
  color: #c0c4cc;
  font-size: 14px;
  font-weight: 400;
  /* margin-top: 10px; */
}

.flList {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-right: 10px;
}

.el-icon-circle-close {
  cursor: pointer;
}
</style>

<style>
.flDialogCon .el-dialog__body {
  padding: 0 20px 10px 20px;
}

.single-choice .el-input.is-disabled .el-input__inner {
  background-color: transparent;
}
</style>