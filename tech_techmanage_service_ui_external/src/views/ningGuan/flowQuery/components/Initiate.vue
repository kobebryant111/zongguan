<template>
    <div>
        <!-- 流程类型选择 -->
        <div v-show="isSelectType">
            <el-form :model="formData" ref="formOneData" label-width="100px" :rules="oneRules">
                <el-form-item prop="processType" label="流程类型：">
                    <el-select v-model="formData.processType" filterable placeholder="请选择">
                        <el-option
                            v-for="item in processTypeData"
                            :key="item.dictid"
                            :label="item.dictname"
                            :value="item.dictid"
                        ></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div :class="'footer-right-div'">
                <span slot="footer">
                    <buttonOrange buttonName="确认发起" :fns="initiateTypeSelect"></buttonOrange>
                </span>
            </div>
        </div>
        <!-- 不同类型选择 -->
        <div v-show="!isSelectType">
            <initiate-xzh v-if="flowType=='com.example.ppm.bps.newBranchBankAcceptance'" ref="xzh"/>
            <initiate-wdsb :closeDialog="closeDialog" v-else-if="flowType=='com.example.ppm.bps.deviceChangeStick'" ref="wdsb"/>
            <div v-else>暂不支持该流程</div>
        </div>
    </div>
</template>

<script>
import InitiateXzh from "@/views/ningGuan/newBranchBankAcceptance/InitiateXzh";
import InitiateWdsb from "@/views/ningGuan/deviceChange/Initiate";

import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import { getDicEntry } from "@/api/eosDictEntry";

export default {
    components: { InitiateXzh,InitiateWdsb,buttonOrange },
    props: {
        setDialog: {
            type: Function
        },
        closeDialog:{
            default:_ =>{}
        }
    },

    data() {
        return {
            processTypeData: [],
            isSelectType: true,
            formData: {},
            flowType: "",
            oneRules: {
                processType: [
                    { required: true, message: "请选择流程类型", trigger: "change" }
                ]
            },
        };
    },
    mounted() {
        let eos = { dicttypeid: "NG_FLOW_TYPE" };
        let self = this;
        getDicEntry(eos).then(res => {
            let data = res.data.results;
            // 流程类型
            self.processTypeData = data.filter(ii => {
                return ii.dicttypeid === "NG_FLOW_TYPE";
            });
        });
        self.setDialog("30%","请选择流程类型");
    },
    methods: {
        initiateTypeSelect() {
            this.$refs.formOneData.validate(valid =>{
                if(valid){
                    if (this.formData.processType) {
                        this.isSelectType = false;
                        this.flowType = this.formData.processType;
                        this.renderDialog(this.formData.processType);
                    }
                }
            });
            
        },
        renderDialog(flowType){
            // 格式: {processDefName} : [{弹出框width},{弹出框title}]
            const renderMap = {
                "com.example.ppm.bps.newBranchBankAcceptance":["80%", "新支行验收流程"],
                "com.example.ppm.bps.deviceChangeStick":["80%", "网点设备变更流程"],
            }
            if (renderMap[flowType]){
                this.setDialog(...renderMap[flowType]);
            } else {
                this.setDialog("30%", "宁波地区管理部流程");
            }
        }
    }
};
</script>
<style scoped>
.footer-right-div {
    text-align: right;
}
</style>