<!-- 部门模板分配 -->
<template>
    <div class="yggl-container">
        <div class="yggl-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left">
                        <el-row>
                            <el-col :span="5">
                                <el-form-item>
                                    <el-select filterable clearable v-model="paramsForm.centerid" @change="seaCenterChange">
                                        <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                            :value="item.id"></el-option>
                                        <template slot="prefix">中心：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col v-if="this.sign == 1" :span="5">
                                <el-form-item>
                                    <el-select filterable clearable v-model="paramsForm.depid">
                                        <el-option v-for="item in depData" :key="item.depId" :label="item.depName"
                                            :value="item.depId"></el-option>
                                        <template slot="prefix">二级部：</template>
                                    </el-select>
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
                <el-button type="primary" @click="jumpModelManager">模板库({{modelTotal}})</el-button>
            </div>
            <div class="yggl-body-table">
                <el-table :data="tableData" height="470" @selection-change="handleSelectionChange">
                    <el-table-column type="index" label="序号" width="55"></el-table-column>
                    <el-table-column label="中心" width="300" prop="centername"></el-table-column>
                    <el-table-column v-if="sign == 1" label="二级部" width="300" prop="depname"></el-table-column>
                    <el-table-column label="当前使用模板" width="200" prop="modelname"></el-table-column>
                    <el-table-column label="提交状态" width="100" prop="commitflag">
                        <template slot-scope="scope">
                            <span v-if="scope.row.commitflag == 1">生效</span>
                            <span v-else>草稿</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="220" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <el-button type="success" size="mini" @click="update(scope.row)">更换模板</el-button>
                                <el-button type="primary" size="mini" @click="indicatorItemsJump(scope.row)">指标项配置</el-button>
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
        <el-dialog title="选择模板" :visible.sync="ifShowTjDialog" @close="addClose" width="40%">
            <div class="xgContent">
                <el-form class="dialog-form" :model="updateData" ref="formAdd" label-width="120px" label-position="left">
                    <el-form-item v-show="false"><el-input v-model="updateData.id"></el-input></el-form-item>
                    <el-form-item v-show="false"><el-input v-model="updateData.depid"></el-input></el-form-item>
                    <el-form-item v-show="false"><el-input v-model="updateData.centerid"></el-input></el-form-item>
                    <el-form-item label="中心：" prop="centername"><el-input v-model="updateData.centername" disabled></el-input></el-form-item>
                    <el-form-item v-if="this.sign == 1" label="二级部：" prop="depname"><el-input v-model="updateData.depname" disabled></el-input></el-form-item>
                    <el-form-item label="模板：" prop="modelid">
                        <el-select v-model="updateData.modelid" filterable clearable>
                            <el-option v-for="item in modelData" :key="item.id" :label="item.name" 
                                :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelAddOrUpdate"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="提 交" size="mini" :fns="sureAdd"></button-orange>
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
    import { queryDepModelPagerList, changeDepModel } from "@/api/zzkhApi/depModel";
    import { queryModelList } from "@/api/zzkhApi/model";
    import pager from "@/components/Pager";
    export default {
        name: 'depModel',
        components: { cleanBtn, buttonOrange, buttonFuscia, buttonDeepskyblue, buttonOranges, pager, buttonCommon },
        data() {
            return {
                size: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                totalNumber: 0, // 表格总数
                ifShowTjDialog: false, //是否显示添加弹窗
                sign: null, // 所属为部门
                paramsForm: { // 搜索框表单
                    centerid: null,
                    depid: null
                },
                modelTotal: 0,//模板库总数
                tableData: [],
                centerData: [],
                modelData: [],
                depData: [],
                updateData: { //添加数据
                    centerid: null,
                    depid: null,
                    modelid: null
                },
            }
        },
        created(){
            if(this.$route.name == "ejbModel"){
                this.sign = 1;
            }else if(this.$route.name == "zxModel"){
                this.sign = 2;
            }else if(this.$route.name == "yjbModel"){
                this.sign = 3;
            }
        },
        mounted() {
            this.getSeaDep(); // 获取所有二级部门
            this.getCenter(); // 获取所有中心
            this.getAllModel(); // 获取所有模板
            this.submitBtn(); // 获取表格数据
        },
        methods: {
            // 获取所有模板
            getAllModel(){
                const loading = common.loading(this);
                let data = common.paramsFormat({sign: this.sign});
                queryModelList(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.modelData = res.data.results;
                        this.modelTotal = res.data.total;
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 搜索中心改变
            seaCenterChange(val) {
                this.paramsForm.depid = null;
                this.getSeaDep(val);
            },

            // 获取中心
            getCenter() {
                queryList().then(res => {
                    if (res.data.status == 200) {
                        this.centerData = res.data.results.filter(li => {
                            return li.id != 61
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 根据中心获取部门
            getSeaDep(id) {
                var param = {};
                if(id){
                    param.centerId = id;
                }
                const loading = common.loading(this);
                queryDeptList(param).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        var fild = res.data.results;
                        this.depData = fild.filter(tt => {
                            tt.depName = tt.depName.replace('总行金融科技部-', "");
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 查询列表数据
            seaTableData(val) {
                if (!val) {
                    val = {};
                }
                val.sign = this.sign;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryDepModelPagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.tableData = res.data.results.filter(tt => {
                            if(tt.depname){
                                tt.depname = tt.depname.replace('总行金融科技部-', "");
                            }
                            if(tt.centername){
                                tt.centername = tt.centername.replace('总行金融科技部-', "");
                            }
                            if(!tt.modelname){
                                tt.modelname = "未使用模板";
                            }
                            return tt;
                        });
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
                params.centerid = this.paramsForm.centerid;
                params.depid = this.paramsForm.depid;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询
            submitBtn: function () {
                let params = {};
                params.centerid = this.paramsForm.centerid;
                params.depid = this.paramsForm.depid;
                params.size = this.size;
                params.index = this.index;
                this.seaTableData(params);
            },

            // 重置
            resetForm() {
                this.getSeaDep();
            },

            // 修改
            update(val) {
                this.updateData = JSON.parse(JSON.stringify(val));
                this.ifShowTjDialog = true;
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
                    if(this.sign == 1){
                        params.depid = this.updateData.depid;
                    } else {
                        params.depid = this.updateData.centerid;
                    }
                    params.modelid = this.updateData.modelid;

                    params = JSON.parse(JSON.stringify(params));
                    let data = common.paramsFormat(params);
                    changeDepModel(data).then(res => {
                        loading.close();
                        console.log("更换模板确定", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('更换成功');
                            this.ifShowTjDialog = false;
                            this.submitBtn();
                        } else {
                            this.$message.error('更换失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                });
            },

            // 添加/修改取消
            cancelAddOrUpdate() {
                this.ifShowTjDialog = false;
                this.$refs["formAdd"].resetFields();
            },

            // 添加弹窗关闭回调
            addClose() {
                this.$refs["formAdd"].resetFields();
            },

            // 表格选中变化
            handleSelectionChange(val) {
                // console.log("分页表格选中", val)
                this.selectRow = val;
            },

            // 指标项配置页面跳转
            indicatorItemsJump(val) {
                if(this.sign == 1){
                    this.$router.push({ path: '/targetAssign', query: {id: val.depid, sign: this.sign, name: val.depname, type: 2}});
                } else {
                    this.$router.push({ path: '/targetAssign', query: {id: val.centerid, sign: this.sign, name: val.centername, type: 2}});
                }
            },

            // 模板库页面跳转
            jumpModelManager(val) {
                this.$router.push({path:'/modelManager', query: {sign: this.sign}})
            }

        },
        watch: {},
    }
</script>
<style scoped>
    .yggl-container {
        padding: 15px;
    }

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
        text-align: right;
    }

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
    .butt-deepskyblue button {
        width: 76px !important;
    }
</style>