<!-- 人力结算人员准入申请第一步之基本信息 -->
<template>
  <div class="First First_other">
    <div class="contentFirst_title">
      <p class="contentFirst_title_p1">基本信息</p>
      <uploadExcel class="upload-demo" @exData="exALLDa"></uploadExcel>
      <span class="mbxz" @click="mbDownload">模板下载</span>
    </div>
    <el-form :model="jibenData" ref="ruleJiben">
      <div class="contentFirst">
        <!-- layout布局1 -->
        <div class="row-jiben">
          <el-row :gutter="50">
            <el-col :span="8">
              <p>姓名 *</p>
              <el-form-item
                prop="userName"
                :rules="[{ required: true, message: '请填写姓名', trigger: 'blur'}, {
                                pattern: '^[\u4e00-\u9fa5]+$', message: '姓名有误，仅支持中文，不能包含空格'
                            }]"
              >
                <el-input
                  v-model.lazy.trim="jibenData.userName"
                  placeholder="请输入"
                  :disabled="false"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>工号 *</p>
              <el-form-item
                prop="jobId"
                :rules="[{ required: true, message: '请填写工号', trigger: 'blur'}]"
              >
                <el-input v-model="jibenData.jobId" placeholder="请输入" :disabled="false "@input="val => jibenData.jobId = val.replace(/[\s\r\n]+/g, '')"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>性别 *</p>
              <el-form-item
                prop="sex"
                :rules="[{ required: true, message: '请选择性别', trigger: 'change'}]"
              >
                <el-radio-group v-model="jibenData.sex" :disabled="false">
                  <el-radio
                    v-for="item in sex_options"
                    :label="item.dictid"
                    :key="item.dictid"
                  >{{item.dictname}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!-- 表第二行 -->
        <div class="row-jiben">
          <el-row :gutter="50">
            <el-col :span="8">
              <p>身份证号 *</p>
              <el-form-item
                prop="certifNumber"
                :rules="[{ required: true, message: '请填写身份证号', trigger: 'blur'}, {
                                pattern: /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '身份证格式有误'
                            }]"
              >
                <el-input
                  v-model.lazy.trim="jibenData.certifNumber"
                  placeholder="请输入"
                  :disabled="false"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>籍贯 *</p>
              <el-form-item
                prop="nativeplace"
                :rules="[{ required: true, message: '请填写籍贯', trigger: 'blur'}, {
                                pattern: '^[\u4e00-\u9fa5]+$', message: '籍贯有误，仅支持中文，不能包含空格'
                            }]"
              >
                <el-input
                  v-model.lazy.trim="jibenData.nativeplace"
                  placeholder="请输入"
                  :disabled="false"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>婚姻状况 *</p>
              <el-form-item
                prop="maritalStatus"
                :rules="[{ required: true, message: '请选择婚姻状况', trigger: 'change'}]"
              >
                <el-radio-group v-model="jibenData.maritalStatus" :disabled="false">
                  <el-radio
                    v-for="(item,index) in marriage_options"
                    :label="item.dictid"
                    :key="index"
                  >{{item.dictname}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!-- 表第三行 -->
        <div class="row-jiben">
          <el-row :gutter="50">
            <el-col :span="8">
              <p>毕业院校 *</p>
              <el-form-item
                prop="school"
                :rules="[{ required: true, message: '请填写毕业院校', trigger: 'blur'}]"
              >
                <el-input
                  v-model="jibenData.school"
                  placeholder="请输入"
                  @blur="schoolChange('school')"
                  :disabled="false"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>专业 *</p>
              <el-form-item
                prop="specialty"
                :rules="[{ required: true, message: '请填写专业', trigger: 'blur'}]"
              >
                <el-input v-model="jibenData.specialty" placeholder="请输入" :disabled="false"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>学历 *</p>
              <el-form-item
                prop="education"
                :rules="[{ required: true, message: '请选择学历', trigger: 'change'}]"
              >
                <el-radio-group v-model="jibenData.education" :disabled="false">
                  <el-radio
                    v-for="item in edu_options"
                    :label="item.dictid"
                    :key="item.dictid"
                  >{{item.dictname}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!-- 表第四行 -->
        <div class="row-jiben">
          <el-row :gutter="50">
            <el-col :span="8">
              <p>联系方式 *</p>
              <el-form-item
                prop="mobile"
                :rules="[{ required: true, message: '请填写联系方式', trigger: 'blur'}, {
                                pattern: /^[0-9]\d{10}$/, message: '联系方式有误，仅支持11位数字', trigger: 'blur'
                            }]"
              >
                <el-input v-model="jibenData.mobile" placeholder="请输入" :disabled="false"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <div class="picker_sty_fir">
                <p>生日 *</p>
                <el-form-item
                  prop="birthday"
                  :rules="[{ required: true, message: '请选择生日', trigger: 'change'}]"
                >
                  <el-date-picker
                    v-model="jibenData.birthday"
                    type="date"
                    placeholder="请选择日期"
                    :disabled="false"
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <p>入职日期 *</p>
              <el-form-item
                prop="entryTime"
                :rules="[{ required: true, message: '请选择入职日期', trigger: 'change'}]"
              >
                <el-date-picker
                  v-model="jibenData.entryTime"
                  type="date"
                  placeholder="请选择日期"
                  :disabled="false"
                  value-format="yyyy-MM-dd"
                  :picker-options="pickerOptions0"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            
          </el-row>
        </div>
        <!-- 表第五行 -->
        <div class="row-jiben">
          <el-row :gutter="50">
            <el-col :span="8">
              <p class="deptno_p">所属部门 *</p>
              <el-form-item prop="deptno" :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]" >
                <el-select v-model="depValue" filterable placeholder="请选择" value-key="id" @change="getDepId" :disabled="false" >
                  <el-option v-for="(item,index) in deptno_options" :key="index" :value="item" :label="item.text" ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
             <el-col :span="8">
              <p>岗位 *</p>
              <el-form-item
                prop="position"
                :rules="[{ required: true, message: '请选择岗位', trigger: 'change'}]"
              >
                <el-select
                  v-model="jibenData.position"
                  filterable
                  placeholder="请选择"
                  @change="positionChange"
                  :disabled="false"
                >
                  <el-option
                    v-for="item in position_options"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>工作内容</p>
              <el-form-item
                prop="workContent"
                :rules="[{ required: false, message: '请填写工作内容', trigger: 'blur'}]"
              >
                <el-input
                  type="textarea"
                  autosize
                  v-model="jibenData.workContent"
                  placeholder="请输入"
                  :disabled="false"
                ></el-input>
              </el-form-item>
            </el-col>
          
          </el-row>
        </div>
        <!-- 表第六行 -->
        <div class="row-jiben">
          <el-row :gutter="50">
          </el-row>
        </div>
        <!-- 表第七行 -->
        <div class="row-jiben" v-if="this.char == 1">
          <el-row :gutter="50">
            <el-col :span="8">
             <div class="wbrzxmmc">
                <p>项目名称 *</p>
                <el-form-item prop="projectno"
                  :rules="[{ required: true, message: '请选择项目名称'}]">
                  <el-select v-model="projectsValue" filterable
                    @focus="cueMessage" @change="searchOrganizers" 
                    value-key="id" :disabled="false">
                    <el-option
                      v-for="(item) in projectno_options" 
                      :key="item.id"
                      :label="item.name"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <p>项目经理 *</p>
              <el-form-item
                prop="organizers"
                :rules="[{ required: true, message: '请选择项目经理'}]"
              >
                <el-select
                  v-model="organizersValue"
                  filterable
                  placeholder="请选择"
                  value-key="badge"
                  @focus="cueMessage"
                  :disabled="false"
                  @change="organizersChange"
                >
                  <el-option
                    v-for="(item) in organizers_options"
                    :key="item.badge"
                    :label="item.empname"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              
            </el-col>
          </el-row>
        </div>

        <!-- 表第八行 -->
        <div class="row-jiben" v-else>
          <el-row :gutter="50">
            <el-col :span="8">
              <p>外包商 *</p>
              <el-form-item
                prop="outsourceNo"
                :rules="[{ required: true, message: '请选择外包商', trigger: 'change'}]"
              >
                <el-select
                  v-model="jibenData.outsourceNo"
                  filterable
                  placeholder="请选择"
                  :disabled="false"
                >
                  <el-option
                    v-for="item in outsourceno_options"
                    :key="item.id"
                    :label="item.outsourcname"
                    :value="item.orgcode"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>系统名称 *</p>
               <el-form-item
                prop="systemnos"
                :rules="[{ required: true, message: '请选择系统名称'}]"
                >
                <el-select v-model="systemsValue" filterable
                  @focus="cueMessage" @change="searchOrganizers" 
                  value-key="id" :disabled="false">
                    <el-option
                      v-for="(item)  in systemnos_options"
                      :key="item.id"
                      :label="item.name"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>项目经理 *</p>
              <el-form-item
                prop="organizers"
                :rules="[{ required: true, message: '请选择项目经理'}]"
              >
                <el-select
                  v-model="organizersValue"
                  filterable
                  placeholder="请选择"
                  value-key="badge"
                  @focus="cueMessage"
                  :disabled="false"
                  @change="organizersChange"
                >
                  <el-option
                    v-for="(item) in organizers_options"
                    :key="item.badge"
                    :label="item.empname"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
            </el-col>
          </el-row>
        </div>
      </div>
    </el-form>
  </div>
