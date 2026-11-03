<!-- 修改elementUi的表格信息 -->
<template>
  <div class="editcell">
    <div v-if="CanEdit" class="canEdit" @click="beginEdit">
      <label v-show="!editStatus">
        <span
          v-if="value !== null &&
            value !== undefined &&
          value !== ''">{{ value }}{{ suffix }}</span>
        <span v-else style="padding-left: 100%;padding-top: 100%;"/>
      </label>
      <label v-show="editStatus">
        <input
          ref="input"
          :maxlength="maxleng"
          :value="value"
          type="text"
          class="inputClass"
          @keyup.13="loseFocus"
          @focus="focusInput"
          @blur="loseFocus">
      </label>
    </div>
    <label
      v-else
      class="cannotEdit">
      <span>{{ value }} {{ suffix }}</span>
    </label>
  </div>
</template>

<script>

export default {
  name: 'EditCell',
  props: {
    /**
     * 绑定数据
     */
    // eslint-disable-next-line vue/require-prop-types
    value: {
      required: true
    },
    /**
     * 是否可编辑
     */
    CanEdit: {
      type: Boolean,
      default: true
    },
    /**
     * 格式化函数
     */
    formatData: {
      type: Function,
      default: value => {
        return value
      }
    },
    afterEdit: {
      type: Function,
      default: () => {}
    },
    initFormat: {
      type: Boolean,
      default: false
    },
    // eslint-disable-next-line vue/require-prop-types
    suffix: {
      default: ''
    },
    maxleng: {
      type: Number
    },
    data: {
      default: ''
    }
  },
  data() {
    return {
      editStatus: false,
      showData: '',
      defaultData: '',
      timeout: null
    }
  },
  watch: {
    'value': function(newVal) {
      this.$emit('input', this.formatData(newVal))
    }
  },
  mounted() {
    if (this.initFormat) {
      this.initData()
    }
  },
  methods: {
    beginEdit() {
      this.editStatus = true
      setTimeout(() => {
        this.$refs.input.focus()
      }, 1)
    },

    loseFocus(event) {
      const value = this.formatData(event.target.value)
      this.editData(value)
      this.closeEditStatus(value)
      this.afterEdit(value)
      this.$emit('custom-event', this.data)
    },

    focusInput() {
      this.$emit('focusInput', this.data)
    },

    editData(value) {
      this.$emit('input', value)
    },

    closeEditStatus(value) {
      this.editStatus = false
    },

    initData() {
      const newValue = this.formatData(this.value)
      this.$emit('input', newValue)
    }
  }
}
</script>
<style scoped>
.editCell {
  height: 100%;
  width: 100%;
}
.inputClass {
  height: 30px;
  width: 100%;
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  line-height: 30px;
  outline: 0;
  padding: 0 15px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  overflow: visible;
  touch-action: manipulation;
  margin: 0;
}
</style>
