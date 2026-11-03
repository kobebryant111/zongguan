<!--草稿-->
<template>
  <div>
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="3">
              <p class="nav_name">分行应用变更</p>
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
    
    <detail ref="detail" :changeTypeRead="false" :draft="true"  :ifOperaAuth="true" :ifDelFile="true">
        <template slot="uploadSlot">
            <!-- 附件 -->
    <el-card style="padding: '10px', margin:'10px'">
      <div slot="header">
        <span>附件</span>
      </div>
      <el-form :model="fileForm" ref="fileForm" :rules="fileRules" label-width="150px">
        <el-row v-if="changetype == '03'">
          <el-col :span="24">
            <el-form-item label="附件">
              <el-upload
                ref="elupload"
                action="Fake Action"
                :http-request="preUload"
                multiple
                :limit="1"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="changetype == '02'">
          <el-col :span="24">
            <el-form-item label="附件">
              <el-upload
                ref="elupload"
                action="Fake Action"
                :http-request="preUload3"
                multiple
                :limit="1"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="changetype == '01'">
          <el-col :span="24">
            <el-form-item label prop="file2">
              <template slot="label">变更方案
                <el-link type="primary" @click="uploadChangeTemp">模板</el-link>
              </template>
              <el-upload
                ref="preUload2"
                action="Fake Action"
                :http-request="preUload2"
                multiple
                :limit="1"
                v-model="fileForm.file2"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="changetype == '01'">
          <el-col :span="24">
            <el-form-item prop="file21">
              <template slot="label">变更checklist
                <el-link type="primary" @click="uploadChangeListTemp">模板</el-link>
              </template>
              <el-upload
                ref="preUload21"
                action="Fake Action"
                :http-request="preUload21"
                multiple
                :limit="1"
                v-model="fileForm.file21"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <!-- 附件 end -->
        </template>
      <template slot="logSlot">
        <!-- 操作日志 -->
        <div class="app-container">
          <flow-logs ref="log"></flow-logs>
          <div class="app-container">
            <div>&nbsp;</div>
            <div>&nbsp;</div>
          </div>
        </div>
      </template>
      <template slot="buttonSlot">
        <!-- 按钮 -->
        <div class="nav bottom">
          <el-row v-if="isDb">
            <el-col :span="1">&nbsp;</el-col>

            <el-col :span="18">
              <button-delete :buttonName="delText" :size="sizes" :fns="delBtn" style="float: left;"></button-delete>
            </el-col>
            <el-col :span="2">
              <button-common :buttonName="draftText" :size="sizes" :fns="draftBtn"></button-common>
            </el-col>
            <el-col :span="3">
              <buttonOrange :buttonName="btnName" :size="sizes" :fns="submitBtn"></buttonOrange>
            </el-col>
            <el-col :span="1">&nbsp;</el-col>
          </el-row>
          <yb-revoke :flowItem="bps" v-else></yb-revoke>
          
        </div>
      </template>
    </detail>
  </div>
