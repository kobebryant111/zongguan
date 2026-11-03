<!-- 人力需求申请-添加编辑退回 -->
<template>
    <div class="rlxqContent">
        <div class="nav">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="1.5">
                            <p class="nav_name">发起需求</p>
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
        <div class="contentMain contentMain_rlxqAdd">
            <div class="contentTwo_title">
                <p class="contentTwo_title_p">需求方信息</p>
            </div>
            <div class="content">
                <el-form :model="ruleForm" ref="ruleForm" :rules="rules">
                    <div class="row_1">
                        <div>
                            <el-row type="flex" justify="space-between">
                                <el-col :span="7">
                                    <div>
                                        <p>需求发起人 *</p>
                                        <el-form-item prop="username">
                                            <el-cascader ref="cascader" :options="requirePeo_options" :props="requirePeoParams"
                                                v-model="ruleForm.username" :show-all-levels="false" filterable @change="peoChange"></el-cascader>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>部门</p>
                                        <el-form-item>
                                            <el-select v-model="dept" disabled>
                                                <el-option :value="dept">
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div>
                                        <p>提至供应商时间 *</p>
                                        <el-form-item prop="supplytime">
                                            <el-date-picker v-model="ruleForm.supplytime" type="date" placeholder="请选择日期"
                                                value-format="yyyy-MM-dd"></el-date-picker>
                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </div>

                    <!-- 面试信息 -->
                    <div class="row_require table_rlxqAdd">
                        <el-row>
                            <el-col :span="24">
                                <div class="contentTwo_title">
                                    <p class="row_2_detail">需求详情<span class="add_row" @click="eduExperAdd(1)">新增一行</span></p>
                                </div>
                                <div class="row_table">
                                    <el-table :data="ruleForm.requireDetail" style="width: 100%" @row-click="rowClick">
                                        <el-table-column type="index" label="序号" width="50">
                                        </el-table-column>
                                        <el-table-column prop="usertype" label="人员类型 *" width="140">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`requireDetail.${scope.$index}.usertype`" :rules="rules.usertype">
                                                    <el-select v-model="scope.row.usertype" placeholder="请选择">
                                                        <el-option v-for="item in peoForm_options" :key="item.dictid"
                                                            :label="item.dictname" :value="item.dictid"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="usernum" label="需求人数 *" width="120">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`requireDetail.${scope.$index}.usernum`" :rules="[{ required: true, message: '请选择需求人数', trigger: 'blur', type: 'number' }]">
                                                    <el-input-number v-model="scope.row.usernum" size="medium"
                                                        controls-position="right" :min="1" :max="50000"></el-input-number>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="isadvertise" label="联系招聘 *" width="110" :render-header="renderHeader">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`requireDetail.${scope.$index}.isadvertise`"
                                                    :rules="rules.isadvertise">
                                                    <el-select v-model="scope.row.isadvertise" placeholder="请选择"
                                                        @change="advertiseChoose(scope.row.isadvertise, scope.$index)">
                                                        <el-option v-for="item in connect_options" :key="item.dictid"
                                                            :label="item.dictname" :value="item.dictid"></el-option>
                                                    </el-select>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="addreason" label="增加原因 *" width="230">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`requireDetail.${scope.$index}.addreason`" :rules="rules.addreason">
                                                    <el-input v-model="scope.row.addreason" placeholder="请输入"></el-input>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="personrequire" label="人员要求 *" width="230">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`requireDetail.${scope.$index}.personrequire`"
                                                    :rules="rules.personrequire">
                                                    <el-input v-model="scope.row.personrequire" placeholder="请输入"></el-input>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="supplynames" label="推荐外包商(可多选) *" :show-overflow-tooltip="true" width="343">
                                            <template slot-scope="scope">
                                                <el-form-item :prop="`requireDetail.${scope.$index}.supplynames`"
                                                    :rules="scope.row.isadvertise == '1' ? rules.supplynames : rules.cancelProv"
                                                    ref="wbsProv">
                                                    <div @click="scope.row.isadvertise == '1' ? chooseWbs(scope.$index) : noneWbs()">
                                                        <el-input v-if="scope.row.isadvertise == '' || scope.row.isadvertise == undefined"
                                                            suffix-icon="el-icon-more" :disabled="true"></el-input>

                                                        <div v-else-if="(scope.row.supplynames == '' || scope.row.supplynames == undefined) &&  scope.row.isadvertise == '1'"
                                                            class="wbs-iconmore">
                                                            <el-input suffix-icon="el-icon-more" :disabled="true"
                                                                placeholder="请选择外包商" class="row_require_wbs"></el-input>
                                                        </div>
                                                        <el-input v-else-if="scope.row.isadvertise == '2'" suffix-icon="el-icon-more"
                                                            :disabled="true"></el-input>
                                                        <div class="table_supply_w" v-else>
                                                            <div class="table_supply">
                                                                {{scope.row.supplynames}}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </el-form-item>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="operation" label="操作">
                                            <template slot-scope="scope">
                                                <div class="operation">
                                                    <el-button @click.native.prevent="deleteRow(scope.$index, ruleForm.requireDetail,scope.row)">
                                                        <i class="el-icon-delete"></i>
                                                    </el-button>
                                                </div>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-form>
            </div>
            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs :processInstId="flowItem.processInstID" ref="log"></flow-logs>
            </div>
        </div>
        <div class="blank"></div>
        <div class="nav bottom">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="1.5">
                            <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete>
                        </el-col>
                    </div>
                    <div class="wrapper_right">
                        <div class="wrapper_right_bottom_1_zb">
                            <!-- 
                                <el-col :span="1.5">
                                <button-common :buttonName="buttonKeep" :size="size" :fns="saveRequire" :disabled="avoidRepeat"></button-common>
                            </el-col>
                            -->
                        </div>
                        <div>
                            <el-col :span="1.5">
                                <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
                            </el-col>
                        </div>
                    </div>
                </div>
            </el-row>
        </div>
        <!-- 推荐外包商弹窗 -->
        <el-dialog title="推荐外包商" :visible.sync="wbsDialog" class="wbsDialogCon">
            <div class="wbsDialogBody">
                <div class="wbsDialogBody_title">请选择外包商,可多选</div>
                <div class="wbsDialogBody_con">
                    <el-row>
                        <el-col :span="12">
                            <div class="wbsDialogBody_con_in">
                                <div class="wbsDialogBody_con_in_input">
                                    <el-input placeholder="搜索外包商名称" v-model="filterText" prefix-icon="el-icon-search"></el-input>
                                </div>
                                <el-scrollbar style="height:100%">
                                    <div class="wbsDialogBody_con_1">
                                        <el-tree :data="wbsData" :props="wpsProps" show-checkbox ref="tree" @check="wbsTree"
                                            v-infinite-scroll="loadWbs" node-key="id" default-expand-all></el-tree>
                                    </div>
                                </el-scrollbar>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="wbsDialogBody_con_in2">
                                <div class="wbsDialogBody_con_in2_title">已选<span>{{wbsCurrentDetial.length}}</span>家：</div>
                                <el-scrollbar style="height:100%">
                                    <div class="wbsDialogBody_con_2">
                                        <ul v-for="(item, index) in wbsCurrentDetial" :key="index" class="wbsDialogBody_ul">
                                            <div class="wbsList">
                                                <div>
                                                    <li class="wbsDialogBody_ul_li_1">{{item.outsourcname}}</li>
                                                    <li class="wbsDialogBody_ul_li_2">联系人：<span>{{item.contactsname}}</span></li>
                                                </div>
                                                <div class="el-icon-circle-close" @click="wbsDel(item,index)"></div>
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
                            <button-common :buttonName="buttonCancel" :size="size" :fns="wbsCancel"></button-common>
                        </el-col>
                        <el-col :span="1.5">
                            <button-orange :buttonName="buttonSure" :size="size" :fns="wbsSure"></button-orange>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
    import { getWbCompany, queryEmployeeTree, queryResourceDept, queryWorkItem } from "@/api/integrated/wbryzrlc";
    import { doDel, liuzhuan, save, delRequireStart, queryDetail, deletePpmManpower } from "@/api/integrated/xqgl";
    import { getDicEntry } from "@/api/eosDictEntry";
    import common from "@/api/common";
    import helpHint from '@/views/crewManager/common/helpHint'; //引入自定义联系招聘表头 
    import flowLogs from "@/views/plugins/camunda/FlowLogs";
    export default {
        name: 'RequireAdd',
        components: { buttonOrange, buttonCommon, buttonDelete, helpHint, flowLogs },
        data() {
            return {
                userid: '', //需求发起人Id
                username: '', //需求发起人名字
                dept: '', //部门名字
                deptno: '', //部门Id
                buttonClose: '关 闭', //关闭按钮名字
                size: 'mini',  //按钮大小
                buttonDel: '删 除',
                buttonKeep: '存草稿',
                buttonCommit: '提 交',
                buttonCancel: '取 消',
                buttonSure: '确 定',
                requirePeo_options: [], //需求发起人数据
                requirePeoParams: { //需求发起人配置信息
                    label: 'text',
                    value: 'id',
                    children: 'children',
                    // leaf: 'id'
                },
                peoForm_options: [],//人员类型形式
                connect_options: [], //联系招聘下拉内容
                ruleForm: { //验证字段
                    username: [], //需求发起人信息
                    supplytime: '', //提至供应商时间
                    requireDetail: [{
                        usertype: '', //人员类型形式
                        usernum: '', //需求人数
                        isadvertise: '', //联系招聘
                        addreason: '', //增加原因
                        personrequire: '', //人员要求
                        supplynames: '', //推荐外包商
                        supplyids: '', //外包商Id
                        id: '' //表ID
                    }]
                },
                rules: {
                    username: [
                        { required: true, message: '请选择需求发起人', trigger: 'change' }
                    ],
                    supplytime: [
                        { required: true, message: '请提至供应商时间', trigger: 'blur' }
                    ],
                    usertype: [
                        { required: true, message: '请选择人员类型', trigger: 'change' }
                    ],
                    cancelProv: [
                        { required: false, message: '', trigger: '' }
                    ],
                    isadvertise: [
                        { required: true, message: '请选择联系招聘', trigger: 'change' }
                    ],
                    addreason: [
                        { required: true, message: '请填写增加原因', trigger: 'blur' }
                    ],
                    personrequire: [
                        { required: true, message: '请填写人员要求', trigger: 'blur' }
                    ],
                    supplynames: [
                        { required: true, message: '请选择推荐外包商', trigger: 'blur' }
                    ],

                },
                wbsDialog: false, //是否显示外包商弹窗
                wbsData: [], //外包商数据
                pagerSize: 1, //外包商分页
                pagerSizes: 1, //外包商搜索分页
                wpsProps: {
                    label: 'outsourcname'
                },
                filterText: '', //过滤外包商
                wbsCurrentDetial: [], //选取的外包商
                wbsSearchDetial: [], //选取搜索的外包商
                wbsCurrentLength: 0, //选取的外包商数量
                currentRow: '', //点击的当前行
                delBtnIf: true, //删除按钮是否出现
                successMessage: '', // 成功消息提示
                errorMessage: '', //失败消息提示
                instanceid: '', //流程ID
                workItemID: '', //工作项id
                processDefName: '', //流程定义名称
                id: '', //主键Id
                avoidRepeat: false, //避免重复点击存草稿
                nodeVals: [], //选中的label值
                paramsBps: {}, //bps数据
                flowItem: {}, //bps数据
                currentCheckedOutIndex: 0,        // 当前选中外包商下标
            };
        },
        created() {
            // this.init();
        },
        mounted() {

        },
        watch: {
            filterText(val) {
                this.wbsData = [];
                this.pagerSizes = 1;
                this.getSearchWbs();
            },
        },
        methods: {
            // 初始化
            init(params) {
                this.flowItem = params;
                this.paramsBps = params;
                this.instanceid = params.processInstanceId;
                this.workItemID = params.businessKey;
                this.processDefName = params.processDefineName;
                this.$nextTick(_ => {
                    this.getDic();
                    this.searchEpmlo();
                    // 流程日志
                    this.$refs.log.init(params.processInstanceId);
                });
            },

            // 查询业务字典
            getDic() {
                getDicEntry({
                    dicttypeid: "REQUIRE_PEOPLE_TYPE,WHETHER_CONTACT_RECRUITMENT"
                })
                    .then(res => {
                        if (res.data.status !== 200) {
                            this.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        let data = res.data.results;
                        //人员类型
                        this.peoForm_options = data.filter(item => {
                            return item.dicttypeid == "REQUIRE_PEOPLE_TYPE";
                        });

                        //联系招聘
                        this.connect_options = data.filter(item => {
                            return item.dicttypeid == "WHETHER_CONTACT_RECRUITMENT";
                        });
                        return data;
                    });
            },

            // 取消提示消息
            cancelMessage() {

            },

            // 打开删除messageBox窗口
            delBox() {
                this.$confirm('确认删除该流程吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.delPro();
                }).catch(() => {

                })
            },
            // 新增一行
            eduExperAdd(v) {
                var list;
                list = {
                    usertype: '', //人员类型形式
                    usernum: '', //需求人数
                    isadvertise: '', //联系招聘
                    addreason: '', //增加原因
                    personrequire: '', //人员要求
                    supplynames: '', //推荐外包商
                    supplyids: '', //外包商Id
                    id: '', //表ID
                };
                this.ruleForm.requireDetail.push(list);

            },

            // 删除行
            deleteRow(index, rows, row) {
                if (rows.length != 1 && row.id == "") {
                    rows.splice(index, 1);
                } else if (rows.length != 1 && row.id != "") {
                    this.delTableData(index, rows, row);
                }
                else {
                    this.errorMessage = '不能少于一行'
                    this.saveError();
                }

            },

            // 删除表中数据
            delTableData(index, rows, row) {
                let params = {};
                params.id = row.id;
                let data = common.paramsFormat(params);
                deletePpmManpower(data).then(res => {
                    if (res.data.status == 200) {
                        rows.splice(index, 1);
                    } else {
                        this.errorMessage = '删除失败'
                        this.saveError();
                    }

                });
            },

            // 无法点击时提示
            noneWbs() {
                this.errorMessage = '不能选择外包商'
                this.saveError();
            },


            renderHeader() {
                return this.$createElement('helpHint', {
                    props: {
                        content: '是否需要综合部联系招聘',
                        name: '联系招聘 *'
                    }
                })
            },

            // 查询外包商
            getWbcom(index) {
                let indexs = this.currentCheckedOutIndex;
                let params = {};
                params.index = index;
                let data = common.paramsFormat(params);
                getWbCompany(data)
                    .then(res => {
                        this.$nextTick(() => {
                            this.wbsData = this.wbsData.concat(res.data.results);
                            this.ruleForm.requireDetail[indexs].wbsData = this.wbsData;
                            let wbsKeys = [];
                            this.wbsCurrentDetial.filter(item => wbsKeys.push(item.id))
                            this.$refs.tree.setCheckedKeys(wbsKeys);
                        })

                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            //搜索查询外包商
            getSearchWbs() {
                let params = {};
                params.outsourcname = this.filterText;
                params.index = this.pagerSizes;
                let data = common.paramsFormat(params);
                getWbCompany(data)
                    .then(res => {
                        if (this.filterText != '') {
                            this.wbsData = this.wbsData.concat(res.data.results);
                        } else {
                            let indexs = this.currentCheckedOutIndex;
                            this.wbsData = this.ruleForm.requireDetail[indexs].wbsData;
                        }
                        this.$nextTick(() => {
                            let wbsKeys = [];
                            this.wbsCurrentDetial.filter(item => wbsKeys.push(item.id))
                            this.$refs.tree.setCheckedKeys(wbsKeys);
                        })
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 表格行被点击时触发
            rowClick(row) {
                this.currentRow = row;

            },
            //弹窗外包商确认
            wbsSure() {
                let wbsArray = [], wbsIdArray = [];
                // 外包商右边清空问题
                let indexs = this.currentCheckedOutIndex;
                for (var i = 0; i < this.ruleForm.requireDetail[indexs].oinfos.length; i++) {
                    wbsArray.push(this.ruleForm.requireDetail[indexs].oinfos[i].outsourcname);
                    wbsIdArray.push(this.ruleForm.requireDetail[indexs].oinfos[i].orgcode);
                }
                this.currentRow.supplynames = wbsArray.toString();
                this.currentRow.supplyids = wbsIdArray.toString();
                this.ruleForm.requireDetail.splice(0, 0);


                this.wbsDialog = false;
            },

            //弹窗外包商取消
            wbsCancel() {
                this.wbsDialog = false;
            },

            // 是否联系招聘选中值发生变化
            advertiseChoose(val, index) {
                if (val == 2) {
                    this.ruleForm.requireDetail[index].supplynames = '';
                    this.ruleForm.requireDetail[index].supplyids = '';
                    // 外包商右边清空问题
                    this.ruleForm.requireDetail[index].oinfos = [];
                    if (this.$refs.tree != undefined) {
                        this.$refs.tree.setCheckedNodes([]);
                    }
                }
            },

            // 查询并且过滤部门
            filDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";

                queryResourceDept(params)
                    .then(res => {
                        var fild = res.data.results[0].children;
                        this.fild = fild;
                        var fildN = [];
                        var reg = /([^-]+)$/;
                        var lv;
                        if (fild) {
                            fild.forEach(function (v) {
                                lv = v.text.match(reg)[1]; //取到金融科技部后面的部分
                                fildN.push(lv);
                            })
                        }
                        this.depData = fildN;
                        var fildnF = fildN.slice(0, this.counts);
                        this.deptno_options = fildnF;
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end

            },

            // 外包商分页加载
            loadWbs() {
                let indexs = this.currentCheckedOutIndex;
                this.$nextTick(() => {
                    if (this.filterText == '') {
                        this.pagerSize += 1;
                        this.ruleForm.requireDetail[indexs].pagerSize = this.pagerSize;
                        this.getWbcom(this.pagerSize);
                    } else {
                        this.pagerSizes += 1;
                        this.getSearchWbs()
                    }
                })
            },

            //点击弹出外包商弹窗2
            chooseWbs(index) {
                // 外包商清空问题
                this.wbsCurrentDetial = [];
                this.wbsCurrentDetial = [];
                if (this.ruleForm.requireDetail[index].oinfos != undefined && this.$refs.tree != undefined) {
                    this.wbsCurrentDetial = this.ruleForm.requireDetail[index].oinfos;
                    this.$nextTick(() => {
                        this.$refs.tree.setCheckedNodes(this.wbsCurrentDetial)
                    })
                }
                if (this.wbsCurrentDetial.length == 0 && this.wbsData.length != 0) {
                    this.$nextTick(() => {
                        this.$refs.tree.setCheckedNodes([]);
                    })
                }
                this.currentCheckedOutIndex = index
                this.filterText = '';
                this.wbsDialog = true;
                if (this.ruleForm.requireDetail[index].wbsData == undefined) {
                    this.wbsData = [];
                    this.pagerSize = 1;
                    this.getWbcom(this.pagerSize);
                }
            },

            // 外包商选中2
            wbsTree(value, curr) {
                let index = this.currentCheckedOutIndex;
                if (this.filterText == '') {
                    this.wbsCurrentDetial = curr.checkedNodes;
                } else {
                    this.wbsSearchDetial = curr.checkedNodes;
                }
                let __temps = this.wbsCurrentDetial.concat(this.wbsSearchDetial);
                let res = new Map();
                this.wbsCurrentDetial = __temps.filter(ii => !res.has(ii.id) && res.set(ii.id, ii));
                this.ruleForm.requireDetail[index].oinfos = this.wbsCurrentDetial;
            },

            // 外包商删除2
            wbsDel(val, index) {
                let indexs = this.currentCheckedOutIndex;
                this.ruleForm.requireDetail[indexs].oinfos.splice(index, 1);
                this.wbsCurrentDetial = this.ruleForm.requireDetail[indexs].oinfos;
                this.$nextTick(() => {
                    this.$refs.tree.setCheckedNodes(this.wbsCurrentDetial);
                })
            },

            // 查询行员
            searchEpmlo() {
                let params = '';
                params = "7004";
                queryEmployeeTree(params)
                    .then(res => {
                        this.organizers_options = res.data.results;
                        let data = res.data.results;
                        let name = data[0].text;
                        this.requirePeo_options = data[0].children.filter(tt => {
                            tt.text = tt.text.replace(name + "-", "");
                            return tt;
                        });
                        // this.requirePeo_options = res.data.results[0].children;
                        this.getRequireDetail();
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 需求发起人节点变化
            peoChange(v) {
                this.nodeVals = this.getCascaderObj(this.ruleForm.username, this.requirePeo_options);
                this.userid = this.nodeVals[1].pcode;
                this.username = this.nodeVals[1].text;
                this.dept = this.nodeVals[0].text;
                var depId = this.nodeVals[1].pid;
                this.deptno = depId.slice(3);
            },

            // 获取当前节点Label值方法
            getCascaderObj(val, opt) {
                return val.map(function (value, index, array) {
                    for (var itm of opt) {
                        if (itm.id == value) {
                            opt = itm.children;
                            return itm;
                        }
                    }
                    return null;
                })
            },

            // 验证必填项
            proving() {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        this.commit();
                    } else {
                        this.$message({
                            showClose: true,
                            message: '操作失败,请填写必填项',
                            type: 'error'
                        })
                        return false;
                    }
                })
            },

            // 保存需求信息
            saveRequire() {
                this.avoidRepeat = true;
                let ppmManpowperdemandApply = {};
                const loading = common.loading(this);
                ppmManpowperdemandApply.userid = this.userid;
                ppmManpowperdemandApply.username = this.username;
                ppmManpowperdemandApply.dept = this.dept;
                ppmManpowperdemandApply.deptno = this.deptno;
                ppmManpowperdemandApply.supplytime = this.ruleForm.supplytime;
                ppmManpowperdemandApply.id = this.id;
                ppmManpowperdemandApply.infos = this.ruleForm.requireDetail;
                ppmManpowperdemandApply.instanceid = this.instanceid;
                let data = ppmManpowperdemandApply;
                save(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.instanceid = res.data.results.instanceid;
                        this.getRequireDetail();
                        this.delBtnIf = true;
                        this.successMessage = '保存草稿成功'
                        this.saveSuccess();
                        this.avoidRepeat = false;
                        this.getWorkItem();
                    } else {
                        this.errorMessage = '保存草稿失败'
                        this.saveError();
                    }
                });

            },

            // 获取保存的需求详细信息
            getRequireDetail() {
                let params = {};
                params.instanceid = this.instanceid;
                queryDetail(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            let echoData = res.data.results;
                            this.ruleForm.requireDetail = echoData.infos;
                            this.id = echoData.id;
                            this.dept = echoData.dept;
                            this.ruleForm.supplytime = echoData.supplytime;
                            this.userid = echoData.userid;
                            this.deptno = echoData.deptno;
                            this.username = echoData.username;
                            this.handleEdit(echoData);
                        } else {
                            // location.reload();
                            this.errorMessage = '获取数据失败'
                            this.saveError();
                        }

                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
            },

            // 级联选择器回显
            handleEdit(data) {
                let userid = "emp" + data.userid;
                this.ruleForm.username = this.changeDetSelect(userid, this.requirePeo_options);
            },

            changeDetSelect(key, treeData) {
                let arr = []; //递归时操作的数组
                let returnArr = []; //存放结果的数组
                let depth = 0;//定义全局层级
                function childrenEach(childrenData, depthN) {
                    for (var j = 0; j < childrenData.length; j++) {
                        depth = depthN; //将执行的层级赋值到全局层级
                        arr[depthN] = (childrenData[j].id);
                        if (childrenData[j].id == key) {
                            returnArr = arr.slice(0, depthN + 1); //将目前匹配的数组，截断并保存到结果数组
                            break
                        } else {
                            if (childrenData[j].children) {
                                depth++
                                childrenEach(childrenData[j].children, depth);
                            }
                        }
                    }
                    return returnArr;
                }
                return childrenEach(treeData, depth);
            },

            // 提交本页面数据
            commit() {
                const loading = common.loading(this);
                let ppmManpowperdemandApply = {};
                ppmManpowperdemandApply.userid = this.userid;
                ppmManpowperdemandApply.username = this.username;
                ppmManpowperdemandApply.dept = this.dept;
                ppmManpowperdemandApply.deptno = this.deptno;
                ppmManpowperdemandApply.supplytime = this.ruleForm.supplytime;
                ppmManpowperdemandApply.id = this.id;
                ppmManpowperdemandApply.infos = this.ruleForm.requireDetail;
                ppmManpowperdemandApply.instanceid = this.instanceid;
                ppmManpowperdemandApply.bps = this.paramsBps;

                let data = ppmManpowperdemandApply;
                liuzhuan(data).then(res => {
                    // 关闭loading
                    loading.close();
                    if (res.data.status == 200) {
                        let message = common.getFlowMessgeNew(res.data.message);
                        let messageArr = message.split(",");
                        this.$nextTick(() => {
                            this.messageNotify(messageArr);
                            this.$router.push({ name: 'dashboard' });
                        })
                    } else {
                        this.errorMessage = '操作失败'
                        this.saveError();
                    }
                });

            },

            //获取待办工作项详情-单条
            getWorkItem() {
                let params = {};
                params.processInstID = this.instanceid;
                queryWorkItem(params).then(res => {
                    this.processDefName = res.data.results.processDefName;
                    this.workItemID = res.data.results.workItemID;
                });
            },

            // 删除流程
            delPro() {
                let params = {};
                params.id = this.workItemID; 
                params.instanceid = this.instanceid;
                let data = common.paramsFormat(params);
                doDel(data).then(res => {
                    if (res.data.status == 200) {
                        this.successMessage = '流程删除成功'
                        this.saveSuccess();
                        this.$router.push({ name: 'dashboard' });
                    } else {
                        this.errorMessage = '流程删除失败'
                        this.saveError();
                    }

                });
            },

            // 删除业务
            delBusiness() {
                let params = {};
                params.instanceid = this.instanceid;
                let data = common.paramsFormat(params);
                delRequireStart(data).then(res => {
                    this.ident = res.data.results; //标识
                });
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },

            //消息提示
            messageNotify(messageVal) {
                this.$notify({
                    title: '提示',
                    // message: this.messageNote,
                    dangerouslyUseHTMLString: true,
                    message: '<div><div>' + messageVal[1] + '</div><div>' + messageVal[2] + '</div></div>',
                    duration: 0
                })
            },

            // 成功消息提示
            saveSuccess() {
                this.$message({
                    showClose: true,
                    message: this.successMessage,
                    type: 'success'
                })
            },

            // 失败消息提示
            saveError() {
                this.$message({
                    showClose: true,
                    message: this.errorMessage,
                    type: 'error'
                });
            },


        }
    };
</script>
<style scoped>
    .contentMain {
        width: 100%;
        height: 100%;
        font-size: 16px;
        padding: 10px 20px;
    }

    /* 去掉ul默认样式 */
    body ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }


    .nav {
        width: 100%;
        /* height: 30px; */
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 100px;
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

    .wrapper_right_bottom_1_zb {
        margin-right: 10px;
    }

    .content {
        width: 100%;
        font-size: 16px;
        /* background-color: #ffffff; */
        /* padding: 15px 20px; */
    }

    .content p {
        color: #262626;
        font-size: 14px;
        margin: 12px 0;
    }

    .content .row_2_detail {
        font-size: 16px;
        font-weight: 600;
    }

    .content .add_row {
        font-size: 15px;
    }

    .contentTwo_title {
        height: 60px;
        line-height: 60px;
        background-color: #ffffff;
        padding: 0 25px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .contentTwo_title_p {
        color: #262626;
        font-weight: 600;
    }

    .content .row_1 {
        background-color: #ffffff;
        /* padding: 0 20px 15px 20px; */
        padding: 0 20px;

    }

    .contentMain .row_require {
        background-color: #ffffff;
        /* padding: 0 20px 15px 20px; */
        margin-top: 15px;
    }

    .row_table {
        padding: 12px 20px 15px 20px;
    }

    .add_row {
        color: #faad14;
        font-weight: 500;
        margin-left: 20px;
        cursor: pointer;
    }

    /* 空格占位 */
    .blank {
        width: 100%;
        height: 160px;
    }

    .bottom {
        position: absolute;
        bottom: 0;
        padding: 15px 0;
    }

    /* 选择推荐外包商弹窗 */
    .wbsDialogBody_con {
        border: 1px solid #d9d9d9;
        margin: 15px 0;
    }

    .wbsDialogBody_con_in {
        border-right: 1px solid #d9d9d9;
        padding: 10px 0 10px 10px;
    }

    .wbsDialogBody_con_in_input {
        padding-right: 10px;
    }

    .wbsDialogBody_con_in2_title {
        font-size: 16px;
        font-weight: 550;
        color: #303133;
    }

    .wbsDialogBody_con_in2_title span {
        margin: 0 5px;
    }

    .wbsDialogBody_con_1 {
        min-height: 300px;
        max-height: 300px;
        padding-top: 10px;
    }

    .wbsDialogBody_con_2 {
        min-height: 310px;
        max-height: 310px;
        padding-top: 5px;
    }

    .wbsDialogBody_con_in2 {
        padding: 18px 0 0 15px;
    }

    .wbsDialogBody_ul div {
        margin-top: 10px;
    }

    .wbsDialogBody_ul_li_1 {
        color: #303133;
        font-size: 14px;
        font-weight: 400;
    }

    .wbsDialogBody_ul_li_2 {
        color: #C0C4CC;
        font-size: 14px;
        font-weight: 400;
        margin-top: 5px;
    }

    /* 供应商 */
    .table_supply_w {
        width: 100%;
        height: 36px;
        line-height: 36px;
        font-size: inherit;
        border: 1px solid #E4E7ED;
        /* background-color: #F5F7FA; */
        border-radius: 4px;
        padding: 0 15px;
        margin: 0;
        cursor: pointer;
    }

    .table_supply {
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }

    .wbsList {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding-right: 10px;
    }
</style>

<style>
    .contentMain_rlxqAdd .el-icon-delete {
        width: 11px;
        height: 11px;
        color: #f56c6c;
    }

    .operation .el-button {
        border: 1px solid #ffffff;
        padding: 0;
    }

    /* table表格修改 */

    .contentMain_rlxqAdd .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    .contentMain_rlxqAdd .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }

    /* .contentMain .el-table--medium td,
        .el-table--medium th {
            padding: 0;
        } */

    .table_rlxqAdd .el-table--medium th {
        padding: 0;
    }

    .table_rlxqAdd .el-table--medium td {
        padding: 0;
    }


    .table_rlxqAdd .el-table th {
        padding: 10px 0;
    }

    /* form表单样式修改 */
    .contentMain_rlxqAdd .el-form-item {
        margin: 16px 0;
    }

    /* 禁止点击输入框 */
    .rlxqContent .el-input.is-disabled .el-input__inner {
        cursor: pointer;
    }


    /* 计数器 */
    .contentMain_rlxqAdd .el-input-number--medium {
        width: 100px;
    }

    .contentMain_rlxqAdd .el-input-number__decrease:hover,
    .el-input-number__increase:hover {
        color: rgba(251, 164, 47, 1);
    }

    .contentMain_rlxqAdd .el-input-number__decrease:hover:not(.is-disabled)~.el-input .el-input__inner:not(.is-disabled),
    .el-input-number__increase:hover:not(.is-disabled)~.el-input .el-input__inner:not(.is-disabled) {
        border-color: rgba(251, 164, 47, 1);
    }

    /* 外包商弹窗 */
    .wbsDialogCon .el-dialog__title {
        color: #262626;
        font-weight: 600;
    }

    .wbsDialogCon .el-dialog__body {
        padding-top: 0;
    }

    .wbsDialogCon .el-checkbox__input.is-checked .el-checkbox__inner {
        border-color: rgba(251, 164, 47, 1);
        background: rgba(251, 164, 47, 1);
    }

    .wbsDialogCon .el-checkbox__inner:hover {
        border-color: rgba(251, 164, 47, 1);
    }

    .wbsDialogCon .el-checkbox__input.is-focus .el-checkbox__inner {
        border-color: rgba(251, 164, 47, 1);
    }

    .wbsDialogCon .el-dialog__headerbtn:focus .el-dialog__close,
    .el-dialog__headerbtn:hover .el-dialog__close {
        color: rgba(251, 164, 47, 1);
    }

    /* 级联框修改 */
    .contentMain_rlxqAdd .el-cascader {
        display: block;
    }

    /* el-input获得焦点样式修改 */
    .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }

    /* 禁用的输入框 */
    .contentMain_rlxqAdd .el-select {
        display: block;
    }

    /* 日期选择器 */
    .contentMain_rlxqAdd .el-date-editor.el-input,
    .el-date-editor.el-input__inner {
        width: 100%;
    }

    .el-date-table td.today span {
        color: rgba(251, 164, 47, 1);
    }

    .el-date-table td.current:not(.disabled) span {
        background-color: rgba(251, 164, 47, 1);
    }

    .el-date-table td.available:hover {
        color: rgba(251, 164, 47, 1);
    }

    .el-picker-panel__icon-btn:hover {
        color: rgba(251, 164, 47, 1);
    }

    .el-date-picker__header-label.active,
    .el-date-picker__header-label:hover {
        color: rgba(251, 164, 47, 1);
    }

    /* 下拉框获得焦点样式修改 */
    .el-select .el-input.is-focus .el-input__inner {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-select .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-select-dropdown__item.selected {
        color: rgba(251, 164, 47, 1);
    }

    /* 级联选择器 */

    .el-cascader {
        display: block;
    }

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

    /* MessageBox弹窗 */
    .el-message-box__content {
        color: #262626;
        font-size: 16px;
    }

    .el-message-box__headerbtn {
        display: none;
    }

    /* MessageBox弹窗按钮 */
    .el-message-box__btns .el-button:focus {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .el-message-box__btns .el-button:hover {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .el-message-box__btns .el-button--primary {
        background-color: rgba(251, 164, 47, 1);
        border-color: rgba(251, 164, 47, 1);
    }

    .el-message-box__btns .el-button--primary:focus {
        color: #ffffff;
        background-color: #ebb563;
        border-color: #ebb563;
    }

    .el-message-box__btns .el-button--primary:hover {
        color: #ffffff;
        background-color: #ebb563;
        border-color: #ebb563;
    }

    .wbs-iconmore .el-input.is-disabled .el-input__icon {
        cursor: pointer;
    }
</style>