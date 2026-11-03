<!-- 员工考核-平衡计分考核详情 -->
<template>
    <div class="phjfKh-container">
        <div class="kh-back-bread">
            <div class="kh-back-bread-butt">
                <el-button size="small" icon="el-icon-arrow-left" @click="lastPage">返回</el-button>
            </div>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item v-for="(item,index) in routesMeta" :key="index">
                    <span :class="[routesMeta.length-1 == index ? 'lastJc' : 'crumbOther']">{{item}}</span>
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div ref="ygkhbody" class="target-body">
            <div class="yggl-body-operation">
                <div class="yggl-body-operation-left">平衡计分扣分详情</div>
                <div>
                    <!-- <el-button size="small" @click="exportEx">导 出</el-button> -->
                </div>
            </div>
            <div class="yggl-body-table">
                <el-table :data="targetData" :max-height="tableHeight">
                    <el-table-column label="考核项" min-width="15%" prop="itemdescribe" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column label="考核内容" min-width="15%" prop="plandescribe"></el-table-column>
                    <el-table-column label="评分标准" min-width="15%" prop="workremark"></el-table-column>
                    <el-table-column label="扣分" min-width="15%" prop="value"></el-table-column>
                    <el-table-column label="扣分理由" min-width="25%" prop="deducescorereason"></el-table-column>
                    <el-table-column label="扣分时间" min-width="15%" prop="regtime"></el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
    import Moment from 'moment';
    import common from "@/api/common";
    import { exportBalanceDetail, getBalanceDeduceItem } from "@/api/hykh/phjf";
    export default {
        name: 'ygkhPhjf',
        data() {
            return {
                tableHeight: null,
                targetData: [],
                routerData: {},
                routesMeta: []
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "ygkhDetail") {
                    vm.routesMeta = ['我的考核', to.params.eidname, '平衡计分']
                } else if (from.name == "myKh") {
                    vm.routesMeta = ['我的考核', '平衡计分']
                }
            })
        },
        mounted() {
            console.log('路由传值平衡计分', this.$route.params)
            this.routerData = this.$route.params;
            this.seaTableData();
            this.$nextTick(() => {
                this.tableHeight = this.$refs.ygkhbody.offsetHeight * 0.86
                console.log('测试高度', this.tableHeight)
            })
        },
        methods: {
            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let params = {};
                params.tyear = this.routerData.tyear;
                params.eid = this.routerData.eid;
                let data = common.paramsFormat(params);
                getBalanceDeduceItem(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.targetData = res.data.results.filter(li => {
                            if (li.regtime != undefined) {
                                li.regtime = Moment(li.regtime).format('YYYY-MM-DD');
                            }
                            return li
                        });
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 导出
            exportEx() {
                const loading = common.loading(this);
                const self = this;
                exportBalanceDetail().then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "平衡计分扣分详情.xls");
                });
            },

            // 返回
            lastPage() {
                this.$router.back()
            }
        }
    }
</script>
<style scoped>
    .phjfKh-container {
        padding: 8px 15px 15px 15px;
    }

    .yggl-body-operation {
        display: flex;
        justify-content: space-between;
        margin-bottom: 10px;
    }

    .yggl-body-operation-left {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: 400;
        font-size: 16px;
        color: #000333;
        line-height: 33px;
    }

    .target-body {
        padding: 15px;
        border-radius: 4px;
        background-color: #ffffff;
        height: calc(100vh - 110px);
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

    .kh-back-bread {
        height: 32px;
        margin-bottom: 8px;
    }

    .kh-back-bread-butt {
        height: 32px;
        line-height: 32px;
        float: left;
        margin-right: 10px;
    }
</style>

<style>
    .kh-back-bread .el-breadcrumb {
        height: 32px;
        line-height: 32px;
    }
</style>