<!-- 人力结算人员准入申请 -->
<template>
  <div class="contentWb">
    <div class="nav">
      <div class="zr-nav-apply-left">
        <p v-if="wbrzData.userType == 0" class="nav_name">外包人员准入申请</p>
        <p v-else class="nav_name">外包人员准入申请</p>
        <p class="nav_name">{{ wbrzData.userName }}</p>
      </div>
      <div>
        <buttonCommon :button-name="buttonClose" :size="size" :fns="close"/>
      </div>
    </div>
    <!-- 人员准入申请 -->
    <div v-if="currentShow" class="contentWbry">
      <!-- 步骤条 -->
      <Steps ref="steps" @currentV="cut"/>
      <!-- 步骤条内容 -->
      <jiben v-if="current==0" ref="jiben" :instance-id="instanceId" @verifyJiben="verify" @on-organizer-select="handleOrganizerSelect"/>
      <pzms v-else-if="current==1" ref="pzms" :instance-id="instanceId" :evaluatorValue="evaluatorValue" @verifyPzms="verify"/>
      <jianli v-else-if="current==2" ref="jianli" :instance-id="instanceId" @verifyJianli="verify"/>
      <pxrz v-else-if="current==3" ref="pxrz" :instance-id="instanceId" @verifyPxrz="verify"/>
      <finishs v-else="current==4" :instance-id="instanceId"/>
    </div>
    <div class="czLog">
      <flow-logs ref="log"/>
    </div>
    <!-- 底部操作栏 -->
    <div v-if="current!=4" class="zr-apply-bottom">
      <div>
        <button-delete :button-name="buttonDel" :size="size" :fns="delBox"/>
      </div>
      <div class="zr-apply-bottom-right">
        <div>
          <buttonCommon :button-name="buttonKeep" :size="size" :fns="save"/>
        </div>
        <div class="zr-apply-bottom-right-last">
          <buttonCommon v-show="current != 0 ? true : false" :button-name="buttonLast" :size="size" :fns="prevF"/>
        </div>
        <div>
          <el-button size="mini" class="nextButton" @click="nextF()">{{ current!=3?"下一步":"提交" }}</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import common from '@/api/common'
