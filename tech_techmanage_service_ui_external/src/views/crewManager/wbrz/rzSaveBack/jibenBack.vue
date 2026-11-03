<!-- 人力结算人员准入申请第一步之基本信息 -->
<template>
  <div class="First First_other">
    <div class="contentFirst_title">
      <p class="contentFirst_title_p1">基本信息</p>
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
                <el-input v-model.lazy.trim="jibenData.userName" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>工号</p>
              <el-form-item prop="jobId">
                <el-input v-model="jibenData.jobId" @input="val => jibenData.jobId = val.replace(/[\s\r\n]+/g, '')"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>性别 *</p>
              <el-form-item
                prop="sex"
                :rules="[{ required: true, message: '请选择性别', trigger: 'change'}]"
              >
                <el-radio-group v-model="jibenData.sex">
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
                <el-input v-model.lazy.trim="jibenData.nativeplace" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <p>婚姻状况 *</p>
              <el-form-item
                prop="maritalStatus"
                :rules="[{ required: true, message: '请选择婚姻状况', trigger: 'change'}]"
              >
                <el-radio-group v-model="jibenData.maritalStatus">
                  <el-radio
                    v-for="(item) in marriage_options"
                    :label="item.dictid"
                    :key="item.dictid"
                    :value="item.dictid"
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
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>专业 *</p>
              <el-form-item
                prop="specialty"
                :rules="[{ required: true, message: '请填写专业', trigger: 'blur'}]"
              >
                <el-input v-model="jibenData.specialty" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <p>学历 *</p>
              <el-form-item
                prop="education"
                :rules="[{ required: true, message: '请选择学历', trigger: 'change'}]"
              >
                <el-radio-group v-model="jibenData.education">
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
                <el-input v-model="jibenData.mobile" placeholder="请输入"></el-input>
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
                    value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <p>入职日期 *</p>
                <el-form-item
                  prop="entryTime"
                  :rules="[{ required: true, message: '请选择入职日期', trigger: 'change'}]"
                >
                  <el-date-picker
                    v-model="jibenData.entryTime"
                    type="date"
                    placeholder="请选择日期"
                    value-format="yyyy-MM-dd"
                    :picker-options="pickerOptions0"
                  ></el-date-picker>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
        </div>
        <!-- 表第五行 -->
        <div class="row-jiben">
          <el-row :gutter="50">
            <el-col :span="8">
              <div>
                <p class="deptno_p">所属部门 *</p>
                <el-form-item
                  prop="deptno"
                  :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]"
                >
                  <el-select
                    v-model="jibenData.deptno"
                    filterable
                    placeholder="请选择"
                    value-key="id"
                    @change="getDepId"
                  >
                    <el-option
                      v-for="(item,index) in deptno_options"
                      :key="index"
                      :value="item.id"
                      :label="item.text"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <p>岗位 *</p>
                <el-form-item
                  prop="position"
                  :rules="[{ required: true, message: '请选择岗位', trigger: 'change'}]"
                >
                  <el-select v-model="jibenData.position" filterable placeholder="请选择">
                    <el-option
                      v-for="item in position_options"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
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
                  ></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
        </div>

        <div class="row-jiben" v-if="this.jibenData.userType == 1">
            <el-row :gutter="50">
              <el-col :span="8">
                 <div>
                   <el-form-item prop="projectno"
                    :rules="[{ required: true, message: '请选择项目名称'}]">
                    <p>项目名称</p>
                    <el-select v-model="jibenData.projectno" @change="searchOrganizers" filterable>
                      <el-option v-for="item in projectno_options" :key="item.id" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                   </el-form-item>
                 </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <el-form-item
                    prop="organizers"
                    :rules="[{ required: true, message: '请选择项目经理'}]"
                  >
                      <p>项目经理</p>
                      <el-select v-model="jibenData.organizers" @change="setProjectEmp" >
                        <el-option v-for="item in organizers_options" :key="item.badge" :label="item.empname" :value="item.badge"></el-option>
                      </el-select>
                    </el-form-item>
                </div>
              </el-col>
              <el-col :span="8">

              </el-col>
            </el-row>
          </div>
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

               <!-- <el-col :span="8">
                <p>外包商 *</p>
                <el-form-item
                  prop="outsourceNo"
                  :rules="[{ required: true, message: '请选择外包商', trigger: 'change'}]"
                >
                  <el-select v-model="jibenData.outsourceNo" filterable placeholder="请选择">
                    <el-option
                      v-for="item in outsourceno_options"
                      :key="item.id"
                      :label="item.outsourcname"
                      :value="item.orgcode"
                    ></el-option>
                  </el-select>
                </el-form-item>
            </el-col> -->
              <el-col :span="8">
                <div>
                  <el-form-item
                    prop="systemnos"
                    :rules="[{ required: true, message: '请选择系统名称'}]"
                    >
                      <p>系统名称 *</p>
                      <el-select v-model="jibenData.systemnos" @change="searchOrganizers" filterable>
                        <el-option v-for="item in systemnos_options" :key="item.id" :label="item.name" :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <el-form-item
                    prop="organizers"
                    :rules="[{ required: true, message: '请选择项目经理'}]"
                  >
                      <p>项目经理 *</p>
                      <el-select v-model="jibenData.organizers"  @change="changeProjectEmp">
                        <el-option v-for="item in organizers_options" :key="item.badge" :label="item.empname" :value="item.badge"></el-option>
                      </el-select>
                    </el-form-item>
                </div>
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
  querySystemEmp,
  queryProjectEmp,
  queryApplyList,
  queryEmployeeTree,
  querySystemTree,
  getSystemPeo,
  getProcurements,
  validMotion,
  validContract
} from "@/api/integrated/wbryzrlc";
import {
  saveDraft,
  getWbrzDetail,
  getCheckedIdCard
} from "@/api/integrated/newWbrz";
import { getDicEntry } from "@/api/eosDictEntry";
import common from "@/api/common";
import uploadExcel from "@/components/UploadExcel"; //导入上传表格组件
import { queryWorkItem } from "@/api/integrated/wbryzrlc";
import { filterArr } from "@/utils/filterArray";
import {
  getListAll,
  getProjectDetail,
  getEmployUser
} from "@/api/common/workflowApi";
import { checkedIsBlack } from "@/api/integrated/wbHmd";
export default {
  components: { uploadExcel },
  data() {
    return {
      jibenData: {},
      managerUsername: "", //项目经理名称
      systemPrincipalname: "", //系统负责人名称
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
      systemsValue: {}, //系统信息
      projectsValue: {}, //项目信息
      purchaseItem_options: [],
      purchaseItemCopy: [],
      position_options: [],
      marriage_options: [],
      char: "1", //判断是项目还是人力
      tableRow: "", //选中行数据
      xmmcIsRequired: true, //项目名称是否必填
      xmmcIsEdit: false, //项目名称是否可编辑
      bps: {},
      countDep: 0,
      pickerOptions0: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
      disabled: true, // 是否只读，默认true
      purchaseCheckedData: {}, // 采购项目选中的数据
      ceneralOfficeMotion_options: [], // 总办议案
      newContractId_options: [], // 合同
    };
  },
  props: {
    instanceId: {
      type: String,
      required: true
    }
  },
  created() {},
  mounted() {
    this.init();
    if (this.instanceId != "") {
      this.getDatail();
    }
    //加载下拉框数据
    this.filDep();
    this.getWbcom();
    this.getvalidMotion()
    // this.getvalidContract()
  },
  computed: {},
  watch: {
    instanceId: function() {
      this.getDatail();
    },
    "jibenData.deptno": function() {
      this.countDep++;
      if (this.countDep > 1) {
        if (this.jibenData.groupId != undefined) {
          this.jibenData.groupId = "";
        }
        if (this.jibenData.organizers != undefined) {
          this.jibenData.organizers = "";
        }
      }
      this.searchGroup();
    },
     "jibenData.position": function(val) {
      this.positionChange(val);
    }
  },
  methods: {
    // 页面初始化
    init: function() {
      getDicEntry({
        dicttypeid:
          "BUSI_OUTSOURCE_GENFER,BUSI_OUTSOURCE_MARITALSTATUS,NEWYY_EDUCATION,NEWKG_WBFS,NEWKG_JSFS"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        // this.initData = data;
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
        //结算方式
        this.usertype_options = data.filter(item => {
          return item.dicttypeid == "NEWKG_JSFS";
        });
        return data;
      });

    },

    // 获取岗位
    getPosition(val) {
      console.log("测试", val);
      if (val == undefined) {
        return;
      }
      getDicEntry({
        dicttypeid: "BUSI_OUTSOURCE_POSITION"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //岗位
        if (val == "dep3634") {
          this.position_options = data;
        } else {
          this.position_options = data.filter(item => {
            return item.dictid != "17";
          });
        }
        return data;
      });
    },

    // 部门改变
    getDepId(val) {
      this.jibenData.position = "";
      this.organizers_options = {};
      if(this.jibenData.userType == "0"){
          this.jibenData.systemnos = "";
          this.searchSystem();
        } else{
          this.jibenData.projectno = "";
          this.searchProject();
        }
      this.getPosition(val);
    },

    // 获取详情
    getDatail() {
      getWbrzDetail({ instanceId: this.instanceId }).then(res => {
        if (res.data.status == 200) {
          if(res.data.results.newContractId == null || res.data.results.newContractId == undefined) {
            console.log('zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz')
            res.data.results.newContractId = '';
          }
          console.log("详情", res.data.results);
          res.data.results.education = res.data.results.education.toString();
          if (res.data.results.isOtherProject != undefined) {
            res.data.results.isOtherProject = eval(
              res.data.results.isOtherProject.toLowerCase()
            );
          }
          if (res.data.results.isOtherProject == true) {
            this.xmmcIsEdit = true;
          }
          if (res.data.results.position == undefined) {
            res.data.results.position = '';
          }
          if (
            res.data.results.deptno != undefined &&
            res.data.results.deptno.indexOf("dep") == -1
          ) {
            res.data.results.deptno = "dep" + res.data.results.deptno;
          }
          if (res.data.results.deptno != undefined) {
            this.getPosition(res.data.results.deptno);
          }
          this.jibenData = res.data.results;
          // 采购项目
          if (
            this.jibenData.outsourceType === "0" &&
            this.jibenData.userType === "1"
          ) {
            this.disabled = false;
            this.purchaseCheckedData.xmmc = this.jibenData.purchaseItem;
            this.purchaseCheckedData.zbbh = this.jibenData.purchaseItemId;
            this.jibenData.purchaseItem = this.purchaseCheckedData.zbbh;
          }
          this.$nextTick(() => {
            this.searchIfCfrz(
              this.jibenData.certifNumber,
              this.jibenData.userName
            );
            this.getvalidContract(res.data.results.ceneralOfficeMotion)
            this.searchProject();
            this.searchSystem();
            this.searchOrganizers();
            this.getWorkItem(res.data.results.instanceId);
          });
        } else {
          this.$message.error("获取数据失败");
        }
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
          }
        } else {
          this.$message.error("查询黑名单信息失败");
        }
      });
    },

    //获取待办工作项详情-单条
    getWorkItem(id) {
      let params = {};
      params.processInstID = id;
      queryWorkItem(params).then(res => {
        if (res.data.status == 200) {
          this.bps = res.data.results;
        } else {
          thsi.$message.error("获取待办工作项详情失败");
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
      console.log("depId", depId,  this.jibenData.deptno)
      params.depId = depId.replace("dep", "");
      queryApplyList(params).then(res => {
          let data = res.data.results;
          this.projectno_options = data.filter(item => {
            item.id = String(item.id);
            return item;
          });
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
          let data = res.data.results;
          this.systemnos_options = data.filter(item => {
            item.id = String(item.id);
            return item;
          });
        }).catch(error => {
        this.$message.error("查询系统名称失败");
        });
    },

    // 查询行内负责人
    searchOrganizers(val) {
        if(this.jibenData.userType == "0"){
          if(!val){
            val = this.jibenData.systemnos;
          }else{
            this.jibenData.organizers = "";
          }
          this.searchSystemEmp(val);
        }else{
          if(!val){
            val = this.jibenData.projectno;
          }else{
            this.jibenData.organizers = "";
          }
          this.searchProjectEmp(val);
        }
    },

    //查询项目经理
    searchProjectEmp(val) {
      let params = {};
      params.id = val;
      queryProjectEmp(params).then(res => {
        let projectRet = {};
        let results = res.data.results;
        const deptNo = this.jibenData.deptno.replace('dep','');
        let i = 0;
        // results.forEach(element => {
        //   debugger;
        //   if (element.depid == deptNo) {
        //     projectRet[i] = element;
        //     i++;
        //   }
        // });
        this.organizers_options = results;
        // this.organizers_options = projectRet;
        // this.jibenData.organizers = res.data.results[0].badge;
        if(this.jibenData.organizers) {
            let param = this.organizers_options.find(ele => ele.badge === this.jibenData.organizers) || {}
            // 项目经理选中后发给父组件
            this.$emit("on-organizer-select", param);
          }
      }).catch(error => {
          this.$message.error("查询项目经理失败");
      });
    },

    setProjectEmp(){
      this.jibenData.managerUserid = this.jibenData.organizers
    },
    changeProjectEmp(val){
      let param = this.organizers_options.find(ele => ele.badge === val) || {}
      // 项目经理选中后发给父组件
      this.$emit("on-organizer-select", param);
    },

    //查询系统负责人
    searchSystemEmp(val) {
      let params = {};
      params.id = val;
      querySystemEmp(params).then(res => {
          this.organizers_options = res.data.results;
          if(this.jibenData.organizers) {
            let param = this.organizers_options.find(ele => ele.badge === this.jibenData.organizers) || {}
            // 项目经理选中后发给父组件
            this.$emit("on-organizer-select", param);
          }
      }).catch(error => {
          this.$message.error("查询项目经理失败");
      });
    },
    //查询小组名称
    searchGroup() {
      let params = {};
      let depid = this.jibenData.deptno;
      params.depid = depid.replace("dep", "");
      queryGroup(params)
        .then(res => {
          this.groupid_options = res.data.results;
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询所有外包商
    getWbcom() {
      getListAll()
        .then(res => {
          this.outsourceno_options = res.data.results;
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //没有选择部门时的提示信息
    cueMessage() {
      if (this.jibenData.deptno == "") {
        this.$message("请先选择部门");
      }
    },
    // 岗位变化
    positionChange(val) {
      if (val == "11") {
        this.xmmcIsRequired = false;
      } else {
        this.xmmcIsRequired = true;
      }
    },

    // 项目是否立项
    ifLxChange(val) {
      if (val == true) {
        this.xmmcIsEdit = true;
        this.jibenData.projectno = "";
        this.jibenData.managerUserid = "";
        this.managerUsername = "";
      } else {
        this.xmmcIsEdit = false;
      }
    },


    // 选中行触发的事件
    handleCurrentChange(val) {
      this.tableRow = val;
    },

    //保存当前组件信息
    savejiben() {
      const loading = common.loading(this);
      this.jibenData.bps = this.bps;
      // 采购项目
      this.jibenData.purchaseItemId = this.purchaseCheckedData.zbbh;
      this.jibenData.purchaseItem = this.purchaseCheckedData.xmmc;
      return saveDraft(this.jibenData).then(res => {
        loading.close();
        if (res.data.status == 200) {
          this.$message.success("保存草稿成功");
          return res.data;
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
      self.$forceUpdate(self.jibenData);
    }, //   handlePurchase ebd
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
      // self.jibenData.purchaseItem = val
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
    // 总办议案
    getvalidMotion() {
      validMotion({fileType:0}).then(res => {
        console.log(res, '总办议案')
        if(res.data.status === 200) {
          this.ceneralOfficeMotion_options = res.data.results.map(item => {
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
      console.log('合同', val);
      let params = {}
      params.attachInfo = val;
      params.outsourceType = this.jibenData.outsourceType;
      validContract(params).then(res => {
        // console.log(res， '合同');
        const {results, status} = res.data;
        if(status === 200) {
          this.newContractId_options = results.map(item => {
            item.label = item.contractName + "(" + item.contractNo +")"
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
  /* overflow: hidden; */
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
