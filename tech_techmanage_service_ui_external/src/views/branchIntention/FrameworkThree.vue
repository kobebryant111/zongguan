<!-- 架构部三新审核  -->
<template>
  <div class="global-container">
    <detail ref="detail">
      <template slot="kjzjlSlot">
        <div class="app-container">
          <el-form
            :model="form"
            ref="form"
            label-width="200px"
            :rules="{sfThreenew:[{required: true,message: '请选择是否涉及三新',trigger: 'change'}]}"
          >
            <el-form-item label="是否涉及三新" prop="sfThreenew">
              <el-radio-group v-model="form.sfThreenew" @change="selectedThreenew" :disabled="bps.status == 'yb'">
                <el-radio v-for="item in items" :key="item.id" :label="item.id">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否涉及新架构" prop="sfXjg" v-show="isShow">
              <el-radio-group v-model="form.sfXjg" :disabled="!isShow || bps.status == 'yb'">
                <el-radio v-for="item in items" :key="item.id" :label="item.id">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否涉及新技术" prop="sfXjs" v-show="isShow" :disabled="isShow">
              <el-radio-group v-model="form.sfXjs" :disabled="!isShow || bps.status == 'yb'">
                <el-radio v-for="item in items" :key="item.id" :label="item.id">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
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
import { getDateil, frameworkDept } from "@/api/branchIntentionApi/fhyx";
import common from "@/api/common";
import detail from "@/views/branchIntention/comm/Detail";
import flowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
export default {
  components: {
    detail,
    ybRevoke,
    approveBottom
  },
  data() {
    return {
      data: [],
      bps: {}, // bps
      isDb: true, // 是否待办，默认:true
      isShow: false, // 是否显示
      items: [{ id: "1", label: "是" }, { id: "0", label: "否" }], // 是否涉及三新
      form: {
        sfThreenew: "", // 是否涉及三新(1:是，0：否)
        sfXjg: "", //是否涉及新架构(1:是，0：否)
        sfXjs: "" // 是否涉及新技术(1:是，0：否)
      } // 是否涉及三新表单
    };
  },
  methods: {
    init(params) {
      let self = this;
      let param = {};
      self.bps = params;
      param.instanceid = params.processInstID;

      getDateil(param).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.messge, { type: "error" });
          return false;
        }
        self.data = res.data.results;
        console.log("params.status", params.status);
        if (params.status !== "db") {
          self.isDb = false;
          self.form.sfThreenew = self.data.sfThreenew;
          if (self.data.sfThreenew === "1") {
            self.isShow = true;
          }
          self.form.sfXjg = self.data.sfXjg;
          self.form.sfXjs = self.data.sfXjs;
        }

        self.$refs.detail.showDateil(self.data, params);
      });
    }, // end
    /**
     * 提交
     */
    proving(data) {
      let self = this;
      let status = new Promise(() => {});
      if (data.conclusion === "01") {
        self.$refs["form"].validate(vali => {
          if (vali) {
            // 验证 三新
            if (self.form.sfThreenew === "1") {
              if (!self.form.sfXjg) {
                
                self.$alert("是否涉及新架构不能为空", { type: "error" });
                return false;
              }
              if (!self.form.sfXjs) {
                
                self.$alert("是否涉及新技术不能为空", { type: "error" });
                return false;
              }
              // 必须有一个为是
              if (self.form.sfXjg != "1" && self.form.sfXjs != "1") {
                
                self.$alert("涉及三新时，是否涉及新技术、是否涉及新架构必须有一个选是", { type: "error" });
                return false;
              }
            }
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
      parms = self.form;
      self.bps.conclusion = data.conclusion;
      self.bps.suggest = data.suggest;
      parms.bps = self.bps;
      console.log("parms", parms);

      return frameworkDept(parms).then(res => {
        loading.close();
        return res.data;
      });
    },
    /**
     * 是否涉及三新触发
     */
    selectedThreenew() {
      let self = this;
      // 选择否时 情况子项的值
      if (self.form.sfThreenew === "0") {
        self.isShow = false;
        self.form.sfXjg = "";
        self.form.sfXjs = "";
      } else {
        self.isShow = true;
      }
    }
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
