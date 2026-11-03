<!-- 新增/修改页面 -->
<template>
  <div>
    <!-- <div class="psire-title">
      <div class="psire-title-main">
        <p v-if="flagNew">新增</p>
        <p v-else>修改</p>
      </div>
    </div> -->

    <div class="psire-container">
      <el-form :model="psireDataNew" ref="baseInfoForm" :rules="rules">
        <div class="psire-main">
          <el-row justify="space-around" :gutter="30">
            <el-col :span="12">
              <el-row>
                <el-col :span="24">
                  <div>
                    <p class="mandatory">*</p>
                    <p style="float: left">流程名称:</p>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="psireName">
                  <el-input v-model="psireDataNew.psireName" maxlength="25"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <el-row>
                <el-col :span="24">
                  <div>
                    <p class="mandatory">*</p>
                    <p style="float: left">流程编号:</p>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="psireNo">
                  <el-input v-model="psireDataNew.psireNo"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row justify="space-around" :gutter="30">
            <el-col :span="12">
              <el-row>
                <el-col :span="24">
                  <div>
                    <p class="mandatory">*</p>
                    <p style="float: left">关联人员:</p>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="relationUserName">
                  <el-select
                    ref="relationUserSelect"
                    placeholder=""
                    v-model="psireDataNew.relationUserName"
                    filterable
                    remote
                    resreve-keyword
                    :remote-method="remoteMethod"
                    :loading="loading"
                    @change="handelSelectChange"
                  >
                    <el-option
                      v-for="item in relationUser_options"
                      :key="item.pcode"
                      :label="item.text"
                      :value="item.text"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <el-row>
                <el-col :span="24">
                  <div>
                    <p class="mandatory">*</p>
                    <p style="float: left">关联部门:</p>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item>
                  <el-select v-model="psireDataNew.dept" placeholder="" disabled>
                    <el-option
                      v-for="item in depData"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row justify="space-around" :gutter="30">
            <el-col :span="12">
              <el-row>
                <el-col :span="24">
                  <div>
                    <p class="mandatory">*</p>
                    <p style="float: left">问题分类:</p>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="problemType">
                  <el-select placeholder="" v-model="psireDataNew.problemType">
                    <el-option key="0" label="流程问题" value="0"></el-option>
                    <el-option key="1" label="专项问题" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row justify="space-around" :gutter="30">
            <el-col :span="24">
              <el-row>
                <el-col :span="24">
                  <div>
                    <p class="mandatory">*</p>
                    <p style="float: left">问题详情:</p>
                  </div>
                </el-col>
              </el-row>
              <div class="formstyle">
                <el-form-item prop="problemDetail">
                  <el-input
                    placeholder="最多可输入500个字！"
                    type="textarea"
                    :autosize="{ minRows: 3}"
                    v-model="psireDataNew.problemDetail"
                    maxlength="500"
                  ></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <div class="hmddialog-bottom">
        <el-button type="primary" @click="sureAdd">保 存</el-button>
        <el-button @click="cancelAdd">关闭</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import common from "@/api/common";
import Moment from "moment";
import { getEmployUser, getDepartmentDetail } from "@/api/common/flowApi";
import {
  save,
  update,
  queryProject
} from "@/api/dataCenter/dataCenPostSupervision";
import {
  queryEmployeeTree,
  queryResourceDept
} from "@/api/integrated/wbryzrlc";
import { mapGetters } from "vuex";

