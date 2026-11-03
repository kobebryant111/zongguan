<!-- 员工考核详情 -->
<template>
    <div class="mykh-container">
        <div class="ygkhdetail-head">
            <div class="kh-back-bread">
                <div class="kh-back-bread-butt">
                    <el-button size="small" icon="el-icon-arrow-left" @click="lastPage">返回</el-button>
                </div>
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item v-for="(item,index) in routesMeta" :key="index">
                        <span :class="[routesMeta.length-1 == index ? 'lastJc' : 'crumbOther']">{{item}}</span>
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="mykh-nav">
                <!-- <el-row>
                    <el-col :span="6"> -->
                <el-select placeholder="年份" v-model="currentYear" @change="cliTag">
                    <el-option v-for="(item, index) in yearArr" :key="index" :label="item.tyear" :value="item.tyear"></el-option>
                    <template slot="prefix">年份：</template>
                </el-select>
                <!-- </el-col>
                </el-row> -->
            </div>
        </div>
        <div class="mykh-head">
            <el-row type="flex" justify="space-between">
                <el-col :span="7">
                    <div class="mykh-head-left">
                        <el-row type="flex" justify="space-between" align="middle">
                            <el-col :span="6">
                                <div class="mykh-head-left-left">
                                    <!-- <el-image v-if="this.avatar == null || this.avatar == ''" class="mykh-head-left-left-image"
                                        src="../../.././src/assets/avatar.png" fit="contain"></el-image> -->
                                    <img v-if="this.avatar == null || this.avatar == ''" class="mykh-head-left-left-image"
                                        src="@/assets/avatar.png">
                                    <img v-else class="mykh-head-left-left-image" :src="avatar">
                                    <!-- <el-image class="mykh-head-left-left-image" src="../../.././src/assets/avatar.png"
                                        fit="contain"></el-image> -->
                                </div>
                            </el-col>
                            <el-col :span="18">
                                <div class="mykh-head-left-middle">
                                    <div class="mykh-head-left-middle-top">
                                        <div class="mykh-head-left-middle-top-name"><span>{{myKhData.empname}}</span><span
                                                class="mykh-head-left-middle-top-jobid">{{myKhData.badge}}</span></div>
                                        <div class="mykh-head-left-middle-top-right">
                                            <!-- <el-link type="primary" @click="corrSingle">设置</el-link> -->
                                        </div>
                                    </div>
                                    <div class="mykh-head-left-middle-middle">
                                        <span>{{myKhData.centername}}</span>
                                        <span class="xkg-marginleft-10px">{{myKhData.depname}}</span>
                                    </div>
                                    <div class="mykh-head-left-middle-bottom">
                                        <span class="mykh-head-left-middle-bottom-describe">总分</span>
                                        <span class="mykh-head-left-middle-bottom-score">{{my_kpis_year_eval.tscore !=
                                            undefined ? my_kpis_year_eval.tscore : '-'}}</span>
                                        <!-- <span class="mykh-head-left-middle-bottom-score">{{tatalTscore}}</span> -->
                                        <!-- <span class="mykh-head-left-middle-bottom-score">-</span> -->
                                        <el-divider direction="vertical"></el-divider>
                                        <span class="mykh-head-left-middle-bottom-describe">评级</span>
                                        <span class="mykh-head-left-middle-bottom-score">{{my_kpis_year_eval.tgrade !=
                                            undefined ? my_kpis_year_eval.tgrade : '-'}}</span>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-col>
                <el-col :span="7">
                    <div class="mykh-head-middle">
                        <div class="mykh-head-middle-head">
                            <div class="mykh-head-middle-head-left">上半年评语：</div>
                            <div>
                                <!-- <el-link type="primary" @click="editFirstComment" v-if="(emp_kpis_first_half.id != undefined || manager_kpis.id != undefined) && myKhData.secondcheck == userId && pyFirstData.itemstatus > 5">编辑</el-link> -->
                                <el-link type="primary" @click="editFirstComment" v-if="(emp_kpis_first_half.id != undefined || manager_kpis.id != undefined) && pyFirstData.itemstatus > 5">{{myKhData.secondcheck
                                    == userId ? '编辑' : '查看'}}</el-link>
                            </div>
                        </div>
                        <div class="mykh-head-middle-text" v-if="emp_kpis_first_half.id != undefined || manager_kpis.id != undefined">
                            <el-scrollbar style="height:100%;width:100%;">
                                <div class="tdWarp">{{pyFirstData.secondevalcomment}}</div>
                            </el-scrollbar>
                        </div>
                        <div v-else class="mykh-head-middle-text-wfq">
                            未发起
                        </div>
                    </div>
                </el-col>
                <el-col :span="7">
                    <div class="mykh-head-right mykh-head-middle">
                        <div class="mykh-head-middle-head">
                            <div class="mykh-head-middle-head-left">下半年评语：</div>
                            <div>
                                <!-- <el-link type="primary" @click="editLaterComment" v-if="(emp_kpis_last_half.id != undefined || manager_kpis.id != undefined) && myKhData.secondcheck == userId && pyLaterData.itemstatus > 5">编辑</el-link> -->
                                <el-link type="primary" @click="editLaterComment" v-if="(emp_kpis_last_half.id != undefined || manager_kpis.id != undefined) && pyLaterData.itemstatus > 5">{{myKhData.secondcheck
                                    == userId ? '编辑' : '查看'}}</el-link>
                            </div>
                        </div>
                        <div class="mykh-head-middle-text" v-if="emp_kpis_last_half.id != undefined || manager_kpis.id != undefined">
                            <el-scrollbar style="height:100%;width:100%;">
                                <div class="tdWarp">{{pyLaterData.secondevalcomment}}</div>
                            </el-scrollbar>
                        </div>
                        <div v-else class="mykh-head-middle-text-wfq">
                            未发起
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div v-if="myKhData.role == 'COMM_USER'" class="mykh-body rowHeight-100">
            <el-row :gutter="10">
                <el-col :span="8">
                    <div class="mykh-body-left">
                        <div class="mykh-body-left-top mykh-body-card">
                            <div class="mykh-body-left-top-head">
                                <div>
                                    <div class="word-line-con">
                                        <span>上半年KPI考核</span>
                                        <div class="word-line-color-pink"></div>
                                    </div>
                                    <!-- <span class="card-title border-bottom-color-pink">上半年KPI考核</span> -->
                                    <el-link v-if="emp_kpis_first_half.id != undefined" type="primary" class="card-detail"
                                        @click="toFirstKpizp">更多详情</el-link>
                                </div>
                                <div class="card-score">{{emp_kpis_first_half.kpiscore != undefined ?
                                    emp_kpis_first_half.kpiscore : 0}}分</div>
                            </div>
                            <div class="mykhcomm-body-left-top-con">
                                <el-scrollbar style="height:100%;width:100%;">
                                    <div>
                                        <el-table :data="firstHalfYearKpi">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="指标项" prop="itemdescribe" min-width="30%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="工作内容" prop="plandescribe" min-width="40%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="评分" prop="tscore" min-width="20%">
                                                <template slot-scope="scope">
                                                    <template v-if="scope.row.tscore != undefined">{{scope.row.tscore}}</template>
                                                    <template v-else>{{scope.row.itemstatusname}}</template>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <p class="mykhcomm-body-left-top-con-title">知识技能提升</p>
                                        <el-table :data="firstHalfYearNlts">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="待提升的知识技能" prop="itemdescribe" min-width="40%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="提升目标" prop="plandescribe" min-width="50%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                        </el-table>
                                    </div>
                                    <!-- <div v-else>
                                        <el-table empty-text="未发起">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="指标项" min-width="30%"></el-table-column>
                                            <el-table-column label="工作内容" min-width="40%"></el-table-column>
                                            <el-table-column label="评分" min-width="20%"></el-table-column>
                                        </el-table>
                                    </div> -->
                                </el-scrollbar>
                            </div>
                            <!-- <div ref="mykhcommLeftTop" class="mykhcomm-body-left-top-con">
                                    <el-table :data="firstHalfYearKpi" :max-height="commLeftTopTableHeight">
                                        <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                        <el-table-column label="指标项" prop="itemdescribe" min-width="30%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column label="工作内容" prop="plandescribe" min-width="60%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                    </el-table>
                                </div> -->
                        </div>
                        <div class="mykh-body-left-top mykh-body-card">
                            <div class="mykh-body-left-top-head">
                                <div>
                                    <div class="word-line-con">
                                        <span>下半年KPI考核</span>
                                        <div class="word-line-color-pink"></div>
                                    </div>
                                    <!-- <span class="card-title border-bottom-color-pink">下半年KPI考核</span> -->
                                    <el-link v-if="emp_kpis_last_half.id != undefined" type="primary" class="card-detail"
                                        @click="toLaterKpizp">更多详情</el-link>
                                </div>
                                <div class="card-score">{{emp_kpis_last_half.kpiscore != undefined ?
                                    emp_kpis_last_half.kpiscore : 0}}分</div>
                            </div>
                            <div class="mykhcomm-body-left-top-con">
                                <el-scrollbar style="height:100%;width:100%;">
                                    <div>
                                        <el-table :data="latterHalfYearKpi">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="指标项" prop="itemdescribe" min-width="30%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="工作内容" prop="plandescribe" min-width="40%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="评分" prop="tscore" min-width="20%">
                                                <template slot-scope="scope">
                                                    <template v-if="scope.row.tscore != undefined">{{scope.row.tscore}}</template>
                                                    <template v-else>{{scope.row.itemstatusname}}</template>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                        <p class="mykhcomm-body-left-top-con-title">知识技能提升</p>
                                        <el-table :data="latterHalfYearNlts">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="待提升的知识技能" prop="itemdescribe" min-width="40%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="提升目标" prop="plandescribe" min-width="50%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                        </el-table>
                                    </div>
                                    <!-- <div v-else>
                                        <el-table empty-text="未发起">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="指标项" min-width="30%"></el-table-column>
                                            <el-table-column label="工作内容" min-width="40%"></el-table-column>
                                            <el-table-column label="评分" min-width="20%"></el-table-column>
                                        </el-table>
                                    </div> -->
                                </el-scrollbar>
                            </div>
                            <!-- <div class="mykhcomm-body-left-top-con">
                                    <el-table :data="latterHalfYearKpi" :max-height="commLeftTopTableHeight">
                                        <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                        <el-table-column label="指标项" prop="itemdescribe" min-width="30%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column label="工作内容" prop="plandescribe" min-width="60%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                    </el-table>
                                </div> -->
                        </div>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="mykh-body-left">
                        <div class="mykh-body-left-top mykh-body-card">
                            <div class="mykh-body-left-top-head">
                                <div>
                                    <div class="word-line-con">
                                        <span>上半年综合能力评估</span>
                                        <div class="word-line-color-blue"></div>
                                    </div>
                                    <!-- <span class="card-title border-bottom-color-blue">上半年综合能力评估</span> -->
                                    <el-link v-if="emp_kpis_first_half.id != undefined" type="primary" class="card-detail"
                                        @click="toFirstZhnlpg">更多详情</el-link>
                                </div>
                                <div class="card-score">{{emp_kpis_first_half.comprehensivescore != undefined ?
                                    emp_kpis_first_half.comprehensivescore : 0}}分</div>
                            </div>
                            <div class="mykhcomm-body-left-top-con zhnlAss-block">
                                <el-scrollbar style="height:100%;width:100%;">
                                    <el-table :data="firstHalfYearZhnlAssess">
                                        <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                        <el-table-column label="评估项" prop="itemdescribe" min-width="25%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column label="子评估项" prop="plandescribe" min-width="30%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column label="评分" min-width="35%">
                                            <template slot-scope="scope">
                                                <template v-if="scope.row.tscore != undefined && scope.row.tscore != 0">
                                                    <el-rate v-model="scope.row.tscore" disabled></el-rate>
                                                </template>
                                                <template v-else>{{scope.row.itemstatusname}}</template>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </el-scrollbar>
                            </div>
                        </div>
                        <div class="mykh-body-left-top mykh-body-card">
                            <div class="mykh-body-left-top-head">
                                <div>
                                    <div class="word-line-con">
                                        <span>下半年综合能力评估</span>
                                        <div class="word-line-color-blue"></div>
                                    </div>
                                    <!-- <span class="card-title border-bottom-color-blue">下半年综合能力评估</span> -->
                                    <el-link v-if="emp_kpis_last_half.id != undefined" type="primary" class="card-detail"
                                        @click="toLaterZhnlpg">更多详情</el-link>
                                </div>
                                <div class="card-score">{{emp_kpis_last_half.comprehensivescore != undefined ?
                                    emp_kpis_last_half.comprehensivescore : 0}}分</div>
                            </div>
                            <div class="mykhcomm-body-left-top-con zhnlAss-block">
                                <el-scrollbar style="height:100%;width:100%;">
                                    <el-table :data="latterHalfYearZhnlAssess">
                                        <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                        <el-table-column label="评估项" prop="itemdescribe" min-width="25%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column label="子评估项" prop="plandescribe" min-width="30%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column label="评分" min-width="35%">
                                            <template slot-scope="scope">
                                                <template v-if="scope.row.tscore != undefined && scope.row.tscore != 0">
                                                    <el-rate v-model="scope.row.tscore" disabled></el-rate>
                                                </template>
                                                <template v-else>{{scope.row.itemstatusname}}</template>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </el-scrollbar>
                            </div>
                        </div>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="mykh-body-right">
                        <div class="mykh-body-right-top mykh-body-card">
                            <div class="mykh-body-left-top-head">
                                <div>
                                    <div class="word-line-con">
                                        <span>平衡计分</span>
                                        <div class="word-line-color-yellow"></div>
                                    </div>
                                    <!-- <span class="card-title border-bottom-color-yellow">平衡计分</span> -->
                                    <el-link type="primary" class="card-detail" @click="toPhjf">更多详情</el-link>
                                </div>
                                <div class="card-score">{{scoreTj.yearPhjfScore}}分</div>
                            </div>
                            <div class="mykhcomm-body-left-top-con">
                                <el-scrollbar style="height:100%;width:100%;">
                                    <el-table :data="yearPhjf">
                                        <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                        <el-table-column label="考核项" prop="itemdescribe" min-width="30%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column label="考核内容" prop="plandescribe" min-width="40%"
                                            :show-overflow-tooltip="true"></el-table-column>
                                        <el-table-column label="评分" prop="tscore" min-width="20%">
                                            <template slot-scope="scope">
                                                <template v-if="scope.row.tscore != undefined && scope.row.tscore != 0">
                                                    <span class="phjfColor-red">{{scope.row.tscore}}</span>
                                                </template>
                                                <template v-else>合格</template>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </el-scrollbar>
                            </div>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div v-else class="mykh-body rowHeight-100">
            <div v-if="myKhData.role == 'DEP_MANAGERS'" class="mykh-body-fgjl" style="margin-bottom: 5px">
                <el-row :gutter="10">
                    <el-col :span="16">
                        <div class="mykh-body-right">
                            <div class="mykh-body-right-top mykh-body-card">
                                <div class="mykh-body-left-top-head">
                                    <div>
                                        <div class="word-line-con">
                                            <span>KPI考核</span>
                                            <div class="word-line-color-pink"></div>
                                        </div>
                                        <!-- <span class="card-title border-bottom-color-pink">KPI考核</span> -->
                                        <el-link v-if="manager_kpis.id != undefined" type="primary" class="card-detail"
                                            @click="toKpipj">更多详情</el-link>
                                    </div>
                                    <div class="card-score">{{manager_kpis.kpiscore != undefined ?
                                        manager_kpis.kpiscore : 0}}分</div>
                                </div>
                                <div class="mykhcomm-body-left-top-con">
                                    <el-scrollbar style="height:100%;width:100%;">
                                        <div>
                                            <el-table :data="yearKpiKh">
                                                <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                                <el-table-column label="指标项" prop="itemdescribe" min-width="20%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <el-table-column label="工作内容" prop="plandescribe" min-width="28%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <el-table-column label="计划完成时间" prop="finishdate" min-width="12%"></el-table-column>
                                                <el-table-column label="分值" prop="value" min-width="10%"></el-table-column>
                                                <el-table-column label="状态" prop="itemstatusname" min-width="10%"></el-table-column>
                                                <el-table-column label="评分" prop="tscore" min-width="10%">
                                                    <!-- <template slot-scope="scope">
                                                        <template v-if="scope.row.tscore != undefined">{{scope.row.tscore}}</template>
                                                        <template v-else>{{scope.row.itemstatusname}}</template>
                                                    </template> -->
                                                </el-table-column>
                                            </el-table>
                                            <!-- <p class="mykhcomm-body-left-top-con-title">知识技能提升</p>
                                            <el-table :data="yearKpiNlts">
                                                <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                                <el-table-column label="待提升的知识技能" prop="itemdescribe" min-width="20%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <el-table-column label="提升目标" prop="plandescribe" min-width="20%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <el-table-column label="发展/学习活动计划" prop="workremark" min-width="40%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <el-table-column label="计划完成时间" prop="finishdate" min-width="10%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <el-table-column label="状态" prop="itemstatusname" min-width="10%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                            </el-table> -->
                                        </div>
                                    </el-scrollbar>
                                </div>
                            </div>
                        </div>
                    </el-col>
                    <el-col :span="8">
                        <div class="mykh-body-right">
                            <div class="mykh-body-right-top mykh-body-card">
                                <div class="mykh-body-left-top-head">
                                    <div>
                                        <div class="word-line-con">
                                            <span>知识技能提升</span>
                                            <div class="word-line-color-pink"></div>
                                        </div>
                                        <!-- <span class="card-title border-bottom-color-pink">KPI考核</span> -->
                                        <el-link v-if="manager_kpis.id != undefined" type="primary" class="card-detail"
                                            @click="toKpipj">更多详情</el-link>
                                    </div>
                                    <!-- <div class="card-score">{{manager_kpis.kpiscore != undefined ?
                                        manager_kpis.kpiscore : 0}}分</div> -->
                                </div>
                                <div class="mykhcomm-body-left-top-con">
                                    <el-scrollbar style="height:100%;width:100%;">
                                        <div>
                                            <el-table :data="yearKpiNlts">
                                                <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                                <el-table-column label="待提升的知识技能" prop="itemdescribe" min-width="40%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <el-table-column label="提升目标" prop="plandescribe" min-width="35%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <!-- <el-table-column label="发展/学习活动计划" prop="workremark" min-width="40%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                                <el-table-column label="计划完成时间" prop="finishdate" min-width="10%"
                                                    :show-overflow-tooltip="true"></el-table-column> -->
                                                <el-table-column label="状态" prop="itemstatusname" min-width="15%"
                                                    :show-overflow-tooltip="true"></el-table-column>
                                            </el-table>
                                        </div>
                                    </el-scrollbar>
                                </div>
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </div>
            <div :class="[myKhData.role == 'DEP_MANAGERS' ? 'mykh-body-fgjl' : 'mykh-body-zcjl']">
                <el-row :gutter="10">
                    <el-col :span="10">
                        <div class="mykh-body-right">
                            <div class="mykh-body-right-top mykh-body-card">
                                <div class="mykh-body-left-top-head">
                                    <div>
                                        <div class="word-line-con">
                                            <span>履职评估</span>
                                            <div class="word-line-color-blue"></div>
                                        </div>
                                        <!-- <span class="card-title border-bottom-color-blue">履职评估</span> -->
                                        <el-link type="primary" class="card-detail" @click="toLzpg">更多详情</el-link>
                                    </div>
                                    <div class="card-score">{{manager_kpis.performanceevaluationscore != undefined ?
                                        manager_kpis.performanceevaluationscore : 0}}分</div>
                                </div>
                                <div class="mykhcomm-body-left-top-con">
                                    <el-scrollbar style="height:100%;width:100%;">
                                        <el-table :data="yeaLzpg">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="评估项" prop="itemdescribe" min-width="30%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="行为标准" prop="plandescribe" min-width="40%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="评分" prop="tscore" min-width="20%"></el-table-column>
                                        </el-table>
                                    </el-scrollbar>
                                </div>
                            </div>
                        </div>
                    </el-col>
                    <el-col :span="6">
                        <div class="mykh-body-right">
                            <div class="mykh-body-right-top mykh-body-card">
                                <div class="mykh-body-left-top-head">
                                    <div>
                                        <div class="word-line-con">
                                            <span>二级部得分</span>
                                            <div class="word-line-color-qiancong"></div>
                                        </div>
                                        <!-- <span class="card-title border-bottom-color-qiancong">二级部得分</span> -->
                                        <el-link type="primary" class="card-detail" @click="toEjbdf">更多详情</el-link>
                                    </div>
                                    <div class="card-score">0分</div>
                                </div>
                                <div class="mykhcomm-body-left-top-con">
                                    <el-scrollbar style="height:100%;width:100%;">
                                        <el-table :data="yearEjbScore">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="考核项" prop="plandescribe" min-width="70%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="评分" prop="tscore" min-width="20%"></el-table-column>
                                        </el-table>
                                    </el-scrollbar>
                                </div>
                            </div>
                        </div>
                    </el-col>
                    <el-col :span="8">
                        <div class="mykh-body-right">
                            <div class="mykh-body-right-top mykh-body-card">
                                <div class="mykh-body-left-top-head">
                                    <div>
                                        <div class="word-line-con">
                                            <span>平衡计分</span>
                                            <div class="word-line-color-yellow"></div>
                                        </div>
                                        <!-- <span class="card-title border-bottom-color-yellow">平衡计分</span> -->
                                        <el-link type="primary" class="card-detail" @click="toPhjf">更多详情</el-link>
                                    </div>
                                    <div class="card-score">{{scoreTj.yearPhjfScore}}分</div>
                                </div>
                                <div class="mykhcomm-body-left-top-con">
                                    <el-scrollbar style="height:100%;width:100%;">
                                        <el-table :data="yearPhjf">
                                            <el-table-column label="序号" type="index" min-width="10%"></el-table-column>
                                            <el-table-column label="考核项" prop="itemdescribe" min-width="30%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="考核内容" prop="plandescribe" min-width="40%"
                                                :show-overflow-tooltip="true"></el-table-column>
                                            <el-table-column label="评分" prop="tscore" min-width="20%">
                                                <template slot-scope="scope">
                                                    <template v-if="scope.row.tscore != undefined && scope.row.tscore != 0">
                                                        <span class="phjfColor-red">{{scope.row.tscore}}</span>
                                                    </template>
                                                    <template v-else>合格</template>
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                    </el-scrollbar>
                                </div>
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </div>
        </div>
        <!-- 设置弹窗 -->
        <el-dialog title="权限配置" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="mykh-setauth-dia">
                <el-form :model="updateSingData" ref="formXgSing">
                    <div class="hmd-row-block">
                        <el-row :gutter="50">
                            <el-col :span="12">
                                <div>
                                    <p>中心 *</p>
                                    <el-form-item prop="centerid" :rules="[{ required: true, message: '请选择中心', trigger: 'change'}]">
                                        <el-select placeholder="中心" filterable v-model="updateSingData.centerid"
                                            @change="centerChange">
                                            <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                :value="item.id"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p>部门 *</p>
                                    <el-form-item prop="depid" :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]">
                                        <el-select placeholder="部门" v-model="updateSingData.depid">
                                            <el-option v-for="item in centerDepData" :key="item.depId" :label="item.depName"
                                                :value="item.depId"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="hmd-row-block">
                        <el-row :gutter="50">
                            <el-col :span="12">
                                <div>
                                    <p>初审/评人</p>
                                    <el-form-item prop="firstcheck">
                                        <el-cascader ref="cascader" clearable :options="kjPeo_options" :props="kjPeoParams"
                                            v-model="updateSingData.firstcheck" :show-all-levels="false" filterable></el-cascader>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p>终审/评人 *</p>
                                    <el-form-item prop="secondcheck" :rules="[{ required: true, message: '请选择终审/评人', trigger: 'change'}]">
                                        <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                            v-model="updateSingData.secondcheck" :show-all-levels="false" filterable></el-cascader>
                                    </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelXgSing"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="确 定" size="mini" :fns="sureXgSing"></button-orange>
                </div>
            </div>
        </el-dialog>
        <!-- 编辑上半年评语弹窗 -->
        <el-dialog title="上半年评语" :visible.sync="ifShowFirstPyDialog" @close="xgPySingClose">
            <div class="pyContent">
                <el-form label-position="left" label-width="90px">
                    <el-row v-if="myKhData.firstcheck != undefined">
                        <el-col :span="24">
                            <el-form-item label="初评：">
                                <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="pyFirstData.firstevalcomment"
                                    :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" v-if="myKhData.firstcheck != undefined">
                        <el-col :span="12">
                            <el-form-item label="初评人：">
                                <el-input v-model="pyFirstData.firstevalname" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="初评时间：">
                                <el-input v-model="pyFirstData.firstevaltime" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="终评：">
                                <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="pyFirstData.secondevalcomment"
                                    :disabled="myKhData.secondcheck != userId"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" v-if="myKhData.secondcheck != userId">
                        <el-col :span="12">
                            <el-form-item label="终评人：">
                                <el-input v-model="pyFirstData.secondevalname" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="终评时间：">
                                <el-input v-model="pyFirstData.secondevaltime" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelFirstPy">取 消</el-button>
                    <el-button type="primary" @click="commitFirstPy" v-if="myKhData.secondcheck == userId">提 交</el-button>
                </div>
            </div>
        </el-dialog>
        <!-- 编辑下半年评语弹窗 -->
        <el-dialog title="下半年评语" :visible.sync="ifShowLaterPyDialog" @close="xgPySingClose">
            <div class="pyContent">
                <el-form label-position="left" label-width="90px">
                    <el-row v-if="myKhData.firstcheck != undefined">
                        <el-col :span="24">
                            <el-form-item label="初评：">
                                <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="pyLaterData.firstevalcomment"
                                    :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" v-if="myKhData.firstcheck != undefined">
                        <el-col :span="12">
                            <el-form-item label="初评人：">
                                <el-input v-model="pyLaterData.firstevalname" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="初评时间：">
                                <el-input v-model="pyLaterData.firstevaltime" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="终评：">
                                <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" v-model="pyLaterData.secondevalcomment"
                                    :disabled="myKhData.secondcheck != userId"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" v-if="myKhData.secondcheck != userId">
                        <el-col :span="12">
                            <el-form-item label="终评人：">
                                <el-input v-model="pyLaterData.secondevalname" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="终评时间：">
                                <el-input v-model="pyLaterData.secondevaltime" :disabled="true"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelLaterPy">取 消</el-button>
                    <el-button type="primary" @click="commitLaterPy" v-if="myKhData.secondcheck == userId">提 交</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import common from "@/api/common";
    import { updatePpmKpiRoleDtos } from "@/api/integrated/hykhStaff";
    import { getMyKpi, getPpmKpiPmTyears, getProfilePicture } from "@/api/hykh/myKh";
    import { getPpmKpiPmDetails } from "@/api/hykh/myDb";
    import { submitWholeComment } from "@/api/hykh/kpiEval";
    import Moment from 'moment';
    import { mapGetters } from 'vuex';
    export default {
        name: 'ygkhDetail',
        components: { buttonOrange, buttonCommon },
        data() {
            return {
                myKhData: {}, //我的考核页面数据
                my_kpis_year_eval: {}, //我的考核年度评价数据
                emp_kpis_first_half: {}, //我的考核员工上半年数据
                emp_kpis_last_half: {}, //我的考核员工下半年数据
                manager_kpis: {}, //我的考核经理层数据
                yearArr: [],
                currentYear: null,
                firstHalfYearKpi: [], //上半年kpi考核
                firstHalfYearNlts: [], //上半年能力提升
                latterHalfYearKpi: [], //下半年kpi考核
                latterHalfYearNlts: [], //下半年能力提升
                firstHalfYearZhnlAssess: [], //上半年综合能力评估
                latterHalfYearZhnlAssess: [], //下半年综合能力评估
                yearPhjf: [], //平衡计分
                yearKpiKh: [], //kpi考核
                yearKpiNlts: [], //kpi经理能力提升
                yeaLzpg: [], //履职评估
                yearEjbScore: [], //二级部得分
                updateSingData: {}, //权限配置数据
                ifShowXgSingDialog: false, //权限配置弹窗
                kjPeo_options: [],
                centerDepData: [],
                centerData: [],
                kjPeoParams: { //级联选择器配置信息
                    label: 'text',
                    value: 'pcode',
                    children: 'children',
                    emitPath: false,
                },
                commLeftTopTableHeight: null,
                scoreTj: {},
                routesMeta: [],
                avatar: null,
                pyFirstData: {}, //上半年评语
                pyLaterData: {}, //下半年评语
                ifShowFirstPyDialog: false, //是否展示上半年评语弹窗
                ifShowLaterPyDialog: false, //是否展示下半年评语弹窗
                tatalTscore: '-'
            }
        },
        computed: {
            ...mapGetters(['userId'])
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (to.params.eidname != undefined) {
                    vm.routesMeta = ['员工考核', to.params.eidname]
                } else {
                    vm.routesMeta = ['员工考核', vm.routerData.eidname]
                }
                // if (from.name == "ygKh") {
                //     vm.routesMeta = ['员工考核', to.params.eidname]
                // }
            })
        },
        beforeRouteLeave(to, from, next) {
            if (to.name != "ygKh") {
                sessionStorage.setItem('hykh_ygkhdetail_jump_data', JSON.stringify(this.routerData));
            }
            next();
        },
        mounted() {
            console.log('路由传值', this.$route.params);
            let valueTrans = {};
            if (JSON.stringify(this.$route.params) == '{}') {
                valueTrans = JSON.parse(sessionStorage.getItem('hykh_ygkhdetail_jump_data'))
            } else {
                valueTrans = this.$route.params;
            }
            this.routerData = valueTrans;
            this.currentYear = valueTrans.tyear;
            this.getYear();
            this.init();
        },
        methods: {
            // 初始化
            init() {
                this.getMyKhData();
                this.searchEpmlo();
                this.getCenter();
                // this.$nextTick(() => {
                //     this.commLeftTopTableHeight = this.$refs.mykhcommLeftTop.offsetHeight
                //     console.log('测试高度', this.commLeftTopTableHeight)
                // })
            },

            // 获取年份
            getYear() {
                let date = new Date();
                // this.yearArr = [date.getFullYear(), date.getFullYear() - 1];
                let params = {};
                params.eid = this.routerData.eid;
                let data = common.paramsFormat(params);
                getPpmKpiPmTyears(data).then(res => {
                    console.log('获取年份', res.data)
                    if (res.data.status == 200) {
                        this.yearArr = res.data.results;
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 年份变化
            cliTag() {
                this.init();
            },

            // 获取头像
            getPhoto(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat({ userid: val });
                getProfilePicture(data).then(res => {
                    loading.close();
                    console.log('获取头像', res.data)
                    if (res.data.status == 200) {
                        this.avatar = res.data.results.avatar;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取我的考核数据
            getMyKhData() {
                const loading = common.loading(this);
                let params = {};
                params.tyear = this.currentYear;
                params.eid = this.routerData.eid;
                params.secertkey = this.$md5('getMyKpi' + this.routerData.eid);
                let data = common.paramsFormat(params);
                getMyKpi(data).then(res => {
                    loading.close();
                    console.log("获取我的考核数据", res.data)
                    if (res.data.status == 200) {
                        if (res.data.results.ppmKpiRoleInfoDto.depname != undefined) {
                            res.data.results.ppmKpiRoleInfoDto.depname = res.data.results.ppmKpiRoleInfoDto.depname.replace('总行金融科技部-', '')
                        }
                        if (res.data.results.my_kpis_year_eval != undefined) {
                            this.my_kpis_year_eval = res.data.results.my_kpis_year_eval;
                        } else {
                            this.my_kpis_year_eval = {}
                        }
                        if (res.data.results.emp_kpis_first_half != undefined) {
                            if (res.data.results.emp_kpis_first_half.kpiscore == undefined) {
                                res.data.results.emp_kpis_first_half.kpiscore == 0;
                            }
                            this.emp_kpis_first_half = res.data.results.emp_kpis_first_half;
                            this.$nextTick(() => {
                                this.seaKhFirstHalfYearData();
                                this.seaNltsFirstHalfYearData();
                                this.seaZhnlpgFirstHalfYearData();
                            })
                        } else {
                            this.emp_kpis_first_half = {};
                            this.firstHalfYearKpi = [];
                            this.firstHalfYearNlts = [];
                            this.firstHalfYearZhnlAssess = [];
                        }
                        if (res.data.results.emp_kpis_last_half != undefined) {
                            if (res.data.results.emp_kpis_last_half.kpiscore == undefined) {
                                res.data.results.emp_kpis_last_half.kpiscore == 0;
                            }
                            this.emp_kpis_last_half = res.data.results.emp_kpis_last_half;
                            this.$nextTick(() => {
                                this.seaKhLaterHalfYearData();
                                this.seaNltsLaterHalfYearData();
                                this.seaZhnlpgLaterHalfYearData();
                            })
                        } else {
                            this.emp_kpis_last_half = {};
                            this.latterHalfYearKpi = [];
                            this.latterHalfYearNlts = [];
                            this.latterHalfYearZhnlAssess = [];
                        }
                        if (res.data.results.manager_kpis != undefined) {
                            if (res.data.results.manager_kpis.kpiscore == undefined) {
                                res.data.results.manager_kpis.kpiscore == 0;
                            }
                            this.manager_kpis = res.data.results.manager_kpis;
                            this.seaLzpgYearData();
                            if (res.data.results.ppmKpiRoleInfoDto.role == 'DEP_MANAGERS') {
                                this.seaKhYearData();
                                this.seaNltsYearData();
                            }
                        } else {
                            this.manager_kpis = {};
                            this.yeaLzpg = [];
                            this.yearKpiKh = [];
                            this.yearKpiNlts = [];
                        }
                        if (res.data.results.ppmKpiRoleInfoDto.centerid != undefined) {
                            this.updateSingData.centerid = Number(res.data.results.ppmKpiRoleInfoDto.centerid)
                        }
                        this.updateSingData.depid = res.data.results.ppmKpiRoleInfoDto.depid;
                        this.updateSingData.id = res.data.results.ppmKpiRoleInfoDto.id;
                        this.updateSingData.firstcheck = res.data.results.ppmKpiRoleInfoDto.firstcheck;
                        this.updateSingData.secondcheck = res.data.results.ppmKpiRoleInfoDto.secondcheck;
                        this.myKhData = res.data.results.ppmKpiRoleInfoDto;
                        if (this.myKhData.role == 'COMM_USER') {
                            if (this.emp_kpis_first_half.kpiscore != undefined && this.emp_kpis_last_half.kpiscore != undefined) {
                                this.tatalTscore = this.emp_kpis_first_half.kpiscore + this.emp_kpis_last_half.kpiscore
                            } else if (this.emp_kpis_first_half.kpiscore != undefined) {
                                this.tatalTscore = this.emp_kpis_first_half.kpiscore
                            } else if (this.emp_kpis_last_half.kpiscore != undefined) {
                                this.tatalTscore = this.emp_kpis_last_half.kpiscore
                            }
                        } else {
                            if (this.manager_kpis.kpiscore != undefined) {
                                this.tatalTscore = this.manager_kpis.kpiscore
                            }
                        }
                        this.getPhoto(res.data.results.ppmKpiRoleInfoDto.badge);
                        this.seaPhjfYearData();
                        this.getComment();
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取中心
            getCenter() {
                queryList().then(res => {
                    console.log('中心', res.data)
                    if (res.data.status == 200) {
                        this.centerData = res.data.results.filter(li => {
                            return li.id != 61
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 查询行员
            searchEpmlo() {
                let params = '';
                params = "7004";
                queryEmployeeTree(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            this.kjPeo_options = res.data.results[0].children.filter(tt => {
                                tt.text = tt.text.replace('总行金融科技部-', "");
                                return tt;
                            });
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
            },

            // 中心改变
            centerChange(val) {
                console.log('中心改变', val)
                this.getDep(val);
            },

            // 获取部门
            getDep(id) {
                const loading = common.loading(this);
                queryDeptList({ centerId: id })
                    .then(res => {
                        loading.close();
                        console.log("获取部门", res.data)
                        if (res.data.status == 200) {
                            var fild = res.data.results;
                            this.centerDepData = fild.filter(tt => {
                                tt.depName = tt.depName.replace('总行金融科技部-', "");
                                return tt;
                            });
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
            },

            // 修改单人
            corrSingle() {
                this.getDep(this.updateSingData.centerid);
                this.ifShowXgSingDialog = true;
            },

            // 修改单人确定
            sureXgSing() {
                console.log("修改单人确定", this.updateMoData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.ids = this.updateSingData.id;
                        params.centerid = this.updateSingData.centerid;
                        params.depid = this.updateSingData.depid;
                        if (this.updateSingData.firstcheck == null) {
                            params.firstcheck = "";
                        } else {
                            params.firstcheck = this.updateSingData.firstcheck;
                        }
                        params.secondcheck = this.updateSingData.secondcheck;
                        let data = common.paramsFormat(params);
                        updatePpmKpiRoleDtos(data).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('更新成功');
                                this.getMyKhData();
                                this.ifShowXgSingDialog = false;
                            } else {
                                this.$message.error('更新失败')
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    } else {
                        this.$message.error('请填写必填项');
                        return false;
                    }
                });
            },

            // 修改单人取消
            cancelXgSing() {
                this.ifShowXgSingDialog = false;
            },

            // 修改单人弹窗关闭回调
            xgSingClose() {
                this.$refs["formXgSing"].clearValidate();
            },

            // 获取年度考核信息
            seaKhYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.manager_kpis.id, kpitype: '1' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取年度考核信息', res.data)
                    if (res.data.status == 200) {
                        // let yearKhScore = 0;
                        // res.data.results.forEach(li => {
                        //     if (li.tscore != undefined) {
                        //         yearKhScore += li.tscore
                        //     }
                        // })
                        // this.scoreTj.yearKhScore = yearKhScore;
                        this.yearKpiKh = res.data.results.filter(item => {
                            item.finishdate = Moment(item.finishdate).format('YYYY-MM-DD');
                            return item
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取年度能力提升信息
            seaNltsYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.manager_kpis.id, kpitype: '5' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取年度能力提升信息', res.data)
                    if (res.data.status == 200) {
                        this.yearKpiNlts = res.data.results.filter(item => {
                            item.finishdate = Moment(item.finishdate).format('YYYY-MM-DD');
                            return item
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取上半年考核信息
            seaKhFirstHalfYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.emp_kpis_first_half.id, kpitype: '1' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取上半年考核信息', res.data)
                    if (res.data.status == 200) {
                        // let firstHalfYearKhScore = 0;
                        // res.data.results.forEach(li => {
                        //     if (li.tscore != undefined) {
                        //         firstHalfYearKhScore += li.tscore
                        //     }
                        // })
                        // this.scoreTj.firstHalfYearKhScore = firstHalfYearKhScore;
                        this.firstHalfYearKpi = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取上半年能力提升信息
            seaNltsFirstHalfYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.emp_kpis_first_half.id, kpitype: '5' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取上半年能力提升信息', res.data)
                    if (res.data.status == 200) {
                        this.firstHalfYearNlts = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取下半年考核信息
            seaKhLaterHalfYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.emp_kpis_last_half.id, kpitype: '1' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取下半年考核信息', res.data)
                    if (res.data.status == 200) {
                        // let LaterHalfYearKhScore = 0;
                        // res.data.results.forEach(li => {
                        //     if (li.tscore != undefined) {
                        //         LaterHalfYearKhScore += li.tscore
                        //     }
                        // })
                        // this.scoreTj.LaterHalfYearKhScore = LaterHalfYearKhScore;
                        this.latterHalfYearKpi = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取下半年考能力提升信息
            seaNltsLaterHalfYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.emp_kpis_last_half.id, kpitype: '5' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取下半年考能力提升信息', res.data)
                    if (res.data.status == 200) {
                        this.latterHalfYearNlts = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取上半年综合能力评估信息
            seaZhnlpgFirstHalfYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.emp_kpis_first_half.id, kpitype: '2' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取上半年综合能力评估信息', res.data)
                    if (res.data.status == 200) {
                        // let firstHalfYearZhnlAssessScore = 0;
                        // res.data.results.forEach(li => {
                        //     if (li.tscore != undefined) {
                        //         firstHalfYearZhnlAssessScore += li.tscore
                        //     }
                        // })
                        // this.scoreTj.firstHalfYearZhnlAssessScore = firstHalfYearZhnlAssessScore;
                        this.firstHalfYearZhnlAssess = res.data.results.filter(li => {
                            if (li.tscore != undefined) {
                                li.tscore = li.tscore / 20
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取下半年综合能力评估信息
            seaZhnlpgLaterHalfYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.emp_kpis_last_half.id, kpitype: '2' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取下半年综合能力评估信息', res.data)
                    if (res.data.status == 200) {
                        // let laterHalfYearZhnlAssessScore = 0;
                        // res.data.results.forEach(li => {
                        //     if (li.tscore != undefined) {
                        //         laterHalfYearZhnlAssessScore += li.tscore
                        //     }
                        // })
                        // this.scoreTj.laterHalfYearZhnlAssessScore = laterHalfYearZhnlAssessScore;
                        this.latterHalfYearZhnlAssess = res.data.results.filter(li => {
                            if (li.tscore != undefined) {
                                li.tscore = li.tscore / 20
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取平衡计分信息
            seaPhjfYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ eid: this.myKhData.badge, tyear: this.currentYear, kpitype: '4' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取平衡计分信息', res.data)
                    if (res.data.status == 200) {
                        let yearPhjfScore = 0;
                        res.data.results.forEach(li => {
                            if (li.tscore != undefined) {
                                yearPhjfScore = common.numAdd(yearPhjfScore, li.tscore)
                            }
                        })
                        this.scoreTj.yearPhjfScore = yearPhjfScore;
                        this.yearPhjf = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取履职评估信息
            seaLzpgYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.manager_kpis.id, kpitype: '3' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取履职评估信息', res.data)
                    if (res.data.status == 200) {
                        // let yearLzpgScore = 0;
                        // res.data.results.forEach(li => {
                        //     if (li.tscore != undefined) {
                        //         yearLzpgScore += li.tscore
                        //     }
                        // })
                        // this.scoreTj.yearLzpgScore = yearLzpgScore;
                        this.yeaLzpg = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取二级部得分信息
            seaEjbScoreYearData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.emp_kpis_last_half.id, kpitype: '' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取二级部得分信息', res.data)
                    if (res.data.status == 200) {
                        this.latterHalfYearKpi = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取评语
            getComment() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ eid: this.myKhData.badge, tyear: this.currentYear, kpitype: '6' });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close();
                    console.log('获取评语', res.data)
                    if (res.data.status == 200) {
                        for (var i = 0; i < res.data.results.length; i++) {
                            if (res.data.results[i].term == '上半年') {
                                if (res.data.results[i].firstevaltime != undefined) {
                                    res.data.results[i].firstevaltime = Moment(res.data.results[i].firstevaltime).format('YYYY-MM-DD HH:mm');
                                } else {
                                    res.data.results[i].firstevaltime = null;
                                }
                                if (res.data.results[i].secondevaltime != undefined) {
                                    res.data.results[i].secondevaltime = Moment(res.data.results[i].secondevaltime).format('YYYY-MM-DD HH:mm');
                                } else {
                                    res.data.results[i].secondevaltime = null;
                                }
                                this.pyFirstData = res.data.results[i];
                            } else if (res.data.results[i].term == '下半年') {
                                if (res.data.results[i].firstevaltime != undefined) {
                                    res.data.results[i].firstevaltime = Moment(res.data.results[i].firstevaltime).format('YYYY-MM-DD HH:mm');
                                } else {
                                    res.data.results[i].firstevaltime = null;
                                }
                                if (res.data.results[i].secondevaltime != undefined) {
                                    res.data.results[i].secondevaltime = Moment(res.data.results[i].secondevaltime).format('YYYY-MM-DD HH:mm');
                                } else {
                                    res.data.results[i].secondevaltime = null;
                                }
                                this.pyLaterData = res.data.results[i];
                            }
                        }
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 编辑上半年评语
            editFirstComment() {
                this.ifShowFirstPyDialog = true;
            },

            // 提交上半年评语
            commitFirstPy() {
                if (this.pyFirstData.secondevalcomment == null || this.pyFirstData.secondevalcomment == '' || this.pyFirstData.secondevalcomment == undefined) {
                    this.$message.error('请填写评语');
                    return
                }
                const loading = common.loading(this);
                let params = {};
                params.secondevalcomment = this.pyFirstData.secondevalcomment;
                params.term = this.pyFirstData.term;
                params.eid = this.pyFirstData.eid;
                params.id = this.pyFirstData.id;
                params.pid = this.pyFirstData.pid;
                params.itemstatus = this.pyFirstData.itemstatus;
                let data = common.paramsFormat(params);
                submitWholeComment(data).then(res => {
                    loading.close();
                    console.log('提交评语', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('提交成功');
                        this.ifShowFirstPyDialog = false;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 取消上半年评语
            cancelFirstPy() {
                this.ifShowFirstPyDialog = false;
            },

            // 关闭上半年评语
            xgPySingClose() {
                this.getComment();
            },

            // 编辑下半年评语
            editLaterComment() {
                this.ifShowLaterPyDialog = true
            },

            // 提交下半年评语
            commitLaterPy() {
                if (this.pyLaterData.secondevalcomment == null || this.pyLaterData.secondevalcomment == '' || this.pyLaterData.secondevalcomment == undefined) {
                    this.$message.error('请填写评语');
                    return
                }
                const loading = common.loading(this);
                let params = {};
                params.secondevalcomment = this.pyLaterData.secondevalcomment;
                params.term = this.pyLaterData.term;
                params.eid = this.pyLaterData.eid;
                params.id = this.pyLaterData.id;
                params.pid = this.pyLaterData.pid;
                params.itemstatus = this.pyLaterData.itemstatus;
                let data = common.paramsFormat(params);
                submitWholeComment(data).then(res => {
                    loading.close();
                    console.log('提交评语', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('提交成功');
                        this.ifShowLaterPyDialog = false;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 取消下半年评语
            cancelLaterPy() {
                this.ifShowLaterPyDialog = false;
            },

            //跳转上半年自评
            toFirstKpizp() {
                let params = {};
                if (this.emp_kpis_first_half.term != undefined) {
                    params.term = this.emp_kpis_first_half.term
                };
                params.tyear = this.emp_kpis_first_half.tyear;
                params.eid = this.emp_kpis_first_half.eid;
                params.eidname = this.emp_kpis_first_half.eidname;
                params.isDb = '2';
                this.$router.push({
                    name: 'kpiPj',
                    params
                })
            },

            //跳转下半年自评
            toLaterKpizp() {
                let params = {};
                if (this.emp_kpis_last_half.term != undefined) {
                    params.term = this.emp_kpis_last_half.term
                };
                params.tyear = this.emp_kpis_last_half.tyear;
                params.eid = this.emp_kpis_last_half.eid;
                params.eidname = this.emp_kpis_last_half.eidname;
                params.isDb = '2';
                this.$router.push({
                    name: 'kpiPj',
                    params
                })
            },

            //跳转自评
            toKpipj() {
                let params = {};
                if (this.manager_kpis.term != undefined) {
                    params.term = this.manager_kpis.term
                };
                params.tyear = this.manager_kpis.tyear;
                params.eid = this.manager_kpis.eid;
                params.eidname = this.manager_kpis.eidname;
                params.isDb = '2';
                this.$router.push({
                    name: 'kpiPj',
                    params
                })
            },

            // 跳转上半年综合能力评估
            toFirstZhnlpg() {
                let params = {};
                if (this.emp_kpis_first_half != undefined) {
                    params = this.emp_kpis_first_half
                };
                this.$router.push({
                    name: 'ygkhZhnlAssessment',
                    params
                })
            },

            // 跳转下半年综合能力评估
            toLaterZhnlpg() {
                let params = {};
                if (this.emp_kpis_last_half != undefined) {
                    params = this.emp_kpis_last_half
                };
                this.$router.push({
                    name: 'ygkhZhnlAssessment',
                    params
                })
            },

            // 跳转平衡计分
            toPhjf() {
                let params = {};
                params.tyear = this.currentYear;
                params.eid = this.myKhData.badge;
                params.eidname = this.myKhData.empname;
                this.$router.push({
                    name: 'ygkhPhjf',
                    params
                })
            },

            // 跳转履职评估
            toLzpg() {
                let params = {};
                if (this.manager_kpis != undefined) {
                    params = this.manager_kpis
                };
                this.$router.push({
                    name: 'mykhLzpgDetail',
                    params
                })
            },

            // 跳转二级部得分
            toEjbdf() {

            },

            // 返回
            lastPage() {
                this.$router.back()
            }
        }
    }
</script>
<style scoped>
    .mykh-container {
        /* padding: 15px; */
        margin: 0px 15px;
        height: calc(100vh - 52px);
    }

    .mykh-head {
        /* height: 20%; */
        height: 17%;
        background: inherit;
        background-color: rgba(255, 255, 255, 1);
        border: none;
        border-radius: 10px;
        margin-top: 10px;
        padding: 5px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.149019607843137);
    }

    .mykh-body {
        height: 72%;
        margin-top: 10px;
    }

    .mykh-head-left {
        height: 100%;
    }

    .mykh-head-middle {
        height: 100%;
        padding: 10px 0;
    }

    .mykh-head-middle-head-left {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-size: 14px;
        color: #464959;
    }

    .mykh-head-right {
        height: 100%;
    }

    .mykh-head-left-left {
        /* width: 100%; */
        height: 100%;
        text-align: center;
    }

    .mykh-head-left-left-image {
        width: 80%;
        height: 80%;
    }

    .mykh-head-left-middle-bottom {
        margin-top: 8%;
    }

    .mykh-head-left-middle-top {
        display: flex;
        justify-content: space-between;
    }

    .mykh-head-left-middle-top-name {
        box-shadow: none;
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 16px;
        color: #181C32;
    }

    .mykh-head-left-middle-top-jobid {
        box-shadow: none;
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 14px;
        color: #ABACB0;
        margin-left: 5px;
    }

    .mykh-head-left-middle-top-right {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 12px;
        color: #3A8EE6;
    }

    .mykh-head-left-middle-middle {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #C3C4C4;
        margin-top: 1%;
    }

    .mykh-head-left-middle-bottom-describe {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        margin-right: 10px;
    }

    .mykh-head-left-middle-bottom-score {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 24px;
        color: #FBA42F;
    }

    .mykh-head-middle-text {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 12px;
        color: #7E8299;
        line-height: 20px;
        padding: 7px 0;
        height: 100%;
    }

    .mykh-head-middle-text-wfq {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 20px;
        color: #C4C4C4;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .mykh-body-left {
        height: 100%;
        box-sizing: border-box;
    }

    .mykh-body-right {
        height: 98%;
        box-sizing: border-box;
        background-color: rgba(255, 255, 255, 1);
        border-radius: 10px;
    }

    .mykh-body-left-top {
        height: 48%;
        margin-bottom: 10px;
    }

    .mykh-body-left-top-head {
        display: flex;
        justify-content: space-between;
        margin-bottom: 10px;
    }

    .mykh-body-card {
        background: inherit;
        background-color: rgba(255, 255, 255, 1);
        border: none;
        border-radius: 10px;
        padding: 15px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.149019607843137);
    }

    .card-title {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        border-radius: 5px;
        padding: 0 5px 5px 5px;
    }

    .border-bottom-color-pink {
        border-bottom: 7px solid rgba(253, 57, 122, 1);
    }

    .border-bottom-color-blue {
        border-bottom: 7px solid rgba(88, 103, 221, 1);
    }

    .border-bottom-color-yellow {
        border-bottom: 7px solid rgba(255, 184, 34, 1);
    }

    .border-bottom-color-qiancong {
        border-bottom: 7px solid rgba(29, 201, 183, 1);
    }

    .word-line-con {
        display: inline-block;
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        padding: 0 5px;
    }

    .word-line-color-pink {
        border-top: 3.5px solid rgba(253, 57, 122, 1);
        border-bottom: 3.5px solid rgba(253, 57, 122, 1);
        border-radius: 3.5px;
        margin-top: 2px;
    }

    .word-line-color-yellow {
        border-top: 3.5px solid rgba(255, 184, 34, 1);
        border-bottom: 3.5px solid rgba(255, 184, 34, 1);
        border-radius: 3.5px;
        margin-top: 2px;
    }

    .word-line-color-blue {
        border-top: 3.5px solid rgba(88, 103, 221, 1);
        border-bottom: 3.5px solid rgba(88, 103, 221, 1);
        border-radius: 3.5px;
        margin-top: 2px;
    }

    .word-line-color-qiancong {
        border-top: 3.5px solid rgba(29, 201, 183, 1);
        border-bottom: 3.5px solid rgba(29, 201, 183, 1);
        border-radius: 3.5px;
        margin-top: 2px;
    }

    .card-detail {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 12px;
        color: #5867DD;
        margin-left: 5px;
    }

    .card-score {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 20px;
    }

    .mykh-body-right-top {
        height: 100%;
    }

    .hmddialog-bottom {
        text-align: center;
    }

    .mykhcomm-body-left-top-con {
        height: 90%;
    }

    .mykh-body-fgjl {
        height: 49%;
    }

    .mykh-body-zcjl {
        height: 98%;
    }

    .mykhcomm-body-left-top-con-title {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        border-radius: 5px;
        padding: 0 5px 5px 5px;
        margin: 12px 0 6px 0;
        display: inline-block;
    }

    .lastJc {
        font-family: '微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑', sans-serif;
        font-weight: 700;
        color: #414141;
    }

    .crumbOther {
        font-family: '微软雅黑', sans-serif;
        font-weight: 400;
        color: #939393;
    }

    .mykh-head-middle-head {
        display: flex;
        justify-content: space-between;
    }

    .tdWarp {
        white-space: pre-wrap;
    }

    .ygkhdetail-head {
        display: flex;
        justify-content: space-between;
        margin: 5px 0;
    }

    .kh-back-bread {
        height: 36px;
    }

    .kh-back-bread-butt {
        height: 36px;
        line-height: 36px;
        float: left;
        margin-right: 10px;
    }
</style>
<style>
    .mykh-nav .el-input__prefix {
        line-height: 36px;
    }

    .mykh-nav .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .mykh-head .el-row {
        height: 100%;
    }

    .mykh-head-middle .el-form-item__label {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #7E8299;
    }

    .mykh-head-middle .el-form {
        height: 100%;
    }

    .mykh-head-middle .el-form-item {
        height: 100%;
    }

    .mykh-head-middle .el-form-item__content {
        height: 100%;
    }

    .mykh-head-middle .el-scrollbar__wrap {
        overflow-x: auto;
    }

    .mykhcomm-body-left-top-con .el-scrollbar__wrap {
        overflow-x: auto;
    }

    .mykh-setauth-dia .el-cascader {
        display: block
    }

    .rowHeight-100 .el-row {
        height: 100%;
    }

    .rowHeight-100 .el-col-6 {
        height: 100%;
    }

    .rowHeight-100 .el-col-8 {
        height: 100%;
    }

    .rowHeight-100 .el-col-10 {
        height: 100%;
    }

    .rowHeight-100 .el-col-16 {
        height: 100%;
    }

    .rowHeight-100 .el-col-24 {
        height: 100%;
    }

    .zhnlAss-block .el-rate {
        height: 100%;
    }

    .kh-back-bread .el-breadcrumb {
        height: 36px;
        line-height: 36px;
        float: left;
    }
</style>