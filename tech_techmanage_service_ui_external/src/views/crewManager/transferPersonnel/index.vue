<!-- 人员移交 聚合页-->
<template>
  <!-- 发起申请 -->
  <ryyj-apply v-if="auditNodeState== -1" ref="ryyjApply"></ryyj-apply>
  <!-- 添加、退回 -->
  <ryyj-save v-else-if="auditNodeState== 1" ref="ryyjSave"></ryyj-save>
  <!-- 审批、二级部和综合部 -->
  <ryyj-approve v-else-if="auditNodeState== 2" ref="ryyjApprove"></ryyj-approve>
  <!-- 已办、归档 -->
  <ryyj-ybgd v-else-if="auditNodeState== 3" ref="ryyjYbgd"></ryyj-ybgd>
</template>
<script>
import ryyjApply from '@/views/crewManager/transferPersonnel/flows/applyfor'
import ryyjSave from '@/views/crewManager/transferPersonnel/flows/save'
import ryyjApprove from '@/views/crewManager/transferPersonnel/flows/approve'
import ryyjYbgd from '@/views/crewManager/transferPersonnel/flows/ybgd'
  
  export default {
    components: {
        ryyjApply,
        ryyjSave,
        ryyjApprove,
        ryyjYbgd
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() { 
      // console.log('人员移交', this.flowItem)
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem)
        } else if (this.flowItem.status == "yb" || this.flowItem.status == "gd") {
          this.flowItem.formScene = "ryyjYbgd"
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
            { name: "ryyjApply" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.ryyjApply.init(item);
              });
            }
          ],
          [
            { name: "ryyjsave" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.ryyjSave.init(item);
              });
            }
          ],
          [
            { name: "erryyjConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.ryyjApprove.init(item);
              });
            }
          ],
          [
            { name: "newerryyjConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.ryyjApprove.init(item);
              });
            }
          ],
          [
            { name: "oneryyjConfirm" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.ryyjApprove.init(item);
              });
            }
          ],
          [
            { name: "ryyjYbgd" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.ryyjYbgd.init(item);
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