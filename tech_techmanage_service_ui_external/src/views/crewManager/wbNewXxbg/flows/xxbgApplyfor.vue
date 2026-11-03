<!-- 信息变更新增页面 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">信息变更申请</p>
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

      <div class="content">
        <el-form :model="formData" ref="formData" label-position="left">
          <!-- 表格 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">外包人员信息</p>
                  <el-table :data="formData.updateInfoDtos"  style="width: 100%"  @row-click="rowClick">
                        <el-table-column label="序号" align="center" type="index"></el-table-column>
                        <el-table-column label="姓名 *" width="200">
                            <template slot-scope="scope">
                                <el-form-item :prop="`updateInfoDtos.${scope.$index}.userName`" :rules="[{ required: true, message: '请填写姓名'}]">
                                    <el-select v-model="scope.row.userName" @change="handleChange(scope.row,`${scope.$index}`)" filterable>
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
                        <el-table-column label="项目经理 *" width="200">
                          <template slot-scope="scope">
                            <el-form-item :prop="organizersValue.badge" :rules="[{ required: true, message: '请选择项目经理'}]">
                              <el-select v-model="organizersValue" filterable placeholder="请选择" value-key="badge" @focus="cueMessage" :disabled="false" @change="organizersChange">
                                <el-option
                                  v-for="(item) in organizers_options"
                                  :key="item.badge"
                                  :label="item.empname"
                                  :value="item"
                                ></el-option>
                              </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="部门 *" width="200">
                          <template slot-scope="scope">
                            <el-form-item :prop="depValue.id" :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]" >
                              <el-select v-model="depValue" filterable placeholder="请选择" value-key="id" @change="getDepId" :disabled="false" >
                                <el-option v-for="(item,index) in deptno_options" :key="index" :value="item" :label="item.text" ></el-option>
                              </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="系统 *" width="200">
                          <template slot-scope="scope">
                            <el-form-item :prop="systemsValue.id" :rules="[{ required: true, message: '请选择系统名称'}]">
                              <el-select v-model="systemsValue" filterable @focus="cueMessage" @change="searchOrganizers"  value-key="id" :disabled="false">
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
                        <!-- <div> -->
                          <p class="file_box_title">
                            征信报告
                          </p>
                          <el-form-item prop="creditReportAf">
                            <el-upload
                              class="upload-demo"
                              action="Fake Action"
                              :file-list="zxData"
                              :show-file-list="false"
                              :http-request="changezxFile"
                              multiple
                            >
                              <el-button size="small" type="primary">
                                <i class="el-icon-upload2"></i>上传文件
                              </el-button>
                            </el-upload>
                            <div v-for="(item) in zxData" :key="item.docid">
                              {{item.docname}}
                              <span class="xkg-color-yellow xkg-marginleft-5px delButt" @click="openNtkoWindow(item, 'zx')">预览</span>
                              <span
                                class="xkg-color-yellow xkg-marginleft-5px delButt"
                                @click="delUpload('zxbg',item.docid)"
                              >
                                删除
                              </span>
                            </div>
                          </el-form-item>
                        <!-- </div> -->
                      </el-col>
                      <el-col :span="8">
                        <!-- <div> -->
                          <p class="file_box_title">
                            社保证明
                          </p>
                          <el-form-item prop="ssPaymentProveAf">
                            <el-upload
                                class="upload-demo"
                                action="Fake Action"
                                :file-list="sbData"
                                :show-file-list="false"
                                :http-request="changesbFile"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                            <div v-for="(item) in sbData" :key="item.docid">
                              {{item.docname}}
                              <span class="xkg-color-yellow xkg-marginleft-5px delButt" @click="openNtkoWindow(item, 'sb')">预览</span>
                              <span
                                class="xkg-color-yellow xkg-marginleft-5px delButt"
                                @click="delUpload('sbbg',item.docid)"
                              >
                                删除
                              </span>
                            </div>
                          </el-form-item>
                        <!-- </div> -->
                      </el-col>
                      <el-col :span="8">
                        <!-- <div> -->
                          <p class="file_box_title">
                            保密承诺书、服务提供/外包员工背景调查表/厂商承诺书
                          </p>
                          <el-form-item prop="ssPaymentProveAf">
                            <el-upload
                                class="upload-demo"
                                action="Fake Action"
                                :file-list="bmData"
                                :show-file-list="false"
                                :http-request="changebmFile"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                            <div v-for="(item) in bmData" :key="item.docid">
                              {{item.docname}}
                              <span class="xkg-color-yellow xkg-marginleft-5px delButt" @click="openNtkoWindow(item, 'bm')">预览</span>
                              <span
                                class="xkg-color-yellow xkg-marginleft-5px delButt"
                                @click="delUpload('bmbg',item.docid)"
                              >
                                删除
                              </span>
                            </div>
                          </el-form-item>
                        <!-- </div> -->
                      </el-col>
                    </el-row>
                    <el-row :gutter="20" >
                      <el-col :span="8">
                        <!-- <div> -->
                          <p class="file_box_title">
                            身份证正反面
                          </p>
                          <el-form-item prop="otherFileAf">
                            <el-upload
                                class="upload-demo"
                                action="Fake Action"
                                :file-list="sfzData"
                                :show-file-list="false"
                                :http-request="changesfzFile"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                            <div v-for="(item) in sfzData" :key="item.docid">
                              {{item.docname}}
                              <span class="xkg-color-yellow xkg-marginleft-5px delButt" @click="openNtkoWindow(item, 'sfz')">预览</span>
                              <span
                                class="xkg-color-yellow xkg-marginleft-5px delButt"
                                @click="delUpload('sfzbg',item.docid)"
                              >
                                删除
                              </span>
                            </div>
                          </el-form-item>
                        <!-- </div> -->
                      </el-col>
                      <el-col :span="8">
                        <!-- <div> -->
                          <p class="file_box_title">
                            人脸照片
                          </p>
                          <el-form-item prop="faceimageAf">
                            <el-upload
                                class="upload-demo"
                                action="Fake Action"
                                :file-list="rlData"
                                :show-file-list="false"
                                :http-request="changefacephotoFile"
                                :before-upload="beforeAvatarUpload"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                            <div v-for="(item) in rlData" :key="item.docid">
                              {{item.docname}}
                              <span class="xkg-color-yellow xkg-marginleft-5px delButt" @click="openNtkoWindow(item, 'rl')">预览</span>
                              <span
                                class="xkg-color-yellow xkg-marginleft-5px delButt"
                                @click="delUpload('rlbg',item.docid)"
                              >
                                删除
                              </span>
                            </div>
                          </el-form-item>
                        <!-- </div> -->
                      </el-col>
                      <el-col :span="8">
                        <!-- <div> -->
                           <p class="file_box_title">
                            学历、学位证书
                          </p>
                          <el-form-item prop="dueDiligenceReportAf">
                            <el-upload
                              class="upload-demo"
                              action="Fake Action"
                              :file-list="jdData"
                              :show-file-list="false"
                              :http-request="changedueDiligenceReport"
                              multiple
                            >
                              <el-button size="small" type="primary">
                                <i class="el-icon-upload2"></i>上传文件
                              </el-button>
                            </el-upload>
                            <div v-for="(item) in jdData" :key="item.docid">
                              {{item.docname}}
                              <span class="xkg-color-yellow xkg-marginleft-5px delButt" @click="openNtkoWindow(item, 'zx')">预览</span>
                              <span
                                class="xkg-color-yellow xkg-marginleft-5px delButt"
                                @click="delUpload('jdbg',item.docid)"
                              >
                                删除
                              </span>
                            </div>
                          </el-form-item>
                        <!-- </div> -->
                      </el-col>
                    </el-row>
                    <el-row :gutter="20" >
                        <el-col :span="8">
                           <p class="file_box_title">
                            其他(无社保缴纳证明、社保代缴、学籍在线报告等)
                          </p>
                          <el-form-item prop="qtFiles">
                            <el-upload
                              class="upload-demo"
                              action="Fake Action"
                              :file-list="qtData"
                              :show-file-list="false"
                              :http-request="changeqtFile"
                              multiple
                            >
                              <el-button size="small" type="primary">
                                <i class="el-icon-upload2"></i>上传文件
                              </el-button>
                            </el-upload>
                            <div v-for="(item) in qtData" :key="item.docid">
                              {{item.docname}}
                              <span class="xkg-color-yellow xkg-marginleft-5px delButt" @click="openNtkoWindow(item, 'zx')">预览</span>
                              <span
                                class="xkg-color-yellow xkg-marginleft-5px delButt"
                                @click="delUpload('qtfj',item.docid)"
                              >
                                删除
                              </span>
                            </div>
                          </el-form-item>
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
                  <el-form-item label="级别 *" prop="levelValue" label-width="70px">
                      <el-select v-model="levelValue" placeholder="请选择级别" value-key="dictid" :disabled="false" style="width: 40%;">
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
                  <el-form-item label="定级理由 *" prop="levelReason">
                    <el-input
                      v-model="levelReason"
                      type="textarea"
                      :autosize="true"
                      placeholder="请输入定级理由，不超过200字"
                      maxlength="200"
                      show-word-limit
                      style="width: 45%;"
                    />
                  </el-form-item>
                </el-col>
            </el-row>
          </div>
        </el-form>
      </div>
    </div>

    <div class="blank"></div>

    <!-- 底部操作栏 -->
    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <!-- <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete> -->
            </el-col>
          </div>
          <div class="wrapper_right">
            <!-- 草稿按钮 -->
            <div class="wrapper_right_bottom_1_zb">
                <el-col :span="1.5">
                  <button-common :buttonName="buttonSave" :size="size" :fns="saveInterview"></button-common>
              </el-col>
            </div>
            <!-- 提交按钮 -->
            <div>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonName" :size="size" :fns="onSubmit"></button-orange>
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>
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
import { queryUserDept } from "@/api/tqrcApplyfor";
import { saveDraft, liuzhuan, upload } from "@/api/integrated/newWbrz";
import { presenceInfo, initFlow, createFlow } from '@/api/xxbg/index'
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import approveBottom from "@/views/plugins/camunda/approveBottom";
import { preview } from '@/api/common/flowApi'
import { isDate } from 'moment';
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser; // 引入ntko
import { getDicEntry } from "@/api/eosDictEntry";
import {
  queryResourceDept,
  querySystemEmp,
  querySystemList
} from "@/api/integrated/wbryzrlc";

  export default {
    name: 'NewXxChange',
    components: { buttonOrange, buttonCommon, approveBottom },
    data() {
        return {
          formData:{
            instanceid: null, // 流程ID
            applyerDept: '', // 部门编号
            applyerDeptName: "", // 部门名称
            applyerName: '', // 申请人名称
            applyer: "", // 申请人编号
            updateReason: "", //变更原因
            updateInfoDtos: [ // 外包人员集合信息
                {
                  id: '',
                  interviewId: "", // 关联面试ID
                  jobId: "", // 工号
                  userName: '', // 姓名
                  outsourceTypeAf: "", // 外包方式
                  positionAf: '', // 岗位前
                  vmAccount: '', // 虚拟机账号
                  entranceCardAf: '', // 门禁卡编号
                  ceneralOfficeMotionAf: "", // 总办议案
                  newContractIdAf: null, // 所属合同
                  creditReportAf: '', // 征信报告
                  creditReports: [], // 征信报告列表
                  ssPaymentProveAf: '', // 社保缴纳证明/劳动合同/纳税证明
                  ssPaymentProves: [], // 社保缴纳证明/劳动合同/纳税证明列表
                  confidentCommitmentAf: '', // 保密承诺书
                  confidentCommitments: [], // 保密承诺书列表
                  otherFileAf: '', // 身份证反面
                  otherFiles: [], // 身份证反面列表
                  faceimageAf: '', // 人脸照片
                  faceimages: [], // 人脸照片列表
                  dueDiligenceReportAf: '', // 学历、学位证书
                  dueDiligenceReports: [], // 学历、学位证书列表
                  qtFileAf: '', // 其他附件
                  qtFiles: [], // 其他附件列表
                  deptIdAf: "", // 部门编号
                  systemNoAf: "", // 系统名称
                  personInChargeAf: '',// 行内负责人
                  evaluationLevelAf: '',// 级别
                  levelReasonAf: "", // 定级理由
                  evaluatorIdAf: "", // 定级人员
                  evaluationTime: "", // 定级时间
                }
            ]
          },
          buttonName: "提交",
          buttonSave: '存草稿',
          size: "mini",
          errorMessage: '', //失败消息提示,
          objectcode: "6000000300",
          objectgrade: "2",
          depData: [],
          disableds: true,
          depValue: {}, // 部门数据
          systemsValue: {}, // 系统信息
          organizersValue: {}, // 行内负责人数据
          levelValue: {},// 级别
          levelReason: "", // 定级理由
          evaluatorValue: {}, // 定级人员
          deptno_options: [], // 部门列表
          systemnos_options: [], // 系统列表
          organizers_options: [], //项目经理列表
          level_options: [], // 级别列表
          evaluatorDate: "", // 上一次定级日期
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
          flowItem: {},
          oldzxData: [],
          oldsbData: [],
          oldbmData: [],
          oldsfzData: [],
          oldrlData: [],
          zxData: [], // 征信
          sbData: [], // 社保
          bmData: [], // 保密
          sfzData: [], // 身份证
          rlData: [], // 人力
          jdData: [], //尽调
          qtData: [], //其他
          position_options: [], // 岗位
          ylDialog: false, //是否显示预览图片弹窗
          ylPhotoUrl: "", //预览图片地址
          photoFit:"scale-down",
          outsourceType: ''
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
      init(item) {
        // console.log('信息变更', item)
        this.getapplyerData();
        this.getPosition();
        this.flowItem = item;
        //加载下拉框数据
        this.filDep();
        this.getBusDicEntry();
      },
      changeContract(val,oppo){
        // console.log(val,oppo,'值是多少')
        this.formData.updateInfoDtos[val].newContractIdAf = this.newContractId_options[oppo].id;
        this.$forceUpdate();
      },
      // 获取申请人部门
      getapplyerData() {
        queryUserDept().then(res => {
          // console.log(res, '获取申请人部门')
          const { message,results, status } = res.data;
          if(status === 200) {
            this.formData.applyer = results.userId;
            this.formData.applyerName = results.userName;
            this.formData.applyerDept = results.deptNo
            this.formData.applyerDeptName = results.deptName;
            // this.getrcInfo()
            this.getpresenceInfo(results.userId);
          } else {
            this.$message.error(message);
          }
        })
      },
      // 获取外包人员变更信息
      getpresenceInfo(id) {
        presenceInfo({presenceState: '1',personInCharge: id}).then(res => {
          // console.log('获取外包人员变更信息', res)
          if(res.data.status === 200) {
            this.applyData = res.data.results;
          } else {
            this.$message.error(res.data.message);
          }
        })
      },
       // 获取业务字典
      getBusDicEntry() {
        getDicEntry({
          dicttypeid: 'EVALUATION_LEVEL'
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
        });
      },
      // 部门变化
      getDepId(val) {
        this.organizers_options = [];
        if(this.formData.organizers != ""){
          this.organizersValue = {};
          this.systemsValue = {};
        }
        this.$nextTick(() => {
            this.searchSystem();

        });
      },
      //查询系统名称
      searchSystem() {
        let params = {};
        let depId = this.depValue.id;
        params.depId = depId.replace("dep", "");
        querySystemList(params).then(res => {

            this.systemnos_options = res.data.results;

          }).catch(error => {
          this.$message.error("查询系统名称失败");
          });
      },
      // 查询行内负责人
      searchOrganizers(val) {
        if(this.formData.organizers != ""){
          this.organizersValue = {};
        }
        let params = {};
        params.id = val.id;
        querySystemEmp(params).then(res => {
            this.organizers_options = res.data.results;
        }).catch(error => {
            this.$message.error("查询项目经理失败");
        });
      },

      //获取所有下拉框内容
      filDep() {
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
          }).catch(error => {
              this.$message.error("获取部门数据失败");
          }); // end
      },
      //没有选择部门时的提示信息
      cueMessage() {
        if (this.depValue.id == "") {
          this.$message("请先选择部门");
        }
      },
      //行内负责人变化
      organizersChange(val) {
        this.evaluatorValue = val;
      },
      // 获取岗位
      getPosition() {
        getDicEntry({
          dicttypeid: "BUSI_OUTSOURCE_POSITION"
        }).then(res => {
          // console.log('岗位', res)
          if(res.data.status === 200) {
            this.position_options = res.data.results;
          }
        });
      },
      // 改变面试者操作
      handleChange(row, index) {
        // // console.log(row, index, '被上天选择的人')
        const loading = common.loading(this)
        this.zxData = []
        this.sbData = []
        this.bmData = []
        this.sfzData = []
        this.rlData = []
        this.jdData = []
        this.qtData = []
        this.depValue = {};
        this.systemnos_options = [];
        this.systemsValue = {};
        this.organizers_options = [];
        this.organizersValue = {};
        this.levelValue = {};
        this.evaluatorValue = {};
        this.levelReason = '';
        this.evaluatorDate = '';
        let arr = JSON.parse(JSON.stringify(this.applyData))
        arr.forEach(item => {
            let zxData = [];
            let sbData = [];
            let bmData = [];
            let sfzData = [];
            let rlData = [];
            let jdData = [];
            let qtData = [];
            let str1 = ''
            let str2 = ''
            let str3 = ''
            let str4 = ''
            let str5 = ''
            let str6 = ''
            let str7 = ''
          if(item.userName === row.userName) {
            // // console.log('item', item)
            // // console.log(item.newContractIdAf == null)
            if(item.newContractIdAf == null) {
              item.newContractIdAf = ''
            }
            this.$set(this.formData.updateInfoDtos, 0, item);
            // // console.log(this.formData.updateInfoDtos)
            str1 = item.creditReportAf || ''; // 现征信
            str2 = item.ssPaymentProveAf || ''; // 现征信
            str3 = item.confidentCommitmentAf || ''; // 现征信
            str4 = item.otherFileAf || ''; // 现征信
            str5 = item.faceimageAf || ''; // 现征信
            str6 = item.dueDiligenceReportAf || ''; // 现征信
            str7 = item.qtFileAf || ''; // 现征信
            zxData = str1.split(",")
            sbData = str2.split(",")
            bmData = str3.split(",")
            sfzData = str4.split(",")
            rlData = str5.split(",")
            jdData = str6.split(",")
            qtData = str7.split(",")
            zxData.forEach(item => {
              this.getFileName(item, 'zx')
            })
            sbData.forEach(item => {
              this.getFileName(item, 'sb')
            })
            bmData.forEach(item => {
              this.getFileName(item, 'bm')
            })
            sfzData.forEach(item => {
              this.getFileName(item, 'sfz')
            })
            rlData.forEach(item => {
              this.getFileName(item, 'rl')
            })
            jdData.forEach(item => {
              this.getFileName(item, 'jd')
            })
            qtData.forEach(item => {
              this.getFileName(item, 'qt')
            })
            this.outsourceType = item.outsourceTypeAf;
            this.getBasicInfo(item);
          }
          loading.close();
        });
      },

      // 外包人员变化
      getBasicInfo(item) {
        // 所在部门
        this.depValue = this.deptno_options.find(ele => ele.id.replace("dep", "") === item.deptIdBe) || {};

        // 所在系统
        let params = {};
        params.depId = item.deptIdBe;
        querySystemList(params).then(res => {
            this.systemnos_options = res.data.results;
            this.systemsValue = this.systemnos_options.find(ele => ele.id === item.systemNoBe) || {};

            if(this.systemsValue && this.systemsValue.id) {
              //查找项目经理
              let organizersParam = {};
              organizersParam.id = item.systemNoBe;
              querySystemEmp(organizersParam).then(res => {

                this.organizers_options = res.data.results;
                this.organizersValue = this.organizers_options.find(ele => ele.badge === item.personInChargeBe) || {}
                //反显定级人员姓名
                this.evaluatorValue = this.organizers_options.find(ele => ele.badge === item.evaluatorIdBe) || {}

                }).catch(error => {
                    this.$message.error("查询项目经理失败");
              });
            }

          }).catch(error => {
          this.$message.error("查询系统名称失败");
          });
        this.levelValue = this.level_options.find(ele => ele.dictid === item.evaluationLevelBe) || {}
        this.levelReason = item.levelReasonBe;
        this.evaluatorDate = item.evaluationDate;
      },

      // 获取文件名称
      getFileName(id, mark) {
        if(id === '') return
        preview(id).then(res => {
          // // console.log('获取文件名字', res);
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
          }
        })
      },
      // 表格行被点击时触发
      rowClick(row) {
        // // console.log('表格行被点击时触发', row)
        this.currentRow = row;
      },
       // 上传征信文件
      changezxFile(file, zxData) {
        // // console.log(file, fileList, '上传文件状态改变', file.file);
        var formData = new FormData();
        formData.append("file", file.file);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        const loading = common.loading(this);
        upload(data).then(res => {
          // console.log('征信', res)
          loading.close();
          if(res.data.status == '200') {
            this.getFileName(res.data.results, 'zx')
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
       // 上传尽调文件
      changedueDiligenceReport(file, jdData) {
        // // console.log(file, fileList, '上传文件状态改变', file.file);
        var formData = new FormData();
        formData.append("file", file.file);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        const loading = common.loading(this);
        upload(data).then(res => {
          loading.close();
          if(res.data.status == '200') {
            this.getFileName(res.data.results, 'jd')
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
       // 上传其他文件
      changeqtFile(file, qtData) {
        // // console.log(file, fileList, '上传文件状态改变', file.file);
        var formData = new FormData();
        formData.append("file", file.file);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        const loading = common.loading(this);
        upload(data).then(res => {
          loading.close();
          if(res.data.status == '200') {
            this.getFileName(res.data.results, 'qt')
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // 上传社保文件
      changesbFile(file, sbData) {
        //  // console.log(file, fileList, '上传文件状态改变')
        var formData = new FormData();
        formData.append("file", file.file);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        const loading = common.loading(this);
        upload(data).then(res => {
          loading.close();
          //  // console.log(res, 'zxzxzxzxzxzxzxzxzxzxzxzxzxzx')
          if(res.data.status == '200') {
            this.getFileName(res.data.results, 'sb')
            // this.sbData.push({'docid':res.data.results, 'docname': file.file.name});
            // this.currentRow.ssPaymentProveAf = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // 上传保密承诺书
      changebmFile(file, bmData) {
        //  // console.log(file, fileList, '上传文件状态改变')
        var formData = new FormData();
        formData.append("file", file.file);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        const loading = common.loading(this);
        upload(data).then(res => {
          //  // console.log(res, 'zxzxzxzxzxzxzxzxzxzxzxzxzxzx')
          loading.close();
          if(res.data.status == '200') {
            this.getFileName(res.data.results, 'bm')
            // this.bmData.push({'docid':res.data.results, 'docname': file.file.name});
            // this.currentRow.confidentCommitmentAf = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // 上传身份证正反面
      changesfzFile(file, sfzData) {
        //  // console.log(file, fileList, '上传文件状态改变')
        var formData = new FormData();
        formData.append("file", file.file);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        const loading = common.loading(this);
        upload(data).then(res => {
          loading.close();
          //  // console.log(res, 'zxzxzxzxzxzxzxzxzxzxzxzxzxzx')
          if(res.data.status == '200') {
            this.getFileName(res.data.results, 'sfz')
            // this.sfzData.push({'docid':res.data.results, 'docname': file.file.name});
            // this.currentRow.otherFileAf = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // 上传人脸照片
      changefacephotoFile(file, rlData) {
        //  // console.log(file, fileList, '上传文件状态改变')
        var formData = new FormData();
        formData.append("file", file.file);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        const loading = common.loading(this);
        upload(data).then(res => {
          //  // console.log(res, 'rlrlrlrl')
          loading.close();
          if(res.data.status == '200') {
            this.getFileName(res.data.results, 'rl')
            // this.rlData.push({'docid':res.data.results, 'docname': file.file.name});
            this.currentRow.faceimageAf = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      beforeAvatarUpload(file) {
        // console.log('上传限制', file);
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 < 150;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 150kb!');
        }
        return isJPG && isLt2M;
      },
      // 删除上传
      delUpload(type,docid) {
        if (type == 'zxbg') {
          let zxData = this.zxData.filter(item => item.docid != docid);
          this.zxData = zxData;
          this.cleanData(type);
        } else if (type == 'sbbg') {
          let sbData = this.sbData.filter(item => item.docid != docid);
          this.sbData = sbData;
          this.cleanData(type);
        } else if (type == 'bmbg') {
          let bmData = this.bmData.filter(item => item.docid != docid);
          this.bmData = bmData;
          this.cleanData(type);
        } else if (type == 'sfzbg') {
          let sfzData = this.sfzData.filter(item => item.docid != docid);
          this.sfzData = sfzData;
          this.cleanData(type);
        } else if (type == 'rlbg') {
          let rlData = this.rlData.filter(item => item.docid != docid);
          this.rlData = rlData;
          this.cleanData(type);
          this.$forceUpdate
        } else if (type == 'jdbg') {
          let jdData = this.jdData.filter(item => item.docid != docid);
          this.jdData = jdData;
          this.cleanData(type);
        } else if (type == 'qtfj') {
          let qtData = this.qtData.filter(item => item.docid != docid);
          this.qtData = qtData;
          this.cleanData(type);
        }
      },
      // 清洗数据
      cleanData(type){
        if (type == 'zxbg') {
            let creditReport = [];
            this.zxData.forEach(item => {
                creditReport.push(item.docid);
            });
            this.$set(this.formData.updateInfoDtos[0], 'creditReports', creditReport);
            this.$refs['formData'].validateField('creditReports');
        } else if (type == 'sbbg') {
            let confidentialityCommitment = [];
            this.sbData.forEach(item => {
                confidentialityCommitment.push(item.docid);
            });
            this.$set(this.formData.updateInfoDtos[0], 'ssPaymentProves', confidentialityCommitment);
            this.$refs['formData'].validateField('ssPaymentProves');
        } else if (type == 'sbbg') {
            let sociaiSecrityPaymentProve = [];
            this.sfzData.forEach(item => {
                sociaiSecrityPaymentProve.push(item.docid);
            });
            this.$set(this.formData.updateInfoDtos[0], 'otherFiles', sociaiSecrityPaymentProve);
            this.$refs['formData'].validateField('otherFiles');
        } else if (type == 'sfzbg') {
            let otherFile = [];
            this.rlData.forEach(item => {
                otherFile.push(item.docid);
            });
            this.$set(this.formData.updateInfoDtos[0], 'faceimages', otherFile);
            this.$refs['formData'].validateField('faceimages');
        } else if (type == 'rlbg') {
            let otherFile = [];
            this.rlData.forEach(item => {
                otherFile.push(item.docid);
            });
            this.$set(this.formData.updateInfoDtos[0], 'faceimages', otherFile);
            this.$refs['formData'].validateField('faceimages');
        } else if (type == 'jdbg') {
            let dueDiligenceReport = [];
            this.jdData.forEach(item => {
                dueDiligenceReport.push(item.docid);
            });
            this.$set(this.formData.updateInfoDtos[0], 'dueDiligenceReports', dueDiligenceReport);
            this.$refs['formData'].validateField('dueDiligenceReports');
        } else if (type == 'qtfj') {
            let qtFile = [];
            this.qtData.forEach(item => {
                qtFile.push(item.docid);
            });
            this.$set(this.formData.updateInfoDtos[0], 'qtFiles', qtFile);
            this.$refs['formData'].validateField('qtFiles');
        }
      },
      //ntko插件预览
      openNtkoWindow(obj, mark) {
        // console.log('obj', obj);
        let formJudge = obj.docname.split(".")[
            obj.docname.split(".").length - 1
        ];
        // console.log('formJudge', formJudge);
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
          // // console.log('obj.docid', obj.docid);
          this.ntkoYl(obj.docid)
        }
      },

      // ntko预览方法
      ntkoYl(objectId) {
        // console.log('objectId',objectId)
        var url = window.location.href;
        // console.log('url',url)
        let ntkoed = ntkoBrowser.ExtensionInstalled();
        if (ntkoed) {
          ntkoBrowser.openWindow(
            url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId,
            // console.log( url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId)
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
      // 保存
      saveInterview() {
       const loading = common.loading(this);
       //变更后
       let preCheck = this.preCheck();
       if(preCheck !== "true") {
          this.$message.error(preCheck);
          loading.close();
          return;
       }
       this.formData.updateInfoDtos[0].evaluationTimeBe = this.evaluatorDate;
       this.formData.updateInfoDtos[0].deptIdAf = this.depValue.id.replace("dep", "");
       this.formData.updateInfoDtos[0].systemNoAf = this.systemsValue.id;
       this.formData.updateInfoDtos[0].personInChargeAf = this.organizersValue.badge;
       this.formData.updateInfoDtos[0].evaluationLevelAf = this.levelValue.dictid;
       this.formData.updateInfoDtos[0].levelReasonAf = this.levelReason;
       this.formData.updateInfoDtos[0].evaluatorIdAf = this.evaluatorValue.badge;
       this.formData.updateInfoDtos[0].creditReportAf = this.zxData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].ssPaymentProveAf = this.sbData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].confidentCommitmentAf = this.bmData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].otherFileAf = this.sfzData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].faceimageAf = this.rlData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].dueDiligenceReportAf = this.jdData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].qtFileAf = this.qtData.map(item => {
          return item.docid
        }).join(",");
        this.$refs['formData'].validate(valid => {
          loading.close();
          if(valid) {
            createFlow(this.formData).then(res => {
              if (res.data.status == "200") {
                this.$nextTick(() => {
                  this.$message.success("保存成功")
                  this.$router.push({ name: 'wbrzManage' });
                })
              } else {
                  this.$message.error(res.data.message);
              }
            })
          } else {
              // loading.close();
              return  this.$message.error('请完整填写数据');
          }
        })
      },
      // 提交
      onSubmit() {
        const loading = common.loading(this);
        // console.log('onSubmit',this.formData)
        //变更后
        let preCheck = this.preCheck();
        if(preCheck !== "true") {
          this.$message.error(preCheck);
          loading.close();
          return;
        }
        this.formData.updateInfoDtos[0].deptIdAf = this.depValue.id.replace("dep", "");
        this.formData.updateInfoDtos[0].systemNoAf = this.systemsValue.id;
        this.formData.updateInfoDtos[0].personInChargeAf = this.organizersValue.badge;
        this.formData.updateInfoDtos[0].evaluationLevelAf = this.levelValue.dictid;
        this.formData.updateInfoDtos[0].levelReasonAf = this.levelReason;
        this.formData.updateInfoDtos[0].evaluatorIdAf = this.evaluatorValue.badge;
        this.formData.updateInfoDtos[0].evaluationTimeBe = this.evaluatorDate;
        this.formData.updateInfoDtos[0].evaluationTime = new Date();
        this.formData.updateInfoDtos[0].creditReportAf = this.zxData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].ssPaymentProveAf = this.sbData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].confidentCommitmentAf = this.bmData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].otherFileAf = this.sfzData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].faceimageAf = this.rlData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].dueDiligenceReportAf = this.jdData.map(item => {
          return item.docid
        }).join(",");
        this.formData.updateInfoDtos[0].qtFileAf = this.qtData.map(item => {
          return item.docid
        }).join(",");
        this.$refs['formData'].validate(valid => {
          loading.close();
          if(valid) {
            initFlow(this.formData).then(res => {
            // console.log(res, '提交')
            if (res.data.status == "200") {
              let message = common.getFlowMessgeNew(res.data.message);
              let messageArr = message.split(",");
              this.$nextTick(() => {
                this.messageNotify(messageArr);
                // 此处更换路径
                this.$router.push({ name: 'wbrzManage' });
              })
            } else {
              this.$message.error(res.data.message);
            }
            })
          } else {
              // loading.close();
              return  this.$message.error('请完整填写数据');
          }
        })
      },
      //校验必输项
      preCheck() {
        if(!this.depValue.id) {
          return "请选择部门";
        }
        if(!this.systemsValue.id) {
          return "请选择系统";
        }
        if(!this.organizersValue.badge) {
          return "请选择项目经理";
        }
        if (!this.levelValue.dictid) {
          return "请选择人员级别";
        }
        if (!this.levelReason || this.levelReason.trim().length === 0) {
          return "请填写定级理由";
        }
        if (this.levelReason.length > 200) {
          return "定级理由不能超过200字";
        }
        return "true";
      },
      // 关闭
      toClose() {
        this.$router.push('/wbrzManage')
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
      // handlenewContractIdAf(val) {
      //   this.$set(this.formData.updateInfoDtos[0], 'newContractIdAf', val)
      // }
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

  .delButt {
    cursor: pointer;
  }
  .upload_scss .float_box {
    display: flex;
    width: 100%;
    justify-content: space-between;
    align-items: center;
  }
  .overflow_ellipsis {
    display: inline-block;
    width: 60px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .archives-title-name {
    font-size: 15px;
    font-weight: 600;
  }
  .file_box .file_box_title {
      font-size: 15px;
      font-weight: 600;
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
