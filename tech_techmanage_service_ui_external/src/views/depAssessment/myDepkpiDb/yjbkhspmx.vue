<!-- 指标配置 -->
<template>
    <div class="web-container">
        <div class="web-head">
            <div class="web-head-back-bread">
                <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
            </div>
            <span class="web-head-name">
                <span>当前审批部门：</span>
                {{this.$route.query.name}}
            </span>
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
                <div class="web-body-content">
                    <div class="web-body-content-title">
                        <el-row>
                            <span class="web-body-content-title-text">考核目录</span>
                        </el-row>
                    </div>
                    <el-table :data="tableData"  row-key="eid" height="490" :tree-props="{children: 'children'}" 
                    @row-click="handleTitleSelectionChange" :expand-row-keys="expandRowKeys">
                        <el-table-column label="考核项" prop="name" min-width="100" :width="tableWidth">
                            <template slot-scope="scope">
                                <span v-if="scope.row.type == 2">📂</span>
                                <span v-if="scope.row.type == 1">📚</span>
                                {{scope.row.name}}
                            </template>
                        </el-table-column>
                        <el-table-column label="基准分" width="30" prop="basepoint" align="center"></el-table-column>
                    </el-table>
                </div>
            </el-col>
            <el-col class="web-body-right" :span="16">
                <div class="web-body-content">
                    <div class="web-body-content-title">
                        <el-row>
                            <span class="web-body-content-title-text">考核内容</span>
                            <span class="web-body-content-title-text2 fun-hover">
                                <img id="u2813_img" class="img" src="../../../assets/u2813.png" @click="showAllContent">
                                显示所有考核项
                            </span>
                            <button-orange class="web-body-content-title-fun" buttonName="审批通过" size="mini" :fns="spCommit"></button-orange>
                            <el-button type="default" class="btn web-body-content-title-fun" size="mini" @click="spBack">退 回</el-button>
                        </el-row>
                    </div>
                    <el-table :data="contentData"  height="490" width="600">
                        <el-table-column label="考核项或标题" width="150" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span v-if="scope.row.titlename">{{scope.row.titlename}}</span>
                                <span v-else>{{scope.row.targetname}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="考核内容" prop="content" :show-overflow-tooltip="true" width="150"></el-table-column>
                        <el-table-column label="基准分" width="60" fixed="right" prop="basepoint" align="center"></el-table-column>
                        <el-table-column label="评分标准" width="180"  prop="standard" align="center" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="时间安排" width="140" align="center" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span v-if="scope.row.startdate">{{scope.row.startdate.substr(5,10).replace('-', "月") + "日"}}</span>
                                <span>~</span>
                                <span v-if="scope.row.enddate">{{scope.row.enddate.substr(5,10).replace('-', "月") + "日"}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="80" fixed="right" align="center">
                            <template slot-scope="scope">
                                <span @click="updateContent(scope.row)" class="fun-hover" style="color:#409EFF">修改</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
        </el-row>

        <!-- 添加/修改 -->
        <el-dialog title="修改考核内容" :visible.sync="ifShowTjContentDialog" @close="addClose" width="40%">
            <div class="xgContent">
                <el-form class="dialog-form" :model="updateContentData" ref="formAddContent" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateContentData.id"></el-input></el-form-item>
                    <el-form-item label="考核项：" prop="detail">
                        <el-input v-model="updateContentData.targetname" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="考核标题：" prop="title">
                        <el-input v-model="updateContentData.titlename" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="考核内容：" prop="content" :rules="[{ required: true, message: '考核内容不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateContentData.content"></el-input>
                    </el-form-item>
                    <el-form-item label="基准分：" prop="basepoint">
                        <el-input v-model="updateContentData.basepoint" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="评分标准：" prop="standard" :rules="[{ required: true, message: '评分标准不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateContentData.standard"></el-input>
                    </el-form-item>
                    <el-form-item label="时间范围：" prop="datepicker">
                        <el-date-picker v-model="updateContentData.datepicker" type="daterange" value-format="yyyy-MM-dd"
                            range-separator="至" start-placeholde="开始日期" end-placeholde="结束日期" ></el-date-picker>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelContent"></button-common>
                    <button-orange buttonName="保 存" size="mini" :fns="sureAddContent"></button-orange>
                </div>
            </div>
        </el-dialog>

        <!-- 添加/修改 -->
        <el-dialog title="退回意见" :visible.sync="ifShowBackDialog" @close="addClose" width="40%">
            <div class="xgContent">
                <el-form class="dialog-form" :model="updateBacakData" ref="formBack" label-width="80px" label-position="left">
                    <el-form-item label="意见：" prop="suggest" :rules="[{ required: true, message: '退回意见不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateBacakData.suggest" type="textarea" maxlength="500" :autosize="{minRows: 3, maxRows: 5}"></el-input>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelBack"></button-common>
                    <button-orange buttonName="保 存" size="mini" :fns="sureBack"></button-orange>
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
    import { getCheckTree, spCommit, suggest } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    import { queryTitleTreeData } from "@/api/zzkhApi/title";
    import { queryContentList, updateZdContent } from "@/api/zzkhApi/content";
    export default {
        name: 'khspxm',
        components: { buttonOrange, pager, buttonCommon, cleanBtn },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0,
                ifShowTjContentDialog: false, //是否显示添加弹窗
                ifShowBackDialog: false,
                tableWidth: "",
                maxLevel: 1,
                maxTitleLevel: 1,
                tableData: [],
                contentData: [],
                titleList: [], // 考核标题数据
                targetList: [], // 考核项下拉数据
                depkpiEvalSet: [], // 考核评级档位
                targettype: 2,
                paramsForm: {
                    content: null,
                    parentDetailid: null,
                    parentTitleid: null
                },
                updateContentData: {
                    id: null,
                    title: null,
                    targetname: "",
                    titlename: "",
                    detail: null,
                    targetid: null,
                    depid: null,
                    content: null,
                    basepoint: null,
                    standard: null,
                    datepicker:[],
                    evaltype: null,
                    maxpoint: null,
                    minpoint: null,
                }, //添加数据
                updateBacakData: {
                    suggest: "",
                },
                expandRowKeys: [], // 需要展开的树
            }
        },
        beforeCreate(){
            if(!this.$route.query.sign || !this.$route.query.id || !this.$route.query.depid){
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
            this.submitBtn();  
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.push({path: "/yjbkhsp", query: {sign: this.$route.query.sign, tyear: this.$route.query.tyear}});
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
                        this.$message.error(res[0].data.message);
                        return false;
                    }
                     if (res[1].data.status != 200) {
                        this.$message.error(res[1].data.message);
                        return false;
                    }
                    this.titleList = res[1].data.results;
                    this.targetList = res[0].data.results.filter(item => this.$route.query.id == item.id);
                    that.tableData = that.getTreeData(this.targetList[0]);
                });
            },

            // 部门考核项查询
            getCheckTree(){
                var val = {};
                val.targettype = this.targettype;
                val.depid = this.$route.query.depid;
                val.id = this.$route.query.id;
                val.sign = this.$route.query.sign
                val.dbuser = this.userId;
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
                val.targettype = this.targettype;
                val.depid = this.$route.query.depid;
                val.detailid = this.$route.query.id;
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

            // 显示所有内容
            showAllContent(){
                this.queryContentList({});
            },

            // 考核内容查询
            queryContentList(val){
                if(!val){
                    val = {};
                }
                val.tyear = this.$route.query.tyear;
                val.targettype = this.targettype;
                val.depid = this.$route.query.depid;
                val.sign = this.$route.query.sign;
                val.detailid = this.$route.query.id;
                if(this.paramsForm.content && this.paramsForm.content != ""){
                    val.content = this.paramsForm.content;
                }
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

            // 修改
            updateContent(val) {
                this.updateContentData = JSON.parse(JSON.stringify(val));
                if(val.startdate){
                    this.$set(this.updateContentData, "datepicker", [val.startdate, val.enddate]);
                }
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
                    let params = {};
                    params.targettype = this.targettype;
                    params.sign = this.$route.query.sign;
                    params.depid = this.$route.query.depid;
                    params.targetid = this.updateContentData.targetid;
                    params.titleid = this.updateContentData.titleid;
                    params.content = this.updateContentData.content;
                    params.basepoint = Number(this.updateContentData.basepoint);
                    params.standard = this.updateContentData.standard;
                    if(this.updateContentData.datepicker){
                        params.startdate = this.updateContentData.datepicker[0];
                        params.enddate = this.updateContentData.datepicker[1];
                    }
                    params.detailid = this.$route.query.id;
                    params.tyear =  this.$route.query.tyear;
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
                });
            },

            cancelContent() {
                this.ifShowTjContentDialog = false;
                this.$refs["formAddContent"].resetFields();
            },

            spBack(val) {
                this.updateBacakData.suggest = "";
                this.ifShowBackDialog = true;
            },

            cancelBack() {
                this.ifShowBackDialog = false;
                this.$refs["formBack"].resetFields();
            },

            // 保存确定
            sureBack(){
                this.$refs["formBack"].validate(valid => {
                    if (!valid) {
                        this.$message.error('请填写必填项');
                        return false;
                    } 
                    const loading = common.loading(this);
                    let params = {};
                    params.id = this.$route.query.id;
                    params.targettype = this.targettype;
                    params.sign = this.$route.query.sign;
                    params.depid = this.$route.query.depid;
                    params.tyear = this.$route.query.tyear;
                    params.suggest = this.updateBacakData.suggest;
                    params = JSON.parse(JSON.stringify(params));
                    let data = common.paramsFormat(params);
                    suggest(data).then(res => {
                        loading.close();
                        console.log("退回确定", res.data);
                        if (res.data.status == 200) {
                            this.$message.success('退回成功');
                            this.goBack();
                        } else {
                            this.$message.error(res.data.message);
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                });
            },

            // 添加弹窗关闭回调
            addClose() {
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

            // 审批提交
            spCommit(){
                this.$confirm('确认审批通过吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let data = common.paramsFormat({
                        sign: 3,
                        idlist: this.$route.query.id,
                        tyear: this.$route.query.tyear
                    });
                    spCommit(data).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('审批通过');
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
            getTreeData(item){
                var max = 0;
                var that = this;
                var newData = [];
                this.maxLevel = 0;
                this.maxTitleLevel = 0;
                var parObj = {
                    eid: item.id + "-" + 1, 
                    id: item.id,
                    depid: parseInt(item.depid),
                    hasson: item.hasson,
                    levelnum: item.levelnum,
                    parentid: item.parentid,
                    targetid: item.targetid,
                    name: item.name,
                    targettype: item.targettype,
                    remark: item.remark,
                    basepoint: item.basepoint,
                    type: 1,
                };
                this.expandRowKeys.push(parObj.eid);
                var titleListTmp = []; 
                this.titleList.filter(title => {
                    if(title.detailid == parObj.id) {
                        titleListTmp = title.titlelist;
                    }
                });
                parObj.children = this.getTreeTitleData(parObj, titleListTmp);
                newData.push(parObj);
                if(max < this.maxLevel + this.maxTitleLevel){
                    max = this.maxLevel + this.maxTitleLevel;
                }
                this.tableWidth = 55 * max;
                console.log(newData)
                return newData;
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
                        type: 2,
                    };
                    this.expandRowKeys.push(parObj.eid);
                    if(item.hasson == 1){
                        var obj = that.getTreeChildrenTitleData(target.id, target.detailid, parObj, dataList);
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
                        name: item.name,
                        hasson: item.hasson,
                        levelnum: item.levelnum,
                        parentid: item.parentid,
                        targetid: targetid,
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
            margin-right: 50%;
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
                        margin-left: 10px;
                    }
                    
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
    }

    ::v-deep .fun-hover:hover{
        cursor: pointer;
    }
</style>

