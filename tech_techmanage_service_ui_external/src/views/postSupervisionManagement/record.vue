<!-- 记录页面 -->
<template>
  <div class="requireContainer">
    <div class="requireContainer_head">
      <el-row type="flex">
        <div class="requireList_head_right_orange_common">
          <button-common :buttonName="buttonExport" :size="sizes" :fns="exportData"></button-common>
        </div>
      </el-row>
    </div>
    <el-table
      style="margin-bottom: 80px;"
      :data="psireDatas"
      highlight-current-row
      height="510"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column
        fixed
        prop="psireName"
        label="流程名称"
        width="160"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column fixed prop="psireNo" label="流程编号" width="200" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <div @click="skipToOldKG(scope.row.flowUrl,scope.row.instanceId)">
            <span class="pointer_relationDemandNo">{{scope.row.psireNo}}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="relationUserName"
        label="关联人员"
        width="140"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column prop="deptName" label="关联部门" width="130" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="problemType" label="问题分类" width="100" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span v-if="scope.row.problemType == '0'">流程问题</span>
          <span v-else-if="scope.row.problemType == '1'">专项问题</span>
        </template>
      </el-table-column>
      <el-table-column prop="problemDetail" label="问题详情" width="160" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column
        prop="psireFeedbackNo"
        label="后督反馈流程编号"
        width="200"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="feedbackContent"
        label="反馈内容"
        width="120"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="twoManagerSuggestion"
        label="二级部经理意见"
        width="120"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column prop="isCompliance" label="是否合规" width="80" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="note" label="备注" width="120" :show-overflow-tooltip="true"></el-table-column>
    </el-table>
    <div class="nav-bottom">
      <div class="wrapper">
        <div class="wrapper_left"></div>
        <div class="wrapper_right">
          <div class="requireList_head_right_orange_common">
            <pager
              :fns="getPager"
              :total="totalNumber"
              :pageSize="size"
              v-model="$attrs"
              ref="pager"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import pager from "@/components/Pager";
import newData from "./newData";
import common from "@/api/common";
import {
  queryPager,
  exportPostGovernorDatas
} from "@/api/dataCenter/dataCenPostSupervision";
import { getDomainName } from "@/api/eosDictEntry";
import { mapGetters } from "vuex";

export default {
  name: "record",
  components: {
    buttonCommon,
    buttonOrange,
    newData,
    pager
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认:50
      index: 1, // 初始值 默认:1

      sizes: "small", //按钮大小
      buttonExport: "导 出", //导出按钮
      flag: true, //新增和修改判断
      showNewData: false, //显示新增弹出框
      selectData: [], //选中的数据
      rowData: {}, //当前行数据
      psireDatas: [] //后督数据
    };
  },
  props: {
    paramsForm: {
      type: Object,
      required: true,
      default: () => ({})
    }
  },
  watch: {
    paramsForm: {
      handler(val) {
        this.exportParams = JSON.parse(JSON.stringify(val));
      },
      deep: true
    }
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  created() {},
  mounted() {
    this.loadInit();
  },
  methods: {
    loadInit() {
      this.exportParams = JSON.parse(JSON.stringify(this.paramsForm));
      let params = {};
      params = this.exportParams;
      params.size = this.size;
      params.index = this.index;
      this.getPsireDatabyParams(params);
    },

    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params = this.exportParams;
      params.size = size;
      params.index = index;
      // this.init(params);
      this.getPsireDatabyParams(params);
    }, // end

    //获取数据
    getPsireDatabyParams(paramsForm) {
      let params = JSON.parse(JSON.stringify(paramsForm));
      let data = common.paramsFormat(params);
      queryPager(data).then(res => {
        this.psireDatas = res.data.results;
        this.totalNumber = res.data.total;
        for (var i = 0; i < this.psireDatas.length; i++) {
          if (this.psireDatas[i].dept != undefined) {
            this.psireDatas[i].dept = "dep" + this.psireDatas[i].dept;
          }
        }
      });
    },

    //获取数据
    getPsireData() {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      let data = common.paramsFormat(params);
      queryPager(data).then(res => {
        this.psireDatas = res.data.results;
        this.totalNumber = res.data.total;
        for (var i = 0; i < this.psireDatas.length; i++) {
          if (this.psireDatas[i].dept != undefined) {
            this.psireDatas[i].dept = "dep" + this.psireDatas[i].dept;
          }
        }
      });
    },

    handleSelectionChange(val) {},

    //导出
    exportData() {
      let data = common.paramsFormat(this.exportParams);
      exportPostGovernorDatas(data).then(res => {
        const self = this;
        common.download(res.data, self, "后督记录导出.xls");
      });
    },

    //跳转老应用
    skipToOldKG(flowUrl, instanceId) {
      let link = document.createElement("a");
      let url_params =
        "/com.example.ppm.system.projectM.newApplicationPostGovernor.flow?redirectUrl=" +
        flowUrl +
        "&processInstID=" +
        instanceId +
        "&md5=" +
        this.$md5(this.userId) +
        "&userid=" +
        this.userId;
      let params = { dicttypeid: "PPM_URL", dictid: "1" };
      getDomainName(params).then(res => {
        let webUrl = res.data.results;
        link.href = webUrl + url_params;
        link.target = "_blank";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      });
    }
  }
};
</script>
<style scoped>
.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
}

.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  margin-top: 20px;
  padding: 15px 0;
  z-index: 999;
}

.nav-bottom .wrapper {
  width: 80%;
  margin-left: 25px;
  margin-right: 25px;
  display: flex;
  justify-content: space-between;
}

.pointer_relationDemandNo {
  width: 400px;
  cursor: pointer;
  -webkit-text-fill-color: #0000ff;
}

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}

.requireList_head_right_orange_common {
  margin: 0 5px 0 5px;
}

.requireContainer_head {
  float: right;
  margin-bottom: 10px;
}

.requireContainer {
  background-color: #ffffff;
  padding: 0 18px 0 18px;
}

/* 表头背景色 */
.requireContainer .el-table .th {
  background: #fafafa;
}
</style>
<style>
.el-tooltip__popper {
  max-width: 600px;
}
</style>



