<template>
    <div>
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
        <host-manager-khpj v-if="routeData.pjType == 'dep'"></host-manager-khpj>
        <general-manager-khpj v-if="routeData.pjType == 'center'"></general-manager-khpj>
        <charge-manager-khpj v-if="routeData.pjType == 'tec'"></charge-manager-khpj>
    </div>
</template>
<script>
    import HostManagerKhpj from '@/views/bankAssessment/dbYearKhpj/hostManagerKhpj'
    import ChargeManagerKhpj from '@/views/bankAssessment/dbYearKhpj/chargeManagerKhpj'
    import GeneralManagerKhpj from '@/views/bankAssessment/dbYearKhpj/generalManagerKhpj'
    export default {
        name: 'yearKhpj',
        components: { HostManagerKhpj, ChargeManagerKhpj, GeneralManagerKhpj },
        data() {
            return {
                routesMeta: [],
                routeData: {},
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name == "myKpiDb") {
                    vm.routesMeta = ['我的待办', to.params.tyear + '年度评级']
                }
            })
        },
        mounted() {
            console.log('路由传值', this.$route.params)
            this.routeData = this.$route.params
        },
        methods: {
            // 返回
            lastPage() {
                this.$router.back()
            }
        }
    }
</script>

<style scoped>
    .kh-back-bread {
        height: 32px;
        margin-top: 8px;
        padding: 0 15px;
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