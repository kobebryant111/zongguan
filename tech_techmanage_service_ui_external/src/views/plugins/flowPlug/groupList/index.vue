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
  </el-tabs>
</template>
<script>
  import flowDb from "@/views/plugins/flowPlug/groupList/db";
  import flowYb from "@/views/plugins/flowPlug/groupList/yb";
  import flowGd from "@/views/plugins/flowPlug/groupList/gd";
  export default {
    name: "FlowGroupList",
    components: {
      flowDb,
      flowYb,
      flowGd
    },
    data() {
      return {
        activeTabName: "db", // 选中tab的name
        dbNumber: ''
      };
    },
    mounted() {
      this.$root.Bus.$off('dbNumber').$on('dbNumber', val => {
        this.dbNumber = val;
        console.log('待办数量',this.dbNumber);
      })
     
    },
    methods: {
      /**
       * 跳转到各个类型流程详情
       */
      jumpDb(row) {
        console.log("jumpDb", row);
        // 读取本地localStroe flow_db_data 
        // const dbDataMap = JSON.parse(localStorage.getItem("flow_db_data"));
        // 读取本地localStroe flow_db_data
        let dbDataMap;
        // const dbDataMap = JSON.parse(sessionStorage.getItem("flow_db_data"));
        if (row.status == "db") {
           dbDataMap = JSON.parse(sessionStorage.getItem("flow_db_data"));
        } else if (row.status == "yb") {
          dbDataMap = JSON.parse(sessionStorage.getItem("flow_yb_data"));
        } else if (row.status == "gd") {
          dbDataMap = JSON.parse(sessionStorage.getItem("flow_gd_data"));
        }
        // console.log("dbData", dbDataMap);


        /**
         * 面试流程 
         * 规则 以流程名称最后一段为 val值 
         */
        for (let [key, val] of dbDataMap) {
          // console.log('dddd', key + '', row.processDefName + '', row.processDefName.trim() == key.trim())
          if (row.processDefName == key || row.flowid == key) {
            // console.log('dddd11111', key, row.processDefName)
            this.$router.push({
              name: val,
              params: row
            });
          }
        }
      },
      /**
       * tabs 点击触发
       */
      onTabsChange(tab) {
        console.log("tab", tab);
        // 已办
        if(tab.index === '1') {
          this.$refs.flowYb.init()
        } else if(tab.index === '2') {  // 归档
          this.$refs.flowGd.init()
        }
      }
    }
  }
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

