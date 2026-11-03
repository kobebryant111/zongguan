<!-- 外包考勤修改分管总经理审批 -->
<template>
    <div class="wbkqxg">
        <div class="nav">
            <el-row>
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="5">
                                <p class="nav_name">外包人员考勤修改</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
                            </el-col>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="wbkqxg-container">
            <el-form inline :model="kqxgData" ref="kqxgForm">
                <div class="wbkqxg-main">
                    <el-row type="flex" justify="space-around">
                        <el-col :span="12">
                            <el-form-item label="发起人：" prop="userName">
                                <el-input v-model="kqxgData.userName" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="所在部门：">
                                <el-select placeholder="部门" v-model="kqxgData.depId" :disabled="true">
                                    <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="修改原因描述：">
                                <el-input type="textarea" :autosize="{ minRows: 3}" v-model="kqxgData.reasonDes"
                                    disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="修改内容描述：">
                                <el-input type="textarea" :autosize="{ minRows: 3}" v-model="kqxgData.contentDes"
                                    disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </div>
            </el-form>
            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs ref="log"></flow-logs>
            </div>
        </div>
        <div class="nav-bottom">
            <approve-bottom :proving="proving" :flowItem="bps"></approve-bottom>
        </div>
    </div>
</template>

<script>
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import { generalMnager, queryDetail } from "@/api/integrated/wbKqxg";
    import common from "@/api/common";
    import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
    import approveBottom from "@/views/plugins/flowPlug/approveBottom";
    export default {
        name: 'wbkqxgFgManager',
        components: { buttonCommon, flowLogs, approveBottom },
        data() {
            return {
                size: 'mini',  //按钮大小
                buttonClose: '关 闭', //关闭按钮名字
                delBtnIf: false, //删除按钮是否出现
                kqxgData: {},
                depData: [], //部门数据
                bps: {}, //bps数据
            }
        },
        mounted() {
        },
        methods: {
            init(item) {
                console.log("分管总经理审批", item);
                this.bps = item;
                this.$refs.log.init(item.processInstID);
                this.getwbkqxgDetail(item.processInstID);
                this.getDep();
            },

            // 获取详情
            getwbkqxgDetail(val) {
                let params = {};
                params.instanceId = val;
                queryDetail(params).then(res => {
                    console.log(res.data)
                    if (res.data.status == 200) {
                        if (res.data.results.depId != undefined && res.data.results.depId.indexOf('dep') == -1) {
                            res.data.results.depId = 'dep' + res.data.results.depId
                        }
                        this.kqxgData = res.data.results;
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 获取部门
            getDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params)
                    .then(res => {
                        if (res.data.status == 200) {
                            var fild = res.data.results[0].children;
                            let name = res.data.results[0].text;
                            this.depData = fild.filter(tt => {
                                tt.text = tt.text.replace(name + "-", "");
                                return tt;
                            });
                        }
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 提交
            proving(data) {
                let status = new Promise(() => { });
                status = this.liuzhuan(data).then((val) => {
                    return val;
                });
                return status
            },

            liuzhuan(params) {
                const loading = common.loading(this);
                this.bps.conclusion = params.conclusion;
                this.bps.suggest = params.suggest;
                let folw = { "bps": this.bps };
                folw.id = this.kqxgData.id;
                return generalMnager(folw).then(res => {
                    loading.close();
                    return res.data
                }).catch(error => {
                    this.$message.error(error);
                });
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },
        }
    }
</script>

<style scoped>
    .wbkqxg {
        padding-bottom: 85px;
    }

    .wbkqxg-container {
        margin: 15px 15px 0 15px;
    }

    .wbkqxg-main {
        /* min-height: calc(100vh - 84px); */
        padding: 15px;
        background-color: #ffffff;
    }

    .wbkqxg-title p {
        color: #606266;
        font-size: 16px;
        font-weight: 600;
        padding: 10px 0 5px 0;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 150px;
        height: 24px;
        font-size: 16px;
        font-weight: 600;
        color: rgba(38, 38, 38, 1);
        line-height: 24px;
        display: inline-block;
    }

    .nav .wrapper {
        margin-left: 25px;
        margin-right: 25px;
        display: flex;
        justify-content: space-between;
    }

    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .wrapper-right-keep {
        margin-right: 10px;
    }

    .bottom {
        margin-top: 20px;
        padding: 15px 0;
    }

    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        position: fixed;
        bottom: 0;
    }
</style>

<style>
    .wbkqxg-container .el-form--inline .el-form-item {
        width: 100%;
    }

    .wbkqxg-container .el-form--inline .el-form-item__content {
        width: 70%;
    }
</style>