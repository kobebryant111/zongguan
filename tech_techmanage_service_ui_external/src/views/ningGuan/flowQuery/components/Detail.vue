<template>
    <div>
        <detail-xzh v-if="flowType=='com.example.ppm.bps.newBranchBankAcceptance'&&!isArchived" ref="xzhDetail"></detail-xzh>
        <archive-xzh v-else-if="flowType=='com.example.ppm.bps.newBranchBankAcceptance'&&isArchived" ref="xzhArchive"></archive-xzh>
        <detail-wdsb v-else-if="flowType=='com.example.ppm.bps.deviceChangeStick'" ref="wdsb"></detail-wdsb>
    </div>
</template>

<script>
import DetailWdsb from "@/views/ningGuan/deviceChange/Archive";
import DetailXzh from "@/views/ningGuan/newBranchBankAcceptance/Detail";
import ArchiveXzh from "@/views/ningGuan/newBranchBankAcceptance/Archive";
export default {
    components: { DetailWdsb, DetailXzh, ArchiveXzh },
    props: {
        detailData: {},
    },

    data() {
        return {
            flowType: "",
            isArchived: false,
        };
    },
    methods: {
        init(params) {
            let flowType = params.flowid;
            this.flowType = flowType;
            this.isArchived = params.isArchived
            let self = this;
            this.$nextTick(_ => {
                self.renderDetail(flowType, params);
            });
        },
        renderDetail(flowType, item) {
            console.log(flowType);
            let self = this;
            const flowMap = {
                "com.example.ppm.bps.newBranchBankAcceptance": () => {
                    self.$nextTick(_ => {
                        item.processInstID = item.instanceid;
                        item.showDrawback = false;
                        if(self.isArchived){
                            self.$refs.xzhArchive.init(item);
                            return
                        }
                        self.$refs.xzhDetail.init(item);
                    });
                },
                "com.example.ppm.bps.deviceChangeStick": () => {
                    self.$nextTick(_ => {
                        self.$refs.wdsb.isShowNav = false;
                        self.$refs.wdsb.init(item);
                    });
                },

            };
            flowMap[flowType]();
        }
    }
};
</script>
<style>
</style>