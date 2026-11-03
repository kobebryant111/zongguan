<!-- 领用门禁卡流程 聚合页-->
<template>
  <!-- 发起流程 -->
  <!-- <lymj-apply v-if="auditNodeState== -1" ref="lymjApply"></lymj-apply> -->
  <!-- 草稿 退回 -->
  <lymj-return v-if="auditNodeState== 1" ref="lymjReturn"></lymj-return>
  <!-- 总经理室  监保部审批  -->
  <lymj-approve v-else-if="auditNodeState== 2" ref="lymjApprove"></lymj-approve>
  <!-- 监保部确认 -->
  <lymj-affirm v-else-if="auditNodeState== 3" ref="lymjAffirm"></lymj-affirm>
  <!-- 已办、归档 -->
  <lymj-finish v-else-if="auditNodeState== 4" ref="lymjFinish"></lymj-finish>
</template>
<script>
// import lymjApply from '@/views/accountManager/receiveCard/flows/init'
import lymjReturn from '@/views/accountManager/accessCard/receiveCard/flows/return'
import lymjApprove from '@/views/accountManager/accessCard/receiveCard/flows/approve'
import lymjAffirm from '@/views/accountManager/accessCard/receiveCard/flows/affirm' 
import lymjFinish from '@/views/accountManager/accessCard/receiveCard/flows/finish'
  
  export default {
    components: {
        // lymjApply,
        lymjReturn,
        lymjApprove,
        lymjAffirm,
        lymjFinish
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() { 
      // console.log('领用门禁卡', this.flowItem)
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem)
        } else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {
          this.flowItem.formScene = "lymjFinish"
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
        //   [
        //     { name: "lymjApply" },
        //     () => {
        //       self.auditNodeState = -1;
        //       this.$nextTick(_ => {
        //         this.$refs.lymjApply.init(item);
        //       });
        //     }
        //   ],
          [
            { name: "lymjinit" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.lymjReturn.init(item);
              });
            }
          ],
          [
            { name: "lymjgmapprove" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.lymjApprove.init(item);
              });
            }
          ],
          [
            { name: "lymjmonitorapprove" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.lymjApprove.init(item);
              });
            }
          ],
          [
            { name: "lymjmonitoraffirm" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.lymjAffirm.init(item);
              });
            }
          ],
          [
            { name: "lymjFinish" },
            () => {
              self.auditNodeState = 4;
              this.$nextTick(_ => {
                this.$refs.lymjFinish.init(item);
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