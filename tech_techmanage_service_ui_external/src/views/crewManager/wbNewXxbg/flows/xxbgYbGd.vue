<!-- 信息变更 已办代办页面 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">信息变更信息</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <el-button size="mini" @click="toClose">关闭</el-button>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <!-- 主体 -->
    <div class="main-app-container">
      <!-- 面试信息 -->
    </div>

     <div class="contentMain contentMain_Add">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">申请人信息</p>
      </div>
       <div class="content">
        <el-form :model="formData" ref="formData" label-position="left">
          <!-- 表格 -->
          <!--信息变更前-->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">变更前</p>
                  <el-table :data="formData.updateInfoDtos"  style="width: 100%" >
                    <el-table-column label="序号" align="center" type="index"></el-table-column>
                    <el-table-column label="姓名 *" width="120" align="center">
                        <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.userName`" :rules="[{ required: true, message: '请填写姓名'}]">
                                <el-select v-model="scope.row.userName" @change="handleChange(scope.row,`${scope.$index}`)" disabled>
                                  <el-option
                                    v-for="item in applyData"
                                    :key="item.id"
                                    :value="item.userName"
                                    :label="item.userName"
                                  ></el-option>
                                </el-select>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="项目经理" width="200">
                        <template slot-scope="scope">
                          <el-form-item :prop="organizersValueOld.badge" :rules="[{ required: true, message: '请选择项目经理'}]">
                            <el-select v-model="organizersValueOld" filterable placeholder="请选择" value-key="badge"  disabled>
                              <el-option v-for="(item) in organizers_options_old" :key="item.badge" :label="item.empname" :value="item" ></el-option>
                            </el-select>
                          </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="部门" width="200">
                        <template slot-scope="scope">
                          <el-form-item :prop="depValueOld.id" :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]" >
                            <el-select v-model="depValueOld" filterable placeholder="请选择" value-key="id"  disabled>
                              <el-option v-for="(item,index) in deptno_options" :key="index" :value="item" :label="item.text" ></el-option>
                            </el-select>
                          </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="系统" width="200">
                        <template slot-scope="scope">
                          <el-form-item :prop="systemsValueOld.id" :rules="[{ required: true, message: '请选择系统名称'}]">
                              <el-select v-model="systemsValueOld" filterable  value-key="id" disabled>
                                  <el-option
                                    v-for="(item)  in systemnos_options_old"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item"
                                  ></el-option>
                              </el-select>
                          </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column width="200">
                    </el-table-column>
                    <el-table-column width="200">
                    </el-table-column>
                    <el-table-column width="200">
                    </el-table-column>
                  </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">外包人员材料</p>
                </div>
                <div class="file_box">
                    <el-row :gutter="20" >
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            前征信证明
                          </p>
                          <el-form-item prop="creditReportBe" >
                           <div v-for="item in oldzxData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'oldzhengxin')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            前社保证明
                          </p>
                          <el-form-item prop="ssPaymentProves" >
                           <div v-for="item in oldsbData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'oldsb')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            前保密承诺书、服务提供/外包员工背景调查表/厂商承诺书
                          </p>
                          <el-form-item prop="ssPaymentProves" >
                            <div v-for="item in oldbmData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'oldbaomi')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                    </el-row>
                    <el-row :gutter="20" >
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            前身份证正反面
                          </p>
                          <el-form-item prop="otherFiles" >
                            <div v-for="item in oldsfzData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'oldsfz')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            前人脸照片
                          </p>
                          <el-form-item prop="faceimages" >
                             <div v-for="item in oldrlData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'oldrl')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            前学历、学位证书
                          </p>
                          <el-form-item prop="dueDiligenceReportBe" >
                           <div v-for="item in oldjdData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'oldjd')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                    </el-row>
                    <el-row :gutter="20" >
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            前其他(无社保缴纳证明、社保代缴、学籍在线报告等)
                          </p>
                          <el-form-item prop="qtFileBe" >
                           <div v-for="item in oldqtData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'oldqt')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                    </el-row>
                  </div>
              </el-col>
            </el-row>
          </div>
          <!--定级信息-->
          <div class="row_2 row_2_add">
            <el-row>
                <div class="tableCon">
                  <p class="row_2_detail">定级信息</p>
                </div>

                <el-col :span="8">
                  <el-form-item label="级别" prop="levelValueOld" label-width="70px">
                      <el-select v-model="levelValueOld" placeholder="请选择级别" value-key="dictid"  style="width: 30%;" :disabled="true">
                        <el-option
                          v-for="item in level_options"
                          :key="item.dictid"
                          :label="item.dictname"
                          :value="item"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                <el-col :span="8">
                  <el-form-item label="定级人员" prop="evaluatorValueOld">
                    <el-input
                      v-model="evaluatorValueOld.empname"
                      :disabled="true"
                      placeholder="自动填充"
                      style="width: 40%;"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="上次定级时间" prop="evaluatorDate">
                    <el-input
                      v-model="evaluatorDate"
                      :disabled="true"
                      placeholder="自动填充"
                      style="width: 40%;"
                    />
                  </el-form-item>
                </el-col>
                <!-- 定级理由 -->
                <el-col :span="24">
                  <el-form-item label="定级理由" prop="levelReason">
                    <el-input v-model="levelReasonOld" type="textarea" :autosize="true" :disabled="true" resize="none" style="width: 45%;"/>
                  </el-form-item>
                </el-col>
            </el-row>
          </div>
          <!--信息变更后-->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">变更后</p>
                  <el-table :data="formData.updateInfoDtos"  style="width: 100%">
                        <el-table-column label="序号" align="center" type="index"></el-table-column>
                        <el-table-column label="姓名 *" width="120" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`updateInfoDtos.${scope.$index}.userName`">
                                    <el-select v-model="scope.row.userName" @change="handleChange(scope.row,`${scope.$index}`)" disabled>
                                      <el-option
                                        v-for="item in applyData"
                                        :key="item.id"
                                        :value="item.userName"
                                        :label="item.userName"
                                      ></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                   <el-table-column label="项目经理" width="200">
                        <template slot-scope="scope">
                          <el-form-item :prop="organizersValue.badge" :rules="[{ required: true, message: '请选择项目经理'}]">
                            <el-select v-model="organizersValue" filterable placeholder="请选择" value-key="badge"  disabled>
                              <el-option v-for="(item) in organizers_options" :key="item.badge" :label="item.empname" :value="item" ></el-option>
                            </el-select>
                          </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="部门" width="200">
                        <template slot-scope="scope">
                          <el-form-item :prop="depValue.id" :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]" >
                            <el-select v-model="depValue" filterable placeholder="请选择" value-key="id"  disabled>
                              <el-option v-for="(item,index) in deptno_options" :key="index" :value="item" :label="item.text" ></el-option>
                            </el-select>
                          </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="系统" width="200">
                        <template slot-scope="scope">
                          <el-form-item :prop="systemsValue.id" :rules="[{ required: true, message: '请选择系统名称'}]">
                              <el-select v-model="systemsValue" filterable  value-key="id" disabled>
                                  <el-option
                                    v-for="(item)  in systemnos_options"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item"
                                  ></el-option>
                              </el-select>
                          </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column width="200">
                    </el-table-column>
                    <el-table-column width="200">
                    </el-table-column>
                    <el-table-column width="200">
                    </el-table-column>
                    </el-table>
                </div>
              </el-col>
            </el-row>
          </div>

          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">外包人员材料</p>
                </div>
                <div class="file_box">
                    <el-row :gutter="20" >
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            征信报告
                          </p>
                          <el-form-item prop="creditReportAf">
                          <div v-for="item in zxData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'zhengxin')">预览</span>
                            <span class="download" @click="downloadFile(item.docid)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            社保证明
                          </p>
                          <el-form-item prop="ssPaymentProveAf" >
                           <div v-for="item in sbData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'sb')">预览</span>
                            <span class="download" @click="downloadFile(item.docid)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            保密承诺书、服务提供/外包员工背景调查表/厂商承诺书
                          </p>
                          <el-form-item prop="ssPaymentProveAf" >
                            <div v-for="item in bmData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'bm')">预览</span>
                            <span class="download" @click="downloadFile(item.docid)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                    </el-row>
                    <el-row :gutter="20" >
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            身份证正反面
                          </p>
                          <el-form-item prop="otherFileAf" >
                            <div v-for="item in sfzData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'sfz')">预览</span>
                            <span class="download" @click="downloadFile(item.docid)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            人脸照片
                          </p>
                          <el-form-item prop="faceimageAf" >
                             <div v-for="item in rlData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'rl')">预览</span>
                            <span class="download" @click="downloadFile(item.docid)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            学历、学位证书
                          </p>
                          <el-form-item prop="dueDiligenceReportAf">
                          <div v-for="item in jdData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'jd')">预览</span>
                            <span class="download" @click="downloadFile(item.docid)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                    </el-row>
                    <el-row :gutter="20" >
                      <el-col :span="8">
                        <div>
                          <p class="file_box_title">
                            其他
                          </p>
                          <el-form-item prop="qtFileAf">
                          <div v-for="item in qtData" :key="item.docid">
                            {{ item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'qt')">预览</span>
                            <span class="download" @click="downloadFile(item.docid)">下载</span>
                          </div>
                          </el-form-item>
                        </div>
                      </el-col>
                    </el-row>
                  </div>
              </el-col>
            </el-row>
          </div>
          <!--定级信息-->
          <div class="row_2 row_2_add">
            <el-row>
                <div class="tableCon">
                  <p class="row_2_detail">定级信息</p>
                </div>

                <el-col :span="8">
                  <el-form-item label="级别" prop="levelValue" label-width="70px">
                      <el-select v-model="levelValue" placeholder="请选择级别" value-key="dictid" :disabled="true" style="width: 30%;">
                        <el-option
                          v-for="item in level_options"
                          :key="item.dictid"
                          :label="item.dictname"
                          :value="item"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                <el-col :span="8">
                  <el-form-item label="定级人员" prop="evaluatorValue">
                    <el-input
                      v-model="evaluatorValue.empname"
                      :disabled="true"
                      placeholder="自动填充"
                      style="width: 40%;"
                    />
                  </el-form-item>
                </el-col>
                <!-- 定级理由 -->
                <el-col :span="24">
                  <el-form-item label="定级理由" prop="levelReason">
                    <el-input v-model="levelReason" type="textarea" :autosize="true" :disabled="true" resize="none" style="width: 45%;"/>
                  </el-form-item>
                </el-col>
            </el-row>
          </div>
        </el-form>
      </div>

      <div class="czLog">
          <flow-logs-gd ref="log"></flow-logs-gd>
      </div>
    </div>

    <div class="nav-bottom" v-if="flowItem.status == 'yb'">
      <yb-revoke :flowItem="flowItem"></yb-revoke>
    </div>

    <div class="blank"></div>
     <!-- 预览图片弹窗 -->
        <el-dialog title="" :visible.sync="ylDialog">
            <div class="ylDialogCon">
                <el-image :src="ylPhotoUrl" style="width:600px;height:400px" :fit="photoFit" :preview-src-list="ylPhotoUrl.split(',')"/>
            </div>
        </el-dialog>
  </div>
</template>
<script>
import common from "@/api/common";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
import { validMotion, validContract, queryResourceDept, querySystemEmp,querySystemList } from "@/api/integrated/wbryzrlc";
import { saveDraft, liuzhuan, upload } from "@/api/integrated/newWbrz";
import { presenceInfo, initFlow, queryDetail } from '@/api/xxbg/index'
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import approveBottom from "@/views/plugins/camunda/approveBottom";
import { getDicEntry } from "@/api/eosDictEntry";
import { preview } from '@/api/common/flowApi'
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser; // 引入ntko
import ybRevoke from "@/views/plugins/camunda/ybRevoke";


import { isDate } from 'moment';

  export default {
    name: 'NewXxChange',
    components: { buttonOrange, buttonCommon, approveBottom, FlowLogsGd,ybRevoke },
    data() {
        return {
          formData:{
            // instanceid: null, // 流程ID
            // applyerDept: '', // 部门编号
            // applyerDeptName: "", // 部门名称
            // applyerName: '', // 申请人名称
            // applyer: "", // 申请人编号
            // updateInfoDtos: [ // 外包人员集合信息
            //     {
            //       id: '',
            //       interviewId: "", // 关联面试ID
            //       jobId: "", // 工号
            //       userName: '', // 姓名
            //       vmAccount: '', // 虚拟机账号
            //       entranceCard: '', // 门禁卡编号
            //       ceneralOfficeMotion: "", // 总办议案
            //       newContractId: "", // 合同
            //       creditReport: '', // 征信报告
            //       sociaiSecrityPaymentProve: '', // 社保缴纳证明/劳动合同/纳税证明
            //       confidentialityCommitment: '', // 保密承诺书
            //       otherFile: '', // 身份证反面
            //       faceimage: '' // 人脸照片
            //     }
            // ]
          },
          buttonName: "提交",
          buttonSave: '草稿',
          size: "mini",
          errorMessage: '', //失败消息提示,
          objectcode: "6000000300",
          objectgrade: "2",
          depData: [],
          disableds: true,
          isComplementOption: [
            {
              id: "0",
              label: '否',
              value: 'N'
            },
            {
              id: "1",
              label: '是',
              value: 'Y'
            }
          ],
          outsourceTypeAf_options: [
            {
              id: "0",
              label: '项目',
              value: '0'
            },
            {
              id: "1",
              label: '人力',
              value: '1'
            }
          ],
          applyData: [], // 信息变更者信息
          currentRow: {}, //当前行
          instanceid: '', // 流程ID
          ceneralOfficeMotion_options: [], // 总办议案
          newContractId_options: [], // 合同
          fileList: [], //上传文件列表
          zxData: [], // 征信文件
          sbData: [], // 社保文件
          bmData: [], // 保密文件
          sfzData: [], // 身份证文件
          rlData: [], // 人脸照片文件
          jdData: [], // 学历、学位证书
          qtData: [], // 其他附件
          flowItem: {},
          formScene: '', //场景
          position_options: [], // 岗位
          oldzxData: [],
          oldsbData: [],
          oldbmData: [],
          oldsfzData: [],
          oldrlData: [],
          oldjdData: [],
          oldqtData: [],
          ylDialog: false, //是否显示预览图片弹窗
          ylPhotoUrl: "", //预览图片地址
          photoFit:"scale-down",
          depValueOld: {}, // 部门数据
          systemsValueOld: {}, // 系统信息
          organizersValueOld: {}, // 行内负责人数据
          levelValueOld: {},// 级别
          evaluatorValueOld: {},// 定级人员
          levelReasonOld: "", // 定级理由
          systemnos_options_old: [], // 系统列表
          organizers_options_old: [], //项目经理列表
          depValue: {}, // 部门数据
          systemsValue: {}, // 系统信息
          organizersValue: {}, // 行内负责人数据
          levelValue: {},// 级别
          levelReason: "", // 定级理由
          evaluatorValue: {},// 定级人员
          deptno_options: [], // 部门列表
          systemnos_options: [], // 系统列表
          organizers_options: [], //项目经理列表
          level_options: [], // 级别列表
          evaluatorDate: "", // 上一次定级日期
        }
    },
    computed: {
    },
    watch: {
    },
    created() {


    },
    mounted() {
    },

    methods: {
      init(params) {
        this.$nextTick(_ => {
        this.flowItem = params
          this.instanceid = params.instanceid || params.processInstanceId;
          this.formScene = params.formScene;
          this.taskId = params.taskId;
          this.getvalidMotion();
          this.getvalidContract();
          this.getPosition();
          this.getXxbgDetail(this.instanceid)
          this.$refs.log.init(params);
        })
      },
      // 获取需求审批详细信息
      getXxbgDetail(id) {
        let params = {}
        params.instanceid = id;
        let data = common.paramsFormat(params)
        queryDetail(data).then(res => {
          // console.log('获取需求审批详细信息', res)
          let {status, results} = res.data
          if(status === 200) {
            if(results.updateInfoDtos.length == 0) return this.$message.error('数据获取失败，请稍后重试')
            this.formData = results;
            let str1 = results.updateInfoDtos[0].creditReportBe || '';
            let str2 = results.updateInfoDtos[0].creditReportAf || '';
            let str3 = results.updateInfoDtos[0].ssPaymentProveBe || '';
            let str4 = results.updateInfoDtos[0].ssPaymentProveAf || '';
            let str5 = results.updateInfoDtos[0].confidentCommitmentBe || '';
            let str6 = results.updateInfoDtos[0].confidentCommitmentAf || '';
            let str7 = results.updateInfoDtos[0].otherFileBe || '';
            let str8 = results.updateInfoDtos[0].otherFileAf || '';
            let str9 = results.updateInfoDtos[0].faceimageBe || '';
            let str10 = results.updateInfoDtos[0].faceimageAf || '';
            let str11 = results.updateInfoDtos[0].dueDiligenceReportBe || '';
            let str12 = results.updateInfoDtos[0].dueDiligenceReportAf || '';
            let str13 = results.updateInfoDtos[0].qtFileBe || '';
            let str14 = results.updateInfoDtos[0].qtFileAf || '';
            let oldzxData = []
            let zxData = []
            let oldsbData = []
            let sbData = []
            let oldbmData = []
            let bmData = []
            let oldsfzData = []
            let sfzData = []
            let oldrlData = []
            let rlData = []
            let oldjdData = []
            let jdData = []
            let oldqtData = []
            let qtData = []
            oldzxData = str1.split(",")
            zxData = str2.split(",")
            oldsbData = str3.split(",")
            sbData = str4.split(",")
            oldbmData = str5.split(",")
            bmData = str6.split(",")
            oldsfzData = str7.split(",")
            sfzData = str8.split(",")
            oldrlData = str9.split(",")
            rlData = str10.split(",")
            oldjdData = str11.split(",")
            jdData = str12.split(",")
            oldqtData = str13.split(",")
            qtData = str14.split(",")
            oldzxData.forEach(item => {
              this.getFileName(item, 'oldzx')
            })
            zxData.forEach(item => {
              this.getFileName(item, 'zx')
            })
            oldsbData.forEach(item => {
              this.getFileName(item, 'oldsb')
            })
            sbData.forEach(item => {
              this.getFileName(item, 'sb')
            })
            oldbmData.forEach(item => {
              this.getFileName(item, 'oldbm')
            })
            bmData.forEach(item => {
              this.getFileName(item, 'bm')
            })
            oldsfzData.forEach(item => {
              this.getFileName(item, 'oldsfz')
            })
            sfzData.forEach(item => {
              this.getFileName(item, 'sfz')
            })
            oldrlData.forEach(item => {
              this.getFileName(item, 'oldrl')
            })
            rlData.forEach(item => {
              this.getFileName(item, 'rl')
            })
            oldjdData.forEach(item => {
              this.getFileName(item, 'oldjd')
            })
            jdData.forEach(item => {
              this.getFileName(item, 'jd')
            })
            oldqtData.forEach(item => {
              this.getFileName(item, 'oldqt')
            })
            qtData.forEach(item => {
              this.getFileName(item, 'qt')
            })
            // console.log('人脸照片列表', rlData);
            this.showBasicInfo(results.updateInfoDtos[0]);
          }
        })
      },
       //反显项目定级信息
      showBasicInfo(item) {
        let params = {};
        params.objectcode = "6000000300";
        params.objectgrade = "2";
        queryResourceDept(params).then(res => {

          var fild = res.data.results[0].children;
          var reg = /([^-]+)$/;
          this.deptno_options = fild.filter(li => {
            li.text = li.text.match(reg)[1];
            return li;
          });
          //反显信息
          this.getBasicInfoBe(item);
          this.getBasicInfoAf(item);
          }).catch(error => {
              this.$message.error("获取部门数据失败");
          }); // end
      },
      // 外包人员变化前反显
      getBasicInfoBe(item) {
        // 所在部门
        this.depValueOld = this.deptno_options.find(ele => ele.id.replace("dep", "") === item.deptIdBe) || {};

        // 所在系统
        let params = {};
        params.depId = item.deptIdBe;
        querySystemList(params).then(res => {
            this.systemnos_options_old = res.data.results;
            this.systemsValueOld = this.systemnos_options_old.find(ele => ele.id === item.systemNoBe) || {};

            if(this.systemsValueOld && this.systemsValueOld.id) {
              //查找项目经理
              let organizersParam = {};
              organizersParam.id = item.systemNoBe;
              querySystemEmp(organizersParam).then(res => {

                this.organizers_options_old = res.data.results;
                this.organizersValueOld = this.organizers_options_old.find(ele => ele.badge === item.personInChargeBe) || {}
                // 反显定级人员姓名
                this.evaluatorValueOld = this.organizers_options_old.find(ele => ele.badge === item.evaluatorIdBe) || {}

                }).catch(error => {
                    this.$message.error("查询项目经理失败");
              });
            }

          }).catch(error => {
          this.$message.error("查询系统名称失败");
          });

        this.levelValueOld = this.level_options.find(ele => ele.dictid === item.evaluationLevelBe) || {}
        this.levelReasonOld = item.levelReasonBe;
        this.evaluatorDate = item.evaluationDate;
      },

      //外包人员变化后反显
      getBasicInfoAf(item) {
        // 所在部门
        this.depValue = this.deptno_options.find(ele => ele.id.replace("dep", "") === item.deptIdAf) || {};

        // 所在系统
        let params = {};
        params.depId = item.deptIdAf;
        querySystemList(params).then(res => {
            this.systemnos_options = res.data.results;
            this.systemsValue = this.systemnos_options.find(ele => ele.id === item.systemNoAf) || {};

            if(this.systemsValue && this.systemsValue.id) {
              //查找项目经理
              let organizersParam = {};
              organizersParam.id = item.systemNoAf;
              querySystemEmp(organizersParam).then(res => {

                this.organizers_options = res.data.results;
                this.organizersValue = this.organizers_options.find(ele => ele.badge === item.personInChargeAf) || {}
                // 反显定级人员姓名
                this.evaluatorValue = this.organizers_options.find(ele => ele.badge === item.evaluatorIdAf) || {}

                }).catch(error => {
                    this.$message.error("查询项目经理失败");
              });
            }

          }).catch(error => {
          this.$message.error("查询系统名称失败");
          });

        this.levelValue = this.level_options.find(ele => ele.dictid === item.evaluationLevelAf) || {}
        this.levelReason = item.levelReasonAf;
      },

      //业务字典
      getPosition() {
        getDicEntry({
          dicttypeid: 'EVALUATION_LEVEL,BUSI_OUTSOURCE_POSITION'
        }).then(res => {
         if (res.data.status !== 200) {
            this.$alert(res.data.message, { type: 'error' })
            return false
          }
          const data = res.data.results
          // 级别
          this.level_options = data.filter(item => {
            return item.dicttypeid == "EVALUATION_LEVEL";
          });
          // 岗位
          this.position_options = data.filter(item => {
            return item.dicttypeid == "BUSI_OUTSOURCE_POSITION";
          });
        });
      },
      // 获取文件名称
      getFileName(id, mark) {
        if(id === '') return
        preview(id).then(res => {
          // console.log('获取文件名字', res);
          if(mark === 'zx') {
            this.zxData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'sb') {
            this.sbData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'bm') {
            this.bmData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'sfz') {
            this.sfzData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'rl') {
            this.rlData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
            // console.log('111', this.rlData);
          } else if(mark === 'jd') {
            this.jdData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'qt') {
            this.qtData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'oldzx') {
            this.oldzxData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'oldsb') {
            this.oldsbData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'oldbm') {
            this.oldbmData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'oldsfz') {
            this.oldsfzData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'oldrl') {
            this.oldrlData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'oldjd') {
            this.oldjdData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          } else if(mark === 'oldqt') {
            this.oldqtData.push({
              "docname": res.data.results.docname,
              "docid":res.data.results.documentno,
              "tag1":res.data.results.tag1,
              "tag2": res.data.results.tag2
            })
          }
        })
      },
      //ntko插件预览
      openNtkoWindow(obj, mark) {
          let formJudge = obj.docname.split(".")[
              obj.docname.split(".").length - 1
          ];
          if (formJudge == "jpg" || formJudge == "JPG" ||
            formJudge == "jpeg" || formJudge == "JPEG" ||
            formJudge == "png" || formJudge == "PNG") {
          this.ylDialog = true;
          let pos = obj.tag1.lastIndexOf("/");
          let str = obj.tag1.substr(0, pos);
          if (obj.tag2 != undefined) {
              this.ylPhotoUrl = str + "/" + obj.tag2;
          } else {
              this.ylPhotoUrl = obj.tag1;
          }
          } else if (formJudge == "zip" || formJudge == "rar" ||  formJudge == "txt") {
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
      // 下载文件
      downloadFile(docId) {
        // console.log('下载文件', docId);
          const self = this;
          common.downloadDoc(docId, self);
      },
      // 关闭
      toClose() {
        this.$router.push('/dashboard')
      },
      // 失败提示消息
      saveError() {
        this.$message({
            showClose: true,
            message: this.errorMessage,
            type: 'error'
        })
      },
      //消息提示
      messageNotify(messageVal) {
        let messageSec = messageVal.slice(2).join("，");
        this.$notify({
          title: '提示',
          dangerouslyUseHTMLString: true,
          message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
          duration: 0
        })
      },
      // 总办议案
      getvalidMotion() {
        validMotion({fileType:0}).then(res => {
          // console.log(res, '总办议案')
          const {results, status} = res.data;
          if(status === 200) {
            this.ceneralOfficeMotion_options = results.map(item => {
              item.label = item.fileName + "("+item.fileNo+")"
              return item;
            })
          }
        })
      },
      // 合同
      getvalidContract() {
        validContract().then(res => {
          // console.log(res);
          const {results, status} = res.data;
          if(status === 200) {
            this.newContractId_options = results.map(item => {
              item.label = item.contractName + "(" + item.contractNo +")"
              return item
            })
          }
        })
      }
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
    width: 100px;
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
    position: absolute;
    bottom: 0;
    padding: 15px 0;
  }

  .upload_scss div {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
    cursor: pointer;
  }

  /* 重传 */
  .againUpLoad {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
  }

  .wrapper_right_bottom_1_zb {
    margin-right: 10px;
  }
  .font_style {
    font-size: 12px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .preview,
    .download {
        color: #FBA42F;
        font-size: 14px;
        margin-left: 5px;
        cursor: pointer;
    }
    /* 预览弹窗内容居中 */
    .ylDialogCon {
        text-align: center;
    }
    .file_box .file_box_title {
      font-size: 15px;
      font-weight: 600;
    }
    .nav-bottom {
    width: 100%;
    background-color: #ffffff;
    position: absolute;
    bottom: 0;
  }
</style>

<style>
  /* 级联选择器 */
  .contentMain_Add .el-cascader {
    display: block;
  }

  /* 下拉框 */
  .contentMain_Add .el-select {
    display: block;
  }

  /* 表格删除按钮 */
  .contentMain_Add .el-icon-delete {
    width: 11px;
    height: 11px;
    color: #f56c6c;
  }

  .operation_add .el-button {
    border: 1px solid #ffffff;
    padding: 0;
  }

  /* table表格修改 */

  .row_2_add .el-table th {
    background: rgba(250, 250, 250, 1);
  }

  .row_2_add .el-table thead {
    font-size: 14px;
    color: rgba(38, 38, 38, 1);
    font-weight: 400;
  }

  .row_2_add .el-table--medium th {
    padding: 0;
  }

  .row_2_add .el-table--medium td {
    padding: 0;
  }


  .row_2_add .el-table th {
    padding: 10px 0;
  }

  /* form表单样式修改 */
  .contentMain_Add .el-form-item {
    margin: 16px 0;
  }

  /* 计数器 */
  .contentMain_Add .el-input-number--medium {
    width: 100px;
  }

  /* 上传文件按钮 */

  .tableCon .el-button--primary {
    color: #000000;
    background-color: #ffffff;
    border-color: rgba(0, 0, 0, 0.15);
  }

  /* 重传 */
  .againUpLoad .el-upload--picture-card:hover {
    color: rgba(251, 164, 47, 1);
  }

  .againUpLoad .el-upload:focus {
    color: rgba(251, 164, 47, 1);
  }
</style>
