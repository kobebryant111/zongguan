<template>
  <div class="icons-container">
    <el-tabs type="border-card">
      <el-tab-pane label="本项目使用的图标">
        <div class="grid">
          <div v-for="item of svgIcons" :key="item" @click="handleClipboard(generateIconCode(item),$event)">
            <el-tooltip placement="top">
              <div slot="content">
                {{ generateIconCode(item) }}
              </div>
              <div class="icon-item">
                <ts-svg-icon :icon="item" />
                <span>{{ item }}</span>
              </div>
            </el-tooltip>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="饿了吗图片">
        <div class="grid">
          <div v-for="item of elementIcons" :key="item" @click="handleClipboard(generateElementIconCode(item),$event)">
            <el-tooltip placement="top">
              <div slot="content">
                {{ generateElementIconCode(item) }}
              </div>
              <div class="icon-item">
                <i :class="'el-icon-' + item" />
                <span>{{ item }}</span>
              </div>
            </el-tooltip>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="项目使用iconfont图标">
        <div class="clearfix">
          <div v-for="item of iconfont" :key="item" @click="handleClipboard(generateElementIconCode(item),$event)">
            <el-tooltip placement="top">
              <div slot="content">
                {{ generateIconFontCode(item) }}
              </div>
              <div class="icon1-item">
                <i :class="item"></i>
                <span>{{ substrings(item) }}</span>
              </div>
            </el-tooltip>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import clipboard from '@/utils/clipboard'
import svgIcons from './svg-icons'
import elementIcons from './element-icons'
import iconfont from './iconfont'
export default {
  name: 'Icons',
  components: { },
  data() {
    return {
      svgIcons,
      elementIcons,
      iconfont
    }
  },
  created() {
  },
  methods: {
    generateIconCode(symbol) {
      return `<ts-svg-icon :icon="${symbol}" />`
    },
    generateElementIconCode(symbol) {
      return `<i class="el-icon-${symbol}" />`
    },
    generateIconFontCode(symbol) {
      return `<i class="${symbol}" />`
    },
    handleClipboard(text, event) {
      clipboard(text, event)
    },
    substrings(str) {
      return str.substring(9, str.length)
    }
  }
}
</script>

<style lang="scss" scoped>
.icons-container {
  margin: 10px 20px 0;
  overflow: hidden;

  .grid {
    position: relative;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  }

  .icon-item {
    margin: 20px;
    height: 85px;
    text-align: center;
    width: 100px;
    float: left;
    font-size: 30px;
    color: #24292e;
    cursor: pointer;
  }

  span {
    display: block;
    font-size: 16px;
    margin-top: 10px;
  }

  .disabled {
    pointer-events: none;
  }

  .icon1-item {
    margin: 10px 20px;
    height: 85px;
    text-align: center;
    width: 150px;
    float: left;
    cursor: pointer;
    .iconfont {
      font-size: 24px;
    }
  }
}
</style>
