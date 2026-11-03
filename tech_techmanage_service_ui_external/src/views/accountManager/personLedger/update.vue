<!-- 修改非科技外包人员信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">修改非科技外包人员信息</p>
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
                    <el-form-item prop="jobId" >
                      <el-input v-model="formData.jobId" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>姓名 *</p>
                    <el-form-item prop="userName" :rules="[ {required: true, message: '请填写姓名信息', trigger: 'blur'}]" ref="msgForm">
                      <el-input placeholder="请填写姓名信息" v-model="formData.userName"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>项目经理 *</p>
                    <el-form-item prop="proPersonName" :rules="[{ required: true, message: '请选择项目经理', trigger: 'change'}]">
                      <el-input v-model="formData.proPersonName" placeholder="请选择项目经理"  suffix-icon="el-icon-search" readonly @focus="showDep"></el-input>
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
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>身份证 *</p>
                    <el-form-item prop="cardId" :rules="[{ required: true, message: '请输入身份证号', trigger: 'change'}]">
                      <el-input placeholder="请输入身份证号" v-model="formData.cardId" ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>手机号 *</p>
                    <el-form-item prop="telphone" :rules="[{ required: true, message: '请输入手机号', trigger: 'change'}]">
                      <el-input placeholder="手机号" v-model="formData.telphone"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <!-- <el-col :span="11">
                  <div>
                    <p>外包服务提供商 *</p>
                    <el-form-item prop="supplierId" :rules="[ { required: true, message: '请选择供应商', trigger: 'change'}]">
                      <el-select v-model="formData.supplierId" placeholder="请选择供应商" :disabled="forbidden" value-key="label" >
                        <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname" :value="item.orgcode" />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col> -->
                <el-col :span="11">
                  <div>
                    <p>人员状态 *</p>
                    <el-form-item prop="state" :rules="[{ required: true, message: '请选择', trigger: 'change'}]">
                      <el-select placeholder="请选择" v-model="formData.state" >
                        <el-option v-for="item in personState_options" :key="item.value" :label="item.label" :value="item.value" />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <p>人脸照片 * <span class="upload_pict">照片仅限jpg格式，大小不得大于200k</span></p>
                  <el-form-item prop="imageNo" :rules="[{ required: true, message: '请上传人脸照片'}]">
                      <el-upload class="upload-wbrz-picture" action="Fake Action" :file-list="imgLists"
                          :http-request="changePhoto" :on-preview="photoPreview" :before-upload="beforeUpload"
                          :show-file-list="true" list-type="picture-card" :on-success="succPhoto" :on-remove="removePhoto"
                          :on-error="errPhoto" accept="image/jpeg,image/png">
                          <i class="el-icon-plus">
                              <div class="upload_i">上传照片</div>
                          </i>
                      </el-upload>
                  </el-form-item>
                  <el-dialog :visible.sync="dialogImageVisible">
                      <el-image :src="dialogImageUrl" :preview-src-list="dialogImageUrl.split(',')"/>
                  </el-dialog>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>

    <!-- 弹框 -->
    <el-dialog title="提示" :visible.sync="dialogVisible" width="50%" :before-close="handleClose">
      <div><!-- 表单 -->
        <el-form inline ref="deptForm" :model="deptForm">
          <el-form-item label="员工工号" prop="badge">
            <el-input v-model="deptForm.badge" placeholder="员工工号"></el-input>
          </el-form-item>
          <el-form-item label="员工姓名" prop="empname">
            <el-input v-model="deptForm.empname" placeholder="员工姓名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="submitSearchBtn">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="dept-table-container"><!-- 树 -->
        <el-table :data="employeeList" fit border height="500" highlight-current-row @current-change="handleTable" :row-style="rowClass" :row-class-name="tableRowClassName">
          <el-table-column label="项目经理编号" prop="badge" width="70"></el-table-column>
          <el-table-column label="项目经理名称" prop="empname" width="70"></el-table-column>
          <!-- <el-table-column label="所属部门编号" prop="depid" width="70"></el-table-column> -->
          <el-table-column label="所属部门名称" prop="chargeName" width="260"></el-table-column>
        </el-table>
        <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" />
      </div>
      <span slot="footer" class="dialog-footer"><!-- 底部 -->
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="determine">确 定</el-button>
      </span>
    </el-dialog>
    <el-row>
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
    </el-row>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import { getTwoDepartDetail } from "@/api/common/flowApi";
