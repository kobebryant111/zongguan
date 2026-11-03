<template>
  <el-tooltip class="item" :effect="effect" :content="content" :placement="placement">
    <!--        <div slot="content">{{title}}<br/>{{content}}</div>-->
    <slot></slot>
  </el-tooltip>
</template>

<script>
import { getTipByCode } from '@/api/helpCenter/index'

export default {
  name: 'TsTooltip',
  props: {
    code: {
      type: String,
      default: ''
    },
    effect: {
      type: String,
      default: 'dark'
    },
    placement: {
      type: String,
      default: 'top-start'
    },
    params: {
      type: String,
      default: ''
    }
  },
  data: function() {
    return {
      content: '',
      title: ''
    }
  },
  created: function() {
    this.setContent()
  },
  methods: {
    setContent: function() {
      getTipByCode({
        code: this.code,
        params: this.params
      }).then(response => {
        if (response.data != null) {
          this.content = response.data.content
          this.title = response.data.title
        }
      })
    }
  }
}
</script>

<style>
  .item {
    margin: 4px;
  }
</style>
