<!-- 业务总经理意向审批 -->
<template>
  <div class="global-container">
    <detail ref="detail">
      <template slot="kjzjlSlot">
        <div class="app-container">
          <el-form
            :model="fhForm"
            ref="fhForm"
            label-width="200px"
            label-position="left"
            v-if="ifShow"
            :rules="{ywfghz:[{required: true,message: '请选择业务分管行长',trigger: 'change'}]}"
          >
            <el-row>
              <el-col :span="24">
                <el-form-item label="业务分管行长" prop="ywfghz">
                  <el-cascader
                    ref="cascader"
                    :options="requirePeo_options"
                    :props="msgPeoParams"
                    v-model="fhForm.ywfghz"
                    :show-all-levels="false"
                    filterable
                    @change="peoChange"
                    collapse-tags
                    :disabled="!isDb"
                  ></el-cascader>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </template>
    </detail>
    <div class="app-container"></div>
    <div class="nav-bottom">
      <approve-bottom :proving="proving" :flowItem="bps" v-if="isDb"></approve-bottom>
      <yb-revoke :flowItem="bps" v-else></yb-revoke>
    </div>
  </div>
</template>
<script>
import {
  queryEmployeeTree,
  queryDeptIdForCompId
} from "@/api/integrated/wbryzrlc";
import common from "@/api/common";
import { busGeneral } from "@/api/branchIntentionApi/fhyx";
import { getDateil, preliminaryReview } from "@/api/branchIntentionApi/fhyx";
import detail from "@/views/branchIntention/comm/Detail";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
export default {
  components: {
    approveBottom,
    detail,
    ybRevoke
  },
  data() {
    return {
      data: [], // 详情异步数据
      bps: {}, // bps
      ywfghz: "", // 业务分管行长
      ifShow: false, // 是否显示业务分管行长 默认 不显示
      fhForm: {
        ywfghz: ""
      }, // 业务分管行长表单
      requirePeo_options: [], // 分管行长树
      isDb: true, // 是否待办 默认是
      msgPeoParams: {
        // 业务分管行长
        label: "text",
        value: "id",
        children: "children",
        emitPath: false,
        multiple: false
        // leaf: 'id'
      }
    };
  },
  methods: {
    init(params) {
      let self = this;
      let param = {};
      self.bps = params;
      // 判断是否待办流程
      if (params.status !== "db") {
        self.isDb = false;
      }
      param.instanceid = params.processInstID;
      getDateil(param).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.messge, { type: "error" });
          return false;
        }
        console.log("1", res.data.results);
        self.data = res.data.results;
        self.searchEpmlo(self.data);
        // 是否显示 分管行长 02=是，01=否
        if (self.data.issjwbcgxq == "02") {
          self.ifShow = true;
        }
        self.$refs.detail.showDateil(self.data, params);
        if (params.status !== "db") {
          self.fhForm.ywfghz = "emp" + self.data.ywfghz;
        }
      });
    }, // end
    /**
     * 提交
     */
    proving(data) {
      let self = this;
      let status = new Promise(() => {});
      if (data.conclusion === "01" && self.data.issjwbcgxq == "02") {
        // 涉及业务采购时需要验证
        self.$refs["fhForm"].validate(vali => {
          if (vali) {
            status = self.liuzhuan(data).then(val => {
              return val;
            });
          }
        });
      } else {
        status = self.liuzhuan(data).then(val => {
          return val;
        });
      }
      return status;
    },
    /**
     * 流转
     */
    liuzhuan(data) {
      let parms = {};
      let self = this;
      const loading = common.loading(self);
      self.bps.conclusion = data.conclusion;
      self.bps.suggest = data.suggest;
      parms.bps = self.bps;
      parms.ywfghz = self.ywfghz.replace("emp", "");
      console.log("parms", parms);
      return busGeneral(parms).then(res => {
        loading.close();
        return res.data;
      });
    }, // end
    /**
     * 查询业务分管行长
     */
    searchEpmlo(data) {
      let self = this;
      let params = data.orgId;
      // 根据机构id查询行长室部门id
      queryDeptIdForCompId({ compId: params })
        .then(res => {
          if (res.data.status != 200) {
            self.$alert(res.data.message, { type: "error" });
            return false;
          }
          console.log("res.data1:", res.data);
          // 部门id查询行员树
          queryEmployeeTree(res.data.results.depid).then(res => {
            console.log("res.data.results:", res.data.results);
            let data = res.data.results;
            let name = data[0].text;
            this.requirePeo_options = res.data.results;
          });
        })
        .then(res => {});
    }, // end
    /**
     * 分行行长
     */
    peoChange(node) {
      console.log("node::", node);
      //   this.msgDataChange = true;

      if (node.length != 0) {
        let msgNodes = this.$refs["cascader"].getCheckedNodes();
        if (msgNodes.length <= 0) {
          this.ywfghz = node;
        } else {
          let msgids = [];
          let magNames = [];
          if (msgNodes.length != 0) {
            msgids = msgNodes.map(value => {
              return value.data.pcode;
            });
            magNames = msgNodes.map(value => {
              return value.data.text;
            });
          }
          this.ywfghz = msgids.join(",");
        }
      }
    } // end
  }
};
</script>
<style scoped>
.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  z-index: 999;
}
</style>


