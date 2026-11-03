<!-- 信息变更面试打分 -->
<!-- 外包人员信息变更无打分二级部经理审批 -->
<template>
  <div class="wbxxbg-container">
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="3">
              <p class="nav_name">外包人员信息变更</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <div class="contentMain">
      <el-form :model="ryxxbgData" ref="ryxxbgForm">
        <div class="xqrxx-content">
          <div class="xqrxx-title">
            <p>申请人信息</p>
          </div>
          <div>
            <table class="xqrxx-table">
              <tr class="xqrxx-table-tr">
                <div>
                  <td>申请人</td>
                  <td>{{ryxxbgData.applyer}}</td>
                </div>
                <div>
                  <td>申请部门</td>
                  <td>{{ryxxbgData.deptname}}</td>
                </div>
                <div>
                  <td>申请时间</td>
                  <td>{{ryxxbgData.applytime}}</td>
                </div>
                <div>
                  <td>流程编号</td>
                  <td>{{ryxxbgData.flowid}}</td>
                </div>
              </tr>
            </table>
            <div class="xqrxx-edit">
              <el-row type="flex" justify="space-between">
                <el-col :span="7">
                  <p class="xqrxx-edit-cell-title">实际变更时间
                    <el-tooltip effect="dark" content="该字段定义人力结算起止时间，请谨慎填写。" placement="right">
                      <i class="el-icon-question xqrxx-edit-cell-question"></i>
                    </el-tooltip>
                  </p>
                  <el-form-item>
                    <el-date-picker
                      v-model="ryxxbgData.actChangetime"
                      type="date"
                      placeholder
                      value-format="yyyy-MM-dd"
                      :disabled="true"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <p class="xqrxx-edit-cell-title">变更理由</p>
                  <el-form-item>
                    <el-input
                      type="textarea"
                      autosize
                      v-model="ryxxbgData.changereason"
                      placeholder
                      :disabled="true"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="7"></el-col>
              </el-row>
            </div>
          </div>
        </div>

        <div class="xqrxx-content">
          <div class="xqrxx-title">
            <p>变更人员
              <span class="xkg-color-yellow xqrxx-title-detail" @click="searchDetail">详情</span>
            </p>
          </div>
          <div class="lcxx-mian table-form">
            <el-table :data="ryxxbgData.infos" style="width: 100%">
              <el-table-column label="姓名" width="180" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div>
                    {{scope.row.username}}
                    <span
                      class="xkg-color-yellow xqrxx-title-detail"
                      @click="showUserInfo(scope.row.jobid)"
                    >人员档案</span>
                  </div>
                  <div>{{scope.row.companyname}}</div>
                </template>
              </el-table-column>
              <el-table-column width="80" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div>变更前：</div>
                  <div>变更后：</div>
                </template>
              </el-table-column>
              <!-- <el-table-column label="岗位" width="100" :show-overflow-tooltip="true">
                                <template slot-scope="scope">
                                    <div :class="[scope.row.oldPosition == scope.row.newPosition ? '' : 'ifbg']">
                                        <div>{{scope.row.oldPositionname == undefined ? '无' :
                                            scope.row.oldPositionname}}</div>
                                        <div>{{scope.row.newPositionname == undefined ? '无' :
                                            scope.row.newPositionname}}</div>
                                    </div>
                                </template>
              </el-table-column>-->
              <el-table-column label="外包方式" width="110" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div
                    :class="[scope.row.oldOutsourcingtype == scope.row.newOutsourcingtype ? '' : 'ifbg']"
                  >
                    <div>
                      {{scope.row.oldOutsourcingtypename == undefined ? '无' :
                      scope.row.oldOutsourcingtypename}}
                    </div>
                    <div>
                      {{scope.row.newOutsourcingtypename == undefined ? '无' :
                      scope.row.newOutsourcingtypename}}
                    </div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="结算方式" width="110" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div :class="[scope.row.oldUsetype == scope.row.newUsetype ? '' : 'ifbg']">
                    <div>{{scope.row.oldUsetypename == undefined ? '无' : scope.row.oldUsetypename}}</div>
                    <div>{{scope.row.newUsetypename == undefined ? '无' : scope.row.newUsetypename}}</div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="所属部门" width="200" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div :class="[scope.row.oldDepid == scope.row.newDepid ? '' : 'ifbg']">
                    <div>{{scope.row.oldDepname == undefined ? '无' : scope.row.oldDepname}}</div>
                    <div>{{scope.row.newDepname == undefined ? '无' : scope.row.newDepname}}</div>
                  </div>
                </template>
              </el-table-column>
              <!-- <el-table-column prop="newGroupid" label="所在小组" width="200" :show-overflow-tooltip="true">
                                <template slot-scope="scope">
                                    <div :class="[scope.row.oldGroupid == scope.row.newGroupid ? '' : 'ifbg']">
                                        <div>{{scope.row.oldGroupname == undefined ? '无' : scope.row.oldGroupname}}</div>
                                        <div>{{scope.row.newGroupname == undefined ? '无' : scope.row.newGroupname}}</div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="newOrganizers" label="行内负责人" width="150" :show-overflow-tooltip="true">
                                <template slot-scope="scope">
                                    <div :class="[scope.row.oldOrganizers == scope.row.newOrganizers ? '' : 'ifbg']">
                                        <div>{{scope.row.oldOrganizersname == undefined ? '无' :
                                            scope.row.oldOrganizersname}}</div>
                                        <div>{{scope.row.newOrganizersname == undefined ? '无' :
                                            scope.row.newOrganizersname}}</div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="系统名称" width="200" :show-overflow-tooltip="true">
                                <template slot-scope="scope">
                                    <div :class="[scope.row.oldSysno == scope.row.newSysno ? '' : 'ifbg']">
                                        <div>{{scope.row.oldSyaname == undefined ? '无' : scope.row.oldSyaname}}</div>
                                        <div>{{scope.row.newSysname == undefined ? '无' : scope.row.newSysname}}</div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="项目名称" width="200" :show-overflow-tooltip="true">
                                <template slot-scope="scope">
                                    <div :class="[scope.row.oldProjectno == scope.row.newProjectno ? '' : 'ifbg']">
                                        <div>{{scope.row.oldProjectname == undefined ? '无' : scope.row.oldProjectname}}</div>
                                        <div>{{scope.row.newProjectname == undefined ? '无' : scope.row.newProjectname}}</div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="newContractid" label="所属合同" width="230" :show-overflow-tooltip="true">
                                <template slot-scope="scope">
                                    <div :class="[scope.row.oldContractid == scope.row.newContractid ? '' : 'ifbg']">
                                        <div>{{scope.row.oldContractname == undefined ? '无' :
                                            scope.row.oldContractname}}</div>
                                        <div>{{scope.row.newContractname == undefined ? '无' :
                                            scope.row.newContractname}}</div>
                                    </div>
                                </template>
              </el-table-column>-->
              <el-table-column prop="interviewtime" label="面试日期 *" width="155">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`infos.${scope.$index}.interviewtime`"
                    :rules="[{ required: true, message: '请选择面试时间', trigger: 'change' }]"
                    v-if="((scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)) && (scope.row.newOrganizers == userId)"
                  >
                    <el-date-picker
                      v-model="scope.row.interviewtime"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy-MM-dd"
                      :editable="false"
                    ></el-date-picker>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column prop="professionscore" label="技术 *" width="105">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`infos.${scope.$index}.professionscore`"
                    :rules="[{ required: true, message: '请选择专业分', trigger: 'change' }]"
                    v-if="((scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)) && (scope.row.newOrganizers == userId)"
                  >
                    <el-select v-model="scope.row.professionscore" placeholder="技术">
                      <el-option
                        v-for="item in busData"
                        :label="item.dictname"
                        :key="item.dictid"
                        :value="item.dictid"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column prop="comprehensivescore" label="综合 *" width="105">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`infos.${scope.$index}.comprehensivescore`"
                    :rules="[{ required: true, message: '请选择综合', trigger: 'change' }]"
                    v-if="((scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)) && (scope.row.newOrganizers == userId)"
                  >
                    <el-select v-model="scope.row.comprehensivescore" placeholder="综合">
                      <el-option
                        v-for="item in busData"
                        :label="item.dictname"
                        :key="item.dictid"
                        :value="item.dictid"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column prop="valuescore" label="项目经验 *" width="105">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`infos.${scope.$index}.valuescore`"
                    :rules="[{ required: true, message: '请选择项目经验', trigger: 'change' }]"
                    v-if="((scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)) && (scope.row.newOrganizers == userId)"
                  >
                    <el-select v-model="scope.row.valuescore" placeholder="项目经验">
                      <el-option
                        v-for="item in busData"
                        :label="item.dictname"
                        :key="item.dictid"
                        :value="item.dictid"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column prop="imagescore" label="学历 *" width="105">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`infos.${scope.$index}.imagescore`"
                    :rules="[{ required: true, message: '请选择学历', trigger: 'change' }]"
                    v-if="((scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)) && (scope.row.newOrganizers == userId)"
                  >
                    <el-select v-model="scope.row.imagescore" placeholder="学历">
                      <el-option
                        v-for="item in busData"
                        :label="item.dictname"
                        :key="item.dictid"
                        :value="item.dictid"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column prop="comments" label="评语 *" width="200">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`infos.${scope.$index}.comments`"
                    :rules="[{ required: true, message: '请填写评语', trigger: 'change' }]"
                    v-if="((scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)) && (scope.row.newOrganizers == userId)"
                  >
                    <el-input
                      v-model="scope.row.comments"
                      type="textarea"
                      autosize
                      placeholder="评语"
                    ></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label></el-table-column>
            </el-table>
          </div>
        </div>
      </el-form>
      <!-- 操作日志 -->
      <div class="czLog">
        <flow-logs ref="log"></flow-logs>
      </div>
    </div>
    <div class="blank"></div>
    <div class="nav-bottom">
      <approve-bottom :proving="proving" :flowItem="bps"></approve-bottom>
    </div>

    <el-dialog title="变更人员详情" :visible.sync="ifDialog">
      <div>
        <el-table :data="ryxxbgData.infos" style="width: 100%">
          <el-table-column label="姓名" width="180" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div>{{scope.row.username}}</div>
              <div>{{scope.row.companyname}}</div>
            </template>
          </el-table-column>
          <el-table-column width="80" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div>变更前：</div>
              <div>变更后：</div>
            </template>
          </el-table-column>
          <el-table-column label="岗位" width="100" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldPosition == scope.row.newPosition ? '' : 'ifbg']">
                <div>
                  {{scope.row.oldPositionname == undefined ? '无' :
                  scope.row.oldPositionname}}
                </div>
                <div>
                  {{scope.row.newPositionname == undefined ? '无' :
                  scope.row.newPositionname}}
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="外包方式" width="110" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div
                :class="[scope.row.oldOutsourcingtype == scope.row.newOutsourcingtype ? '' : 'ifbg']"
              >
                <div>
                  {{scope.row.oldOutsourcingtypename == undefined ? '无' :
                  scope.row.oldOutsourcingtypename}}
                </div>
                <div>
                  {{scope.row.newOutsourcingtypename == undefined ? '无' :
                  scope.row.newOutsourcingtypename}}
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="结算方式" width="110" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldUsetype == scope.row.newUsetype ? '' : 'ifbg']">
                <div>{{scope.row.oldUsetypename == undefined ? '无' : scope.row.oldUsetypename}}</div>
                <div>{{scope.row.newUsetypename == undefined ? '无' : scope.row.newUsetypename}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="所属部门" width="200" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldDepid == scope.row.newDepid ? '' : 'ifbg']">
                <div>{{scope.row.oldDepname == undefined ? '无' : scope.row.oldDepname}}</div>
                <div>{{scope.row.newDepname == undefined ? '无' : scope.row.newDepname}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="newGroupid" label="所在小组" width="200" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldGroupid == scope.row.newGroupid ? '' : 'ifbg']">
                <div>{{scope.row.oldGroupname == undefined ? '无' : scope.row.oldGroupname}}</div>
                <div>{{scope.row.newGroupname == undefined ? '无' : scope.row.newGroupname}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="newOrganizers"
            label="行内负责人"
            width="150"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <div :class="[scope.row.oldOrganizers == scope.row.newOrganizers ? '' : 'ifbg']">
                <div>
                  {{scope.row.oldOrganizersname == undefined ? '无' :
                  scope.row.oldOrganizersname}}
                </div>
                <div>
                  {{scope.row.newOrganizersname == undefined ? '无' :
                  scope.row.newOrganizersname}}
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="系统名称" width="200" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldSysno == scope.row.newSysno ? '' : 'ifbg']">
                <div>{{scope.row.oldSyaname == undefined ? '无' : scope.row.oldSyaname}}</div>
                <div>{{scope.row.newSysname == undefined ? '无' : scope.row.newSysname}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="项目名称" width="200" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldProjectno == scope.row.newProjectno ? '' : 'ifbg']">
                <div>{{scope.row.oldProjectname == undefined ? '无' : scope.row.oldProjectname}}</div>
                <div>{{scope.row.newProjectname == undefined ? '无' : scope.row.newProjectname}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="newContractid"
            label="所属合同"
            width="230"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <div :class="[scope.row.oldContractid == scope.row.newContractid ? '' : 'ifbg']">
                <div>
                  {{scope.row.oldContractname == undefined ? '无' :
                  scope.row.oldContractname}}
                </div>
                <div>
                  {{scope.row.newContractname == undefined ? '无' :
                  scope.row.newContractname}}
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label></el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import {
  getFLowInfo,
  queryContracts,
  queryOrInsertPpmRlwbhtInfo,
  interviewscore
} from "@/api/integrated/wbryxxbg";
import common from "@/api/common";
import {
  queryResourceDept,
  queryEmployeeTree,
  querySystemTree,
  getTree,
  queryGroup
} from "@/api/integrated/wbryzrlc";
import Moment from "moment";
import { getDicEntry } from "@/api/eosDictEntry";
import helpHint from "@/components/helpHint";
import approveBottom from "@/views/plugins/flowPlug/approveBottom";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import { mapGetters } from "vuex";
export default {
  name: "xxbgTwoManager",
  components: { buttonCommon, helpHint, approveBottom, flowLogs },
  data() {
    return {
      buttonClose: "关 闭", //取消按钮名字
      size: "mini", //按钮大小
      ryxxbgData: {
        applyer: "", //申请人姓名
        deptname: "", //部门名称
        applytime: "", //申请时间
        flowid: "", //流程编号
        actChangetime: "", //实际变更时间
        changereason: "", //变更理由
        infos: [] //表中数据
      },
      bps: {},
      position_options: [], //岗位
      outsourcetype_options: [], //外包方式
      usertype_options: [], //结算方式
      deptno_options: [], //部门数据
      systemnos_options: [], //系统名称数据
      systemParams: {
        //系统名称配置信息
        label: "text",
        value: "pcode",
        children: "children",
        emitPath: false
      },
      projectno_options: [],
      projectnoParams: {
        //项目名称配置信息
        label: "projectname",
        value: "projectno",
        children: "children",
        emitPath: false
      },
      groupid_options: [],
      bgryDetail: "", //变更人员详细信息
      busData: {}, //打分数据
      admin: [], //流程负责人
      ifDialog: false //是否显示详情弹窗
    };
  },
  computed: {
    ...mapGetters(["userId"])
  },
  mounted() {},
  methods: {
    // 初始化
    init(item) {
      console.log("外包信息变更", item);
      this.bps = item;
      this.$refs.log.init(item.processInstID);
      this.getBgDetail(item.processInstID);
      getDicEntry({
        dicttypeid:
          "NEWKG_WBFS,NEWKG_JSFS,BUSI_OUTSOURCE_POSITION,INTERVIEW_MARKING,OUT_CHANGE_ADMIN"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        //外包方式
        this.outsourcetype_options = data.filter(item => {
          return item.dicttypeid == "NEWKG_WBFS";
        });
        //结算方式
        this.usertype_options = data.filter(item => {
          return item.dicttypeid == "NEWKG_JSFS";
        });
        //岗位
        this.position_options = data.filter(item => {
          return item.dicttypeid == "BUSI_OUTSOURCE_POSITION";
        });
        //打分
        this.busData = data
          .filter(item => {
            return item.dicttypeid == "INTERVIEW_MARKING";
          })
          .reverse();
        // 流程负责人
        this.admin = data.filter(item => {
          return item.dicttypeid == "OUT_CHANGE_ADMIN";
        });
        return data;
      });
      this.getDep();
      this.searchSystem();
      this.getProjectName();
    },

    //获取部门
    getDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
          if (res.data.status == 200) {
            var fild = res.data.results[0].children;
            var reg = /([^-]+)$/;
            this.deptno_options = fild.filter(li => {
              li.text = li.text.match(reg)[1];
              return li;
            });
          } else {
            this.$message.error("获取部门数据失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //查询小组名称
    searchGroup(id) {
      const loading = common.loading(this);
      let params = {};
      let depid = id;
      params.depid = depid.replace("dep", "");
      queryGroup(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.bgryDetail.teams = res.data.results;
          } else {
            this.$message.error("查询小组名称失败");
          }
          this.searchEpmlo(id);
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询行内负责人
    searchEpmlo(id) {
      const loading = common.loading(this);
      let depid = id.replace("dep", "");
      queryEmployeeTree(depid)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.bgryDetail.organizers = res.data.results;
          } else {
            this.$message.error("查询行内负责人失败");
          }
          //判断人力外包还是项目外包
          if (this.bgryDetail.outsourceType == 0) {
            this.searchContracts(id);
          } else {
            this.searchContractsRl(this.bgryDetail.companyid);
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询合同(初始化项目)
    searchContracts(id) {
      const loading = common.loading(this);
      let params = {};
      let depid = id;
      params.dealdeptid = depid.replace("dep", "");
      queryContracts(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.bgryDetail.projectContracts = res.data.results;
          } else {
            this.$message.error("查询合同失败");
          }
          this.bgryDetail.newDepid = "dep" + this.bgryDetail.newDepid;
          this.$nextTick(() => {
            this.ryxxbgData.infos.push(this.bgryDetail);
          });
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询合同(初始化人力)
    searchContractsRl(id) {
      const loading = common.loading(this);
      queryOrInsertPpmRlwbhtInfo({ orgcode: id })
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.bgryDetail.personnelContracts = res.data.results;
            this.bgryDetail.newContractid = res.data.results[0].htid;
            this.bgryDetail.newContractname = res.data.results[0].htmc;
          } else {
            this.$message.error("查询合同失败");
          }
          this.bgryDetail.newDepid = "dep" + this.bgryDetail.newDepid;
          this.$nextTick(() => {
            this.ryxxbgData.infos.push(this.bgryDetail);
          });
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询系统名称
    searchSystem() {
      let params = "";
      params = "6000000300";
      querySystemTree(params)
        .then(res => {
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
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询项目名称
    getProjectName() {
      getTree()
        .then(res => {
          if (res.data.status == 200) {
            var reg = /([^-]+)$/;
            this.projectno_options = res.data.results.filter(li => {
              li.projectname = li.projectname.match(reg)[1];
              return li;
            });
          } else {
            this.$message.error("获取项目名称数据失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查看详情
    searchDetail() {
      this.ifDialog = true;
    },

    // 获取本页面详情
    getBgDetail(id) {
      const loading = common.loading(this);
      let params = {};
      params.instanceid = id;
      getFLowInfo(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            res.data.results.applytime = Moment(
              res.data.results.applytime
            ).format("YYYY-MM-DD");
            res.data.results.infos = res.data.results.infos.filter(li => {
              li.imagescore = li.imagescore.toString();
              li.professionscore = li.professionscore.toString();
              li.valuescore = li.valuescore.toString();
              li.comprehensivescore = li.comprehensivescore.toString();
              return li;
            });
            this.ryxxbgData = res.data.results;
          } else {
            this.$message.error("查询变更详情失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 流转并验证
    proving(data) {
      let status = new Promise(() => {});
      if (data.conclusion == "01") {
        this.$refs["ryxxbgForm"].validate((valid, val) => {
          if (valid) {
            status = this.liuzhuan(data).then(val => {
              return val;
            });
          } else {
            this.$message.error("操作失败,请填写必填项");
            return false;
          }
        });
      } else {
        status = this.liuzhuan(data).then(val => {
          return val;
        });
      }
      return status;
    },

    // 提交本页面数据
    liuzhuan(jljy) {
      const loading = common.loading(this);
      this.bps.conclusion = jljy.conclusion;
      this.bps.suggest = jljy.suggest;
      this.ryxxbgData.bps = this.bps;
      return interviewscore(this.ryxxbgData).then(res => {
        loading.close();
        if (res.data.status == 200) {
          return res.data;
        } else {
          // this.$message.error('操作失败，无法找到下一节点，请联系流程负责人' + this.admin[0].dictname);
          this.$message.error("操作失败，无法找到下一节点");
        }
      });
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: "dashboard" });
    },

    //消息提示
    messageNotify(messageVal) {
      this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        message:
          "<div><div>" +
          messageVal[1] +
          "</div><div>" +
          messageVal[2] +
          "</div></div>",
        duration: 0
      });
    },

    // 自定义需求进度表头
    showHelp() {
      return this.$createElement("helpHint", {
        props: {
          content:
            "请确保本次提交变更人员的新二级部相同，若存在多个二级部请分别发起流程。",
          name: "所属部门"
        }
      });
    },
    /**
     * 显示人员档案
     */
    showUserInfo(v) {
      this.$emit("showUserInfo", v);
    }
  }
};
</script>
<style scoped>
.wbxxbg-container {
  padding-bottom: 85px;
}

.contentMain {
  width: 100%;
  height: 100%;
  font-size: 16px;
  padding: 0 20px;
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

.wrapper-right-keep {
  margin-right: 10px;
}

.xqrxx-title {
  height: 60px;
  line-height: 60px;
  padding: 0 25px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.xqrxx-title > p {
  color: #262626;
  font-weight: 600;
}

.xqrxx-content {
  background-color: #ffffff;
}

.xqrxx-table {
  width: 100%;
  padding: 10px;
}

.xqrxx-table td {
  font-size: 15px;
  padding: 5px 0;
}

.xqrxx-table-tr {
  display: flex;
  justify-content: space-between;
}

.xqrxx-table-tr div {
  display: inline-block;
}

.xqrxx-table-tr td:nth-child(odd) {
  color: #909399;
  width: 80px;
  white-space: nowrap;
}

.xqrxx-table-tr td:nth-child(even) {
  color: #262626;
  width: 261px;
}

.xqrxx-edit {
  padding: 0px 15px;
}

.lcxx-mian {
  padding: 15px;
}

.xqrxx-edit-cell-title {
  color: #262626;
  font-size: 14px;
}

.xqrxx-edit-cell-question {
  margin-left: 4px;
  cursor: pointer;
}

.bottom {
  margin-top: 20px;
  padding: 15px 0;
}

.blank {
  width: 100%;
  height: 144px;
}

.nav-bottom {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
}

.ifbg div {
  color: #e02020;
}

.xqrxx-title-detail {
  font-size: 14px;
  font-weight: normal;
  cursor: pointer;
  margin-left: 10px;
}
</style>

<style>
.table-form .el-form-item {
  margin-bottom: 15px;
}
</style>