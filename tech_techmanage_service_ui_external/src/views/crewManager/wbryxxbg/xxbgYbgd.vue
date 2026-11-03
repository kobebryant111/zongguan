<!-- 外包人员信息变更发起已办归档 -->
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
            <p>变更人员</p>
          </div>
          <div class="lcxx-mian" v-if="isinterview == 0">
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
              <el-table-column label="岗位" width="120">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.newPosition" :disabled="true" placeholder>
                    <el-option
                      v-for="item in position_options"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="外包方式" width="130">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.newOutsourcingtype" :disabled="true" placeholder>
                    <el-option
                      v-for="item in outsourcetype_options"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="结算方式" width="130">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.newUsetype" placeholder :disabled="true">
                    <el-option
                      v-for="item in usertype_options"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="所属部门" width="230">
                <template slot-scope="scope">
                  <el-select
                    v-model="scope.row.newDepid"
                    filterable
                    :disabled="true"
                    placeholder
                    value-key="id"
                  >
                    <el-option
                      v-for="(item,index) in deptno_options"
                      :key="index"
                      :value="item.id"
                      :label="item.text"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column prop="newGroupid" label="所在小组" width="230">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.newGroupid" :disabled="true">
                    <el-option
                      v-for="item in scope.row.teams"
                      :key="item.id"
                      :label="item.groupname"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column prop="newOrganizers" label="行内负责人" width="170">
                <template slot-scope="scope">
                  <el-select
                    v-model="scope.row.newOrganizers"
                    filterable
                    :disabled="true"
                    placeholder
                    value-key="id"
                  >
                    <el-option
                      v-for="(item, index) in scope.row.organizers[0].children"
                      :key="item.index"
                      :label="item.text"
                      :value="item.pcode"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="系统名称" width="230" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <el-cascader
                    ref="systemName"
                    :options="systemnos_options"
                    :props="systemParams"
                    v-model="scope.row.newSysno"
                    :show-all-levels="false"
                    filterable
                    :disabled="true"
                  ></el-cascader>
                </template>
              </el-table-column>
              <!-- 新增在场情况 开始 -->
              <el-table-column label="在场情况" width="170" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-select
                    v-model="scope.row.newPresenceState"
                    :disabled="scope.row.presenceDisabled"
                    placeholder
                    @change="handlePresenceState(scope)"
                  >
                    <el-option
                      v-for="item in presenceStateData"
                      :disabled="item.disabled"
                      :key="item.dictid"
                      :label="item.dictname"
                      :value="item.dictid"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="项目名称" width="230" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <el-cascader
                    ref="xmName"
                    :options="projectno_options"
                    :props="projectnoParams"
                    v-model="scope.row.newProjectno"
                    :show-all-levels="false"
                    filterable
                    :disabled="true"
                  ></el-cascader>
                </template>
              </el-table-column>
              <!-- 采购项目 开始 -->
              <el-table-column label="采购项目" width="170" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-select
                    :ref="`purchase${scope.$index}`"
                    v-model="scope.row.newPurchaseItemId"
                    :disabled="scope.row.disabled"
                    placeholder
                    filterable
                    :filter-method="handleFilterCg"
                    @change="handlePurchase(scope)"
                  >
                    <el-option
                      v-for="(item, index) in purchaseItem_options"
                      :key="index"
                      :label="item.xmmc"
                      :value="item.zbbh"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <!-- 采购项目 结束 -->
              <el-table-column
                prop="newContractid"
                label="所属合同"
                width="230"
                :show-overflow-tooltip="true"
              >
                <template slot-scope="scope">
                  <!-- 人力 -->
                  <div v-if="scope.row.newOutsourcingtype == 1" key="1">
                    <el-select
                      v-model="scope.row.newContractid"
                      filterable
                      placeholder
                      :disabled="true"
                    >
                      <el-option
                        v-for="(item, index) in scope.row.personnelContracts"
                        :key="item.index"
                        :label="item.htmc"
                        :value="item.htid"
                      ></el-option>
                    </el-select>
                  </div>
                  <!-- 项目 -->
                  <div v-else key="2">
                    <el-select
                      v-model="scope.row.newContractid"
                      filterable
                      placeholder
                      :disabled="true"
                    >
                      <el-option
                        v-for="(item, index) in scope.row.projectContracts"
                        :key="item.index"
                        :label="item.htmc"
                        :value="item.htbh"
                      ></el-option>
                    </el-select>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label></el-table-column>
            </el-table>
          </div>

          <div class="lcxx-mian table-form" v-else>
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
              <el-table-column label="所属部门" width="150" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div :class="[scope.row.oldDepid == scope.row.newDepid ? '' : 'ifbg']">
                    <div>{{scope.row.oldDepname == undefined ? '无' : scope.row.oldDepname}}</div>
                    <div>{{scope.row.newDepname == undefined ? '无' : scope.row.newDepname}}</div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                prop="newGroupid"
                label="所在小组"
                width="150"
                :show-overflow-tooltip="true"
              >
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
              <!-- 新增在场情况 -->
              <el-table-column label="在场情况" width="200" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div :class="[scope.row.newPresenceState == scope.row.oldPresenceState ? '' : 'ifbg']">
                    <div>
                      <span v-if="!scope.row.oldPresenceState">
                        无
                      </span>
                      <span v-else>
                        <span v-for="(item,index) in presenceStateData" :key="index">
                          <span v-if="item.dictid == scope.row.oldPresenceState">{{item.dictname}}</span>
                        </span>
                      </span>
                    </div>
                    <div>
                       <span v-if="!scope.row.newPresenceState">
                        无
                      </span>
                      <span v-else>
                        <span v-for="(item,index) in presenceStateData" :key="index">
                          <span v-if="item.dictid == scope.row.newPresenceState">{{item.dictname}}</span>
                        </span>
                      </span>
                    </div>
                  </div>
                </template>
              </el-table-column>
              <!-- 项目名称 -->
              <el-table-column label="项目名称" width="200" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div :class="[scope.row.oldProjectno == scope.row.newProjectno ? '' : 'ifbg']">
                    <div>{{scope.row.oldProjectname == undefined ? '无' : scope.row.oldProjectname}}</div>
                    <div>{{scope.row.newProjectname == undefined ? '无' : scope.row.newProjectname}}</div>
                  </div>
                </template>
              </el-table-column>
              <!-- 采购项目 -->
              <el-table-column label="采购项目" width="200" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div :class="[scope.row.oldPurchaseItemId == scope.row.newPurchaseItemId ? '' : 'ifbg']">
                    <div>{{scope.row.oldPurchaseItem == undefined ? '无' : scope.row.oldPurchaseItem}}</div>
                    <div>{{scope.row.newPurchaseItem == undefined ? '无' : scope.row.newPurchaseItem}}</div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                prop="newContractid"
                label="所属合同"
                width="200"
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
              <!-- <el-table-column prop="interviewtime" label="面试日期" width="125" ></el-table-column> -->
              <el-table-column prop="years" label="年限" width="50">
                <template slot-scope="scope">
                  <div
                    v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)"
                  >{{scope.row.years}}</div>
                </template>
              </el-table-column>
              <el-table-column prop="grade" label="等级" width="50">
                <template slot-scope="scope">
                  <div
                    v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)"
                  >{{scope.row.grade}}</div>
                </template>
              </el-table-column>
              <el-table-column prop="comments" label="评语" width="200" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div
                    v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)"
                  >{{scope.row.comments}}</div>
                </template>
              </el-table-column>
              <el-table-column prop="totalscore" label="成绩" width="60">
                <template slot-scope="scope">
                  <div
                    v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)"
                  >{{scope.row.totalscore}}</div>
                </template>
              </el-table-column>
              <el-table-column prop="result" label="结果" width="80">
                <template slot-scope="scope">
                  <span
                    v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)"
                    v-for="(item, index) of scoreData"
                    :key="index"
                  >
                    <span v-if="item.dictid == scope.row.result">{{ item.dictname }}</span>
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="recommendedPrice"
                label="建议单价"
                width="170"
                v-if="bps.status == 'yb'"
              >
                <template slot-scope="scope">
                  <div
                    v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)"
                  >
                    <el-input-number
                      size="medium"
                      v-model="scope.row.recommendedPrice"
                      controls-position="right"
                      :step="500"
                      :disabled="true"
                    ></el-input-number>
                  </div>
                </template>
              </el-table-column>
              <!-- <el-table-column prop="reason" label="修改理由" width="150">
                                <template slot-scope="scope">
                                    <div v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)">
                                        <el-input type="textarea" autosize v-model="scope.row.reason" placeholder="" :disabled="true"></el-input>
                                    </div>
                                </template>
              </el-table-column>-->
            </el-table>
          </div>
        </div>
        <div class="xqrxx-content">
          <div class="xqrxx-title">
            <p>单价参考</p>
          </div>
          <div>
            <div class="xqrxx-edit">
              <el-row>
                <el-col>
                  <div class="wbbgDjck">
                    <el-table :data="priceConsult" style="width: 100%">
                      <el-table-column
                        prop="outsourcname"
                        label="公司"
                        :show-overflow-tooltip="true"
                        width="220"
                      ></el-table-column>
                      <el-table-column
                        prop="positionName"
                        label="岗位"
                        :show-overflow-tooltip="true"
                        width="90"
                      ></el-table-column>
                      <el-table-column label="一档(1-3年)" :show-overflow-tooltip="true" width="170">
                        <template slot-scope="scope">
                          <span>{{scope.row.firststartprie}} - {{scope.row.firstendprie }}</span>
                        </template>
                      </el-table-column>
                      <el-table-column label="二档(3-5年)" width="170" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                          <span>{{scope.row.secondstartprie }} - {{scope.row.secondendprie }}</span>
                        </template>
                      </el-table-column>
                      <el-table-column label="三档(5-7年)" width="170" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                          <span>{{scope.row.thirdstartprie }} - {{scope.row.thirdendprie}}</span>
                        </template>
                      </el-table-column>
                      <el-table-column label="四档(7年以上)" width="170" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                          <span>{{scope.row.fourthstartprie }} - {{scope.row.fourthendprie}}</span>
                        </template>
                      </el-table-column>
                      <el-table-column></el-table-column>
                    </el-table>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <!-- 操作日志 -->
      <div class="czLog">
        <flow-logs-gd ref="log"></flow-logs-gd>
      </div>
    </div>
    <div class="blank"></div>
    <div class="nav-bottom" v-if="bps.status == 'yb'">
      <yb-revoke :flowItem="bps"></yb-revoke>
    </div>
  </div>
