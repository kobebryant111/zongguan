<!-- 密码管理 -->
<template>
  <div class="typeset-container">
    <div class="border-card">
      <div class="typeset-body"/>
      <el-table
        :data="systemTable"
        :show-header="false"
        style="width: 100%"
      >
        <el-table-column prop="systemName" label="主键ID" align="left"/>
        <el-table-column label="操作" width="150" >
          <template slot-scope="scope">
            <el-button
              class="blue-border-btn"
              @click="submitBtn(scope.row)"
            >
              查看密码
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pager-zj">
        <pager
          ref="pager"
          :fns="getPager"
          :total="totalNumber"
          :page-size="size"
          :current-page="index"
          v-model="$attrs"
        />
      </div>
      <el-dialog
        :modal="false"
        :visible.sync="modalVisible"
        :title="titleName"
        :before-close="handleClose"
        width="25%"
        center
      >
        <components ref="verifyComp" :is="currentComponent" @scsVerify = "scsVerify" @cancelDialog = "cancelDialog"/>
      </el-dialog>

      <systemPasswordList
        ref="systemPasswordList"
        :system-params="systemParams"
        :system-psd-table="systemPsdTable"
        :modal-psd-visible.sync = "modalPsdVisible"
        @togglePassword = "togglePassword"
        @scsVerify = "scsVerify"
      />
    </div>
  </div>
</template>

<script>

import { systemListSelect, judgeSecondOper, ecdhefirst } from '@/api/rdmanager/rdPasswordMage'
import { generateClientKey, deriveAesKeyAndDecrypt, decryptFromServer } from '@/api/rdmanager/ecdheutil'
import pager from '@/components/Pager'
import secondPassword from '@/views/researchManager/manager/current/secondPassword.vue'
import setSecondPassword from '@/views/researchManager/manager/current/setSecondPassword.vue'
import updateSecondPassword from '@/views/researchManager/manager/current/updateSecondPassword.vue'
import systemPasswordList from '@/views/researchManager/manager/current/systemPasswordList.vue'
export default {
  name: 'PasswordManagement',
  components: { pager, secondPassword, setSecondPassword, updateSecondPassword, systemPasswordList },
  data() {
    return {
      systemParams: '',
      systemPsdTable: [],
      systemTable: [],
      keyPair: null,
      aesKey: null,
      totalNumber: 0,
      currentComponent: '',
      loading: true,
      modalPsdVisible: false,
      modalVisible: false,
      titleName: '',
      size: 10, // 初始值 默认：10
      index: 1 // 初始值 默认：1
    }
  },
  mounted() {
    this.getInit()
  },
  methods: {
    // 分页初始化
    getInit() {
      this.getPager(this.size, this.index)
    },
    getPager: function(size, index) {
      const params = {}
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    getPagerList(params) {
      this.loading = true
      const self = this
      systemListSelect(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          self.systemTable = res.data.results

          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            return false
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    submitBtn(params) {
      judgeSecondOper().then(res => {
        if (res.data.status === 200) {
          if (!res.data.results) {
            this.titleName = '设置密码'
            this.currentComponent = 'setSecondPassword'
            this.modalVisible = true
          } else if ((new Date(res.data.results.invaldate) < new Date())) {
            this.titleName = '当前密码已使用超过90天，请修改密码'
            this.currentComponent = 'updateSecondPassword'
            this.modalVisible = true
          } else {
            this.currentComponent = 'secondPassword'
            this.titleName = '二次身份认证'
            this.modalVisible = true
            this.$nextTick(() => {
              this.$refs.verifyComp.sysPparams(params)
            })
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // async function ecdhe(){},
    // 校验通过
    async scsVerify(data) {
      // debugger
      const params = await generateClientKey()
      console.log(params.privateKey.length, '16sfaf')
      ecdhefirst({ clientPubKey: params.publicKey, systemNo: data.systemNo }).then(async(res) => {
        if (res.data.status === 200) {
          this.aesKey = await deriveAesKeyAndDecrypt(params.privateKey, res.data.results.serverPubKeyEncodedBase)
          this.systemPsdTable = res.data.results.sysPwDtos
          this.systemParams = data
          this.modalPsdVisible = true
          // console.log(aesKey, '成功了吗')
        } else {
          this.$message.error(res.data.message)
        }
      })
      // console.log(hash, '============')
    },

    // 解密
    async togglePassword(row) {
      // debugger
      const promise = await decryptFromServer(row.psd, row.iv, this.aesKey)
      row.psd = promise
      // row.showRealPsd = '显示'
      this.$set(row, 'showRealPsd', '显示')
    },
    // 关闭密码设置、修改、校验弹窗
    cancelDialog() {
      this.modalVisible = false
    },
    // 关闭系统密码展示窗口
    sysPsdDialog() {
      this.modalPsdVisible = false
    },
    handleClose(done) {
      this.$confirm('确认关闭？').then(_ => {
        this.$refs.verifyComp.clearPwdForm()
        done()
      }).catch(_ => {})
    }
  }
}
</script>

<style scoped>
.typeset-container {
  padding: 10px;
}
.border-card {
  padding: 1px;
  background-color: #ffffff;
}
/* 查看按钮颜色 */
.blue-border-btn{
  border-color: #409eff
}
.pager-zj {
  text-align: center;
}
</style>
