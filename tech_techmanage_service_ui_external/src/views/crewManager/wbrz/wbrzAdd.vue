<!-- 人力结算人员准入申请 -->
<template>
  <div class="contentWb">
    <div class="nav">
      <div class="zr-nav-apply-left">
        <p v-if="char == 0" class="nav_name">外包人员准入申请</p>
        <!-- <p v-else class="nav_name">外包人员准入申请--项目结算（员工需指定具体的项目）</p> -->
        <p class="nav_name">{{userName}}</p>
      </div>
      <div>
        <buttonCommon :buttonName="buttonClose" :size="size" :fns="close"></buttonCommon>
      </div>
    </div>
    <!-- 人员准入申请 -->
    <div class="contentWbry" v-if="currentShow">
      <!-- 步骤条 -->
      <Steps ref="steps" @currentV="cut"></Steps>
      <!-- 步骤条内容 -->
      <jiben ref="jiben" v-show="current==0" @verifyJiben="verify" :bps="bps"  @banIf="banIf" @delBtnIf="delBtnIfs" @on-organizer-select="handleOrganizerSelect"></jiben>
      <pzms ref="pzms" v-show="current==1" :id="id" :instanceId="instanceId" :msInstanceid="msInstanceid" :userType="userType" 
        :evaluatorValue="evaluatorValue"
        :deptno="deptno" :bps="bps" @verifyPzms="verify"></pzms>
      <jianli ref="jianli" v-show="current==2" :id="id" :instanceId="instanceId" :bps="bps" @verifyJianli="verify"></jianli>
      <pxrz ref="pxrz" v-show="current==3" :id="id" :instanceId="instanceId" :bps="bps" @verifyPxrz="verify"></pxrz>
      <finishs ref="finishs" v-show="current==4" :userName="userName" :jobId="jobId" :depName="depName"
        :organizers="organizers"></finishs>
    </div>

    <!-- 底部操作栏 -->
    <div class="zr-apply-bottom" v-if="current!=4">
      <div>
        <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete>
      </div>
      <div class="zr-apply-bottom-right">
        <div>
          <buttonCommon :buttonName="buttonKeep" :size="size" :disabled="front" :fns="save"></buttonCommon>
        </div>
        <div class="zr-apply-bottom-right-last">
          <buttonCommon :buttonName="buttonLast" :size="size" :fns="prevF" v-show="current != 0 ? true : false"></buttonCommon>
        </div>
        <div>
          <el-button size="mini" @click="nextF()" class="nextButton" >{{current!=3?"下一步":"提交"}}</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import common from "@/api/common";
  import Steps from '@/components/Steps'
  import jiben from '@/views/crewManager/wbrz/rzApply/jiben'
  import pzms from '@/views/crewManager/wbrz/rzApply/pzms'
  import jianli from '@/views/crewManager/wbrz/rzApply/jianli'
  import pxrz from '@/views/crewManager/wbrz/rzApply/pxrz'
  import finishs from '@/views/crewManager/wbrz/rzApply/finishs'
  import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
  import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
  import { getWbrzDetail, doDel } from "@/api/integrated/newWbrz";
  import { queryWorkItem } from "@/api/integrated/wbryzrlc";
  
  export default {
    components: { Steps, jiben, pzms, jianli, pxrz, finishs, buttonDelete, buttonCommon },
    data() {
      return {
        current: 0, // 人员准入步骤条第几步的内容
        id: "", //保存时传递的参数
        instanceId: '', //第四步获取详情传递的参数
        currentShow: true, //判断展示人员准入界面或者项目准入
        userName: '', //姓名
        provingMessage: {}, //验证信息
        provingMessages: [], //新验证信息
        countFin: 0, //first点保存时的错误个数
        counted: 0, //总的错误信息
        counts: 0,//新总的错误信息
        organizers: '', //行内负责人
        jobId: '', //工号
        outsourceNo: '', //供应商
        depName: '', //部门
        deptno: '', //部门编号
        buttonDel: '删 除', //删除按钮
        delBtnIf: false, //删除按钮是否出现
        buttonLast: '上一步', //上一步按钮
        size: 'mini', //按钮大小
        buttonKeep: '存草稿',
        buttonClose: '关 闭',
        front: true, //是否可点击下一步和存草稿按钮
        processDefName: '', //流程定义名称
        workItemID: '', //子表 
        bps: {},
        msInstanceid: '', //面试流程ID
        userType: '', //结算方式
        char: '', //结算方式判断
        evaluatorValue: {}, //定级人员行号
      };
    },
    created() {

    },
    mounted() {
      // console.log("入口", this.$route.params);
      this.char = this.$route.params.char;
    },
    watch: {

    },
    methods: {
      //初始化
      init(val) {
        // console.log('val', val);
        this.char = val.char;
      },

      //是否可点击操作按钮
      banIf(val) {
        this.front = val;
      },

      //步骤条下一步
      nextF() {
        if (this.current == 0) {
          this.$refs.jiben.verify();
        } else if (this.current == 1) {
          this.$refs.pzms.verify();
        } else if (this.current == 2) {
          this.$refs.jianli.verify();
        } else if (this.current == 3) {
          this.$refs.pxrz.verify();
        }
      },

      // 基本信息验证必填
      verify(val) {
        if (val == true) {
          if (this.current == 0) {
            this.$refs.jiben.savejiben().then((val) => {
              if (val[0].status == 200) {
                if (val[0].results != undefined) {
                  this.id = val[0].results.id;
                  this.instanceId = val[0].results.instanceId;
                }
                this.deptno = val[1].deptno;
                this.userName = val[1].userName;
                this.jobId = val[1].jobId;
                this.organizers = val[1].organizersName;
                this.outsourceNo = val[1].outsourceNo;
                this.depName = val[1].depName;
                this.userType = val[1].userType;
                this.msInstanceid = val[1].instanceId;
                this.$nextTick(() => {
                  this.getWorkItem();
                  this.$refs.steps.next();
                  this.$refs.pzms.init(val[1].certifNumber);
                })
              }
            });
          } else if (this.current == 1) {
            this.$refs.pzms.savePzms().then((val) => {
              if (val.status == 200) {
                this.$nextTick(() => {
                  this.$refs.steps.next();
                  this.$refs.jianli.init();
                })
              }
            });
          } else if (this.current == 2) {
            this.$refs.jianli.saveJianli().then((val) => {
              if (val.status == 200) {
                this.$nextTick(() => {
                  this.$refs.steps.next();
                  this.$refs.pxrz.init();
                })
              }
            });
          } else if (this.current == 3) {
            this.$refs.pxrz.liuzhuanPxrz().then((val) => {
              if (val.status == 200) {
                this.$nextTick(() => {
                  this.$refs.steps.next();
                  this.$refs.finishs.getWbcom(this.outsourceNo);
                })
              }
            });
          }

        } else {
          this.$message.error('请正确填写')
        }
      },

      prevF() { //步骤条上一步
        this.$refs.steps.prev();
      },

      cut(v) {
        this.current = v;
      },

      //存草稿
      save() {
        if (this.current == 0) {
          this.$refs.jiben.savejiben();
        } else if (this.current == 1) {
          this.$refs.pzms.savePzms();
        } else if (this.current == 2) {
          this.$refs.jianli.saveJianli();
        } else if (this.current == 3) {
          this.$refs.pxrz.savePxrz();
        }
      },

      // 删除按钮是否出现
      delBtnIfs(val) {
        this.delBtnIf = true;
        this.instanceId = val;
        this.$nextTick(() => {
          this.getWorkItem();
        })
      },

      // 删除流程
      delPro() {
        const loading = common.loading(this);
        let params = {};
        params.processInstID = this.instanceId;
        params.workItemID = this.workItemID;
        params.processDefName = this.processDefName;
        let data = common.paramsFormat(params);
        doDel(data).then(res => {
          // 关闭loading
          loading.close()
          if (res.data.status == 200) {
            this.$router.push({ name: 'wbrzManage' });
            this.$message.success('流程删除成功');
          } else {
            this.$message.error('流程删除失败');
          }

        });
      },

      //选中项目经理
      handleOrganizerSelect(organizer) {
        this.evaluatorValue = organizer;
      },

      //获取待办工作项详情-单条
      getWorkItem() {
        let params = {};
        params.processInstID = this.instanceId;
        queryWorkItem(params).then(res => {
          if (res.data.status == 200) {
            this.bps = res.data.results;
            this.processDefName = res.data.results.processDefName;
            this.workItemID = res.data.results.workItemID;
          } else {
            this.$message.error('获取待办工作项详情失败')
          }

        });
      },

      // 打开删除messageBox窗口
      delBox() {
        this.$confirm('确认删除该流程吗?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // this.getWorkItem();
          this.delPro();
          // this.$router.back();
        }).catch(() => {

        })
      },

      // 关闭按钮
      close() {
        this.$router.push({ name: 'wbrzManage' });
      },
    }
  };
</script>
<style scoped>
  .contentWbry {
    height: 100%;
    font-size: 16px;
    padding: 10px 15px;
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
</style>

<style>
  /* 关闭按钮 */
  .contentWb .el-icon-circle-close:before {
    color: #F5222D;
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