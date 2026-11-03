<!-- 分配 -->
<template>
    <div class="tem-target-container">
        <div class="tem-target-body">
            <div class="tem-target-body-title">
                <div class="tem-target-back-bread">
                    <el-button size="small" icon="el-icon-arrow-left" @click="goBack">返回</el-button>
                </div>
                <div>
                    <span class="tem-target-body-title-right-text">已配置基准<span>{{totalBasepoint}}</span>分</span>
                    <el-button type="warning" @click="commitData">提 交</el-button>
                    <el-button type="primary" @click="addTo(null)">添加一级考核项</el-button>
                </div>
            </div>
            <el-table :data="tableData" row-key="id" :tree-props="{children: 'children'}" :expand-row-keys="expandRowKeys">
                <el-table-column label="考核项" prop="name">
                    <template slot-scope="scope">
                        <template v-if="scope.row.levelnum == 1 && scope.row.hasson == 0">&emsp;&nbsp;&nbsp;</template>
                        {{scope.row.name}}
                    </template>
                </el-table-column>
                <el-table-column label="基准分" prop="basepoint"></el-table-column>
                <el-table-column label="自评人"  prop="selfevalusername"></el-table-column>
                <el-table-column label="操作" fixed="right">
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
                    <el-form-item label="考核项" prop="name" :rules="[{ required: true, message: '考核项不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateData.name" placeholder="考核项"></el-input>
                    </el-form-item>
                    <el-form-item label="基准分" prop="basepoint" :rules="[{ required: true, validator: isNumber, trigger: 'blur'}]">
                        <el-input placeholder="基准分" v-model="updateData.basepoint"></el-input>
                    </el-form-item>
                    <el-form-item label="*以下为非必填项，填写后将会应用到所有子项" class="form-title" label-width="200"></el-form-item>
                    <el-form-item label="制定/自评人" prop="selfevaluser">
                        <el-cascader placeholder="制定/自评人" ref="cascader" :options="kjPeo_options" 
                            :props="kjPeoParams" v-model="updateData.selfevaluser" clearable :disabled="!!this.parentDetail.selfevaluser"
                            :show-all-levels="false" filterable></el-cascader>
                    </el-form-item>
                    <el-form-item label="初审人" prop="firstcheckuser">
                        <el-cascader placeholder="初审人" ref="cascader" :options="kjPeo_options"
                            :props="kjPeoParams" v-model="updateData.firstcheckuser" clearable :disabled="!!this.parentDetail.firstcheckuser"
                            :show-all-levels="false" filterable></el-cascader>
                    </el-form-item>
                    <el-form-item label="终审人" prop="secondcheckuser">
                        <el-cascader placeholder="终审人" ref="cascader" :options="kjPeo_options"
                            :props="kjPeoParams" v-model="updateData.secondcheckuser" clearable :disabled="!!this.parentDetail.secondcheckuser"
                            :show-all-levels="false" filterable></el-cascader>
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
    import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
    import { getEosDepKpiGrade } from "@/api/depAssessment/depAssessmentControl";
    import { queryList, addDepDetail, updateDepDetail, deleteDepDetail, commitDepDetail } from "@/api/zzkhApi/yjb";
    import { commitDepTarget } from "@/api/zzkhApi/depmodelDetail";
    export default {
        name: 'targetAssign',
        data() {
            return {
                addFlag: 0, // 修改删除标志
                depDataAll: [],
                tableData: [],
                oriTableData: [],
                totalBasepoint: 0,
                ifShowTjDialog: false, //是否显示添加弹窗
                parentDetail: {
                    selfevaluser: null,  
                    firstcheckuser: null,
                    secondcheckuser: null
                }, // 当前修改项的父级
                updateData: {
                    id: null,
                    parentid: null,
                    levelnum: null,
                    name: null,
                    basepoint: null,
                    selfevaluser: null,
                    firstcheckuser: null,
                    secondcheckuser: null
                }, //添加数据
                diaTitle: '', //弹窗标题
                kjPeoParams: { //级联选择器配置信息
                    label: 'text',
                    value: 'pcode',
                    children: 'children',
                    emitPath: false,
                },
                kjPeo_options: [],
                isNumber: (rule, value, callback) => {
                    if (!value && value != 0) {
                        callback(new Error('基准分不能为空'));
                    }
                    if (!/^\d+$/.test(value)) {
                        callback(new Error('请输入正整数,值为0-100'));
                    }
                    callback();
                },
                maxLevel: 0, // 框架层级
                expandRowKeys: [], // 需要展开的树
            }
        },
        mounted() {
            this.searchEpmlo();
            this.getEosDepKpiGrade();
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
                        this.maxLevel = res.data.results.tec;
                    }
                }).catch(error => {
                    loading.close();
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
            
            //获取表格数据
            seaTableData(val) {
                if (!val) {
                    val = {};
                }
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryList(data).then(res => {
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

            // 添加弹窗
            addTo(valT) {
                this.parentDetail = {
                    selfevaluser: null,  
                    firstcheckuser: null,
                    secondcheckuser: null
                };
                let numList = ["", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                if(valT){
                    var num = parseInt(valT.levelnum);
                    this.diaTitle = '添加'+ numList[num + 1] +'级考核项';
                    this.updateData.parentid = valT.id;
                    this.updateData.levelnum = num + 1;

                    // 赋值父级指标数据
                    this.parentDetail = valT;
                    this.updateData.selfevaluser = valT.selfevaluser;
                    this.updateData.firstcheckuser = valT.firstcheckuser;
                    this.updateData.secondcheckuser = valT.secondcheckuser;
                }else{
                    this.diaTitle = '添加一级考核项';
                    this.updateData.parentid = null;
                    this.updateData.levelnum = 1;
                }
                this.addFlag = 0;
                this.ifShowTjDialog = true;
            },

            // 修改弹窗
            update(valT){
                let numList = ["", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                this.parentDetail = {
                    selfevaluser: null,  
                    firstcheckuser: null,
                    secondcheckuser: null
                };
                let target;
                this.diaTitle = '修改'+ numList[valT.levelnum] +'级考核项';
                if(valT.levelnum > 1){
                    this.parentDetail = this.oriTableData.find(item => item.id == valT.parentid);
                } else {
                    this.parentDetail = {};
                }
                this.addFlag = 1;
                this.updateData.id = valT.id;
                this.updateData.levelnum = valT.levelnum;
                this.updateData.parentid = valT.parentid;
                this.updateData.name = valT.name;
                this.updateData.basepoint = valT.basepoint;
                this.updateData.selfevaluser = valT.selfevaluser;    
                this.updateData.firstcheckuser = valT.firstcheckuser;
                this.updateData.secondcheckuser = valT.secondcheckuser;
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
                            parentid: this.updateData.parentid,
                            levelnum:  this.updateData.levelnum,
                            name: this.updateData.name,
                            basepoint:  this.updateData.basepoint,
                            selfevaluser:  this.updateData.selfevaluser,
                            firstcheckuser:  this.updateData.firstcheckuser,
                            secondcheckuser:  this.updateData.secondcheckuser
                        };
                        params = JSON.parse(JSON.stringify(params));
                        let data = common.paramsFormat(params);
                        addDepDetail(data).then(res => {
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
                            parentid: this.updateData.parentid,
                            levelnum:  this.updateData.levelnum,
                            name: this.updateData.name,
                            basepoint:  this.updateData.basepoint,
                            selfevaluser:  this.updateData.selfevaluser,
                            firstcheckuser:  this.updateData.firstcheckuser,
                            secondcheckuser:  this.updateData.secondcheckuser
                        }; 
                        params = JSON.parse(JSON.stringify(params));
                        let data = common.paramsFormat(params);
                        updateDepDetail(data).then(res => {
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
                    name: null,
                    basepoint: null,
                    selfevaluser: null,
                    firstcheckuser: null,
                    secondcheckuser: null
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
                    deleteDepDetail({ id: jlIdStr }).then(res => {
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
                    commitDepDetail({}).then(res => {
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
                        name: item.name,
                        basepoint: item.basepoint,
                        selfevaluser: item.selfevaluser,
                        selfevalusername: item.selfevalusername,
                        firstcheckuser: item.firstcheckuser,
                        secondcheckuser: item.secondcheckuser
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
                        parentid: item.parentid,
                        hasson: item.hasson,
                        levelnum: item.levelnum,
                        name: item.name,
                        basepoint: item.basepoint,
                        selfevaluser: item.selfevaluser,
                        selfevalusername: item.selfevalusername,
                        firstcheckuser: item.firstcheckuser,
                        secondcheckuser: item.secondcheckuser
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

    .el-form/deep/ .el-cascader {
        width: 100% !important;
    }

    .hmddialog-bottom {
        margin-top: 10px;
        text-align: center;
    }
</style>