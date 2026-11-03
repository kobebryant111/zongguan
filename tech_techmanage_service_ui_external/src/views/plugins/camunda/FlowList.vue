<!-- 外包员工准入-->
<template>
  <div class="app-container">
    <div class="buttons-container">
      <el-button type="warning" @click="add()">流程发起</el-button>
      <el-button type="warning" @click="db()">我的待办</el-button>
      <el-button type="warning" @click="yb()">我的已办</el-button>
      <el-button type="warning" @click="gd()">我的归档</el-button>
    </div>
    <!-- 我的待办 -->
    <el-table :data="flowData" border stripe v-show="showTable == 'db'">
      <el-table-column prop="processInstName" label="流程名称" align="center"></el-table-column>
      <el-table-column prop="activityInstName" label="流程节点" align="center"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <a @click="toDb(scope.row)">执行</a>
        </template>
      </el-table-column>
    </el-table>
    <!-- 我的已办 -->
    <el-table :data="flowData" border stripe v-show="showTable == 'yb'">
      <el-table-column prop="processInstName" label="流程名称" align="center"></el-table-column>
      <el-table-column prop="activityInstName" label="流程节点" align="center"></el-table-column>
      <el-table-column label="节点处理人" align="center">
        <template slot-scope="scope">{{scope.row.partiName}}({{scope.row.participant}})</template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <a @click="showDetail(scope.row)">查看</a>
        </template>
      </el-table-column>
    </el-table>
    <!-- 我的归档 -->
    <div v-show="showTable=='gd'">
      <el-table :data="flowData" border stripe >
        <el-table-column prop="flowtitle" label="流程名称" align="center"></el-table-column>
        <el-table-column prop="flowstatus" label="状态" align="center"></el-table-column>
        <el-table-column
          prop="createtime"
          label="创建时间"
          align="center"
          :formatter="createtimeFormat"
        ></el-table-column>
        <el-table-column
          prop="lastmodtime"
          label="结束时间"
          align="center"
          :formatter="lastmodtimeFormat"
        ></el-table-column>
        <el-table-column prop="creators" label="创建人" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <a @click="showDetail(scope.row)">查看</a>
          </template>
        </el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>
  </div>
</template>
<script>
import { getFlowData, getFlowGDData } from "@/api/common/workflowApi";
import common from "@/api/common";
import pager from "@/components/Pager";
export default {
  name: "wbryzr",
  props: {
    /**
     * 流程定义名称
     */
    processDefName: {
      type: String,
      required: true
    },
    /**
     * 添加流程方法
     */
    addFun: {
      type: Function,
      required: true
    },
    /**
     * 跳转到待办页面
     */
    jumpDb: {
      type: Function,
      required: true,
      default: row => {}
    },
    dbFun: {
      type: Function,
      default: _ => {}
    },
    /**
     * 查看方法
     */
    getDateil: {
      type: Function,
      default: row => {}
    }
  },
  components: { pager },
  data() {
    return {
      size: 50, // 分页 - 显示条数
      index: 1, //  分页 - 默认第一页
      totalNumber: 0, // 分页总数

      flowData: [], // 初始数据
      showTable: "" // 显示待办 已办 归档 其一
    };
  },
  mounted() {
    this.$nextTick(_ => {
      this.init("/commonFlowApi/getdb");
    });
    this.showTable = "db";
  },
  methods: {
    /**
     * 初始数据
     */
    init: function(url) {
      getFlowData(url, { processDefName: this.processDefName })
        .then(res => {
          console.log("data", res.data.results);
          this.flowData = res.data.results;
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
    }, // end
    /**
     * 执行待办
     */
    toDb: function(row) {
      console.log("row", row);
      this.jumpDb(row);
    }, // 执行待办 end
    /**
     * 查看
     */
    showDetail: function(row) {
      this.getDateil(row);
    },
    /**
     * 流程发起
     */
    add: function() {
      this.addFun();
    }, // 流程发起 end
    /**
     * 我的待办,我的已办,我的归档
     */
    db: function() {
      this.init("/commonFlowApi/getdb");
      this.dbFun();
      this.showTable = "db";
    }, // end
    /**
     * 我的已办
     */
    yb: function() {
      this.init("/commonFlowApi/getyb");
      this.showTable = "yb";
    }, // end
    /**
     * 我的归档
     */
    gd: function() {
      let data = {
        projectno: "",
        flowid: this.processDefName,
        size: this.size,
        index: this.index
      };
      let params = common.paramsFormat(data);
      getFlowGDData("/commonFlowApi/getgd", params)
        .then(res => {
          this.isgdShow = true;
          this.flowData = res.data.results;
          this.totalNumber = res.data.total;
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
      this.showTable = "gd";
    }, // end
    /**
     * 分页触发
     */
    getPager: function(pageSize, currentPage) {
      this.size = pageSize;
      this.index = currentPage;
      this.gd();
    }, //  分页触发 end
    /**
     * 结束时间格式化
     */
    lastmodtimeFormat: function(row, cloumn) {
      let time = common.dateFormat(row.lastmodtime, "yyyy-MM-dd HH:mm:ss");
      return time;
    },
    /**
     * 开始时间格式化
     */
    createtimeFormat: function(row, cloumn) {
      let time = common.dateFormat(row.createtime, "yyyy-MM-dd HH:mm:ss");
      return time;
    }
  }
};
</script>