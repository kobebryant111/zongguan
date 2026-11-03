<!-- 外包离场 聚合页-->
<template>
    <!-- 发起申请 -->
    <wblc-apply v-if="auditNodeState==-1" ref="wblcApply"></wblc-apply>
    <!-- 添加、退回 -->
    <wblc-save-back v-else-if="auditNodeState==1" ref="wblcSaveBack"></wblc-save-back>
    <!-- 发起已办 -->
    <wblc-Yb v-else-if="auditNodeState==2" ref="wblcYb"></wblc-Yb>
    <!-- 二级部经理审批 -->
    <wblc-approve-two v-else-if="auditNodeState==3" ref="wblcApproveTwo"></wblc-approve-two>
    <!-- 归档 -->
    <wblc-Gd v-else-if="auditNodeState==4" ref="wblcGd"></wblc-Gd>
</template>
<script>
    import wblcApply from '@/views/crewManager/wblc/wblcApply';
    import wblcSaveBack from '@/views/crewManager/wblc/wblcSaveBack';
    import wblcYb from '@/views/crewManager/wblc/wblcYb';
    import wblcGd from '@/views/crewManager/wblc/wblcGd';
    import wblcApproveTwo from '@/views/crewManager/wblc/wblcApproveTwo';
    export default {
        components: {
            wblcApply,
            wblcSaveBack,
            wblcApproveTwo,
            wblcYb,
            wblcGd
        },
        data() {
            return {
                auditNodeState: '',         // 节点状态
                flowItem: this.$route.query,  // 流程对象
            };
        },
        mounted() {
            console.log("离场流程", this.flowItem);
            this.init()
        },
        methods: {
            init() {
                if (this.flowItem.status == "db") {
                    this.showNodeAudit(this.flowItem)
                } else if (this.flowItem.status == "yb") {
                    this.flowItem.formScene = "wblcYb"
                    this.$nextTick(_ => {
                        this.showNodeAudit(this.flowItem);
                    });
                } else if (this.flowItem.status == "gd") {
                    this.flowItem.formScene = "wblcGd"
                    this.$nextTick(_ => {
                        this.showNodeAudit(this.flowItem);
                    });
                } else {
                    this.showNodeAudit(this.flowItem)
                }
            },
            /**
           * 显示对应节点的审批内容
           */
            showNodeAudit: function (item) {

                console.info(item,"---item")
                let url = item.formScene;
                let self = this;
                const flowMap = new Map([
                    [
                        { name: "wblcApply" },
                        () => {
                            self.auditNodeState = -1;
                            this.$nextTick(_ => {
                                this.$refs.wblcApply.init(item);
                            });
                        }
                    ],
                    [
                        { name: "wblcSaveBack" },
                        () => {
                            self.auditNodeState = 1;
                            this.$nextTick(_ => {
                                this.$refs.wblcSaveBack.init(item);
                            });
                        }
                    ],
                    [
                        { name: "wblcYb" },
                        () => {
                            self.auditNodeState = 2;
                            this.$nextTick(_ => {
                                this.$refs.wblcYb.init(item);
                            });
                        }
                    ],
                    [
                        { name: "wblcApproveTwo" },
                        () => {
                            self.auditNodeState = 3;
                            this.$nextTick(_ => {
                                this.$refs.wblcApproveTwo.init(item);
                            });
                        }
                    ],
                    [
                        { name: "wblcGd" },
                        () => {
                            self.auditNodeState = 4;
                            this.$nextTick(_ => {
                                this.$refs.wblcGd.init(item);
                            });
                        }
                    ],

                ]);
                const shwoFlowNode = name => {
                    console.info('-----------------'+name);
                    [...flowMap].forEach(([key, val]) => {
                        name.indexOf(key.name) != -1 ? val.call() : "";
                    });
                };
                shwoFlowNode(url);
            },
        }
    };
</script>