<!-- 花名册档案 -->
<template>
  <div class="archives-container">
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="3">
              <p class="nav_name">个人档案</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common v-if="rydaDisplay" :button-name="buttonClose" :size="size" :fns="close"/>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <div class="archives-container-main">
      <div class="archives-container-main-left">
        <div class="main-left-title">
          <span class="hucName">外包花名册</span>/
          <span class="grdaName">个人档案</span>
        </div>
        <div class="main-left-content">
          <div class="main-left-content-photo">
            <img :src="facePhotoUrl" class="finish_img">
          </div>
          <div class="main-left-content-top">
            <p class="xkg-color-black main-left-content-top-name">
              {{ jibenData.userName == undefined ? '无' :
              jibenData.userName }}
            </p>
            <p class="xkg-color-black main-left-content-top-jobId">
              {{ jibenData.jobId == undefined ? '无' :
              jibenData.jobId }}
            </p>
          </div>
          <div class="main-left-content-middle">
            <p class="xkg-color-grey xkg-fontsize-14px">
              {{ jibenData.postname == undefined ? '无' :
              jibenData.postname }}
            </p>
            <p
              class="xkg-color-grey xkg-fontsize-14px"
            >{{ jibenData.depname == undefined ? '无' : jibenData.depname }}</p>
            <p
              class="xkg-color-grey xkg-fontsize-14px"
            >{{ jibenData.groupname == undefined ? '无' : jibenData.groupname }}</p>
          </div>
          <div class="main-left-content-bottom">
            <div class="marginbottom-20px main-left-content-phone">
              <p class="xkg-color-grey xkg-fontsize-14px">联系电话</p>
              <p
                class="xkg-fontsize-14px"
              >{{ jibenData.mobile == undefined ? '无' : jibenData.mobile }}</p>
            </div>
            <div class="marginbottom-20px main-left-content-phone">
              <p class="xkg-color-grey xkg-fontsize-14px">项目经理</p>
              <p class="xkg-fontsize-14px">
                {{ jibenData.personInChargename }}
                ({{ jibenData.personInCharge }})
              </p>
            </div>
            <div class="marginbottom-20px main-left-content-phone">
              <p class="xkg-color-grey xkg-fontsize-14px">供应商</p>
              <p
                class="xkg-fontsize-14px"
              >{{ jibenData.outsourcname == undefined ? '无' : jibenData.outsourcname }}</p>
            </div>
            <div class="main-left-content-phone">
              <p class="xkg-color-grey xkg-fontsize-14px">状态</p>
              <p
                class="xkg-fontsize-14px"
              >{{ jibenData.statename == undefined ? '无' : jibenData.statename }}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="archives-container-main-right">
        <el-tabs v-model="activeTabName" @tab-click="handleClick">
          <el-tab-pane label="基本信息" name="jbxx">
            <hmc-jiben :jiben-detail="jibenData" :auth="auth"/>
          </el-tab-pane>
          <el-tab-pane label="面试信息" name="pzms">
            <hmc-pzms ref="hmcPzms" :tuozhan-detail="tuozhanData" :interview-detail="interviewData"/>
          </el-tab-pane>
          <!-- <el-tab-pane label="工作情况" name="gzqk">
            <hmc-work-condition :jibenDetail="jibenData"></hmc-work-condition>
          </el-tab-pane>
          <el-tab-pane label="培训成绩和历经采购项目" name="pxcg">
            <hmc-pxcg :jibenDetail="jibenData"></hmc-pxcg>
          </el-tab-pane>
          <el-tab-pane label="排查信息" name="pcxx">
            <hmc-pcxx :jibenDetail="jibenData"></hmc-pcxx>
          </el-tab-pane> -->
          <el-tab-pane label="简历信息" name="jlxx">
            <hmc-jlxx ref="jlxx" :auth="auth"/>
          </el-tab-pane>
          <el-tab-pane label="入职材料" name="rzcl">
            <hmc-rzcl :tuozhan-detail="tuozhanData" :auth="auth"/>
          </el-tab-pane>
          <el-tab-pane label="相关流程和操作日志" name="lcrz">
            <hmc-xglc-czrz :jiben-detail="jibenData"/>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>
