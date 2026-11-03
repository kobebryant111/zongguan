<!--
roleData : 数据
defaultProp : 配置项 或者 子节点字段名称=> children： 显示名称字段=> lable:
nodeKey ： 节点编号
defaultExpandAll : 是否展开 默认：false
getCurrentNode ： 内部方法，可扩展
filterNode ： 过滤树内容
expandedKeys : 默认展开的节点key的数组
-->
<template>
  <div class="apptree-container">
    <!-- <el-container>
        <el-aside style="overflow-x:scroll;"> -->
    <el-form v-model="treeForm">
      <el-form-item label="" prop="name">
        <!-- <el-input v-model="filterText" size="mini"></el-input> -->
        <el-input placeholder="搜索机构名称" v-model="filterText" prefix-icon="el-icon-search"></el-input>
      </el-form-item>
    </el-form>
    <!--   树 -->
    <div class="tree-container" ref="qwcontent">
      <el-scrollbar style="height:100%;width:100%;">
        <el-tree ref="tree" :data="roleData" :props="defaultProp" :node-key="nodeKey" :default-expand-all="defaultExpandAll"
          @current-change="getCurrentNode" :filter-node-method="filterNode" :default-expanded-keys="expandedKeys"
          highlight-current />
      </el-scrollbar>
    </div>
    <!-- </el-aside> -->
    <!-- 主题内容 -->
    <!-- <el-main>
        <slot></slot>
      </el-main> -->
    <!-- </el-container> -->
  </div>
</template>
<script>
  import common from '@/api/common'
  export default {
    name: "AboutLayout",
    components: {},
    props: {
      defaultProp: {
        type: Object,
        required: true
      },
      nodeKey: {
        type: String,
        required: true
      },
      roleData: {
        type: [Array, Object],
        required: true
      },
      defaultExpandAll: {
        type: Boolean,
        default: () => false
      },
      expandedKeys: {
        type: Array
      },
      fns: {
        type: Function
      },
      /**
       * 搜索过滤
       */
      filterNodeMethod: {
        type: Function,
        required: true
      }
    },
    data() {
      return {
        filterText: "",
        treeForm: {
          name: ""
        },
      };
    },
    watch: {
      filterText(val) {
        console.log("val" + val);
        this.$refs.tree.filter(val);
      }
    },
    mounted() {
      window.onresize = function () {
        this.$refs.qwcontent.style.height = window.innerHeight - 265 + 'px';
        console.log("tree", this.$refs.qwcontent.style.height)
      }.bind(this)
    },
    methods: {
      getCurrentNode: function (node) {
        console.log(node);
        this.fns(node)
      },
      treeBtn: function () {
        console.log(this.treeForm);
        this.$refs.tree.filter(this.treeForm.name);
      },
      filterNode: function (value, data) {
        return this.filterNodeMethod(value, data);
        //data[this.filterField].indexOf(value) !== -1;
      }
    }
  };
</script>

<style lang="scss" scoped>
  .apptree-container {
    /* margin: 15px; */
    height: 100%;
    padding: 10px;
    background-color: #fff;
  }

  .tree-container {
    /* height: 615px; */
    /* max-height: calc(100vh - 127px); */
    /* overflow-y: scroll; */
    /* width: 290px; */
    width: 100%;
  }
</style>

<style>
  .apptree-container .el-aside {
    -ms-flex-shrink: 1;
    -webkit-flex-shrink: 1;
    -moz-flex-shrink: 1;
    flex-shrink: 1;
  }

  .apptree-container .el-scrollbar__wrap {
    overflow-x: auto;
  }

  .apptree-container .el-tree {
    height: calc(100vh - 142px);
  }

  .apptree-container .el-scrollbar {
    padding-bottom: 10px;
  }
</style>