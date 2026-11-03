<!-- 宁管 - 分页 -->
<template>
    <div class="app-container">
        <!-- 搜索 -->
        <div class="search-container">
            <el-form :model="form" ref="form" label-width="80px" inline>
                <el-form-item label="流程类型">
                    <el-select v-model="form.processType" placeholder="流程类型">
                        <el-option
                            v-for="item in processTypeData"
                            :key="item.dictid"
                            :label="item.dictname"
                            :value="item.dictid"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.processTitle"></el-input>
                </el-form-item>
                <el-form-item label="创建人">
                    <el-input v-model="form.creator"></el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-date-picker
                        v-model="form.date"
                        type="daterange"
                        unlink-panels
                        start-placeholde="开始时间"
                        end-placeholde="结束时间"
                        range-separator="至"
                        value-format="yyyy-MM-dd"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="form.flowStatus" placeholder="流程类型">
                        <el-option
                            v-for="item in stateData"
                            :key="item.dictid"
                            :label="item.dictname"
                            :value="item.dictname"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <buttonOrange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></buttonOrange>
                    <clean-btn :size="sizes" :forms="this.form"/>
                </el-form-item>
            </el-form>
        </div>
        <!-- 操作按钮 -->
        <div class="operat-conatiner marginbottom-10px">
            <el-row>
                <el-col :span="20">&nbsp;&nbsp;</el-col>
                <el-col :span="2">
                    <buttonOrange :buttonName="excleExportText" :size="sizes" :fns="excleExport"></buttonOrange>
                </el-col>
                <el-col :span="2">
                    <buttonOrange
                        :buttonName="initiateProcessText"
                        :size="sizes"
                        :fns="initiateBtn"
                    ></buttonOrange>
                </el-col>
            </el-row>
        </div>
        <!-- 分页 -->
        <div>
            <pager-table
                :data="data"
                :form="form" 
                :totalNumber="totalNumber" 
                :size="size" 
                :pagerFns="this.pager" 
                :showDetail="showDetail" 
            />
        </div>
        <!-- 发起 dialog-->
        <div class="dialog-container">
            <el-dialog
                :title="dialogTitle"
                :visible.sync="visibleClose"
                :width="dialogWidth"
                v-if="visibleClose"
                :before-close="handleClose"
                @close="dialogColse"
                top="12px"
            >
                <detail :setDialog="setDialog" :dialogColse="dialogColse" ref="detail" v-if="dialogType=='detail'"></detail>
                <initiate :setDialog="setDialog" :closeDialog="dialogColse" v-else-if="dialogType=='initiate'"></initiate>
                <!-- <initiate-todo @refreshDialog="setDialog" @closeDialog="dialogColse" ref="initiate" v-show="!ifReadOnly"></initiate-todo>
                <archive ref="details" v-show="ifReadOnly"></archive>-->
            </el-dialog>
        </div>
    </div>
