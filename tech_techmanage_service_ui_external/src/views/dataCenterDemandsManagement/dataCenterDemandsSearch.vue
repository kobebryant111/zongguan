<!-- 数据中心需求查询 -->
<template>
  <div class="list-container requireList">
    <el-form size="mini" :model="paramsForm" inline>
      <div class="requireList_head">
        <div class="requireList_head_input">
          <el-row type="flex">
            <el-col :span="6">
              <el-form-item style="width: 250px;">
                <span style="float: left; width:70px;">流程名称:</span>
                <el-input style="float: left; width: 180px;" v-model="paramsForm.title"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="empname" style="width: 250px;">
                <div style="float: left; width: 70px;">
                  <span>流程状态:</span>
                </div>
                <div style="float: left; width: 180px;">
                  <el-select placeholder="请选择" v-model="paramsForm.flowStatus" clearable>
                    <!-- <el-option value></el-option> -->
                    <el-option
                      v-for="item in flowStatus_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictname"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item style="width: 250px;">
                <span style="float: left; width:90px;">需求负责人:</span>
                <el-input
                  style="float: left; width: 160px;"
                  v-model="paramsForm.demandBy"
                  placeholder="姓名/工号"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item style="width: 250px;">
                <div style="float: left; width: 70px;">
                  <span>需求部门:</span>
                </div>
                <div style="float: left; width: 180px;">
                  <el-select placeholder="请选择" filterable v-model="paramsForm.demandDept">
                    <el-option
                      v-for="item in depData"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item style="width:333px;">
                <span style="float: left; width:70px;">申请日期:</span>
                <el-date-picker
                  v-model="paramsForm.createDates"
                  value-format="yyyy-MM-dd"
                  type="daterange"
                  start-placeholde="开始日期"
                  end-placeholde="结束日期"
                  style="width:240px;"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="8">
              <el-form-item style="width:333px;">
                <span style="float: left; width:85px;">关联主系统:</span>
                <el-cascader
                  ref="systemName"
                  :options="systemnos_options"
                  :props="systemParams"
                  v-model="paramsForm.mainSystemId"
                  :show-all-levels="false"
                  @change="systemChange"
                  filterable
                  style="width:240px;"
                ></el-cascader>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="8">
              <el-form-item style="width:339px;">
                <span style="float: left; width:93px;">上线日期:</span>
                <el-date-picker
                  v-model="paramsForm.demandOnlineDates"
                  type="daterange"
                  value-format="yyyy-MM-dd"
                  start-placeholde="开始日期"
                  end-placeholde="结束日期"
                  style="width:246px;"
                ></el-date-picker>
              </el-form-item>
            </el-col>

            <el-col v-if="isSeniorSearch" :span="6">
              <el-form-item style="width: 250px;">
                <span style="float: left; width:70px;">申请人:</span>
                <el-input
                  style="float: left; width: 180px;"
                  v-model="paramsForm.createBy"
                  placeholder="姓名/工号"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- <el-col v-if="isSeniorSearch" :span="6">
              <el-form-item prop="badge" style="width: 250px;">
                <div style="float: left; width: 70px;">
                  <span>需求分类:</span>
                </div>
                <div style="float: left; width: 180px;">
                  <el-select placeholder="请选择" filterable v-model="paramsForm.demandType">
                    
                    <el-option
                      v-for="item in demandType_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>-->
            <el-col v-if="isSeniorSearch" :span="6">
              <el-form-item prop="empname" style="width: 250px;">
                <div style="float: left; width: 70px;">
                  <span>需求状态:</span>
                </div>
                <div style="float: left; width: 180px;">
                  <el-select
                    multiple
                    placeholder="请选择"
                    v-model="paramsForm.relationDemandStatus"
                    collapse-tags
                  >
                    <el-option
                      v-for="item in relationDemandStatus_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="6">
              <el-form-item style="width: 250px;">
                <span style="float: left; width:70px;">意向编号:</span>
                <el-autocomplete
                  style="float: left; width: 180px;"
                  popper-class="my-autocomplete"
                  v-model="paramsForm.relationDemandNo"
                  :fetch-suggestions="querySearchAsync"
                  @select="handleSelect"
                >
                  <template slot-scope="{ item }">
                    <div class="number">{{ item.demandno }}</div>
                    <span class="name">{{ item.demandname }}</span>
                  </template>
                </el-autocomplete>
              </el-form-item>
            </el-col>
            <el-col v-if="isSeniorSearch" :span="6">
              <el-form-item style="width: 250px;">
                <div style="float: left; width: 70px;">
                  <span>项目状态:</span>
                </div>
                <div style="float: left; width: 180px;">
                  <el-select
                    placeholder="请选择"
                    v-model="paramsForm.projectStatus"
                    multiple
                    collapse-tags
                  >
                    <el-option
                      v-for="item in projectStatus_option"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 重置、查询、高级搜索 按钮 -->
        <div class="requireList_head_buttons">
          <el-row>
            <el-col :span="6">
              <clean-btn :size="sizes" :forms="this.paramsForm"/>
            </el-col>
            <el-col :span="6">
              <div class="requireList_head_right_orange">
                <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="requireList_head_right_orange">
                <button-orange :buttonName="buttonSeniorSearch" :size="sizes" :fns="seniorSerch"></button-orange>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-form>

    <div class="requireContainer">
      <div class="requireContainer_head">
        <el-row type="flex">
          <div class="requireList_head_right_orange_common">
            <button-common :buttonName="buttonClose" :size="sizes" :fns="close"></button-common>
          </div>
          <div class="requireList_head_right_orange_common">
            <button-orange :buttonName="buttonExport" :size="sizes" :fns="exportExcel"></button-orange>
          </div>
          <div class="requireList_head_right_orange_common">
            <button-orange :buttonName="buttonStart" :size="sizes" :fns="launchDemand"></button-orange>
          </div>
        </el-row>
      </div>

      <el-table
        style="margin-bottom: 80px;"
        :data="demandDatas"
        border
        highlight-current-row
        height="510"
        @row-dblclick="showDetail"
      >
        <el-table-column fixed prop="title" label="流程名称" width="160" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="flowStatus" label="流程状态" width="80" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="sysName" label="关联主系统" width="140" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="createBy" label="申请人" width="130" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="createDate" label="申请日期" width="160" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column
          prop="demandTypeName"
          label="需求分类"
          width="100"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column prop="demandBy" label="需求负责人" width="140" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="deptName" label="需求部门" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column
          prop="demandOnlineDate"
          label="期望上线日期"
          width="120"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column label="意向编号" width="120" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <div @click="skipToOldKG(scope.row.relationDemandNo)">
              <span class="pointer_relationDemandNo">{{scope.row.relationDemandNo}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="relationDemandStatusName"
          label="需求状态"
          width="100"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column prop="bringUpdate" label="提出时间" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column
          prop="projectStatus"
          label="项目状态"
          width="100"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column prop="buildDate" label="立项时间" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="actualDate" label="上线时间" width="100" :show-overflow-tooltip="true"></el-table-column>
      </el-table>

      <!-- 需求详情弹出框 -->
      <el-dialog title="需求详情" :visible.sync="showDetails" width="70%">
        <demandDetail :rowData="rowData"></demandDetail>
      </el-dialog>

      <div class="nav-bottom">
        <div class="wrapper">
          <div class="wrapper_left"></div>
          <div class="wrapper_right">
            <div class="requireList_head_right_orange_common">
              <pager
                :fns="getPager"
                :total="totalNumber"
                :pageSize="size"
                v-model="$attrs"
                ref="pager"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import demandDetail from "./demandDetail";
import { mapGetters } from "vuex";
import dataCenterDemandsLaunch from "./dataCenterDemandsLaunch"; //需求发起页面
import { getCrewPager } from "@/api/userApi/bankUser";
import common from "@/api/common";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import cleanBtn from "@/components/CleanBtn"; //引入重置按钮
import { queryResourceDept, querySystemTree } from "@/api/integrated/wbryzrlc";
import { getDicEntry, getDomainName } from "@/api/eosDictEntry";
// import { getEmpBaseInfo } from "@/api/integrated/wbryxxbg";
import { pagerList } from "@/api/branchIntentionApi/fhyx";

import {
  queryDemands,
  exportDacenterDemandDatas
} from "@/api/dataCenter/dataCenDemManager";
import helpHint from "@/components/helpHint";
import Moment from "moment";
export default {
  name: "demandList",
  components: {
    pager,
    buttonOrange,
    helpHint,
    buttonCommon,
    cleanBtn,
    demandDetail,
    dataCenterDemandsLaunch
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认:50
      index: 1, // 初始值 默认:1
      paramsForm: {
        //数据中心需求查询
        title: "", //流程名称
        flowStatus: "", //流程状态
        demandBy: "", //需求负责人
        demandDept: "", //需求部门id
        createDate: "", //申请日期
        mainSystemId: "", //关联主系统
        createBy: "", //申请人
        demandType: "", //需求分类
        demandOnlineDate: "", //期望上线日期
        relationDemandStatus: "", // 关联需求状态
        relationDemandNo: "", //意向编号
        startDate: "", //申请日期开始时间
        endDate: "", //申请日期结束时间
        startDemandOnlineDate: "", //上线日期开始时间
        endDemandOnlineDate: "", //上线日期结束时间
        startBuildDate: "", //立项时间开始时间
        endBuildDate: "", //立项时间结束时间
        startBringUpdate: "", //提出时间开始时间
        endBringUpdate: "", //提出时间结束时间
        createDates: [], //申请日期
        demandOnlineDates: [], //上线日期
        buildDates: [], //立项时间
        bringUpdates: [] //提出时间
      },
      buttonExport: "导 出", //导出按钮
      // showExport: false, //是否显示导出按钮
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      buttonSeniorSearch: "高级搜索", //高级搜索按钮
      buttonClose: "关 闭", //关闭按钮名字
      buttonStart: "发起需求", //发起需求按钮
      deptno: "",
      depData: [], // 部门数据
      deptno_options: [],
      demandType_option: [], //需求分类
      relationDemandStatus_option: [], //需求状态
      systemnos_options: [], ////主系统数据
      systemParams: {
        //系统名称配置信息
        label: "text",
        value: "pcode",
        children: "children",
        emitPath: false
      },
      flowStatus_option: [], //流程状态
      projectStatus_option: [], //项目状态

      //数据中心需求查询
      isSeniorSearch: false, //高级搜索
      showDemandLaunch: false, //是否显示发起需求页面
      demandDatas: [], // 列表分页数据zb
      showDetails: false, //是否展示需求数据详情
      relationDemandNoSuggestions: [], //意向编号输入框建议
      rowData: {} //当前行数据流程编号
    };
  },
  created() {
    this.getDic();
    this.getDep();
    this.searchSystem();
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  mounted() {
    this.loadInit();
    this.loadAll();
  },
  methods: {
    /**
     * 分页初始化方法
     */
    loadInit: function() {
      this.paramsForm.flowStatus = "进行中";
      let param = {};
      param.size = this.size;
      param.index = this.index;
      param.flowStatus = "进行中";
      let data = common.paramsFormat(param);
      this.getPagerList(data);
      // this.getCurrentUser();
    }, // end

    //导出
    exportExcel() {
      let data = common.paramsFormat(this.paramsForm);
      exportDacenterDemandDatas(data).then(res => {
        const self = this;
        common.download(res.data, self, "数据中心需求导出文件.xls");
      });
    },

    //跳转老应用
    skipToOldKG(relationDemandNo) {
      let link = document.createElement("a");
      let url_params =
        "/com.example.ppm.system.projectM.newApplicationDemandYxM.flow?processInstID=" +
        relationDemandNo +
        "&processDefName=com.example.ppm.bps.daCenterDemandManager" +
        "&md5=" +
        this.$md5(this.userId) +
        "&userid=" +
        this.userId;
      let params = { dicttypeid: "PPM_URL", dictid: "1" };
      getDomainName(params).then(res => {
        let webUrl = res.data.results;
        link.href = webUrl + url_params;
        link.target = "_blank";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      });
    },

    // 查询系统名称
    searchSystem() {
      let params = "";
      // params = this.pcode;
      params = "6000000300";
      querySystemTree(params).then(res => {
        if (res.data.status == 200) {
          res.data.results[0].children = res.data.results[0].children.filter(
            li => {
              li.text = li.text.replace("总行金融科技部-", "");
              return li;
            }
          );
          res.data.results[1].children[0].children = res.data.results[1].children[0].children.filter(
            li => {
              li.text = li.text.replace("永赢金租金融科技部-", "");
              return li;
            }
          );
          this.systemnos_options = res.data.results;
        } else {
          this.$message.error("获取系统名称失败");
        }
      });
    },

    // 系统名称变化
    systemChange(val) {},

    //获取当前登录人用户信息
    // getCurrentUser() {
    //   let data = common.paramsFormat(this.userId);
    //   getEmpBaseInfo(data).then(res => {
    //     let userDep = res.data.results.depname;
    //     if (userDep.indexOf("运维管理部") > -1) {
    //       this.showExport = true;
    //     } else {
    //       this.showExport = false;
    //     }
    //   });
    // },

    // 查询业务字典
    getDic() {
      getDicEntry({
        dicttypeid: "NEWKG_XQFL"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //需求分类
        this.demandType_option = data.filter(item => {
          return item.dicttypeid == "NEWKG_XQFL";
        });

        return data;
      });

      getDicEntry({
        dicttypeid: "NEWKG_XQZT"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //需求状态
        this.relationDemandStatus_option = data.filter(item => {
          return item.dicttypeid == "NEWKG_XQZT";
        });

        return data;
      });

      getDicEntry({
        dicttypeid: "NEWKG_XQZT"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //需求状态
        this.relationDemandStatus_option = data.filter(item => {
          return item.dicttypeid == "NEWKG_XQZT";
        });

        getDicEntry({
          dicttypeid: "BUSI_DEMAND_STATUS"
        }).then(res => {
          if (res.data.status !== 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          let data = res.data.results;
          //老科管需求状态
          let options = data.filter(item => {
            return item.dicttypeid == "BUSI_DEMAND_STATUS";
          });
          console.log("options:", options);
          for (var i = 0; i < options.length; i++) {
            this.relationDemandStatus_option.push(options[i]);
          }
        });

        return data;
      });

      getDicEntry({
        dicttypeid: "NEWKE_LCZT"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //流程状态
        this.flowStatus_option = data.filter(item => {
          return item.dicttypeid == "NEWKE_LCZT";
        });

        return data;
      });

      getDicEntry({
        dicttypeid: "GLOBAL_PROJECT_STATUS"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //项目状态
        this.projectStatus_option = data.filter(item => {
          return item.dicttypeid == "GLOBAL_PROJECT_STATUS";
        });

        return data;
      });
    },

    //查看详情
    showDetail(row) {
      this.rowData = row;
      this.showDetails = true;
    },

    // 获取部门
    getDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
          var fild = res.data.results[0].children;
          let name = res.data.results[0].text;
          this.depData = fild.filter(tt => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 获取列表数据
    getPagerList(params) {
      queryDemands(params).then(res => {
        if (res.data.status == 200) {
          this.demandDatas = res.data.results;
          this.totalNumber = res.data.total;
        }
      });
    },

    //输入框建议
    loadAll() {
      pagerList().then(res => {
        this.relationDemandNoSuggestions = res.data.results;
        // console.log("pagerList:",this.relationDemandNoSuggestions);
      });
    },
    querySearchAsync(queryString, cb) {
      var relationDemandNoSuggestions = this.relationDemandNoSuggestions;
      var results = queryString
        ? relationDemandNoSuggestions.filter(
            this.createStateFilter(queryString)
          )
        : relationDemandNoSuggestions;
      // for (var i = 0; i < results.length; i++) {
      //   results[i].value = results[i].demandno;
      // }
      cb(results);
    },
    createStateFilter(queryString) {
      return state => {
        return (
          state.demandno.toLowerCase().indexOf(queryString.toLowerCase()) !=
            -1 || state.demandname.indexOf(queryString) != -1
        );
      };
    },
    handleSelect(item) {
      this.paramsForm.relationDemandNo = item.demandno;
    },

    //  时间格式化
    createtimeFormat: function(row, cloumn) {
      let time = "";
      if (row.createdate != null) {
        // time = common.dateFormat(row.createdate, "yyyy年MM月dd日");
        time = Moment(row.createdate).format("YYYY-MM-DD");
      }
      return time;
    },

    //  时间格式化
    msDateFormat: function(row, cloumn) {
      let time = "";
      if (row.msDate != null) {
        // time = common.dateFormat(row.createdate, "yyyy年MM月dd日");
        time = Moment(row.msDate).format("YYYY-MM-DD");
      }
      return time;
    },

    /**
     * 搜索事件
     */
    submitBtn: function() {
      if (
        this.paramsForm.createDates.length > 0 &&
        this.paramsForm.createDates != undefined
      ) {
        this.paramsForm.startDate = this.paramsForm.createDates[0];
        this.paramsForm.endDate = this.paramsForm.createDates[1];
      }
      if (
        this.paramsForm.demandOnlineDates.length > 0 &&
        this.paramsForm.demandOnlineDates != undefined
      ) {
        this.paramsForm.startDemandOnlineDate = this.paramsForm.demandOnlineDates[0];
        this.paramsForm.endDemandOnlineDate = this.paramsForm.demandOnlineDates[1];
      }
      if (
        this.paramsForm.buildDates.length > 0 &&
        this.paramsForm.buildDates != undefined
      ) {
        this.paramsForm.startBuildDate = this.paramsForm.buildDates[0];
        this.paramsForm.endBuildDate = this.paramsForm.buildDates[1];
      }
      if (
        this.paramsForm.bringUpdates.length > 0 &&
        this.paramsForm.bringUpdates != undefined
      ) {
        this.paramsForm.startBringUpdate = this.paramsForm.bringUpdates[0];
        this.paramsForm.endBringUpdate = this.paramsForm.bringUpdates[1];
      }
      let params = {};
      params = this.paramsForm;
      if (params.demandDept != "") {
        params.demandDept = params.demandDept.replace("dep", "");
      }
      params.size = this.size;
      params.index = this.index;
      let data = common.paramsFormat(params);
      this.getPagerList(data);
      if (params.demandDept != "") {
        params.demandDept = "dep" + params.demandDept;
      }
    }, // 搜索事件 end

    /**
     * 高级搜索
     */
    seniorSerch() {
      if (this.isSeniorSearch == false) {
        this.isSeniorSearch = true;
        this.buttonSeniorSearch = "普通查询";
      } else {
        this.isSeniorSearch = false;
        this.buttonSeniorSearch = "高级搜索";
        this.paramsForm.relationDemandNo = ""; //意向编号
        this.paramsForm.mainSystemId = ""; //关联主系统
        this.paramsForm.createBy = ""; //申请人
        this.paramsForm.demandType = ""; //需求分类
        this.paramsForm.demandOnlineDates = []; //期望上线日期
        this.paramsForm.relationDemandStatus = ""; //需求状态
        this.paramsForm.startDemandOnlineDate = ""; //期望上线日期开始日期
        this.paramsForm.endDemandOnlineDate = ""; //期望上线日期结束日期
        this.paramsForm.projectStatus = ""; //项目状态
      }
    },

    // 发起需求
    launchDemand() {
      this.$router.push({
        name: "daCenterDemandManager",
        params: {
          actionURL: "dataCenterDemandsLaunch"
        }
      });
    },

    /**
     * 点击table行
     */
    onSelectTable: function(selection, row) {},

    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params = this.paramsForm;
      params.size = size;
      params.index = index;
      let data = common.paramsFormat(params);
      // this.init(params);
      this.getPagerList(data);
    }, // end

    // 关闭按钮
    close() {
      this.$router.push({ name: "dashboard" });
    }
  }
};
</script>
<style scoped>
.requireList {
  padding: 15px;
}

.requireList_head {
  /* background-color: #ffffff;
    padding: 18px; */
  background-color: #ffffff;
  padding: 18px;
  display: flex;
  justify-content: space-between;
}

.requireContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 18px;
}

.requireContainer_head {
  float: right;
  margin-bottom: 10px;
}

.requireContainer_head_en {
  width: 160px;
}

.requireList_head_buttons {
  width: 20%;
}

.requireList_head_input {
  width: 80%;
}

.seniorSearch {
  margin: -25px 0 10px 0;
}

.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
}

