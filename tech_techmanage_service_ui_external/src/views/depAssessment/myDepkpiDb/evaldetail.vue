<!-- 指标配置 -->
<template>
    <div class="web-container">
        <div class="web-head">
            <div class="web-head-back-bread">
                <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
            </div>
        </div>
        <div class="web-head-search-form">
            <el-form inline>
                <div class="web-head-content xkg-borderradius-4px">
                    <div class="web-head-left">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="考核内容:">
                                    <el-input v-model="paramsForm.content"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="web-head-right">
                        <div class="web-head-right-clean" @click="resetForm">
                            <clean-btn size="small" :forms="this.paramsForm" />
                        </div>
                        <div class="web-head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>
        <el-row class="web-body" :gutter="10">
            <el-col class="web-body-left" :span="8">
                <div class="web-body-count">
                    <div class="web-body-count-title">
                        <el-row>
                            <span class="web-body-count-title-text">考核数据</span>
                        </el-row>
                    </div>
                    <div class="web-body-count-num">
                        <el-row>
                            <el-col :span="6">
                                <div class="web-body-count-num-item">
                                    <span class="web-body-count-num-item-point">{{sumpoint.commitsum + '/' + sumpoint.countcontent}}</span>
                                    <span>待评价项</span>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="web-body-count-num-item">
                                    <span class="web-body-count-num-item-point">{{(sumpoint.zdysumpoint + "").replace(".0", "")}}<span>分</span></span>
                                    <span>自定义评分</span>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="web-body-count-num-item">
                                    <span class="web-body-count-num-item-point">{{(sumpoint.ggsumpoint + "").replace(".0", "")}}<span>分</span></span>
                                    <span>公共项评分</span>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="web-body-count-num-item" style="border: none;">
                                    <span class="web-body-count-num-item-point sum-point">{{(sumpoint.zdysumpoint + sumpoint.ggsumpoint + "").replace(".0", "")}}<span>分</span></span>
                                    <span>总分</span>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <div class="web-body-content">
                    <div class="web-body-content-title">
                        <el-row>
                            <span class="web-body-content-title-text">考核目录</span>
                            <span 
                                :class="[ dataType == 1 ? 'web-body-content-title-left fun-hover web-body-content-title-left-selected': 'web-body-content-title-left fun-hover']" 
                                @click="titltSelect(1)">待办项</span>
                            <span 
                                :class="[ dataType == 0 ? 'web-body-content-title-left fun-hover web-body-content-title-left-selected': 'web-body-content-title-left fun-hover']" 
                                @click="titltSelect(0)">全部</span>
                        </el-row>
                    </div>
                    <el-table :data="tableData" row-key="eid" height="370" :tree-props="{children: 'children'}" 
                    :row-class-name="rowClassChoose" @row-click="handleTitleSelectionChange" :expand-row-keys="expandRowKeys">
                        <el-table-column label="考核项" prop="name" min-width="100" :width="tableWidth">
                            <template slot-scope="scope">
                                <template v-if="scope.row.type == 1 && scope.row.levelnum == 1 && scope.row.children.length == 0">
                                    &emsp;&nbsp;&nbsp;&nbsp;
                                </template>
                                <span v-if="scope.row.type == 2">📂&nbsp;&nbsp;{{scope.row.name}}</span>
                                <span v-if="scope.row.type == 1" style="font-weight:700;color: #333333;">📚&nbsp;&nbsp;{{scope.row.name}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="基准分" width="30" prop="basepoint" align="center"></el-table-column>
                    </el-table>
                </div>
            </el-col>
            <el-col class="web-body-right" :span="16">
                <div class="web-body-content web-body-content-left">
                    <div class="web-body-content-title">
                        <el-row>
                            <span class="web-body-content-title-text">考核内容</span>
                            <span class="web-body-content-title-text2 fun-hover" @click="showAllContent">
                                <img id="u2813_img" class="img" src="../../../assets/u2813.png">
                                显示所有考核项
                            </span>
                            <button-orange class="web-body-content-title-fun" :fns="pjCommit" buttonName="提 交" size="mini"></button-orange>
                        </el-row>
                    </div>
                    <el-table :data="contentData" height="490" width="600" :row-class-name="rowClassChoose" >
                        <el-table-column label="考核项或标题" width="150" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span v-if="scope.row.titlename">{{scope.row.titlename}}</span>
                                <span v-else>{{scope.row.targetname}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="考核内容" prop="content" width="150" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="基准分" width="60" prop="basepoint" align="center"></el-table-column>
                        <el-table-column label="时间安排" width="140" align="center" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span v-if="scope.row.startdate">{{scope.row.startdate.substr(5,10).replace('-', "月") + "日"}}</span>
                                <span>~</span>
                                <span v-if="scope.row.enddate">{{scope.row.enddate.substr(5,10).replace('-', "月") + "日"}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="评分标准" width="100" prop="standard" align="center" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="评价" width="100" align="center">
                            <template slot-scope="scope">
                                <template v-if="scope.row.evaltype == 1">
                                    <span v-if="scope.row.secondcheckres">
                                        {{scope.row.secondcheckres}}
                                    </span>
                                    <span v-else-if="scope.row.firstcheckres">
                                        {{scope.row.firstcheckres}}
                                    </span>
                                    <span v-else>
                                        -
                                    </span>
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="得分" width="80" align="center">
                            <template slot-scope="scope">
                                <span v-if="scope.row.targettype == 1">
                                    {{scope.row.secondcheckpoint}}
                                </span>
                                <span v-else-if="scope.row.secondcheckpoint">
                                    {{scope.row.secondcheckpoint}}
                                </span>
                                <span v-else-if="scope.row.firstcheckpoint">
                                    {{scope.row.firstcheckpoint}}
                                </span>
                                <span v-else>
                                    -
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="80" align="center">
                            <template slot-scope="scope">
                                <span v-if="scope.row.targettype == 1 || scope.row.dbuser != userId" class="fun-hover" @click="seeContent(scope.row)" style="color:#409EFF;">详情</span>
                                <template v-else>
                                    <span v-if="scope.row.isSecond == 0 && scope.row.firstcheckstatus != 1" class="fun-hover" @click="updateContent(scope.row)" style="color:#409EFF;">评价</span>
                                    <span v-if="scope.row.isSecond == 0 && scope.row.firstcheckstatus == 1" class="fun-hover" @click="updateContent(scope.row)" style="color:#409EFF;">已评价</span>
                                    <span v-if="scope.row.isSecond != 0 && scope.row.secondcheckstatus != 1" class="fun-hover" @click="updateContent(scope.row)" style="color:#409EFF;">评价</span>
                                    <span v-if="scope.row.isSecond != 0 && scope.row.secondcheckstatus == 1" class="fun-hover" @click="updateContent(scope.row)" style="color:#409EFF;">已评价</span>
                                </template>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
        </el-row>

        <!-- 添加/修改 -->
        <el-dialog title="评价" :visible.sync="ifShowTjContentDialog" @close="addClose" width="40%">
            <div class="dialog-content">
                <el-form class="dialog-form" :model="updateData" ref="formAddContent" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateData.id"></el-input></el-form-item>
                    <el-form-item label="考核内容：" prop="content">
                        <el-input v-model="updateData.content" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="基准分：" prop="basepoint">
                        <el-input v-model="updateData.basepoint" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="评分标准：" prop="standard">
                        <el-input v-model="updateData.standard" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="时间范围：" prop="datepicker">
                        <el-date-picker v-model="updateData.datepicker" type="daterange" value-format="yyyy-MM-dd"
                            range-separator="至" start-placeholde="开始日期" end-placeholde="结束日期" disabled></el-date-picker>
                    </el-form-item>
                    <el-form-item v-if="updateData.selfeval == 1" label="自评内容：" prop="selfevalres">
                        <el-input v-model="updateData.selfevalres" type="textarea" maxlength="200" 
                        :autosize="{minRows: 3, maxRows: 5}" disabled></el-input>
                    </el-form-item>
                    <hr style="margin: 20px 0; opacity: 0.2;"/>
                    <div v-if="updateData.isSecond == 2" class="dialog-content-center">
                        <span class="dialog-center-title">初评</span>
                        <div class="dialog-center-content">
                            <div class="dialog-center-content-item dialog-center-content-point">
                                <div v-if="updateData.evaltype == 1"><span>考核评价：</span><span>{{updateData.firstcheckres}}</span></div>
                                <div><span>得分：</span><span>{{updateData.firstcheckpoint}}</span></div>
                            </div>
                            <div class="dialog-center-content-item dialog-center-content-user">
                                <div><span>评价人：</span><span>{{updateData.firstcheck}}</span></div>
                                <div><span>评价时间：</span><span>{{updateData.firstchecktime}}</span></div>
                            </div>
                            <div v-if="updateData.evaltype == 2" class="dialog-center-content-item dialog-center-content-remark">
                                <div><span>备注：</span><span>{{updateData.firstcheckremark}}</span></div>
                            </div>
                        </div>
                    </div>
                    <hr v-if="updateData.isSecond == 2" style="margin: 20px 0; opacity: 0.2;"/>
                    <span v-if="updateData.isSecond != 0" class="dialog-center-title">终评</span>
                    <span v-if="updateData.isSecond == 0" class="dialog-center-title">初评</span>
                    <template v-if="updateData.isSecond == 0">
                        <template v-if="updateData.evaltype == 1">
                            <el-form-item label="考核评价：" prop="firstcheckres" :rules="[{ required: true, message: '请选择档位', trigger: 'change'}]">
                                <el-select v-model="updateData.firstcheckres" value-key="dictid" filterable @change="changeFirstEvalSet" :disabled="seeFlag">
                                    <el-option v-for="item in depkpiEvalSet" :key="item.dictname" :label="item.dictname" :value="item.dictname"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="得分：" prop="firstcheckpoint" 
                                :rules="[{ required: true, message: '得分不能为空', trigger: 'blur'}]">
                                <el-input type="number" v-model="updateData.firstcheckpoint" disabled></el-input>
                            </el-form-item>
                        </template>
                        <template v-if="updateData.evaltype == 2">
                            <el-form-item  label="得分：" prop="firstcheckpoint" :rules="[{ required: true, validator: isNumber, trigger: 'blur'}]">
                                <el-input type="number" v-model="updateData.firstcheckpoint" :disabled="seeFlag"></el-input>
                            </el-form-item>
                        </template>
                        <template v-if="updateData.evaltype == 3">
                            <el-form-item label="得分：" prop="firstcheckpoint" 
                            :rules="[{ required: true, validator: this.pointRule, trigger: 'blur'}]">
                                <el-input type="number" v-model="updateData.firstcheckpoint" :disabled="seeFlag" 
                                :placeholder="'上限分数' + updateData.maxpoint + ',下限分数' + updateData.minpoint"></el-input>
                            </el-form-item>
                            <el-form-item label="备注：" prop="firstcheckremark" :rules="[{ required: true, message: '评论备注不能为空', trigger: 'blur'}]">
                                <el-input v-model="updateData.firstcheckremark" type="textarea" maxlength="200" 
                                    :autosize="{minRows: 3, maxRows: 5}" :disabled="seeFlag"></el-input>
                            </el-form-item>
                        </template>
                    </template>
                    <template v-if="updateData.isSecond != 0">
                        <template v-if="updateData.evaltype == 1">
                            <el-form-item label="考核评价：" prop="secondcheckres" :rules="[{ required: true, message: '请选择档位', trigger: 'change'}]">
                                <el-select v-model="updateData.secondcheckres" value-key="dictid" filterable @change="changeSecondEvalSet" :disabled="seeFlag">
                                    <el-option v-for="item in depkpiEvalSet" :key="item.dictname" :label="item.dictname" :value="item.dictname"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="得分：" prop="secondcheckpoint" 
                                :rules="[{ required: true, message: '得分不能为空', trigger: 'blur'}]">
                                <el-input type="number" v-model="updateData.secondcheckpoint" disabled></el-input>
                            </el-form-item>
                        </template>
                        <template v-if="updateData.evaltype == 2">
                            <el-form-item  label="得分：" prop="secondcheckpoint" :rules="[{ required: true, validator: isNumber, trigger: 'blur'}]">
                                <el-input type="number" v-model="updateData.secondcheckpoint" :disabled="seeFlag"></el-input>
                            </el-form-item>
                        </template>
                        <template v-if="updateData.evaltype == 3">
                            <el-form-item label="得分：" prop="secondcheckpoint" 
                            :rules="[{ required: true, validator: pointRule, trigger: 'blur'}]">
                                <el-input type="number" v-model="updateData.secondcheckpoint" :disabled="seeFlag" 
                                :placeholder="'上限分数' + updateData.maxpoint + ',下限分数' + updateData.minpoint"></el-input>
                            </el-form-item>
                            <el-form-item label="备注：" prop="secondcheckremark" :rules="[{ required: true, message: '评论备注不能为空', trigger: 'blur'}]">
                                <el-input v-model="updateData.secondcheckremark" type="textarea" maxlength="200" 
                                    :autosize="{minRows: 3, maxRows: 5}" :disabled="seeFlag"></el-input>
                            </el-form-item>
                        </template>
                    </template>
                </el-form>
                <div v-if="!seeFlag" class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelContent"></button-common>
                    <button-orange buttonName="保 存" size="mini" :fns="sureAddContent"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import { mapGetters } from "vuex";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { getEosDictEntrys } from "@/api/hykh/pjsz";
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { getCheckTree, zdyPjCommit } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    import { queryTitleTreeData } from "@/api/zzkhApi/title";
    import { getEvalContentList, evalContent, queryZdySumpoint } from "@/api/zzkhApi/content";
    import Vue from 'vue';
    export default {
        name: 'evaldetail',
        components: { buttonOrange, pager, buttonCommon, cleanBtn },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0,
                ifShowTjContentDialog: false, //是否显示添加弹窗
                seeFlag:false, //是否为查看
                tableWidth: "",
                maxLevel: 1,
                maxTitleLevel: 1,
                tableData: [],
                contentData: [],
                centerData: [],
                depData: [],
                depDataAll: [],
                titleList: [], // 考核标题数据
                noSonTitleList: [], // 标题下拉数据
                depkpiEvalSet: [], // 考核评级档位
                paramsForm: {
                    content: null,
                    parentDetailid: null,
                    parentTitleid: null
                },
                updateData: {
                    id: null,
                    title: null,
                    targetid: null,
                    depid: null,
                    content: null,
                    basepoint: null,
                    standard: null,
                    datepicker:[],
                    selfevalres: null,
                    isSecond: null,
                    firstcheckpoint: null,
                    firstcheckremark: null,
                    firstcheckres: null,
                    secondcheckpoint: null,
                    secondcheckremark: null,
                    secondcheckres: null,
                    maxpoint: null,
                    minPoint: null
                }, //添加数据
                sumpoint: {
                    ggsumpoint: 0,
                    zdysumpoint: 0,
                    commitsum: 0,
                    sumpoint: 0.0,
                    countcontent: 0
                },
                pointRule: (rule, value, callback) => {
                    if (!value && value != 0) {
                        callback((new Error('得分不能为空')));
                    } else if(Number(value) > this.updateData.maxpoint){
                        callback((new Error('不能超过上限分数' + this.updateData.maxpoint)));
                    } else if (Number(value) < this.updateData.minpoint){
                        callback((new Error('不能小于下限分数' + this.updateData.minpoint)));
                    } else {
                        callback();
                    }
                },
                isNumber: (rule, value, callback) => {
                    if (!value && value != 0) {
                        callback(new Error('得分不能为空'));
                    }
                    if (!/^\d+$/.test(value)) {
                        callback(new Error('请输入正整数,值为0-100'));
                    }
                    if(value > this.updateData.basepoint){
                        callback(new Error('得分不能超基准分'));
                    }
                    callback();
                },
                dataType: 1, // 0：所有；1：待办
                expandRowKeys: [], // 需要展开的树
            }
        },
        beforeCreate(){
            if(!this.$route.query.sign || !this.$route.query.id || !this.$route.query.name || !this.$route.query.tyear){
                this.$router.push({path:"/myDepKpiDb"});
            }
        },
        computed: {
            ...mapGetters([
                'userId'
            ])
        },
        mounted() {
            this.getEvalSet();
            this.queryZdySumpoint();
            this.submitBtn();
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.push({path: "/eval", query: {sign: this.$route.query.sign, tyear: this.$route.query.tyear}});
            },
            // 左侧标题头选择切换
            titltSelect(val){
                this.$nextTick(function(){
                    if(val == 1){
                        this.dataType = 1;
                    }else{
                        this.dataType = 0;
                    }
                    this.submitBtn();
                }) 
            },
            // 显示所有考核内容
            showAllContent(){
                this.submitBtn();
            },

            // 获取档位字典
            getEvalSet(){
                const loading = common.loading(this);
                let data = common.paramsFormat({ dicttypeid: 'DEP_KPI_EVAL_SET' });
                getEosDictEntrys(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.depkpiEvalSet = res.data.results.filter(li => {
                            li.dictid = common.accMul(li.dictid, 100);
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            rowClassChoose: function(row){
                if(row.row.targettype == 1){
                    return "gg-disabled";
                }
            },

            // 查询
            submitBtn: function(){
                this.setLefttable();
                this.queryContentList({});
            },

            setLefttable(){
                this.dealTreeData();
            },

            // 处理树结构
            dealTreeData(){
                let that = this;
                const loading = common.loading(that);
                Promise.all([
                    that.getCheckTree(),
                    that.queryTitleTreeData()
                ]).then(res => {
                    loading.close();
                    if (res[0].data.status != 200) {
                        this.$message.error(res[0].data.message);
                        return false;
                    }
                     if (res[1].data.status != 200) {
                        this.$message.error(res[1].data.message);
                        return false;
                    }
                    this.titleList = res[1].data.results;
                    that.tableData = that.getTreeData(res[0].data.results);
                });
            },

            // 考核汇总查询
            queryZdySumpoint(){
                var val = {};
                val.depid = this.$route.query.id;
                val.sign = this.$route.query.sign;
                val.tyear = this.$route.query.tyear;
                let data = common.paramsFormat(val);
                const loading = common.loading(this);
                queryZdySumpoint(data).then(res => {
                    loading.close();
                    console.log('考核汇总查询', res.data.results)
                    if (res.data.status == 200) {
                        this.sumpoint = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 部门考核项查询
            getCheckTree(){
                var val = {};
                val.depid = this.$route.query.id;
                val.sign = this.$route.query.sign;
                val.tyear = this.$route.query.tyear;
                // val.statuslist = '5,6';
                if(this.dataType == 1){
                    val.targettype = 2;
                    val.dbuser = this.userId;
                }
                return new Promise((resolve, reject)=>{
                    let data = common.paramsFormat(val);
                    getCheckTree(data).then(res => {
                        resolve(res);
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                })
            },     
            
            // 考核标题查询
            queryTitleTreeData(){
                var val = {};
                if(this.dataType == 1){
                    val.targettype = 2;
                }
                val.depid = this.$route.query.id;
                val.tyear = this.$route.query.tyear;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                return new Promise((resolve, reject)=>{
                    let data = common.paramsFormat(val);
                    queryTitleTreeData(data).then(res => {
                        resolve(res);
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                })
            },

            showAllContent(){
                this.queryContentList({});
            },

            // 考核内容查询
            queryContentList(val){
                if(!val){
                    val = {};
                }
                if(this.paramsForm.content && this.paramsForm.content != ""){
                    val.content = this.paramsForm.content;
                }
                val.depid = this.$route.query.id;
                val.sign = this.$route.query.sign;
                val.tyear = this.$route.query.tyear;
                if(this.dataType == 1){
                    val.targettype = 2;
                    val.dbuser = this.userId;
                    val.statuslist = '5,6';
                }
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                getEvalContentList(data).then(res => {
                    loading.close();
                    console.log('考核内容查询', res.data.results)
                    if (res.data.status == 200) {
                        this.contentData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 重置
            resetForm() {
            },

            changeFirstEvalSet(val){
                let evalset = this.depkpiEvalSet.find(item => item.dictname == val);
                this.updateData.firstcheckpoint = common.accMul(evalset.dictid, this.updateData.basepoint);
            },
            changeSecondEvalSet(val){
                console.info(val)
                let evalset = this.depkpiEvalSet.find(item => item.dictname == val);
                this.updateData.secondcheckpoint = common.accMul(evalset.dictid, this.updateData.basepoint);
            },

            // 修改
            updateContent(val) {
                this.updateData = JSON.parse(JSON.stringify(val));
                if(val.startdate){
                    this.updateData.datepicker = [val.startdate, val.enddate]
                }
                this.seeFlag = false;
                this.ifShowTjContentDialog = true;
            },

            //查看详情
            seeContent(val){
                this.updateData = JSON.parse(JSON.stringify(val));
                if(val.startdate){
                    this.updateData.datepicker = [val.startdate, val.enddate]
                }
                this.seeFlag = true;
                this.ifShowTjContentDialog = true;
            },

            // 保存确定
            sureAddContent(){
                this.$refs["formAddContent"].validate(valid => {
                    if (!valid) {
                        this.$message.error('请填写必填项');
                        return false;
                    } 
                    const loading = common.loading(this);
                    var params = {};
                    params.id = this.updateData.id;
                    if(this.updateData.isSecond == 0){
                        if(this.updateData.evaltype == 2 
                            && (this.updateData.firstcheckpoint < 0 || this.updateData.firstcheckpoint > this.updateData.basepoint)){
                            this.$message.error('分值不符合要求');
                            loading.close();
                            return;
                        }
                        params.checkpoint = this.updateData.firstcheckpoint;
                        params.checkremark = this.updateData.firstcheckremark;
                        params.checkres = this.updateData.firstcheckres;
                    } else {
                        if(this.updateData.evaltype == 2 
                            && (this.updateData.secondcheckpoint < 0 || this.updateData.secondcheckpoint > this.updateData.basepoint)){
                            this.$message.error('分值不符合要求');
                            loading.close();
                            return;
                        }
                        params.checkpoint = this.updateData.secondcheckpoint;
                        params.checkremark = this.updateData.secondcheckremark;
                        params.checkres = this.updateData.secondcheckres;
                    }
                    params = JSON.parse(JSON.stringify(params));
                    let data = common.paramsFormat(params);
                    evalContent(data).then(res => {
                        loading.close();
                        console.log("修改确定", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('修改成功');
                            this.ifShowTjContentDialog = false;
                            this.$refs["formAddContent"].resetFields();
                            this.queryContentList(this.paramsForm);
                            this.queryZdySumpoint();
                        } else {
                            this.$message.error('修改失败');
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                });
            },

            // 取消
            cancelContent() {
                this.ifShowTjContentDialog = false;
                this.$refs["formAddContent"].resetFields();
            },

            // 添加弹窗关闭回调
            addClose() {
                this.updateData = {
                    id: null,
                    titleid: null,
                    content: null,
                    basepoint: null,
                    standard: null,
                    datepicker:[],
                    evaltype: null,
                    maxpoint: null,
                    minpoint: null,
                };
            },

            // 表格选中变化
            handleTitleSelectionChange(val) {
                if(val.type == 1){// 考核项
                    this.paramsForm.parentTitleid = null;
                    this.paramsForm.parentDetailid = val.id;
                }else {// 标题
                    this.paramsForm.parentTitleid = val.id;
                    this.paramsForm.parentDetailid = null;
                }
                this.queryContentList(this.paramsForm);
            },

            // 评价提交
            pjCommit(){
                this.$confirm('确认提交吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let data = common.paramsFormat({ 
                        depid: this.$route.query.id,
                        tyear: this.$route.query.tyear,
                        sign: this.$route.query.sign
                    });
                    zdyPjCommit(data).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('提交成功');
                            this.goBack();
                        } else {
                            this.$message.error(res.data.message)
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => {})
            },

            // 封装递归树
            getTreeData(dataList){
                var max = 0;
                var that = this;
                var newData = [];
                let parentArr = dataList.filter(item => !item.parentid);
                parentArr.forEach(item =>{
                    this.maxLevel = 0;
                    this.maxTitleLevel = 0;
                    var parObj = {
                        eid: item.id + "-" + 1, 
                        id: item.id,
                        depid: parseInt(item.depid),
                        hasson: item.hasson,
                        levelnum: item.levelnum,
                        maxpoint: item.maxpoint,
                        minpoint: item.minpoint,
                        modelid: item.modelid,
                        parentid: item.parentid,
                        selfeval: item.selfeval,
                        targetid: item.targetid,
                        name: item.name,
                        targettype: item.targettype,
                        evaltype: item.evaltype,
                        remark: item.remark,
                        basepoint: item.basepoint,
                        type: 1,
                    };
                    this.expandRowKeys.push(parObj.eid);
                    if(item.hasson == 1){
                        var obj = that.getTreeChildrenData(parObj, dataList);
                        if(obj.children && obj.children.length > 0){
                            parObj.children = obj.children; 
                            parObj.basepoint = obj.basepoint;   
                        } else {
                            parObj.children = []; 
                        }
                    } else {
                        var titleListTmp = []; 
                        this.titleList.filter(title => {
                            if(title.detailid == parObj.id) {
                                titleListTmp = title.titlelist;
                            }
                        });
                        parObj.children = this.getTreeTitleData(parObj, titleListTmp);
                    }
                    newData.push(parObj);
                    if(max < this.maxLevel + this.maxTitleLevel){
                        max = this.maxLevel + this.maxTitleLevel;
                    }
                });
                this.tableWidth = 55 * max;
                return newData;
            },

            // 封装递归树
            getTreeChildrenData(parent, dataList){
                var basepoint = 0;
                var newData = [];
                var that = this;
                dataList.forEach(item =>{
                    if(item.parentid != parent.id){
                        return true;
                    }
                    if(item.levelnum > this.maxLevel){
                        this.maxLevel = item.levelnum;
                    }
                    var child = {
                        eid: item.id + "-" + 1, 
                        id: item.id + "",
                        depid: parseInt(parent.depid ? parent.depid: item.depid),
                        hasson: item.hasson,
                        levelnum: item.levelnum,
                        maxpoint: parent.maxpoint ? parent.maxpoint: item.maxpoint,
                        minpoint: parent.minpoint ? parent.minpoint: item.minpoint,
                        modelid: item.modelid,
                        parentid: item.parentid,
                        selfeval: parent.selfeval ? parent.selfeval: item.selfeval,
                        targetid: item.targetid,
                        name: item.name,
                        targettype: parent.targettype ? parent.targettype: item.targettype,
                        evaltype: parent.evaltype ? parent.evaltype: item.evaltype,
                        basepoint: item.basepoint,
                        remark: item.remark,
                        type: 1
                    }
                    this.expandRowKeys.push(child.eid);
                    if(item.hasson == 1){
                        var obj = that.getTreeChildrenData(child, dataList)
                        child.children = obj.children;
                        child.basepoint = obj.basepoint;
                        newData.push(child);
                    } else {
                        var titleListTmp = []; 
                        this.titleList.filter(title => {
                            if(title.detailid == child.id) {
                                titleListTmp = title.titlelist;
                            }
                        });
                        child.children = this.getTreeTitleData(child, titleListTmp);
                        newData.push(child);
                    }
                    basepoint += parseInt(child.basepoint);
                })
                return {
                    children: newData,
                    basepoint: basepoint
                };
            },

            // 封装标题递归树
            getTreeTitleData(target, dataList){
                var that = this;
                
                var newData = [];
                let parentArr = dataList.filter(item => !item.parentid);
                parentArr.forEach(item =>{
                    var parObj = {
                        eid: item.id + "-" + 2, 
                        id: item.id,
                        detailid: target.id,
                        name: item.name,
                        hasson: item.hasson,
                        levelnum: item.levelnum,
                        parentid: item.parentid,
                        targetid: target.targetid,
                        targettype: target.targettype,
                        maxpoint: target.maxpoint ? target.maxpoint: item.maxpoint,
                        minpoint: target.minpoint ? target.minpoint: item.minpoint,
                        evaltype: target.evaltype ? target.evaltype: item.evaltype,
                        type: 2,
                    };
                    this.expandRowKeys.push(parObj.eid);
                    if(item.hasson == 1){
                        var obj = that.getTreeChildrenTitleData(target, parObj, dataList);
                        parObj.children = obj.children;
                        parObj.basepoint = obj.basepoint;
                    } else {
                        parObj.children = [];
                        parObj.basepoint = item.basepoint;
                    }
                    newData.push(parObj);
                });
                return newData;
            },

            // 封装递归树
            getTreeChildrenTitleData(target, parent, dataList){
                var basepoint = 0;
                var newData = [];
                var that = this;
                dataList.forEach(item =>{
                    if(item.parentid != parent.id ){
                        return true;
                    }
                    if(item.levelnum > this.maxTitleLevel){
                        this.maxTitleLevel = item.levelnum;
                    }
                    var child = {
                        eid: item.id + "-" + 2, 
                        id: item.id + "",
                        detailid: parent.id,
                        name: item.name,
                        hasson: item.hasson,
                        levelnum: item.levelnum,
                        parentid: item.parentid,
                        targetid: parent.targetid,
                        targettype: parent.targettype,
                        maxpoint: parent.maxpoint ? parent.maxpoint: item.maxpoint,
                        minpoint: parent.minpoint ? parent.minpoint: item.minpoint,
                        evaltype: parent.evaltype ? parent.evaltype: item.evaltype,
                        basepoint: item.basepoint,
                        type: 2
                    }
                    this.expandRowKeys.push(child.eid);
                    if(item.hasson == 1){
                        var obj = that.getTreeChildrenTitleData(detailid, targetid, child, dataList)
                        child.children = obj.children;
                        child.basepoint = obj.basepoint;
                        newData.push(child);
                    } else {
                        newData.push(child);
                    }
                    basepoint += parseInt(child.basepoint);
                })
                return {
                    children: newData,
                    basepoint: basepoint
                };
            },
        },
    }
</script>
<style scoped lang="scss">
    .web-head {
        padding: 15px 25px 5px 25px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        .web-head-name {
            text-align: center;
            position: relative;
            margin-right: 240px;
            font-family: 'Nunito Sans', sans-serif;
            font-weight: 400;
            font-style: normal;
            font-size: 16px;
            color: #333333;
        }
    }

    .web-head-back-bread .el-breadcrumb {
        display: inline-block;
        vertical-align: middle;
        margin-left: 10px;
    }

    .web-body {
        padding: 15px 5px 15px 25px;
        width: 100%;
        .web-body-left,.web-body-right {
            .web-body-content{
                background: white;
                height: 73vh;
                border-radius: 10px;
                padding: 10px;
                .web-body-content-title {
                    padding: 10px;
                    height: 40px;
                    .web-body-content-title-text {
                        font-size: 20px;
                        font-weight: 500;
                    }
                    .web-body-content-title2 {
                        img {
                            margin-left: 5px;
                            display: inline-block;
                            width: 25px;
                            height: 15px;
                            vertical-align: middle;
                        }
                    }
                     .web-body-content-title-fun {
                        float: right;
                    }
                    .web-body-content-title-left{
                        font-size: 13px;
                        font-weight: 600;
                        padding: 5px;
                        border-radius: 3px;
                        float: right;
                        margin-left: 5px;
                    }
                    .web-body-content-title-left-selected {
                        color: #FFFFFF;
                        background: #5d78ff;
                    }
                    
                }

            }
        }
        .web-body-left {
            .web-body-content{
                height: 56.5vh !important;
            }
            .web-body-count{
                background: white;
                height: 15vh;
                border-radius: 10px;
                padding: 10px;
                margin-bottom: 10px;
                .web-body-count-title {
                    padding: 10px;
                    height: 40px;
                    .web-body-count-title-text {
                        font-size: 20px;
                        font-weight: 500;
                    }
                }
                .web-body-count-num {
                   .web-body-count-num-item{
                        text-align: center;
                        border-right: 1px solid #CCCCCC;
                        font-size: 13px;
                        .web-body-count-num-item-point {
                            padding: 5px;
                            // height: 30px;
                            display: block;
                            font-weight: 700;
                            font-size: 18px;
                            margin-bottom: 5px;
                            span {
                                margin-left: 2px;
                                display: inline-block;
                                font-size: 12px;
                                font-weight: 500;
                            }
                        }
                        .sum-point {
                            color:rgb(255, 0, 102);
                        }
                   }
                }
                .web-body-count-num :nth-last-child(){
                    border: none !important;
                }
                
            }
        }
    }

    .web-head-search-form {
        padding: 5px 15px 0px 25px;
        border-radius: 10px;
    }
    
    .web-head-content {
        background: #FFFFFF;
        padding: 10px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        -webkit-justify-content: space-between;
        justify-content: space-between;
       
    }
    .web-head-content .el-form-item {
        margin: 0;
    }

    .web-head-left {
        flex: 1;
    }

    .web-head-right {
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        padding-right: 15px;
        width: 145px;
        display: flex;
    }

    .web-head-right-clean {
        flex: 1;
    }

    .web-head-right-search {
        padding-left: 10px;
        flex: 1;
    }

    .dialog-center-title {
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        margin: 10px 0;
        display: block;
    }
    .dialog-content-center {
        background: rgba(240, 242, 245, 1);
        padding: 20px;
        border-radius: 10px;
        .dialog-center-content {
            width: 100%;
            .dialog-center-content-item{
                margin-top: 15px;
                div {
                    span {
                        display: inline-block;
                    }
                }
                div :nth-child(1){
                    color: #646C9A;
                    width: 80px;
                    display: inline-block;
                    vertical-align: top;
                }
                div :nth-child(2){
                    color: #606266;
                }
            }
            .dialog-center-content-user,.dialog-center-content-point {
                margin-top: 15px;
                width: 100%;
                display: inline-flex;
            }
            .dialog-center-content-user :nth-child(1),.dialog-center-content-point :nth-child(1){
                width: 50%;
            }
            .dialog-center-content-remark {
                div :nth-child(2){
                    width: 80%;
                }
            }
        }
    }

    .hmddialog-bottom {
        text-align: center;
        margin-top: 19px;
    }

    .pager-zj {
        text-align: center;
    }

    .el-table .cell{
        white-space: nowrap;
        font-size: 12px;
    }
    ::v-deep .fun-hover:hover{
        cursor: pointer;
    }

    ::v-deep .el-table__empty-block {
        width: 100%;
        min-width: 100%;
        max-width: 100%;
    }

    /* 有子节点且未展开 */
    .el-table/deep/ .el-icon-arrow-right:before {
        // background: url(../../../assets/u5693.png) no-repeat 0 3px;
        // content: "" !important;
        display: block;
        height: 16px;
        width: 16px;
        font-size: 12px;
        background-size: 16px;
    }

    .el-form/deep/ .el-form-item__label {
        font-size: 12px;
        color: #646C9A;
        background-color: rgba(255, 255, 255, 0);
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
    }

    .khpzx-dialog/deep/ .el-dialog__title {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 12px;
        color: #333333;
    }

    .el-table--border::after, .el-table--group::after, .el-table::before {
        background: none;
    }
    
    .el-table {
        font-size: 12px;
    }

    ::v-deep .cell {
        line-height: 15px;
        height: 15px;
    }

    ::v-deep .gg-disabled {
        background: rgb(236, 236, 236);
    }
    ::v-deep .gg-disabled:hover > td{
        background-color: transparent !important;
    }
</style>

