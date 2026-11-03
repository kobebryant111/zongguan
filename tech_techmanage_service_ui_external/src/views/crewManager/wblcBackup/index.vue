<!-- 外包离场 聚合页-->
<template>
    <!-- 发起申请 -->
    <wblc-apply v-if="auditNodeState==-1" ref="wblcApply"></wblc-apply>
    <!-- 添加、退回 -->
    <wblc-save-back v-else-if="auditNodeState==1" ref="wblcSaveBack"></wblc-save-back>
    <!-- 发起已办、归档 -->
    <wblc-fq-yb-gd v-else-if="auditNodeState==2" ref="wblcFqYb"></wblc-fq-yb-gd>
    <!-- 行内负责人审批 -->
    <wblc-organizers-approve v-else-if="auditNodeState==3" ref="wblcOrganizersApprove"></wblc-organizers-approve>
    <!-- 账号注销 -->
    <wblc-zhzx v-else-if="auditNodeState==4" ref="wblcZhzx"></wblc-zhzx>
    <!-- 行内负责人、账号注销已办、归档 -->
    <zhzx-ybgd v-else-if="auditNodeState==5" ref="zhzxYbgd"></zhzx-ybgd>
    <!-- 二级部经理审批 -->
    <wblc-twomanager-approve v-else-if="auditNodeState==6" ref="wblcTwomanagerApprove"></wblc-twomanager-approve>
    <!--综合管理部确认 -->
    <zhglb-confirm v-else-if="auditNodeState==7" ref="zhglbConfirm"></zhglb-confirm>
    <!-- 综合管理部确认归档 -->
    <zhglb-Gd v-else-if="auditNodeState==8" ref="zhglbGd"></zhglb-Gd>
</template>
<script>
    import wblcApply from '@/views/crewManager/wblc/wblcApply';
    import wblcSaveBack from '@/views/crewManager/wblc/wblcSaveBack';
    import wblcFqYbGd from '@/views/crewManager/wblc/wblcFqYbGd';
    import wblcOrganizersApprove from '@/views/crewManager/wblc/wblcOrganizersApprove';
    import wblcZhzx from '@/views/crewManager/wblc/wblcZhzx';
    import zhzxYbgd from '@/views/crewManager/wblc/zhzxYbgd';
    import wblcTwomanagerApprove from '@/views/crewManager/wblc/wblcTwomanagerApprove';
    import zhglbConfirm from '@/views/crewManager/wblc/zhglbConfirm';
    import zhglbGd from '@/views/crewManager/wblc/zhglbGd';
    export default {
        components: {
            wblcApply,
            wblcSaveBack,
            wblcFqYbGd,
            wblcOrganizersApprove,
            wblcZhzx,
            zhzxYbgd,
            wblcTwomanagerApprove,
            zhglbConfirm,
            zhglbGd
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
                } else if ((this.flowItem.status == "yb" || this.flowItem.status == "gd") && (this.flowItem.activityDefID == "manualActivity4")) {
                    //发起-保存-退回-行内负责人审批已办
                    this.flowItem.actionURL = "lcfqybgd"
                    this.$nextTick(_ => {
                        this.showNodeAudit(this.flowItem);
                    });
                } else if ((this.flowItem.status == "yb" || this.flowItem.status == "gd") && (this.flowItem.activityDefID == "manualActivity5" || this.flowItem.activityDefID == "manualActivity3" || this.flowItem.activityDefID == "manualActivity1")) {
                    //行内负责人审批已办-账号注销
                    this.flowItem.actionURL = "zhzxybgd"
                    this.$nextTick(_ => {
                        this.showNodeAudit(this.flowItem);
                    });
                } else if (this.flowItem.status == "gd") {
                    this.flowItem.actionURL = "wblcGd"
                    this.$nextTick(_ => {
                        this.showNodeAudit(this.flowItem);
                    });
                }
                else {
                    this.showNodeAudit(this.flowItem)
                }
            },
            /**
           * 显示对应节点的审批内容
           */
            showNodeAudit: function (item) {
                let url = item.actionURL;
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
                        { name: "outlzrstart" },
                        () => {
                            self.auditNodeState = 1;
                            this.$nextTick(_ => {
                                this.$refs.wblcSaveBack.init(item);
                            });
                        }
                    ],
                    [
                        { name: "lcfqybgd" },
                        () => {
                            self.auditNodeState = 2;
                            this.$nextTick(_ => {
                                this.$refs.wblcFqYb.init(item);
                            });
                        }
                    ],
                    [
                        { name: "leavePersonHnfzr" },
                        () => {
                            self.auditNodeState = 3;
                            this.$nextTick(_ => {
                                this.$refs.wblcOrganizersApprove.init(item);
                            });
                        }
                    ],
                    [
                        { name: "outPersonChangeZhzxqr" },
                        () => {
                            self.auditNodeState = 4;
                            this.$nextTick(_ => {
                                this.$refs.wblcZhzx.init(item);
                            });
                        }
                    ],
                    [
                        { name: "zhzxybgd" },
                        () => {
                            self.auditNodeState = 5;
                            this.$nextTick(_ => {
                                this.$refs.zhzxYbgd.init(item);
                            });
                        }
                    ],
                    [
                        { name: "outlzermanager" },
                        () => {
                            self.auditNodeState = 6;
                            this.$nextTick(_ => {
                                this.$refs.wblcTwomanagerApprove.init(item);
                            });
                        }
                    ],
                    [
                        { name: "outlzzyglg" },
                        () => {
                            self.auditNodeState = 7;
                            this.$nextTick(_ => {
                                this.$refs.zhglbConfirm.init(item);
                            });
                        }
                    ],
                    [
                        { name: "wblcGd" },
                        () => {
                            self.auditNodeState = 8;
                            this.$nextTick(_ => {
                                this.$refs.zhglbGd.init(item);
                            });
                        }
                    ],

                ]);
                const shwoFlowNode = name => {
                    [...flowMap].forEach(([key, val]) => {
                        name.indexOf(key.name) != -1 ? val.call() : "";
                    });
                };

                shwoFlowNode(url);
            },
        }
    };
</script>