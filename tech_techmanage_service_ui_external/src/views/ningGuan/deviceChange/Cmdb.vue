<!-- CMDB录入确认 -->
<template>
  <div class>
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="3">
              <p class="nav_name">网点设备变更流程</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <el-button @click="closeRouter">关 闭</el-button>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <div class="deveice-dateil-container">
      <deveice-detail :data="ngData" ref="deveiceDetail">
        <slot>
          <tr v-if="ifDataIfNormal">
            <td colspan="2">
              <el-form-item label="各个数据是否正常：" prop="dataIfNormal" :rules="[{required: true, message: '请输入标题', trigger: 'blur'}]">
                <el-radio-group v-model="ngData.dataIfNormal" @change="handleDataChange" :disabled="disabled">
                  <el-radio :label="1">是</el-radio>
                  <el-radio :label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </td>
            <td></td>
            <td></td>
          </tr>
          <tr v-for="(item,index) in itemData" :key="item.id">
            <td colspan="2" v-if="item.itemType == 2">
              <el-form-item :label="item.itemName" :prop="$data[item.fields]" :rules="[{required: false, message: '请选择', trigger: 'change'}]">
                <el-radio-group v-model="$data[item.fields]" :disabled="disabled">
                  <el-radio label="1">是</el-radio>
                  <el-radio label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </td>
            <td colspan="2" v-else>
              <el-form-item :label="item.itemName" :prop="$data[item.fields]" :rules="[{required: false, message: '请输入', trigger: 'blur'}]">
                <el-input type="textarea" :rows="2" :disabled="disabled" v-model="$data[item.fields]" placeholder="请输入"></el-input>
              </el-form-item>
            </td>
            <td></td>
            <td></td>
          </tr>
          <tr v-show="ngData.changeType == 3 || ngData.changeType == 4">
            <td colspan="4">
              <el-form-item label="监控变更确认AB岗" prop="monitoringConfirm">
                <el-cascader :options="ngDataTree" :props="props" v-model="ngData.monitoringConfirm" :show-all-levels="false"
                  disabled></el-cascader>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <el-form-item label="出入库录入确认AB岗" prop="forConfirm">
                <el-cascader :options="ngDataTree" :props="props" v-model="ngData.forConfirm" :show-all-levels="false"
                  disabled></el-cascader>
              </el-form-item>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <el-form-item label="CMDB录入确认AB岗" prop="cmdbConfirm">
                <el-cascader :options="ngDataTree" :props="props" v-model="ngData.cmdbConfirm" :show-all-levels="false"
                  disabled></el-cascader>
              </el-form-item>
            </td>
          </tr>
        </slot>
      </deveice-detail>
    </div>
    <div class="czLog app-container">
      <flow-logs :processInstId="flowItem.processInstID" ref="log"></flow-logs>

    </div>
    <!-- 底部栏样式2 -->
    <div class="nav-bottom">
      <approve-bottom :proving="proving" :flowItem="flowItem" :ifConclusion="false" v-if="flowItem.status == 'db'"></approve-bottom>
      <yb-revoke :flowItem="flowItem" v-else></yb-revoke>
    </div>
  </div>
