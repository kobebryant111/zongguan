<!-- 分行系统负责人应用变更申请 -->
<template>
  <div class>
    <div class="app-container">
      <el-card :body-style="{ padding: '0px' }">
        <p class="fu-title">
          <strong>基本信息</strong>
        </p>
        <el-form
          :model="form"
          ref="form"
          :rules="formRules"
          label-width="150px"
          label-position="right"
          inline
        >
          <el-row>
            <el-col :span="12">
              <el-form-item label="变更提出人" prop="applyuser">
                <el-input v-model="form.applyuser" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="变更提出时间" prop="applytime">
                <el-date-picker
                  v-model="form.applytime"
                  type="date"
                  placeholder="选择日期时间"
                  value-format="yyyy-MM-dd"
                  disabled
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="变更类型" prop="changetype">
                <el-select v-model="form.changetype" placeholder="请选择" @change="onChangeType">
                  <el-option
                    v-for="item in ctData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="变更时间" prop="changedate">
                <el-date-picker
                  v-model="form.changedate"
                  type="date"
                  placeholder="选择日期时间"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="sjwhShow ? 24 : 12">
              <el-form-item label="上线流程" prop="projectno1" v-if="form.changetype === '01'">
                <el-input v-model="form.projectno1" placeholder maxlength="200" disabled>
                  <template slot="append">
                    <el-button type="primary" @click="selectedFlow()" icon="el-icon-more"></el-button>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="补丁主题" prop="patchtitle" v-else-if="form.changetype === '02'">
                <el-input
                  v-model="form.patchtitle"
                  placeholder="请选择奋进号对应生产缺陷"
                  maxlength="200"
                  disabled
                >
                  <template slot="append">
                    <el-button type="primary" @click="selectedPatch()" icon="el-icon-more"></el-button>
                  </template>
                </el-input>
              </el-form-item>
              <template v-else-if="form.changetype === '03'">
                <el-form-item label="分行应用变更名称(16字以内)" prop="applyChangeTitle">
                  <el-input v-model="form.applyChangeTitle" placeholder="分行应用变更名称" maxlength="16"></el-input>
                </el-form-item>
              </template>
            </el-col>
            <el-col :span="12" v-if="!sjwhShow">
              <el-form-item label="变更应用系统" prop="mSysnoText">
                <el-input v-model="form.mSysnoText" placeholder="变更应用系统" maxlength="200" disabled>
                  <template slot="append">
                    <el-button type="primary" @click="selectedSystem()" icon="el-icon-more"></el-button>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item :label="mangerText" prop="fhitmanager">
                <el-input v-model="form.fhitmanager" placeholder disabled>
                  <template slot="append">
                    <el-button type="primary" @click="selectedIT()" icon="el-icon-more"></el-button>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="sumMangerText" prop="fhyybmanager">
                <el-input v-model="form.fhyybmanager" placeholder disabled>
                  <template slot="append">
                    <el-button type="primary" @click="selectedYY()" icon="el-icon-more"></el-button>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-if="sjwhShow">
            <el-col :span="24">
              <p>
                <strong>变更关联系统</strong>
              </p>
              <el-table :data="lData" border stripe with="95%">
                <el-table-column type="index" label="序号" width="70px"></el-table-column>
                <el-table-column label="系统名称" prop="sysName"></el-table-column>
                <el-table-column label="系统编号" prop="systemId"></el-table-column>
                <el-table-column label="系统负责人" prop="tecUserName"></el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
    </div>

    <!-- 密码获取 -->
    <div class="app-container" v-if="sjwhShow">
      <el-card :body-style="{ padding: '0px' }">
        <p class="fu-title">
          <strong>密管信息</strong>
        </p>
        <el-form :model="form1" ref="form1" :rules="form1Rules" label-width="150px" inline>
          <el-row>
            <el-col :span="24">
              <el-form-item label="是否需要申请密码" prop="istopwd">
                <el-radio-group v-model="form1.istopwd" @change="onIstopwd">
                  <el-radio
                    v-for="item in isPawdData"
                    :key="item.id"
                    :label="item.text"
                  >{{item.text}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <div v-if="mgTableShow">
            <el-row >
              <el-col :span="24">
                <el-form-item label="预计变更时长(小时)" prop="changehour">
                  <el-input v-model="form1.changehour" ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row >
              <el-col :span="24">
                <el-form-item label="计划变更开始日期" prop="planstarttime">
                  <el-date-picker
                    v-model="form1.planstarttime"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    @change="checkedIsDay"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="备注" >
              <el-input type="textarea" v-model="form1.pwdnote"></el-input>
            </el-form-item>
            <el-row >
              <el-col :span="24">
                <el-form-item label>
                  <div>
                    <button-orange :buttonName="addText" :fns="addSheBeiBtn"></button-orange>
                    <button-common :buttonName="deleteText" :fns="deleteSheBeiBtn"></button-common>
                  </div>
                  <el-table
                    :data="mgData"
                    border
                    stripe
                    highlight-current-row
                    @row-click="handleMgRow"
                  >
                    <el-table-column type="index" label="序号"></el-table-column>
                    <el-table-column prop="systemid" label="系统"></el-table-column>
                    <el-table-column prop="ip" label="设备ip"></el-table-column>
                    <el-table-column prop="usertype" label="用户类型"></el-table-column>
                    <el-table-column prop="userid" label="用户名"></el-table-column>
                  </el-table>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </el-card>
    </div>

    <!-- 是否涉及变更 -->
    <div class="app-container">
      <el-card :body-style="{ padding: '0px' }">
        <p class="fu-title">
          <strong>是否涉及变更</strong>
        </p>
        <el-form :model="form5" ref="form5" :rules="form5Rules" label-width="180px" inline>
          <el-row>
            <el-col :span="8">
              <el-form-item label="是否涉及网络变更" prop="ifWebChange">
                <el-radio-group v-model="form5.ifWebChange">
                  <el-radio
                    v-for="item in mgChangeData"
                    :key="item.id"
                    :label="item.id"
                  >{{item.text}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="是否涉及服务器变更" prop="ifServerChange">
                <el-radio-group v-model="form5.ifServerChange">
                  <el-radio
                    v-for="item in mgChangeData"
                    :key="item.id"
                    :label="item.id"
                  >{{item.text}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="是否涉及中间件变更" prop="ifMiddleware">
                <el-radio-group v-model="form5.ifMiddleware">
                  <el-radio
                    v-for="item in mgChangeData"
                    :key="item.id"
                    :label="item.id"
                  >{{item.text}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
    </div>

    <el-card style="padding: '10px', margin:'10px'">
      <div slot="header">
        <span>附件</span>
      </div>
      <el-form :model="form2" ref="form2" :rules="fileRules" label-width="150px">
        <el-row v-if="!sjwhShow">
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
        <el-row v-if="form.changetype == '02'">
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
        <el-row v-if="form.changetype == '01'">
          <el-col :span="24">
            <el-form-item label prop="file2">
              <template slot="label">变更方案
                <el-link type="primary" @click="uploadChangeTemp">模板</el-link>
              </template>
              <el-upload
                v-model="form2.file2"
                ref="elupload2"
                action="Fake Action"
                :http-request="preUload2"
                multiple
                :limit="1"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.changetype == '01'">
          <el-col :span="24">
            <el-form-item prop="file21">
              <template slot="label">变更checklist
                <el-link type="primary" @click="uploadChangeListTemp">模板</el-link>
              </template>
              <el-upload
                v-model="form2.file21"
                ref="elupload21"
                action="Fake Action"
                :http-request="preUload21"
                multiple
                :limit="1"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <!-- 附件 end -->
    <!-- 按钮 -->
    <div :class="!ifShow ? 'footer-center-div' : 'footer-right-div'">
      <span slot="footer">
        <button-delete
          :buttonName="delText"
          :size="sizes"
          :fns="delBtn"
          v-show="ifDelShow"
          style="float: left;"
        ></button-delete>
        <button-common :buttonName="draftText" :size="sizes" :fns="draftBtn" v-show="!ifShow"></button-common>
        <buttonOrange :buttonName="btnName" :size="sizes" :fns="submitBtn"></buttonOrange>
      </span>
    </div>
    <!-- dialog -->
    <el-dialog
      title="选择列表"
      :visible.sync="visibleClose"
      :width="dialogWidth"
      :lock-scroll="false"
      :modal="false"
      @close="dialogClose"
    >
      <!-- 部门人员树 || 系统-->
      <el-tree
        :data="utData"
        :props="props"
        icon-class
        node-key="pcode"
        :default-checked-keys="expandedKeys"
        :expand-on-click-node="false"
        :highlight-current="false"
        @node-click="currentNoke"
        v-show="dialogType== '1' || dialogType== '5'"
      ></el-tree>
      <!-- 密管设备列表 -->
      <mg-shebei-pager
        ref="shebei"
        :busSystemShow="false"
        :systemName="sysName"
        v-show="dialogType == 2"
      ></mg-shebei-pager>
      <!-- 我担任业务经理的项目 -->
      <my-project ref="myProject" v-show="dialogType == 3"></my-project>
      <!-- 生产缺陷 -->
      <my-bug-pro ref="myBugPro" v-show="dialogType == 4"></my-bug-pro>
      <span slot="footer">
        <el-button @click="dialogClose">取 消</el-button>
        <el-button type="primary" @click="selectedNode">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  getProjectChangeInfo,
  getFhITManager,
  getFhYybManager,
  querySheBeiPager,
  getSystemHead,
  preUpload,
  draftBtn,
  liuzhuan,
  isDayTime,
  querySysNoForOnlineno
} from "@/api/branchIntentionApi/yyxmbg";
import { querySystemTree } from "@/api/integrated/wbryzrlc";
import MyProject from "@/views/plugins/otherPlug/MyProject";
import MgShebeiPager from "@/views/plugins/otherPlug/MgShebeiPager";
import MyBugPro from "@/views/plugins/otherPlug/MyBugPro";
import buttonDelete from "@/components/eleButton/delete";
import { getdeptIdForUsers } from "@/api/userApi/bankUser";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
import common from "@/api/common";
import { getDicEntry } from "@/api/eosDictEntry";
import Moment from "moment";
import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import { doDel, getDownloadUrl } from "@/api/common/flowApi";
import { mapGetters } from "vuex";
export default {
  name: "pagerOne",
  components: {
    buttonOrange,
    buttonCommon,
    MgShebeiPager,
    MyProject,
    buttonDelete,
    MyBugPro
  },
  data() {
    return {
      lData: [], // 关联流程|项目书记
      mgData: [], // 密管
      dialogType: "", // dialog 显示类型 1：人员树，2：设备
      mgTableShow: false, // 密管列表是否实现 默认 false
      sjwhShow: true, // 类型为数据维护，默认 true
      mangerText: "", // 经理文本值
      sumMangerText: "", // 总经理文本值
      documentno1: "", // 附件
      documentno2: "", // 变更方案2
      documentno21: "", // 变更方案21
      documentno3: "", // 变更checklist
      form: {
        changetype: "", // 变更类型
        applyuser: "", // 申请变更人
        fhitmanager: "", // 分行IT支持部经理
        fhyybmanager: "", //分行运营部总经理
        applytime: "", // 变更提出时间
        projectno1: "", // 上线流程
        changedate: "", // 变更时间
        mSysnoText: "", // 系统名称
        mSysno: "", // 系统编号
        patchtitle: "", //补丁主题
        onlineno: "" // 上线编号
      },
      dialogWidth: "", // dialog 宽度
      depId: "", // 部门编号
      compCode: "", // 机构编码
      sysName: "", // 系统名称
      visibleClose: false, //dialog 是否显示 默认 false
      form1: {
        changehour: "", // 预计变更时长(小时)
        planstarttime: "", // 计划变更开始日期
        pwdnote: "" // 备注
      },
      form5: {
        ifWebChange: "",
        ifServerChange: "",
        ifMiddleware: ""
      },
      ctData: [], // 变更类型
      utData: [], // 人员树,
      sysData: [], // 系统集合数据
      expandedKeys: [], // 默认展开
      ifShow: false, // 面板显示
      ifDelShow: false, // 是否显示删除按钮
      selectedNodeTemp: {}, // 临时存选中的人员树信息
      treeDataTag: 1, // 1:代表 分行IT支持部经理，2：分行运营部总经理
      addText: "添加", // 按钮值
      deleteText: "删除", // 按钮值
      sizes: "size", // 按钮大小
      delText: "删除",
      draftText: "草稿",
      btnName: "提交",
      requiredType: true, // 是否必填 默认 0
      form2: {
        file2: "",
        file21: ""
      }, // 文件上传表单
      props: {
        children: "children",
        label: "text"
      },
      isPawdData: [{ id: "是", text: "是" }, { id: "否", text: "否" }],
      // 密管 三个 是否涉及
      mgChangeData: [{ id: "1", text: "是" }, { id: "0", text: "否" }],
      formRules: {
        changetype: [
          { required: true, message: "请选择变更类型", trigger: "change" }
        ],
        fhitmanager: [
          {
            required: true,
            message: "请选择分行IT支持部经理",
            trigger: "change"
          }
        ],
        fhyybmanager: [
          {
            required: true,
            message: "请选择分行运营部总经理",
            trigger: "change"
          }
        ],
        changedate: [
          { required: true, message: "请选择变更时间", trigger: "blur" }
        ],
        projectno1: [
          { required: true, message: "请选择上线流程", trigger: "blur" }
        ],
        patchtitle: [
          { required: true, message: "请选择补丁更新", trigger: "blur" }
        ],
        mSysnoText: [
          { required: true, message: "请选择系统", trigger: "blur" }
        ],
        applyChangeTitle: [
          { required: true, message: "请填写", trigger: "blur" }
        ]
      },
      form1Rules: {
        istopwd: [{ required: true, message: "请选择", trigger: "change" }],
        planstarttime: [{ required: true, message: "请选择", trigger: "blur" }],
        changehour: [
          { required: true, message: "请填写", trigger: "blur" },
          {
            pattern: /^([1-9]{1}[0-9]?)$/,
            message: "必须为整数",
            trigger: "blur"
          }
        ]
      },
      form5Rules: {
        ifWebChange: [{ required: true, message: "请选择", trigger: "change" }],
        ifServerChange: [
          { required: true, message: "请选择", trigger: "change" }
        ],
        ifMiddleware: [{ required: true, message: "请选择", trigger: "change" }]
      },
      fileRules: {
        file2: [{ required: true, message: "请选择", trigger: "change" }],
        file21: [{ required: true, message: "请选择", trigger: "change" }]
      }
    };
  },
  computed: {
    ...mapGetters(["userId", "name"])
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      const self = this;
      //
      getProjectChangeInfo().then(res => {
        console.log("页面打开数据", res.data);
        let data = res.data.results;
        self.form.fhitmanager = data.fhitmanager;
        self.form.fhyybmanager = data.fhyybmanager;
        self.form.applyuser = data.applyuser;
        self.depId = data.deptId;
        self.compCode = data.compcode;
        self.form.applytime = Moment(data.applytime).format("YYYY-MM-DD");
        // 是否外包管理员
        if (data.flag) {
          self.sumMangerText = "分行金融科技部总经理";
          self.mangerText = "分行研发部经理";
        } else {
          self.sumMangerText = "分行IT支持部经理";
          self.mangerText = "分行运营部总经理";
        }
      });

      let eos = { dicttypeid: "FHZYXMBGTYPE" };
      getDicEntry(eos).then(res => {
        const data = res.data.results;
        console.log("data", data);
        self.ctData = data;
      });
    },
    /**
     * 变更类型变更事件
     */
    onChangeType() {
      let self = this;
      self.form.patchtitle = "";
      self.form.pdefectid = "";
      self.form.issueId = "";
      self.form.projectno = "";
      self.lData = []
      // 清理下载文件
      self.documentno1 = ''
      self.documentno2 = ''
      self.documentno21 = ''
      self.documentno3 = ''
      self.form1.mgData=[]
      if (self.form.changetype == "03") {
        self.sjwhShow = false;
        self.form1.istopwd = "";
      } else {
        self.sjwhShow = true;
      }
    },

    /**
     * 选择流程
     */
    selectedFlow() {
      const self = this;
      self.dialogType = "3";
      if (!self.form.changedate) {
        self.$message.error("请先选择变更时间");
        return false;
      }
      self.visibleClose = true;
      // 提取工号
      let badge = self.form.fhitmanager.substring(
        self.form.fhitmanager.indexOf("(") + 1,
        self.form.fhitmanager.lastIndexOf(")")
      );
      console.log("提取工号", badge);
      self.$nextTick(_ => {
        let obj = { userid: badge, planonline: self.form.changedate };
        console.log("obj", obj);
        self.$refs.myProject.init(obj);
      });
    },
    /**
     * 选择补丁
     */
    selectedPatch() {
      let self = this;
      self.visibleClose = true;
      self.dialogType = "4";
      // 提取工号
      let badge = self.form.fhitmanager.substring(
        self.form.fhitmanager.indexOf("(") + 1,
        self.form.fhitmanager.lastIndexOf(")")
      );
      console.log("提取工号", badge);
      self.$nextTick(_ => {
        self.$refs.myBugPro.init({ userId: badge });
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
      let self = this;
      if (file.file.name.indexOf("变更方案") <= -1) {
        self.$refs.elupload2.clearFiles();
        self.$message.error("文件名称必须带有[变更方案]");
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
          self.form2.file2 = res.data.results;
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
      let self = this;
      var formData = new FormData();
      if (file.file.name.indexOf("变更checklist") <= -1) {
        self.$refs.elupload21.clearFiles();
        self.$message.error("文件名称必须带有[变更checklist]");
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
          self.form2.file21 = res.data.results;
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
     * dialog 关闭
     */
    dialogClose() {
      this.visibleClose = false;
    },
    /**
     * 选择分行IT支持部经理
     */
    selectedIT() {
      let self = this;
      self.dialogWidth = "30%";
      self.dialogType = "1";

      self.treeDataTag = 1;
      console.log("分行运营部总经理", self.compCode);
      getFhITManager({ compcode: self.compCode }).then(res => {
        console.log("分行运营部总经理", res.data.results);
        getdeptIdForUsers(res.data.results).then(res => {
          console.log("部门人员树", res.data.results);
          self.utData = res.data.results;
          self.expandedKeys = [res.data.results[0].pcode];
          console.log("部门人员树", self.expandedKeys);
          self.visibleClose = true;
        });
      });
    },
    /**
     * 分行运营部总经理
     */
    selectedYY() {
      let self = this;
      self.dialogWidth = "30%";
      self.treeDataTag = 2;
      self.dialogType = "1";
      getFhYybManager({ compcode: self.compCode }).then(res => {
        getdeptIdForUsers(res.data.results).then(res => {
          self.utData = res.data.results;
          self.expandedKeys = [res.data.results[0].pcode];
          self.visibleClose = true;
        });
      });
    },
    currentNoke(data, node, event) {
      console.log(data, node, event);
      let self = this;
      self.selectedNodeTemp = data;
    },
    /**
     * 部门人员树选中- 确定事件
     */
    selectedNode() {
      let self = this;
      // 先判断dialogtype 类型
      if (self.dialogType === "1") {
        if (self.treeDataTag === 1) {
          self.form.fhitmanager = self.selectedNodeTemp.text;
        } else {
          self.form.fhyybmanager = self.selectedNodeTemp.text;
        }
      } else if (self.dialogType === "2") {
        // 设备列表
        console.log("设备列表", self.$refs.shebei.rowData);
        self.$refs.shebei.rowData.forEach((val, key) => {
          self.mgData.push(val);
        });
      } else if (self.dialogType === "3") {
        // 项目更新
        console.log("项目更新：", self.$refs.myProject.rowData);
        let rowData = self.$refs.myProject.rowData;
        self.form.onlineno = rowData.onlineno;
        self.form.projectno1 = rowData.projectName;
        self.form.patchtitle = rowData.projectName;

        // 根据上线编号查询 系统编号
        querySysNoForOnlineno({ onlineno: rowData.onlineno }).then(res => {
          console.log("关联上线流程数据", res.data);
          let sys_item = res.data.results;
          let sysids = [];
          sys_item.forEach((val, key) => {
            sysids.push(val.sysNo);
          });
          let sysid = sysids.join(",");
          // self.form.projectno = rowData.projectName;
          getSystemHead({ sysId: sysid }).then(res => {
            console.log("关联系统1", res.data);
            let item = res.data.results;
            // self.form.mSysno = item.systemId
            // self.form.projectno = rowData.sysno;
            self.lData = [];
            let systemp = [];
            item.forEach((val, key) => {
              val.tecUserName = val.principalaName + "(" + val.principala + ")";
              systemp.push(val.sysName);
              self.lData.push(val);
            });
            self.sysName = systemp.join(",");
          });
        });
      } else if (self.dialogType === "4") {
        // self.dialogType === '4' 缺陷
        let rowData = self.$refs.myBugPro.rowData;
        console.log("选择行数据", rowData);
        self.form.patchtitle = rowData.pacthTitle;
        self.form.pdefectid = rowData.pacthNo;
        self.form.issueId = rowData.issueId;
        getSystemHead({ sysId: rowData.sysNo }).then(res => {
          console.log("关联系统2", res.data);
          let item = res.data.results[0];
          self.form.mSysno = item.systemId;
          self.sysName = item.sysName;
          item.tecUserName = item.principalaName + "(" + item.principala + ")";
          self.lData = [];
          self.lData.push(item);
        });
      } else {
        // self.dialogType === '5' 数据维护-系统列表
        self.form.mSysnoText = self.selectedNodeTemp.text;
        self.form.mSysno = self.selectedNodeTemp.pcode;
      }
      self.dialogClose();
    },
    /**
     * 数据维护-选择系统
     */
    selectedSystem() {
      let self = this;
      querySystemTree(self.compCode).then(res => {
        let data = res.data.results;
        data = data.filter(ii => {
          return ii.id != "org342";
        });
        self.utData = data;
        self.visibleClose = true;
        self.dialogType = "5";
        console.log("系统列表", data);
      });
    },
    /**
     * 计划变更开始日期 是否白天
     */
    checkedIsDay() {
      let self = this;
      isDayTime({ date: self.form1.planstarttime }).then(res => {
        console.log("res:", res.data);
        if (res.data.results === 1) {
          let message =
            "风险提示：白天对生产环境进行变更，可能对系统带来影响，造成部分业务不可用。变更原则上仅允许在非白天（17：30以后）执行，如需要在白天变更，请注明原因。";
          self
            .$confirm(message, { type: "warning" })
            .then(res => {
              console.log("confirm", res);
              self.form1.planstarttime = "";
            })
            .catch(e => {
              console.log("confirm1", e);
            });
          return false;
        }
      });
    },
    /**
     * 是否需要密码事件
     */
    onIstopwd() {
      let self = this;
      
      if (self.form1.istopwd === "是") {
        self.mgTableShow = true;
      } else {
        self.mgTableShow = false;
        // 清理数据
        // self.form1.pwdnote = "";
        // self.form1.changehour = "";
        // self.form1.planstarttime = "";
        // self.form1.mgData = [];
      }
      console.log(self.form1);
    },
    /**
     * 密管设备添加
     */
    addSheBeiBtn() {
      let self = this;
      self.dialogWidth = "50%";
      self.dialogType = "2";
      self.visibleClose = true;
      self.$nextTick(_ => {
        console.log("self.sysName", self.sysName);
        self.$refs.shebei.init({ systemname: self.sysName });
      });

      // querySheBeiPager({systemname:self.sysName}).then(res => {
      //     console.log("密管设备列表", res.data)
      // })
    },
    /**
     * 设备数据点击行数据
     */
    handleMgRow(row) {
      let self = this;
      console.log("row,,", row);
      self.mgRowData = row;
    },
    /**
     * 密管设备删除
     */
    deleteSheBeiBtn() {
      let self = this;
      // 删除
      self.mgData.splice(
        self.mgData.findIndex(item => {
          return (
            self.mgRowData.systemname == item.systemname &&
            self.mgRowData.ip == item.ip &&
            self.mgRowData.usertype == item.usertype &&
            self.mgRowData.username == item.username
          );
        }),
        1
      );
    },
    /**
     * 草稿
     */
    draftBtn() {
      let self = this;
      let load = common.loading(self);
      
      let params = Object.assign(self.form, self.form1, self.form5);
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
        params.patchtitle = params.projectno1;
        // 补丁更新
      } else if (params.changetype == "02") {
        params.patchtitle = params.patchtitle;
      } else {
        // 03 数据维护
        params.patchtitle = params.applyChangeTitle;
      }
      params.pwdList = self.mgData;
      params.sysList = self.lData;
      console.log("表单：", params);
      draftBtn(params).then(res => {
        load.close();
        let message = common.getFlowMessge(res.data.message);
        let messageArr = message.split(",");
        self.$nextTick(() => {
          common.messageNotify(messageArr, self);
          self.$router.push({ name: "dashboard" });
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
      // let params = Object.assign(self.form, self.form1);
      console.log("表单", self.form);
      const f1 = new Promise((resolve, reject) => {
        
        self.$refs.form.validate(vaild => {
          if (vaild) {
            resolve();
          }
        });
      });

      const f2 = new Promise((resolve, reject) => {
        
        if (self.form.changetype == "01") {
          self.$refs.form2.validate(vaild => {
            if (vaild) {
              resolve();
            }
          });
        } else {
          resolve();
        }
      });

      const f3 = new Promise((resolve, reject) => {
        if (self.sjwhShow) {
          self.$refs["form1"].validate(vaild => {
            if (vaild) {
              resolve();
            }
          });
        } else {
          resolve();
        }
      });

      const f5 = new Promise((resolve, reject) => {
        
        self.$refs["form5"].validate(vaild => {
          if (vaild) {
            resolve();
          }
        });
      });

      Promise.all([f1, f2, f3, f5]).then(res => {
        if (self.form.changetype !== "03" && self.form1.istopwd == "是") {
          if (!self.mgData || self.mgData.length <= 0) {
            self.$message.warning("系统设备不能为空");
            return false;
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
      let load = common.loading(self);
      let params = Object.assign(self.form, self.form1, self.form5);
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
        params.patchtitle = params.projectno1;
        // 补丁更新
      } else if (params.changetype == "02") {
        params.patchtitle = params.patchtitle;
      } else {
        // 03 数据维护
        params.patchtitle = params.applyChangeTitle;
      }
      params.pwdList = self.mgData;
      params.sysList = self.lData;
      console.log("表单：", params);
      // 发起流程
      liuzhuan(params).then(res => {
        load.close();
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
      params.processInstID = self.formData.bps.processInstID;
      params.workItemID = self.formData.bps.workItemID;
      params.processDefName = self.formData.bps.processDefName;
      let data = common.paramsFormat(params);
      doDel(data).then(res => {
        if (res.data.status == 200) {
          self.$message.success("流程删除成功");
          self.$emit("closeDialog");
          // this.$router.push({ name: 'dashboard' });
        } else {
          self.$message.error("流程删除失败");
        }
      });
    },
    /**
     * 模板 变更方案
     */
    uploadChangeTemp() {
      let self = this;
      let docId = "FH_YYBG_BGFA_TEMPLER";
      getDownloadUrl({ key: docId }).then(res => {
        // loading.close();
        if (res.data.status == 200) {
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error("获取下载地址失败");
        }
      });
    },
    /**
     * 模板 变更方案list
     */
    uploadChangeListTemp() {
      let self = this;
      let docId = "FH_YYBG_CHECKLIST_TEMPLER";
      getDownloadUrl({ key: docId }).then(res => {
        // loading.close();
        if (res.data.status == 200) {
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error("获取下载地址失败");
        }
      });
    },
    /**
     * 
     */
    mgChange() {
      this.$forceUpdate()
    }
  } // end
};
</script>
<style scoped>
.fu-title {
  margin-left: 10px;
}
.footer-center-div {
  text-align: center;
}

.footer-right-div {
  text-align: right;
  padding-top: 10px;
}
</style>