</template>
<script>
import PagerTable from "@/views/ningGuan/flowQuery/components/PagerTable";
import Detail from "@/views/ningGuan/flowQuery/components/Detail";
import Initiate from "@/views/ningGuan/flowQuery/components/Initiate";
import common from "@/api/common";
import { getFlowInfo } from "@/api/common/flowApi";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon";
import { flowPagerList, exportExcel } from "@/api/ningGuanApi/ngFlowQuery";
import Archive from "@/views/ningGuan/deviceChange/Archive";
import { getDicEntry } from "@/api/eosDictEntry";
import Moment from "moment";
export default {
    name: "Pagers",
    components: {
        cleanBtn,
        buttonOrange,
        buttonCommon,
        Initiate,
        Archive,
        PagerTable,
        Detail
    },
    data() {
        return {
            ifReadOnly: false, // 查看详情
            processTypeData: [], // 流程类型集合
            stateData: [], // 状态集合
            dialogTitle: "", // dialog 标题
            dialogType: "", // dialog 标题
            dialogWidth: "30%", // dialong 宽度
            visibleClose: false, // dialog显示默认不显示
            initiateProcessText: "发起流程", // 按钮文本
            excleExportText: "导 出", // 按钮文本
            form: {}, // 搜索表单
            buttonSearch: "搜 索",
            sizes: "size",
            // 分页
            totalNumber: 0,
            size: 50, // 初始值 默认：50
            index: 1, // 初始值 默认：1
            data: [] // 分页数据
        };
    },
    mounted() {
        this.queryHandler();
        this.init();
    },
    methods: {
        /**
         * 页面初始化加载
         */
        init() {
            let self = this;
            let params = self.form;
            params.size = self.size;
            params.index = self.index;
            self.pager(params);
            let eos = { dicttypeid: "NG_FLOW_TYPE,NEW_DEVICE_CHAGE_STATE" };
            getDicEntry(eos).then(res => {
                let data = res.data.results;
                // 流程类型
                self.processTypeData = data.filter(ii => {
                    return ii.dicttypeid === "NG_FLOW_TYPE";
                });
                // 状态
                self.stateData = data.filter(ii => {
                    return ii.dicttypeid === "NEW_DEVICE_CHAGE_STATE";
                });
            }); // getDicEntry end
        },
        /**
         * 分页方法
         */
        pager(params) {
            let self = this;
            let data = common.paramsFormat(params);
            flowPagerList(data).then(res => {
                if (res.data.status !== 200) {
                    self.$alert(res.data.message, { type: "error" });
                    return false;
                }
                res.data.results.forEach((v, k) => {
                    v.lastmodtime = Moment(v.lastmodtime).format("YYYY-MM-DD");
                    v.createtime = Moment(v.createtime).format("YYYY-MM-DD");
                    if (v.clr && !v.clr.endsWith(")")) {
                        v.clr = v.clr + ")";
                    }
                });
                self.data = res.data.results;
                self.totalNumber = res.data.total;
            }); // pagerList
        },
        /**
         * 搜索
         */
        submitBtn() {
            let self = this;
            let params = self.form;
            params.index = 1;
            self.index = 1;
            params.size = self.size;
            if (
                undefined != params.date &&
                "" != params.date &&
                params.date.length > 0
            ) {
                params.startDate = params.date[0];
                params.endDate = params.date[1];
            }
            self.pager(params);
        },
        /**
         * 发起申请
         */
        initiateBtn() {
            let self = this;
            self.visibleClose = true;
            self.dialogType = "initiate";
        },
        /**
         * dialog 关闭
         */
        dialogColse() {
            let self = this;
            self.visibleClose = false;
            self.dialogTitle = "";
            self.dialogType = "";
            self.dialogWidth = "30%";
            self.ifReadOnly = false;
        },
        /**
         * 点击发起流程赋值dialog
         */
        setDialog(width, title) {
            let self = this;
            self.dialogWidth = width;
            self.dialogTitle = title;
        },
        /**
         * dialog关闭前提示
         */
        handleClose(done) {
            if (!this.ifReadOnly) {
                this.$confirm("确认关闭?")
                    .then(res => {
                        done();
                    })
                    .catch(_ => {});
            } else {
                done();
            }
        },
        /**
         * 查看详情
         */
        showDetail(item) {
            let self = this;
            self.visibleClose = true;
            self.dialogType = "detail";
            self.$nextTick(_ => {
                self.ifReadOnly = true;
                self.dialogTitle = "详情";
                self.dialogWidth = "80%";
                if(item.flowstatus === "已结束"){
                    item.isArchived = true;
                } else {
                    item.isArchived = false;
                }
                console.log("item::", item);
                self.$refs.detail.init(item);
            });
        },
        /**
         * excel 导出
         */
        excleExport() {
            let self = this;
            // 判断流程类型不能为空
            if (!self.form.processType) {
                self.$alert("请选择流程类型", { type: "error" });
                return false;
            }
            let data = common.paramsFormat(self.form);
            let pt = self.processTypeData
            let filename;
            for(let t of pt){
                if(self.form.processType === t.dictid){
                    filename = t.dictname;
                    break;
                }
            }
            exportExcel(data).then(res => {
                common.download(res.data, self, `${filename}.xls`)
            })
        },
        /**
         * 处理低码平台跳转query，并显示message
         */
        queryHandler(){
            let query=this.$route.query;
            let params=this.$route.params;
            let self = this;
            if(Object.keys(params).length > 0){
                console.log(params);
                if(params.action != undefined){
                    if(params.instanceId != undefined){
                        getFlowInfo({processInstId:params.instanceId}).then(res=>{
                            if (res.data.status !== 200) {
                                self.$alert(res.data.message, { type: "error" });
                                return false;
                            }
                            let msg = common.getFlowMessge(res.data.results);
                            let msgarr = msg.split(",")
                            self.flowMessageNotify(msgarr);
                        });
                    } else {
                        self.messageNotify("操作成功");
                    }
                }
                return
            } 
            if(Object.keys(query).length > 0){
                this.$router.push({
                    name:"ningGuanflow.pager",
                    params: query
                })
            }
        },
        flowMessageNotify(messageVal) {
            if (messageVal.length == 1){
                this.messageNotify(messageVal[0]);
                return;
            }
            if (messageVal.length > 3) {
                messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(")")[0] + ')';
            } 
            let messageSec = messageVal.slice(2).join("，");
            console.log("提示信息", messageSec, messageVal)
            this.$notify({
                title: '提示',
                dangerouslyUseHTMLString: true,
                message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
                duration: 0
            })
        },
        messageNotify(messageVal) {
            this.$notify({
                title: '提示',
                message: messageVal,
                duration: 0
            })
        },

    }
};
</script>
<style scoped>
.marginbottom-10px {
    margin-bottom: 10px;
}
</style>