</template>
<script>
  import common from "@/api/common";
  import DeveiceDetail from "@/views/ningGuan/deviceChange/common/Detail";
  import approveBottom from "@/views/plugins/flowPlug/approveBottom";
  import {
    queryDetail,
    cmdbEntry,
    queryListForMid
  } from "@/api/ningGuanApi/deviceChange";
  import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
  import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
  import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
  import Moment from 'moment';

  export default {
    components: { DeveiceDetail, approveBottom, flowLogs, ybRevoke },
    data() {
      return {
        ifDataIfNormal: true,  // 是否显示 各个数据是否正常 默认 显示
        ngData: {}, // 详情
        flowItem: {}, // bps 对象
        itemData: [], // 设备名称选项集合
        batteryModel: '',  // 电池型号
        batterySit: '',    // 电池数量、单节浮充电压、内阻、放电测试是否符合要求
        notes: '',     //备注
        orElectricity: '',   // 主路转旁路、充电、放电是否正常
        orNetwork: '',        // 网络、数据采集是否正常
        upOrNetwork: '',     // 上下线后网络是否正常
        orWiring: '',     //S1和S2接线是否正常
        orOutput: '',    //STS输出是否正常
        orTest: '',    //切换测试是否正常
        activeDisable: '',     // 是否激活
        ngDataTree: [],      // 宁波地区管理部树
        props: {
          // 出入库录入确认AB岗
          label: "text",
          value: "id",
          children: "children",
          emitPath: false,
          multiple: true
          // leaf: 'id'
        },
        ifShowMonitor: true,       // 是否显示监控变更 默认显示
        disabled: true,         // 各个数据组件 是否启用，默认不启用
      };
    },
    methods: {
      /**
       * 页面加载
       */
      init(params) {
        let self = this;
        self.flowItem = params;

        self.$refs.deveiceDetail.init();
        this.$refs.log.init(params.processInstID);
        self.$nextTick(_ => {
          let params = { instanceId: self.flowItem.processInstID };
          queryDetail(params).then(res => {
            console.log("res:", res.data.results);
            self.ngData = res.data.results;
            if (undefined == self.ngData.dataIfNormal || self.ngData.dataIfNormal.lenght <= 0) {
              self.ifDataIfNormal = false
            }
            // self.ngData.createDate = common.dateFormat(
            //   self.ngData.createDate,
            //   "yyyy-MM-dd HH:mm:ss"
            // );
            self.ngData.createDate = Moment(self.ngData.createDate).format('YYYY-MM-DD HH:mm:ss');
            // 各个AB岗 数据处理
            if (self.ngData.monitoringConfirm) {
              self.ngData.monitoringConfirm = self.ngData.monitoringConfirm.split(',')
            }
            if (self.ngData.forConfirm) {
              self.ngData.forConfirm = self.ngData.forConfirm.split(',')
            }
            if (self.ngData.cmdbConfirm) {
              self.ngData.cmdbConfirm = self.ngData.cmdbConfirm.split(',')
            }
            self.initYb(self.flowItem)
            // 各个数据正常
            if (self.ngData.dataIfNormal === 1) {
              console.log('111', self.ngData.dataIfNormal)
              self.handleDataChange(self.ngData.dataIfNormal)
              self.batteryModel = self.ngData.batteryModel
              self.batterySit = self.ngData.batterySit
              self.notes = self.ngData.notes
              self.orElectricity = self.ngData.orElectricity
              self.orNetwork = self.ngData.orNetwork
              self.upOrNetwork = self.ngData.upOrNetwork
              self.orWiring = self.ngData.orWiring
              self.orOutput = self.ngData.orOutput
              self.orTest = self.ngData.orTest
              self.activeDisable = self.ngData.activeDisable
            }
          });
          //
          queryEmployeeTree("1502").then(res => {
            res.data.results.forEach((i, e) => {
              i.text = i.text.substring(8)
              i.children.forEach((v, k) => {
                v.id = v.id.substring(3)
              })
            })
            self.ngDataTree = res.data.results;
          });
        });
      },
      /**
       * 初始化-流程已办
       */
      initYb(params) {
        let self = this
        if (params.status === 'yb') {
          console.log('params.status', params.status)
          self.ifShowMonitor = false
          self.disabled = true
        }
      },
      /**
       * 流转
       */
      liuzhuan(params) {
        let self = this
        const loading = common.loading(self);
        self.flowItem.conclusion = params.conclusion;
        self.flowItem.suggest = params.suggest;
        let folw = {};
        folw.bps = self.flowItem;

        self.ngData.createDate = null

        self.ngData.monitoringConfirm = null
        self.ngData.forConfirm = null
        self.ngData.cmdbConfirm = null
        folw = Object.assign(folw, self.ngData)
        console.log("folw", folw);
        return cmdbEntry(folw)
          .then(res => {
            loading.close();
            console.log('res::', res.data);
            return res.data;
          })
          .catch(error => {
            this.$alert(error, { type: "error" });
          });
      },
      /**
       * 流转
       */
      proving(data) {
        let status = new Promise(() => { });
        status = this.liuzhuan(data).then(val => {
          return val;
        });
        return status;
      },
      /**
       * 各个数据是否正常 change事件
       */
      handleDataChange(val) {

        let self = this;
        console.log("val", self.value = val);
        // 是
        if (val == "1") {
          let data = { mid: self.ngData.changeDevice };
          queryListForMid(data).then(res => {
            if (res.data.status != 200) {
              self.$alert(res.data.message, { type: "error" });
              return false;
            }
            self.itemData = res.data.results;
            console.log("itemData", self.itemData);
          });
        } else {
          self.itemData = []
        }
      },    // end
      /**
       *  关闭当前路由
       */
      closeRouter() {
        this.$router.push({ name: 'deviceChange' });
      },
    }
  };
</script>
<style scoped>
  .nav {
    width: 100%;
    background-color: #ffffff;
  }

  .nav .nav_name {
    width: 150px;
    height: 24px;
    font-size: 16px;
    font-weight: 600;
    color: rgba(38, 38, 38, 1);
    line-height: 24px;
    display: inline-block;
  }

  .nav .wrapper {
    margin-left: 25px;
    margin-right: 25px;
    display: flex;
    justify-content: space-between;
  }

  .wrapper_left,
  .wrapper_right {
    display: flex;
    align-items: center;
  }
</style>