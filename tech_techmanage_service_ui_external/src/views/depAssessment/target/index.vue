<!-- 指标配置-聚合页 -->
<template>
    <div class="pzzx-container">
        <div class="pzzx-head">
            <el-button v-for="index of maxLevel" :key="index" @click="cliTag(index)" :class="{ 'buttFous': showCon == index}">{{getCNNUm(index)}}级项框架配置</el-button>
        </div>
        <div class="pzzx-body">
            <target :level="showCon"></target>
        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { getEosDepKpiGrade } from "@/api/depAssessment/depAssessmentControl";
    import target from "@/views/depAssessment/target/target";
    export default {
        name: 'index',
        components: { target },
        data() {
            return {
                showCon: 1, // 当前选择层级
                maxLevel: 0, //最大层级
            }
        },
        created(){
            this.getEosDepKpiGrade();
        },
        mounted() {},
        methods: {
            getEosDepKpiGrade() {
                const loading = common.loading(this);
                getEosDepKpiGrade().then(res => {
                    loading.close();
                    console.log('查询框架数据', res.data)
                    if (res.data.status == 200) {
                        if(this.$route.name == "ejbTarget"){
                            this.maxLevel = res.data.results.dep;
                        }else if(this.$route.name == "zxTarget"){
                            this.maxLevel = res.data.results.cen;
                        }else if(this.$route.name == "yjbTarget"){
                            this.maxLevel = res.data.results.tec;
                        }
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 点击tag
            cliTag(v) {
                this.showCon = v;
            },

            //获取中文数字
            getCNNUm(level){
                var list = ["", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"];
                return list[level];
            }
        }
    }
</script>
<style scoped>
    .pzzx-container {
        padding: 15px;
    }

    .pzzx-head {
        padding-bottom: 15px;
        display: flex;
        overflow-x: auto;
    }

    .buttFous {
        color: #409EFF;
        border-color: #c6e2ff;
        background-color: #ecf5ff;
    }
</style>