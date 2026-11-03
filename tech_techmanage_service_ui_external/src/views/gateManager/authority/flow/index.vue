<!-- 外包离场 聚合页-->
<template> 
    <!-- 添加 -->
    <authorityAdd v-if="auditNodeState==-1" ref="authorityAdd"/> 
    <!-- 跟踪 -->
    <authorityTrack v-else-if="auditNodeState==0" ref="authorityTrack"/>
    <!-- 申请 -->
    <authorityApply v-else-if="auditNodeState==1" ref="authorityApply"/> 
    <!-- 二级部审批 -->
    <authorityTwoMgr v-else-if="auditNodeState==2" ref="authorityTwoMgr"/> 
    <!-- 一级审批 -->
    <authorityTopMgr v-else-if="auditNodeState==3" ref="authorityTopMgr"/>     
    <!-- 已办、归档 -->
    <authorityYbGd v-else-if="auditNodeState==4" ref="authorityYbGd"></authorityYbGd>
</template>

<script> 
    import authorityAdd from '@/views/gateManager/authority/flow/add';
    import authorityApply from '@/views/gateManager/authority/flow/apply';
    import authorityTrack from '@/views/gateManager/authority/flow/track';
    import authorityTwoMgr from '@/views/gateManager/authority/flow/twoMgr';
    import authorityTopMgr from '@/views/gateManager/authority/flow/topMgr';
    import authorityYbGd from '@/views/gateManager/authority/flow/ybGd' 

    export default {
        components: {authorityAdd,authorityApply,authorityTrack,authorityYbGd,authorityTwoMgr,authorityTopMgr},
        data() {
            return {
                auditNodeState: '',         // 节点状态
                flowItem: this.$route.query,  // 流程对象
            };
        },
        mounted() {
            console.log("闸机权限申请流程", this.flowItem);
            this.init()
        },
        methods: {
             init() {
                if (this.flowItem.status == "db") {
                    console.log("待办");
                    this.showNodeAudit(this.flowItem)
                }else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {                    
                    this.flowItem.formScene = "ybGd"
                    this.$nextTick(_ => {
                        console.log("已办|归档");
                        this.showNodeAudit(this.flowItem);
                    });  
                }else{ 
                    console.log("新建");
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
                        { name: "add" },
                        () => {
                            self.auditNodeState = -1;
                            this.$nextTick(_ => {
                                this.$refs.authorityAdd.init(item);
                            });
                        }
                    ], 
                    [
                        { name: "track" },
                        () => {
                            self.auditNodeState = 0;
                            this.$nextTick(_ => {
                                this.$refs.authorityTrack.init(item);
                            });
                        }
                    ], 
                     [
                        { name: "apply" },
                        () => {
                            self.auditNodeState = 1;
                            this.$nextTick(_ => {
                                this.$refs.authorityApply.init(item);
                            });
                        }
                    ], 
                    [
                        { name: "twoMgr" },
                        () => {
                            self.auditNodeState = 2;
                            this.$nextTick(_ => {
                                this.$refs.authorityTwoMgr.init(item);
                            });
                        }
                    ],
                    [
                        { name: "topMgr" },
                        () => {
                            self.auditNodeState = 3;
                            this.$nextTick(_ => {
                                this.$refs.authorityTopMgr.init(item);
                            });
                        }
                    ],
                    [
                        { name: "ybGd" },
                        () => {
                            self.auditNodeState = 4;
                            this.$nextTick(_ => {
                                this.$refs.authorityYbGd.init(item);
                            });
                        }
                    ]
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