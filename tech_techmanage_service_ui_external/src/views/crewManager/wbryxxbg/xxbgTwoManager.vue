<!-- 外包人员信息变更二级部经理审批 -->
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
              <span
                v-if="isinterview == 1"
                class="xkg-color-yellow xqrxx-title-detail"
                @click="searchDetail"
              >详情</span>
            </p>
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
              <el-table-column
                prop="newGroupid"
                label="所在小组"
                width="200"
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
              <el-table-column label="在场情况" width="200" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div :class="[scope.row.oldPresenceState == scope.row.newPresenceState ? '' : 'ifbg']">
                    <div>{{scope.row.oldPresenceStateName == undefined ? '无' : scope.row.oldPresenceStateName}}</div>
                    <div>{{scope.row.newPresenceStateName == undefined ? '无' : scope.row.newPresenceStateName}}</div>
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
          <el-table-column label="采购项目" width="200" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldPurchaseItem == scope.row.newPurchaseItem ? '' : 'ifbg']">
                <div>{{scope.row.oldPurchaseItem == undefined ? '无' : scope.row.oldPurchaseItem}}</div>
                <div>{{scope.row.newPurchaseItem == undefined ? '无' : scope.row.newPurchaseItem}}</div>
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
              <el-table-column label="所属部门" width="150" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <div :class="[scope.row.oldDepid == scope.row.newDepid ? '' : 'ifbg']">
                    <div>{{scope.row.oldDepname == undefined ? '无' : scope.row.oldDepname}}</div>
                    <div>{{scope.row.newDepname == undefined ? '无' : scope.row.newDepname}}</div>
                  </div>
                </template>
              </el-table-column>
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
              <el-table-column prop="recommendedPrice" label="建议单价 *" width="170">
                <template slot-scope="scope">
                  <!-- 等级1 -->
                  <div
                    v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)"
                  >
                    <el-form-item
                      :prop="`infos.${scope.$index}.recommendedPrice`"
                      :rules="scope.row.result == 1 ? [{ required: true, message: '请选择单价', trigger: 'blur' }] : rulePrice.recommendedPrice"
                      v-if="scope.row.grade == '1'"
                    >
                      <el-input-number
                        size="medium"
                        v-model="scope.row.recommendedPrice"
                        controls-position="right"
                        :step="500"
                        :disabled="scope.row.result == '0'"
                        @change="inputNumchange(scope.$index, scope.row.recommendedPrice, scope.row.initSuggestprice)"
                      ></el-input-number>
                    </el-form-item>
                    <!-- 等级2 -->
                    <el-form-item
                      :prop="`infos.${scope.$index}.recommendedPrice`"
                      :rules="scope.row.result == 1 ? [{ required: true, message: '请选择单价', trigger: 'blur' }] : rulePrice.recommendedPrice"
                      v-else-if="scope.row.grade == '2'"
                    >
                      <el-input-number
                        size="medium"
                        v-model="scope.row.recommendedPrice"
                        controls-position="right"
                        :step="500"
                        :disabled="scope.row.result == '0'"
                        @change="inputNumchange(scope.$index, scope.row.recommendedPrice, scope.row.initSuggestprice)"
                      ></el-input-number>
                    </el-form-item>
                    <!-- 等级3 -->
                    <el-form-item
                      :prop="`infos.${scope.$index}.recommendedPrice`"
                      :rules="scope.row.result == 1 ? [{ required: true, message: '请选择单价', trigger: 'blur' }] : rulePrice.recommendedPrice"
                      v-else-if="scope.row.grade == '3'"
                    >
                      <el-input-number
                        size="medium"
                        v-model="scope.row.recommendedPrice"
                        controls-position="right"
                        :step="500"
                        :disabled="scope.row.result == '0'"
                        @change="inputNumchange(scope.$index, scope.row.recommendedPrice, scope.row.initSuggestprice)"
                      ></el-input-number>
                    </el-form-item>
                    <!-- 等级4 -->
                    <el-form-item
                      :prop="`infos.${scope.$index}.recommendedPrice`"
                      :rules="scope.row.result == 1 ? [{ required: true, message: '请选择单价', trigger: 'blur' }] : rulePrice.recommendedPrice"
                      v-else
                    >
                      <el-input-number
                        size="medium"
                        v-model="scope.row.recommendedPrice"
                        controls-position="right"
                        :step="500"
                        :disabled="scope.row.result == '0'"
                        @change="inputNumchange(scope.$index, scope.row.recommendedPrice, scope.row.initSuggestprice)"
                      ></el-input-number>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="reason" label="修改理由" width="150">
                <template slot-scope="scope">
                  <div
                    v-if="(scope.row.newOutsourcingtype == 1 && scope.row.oldOutsourcingtype == 0) || (scope.row.newUsetype == 0 && scope.row.oldUsetype == 1)"
                  >
                    <div
                      v-if="scope.row.result == 1 && scope.row.recommendedPrice != scope.row.initSuggestprice"
                      key="1"
                    >
                      <el-form-item
                        :prop="`infos.${scope.$index}.reason`"
                        :rules="[{ required: true, message: '请输入修改理由', trigger: 'change' }]"
                      >
                        <el-input
                          type="textarea"
                          autosize
                          v-model="scope.row.reason"
                          placeholder="请输入"
                        ></el-input>
                      </el-form-item>
                    </div>
                    <div v-else key="2">
                      <el-form-item>
                        <el-input
                          type="textarea"
                          autosize
                          v-model="scope.row.reason"
                          placeholder
                          :disabled="true"
                        ></el-input>
                      </el-form-item>
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>

        <div v-if="isinterview == 1" class="xqrxx-content">
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
          <el-table-column label="在场情况" width="200" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldPresenceState == scope.row.newPresenceState ? '' : 'ifbg']">
                <div>{{scope.row.oldPresenceStateName == undefined ? '无' : scope.row.oldPresenceStateName}}</div>
                <div>{{scope.row.newPresenceStateName == undefined ? '无' : scope.row.newPresenceStateName}}</div>
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
          <el-table-column label="采购项目" width="200" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div :class="[scope.row.oldPurchaseItem == scope.row.newPurchaseItem ? '' : 'ifbg']">
                <div>{{scope.row.oldPurchaseItem == undefined ? '无' : scope.row.oldPurchaseItem}}</div>
                <div>{{scope.row.newPurchaseItem == undefined ? '无' : scope.row.newPurchaseItem}}</div>
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
  twoManager,
  queryList
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
      isinterview: "", //是否面试
      busData: {}, //打分数据
      rulePrice: {
        suggestprice: [{ required: false, message: "", trigger: "blur" }]
      },
      priceConsult: [], //单价参考
      scoreData: [], // 面试结果数据集合
      admin: [], //流程负责人
      ifDialog: false //是否显示详情弹窗
    };
  },
  created() {},
  mounted() {},
  methods: {
    // 初始化
    init(item) {
      console.log("外包信息变更111", item);
      this.bps = item;
      this.$refs.log.init(item.processInstID);
      this.getBgDetail(item.processInstID);
      getDicEntry({
        dicttypeid:
          "NEWKG_WBFS,NEWKG_JSFS,BUSI_OUTSOURCE_POSITION,NEW_BUSI_OUTSOURCE_INTERVRESULT,OUT_CHANGE_ADMIN"
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
        //面试结果
        this.scoreData = data.filter(item => {
          return item.dicttypeid == "NEW_BUSI_OUTSOURCE_INTERVRESULT";
        });
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
            this.isinterview = res.data.results.isinterview;
            // 外包商单价
            let temps = [];
            let counts = 0;
            console.log('详情', res.data.results)
            res.data.results.infos.forEach(item => {
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

    // 计数器改变弹窗提示
    inputNumchange(index, currentValue, priceCount) {
      if (currentValue == priceCount) {
        this.ryxxbgData.infos[index].reason = "";
      }
      // let currentPriceData = {};
      // for (var i = 0; i < this.ryxxbgData.infos[index].upData; i++) {
      //     if (this.ryxxbgData.infos[index].upData[i].positionName == this.ryxxbgData.infos[index].newPositionname) {
      //         let currentPriceData = this.ryxxbgData.infos[index].upData[i];
      //     }
      // }

      // if (this.ryxxbgData.infos[index].grade == "1") {
      //     if (currentValue > currentPriceData.firstendprie) {
      //         this.inputNumBox();
      //     }
      // } else if (this.ryxxbgData.infos[index].grade == "2") {
      //     if (currentValue > currentPriceData.secondendprie) {
      //         this.inputNumBox();
      //     }
      // } else if (this.ryxxbgData.infos[index].grade == "3") {
      //     if (currentValue > currentPriceData.thirdendprie) {
      //         this.inputNumBox();
      //     }
      // } else if (this.ryxxbgData.infos[index].grade == "4") {
      //     if (currentValue > currentPriceData.fourthendprie) {
      //         this.inputNumBox();
      //     }
      // }
    },

    // 计数器改变弹窗提示
    inputNumBox() {
      this.$confirm("该外包人员单价已超出对应档位，请请示分管总经理", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {})
        .catch(() => {});
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
      return twoManager(this.ryxxbgData).then(res => {
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