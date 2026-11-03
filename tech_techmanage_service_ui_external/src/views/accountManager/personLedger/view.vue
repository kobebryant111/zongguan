<!-- 非科技外包人员详情信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">非科技外包人员详情</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common :buttonName="buttonClose" :size="buttonSize" :fns="close"></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>

    <!-- 非科技外包人员信息 -->
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
                    <p>系统编号 *</p>
                    <el-form-item prop="jobId">
                      <el-input v-model="formData.jobId" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>姓名 *</p>
                    <el-form-item prop="userName" ref="msgForm">
                      <el-input v-model="formData.userName" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>项目经理 *</p>
                    <el-form-item prop="proPersonName">
                      <el-input v-model="formData.proPersonName" disabled />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>所属部门 *</p>
                    <el-form-item prop="deptName">
                      <el-input v-model="formData.deptName" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <!-- <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>入职时间 *</p>
                    <el-form-item prop="startingTime">
                      <el-date-picker v-model="formData.startingTime" type="date" value-format="yyyy-MM-dd" start-placeholder="开始时间" end-placeholder="结束时间" disabled />
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>离职时间 *</p>
                    <el-form-item prop="endingTime">
                      <el-date-picker v-model="formData.endingTime" type="date" value-format="yyyy-MM-dd" start-placeholder="开始时间" end-placeholder="结束时间" disabled />
                    </el-form-item>
                  </div>
                </el-col>
              </el-row> -->
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>身份证 *</p>
                    <el-form-item prop="cardId">
                      <el-input placeholder="请输入身份证号" v-model="formData.cardId" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>手机号 *</p>
                    <el-form-item prop="telphone">
                      <el-input placeholder="手机号" v-model="formData.telphone" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <!-- <el-col :span="11">
                  <div>
                    <p>外包服务提供商 *</p>
                    <el-form-item prop="supplierId">
                      <el-input v-model="formData.supplierName" disabled />
                    </el-form-item>
                  </div>
                </el-col> -->
                <el-col :span="11">
                  <div>
                    <p>人员状态 *</p>
                    <el-form-item prop="state">
                      <el-select v-model="formData.state" placeholder="请选择" disabled>
                        <el-option v-for="item in personState_options" :key="item.value" :label="item.label" :value="item.value" disabled />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <p>人脸照片 * <span class="upload_pict">照片仅限jpg格式，大小不得大于200k</span></p>
                  <el-form-item prop="imageNo" :rules="[{ required: true, message: '请上传人脸照片'}]">
                      <el-upload class="upload-wbrz-picture" action="Fake Action" :file-list="imgLists"
                          :on-preview="photoPreview" :class="true" :show-file-list="true" list-type="picture-card" accept="image/jpeg" auto-upload disabled>
                      </el-upload>
                  </el-form-item>
                  <el-dialog :visible.sync="dialogVisible">
                      <el-image :src="dialogImageUrl" :preview-src-list="dialogImageUrl.split(',')"/>
                  </el-dialog>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <!-- <el-row>
        <div class="nav bottom">
          <div class="wrapper">
            <div class="wrapper_left">
              <el-col :span="1.5"></el-col>
            </div>
            <div class="wrapper_right">
              <div class="wrapper_right_bottom_1_zb">
                <el-col :span="1.5">
                  <button-common :buttonName="buttonCancel" :size="buttonSize" :fns="close" />
                </el-col>
              </div>
              <div>
                <el-col :span="1.5">
                  <button-orange :buttonName="buttonCommit" :size="buttonSize" :fns="onSubmit" />
                </el-col>
              </div>
            </div>
          </div>
        </div>
      </el-row> -->
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import { queryResourceDept, queryEmployeeTree } from "@/api/integrated/wbryzrlc";
import { queryDetail } from "@/api/accountManager/personLedger";
import { getFlowData, getListAll, preview } from "@/api/common/workflowApi";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import { toPoint } from "@/utils";
import { getJobid, getImageUrl, upload } from "@/api/integrated/newWbrz";
import Moment from "moment";
// 引入ntko
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "personLedgerView",
  components: { buttonOrange, buttonCommon },
  data() {
    return {
      id: null,
      deptId: null,
      formData: {
        jobId: null,
        userName: null,
        deptId: null,
        deptName: null,
        supplierId: null,
        supplierName: null,
        outsourceType: null,
        outsourceTypeName: null,
        cardId: null,
        startingTime: null,
        endingTime: null,
        state: null,
        proPerson: null,
        proPersonName: null,
        telphone: null,
        imageNo: null
      },
      managerData: {},
      deptmentData: {},
      buttonSize: "mini", //按钮大小
      title: "添加",

      buttonClose: "关 闭", //关闭按钮名字
      buttonDel: "删 除",
      buttonCancel: "取 消",
      buttonCommit: "提 交",
      buttonSure: "确 定",
      depData: [],
      forbidden: false,
      routerName: "",
      imgLists: [], //照片上传列表
      dialogImageUrl: '',
      dialogVisible: false,
      headers: { "Content-Type": "multipart/form-data" },
      personState_options: [
      	{
          value: "0",
          label: "在岗",
        },
        {
          value: "1",
          label: "离岗",
        },
      ],
    };
  },
  created() {
    console.log(this.$route.params,"-----params")
    this.filDep();
    this.getDetail(this.$route.params.id);
    console.log(this.$route.params.id,"-----id")
  },
  methods: {
    // 图片预览
    photoPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 获取图片地址
    getPhotoUrl(id) {
      getImageUrl({ documentno: id }).then(res => {
        if (res.data.status == 200) {
            this.imgLists.push({ url: res.data.results.documentno });
            this.imgLists.splice(0, 0);
        } else {
            this.$message.error('获取图片地址失败');
        }
      })
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
    filDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then((res) => {
          var fild = res.data.results[0].children;
          let name = res.data.results[0].text;
          this.depData = fild.filter((tt) => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
          console.log(this.depData, "部门数据1212121");
        })
        .catch((error) => {
          this.$message.error(error);
        }); // end
    },
    /** 查询详情信息 */
    getDetail(id) {
      queryDetail({id: id}).then((res) => {
        this.formData = res.data.results;
          if (res.data.results.imageNo != undefined) {
              this.getPhotoUrl(res.data.results.imageNo);
          } 
        
      }).catch((error) => {});
    },
    /** 关闭按钮 **/
    close() {
      this.$router.push({ name: "personindex" });
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
  },
};
</script>
<style scoped>
.upload_pict {
  font-size: 14px;
  color: rgba(0, 0, 0, 0.45);
  font-weight: 400;
}

/* 上传照片样式修改 */
.upload-wbrz-picture {
    display: flex;
    flex-direction: row;
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

/* 照片样式修改 */
.contentMain .el-icon-plus {
    color: rgba(0, 0, 0, 0.45);
}

.contentMain .el-upload--picture-card {
    display: flex;
    align-items: center;
    justify-content: center;
}

.contentMain .el-upload:focus {
        border-color: #faad14;
        color: #faad14;
}
.upload_i {
    font-size: 14px;
    margin-top: 15px;
}

.contentMain .el-icon-upload2 {
    margin-right: 10px;
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

/* 上传图片后隐藏删除按钮 */
.upload-wbrz-picture .el-upload-list__item-actions:hover .el-upload-list__item-delete {
    display: none;
}
</style>