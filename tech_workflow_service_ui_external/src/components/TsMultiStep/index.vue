<!--
 * @Author: amberself
 * @Date: 2020-04-10 16:02:22
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-07-15 15:47:38
 * @Description: 步骤条大页面
 -->
<template>
  <div>
    <!-- <el-steps v-if="hasStep" :active="stepIndex" finish-status="success" simple> -->
    <el-steps v-if="hasStep" :active="stepIndex" finish-status="success">
      <el-step v-for="(item, index) in viewStep" :key="index" :title="item.title"></el-step>
    </el-steps>

    <div v-if="hasStep">

      <div class="step-content" v-if="step.length > 0">
        <keep-alive :exclude="getRefreshData()">
          <component
            ref="stepRef"
            plugin-sign="stepPlugin"
            :plugin-id="step[stepIndex] && step[stepIndex].id"
            :is="step[stepIndex] && step[stepIndex].content"
            :data="datam"
            :callback="callback"
            :global-data="globalData"
            @next="activeNextBtn"
            @update="updateData"
            @updateFlowData="updateFlowData"
          />
        </keep-alive>

        <div class="overview-content" v-if="showOverView()">
          <div v-for="(item, index) in step" :key="index" class="content-box" >
            <div class="content-title">
              <span class="text">{{step[index].title}}</span>
            </div>
            <component :is="step[index].content" :disabled="true" />
          </div>
        </div>

      </div>

      <div class="step-btns" v-if="showBtn">
        <el-button size="mini" type="primary" v-if="stepIndex > 0"  @click="prevStep">上一步</el-button>
        <el-button size="mini" type="primary" v-if="stepIndex < lastStep" @click="nextStep">下一步</el-button>
        <el-button size="mini" type="primary" v-if="stepIndex === lastStep" @click="nextStep">保存</el-button>
        <el-button size="mini" type="primary" v-if="options.showBackBtn !== false" @click="back">返回</el-button>
      </div>

    </div>

    <div v-else>
      <div>调用步骤条组件时，请传入step参数！</div>
    </div>

</div>
</template>

<script>

export default {
  name: 'MultiStep',
  props: {
    options: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      globalData: {}, // 组件间共享的数据用这个globalData,之前options.data是兼容性的写法,暂时保留
      initData: this.options.data || {},
      stepIndex: 0,
      callback: {
        prevStep: this.prevStep,
        nextStep: this.nextStep
      }
    }
  },
  computed: {
    hasStep() {
      return this.options.step
    },
    step() { // 参数传递的正常步骤条
      if (this.hasStep) {
        // showStep是兼容旧的代码，正式版本用showPage
        const showStep = this.options.step.filter(item => {
          const { showStep = true, showPage = true } = item
          return (showStep !== false) && (showPage !== false)
        })
        return showStep
      } else {
        return []
      }
    },
    viewStep() { // 有总预览图的步骤条
      const { step, createOverView = false } = this.options
      if (step && createOverView) {
        const newSteps = [...this.step, { title: '信息总览', content: null, refresh: false }]
        return newSteps
      } else {
        return this.step
      }
    },
    lastStep() {
      if (this.step) {
        return (this.viewStep.length - 1)
      } else {
        return 0
      }
    },
    showBtn() {
      const stepIndex = this.stepIndex
      const lastStep = this.lastStep
      if (stepIndex >= this.lastStep) {
        return !(this.viewStep[lastStep].customBtn === true)
      }
      return !(this.viewStep[stepIndex].customBtn === true)
    },
    datam() {
      return this.initData
    }
  },
  mounted() {

  },
  methods: {
    // 获取不缓存的组件
    getRefreshData() {
      const viewStep = this.viewStep
      const result = []
      viewStep.forEach(item => {
        if (item.content && item.content.name && (item.refresh === true)) {
          result.push(item.content.name)
        }
      })
      return result
    },

    // 是否生成总概览视图
    showOverView() {
      const steps = this.viewStep
      const { createOverView = false } = this.options
      if (createOverView) {
        const stepsLen = steps.length
        return this.stepIndex === stepsLen
      } else {
        return false
      }
    },

    // 跳到下一步
    nextStep() {
      // saveStep是为了兼容旧代码，正式版本用savePage
      const { createOverView = false } = this.options
      if (createOverView && this.stepIndex === this.lastStep - 1) {
        this.activeNextBtn()
      } else {
        const { savePage, saveStep } = this.$refs.stepRef
        if (savePage) {
          savePage()
        } else if (saveStep) {
          saveStep()
        } else {
          this.activeNextBtn()
        }
      }
    },

    // 跳回上一步
    prevStep() {
      this.stepIndex -= 1
      const { createOverView = false } = this.options
      if (createOverView && this.stepIndex === this.lastStep) {
        this.stepIndex -= 1 // 概览页面跳上一步减2
      }
    },

    // 返回上一页
    back() {
      const { back, needupdate } = this.options
      let backquery = {}
      const defaultquery = { newtag: false, goBack: 'goBack', part: this.$route.query.part }
      const addquery = back && back.query || {}// 附加的自定义参数
      const needupdateFalg = needupdate || 'yes'// yes 是刷新 no是不刷新，默认只认yes/no
      if (back && this.$route.meta) {
        this.$route.meta.stopcache = true
        this.$route.meta.stopcacheself = true
        backquery = Object.assign({}, defaultquery, { 'needupdate': needupdateFalg }, addquery)
      }
      back ? this.$router.push({ path: back.path, query: backquery }) : this.$tsVue.tansunBack(this, { 'needupdate': needupdateFalg })
    },

    // 保存正确调到下一步
    activeNextBtn() {
      // 最后一步概览直接是完成状态
      const { createOverView = false } = this.options
      if (createOverView && this.stepIndex === this.lastStep - 1) {
        this.stepIndex += 2
      } else {
        this.stepIndex += 1
      }
    },

    // 更新data数据
    updateData(data) {
      this.initData = Object.assign({}, this.datam, data) // 兼容旧代码
      this.globalData = Object.assign({}, this.globalData, data)
    },
    // 更新data.flowData数据
    updateFlowData(data) {
      // 兼容旧代码
      const originData = this.initData.flowData || {}
      this.initData.flowData = Object.assign({}, originData, data)
      // 正式版代码
      const flowData = this.globalData.flowData || {}
      this.globalData.flowData = Object.assign({}, flowData, data)
    }
  }
}
</script>

