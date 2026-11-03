<!-- 外包选择 -->
<template>
  <div class="outsourcing-container">
    <div class="param-form-container">
      <el-form :model="paramForm" inline>
        <el-form-item prop="badge" label="工号：">
            <el-input v-model="paramForm.badge" placeholder="工号"/>
        </el-form-item>
        <el-form-item prop="username" label="名称">
          <el-input v-model="paramForm.username" placeholder="名称"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="this.paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table :data="outsourcingData" fit border height="400" @selection-change="selectedRows" highlight-current-row stripe >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="工号" prop="badge" width="80" />
        <el-table-column label="姓名" prop="username" width="90" />
        <el-table-column label="电话" prop="mobiletelphone" width="120" show-overflow-tooltip />
        <el-table-column label="供应商" prop="outsourcname" width="120" show-overflow-tooltip />
        <el-table-column label="状态" width="60" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span v-for="item in ryzt_options" :key="item.dictid" v-if="item.dictid == scope.row.state">{{item.dictname}}</span>
          </template>
        </el-table-column>
        <el-table-column label="类型" prop="outsourceTypeName" width="90" />
        <el-table-column label="所属部门" prop="chargeName" width="160" show-overflow-tooltip />
        <el-table-column label="项目经理" prop="empName" width="140" show-overflow-tooltip />
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import request from "@/utils/request";
import common from "@/api/common";
import cleanBtn from '@/components/CleanBtn'
import { getDicEntry } from "@/api/eosDictEntry";
export default {
  components: { pager, cleanBtn },
  props: {     
    userData: {
      type: [Object],
      required: true
    },
    //选中行
    selectedRows : {
      type : Function
    }
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      ryzt_options: [], // 人员状态
      zcState: [], //在场情况
      outsourcingData: [], 
      state: this.userData.state,
      depid: this.userData.depid,
      paramForm: {
        depid: "",
        badge: "",
        username: ""
      }
    };
  },
  mounted() {
    let params = {};
    params.size = this.size;
    params.index = this.index; 
    params.depid = this.depid;
    params.state = this.state;
    this.init(params);
    this.getDic();
  },
  methods: {
    // 初始化
    init: function(param) {
      let data = common.paramsFormat(param);
      request({
        url: "/ppmOutsourcingBasic/queryCrewPage",
        method: "post",
        data: data
      }).then(res => {
        var reg = /([^-]+)$/;
        this.outsourcingData = res.data.results.filter(li => {
            li.chargeName = li.chargeName.match(reg)[1];
            return li;
        });
        this.totalNumber = res.data.total;
      });
    },
    // 查询业务字典
    getDic() {
        getDicEntry({
            dicttypeid: "NEWKG_RYZT,XKG_ZCSTATE"
        }).then(res => {
            if (res.data.status !== 200) {
                this.$alert(res.data.message, { type: "error" });
                return false;
            }
            let data = res.data.results;
            //状态
            this.ryzt_options = data.filter(item => {
                return item.dicttypeid == "NEWKG_RYZT";
            });
            // 在场情况
            this.zcState = data.filter(item => {
                return item.dicttypeid == "XKG_ZCSTATE";
            });
            return data;
        });
    },
    // 分页触发
    getPager: function(pageSize, currentPage) {
      let params = {};
      params.size = pageSize;
      params.index = currentPage;
      params.depid = this.depid;
      params.state = this.state;
      params.badge = this.paramForm.badge;
      params.username = this.paramForm.username;
      this.init(params);
    },
    // 搜索
    submitBtn: function() {
      let params = {}; 
      params.depid = this.depid;
      params.state = this.state;
      params.badge = this.paramForm.badge;
      params.username = this.paramForm.username;
      this.init(params);
    }
  }
};
</script>