.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  margin-top: 20px;
  padding: 15px 0;
  z-index: 999;
}

.nav-bottom .wrapper {
  width: 80%;
  margin-left: 25px;
  margin-right: 25px;
  display: flex;
  justify-content: space-between;
}

/* 占位 */
.occupied {
  width: 8px;
  display: inline-block;
}

.requireList_head_right {
  float: right;
}

.requireList_head_right div {
  display: inline-block;
}

.requireList_head_right_clean {
  margin-right: 10px;
}

/* el-row超出24换行 */
.el-row {
  display: flex;
  flex-wrap: wrap;
}

.my-autocomplete li {
  line-height: normal;
  padding: 7px;
}

.number {
  /* padding: -10px; */
  margin: -5px 0 -15px 0;
  text-overflow: ellipsis;
  overflow: hidden;
}

.name {
  line-height: 14px;
  font-size: 12px;
  color: #b4b4b4;
}

/* 表格推荐供应商 */
.recGys {
  border: none;
}

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}

/* 弹窗关闭按钮 */
.cloosePre {
  margin: 15px 0;
  float: right;
}

.requireList_head_right_orange {
  margin: 0 5px 0 5px;
}

.requireList_head_right_orange_common {
  margin: 0 5px 0 5px;
}
</style>
<style>
/* el-input修改 */
.requireList .el-input--prefix .el-input__inner {
  padding-left: 75px;
}

.requireList .el-input__prefix {
  left: 8px;
  color: #262626;
  font-size: 14px;
}

/* element表单修改 */
.requireList .el-form-item {
  margin-bottom: 10px;
}

/* 表头背景色 */
.requireList .el-table th {
  border: #ffffff;
  background: #fafafa;
}

.requireList .el-table,
.requireList .el-table td {
  border: #ffffff;
}

.pointer_relationDemandNo {
  cursor: pointer;
  color: #0000ff;
}

/* 弹窗 */
.dialog .el-dialog {
  overflow: hidden;
}
</style>
