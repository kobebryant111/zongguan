<!-- 归还门禁卡流程 聚合页-->
<template>
  <!-- 发起流程 -->
  <!-- <ghmj-apply v-if="auditNodeState== -1" ref="ghmjApply"></ghmj-apply> -->
  <!-- 草稿 退回 -->
  <ghmj-return v-if="auditNodeState== 1" ref="ghmjReturn"></ghmj-return>
  <!-- 监保部确认 -->
  <ghmj-affirm v-else-if="auditNodeState== 2" ref="ghmjAffirm"></ghmj-affirm>
  <!-- 已办、归档 -->
  <ghmj-finish v-else-if="auditNodeState== 3" ref="ghmjFinish"></ghmj-finish>
</template>
<script>
// import ghmjApply from '@/views/accountManager/accessCard/returnCard/flows/init'
import ghmjReturn from '@/views/accountManager/accessCard/returnCard/flows/return'
import ghmjAffirm from '@/views/accountManager/accessCard/returnCard/flows/affirm' 
import ghmjFinish from '@/views/accountManager/accessCard/returnCard/flows/finish'
  
  export default {
    components: {
        // ghmjApply,
        ghmjReturn,
        ghmjAffirm,
        ghmjFinish
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() { 
      // console.log('归还门禁卡', this.flowItem)
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem)
        } else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {
          this.flowItem.formScene = "ghmjfinish"
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
        let url = item.formScene;
        // console.log('url', url);
        let self = this;
        const flowMap = new Map([
            [
                { name: "ghmjinit" },
                () => {
                self.auditNodeState = 1;
                this.$nextTick(_ => {
                    this.$refs.ghmjReturn.init(item);
                });
                }
            ],
            [
                { name: "ghmjaffirm" },
                () => {
                self.auditNodeState = 2;
                this.$nextTick(_ => {
                    this.$refs.ghmjAffirm.init(item);
                });
                }
            ],
            [
                { name: "ghmjfinish" },
                () => {
                self.auditNodeState = 3;
                this.$nextTick(_ => {
                    this.$refs.ghmjFinish.init(item);
                });
                }
            ],

        ]);

        const shwoFlowNode = name => {
          [...flowMap].forEach(([key, val]) => {
            // console.log([key, val]);
            name.indexOf(key.name) != -1 ? val.call() : "";
          });
        };

        shwoFlowNode(url);
      },
    }
  };
</script>