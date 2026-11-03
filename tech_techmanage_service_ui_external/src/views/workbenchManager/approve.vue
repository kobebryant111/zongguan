<template>
    <div class="approveCon">
        <div class="nav">
            <el-row :gutter="10">
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="8">
                                <p class="nav_name">外包人员准入审批</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="8">
                                <!-- <el-button size="small" class="cancel_s" @click="close">关闭</el-button> -->
                                <buttonCommon :buttonName="closeName" :size="sizes" :fns="close"></buttonCommon>
                            </el-col>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="mainCon">
            <div class="First First_other">
                <div class="contentFirst_title">
                    <p class="contentFirst_title_p1">基本信息</p>
                </div>
                <div class="contentFirst">
                    <!-- layout布局1 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>姓名</p>
                                    <el-input v-model="approveData.username" :disabled="true"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>工号</p>
                                    <el-input v-model="approveData.jobid" :disabled="true"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="sex">
                                    <p>性别</p>
                                    <!-- <el-radio-group v-model="genfer">
                                        <el-radio :label="0" disabled>男</el-radio>
                                        <el-radio :label="1" disabled>女</el-radio>
                                    </el-radio-group> -->
                                    <el-radio-group v-model="approveData.genfer">
                                        <el-radio v-for="item in sex_options" :label="item.label" :key="item.label" disabled>{{item.value}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>籍贯</p>
                                    <el-input v-model="approveData.nativeplace" :disabled="true"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num picker_sty_fir">
                                    <p>生日</p>
                                    <el-date-picker type="date" :disabled="true" v-model="approveData.birthday"></el-date-picker>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="sex">
                                    <p>婚姻状况</p>
                                    <el-radio-group v-model="approveData.marriage">
                                        <el-radio v-for="item in marriage_options" :label="item.label" :key="item.label" disabled>{{item.value}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>毕业院校</p>
                                    <el-input :disabled="true" v-model="approveData.school"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>专业</p>
                                    <el-input :disabled="true" v-model="approveData.specialty"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p>学历</p>
                                    <el-radio-group v-model="approveData.education">
                                        <el-radio v-for="item in edu_options" :label="item.label" :key="item.label" disabled>{{item.value}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>联系方式</p>
                                    <el-input :disabled="true" v-model="approveData.telephone"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <!-- 公司名称 -->
                                    <p>外包商</p>
                                    <el-select v-model="approveData.outsourceno" disabled>
                                        <el-option :value="approveData.outsourceno">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p>外包方式</p>
                                    <el-radio-group v-model="approveData.outsourcetype">
                                        <el-radio v-for="item in outsourcetype_options" :label="item.label" :key="item.label" disabled>{{item.value}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="edu">
                                    <p class="deptno_p">所属部门</p>
                                    <!-- <el-select v-model="deptno">
                                        <el-option v-for="(item,index) in deptno_options" :key="index" :labal="index"
                                            :value="item" v-infinite-scroll="loadMore"></el-option>
                                    </el-select> -->
                                    <el-select v-model="approveData.deptno" disabled>
                                        <el-option :value="approveData.deptno">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p>所在小组</p>
                                    <el-select v-model="approveData.groupid" disabled>
                                        <el-option :value="approveData.groupid">
                                        </el-option>
                                    </el-select>

                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p>结算方式</p>
                                    <el-radio-group v-model="approveData.usertype">
                                        <el-radio v-for="item in usertype_options" :label="item.label" :key="item.label" disabled>{{item.value}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>岗位</p>
                                    <el-select v-model="approveData.position" disabled>
                                        <el-option :value="approveData.position">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p>工作内容</p>
                                    <el-input :disabled="true" v-model="approveData.gznr"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu picker_sty">
                                    <p>入职日期</p>
                                    <el-date-picker type="date" :disabled="true" v-model="approveData.appltbegtime"></el-date-picker>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>项目名称</p>
                                    <el-select v-model="approveData.projectname" disabled>
                                        <el-option :value="approveData.projectname">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p>项目经理</p>
                                    <el-input :disabled="true" v-model="approveData.manageruserid"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p>行内负责人</p>
                                    <el-select v-model="approveData.organizers" disabled>
                                        <el-option :value="approveData.organizers">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                        </el-row>
                    </div>


                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>系统名称</p>
                                    <el-select v-model="approveData.systemnos" disabled>
                                        <el-option :value="approveData.systemnos">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p>系统负责人</p>
                                    <el-input :disabled="true" v-model="approveData.systemuserids"></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="edu">
                                    <p> 采购项目</p>
                                    <el-select v-model="test" disabled>
                                        <el-option :value="test">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <!-- 表第九行 -->
                    <div class="row_approve" >
                      <el-row :gutter="50">
                        <el-col :span="8">
                          <p>总办议案 *</p>
                           
                              <el-select 
                                v-model="approveData.ceneralOfficeMotion"
                                value-key="id"
                                disabled
                              >
                                <el-option
                                  v-for="(item) in ceneralOfficeMotion_options"
                                  :key="item.id"
                                  :label="item.label"
                                  :value="item.id"
                                ></el-option>
                              </el-select>
                        </el-col>
                        <el-col :span="8">
                          <p>合同</p>
                         
                            <el-select
                              v-model="approveData.newContractId"
                              filterable
                              placeholder="请选择"
                              value-key="id"
                              disabled
                            >
                              <el-option
                                v-for="(item) in newContractId_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.id"
                              ></el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="8">
                        </el-col>
                      </el-row>
                    </div>
                </div>

            </div>
            <!-- two页面 -->
            <div class="contentTwo">
                <!-- 面试信息 -->
                <div class="contentTwo_3">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">面试信息</p>
                    </div>
                    <div class="contentTwo_inte">
                        <!-- 表第四行 -->
                        <div class="row_1">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div class="sex">
                                        <p>面试总成绩</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div class="num">
                                        <p>面试结果</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <!-- 表第五行 -->
                        <div class="row_1">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div class="sex">
                                        <p>面试官1</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div class="num">
                                        <p>评语1</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                        <!-- 表第六行 -->
                        <div class="row_1">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div class="sex">
                                        <p>面试官2</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div class="num">
                                        <p>评语2</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 征信和风控查询信息 -->
            <div class="contentTwo">
                <div class="contentTwo_3">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">征信和风控查询信息</p>
                    </div>
                    <div class="contentTwo_inte">
                        <!-- 表第四行 -->
                        <div class="row_1">
                            <el-row :gutter="60">
                                <el-col :span="6">
                                    <div class="sex">
                                        <p>征信和风控结果</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="6">
                                    <div class="num">
                                        <p>公安检查</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="6">
                                    <div class="num">
                                        <p>个人征信</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="6">
                                    <div class="num">
                                        <p>是否重复入职</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                        <!-- 表第五行 -->
                        <div class="row_1">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div class="sex">
                                        <p>法院信息</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div class="num">
                                        <p>工商信息</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                    </div>
                </div>
            </div>

            <!-- 准入要求 -->
            <div class="contentTwo">
                <div class="contentTwo_3">
                    <div class="contentTwo_title">
                        <p class="contentTwo_title_p">准入要求</p>
                    </div>
                    <div class="contentTwo_inte">

                        <div class="row_1">
                            <el-row :gutter="60">
                                <el-col :span="8">
                                    <div class="sex">
                                        <p>是否符合准入要求</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                                <el-col :span="8">
                                    <div class="num">
                                        <p>原因</p>
                                        <el-input v-model="test" :disabled="true"></el-input>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>

                    </div>
                </div>
            </div>

            <!-- 简历信息 -->
            <div class="contentTwo">
                <div class="contentTwo_title">
                    <p class="contentTwo_title_p">简历信息</p>
                </div>
                <div class="content">

                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>证件类型 *</p>
                                    <el-radio-group v-model="certiftype">
                                        <el-radio v-for="item in certiftype_options" :label="item.label" :key="item.label" disabled>{{item.value}}</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>证件号码 *</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="sex">
                                    <p>政治面貌 *</p>
                                    <!-- <el-radio-group v-model="politiccontenance">
                                            <el-radio v-for="item in politiccontenance_options" :label="item.dictid" size="medium">{{item.dictname}}</el-radio>
                                        </el-radio-group> -->
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <!-- 表第二行 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>当前住址 *</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>配偶姓名 *</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="sex">
                                    <p>配偶证件号码 *</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <!-- 表第三行 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>所获证书</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_1 study-select">
                        <el-row>
                            <el-col :span="24">
                                <div>
                                    <p>学习经历(大专/本科起)</p>
                                    <el-table :data="studyData" style="width: 100%">
                                        <el-table-column prop="date" label="起止时间 *" width="270">
                                            <template>
                                                <el-date-picker type="date" :disabled="true"></el-date-picker>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="school" label="学校" width="270">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="profession" label="专业" width="260">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="edu" label="教育类别" width="220">
                                            <template>
                                                <el-select v-model="test" disabled>
                                                    <el-option :value="test">
                                                    </el-option>
                                                </el-select>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="study" label="学习形式" width="220">
                                            <template>
                                                <el-select v-model="test" disabled>
                                                    <el-option :value="test">
                                                    </el-option>
                                                </el-select>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="">
                                            <template>

                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <div class="row_1">
                        <el-row>
                            <el-col :span="24">
                                <div class="table">
                                    <p>主要工作经历(入职前)</p>
                                    <el-table :data="workData" style="width: 100%">
                                        <el-table-column prop="date" label="起止时间 *" width="270">
                                            <template>

                                                <!-- <el-date-picker v-model="scope.row.date" type="daterange" range-separator="-"
                                                        start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker> -->
                                                <el-date-picker type="date" :disabled="true"></el-date-picker>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="school" label="公司名称" width="270">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <!-- <el-table-column prop="profession" label="所在部门" width="270">
                                            <template slot-scope="scope">
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column> -->
                                        <el-table-column prop="edu" label="主要工作" width="700">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="">
                                            <template>

                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_1">
                        <el-row>
                            <el-col :span="24">
                                <div class="table">
                                    <p>主要项目经历(入职前)</p>
                                    <el-table :data="projData" style="width: 100%">
                                        <el-table-column prop="date" label="起止时间" width="270">
                                            <template>

                                                <!-- <el-date-picker v-model="scope.row.date" type="daterange" range-separator="-"
                                                        start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker> -->
                                                <el-date-picker type="date" :disabled="true"></el-date-picker>
                                            </template>
                                        </el-table-column>
                                        <!-- <el-table-column prop="school" label="公司名称" width="270">
                                            <template slot-scope="scope">
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column> -->
                                        <el-table-column prop="profession" label="项目名称" width="270">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="edu" label="主要工作" width="700">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="">
                                            <template>

                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <div class="row_1">
                        <el-row>
                            <el-col :span="24">
                                <div class="table">
                                    <p>主要奖励记录</p>
                                    <el-table :data="prizeData" style="width: 100%">
                                        <el-table-column prop="date" label="获奖时间" width="270">
                                            <template>
                                                <div class="picker_sty">
                                                    <!-- <el-date-picker v-model="scope.row.date" type="date" placeholder="选择日期"></el-date-picker> -->
                                                    <el-date-picker type="date" :disabled="true"></el-date-picker>
                                                </div>
                                                <!-- <el-date-picker v-model="value1" type="daterange"  range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker> -->
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="school" label="奖励名称" width="270">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="profession" label="奖励级别" width="260">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="edu" label="奖励原因" width="443">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="">
                                            <template>

                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="row_1">
                        <el-row>
                            <el-col :span="24">
                                <div class="table">
                                    <p>主要家庭成员</p>
                                    <el-table :data="familyData" style="width: 100%">
                                        <el-table-column prop="date" label="姓名" width="270">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="school" label="称谓" width="270">
                                            <template>
                                                <el-select v-model="test" disabled>
                                                    <el-option :value="test">
                                                    </el-option>
                                                </el-select>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="profession" label="出生年月日" width="260">
                                            <template>
                                                <div class="picker_sty">
                                                    <el-date-picker v-model="scope.row.birthday" type="date"
                                                        placeholder="选择日期" :disabled="true"></el-date-picker>
                                                </div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="edu" label="工作单位及职务" width="443">
                                            <template>
                                                <el-input v-model="test" :disabled="true"></el-input>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="">
                                            <template>

                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>

            <!-- 培训成绩 -->

            <div class="contentTwo">
                <div class="contentTwo_title">
                    <p class="contentTwo_title_p">礼仪考试成绩</p>
                </div>
                <div class="content">
                    
                    <!-- 表第一行 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="sex">
                                    <p>时间 *</p>
                                    <div class="picker_sty">
                                        <el-date-picker type="date" :disabled="true"></el-date-picker>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>成绩</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>

            <!-- 入职材料 -->
            <div class="contentTwo">
                <div class="contentTwo_title">
                    <p class="contentTwo_title_p">入职材料</p>
                </div>
                <div class="contentTwo_inte">
                    <!-- 表第四行 -->
                    <div class="row_1 upload">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <div class="sex">
                                    <p>征信报告
                                        <span class="preview">预览</span>
                                        <span class="download">下载</span>
                                    </p>
                                    <el-input v-model="test" disabled></el-input>

                                </div>
                                <div class="upload_1_2">
                                    <p>保密承诺书、服务提供/外包员工背景调查表/厂商承诺书
                                        <span class="preview">预览</span>
                                        <span class="download">下载</span>
                                    </p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="upload_2_d1">
                                    <p>社保缴纳证明/劳动合同/纳税证明
                                        <span class="preview">预览</span>
                                        <span class="download">下载</span>
                                    </p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                                <div class="upload_2_2">
                                    <p>其他(无社保缴纳证明、社保代缴、学籍在线报告等)
                                        <span class="preview">预览</span>
                                        <span class="download">下载</span>
                                    </p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>人脸照片</p>
                                    <!-- <el-upload class="upload-demo" action="/" :http-request="uploadFile" 
                                            :on-preview="handlePreviewCardPreview"  list-type="picture-card"
                                            :limit="3">
                                            <i class="el-icon-plus">
                                                <div class="upload_i">上传照片</div>
                                            </i>
                                        </el-upload>
                                        <el-dialog :visible.sync="dialogVisible">
                                            <img width="100%" :src="dialogImageUrl" alt="">
                                        </el-dialog> -->
                                    <div>
                                        <img src="../../assets/finish2.png" alt="" class="facePhoto">
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                </div>
            </div>

            <!-- 社保信息 -->

            <div class="contentTwo">
                <div class="contentTwo_title">
                    <p class="contentTwo_title_p">社保信息</p>
                </div>
                <div class="content">

                    <!-- 表第一行 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="sex">
                                    <p>社保缴纳</p>
                                    <div class="picker_sty">
                                        <!-- <el-date-picker v-model="comityTime" type="date" placeholder="请选择日期"></el-date-picker> -->
                                    </div>
                                </div>
                            </el-col>

                        </el-row>
                    </div>
                </div>
            </div>

            <!-- 配置信息 -->

            <div class="contentTwo">
                <div class="contentTwo_title">
                    <p class="contentTwo_title_p">配置信息</p>
                </div>
                <div class="content">

                    <!-- 表第一行 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="sex">
                                    <p>考勤类型 *</p>

                                    <el-select v-model="test" disabled>
                                        <el-option :value="test">
                                        </el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>考勤时间</p>
                                    <el-input v-model="test" :disabled="true"></el-input>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <!-- 表第二行 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>虚拟桌面 *</p>
                                    <el-radio v-model="radio" :label="1" disabled>是</el-radio>
                                    <el-radio v-model="radio" :label="2" disabled>否</el-radio>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>svn申请 *</p>

                                    <el-radio v-model="radio" :label="1" disabled>是</el-radio>
                                    <el-radio v-model="radio" :label="2" disabled>否</el-radio>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="sex">
                                    <p>特殊权限 *</p>
                                    <el-radio v-model="radio" :label="1" disabled>是</el-radio>
                                    <el-radio v-model="radio" :label="2" disabled>否</el-radio>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <!-- 表第三行 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="8">
                                <div class="name">
                                    <p>虚拟桌面配置</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="num">
                                    <p>SVN地址</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="sex">
                                    <p>权限描述</p>
                                    <el-input v-model="test" disabled></el-input>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>


            <!-- 操作日志 -->
            <div class="contentTwo">
                <div class="contentTwo_title">
                    <p class="contentTwo_title_p">操作日志</p>
                </div>
                <div class="content">

                    <!-- 表第一行 -->
                    <div class="row_1">
                        <el-row :gutter="60">
                            <el-col :span="24">
                                <div class="sex">
                                    <el-table :data="logTable" style="width: 100%">
                                        <el-table-column prop="createtime" label="操作时间" width="270">

                                        </el-table-column>
                                        <el-table-column prop="username" label="操作人" width="270">

                                        </el-table-column>
                                        <el-table-column prop="applydesc" label="流程节点" width="270">

                                        </el-table-column>
                                        <el-table-column prop="conclusion" label="决策" width="140">

                                        </el-table-column>
                                        <el-table-column prop="suggest" label="意见">

                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-col>

                        </el-row>
                    </div>
                </div>
            </div>
        </div>
        <!-- 底部栏样式一 -->
        <!-- <div class="nav-bottom">
            <el-row :gutter="10" type="flex" justify="end">
                <el-col :span="12">
                    <el-col :span="7">
                        <div class="nav-bottom-left">
                            <el-row :gutter="15">
                                <el-col :span="6.5">
                                    <div>结论 *</div>
                                </el-col>
                                <el-col :span="7">
                                    <el-radio v-model="conclusion" :label="1">通过</el-radio>
                                </el-col>
                                <el-col :span="7">
                                    <el-radio v-model="conclusion" :label="2">返回</el-radio>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="11">
                        <div class="nav-bottom-mid">
                            <el-row :gutter="10">
                                <el-col :span="6.5">
                                    <div>意见 *</div>
                                </el-col>
                                <el-col :span="19">
                                    <el-input v-model="suggest"></el-input>
                                </el-col>

                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="6">
                        <div class="nav-bottom-right">
                            <el-row>
                                <el-col :span="10">
                                  
                                    <buttonCommon :buttonName="empowerName" :size="sizes" :fns="getApplist"></buttonCommon>
                                </el-col>
                                <el-col :span="10">
                                    <buttonOrange :buttonName="liuzhuanName" :size="sizes" :fns="approve"></buttonOrange>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                </el-col>
            </el-row>
        </div> -->

        <!-- 底部栏样式2 -->
        <div class="nav-bottom">
            <el-row>
                <el-col :span="18">
                    <buttonCommon :buttonName="empowerName" :size="sizes" :fns="getApplist"></buttonCommon>
                </el-col>
                <el-col :span="6">
                        <el-row  type="flex" justify="end">
                            <el-col :span="5">
                                <buttonCommon :buttonName="retreatName" :size="sizes" :fns="openRetreatDialog"></buttonCommon>
                                <!-- <buttonCommon :buttonName="retreatName" :size="size" :fns="openRetreatDialog"></buttonCommon> -->
                            </el-col>
                            <el-col :span="5">
                                <buttonOrange :buttonName="liuzhuanName" :size="sizes" :fns="openLiuzhuanDialog"></buttonOrange>
                                <!-- <buttonOrange :buttonName="liuzhuanName" :size="size" :fns="openLiuzhuanDialog"></buttonOrange> -->
                            </el-col>
                        </el-row>
                </el-col>
            </el-row>
        </div>
        <!-- 授权弹窗 -->
        <el-dialog title="" :visible.sync="dialogEmpower" class="ryzrApprove_dialog">
            <div class="power_dia">授权</div>
            <div class="approv_dia">授权审批</div>
            <div class="approv_dia_con">
                <div class="approv_dia_con1">
                    <p class="approv_dia_con2_p">授权对象 *</p>
                    <el-cascader ref="cascAppro" :options="empowerList" :props="approveParams" v-model="approListData"
                        :show-all-levels="false" filterable @change="appLiChange"></el-cascader>
                </div>
                <div class="approv_dia_con2">
                    <p class="approv_dia_con2_p">授权意见 *</p>
                    <el-input v-model="approveSuggest"></el-input>
                </div>
            </div>
            <div class="dia_button">
                <buttonCommon :buttonName="cancelName" :size="sizes" :fns="approveCancel" class="butt_cancel"></buttonCommon>
                <buttonOrange :buttonName="sureName" :size="sizes" :fns="approveSure"></buttonOrange>
            </div>
        </el-dialog>

        <!-- 授权弹窗2 -->
        <el-dialog title="" :visible.sync="dialogEmpower" class="ryzrApprove_dialog">
                <div class="power_dia">授权</div>
                <div class="approv_dia">授权审批</div>
                <div class="approv_dia_con">
                        <el-form :model="ruleForm" ref="ruleForm" :rules="rules">
                    <div class="approv_dia_con1">
                        <p class="approv_dia_con2_p">授权对象 *</p>
                        <el-form-item prop="approListData">
                        <el-cascader ref="cascAppro" :options="empowerList" :props="approveParams" v-model="ruleForm.approListData"
                            :show-all-levels="false" filterable @change="appLiChange"></el-cascader>
                        </el-form-item>
                    </div>
                    <div class="approv_dia_con2">
                        <p class="approv_dia_con2_p">授权意见 *</p>
                        <el-form-item prop="approveSuggest">
                        <el-input v-model="ruleForm.approveSuggest"></el-input>
                    </el-form-item>
                    </div>
                </el-form>
                </div>
                <div class="dia_button">
                    <buttonCommon :buttonName="cancelName" :size="sizes" :fns="approveCancel" class="butt_cancel"></buttonCommon>
                    <buttonOrange :buttonName="sureName" :size="sizes" :fns="approveSure"></buttonOrange>
                </div>
            </el-dialog>

             <!-- 退回弹窗 -->
        <el-dialog title="" :visible.sync="dialogRetreat" class="ryzrApprove_dialog retreatDialog">
                <div class="retreatCon">
                    <div class="box_retreat_zb el-icon-warning"></div>
                    <div class="approv_dia_con retreatCon_2">
                        <div class="retreat_dia_titlte_zb">确认退回{{approveData.username}}的准入申请吗?</div>
                        <div class="retreat_dia_con_zb">
                            <p class="approv_dia_con2_p">请填写审批意见 *</p>
                            <el-form :model="retreatForm" ref="retreatForm" :rules="rulesRetreat">
                                <el-form-item prop="retreatSuggest">
                                        <el-input v-model="retreatForm.retreatSuggest"></el-input>
                                </el-form-item>
                        </el-form>
                        </div>
                    </div>
                </div>
                <div class="dia_button_type2_zb">
                    <buttonCommon :buttonName="cancelName" :size="sizes" :fns="retreatCancel" class="butt_cancel"></buttonCommon>
                    <buttonOrange :buttonName="sureName" :size="sizes" :fns="retreatSure"></buttonOrange>
                </div>
            </el-dialog>
    
            <!-- 流转弹窗 -->
            <el-dialog title="" :visible.sync="dialogLiuzhuan" class="ryzrApprove_dialog retreatDialog">
                    <div class="retreatCon">
                        <div class="box_retreat_zb el-icon-warning"></div>
                        <div class="approv_dia_con retreatCon_2">
                            <div class="retreat_dia_titlte_zb">确认同意{{approveData.username}}的准入申请吗</div>
                            <div class="retreat_dia_con_zb">
                                <p class="approv_dia_con2_p">请填写审批意见 *</p>
                                <el-form :model="liuzhuanForm" ref="liuzhuanForm" :rules="rulesLiuzhuan">
                                    <el-form-item prop="liuzhuanSuggest">
                                            <el-input v-model="liuzhuanForm.liuzhuanSuggest"></el-input>
                                    </el-form-item>
                            </el-form>
                            </div>
                        </div>
                    </div>
                    <div class="dia_button_type2_zb">
                        <buttonCommon :buttonName="cancelName" :size="sizes" :fns="liuzhuanCancel" class="butt_cancel"></buttonCommon>
                        <buttonOrange :buttonName="sureName" :size="sizes" :fns="liuzhuanSure"></buttonOrange>
                    </div>
                </el-dialog>
    </div>

</template>

<script>
    import { approve2, approveDetail,validMotion, validContract } from "@/api/integrated/wbryzrlc";
    import { getLogDetail, doAuth, getAuthList } from "@/api/common/flowApi";
    import common from "@/api/common";
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    export default {
        components: { buttonOrange, buttonCommon },
        data() {
            return {
                test: '',
                radio: '',
                username: '', //姓名
                name: '',
                num: '',
                jobid: '系统自动生成', //工号
                ident: '', //保存时系统生成的标识
                genfer: '', //性别
                genferData: [],
                sex_options: [{
                    value: '男',
                    label: '0'
                }, {
                    value: '女',
                    label: '1'
                }],
                telephone: '', //联系电话
                brithday: '', //生日
                edu: '', //学历
                edu_options: [{
                    value: '大专',
                    label: '1'
                }, {
                    value: '本科',
                    label: '2'
                }, {
                    value: '硕士',
                    label: '3'
                }, {
                    value: '博士',
                    label: '4'
                }, {
                    value: '其他',
                    label: '5'
                }],
                school: '', //毕业院校
                specialty: '', //专业信息
                nativeplace: '', //籍贯
                outsourceno: '', //公司名称
                deptno: '', //所属部门
                depId: '', //部门编号
                fild: '', //所属部门数据
                outsourcetype: '', //外包方式
                outsourcetype_options: [{
                    value: '人力外包',
                    label: '1'
                }, {
                    value: '项目外包',
                    label: '0'
                }],
                usertype: '', //结算方式
                usertype_options: [{
                    value: '人力结算',
                    label: '0'
                }, {
                    value: '项目结算',
                    label: '1'
                }],
                groupid: '', //小组
                projectno: '', //项目名称
                projectnoData: '',
                manageruserid: '', //项目经理
                organizers: '', //行内负责人
                organizersData: '',
                systemnos: '',  //系统名称
                systemnosData: '',
                systemuserids: '', //系统负责人
                buy: '', //采购项目（没有）
                position: '', //岗位
                position_options: [],
                gznr: '', //工作内容
                appltbegtime: '', //入职时间
                date: '',
                marriage: '',
                marriage_options: [{
                    value: '未婚',
                    label: '1'
                }, {
                    value: '已婚',
                    label: '2'
                }, {
                    value: '离异',
                    label: '3'
                }, {
                    value: '丧偶',
                    label: '4'
                }],
                certiftype: '', //证件类型
                certiftype_options: [{
                    value: '身份证',
                    label: '0'
                }, {
                    value: '其他',
                    label: '1'
                }],
                approveData: '', //审批页面信息
                studyData: [{ //主要学习经历
                    date: '',
                    schoolName: '',
                    professName: '', //专业
                    eduGenre: '', //教育类别
                    studyForm: '' //学习形式
                }],
                workData: [{ //主要工作经历
                    date: '',
                    comName: '',
                    depName: '',
                    mainWork: ''
                }],
                projData: [{ //主要项目经历
                    date: '',
                    comName: '',
                    projName: '',
                    mainWork: ''
                }],
                prizeData: [{ //主要奖励记录
                    date: '',
                    prizeName: '',
                    prizeGrade: '',
                    prizeCause: ''
                }],
                familyData: [{ //家庭成员
                    name: '', //家庭成员姓名
                    feminist: '', // 称谓
                    feminist_options: [
                        {
                            value: '父亲',
                            labal: '1'
                        }, {
                            value: '母亲',
                            labal: '2'
                        }],
                    birthday: '',
                    unit: '' //工作单位及职务
                }],
                processInstID: '', //流程ID
                workItemID: '', //工作项id
                processInstName: '',  //流程实例名称
                processDefName: '', // 流程定义名称
                personId: '', //指派的人员id
                personName: '', //指派的人员name
                logTable: [{ //流程日志信息
                    applydesc: '',
                    conclusion: '',
                    username: '',
                    createtime: '',
                    suggest: ''
                }],
                suggest: '',   //审核意见  
                conclusion: '', //审核结论
                dialogTableVisible: false, //授权弹窗
                dialogEmpower: false, //是否显示授权弹窗
                ruleForm: { //验证字段
                    approListData: '', //授权列表绑定值
                    approveSuggest: '', //授权意见
                },
                rules: {
                    approListData: [
                        { required: true, message: '请选择授权人', trigger: 'change' }
                    ],
                    approveSuggest: [
                        { required: true, message: '请填写意见', trigger: 'blur' }
                    ],

                },
                empowerList: [], //授权列表数据
                approListData: '', //授权列表绑定值
                authMessage: '', //授权提示信息
                approveParams: {  //授权列表配置信息
                    label: 'text',
                    value: 'id',
                    children: 'children'
                },
                sureName: '确定',
                sizes: 'mini',
                cancelName: '取消',
                empowerName: '授权', //授权按钮
                liuzhuanName: '流转', //流转按钮
                closeName: '关闭', // 关闭按钮
                retreatName: '退回', //退回按钮
                approveSuggest: '', //授权意见
                successMessage: '', //成功提示
                errorMessage: '', //失败提示
                dialogRetreat: false, //退回弹窗
                retreatForm: {
                    retreatSuggest: '不同意', //退回意见
                },
                rulesRetreat: {
                    retreatSuggest: [
                        { required: true, message: '请填写审批意见', trigger: 'blur' }
                    ],
                },
                dialogLiuzhuan: false, //流转弹窗
                liuzhuanForm: {
                    liuzhuanSuggest: '同意', //流转意见
                },
                rulesLiuzhuan: {
                    liuzhuanSuggest: [
                        { required: true, message: '请填写审批意见', trigger: 'blur' }
                    ],
                },
                ceneralOfficeMotion_options: [], // 总办议案
                newContractId_options: [], // 合同
            }
        },
        watch: {
            'conclusion': function () {
                console.log(this.conclusion);
            }

        },
        created() { 
            this.depId = this.$store.getters.deptId;  
            this.processInstID = this.$route.params.processInstID;
            this.workItemID = this.$route.params.workItemID;
            this.processInstName = this.$route.params.processInstName;
            this.processDefName = this.$route.params.processDefName;
            this.init();
        },
        mounted() {

        },
        methods: {
            // 初始化
            init() {
                this.getLog();
                this.getApproveDetail();
                this.getvalidMotion()
                this.getvalidContract()
            },
            // 获取审批页面信息
            getApproveDetail() {
                let params = {}; 
                params.instanceid = this.processInstID;

                approveDetail(params)
                    .then(res => { 
                        this.approveData = res.data.results;

                    })
                    .catch(error => {
                        console.error("error", error);
                    });  // end
            },

            //获取流程日志
            getLog() {
                let params = {}; 
                params.flowcaseno = this.processInstID; 
                let data = common.paramsFormat(params); 
                getLogDetail(data)
                    .then(res => { 
                        var logData = res.data.results.reverse();
                        for (var i = 0; i < logData.length; i++) {
                            if (logData[i].conclusion == "01") {
                                logData[i].conclusion = "通过"
                            } else if (logData[i].conclusion == "02") {
                                logData[i].conclusion = "退回"
                            } else if (logData[i].conclusion == "03") {
                                logData[i].conclusion = "授权"
                            } else if (logData[i].conclusion == "04") {
                                logData[i].conclusion = "撤回"
                            }

                        }
                        for (var i = 0; i < logData.length - 1; i++) {
                            var createTime = logData[i].createtime; 
                            logData[i].createtime = common.dateFormat(createTime, 'yyyy-MM-dd HH:mm:ss');
                        }
                        this.logTable = logData;
                    })
                    .catch(error => {
                        console.error("error", error);
                    });  // end
            },

            //获取授权列表
            getApplist() {
                this.approveSuggest = "";
                let params = {};
                params.deptNo = "130"; //130是总行科技部部门编号 
                getAuthList(params)
                    .then(res => { 
                        this.empowerList = res.data.results; 
                    })
                    .catch(error => {
                        console.error("error", error);
                    });  // end

                this.dialogEmpower = true;
            },
            
            // 授权取消
            approveCancel() {
                this.dialogEmpower = false;
            },
            
            // 授权确认
            approveSure() {
                // this.empower();
                // this.dialogEmpower = false;
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) { 
                        this.empower();
                    } else { 
                        return false;
                    }
                })
            },

            // 授权列表选中节点发生变化时触发
            appLiChange() { 
                let nodesObj = this.$refs['cascAppro'].getCheckedNodes(); 
                this.personId = nodesObj[0].data.pcode;
                this.personName = nodesObj[0].data.text; 
            },
            // 授权
            empower() {
                let params = {};
                params.personId = this.personId;
                params.personName = this.personName;
                params.workItemId = this.workItemID;
                params.processInstID = this.processInstID;
                params.processInstName = this.processInstName;
                params.processDefName = this.processDefName;
                params.suggest = this.approveSuggest; 
                let data = common.paramsFormat(params);
                doAuth(data)
                    .then(res => { 
                        // this.authMessage = res.data.message;
                        if (res.data.status == 200) {
                            this.successMessage = '流程授权成功'
                            this.messageSuccess();
                            this.$router.back();
                        } else {
                            this.errorMessage = '流程授权失败'
                            this.messageError();
                        }
                        // this.dialogEmpower = false;
                        // this.cueMessage();
                    })
                    .catch(error => {
                        console.error("error", error);
                    });  // end
            },

            //二级部经理审批
            approve() {
                
                var formData = new FormData();
                formData.append("processInstID", this.processInstID);
                formData.append("workItemID", this.workItemID);
                formData.append("conclusion", this.conclusion);
                formData.append("suggest", this.suggest);
                // formData.append("childmanageruserid", "沈栋(030029)"); //指定分管总经理
                formData.append("processInstName", this.processInstName);
                formData.append("processDefName", this.processDefName);
                let data = formData;
                approve2(data)
                    .then(res => {  
                        if (res.data.status == 200) {
                        this.successMessage = '操作成功'
                        this.messageSuccess();
                        this.$router.back();
                    } else {
                        this.errorMessage = '操作失败'
                        this.messageError();
                    }

                    })
                    .catch(error => {
                        console.error("error", error);
                    });  // end
            },

             // 打开退回
             openRetreatDialog() {
                this.dialogRetreat = true;
            },

            // 退回取消
            retreatCancel() {
                this.dialogRetreat = false;
            },

            // 退回确认
            retreatSure() {
                // this.dialogRetreat = false;
                this.$refs['retreatForm'].validate((valid) => {
                    if (valid) { 
                        this.conclusion = "02";
                        this.suggest = this.retreatForm.retreatSuggest;
                        this.approve();
                    } else { 
                        return false;
                    }
                })
            },

              // 打开流转
              openLiuzhuanDialog() {
                this.dialogLiuzhuan = true;
            },

             // 流转取消
             liuzhuanCancel() {
                this.dialogLiuzhuan = false;
            },

             // 流转确认
             liuzhuanSure() {
                this.dialogRetreat = false;
                this.$refs['liuzhuanForm'].validate((valid) => {
                    if (valid) { 
                        this.conclusion = "01";
                        this.suggest = this.liuzhuanForm.liuzhuanSuggest;
                        this.approve();
                    } else { 
                        return false;
                    }
                })
            },

            //关闭
            close() {
                this.$router.back();
            },

            // 提示信息
            cueMessage() { 
                if (this.depId == "") { 
                    this.$message(this.authMessage);
                }
            },

            // 成功消息提示
            messageSuccess() {
                this.$message({
                    message: this.successMessage,
                    type: 'success'
                })
            },

            // 失败消息提示
            messageError() {
                this.$message.error(this.errorMessage);
            },

                    // 总办议案
    getvalidMotion() {
      validMotion({fileType:0}).then(res => {
        console.log(res, '总办议案')
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
        console.log(res);
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
    }
</script>
<style scoped>
    .approveCon {
        color: rgba(38, 38, 38, 1);
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 200px;
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
        padding: 0 15px;
        display: flex;
        justify-content: space-between;
    }

    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .mainCon {
        padding: 0 15px;
    }

    /* first页面样式 */
    .contentFirst_title {
        height: 60px;
        line-height: 60px;
        font-size: 16px;
        background-color: #ffffff;
        /* margin: 0 15px; */
        padding: 0 15px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .contentFirst {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        padding: 0 15px 15px 15px;
        /* margin: 0 15px; */
    }

    /* second页面样式 */
    .contentTwo {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        margin-top: 20px;
    }

    .contentTwo_title {
        height: 60px;
        line-height: 60px;
        background-color: #ffffff;
        padding: 0 15px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .contentTwo_inte {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        padding: 0 15px 15px 15px;
    }

    .row_1 p {
        font-size: 14px;
    }

    /* 简历信息页面样式 */
    .content {
        padding: 0 15px 15px 15px;
    }

    /* 底部栏 */
    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        margin-top: 20px;
        font-size: 14px;
        padding: 0 15px;
        height: 60px;
        line-height: 60px;
    }

    .nav-bottom-left {
        font-size: 14px;
    }

    /* .bottom_right {
        padding-left: 200px;
    } */

    /* 授权弹窗 */
    .power_dia {
        font-size: 16px;
        color: #262626;
    }

    .approv_dia {
        font-size: 14px;
        margin-top: 8px;
    }

    .approv_dia_con1,
    .approv_dia_con2 {
        margin-top: 20px;
        color: #262626;
    }

    .ryzrApprove_dialog {
        overflow: hidden;
    }

    .dia_button {
        float: right;
        margin: 85px 0 15px 0;
    }

    .butt_cancel {
        margin-right: 10px;
    }

    /* 人脸照片 */
    .facePhoto {
        width: 90px;
        height: 90px;
    }

    /* 入职材料 */
    .preview,
    .download {
        color: #FBA42F;
        font-size: 14px;
        margin-left: 5px;
        cursor: pointer;
    }

    /* 底部栏 */
    .nav-bottom {
        padding-left: 15px;
    }

     /* 退回弹窗 */
     .retreatCon {
        display: flex;
        justify-content: space-around;
        /* flex-direction: row; */
    }

    .retreatCon_2 {
        width: 87%;
    }

    .dia_button_type2_zb {
        float: right;
        margin: 25px 0 15px 0;
    }

    .retreat_dia_titlte_zb {
        font-size: 16px;
        color: #262626;
        margin-top: 4px;
    }

    .retreat_dia_con_zb {
        margin-top: 20px;
    }

    /* 退回弹窗element警告图标 */
    .box_retreat_zb {
        color: rgba(251, 164, 47, 1);
        font-size: 180%;
    }
</style>

<style>
    .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }

    .el-date-editor--daterange.el-input,
    .el-date-editor--daterange.el-input__inner,
    .el-date-editor--timerange.el-input,
    .el-date-editor--timerange.el-input__inner {
        width: 250px;
    }

    /* 日期选择器 */


    .el-date-table td.end-date span,
    .el-date-table td.start-date span {
        background-color: rgba(251, 164, 47, 1);
    }

    .el-range-editor.is-active,
    .el-range-editor.is-active:hover {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-picker-panel__icon-btn:hover {
        color: rgba(251, 164, 47, 1);
    }

    .el-date-table td.available:hover {
        color: rgba(251, 164, 47, 1);
    }

    .el-date-editor .el-range-separator {
        padding: 0;
        width: 4%;
    }

    /* 学习经历el-select */
    .study-select .el-select {
        display: block;
    }

    /* 底部栏按钮 */
    /* .nav-bottom-right .el-button--warning {
        background-color: rgba(251, 164, 47, 1);
    } */



    /* 日期组件 */
    .row_1 .el-date-editor.el-input {
        width: auto;
        display: block;
    }

    /* 下拉框 */
    .row_1 .el-select {
        display: block;
    }

    /* 级联选择器 */
    .el-cascader {
        display: block;
    }

    /* 单选框 */
    /* .approveCon .el-radio-group {
        display: flex;
        align-items: center;
    } */

    /* 弹窗 */
    .approveCon .el-dialog {
        overflow: hidden;
    }

    .approveCon .el-dialog__body {
        padding: 0 30px;
    }


    /* 级联选择器 */
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

    /* button按钮 */
    /* .approveCon .el-button:focus {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .approveCon .el-button:hover {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    } */

    /* 弹窗输入框 */
    .approveCon .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }

    /* 弹窗关闭按钮 */
    .approveCon .el-dialog__headerbtn:focus .el-dialog__close,
    .el-dialog__headerbtn:hover .el-dialog__close {
        color: rgba(251, 164, 47, 1);
    }

     /* 退回弹窗 */
     .retreatDialog .el-dialog {
        width: 30%;
    }
</style>