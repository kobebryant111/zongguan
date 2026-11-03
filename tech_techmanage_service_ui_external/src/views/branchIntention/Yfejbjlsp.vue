<!-- 研发二机部经理审核 -->
<template>
  <div class="global-container">
    <detail ref="detail" :disabled-prop="false" :techFns="budgetWorkload">
      <template slot="ysclSlot">
        <el-row>
          <el-col :span="24">
            <el-form-item label="预审材料" prop="pretMaterial">
              <div class="upload-file">
                <el-upload
                  ref="elupload"
                  action="Fake Action"
                  :http-request="preUload"
                  multiple
                  :limit="1"
                  v-model="pretMaterial"
                  :on-remove="fileRevmoe"
                >
                  <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
                <el-link :underline="false" type="primary" @click="downloadTemp">模板下载</el-link>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </template>
      <template slot="kjzjlSlot">
        <div class="app-container">
          <el-form
            :model="fhkjzjlForm"
            ref="fhkjzjlForm"
            label-width="200px"
            label-position="left"
            :rules="fhkjzjlRules"
          >
            <el-row>
              <el-col :span="24">
                <el-form-item label="分行科技总经理" prop="fhKjzjl">
                  <el-cascader
                    ref="cascader"
                    :options="requirePeo_options"
                    :props="msgPeoParams"
                    v-model="fhkjzjlForm.fhKjzjl"
                    :show-all-levels="false"
                    filterable
                    @change="peoChange"
                    collapse-tags
                  ></el-cascader>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </template>
    </detail>
    <div class="app-container"></div>
    <div class="nav-bottom">
      <approve-bottom :proving="proving" :flowItem="bps"></approve-bottom>
    </div>
  </div>
