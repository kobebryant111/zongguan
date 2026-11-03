<!-- 步骤条 -->
<template>
  <div class="contentSteps">
    <el-steps :active="active" align-center finish-status="wait">
      <!-- <el-step title="基本信息"></el-step>
      <el-step title="配置信息和面试信息"></el-step>
      <el-step title="简历信息"></el-step>
      <el-step title="培训成绩和入职材料"></el-step>
      <el-step title="完成"></el-step> -->
      <el-step v-for="(item,index) in titleList" :title="item.title" :key="index"></el-step>
    </el-steps>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        active: 0,
        titleList: [{
          title: '基本信息'
        }, {
          title: '面试信息'
        }, {
          title: '简历信息'
        }, {
          title: '入职材料'
        }, {
          title: '完成'
        }]
      };
    },
    mounted() {

    },
    methods: {
      next() {
        if (this.active++ > 3) this.active = 0;
      },
      prev() {
        --this.active;
        if (this.active < 0) this.active = 0;
      }
    },
    watch: {
      'active': function (val) {
        this.$emit('currentV', val);
        console.log(val);
      }
    }
  };
</script>
<style scoped>
  .contentSteps {
    width: 100%;
    padding: 20px 0;
    background-color: #ffffff;
    margin-bottom: 10px;
  }
</style>
<!-- element组件样式修改 -->
<style>
  .el-steps--horizontal{
    display: flex;
    justify-content: center;
  }
  .el-step__line {
    width: 63%;
    margin-left: 18.5%;
    /* margin-right: 30px !important; */
  }

  .el-step{
    flex-basis: 13% !important;
    margin-right: 0;
  }

  .el-step__icon {
    width: 32px;
    height: 32px;
  }

  .el-step.is-horizontal .el-step__line {
    top: 14px;
  }

  .el-step__head.is-process {
    color: rgba(251, 164, 47, 1);
    border-color: rgba(251, 164, 47, 1);
  }

  .el-step__title.is-process {
    color: rgba(251, 164, 47, 1);
  }

  .el-step.is-horizontal .el-step__line {
    height: 1.4px;
  }
</style>