<script>
import buttonCommon from '@/components/eleButton/buttonCommon'
import hmcJiben from '@/views/crewManager/hmcArchives/hmcJiben'
import hmcPzms from '@/views/crewManager/hmcArchives/hmcPzms'
import hmcWorkCondition from '@/views/crewManager/hmcArchives/hmcWorkCondition'
import hmcPxcg from '@/views/crewManager/hmcArchives/hmcPxcg'
import hmcPcxx from '@/views/crewManager/hmcArchives/hmcPcxx'
import hmcJlxx from '@/views/crewManager/hmcArchives/hmcJlxx'
import hmcRzcl from '@/views/crewManager/hmcArchives/hmcRzcl'
import hmcXglcCzrz from '@/views/crewManager/hmcArchives/hmcXglcCzrz'
import common from '@/api/common'
import Moment from 'moment'
import {
  getOutsBasicInfo,
  getExtDetailAll,
  // eslint-disable-next-line no-unused-vars
  update,
  // eslint-disable-next-line no-unused-vars
  getIntervieweeInfo,
  // eslint-disable-next-line no-unused-vars
  queryDetailExt,
  queryPermission
} from '@/api/integrated/hmcDa'
import { queryInterDataList } from '@/api/integrated/wbms'
import { getImageUrl } from '@/api/integrated/newWbrz'
export default {
  name: 'HmcArchives',
  components: {
    buttonCommon,
    hmcJiben,
    hmcPzms,
    hmcWorkCondition,
    hmcPxcg,
    hmcPcxx,
    hmcJlxx,
    hmcRzcl,
    hmcXglcCzrz
  },
  props: {
    rydaDisplay: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      buttonClose: '关 闭', // 取消按钮名字
      size: 'mini', // 按钮大小
      activeTabName: 'jbxx', // 默认tab
      jobId: '',
      jibenData: {}, // 基本数据详情
      tuozhanData: {}, // 拓展信息详情
      interviewData: [], // 面试信息详情
      facePhotoUrl: '', // 人脸图片地址
      auth: '', // 权限
      badge: '', // 人员档案工号
      createFlag: false
    }
  },
  created() {
    const loading = common.loading(this)
    // console.log(this.rydaDisplay)
    // console.log("----------------" ,this.$route.query)
    if (this.$route.query != null && this.$route.query.activeTabName === 'jlxx') {
      this.activeTabName = this.$route.query.activeTabName
      this.createFlag = true
      this.jobId = this.$route.query.param
    }
    loading.close()
  },
  mounted() {
    this.$nextTick(_ => {
      this.jobId = this.$route.query.param || this.badge
      this.getAuth()
      this.getJiBenDetail()
      if (this.createFlag) {
        this.$refs.jlxx.getTuozhanDetail(this.jobId)
        this.createFlag = false
      }
    })
  },
  methods: {
    getJiBenDetail() {
      const loading = common.loading(this)
      getOutsBasicInfo({ jobId: this.jobId })
        .then(res => {
          loading.close()
          if (res.data.results.faceimage != undefined) {
            this.getPhotoUrl(res.data.results.faceimage)
          }
          if (res.data.results.entryTime != undefined) {
            res.data.results.entryTime = Moment(
              res.data.results.entryTime
            ).format('YYYY-MM-DD')
          }
          if (res.data.results.leavingTime != undefined) {
            res.data.results.leavingTime = Moment(
              res.data.results.leavingTime
            ).format('YYYY-MM-DD')
          }
          if (
            res.data.results.isOtherProject != undefined &&
            res.data.results.isOtherProject == 'true'
          ) {
            res.data.results.isOtherProject = true
          } else if (
            res.data.results.isOtherProject != undefined &&
            res.data.results.isOtherProject == 'false'
          ) {
            res.data.results.isOtherProject = false
          }
          if (res.data.results.deptId.indexOf('dep') == -1) {
            res.data.results.deptId = 'dep' + res.data.results.deptId
          }
          this.jibenData = res.data.results

          this.getTuozhanDetail()
          this.getInterviewDetail(res.data.results.certificateNumber)
        }).catch(error => {
          this.$message.error('查询基本信息失败')
        })
    },

    // 获取图片地址
    getPhotoUrl(id) {
      getImageUrl({ documentno: id }).then(res => {
        if (res.data.status == 200) {
          this.facePhotoUrl = res.data.results.documentno
        } else {
          this.$message.error('获取图片地址失败')
        }
      })
    },

    // 获取权限
    getAuth(id) {
      queryPermission().then(res => {
        if (res.data.status == 200) {
          this.auth = res.data.results
        } else {
          this.$message.error('查询权限失败')
        }
      })
    },

    // 查询拓展信息
    getTuozhanDetail() {
      getExtDetailAll({ userId: this.jobId })
        .then(res => {
          this.$nextTick(() => {
            const tuozhanData = res.data.results || {}
            tuozhanData.vmAccount = this.jibenData.vmAccount
            tuozhanData.entranceCard = this.jibenData.entranceCard
            this.tuozhanData = tuozhanData
          })
        // eslint-disable-next-line handle-callback-err
        }).catch(error => {
          this.$message.error('查询拓展信息失败')
        })
    },

    // 查询面试信息
    getInterviewDetail(v) {
      const params = {}
      params.certifNumber = v
      queryInterDataList(params)
        .then(res => {
          this.$nextTick(() => {
            const interviewData = res.data.results.scoreList
            this.interviewData = interviewData
          })
        // eslint-disable-next-line handle-callback-err
        }).catch(error => {
          this.$message.error('查询面试信息失败')
        })
    },

    // tab被选中时触发
    handleClick(tab) {
      if (tab.name === 'jlxx') {
        this.$refs.jlxx.getTuozhanDetail(this.jobId)
      }
      this.$refs.hmcPzms.selectMsempRole()
    },

    // 关闭按钮
    close() {
      this.$router.push({ name: 'wbrzManage' })
    }
  }
}
</script>

