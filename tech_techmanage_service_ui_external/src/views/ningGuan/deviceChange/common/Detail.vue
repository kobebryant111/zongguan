<!-- 显示主要详情 -->
<template>
    
  <div class="">
    <div class="app-container paenl-container-1">
      <table class="panel-table originator-table">
        <tbody>
          <tr>
            <td class="td-right">发起人：</td>
            <td>{{formData.userName}}</td>
            <td class="td-right">所在部门：</td>
            <td>{{formData.deptName}}</td>
          </tr>
          <tr>
            <td class="td-right">联系方式：</td>
            <td>{{formData.contact}}</td>

            <td class="td-right">发起时间：</td>
            <td>{{formData.createDate}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="app-container paenl-container-2">
      <el-form :model="formData" ref="formTwoData" label-width="150px">
        <table class="panel-table">
          <tbody>
            <tr>
              <td colspan="3">
                <el-form-item prop="processTitle" label="标题：">
                  <el-input
                    v-model="formData.processTitle"
                    disabled
                    placeholder="标题"
                    maxlength="20"
                  ></el-input>
                </el-form-item>
              </td>
              <td></td>
            </tr>
            <tr>
              <td colspan="2">
                <el-form-item prop="changeDevice" label="变更设备名称:">
                  <el-select v-model="formData.changeDevice" placeholder="请选择" disabled>
                    <el-option
                      v-for="item in devicesData"
                      :key="item.id"
                      :label="item.devices"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </td>
              <td colspan="2">
                <el-form-item prop="changeType" label="变更类型:">
                  <el-select
                    v-model="formData.changeType"
                    placeholder="请选择"
                    disabled
                  >
                    <el-option
                      v-for="item in changeTypeData"
                      :key="Number(item.dictid)"
                      :label="item.dictname"
                      :value="Number(item.dictid)"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </td>
            </tr>
            <tr>
              <td colspan="4">
                <el-form-item prop="describe" label="描述:">
                  <el-input
                    type="textarea"
                    :rows="2"
                    v-model="formData.describe"
                    disabled
                  ></el-input>
                </el-form-item>
              </td>
            </tr>
            <slot></slot>
          </tbody>
        </table>
      </el-form>
    </div>
  </div>
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
import {
  getDeviceList,
} from "@/api/ningGuanApi/deviceChange";
export default {
  name: "DeveiceDetail",
  components: {},
  props: {
    data: {
      type: Object,
      default: {}
    }
  },
  data() {
    return {
      formData: this.data,
      processTypeData : [],
      changeTypeData : [],
      devicesData : [],
      rules : {
        batteryModel : [
          {required: true , message: '请选择', trigger: 'change'}
        ]
      },

    };
  },
  watch: {
    data(nld){
      console.log('nldn',nld)
      this.formData = nld
    }
  },
  methods: {
    init() {
      let self = this;
      let eosPramas = { dicttypeid: "NEW_DEVICE_CHANGE,NEW_CHANGE_TYPE" };
      // 业务字典
      getDicEntry(eosPramas).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.status, { type: "error" });
          return false;
        }
        let data = res.data.results;
        // 流程类型
        self.processTypeData = data.filter(ii => {
          return ii.dicttypeid === "NEW_DEVICE_CHANGE";
        });
        // 变更类型
        self.changeTypeData = data.filter(ii => {
          return ii.dicttypeid === "NEW_CHANGE_TYPE";
        });
        // self.processTypeData = res.data.results
      });
      // 设备名称
      getDeviceList().then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.status, { type: "error" });
          return false;
        }
        self.devicesData = res.data.results;
      });
    },
    /**
     * form 验证
     */
    async vaildation(item) {
      let self = this
      let falg = false ;
      let data_obj = Object.assign(self.data,item);
      self.formTwoData = data_obj
      self.$refs.formTwoData.validateField('batteryModel')
      self.$refs.formTwoData.validate(vali=>{
        console.log("vali", vali,self.$refs.formTwoData)
        falg = vali
        return vali
      })
      return falg ;
    }
  }
};
</script>
<style scoped>
.panel-table {
  width: 90%;
}
.paenl-container-1 {
  border-bottom: 10px solid #F0F2F5
}
.td-right{
  text-align: right;
}
.originator-table tr {
  height: 30px;
}
</style>



