<!-- 待办-下半年总体评语 -->
<template>
    <div class="dbpy-container">
        <div class="dbpy-operate">
            <div class="dbpy-operate-left">{{routerData.tyear}}下半年整体评语</div>
            <div>
                <!-- <el-button type="primary" @click="commitComment">提 交</el-button> -->
                <button-oranges buttonName="提交" size="small" :fns="commitComment"></button-oranges>
            </div>
        </div>
        <div class="dbpy-body">
            <div v-if="judgeStatus == 6" class="dbpy-body-cpPy">
                <el-form label-position="left" label-width="90px">
                    <el-form-item label="初评评语：">
                        <div class="tdWarp">{{pyData.firstevalcomment != undefined ? pyData.firstevalcomment : '无'}}</div>
                    </el-form-item>
                </el-form>
            </div>
            <p class="dbpy-body-title">请详细点评该{{routerData.role == 'COMM_USER' ? '员工' : '经理'}}下半年KPI完成情况及其他能力表现</p>
            <el-input type="textarea" maxlength="500" show-word-limit :autosize="{ minRows: 10, maxRows: 15}"
                placeholder="请输入" v-model="comment"></el-input>
        </div>
        <div style="text-align: right;margin-top: 15px;">
            <el-button @click="saveComment">保 存</el-button>
        </div>
    </div>
</template>
<script>
    import { submitWholeComment, judgeStatus, saveWholeComment } from "@/api/hykh/kpiEval";
    import common from "@/api/common";
    import { mapGetters } from 'vuex';
    import { getPpmKpiPmInfo, getPpmKpiPmDetails } from "@/api/hykh/myDb";
    import buttonOranges from '@/components/eleButton/buttonOranges';
    export default {
        name: 'dbPopulationComment',
        components: { buttonOranges },
        data() {
            return {
                routerData: {},
                comment: null,
                judgeStatus: null, //判断状态
                lcData: {},
                pyData: {},
            }
        },
        computed: {
            ...mapGetters(['userId'])
        },
        mounted() { 
            if (this.$route.params.term == undefined) {
                this.$route.params.term = null
            }
            this.getLcData(this.$route.params);
            this.routerData = this.$route.params;
        },
        methods: {
            // 获取流程信息
            getLcData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat({ eid: val.eid, tyear: val.tyear, term: val.term });
                getPpmKpiPmInfo(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.lcData = res.data.results;
                        this.judgeHandlePeople(val);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 通过当前人和审批人判断考核所处阶段
            judgeHandlePeople(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat({ check: this.userId, eid: val.eid, flag: 'pj' });
                judgeStatus(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.judgeStatus = Number(res.data.message);
                        this.seaTableData()
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 获取评语
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ pid: this.lcData.id, kpitype: '6', itemstatus: this.judgeStatus });
                getPpmKpiPmDetails(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        for (var i = 0; i < res.data.results.length; i++) {
                            if (res.data.results[i].term == '下半年') {
                                this.pyData = res.data.results[i];
                                if (this.pyData.itemstatus == 5 && this.pyData.firstevalcomment != undefined) {
                                    this.comment = this.pyData.firstevalcomment
                                } else if (this.pyData.itemstatus == 6 && this.pyData.secondevalcomment != undefined) {
                                    // this.comment = this.pyData.secondevalcomment
                                    if (this.pyData.secondevalcomment != undefined) {
                                        this.comment = this.pyData.secondevalcomment
                                    } else if (this.pyData.firstevalcomment != undefined) {
                                        this.comment = this.pyData.firstevalcomment
                                    }
                                }
                                break
                            }
                        }
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 保存
            saveComment() {
                if (this.comment == null || this.comment == '' || this.comment == undefined) {
                    this.$message.error('请填写评语');
                    return
                }
                const loading = common.loading(this);
                let params = {};
                if (this.pyData.itemstatus == 5) {
                    params.firstevalcomment = this.comment;
                } else if (this.pyData.itemstatus == 6) {
                    params.secondevalcomment = this.comment;
                }
                params.term = this.pyData.term;
                params.eid = this.pyData.eid;
                params.id = this.pyData.id;
                params.pid = this.pyData.pid;
                params.itemstatus = this.pyData.itemstatus;
                let data = common.paramsFormat(params);
                saveWholeComment(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.$message.success('保存成功')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 提交
            commitComment() {
                if (this.comment == null || this.comment == '' || this.comment == undefined) {
                    this.$message.error('请填写评语');
                    return
                }
                const loading = common.loading(this);
                let params = {};
                if (this.pyData.itemstatus == 5) {
                    params.firstevalcomment = this.comment;
                } else if (this.pyData.itemstatus == 6) {
                    params.secondevalcomment = this.comment;
                }
                params.term = this.pyData.term;
                params.eid = this.pyData.eid;
                params.id = this.pyData.id;
                params.pid = this.pyData.pid;
                params.itemstatus = this.pyData.itemstatus;
                let data = common.paramsFormat(params);
                submitWholeComment(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.$message.success('提交成功');
                        // this.$router.back();
                        this.$emit('getTongJi', 'latterpy');
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            }
        }
    }
</script>

<style scoped>
    .dbpy-container {
        margin-top: 10px;
        padding: 20px;
        background-color: rgba(255, 255, 255, 1);
        border: none;
        border-radius: 4px;
    }

    .dbpy-operate {
        display: flex;
        justify-content: space-between;
    }

    .dbpy-operate-left {
        font-family: 'Nunito Sans Regular', 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 16px;
        line-height: 36px;
    }

    .dbpy-body-title {
        font-family: 'Nunito Sans Regular', 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-style: normal;
        font-size: 12px;
        color: #A1A1A1;
    }

    .tdWarp {
        white-space: pre-wrap;
    }

    .dbpy-body-cpPy {
        margin-top: 6px;
    }
</style>
<style>
    .dbpy-body-cpPy .el-form-item--medium .el-form-item__content {
        line-height: 26px;
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
    }

    .dbpy-body-cpPy .el-form-item--medium .el-form-item__label {
        line-height: 26px;
    }
</style>