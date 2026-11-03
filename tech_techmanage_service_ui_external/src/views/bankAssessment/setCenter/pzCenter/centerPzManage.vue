<!-- 中心管理 -->
<template>
    <div class="yggl-container">
        <div class="yggl-head">
            <el-form inline>
                <div class="yggl-head-content xkg-borderradius-4px">
                    <div class="head-left organization-head-left">
                        <el-row>
                            <el-col :span="10">
                                <el-form-item>
                                    <el-select filterable v-model="paramsForm.centerid" multiple>
                                        <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                            :value="item.id"></el-option>
                                        <template slot="prefix">中心：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean">
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
                <el-button size="small" @click="synchr">同 步</el-button>
                <el-button size="small" @click="enterEx">导 入</el-button>
                <el-button size="small" @click="exportEx">导 出</el-button>
                <button-fuscia class="xkg-marginleft-10px" buttonName="添 加" :fns="addTo"></button-fuscia>
                <button-deepskyblue size="small" class="xkg-marginleft-10px" buttonName="修 改" :fns="corrMo"></button-deepskyblue>
                <button-oranges buttonName="删 除" class="xkg-marginleft-10px" size="small" :fns="delMo"></button-oranges>
            </div>
            <div class="yggl-body-table">
                <el-table :data="staffData" height="510" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" min-width="10%"></el-table-column>
                    <el-table-column type="index" label="序号" min-width="10%"></el-table-column>
                    <el-table-column label="中心" min-width="20%" prop="centername"></el-table-column>
                    <el-table-column label="中心总工号" min-width="20%" prop="centercharge"></el-table-column>
                    <el-table-column label="中心总" min-width="20%" prop="centerchargename"></el-table-column>
                    <el-table-column label="操作" min-width="20%" fixed="right">
                        <template slot-scope="scope">
                            <div>
                                <button-deepskyblue size="mini" buttonName="修 改" :parentData="scope.row" :fns="corrSingle"></button-deepskyblue>
                                <button-oranges buttonName="删 除" class="xkg-marginleft-10px" size="mini" :parentData="scope.row"
                                    :fns="delSingle"></button-oranges>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>

                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>

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
        <el-dialog title="组织管理" :visible.sync="ifShowXgMoDialog" @close="xgMoClose">
            <div class="xgContent">
                <el-form :model="updateMoData" ref="formXgMo">
                    <div>
                        <el-form-item label="组织类型">
                            <!-- <el-radio-group v-model="updateMoData.sign" :disabled="true">
                                <el-radio v-for="item in organ_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                            </el-radio-group> -->
                            <el-radio v-model="radioType" label="1">中心</el-radio>
                        </el-form-item>
                        <!-- <div v-if="updateMoData.sign == '1'">
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>中心 *</p>
                                            <el-form-item prop="centerid" :rules="[{ required: true, message: '请选择中心', trigger: 'change'}]">
                                                <el-select placeholder="中心" filterable v-model="updateMoData.centerid">
                                                    <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                        :value="item.id"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>
                                            <p>中心分管总 *</p>
                                            <el-form-item prop="centercharge" :rules="[{ required: true, message: '请选择中心分管总', trigger: 'change'}]">
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateMoData.centercharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>部门主持经理 *</p>
                                            <el-form-item prop="director" :rules="[{ required: true, message: '请选择部门主持经理', trigger: 'change'}]">
                                                <el-cascader placeholder="姓名" ref="cascader" :options="kjPeo_options"
                                                    :props="kjPeoParams" v-model="updateMoData.director"
                                                    :show-all-levels="false" filterable></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>
                                            <p>部门分管总</p>
                                            <el-form-item prop="depcharge" :rules="[{ required: true, message: '请选择部门分管总', trigger: 'change'}]">
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateMoData.depcharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                        </div> -->
                        <div>
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>中心分管总 *</p>
                                            <el-form-item prop="centercharge" :rules="[{ required: true, message: '请选择中心分管总', trigger: 'change'}]">
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateMoData.centercharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>

                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                        </div>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelXgMo"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="提 交" size="mini" :fns="sureXgMo"></button-orange>
                </div>
            </div>
        </el-dialog>
        <!-- 单人 -->
        <el-dialog title="组织管理" :visible.sync="ifShowXgSingDialog" @close="xgSingClose">
            <div class="xgContent">
                <el-form :model="updateSingData" ref="formXgSing">
                    <div>
                        <el-form-item label="组织类型">
                            <!-- <el-radio-group v-model="updateSingData.sign" :disabled="true">
                                <el-radio v-for="item in organ_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                            </el-radio-group> -->
                            <el-radio v-model="radioType" label="1">中心</el-radio>
                        </el-form-item>
                        <!-- <div v-if="updateSingData.sign == '1'">
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>中心</p>
                                            <el-form-item>
                                                <el-select placeholder="中心" filterable v-model="updateSingData.centerid">
                                                    <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                        :value="item.id"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>
                                            <p>中心分管总</p>
                                            <el-form-item>
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateSingData.centercharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>主持经理</p>
                                            <el-form-item>
                                                <el-cascader placeholder="姓名" ref="cascader" :options="kjPeo_options"
                                                    :props="kjPeoParams" v-model="updateSingData.director"
                                                    :show-all-levels="false" filterable></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>
                                            <p>分管总</p>
                                            <el-form-item>
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateSingData.depcharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                        </div> -->
                        <div>
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>中心分管总</p>
                                            <el-form-item>
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateSingData.centercharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>

                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                        </div>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelXgSing"></button-common>
                    <button-orange class="xkg-marginleft-10px" buttonName="提 交" size="mini" :fns="sureXgSing"></button-orange>
                </div>
            </div>
        </el-dialog>
        <!-- 添加 -->
        <el-dialog title="组织管理" :visible.sync="ifShowTjDialog" @close="addClose">
            <div class="xgContent">
                <el-form :model="updateTjData" ref="formAdd">
                    <div>
                        <el-form-item label="组织类型">
                            <!-- <el-radio-group v-model="updateTjData.sign" @change="zzTypeChange">
                                <el-radio v-for="item in organ_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                            </el-radio-group> -->
                            <el-radio v-model="radioType" label="1">中心</el-radio>
                        </el-form-item>
                        <!-- <div v-if="updateTjData.sign == '1'">
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>中心 *</p>
                                            <el-form-item prop="centerid" :rules="[{ required: true, message: '请选择中心', trigger: 'change'}]">
                                                <el-select placeholder="中心" filterable v-model="updateTjData.centerid">
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
                                                <el-select placeholder="部门" filterable v-model="updateTjData.depid">
                                                    <el-option v-for="item in depDataAll" :key="item.id" :label="item.text"
                                                        :value="item.id"></el-option>
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
                                            <p>部门主持经理 *</p>
                                            <el-form-item prop="director" :rules="[{ required: true, message: '请选择部门主持经理', trigger: 'change'}]">
                                                <el-cascader placeholder="姓名" ref="cascader" :options="kjPeo_options"
                                                    :props="kjPeoParams" v-model="updateTjData.director"
                                                    :show-all-levels="false" filterable></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>
                                            <p>部门分管总 *</p>
                                            <el-form-item prop="depcharge" :rules="[{ required: true, message: '请选择部门分管总', trigger: 'change'}]">
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateTjData.depcharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>中心分管总 *</p>
                                            <el-form-item prop="centercharge" :rules="[{ required: true, message: '请选择中心分管总', trigger: 'change'}]">
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateTjData.centercharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>

                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                        </div> -->
                        <div>
                            <div class="hmd-row-block">
                                <el-row :gutter="50">
                                    <el-col :span="12">
                                        <div>
                                            <p>中心 *</p>
                                            <el-form-item prop="centerid" :rules="[{ required: true, message: '请选择中心', trigger: 'change'}]">
                                                <el-select placeholder="中心" filterable v-model="updateTjData.centerid">
                                                    <el-option v-for="item in centerData" :key="item.id" :label="item.centername"
                                                        :value="item.id"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                    <el-col :span="12">
                                        <div>
                                            <p>中心分管总 *</p>
                                            <el-form-item prop="centercharge" :rules="[{ required: true, message: '请选择中心分管总', trigger: 'change'}]">
                                                <el-cascader ref="cascader" :options="kjPeo_options" :props="kjPeoParams"
                                                    v-model="updateTjData.centercharge" :show-all-levels="false"
                                                    filterable @change="kjPeoChange"></el-cascader>
                                            </el-form-item>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                        </div>
                    </div>
                </el-form>
                <div class="hmddialog-bottom">
                    <button-common buttonName="取 消" size="mini" :fns="cancelAdd"></button-common>
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
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { queryPagerList, exportKpiDepRoles, importExcel, insertPpmKpiDepRoles, deletePpmKpiDepRoleList, updatePpmKpiDepRoleDto, updatePpmKpiRoleDtos, add } from "@/api/integrated/hykhOrg";
    import pager from "@/components/Pager";
    import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
    export default {
        name: 'centerPzManage',
        components: { cleanBtn, buttonOrange, buttonFuscia, buttonDeepskyblue, buttonOranges, pager, buttonCommon },
        data() {
            return {
                paramsForm: {
                    centerid: null,
                },
                centerData: [],
                depDataAll: [],
                organ_options: [],
                radioType: '1',
                staffData: [], //员工数据
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                ifShowEnDialog: false, //是否显示导入弹窗
                drFileData: {}, //导入文件信息
                drList: [], //导入列表
                selectRow: [], //表格选中行
                ifShowXgMoDialog: false, //修改多人弹窗
                ifShowXgSingDialog: false, //修改单人弹窗
                ifShowTjDialog: false, //是否显示添加弹窗
                updateMoData: {
                    centerid: null,
                    director: null,
                    depcharge: null,
                    centercharge: null,
                    sign: '1',
                }, //修改多人数据
                updateSingData: {}, //修改单人数据
                updateTjData: {
                    director: null,
                    centerid: null,
                    depid: null,
                    depcharge: null,
                    centercharge: null,
                    sign: '1',
                }, //添加数据
                kjPeoParams: { //级联选择器配置信息
                    label: 'text',
                    value: 'pcode',
                    children: 'children',
                    emitPath: false,
                },
                kjPeo_options: [],
            }
        },
        mounted() {
            this.getCenter();
            this.getKjDep();
            this.getDic();
            this.seaTableData({});
            this.searchEpmlo();
        },
        methods: {
            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KHBMJSBZ" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //组织类型
                    this.organ_options = data.filter(item => {
                        return item.dicttypeid == "KHBMJSBZ";
                    });
                    return data;
                });
            },

            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                val.sign = '2';
                let data = common.paramsFormat(val);
                queryPagerList(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.staffData = res.data.results.filter(li => {
                            if (li.depname != undefined) {
                                li.depname = li.depname.replace('总行金融科技部-', '');
                            }
                            return li
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
                params.centerid = this.paramsForm.centerid;
                params.centerid = params.centerid.join(',');
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 查询
            submitBtn: function () {
                console.log("查询测试", this.paramsForm)
                let params = {};
                params.centerid = this.paramsForm.centerid;
                if (typeof params.centerid != 'string' && params.centerid != null) {
                    params.centerid = params.centerid.join(',');
                }
                this.seaTableData(params);
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

            // 获取金融科技部门
            getKjDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params).then(res => {
                    if (res.data.status == 200) {
                        var fild = res.data.results[0].children;
                        this.depDataAll = fild.filter(tt => {
                            tt.text = tt.text.replace('总行金融科技部-', "");
                            tt.id = tt.id.replace('dep', '');
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 组织类型改变
            zzTypeChange() {
                this.$refs["formAdd"].resetFields();
            },

            // 添加
            addTo() {
                this.ifShowTjDialog = true;
            },

            // 添加确定
            sureAdd() {
                console.log("添加确定", this.updateTjData)
                this.$refs["formAdd"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        if (this.updateTjData.sign == 1) {
                            params.centerid = this.updateTjData.centerid;
                            params.depcharge = this.updateTjData.depcharge;
                            params.director = this.updateTjData.director;
                            params.depid = this.updateTjData.depid;
                            params.centercharge = this.updateTjData.centercharge;
                        } else {
                            params.centerid = this.updateTjData.centerid;
                            params.centercharge = this.updateTjData.centercharge;
                        }
                        params.sign = this.updateTjData.sign;
                        let data = common.paramsFormat(params);
                        add(data).then(res => {
                            loading.close();
                            console.log("添加确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('添加成功');
                                this.ifShowTjDialog = false;
                                this.seaTableData({});
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

            // 添加取消
            cancelAdd() {
                this.ifShowTjDialog = false;
            },

            // 添加弹窗关闭回调
            addClose() {
                this.$refs["formAdd"].resetFields();
            },

            // 同步
            synchr() {
                const loading = common.loading(this);
                insertPpmKpiDepRoles().then(res => {
                    loading.close();
                    console.log('同步', res.data)
                    if (res.data.status == 200) {
                        this.seaTableData({});
                        this.$message.success('同步成功');
                    } else {
                        this.$message.error('同步失败')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 导入
            enterEx() {
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

            // 确认导入
            sureEnter() {
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
                        this.seaTableData({});
                    } else {
                        this.$message.error('导入失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 上传变化
            changeUpFile(file) {
                console.log('上传批次变化', file, this.drList)
                this.drFileData = file.file;
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

            // 导出
            exportEx() {
                const loading = common.loading(this);
                let searchData = {};
                searchData.centerid = this.paramsForm.centerid;
                if (typeof searchData.centerid != 'string') {
                    searchData.centerid = searchData.centerid.join(',');
                }
                let params = common.paramsFormat(searchData);
                const self = this;
                exportKpiDepRoles(params).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "员工.xls");
                });
            },

            // 表格选中变化
            handleSelectionChange(val) {
                console.log("分页表格选中", val)
                this.selectRow = val;
            },

            // 修改
            corrMo() {
                if (this.selectRow.length == 0) {
                    this.$message.error('请至少选中一行');
                    return
                }
                if (this.selectRow.every(item => item.sign == 1)) {
                    console.log("部门")
                    this.updateMoData.sign = '1'
                } else if (this.selectRow.every(item => item.sign == 2)) {
                    console.log("中心")
                    this.updateMoData.sign = '2'
                } else {
                    this.$message.error('部门或者中心不一致');
                    return
                }
                this.ifShowXgMoDialog = true;
            },

            // 修改多人确定
            sureXgMo() {
                console.log("修改多人确定", this.updateMoData)
                this.$refs["formXgMo"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let jlIdAr = [];
                        this.selectRow.forEach(li => {
                            jlIdAr.push(li.id)
                        })
                        let params = {};
                        params.ids = jlIdAr.join(',');
                        if (this.updateMoData.sign == 1) {
                            params.centerid = this.updateMoData.centerid;
                            params.depcharge = this.updateMoData.depcharge;
                            params.director = this.updateMoData.director;
                            params.centercharge = this.updateMoData.centercharge;
                        } else {
                            params.centercharge = this.updateMoData.centercharge;
                        }
                        params.sign = this.updateMoData.sign;
                        let data = common.paramsFormat(params);
                        updatePpmKpiRoleDtos(data).then(res => {
                            loading.close();
                            console.log("修改多人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('更新成功');
                                this.ifShowXgMoDialog = false;
                                this.seaTableData({});
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

            // 修改多人取消
            cancelXgMo() {
                this.ifShowXgMoDialog = false;
            },

            // 修改多人弹窗关闭回调
            xgMoClose() {
                this.$refs["formXgMo"].resetFields();
            },

            // 修改单人
            corrSingle(val) {
                console.log('修改单人', val)
                if (val.centerid != undefined) {
                    val.centerid = Number(val.centerid);
                }
                val.sign = val.sign.toString();
                this.updateSingData = JSON.parse(JSON.stringify(val));
                this.ifShowXgSingDialog = true;
            },
            // 修改单人确定
            sureXgSing() {
                console.log("修改单人确定", this.updateSingData)
                this.$refs["formXgSing"].validate(valid => {
                    if (valid) {
                        const loading = common.loading(this);
                        let params = {};
                        params.ids = this.updateSingData.id;
                        if (this.updateSingData.sign == 1) {
                            if (this.updateSingData.centerid != undefined) {
                                params.centerid = this.updateSingData.centerid;
                            }
                            params.depcharge = this.updateSingData.depcharge;
                            params.director = this.updateSingData.director;
                            params.centercharge = this.updateSingData.centercharge;
                        } else {
                            params.centercharge = this.updateSingData.centercharge;
                        }
                        params.sign = this.updateSingData.sign;
                        let data = common.paramsFormat(params);
                        updatePpmKpiRoleDtos(data).then(res => {
                            loading.close();
                            console.log("修改单人确定", res.data)
                            if (res.data.status == 200) {
                                this.$message.success('更新成功');
                                this.ifShowXgSingDialog = false;
                                this.seaTableData({});
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

            // 删除（多）
            delMo() {
                if (this.selectRow.length == 0) {
                    this.$message.error('请至少选中一行');
                    return
                }
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    let jlIdAr = [];
                    this.selectRow.forEach(li => {
                        jlIdAr.push(li.id)
                    })
                    let jlIdStr = jlIdAr.join(',');
                    deletePpmKpiDepRoleList({ ids: jlIdStr }).then(res => {
                        loading.close();
                        console.log("删除（多）", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.seaTableData({});
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
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
                    deletePpmKpiDepRoleList({ ids: jlIdStr }).then(res => {
                        loading.close();
                        console.log("删除（多）", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.seaTableData({});
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
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

            // 级联选中变化
            kjPeoChange(val) {
                console.log("级联选中变化", val)
            },
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

    .dia-bottom {
        margin-top: 30px;
    }

    .hmddialog-bottom {
        text-align: right;
    }

    .hmd-row-block p {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 14px;
        color: #181C32;
        margin: 8px 0;
    }
</style>

<style>
    .yggl-head-content .el-input--prefix .el-input__inner {
        padding-left: 50px;
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

    .organization-head-left .el-form-item {
        display: block;
    }

    .organization-head-left .el-form-item__content {
        display: block;
    }

    .organization-head-left .el-select__input {
        margin-left: 45px;
    }
</style>