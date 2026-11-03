<!-- 信息变更保存后页面 -->
<template>
  <div class="xxbg-container">
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
                  <p class="xqrxx-edit-cell-title">实际变更时间 *
                    <el-tooltip effect="dark" content="该字段定义人力结算起止时间，请谨慎填写。" placement="right">
                      <i class="el-icon-question xqrxx-edit-cell-question"></i>
                    </el-tooltip>
                  </p>
                  <el-form-item
                    prop="actChangetime"
                    :rules="[{ required: true, message: '请选择实际变更时间', trigger: 'change' }]"
                  >
                    <el-date-picker
                      v-model="ryxxbgData.actChangetime"
                      type="date"
                      placeholder="请选择日期"
                      value-format="yyyy-MM-dd"
                      :picker-options="pickerOptions0"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <p class="xqrxx-edit-cell-title">变更理由 *</p>
                  <el-form-item
                    prop="changereason"
                    :rules="[{ required: true, message: '请填写变更理由', trigger: 'blur' }]"
                  >
                    <el-input
                      type="textarea"
                      autosize
                      maxlength="150"
                      show-word-limit
                      v-model="ryxxbgData.changereason"
                      placeholder="请输入"
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
            <p>
              变更人员
              <span class="xqrxx-title-add" @click="addRow">新增一行</span>
            </p>
          </div>
          <div class="lcxx-mian xxbg-table-form">
            <el-table :data="ryxxbgData.infos" style="width: 100%">
              <el-table-column label="姓名" width="180" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <el-form-item>
                    <div>
                      {{scope.row.username}}
                      <span
                        class="xkg-color-yellow xqrxx-title-detail"
                        @click="showUserInfo(scope.row.jobid)"
                      >人员档案</span>
                    </div>
                    <div>{{scope.row.companyname}}</div>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="岗位" width="120">
                <template slot-scope="scope">
                  <el-form-item>
                    <div :class="[scope.row.newPosition == scope.row.oldPosition ? '' : 'altered']">
                      <el-select
                        v-model="scope.row.newPosition"
                        placeholder="请选择"
                        :ref="'gwDom' + scope.$index"
                        @change="gwChange( scope.$index)"
                        @focus="depFocus(scope.row.newDepid)"
                      >
                        <el-option
                          v-for="item in position_options"
                          :key="item.dictid"
                          :label="item.dictname"
                          :value="item.dictid"
                        ></el-option>
                      </el-select>
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="外包方式" width="130">
                <template slot-scope="scope">
                  <el-form-item>
                    <div
                      :class="[scope.row.newOutsourcingtype == scope.row.oldOutsourcingtype ? '' : 'altered']"
                    >
                      <el-select
                        v-model="scope.row.newOutsourcingtype"
                        placeholder="请选择"
                        :ref="'wbfsDom' + scope.$index"
                        @change="wbfsChange(scope.row.newOutsourcingtype, scope.$index, scope.row.newDepid, scope.row.companyid)"
                      >
                        <el-option
                          v-for="item in outsourcetype_options"
                          :key="item.dictid"
                          :label="item.dictname"
                          :value="item.dictid"
                        ></el-option>
                      </el-select>
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="结算方式" width="130">
                <template slot-scope="scope">
                  <el-form-item>
                    <div :class="[scope.row.newUsetype == scope.row.oldUsetype ? '' : 'altered']">
                      <el-select
                        v-model="scope.row.newUsetype"
                        placeholder="请选择"
                        :disabled="scope.row.newOutsourcingtype == 1"
                        :ref="'jsfsDom' + scope.$index"
                        @change="jsfsChange(scope.$index)"
                      >
                        <el-option
                          v-for="item in usertype_options"
                          :key="item.dictid"
                          :label="item.dictname"
                          :value="item.dictid"
                        ></el-option>
                      </el-select>
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="所属部门" :render-header="showHelp" width="230">
                <template slot-scope="scope">
                  <el-form-item>
                    <div :class="[scope.row.newDepname == scope.row.oldDepname ? '' : 'altered']">
                      <el-select
                        v-model="scope.row.newDepid"
                        filterable
                        placeholder="请选择"
                        :ref="'ssbmDom' + scope.$index"
                        @change="depChange(scope.row.newDepid, scope.$index)"
                        value-key="id"
                      >
                        <el-option
                          v-for="(item,index) in deptno_options"
                          :key="index"
                          :value="item.id"
                          :label="item.text"
                        ></el-option>
                      </el-select>
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column prop="newGroupid" label="所在小组" width="230">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="`infos.${scope.$index}.newGroupid`"
                    :rules="[{ required: true, message: '请选择所在小组', trigger: 'change' }]"
                  >
                    <div :class="[scope.row.newGroupid == scope.row.oldGroupid ? '' : 'altered']">
                      <el-select
                        v-model="scope.row.newGroupid"
                        placeholder="请选择"
                        :ref="'szxzDom' + scope.$index"
                        @change="szxzChange(scope.$index)"
                      >
                        <el-option
                          v-for="item in scope.row.teams"
                          :key="item.id"
                          :label="item.groupname"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column prop="newOrganizers" label="行内负责人" width="170">
                <template slot-scope="scope">
                  <el-form-item>
                    <div
                      :class="[scope.row.newOrganizers == scope.row.oldOrganizers ? '' : 'altered']"
                    >
                      <el-select
                        v-model="scope.row.newOrganizers"
                        filterable
                        placeholder="请选择"
                        :ref="'hnfzrDom' + scope.$index"
                        @change="hnfzrChange(scope.$index)"
                      >
                        <el-option
                          v-for="(item, index) in scope.row.organizers[0].children"
                          :key="item.index"
                          :label="item.text"
                          :value="item.pcode"
                        ></el-option>
                      </el-select>
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="系统名称" width="230" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div
                    v-if="scope.row.newPosition == '11' || scope.row.newPosition == '12' || scope.row.newPosition == '18' || scope.row.newPosition == '24' || scope.row.newPosition == '33'"
                    key="1"
                  >
                    <el-form-item
                      :prop="`infos.${scope.$index}.newSysno`"
                      :rules="[{ required: true, message: '请填写系统名称', trigger: 'change' }]"
                    >
                      <div :class="[scope.row.newSysno == scope.row.oldSysno ? '' : 'altered']">
                        <!-- <el-cascader :ref="'systemName' + scope.$index" :options="systemnos_options"
                                                    @change="xtmcChange(scope.row.newSysno,scope.$index)" :props="systemParams"
                        v-model="scope.row.newSysno" :show-all-levels="false" filterable></el-cascader>-->
                        <single-choice
                          :treeData="systemnos_options"
                          :input="scope.row.newSysname"
                          :treeProps="xtmcProps"
                          nodeKey="id"
                          :fns="xtmcNodeChange"
                          :rowId="scope.$index"
                          flrcLowerName="系统名称："
                          flrcLowerField="text"
                          :filterNodeMethod="xtmcFilterNode"
                          flrcUpName="系统编号："
                          flrcUpField="pcode"
                          titleName="系统名称"
                          flRightTitleName="选中的系统"
                        ></single-choice>
                      </div>
                    </el-form-item>
                  </div>
                  <div v-else key="2">
                    <el-form-item>
                      <div :class="[scope.row.newSysno == scope.row.oldSysno ? '' : 'altered']">
                        <!-- <el-cascader :ref="'systemName' + scope.$index" :options="systemnos_options"
                                                @change="xtmcChange(scope.row.newSysno,scope.$index)" :props="systemParams"
                        v-model="scope.row.newSysno" :show-all-levels="false" filterable></el-cascader>-->
                        <single-choice
                          :treeData="systemnos_options"
                          :input="scope.row.newSysname"
                          :treeProps="xtmcProps"
                          nodeKey="id"
                          :fns="xtmcNodeChange"
                          :rowId="scope.$index"
                          flrcLowerName="系统名称："
                          flrcLowerField="text"
                          :filterNodeMethod="xtmcFilterNode"
                          flrcUpName="系统编号："
                          flrcUpField="pcode"
                          titleName="系统名称"
                          flRightTitleName="选中的系统"
                        ></single-choice>
                      </div>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <!-- 新增在场情况 开始 -->
              <el-table-column prop="newPresenceState" label="在场情况" width="170">
                <template slot-scope="scope">
                  <el-form-item>
                    <div
                      :class="[scope.row.newPresenceState == scope.row.oldPresenceState ? '' : 'altered']"
                    >
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
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>
              <!-- 项目 开始 -->
              <el-table-column label="项目名称" width="230">
                <template slot-scope="scope">
                  <div
                    v-if="scope.row.newPosition == '11' || scope.row.newPosition == '12' || scope.row.newPosition == '18' || scope.row.newPosition == '24' || scope.row.newPosition == '33'"
                    key="1"
                  >
                    <el-form-item
                      :prop="`infos.${scope.$index}.newProjectno`"
                      :rules="[{ required: true, message: '请填写项目名称', trigger: 'change' }]"
                    >
                      <!-- <div :class="[scope.row.newProjectno == scope.row.oldProjectno ? '' : 'altered']">
                                                <el-cascader :ref="'xmName' + scope.$index" :options="projectno_options"
                                                    :props="projectnoParams" v-model="scope.row.newProjectno"
                                                    :show-all-levels="false" filterable @change="xmmcChange(scope.row.newProjectno,scope.$index)"></el-cascader>
                      </div>-->
                      <div
                        :class="[scope.row.newProjectno == scope.row.oldProjectno ? '' : 'altered']"
                      >
                        <single-choice
                          :disabled="scope.row.disabled"
                          :disablBtn="scope.row.disabled"
                          :ref="`project${scope.$index}`"
                          :treeData="projectno_options"
                          :input="scope.row.newProjectname"
                          :treeProps="xmmcProps"
                          nodeKey="projectno"
                          :fns="xmmcNodeChange"
                          :rowId="scope.$index"
                          flrcLowerName="项目名称："
                          flrcLowerField="projectname"
                          :filterNodeMethod="xmmcFilterNode"
                          flrcUpName="项目经理："
                          flrcUpField="manausername"
                          titleName="项目名称"
                          flRightTitleName="选中的项目"
                        ></single-choice>
                      </div>
                    </el-form-item>
                  </div>
                  <div v-else key="2">
                    <!-- <div :class="[scope.row.newProjectno == scope.row.oldProjectno ? '' : 'altered']">
                                            <el-cascader :ref="'xmName' + scope.$index" :options="projectno_options"
                                                :props="projectnoParams" v-model="scope.row.newProjectno"
                                                :show-all-levels="false" filterable @change="xmmcChange(scope.row.newProjectno,scope.$index)"></el-cascader>
                    </div>-->
                    <el-form-item>
                      <div
                        :class="[scope.row.newProjectno == scope.row.oldProjectno ? '' : 'altered']"
                      >
                        <single-choice
                        :disabled="scope.row.disabled"
                          :disablBtn="scope.row.disabled"
                          :ref="`project${scope.$index}`"
                          :treeData="projectno_options"
                          :input="scope.row.newProjectname"
                          :treeProps="xmmcProps"
                          nodeKey="projectno"
                          :fns="xmmcNodeChange"
                          :rowId="scope.$index"
                          flrcLowerName="项目名称："
                          flrcLowerField="projectname"
                          :filterNodeMethod="xmmcFilterNode"
                          flrcUpName="项目经理："
                          flrcUpField="manausername"
                          titleName="项目名称"
                          flRightTitleName="选中的项目"
                        ></single-choice>
                      </div>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <!-- 项目 结束 -->
              <!-- 采购项目 开始 -->
              <el-table-column prop="purchaseItem" label="采购项目" width="170">
                <template slot-scope="scope">
                  <el-form-item>
                    <div
                      :class="[scope.row.newPurchaseItemId == scope.row.oldPurchaseItemId ? '' : 'altered']"
                    >
                      <el-select
                        :ref="`purchase${scope.$index}`"
                        v-model="scope.row.newPurchaseItemId"
                        :disabled="scope.row.disabled"
                        placeholder
                        filterable
                        :filter-method="handleFilterCg"
                        @change="handlePurchase(scope)"
                        clearable
                      >
                        <el-option
                          v-for="(item, index) in purchaseItem_options"
                          :key="index"
                          :label="item.xmmc"
                          :value="item.zbbh"
                        ></el-option>
                      </el-select>
                    </div>
                  </el-form-item>
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
                  <el-form-item>
                    <!-- 人力 -->
                    <div v-if="scope.row.newOutsourcingtype == 1" key="1">
                      <el-select
                        v-model="scope.row.newContractid"
                        filterable
                        placeholder="请选择"
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
                    <div
                      v-else
                      key="2"
                      :class="[scope.row.newContractid == scope.row.oldContractid ? '' : 'altered']"
                    >
                      <el-select
                        v-model="scope.row.newContractid"
                        filterable
                        placeholder="请选择"
                        :ref="'xmhtDom' + scope.$index"
                        @change="xmhtChange(scope.$index)"
                      >
                        <el-option
                          v-for="(item, index) in scope.row.projectContracts"
                          :key="item.index"
                          :label="item.htmc"
                          :value="item.htbh"
                        ></el-option>
                      </el-select>
                    </div>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column label="操作">
                <template slot-scope="scope">
                  <div class="operation">
                    <el-button
                      @click.native.prevent="deleteRow(scope.$index, ryxxbgData.infos, scope.row)"
                    >
                      <i class="el-icon-delete"></i>
                    </el-button>
                  </div>
                </template>
              </el-table-column>
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
      <div class="nav-bottom-left">
        <button-delete :buttonName="buttonDel" :size="size" :fns="delBox"></button-delete>
      </div>
      <div class="nav-bottom-right">
        <button-common
          :buttonName="buttonKeep"
          :size="size"
          :fns="saveDraft"
          class="nav-bottom-right-save"
        ></button-common>
        <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
      </div>
    </div>

    <!-- 选择变更人员弹窗 -->
    <el-dialog title="变更人员" :visible.sync="flDialog" class="flDialogCon">
      <div class="flDialogBody">
        <div class="flDialogBody_title">选择需要变更信息的外包人员</div>
        <div class="flDialogBody_con">
          <el-row>
            <el-col :span="12">
              <div class="flDialogBody_con_left">
                <div class="flDialogBody_con_left_input">
                  <el-input placeholder="搜索人员名称" v-model="filterText" prefix-icon="el-icon-search"></el-input>
                </div>
                <el-scrollbar style="height:100%">
                  <div class="flDialogBody_con_tree">
                    <!-- <el-tree v-if="currentUserData.flag == undefined" :data="treeData" :props="treeProps"
                                            lazy :load="loadNode" ref="tree" @node-click="treeClick" node-key="id"
                    :highlight-current="true" :default-expanded-keys="['dep131']"></el-tree>-->
                    <!-- 两层数据 -->
                    <el-tree
                      :data="treeData"
                      :props="treeProps"
                      lazy
                      :load="loadNodeWy"
                      ref="tree"
                      @node-click="treeClick"
                      node-key="id"
                      :highlight-current="true"
                      :filter-node-method="filterNode"
                    ></el-tree>
                  </div>
                </el-scrollbar>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="flDialogBody_con_right">
                <div class="flDialogBody_con_right_title">已选的人员</div>
                <el-scrollbar style="height:100%">
                  <div class="flDialogBody_con_right_main" v-if="ifwbtcRight">
                    <ul class="flDialogBody_ul">
                      <div class="flList">
                        <div>
                          <li class="flDialogBody_ul_up">{{wbname}}</li>
                          <li class="flDialogBody_ul_low">
                            部门：
                            <span>{{wbDept}}</span>
                          </li>
                        </div>
                        <div class="el-icon-circle-close" @click="flDel()"></div>
                      </div>
                    </ul>
                  </div>
                </el-scrollbar>
              </div>
            </el-col>
          </el-row>
        </div>
        <div class="wrapper_bottom">
          <el-row :gutter="10" type="flex" justify="end">
            <el-col :span="1.5">
              <button-common :buttonName="buttonCancel" :size="size" :fns="flCancel"></button-common>
            </el-col>
            <el-col :span="1.5">
              <button-orange :buttonName="buttonSure" :size="size" :fns="flSure"></button-orange>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件1
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import buttonDelete from "@/components/eleButton/delete"; //引入删除按钮
import {
  save,
  liuzhuan,
  queryCenterDepTree,
  queryOutBaseInfos,
  delBgTableData,
  getFLowInfo,
  queryContracts,
  queryOrInsertPpmRlwbhtInfo,
  getOutBaseInfo
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
import { doDel } from "@/api/common/flowApi";
import Moment from "moment";
import { getDicEntry } from "@/api/eosDictEntry";
import helpHint from "@/components/helpHint";
import { mapGetters } from "vuex";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import singleChoice from "@/components/selectDialog/singleChoice";
export default {
  name: "xxbgSaveBack",
  components: {
    buttonOrange,
    buttonCommon,
    buttonDelete,
    helpHint,
    flowLogs,
    singleChoice
  },
  data() {
    return {
      buttonClose: "关 闭", //取消按钮名字
      size: "mini", //按钮大小
      buttonDel: "删 除",
      buttonKeep: "存草稿",
      buttonCommit: "提 交",
      ryxxbgData: {
        applyer: "", //申请人姓名
        deptname: "", //部门名称
        applytime: "", //申请时间
        flowid: "", //流程编号
        actChangetime: "", //实际变更时间
        changereason: "", //变更理由
        infos: [] //表中数据
      },
      workItemID: "",
      processDefName: "", //流程定义名称
      delDialog: false, //是否显示删除弹窗
      bps: {},
      id: "", //主键ID
      instanceid: "", //流程ID
      flDialog: false, //是否选择变更人员弹窗
      buttonCancel: "取 消",
      buttonSure: "确 定",
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
      treeData: [], //机构人员数据
      treeProps: {
        label: "text",
        children: "children",
        isLeaf: "isLeaf"
      },
      groupid_options: [],
      filterText: "", //过滤机构树
      treeCurrentDetial: [], //选取的变更人员
      bgryDetail: "", //变更人员详细信息
      wbname: "", //变更人员名字
      wbDept: "", //外包人员所在部门
      ifwbtcRight: false, //是否显示变更人员弹窗右边
      currentUserData: {}, //当前登陆人信息
      admin: [], //流程负责人
      ifTreeClick: false, //机构树是否选中
      xmmcProps: {
        //项目名称树配置
        label: "projectname",
        children: "children"
      },
      xtmcProps: {
        //系统名称树配置
        label: "text",
        children: "children"
      },
      pickerOptions0: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
      presenceStateData: [], // 在场情况
      purchaseItem_options: [], // 采购项目初始数据
      purchaseItemCopy : [],    // 采购项目初始数据复制
      purchaseDisabled: false, //采购是否可读，默认：可读可写
      projectnoTemp: [] // 临时项目集合数据
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  computed: {
    ...mapGetters(["deptId"])
  },
  mounted() {},
  methods: {
    // 初始化
    init(item) {
      console.log("外包信息变更", item);
      this.bps = item;
      this.getBgDetail(item.processInstID);
      this.$refs.log.init(item.processInstID);
      getDicEntry({
        dicttypeid:
          "NEWKG_WBFS,NEWKG_JSFS,BUSI_OUTSOURCE_POSITION,OUT_CHANGE_ADMIN,XKG_ZCSTATE"
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
        // 流程负责人
        this.admin = data.filter(item => {
          return item.dicttypeid == "OUT_CHANGE_ADMIN";
        });
         // 岗位
        this.position_options = data.filter(item => {
          return item.dicttypeid == "BUSI_OUTSOURCE_POSITION";
        });
        // 在场情况
        this.presenceStateData = data.filter(item => {
          return item.dicttypeid == "XKG_ZCSTATE";
        });
        this.presenceStateData.forEach(item => {
          if (
            item.dictid === "2" ||
            item.dictid === "3" ||
            item.dictid === "4"
          ) {
            item.disabled = true;
          }
        });
        return data;
      });

      console.log("ddd", this.presenceStateData);
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

       // 获取岗位
      getPosition(val) {
        if (val == undefined) {
          return
        }
        getDicEntry({
          dicttypeid: "BUSI_OUTSOURCE_POSITION"
        })
          .then(res => {
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
          });
      },

      // 部门获取焦点
      depFocus(val) {
        console.log('部门获取焦点', val)
        this.getPosition(val);
      },

    //查询中心部门树数据
    searchDepTree() {
      let params = {};
      params.depid = this.deptId;
      queryCenterDepTree(params)
        .then(res => {
          if (res.data.status == 200) {
            this.treeData = res.data.results;
          } else {
            this.$message.error("查询中心部门树数据");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 机构树懒加载
    loadNode(node, resolve) {
      if (node.level === 0) {
        return resolve(node.data);
      }

      if (node.level === 1) {
        if (node.data.children != undefined) {
          return resolve(node.data.children);
        } else {
          return resolve([]);
        }
      }
      if (node.level === 2) {
        this.getCurrentDepPerson(node.data.id, resolve);
        return resolve([]);
      }
      if (node.level > 2) {
        return resolve([]);
      }
    },

    // 机构树懒加载(文员)
    loadNodeWy(node, resolve) {
      if (node.level === 0) {
        return resolve(node.data);
      }
      if (node.level === 1) {
        this.getCurrentDepPerson(node.data.id, resolve);
        return resolve([]);
      }
      if (node.level > 1) {
        return resolve([]);
      }
    },

    // 点击部门加载人员
    getCurrentDepPerson(id, resolve) {
      let depId = id.replace("dep", "");
      let params = {};
      params.deptId = depId;
      let data = common.paramsFormat(params);
      queryOutBaseInfos(data)
        .then(res => {
          if (res.data.status == 200) {
            let data = res.data.results;
            data.forEach(item => {
              item.text = item.userName;
              item.isLeaf = true;
            });
            resolve(data);
          } else {
            this.$message.error("加载人员失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 机构树选中
    treeClick(node, nodeArr, currentEle) {
      if (nodeArr.level == 2) {
        this.ifwbtcRight = true;
        let params = {};
        params.jobId = node.jobId;
        let data = common.paramsFormat(params);
        getOutBaseInfo(data)
          .then(res => {
            if (res.data.status == 200) {
              if (res.data.results.newContractid == "0000") {
                res.data.results.newContractname = "暂未签订合同";
                res.data.results.oldContractname = "暂未签订合同";
              }
              let dataPeo = res.data.results;
              dataPeo.newDepname = dataPeo.newDepname.replace(
                "总行金融科技部-",
                ""
              );
              dataPeo.oldDepname = dataPeo.oldDepname.replace(
                "总行金融科技部-",
                ""
              );
              dataPeo.newOrganizersname =
                dataPeo.newOrganizersname + "(" + dataPeo.newOrganizers + ")";
              dataPeo.oldOrganizersname =
                dataPeo.oldOrganizersname + "(" + dataPeo.oldOrganizers + ")";
              this.bgryDetail = dataPeo;
              this.wbDept = dataPeo.newDepname;
              this.wbname = dataPeo.username;
              this.ifTreeClick = true;
              // 新增 在场情况如果是
              dataPeo.disabled = true;
              // 初始状态下是正常，提前入场、离场、挂起 则置灰
              if (
                dataPeo.newPresenceState === "1" ||
                dataPeo.newPresenceState === "2" ||
                dataPeo.newPresenceState === "4"
              ) {
                dataPeo.presenceDisabled = true;
              }
            } else {
              this.$message.error("查询人员详情失败");
            }
          })
          .catch(error => {
            this.$message.error(error);
          });
      }
    },

    // 机构树过滤
    filterNode(value, data) {
      if (!value) return true;
      return data.text.indexOf(value) !== -1;
    },

    // 系统名称树过滤
    xtmcFilterNode(value, data) {
      if (!value) return true;
      return data.text.indexOf(value) !== -1;
    },

    // 项目名称树过滤
    xmmcFilterNode(value, data) {
      if (!value) return true;
      return data.projectname.indexOf(value) !== -1;
    },

    flDel() {
      this.ifwbtcRight = false;
      this.wbname = "";
      this.wbDept = "";
    },

    //变更人员弹窗确认
    flSure() {
      if (this.ifTreeClick == false) {
        this.$message.error("请选择人员");
        return;
      }
      let ifcm = true;
      if (this.ryxxbgData.infos.length != 0) {
        for (var i = 0; i < this.ryxxbgData.infos.length; i++) {
          if (this.ryxxbgData.infos[i].jobid == this.bgryDetail.jobid) {
            ifcm = false;
          }
        }
      }
      if (ifcm) {
        if (this.bgryDetail.newContractid == "1108") {
          this.bgryDetail.newContractid = "";
          this.bgryDetail.oldContractid = "";
        }
        this.searchGroup(this.bgryDetail.newDepid);
        this.flDialog = false;
        this.ifwbtcRight = false;
        this.wbname = "";
        this.wbDept = "";
      } else {
        this.$message.error("人员选择重复");
      }
    },

    //变更人员弹窗取消
    flCancel() {
      this.flDialog = false;
      this.ifwbtcRight = false;
      this.wbname = "";
      this.wbDept = "";
    },

    // 新增一行
    addRow() {
      this.filterText = "";
      this.ifTreeClick = false;
      this.searchDepTree();
      this.flDialog = true;
      this.ifwbtcRight = false;
      this.wbname = "";
      this.wbDept = "";
    },

    // 删除行
    deleteRow(index, rows, row) {
      if (rows.length != 1 && row.id == undefined) {
        rows.splice(index, 1);
      } else if (rows.length != 1 && row.id != undefined) {
        this.delTableData(index, rows, row);
      } else {
        this.$message.error("不能少于一行");
      }
    },

    // 删除表中数据
    delTableData(index, rows, row) {
      let params = {};
      params.id = row.id;
      let data = common.paramsFormat(params);
      delBgTableData(data).then(res => {
        if (res.data.status == 200) {
          rows.splice(index, 1);
        } else {
          this.errorMessage = "删除失败";
          this.saveError();
        }
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
          if (this.bgryDetail.newOutsourcingtype == 0) {
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
            res.data.results = res.data.results.filter(li => {
              li.id = li.id.toString();
              return li;
            });
            res.data.results.push({
              id: "0000",
              htmc: "暂未签订合同"
            });
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

    // 依据外包方式查询合同(项目)
    searchContractsBg(id, index) {
      const loading = common.loading(this);
      let params = {};
      let depid = id;
      params.dealdeptid = depid.replace("dep", "");
      queryContracts(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.ryxxbgData.infos[index].newContractid = "";
            this.ryxxbgData.infos[index].newContractname = "";
            res.data.results = res.data.results.filter(li => {
              li.id = li.id.toString();
              return li;
            });
            res.data.results.push({
              id: "0000",
              htmc: "暂未签订合同"
            });
            this.$set(
              this.ryxxbgData.infos[index],
              "projectContracts",
              res.data.results
            );
            this.ryxxbgData.infos.splice(0, 0);
          } else {
            this.$message.error("查询合同失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 依据外包方式查询合同(人力)
    searchhtRlwbBg(id, index) {
      const loading = common.loading(this);
      queryOrInsertPpmRlwbhtInfo({ orgcode: id })
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.ryxxbgData.infos[index].personnelContracts = res.data.results;
            this.$set(
              this.ryxxbgData.infos[index],
              "newContractid",
              res.data.results[0].htid
            );
            this.$set(
              this.ryxxbgData.infos[index],
              "newContractname",
              res.data.results[0].htmc
            );
            this.ryxxbgData.infos.splice(0, 0);
          } else {
            this.$message.error("查询合同失败");
          }
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
            // res.data.results[1].children[0].children = res.data.results[1].children[0].children.filter(li => {
            //     li.text = li.text.replace('永赢金租金融科技部-', '');
            //     return li;
            // });
            this.systemnos_options = res.data.results[0].children;
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
      let self = this;
      getTree()
        .then(res => {
          if (res.data.status == 200) {
            var reg = /([^-]+)$/;
            let projectItems = res.data.results.filter(li => {
              li.projectname = li.projectname.match(reg)[1];
              return li;
            });
            self.projectno_options = projectItems;
            // 处理,防止对象引用
            self.projectnoTemp = JSON.parse(JSON.stringify(projectItems));
          } else {
            this.$message.error("获取项目名称数据失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 外包方式变化
    wbfsChange(val, index, id, orgcode) {
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newOutsourcingtypename = this.$refs[
          "wbfsDom" + index
        ].selectedLabel;
        this.ryxxbgData.infos[index].newOutsourcingtype = this.$refs[
          "wbfsDom" + index
        ].value;
        if (val == 0) {
          this.searchContractsBg(id, index);
        } else {
          this.$set(this.ryxxbgData.infos[index], "newUsetype", "0");
          this.$set(this.ryxxbgData.infos[index], "newUsetypename", "人力结算");
          this.searchhtRlwbBg(orgcode, index);
        }
      }, 10);
    },

    // 所属部门变化
    depChange(id, index) {
      // this.searchContractsBg(id, index);
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newDepname = this.$refs[
          "ssbmDom" + index
        ].selectedLabel;
        this.ryxxbgData.infos[index].newDepid = this.$refs[
          "ssbmDom" + index
        ].value;
        this.searchGroupDep(id, index);
        this.searchEpmloDep(id, index);
      }, 10);
    },

    //查询小组名称(部门变化)
    searchGroupDep(id, index) {
      const loading = common.loading(this);
      let params = {};
      let depid = id;
      params.depid = depid.replace("dep", "");
      queryGroup(params)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.ryxxbgData.infos[index].teams = res.data.results;
            if (
              this.ryxxbgData.infos[index].newDepid !=
              "dep" + this.ryxxbgData.infos[index].oldDepid
            ) {
              // this.ryxxbgData.infos[index].newGroupid = '';
              // this.ryxxbgData.infos[index].newGroupname = '';
              if (this.ryxxbgData.infos[index].newGroupid != undefined) {
                this.ryxxbgData.infos[index].newGroupid = "";
                this.ryxxbgData.infos[index].newGroupname = "";
              }
            } else {
              this.ryxxbgData.infos[index].newGroupid = this.ryxxbgData.infos[
                index
              ].oldGroupid;
            }
            this.ryxxbgData.infos.splice(0, 0);
          } else {
            this.$message.error("查询小组名称失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询行内负责人(部门变化)
    searchEpmloDep(id, index) {
      const loading = common.loading(this);
      let depid = id.replace("dep", "");
      queryEmployeeTree(depid)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            // this.bgryDetail.organizers = res.data.results;
            this.ryxxbgData.infos[index].organizers = res.data.results;
            if (
              this.ryxxbgData.infos[index].newDepid !=
              "dep" + this.ryxxbgData.infos[index].oldDepid
            ) {
              // this.ryxxbgData.infos[index].newOrganizers = '';
              // this.ryxxbgData.infos[index].newOrganizersname = '';
              if (this.ryxxbgData.infos[index].newOrganizers != undefined) {
                this.ryxxbgData.infos[index].newOrganizers = "";
                this.ryxxbgData.infos[index].newOrganizersname = "";
              }
            } else {
              this.ryxxbgData.infos[
                index
              ].newOrganizers = this.ryxxbgData.infos[index].oldOrganizers;
            }
            this.ryxxbgData.infos.splice(0, 0);
          } else {
            this.$message.error("查询行内负责人失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 岗位变化
    gwChange(index) {
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newPositionname = this.$refs[
          "gwDom" + index
        ].selectedLabel;
        this.ryxxbgData.infos[index].newPosition = this.$refs[
          "gwDom" + index
        ].value;
      }, 10);
    },

    // 结算方式变化
    jsfsChange(index) {
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newUsetypename = this.$refs[
          "jsfsDom" + index
        ].selectedLabel;
        this.ryxxbgData.infos[index].newUsetype = this.$refs[
          "jsfsDom" + index
        ].value;
      }, 10);
    },

    //所在小组变化
    szxzChange(index) {
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newGroupname = this.$refs[
          "szxzDom" + index
        ].selectedLabel;
        this.ryxxbgData.infos[index].newGroupid = this.$refs[
          "szxzDom" + index
        ].value;
      }, 10);
    },

    // 行内负责人变化
    hnfzrChange(index) {
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newOrganizersname = this.$refs[
          "hnfzrDom" + index
        ].selectedLabel;
        this.ryxxbgData.infos[index].newOrganizers = this.$refs[
          "hnfzrDom" + index
        ].value;
      }, 10);
    },

    // 系统名称变化
    xtmcChange(id, index) {
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newSysname = this.$refs[
          "systemName" + index
        ].getCheckedNodes()[0].label;
      }, 10);
    },

    // 项目名称变化
    xmmcChange(id, index) {
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newProjectname = this.$refs[
          "xmName" + index
        ].getCheckedNodes()[0].label;
      }, 10);
    },

    // 项目合同变化
    xmhtChange(index) {
      setTimeout(() => {
        this.ryxxbgData.infos.splice(0, 0);
        this.ryxxbgData.infos[index].newContractname = this.$refs[
          "xmhtDom" + index
        ].selectedLabel;
        this.ryxxbgData.infos[index].newContractid = this.$refs[
          "xmhtDom" + index
        ].value;
      }, 10);
    },

    // 系统名称节点变化
    xtmcNodeChange(node, index) {
      this.ryxxbgData.infos[index].newSysname = node.text;
      this.ryxxbgData.infos[index].newSysno = node.pcode;
      this.ryxxbgData.infos.splice(0, 0);
    },

    // 项目名称节点变化
    xmmcNodeChange(node, index) {
      let self = this;
      let info = self.ryxxbgData.infos[index];
      info.newProjectname = node.projectname;
      info.newProjectno = node.projectno;
      if (info.newPresenceState === "1") {
        info.newPurchaseItemId = node.glcglc;
      }

      self.ryxxbgData.infos.splice(0, 0);
    },

    // 打开删除messageBox窗口
    delBox() {
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

    // 保存需求信息
    saveDraft() {
      if (
        this.ryxxbgData.infos.length == 0 ||
        this.ryxxbgData.infos.length == undefined
      ) {
        this.$message.error("表中至少有一条数据");
        return;
      }
      console.log("表单", this.ryxxbgData);
      return false;
      const loading = common.loading(this);
      save(this.ryxxbgData).then(res => {
        // 关闭loading
        loading.close();
        if (res.data.status == 200) {
          this.$message.success("保存成功");
          // this.ryxxbgData.infos = res.data.results.infos;
          this.getBgDetail(res.data.results.instanceid);
        } else {
          this.$message.error("保存草稿失败");
        }
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
            res.data.results.applytime = Moment(
              res.data.results.applytime
            ).format("YYYY-MM-DD");
            res.data.results.infos = res.data.results.infos.filter(li => {
              if (li.newDepid.indexOf("dep") == -1) {
                li.newDepid = "dep" + li.newDepid;
              }
              if (li.oldDepid.indexOf("dep") == -1) {
                li.oldDepid = "dep" + li.oldDepid;
              }
              return li;
            });
            this.ryxxbgData = res.data.results;
            console.log("ryxxbgData", this.ryxxbgData);
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
          } else {
            this.$message.error("查询变更详情失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 提交本页面数据
    commit() {
      const loading = common.loading(this);
      this.ryxxbgData.infos = this.ryxxbgData.infos.filter(li => {
        li.newDepid = li.newDepid.replace("dep", "");
        li.oldDepid = li.oldDepid.replace("dep", "");
        return li;
      });
      this.ryxxbgData.bps = this.bps;
      liuzhuan(this.ryxxbgData).then(res => {
        loading.close();
        if (res.data.status == 200) {
          let message = common.getFlowMessge(res.data.message);
          let messageArr = message.split(",");
          // this.messageNotify(messageArr);
          messageArr[2] = messageArr[2].split(")")[0] + ")";
          this.messageNotifyMore(messageArr);
          this.$router.push({
            name: "wbxxbgFinish",
            params: {
              finishData: "1"
            }
          });
        } else {
          // this.$message.error('操作失败，无法找到下一节点，请联系流程负责人' + this.admin[0].dictname);
          this.$message.error("操作失败，无法找到下一节点");
        }
      });
    },

    // 验证必填项并提交
    proving() {
      let ifbg = true;
      // this.ryxxbgData.infos.forEach(item => {
      //     if (item.newOutsourcingtypename != item.oldOutsourcingtypename) {
      //         ifbg = true
      //     } else if (item.newDepname != item.oldDepname) {
      //         ifbg = true
      //     } else if (item.newPositionname != item.oldPositionname) {
      //         ifbg = true
      //     } else if (item.newUsetypename != item.oldUsetypename) {
      //         ifbg = true
      //     } else if (item.newGroupname != item.oldGroupname) {
      //         ifbg = true
      //     } else if (item.newOrganizersname != item.oldOrganizersname) {
      //         ifbg = true
      //     } else if (item.newSysname != item.oldSyaname) {
      //         ifbg = true
      //     } else if (item.newProjectname != item.oldProjectname) {
      //         ifbg = true
      //     } else if (item.newContractname != item.oldContractname) {
      //         ifbg = true
      //     }
      // })
      this.ryxxbgData.infos.forEach(item => {
        if (
          item.newOutsourcingtypename != item.oldOutsourcingtypename ||
          item.newDepname != item.oldDepname ||
          item.newPositionname != item.oldPositionname ||
          item.newUsetypename != item.oldUsetypename ||
          item.newGroupname != item.oldGroupname ||
          item.newOrganizersname != item.oldOrganizersname ||
          item.newSysname != item.oldSyaname ||
          item.newProjectname != item.oldProjectname ||
          item.newContractname != item.oldContractname
        ) {
          ifbg = true;
        } else {
          ifbg = false;
        }
        // 新增 信息变更 挂起->正常 采购项目、项目名称必填，挂起->新项目开发  项目名称必填
        if(item.newPresenceState === "1" 
            && (!item.newPurchaseItemId 
            || !item.newProjectno) ) {
            ifbg = false;
        }
        if(item.newPresenceState === "6" && !item.newProjectno ) {
          ifbg = false;
        }
      });
      if (!ifbg ) {
        this.$message.error("请确保所有人员已选择变更内容");
        return;
      }
      for (var i = 0; i < this.ryxxbgData.infos.length; i++) {
        if (
          this.ryxxbgData.infos[0].newDepid != this.ryxxbgData.infos[i].newDepid
        ) {
          this.$message.error("请确保本次提交变更人员的二级部相同");
          return;
        }
      }
      this.ryxxbgData.infos.splice(0, 0);
      this.$refs["ryxxbgForm"].validate(valid => {
        if (valid) {
          this.commit();
        } else {
          this.$message({
            showClose: true,
            message: "操作失败,请填写必填项",
            type: "error"
          });
          return false;
        }
      });
    },

    // 删除流程
    delPro() {
      const loading = common.loading(this);
      let params = {};
      params.processInstID = this.ryxxbgData.instanceid;
      params.workItemID = this.bps.workItemID;
      params.processDefName = this.bps.processDefName;
      let data = common.paramsFormat(params);
      doDel(data).then(res => {
        loading.close();
        if (res.data.status == 200) {
          this.$message.success("流程删除成功");
          this.$router.back();
        } else {
          this.$message.error("流程删除失败");
        }
      });
    },

    // 删除按钮
    deleteProcess() {
      this.delDialog = true;
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

    // 消息提示(多节点)
    messageNotifyMore(messageVal) {
      if (messageVal.length > 3) {
        messageVal[messageVal.length - 1] =
          messageVal[messageVal.length - 1].split(")")[0] + ")";
      }
      let messageSec = messageVal.slice(2).join("，");
      this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        message:
          "<div><div>" +
          messageVal[1] +
          "</div><div>" +
          messageSec +
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
        val.row.newProjectname = ""
        val.row.newProjectno = ""
        projectItem.ifClick = false;
        item.disabled = true;
        self.$message.warning("必须在2个月内转为正常，关联采购项目");
        self.projectno_options = self.projectnoTemp;
        self.projectno_options.splice(0, 0);
      } else {
        // 正常
        // 清空项目名称和采购项目
        projectItem.inputContent = "";
        val.row.newProjectname = ""
        val.row.newProjectno = ""
        val.row.newPurchaseItemId = "";
        projectItem.ifClick = false;
        item.disabled = false;
        let newProject = [];
        // 过滤 只留01 正常的项目
        self.projectno_options.forEach(item => {
          if (item.proattrbutes === "01") {
            if (item.children && item.children.length > 0) {
              let projectTemp = item.children.filter(tt => {
                return tt.proattrbutes === "01";
              });
              item.children = projectTemp;
            }
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
      self.ryxxbgData.infos[self.indexTag].newPurchaseItemId = val;
      self.purchaseItem_options = self.purchaseItemCopy.filter(item => {
        if (
          !!~item.xmmc.indexOf(val) ||
          (item.sysName && !!~item.sysName.indexOf(val))
        ) {
          return item;
        }
      });
    } // handleFilterCg end
  }
};
</script>
<style scoped>
.xxbg-container {
  width: 100%;
  /* padding-bottom: 85px; */
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
  /* margin-left: 25px;
        margin-right: 25px; */
  padding: 0 25px;
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

.xqrxx-title-add {
  font-size: 14px;
  color: #faad14;
  font-weight: 500;
  margin-left: 15px;
  cursor: pointer;
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

.nav-bottom {
  display: flex;
  justify-content: space-between;
  padding: 15px 15px;
  width: 100%;
  background-color: #ffffff;
}

.nav-bottom-right-save {
  margin-right: 10px;
}

.operation .el-button {
  border: 1px solid #ffffff;
  padding: 0;
}

.blank {
  width: 100%;
  height: 30px;
}

/* 选择变更人员弹窗 */
.flDialogBody_con {
  border: 1px solid #d9d9d9;
  margin: 15px 0;
}

.flDialogBody_con_left {
  border-right: 1px solid #d9d9d9;
  padding: 10px 0 10px 10px;
}

.flDialogBody_con_left_input {
  padding-right: 10px;
}

.flDialogBody_con_right_title {
  font-size: 16px;
  font-weight: 550;
  color: #303133;
}

.flDialogBody_con_right_title span {
  margin: 0 5px;
}

.flDialogBody_con_tree {
  min-height: 300px;
  max-height: 300px;
  padding-top: 10px;
}

.flDialogBody_con_right_main {
  min-height: 310px;
  max-height: 310px;
  padding-top: 5px;
}

.flDialogBody_con_right {
  padding: 18px 0 0 15px;
}

.flDialogBody_ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.flDialogBody_ul div {
  margin-top: 8px;
}

.flDialogBody_ul_up {
  color: #303133;
  font-size: 14px;
  font-weight: 400;
}

.flDialogBody_ul_low {
  color: #c0c4cc;
  font-size: 14px;
  font-weight: 400;
  margin-top: 10px;
}

.flList {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-right: 10px;
}

.el-icon-circle-close {
  cursor: pointer;
}
.xqrxx-title-detail {
  font-size: 14px;
  font-weight: normal;
  cursor: pointer;
  margin-left: 10px;
}
</style>

<style>
/* 选择变更人员弹窗 */
.flDialogCon .el-dialog__body {
  padding: 0 20px 10px 20px;
}

.xqrxx-content .el-icon-delete {
  width: 11px;
  height: 11px;
  color: #f56c6c;
}

/* 变更后边框变红 */
.altered .el-input--medium .el-input__inner {
  border-color: #e02020;
}
</style>