<style scoped>
.nav {
  width: 100%;
  background-color: #ffffff;
}

.nav .nav_name {
  width: 150px;
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
}

.nav .wrapper {
  margin-left: 25px;
  margin-right: 25px;
  display: flex;
  justify-content: space-between;
}

.wrapper_left,
.wrapper_right {
  display: flex;
  align-items: center;
}

.archives-container-main {
  padding: 15px;
  display: flex;
}

.archives-container-main-right {
  width: 78%;
  min-height: calc(100vh - 137px);
  background-color: #ffffff;
  padding: 20px;
  display: inline-block;
  margin-left: 15px;
}

.archives-container-main-left {
  width: 22%;
  background-color: #ffffff;
  padding: 15px;
  display: inline-block;
}

.hucName {
  color: #909399;
  padding-right: 5px;
}

.grdaName {
  color: #262626;
  padding-left: 5px;
}

.main-left-content {
  text-align: center;
  padding: 30px 40px 10px 40px;
}

.finish_img {
  width: 100px;
  height: 130px;
}

.main-left-content .main-left-content-bottom {
  text-align: left;
}

.main-left-content-top {
  margin-top: 4px;
}

.main-left-content-top-jobId {
  font-size: 14px;
  margin: 0;
}

.main-left-content-top-name {
  font-size: 16px;
  margin-bottom: 3px;
}

.main-left-content-middle {
  margin: 30px 0;
}

.marginbottom-20px {
  margin-bottom: 20px;
}
</style>

<style>
.archives-container-main-right .el-tabs__nav-wrap::after {
  height: 1px;
}
</style>
