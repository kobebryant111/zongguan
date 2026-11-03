<!-- 指标配置 -->
<template>
    <div class="web-container">
        <div class="web-head">
            <div class="web-head-back-bread">
                <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
            </div>
            <span class="web-head-name">
                <span>当前制定部门：</span>
                {{this.$route.query.name}}
            </span>
            <div>
                <el-button type="warning" size="small" @click="commit">提 交</el-button>
                <el-button type="primary" size="small" @click="addContent"> 新 增 考 核 内 容</el-button>
                <el-button type="default" size="small" @click="exportEx">导 出</el-button>
            </div>
        </div>
        <el-row class="web-body" :gutter="10">
            <el-col class="web-body-left" :span="8">
                <div class="web-body-content">
                    <el-table :data="tableData" row-key="eid" height="585" :tree-props="{children: 'children'}" 
                    @row-click="handleTitleSelectionChange" :expand-row-keys="expandRowKeys">
                        <el-table-column label="考核项" prop="name" :width="tableWidth">
                            <template slot-scope="scope">
                                <template v-if="scope.row.type == 1 && scope.row.levelnum == 1 && scope.row.children.length == 0">
                                    &emsp;&nbsp;&nbsp;&nbsp;
                                </template>
                                <span v-if="scope.row.type == 2">📂&nbsp;&nbsp;{{scope.row.name}}</span>
                                <span v-if="scope.row.type == 1" style="font-weight:700;color: #333333;">📚&nbsp;&nbsp;{{scope.row.name}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="基准分" width="60" prop="basepoint" align="center"></el-table-column>
                        <el-table-column label="操作" width="90" align="center">
                            <template slot-scope="scope">
                                <i class="el-icon-plus fun-icon" v-if="(scope.row.type == 1 && scope.row.hasson == 0) || scope.row.type == 2" 
                                    @click="addTitle(scope.row)" style="color:#409EFF" title="新增子标题"></i>
                                    <!-- <br v-if="scope.row.type == 2"/> -->
                                    <i class="el-icon-edit fun-icon" v-if="scope.row.type == 2 && scope.row.hasson == 0" @click="updateTitle(scope.row)" style="color:#409EFF" title="修改"></i>
                                    <!-- <br v-if="scope.row.type == 2"/> -->
                                    <i class="el-icon-delete fun-icon" v-if="scope.row.type == 2" @click="deleteTitle(scope.row)"  style="color:#FF4044" title="删除"></i>

                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
            <el-col class="web-body-right" :span="16">
                <div v-if="this.$route.query.suggest" class="web-body-head">
                    <div class="web-body-head-title">退回意见</div>
                    <div class="web-body-head-content">{{this.$route.query.suggest}}</div>
                </div>
                <div :class="this.$route.query.suggest ? 'web-body-content-back' : 'web-body-content'">
                   <el-table :data="contentData"  height="465" width="600">
                       <el-table-column label="考核项" prop="targetname" width="150" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="考核标题" prop="titlename" width="150" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="考核内容" prop="content" width="150" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="评分标准" width="100"  prop="standard" align="center" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="时间安排" width="140" align="center" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span v-if="scope.row.startdate">{{scope.row.startdate.substr(5,10).replace('-', "月") + "日"}}</span>
                                <span>~</span>
                                <span v-if="scope.row.enddate">{{scope.row.enddate.substr(5,10).replace('-', "月") + "日"}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="基准分" width="60"  prop="basepoint" align="center"></el-table-column>
                        <el-table-column label="操作" width="80" align="center">
                            <template slot-scope="scope">
                                <i class="el-icon-edit fun-icon" @click="updateContent(scope.row)" style="color:#409EFF" title="修改"></i>
                                <i class="el-icon-delete fun-icon" @click="deleteContent(scope.row)"  style="color:#FF4044" title="删除"></i>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
        </el-row>

        <!-- 添加修改标题 -->
        <el-dialog title="配置考核标题" :visible.sync="ifShowTjTitleDialog" @close="addClose" width="40%">
            <div class="xgContent">
                <el-form class="dialog-form" :model="updateTitleData" ref="formAddTitle" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateTitleData.id"></el-input></el-form-item>
                    <el-form-item label="标题名称：" prop="name" :rules="[{ required: true, message: '标题名称不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateTitleData.name"></el-input>
                    </el-form-item>
                    <el-form-item label="基准分：" prop="basepoint"
                        :rules="[{ required: true, type:'number', validator: isNumber, trigger: 'blur'}]">
                        <el-input v-model="updateTitleData.basepoint" :disabled="updateTitleData.evaltype == 3"></el-input>
                    </el-form-item>
                    <el-form-item v-if="this.$route.query.sign != 3" label="*以下为非必填项，填写后将会应用到所有子项" label-width="200"></el-form-item>
                    <el-form-item v-if="this.$route.query.sign != 3" label="评分方式：" prop="evaltype">
                        <el-radio-group v-model="updateTitleData.evaltype" :disabled="titleDisableFlag">
                            <el-radio>混合</el-radio>
                            <el-radio :label="1">档位评分</el-radio>
                            <el-radio :label="2">任意分值</el-radio>
                            <el-radio :label="3">其他（加分/倒扣分）</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-table v-if="updateTitleData.evaltype == 1" :data="depkpiEvalSet" border>
                        <el-radio>空</el-radio>
                        <el-table-column type="index" label="序号" width="55"></el-table-column>
                        <el-table-column label="分档描述" width="260" prop="dictname"></el-table-column>
                        <el-table-column label="系数" width="260" prop="dictid"></el-table-column>
                    </el-table>
                    <el-form-item v-if="updateTitleData.evaltype == 3" label="额外区间分：">
                        <el-col :span="4">
                            <el-form-item prop="minpoint" :rules="[{ required: true, validator: minValidator, trigger: 'blur'}]">
                                <el-input v-model="updateTitleData.minpoint" :disabled="titleDisableFlag"></el-input>
                            </el-form-item> 
                        </el-col>
                        <el-col class="line" :span="2" :offset="2">-</el-col>
                        <el-col :span="4">
                            <el-form-item prop="maxpoint"  :rules="[{ required: true, validator: maxValidator, trigger: 'blur'}]">
                                <el-input v-model="updateTitleData.maxpoint" :disabled="titleDisableFlag"></el-input>
                            </el-form-item> 
                        </el-col>
                    </el-form-item> 
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelTitle"></button-common>
                    <button-orange buttonName="保 存" size="mini" :fns="sureAddTitle"></button-orange>
                </div>
            </div>
        </el-dialog>

        <!-- 添加/修改 -->
        <el-dialog title="配置考核内容" :visible.sync="ifShowTjContentDialog" @close="addClose" width="40%" style="left: 35px;">
            <div class="xgContent">
                <el-form class="dialog-form" :model="updateContentData" ref="formAddContent" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateContentData.id"></el-input></el-form-item>
                    <el-form-item label="考核项：" prop="detail" :rules="[{ required: true, message: '请选择考核项', trigger: 'change'}]">
                        <el-select v-model="updateContentData.detail" value-key="id" filterable clearable @change="changeTarget">
                            <el-option v-for="item in noSonTargetList" :key="item.id" :label="item.name" :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="考核标题：" prop="title">
                        <el-select v-model="updateContentData.title" value-key="id" filterable clearable @change="changeTitle">
                            <el-option v-for="item in noSonTitleList" :key="item.id" :label="item.name" :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="考核内容：" prop="content" :rules="[{ required: true, message: '考核内容不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateContentData.content"></el-input>
                    </el-form-item>
                    <el-form-item label="基准分：" prop="basepoint"
                        :rules="[{ required: true, type:'number', validator: isNumber, trigger: 'blur'}]">
                        <el-input v-model="updateContentData.basepoint" :disabled="updateContentData.evaltype == 3"></el-input>
                    </el-form-item>
                    <el-form-item label="评分标准：" prop="standard" :rules="[{ required: true, message: '评分标准不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateContentData.standard"></el-input>
                    </el-form-item>
                    <el-form-item label="时间范围：" prop="datepicker">
                        <el-date-picker v-model="updateContentData.datepicker" type="daterange" value-format="yyyy-MM-dd"
                            range-separator="至" start-placeholde="开始日期" end-placeholde="结束日期" ></el-date-picker>
                    </el-form-item>
                     <el-form-item v-if="this.$route.query.sign != 3" label="评分方式：" prop="evaltype" 
                        :rules="[{ required: true, message: '评分方式不能为空', trigger: 'blur'}]">
                        <el-radio-group v-model="updateContentData.evaltype" :disabled="contentDisableFlag" @change="changeEvalType">
                            <!-- <el-radio>混合</el-radio> -->
                            <el-radio :label="1">档位评分</el-radio>
                            <el-radio :label="2">任意分值</el-radio>
                            <el-radio :label="3">其他（加分/倒扣分）</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-table v-if="updateContentData.evaltype == 1" :data="depkpiEvalSet" border>
                        <el-radio>空</el-radio>
                        <el-table-column type="index" label="序号" width="55"></el-table-column>
                        <el-table-column label="分档描述" width="260" prop="dictname"></el-table-column>
                        <el-table-column label="系数" width="260" prop="dictid"></el-table-column>
                    </el-table>
                    <el-form-item v-if="updateContentData.evaltype == 3" label="额外区间分：">
                        <el-col :span="4">
                            <el-form-item prop="minpoint" 
                                :rules="[{ required: true, validator: minValidator, trigger: 'blur'}]">
                                <el-input type="number" v-model="updateContentData.minpoint" :disabled="contentDisableFlag"></el-input>
                            </el-form-item> 
                        </el-col>
                        <el-col class="line" :span="2" :offset="2">-</el-col>
                        <el-col :span="4">
                            <el-form-item prop="maxpoint" 
                            :rules="[{ required: true, validator: maxValidator, trigger: 'blur'}]">
                                <el-input type="number" v-model="updateContentData.maxpoint" :disabled="contentDisableFlag"></el-input>
                            </el-form-item> 
                        </el-col>
                    </el-form-item> 

                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelContent"></button-common>
                    <button-orange buttonName="保 存" size="mini" :fns="sureAddContent"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import { mapGetters } from "vuex";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { getEosDictEntrys } from "@/api/hykh/pjsz";
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { getCheckTree, commitGgzbzd, commitzbzd } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    import { queryTitleTreeData, queryTitleList, addTitle, updateTitle, deleteTitle } from "@/api/zzkhApi/title";
    import { queryContentList, addContent, updateZdContent, deleteContent, exportContent } from "@/api/zzkhApi/content";
    export default {
        name: 'khzdmx',
        components: { buttonOrange, pager, buttonCommon },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0,
                ifShowTjTitleDialog: false, //是否显示添加弹窗
                ifShowTjContentDialog: false, //是否显示添加弹窗
                tableWidth: "",
                maxLevel: 1,
                maxTitleLevel: 1,
                tableData: [],
                contentData: [],
                centerData: [],
                depData: [],
                depDataAll: [],
                oriTargetData: [], // 原始指标数据
                oriTitleData: [], // 原始标题数据
                titleList: [], // 考核标题数据
                noSonTargetList: [], // 考核项下拉数据
                noSonTitleList: [], // 标题下拉数据
                allNoSonTitleList: [], // 标题下拉数据
                depkpiEvalSet: [], // 考核评级档位
                addFlag: 0,
                titleDisableFlag: false,
                contentDisableFlag: false,
                parentTitle: null,
                targettype: 2,
                updateTitleData: {
                    id: null,
                    name: null,
                    basepoint: null,
                    evaltype: null,
                    maxpoint: null,
                    minpoint: null,
                }, //添加数据
                updateContentData: {
                    id: null,
                    title: null,
                    detail: null,
                    targetid: null,
                    depid: null,
                    content: null,
                    basepoint: null,
                    standard: null,
                    datepicker:[],
                    selfeval: null,
                    evaltype: null,
                    maxpoint: null,
                    minpoint: null,
                }, //添加数据
                isNumber: (rule, value, callback) => {
                    if (!value && value != 0) {
                        callback(new Error('基准分不能为空'));
                    }
                    if (!/^\d+$/.test(value)) {
                        callback(new Error('请输入正整数,值为0-100'));
                    }
                    callback();
                },
                minValidator: (rule, value, callback) => {
                    if(!value && value !== 0){
                        callback(new Error('下限不能为空'));
                    }
                    if(value > 0 || value < -5){
                        callback(new Error('下限范围为-5至0'));
                    }
                    callback();
                },
                maxValidator: (rule, value, callback) => {
                    if(!value && value !== 0){
                        callback(new Error('上限不能为空'));
                    }
                    if(value < 0 || value > 5){
                        callback(new Error('上限范围为0至5'));
                    }
                    callback();
                },
                expandRowKeys: [], // 需要展开的树
            }
        },
        beforeCreate(){
            if(!this.$route.query.sign || !this.$route.query.id || !this.$route.query.name){
                this.$router.push({path:"/myDepKpiDb"});
            }
        },
        computed: {
            ...mapGetters([
                'userId'
            ])
        },
        created(){
        },
        mounted() {
            this.getEvalSet();
            this.submitBtn();
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.push({path: "/zdykhzd", query: {sign: this.$route.query.sign, tyear: this.$route.query.tyear}})
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

            // 查询
            submitBtn: function () {
                this.setLefttable();
                this.queryContentList({});
            },
            
            // 左边树
            setLefttable(){
                this.dealTreeData();
                this.queryTitleList();
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
                    console.log('部门已提交指标查询', res[0].data.results);
                    console.log('标题查询', res[1].data.results)
                    if (res[0].data.status != 200) {
                        that.$message.error(res[0].data.message);
                        return false;
                    }
                     if (res[1].data.status != 200) {
                        that.$message.error(res[1].data.message);
                        return false;
                    }
                    that.titleList = res[1].data.results;
                    that.oriTargetData = res[0].data.results;
                    that.tableData = that.getTreeData(res[0].data.results);
                });
            },

            // 部门考核项查询
            getCheckTree(){
                var val = {};
                val.targettype = this.targettype;
                val.depid = this.$route.query.id;
                val.tyear = this.$route.query.tyear
                val.dbuser = this.userId;
                val.status = 0;
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

            // 考核项下拉改变
            changeTarget(val){
                this.updateContentData.detailid = val.id;
                this.contentDisableFlag = false;
                this.updateContentData.selfeval = val.selfeval;
                if(val.evaltype){
                    this.updateContentData.evaltype = val.evaltype;
                    this.updateContentData.maxpoint = val.maxpoint;
                    this.updateContentData.minpoint = val.minpoint;
                    this.contentDisableFlag = true;
                    if(val.evaltype == 3){
                        this.updateContentData.basepoint = 0;
                    }
                } else if(this.updateContentData.evaltype) {

                } else {
                    this.updateContentData.evaltype = null;
                    this.updateContentData.maxpoint = null;
                    this.updateContentData.minpoint = null;
                }
                this.noSonTitleList = this.allNoSonTitleList.filter(item => item.detailid == val.id)
            },
            
            // 标题下拉改变
            changeTitle(val){
                this.updateContentData.targetid = val.targetid;
                this.updateContentData.depid = val.depid;
                this.contentDisableFlag = false;
                if(val == ""){
                    this.changeTarget(this.noSonTargetList.find(item => item.id == this.updateContentData.detailid));
                }else if(val.evaltype){
                    this.updateContentData.evaltype = null;
                    this.updateContentData.maxpoint = null;
                    this.updateContentData.minpoint = null;
                    this.updateContentData.evaltype = val.evaltype;
                    this.updateContentData.maxpoint = val.maxpoint;
                    this.updateContentData.minpoint = val.minpoint;
                    this.contentDisableFlag = true;
                    if(val.evaltype == 3){
                        this.updateContentData.basepoint = 0;
                    }
                }
                this.updateContentData = JSON.parse(JSON.stringify(this.updateContentData));
            },

            changeEvalType(val){
                if(val == 3) {
                    this.updateContentData.basepoint = 0;
                }
            },

            // 考核标题查询
            queryTitleTreeData(){
                var val = {};
                val.targettype = this.targettype;
                val.depid = this.$route.query.id;
                val.tyear = this.$route.query.tyear
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

            // 考核标题下拉内容
            queryTitleList(){
                var val = {};
                val.targettype = this.targettype;
                val.depid = this.$route.query.id;
                val.tyear = this.$route.query.tyear
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryTitleList(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.oriTitleData = res.data.results;
                        this.allNoSonTitleList = res.data.results.filter(item => item.hasson == 0);
                        this.noSonTitleList = [];
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 考核内容查询
            queryContentList(val){
                if(!val){
                    val = {};
                }
                val.sign = this.$route.query.sign;
                val.targettype = this.targettype;
                val.depid = this.$route.query.id;
                val.tyear = this.$route.query.tyear
                val.status = 0;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryContentList(data).then(res => {
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

            // 添加
            addTitle(row) {
                this.addFlag = 0;
                //新增一级标题
                if(row.type == 1){
                    // 取父级指标数据
                    this.updateTitleData.levelnum = 1;
                    this.updateTitleData.detailid = row.id;
                    this.updateTitleData.depid = row.depid;
                } else {
                    // 取父级标题数据
                    this.updateTitleData.levelnum = row.levelnum + 1;
                    this.updateTitleData.detailid = row.detailid;
                    this.updateTitleData.parentid = row.id;
                    this.updateTitleData.depid = row.depid;
                }
                this.titleDisableFlag = false;
                if(row.evaltype){
                    this.updateTitleData.evaltype = row.evaltype;
                    this.updateTitleData.maxpoint = null;
                    this.updateTitleData.minpoint = null;
                    if(row.evaltype == 3){
                        this.updateTitleData.basepoint = 0;
                        this.updateTitleData.maxpoint = row.maxpoint;
                        this.updateTitleData.minpoint = row.minpoint;
                    }
                    this.titleDisableFlag = true;
                }
                this.updateTitleData.targetid = row.targetid;
                this.ifShowTjTitleDialog = true;
            },
            // 添加
            addContent() {
                this.addFlag = 0;
                this.ifShowTjContentDialog= true;
            },
            // 修改
            updateTitle(val) {
                this.updateTitleData = JSON.parse(JSON.stringify(val));
                this.addFlag = 1;   
                this.ifShowTjTitleDialog = true;
                let parent;
                if(val.levelnum == 1){
                    parent = this.oriTargetData.find(item => item.id == val.detailid);
                } else {
                    parent = this.oriTitleData.find(item => item.id == val.parentid);
                }
                console.log('修改标题父级数据', parent)
                if(parent.evaltype){
                    this.updateTitleData.evaltype = parent.evaltype;
                    this.updateTitleData.maxpoint = null;
                    this.updateTitleData.minpoint = null;
                    this.titleDisableFlag = true;
                    if(parent.evaltype == 3){
                        this.updateTitleData.maxpoint = parent.maxpoint;
                        this.updateTitleData.minpoint = parent.minpoint;
                        this.updateTitleData.basepoint = 0;
                    }
                }
            },

            // 修改
            updateContent(val) {
                this.noSonTitleList = this.allNoSonTitleList.filter(item => item.detailid == val.detailid)
                this.updateContentData = JSON.parse(JSON.stringify(val));
                this.updateContentData.title = {id: val.titleid, detailid: val.detailid + ''};
                this.updateContentData.detail = {id: val.detailid + ''};
                if(val.startdate){
                    this.$set(this.updateContentData, "datepicker", [val.startdate, val.enddate]);
                    // this.updateContentData.datepicker = [val.startdate, val.enddate];
                }
                this.addFlag = 1;   
                this.ifShowTjContentDialog = true;

                this.contentDisableFlag = false;
                this.noSonTargetList.forEach(item => {
                    if(item.id == val.detailid && item.evaltype) {
                        this.contentDisableFlag = true;
                    }
                })
                this.noSonTitleList.forEach(item => {
                    if(item.id == val.titleid && item.evaltype) {
                        this.contentDisableFlag = true;
                    }
                })
            },

            // 添加标题确定
            sureAddTitle() {
                this.$refs["formAddTitle"].validate(valid => {
                    if (!valid) {
                        this.$message.error('请填写必填项');
                        return false;
                    } 
                    const loading = common.loading(this);
                    let params = {};
                    params.targettype = this.targettype;
                    params.tyear =  this.$route.query.tyear;
                    params.sign = this.$route.query.sign;
                    params.depid = this.$route.query.id;
                    params.detailid = this.updateTitleData.detailid;
                    params.targetid = this.updateTitleData.targetid;
                    params.levelnum = this.updateTitleData.levelnum;
                    params.name = this.updateTitleData.name;
                    params.basepoint = Number(this.updateTitleData.basepoint);
                    params.parentid = this.updateTitleData.parentid;
                    params.evaltype = this.updateTitleData.evaltype;
                    params.minpoint = this.updateTitleData.minpoint;
                    params.maxpoint = this.updateTitleData.maxpoint;
                    if(this.addFlag === 0){
                        params = JSON.parse(JSON.stringify(params));
                        let data = common.paramsFormat(params);
                        addTitle(data).then(res => {
                            loading.close();
                            console.log("添加确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('添加成功');
                                this.ifShowTjTitleDialog = false;
                                this.$refs["formAddTitle"].resetFields();
                                this.setLefttable();
                            } else {
                                this.$message.error(res.data.message);
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    }else{
                        params.id = this.updateTitleData.id;
                        params = JSON.parse(JSON.stringify(params));
                        let data = common.paramsFormat(params);
                        updateTitle(data).then(res => {
                            loading.close();
                            console.log("修改确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('修改成功');
                                this.ifShowTjTitleDialog = false;
                                this.setLefttable();
                                this.$refs["formAddTitle"].resetFields();
                            } else {
                                this.$message.error(res.data.message);
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    }
                });
            },

            // 添加内容确定
            sureAddContent(){
                this.$refs["formAddContent"].validate(valid => {
                    if (!valid) {
                        this.$message.error('请填写必填项');
                        return false;
                    } 
                    const loading = common.loading(this);
                    let params = {};
                    params.sign = this.$route.query.sign;
                    params.depid = this.$route.query.id;
                    params.targettype = this.targettype;
                    params.targetid = this.updateContentData.targetid;
                    params.titleid = this.updateContentData.title ? this.updateContentData.title.id : null;
                    params.content = this.updateContentData.content;
                    params.basepoint = Number(this.updateContentData.basepoint);
                    params.standard = this.updateContentData.standard;
                    if(this.updateContentData.datepicker){
                        params.startdate = this.updateContentData.datepicker[0];
                        params.enddate = this.updateContentData.datepicker[1];
                    }
                    params.detailid = this.updateContentData.detailid;
                    params.tyear =  this.$route.query.tyear;
                    params.selfeval = this.updateContentData.selfeval;
                    if(this.$route.query.sign == 3){
                        params.selfeval = 1;
                    }
                    params.evaltype = this.updateContentData.evaltype;
                    params.minpoint = this.updateContentData.minpoint;
                    params.maxpoint = this.updateContentData.maxpoint;
                    if(this.addFlag === 0){
                        params = JSON.parse(JSON.stringify(params));
                        let data = common.paramsFormat(params);
                        addContent(data).then(res => {
                            loading.close();
                            console.log("添加确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('添加成功');
                                this.ifShowTjContentDialog = false;
                                this.$refs["formAddContent"].resetFields();
                                this.queryContentList({});
                            } else {
                                this.$message.error(res.data.message);
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    }else{
                        params.id = this.updateContentData.id;
                        params = JSON.parse(JSON.stringify(params));
                        let data = common.paramsFormat(params);
                        updateZdContent(data).then(res => {
                            loading.close();
                            console.log("修改确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('修改成功');
                                this.ifShowTjContentDialog = false;
                                this.$refs["formAddContent"].resetFields();
                                this.queryContentList({});
                            } else {
                                this.$message.error(res.data.message);
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    }
                });
            },

            // 添加/修改取消
            cancelTitle() {
                this.ifShowTjTitleDialog = false;
                this.$refs["formAddTitle"].resetFields();
            },
            cancelContent() {
                this.ifShowTjContentDialog = false;
                this.$refs["formAddContent"].resetFields();
                this.noSonTitleList = [];
            },
            // 添加弹窗关闭回调
            addClose() {
               this.updateTitleData = {
                    id: null,
                    name: null,
                    basepoint: null,
                    evaltype: null,
                    maxpoint: null,
                    minpoint: null,
                };
                this.updateContentData = {
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
                this.noSonTitleList = [];
            },

            // 表格选中变化
            handleTitleSelectionChange(val) {
                var param = {};
                if(val.type == 1){// 考核项
                    param.parentDetailid = val.id;
                }else {// 标题
                    param.parentTitleid = val.id;
                }
                this.queryContentList(param);
            },

            // 删除标题
            deleteTitle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let jlIdStr = val.id.toString();
                    deleteTitle({ id: jlIdStr, targettype: this.targettype }).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.setLefttable();
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            },

            // 删除内容
            deleteContent(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let jlIdStr = val.id.toString();
                    deleteContent({ id: jlIdStr, targettype: this.targettype }).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.queryContentList();
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            },

            // 提交
            commit(){
                this.$confirm('确认提交吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    var data = { 
                        sign: this.$route.query.sign,
                        depid: this.$route.query.id,
                        tyear: this.$route.query.tyear,
                        targettype: this.targettype
                    };
                    data = common.paramsFormat(data);
                    commitzbzd(data).then(res => {
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

            // 导出
            exportEx() {
                const loading = common.loading(this);
                let val = {};
                val.sign = this.$route.query.sign;
                val.targettype = this.targettype;
                val.depid = this.$route.query.id;
                val.tyear = this.$route.query.tyear
                let params = common.paramsFormat(val);
                const self = this;
                exportContent(params).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "考核内容.xls");
                });
            },

            // 封装递归树
            getTreeData(dataList){
                var max = 0;
                var that = this;
                var newData = [];
                this.noSonTargetList = [];
                let parentArr = dataList.filter(item => !item.parentid);
                parentArr.forEach(item =>{
                    this.maxLevel = 0;
                    this.maxTitleLevel = 0;
                    var parObj = {
                        eid: item.id + "-" + 1, 
                        id: item.id + "",
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
                        this.noSonTargetList.push(parObj);
                        parObj.children = this.getTreeTitleData(parObj, titleListTmp);
                    }
                    newData.push(parObj);
                    if(max < this.maxLevel + this.maxTitleLevel){
                        max = this.maxLevel + this.maxTitleLevel;
                    }
                });
                this.tableWidth = 10 * max + 200;
                console.log(this.noSonTargetList)
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
                        id: item.id+ "",
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
                        this.noSonTargetList.push(child);
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
                        id: item.id + "",
                        detailid: target.id,
                        depid: item.depid,
                        name: item.name,
                        hasson: item.hasson,
                        levelnum: item.levelnum,
                        parentid: item.parentid,
                        targetid: target.targetid,
                        maxpoint: target.maxpoint ? target.maxpoint: item.maxpoint,
                        minpoint: target.minpoint ? target.minpoint: item.minpoint,
                        evaltype: target.evaltype ? target.evaltype: item.evaltype,
                        type: 2,
                    };
                    this.expandRowKeys.push(parObj.eid);
                    if(item.hasson == 1){
                        var obj = that.getTreeChildrenTitleData(target.id, target.targetid, parObj, dataList);
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
            getTreeChildrenTitleData(detailid, targetid, parent, dataList){
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
                        detailid: detailid,
                        depid: item.depid,
                        name: item.name,
                        hasson: item.hasson,
                        levelnum: item.levelnum,
                        parentid: item.parentid,
                        targetid: targetid,
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
            font-family: 'Nunito Sans', sans-serif;
            font-weight: 400;
            font-style: normal;
            font-size: 16px;
            color: #333333;
            flex: auto;
            margin-left: 15px;
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
        .web-body-left, .web-body-right {
            .web-body-content {
                background: white;
                height: 80vh;
                border-radius: 10px;
                padding: 10px;
            }
        }
        .web-body-right {
            .web-body-content-back {
                background: white;
                height: 64vh !important;
                border-radius: 10px;
                padding: 10px;
            }
            .web-body-head {
                background: white;
                height: 15vh;
                border-radius: 10px;
                padding: 10px;
                margin-bottom: 5px;
                .web-body-head-title {
                    height: 35%;
                    font-family: "微软雅黑 Bold", "微软雅黑 Regular", 微软雅黑, sans-serif;
                    font-weight: 700;
                    font-style: normal;
                    font-size: 18px;
                    color: rgb(65, 65, 65);
                }
                .web-body-head-content {
                    height: 65%;
                    overflow-y: auto;
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
        .fun-icon{
            margin: 0 3px;
            font-size: 14px;
        }
    }

    ::v-deep .fun-icon:hover{
        cursor: pointer;
    }
</style>
