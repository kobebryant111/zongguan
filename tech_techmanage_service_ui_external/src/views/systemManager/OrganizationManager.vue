<template>
  <about-layout
    :roleData="roleData"
    :defaultProp="defaultProp"
    :nodeKey="nodeKey"
    :expandedKeys="[1]"
    v-model="$attrs"
    :fns="getCurrentNode"
    :filterNodeMethod="getFilterNode"
  >
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick" ref="tabs">
      <el-tab-pane label="下级部门" name="dep">
        <subordingate-department ref="sdept" :checkedNode="checkedNode"></subordingate-department>
      </el-tab-pane>
      <el-tab-pane label="下级岗位" name="job">
        <Subordinate-job ref="sjob" :checkedNode="checkedNode"></Subordinate-job>
      </el-tab-pane>
      <el-tab-pane label="机构查询" name="org">
        <mechanism></mechanism>
      </el-tab-pane>
      <el-tab-pane label="人员查询" name="user">
        <employee :roleData="roleData"></employee>
      </el-tab-pane>
      <el-tab-pane label="兼职人员查询" name="partTimeUser">
        <parttimejob :roleData="roleData"></parttimejob>
      </el-tab-pane>
    </el-tabs>
  </about-layout>
</template>
<script>
import request from "@/utils/request";
import aboutLayout from "@/components/AboutLayout";
import mechanism from "./common/Mechanism";
import employee from "./common/Employee";
import parttimejob from "./common/Parttimejob";
import subordingateDepartment from "./common/SubordingateDepartment";
import SubordinateJob from './common/SubordinateJob';

export default {
  components: {
    aboutLayout,
    mechanism,
    employee,
    parttimejob,
    subordingateDepartment,
    SubordinateJob
  },
  data() {
    return {
      // aboutLayout 插件
      roleData: [],
      defaultProp: {
        children: "children",
        label: "location"
      },
      nodeKey: "compid",
      // aboutLayout 插件 end
      activeName: "org", // tab 默认选中
      checkedNode: {} //机构树 节点对象
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    // 页面初始化
    init: function() {
      request({
        url: "/commOrganization/queryOrgTree",
        type: "get"
      }).then(res => {
        this.roleData = res.data.results;
      });
      this.$nextTick(_ => {
        let tab = this.$refs.tabs.$children[0].$refs;
        tab.tabs[0].style.display = "none";
        tab.tabs[1].style.display = "none";
      });
    }, // 页面初始化 end
    // tab选中触发
    handleClick: function(tab, event) {
    }, // tab选中触发 end
    // 机构树选中触发
    getCurrentNode: function(item) {
      if (item.compid === 1) {
          this.activeName = "org";
        this.$refs.tabs.$children[0].$refs.tabs[0].style.display = "none";
        this.$refs.tabs.$children[0].$refs.tabs[1].style.display = "none";
      } else {
        this.activeName = "dep";
        this.checkedNode = item;
        this.$refs.tabs.$children[0].$refs.tabs[0].style.display = "";
        this.$refs.tabs.$children[0].$refs.tabs[1].style.display = "";
        this.$refs.sdept.init({
          size: this.$refs.sdept.size,
          index: this.$refs.sdept.index,
          compid: item.compid
        });
        this.$refs.sjob.init({
          size: this.$refs.sjob.size,
          index: this.$refs.sjob.index,
          compid: item.compid
        });
      }
    },
    getFilterNode :function(val, data) {
      return data.location.indexOf(val) !== -1;
    }
  }
};
</script>

