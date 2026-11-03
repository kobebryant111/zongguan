<!-- 发起申请 || 退回 -->
<template>
  <div class="app-container">
    <div class="app-container paenl-container-1">
      <table class="panel-table originator-table">
        <tbody>
          <tr>
            <td class="td-right">发起人：</td>
            <td>{{user.userName}}({{user.badge}})</td>
            <td class="td-right">所在部门：</td>
            <td>{{user.deptName}}</td>
          </tr>
          <tr>
            <td class="td-right">联系方式：</td>
            <td>{{user.mobile}}</td>
            <td class="td-right">发起时间：</td>
            <td>{{user.createDate}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="panel-two">
      <el-form :model="formData" ref="formTwoData" label-width="120px" :rules="twoRules">
        <table class="panel-table">
          <tbody>
            <tr>
              <td colspan="3">
                <el-form-item prop="processTitle" label="标题：">
                  <el-input v-model="formData.processTitle" placeholder="标题" maxlength="20"></el-input>
                </el-form-item>
              </td>
              <td></td>
            </tr>
            <tr>
              <td colspan="2">
                <el-form-item prop="changeDevice" label="变更设备名称:">
                  <el-select v-model="formData.changeDevice" placeholder="请选择">
                    <el-option v-for="item in devicesData" :key="item.id" :label="item.devices" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
              </td>
              <td colspan="2">
                <el-form-item prop="changeType" label="变更类型:">
                  <el-select v-model.number="formData.changeType" placeholder="请选择" @change="handleChangeType">
                    <el-option v-for="item in changeTypeData" :key="Number(item.dictid)" :label="item.dictname"
                      :value.number="Number(item.dictid)"></el-option>
                  </el-select>
                </el-form-item>
              </td>
            </tr>
            <tr>
              <td colspan="4">
                <el-form-item prop="describe" label="描述:">
                  <el-input type="textarea" :rows="2" v-model="formData.describe" :placeholder="description"></el-input>
                </el-form-item>
              </td>
            </tr>
            <tr v-if="ifShowAb">
              <td colspan="4">
                <el-form-item prop="forConfirm" label="出入库录入确认AB岗:" :rules="[{ required: true, message: '请选择', trigger: 'blur' }]">
                  <el-cascader ref="cascader" :options="ngData" :props="props" v-model="formData.forConfirm"
                    :show-all-levels="false" filterable @change="peoChange" collapse-tags></el-cascader>
                </el-form-item>
              </td>
            </tr>
          </tbody>
        </table>
      </el-form>
    </div>
    <!-- 按钮 -->
    <div :class="'footer-right-div'">
      <span slot="footer">
        <button-delete :buttonName="delText" :size="sizes" :fns="delBtn" v-show="ifDelShow" style="float: left;"></button-delete>
        <button-common :buttonName="draftText" :size="sizes" :fns="draftBtn" v-show="ifDraftShow"></button-common>
        <buttonOrange :buttonName="btnName" :size="sizes" :fns="initiateProcessBtn"></buttonOrange>
      </span>
    </div>
  </div>
</template>
<script>
  import common from "@/api/common";
  import buttonDelete from '@/components/eleButton/delete'
  import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
  import buttonCommon from "@/components/eleButton/buttonCommon";
  import { getDicEntry } from "@/api/eosDictEntry";
  import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
  import { doDel } from '@/api/common/flowApi'
  import Moment from 'moment';
  import {
    getCurrentInfo,
    getDeviceList,
    initiateProcess,
    draftProcess
  } from "@/api/ningGuanApi/deviceChange";
  export default {
    name: "InitiateTodo",
    props: {
        closeDialog:{
            default:_ =>{}
        }
    },
    components: { buttonOrange, buttonCommon, buttonDelete },
    data() {
      return {
        flowItem: {},   // 流程对象
        ifDelShow: false,    // 是否显示删除按钮
        ifShowAb: false, // 是否显示AB岗
        draftText: "保存草稿", // 按钮值
        ifShow: false, // 面板显示
        ifDraftShow: true, // 保存草稿按钮显示
        btnName: "确认发起",
        sizes: "size",
        delText: '删 除',
        user: {}, // 用户信息
        description: "", // 描述-placeholder
        formData: {
          processType: "", // 流程类型
          processTitle: "", // 标题
          changeDevice: "", // 变更设备
          describe: "", // 描述
          changeType: "", // 变更类型
          forConfirm: "" // 出入库录入确认
        }, // 表单数据
        processTypeData: [], // 流程类型业务字典
        changeTypeData: [], // 变更类型业务字典
        devicesData: [], // 设备名称集合
        ngData: [], // 出入库录入确认AB岗数据
        props: {
          // 出入库录入确认AB岗
          label: "text",
          value: "id",
          children: "children",
          emitPath: false,
          multiple: true
          // leaf: 'id'
        },
        oneRules: {
          processType: [
            { required: true, message: "请选择流程类型", trigger: "change" }
          ]
        },
        twoRules: {
          processTitle: [
            { required: true, message: "请输入标题", trigger: "blur" }
          ],
          changeDevice: [
            { required: true, message: "请选择变更设备名称", trigger: "change" }
          ],
          changeType: [
            { required: true, message: "请选择变更类型", trigger: "change" }
          ],
          describe: [{ required: true, message: "请输入描述", trigger: "blur" }]
        }
      };
    },
    created() {
      this.init();
    },
    methods: {
      /**
       * 页面加载
       */
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
          console.log('ddd', self.changeTypeData)
        });
        // 当前登录人信息
        getCurrentInfo().then(res => {
          if (res.data.status !== 200) {
            self.$alert(res.data.status, { type: "error" });
            return false;
          }
          self.user = res.data.results;
          self.user.createDate = Moment(self.user.createDate).format('YYYY-MM-DD HH:mm:ss');
        });
        // 设备名称
        getDeviceList().then(res => {
          if (res.data.status !== 200) {
            self.$alert(res.data.status, { type: "error" });
            return false;
          }
          self.devicesData = res.data.results;
        });
      }, // end
      /**
       * 流程发起
       */
      initiateProcessBtn() {
        let self = this;
        console.log("for111", self.formData);
        self.$refs.formTwoData.validate(valid => {
          if (valid) {
              const loading = common.loading(self);
              self.ifShow = true;
              let data = Object.assign(self.formData, self.user);
              data.startDate = self.user.createDate;
              data.contact = self.user.mobile
              if (data.forConfirm === Array) {
                data.forConfirm = data.forConfirm.join(',')
              }
              data.createDate = null;
              console.log("form", data);
              initiateProcess(data).then(res => {
                loading.close()
                self.closeDialog();
                if (res.data.status !== 200) {
                  self.$alert(res.data.message, { type: "error" });
                  return false;
                }
                let message = common.getFlowMessge(res.data.message);
                let messageArr = message.split(",");
                self.$nextTick(() => {
                  common.messageNotify(messageArr, self);
                });
              });
            }
          });

      },
      /**
       * 保存草稿
       */
      draftBtn() {
        let self = this;
        console.log("草稿");
        let data = Object.assign(self.formData, self.user);
        data.startDate = self.user.createDate;
        data.createDate = null;
        data.contact = self.user.mobile
        if (data.forConfirm === Array) {
          data.forConfirm = data.forConfirm.join(',')
        }
        console.log("form", data);
        self.$refs.formTwoData.validate(valid => {
          if (valid) {
            const loading = common.loading(self);
            draftProcess(data).then(res => {
              self.closeDialog();
              loading.close();
              if (res.data.status !== 200) {
                self.$alert(res.data.message, { type: "error" });
                return false;
              }
              let message = common.getFlowMessge(res.data.message);
              let messageArr = message.split(",");
              self.$nextTick(() => {
                common.messageNotify(messageArr, self);
              });
            });
          }
        })

      },
      /**
       * 事件变更-变更类型
       */
      handleChangeType(val) {
        let self = this;
        console.log("val:", val === 1);
        // 采购 || 报废
        if (val === 1 || val === 2) {
          self.description =
            "含入库出库地点、处理人名单、设备变动数量、资产标签等要素。";
          queryEmployeeTree("1502").then(res => {
            self.ngData = res.data.results;
            res.data.results.forEach((i, e) => {
              i.text = i.text.substring(8)
              i.children.forEach((v, k) => {
                v.id = v.id.substring(3)
              })
            })

            console.log(" res.data.results", res.data.results)
            self.ifShowAb = true;
          });
        } else if (val === 3 || val === 4) {
          self.description =
            "含计划完成时间、更换地点名称、处理人员名单、设备变更数量、资产标签、设备供货点等要素。";
          self.ifShowAb = false;
        }
      },
      /**
       * Ab岗
       */
      peoChange(node) {
        let userIds = [];
        node.forEach((v, k) => {
          console.log("数组", v);
          userIds.push(v);
        });
        console.log("数组", userIds.join(","));
        this.formData.forConfirm = userIds.join(",");
      },
      /**
       * 删除流程
       */
      delBtn() {
        this.$confirm('确认删除该流程吗?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.delPro();
        }).catch(error => {
          // this.$message.error(error);
        })
      },

      // 删除流程
      delPro() {
        let params = {};
        let self = this
        params.processInstID = self.formData.bps.processInstID;
        params.workItemID = self.formData.bps.workItemID;
        params.processDefName = self.formData.bps.processDefName;
        let data = common.paramsFormat(params);
        doDel(data).then(res => {
          if (res.data.status == 200) {
            self.$message.success('流程删除成功');
            self.closeDialog();
            // this.$router.push({ name: 'dashboard' });
          } else {
            self.$message.error('流程删除失败');
          }

        });
      },
    }
  };
</script>
<style scoped>
  .td-left {
    text-align: left;
    width: 20%;
  }

  .td-right {
    text-align: right;
    width: 20%;
  }

  .panel-table {
    width: 90%;
  }

  .panel-table td {
    height: 50px;
  }

  .footer-center-div {
    text-align: center;
  }

  .footer-right-div {
    text-align: right;
    padding-top: 10px;
  }

  .el-form-item {
    margin-bottom: 10px;
  }

  div .el-form-item__content {
    margin-left: 0px;
  }
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