export default {
  name: "newData",
  components: {},
  data() {
    return {
      psireDataNew: {
        psireName: "", //流程名称
        psireNo: "", //流程编号
        relationUser: "", //关联人员id
        relationUserName: "", //关联人员名称
        deptName: "", //关联部门名称
        dept: "", //关联部门
        problemType: "", //问题分类
        problemDetail: "" //问题详情
      }, //表单数据
      loading: false, //是否显示正在加载
      flagNew: true, //新增或修改
      depData: [], //部门列表
      rules: {
        psireName: [
          { required: true, message: "请填写流程名称", trigger: "blur" }
        ],
        psireNo: [
          { required: true, message: "请填写流程编号", trigger: "blur" }
        ],
        relationUserName: [
          { required: true, message: "请填写关联人员", trigger: "blur" }
        ],
        problemType: [
          { required: true, message: "请选择问题分类", trigger: "change" }
        ],
        problemDetail: [
          { required: true, message: "请填写问题详情", trigger: "blur" }
        ]
      },
      relationUser_options: [], //关联人员列表
      relationUser_options_all: [] //关联人员列表
    };
  },
  props: {
    //后督数据
    psireData: {
      type: Object,
      default: () => ({})
    },
    //新增和修改判断
    flag: {
      type: Boolean,
      default: true
    },
    closeDialog: {
      type: Function,
      default: _ => {}
    },
    getPsireData: {
      type: Function,
      default: _ => {}
    }
  },
  watch: {
    psireData: {
      handler(val) {
        if (val != undefined) {
          this.psireDataNew = JSON.parse(JSON.stringify(val));
        }
      },
      deep: true
    },
    flag: {
      handler(val) {
        if (val != undefined) {
          this.flagNew = val;
        }
      },
      deep: true
    }
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  created() {},
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.getDep();
      this.psireDataNew = JSON.parse(JSON.stringify(this.psireData));
      this.flagNew = this.flag;
      this.getKjList();
    },

    //获取行员列表
    remoteMethod(val) {
      if (val != "") {
        this.loading = true;
        this.relationUser_options = [];
        for (var i = 0; i < this.relationUser_options_all.length; i++) {
          if (this.relationUser_options_all[i].text.indexOf(val) != -1) {
            this.relationUser_options.push(this.relationUser_options_all[i]);
          }
        }
        this.loading = false;
      } else {
        this.relationUser_options = [];
      }
    },

    //获取科技部行员列表
    getKjList() {
      let params = "";
      params = "7004"; //130是总行科技部部门编号
      queryEmployeeTree(params)
        .then(res => {
          if (res.data.status == 200) {
            res.data.results[0].children.filter(li => {
              li.children.filter(ol => {
                ol.chargeName = li.text.replace("总行金融科技部-", "");
                this.relationUser_options_all.push(ol);
              });
            });
          } else {
            this.relationUser_options_all = [];
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 获取部门
    getDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
          var fild = res.data.results[0].children;
          let name = res.data.results[0].text;
          this.depData = fild.filter(tt => {
            // tt.id = tt.id.replace("dep", "");
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //处理
    handelSelectChange(val) {
      var reg = /\(([^)]*)\)/;
      var badge = val.match(reg)[1];
      // var badge = val;
      for (var i = 0; i < this.relationUser_options.length; i++) {
        if (this.relationUser_options[i].pcode == badge) {
          this.psireDataNew.dept = this.relationUser_options[i].pid;
          this.psireDataNew.relationUser = this.relationUser_options[i].pcode;
          this.psireDataNew.deptName = this.relationUser_options[i].chargeName;
          return;
        }
      }
    },

    //保存
    sureAdd() {
      this.$refs["baseInfoForm"].validate(valid => {
        if (valid) {
          this.queryProjectNo(this.psireDataNew.psireNo);
        } else {
          this.$message({
            showClose: true,
            message: "有信息未填写完整！",
            type: "error"
          });
          return false;
        }
      });
    },

    //流程编号校验
    queryProjectNo(projectNo) {
      let params = {};
      params.projectno = projectNo;
      let data = common.paramsFormat(params);
      let flag = false;
      queryProject(data).then(res => {
        if (res.data.status == 200 && res.data.results != undefined) {
          this.saveAndUpdate();
        } else {
          this.$message({
            showClose: true,
            message: "流程编号填写不正确，请修改！",
            type: "error"
          });
        }
      });
    },

    saveAndUpdate() {
      if (this.flagNew) {
        let params = this.psireDataNew;
        params.dept = params.dept.replace("dep", "");
        let data = common.paramsFormat(params);
        save(data).then(res => {
          if (res.data.status == 200) {
            this.$message.success("添加成功");
            this.closeDialog();
            this.getPsireData();
          }
        });
      } else {
        let params = this.psireDataNew;
        params.dept = params.dept.replace("dep", "");
        let data = common.paramsFormat(params);
        update(data).then(res => {
          if (res.data.status == 200) {
            this.$message.success("修改成功");
            this.closeDialog();
            this.getPsireData();
          }
        });
      }
    },

    //关闭
    cancelAdd() {
      this.psireDataNew = {};
      this.closeDialog();
    }
  }
};
</script>

<style scoped>
.formstyle {
  margin: 13px 0 10px 0;
}

.mandatory {
  color: red;
  float: left;
  margin-right: 5px;
}

.hmddialog-bottom {
  text-align: center;
}

.psire-title-main {
  /* min-height: calc(100vh - 84px); */
  padding: 0 15px 0 15px;
  border-bottom: 1px solid #dcdcdc;
  background-color: #ffffff;
}

.psire-title {
  margin: 15px 15px 0 15px;
}

.psire-container {
  margin: 0 15px 0 15px;
}

.psire-main {
  /* min-height: calc(100vh - 84px); */
  padding: 15px;
  background-color: #ffffff;
}

/* 布局调整 */
.psire-container .el-row {
  margin-bottom: -13px;
  display: flex;
  flex-wrap: wrap;
}
</style>

<style>
.psire-container .el-form--inline .el-form-item {
  width: 100%;
}

.psire-container .el-form--inline .el-form-item__content {
  width: 70%;
}
</style>
