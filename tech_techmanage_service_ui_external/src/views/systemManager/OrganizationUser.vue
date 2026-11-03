<!-- 机构人员 -->
<template>
  <!-- <about-layout
    :roleData="roleData"
    :defaultProp="defaultProp"
    :nodeKey="nodeKey"
    :expandedKeys="[1]"
    v-model="$attrs"
    :fns="getCurrentNode"
    :filterNodeMethod="filterNodeMethod"
  >
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick" ref="tabs">
      <el-tab-pane label="下级部门" name="dep">
        <subordingate-department ref="sdept" :checkedNode="checkedNode"></subordingate-department>
      </el-tab-pane>
      
      <el-tab-pane label="人员查询" name="user">
        <employee :roleData="roleData"></employee>
      </el-tab-pane>

      <el-tab-pane label="兼职人员查询" name="partTimeUser">
        <parttimejob :roleData="roleData"></parttimejob>
      </el-tab-pane>
    </el-tabs>
  </about-layout> -->

  <div>
    <el-row>
      <el-col :span=5>
        <div>
          <about-layout :roleData="roleData" :defaultProp="defaultProp" :nodeKey="nodeKey" :expandedKeys="[1]" v-model="$attrs"
            :fns="getCurrentNode" :filterNodeMethod="filterNodeMethod">
          </about-layout>
        </div>
      </el-col>
      <el-col :span=19>
        <div>
          <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick" ref="tabs">
            <el-tab-pane label="下级部门" name="dep">
              <subordingate-department ref="sdept" :checkedNode="checkedNode"></subordingate-department>
            </el-tab-pane>

            <el-tab-pane label="人员查询" name="user">
              <employee :roleData="roleData"></employee>
            </el-tab-pane>
            <el-tab-pane label="兼职人员查询" name="partTimeUser">
              <parttimejob :roleData="roleData"></parttimejob>
            </el-tab-pane>

            <el-tab-pane label="密码本人员管理" name="CMDBuser">
              <CMDBEmployee/>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import request from "@/utils/request";
  import aboutLayout from "@/components/AboutLayout";
  import subordingateDepartment from "./common/SubordingateDepartment";
  import parttimejob from "./common/Parttimejob";
  import employee from "./common/Employee";
import CMDBEmployee from '@/views/systemManager/common/CMDBEmployee.vue'

  export default {
    components: {
      aboutLayout,
      employee,
    CMDBEmployee,
      subordingateDepartment,
      parttimejob
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
        activeName: "user", // tab 默认选中
        checkedNode: {} //机构树 节点对象
      };
    },
    mounted() {
      this.init();
    },
    methods: {
      // 页面初始化
      init: function () {
        request({
          url: "/commOrganization/queryOrgTree",
          type: "get"
        }).then(res => {
          this.roleData = res.data.results;
        });
        this.$nextTick(_ => {
          let tab = this.$refs.tabs.$children[0].$refs;
          tab.tabs[0].style.display = "none";
          tab.tabs[2].style.display = "none";
        });
      }, // 页面初始化 end
      // tab选中触发
      handleClick: function (tab, event) {
      }, // tab选中触发 end
      // 机构树选中触发
      getCurrentNode: function (item) {
        if (item.compid === 1) {
          this.activeName = "user";
          this.$refs.tabs.$children[0].$refs.tabs[0].style.display = "none";
          this.$refs.tabs.$children[0].$refs.tabs[2].style.display = "none";
        } else {
          this.activeName = "dep";
          this.checkedNode = item;
          this.$refs.tabs.$children[0].$refs.tabs[0].style.display = "";
          this.$refs.tabs.$children[0].$refs.tabs[2].style.display = "";
          this.$refs.sdept.init({
            size: this.$refs.sdept.size,
            index: this.$refs.sdept.index,
            compid: item.compid
          });
        }
      },
      filterNodeMethod: function (value, data) {
        return data.location.indexOf(value) !== -1;;
      }
    }
  };
</script>