<style lang="scss">
// 横向滚动、头部固定

  $activeColor: #007BFD;
  $finishColor: #16D556;

  .ts-scrollbar {
    &::-webkit-scrollbar {
      width: 6px;
      height: 6px;
    }
    &::-webkit-scrollbar-thumb {
      background-color: rgba(0, 123, 253, 0.6);
      border-radius: 5px;
    }
  }

  #app {
    .el-steps--horizontal {
      padding: 15px;
      background: #F6F7F9;
      width: 100%;
      overflow-x: auto;
      @extend .ts-scrollbar;
    }
    .el-step.is-horizontal {
      min-width: 100px;
      .el-step__line {
        top: 6px;
      }
    }
    .el-step__icon-inner {
      display: none;
    }
    .el-step__line {
      color: #cccccc;
    }
    .el-step__icon {
      width: 14px;
      height: 14px;
      &.is-text {
        border: 2px solid #ffffff;
        background: #CCCCCC;
      }
    }
    .el-step__head {
      &.is-success {
        border-color: $activeColor;
        .el-step__line {
          background-color: $activeColor;
        }
        .el-step__icon {
          top: -4px;
          width: 20px;
          height: 20px;
          &.is-text {
            border: 2px solid #A7FEAF;
            background: $finishColor;
          }
        }
        .el-step__icon:after {
          content: '✓';
          position: absolute;
          left: 2px;
          top: -2px;
          width: 20px;
          height: 20px;
          font-size: 14px;
          font-weight: bold;
          color: #ffffff;
        }
      }
      &.is-process {
        .el-step__icon {
          top: -4px;
          width: 20px;
          height: 20px;
          &.is-text {
            border: 2px solid #C2DFFF;
            background: $activeColor;
          }
        }
        .el-step__icon:after {
          content: '...';
          position: absolute;
          left: 0;
          top: -7px;
          width: 20px;
          height: 20px;
          font-size: 18px;
          color: #ffffff;
        }
      }
    }
    .el-step__title {
      font-size: 13px;
      padding-right: 15px;
      line-height: 18px;
      &.is-success {
        color: #333333;
      }
      &.is-wait {
        color: #808080;
      }
      &.is-process {
        color: #007BFD;
        font-weight: normal;
      }
    }
  }
</style>