</template>
<script>
import { getFhChangeDateil, banchDeve, preUpload,draftBtn,liuzhuan  } from "@/api/branchIntentionApi/yyxmbg";
import Detail from "@/views/BanchProjectChange/common/Detail";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import buttonDelete from "@/components/eleButton/delete";
import { doDel } from '@/api/common/flowApi'
import common from "@/api/common";
export default {
  name: "pagerTwo",
  components: {
    Detail,
    ybRevoke,
    buttonOrange,
    buttonCommon,
    buttonDelete,
    flowLogs
  },
  data() {
    return {
      bps: {}, // bps 对象
      delText: "删 除",
      draftText: "草 稿",
      btnName: "提交",
      sizes: "mini",
      documentno1: "", // 数据维护-文件上传
      documentno2: "", // 项目更新-文件上传
      documentno21: "", // 项目更新-文件上传
      documentno3: "", // 补丁更新-文件上传
      changetype : '',  // 变更类型
      draftDateil : {},   // 主体数据
      fileForm: {
        // 文件上传表单
        file2 : '',
        file21 : ''
      },
      isDb: true,  // 是否待办 默认 true
      fileRules:{
        file2 :[{ required: true, message: "请选择", trigger: "change" }],
        file21 :[{ required: true, message: "请选择", trigger: "change" }]
      },
    };
  },
 
  methods: {
     init(item) {
      const self = this;
      self.bps = item;
      let param = {};
      param.instanceid = item.processInstID;
      if (item.status !== "db") {
        self.isDb = false;
      }
       self.$nextTick(_ => {
        self.$refs.detail.ifRead = false;
        self.$refs.log.init(self.bps.processInstID);
        
        getFhChangeDateil(param).then(res => {
          let res_itenm = res.data.results;
          self.draftDateil = res_itenm;
          self.$refs.detail.form = res_itenm;
          self.$refs.detail.form1 = res_itenm;
          self.$refs.detail.form5 = res_itenm;
          self.$refs.detail.lData = res_itenm.systemList;
          let list = res_itenm.pwdList.filter(ii => {
              ii.userid = ii.username
              ii.systemid = ii.systemname
                return ii
          });
          self.$refs.detail.mgData = list;
          self.$refs.detail.docData = res_itenm.docList;
          console.log('res_itenm',res_itenm)
          self.changetype = res_itenm.changetype
          let ispad = res_itenm.istopwd
          self.$refs.detail.sysName = res_itenm.systemList[0].sysName
          if("是" === ispad) {
            self.$refs.detail.mgTableShow = true
          } else {
              self.$refs.detail.mgTableShow = false
          }
          //   变更类型是否==03
          if(res_itenm.changetype !== '03') {
              self.$refs.detail.sjwhShow = false
          }
          
        });
      });
    },
    /**
     * 数据维护-文件上传
     */
    preUload(file, fileList) {
      var formData = new FormData();
      formData.append("file", file.file);
      // formData.append("objectno", this.data.demandno);
      formData.append("objecttype", "SJWH");
      let data = formData;
      preUpload(data).then(res => {
        console.log("文件上传", res.data);
        if (res.data.status == "200") {
          this.documentno1 = res.data.results;
          this.$message.success("上传成功");
        } else {
          this.$message.error("上传失败");
        }
      });
      return;
    },

    /**
     * 项目更新-文件上传
     */
    preUload2(file, fileList) {
      let formData = new FormData();
      let self = this 
      if(file.file.name.indexOf('变更方案') <= -1) {
        self.$refs.preUload2.clearFiles()
        self.$message.error('文件名称必须带有[变更方案]')
        return false;
      }
      formData.append("file", file.file);
      // formData.append("objectno", this.data.demandno);
      formData.append("objecttype", "XMGX");
      let data = formData;
      preUpload(data).then(res => {
        console.log("文件上传", res.data);
        if (res.data.status == "200") {
          self.documentno2 = res.data.results;
          self.fileForm.file2 = res.data.results
          self.$message.success("上传成功");
        } else {
          self.$message.error("上传失败");
        }
      });
      return;
    },
    /**
     * 项目更新-文件上传
     */
    preUload21(file, fileList) {
      let self = this
      var formData = new FormData();
      if(file.file.name.indexOf('变更checklist') <= -1) {
        self.$refs.preUload21.clearFiles()
        self.$message.error('文件名称必须带有[变更checklist]')
        return false;
      }
      formData.append("file", file.file);
      // formData.append("objectno", this.data.demandno);
      formData.append("objecttype", "XMGX");
      let data = formData;
      preUpload(data).then(res => {
        console.log("文件上传", res.data);
        if (res.data.status == "200") {
          self.documentno21 = res.data.results;
          self.fileForm.file21 = res.data.results
          tselfhis.$message.success("上传成功");
        } else {
          self.$message.error("上传失败");
        }
      });
      return;
    },
    /**
     * 补丁更新-文件上传
     */
    preUload3(file, fileList) {
      var formData = new FormData();
      formData.append("file", file.file);
      // formData.append("objectno", this.data.demandno);
      formData.append("objecttype", "BDGX");
      let data = formData;
      preUpload(data).then(res => {
        console.log("文件上传", res.data);
        if (res.data.status == "200") {
          this.documentno3 = res.data.results;
          this.$message.success("上传成功");
        } else {
          this.$message.error("上传失败");
        }
      });
      return;
    },
    /**
     *  关闭当前路由
     */
    closeRouter() {
      this.$router.push({ name: "dashboard" });
    },
    /**
     * 草稿
     */
    draftBtn() {
      let self = this;
      let params = Object.assign(self.$refs.detail.form,self.$refs.detail.form1, self.$refs.detail.form5);
      let fujian = [];
      if (self.documentno1) {
        fujian.push(self.documentno1);
      }
      if (self.documentno2) {
        fujian.push(self.documentno2);
      }
      if (self.documentno21) {
        fujian.push(self.documentno21);
      }
      if (self.documentno3) {
        fujian.push(self.documentno3);
      }
      let files = fujian.join(",");
      params.files = files;
      // 项目更新
      if (params.changetype == "01") {
        params.patchtitle = params.projectno;
        // 补丁更新
      } else if (params.changetype == "02") {
        params.patchtitle = params.patchtitle;
      } else {
        // 03 数据维护
        params.patchtitle = params.applyChangeTitle;
      }
      params.bps = self.bps
      params.pwdList = self.$refs.detail.mgData
      console.log("表单：", params);
      
      draftBtn(params).then(res => {
        let message = common.getFlowMessge(res.data.message);
        let messageArr = message.split(",");
        self.$nextTick(() => {
          common.messageNotify(messageArr, self);
        });
      });
    },
    /**
     * 提交流程
     */
    submitBtn() {
      // 验证表单
      let status = new Promise(() => {});
      let self = this;
      console.log("表单", self.form);
      const f1 = new Promise((resolve, reject) => {
        self.$refs.detail.$refs.form.validate(vaild => {
          if (vaild) {
            resolve();
          }
        });
      });
      const f2 = new Promise((resolve, reject) => { 
        if(self.draftDateil.changetype === '01') {
         self.$refs.fileForm.validate(vaild => {
            if (vaild) {
              resolve();
            }
          });
        } else {
          resolve();
        }
        
      });
      const f3 = new Promise((resolve, reject) => {
        if (self.draftDateil.changetype !== '03') {
          self.$refs.detail.$refs.form1.validate(vaild => {
            if (vaild) {
              resolve();
            }
          });
        } else {
          resolve();
        }
      });
      const f5 = new Promise((resolve, reject) => {
         self.$refs.detail.$refs.form5.validate(vaild => {
            if (vaild) {
              resolve();
            }
          });
      });

      Promise.all([f1, f2, f3,f5]).then(res => { 
        if(self.draftDateil.changetype !== '03' && self.draftDateil.istopwd == '是') {
          if(!self.$refs.detail.mgData || self.$refs.detail.mgData.length <= 0) {
            self.$message.warning('系统设备不能为空')
            return false
          }
        }
        self.liuzhuan();
      });
    },
    /**
     * 流转
     */
    liuzhuan() {
      let self = this;
      let params = Object.assign(self.$refs.detail.form, self.$refs.detail.form1);
      let fujian = [];
      
      if (self.documentno1) {
        fujian.push(self.documentno1);
      }
      if (self.documentno2) {
        fujian.push(self.documentno2);
      }
      if (self.documentno21) {
        fujian.push(self.documentno21);
      }
      if (self.documentno3) {
        fujian.push(self.documentno3);
      }
      let files = fujian.join(",");
      params.files = files;
      // 项目更新
      if (params.changetype == "01") {
        params.patchtitle = params.projectno;
        // 补丁更新
      } else if (params.changetype == "02") {
        params.patchtitle = params.patchtitle;
      } else {
        // 03 数据维护
        params.patchtitle = params.applyChangeTitle;
      }
      params.pwdList = self.$refs.detail.mgData
      params.bps = self.bps
      console.log("表单：", params);
      // 发起流程
      liuzhuan(params).then(res => {
        console.log("流转回调", res.data);
        let message = common.getFlowMessge(res.data.message);
        let messageArr = message.split(",");
        self.$nextTick(() => {
          common.messageNotify(messageArr, self);
          self.$router.push({ name: "dashboard" });
        });
      });
    },
    /**
     * 删除流程
     */
    delBtn() {
      this.$confirm("确认删除该流程吗?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.delPro();
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    /**
     * 删除流程
     */
    delPro() {
      let params = {};
      let self = this;
      params.processInstID = self.bps.processInstID;
      params.workItemID = self.bps.workItemID;
      params.processDefName = self.bps.processDefName;
      let data = common.paramsFormat(params);
      doDel(data).then(res => {
        if (res.data.status == 200) {
          self.$message.success("流程删除成功");
          self.$router.push({ name: 'dashboard' });
        } else {
          self.$message.error("流程删除失败");
        }
      });
    },
    /**
     * 模板 变更方案
     */
    uploadChangeTemp(){
      let self =this
      let docId = "FH_YYBG_BGFA_TEMPLER"
      getDownloadUrl({ key: docId }).then(res => {
          // loading.close();
          if (res.data.status == 200) {
              common.downloadJl(res.data.results.documentno, self);
          } else {
              this.$message.error("获取下载地址失败");
          }
      })
    },
    /**
     * 模板 变更方案list
     */
    uploadChangeListTemp() {
      let self =this
      let docId = "FH_YYBG_CHECKLIST_TEMPLER"
      getDownloadUrl({ key: docId }).then(res => {
          // loading.close();
          if (res.data.status == 200) {
              common.downloadJl(res.data.results.documentno, self);
          } else {
              this.$message.error("获取下载地址失败");
          }
      })
    },
  } // end
};
</script>
<style scoped>
.fu-title {
  margin-left: 10px;
}
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

.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  z-index: 999;
}

.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
}

.wrapper-right-keep {
  margin-right: 10px;
}
.bottom {
  margin-top: 20px;
  padding: 15px 0;
}
</style>

