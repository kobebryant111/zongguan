<!-- 流程配置 -->
<template>
  <div class="container container_zb">
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">流程配置</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common :buttonName="buttonClose" :size="sizes" :fns="close"></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <div class="contentMain">
      <div class="requireContainer">
        <div class="requireContainer_head">
          <el-row type="flex">
            <div class="requireList_head_right_orange_common">
              <button-common :buttonName="buttonNew" :size="sizes" :fns="addSettingData"></button-common>
            </div>
            <div class="requireList_head_right_orange_common">
              <button-common :buttonName="buttonEdit" :size="sizes" :fns="updateSettingData"></button-common>
            </div>
            <div class="requireList_head_right_orange_common">
              <button-common :buttonName="buttonDelete" :size="sizes" :fns="deleteSettingData"></button-common>
            </div>
          </el-row>
        </div>
        <el-table
          border
          :data="settingDatas"
          highlight-current-row
          max-height="510"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="40"></el-table-column>
          <el-table-column type="index" label="序号" width="40"></el-table-column>
          <el-table-column prop="teamName" label="部门名称" width="240" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column
            prop="permissioncategory"
            label="权限类别"
            width="130"
            :show-overflow-tooltip="true"
          ></el-table-column>
          <el-table-column prop="remarks" label="备注" width="160" :show-overflow-tooltip="true"></el-table-column>
        </el-table>

        <el-dialog :title="title" :visible.sync="showAddAndUpdate" width="40%" :close-on-click-modal="false">
          <div class="requireContainer">
            <el-form inline :model="newOrUpdateData" ref="dataInfoForm">
              <el-row>
                <el-col :span="24">
                  <el-form-item
                    label="权限类别:"
                    label-width="100px"
                    prop="permissioncategory"
                    :rules="[{ required: true, message: '请填写权限类别', trigger: 'blur' }]"
                  >
                    <el-input
                      style="width: 400px"
                      :title="newOrUpdateData.permissioncategory"
                      v-model="newOrUpdateData.permissioncategory"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item
                    label="部门范围:"
                    label-width="100px"
                    prop="teamName"
                    :rules="[{ required: true, message: '请填写部门范围', trigger: ['blur','change'] }]"
                  >
                    <!-- <el-input
                      style="width: 260px"
                      :title="newOrUpdateData.teamName"
                      v-model="newOrUpdateData.teamName"
                    ></el-input>-->
                    <el-input
                      v-model="newOrUpdateData.teamName"
                      :disabled="true"
                      style="width: 400px"
                      :title="newOrUpdateData.teamName"
                    >
                      <el-button slot="append" icon="el-icon-more" @click="showDialog"></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item
                    label="人员类型:"
                    label-width="100px"
                    prop="userTypes"
                    :rules="[{ required: true, message: '请选择人员类型', trigger: 'change' }]"
                  >
                    <el-select
                      :disabled="isDisabled"
                      style="width: 400px"
                      v-model="newOrUpdateData.userTypes"
                      multiple
                      @change="handleUserTypes"
                    >
                      <el-option key="0" value="行员"></el-option>
                      <el-option key="1" value="外包"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="备注:" label-width="100px" prop="remarks">
                    <el-input
                      style="width: 400px"
                      :title="newOrUpdateData.remarks"
                      v-model="newOrUpdateData.remarks"
                      type="textarea"
                      :autosize="{ maxRows: 3}"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div class="wrapper_bottom">
            <el-row :gutter="10" type="flex" justify="end">
              <el-col :span="1.5">
                <button-common :buttonName="buttonCancel" :size="sizes" :fns="flCancel"></button-common>
              </el-col>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonKeep" :size="sizes" :fns="flSure"></button-orange>
              </el-col>
            </el-row>
          </div>
        </el-dialog>
        <el-dialog title="选择部门" :visible.sync="showDeptSelect" width="40%">
          <single-dept-for-ap
            :input="newOrUpdateData.teamName"
            pcode="6000000300"
            :fns="returnToFlowSetting"
          ></single-dept-for-ap>
        </el-dialog>
      </div>
    </div>
  </div>
</template>
<script>
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import singleDeptForAp from "./singleDeptForAP";

import common from "@/api/common";
import Moment from "moment";
import {
  queryPagerForFlowSetting,
  queryDetailForFlowSetting,
  deleteForFlowSetting,
  updateForFlowSetting,
  addForFlowSetting
} from "@/api/dataCenter/dataCenAccessPermission";
import { mapGetters } from "vuex";

