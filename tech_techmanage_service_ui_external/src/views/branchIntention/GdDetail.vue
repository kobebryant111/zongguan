<!-- 归档详情 -->
<template>
  <div class="global-container">
    <detail ref="detail" :isShowLog="false">
      <template slot="kjzjlSlot">
        <div class="app-container">
          <el-form
            :model="form"
            ref="form"
            label-width="200px"
            :rules="{sfThreenew:[{required: true,message: '请选择是否涉及三新',trigger: 'change'}]}"
          >
            <el-form-item label="是否涉及三新" prop="sfThreenew">
              <el-radio-group v-model="form.sfThreenew" :disabled="isShow">
                <el-radio
                  v-for="item in items"
                  :key="item.id"
                  :label="item.id"
                  disabled
                >{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否涉及新架构" prop="sfXjg" v-show="isShow">
              <el-radio-group v-model="form.sfXjg" disabled>
                <el-radio v-for="item in items" :key="item.id" :label="item.id">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否涉及新技术" prop="sfXjs" v-show="isShow" :disabled="isShow">
              <el-radio-group v-model="form.sfXjs" disabled>
                <el-radio v-for="item in items" :key="item.id" :label="item.id">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
        </div>
      </template>
    </detail>
    <div class="app-container">
        <flow-logs-gd ref="log"></flow-logs-gd>
    </div>
    
  </div>
</template>
<script>
import { getDateil } from "@/api/branchIntentionApi/fhyx";
import detail from "@/views/branchIntention/comm/Detail";
import flowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
export default {
  components: {
    detail,
    flowLogsGd
  },
  data() {
    return {
      data : [],
      flowItem : {},        // bps
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
  methods : {
      init(params) {
          console.log('params:',params)
      let self = this;
      let param = {};
      self.flowItem = params;   
      param.instanceid = params.instanceid || params.processInstID;
      self.$refs.log.init2(params);
      getDateil(param).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.messge, { type: "error" });
          return false;
        }
        console.log("1", res.data.results);
        self.data = res.data.results;
        self.isDb = false;
        self.form.sfThreenew = self.data.sfThreenew;
        if (self.data.sfThreenew === "1") {
          self.isShow = true;
        }
        self.form.sfXjg = self.data.sfXjg;
        self.form.sfXjs = self.data.sfXjs;
        self.$refs.detail.showDateil(self.data, params);
      });
    }, // end
  },
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
