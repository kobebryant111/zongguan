<!-- 外包面试 聚合页-->
<template>
  <!-- 添加、编辑、发起申请-->
  <add :flow-obj="flowItem" v-if="auditNodeState==-1" ref="detail"></add>
  <!-- 发起申请-退回 -->
  <apply-back :flow-obj="flowItem" v-else-if="auditNodeState==1" ref="interviewApply"></apply-back>
  <!-- 面试打分 -->
  <interview-score v-else-if="auditNodeState==2" ref="interviewScore"></interview-score>
  <!-- 结果录入 -->
  <result-entry v-else-if="auditNodeState==3" ref="resultEntry"></result-entry>
  <!-- 单价确认 -->
  <price-confirm v-else-if="auditNodeState==4" ref="priceConfirm"></price-confirm>
  <!-- 综合部确认 -->
  <zh-dep-confirm v-else-if="auditNodeState==5" ref="zhbConfirm"></zh-dep-confirm>
  <!-- 已办页面 -->
  <interview-yb v-else-if="auditNodeState==6" ref="interviewYb"></interview-yb>
  <!-- 综合部已办归档页面 -->
  <interview-yb-gds v-else-if="auditNodeState==7" ref="interviewGds"></interview-yb-gds>
  <!-- 已办打分页面 -->
  <interview-yb-df v-else-if="auditNodeState==8" ref="interviewYbDf"></interview-yb-df>
  <!-- 已办结果录入页面 -->
  <interview-yb-jglr v-else-if="auditNodeState==9" ref="interviewYbJglr"></interview-yb-jglr>
  <!-- 已办单价确认页面 -->
  <interview-yb-djqr v-else-if="auditNodeState==10" ref="interviewYbDjqr"></interview-yb-djqr>
  <!-- 分管总经理审批 -->
  <fg-manager-approve v-else-if="auditNodeState==11" ref="fgManagerApprove"></fg-manager-approve>
</template>
<script>
  import add from '@/views/flowsManager/wbms/add'
  import applyBack from '@/views/flowsManager/wbms/applyBack'
  import interviewScore from '@/views/flowsManager/wbms/interviewScore'
  import resultEntry from '@/views/flowsManager/wbms/resultEntry'
  import priceConfirm from '@/views/flowsManager/wbms/priceConfirm'
  import zhDepConfirm from '@/views/flowsManager/wbms/zhDepConfirm'
  import interviewYb from '@/views/flowsManager/wbms/interviewYb'
  import interviewYbDf from '@/views/flowsManager/wbms/interviewYbDf'
  import interviewYbJglr from '@/views/flowsManager/wbms/interviewYbJglr'
  import interviewYbDjqr from '@/views/flowsManager/wbms/interviewYbDjqr'
  import interviewYbGds from '@/views/flowsManager/wbms/interviewYbGds'
  import fgManagerApprove from '@/views/flowsManager/wbms/fgManagerApprove'

  export default {
    components: {
      add,
      applyBack,
      interviewScore,
      resultEntry,
      priceConfirm,
      zhDepConfirm,
      interviewYb,
      interviewYbGds,
      interviewYbDf,
      interviewYbJglr,
      interviewYbDjqr,
      fgManagerApprove
    },
    data() {
      return {
        auditNodeState: "",         // 节点状态
        flowItem: this.$route.query,  // 流程对象
      };
    },
    mounted() {
      console.log('面试流程', this.flowItem)
      this.init()
    },
    methods: {
      init() {
        if (this.flowItem.status == "db") {
          console.log("待办");
          this.showNodeAudit(this.flowItem)
        }else if (this.flowItem.status == "yb") {
          this.flowItem.formScene = "msYbGd"
          if(this.flowItem.activityName == "面试打分"){
            this.flowItem.formScene = "msDf"
          }
           if(this.flowItem.activityName == "结果录入"){
            this.flowItem.formScene = "msJglr"
          }
           if(this.flowItem.activityName == "单价确认"){
            this.flowItem.formScene = "msDjqr"
          }
          this.$nextTick(_ => {
            console.log("已办",this.flowItem);
            this.showNodeAudit(this.flowItem);
          });
       }else if (this.flowItem.status == "gd") {
          this.flowItem.formScene = "msYbGd"
          this.$nextTick(_ => {
            console.log("归档");
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
            { name: "interviewApply" },
            () => {
              self.auditNodeState = 1;
              this.$nextTick(_ => {
                this.$refs.interviewApply.init(item);
              });
            }
          ],
          [
            { name: "interviewAdd" },
            () => {
              self.auditNodeState = -1;
              this.$nextTick(_ => {
                this.$refs.detail.init(item);
              });
            }
          ],
          [
            { name: "interviewScore" },
            () => {
              self.auditNodeState = 2;
              this.$nextTick(_ => {
                this.$refs.interviewScore.init(item);
              });
            }
          ],
          [
            { name: "resultEntry" },
            () => {
              self.auditNodeState = 3;
              this.$nextTick(_ => {
                this.$refs.resultEntry.init(item);
              });
            }
          ],
          [
            { name: "priceConfirm" },
            () => {
              self.auditNodeState = 4;
              this.$nextTick(_ => {
                this.$refs.priceConfirm.init(item);
              });
            }
          ],
          [
            { name: "zhbConfirm" },
            () => {
              self.auditNodeState = 5;
              this.$nextTick(_ => {
                this.$refs.zhbConfirm.init(item);
              });
            }
          ],
          [
            { name: "mianshiYb" },
            () => {
              self.auditNodeState = 6;
              this.$nextTick(_ => {
                this.$refs.interviewYb.init(item);
              });
            }
          ],
          [
            { name: "msYbGd" },
            () => {
              self.auditNodeState = 7;
              this.$nextTick(_ => {
                this.$refs.interviewGds.init(item);
              });
            }
          ],
          [
            { name: "msDf" },
            () => {
              self.auditNodeState = 8;
              this.$nextTick(_ => {
                this.$refs.interviewYbDf.init(item);
              });
            }
          ],
          [
            { name: "msJglr" },
            () => {
              self.auditNodeState = 9;
              this.$nextTick(_ => {
                this.$refs.interviewYbJglr.init(item);
              });
            }
          ],
          [
            { name: "msDjqr" },
            () => {
              self.auditNodeState = 10;
              this.$nextTick(_ => {
                this.$refs.interviewYbDjqr.init(item);
              });
            }
          ],
          [
            { name: "meetfirm" },
            () => {
              self.auditNodeState = 11;
              this.$nextTick(_ => {
                this.$refs.fgManagerApprove.init(item);
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