<!-- kpi评价-待办-聚合页 -->
<template>
    <div class="kpizd-container">
        <div class="kpizd-nav">
            <div></div>
            <div>
                <!-- <button-oranges v-if="lcData.status == '3' || lcData.status == '4'" buttonName="提交" class="xkg-marginleft-10px"
                    size="small" :fns="approval"></button-oranges> -->
                <button-oranges v-if="ifShowSpButt" buttonName="提交" class="xkg-marginleft-10px" size="small" :fns="approval"></button-oranges>
            </div>
        </div>
        <div class="kpizd-body">
            <div class="kpizd-body-content">
                <el-tabs type="border-card">
                    <el-tab-pane label="KPI指标">
                        <dbkpi-pj-kh ref="kpisp" @tongji="getTongJi"></dbkpi-pj-kh>
                    </el-tab-pane>
                    <el-tab-pane label="知识技能提升">
                        <dbkpi-pjzhnl-promote ref="kpizhnl">
                        </dbkpi-pjzhnl-promote>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { submitKpiEval } from "@/api/hykh/kpiEval";
    import { getPpmKpiPmInfo } from "@/api/hykh/myDb";
    import dbkpiPjKh from '@/views/bankAssessment/dbKpiPjPgPy/dbKpiEvaluate/dbkpiPjKh';
    import dbkpiPjzhnlPromote from '@/views/bankAssessment/dbKpiPjPgPy/dbKpiEvaluate/dbkpiPjzhnlPromote';
    import buttonOranges from '@/components/eleButton/buttonOranges';
    import Moment from 'moment';
    import { getPpmKpiRoleByBadge } from "@/api/hykh/ygKh";
    import { mapGetters } from 'vuex';
    export default {
        name: 'dbkpiPj',
        components: { dbkpiPjKh, buttonOranges, dbkpiPjzhnlPromote },
        data() {
            return {
                lcData: {},
                routerData: {},
                ifShowSpButt: false,
            }
        },
        props: {
            getTongJi: {
                type: Function,
                default: null
            }
        },
        computed: {
            ...mapGetters(['userId'])
        },
        mounted() { 
            if (this.$route.params.term == undefined) {
                this.$route.params.term = null
            }
            this.routerData = this.$route.params;
            this.$nextTick(() => {
                this.getLcData(this.$route.params);
            })
        },
        methods: {
            // 根据工号查询详情
            getApprovalAuth(val) {
                getPpmKpiRoleByBadge({ badge: val.eid }).then(res => { 
                    if (res.data.status == 200) {
                        if (this.lcData.status == 4 && this.userId == res.data.results.firstcheck) { //初评
                            this.ifShowSpButt = true
                        } else if (this.lcData.status == 4 && this.userId == res.data.results.secondcheck) { //终评
                            this.ifShowSpButt = true
                        }
                        this.$refs.kpisp.getZbData(this.lcData, res.data.results);
                        this.$refs.kpizhnl.getZbData(this.lcData);
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 获取流程信息
            getLcData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat({ eid: val.eid, tyear: val.tyear, term: val.term });
                getPpmKpiPmInfo(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        if (res.data.results.createtime != undefined) {
                            res.data.results.createtime = Moment(res.data.results.createtime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.createtime = '';
                        }
                        if (res.data.results.firstchecktime != undefined) {
                            res.data.results.firstchecktime = Moment(res.data.results.firstchecktime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.firstchecktime = '';
                        }
                        if (res.data.results.secondchecktime != undefined) {
                            res.data.results.secondchecktime = Moment(res.data.results.secondchecktime).format('YYYY-MM-DD HH:mm:ss');
                        } else {
                            res.data.results.secondchecktime = '';
                        }
                        this.lcData = res.data.results;
                        this.$nextTick(() => {
                            this.getApprovalAuth(val);
                        })
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 提交弹窗
            approval(val) {
                this.$confirm('是否确认提交？', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.commit();
                }).catch(error => { })
            },

            // 提交
            commit() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ id: this.lcData.id, eid: this.routerData.eid });
                submitKpiEval(data).then(res => {
                    loading.close(); 
                    if (res.data.status == 200) {
                        this.getTongJi('kpipj');
                        this.$message.success(res.data.message);
                        // this.$router.push({ name: 'myKpiDb' });
                        // this.$router.back();
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },
        }
    }
</script>
<style scoped>
    .kpizd-body {
        margin-top: 10px;
    }

    .kpizd-nav {
        display: flex;
        justify-content: space-between;
        margin-top: 5px;
    }

    .hmddialog-bottom {
        text-align: center;
        margin-top: 20px;
    }

    .lastJc {
        font-family: '微软雅黑 Bold', '微软雅黑 Regular', '微软雅黑', sans-serif;
        font-weight: 700;
        color: #414141;
    }

    .crumbOther {
        font-family: '微软雅黑', sans-serif;
        font-weight: 400;
        color: #939393;
    }
</style>