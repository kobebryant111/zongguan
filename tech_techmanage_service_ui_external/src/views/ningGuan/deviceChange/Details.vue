<!-- 网点设备变更流程详情 -->
<template>
  <div class>
    <div class="deveice-dateil-container">
      <deveice-detail :data="ngData" ref="deveiceDetail">
        <slot>
          <tr v-if="ifDataIfNormal">
            <td colspan="2">
              <el-form-item label="各个数据是否正常：" prop="dataIfNormal" :rules="[{required: true, message: '请输入标题', trigger: 'blur'}]">
                <el-radio-group v-model.number="ngData.dataIfNormal" @change="handleDataChange" :disabled="disabled">
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
              <el-form-item :label="item.itemName" :prop="item.fields" :rules="[{required: item.notifications == 1 ? true : false, message: '请选择', trigger: 'change'}]">
                <el-radio-group v-model="ngData[item.fields]" :disabled="disabled">
                  <el-radio label="1">是</el-radio>
                  <el-radio label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </td>
            <td colspan="2" v-else>
              <el-form-item :label="item.itemName" :prop="item.fields" :rules="[{required: item.notifications == 1 ? true : false, message: '请输入', trigger: 'blur'}]">
                <el-input type="textarea" :rows="2" :disabled="disabled" v-model="ngData[item.fields]" placeholder="请输入"></el-input>
              </el-form-item>
            </td>
            <td></td>
            <td></td>
          </tr>
          <tr v-show="ngData.dataIfNormal == 0">
            <td colspan="2">
              <el-form-item label="异常说明:" prop="exceDesc">
                <el-input type="textarea" :rows="2" :disabled="disabled" v-model="ngData.exceDesc" placeholder="请输入"></el-input>
              </el-form-item>
            </td>
            <td></td>
            <td></td>
          </tr>
        </slot>
      </deveice-detail>
    </div>
    <div class="czLog app-container">
      <flow-logs-gd ref="log"></flow-logs-gd>
    </div>

  </div>
</template>
<script>
  import common from "@/api/common";
  import DeveiceDetail from "@/views/ningGuan/deviceChange/common/Detail";
  import {
    queryDetail,
    cmdbEntry,
    queryListForMid
  } from "@/api/ningGuanApi/deviceChange";
  import flowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
  import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
  import Moment from 'moment';
  export default {
    name: 'DetailView',
    components: { DeveiceDetail, flowLogsGd },
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
        params.instanceid = params.instanceId
        self.$refs.deveiceDetail.init();
        console.log("params:", params.instanceId);
        self.$refs.log.init(params);
        self.$nextTick(_ => {
          let params = { instanceId: self.flowItem.instanceId };
          queryDetail(params).then(res => {
            console.log("res:", res.data.results);
            self.ngData = res.data.results;
            // self.ngData.createDate = common.dateFormat(
            //   self.ngData.createDate,
            //   "yyyy-MM-dd HH:mm:ss"
            // );
            self.ngData.createDate = Moment(self.ngData.createDate).format('YYYY-MM-DD HH:mm:ss');
            console.log('ngData', self.ngData)
            // 各个数据正常
            if (undefined != self.ngData.dataIfNormal
              && self.ngData.dataIfNormal === 1) {
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
            } else if (self.ngData.dataIfNormal === 0) {

            } else {
              self.ifDataIfNormal = false
            }
          });
          //
          queryEmployeeTree("1502").then(res => {
            self.ngDataTree = res.data.results;
          });
        });
      },  // end
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

    }
  };
</script>