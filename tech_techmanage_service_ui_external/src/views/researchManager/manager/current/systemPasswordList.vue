<template>
  <div>
    <el-dialog
      :modal="false"
      :visible.sync="modalPsdVisible"
      :title="sysTitleName"
      :before-close="handleClose"
      width="65%"
      center
      @open = "onDialogOpen"
      @close = "onDialogClose"
    >
      <div>
        <span >
          <div style="text-align: right;padding:15px 10px">
            <el-button @click="addData">新 增</el-button>
            <el-button @click="toggleDelete">{{ deletebtn }}</el-button>
          </div>
        </span>
      </div>
      <div>
        <el-table
          ref="multipleTable"
          :data="systemPsdTable"
          :show-header="false"
          tooltip-effect="dark"
          border
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" align="left" width="55"/>
          <el-table-column prop="sysName" label="名称" align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ scope.row.sysName }}</span>
              <el-input v-if="scope.row.edited" v-model="scope.row.sysName" maxlength="32" show-word-limit placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column prop="psd" label="密码" align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ scope.row.showRealPsd === '显示' ? scope.row.psd : '********' }}</span>
              <el-input v-if="scope.row.edited" v-model="scope.row.psd" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150" fixed="right">
            <template slot-scope="scope">
              <div class="op-btns">
                <el-badge
                  class="item">
                  <el-button
                    :style="{color: '#606266'}"
                    class="big-icon-btn"
                    icon="el-icon-view"
                    @click="togglePsd(scope.row)"
                  />
                </el-badge>
                <el-badge
                  v-if="!scope.row.edited"
                  :is-dot = "isDot(scope.row)"
                  class="item">
                  <el-button
                    :style="{color: '#606266'}"
                    class="big-icon-btn"
                    icon="el-icon-edit-outline"
                    @click="handleUpdate(scope.row)"
                  />
                </el-badge>
                <el-badge
                  v-if="scope.row.edited"
                  class="item">
                  <el-button
                    icon="el-icon-folder-checked"
                    @click="handleSave(scope.row)"
                  />
                </el-badge>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
    <el-dialog
      :modal="false"
      :visible.sync="dotModalVisible"
      :before-close="dotHandleClose"
      width="25%"
      center
    >
      <span>{{ textdialog }}</span>
      <el-popover
        v-if="popoverBtn"
        content="8位以上，由大写字母、小写字母、数字、特殊字符中三种以上组成"
        placement="bottom"
        width="200"
        trigger="hover"
      >
        <el-button
          slot="reference"
          :style="{color: '#606266'}"
          icon="el-icon-question"
          type="text"/>
      </el-popover>
    </el-dialog>
    <addSysPsdLit
      :system-params="systemParams"
      :modal-visible.sync="modalVisible"
      @addSysPsdOk = "addSysPsdOk"/>
  </div>
