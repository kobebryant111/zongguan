<!-- 指标配置 -->
<template>
    <div class="yggl-container">
        <div class="yggl-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item>
                                    <el-input v-model="paramsForm.name">
                                        <template slot="prefix">考核项：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean" @click="resetForm">
                            <clean-btn size="small" :forms="this.paramsForm" />
                        </div>
                        <div class="head-right-search">
                            <buttonOrange buttonName="搜 索" size="small" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
        </div>

        <div class="yggl-body xkg-borderradius-4px">
            <div class="yggl-body-operation">
                <el-button size="small" @click="importEx">导 入</el-button>
                <el-button size="small" @click="exportEx">导 出</el-button>
                <el-button size="small" @click="addTo">+添加考核项</el-button>
                <!-- <button-fuscia class="xkg-marginleft-10px" buttonName="+添加考核项" :fns="addTo"></button-fuscia> -->
            </div>
            <div class="yggl-body-table">
                <el-table :data="tableData" height="400" @selection-change="handleSelectionChange">
                    <el-table-column label="考核项" width="220" prop="name" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            {{scope.row.name || '-'}}
                        </template>
                    </el-table-column>
                    <el-table-column label="备注" width="220" prop="remark" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            {{scope.row.remark || '-'}}
                        </template>
                    </el-table-column>
                    <el-table-column label="指标制定类型" width="150" prop="targettype">
                        <template slot-scope="scope">
                            <span v-if="!scope.row.targettype">-</span>
                            <span v-else-if="scope.row.targettype == 1">公共指标</span>
                            <span v-else-if="scope.row.targettype == 2">自定义</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="指标制定部门" width="200" prop="depid">
                        <template slot-scope="scope">
                            {{getDepName(scope.row.depid)}}
                        </template>
                    </el-table-column>
                    <el-table-column label="自评" width="80" prop="selfeval">
                        <template slot-scope="scope">
                            <span v-if="!scope.row.selfeval">-</span>
                            <span v-else-if="scope.row.selfeval == 1">是</span>
                            <span v-else-if="scope.row.selfeval == 2">否</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="评价方式" width="100" prop="evaltype">
                        <template slot-scope="scope">
                            <span v-if="!scope.row.evaltype">-</span>
                            <span v-else-if="scope.row.evaltype == 1">档位评分</span>
                            <span v-else-if="scope.row.evaltype == 2">任意分值</span>
                            <span v-else-if="scope.row.evaltype == 3">其他（加分/倒扣分）</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="170" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <button-deepskyblue size="mini" buttonName="修 改" :parentData="scope.row" :fns="update"></button-deepskyblue>
                                <button-oranges buttonName="删 除" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                    :fns="delSingle"></button-oranges>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column></el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
        <!-- 添加/修改 -->
        <el-dialog :visible.sync="ifShowTjDialog" @close="addClose" width="40%">
            <div class="xgContent">
                <el-form class="dialog-form" :model="updateTjData" ref="formAdd" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateTjData.id"></el-input></el-form-item>
                    <el-form-item label="*考核项预设" class="form-title" label-width="200"></el-form-item>
                    <el-form-item label="考核项：" prop="name" :rules="[{ required: true, message: '考核项不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateTjData.name"></el-input>
                    </el-form-item>
                    <el-form-item label="备注：" prop="remark" :rules="[{ required: true, message: '备注不能为空', trigger: 'blur'}]">
                        <el-input v-model="updateTjData.remark"></el-input>
                    </el-form-item>
                    <el-form-item label="*以下为非必填项，填写后将会应用到所有子项" class="form-title" label-width="200"></el-form-item>
                    <el-form-item label="指标类型：" prop="targettype">
                        <el-radio-group v-model="updateTjData.targettype" @change="changeTargettype">
                            <el-radio :label="0">空</el-radio>
                            <el-radio :label="1">公共指标</el-radio>
                            <el-radio :label="2">自定义</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="指标制定部门：" v-if="updateTjData.targettype == 1" prop="depid" :rules="[{ required: true, message: '请选择部门', trigger: 'change'}]">
                        <el-select v-model="updateTjData.depid" filterable>
                            <el-option v-for="item in depDataAll" :key="item.depId" :label="item.depName"
                                :value="parseInt(item.depId)"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="是否需要自评：" prop="selfeval">
                        <el-radio-group v-model="updateTjData.selfeval" :disabled="updateTjData.targettype == 1">
                            <el-radio :label="0">空</el-radio>
                            <el-radio :label="1">是</el-radio>
                            <el-radio :label="2">否</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="评分方式：" prop="evaltype">
                        <el-radio-group v-model="updateTjData.evaltype">
                            <el-radio :label="0">混合</el-radio>
                            <el-radio :label="1">档位评分</el-radio>
                            <el-radio :label="2">任意分值</el-radio>
                            <el-radio :label="3">其他（加分/倒扣分）</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-table v-if="updateTjData.evaltype == 1" :data="depkpiEvalSet" border>
                         <el-radio>空</el-radio>
                        <el-table-column type="index" label="序号" width="55"></el-table-column>
                        <el-table-column label="分档描述" width="260" prop="dictname"></el-table-column>
                        <el-table-column label="系数" width="260" prop="dictid"></el-table-column>
                    </el-table>
                    <el-form-item v-if="updateTjData.evaltype == 3" label="额外区间分：">
                        <el-col :span="4">
                            <el-form-item prop="minpoint" 
                                :rules="[{ required: true, validator: minValidator, trigger: 'blur'}]">
                                <el-input type="number" v-model="updateTjData.minpoint"></el-input>
                            </el-form-item> 
                        </el-col>
                        <el-col class="line" :span="2" :offset="2">-</el-col>
                        <el-col :span="4">
                            <el-form-item prop="maxpoint" 
                                :rules="[{ required: true, validator: maxValidator, trigger: 'blur'}]">
                                <el-input type="number" v-model="updateTjData.maxpoint"></el-input>
                            </el-form-item> 
                        </el-col>
                    </el-form-item> 

                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelAddOrUpdate"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="提 交" :fns="sureAdd" size="mini"></button-orange>
                </div>
            </div>
        </el-dialog>

        <el-dialog title="上传文件" :visible.sync="ifShowEnDialog" @close="scClose">
            <div>
                <div class="upload-butt">
                    <el-upload action="Fake Action" accept=".xlsx, .xls" :file-list="drList" :show-file-list="true"
                        :http-request="changeUpFile" :on-change="handleChange" :on-remove="handleRemove">
                        <el-button size="small" type="primary">
                            <i class="el-icon-upload2"></i>
                            <span>{{drList.length == 0 ? '上传文件' : '重新上传'}}</span>
                        </el-button>
                    </el-upload>
                </div>

                <div class="dia-bottom">
                    <el-row :gutter="10" type="flex" justify="end">
                        <el-col :span="1.5">
                            <button-common buttonName="取 消" size="mini" :fns="cancelEnter"></button-common>
                        </el-col>
                        <el-col :span="1.5">
                            <button-orange buttonName="确 定" size="mini" :fns="sureEnter"></button-orange>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import buttonFuscia from '@/components/eleButton/buttonFuscia';
    import buttonDeepskyblue from '@/components/eleButton/buttonDeepskyblue';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { getEosDictEntrys } from "@/api/hykh/pjsz";
    import { queryPagerList, add, update, deleteTarget, importExcel, exportDepkpiTarget } from "@/api/zzkhApi/target";
    export default {
        name: 'target',
        components: { cleanBtn, buttonOrange, buttonFuscia, buttonDeepskyblue, buttonOranges, pager, buttonCommon },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0, // 总数
                ifShowTjDialog: false, //是否显示添加弹窗
                sign: null, //所属为部门
                paramsForm: { //搜索框表单
                    name: null
                },
                tableData: [],
                depDataAll: [],
                depkpiEvalSet: [], // 考核评级档位
                addFlag: 0,
                ifShowEnDialog: false, //是否显示导入弹窗
                drFileData: {}, //导入文件信息
                drList: [], //导入列表
                updateTjData: {
                    id: null,
                    name: null,
                    remark: null,
                    targettype: 0,
                    depid: null,
                    selfeval: 0,
                    evaltype: 0,
                    minpoint: null,
                    maxpoint: null
                }, //添加数据
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
                }
            }
        },
        props:{
            level: Number //框架层级
        },
        created(){
            if(this.$route.name == "ejbTarget"){
                this.sign = 1;
            }else if(this.$route.name == "zxTarget"){
                this.sign = 2;
            }else if(this.$route.name == "yjbTarget"){
                this.sign = 3;
            }
        },
        mounted() {
            this.getDep();
            this.getEvalSet();
            this.submitBtn();
        },
        methods: {
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
            
            // 获取二级部门名称
            getDepName(depid){
                let dep = this.depDataAll.find(item => item.depId ==depid)
                return dep ? dep.depName : '-';
            },

            // 获取所有二级部门
            getDep() {
                const loading = common.loading(this);
                if(this.sign == 1){
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
                                tt.depId = tt.id
                                return tt.id != 61;
                            });
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    });
                }
            },

            // 查询列表数据
            seaTableData(val) {
                if(!val){
                    val = {};
                }
                val.levelnum = this.level;
                val.sign = this.sign;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryPagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.tableData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.size = size;
                params.index = index;
                params.name = this.paramsForm.name;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.name = this.paramsForm.name;
                params.size = this.size;
                params.index = this.index;
                this.seaTableData(params);
            },

            // 改变指标类型
            changeTargettype(val){
                if(val == 1 ){
                    this.updateTjData.selfeval = 2;
                }
                
            },

            // 重置
            resetForm() {
            },

            // 添加
            addTo() {
                this.ifShowTjDialog = true;
                this.addFlag = 0;
                this.updateTjData.targettype = 0;
                this.updateTjData.selfeval = 0;
                this.updateTjData.evaltype = 0;
            },
            
            // 修改
            update(val) {
                this.ifShowTjDialog = true;
                val.targettype = val.targettype ? val.targettype : 0;
                val.selfeval = val.selfeval ? val.selfeval : 0;
                val.evaltype = val.evaltype ? val.evaltype : 0;
                this.updateTjData = JSON.parse(JSON.stringify(val));
                // this.updateTjData.targettype = this.updateTjData.targettype ? this.updateTjData.targettype + "" : "0";
                // this.updateTjData.selfeval = this.updateTjData.selfeval ? this.updateTjData.selfeval + ""  : "0";
                // this.updateTjData.evaltype = this.updateTjData.evaltype ? this.updateTjData.evaltype + ""  : "0";
                this.addFlag = 1;   
            },
            // 添加确定
            sureAdd() {
                this.$refs["formAdd"].validate(valid => {
                    if (!valid) {
                        this.$message.error('请填写必填项');
                        return false;
                    } 
                    const loading = common.loading(this);
                    let params = {};
                    params.levelnum = this.level;
                    params.sign = this.sign;
                    params.name = this.updateTjData.name;
                    params.remark = this.updateTjData.remark ? this.updateTjData.remark : "";
                    params.targettype = this.updateTjData.targettype ? this.updateTjData.targettype : null;
                    if(!this.updateTjData.targettype || this.updateTjData.targettype == 2){
                        params.depid = null;
                    }else{
                        params.depid = this.updateTjData.depid
                    }
                    params.selfeval = this.updateTjData.selfeval ? this.updateTjData.selfeval : null;
                    params.evaltype = this.updateTjData.evaltype ? this.updateTjData.evaltype : null;
                    params.minpoint = this.updateTjData.minpoint;
                    params.maxpoint = this.updateTjData.maxpoint;
                    params = JSON.parse(JSON.stringify(params));
                    if(this.addFlag === 0){
                        let data = common.paramsFormat(params);
                        add(data).then(res => {
                            loading.close();
                            console.log("添加确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('添加成功');
                                this.ifShowTjDialog = false;
                                this.$refs["formAdd"].resetFields();
                                this.submitBtn();
                            } else {
                                this.$message.error('添加失败')
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    }else{
                        params.id = this.updateTjData.id;
                        let data = common.paramsFormat(params);
                        update(data).then(res => {
                            loading.close();
                            console.log("修改确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('修改成功');
                                this.ifShowTjDialog = false;
                                this.submitBtn();
                                this.$refs["formAdd"].resetFields();
                            } else {
                                this.$message.error('修改失败')
                            }
                        }).catch(error => {
                            loading.close();
                            this.$message.error(error)
                        })
                    }
                });
            },

            // 添加/修改取消
            cancelAddOrUpdate() {
                this.ifShowTjDialog = false;
                this.$refs["formAdd"].resetFields();
            },

            // 添加弹窗关闭回调
            addClose() {
                this.updateTjData = {
                    id: null,
                    name: null,
                    remark: null,
                    targettype: null,
                    depid: null,
                    selfeval: null,
                    evaltype: null,
                    minpoint: null,
                    maxpoint: null
                };
            },

            // 表格选中变化
            handleSelectionChange(val) {
                this.selectRow = val;
            },

            // 删除（单）
            delSingle(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let jlIdStr = val.id.toString();
                    deleteTarget({ id: jlIdStr }).then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.submitBtn();
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            },

            // 导入
            importEx() {
                this.ifShowEnDialog = true;
            },

            // 取消导入
            cancelEnter() {
                this.ifShowEnDialog = false;
            },

            // 关闭导入框
            scClose() {
                this.drList = [];
            },

            // on-change
            handleChange(file, fileList) {
                console.log('on-change', fileList);
                this.drList = [fileList[fileList.length - 1]];
            },

            // on-remove
            handleRemove() {
                this.drList = [];
            },

            // 上传变化
            changeUpFile(file) {
                console.log('上传批次变化', file, this.drList)
                this.drFileData = file.file;
            },

            sureEnter(){
                if (this.drList.length == 0) {
                    this.$message.error('请上传文件');
                    return
                }
                const loading = common.loading(this);
                var formData = new FormData();
                formData.append("file", this.drFileData);
                importExcel(formData).then(res => {
                    loading.close();
                    console.log("确认导入", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('导入成功');
                        this.ifShowEnDialog = false;
                        this.submitBtn();
                    } else {
                        this.$message.error('导入失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 导出
            exportEx() {
                const loading = common.loading(this);
                let val = {};
                val.sign = this.sign;
                let params = common.paramsFormat(val);
                const self = this;
                exportDepkpiTarget(params).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "指标.xls");
                });
            },
        },
        watch: {
            level(val, oldVal){
                let params = {};
                params.name = this.paramsForm.name;
                params.size = this.size;
                this.index = 1;
                params.index = this.index;
                this.seaTableData(params);
            }
        }
    }
</script>
<style scoped>
    .yggl-head-content {
        background: #FFFFFF;
        padding: 15px 0 15px 15px;
        margin-bottom: 15px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        -webkit-justify-content: space-between;
        justify-content: space-between;
    }

    .head-left {
        flex: 1;
    }

    .head-right {
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        padding-right: 15px;
        width: 145px;
        display: flex;
    }

    .head-right-clean {
        flex: 1;
    }

    .head-right-search {
        padding-left: 10px;
        flex: 1;
    }

    .yggl-body {
        padding: 15px;
        background: #FFFFFF;
    }

    .yggl-body-operation {
        text-align: right;
        margin-bottom: 10px;
    }

    .pager-zj {
        text-align: center;
    }

    .hmddialog-bottom {
        text-align: center;
        margin-top: 10px;
    }

    /* .hmd-row-block p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        margin: 8px 0;
    } */
</style>

<style>
    .yggl-head-content .el-input--prefix .el-input__inner {
        padding-left: 55px;
    }

    .yggl-head-content .el-form-item {
        margin-bottom: 0;
    }

    .upload-butt .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }

    .xgContent .el-cascader {
        display: block;
    }
    .form-title .el-form-item__label{
        font-size: 16px;
    }
</style>