</template>
<script>
import { getBasicInformation } from "@/api/userApi/wbry";
import {
  getTree,
  queryResourceDept,
  queryGroup,
  querySystemList,
  queryProjectList,
  queryApplyList,
  querySystemEmp,
  queryProjectEmp,
  queryEmployeeTree,
  querySystemTree,
  getSystemPeo,
  queryWorkItem,
  getProcurements,
  validMotion,
  validContract
} from "@/api/integrated/wbryzrlc";
import {
  getJobid,
  saveDraft,
  getCheckedIdCard,
  checkedExistEntry
} from "@/api/integrated/newWbrz";
import { getDicEntry } from "@/api/eosDictEntry";
import common from "@/api/common";
import uploadExcel from "@/components/UploadExcel"; //导入上传表格组件
import XLSX from "xlsx";
import { filterArr } from "@/utils/filterArray";
import {
  getListAll,
  getProjectDetail,
  getEmployUser,
  getDownloadUrl
} from "@/api/common/workflowApi";
import { checkedIsBlack } from "@/api/integrated/wbHmd";
export default {
  components: { uploadExcel },
  data() {
    return {
      jibenData: {
        userName: "", //姓名
        jobId: "", //工号
        sex: "", //性别
        mobile: "", //联系电话
        birthday: "", //生日
        maritalStatus: "", //婚姻状况
        education: "", //学历
        school: "", //毕业院校
        specialty: "", //专业信息
        nativeplace: "", //籍贯
        outsourceNo: "", //外包商名称
        deptno: "", //部门编号
        depName: "", //部门名字
        outsourceType: "", //外包方式
        userType: "", //结算方式
        groupId: "", //小组
        position: "", //岗位
        workContent: "", //工作内容
        entryTime: "", //入职时间
        projectno: "", //项目编号
        isOtherProject: false, //是否为其他项目
        systemnos: "", //系统名称
        managerUserid: "", //项目经理id
        systemPrincipal: "", //系统负责人
        organizers: "", //行内负责人
        organizersName: "", //行内负责人名字
        purchaseItem: "", //采购项目（没有）
        // learnExperience: [], //学习经历
        mainJobs: '[]', //工作经历
        mainItem: '[]', //项目经历
        mainReward: '[]', // 奖励情况
        mainFamily: '[]', //家庭情况
        certifNumber: "", //证件号码
        address: "", //当前住址
        certificate: "", //所获证书
        politicalStatus: "", //政治面貌
        id: "",
        instanceId: "", //流程ID
        eneralOfficeMotion: '', // 总办议案
        newContractId: "", // 合同
      },
      managerUsername: "", //项目经理名称
      systemPrincipalname: "", //系统负责人名称
      depValue: {}, //部门数据
      organizersValue: {}, //行内负责人数据
      systemsValue: {}, //系统信息
      projectsValue: {}, //项目信息
      edu_options: [], //学历数据
      sex_options: [],
      outsourceno_options: [],
      deptno_options: [], 
      outsourcetype_options: [],
      usertype_options: [],
      groupid_options: [],
      projectno_options: [],
      organizers_options: [],
      systemnos_options: [],
      purchaseItem_options: [],
      purchaseItemCopy: [],
      position_options: [],
      marriage_options: [],
      depData: [],
      counts: 10, //初始加载10条
      deptno_sea: "", //部门搜索输入的值
      upTableData: "", //上传的表格数据
      char: "", //判断是项目还是人力
      tableRow: "", //选中行数据
      front: true, //选择面试人员或者简历前后
      xmmcIsRequired: true, //项目名称是否必填
      xmmcIsEdit: false, //项目名称是否可编辑
      msInstanceid: "", //面试流程Id
      pickerOptions0: {
        // disabledDate(time) {
        //   return time.getTime() < Date.now() - 8.64e7;
        // }
      },
      disabled: true, // 是否只读，默认true
      purchaseCheckedData: {}, // 采购项目选中的数据
      ceneralOfficeMotion_options: [], // 总办议案
      newContractId_options: [], // 合同
    };
  },
  props: {
    bps: {
      type: Object,
      required: true
    }
  },
  created() {
    console.log("基本信息页");
    this.init();
    //加载下拉框数据
    this.filDep();
    this.getWbcom();
    this.getvalidMotion()
  },
  mounted() {
    this.char = this.$route.query.char;
  },
  watch: {
    front(val) {
      this.$emit("banIf", val);
    }
  },
  methods: {
    // 页面初始化
    init: function() {
      getDicEntry({
        dicttypeid: "BUSI_OUTSOURCE_GENFER,BUSI_OUTSOURCE_MARITALSTATUS,NEWYY_EDUCATION,NEWKG_WBFS,NEWKG_JSFS"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        // 性别
        this.sex_options = data.filter(item => {
          return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
        });
        // 婚姻状况
        this.marriage_options = data.filter(item => {
          return item.dicttypeid == "BUSI_OUTSOURCE_MARITALSTATUS";
        });
        // 学历
        this.edu_options = data.filter(item => {
          return item.dicttypeid == "NEWYY_EDUCATION";
        });

        //外包方式
        this.outsourcetype_options = data.filter(item => {
          return item.dicttypeid == "NEWKG_WBFS";
        });
        console.log('this.outsourcetype_options', this.outsourcetype_options)
        //结算方式
        this.usertype_options = data.filter(item => {
          return item.dicttypeid == "NEWKG_JSFS";
        });
        return data;
      });
    },

    // 获取岗位
    getPosition(val) {
      getDicEntry({
        dicttypeid: "BUSI_OUTSOURCE_POSITION"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //岗位
        if (val.id == "dep3634") {
          this.position_options = data;
        } else {
          this.position_options = data.filter(item => {
            return item.dictid != "17";
          });
        }
        return data;
      });
    },

    // 模板下载
    mbDownload() {
      getDownloadUrl({ key: "PPM_NEW_WBZR" }).then(res => {
        if (res.data.status == 200) {
          const self = this;
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error("获取下载地址失败");
        }
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

    //查询项目名称
    searchProject() {
      let params = {};
      let depId = this.jibenData.deptno;
      params.depId = depId.replace("dep", "");
      queryApplyList(params).then(res => {
          this.projectno_options = res.data.results;
        }).catch(error => {
          this.$message.error("查询项目名称失败");
        });
    },
    //查询系统名称
    searchSystem() {
      let params = {};
      let depId = this.jibenData.deptno;
      params.depId = depId.replace("dep", "");
      querySystemList(params).then(res => {
          this.systemnos_options = res.data.results;
        }).catch(error => {
         this.$message.error("查询系统名称失败");
        });
    },
    
    // 查询行内负责人
    searchOrganizers(val) {
      if(this.jibenData.organizers != ""){
        this.organizersValue = {};
        this.jibenData.organizers = ""; 
      }
      if(this.char == "0"){
        this.jibenData.systemnos = val.id;
        this.searchSystemEmp(val);
      }else{
        this.jibenData.projectno = val.id;
        this.searchProjectEmp(val);
      }
    },

    //查询项目经理
    searchProjectEmp(val) {
      let params = {};
      params.id = val.id;
      queryProjectEmp(params).then(res => {
        let projectRet = {};
        let results = res.data.results;
        const deptNo = this.jibenData.deptno.replace('dep','');
        let i = 0;
        results.forEach(element => {
          if (element.depid == deptNo) {
            projectRet[i] = element;
            i++;
          }
        });
        
        this.organizers_options = projectRet;
        // this.organizersValue = res.data.results[0].empname; 
        // this.jibenData.organizers = res.data.results[0].badge; 
        // this.organizersChange(res.data.results);
      }).catch(error => {
        console.log(error)
          this.$message.error("查询项目经理失败");
      });
    },

    //查询系统负责人
    searchSystemEmp(val) {
      let params = {};
      params.id = val.id;
      querySystemEmp(params).then(res => {
          this.organizers_options = res.data.results;
      }).catch(error => {
          this.$message.error("查询项目经理失败");
      });
    },
    //查询小组名称
    searchGroup() {
      let params = {};
      let depid = this.jibenData.deptno;
      params.depid = depid.replace("dep", "");
      queryGroup(params).then(res => {
        if (res.data.status == 200) {
          this.groupid_options = res.data.results;
        } else {
          this.$message.error("查询小组数据失败");
        }
      }).catch(error => {
        this.$message.error(error);
      });
    },
    // 获取工号
    getJob() {
      getJobid().then(res => {
        if (res.data.status == 200) {
          this.jibenData.jobId = res.data.results.jobId;
        } else {
          this.$message.error("获取工号失败");
        }
      }).catch(error => {
        this.$message.error(error);
      });
    },

    // 查询所有外包商
    getWbcom() {
      getListAll()
        .then(res => {
          this.outsourceno_options = res.data.results;
        }).catch(error => {
          this.$message.error(error);
        });
    },

    //没有选择部门时的提示信息
    cueMessage() {
      if (this.jibenData.deptno == "") {
        this.$message("请先选择部门");
      }
    },

    // 下拉框触底事件
    loadMore() {
      if (this.deptno_sea == "") {
        if (this.counts < this.depData.length) {
          this.counts += 10;
        }
        var fildnF = this.depData.slice(0, this.counts);
        this.deptno_options = fildnF;
      }
    },

    // 部门变化
    getDepId(val) {
      this.jibenData.deptno = val.id;
      this.jibenData.depName = val.text;
      this.jibenData.groupId = ""; 
      this.jibenData.position = ""; 
      this.organizers_options = {};
      if(this.jibenData.organizers != ""){
        this.organizersValue = {};
        this.projectsValue = {};
        this.systemsValue = {};
        this.jibenData.projectno = ""; 
        this.jibenData.systemnos = ""; 
        this.jibenData.organizers = ""; 
      }
      this.$nextTick(() => {
        this.searchGroup();
        this.getPosition(val);
        if(this.char == "0"){
          this.searchSystem();
        } else{
          this.searchProject();
        }
      });
    },
    //行内负责人变化
    organizersChange(val) {
      this.jibenData.organizers = val.badge;
      this.jibenData.organizersName = val.empname;
      if(this.char == "0"){
         this.jibenData.systemPrincipal = val.badge;
      } else{
         this.jibenData.managerUserid = val.badge;
      }
      // console.log("organizersChange->",this.jibenData)
      // 选中后发给父组件
      this.$emit("on-organizer-select", val);
    },
     // 岗位变化
    positionChange(val) {
      if (val == "11") {
        this.xmmcIsRequired = false;
      } else {
        this.xmmcIsRequired = true;
      }
    },

    // 选中行触发的事件
    handleCurrentChange(val) {
      this.tableRow = val;
    },
    // 取消选择或选中某行
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
    },

    //获取导入excel传来的数据
    exALLDa(v) {
      console.log(v.header[0], 'cccccccccccccccccccccccccccccccccccccc')
      if(v.header[0] !== '宁银消金金融科技部外包员工简历') {
        return this.$message.error("简历模板错误，请检查表头等信息是否正确")
      }
      if (v != "") {
        // 获取证件号码
        var reg = /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
        if (reg.test(v.results[3].__EMPTY_1)) {
          if (common.checkSfzhLast(v.results[3].__EMPTY_1)) {
            this.jibenData.certifNumber = v.results[3].__EMPTY_1;
            // 查询面试信息面试信息流程ID
            this.msInstanceid = "flag";
            // this.searchMsinfo(v.results[3].__EMPTY_1);
          } else {
            this.$message.error("身份证号错误，请检查");
            return;
          }
        } else {
          this.$message.error("身份证号格式有误");
          return;
        }
        this.jibenData.userName = v.results[1].__EMPTY_1;
        if (v.results[1].__EMPTY_3 == "男") {
          this.jibenData.sex = "0";
        } else if (v.results[1].__EMPTY_3 == "女") {
          this.jibenData.sex = "1";
        }
        this.jibenData.nativeplace = v.results[2].__EMPTY_5;
        // 从身份证获取生日
        this.jibenData.birthday = common.getBirthdayFromIdCard(
          v.results[3].__EMPTY_1
        );
        if (v.results[2].__EMPTY_3 == "未婚") {
          this.jibenData.maritalStatus = "1";
        } else if (v.results[2].__EMPTY_3 == "已婚") {
          this.jibenData.maritalStatus = "2";
        } else if (v.results[2].__EMPTY_3 == "离异") {
          this.jibenData.maritalStatus = "3";
        } else if (v.results[2].__EMPTY_3 == "丧偶") {
          this.jibenData.maritalStatus = "4";
        }
        this.jibenData.school = v.results[4].__EMPTY_3;
        this.jibenData.specialty = v.results[4].__EMPTY_5;
        if (v.results[4].__EMPTY_1 == "大专") {
          this.jibenData.education = "1";
        } else if (v.results[4].__EMPTY_1 == "本科") {
          this.jibenData.education = "2";
        } else if (v.results[4].__EMPTY_1 == "硕士") {
          this.jibenData.education = "3";
        } else if (v.results[4].__EMPTY_1 == "博士") {
          this.jibenData.education = "4";
        }
        this.jibenData.mobile = v.results[5].__EMPTY_3;
        // if (this.front == true) {
        //   this.getJob();
        // }
        this.front = false;
        if (this.char == 0) {
          this.jibenData.outsourceType = "1";
          this.jibenData.userType = "0";
        } else {
          this.jibenData.outsourceType = "0"; // 项目外包
          this.jibenData.userType = "1"; // 项目结算
          this.disabled = false;
          
        }
        // 获取政治面貌
        if (v.results[2].__EMPTY_1 == "中共党员") {
          this.jibenData.politicalStatus = "1";
        } else if (v.results[2].__EMPTY_1 == "民主党派成员") {
          this.jibenData.politicalStatus = "2";
        } else if (v.results[2].__EMPTY_1 == "共青团员") {
          this.jibenData.politicalStatus = "3";
        } else if (v.results[2].__EMPTY_1 == "群众") {
          this.jibenData.politicalStatus = "4";
        }
        // 获取家庭住址
        this.jibenData.address = v.results[3].__EMPTY_3;
        // 所获证书
        this.jibenData.certificate = v.results[6].__EMPTY_1;
        //获取表格内容
        let learnExperience = [];
        let startCountStudy;
        let endCountStudy;
        let mainJobs = [];
        let startCountWork;
        let endCountWork;
        let mainItem = [];
        let startCountItem;
        let endCountItem;
        let mainReward = [];
        let startCountReward;
        let endCountReward;
        let mainFamily = [];
        let startCountFamily;
        let endCountFamily;
        for (var i = 0; i < v.results.length; i++) {
          if (v.results[i].__EMPTY_1 == "学校") {
            startCountStudy = i + 1;
          }
          if (v.results[i].宁银消金金融科技部外包员工简历 == "主要工作经历") {
            endCountStudy = i;
          }
          if (v.results[i].__EMPTY_1 == "单位") {
            startCountWork = i + 1;
          }
          if (v.results[i].宁银消金金融科技部外包员工简历 == "主要项目经历") {
            endCountWork = i;
          }
          if (v.results[i].__EMPTY_1 == "项目名称") {
            startCountItem = i + 1;
          }
          if (v.results[i].宁银消金金融科技部外包员工简历 == "主要奖励记录") {
            endCountItem = i;
          }
          if (v.results[i].__EMPTY_1 == "奖励名称") {
            startCountReward = i + 1;
          }
          if ( 
            v.results[i].宁银消金金融科技部外包员工简历 == 
            "家庭主要成员（直系亲属及主要社会关系）" 
            ) {
            endCountReward = i;
          }
          if (v.results[i].__EMPTY_1 == "称谓") {
            startCountFamily = i + 1;
          }
          if (
            v.results[i].宁银消金金融科技部外包员工简历 ==
            "如果此次应聘成功，您需要的通知期时间。"
          ) {
            endCountFamily = i;
          }
        }
        learnExperience = v.results.slice(startCountStudy, endCountStudy);
        mainJobs = v.results.slice(startCountWork, endCountWork);
        mainItem = v.results.slice(startCountItem, endCountItem);
        mainReward = v.results.slice(startCountReward, endCountReward);
        mainFamily = v.results.slice(startCountFamily, endCountFamily);   
        learnExperience = learnExperience.filter(li => {
          li.studyDate = [];
          li.studyDate[0] = li.宁银消金金融科技部外包员工简历;
          li.studyDate[1] = li.__EMPTY;
          li.schoolName = li.__EMPTY_1;
          li.professName = li.__EMPTY_3;
          if (li.__EMPTY_4 == "大专") {
            li.eduGenre = "1";
          } else if (li.__EMPTY_4 == "本科") {
            li.eduGenre = "2";
          } else if (li.__EMPTY_4 == "硕士") {
            li.eduGenre = "3";
          } else if (li.__EMPTY_4 == "博士") {
            li.eduGenre = "4";
          } else {
            li.eduGenre = "5";
          }
          if (li.__EMPTY_5 == "全日制") {
            li.studyForm = "1";
          } else {
            li.studyForm = "2";
          }
          delete li.宁银消金金融科技部外包员工简历;
          delete li.__EMPTY;
          delete li.__EMPTY_1;
          delete li.__EMPTY_3;
          delete li.__EMPTY_4;
          delete li.__EMPTY_5;
          return li;
        });

        mainJobs = mainJobs.filter(li => {
          li.workDate = [];
          li.workDate[0] = li.宁银消金金融科技部外包员工简历;
          li.workDate[1] = li.__EMPTY;
          li.comName = li.__EMPTY_1;
          li.mainWork = li.__EMPTY_3;
          delete li.宁银消金金融科技部外包员工简历;
          delete li.__EMPTY;
          delete li.__EMPTY_1;
          delete li.__EMPTY_3;
          return li;
        });

        mainItem = mainItem.filter(li => {
          li.projDate = [];
          li.projDate[0] = li.宁银消金金融科技部外包员工简历;
          li.projDate[1] = li.__EMPTY;
          li.projName = li.__EMPTY_1;
          li.mainWork = li.__EMPTY_3;
          delete li.宁银消金金融科技部外包员工简历;
          delete li.__EMPTY;
          delete li.__EMPTY_1;
          delete li.__EMPTY_3;
          return li;
        });

        mainReward = mainReward.filter(li => {
          li.prizeDate = li.宁银消金金融科技部外包员工简历;
          li.prizeName = li.__EMPTY_1;
          li.prizeCause = li.__EMPTY_2;
          li.prizeGrade = li.__EMPTY_5;
          delete li.宁银消金金融科技部外包员工简历;
          delete li.__EMPTY_1;
          delete li.__EMPTY_2;
          delete li.__EMPTY_5;
          return li;
        });

        mainFamily = mainFamily.filter(li => {
          li.familyName = li.宁银消金金融科技部外包员工简历;
          // li.feminist = li.__EMPTY_1;
          if (li.__EMPTY_1 == "父亲") {
            li.feminist = "1";
          } else if (li.__EMPTY_1 == "母亲") {
            li.feminist = "2";
          } else if (li.__EMPTY_1 == "配偶") {
            li.feminist = "3";
          } else if (li.__EMPTY_1 == "子女") {
            li.feminist = "4";
          } else if (li.__EMPTY_1 == "兄弟") {
            li.feminist = "5";
          } else {
            li.feminist = "6";
          }

          li.familyBirthday = li.__EMPTY_2;

          const pattern = /((\d{3}[1-9]|\d{2}[1-9]\d|\d[1-9]\d{2}|[1-9]\d{3})(((0[13578]|1[02])(0[1-9]|[12]\d|3[01]))|((0[469]|11)(0[1-9]|[12]\d|30))|(02(0[1-9]|[1]\d|2[0-8]))))|(((\d{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)/;
          let reg = pattern.test(li.familyBirthday);
          if (!reg){
            this.$message.error("简历家庭成员出生年月日日期格式有误");
          }
          
          li.unit = li.__EMPTY_3;
          delete li.宁银消金金融科技部外包员工简历;
          delete li.__EMPTY_1;
          delete li.__EMPTY_2;
          delete li.__EMPTY_3;
          return li;
        });
        this.jibenData.learnExperience = JSON.stringify(learnExperience);
        this.jibenData.mainJobs = JSON.stringify(mainJobs);
        this.jibenData.mainItem = JSON.stringify(mainItem);
        this.jibenData.mainReward = JSON.stringify(mainReward);
        this.jibenData.mainFamily = JSON.stringify(mainFamily);
        this.jibenData.certifType = "1"; //证件类型默认
        this.$nextTick(() => {
          this.searchIfCfrz(
            this.jibenData.certifNumber,
            this.jibenData.userName
          );
        });
      }
    },

    // 查询面试信息流程ID
    searchMsinfo(id) {
      checkedExistEntry(id)
        .then(res => {
          if (res.data.status == 200 && res.data.results != undefined) {
            this.msInstanceid = res.data.results.instanceid;
          } else {
            this.$message.error("查询面试信息失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询是否重复入职
    searchIfCfrz(id, name) {
      const loading = common.loading(this);
      getCheckedIdCard({ certifNumber: id, userName: name }).then(res => {
        loading.close();
        if (res.data.status == 200) {
          if (res.data.results.entry != "无") {
              this.$message.error(res.data.results.entry);
          }else{
            this.checkHmd(id);
          }
        } else {
          this.$message.error("查询入职信息失败");
        }
      });
    },

    // 检查是否在黑名单中
    checkHmd(id) {
      checkedIsBlack({ icard: id }).then(res => {
        if (res.data.status == 200) {
          console.log("黑名单", res.data);
          if (res.data.results == true) {
            this.$message({
              showClose: true,
              duration: 0,
              message: "该外包人员已被列入黑名单",
              type: "error"
            });
            this.front = true;
          }
        } else {
          this.$message.error("查询黑名单信息失败");
        }
      });
    },

    // 从本地导入excel文件
    httpRequest(e) {
      let file = e.file; //文件信息
      if (!file) {
        return false;
      } else if (!/\.(xls|xlsx)$/.test(file.name.toLowerCase())) {
        this.$message.error("上传格式不正确,请上传xls或者xlsx格式");
        return false;
      }

      const fileReader = new FileReader();
      var binary = "";
      fileReader.onload = ev => {
        try {
          const data = ev.target.result;
          const workbook = XLSX.read(data, {
            type: "binary"
          });
          const exlname = workbook.SheetNames[0];
          const exl = XLSX.utils.sheet_to_json(workbook.Sheets[exlname]); //生产jsons数据

          this.upTableData = exl;
        } catch (e) {
          return false;
        }
      };
      fileReader.readAsBinaryString(file);
    },

    //保存当前组件信息
    savejiben() {
      // if (this.msInstanceid == "flag") {
      //   this.$message.error("没有查询到面试信息,不能保存和流转");
      //   let status = new Promise(() => {});
      //   return status;
      // }
      const loading = common.loading(this);
      // 采购项目
      if (this.jibenData.purchaseItem) {
        this.jibenData.purchaseItemId = this.purchaseCheckedData.zbbh;
        this.jibenData.purchaseItem = this.purchaseCheckedData.xmmc;
      }

      this.jibenData.bps = this.bps;
      console.log("表单", this.jibenData);
      if (this.char == 0) {
          this.jibenData.outsourceType = "1";
          this.jibenData.userType = "0";
        } else {
          this.jibenData.outsourceType = "0"; // 项目外包
          this.jibenData.userType = "1"; // 项目结算
          this.disabled = false;
          
        }
      return saveDraft(this.jibenData).then(res => {
        setTimeout(function() {
          loading.close();
        }, 200);
        if (res.data.status == 200) {
          this.$message.success("保存草稿成功");
          if (res.data.results != undefined) {
            this.jibenData.id = res.data.results.id;
            this.jibenData.instanceId = res.data.results.instanceId;
            this.$emit("delBtnIf", this.jibenData.instanceId);
          }
          return [res.data, this.jibenData, this.msInstanceid];
        } else {
          this.$message.error(res.data.message);
        }
      });
    },

    //验证必填项
    verify() {
      let self = this;
      self.$refs["ruleJiben"].validate(valid => {
        self.$emit("verifyJiben", valid);
      });
    },
    /**
     * 下载提前入场申请表
     */
    handleClickDoc() {
      getDownloadUrl({ key: "XMCG_XMCGSQB" }).then(res => {
        if (res.data.status == 200) {
          const self = this;
          common.downloadJl(res.data.results.documentno, self);
        } else {
          this.$message.error("获取下载地址失败");
        }
      });
    },
    /**
     * 项目采购过滤
     */
    handleFilterCg(val) {
      let self = this;
      if (!val) {
        self.purchaseItem_options = self.purchaseItemCopy;
        return false;
      }
      // self.jibenData.purchaseItemId = val;
      self.purchaseItem_options = self.purchaseItemCopy.filter(item => {
        if (
          !!~item.xmmc.indexOf(val) ||
          (item.sysName && !!~item.sysName.indexOf(val))
        ) {
          return true;
        }
      });
      console.log("项目采购过滤", self.purchaseItem_options);
    }, // handleFilterCg end
    /**
     * 采购项目change事件
     */
    handlePurchase(val) {
      console.log("采购项目", val);
      let self = this;
      let cg_item = self.purchaseItem_options.filter(item => {
        return item.zbbh === val;
      });
      if (cg_item && cg_item.length > 0) {
        self.purchaseCheckedData = cg_item[0];
        self.jibenData.purchaseItemId = cg_item[0].zbbh;
        self.jibenData.purchaseItem = cg_item[0].xmmc;
      }
      console.log("采购项目2", self.purchaseCheckedData);
      self.$forceUpdate()
    },
    // 总办议案
    getvalidMotion() {
      validMotion({fileType:0}).then(res => {
        console.log(res, '总办议案')
        const {results, status} = res.data;
        if(status === 200) {
          this.ceneralOfficeMotion_options = results.map(item => {
            item.label =  item.fileName + "("+item.fileNo+")"
            return item;
          })
        }
      })
    },
    handleceneralOfficeMotion(val) {
        console.log('handleceneralOfficeMotion',val);
        this.jibenData.newContractId = '';
        this.getvalidContract(val);
      },
    // 合同
    getvalidContract(val) {
      console.log('合同')
      let params = {}
      params.attachInfo = val;
      if(this.char == '0') {
        params.outsourceType = '1';
      } else if(this.char == '1') {
        params.outsourceType = '0'
      }
      validContract(params).then(res => {
        // console.log(res， '合同');
        const {results, status} = res.data;
        if(status === 200) {
          this.newContractId_options = results.map(item => {
            item.label =  item.contractName + "(" + item.contractNo +")"
            return item
          })
        }
      })
    }
  }
};
</script>
<style scoped>
.contentFirst {
  width: 100%;
  /* height: 600px; */
  font-size: 16px;
  background-color: #ffffff;
  padding: 15px 20px;
}

.contentFirst p {
  color: #262626;
  font-size: 14px;
  margin: 8px 0;
}

.selp_click {
  width: 1080px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  border-radius: 2px;
  border: 1px dashed rgba(0, 0, 0, 15);
}

.contentFirst span {
  color: #909399;
}

.contentFirst .row-jiben {
  margin-bottom: 20px;
}

.contentFirst_title {
  height: 60px;
  line-height: 60px;
  background-color: #ffffff;
  padding: 0 25px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
  /* display: flex;
        align-items: center; */
}

.contentFirst_title p {
  margin: 0;
  display: inline-block;
}

/* 选择面试人员 */
.closePre {
  margin-left: 10px;
}

.mbxz {
  color: rgba(251, 164, 47, 1);
  font-size: 12px;
  cursor: pointer;
  font-weight: 500;
  font-family: sans-serif;
}

.contentFirst_title > .contentFirst_title_p1 {
  color: rgba(38, 38, 38, 1);
  font-size: 16px;
  height: 22px;
  line-height: 22px;
  font-weight: 600;
}

.contentFirst_title .contentFirst_title_p2 {
  color: rgba(251, 164, 47, 1);
  font-size: 14px;
  height: 22px;
  line-height: 22px;
  font-weight: 500;
  margin-left: 10px;
  cursor: pointer;
}

/* 部门搜索框定位 */
.deptno_search {
  position: absolute;
  top: 4px;
  z-index: 1;
}

/* 选择面试人员弹窗 */
.cloosePre {
  margin-top: 15px;
  float: right;
}
</style>

<!-- element组件样式修改 -->
<style>
.row-jiben .el-select {
  display: block;
}

.row-jiben .el-date-editor.el-input {
  width: auto;
  display: block;
}

/* el-input获得焦点样式修改(全局) */
.el-input__inner:focus {
  border-color: rgba(251, 164, 47, 1);
}

/* 下拉框获得焦点样式修改(全局) */
.el-select .el-input.is-focus .el-input__inner {
  border-color: rgba(251, 164, 47, 1);
}

.el-select .el-input__inner:focus {
  border-color: rgba(251, 164, 47, 1);
}

/* 单选框组件修改 */
/* .el-radio-group {
        height: 36px;
        display: flex;
        align-items: center;
    } */

.el-radio__input.is-checked .el-radio__inner {
  border-color: rgba(251, 164, 47, 1);
  background: rgba(251, 164, 47, 1);
}

.el-radio__input.is-checked + .el-radio__label {
  color: rgba(251, 164, 47, 1);
}

/* 带搜索下拉框 */
.el-scrollbar {
  display: block !important;
}

/* 下拉菜单 */

.First_other .el-select-dropdown__list {
  padding-top: 40px;
}

.el-date-table td.today span {
  color: rgba(251, 164, 47, 1);
}

.el-date-table td.current:not(.disabled) span {
  background-color: rgba(251, 164, 47, 1);
}

/* 可搜索输入框 */
.el-select-dropdown__item.selected {
  color: rgba(251, 164, 47, 1);
}

/* 日历组件 */
.el-date-picker__header-label.active,
.el-date-picker__header-label:hover {
  color: rgba(251, 164, 47, 1);
}

/* 级联选择器 */

.el-cascader {
  display: block;
}

.el-cascader .el-input .el-input__inner:focus,
.el-cascader .el-input.is-focus .el-input__inner {
  border-color: rgba(251, 164, 47, 1);
}

.el-cascader-node.in-active-path,
.el-cascader-node.is-active,
.el-cascader-node.is-selectable.in-checked-path {
  color: rgba(251, 164, 47, 1);
}

.el-cascader__suggestion-item.is-checked {
  color: rgba(251, 164, 47, 1);
}

/* 单选框 */
.el-radio {
  margin-right: 25px;
}

/* el-upload */
.First .upload-demo {
  display: inline-block;
  margin-left: 10px;
}

.contentFirst_title .el-button--text {
  color: rgba(251, 164, 47, 1);
}

.el-dialog__body {
  padding: 10px 20px;
}

/* el-dialog关闭按钮 */
.el-dialog__headerbtn:focus .el-dialog__close,
.el-dialog__headerbtn:hover .el-dialog__close {
  color: rgba(251, 164, 47, 1);
}

/* 清除el-dialog中的浮动 */
.el-dialog__body {
  overflow: hidden;
}
</style>