</template>
<script>
import { generateClientKey, deriveAesKeyAndDecrypt, encryptParams } from '@/api/rdmanager/ecdheutil'
import addSysPsdLit from '@/views/researchManager/manager/current/addSysPsdLit.vue'
import { deleteSysPsd, addecdhefirst, updataSysPsdList } from '@/api/rdmanager/rdPasswordMage'
export default {
  name: 'SystemPasswordList',
  components: { addSysPsdLit },
  props: {
    systemPsdTable: {
      type: Array,
      required: true
    },
    systemParams: {
      type: [Object, String], // 允许 Object 或 String
      default: () => ({})
    },
    modalPsdVisible: {
      type: Boolean,
      default: false //  布尔类型的默认值 false
    }
  },
  data() {
    return {
      modalVisible: false,
      deletebtn: '删 除',
      multipleSelection: [],
      dotModalVisible: false,
      textdialog: '',
      popoverBtn: false,
      idleTimer: null,
      idleLimit: 1000 * 5 * 60
    }
  },
  computed: {
    sysTitleName() {
      return this.systemParams.systemName
    }
  },
  watch: {
  },
  created() {
  },
  methods: {
    // 编辑
    handleUpdate(params) {
      if ((new Date(params.invaldate) < new Date())) {
        this.dotModalVisible = true
        this.popoverBtn = false
        this.textdialog = '该密码已90天未修改，请及时进行修改'
        this.updateIdDot(params)
      } else if (params.rulesTorf === 'N') {
        this.popoverBtn = true
        this.dotModalVisible = true
        params.rulesTorf = 'Y'
        this.updateIdDot(params)
        this.textdialog = '该密码不符合密码设置规则，请尽快修改密码'
      }
      this.$set(params, 'edited', true)
      this.togglePsd(params)
    },
    async updateIdDot(data) {
      const params = await generateClientKey()
      addecdhefirst({ clientPubKey: params.publicKey }).then(async(res) => {
        if (res.data.status === 200) {
          const aesKey = await deriveAesKeyAndDecrypt(params.privateKey, res.data.results)
          const updata = await encryptParams(aesKey, data)
          updataSysPsdList(updata).then(async(res) => {
            if (res.data.status === 200) {
              // this.$emit('scsVerify', this.systemParams)
            } else {
              this.subfail('', res.data.message)
            }
          })
        } else {
          this.subfail('', res.data.message)
        }
      })
    },
    // 保存
    async handleSave(data) {
      this.rulseUpdate(data)
      const params = await generateClientKey()
      addecdhefirst({ clientPubKey: params.publicKey }).then(async(res) => {
        if (res.data.status === 200) {
          const aesKey = await deriveAesKeyAndDecrypt(params.privateKey, res.data.results)
          const updata = await encryptParams(aesKey, data)
          updataSysPsdList(updata).then(async(res) => {
            if (res.data.status === 200) {
              this.$set(data, 'edited', false)
              this.$emit('scsVerify', this.systemParams)
            } else {
              this.subfail('', res.data.message)
            }
          })
        } else {
          this.subfail('', res.data.message)
        }
      })
    },
    // 规则校验
    rulseUpdate(data) {
      // 是数字
      var isDigit = /^.*[0-9]+.*/
      // isLowerCase 小写字母
      var isLowerCase = /^.*[a-z]+.*/
      // isUpperCase 大写字母
      var isUpperCase = /^.*[A-Z]+.*/
      // 特殊字符
      var regEx = /^.*[^a-zA-Z0-9]+.*/
      // 记录匹配的次数
      let containtype = 0
      if (isDigit.test(data.psd)) {
        containtype++
      }
      if (isLowerCase.test(data.psd)) {
        containtype++
      }
      if (isUpperCase.test(data.psd)) {
        containtype++
      }
      if (regEx.test(data.psd)) {
        containtype++
      }
      if (data.sysName.length === 0) {
        this.$message.warning('名称不能为空')
        data.rulesTorf = 'N'
      } else if (data.psd.length < 8) {
        this.$message.warning('密码不能少于8位')
        data.rulesTorf = 'N'
      } else if (containtype < 3) {
        this.$message.warning('密码至少包含以下三种：大/小写字母，数字，特殊字符')
        data.rulesTorf = 'N'
      } else {
        data.rulesTorf = 'Y'
      }
    },
    // 删除
    toggleDelete() {
      if (this.multipleSelection.length === 0) {
        this.subfail('', '请选择至少一条信息')
        return
      }
      const params = []
      const name = this.multipleSelection.map(item => item.sysName).join('、')
      this.multipleSelection.forEach(row => {
        params.push(row.id)
      })
      this.$confirm('确认删除' + name + '？', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteSysPsd({ ids: params }).then(res => {
          if (res.data.status === 200) {
            this.$emit('scsVerify', this.systemParams)
          } else {
            this.subfail('', res.data.message)
          }
        })
      }
      )
    },
    addSysPsdOk() {
      this.$emit('scsVerify', this.systemParams)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      if (val.length > 0) {
        this.deletebtn = '确认删除'
      } else {
        this.deletebtn = '删 除'
      }
      // console.log('--------------', val)
    },
    addData() {
      this.modalVisible = true
    },
    // 显示密码
    togglePsd(params) {
      if (!params.showRealPsd) {
        this.$emit('togglePassword', params)
      } else if (params.showRealPsd === '显示') {
        this.$set(params, 'showRealPsd', '隐藏')
      } else if (params.showRealPsd === '隐藏') {
        this.$set(params, 'showRealPsd', '显示')
      }
    },
    handleClose(done) {
      this.$confirm('确认关闭？').then(_ => {
        this.$emit('update:modalPsdVisible', false)
      }).catch(_ => {})
    },
    dotHandleClose(done) {
      this.$confirm('确认关闭？').then(_ => {
        done()
      })
    },
    // 开始计时
    onDialogOpen() {
      this.resetIdleTimer()
      this.bindActivtyListener()
    },
    onDialogClose() {
      this.clearIdleTimer()
      this.unbindActivityListener()
    },
    resetIdleTimer() {
      this.clearIdleTimer()
      this.idleTimer = setTimeout(() => {
        this.$emit('update:modalPsdVisible', false)
        this.modalVisible = false
      }, this.idleLimit)
    },
    clearIdleTimer() {
      if (this.idleTimer) {
        clearTimeout(this.idleTimer)
        this.idleTimer = null
      }
    },

    bindActivtyListener() {
      // 操作事件打断计算
      // document.addEventListener('mousemove', this.resetIdleTimer)
      document.addEventListener('keydown', this.resetIdleTimer) // 键盘点击事件
      document.addEventListener('mousedown', this.resetIdleTimer) // 鼠标点击事件
      // document.addEventListener('touchstart', this.resetIdleTimer)
    },
    unbindActivityListener() {
      // document.removeEventListener('mousemove', this.resetIdleTimer)
      document.removeEventListener('keydown', this.resetIdleTimer) // 键盘点击事件
      document.removeEventListener('mousedown', this.resetIdleTimer) // 鼠标点击事件
      // document.removeEventListener('touchstart', this.resetIdleTimer)
    },
    // 编辑图标是否高亮
    isDot(params) {
      // debugger
      if ((new Date(params.invaldate) < new Date()) || params.rulesTorf === 'N') {
        return true
      } else {
        return false
      }
    },
    // 提示函数
    subfail(res, msg) {
      this.$message({
        message: msg,
        type: 'error'
      })
    }
  }
}
</script>

<style scoped>

.op-btns {
  display: flex;
  justify-content: space-between;
  width: 100%;
}
.big-icon-btn {
  font-size: 9px
}
.item {
  margin-top: 4px;
  margin-right: 1px
}
</style>
