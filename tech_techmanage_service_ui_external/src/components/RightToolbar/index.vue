<!-- 测试看板 -->
<template>
  <div :style="style" class="top-right-btn">
    <el-row>
      <el-tooltip v-if="search" :content="showSearch ? '隐藏搜索' : '显示搜索'" class="item" effect="dark" placement="top">
        <el-button size="mini" circle icon="el-icon-search" @click="toggleSearch()"/>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button size="mini" circle icon="el-icon-refresh" @click="refresh()"/>
      </el-tooltip>

      <el-tooltip v-if="columns" class="item" effect="dark" content="显隐列" placement="top">
        <el-button v-if="showColumnsType == 'transfer'" size="mini" circle icon="el-icon-menu" @click="showColumn()"/>
        <el-dropdown v-if="showColumnsType == 'checkbox'" :hide-on-click="false" trigger="click" style="padding-left: 12px">
          <el-button size="mini" circle icon="el-icon-menu"/>
          <el-dropdown-menu slot="dropdown" class="scroll">
            <template v-for="(item,index) in columns">
              <el-dropdown-item :key="item.key">

                <el-checkbox :checked="item.visible" :label="item.label" style="width: 130px" @change="checkboxChange($event, item.label)" />

                <el-button :class="{'highlighted': item.isHighlightedLeft}" size="mini" type="text" @click="togglePinLeft(index)" >
                  <i :class="['el-icon-bottom-left']"/>
                </el-button>
                <el-button :class="{'highlighted': item.isHighlightedRight}" size="mini" type="text" @click="togglePinRight(index)" >
                  <i :class="['el-icon-bottom-right']"/>
                </el-button>
              </el-dropdown-item>

            </template>
          </el-dropdown-menu>
        </el-dropdown>
      </el-tooltip>
    </el-row>
    <el-dialog :title="title" :visible.sync="open" append-to-body>
      <el-transfer
        :titles="['显示','隐藏']"
        v-model="value"
        :data="columns"
        @change="dataChange"
      />
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: 'ReghtToolbar',
  props: {

    // 是否显示检索条件
    showSearch: {
      type: Boolean,
      default: true
    },
    // 显示列信息
    // eslint-disable-next-line vue/require-default-prop
    columns: {
      type: Array
    },
    // 是否显示检索图表
    search: {
      type: Boolean,
      default: true

    },
    // 显隐列类型
    showColumnsType: {
      type: String,
      default: 'checkbox'
    },
    // 右外边距
    gutter: {
      type: Number,
      default: 10
    }
  },
  data() {
    return {
      // 显隐数据
      value: [],
      // 弹出层标题
      title: '显示/隐藏',
      // 是否显示弹出框
      open: false
    }
  },
  computed: {
    style() {
      const ret = {}
      if (this.gutter) {
        ret.marginRight = `${this.gutter / 2}px`
      }
      return ret
    }
  },

  created() {
    if (this.showColumnsType === 'transfer') {
      // 显隐列初始默认隐藏列
      for (const item in this.columns) {
        this.value.push(parseInt(item))
      }
    }
  },
  methods: {
    // 左冻结
    togglePinLeft(index) {
      debugger
      const data = this.columns[index].fixed
      if (!data || data === 'right') {
        this.columns[index].fixed = 'left'
      } else if (data === 'left') {
        this.columns[index].fixed = false
      }
      const lightLeft = this.columns[index].isHighlightedLeft
      const lightRight = this.columns[index].isHighlightedRight
      if (!lightLeft && lightRight) {
        this.columns[index].isHighlightedRight = this.columns[index].isHighlightedLeft
      }
      this.columns[index].isHighlightedLeft = !this.columns[index].isHighlightedLeft
    },
    // 右冻结
    togglePinRight(index) {
      const data = this.columns[index].fixed
      if (!data || data === 'left') {
        this.columns[index].fixed = 'right'
      } else if (data === 'right') {
        this.columns[index].fixed = false
      }
      const lightLeft = this.columns[index].isHighlightedLeft
      const lightRight = this.columns[index].isHighlightedRight
      if (lightLeft && !lightRight) {
        this.columns[index].isHighlightedLeft = this.columns[index].isHighlightedRight
      }
      this.columns[index].isHighlightedRight = !this.columns[index].isHighlightedRight
    },
    // 搜索
    toggleSearch() {
      this.$emit('update:showSearch', !this.showSearch)
    },
    // 刷新
    refresh() {
      this.$emit('queryTable')
    },
    // 右侧列表元素变化
    dataChange(data) {
      for (const item in this.columns) {
        const key = this.columns[item].key
        this.columns[item].visible = !data.includes(key)
      }
    },
    // 打开显隐列dialog
    showColumn() {
      this.open = true
    },
    // 勾选
    checkboxChange(event, label) {
      this.columns.filter(item => item.label === label)[0].visible = event
      this.$emit('doLayoutTable')
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-transfer__button{
    border-radius: 50%;
    padding: 12px;
    display: block;
    margin-right: 0px;
    height: 90% !important;
}

::v-deep .el-transfer__button:first-child {
    margin-bottom: 10px;
    height: 90% !important;
}
.scroll {
    overflow-y: auto;
    max-height: 500px;
}
.highlighted {
  background-color: white;
  color: #ffd723;
}
</style>
