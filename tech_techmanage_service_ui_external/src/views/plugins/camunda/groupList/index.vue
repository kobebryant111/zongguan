<!--
流程分组列表
-->
<template>
  <el-tabs v-model="activeTabName" @tab-click="onTabsChange">
    <el-tab-pane name="db">
      <span slot="label">待办({{dbNumber}})</span>
      <flow-db :jump-db="jumpDb"></flow-db>
    </el-tab-pane>
    <el-tab-pane label="已办" name="yb">
      <flow-yb :jump-db="jumpDb" ref="flowYb"></flow-yb>
    </el-tab-pane>
    <el-tab-pane label="归档" name="gd">
      <flow-gd :jump-db="jumpDb" ref="flowGd"></flow-gd>
    </el-tab-pane>
    <el-tab-pane name="rw">
      <span slot="label">任务({{rwNumber}})</span>
      <flow-rw :jump-db="jumpDb" ref="flowRw"></flow-rw>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
  import flowDb from "@/views/plugins/camunda/groupList/db";
  import flowYb from "@/views/plugins/camunda/groupList/yb";
  import flowGd from "@/views/plugins/camunda/groupList/gd";
  import flowRw from "@/views/plugins/camunda/groupList/rw";
  export default {
    name: "FlowGroupList",
    components: {
      flowDb,
      flowYb,
      flowGd,
      flowRw
    },
    data() {
      return {
        activeTabName: "db", // 选中tab的name
        dbNumber: '',
        rwNumber: ''
      };
    },
    mounted() {
      this.$root.Bus.$off('dbNumber').$on('dbNumber', val => {
        this.dbNumber = val; 
      })
      this.$refs.flowRw.init();
      this.$root.Bus.$off("rwNumber").$on("rwNumber", val => {
        this.rwNumber = val;
      });
    },
    methods: {
      /**
       * 跳转到各个类型流程详情
       */
      jumpDb(row) {
        let dbDataMap; 
        if (row.status == "db") {
           dbDataMap = JSON.parse(sessionStorage.getItem("workflow_db_data"));
        } else if (row.status == "yb") {
          dbDataMap = JSON.parse(sessionStorage.getItem("workflow_yb_data"));
        } else if (row.status == "gd") {
          dbDataMap = JSON.parse(sessionStorage.getItem("workflow_gd_data"));
        } else if (row.status == "rw") {
          dbDataMap = JSON.parse(sessionStorage.getItem("flow_rw_data"));
        }
        console.log(row,'row')
        for (let [key, val] of dbDataMap) {
          console.log(key,val ,'asdasdasd')
          if (row.processInstanceId == key || row.flowid == key) {
            this.$router.push({
              name: val,
              query: row
            });
          }
        }
      },
      /**
       * tabs 点击触发
       */
      onTabsChange(tab) {
        // 已办
        if(tab.index === '1') {
          this.$refs.flowYb.init()
        } else if(tab.index === '2') { 
           // 归档
          this.$refs.flowGd.init()
        }
      }
    }
  };
</script>
<style scoped>
  .border-line {
    border-bottom: 1px solid #cccccc;
  }

  .subtitle span:first {
    float: left;
    padding-left: 20px;
  }

  .subtitle span:last-child {
    float: right;
    padding-right: 10px;
  }

  .collapse-pan {
    height: 500px;
    overflow: scroll;
  }
</style>

