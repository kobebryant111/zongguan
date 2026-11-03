<template>
  <div>
    <requ-dop-approval v-if="auditNodeState==1" ref="RequDopApproval"/>
    <app-hiring-dep v-else-if="auditNodeState==2" ref="AppHiringDep"/>
    <finance-handl v-else-if="auditNodeState==3" ref="FinanceHandl"/>
    <risk-handl v-else-if="auditNodeState==4" ref="RiskHandl"/>
    <risk-per-charge v-else-if="auditNodeState==5" ref="RiskPerCharge"/>
    <res-per-charge v-else-if="auditNodeState==6" ref="ResPerCharge"/>
    <infor-tch-arvl v-else-if="auditNodeState==7" ref="InforTchArvl"/>
  </div>
</template>
<script>
import RequDopApproval from '@/views/inforTch/approval/requDopApproval.vue'
import AppHiringDep from '@/views/inforTch/approval/appHiringDep.vue'
import FinanceHandl from '@/views/inforTch/approval/financeHandl.vue'
import RiskHandl from '@/views/inforTch/approval/riskHandl.vue'
import RiskPerCharge from '@/views/inforTch/approval/riskPerCharge.vue'
import ResPerCharge from '@/views/inforTch/approval/resPerCharge.vue'
import InforTchArvl from '@/views/inforTch/approval/inforTchArvl.vue'
export default {
  components: { RequDopApproval, AppHiringDep, FinanceHandl, RiskHandl, RiskPerCharge, ResPerCharge, InforTchArvl },
  data() {
    return {
      auditNodeState: '', // 节点状态
      flowItem: this.$route.query // 流程对象
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      this.showNodeAudit(this.flowItem)
    },
    /**
           * 显示对应节点的审批内容
           */
    showNodeAudit: function(item) {
      const url = item.bps.formScene
      const self = this
      const flowMap = new Map([
        [
          { name: 'RequDopApproval' },
          () => {
            self.auditNodeState = 1
            this.$nextTick(_ => {
              this.$refs.RequDopApproval.init(item)
            })
          }
        ],
        [
          { name: 'AppHiringDep' },
          () => {
            self.auditNodeState = 2
            this.$nextTick(_ => {
              this.$refs.AppHiringDep.init(item)
            })
          }
        ],
        [
          { name: 'FinanceHandl' },
          () => {
            self.auditNodeState = 3
            this.$nextTick(_ => {
              this.$refs.FinanceHandl.init(item)
            })
          }
        ],
        [
          { name: 'RiskHandl' },
          () => {
            self.auditNodeState = 4
            this.$nextTick(_ => {
              this.$refs.RiskHandl.init(item)
            })
          }
        ],
        [
          { name: 'RiskPerCharge' },
          () => {
            self.auditNodeState = 5
            this.$nextTick(_ => {
              this.$refs.RiskPerCharge.init(item)
            })
          }
        ],
        [
          { name: 'ResPerCharge' },
          () => {
            self.auditNodeState = 6
            this.$nextTick(_ => {
              this.$refs.ResPerCharge.init(item)
            })
          }
        ],
        [
          { name: 'InforTchArvl' },
          () => {
            self.auditNodeState = 7
            this.$nextTick(_ => {
              this.$refs.InforTchArvl.init(item)
            })
          }
        ]
      ])

      const shwoFlowNode = name => {
        if (name) {
          console.info('-----------------' + name);
          [...flowMap].forEach(([key, val]) => {
            name.indexOf(key.name) !== -1 ? val.call() : ''
          })
        }
      }
      console.log(url, 'url')
      shwoFlowNode(url)
    }
  }

}
</script>
