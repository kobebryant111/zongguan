<!-- 我的待办 -->
<template>
    <div class="mydb-container">
        <div v-if="ifShowDbTop || ifShowDbBottom">
            <div class="mydb-area mydb-top" v-show="ifShowDbTop">
                <p class="mydb-title">考核制定/自评</p>
                <el-row :gutter="10">
                    <template v-for="dbData of dbDataList" :label="item.dictname" :value="item.dictid">
                        <template v-for="(value, key) in dbData.data" :label="item.dictname" :value="item.dictid">
                            <el-col :span="6" v-if="value != 0 && dbTopDicEntry[key]" :class="signDicEntry[dbData.sign].classname" :key="dbData.tyear + '_' + dbData.sign + '_' + key">
                                <div class="mydb-card">
                                    <svg-icon class-name="svg-icon" :icon-class="dbTopDicEntry[key].icon" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">{{dbData.tyear + signDicEntry[dbData.sign].name + dbTopDicEntry[key].name + dbTopDicEntry[key].text}}</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-content-bottom-left">{{'待' + dbTopDicEntry[key].text}}<span class="mydb-card-num">{{value}}</span>项</div>
                                            <div class="mydb-card-content-bottom-right" @click="toPath(key, dbData.sign, dbData.tyear)">{{'前往' + dbTopDicEntry[key].text}}></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                        </template>
                    </template>
                </el-row>
            </div>
            <div class="mydb-area mydb-bottom" v-show="ifShowDbBottom">
                <p class="mydb-title">考核审批/评价</p>
                <el-row :gutter="10">
                    <template v-for="dbData of dbDataList" :label="item.dictname" :value="item.dictid">
                        <template v-for="(value, key) in dbData.data" :label="item.dictname" :value="item.dictid">
                            <el-col :span="6" v-if="value != 0 && dbBottomDicEntry[key]" :key="dbData.tyear + '_' + dbData.sign + '_' + key">
                                <div class="mydb-card mydb-card-bottom">
                                    <svg-icon class-name="svg-icon" :icon-class="dbBottomDicEntry[key].icon" />
                                    <div class="mydb-card-content">
                                        <p class="mydb-card-content-top">{{dbData.tyear + signDicEntry[dbData.sign].name + dbBottomDicEntry[key].name + dbBottomDicEntry[key].text}}</p>
                                        <div class="mydb-card-content-bottom">
                                            <div class="mydb-card-content-bottom-left"><span class="mydb-card-num">{{value}}</span>{{dbBottomDicEntry[key].text1}}</div>
                                            <div class="mydb-card-content-bottom-right" @click="toPath(key, dbData.sign, dbData.tyear)">{{'前往' + dbBottomDicEntry[key].text}}></div>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                        </template>
                    </template>
                </el-row>
            </div>
        </div>
        <div v-else class="noneDb">当前没有待办</div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { queryMyDbNum } from "@/api/zzkhApi/ppmDepkpiDepCheck";
    export default {
        name: 'myDepkpiDb',
        components: {},
        data() {
            return {
                dbDataList: [],
                ifShowDbTop: false,
                ifShowDbBottom: false,
                dbTopDicEntry: {
                    zdydb_zd: { name: "自定义指标", text: "制定", text1: "个部门待制定", icon:"u105"},
                    ggdb_zd: { name: "公共指标", text: "制定", text1: "个指标待制定", icon:"u105"},
                    zdydb_zp: { name: "自定义指标", text: "自评", text1: "个部门待自评", icon:"u106"},
                },
                 dbBottomDicEntry: {
                    yjbzdydb_sh: { name: "指标", text: "审批", text1: "个指标待审批", icon:"u114"},
                    zdydb_sh: { name: "自定义指标", text: "审批", text1: "个部门待审批", icon:"u114"},
                    ggdb_sh: { name: "公共指标", text: "审批", text1: "个指标待审批", icon:"u114"},
                    ggkh_pj: { name: "公共考核", text: "评价", text1: "个部门待评价", icon:"u114"},
                    zdykh_pj: { name: "自定义考核", text: "评价", text1: "个部门待评价", icon:"u114"},
                    kh_grade: { name: "考核", text: "评级", text1: "个部门待评级", icon:"u114"},
                },
                signDicEntry: {
                    1: { name: "二级部",classname: "mydb-card-border-blue" },
                    2: { name: "中心", classname: "mydb-card-border-green" },
                    3: { name: "一级部", classname: "mydb-card-border-red" },
                }
            }
        },
        mounted() {
            this.getDbData();
        },
        methods: {
            
            // 获取我的待办数据
            getDbData() {
                const loading = common.loading(this);
                queryMyDbNum().then(res => {
                    loading.close();
                    console.log('查询我的待办数据', res.data)
                    if (res.data.status == 200) {
                        this.dbDataList  = res.data.results;
                        this.dbDataList.forEach(item => {
                            Object.keys(item.data).forEach(key => {
                                if(item.data[key] > 0 && this.dbTopDicEntry[key]){
                                   this.ifShowDbTop = true;
                                }
                                if(item.data[key] > 0 && this.dbBottomDicEntry[key]){
                                   this.ifShowDbBottom = true;
                                }
                            })
                        })
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            toPath(key, sign, tyear){
                // 自定义指标制定
                if(key == "zdydb_zd"){
                    // if(sign == 1 || sign == 2 || sign == 2 ){
                        this.$router.push({path: "/zdykhzd", query: {sign: sign, tyear: tyear}})
                    // }
                } else if (key == "ggdb_zd"){// 公共指标制定
                    if(sign == 1 || sign == 2){
                        this.$router.push({path: "/ggkhzd", query: {sign: sign, tyear: tyear}})
                    }
                } else if (key == "zdydb_zp"){
                    this.$router.push({path: "/selfeval", query: {sign: sign, tyear: tyear}})
                } else if (key == "zdykh_pj"){
                    this.$router.push({path: "/eval", query: {sign: sign, tyear: tyear}})
                } else if (key == "ggkh_pj"){
                    this.$router.push({path: "/ggeval", query: {sign: sign, tyear: tyear}})
                } else if (key == "kh_grade"){
                    this.$router.push({path: "/gradelist", query: {sign: sign, tyear: tyear}})
                } else if (key == "zdydb_sh"){
                    this.$router.push({path: "/zdykhsp", query: {sign: sign, tyear: tyear}})
                } else if (key == "yjbzdydb_sh"){
                    this.$router.push({path: "/yjbkhsp", query: {sign: sign, tyear: tyear}})
                } else if (key == "ggdb_sh"){
                    this.$router.push({path: "/ggkhsp", query: {sign: sign, tyear: tyear}})
                }
            }
        }
    }
</script>
<style scoped lang="scss">
    .mydb-container {
        padding: 20px;
    }

    .mydb-area {
        width: 100%;
    }

    .mydb-title {
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 18px;
    }

    .mydb-card-border-green .mydb-card{
        border-color: rgba(1, 204, 167, 1);
    }
    .mydb-card-border-green .mydb-card-num{
        color: rgba(1, 204, 167, 1);
    }
    .mydb-card-border-blue .mydb-card{
        border-color: rgba(1, 124, 255, 1);
    }
    .mydb-card-border-blue .mydb-card-num{
        color: rgba(1, 124, 255, 1);
    }
    .mydb-card-border-red .mydb-card{
        border-color: rgba(251, 164, 47, 1);
    }
    .mydb-card-border-red .mydb-card-num{
        color: rgba(251, 164, 47, 1);
    }

    .mydb-bottom .mydb-card-num {
        color: #FF0160;
    }

    .mydb-card {
        position: relative;
        border-width: 0px;
        height: 148px;
        background: inherit;
        background-color: rgba(255, 255, 255, 1);
        border-left: 4px solid;
        border-radius: 0 10px 10px 0;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        padding: 25px;
        margin-bottom: 10px;
    }

    .mydb-card-bottom {
        border: none !important;
        border-radius: 10px !important;
    }
    
    .svg-icon {
        height: 73px;
        width: 73px;
        border-width: 0px;
        position: absolute;
        left: 0px;
        top: 0px;
        opacity: 0.1;
    }

    .mydb-card-content-top {
        border-width: 0px;
        white-space: nowrap;
        text-transform: none;
        box-shadow: none;
        font-family: 'Nunito Sans Bold', 'Nunito Sans Regular', 'Nunito Sans', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 18px;
        text-align: left;
        margin: 0;
    }

    .mydb-card-content-bottom {
        display: flex;
        justify-content: space-between;
        font-weight: 400;
        font-style: normal;
        margin-top: 25px;
    }

    .mydb-card-content-bottom-left,
    .mydb-card-approve-content-bottom-left {
        font-family: 'Nunito Sans', sans-serif;
        font-size: 16px;
        color: #9B9B9B;
        white-space: nowrap;
    }

    .mydb-card-content-bottom-left span {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-size: 40px;
        color: #017CFF;
        margin: 0 8px;
        display: inline-block;
        transform: translateY(10%);
    }

    .mydb-card-approve-content-bottom-left span {
        font-family: 'Arial Normal', 'Arial', sans-serif;
        font-size: 40px;
        color: #FF0160;
        margin: 0 8px;
        display: inline-block;
        transform: translateY(10%);
    }

    .mydb-card-content-bottom-right {
        font-size: 14px;
        color: #017CFF;
        font-family: 'Arial Normal', 'Arial', sans-serif;
        cursor: pointer;
        line-height: 65px;
        white-space: nowrap;
    }

    .mydb-bottom {
        margin-top: 40px;
    }

    .noneDb {
        height: calc(100vh - 162px);
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial', sans-serif;
        font-weight: 700;
        font-style: normal;
        font-size: 50px;
        color: #C4C4C4;
        display: flex;
        align-items: center;
        justify-content: center;
    }
</style>