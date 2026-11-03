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
                <div class="web-body-content">
                    <div class="web-body-content-title">
                        <el-row>
                            <span class="web-body-content-title-text">考核内容</span>
                            <span class="web-body-content-title-text2 fun-hover">
                                <img id="u2813_img" class="img" src="../../../assets/u2813.png" @click="showAllContent">
                                显示所有考核项
                            </span>
                            <button-orange class="web-body-content-title-fun" buttonName="提 交" size="mini" :fns="zpCommit"></button-orange>
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
                       <el-table-column label="自评结果" width="100"  prop="selfevalres" align="center" :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column label="操作" width="80" fixed="right" align="center">
                            <template slot-scope="scope">
                                <span @click="updateContent(scope.row)" class="fun-hover" style="color:#409EFF">自评</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
        </el-row>

        <!-- 添加/修改 -->
        <el-dialog title="自评" :visible.sync="ifShowTjContentDialog" @close="addClose" width="40%">
            <div class="xgContent">
                <el-form class="dialog-form" :model="updateContentData" ref="formAddContent" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateContentData.id"></el-input></el-form-item>
                    <el-form-item label="考核内容：" prop="content">
                        <el-input v-model="updateContentData.content" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="基准分：" prop="basepoint">
                        <el-input v-model="updateContentData.basepoint" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="评分标准：" prop="standard">
                        <el-input v-model="updateContentData.standard" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="时间范围：" prop="datepicker">
                        <el-date-picker v-model="updateContentData.datepicker" type="daterange" value-format="yyyy-MM-dd"
                            range-separator="至" start-placeholde="开始日期" end-placeholde="结束日期" disabled></el-date-picker>
                    </el-form-item>
                    <el-form-item label="完成情况自评：" prop="selfevalres" :rules="[{ required: true, message: '自评内容不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateContentData.selfevalres" type="textarea" maxlength="200" :autosize="{minRows: 3, maxRows: 5}"></el-input>
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
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import { mapGetters } from "vuex";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { getEosDictEntrys } from "@/api/hykh/pjsz";
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { getCheckTree, zpCommit } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    import { queryTitleTreeData } from "@/api/zzkhApi/title";
    import { queryContentList, selfeval } from "@/api/zzkhApi/content";
    export default {
        name: 'selfevaldetail',
        components: { buttonOrange, pager, buttonCommon, cleanBtn },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0,
                ifShowTjContentDialog: false, //是否显示添加弹窗
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
                updateContentData: {
                    id: null,
                    title: null,
                    targetid: null,
                    depid: null,
                    content: null,
                    basepoint: null,
                    standard: null,
                    datepicker:[],
                    selfevalres: null
                }, //添加数据
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
        mounted() {
            this.submitBtn();  
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.push({path: "/selfeval", query: {sign: this.$route.query.sign, tyear: this.$route.query.tyear}});
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
                    that.tableData = that.getTreeData(res[0].data.results);
                });
            },

            // 部门考核项查询
            getCheckTree(){
                var val = {};
                val.targettype = 2;
                val.depid = this.$route.query.id;
                val.sign = this.$route.query.sign
                val.dbuser = this.userId;
                val.status = 4
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
                val.targettype = 2;
                val.depid = this.$route.query.id;
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
                val.targettype = 2;
                val.depid = this.$route.query.id;
                val.selfeval = 1;
                val.sign = this.$route.query.sign;
                val.status = 4;
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
                    this.updateContentData.datepicker = [val.startdate, val.enddate]
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
                    params.sign = this.$route.query.sign;
                    params.id = this.updateContentData.id;
                    params.selfevalres = this.updateContentData.selfevalres;
                    let data = common.paramsFormat(params);
                    selfeval(data).then(res => {
                        loading.close();
                        console.log("修改确定", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('修改成功');
                            this.ifShowTjContentDialog = false;
                            this.$refs["formAddContent"].resetFields();
                            this.queryContentList(this.paramsForm);
                        } else {
                            this.$message.error('修改失败')
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

            // 自评提交
            zpCommit(){
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
                    zpCommit(data).then(res => {
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
                console.log(newData)
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
                        maxpoint: target.maxpoint ? target.maxpoint: item.maxpoint,
                        minpoint: target.minpoint ? target.minpoint: item.minpoint,
                        evaltype: target.evaltype ? target.evaltype: item.evaltype,
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