import { save, queryDetail,updateAccount } from "@/api/accountManager/personLedger";
import { getListAll } from "@/api/common/workflowApi";
import { queryPagerForEmployee } from "@/api/dataCenter/dataCenAccessPermission";
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
  name: "personLedgerUpdate",
  components: { buttonOrange, buttonCommon, pager },
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
        isChangeState: false,
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
      outsourceno_options: [], //外包商
      depData: [],
      forbidden: false,
      routerName: "",
      dialogVisible: false,
      selectEmployee: {}, // table当前选中行员info
      deptForm: {
        badge: "", // 业务经理工号
        empname: "", // 业务经理名称
        depid: "",
        chargeName: ""
      },
      employeeList: [], // 行员列表
      totalNumber: 0,
      size: 10,
      index: 1,
      imgLists: [], //照片上传列表
      dialogImageUrl: '',
      dialogImageVisible: false,
      headers: { "Content-Type": "multipart/form-data" },
      isChangeState: '',
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
    console.log(this.$route.params.id,"----------------id")
    // this.getDepartDetail(this.deptId);
    // this.getWbcom();
    // this.getJob();
    this.loadInit();
    this.id = this.$route.params.id;
    this.getDetail(this.$route.params.id);
  },
  watch: {
    formData: function () {
      this.$nextTick(function () {
        this.initStateEvent();
      })
    }
  },
  methods: {
    initStateEvent(){
      this.isChangeState = this.formData.state;
    },
    // 图片文件状态改变
    changePhoto(file, fileList) {
      var formData = new FormData();
      formData.append("file", file.file);
      formData.append("type", "AI");
      formData.append("catalogNo", "CATALOG2014091500001");
      let data = formData;
      upload(data).then(res => {
        if (res.data.status == "200") {
          this.$set(this.formData, 'imageNo', res.data.results);
          this.$nextTick(() => {
              this.$refs['formData'].validateField('imageNo');
          });
          this.$message.success('上传成功');
          file.onSuccess();
        } else {
          this.$message.error('上传失败');
        }
      });
    },
    // 图片预览
    photoPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogImageVisible = true;
    },
    // removePhoto
    removePhoto(file, fileList) {
      if (fileList.length === 0) {
        this.formData.imageNo = '';
      }
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
    // 类型限制
    beforeUpload(file){
      let isJPG = file.type === 'image/jpeg';
      // let isPNG = file.type === 'image/png';
      let isLt200K = file.size / 1024 < 200;
      if(!isJPG){
        this.$message.error('人脸照片只能上传jpg格式图片！')
        return false;
      }
      if(!isLt200K) {
        this.$message.error('照片大小不得大于200k!')
        return false;
      }
      return isJPG && isLt200K
    },
    // 图片上传失败
    errPhoto(res, file, fileList) {
    },
    // 图片上传成功
    succPhoto(res, file, fileList) {
      this.imgLists = [fileList[fileList.length - 1]];
    },
    // 显示弹框
    showDep() {
      // console.log('showDep');
      this.dialogVisible = true;
    },
    // table 选中触发
    handleTable(row) {
      // console.log('row', row);
      this.selectIndex = row.deptId;
      this.selectEmployee = row;
    },
    // 确定
    determine() {
      if(Object.keys(this.selectEmployee).length == 0) return this.$message.error('请选择项目经理')
      this.formData.proPersonName = this.selectEmployee.empname;
      this.formData.proPerson = this.selectEmployee.badge;
      this.formData.deptId = this.selectEmployee.depid;
      this.formData.deptName = this.selectEmployee.chargeName;
      this.dialogVisible = false;
      // this.$refs.busiBadgeNameInput.bulr()
    },
    // 搜索
    submitSearchBtn() {
      let params = this.deptForm;
      params.empstatus = "Norm"
      this.getEmployeeInfo(params);
    },
    // 当前选中行的样式
    rowClass: function({ row, index }) {
      if (this.selectIndex === row.depid) {
        return { "background-color": "rgba(185,221,249,0.75)" };
      }
    },
    // 把每一行的索引放进row
    tableRowClassName: function({ row, rowIndex }) {
      row.index = rowIndex;
    },
    //分页初始化方法
    loadInit: function () {
      let params = {};
      params.size = this.size;
      params.index = this.index;
      params.empstatus = "Norm"
      this.getEmployeeInfo(params);
    },
    // 分页触发
    getPager: function(size, index) {
      let params = {};
      params.size = size;
      params.index = index;
      this.size = size;
      this.index = index;
      params.empstatus = "Norm"
      this.getEmployeeInfo(params);
    },
    // 获取行员信息
    getEmployeeInfo(params) {
      let data = common.paramsFormat(params);
      queryPagerForEmployee(data).then(res => {
        // console.log('获取行员信息', res);
        if(res.data.status === 200) {
          this.employeeList = res.data.results;
          this.totalNumber = res.data.total;
          if(this.totalNumber <= 0) {
            return false
          }
        } else {
          this.$message.error(res.data.message);
        }
      })
    },
    // 关闭弹框
    handleClose() {
      this.dialogVisible = false;
    },
    // 获取工号
    getJob() {
      getJobid().then(res => {
        if (res.data.status == 200) {
          this.formData.jobId = res.data.results.jobId;
        } else {
          this.$message.error("获取工号失败");
        }
      }).catch(error => {
        this.$message.error(error);
      });
    },
    /** 查询外包商 */
    getWbcom() {
      getListAll().then((res) => {
          this.outsourceno_options = res.data.results;
        }).catch((error) => {
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
    /** 查询详情信息 */
    getDetail(id) {
      queryDetail({id: id}).then((res) => {
        this.formData = res.data.results;
          if (res.data.results.imageNo != undefined) {
              this.getPhotoUrl(res.data.results.imageNo);
          }

        }).catch((error) => {});
    },
    /** 提交*/
    onSubmit() {
      if (this.isChangeState != this.formData.state && this.formData.state === '1') {
        this.$confirm('人员状态调整为离岗后将无法进行人脸打卡, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.formData.isChangeState = true;
          this.onSubmitExt();
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          });          
        });
      }else{
        this.formData.isChangeState = false;
        this.onSubmitExt();
      }
    },
    onSubmitExt(){
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          const loading = common.loading(this);
          if (!this.id) {
            this.saveAccount();
          } else {
            updateAccount(this.formData).then((res) => {
              if (res.data.status == "200") {
                this.$message.success("操作成功！");
                this.$router.push({ name: "personindex" });
                loading.close();
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
          this.$router.push({ name: "personindex" });
        } else {
          this.$message.error(res.data.message);
        }
      });
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
    }
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