export default {
  name: "record",
  components: {
    buttonCommon,
    buttonOrange,
    singleDeptForAp
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认:50
      index: 1, // 初始值 默认:1

      sizes: "small", //按钮大小
      buttonNew: "增加",
      buttonEdit: "修改",
      buttonDelete: "删除",
      buttonClose: "关闭",
      buttonKeep: "保存",
      buttonCancel: "取消",

      settingDatas: [], //流程配置数据
      selectDatas: [], //选择的数据
      showAddAndUpdate: false,
      showDeptSelect: false,
      title: "", //标题
      isDisabled: "false",
      newOrUpdateData: {
        permissioncategory: "", //权限分类
        teamName: "", //部门名称
        teamnum: "", //部门id
        usertype: "", //人员类型
        remarks: "", //备注
        userTypes: []
      } //新增或修改数据
    };
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
      this.getSettingData();
    },

    //查询
    getSettingData() {
      let params = {};
      params.size = 500;
      params.index = 1;
      queryPagerForFlowSetting(params).then(res => {
        
        if (res.data.status == 200) {
          this.settingDatas = res.data.results;
        }
      });
    },

    //增加
    addSettingData() {
      this.newOrUpdateData = {
        permissioncategory: "", //权限分类
        teamName: "", //部门名称
        teamnum: "", //部门id
        usertype: "", //人员类型
        remarks: "" //备注
      };
      this.showAddAndUpdate = true;
      this.title = "增加";
      this.isDisabled = false;
    },

    //修改
    updateSettingData() {
      if (this.selectDatas.length > 0) {
        if (this.selectDatas.length > 1) {
          this.$message.error("一次仅可修改1条！");
        } else {
          queryDetailForFlowSetting(this.selectDatas[0]).then(res => {
            console.log("updateSettingData:", res);
            if (res.data.status == 200) {
              this.newOrUpdateData = res.data.results;
              this.newOrUpdateData.userTypes = this.newOrUpdateData.usertype.split(
                ","
              );
            }
          });
          this.showAddAndUpdate = true;
          this.title = "修改";
          this.isDisabled = true;
        }
      }
    },

    //删除
    deleteSettingData() {
      if (this.selectDatas.length > 0) {
        this.$confirm("是否删除选中的流程配置信息?", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: "warning"
        })
          .then(() => {
            deleteForFlowSetting(this.selectDatas).then(res => {
              if (res.data.status == 200) {
                this.$message.success("删除成功");
                this.getSettingData();
              }
            });
          })
          .catch(error => {});
      }
    },

    handleSelectionChange(val) {
      this.selectDatas = val;
    },

    handleUserTypes(val) {
      console.log("handleUserTypes:", val);
      this.newOrUpdateData.usertype = "";
      for (var i = 0; i < val.length; i++) {
        this.newOrUpdateData.usertype =
          this.newOrUpdateData.usertype + val[i] + ",";
      }
      this.newOrUpdateData.usertype = this.newOrUpdateData.usertype.substring(
        0,
        this.newOrUpdateData.usertype.length - 1
      );
      console.log("this.newOrUpdateData:", this.newOrUpdateData);
    },

    showDialog() {
      if (this.title == "增加") {
        this.showDeptSelect = true;
      }
    },

    returnToFlowSetting(val) {
      console.log(val);
      if (val.id != undefined) {
        this.newOrUpdateData.teamName = val.text;
        this.newOrUpdateData.teamnum = val.id.replace("dep", "");
      }
      this.showDeptSelect = false;
    },

    close() {
      this.$router.push({ name: "accessPermission" });
    },

    flCancel() {
      this.newOrUpdateData = {
        permissioncategory: "", //权限分类
        teamName: "", //部门名称
        teamnum: "", //部门id
        usertype: "", //人员类型
        remarks: "" //备注
      };
      this.showAddAndUpdate = false;
    },

    flSure() {
      this.$refs["dataInfoForm"].validate(valid => {
        if (valid) {
          let params = {};
          params = this.newOrUpdateData;
          let data = common.paramsFormat(params);
          if (this.title == "增加") {
            addForFlowSetting(data).then(res => {
              console.log("addForFlowSetting:", res);
            });
          } else {
            updateForFlowSetting(data).then(res => {
              console.log("updateForFlowSetting:", res);
            });
          }
          this.showAddAndUpdate = false;
          this.getSettingData();
        } else {
          this.$message({
            showClose: true,
            message: "操作失败,请填写必填项",
            type: "error"
          });
          return false;
        }
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

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}

.requireContainer_head {
  float: left;
  margin-bottom: 10px;
}

.requireContainer {
  background-color: #ffffff;
  padding: 10px 18px 10px 18px;
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

/* bin修改 */
.contentMain {
  width: 100%;
  height: 100%;
  font-size: 16px;
  padding: 10px 20px;
}

/* 导航菜单栏 */
.nav {
  width: 100%;
  /* height: 30px; */
  background-color: #ffffff;
}

.nav .nav_name {
  width: 300px;
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

.contentMain .row_2 {
  background-color: #ffffff;
  padding: 0 20px 15px 20px;
  margin-top: 15px;
}

.requireList_head_right_orange_common {
  margin: 0 5px 10px 5px;
  float: left;
}
</style>
<style>
.el-tooltip__popper {
  max-width: 500px;
}
</style>