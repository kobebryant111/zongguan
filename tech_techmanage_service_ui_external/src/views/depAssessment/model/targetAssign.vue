<!-- 分配 -->
<template>
    <div class="tem-target-container">
        <div class="tem-target-body">
            <div class="tem-target-body-title">
                <div class="tem-target-back-bread">
                    <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
                    <el-breadcrumb separator="/" v-if="this.$route.query.type == 2">
                        <el-breadcrumb-item :to="{ path: this.modelPath}">模板分配</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$route.query.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <el-breadcrumb separator="/" v-else>
                        <el-breadcrumb-item :to="{ path: this.modelPath}">模板分配</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/modelManager', query: {sign: this.$route.query.sign}}">模板库</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$route.query.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div>
                    <span class="tem-target-body-title-right-text">已配置基准<span>{{totalBasepoint}}</span>分</span>
                    <el-button type="warning" @click="commitData">提 交</el-button>
                    <el-button type="primary" @click="addTo(null)">添加一级考核项</el-button>
                </div>
            </div>
            <el-table :data="tableData" row-key="id" :tree-props="{children: 'children'}" :expand-row-keys="expandRowKeys">
                <el-table-column label="考核项" min-width="35%" prop="name">
                    <template slot-scope="scope">
                        <template v-if="scope.row.levelnum == 1 && scope.row.hasson == 0">&emsp;&nbsp;&nbsp;</template>
                        {{scope.row.name}}
                    </template>
                </el-table-column>
                <el-table-column label="基准分" min-width="10%" prop="basepoint"></el-table-column>
                <el-table-column label="指标制定类型" min-width="10%" prop="targettype">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.targettype">-</span>
                        <span v-else-if="scope.row.targettype == 2">自定义</span>
                        <span v-else-if="scope.row.targettype == 1">公共指标</span>
                    </template>
                </el-table-column>
                <el-table-column label="指标制定部门" min-width="15%" prop="depid">
                     <template slot-scope="scope">
                        {{getDepName(scope.row.markerdepid)}}
                    </template>
                </el-table-column>
                 <!-- <el-table-column v-show="false" prop="levelnum"></el-table-column> -->
                <el-table-column label="评价方式" min-width="15%" prop="evaltype">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.evaltype">-</span>
                        <span v-else-if="scope.row.evaltype == 1">档位评分</span>
                        <span v-else-if="scope.row.evaltype == 2">任意分值</span>
                        <span v-else-if="scope.row.evaltype == 3">其他（加分/倒扣分）</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" min-width="15%" fixed="right">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.levelnum < maxLevel" type="text" style="color: #01CCA7;" @click="addTo(scope.row)">添加子项</el-button>
                        <el-button v-if="scope.row.hasson == 0" type="text" style="color: #409EFF;" @click="update(scope.row)">修 改</el-button>
                        <el-button type="text" style="color: #FF4044;" @click="delSingle(scope.row)">删 除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 添加一级考核项 -->
        <el-dialog :title="diaTitle" :visible.sync="ifShowTjDialog" @close="addClose" class="khpzx-dialog" width="40%">
            <div class="xgContent-bmkh">
                <el-form label-position="left" label-width="120px" :model="updateData" ref="formAdd">
                    <el-form-item v-show="false"><el-input v-model="updateData.id"></el-input></el-form-item>
                    <el-form-item v-show="false"><el-input v-model="updateData.levelnum"></el-input></el-form-item>
                    <el-form-item label="考核项名称" prop="target" :rules="[{ required: true, message: '请选择考核项', trigger: 'change'}]">
                        <el-select placeholder="考核项" v-model="updateData.target" value-key="id" @change="changerTarget" filterable>
                            <el-option v-for="item in khTarget" :key="item.id" :label="item.name" :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item v-show="false" label="考核项名称"><el-input v-model="updateData.name"></el-input></el-form-item>
                    <el-form-item label="备注项" prop="remark">
                        <el-input placeholder="备注项" v-model="updateData.remark" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="基准分" prop="basepoint" :rules="[{ required: true, validator: isNumber, trigger: 'blur'}]">
                        <el-input v-if="updateData.evaltype == 3" placeholder="基准分" v-model="updateData.basepoint" disabled></el-input>
                        <el-input v-else placeholder="基准分" v-model="updateData.basepoint"></el-input>
                    </el-form-item>
                    <el-form-item label="指标类型" prop="targettype">
                        <el-radio-group v-model="updateData.targettype" disabled>
                            <el-radio v-for="item in targetDeptData" :label="item.label" :key="item.label">{{item.name}}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="指标制定部门" v-if="updateData.targettype == 1" prop="depid">
                        <el-select v-model="updateData.markerdepid" disabled>
                            <el-option v-for="item in depDataAll" :key="item.depId" :label="item.depName" :value="parseInt(item.depId)"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="是否需要自评" prop="selfeval">
                        <el-radio-group v-model="updateData.selfeval" disabled>
                            <el-radio v-for="item in whetherData" :label="item.label" :key="item.label">{{item.name}}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="评分方式" prop="evaltype">
                        <el-radio-group v-model="updateData.evaltype" disabled>
                            <el-radio v-for="item in scoringMethodData" :label="item.label" :key="item.label">{{item.name}}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-table v-if="updateData.evaltype == 1" :data="depkpiEvalSet" border>
                        <el-table-column type="index" label="序号" width="55"></el-table-column>
                        <el-table-column label="分档描述" width="260" prop="dictname"></el-table-column>
                        <el-table-column label="系数" width="260" prop="dictid"></el-table-column>
                    </el-table>
                    <el-form-item v-if="updateData.evaltype == 3" label="额外区间分：" >
                        <el-col :span="4">
                            <el-form-item prop="minpoint">
                                <el-input v-model="updateData.minpoint" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col class="line" :span="2" :offset="2">-</el-col>
                        <el-col :span="4">
                            <el-form-item prop="maxpoint">
                                <el-input v-model="updateData.maxpoint" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <el-button @click="cancelAdd">取 消</el-button>
                    <el-button type="primary" @click="sureAdd">保 存</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import common from "@/api/common";
    import cleanBtn from "@/components/CleanBtn"
    import { getDicEntry } from "@/api/eosDictEntry";
    import { getEosDictEntrys } from "@/api/hykh/pjsz";
    import { getEosDepKpiGrade } from "@/api/depAssessment/depAssessmentControl";
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import { queryTargetList } from "@/api/zzkhApi/target";
    import { queryModelTargetList, modelAssignTarget, updateModelTarget, deleteModelTarget } from "@/api/zzkhApi/moelDetailTmp";
    import { queryDepTargetList, depAssignTarget, updateDepTarget, deleteDepTarget } from "@/api/zzkhApi/depmodelDetailTmp";
    import { commitModelTarget } from "@/api/zzkhApi/moelDetail";
    import { commitDepTarget } from "@/api/zzkhApi/depmodelDetail";
    export default {
        name: 'targetAssign',
        data() {
            return {
                addFlag: 0, // 修改删除标志
                modelName: '',// 模板名称
                target: {
                    remark: null,
                    targettype: null,
                    selfeval: null,
                    depid:null,
                    minpoint: null,
                    maxpoint: null
                },
                depDataAll: [],
                tableData: [],
                oriTableData: [],
                totalBasepoint: 0,
                ifShowTjDialog: false, //是否显示添加弹窗
                parentDetail: null, // 当前修改项的父级
                updateData: {
                    id: null,
                    parentid: null,
                    levelnum: null,
                    target: null,
                    targettype: null,
                    remark:null,
                    basepoint: null,
                    selfeval: null,
                    evaltype: null,
                    depid: null,
                    minpoint: null,
                    maxpoint: null,
                }, //添加数据
                khTarget: [], //考核项名称数据
                targetDeptData: [{
                    label: 2,
                    name: "自定义"
                },
                {
                    label: 1,
                    name: "公共指标"
                }], //考核项名称数据
                whetherData: [{
                    label: 1,
                    name: "是"
                },
                {
                    label: 2,
                    name: "否"
                }], //是否需要自评
                scoringMethodData: [
                    {
                        label: 1,
                        name: "档位评分"
                    },
                    {
                        label: 2,
                        name: "任意分值（基准分内）"
                    },
                    {
                        label: 3,
                        name: "其他（加分/倒扣分）"
                    }
                ], //是否需要自评
                diaTitle: '', //弹窗标题
                depkpiEvalSet: [], // 考核评级档位
                isNumber: (rule, value, callback) => {
                    if (!value && value != 0) {
                        callback(new Error('基准分不能为空'));
                    }
                    if (!/^\d+$/.test(value)) {
                        callback(new Error('请输入正整数,值为0-100'));
                    }
                    callback();
                },
                queryFun: null, 
                assignFun: null, 
                updateFun: null, 
                deleteFun: null, 
                commitFun: null,
                modelPath: null,
                maxLevel: 0, // 框架层级
                expandRowKeys: [], // 需要展开的树
            }
        },
        beforeCreate(){
            if(!this.$route.query.sign || !this.$route.query.id || !this.$route.query.name || !this.$route.query.type){
                this.goBack();
            }
        },
        created(){
            if(this.$route.query.type == 1){
                this.queryFun = queryModelTargetList;
                this.assignFun = modelAssignTarget;
                this.updateFun = updateModelTarget;
                this.deleteFun = deleteModelTarget;
                this.commitFun = commitModelTarget;
            }else if(this.$route.query.type == 2){
                this.queryFun = queryDepTargetList;
                this.assignFun = depAssignTarget;
                this.updateFun = updateDepTarget;
                this.deleteFun = deleteDepTarget;
                this.commitFun = commitDepTarget;
            }

            if(this.$route.query.sign == 1){
                this.modelPath = "/ejbModel"
            }else if(this.$route.query.sign == 2){
                this.modelPath = "/zxModel"
            }else if(this.$route.query.sign == 3){
                this.modelPath = "/yjbModel"
            }
        },
        mounted() {
            this.getEosDepKpiGrade();
            this.getEvalSet();
            this.getAllDep();
            this.getTargetList();
            this.seaTableData(); //获取已分配数据
        },
        methods: {
            //返回前一页
            goBack(){
                this.$router.back(-1)
            },

            // 查询档位
            getEosDepKpiGrade() {
                const loading = common.loading(this);
                getEosDepKpiGrade().then(res => {
                    loading.close();
                    console.log('查询框架数据', res.data)
                    if (res.data.status == 200) {
                        if(this.$route.query.sign == 1){
                            this.maxLevel = res.data.results.dep;
                        }else if(this.$route.query.sign == 2){
                            this.maxLevel = res.data.results.cen;
                        }else if(this.$route.query.sign == 3){
                            this.maxLevel = res.data.results.tec;
                        }
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
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
                            // li.dictid = li.dictid * 100;
                            li.dictid = common.accMul(li.dictid, 100);
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 匹配二级部名称
            getDepName(depid){
                let dep = this.depDataAll.find(item => item.depId == depid)
                return dep ? dep.depName : '-';
            },

            // 获取所有二级部
            getAllDep() {
                const loading = common.loading(this);
                if(this.$route.query.sign == 1){
                    queryDeptList({}).then(res => {
                        loading.close();
                        console.log("获取部门", res.data)
                        if (res.data.status == 200) {
                            var fild = res.data.results;
                            this.depDataAll = fild.filter(tt => {
                                tt.depName = tt.depName.replace('总行金融科技部-', "");
                                return tt;
                            });
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
                } else {
                    queryList({}).then(res => {
                        loading.close();
                        console.log("获取部门", res.data)
                        if (res.data.status == 200) {
                            var fild = res.data.results;
                            this.depDataAll = fild.filter(tt => {
                                tt.depName = tt.centername.replace('总行金融科技部-', "");
                                tt.depId = tt.id + "";
                                return tt.id != 61;
                            });
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
                }
            },

            // 获取指标数据
            getTargetList() {
                var val = {};
                val.sign = this.$route.query.sign;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryTargetList(data).then(res => {
                    loading.close();
                    console.log('查询指标数据', res.data)
                    if (res.data.status == 200) {
                        this.targetList = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },
            
            //获取表格数据
            seaTableData(val) {
                if (!val) {
                    val = {};
                }
                val.sign = this.$route.query.sign;
                if(this.$route.query.type == 1){
                    val.modelid = this.$route.query.id;
                } else {
                    val.depid = this.$route.query.id;
                }
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                this.queryFun(data).then(res => {
                    loading.close();
                    console.log('模板已分配指标数据', res.data.results)
                    if (res.data.status == 200) {
                        this.tableData = this.getTreeData(res.data.results);
                        this.oriTableData = res.data.results;
                        this.getTotalBaseponit();
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 改变考核项事件
            changerTarget(target){
                this.updateData.name = target.name;
                this.updateData.remark = target.remark;
                if(this.updateData.levelnum != 1){
                    this.updateData.targettype = this.parentDetail.targettype ? this.parentDetail.targettype: target.targettype;
                    this.updateData.markerdepid = this.parentDetail.markerdepid ? this.parentDetail.markerdepid: target.depid;  
                    this.updateData.selfeval = this.parentDetail.selfeval ? this.parentDetail.selfeval: target.selfeval;
                    this.updateData.evaltype = this.parentDetail.evaltype ? this.parentDetail.evaltype: target.evaltype;
                    this.updateData.minpoint = this.parentDetail.minpoint ? this.parentDetail.minpoint: target.minpoint;
                    this.updateData.maxpoint = this.parentDetail.maxpoint ? this.parentDetail.maxpoint: target.maxpoint;
                } else {
                    this.updateData.targettype = target.targettype;
                    this.updateData.selfeval = target.selfeval;
                    this.updateData.evaltype = target.evaltype;
                    this.updateData.markerdepid = target.depid;
                    this.updateData.minpoint = target.minpoint;
                    this.updateData.maxpoint = target.maxpoint;
                }
                if(this.updateData.targettype != 1){
                    this.updateData.markerdepid = null;
                }
                if(this.updateData.evaltype == 3){
                    this.updateData.basepoint = 0;
                } else {
                    this.updateData.basepoint = null;
                }
            },
            
            // 添加弹窗
            addTo(valT) {
                let numList = ["", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                if(valT){
                    var num = parseInt(valT.levelnum);
                    this.diaTitle = '添加'+ numList[num + 1] +'级考核项';
                    this.khTarget = this.targetList.filter(item => {
                        if(item.levelnum != num + 1){
                           return;
                        }
                        if(!valT.targettype){
                            return item;
                        }
                        if(valT.targettype == item.targettype){
                            if(valT.targettype == 1 && valT.markerdepid != item.depid){
                                return;
                            }
                            return item;
                        }
                    })
                    this.updateData.parentid = valT.id;
                    this.updateData.levelnum = num + 1;

                    // 赋值父级指标数据
                    this.parentDetail = valT;
                    this.updateData.targettype = valT.targettype;
                    if(valT.targettype == 1){
                        this.updateData.markerdepid = valT.markerdepid;
                    }
                    this.updateData.selfeval = valT.selfeval;
                    this.updateData.evaltype = valT.evaltype;
                    if(this.updateData.evaltype == 3){
                        this.updateData.maxpoint = valT.maxpoint;
                        this.updateData.minpoint = valT.minpoint;
                        this.updateData.basepoint = 0;
                    }  
                }else{
                    this.diaTitle = '添加一级考核项';
                    this.khTarget = this.targetList.filter(item => item.levelnum == 1);
                    this.updateData.parentid = null;
                    this.updateData.levelnum = 1;
                }
                this.addFlag = 0;
                this.ifShowTjDialog = true;
            },

            // 修改弹窗
            update(valT){
                console.log(valT)
                let numList = ["", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                this.parentDetail = {
                    targettype: null,  
                    selfeval: null,  
                    evaltype: null,  
                    markerdepid : null,  
                    minpoint: null,  
                    maxpoint: null
                };
                let target;
                this.diaTitle = '修改'+ numList[valT.levelnum] +'级考核项';
                if(valT.levelnum > 1){
                    this.parentDetail = this.oriTableData.find(item => item.id == valT.parentid);
                } else {
                    this.parentDetail = {};
                }
                this.khTarget = this.targetList.filter(item => {
                    if(item.id == valT.targetid){
                        target = item;
                    }
                    return item.levelnum == valT.levelnum
                });
                this.addFlag = 1;
                this.updateData.id = valT.id;
                this.updateData.levelnum = valT.levelnum;
                this.updateData.parentid = valT.parentid;
                this.updateData.target = target;
                this.updateData.name = valT.name;
                this.updateData.remark = valT.remark;
                this.updateData.basepoint = valT.basepoint;
                this.updateData.targettype = valT.targettype;    
                this.updateData.selfeval = valT.selfeval;
                this.updateData.evaltype = valT.evaltype;
                this.updateData.markerdepid = valT.markerdepid;
                this.updateData.minpoint = valT.minpoint;
                this.updateData.maxpoint = valT.maxpoint;
                
                this.ifShowTjDialog = true;
            },

            // 添加确定 判断分值
            sureAdd(){
                if(this.addFlag == 0){
                    this.addNewTarget();
                }else{
                    this.updateModelTarget();
                }
            },

            // 分配
            addNewTarget(){
                this.$refs["formAdd"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {
                            sign: this.$route.query.sign,
                            parentid: this.updateData.parentid,
                            targetid: this.updateData.target.id,
                            name: this.updateData.target.name,
                            remark: this.updateData.remark,
                            basepoint: this.updateData.basepoint,
                            targettype: this.updateData.targettype,
                            levelnum: this.updateData.levelnum,
                            selfeval: this.updateData.selfeval,
                            evaltype: this.updateData.evaltype,
                            minpoint: this.updateData.minpoint,
                            maxpoint: this.updateData.maxpoint
                        };
                        if(this.$route.query.type == 1){
                            params.modelid = this.$route.query.id;
                            if(this.updateData.targettype == 1){
                                params.markerdepid = this.updateData.markerdepid;
                            }
                        } else {
                            params.depid = this.$route.query.id;
                            if(this.updateData.targettype == 2){
                                params.markerdepid = this.$route.query.id;
                            }else if(this.updateData.targettype == 1){
                                params.markerdepid = this.updateData.markerdepid;
                            }else{
                                params.markerdepid = null;
                            }
                        }
                        params = JSON.parse(JSON.stringify(params));
                        let data = common.paramsFormat(params);
                        this.assignFun(data).then(res => {
                            loading.close();
                            console.log("添加确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('添加成功');
                                this.ifShowTjDialog = false;
                                this.seaTableData();
                            } else {
                                this.$message.error('添加失败')
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

            // 修改
            updateModelTarget(){
                this.$refs["formAdd"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {
                            id: this.updateData.id,
                            sign: this.$route.query.sign,
                            targetid: this.updateData.target.id,
                            name: this.updateData.target.name,
                            remark: this.updateData.remark,
                            basepoint: this.updateData.basepoint,
                            targettype: this.updateData.targettype,
                            selfeval: this.updateData.selfeval,
                            evaltype: this.updateData.evaltype,
                            minpoint: this.updateData.minpoint,
                            maxpoint: this.updateData.maxpoint,
                        };

                        if(this.$route.query.type == 1){
                            params.modelid = this.$route.query.id;
                        } else {
                            params.depid = this.$route.query.id;
                            if(this.updateData.targettype == 2){
                                params.markerdepid = this.$route.query.id;
                            }else if(this.updateData.targettype == 1){
                                params.markerdepid = this.updateData.markerdepid;
                            }else{
                                params.markerdepid = null;
                            }
                        }
                        
                        params = JSON.parse(JSON.stringify(params));
                        let data = common.paramsFormat(params);
                        this.updateFun(data).then(res => {
                            loading.close();
                            console.log("修改确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('修改成功');
                                this.ifShowTjDialog = false;
                                this.seaTableData();
                            } else {
                                this.$message.error('修改失败')
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

            // 添加取消
            cancelAdd() {
                this.ifShowTjDialog = false;
            },
            // 添加弹窗关闭回调
            addClose() {
                this.$refs["formAdd"].resetFields();
                this.updateData = {
                    id: null,
                    parentid: null,
                    levelnum: null,
                    target: null,
                    targettype: null,
                    remark:null,
                    basepoint: null,
                    selfeval: null,
                    evaltype: null,
                    depid: null,
                    minpoint: null,
                    maxpoint: null,
                    markerdepid: null
                };
            },

            // 删除
            delSingle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let jlIdStr = val.id.toString();
                    this.deleteFun({ id: jlIdStr }).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.seaTableData();
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                    this.seaTableData();
                }).catch(error => {})
            },

            commitData(){
                this.$confirm('确认提交吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let data;
                    if(this.$route.query.type == 1){
                        data = common.paramsFormat({ modelid: this.$route.query.id });
                    } else {
                        data = common.paramsFormat({ depid: this.$route.query.id });
                    }
                    this.commitFun(data).then(res => {
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
                    this.seaTableData();
                }).catch(error => {})
            },

            // 获取总配置分
            getTotalBaseponit(){
                let point = 0
                this.oriTableData.forEach(item => {
                    if(item.hasson == 0){
                        point += parseInt(item.basepoint);
                    }
                });
                this.totalBasepoint = point;
            },

            // 封装递归树
            getTreeData(dataList){
                var that = this;
                var newData = [];
                let parentArr = dataList.filter(item => !item.parentid);
                parentArr.forEach(item =>{
                    var parObj = {
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
                        markerdepid: item.targettype == 2 ? null : item.markerdepid
                    };
                    this.expandRowKeys.push(parObj.id);
                    if(item.hasson == 1){
                        var obj = that.getTreeChildrenData(parObj, dataList);
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
            getTreeChildrenData(parent, dataList){
                var basepoint = 0;
                var newData = [];
                var that = this;
                dataList.forEach(item =>{
                    if(item.parentid != parent.id){
                        return true;
                    }
                    var child = {
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
                        basepoint: item.basepoint,
                        remark: item.remark,
                        markerdepid: item.targettype == 2 ? null : item.markerdepid
                    }
                    this.expandRowKeys.push(child.id);
                    if(item.hasson == 1){
                        var obj = that.getTreeChildrenData(child, dataList)
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
            } 
        }
    }
</script>
<style scoped>
    .tem-target-container {
        padding: 15px;
    }

    .tem-target-body {
        background-color: #ffffff;
    }

    .tem-target-body-title {
        padding: 15px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .tem-target-back-bread .el-breadcrumb {
        display: inline-block;
        vertical-align: middle;
        margin-left: 10px;
    }

    .tem-target-body-title-left {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 16px;
        color: #595D6E;
    }

    .tem-target-body-title-right-text {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-style: normal;
        font-size: 12px;
        color: #595D6E;
        margin-right: 15px;
    }

    .tem-target-body-title-right-text span {
        font-weight: 700;
        font-size: 18px;
        padding: 5px;
    }

    /* 有子节点且未展开 */
    .el-table/deep/ .el-icon-arrow-right:before {
        /* background: url(../../../assets/u5693.png) no-repeat 0 3px;
        content: "" !important; */
        display: block;
        height: 16px;
        width: 16px;
        font-size: 16px;
        background-size: 16px;
    }

    .el-form/deep/ .el-form-item__label {
        font-size: 13px;
        color: #646C9A;
        background-color: rgba(255, 255, 255, 0);
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
    }

    .khpzx-dialog/deep/ .el-dialog__title {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        color: #333333;
    }

    .hmddialog-bottom {
        margin-top: 10px;
        text-align: center;
    }
</style>