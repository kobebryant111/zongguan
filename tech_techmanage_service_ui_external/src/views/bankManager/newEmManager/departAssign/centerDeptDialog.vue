<template>
    <div>
        <el-input v-model="inputContent" :disabled="true">
            <el-button slot="append" icon="el-icon-more" @click="showDialog"></el-button>
        </el-input>
        <el-dialog title="选择部门" :visible.sync="ifShowDialog" class="" :append-to-body="true">
            <div>
                <el-select v-if="centerId != undefined" placeholder="部门" v-model="deptVal" value-key="depId" @change="bmChange">
                    <el-option v-for="item in depData" :key="item.depId" :label="item.depName" :value="item"></el-option>
                </el-select>
                <el-select v-else placeholder="部门" v-model="deptVal" value-key="id" @change="bmChange">
                    <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item"></el-option>
                </el-select>
                <div class="dia-bottom">
                    <el-row :gutter="10" type="flex" justify="end">
                        <el-col :span="1.5">
                            <button-common :buttonName="buttonCancel" :size="size" :fns="cancelSel"></button-common>
                        </el-col>
                        <el-col :span="1.5">
                            <button-orange :buttonName="buttonSure" :size="size" :fns="sureSel"></button-orange>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import { queryDeptList } from "@/api/integrated/newEmBmfp";
    import { update } from "@/api/integrated/newEmBmfp";
    import common from "@/api/common";
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    export default {
        name: 'centerDeptDialog',
        components: { buttonOrange, buttonCommon },
        data() {
            return {
                size: 'mini',  //按钮大小
                inputContent: this.input,
                buttonCancel: '取 消',
                buttonSure: '确 定',
                ifShowDialog: false, //是否展示弹窗
                depData: [], //部门数据
                deptVal: {},
                lsBmData: {}, //临时部门数据
                depAllData: [], //所有部门
            }
        },
        props: {
            input: { //输入框内容
                type: String
            },
            centerId: {
                // type: String,
                required: true
            },
            tableIndex: {
                type: Number,
                required: true
            },
            fns: {
                type: Function
            },
            isEdit: {
                type: Boolean
            },
            depAllDataProp: {
                type: Array
            }
        },
        watch: {
            input() {
                this.inputContent = this.input;
            }
        },
        mounted() {
            // this.getAllDep();
        },
        methods: {
            // 打开弹窗
            showDialog() {
                if (this.isEdit == true) {
                    this.lsBmData = {};
                    this.$nextTick(() => {
                        this.getDep();
                    })
                } else {
                    this.$message.error('无权限不可编辑')
                }
            },

            // 获取所有部门
            getAllDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params).then(res => {
                    if (res.data.status == 200) {
                        this.depAllData = res.data.results[0].children.filter(tt => {
                            tt.text = tt.text.replace('总行金融科技部-', "");
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 获取部门
            getDep() {
                if (this.centerId == undefined) {
                    this.depData = this.depAllDataProp;
                    this.ifShowDialog = true;
                } else {
                    const loading = common.loading(this);
                    queryDeptList({ centerId: this.centerId })
                        .then(res => {
                            loading.close();
                            this.ifShowDialog = true;
                            if (res.data.status == 200) {
                                var fild = res.data.results;
                                this.depData = fild.filter(tt => {
                                    tt.depName = tt.depName.replace('总行金融科技部-', "");
                                    return tt;
                                });
                            }
                        })
                        .catch(error => {
                            this.$message.error(error)
                        });
                }
            },

            // 部门变化
            bmChange(val) {
                this.lsBmData = val;
            },

            // 取消
            cancelSel() {
                this.ifShowDialog = false;
            },

            // 确认
            sureSel() {
                if (this.centerId != undefined && this.lsBmData.depId == undefined) {
                    this.$message.error('请选择部门');
                    return
                } else if (this.centerId == undefined && this.lsBmData.id == undefined) {
                    this.$message.error('请选择部门');
                    return
                }
                this.$emit('depChange', this.lsBmData, this.tableIndex);
                if (this.lsBmData.depName != undefined) {
                    this.inputContent = this.lsBmData.depName;
                } else {
                    this.inputContent = this.lsBmData.text;
                }
                this.ifShowDialog = false;
            },
        }
    }
</script>

<style scoped>
    .dia-bottom {
        margin-top: 30px;
    }
</style>