import Steps from '@/components/Steps'
import jiben from '@/views/crewManager/wbrz/rzSaveBack/jibenBack'
import pzms from '@/views/crewManager/wbrz/rzSaveBack/pzmsBack'
import jianli from '@/views/crewManager/wbrz/rzSaveBack/jianliBack'
import pxrz from '@/views/crewManager/wbrz/rzSaveBack/pxrzBack'
import finishs from '@/views/crewManager/wbrz/rzSaveBack/finishsBack'
import buttonDelete from '@/components/eleButton/delete' // 引入删除按钮
import buttonCommon from '@/components/eleButton/buttonCommon' // 引入按钮组件2
import { queryWorkItem } from '@/api/integrated/wbryzrlc'
import { getWbrzDetail, doDel } from '@/api/integrated/newWbrz'
import flowLogs from '@/views/plugins/camunda/FlowLogs'
export default {
  components: { Steps, jiben, pzms, jianli, pxrz, finishs, buttonDelete, buttonCommon, flowLogs },
  data() {
    return {
      current: 0, // 人员准入步骤条第几步的内容
      id: -1, // 保存时传递的参数
      instanceId: '', // 第四步获取详情传递的参数
      currentShow: true, // 判断展示人员准入界面或者项目准入
      userName: '', // 姓名
      provingMessage: {}, // 验证信息
      provingMessages: [], // 新验证信息
      countFin: 0, // first点保存时的错误个数
      counted: 0, // 总的错误信息
      counts: 0, // 新总的错误信息
      organizers: '', // 行内负责人
      jobId: '', // 工号
      outsourceNo: '', // 供应商
      depName: '', // 部门
      deptno: '', // 部门编号
      buttonDel: '删 除', // 删除按钮
      buttonLast: '上一步', // 上一步按钮
      size: 'mini', // 按钮大小
      buttonKeep: '存草稿',
      buttonClose: '关 闭',
      processDefName: '', // 流程定义名称
      workItemID: '', // 子表
      wbrzData: {}, // 外包入职详情
      evaluatorValue: {}, // 定级人员行号
      bps: {}
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    // 初始化
    init(item) {
      console.log('外包入职退回保存', item)
      this.instanceId = item.processInstanceId
      this.$nextTick(() => {
        this.getDatail()
      })
    },

    // 获取详情
    getDatail() {
      getWbrzDetail({ instanceId: this.instanceId }).then(res => {
        if (res.data.status === 200) {
          this.wbrzData = res.data.results
          this.instanceId = res.data.results.instanceId
          this.$refs.log.init(res.data.results.instanceId)
          this.$nextTick(() => {
            this.getWorkItem()
          })
        } else {
          this.$message.error('获取数据失败')
        }
      })
    },

    // 步骤条下一步
    nextF() {
      if (this.current === 0) {
        this.$refs.jiben.verify()
      } else if (this.current === 1) {
        this.$refs.pzms.verify()
      } else if (this.current === 2) {
        this.$refs.jianli.verify()
      } else if (this.current === 3) {
        this.$refs.pxrz.verify()
      }
    },

    // 基本信息验证必填
    verify(val) {
      if (val === true) {
        if (this.current === 0) {
          this.$refs.jiben.savejiben().then((val) => {
            if (val.status === 200) {
              this.$nextTick(() => {
                this.$refs.steps.next()
                // this.$refs.pzms.init();
              })
            }
          })
        } else if (this.current === 1) {
          this.$refs.pzms.savePzms().then((val) => {
            if (val.status === 200) {
              this.$nextTick(() => {
                this.$refs.steps.next()
              })
            }
          })
        } else if (this.current === 2) {
          this.$refs.jianli.saveJianli().then((val) => {
            if (val.status === 200) {
              this.$nextTick(() => {
                // this.$refs.pxrz.init();
                this.$refs.steps.next()
              })
            }
          })
        } else if (this.current === 3) {
          this.$refs.pxrz.liuzhuanPxrz().then((val) => {
            if (val.status === 200) {
              this.$nextTick(() => {
                this.$refs.steps.next()
              })
            }
          })
        }
      } else {
        this.$message.error('请正确填写')
      }
    },

    prevF() { // 步骤条上一步
      this.$refs.steps.prev()
    },

    cut(v) {
      this.current = v
    },

    // 存草稿
    save() {
      if (this.current === 0) {
        this.$refs.jiben.savejiben()
      } else if (this.current === 1) {
        this.$refs.pzms.savePzms()
      } else if (this.current === 2) {
        this.$refs.jianli.saveJianli()
      } else if (this.current === 3) {
        this.$refs.pxrz.savePxrz()
      }
    },

    // 删除流程
    delPro() {
      const loading = common.loading(this)
      const params = {}
      params.instanceId = this.instanceId
      const data = common.paramsFormat(params)
      doDel(data).then(res => {
        // 关闭loading
        loading.close()
        if (res.data.status === 200) {
          this.$router.push({ name: 'dashboard' })
          this.$message.success('流程删除成功')
        } else {
          this.$message.error('流程删除失败')
        }
      })
    },

    // 选中项目经理
    handleOrganizerSelect(organizers) {
      this.evaluatorValue = organizers
    },

    // 获取待办工作项详情-单条
    getWorkItem() {
      const params = {}
      params.processInstID = this.instanceId
      queryWorkItem(params).then(res => {
        if (res.data.status === 200) {
          this.bps = res.data.results
          this.processDefName = res.data.results.processDefName
          this.workItemID = res.data.results.workItemID
        } else {
          this.$message.error('获取待办工作项详情失败')
        }
      })
    },

    // 打开删除messageBox窗口
    delBox() {
      this.$confirm('确认删除该流程吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delPro()
      }).catch(() => {

      })
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: 'dashboard' })
    }
  }
}
</script>
<style scoped>
  .contentWbry {
    height: 100%;
    font-size: 16px;
    padding: 10px 15px 0 15px;
  }

  .nav {
    width: 100%;
    background-color: #ffffff;
    padding: 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .nav .wrapper {
    margin-left: 25px;
    margin-right: 25px;
    display: flex;
    justify-content: space-between;
  }

  .wrapper_left {
    display: flex;
    align-items: center;
  }

  .zr-nav-apply-left>p {
    font-size: 16px;
    font-weight: 600;
    color: rgba(38, 38, 38, 1);
    display: inline;
  }

  /* 底部操作栏按钮修改 */
   .nextButton {
    background-color: #FAAD14;
    border-color: #FAAD14;
    color: #FFFFFF;
  }

   .nextButton:hover {
    color: #ffffff;
    background-color: #ebb563;
    border-color: #ebb563;
  }

  .zr-apply-bottom {
    width: 100%;
    padding: 15px;
    margin-top: 20px;
    background-color: #ffffff;
    display: flex;
    justify-content: space-between;
  }

  .zr-apply-bottom-right-last {
    margin: 0 10px;
  }

  .zr-apply-bottom-right {
    display: flex;
    justify-content: space-between;
  }

  .czLog {
    padding: 0 15px;
  }
</style>

<style>
  /* 关闭按钮 */
  .contentWb .el-icon-circle-close:before {
    color: #F5222D;
    height: 100%;
  }

  .wrapper_right_menu {
    height: 500px;
    overflow-y: scroll;
  }

  .el-dropdown-menu--medium .el-dropdown-menu__item {
    border-top: 1px solid #e8e8e8;
  }

  .el-dropdown-menu--medium .el-dropdown-menu__item:first-child {
    border-top: none;
  }

  .el-dropdown-menu--medium .el-icon-circle-close:before {
    color: #F5222D;
  }

  .wrapper_right_menu .el-dropdown-menu__item {
    display: flex;
    flex-direction: row;
  }
</style>
