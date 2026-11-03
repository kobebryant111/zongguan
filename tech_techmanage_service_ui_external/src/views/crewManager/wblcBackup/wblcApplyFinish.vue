<!-- 外包人员离场申请完成 -->
<template>
    <div>
        <div class="nav">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="3">
                            <p class="nav_name">外包人员离场申请</p>
                        </el-col>
                    </div>
                    <div class="wrapper_right">
                        <el-col :span="1.5">
                            <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
                        </el-col>
                    </div>
                </div>
            </el-row>
        </div>

        <div class="content-finish">
            <div class="finish-main">
                <div>
                    <img src="@/assets/finish2.png" class="finish_img" alt="">
                </div>
                <div>
                    <p class="finish-main-success">申请提交成功</p>
                    <p class="finish-main-wait">请耐心等待流程</p>
                </div>
                <div class="finish-main-bottom">
                    <div>
                        <el-row :gutter="100">
                            <el-col :span="12">
                                <div class="finish-main-bottom-row">
                                    <p>姓名：</p>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p class="con_val">{{finishData.userName}}</p>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div>
                        <el-row :gutter="100">
                            <el-col :span="12">
                                <div class="finish-main-bottom-row">
                                    <p>工号：</p>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p class="con_val">{{finishData.jobId}}</p>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div>
                        <el-row :gutter="100">
                            <el-col :span="12">
                                <div class="finish-main-bottom-row">
                                    <p>供应商：</p>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p class="con_val">{{finishData.outsourcname}}</p>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                    <div>
                        <el-row :gutter="100">
                            <el-col :span="12">
                                <div class="finish-main-bottom-row">
                                    <p>所属部门：</p>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p class="con_val">{{finishData.depname}}</p>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                    <div>
                        <el-row :gutter="100">
                            <el-col :span="12">
                                <div class="finish-main-bottom-row">
                                    <p>行内负责人：</p>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div>
                                    <p class="con_val">{{finishData.personInChargename}}</p>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <!-- 继续申请 -->
                <div class="fin-conti">
                    <buttonOrange :buttonName="buttonSure" :size="size" :fns="contiApply"></buttonOrange>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon'
    export default {
        components: { buttonOrange, buttonCommon },
        data() {
            return {
                size: 'mini',  //按钮大小
                buttonSure: '继续申请',
                buttonClose: '关 闭', //关闭按钮名字
                finishData: {}, //本页数据
            }
        },
        mounted() {
            console.log("离场完成", this.$route.params);
            this.finishData = this.$route.params.finishData;
        },
        methods: {
            // 继续申请
            contiApply() {
                this.$router.push({ name: 'wbrzManage' });
            },

            // 关闭按钮
            close() {
                if (this.finishData.saveMark == '1') {
                    this.$router.push({ name: 'dashboard' });
                } else {
                    this.$router.push({ name: 'wbrzManage' });
                }
            },
        }
    }
</script>
<style scoped>
    .content-finish {
        width: 100%;
        background-color: #ffffff;
        display: flex;
        justify-content: center;
        align-items: center;
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
        align-items: center;
    }

    .finish_img {
        width: 250px;
        height: 210px;
        margin-top: 30px;
    }

    .finish-main {
        text-align: center;
    }

    .finish-main-success {
        font-size: 24px;
        color: #000000;
        margin: 0;
    }

    .finish-main-wait {
        font-size: 14px;
    }

    .finish-main-bottom {
        width: 556px;
        background: #F5F5F5;
        border-radius: 2px;
        color: rgba(0, 0, 0, 0.85);
        font-size: 14px;
    }

    .finish-main .con_val {
        color: rgba(0, 0, 0, 0.65);
        text-align: left;
    }

    .content-finish .finish-main-bottom-row {
        text-align: left;
        padding-left: 130px;
    }

    .fin-conti {
        text-align: center;
        margin: 20px 0;
    }
</style>