</template>
<script>
import {
  getDateil,
  preliminaryReview,
  budgetWorkload,
  preUpload,
  getBranchGeneralManager,
  revmoeDocument
} from "@/api/branchIntentionApi/fhyx";
import common from "@/api/common";
import { getDownloadUrl } from '@/api/common/flowApi';
import { queryEmployeeByOrgidTree } from "@/api/integrated/wbryzrlc";
import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import buttonDelete from "@/components/eleButton/delete";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
import detail from "@/views/branchIntention/comm/Detail";
export default {
  components: {
    approveBottom,
    detail
  },
  data() {
    return {
      bps: {},
      formData: {
        fhKjzjl: "" // 分行科技总经理
      }, // 表单所需提交内容
      pretMaterial: "", // 预审材料
      buttonDel: "删 除", // 按钮值
      buttonKeep: "存草稿", // 按钮值
      buttonCommit: "提交", // 按钮值
      requirePeo_options: [], // 部门人员树集合
      delBtnIf: true, // 是否显示删除按钮 默认不显示
      pretMaterialFlag: true, // 附件上传是否为必填项
      msgPeoParams: {
        //需求发起人配置信息
        label: "text",
        value: "id",
        children: "children",
        emitPath: false,
        multiple: false
        // leaf: 'id'
      },

      fhkjzjlForm: {
        fhKjzjl: "" //分行科技总经理
      },
      fhkjzjlRules: {
        fhKjzjl: [
          { required: true, message: "请选择科技部总经理", trigger: "change" }
        ]
      }
    };
  },
  methods: {
    init(params) {
      let self = this;
      let param = {};
      self.bps = params;
      param.instanceid = params.processInstID;
      getDateil(param).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.messge, { type: "error" });
          return false;
        }
        self.data = res.data.results;
        // 判断预审材料
        let files = self.data.documentList.filter(ii => {return ii.objecttype ==='A11'})
        if(!files || files.length <= 0) {
          self.$refs.detail.pretMaterialFlag = true;
        }
        // 默认分行科技部经理
        getBranchGeneralManager({depno:self.data.demanddeptno}).then(res=>{
          console.log("默认分行科技部经理", res.data)
          self.fhkjzjlForm.fhKjzjl = 'emp' + res.data.results.badge
        })
        // 查询行员
        self.searchEpmlo(self.data);
        self.$refs.detail.showDateil(self.data, params);
      });
    },
    /**
    
    /**
     * 分行科技总经理
     */
    peoChange(node) {
      console.log("node::", node);
      //   this.msgDataChange = true;
      if (node.length != 0) {
        let msgNodes = this.$refs["cascader"].getCheckedNodes();
        if (msgNodes.length <= 0) {
          this.ywfghz = node;
        } else {
          let msgids = [];
          let magNames = [];
          if (msgNodes.length != 0) {
            msgids = msgNodes.map(value => {
              return value.data.pcode;
            });
            magNames = msgNodes.map(value => {
              return value.data.text;
            });
          }
          this.ywfghz = msgids.join(",");
        }
      }
    }, // end
    /**
     * 提交
     */
    proving(data) {
      let self = this;
      let status = new Promise(() => {});
      if (data.conclusion === "01") {
        // 验证表单
        const f1 = new Promise((resolve, reject) => {
          self.$refs.detail.$refs.ysForm.validate(vaild => {
            if (vaild) {
              resolve();
            }
          });
        });

        const f3 = new Promise((resolve, reject) => {
          self.$refs["fhkjzjlForm"].validate(vaild => {
            if (vaild) {
              resolve();
            }
          });
        });
        if(self.$refs.detail.pretMaterialFlag) {
          self.$message.warning('请上传预审文件')
          return false
        }

        let pval = async () => {
          return await Promise.all([f1, f3]).then(res => {
            return self.liuzhuan(data).then(val => {
              return val;
            });
          });
        };
        status = pval().then(res => {
          return res;
        });
      } else {
        status = self.liuzhuan(data).then(val => {
          return val;
        });
      }
      return status;
    },
    /**
     * 流转
     */
    liuzhuan(data) {
      const loading = common.loading(this);
      // self.otherForm
      let self = this;
      let params = {};
      let puls = self.$refs.detail;
      // 科技团队工作量
      params.tecworkload = puls.ysForm.tecworkload;
      params.tworkunit = puls.ysForm.tworkunit;
      // 业务团队工作量
      params.busiworkload = puls.ysForm.busiworkload;
      params.sworkunit = puls.ysForm.sworkunit;
      // 业务人力预算
      params.bbudget = puls.ysForm.bbudget;
      // 科技人力预算
      params.tbudget = puls.ysForm.tbudget;
      // 总工作量
      params.sumworkload = puls.ysForm.sumworkload;
      params.sumworkunit = puls.ysForm.sumworkunit;
      // 规模
      params.proscale = puls.ysForm.proscale;
      // 是否涉及外包采购需求
      params.issjwbcgxq = puls.otherForm.issjwbcgxq;
      // 是否涉及包含电子印章
      params.issjbhdzyz = puls.otherForm.issjbhdzyz;
      // 是否可行性分析
      params.isresearch = puls.otherForm.isresearch;

      // 分行科技总经理
      params.fhKjzjl = self.fhkjzjlForm.fhKjzjl.replace("emp", "");
      // 预审材料
      params.pretMaterial = self.pretMaterial
      self.bps.conclusion = data.conclusion;
      self.bps.suggest = data.suggest;
      params.bps = self.bps;

      console.log("self.data", params);
      return preliminaryReview(params)
        .then(res => {
            loading.close()
          console.log("res.data:::", res.data);
          return res.data;
        })
        .catch(error => {
          self.$message.error(error);
        });
    },
    /**
     * 计算预算信息工作量
     */
    budgetWorkload() {
      let self = this;
      let params = {};
      let yf = self.$refs.detail;
      params.sworkunit = yf.ysForm.sworkunit;
      params.tworkunit = yf.ysForm.tworkunit;
      params.tecworkload = yf.ysForm.tecworkload;
      params.busiworkload = yf.ysForm.busiworkload;
      console.log("self.ysForm", params);
      if(!/^[1-9][0-9]{0,10}$/.test(yf.ysForm.tecworkload)) {
         return false
      }
      budgetWorkload(params).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }

        // 科技人力预算
        yf.ysForm.tbudget = res.data.results.tbudget
        // 总工作量
        yf.ysForm.sumworkload = res.data.results.sumworkload;
        // 总工作量 单位
        yf.ysForm.sumworkunit = res.data.results.sumworkunit;
        // 业务人力预算
        yf.ysForm.bbudget = res.data.results.bbudget;
        // 规模
        if('人月' === yf.ysForm.sumworkunit) {
          yf.ysForm.proscale = '04'
        } else {
          yf.ysForm.proscale = '05'
        }
      });
    }, // end
    /**
     * 文件上传
     */
    preUload(file, fileList) {
      var formData = new FormData();
      formData.append("file", file.file);
      formData.append("objectno", this.data.demandno);
      let data = formData;
      preUpload(data).then(res => {
        if (res.data.status == "200") {
          this.$message.success("上传成功");
          this.pretMaterial = res.data.results
          this.$refs.detail.pretMaterialFlag = false;
        } else {
          this.$message.error("上传失败");
        }
      });
      return;
    },
    /**
     * 预审模板下载
     */
    downloadTemp() {
      let self =this
      let docId = "FH_YUSHEN_DOC"
      getDownloadUrl({ key: 'FH_YUSHEN_DOC' }).then(res => {
          // loading.close();
          if (res.data.status == 200) {
              const self = this;
              common.downloadJl(res.data.results.documentno, self);
          } else {
              this.$message.error("获取下载地址失败");
          }
      })
    },
    /**
     * 删除文件
     */
    fileRevmoe(file,files){
      let self = this
      var formData = new FormData();
      formData.append("documentno", this.pretMaterial);
      formData.append("isdel", '1');
      console.log('formData',this.pretMaterial)
      revmoeDocument(formData).then(res => {
        console.log('删除文件',res.data)
        self.$refs.elupload.clearFiles()
        self.$refs.detail.pretMaterialFlag = true;
      })
    },
    /**
     * 查询行员
     */
    searchEpmlo(data) {
      let params = data.orgId;
      queryEmployeeByOrgidTree(params)
        .then(res => {
          this.organizers_options = res.data.results;
          let data = res.data.results;
          let name = data[0].text;
          this.requirePeo_options = data[0].children.filter(tt => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
        })
        .catch(error => {
          this.$message.error(error);
        });
    } // end
  }
};
</script>
<style scoped>
.bottom {
  margin-top: 20px;
  padding: 15px 0;
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

.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
}
.submit-opertion-con {
  width: 88%;
  position: fixed;
  bottom: 0px;
}
.wrapper_right_bottom_1_zb {
  margin-right: 10px;
}
.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
}
</style>


