<!-- 外包入职 聚合页-->
<template>
  <!-- 发起申请 -->
  <wbrz-add v-if="auditNodeState==-1" ref="wbrzAdd"></wbrz-add>
  <!-- 添加、退回 -->
  <wbrz-save-back v-else-if="auditNodeState==1" ref="saveBack"></wbrz-save-back>
  <!-- 二级部审批 -->
  <approve-two v-else-if="auditNodeState==2" ref="approveTwo"></approve-two>
  <!-- 综合部人事审批 -->
  <zhbrs-approve v-else-if="auditNodeState==3" ref="zhbrsApprove"></zhbrs-approve>
  <!-- 综合部风控审核 -->
  <zhbfkzx-approve v-else-if="auditNodeState==4" ref="zhbfkzxApprove"></zhbfkzx-approve>
  <!-- 发起已办、二级部审批已办、综合部人事审批已办 -->
  <wbrz-Zhbrssp-yb v-else-if="auditNodeState==7" ref="wbrzZhbrsspYb"></wbrz-Zhbrssp-yb>
  <!-- 综合部风控审核已办、分管总已办、总经理已办 -->
  <wbrz-Zhbfksh-yb v-else-if="auditNodeState==8" ref="wbrzZhbfkshYb"></wbrz-Zhbfksh-yb>
  <!-- 发起归档、二级部审批归档、综合部人事审批归档、综合部风控审核归档、分管总归档、总经理归档 -->
  <wbrz-Zhbfksh-gd v-else-if="auditNodeState==9" ref="wbrzZhbfkshGd"></wbrz-Zhbfksh-gd>
</template>
<script>
  import wbrzAdd from '@/views/crewManager/wbrz/wbrzAdd'
  import wbrzSaveBack from '@/views/crewManager/wbrz/wbrzSaveBack'
  import approveTwo from '@/views/crewManager/wbrz/approveTwo'
  import zhbrsApprove from '@/views/crewManager/wbrz/zhbrsApprove'
  import zhbfkzxApprove from '@/views/crewManager/wbrz/zhbfkzxApprove' 
  import wbrzZhbrsspYb from '@/views/crewManager/wbrz/wbrzZhbrsspYb'
  import wbrzZhbfkshYb from '@/views/crewManager/wbrz/wbrzZhbfkshYb'
  import wbrzZhbfkshGd from '@/views/crewManager/wbrz/wbrzZhbfkshGd'
  export default {
    components: {
      wbrzAdd,
      wbrzSaveBack,
      approveTwo,
      zhbrsApprove,
      zhbfkzxApprove, 
      wbrzZhbrsspYb,
      wbrzZhbfkshYb,
      wbrzZhbfkshGd,
    },
    data() {
      return {
        auditNodeState: '',         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() {
      console.log('入职流程', this.flowItem)
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          this.showNodeAudit(this.flowItem)
        } else if (this.flowItem.status == "yb"){
          this.flowItem.formScene = "wbrzZhbrsspYb";
          if(this.flowItem.activityName == "研发管理部风控征信审核"){
             this.flowItem.formScene = "wbrzZhbfkshYb"
          }
          this.$nextTick(_ => {
            console.log("已办");
            this.showNodeAudit(this.flowItem);
          });
        } else if (this.flowItem.status == "gd") {
          this.flowItem.formScene = "rzGd"
          this.$nextTick(_ => {
           console.log("归档");
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
        // "approveEmManager"
        let url = item.formScene;
        let self = this;
        const flowMap = new Map([
          [
            { name: "wbrzAdd" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.wbrzAdd.init(item);
              });
            }
          ],
          [
            { name: "wbrzSaveBack" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.saveBack.init(item);
              });
            }
          ],
          [
            { name: "approveTwo" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.approveTwo.init(item);
              });
            }
          ],
          [
            { name: "wbrzrsApprove" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.zhbrsApprove.init(item);
              });
            }
          ],
          [
            { name: "wbrzfkzxApprove" },
            () => {
              self.auditNodeState = 4;
              this.$nextTick(_ => {
                this.$refs.zhbfkzxApprove.init(item);
              });
            }
          ],
          [
            { name: "wbrzZhbrsspYb" },
            () => {
              self.auditNodeState = 7;
              this.$nextTick(_ => {
                this.$refs.wbrzZhbrsspYb.init(item);
              });
            }
          ],
          [
            { name: "wbrzZhbfkshYb" },
            () => {
              self.auditNodeState = 8;
              this.$nextTick(_ => {
                this.$refs.wbrzZhbfkshYb.init(item);
              });
            }
          ],
          [
            { name: "rzGd" },
            () => {
              self.auditNodeState = 9;
              this.$nextTick(_ => {
                this.$refs.wbrzZhbfkshGd.init(item);
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