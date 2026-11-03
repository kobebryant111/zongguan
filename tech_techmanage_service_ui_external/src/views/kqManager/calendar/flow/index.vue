<!-- 外包离场 聚合页-->
<template> 
    <!-- 审批 -->
    <jjrsp v-if="auditNodeState==3" ref="jjrsp"></jjrsp>
    <!-- 发起已办 -->
    <jjryb v-else-if="auditNodeState==2" ref="jjryb"></jjryb>
    <!-- 归档 -->
    <jjrgd v-else-if="auditNodeState==4" ref="jjrgd"></jjrgd>
</template>
<script> 
    import jjryb from '@/views/kqManager/calendar/flow/yb';
    import jjrgd from '@/views/kqManager/calendar/flow/gd';
    import jjrsp from '@/views/kqManager/calendar/flow/sp';
    export default {
        components: {jjrsp,jjryb,jjrgd},
        data() {
            return {
                auditNodeState: '',         // 节点状态
                flowItem: this.$route.query,  // 流程对象
            };
        },
        mounted() {
            console.log("考勤节假日数据导入流程", this.flowItem);
            this.init()
        },
        methods: {
            init() {
                if (this.flowItem.status == "db") {
                    this.showNodeAudit(this.flowItem)
                } else if (this.flowItem.status == "yb") {
                    this.flowItem.formScene = "jjryb"
                    this.$nextTick(_ => {
                        this.showNodeAudit(this.flowItem);
                    });
                } else if (this.flowItem.status == "gd") {
                    this.flowItem.formScene = "jjrgd"
                    this.$nextTick(_ => {
                        this.showNodeAudit(this.flowItem);
                    });
                } 
            },
            /**
           * 显示对应节点的审批内容
           */
            showNodeAudit: function (item) {
                let url = item.formScene;
                let self = this;
                const flowMap = new Map([
                    [
                        { name: "jjryb" },
                        () => {
                            self.auditNodeState = 2;
                            this.$nextTick(_ => {
                                this.$refs.jjryb.init(item);
                            });
                        }
                    ],
                    [
                        { name: "calendarImprt" },
                        () => {
                            self.auditNodeState = 3;
                            this.$nextTick(_ => {
                                this.$refs.jjrsp.init(item);
                            });
                        }
                    ],
                    [
                        { name: "jjrgd" },
                        () => {
                            self.auditNodeState = 4;
                            this.$nextTick(_ => {
                                this.$refs.jjrgd.init(item);
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