</template>
<script>
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import {
  getFLowInfo,
  queryContracts,
  queryOrInsertPpmRlwbhtInfo,
  queryList
} from "@/api/integrated/wbryxxbg";
import common from "@/api/common";
import {
  queryResourceDept,
  queryEmployeeTree,
  querySystemTree,
  getTree,
  queryGroup,
  getProcurements
} from "@/api/integrated/wbryzrlc";
import Moment from "moment";
import { getDicEntry } from "@/api/eosDictEntry";
import helpHint from "@/components/helpHint";
import ybRevoke from "@/views/plugins/flowPlug/ybRevoke";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import FlowLogsGd from "@/views/plugins/flowPlug/FlowLogsGd";
export default {
  name: "xxbgYbgd",
  components: { buttonCommon, helpHint, ybRevoke, flowLogs, FlowLogsGd },
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
      isinterview: "", //是否面试
      busData: {}, //打分数据
      priceConsult: [], //单价参考
      scoreData: [], // 面试结果数据集合
      presenceStateData: [], // 在场情况
      purchaseItem_options: [], // 采购项目初始数据
      purchaseItemCopy : [],    // 采购项目初始数据复制
    };
  },
  created() {},
  mounted() {},
  methods: {
    // 初始化
    init(item) {
      console.log("外包信息变更", item);
      this.bps = item;
      if (item.status == "yb") {
        this.getBgDetail(item.processInstID);
        this.$refs.log.init(item);
      } else if (item.status == "gd") {
        this.getBgDetail(item.instanceid);
        this.$refs.log.init(item);
      }
      getDicEntry({
        dicttypeid:
          "NEWKG_WBFS,NEWKG_JSFS,BUSI_OUTSOURCE_POSITION,NEW_BUSI_OUTSOURCE_INTERVRESULT,XKG_ZCSTATE"
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
        this.scoreData = data.filter(item => {
          return item.dicttypeid == "NEW_BUSI_OUTSOURCE_INTERVRESULT";
        });
        // 在场情况
        this.presenceStateData = data.filter(item => {
          return item.dicttypeid == "XKG_ZCSTATE";
        });
        return data;
      });
      this.getDep();
      this.searchSystem();
      this.getProjectName();
      // 加载 采购项目
      getProcurements().then(res => {
        console.log("采购", res.data);
        this.purchaseItem_options = res.data.results;
        this.purchaseItemCopy = JSON.parse(JSON.stringify(res.data.results))
      });
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

    // 获取本页面详情
    getBgDetail(id) {
      const loading = common.loading(this);
      let params = {};
      params.instanceid = id;
      getFLowInfo(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.isinterview = res.data.results.isinterview;
            // 外包商单价
            let temps = [];
            let counts = 0;
            res.data.results.infos.forEach(item => {
              if (item.newDepid.indexOf("dep") == -1) {
                item.newDepid = "dep" + item.newDepid;
              }
              if (item.oldDepid.indexOf("dep") == -1) {
                item.oldDepid = "dep" + item.oldDepid;
              }
              if (item.years >= 0 && item.years < 3) {
                item.grade = "1";
              } else if (item.years >= 3 && item.years < 5) {
                item.grade = "2";
              } else if (item.years >= 5 && item.years < 7) {
                item.grade = "3";
              } else {
                item.grade = "4";
              }
              item.imagescore = item.imagescore.toString();
              item.professionscore = item.professionscore.toString();
              item.valuescore = item.valuescore.toString();
              item.comprehensivescore = item.comprehensivescore.toString();
              res.data.results.applytime = Moment(
                res.data.results.applytime
              ).format("YYYY-MM-DD");
              this.ryxxbgData = res.data.results;
              // 新增在场情况并且处理
              let users = this.ryxxbgData.infos;
              if (!users || users.length <= 0) {
                return false;
              }
              users.forEach((ii, key) => {
                // 新增 在场情况如果是
                // 初始状态下是1正常，2提前入场、4离场、 则置灰
                if (
                  ii.newPresenceState === "1" ||
                  ii.newPresenceState === "2" ||
                  ii.newPresenceState === "4"
                ) {
                  ii.presenceDisabled = true;
                  ii.disabled = true;
                }
              });
              // ======= 结束 ======
              let params = { orgcode: item.companyid };
              queryList(params)
                .then(res => {
                  if (res.data.status == 200) {
                    const priceData = res.data.results;
                    if (priceData !== undefined) {
                      item.upData = priceData;
                      temps.push(item);
                    }
                  } else {
                    this.$message.error("获取外包商数据失败");
                  }
                })
                .then(() => {
                  counts++;
                  res.data.results.applytime = Moment(
                    res.data.results.applytime
                  ).format("YYYY-MM-DD");
                  this.ryxxbgData = res.data.results;
                  if (counts == res.data.results.infos.length) {
                    this.$nextTick(() => {
                      this.filArr(temps);
                    });
                  }
                });
              item.initSuggestprice = Number(item.recommendedPrice);
            });
          } else {
            this.$message.error("查询变更详情失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 数组去重
    filArr(ele) {
      let priceArr = [];
      if (ele == []) {
        return;
      } else {
        for (var i = 0; i < ele.length; i++) {
          if (
            (ele[i].newOutsourcingtype == 1 &&
              ele[i].oldOutsourcingtype == 0) ||
            (ele[i].newUsetype == 0 && ele[i].oldUsetype == 1)
          )
            priceArr.push(ele[i]);
        }
      }
      let priceData = [];
      let wbs = [];
      for (var j = 0; j < priceArr.length; j++) {
        if (!wbs.includes(priceArr[j].companyid)) {
          wbs.push(priceArr[j].companyid);
          priceData.push(...priceArr[j].upData);
        }
      }
      this.priceConsult = priceData;
      this.priceConsult.splice(0, 0);
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
    },
    /**
     *  在场情况变更事件
     */
    handlePresenceState(val, dd) {
      console.log("在场情况", val, dd);
      let self = this;
      let item = val.row;
      console.log("eee", self.$refs["project" + val.$index]);
      // 项目名称
      let projectItem = self.$refs["project" + val.$index];
      // 采购项目
      let purchaseItem = self.$refs["purchase" + val.$index];
      // 留旧项目运维
      if (item.newPresenceState === "5") {
        item.disabled = true;
        projectItem.ifClick = true;
        val.row.newProjectno = val.row.oldProjectno;
        val.row.newProjectname = val.row.oldProjectname;
        projectItem.inputContent = val.row.oldProjectname;
        val.row.newPurchaseItemId = val.row.oldPurchaseItemId || null;
      } else if (item.newPresenceState === "6") {
        //  留场新项目开发
        projectItem.inputContent = "";
        projectItem.ifClick = false;
        item.disabled = true;
        self.$message.warning("必须在2个月内转为正常，关联采购项目");
      } else {
        // 正常
        projectItem.ifClick = false;
        item.disabled = false;
        let newProject = [];
        // 过滤 只留01 正常的项目
        self.projectno_options.forEach(item => {
          if (item.children && item.length > 0) {
            let projectTemp = item.children.filter(tt => {
              return item.proattrbutes === "01";
            });
            let obj_temp = {};
            obj_temp.children = projectTemp;
            newProject.push(obj_temp);
          }
          if (item.proattrbutes === "01") {
            newProject.push(item);
          }
        });
        console.log("项目3", newProject);
        self.projectno_options = newProject;
        // 强制更新
        self.projectno_options.splice(0, 0);
      }

      console.log("eee", self.$refs);
    }, // handlePresenceState end
    /**
     * 采购项目change事件
     */
    handlePurchase(val) {
      console.log("采购项目", val);
      let self = this;
      let cg_item = self.purchaseItem_options.filter(item => {
        return item.zbbh === val.row.newPurchaseItemId;
      });
      if (cg_item && cg_item.length > 0) {
        self.purchaseCheckedData = cg_item[0];
      }
      console.log("采购项目2", self.purchaseCheckedData);
      self.ryxxbgData.infos[val.$index].newPurchaseItemId =
        self.purchaseCheckedData.zbbh;
      self.ryxxbgData.infos.splice(0, 0);
    },
    /**
     * 项目采购过滤
     */
    handleFilterCg(val) {
      let self = this;
      if (!val) {
        self.purchaseItem_options = self.purchaseItemCopy
        return false;
      }
      // self.jibenData.purchaseItem = val;
      self.purchaseItem_options = self.purchaseItemCopy.filter(item => {
        if (
          !!~item.xmmc.indexOf(val) ||
          (item.sysName && !!~item.sysName.indexOf(val))
        ) {
          return true;
        }
      });
      console.log("项目采购过滤", self.purchaseItem_options);
    } // handleFilterCg end
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

.wbbgDjck .el-table thead {
  font-size: 14px;
  color: rgba(38, 38, 38, 1);
  font-weight: 400;
}

.wbbgDjck .el-table th {
  background: rgba(250, 250, 250, 1);
}

.table-form .el-input-number--medium {
  width: 100%;
}
</style>