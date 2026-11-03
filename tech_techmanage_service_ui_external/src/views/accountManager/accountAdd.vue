<!-- 添加合同信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">{{ title }}台账</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common
                :buttonName="buttonClose"
                :size="size"
                :fns="close"
              ></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>

    <!-- 项目信息 -->
    <div class="contentMain">
      <el-form :model="formData" ref="formData">
        <div class="contentTwo_title">
          <p class="contentTwo_title_p">基本信息</p>
        </div>
        <div class="content">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>外包项名称 *</p>
                    <el-form-item
                      prop="projectName"
                      :rules="[
                        {
                          required: true,
                          message: '请填写外包项名称',
                          trigger: 'blur',
                        },
                      ]"
                    >
                      <el-input
                        placeholder="请输入"
                        v-model="formData.projectName"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>

                <el-col :span="11">
                  <div>
                    <p>具体外包事项 *</p>
                    <el-form-item
                      prop="descriptions"
                      :rules="[
                        {
                          required: true,
                          message: '请填写具体外包事项',
                          trigger: 'blur',
                        },
                      ]"
                      ref="msgForm"
                    >
                      <el-input
                        placeholder="请填写具体外包事项"
                        v-model="formData.descriptions"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>外包业务用工部门 *</p>
                    <el-form-item
                      prop="deptName"
                      :rules="[
                        {
                          required: true,
                          message: '请选择部门',
                          trigger: 'change',
                        },
                      ]"
                    >
                    <el-input
                      placeholder="请输入部门名称"
                      v-model="formData.deptName"
                      clearable>
                      <el-option
                          v-for="item in depData"
                          :key="item.depid"
                          :label="item.location"
                          :value="item.depcode"
                      />
                    </el-input>
                      <!-- <el-select
                        v-model="formData.deptNo"
                        placeholder="请选择部门"
                        @change="changeDept" filterable
                      >
                        <el-option
                          v-for="item in depData"
                          :key="item.depid"
                          :label="item.location"
                          :value="item.depcode"
                        />
                      </el-select> -->
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>外包服务提供商 *</p>
                    <el-form-item
                      prop="vendorName"
                      :rules="[
                        {
                          required: true,
                          message: '请输入供应商名称',
                          trigger: 'change',
                        },
                      ]"
                    >
                    <el-input
                      placeholder="请输入供应商名称"
                      v-model="formData.vendorName"
                      clearable>
                      <el-option
                          v-for="item in outsourceno_options"
                          :key="item.id"
                          :label="item.outsourcname"
                          :value="item.orgcode"
                      />
                    </el-input>
                      <!-- <el-select
                        v-model="formData.vendor"
                        placeholder="请选择供应商"
                        :disabled="forbidden"
                        value-key="label"
                        @change="changeVendor" filterable
                      >
                        <el-option
                          v-for="item in outsourceno_options"
                          :key="item.id"
                          :label="item.outsourcname"
                          :value="item.orgcode"
                        />
                      </el-select> -->
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>服务商注册地（境外、境内） *</p>
                    <el-form-item
                      prop="registerPlace"
                      :rules="[
                        {
                          required: true,
                          message: '请选择',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-input
                        placeholder="请填写服务商注册地（境外、境内）"
                        v-model="formData.registerPlace"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>合同起始时间 *</p>
                    <el-form-item
                      prop="startDate"
                      :rules="[
                        {
                          required: true,
                          message: '请选择合同起始时间',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-date-picker
                        v-model="formData.startDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                      />
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>合同到期日 *</p>
                    <el-form-item
                      prop="endDate"
                      :rules="[
                        {
                          required: true,
                          message: '请选择合同到期日',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-date-picker
                        v-model="formData.endDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                      />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>外包项目金额(元) *</p>
                    <el-form-item
                      prop="amount"
                      :rules="[
                        {
                          required: true,
                          message: '请输入外包项目金额（元）',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-input
                        placeholder="请输入外包项目金额（元）"
                        v-model="formData.amount"
                        type ="number"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>外包项目人数 *</p>
                    <el-form-item
                      prop="nums"
                      :rules="[
                        {
                          required: true,
                          message: '请选择是否完成',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-input
                        placeholder="请输入外包项目人数"
                        v-model="formData.nums"
                        type ="number"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>服务方式（是否驻场） *</p>
                    <el-form-item
                      prop="isStation"
                      :rules="[
                        {
                          required: true,
                          message: '请选择',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-select
                        v-model="formData.isStation"
                        placeholder="请选择"
                      >
                        <el-option
                          v-for="item in isstation_option"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="11">
                  <div>
                    <p>是否分包或转包 *</p>
                    <el-form-item
                      prop="isSubcontract"
                      :rules="[
                        {
                          required: true,
                          message: '请选择',
                          trigger: 'change',
                        },
                      ]"
                    >
                      <el-select
                        v-model="formData.isSubcontract"
                        placeholder="请选择"
                      >
                        <el-option
                          v-for="item in issub_option"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <el-row>
        <div class="nav bottom">
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="1.5"></el-col>
            </div>
            <div class="wrapper_right">
              <div class="wrapper_right_bottom_1_zb">
                <el-col :span="1.5">
                  <button-common
                    :buttonName="buttonCancel"
                    :size="size"
                    :fns="close"
                  />
                </el-col>
              </div>
              <div>
                <el-col :span="1.5">
                  <button-orange
                    :buttonName="buttonCommit"
                    :size="size"
                    :fns="onSubmit"
                  />
                </el-col>
              </div>
            </div>
          </div>
        </div>
      </el-row>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import { getTwoDepartDetail } from "@/api/common/flowApi";
import {
  save,
  queryDetail,
  updateContract,
  queryListByMapSmall,
  updateAccount,
  uploadExcel,
} from "@/api/accountManager/accountManager";
import { queryZBYA, count } from "@/api/supplierManager/fileManage";
import { getFlowData, getListAll, preview } from "@/api/common/workflowApi";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { toPoint } from "@/utils";
import Moment from "moment";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "contractInfo",
  components: { buttonOrange, buttonCommon },
  data() {
    return {
      id: null,
      deptId: null,
      formData: {
        projectName: null,
        descriptions: null,
        deptNo: null,
        registerPlace: null,
        startDate: null,
        endDate: null,
        amount: null,
        nums: null,
        isStation: null,
        isSubcontract: null,
      },
      managerData: {},
      deptmentData: {},
      size: "mini", //按钮大小
      title: "添加",
      buttonClose: "关 闭", //关闭按钮名字
      buttonDel: "删 除",
      buttonCancel: "取 消",
      buttonCommit: "提 交",
      buttonSure: "确 定",
      outsourceno_options: [], //外包商
      depData: [],
      forbidden: false,
      routerName: "",
      headers: { "Content-Type": "multipart/form-data" },
      issub_option: [
        {
          value: "是",
          label: "是",
        },
        {
          value: "否",
          label: "否",
        },
      ],
      isstation_option: [
        {
          value: "是",
          label: "是",
        },
        {
          value: "否",
          label: "否",
        },
      ],
    };
  },
  created() {
    this.deptId = this.$store.getters.deptId;
    this.getDepartDetail(this.deptId);
    this.getWbcom();
    this.getContactFile();
    if (this.$route.query.id) {
      this.title = "修改";
      this.id = this.$route.query.id;
      this.getDetail(this.$route.query.id);
    }
    // console.log(this.$route.params.name,'name')
    // if (/^\d+$/.test(this.$route.params.id)) {
    //   sessionStorage.setItem("id", this.$route.params.id);
    //   sessionStorage.setItem("name", this.$route.params.name);
    //   this.id = sessionStorage.getItem("id");
    //   this.routerName = sessionStorage.getItem("name")
    // } else {
    //   this.id = sessionStorage.getItem("id");
    //   this.routerName = sessionStorage.getItem("name")
    // }
  },
  methods: {
    /** change 行号 */
    bankChange(e) {
      this.formData.bankCode = e.target.value;
    },
    /** 查询外包商 */
    getWbcom() {
      getListAll()
        .then((res) => {
          this.outsourceno_options = res.data.results;
        })
        .catch((error) => {
          this.$message.error(error);
        });
    },
    // 获取部门
    getDepartDetail(id) {
      getTwoDepartDetail(id)
        .then((res) => {
          console.log(res, "res数据");
          if (res.data.status == "200") {
            var fild = res.data.results;
            let name = fild[0].location.split("-")[0];
            console.log(name, "99999");
            this.depData = fild.filter((tt) => {
              tt.location = tt.location.replace(name + "-", "");
              return tt;
            });
            // console.log(this.depData, "部门数据1212121");
          }
        })
        .catch((error) => {
          this.$message.error(error);
        }); // end
    },
    /** 获取关联附件 */
    getContactFile() {
      const data = {};
      queryZBYA().then((res) => {
        this.relatedfile_options = res.data.results;
      });
    },
    /** 获取附件详情 */
    goFileDetail() {
      let text = this.$router.resolve({
        path: "fileDetail",
        query: { id: this.formData.attachInfo },
      });
      window.open(text.href, "_blank");
    },
    /** 查询详情信息 */
    getDetail(id) {
      queryDetail(id)
        .then((res) => {
          this.formData = res.data.results;
          this.formData.taxRate = this.formData.taxRate * 100;
          let contractDoc = this.formData.contractDoc.split(",");
          console.log(this.formData.contractDoc, "合同文档");
          contractDoc.forEach((a) => {
            this.getFileName(a);
          });
        })
        .catch((error) => {});
    },
    /** 获取文件名称 */
    getFileName(id) {
      preview(id).then((res) => {
        if (res.data.status == 200) {
          this.fileList.push({
            docid: id,
            docname: res.data.results.docname,
            tag1: res.data.results.tag1,
            tag2: res.data.results.tag2,
          });
        }
      });
    },
    /** 提交*/
    onSubmit() {
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          const loading = common.loading(this);
          if (!this.id) {
            this.saveAccount();
          } else {
            updateAccount(this.formData).then((res) => {
              loading.close();
              if (res.data.status == "200") {
                this.$message.success("操作成功！");
                this.$router.push({ name: "accountManager" });
              } else {
                this.$message.error(res.data.message);
              }
            });
          }
        }
      });
    },
    /** 保存台账 */
    saveAccount() {
      const loading = common.loading(this);
      save(this.formData).then((res) => {
        loading.close();
        if (res.data.status == "200") {
          this.$message.success("操作成功！");
          this.$router.push({ name: "accountManager" });
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    /** 关闭按钮 **/
    close() {
      this.$router.push({ name: "accountManager" });
    },
    /** ntko插件预览 **/
    openNtkoWindow(obj, mark) {
      let formJudge = obj.docname.split(".")[obj.docname.split(".").length - 1];
      if (
        formJudge == "jpg" ||
        formJudge == "JPG" ||
        formJudge == "jpeg" ||
        formJudge == "JPEG" ||
        formJudge == "png" ||
        formJudge == "PNG"
      ) {
        this.ylDialog = true;
        let pos = obj.tag1.lastIndexOf("/");
        let str = obj.tag1.substr(0, pos);
        if (obj.tag2 != undefined) {
          this.ylPhotoUrl = str + "/" + obj.tag2;
        } else {
          this.ylPhotoUrl = obj.tag1;
        }
      } else if (
        formJudge == "zip" ||
        formJudge == "rar" ||
        formJudge == "txt"
      ) {
        this.$message.error("不能预览压缩文件或txt文件格式");
      } else {
        this.ntkoYl(obj.docid);
      }
    },
    // ntko预览方法
    ntkoYl(objectId) {
      var url = window.location.href;
      let ntkoed = ntkoBrowser.ExtensionInstalled();
      if (ntkoed) {
        ntkoBrowser.openWindow(
          url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
        );
      } else {
        window.open(
          url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
        );
      }
      // 注册回调方法
      window.ntkoCloseEvent = function () {
        alert("跨浏览器插件应用程序窗口已关闭!");
      };
    },
    /** change部门 */
    changeDept(value) {
      let obj = {};
      obj = this.depData.find((item) => {
        return item.depcode === value;
      });
      this.formData.deptName = obj.location;
    },
    /** change供应商 */
    changeVendor(value) {
      let obj = {};
      obj = this.outsourceno_options.find((item) => {
        return item.orgcode === value;
      });
      this.formData.vendorName = obj.outsourcname;
    },
  },
};
</script>
<style scoped>
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
  width: 180px;
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

.wrapper_right_bottom_1_zb {
  margin-right: 10px;
}
.content {
  width: 100%;
  font-size: 16px;
}

.content p {
  color: #262626;
  font-size: 14px;
  margin: 12px 0;
}

.content .row_2_detail {
  font-size: 16px;
  font-weight: 600;
}

.contentTwo_title {
  height: 60px;
  line-height: 60px;
  background-color: #ffffff;
  padding: 0 25px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.contentTwo_title_p {
  color: #262626;
  font-weight: 600;
}

.content .row_1 {
  background-color: #ffffff;
  /* padding: 0 20px 15px 20px; */
  padding: 0 20px;
}

.contentMain .row_2 {
  background-color: #ffffff;
  padding: 0 20px 15px 20px;
  margin-top: 15px;
}

.add_row {
  color: #faad14;
  font-size: 14px;
  font-weight: 500;
  margin-left: 10px;
  cursor: pointer;
}

/* 空格占位 */
.blank {
  width: 100%;
  height: 160px;
}
.bottom {
  padding: 15px 0;
  background-color: #fff;
}
.fknr {
  width: 100%;
}
.fknr .el-form-item__content {
  width: 93%;
}
.preview,
.download {
  color: #fba42f;
  font-size: 14px;
  margin-left: 5px;
  cursor: pointer;
}
/* 上传文件按钮 */
.againUpLoad .el-button--primary {
  color: #000000;
  background-color: #ffffff;
  border-color: rgba(0, 0, 0, 0.15);
}
.row_pxrz {
  background-color: #ffffff;
}
</style>
