<template>
    <div class="proceedCon">
            <buttonOrange :buttonName="name" :size="size"></buttonOrange>
            <inputCommon @getinputData="iDat"></inputCommon>
            <inputCommon @getinputData="iDat2"></inputCommon>
            <buttonCommon :buttonName="name" :size="size"></buttonCommon>
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="待办" name='first'>
                <!-- <div @click="approve" class="wbsp">外包人员准入审批</div> -->
                <el-collapse>
                    <el-collapse-item>
                        <template slot>
                            外包人员准入流程<i class="header-icon-info"></i>
                        </template>
                        <ul>
                            <li v-for="(value, index) in dbList" :key="index"
                                class="wbsp" @click="getInstanceId(value)">{{value.processInstName}}</li>
                        </ul>
                    </el-collapse-item>
                </el-collapse>
            </el-tab-pane>
            <el-tab-pane label="已办" name='second'>
                    <el-collapse>
                            <el-collapse-item>
                                <template slot>
                                    外包人员准入流程<i class="header-icon-info"></i>
                                </template>
                                <ul>
                                    <li v-for="(value, index) in ybList" :key="index" 
                                        class="wbsp">{{value.processInstName}}</li>
                                </ul>
                            </el-collapse-item>
                        </el-collapse>
            </el-tab-pane>
            <el-tab-pane label="归档" name='third'></el-tab-pane>
            <el-tab-pane label="任务" name='fourth'></el-tab-pane>
        </el-tabs>

    </div>
</template>

<script>
    import { getDb, getYb } from "@/api/common/flowApi";
    import common from "@/api/common";
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import inputCommon from '@/components/eleInput/inputCommon'
    export default {
        components: { buttonOrange, inputCommon, buttonCommon },
        data() {
            return {
                activeName: 'first',
                dbList: '',
                ybList: '',
                name: '确定',
                size: 'mini'
            }
        },
        created() {
            this.getDbDetail();
        },
        methods: {
            handleClick(tab, event) {
                if(tab.label == "待办") {
                    this.getDbDetail();
                }

                 if(tab.label == "已办") {
                    this.getYbDetail();
                }
            },
            approve() {
                this.$router.push("/approve");
            },
            // 获取已办列表
            getYbDetail() {
                getYb()
                    .then(res => {
                        this.ybList = res.data.results;
                    })
                    .catch(error => {
                        console.error("error", error);
                    });  // end
            },
            // 获取待办列表
            getDbDetail() {
                getDb()
                    .then(res => {
                        this.dbList = res.data.results;
                    })
                    .catch(error => {
                        console.error("error", error);
                    });  // end
            },
            //获取点击项的流程Id
            getInstanceId(v) {
                // this.$router.push({
                //     path: '/approve',
                //     query: {
                //         char: v
                //     }
                // });
            },
            // 测试
            iDat(v){
            },
            iDat2(v) {
            }
        }
    }

</script>

<style scoped>
    .proceedCon {
        background-color: rgba(255, 255, 255, 1);
        padding-left: 15px;
    }

    .wbsp {
        cursor: pointer;
    }
</style>