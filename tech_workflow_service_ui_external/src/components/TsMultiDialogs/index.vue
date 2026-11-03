<template>
	<div v-if="visible">
		<el-dialog
      :visible="visible"
      v-bind="dialogOptions"
      :before-close="beforeClose"
      @close="close"
		>
      <div v-loading="loading">
        <div v-if="render">
          <component ref="parentRef" :is="content" :init-data="options.data" :options="dialogOptions"/>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel" v-if="options.showCancelBtn !== false" size="small">取 消</el-button>
        <el-button type="primary" v-if="options.showConfirmBtn !== false"  size="small" @click="confirm">{{this.getBtnLabel()}}</el-button>
      </span>

		</el-dialog>
	</div>
</template>

<script>
export default {
  name: 'TsMultiDialogs',
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    options: {
      type: Object
    },
    // 兼容旧示例的代码-后期要删掉
    resource: {
      type: Object
    },
    data: {
      type: Object
    }
  },
  // components: {
  //   'my-component': () => import(this.options.resource),
  //   AsyncContent(resolve) {
  //     require([this.options.resource], resolve)
  //   }
  // },
  data() {
    return {
      contentd: '',
      loading: true,
      formRef: '',
      formModel: {},
      render: false,
      content: this.options.resource || this.resource, // 兼容旧示例的代码-后期要删掉
      default: {
        disabled: false,
        title: '', // Dialog 的标题 | string | ——
        buttons: ['取 消', '确 定'],
        width: '50%', // Dialog 的宽度 | string | 50%
        modal: true, // 是否需要遮罩层 | boolean | true
        fullscreen: false, // 是否为全屏 Dialog | boolean | false
        beforeClose: null, // 关闭前的回调，会暂停 Dialog 的关闭 | function(done)，done 用于关闭 Dialog | ——
        customClass: '', // Dialog 的自定义类名 | string | ——
        showClose: true, // 是否显示关闭按钮 | boolean | true
        center: false, // 是否对头部和底部采用居中布局 | boolean | false
        top: '15vh', // Dialog CSS 中的 margin-top 值 | string | 15vh
        modalAppendToBody: true, // 遮罩层是否插入至 body 元素上，若为 false，则遮罩层会插入至 Dialog 的父元素上 | boolean | true
        appendToBody: false, // Dialog 自身是否插入至 body 元素上。嵌套的 Dialog 必须指定该属性并赋值为 true | boolean | false
        lockScroll: true, // 是否在 Dialog 出现时将 body 滚动锁定 | boolean | true
        closeOnClickModal: false, // 是否可以通过点击 modal 关闭 Dialog | boolean | true
        closeOnPressEscape: false, // 是否可以通过按下 ESC 关闭 Dialog | boolean | true
        destroyOnClose: false // 关闭时销毁 Dialog 中的元素 | boolean | false
      }
    }
  },
  computed: {
    modifyData() {
      return this.options.data || this.data // 兼容旧示例的代码-后期要删掉
    },
    dialogOptions() {
      return Object.assign({}, this.default, this.options)
    },
    initFlag() {
      return this.visible && this.render
    }
  },
  watch: {
    initFlag(newValue, oldValue) {
      if (newValue) {
        this.initFormModel()
      }
    },
    modifyData: {
      handler(newName, oldName) {
        this.initFlag ? this.initFormModel() : ''
      },
      deep: true
    }
  },
  created() {
    this.getContent()
  },
  methods: {
    getBtnLabel() {
      const { buttons } = this.options
      const defaultBtns = this.default.buttons
      return buttons ? buttons[1] : defaultBtns[1]
    },
    getContent() {
      const self = this
      const fun = this.resource || this.options.resource
      if (typeof fun === 'object' && fun instanceof Promise) {
        fun.then(res => {
          self.changeStatus()
        })
      } else if (typeof fun === 'function') {
        const obj = fun()
        if (obj && typeof obj.then === 'function') {
          obj.then(res => { self.changeStatus() })
        }
      } else {
        self.changeStatus()
      }
    },
    changeStatus() {
      this.render = true
      this.loading = false
    },
    initFormModel() {
      this.$nextTick(() => {
        const _this = this
        const parentRef = this.$refs.parentRef
        const refsObj = parentRef.$refs
        const refsKey = Object.keys(refsObj)
        if (refsKey.length < 1) return

        refsKey.forEach(item => {
          if (refsObj[item].validate) {
            this.formRef = item
            _this.resetModel()
          }
        })
      })
    },
    resetModel() {
      this.$nextTick(() => {
        const parentRef = this.$refs.parentRef
        const modelName = this.options.modelName || 'model'
        let childRef

        const formRefName = this.formRef
        if (formRefName) {
          childRef = parentRef.$refs[formRefName]
          childRef.resetFields()

          const formModel = parentRef[modelName]
          const formModelKeys = Object.keys(formModel)
          const modifyData = this.data || this.options.data // 兼容旧示例的代码-后期要删掉
          formModelKeys.forEach(key => {
            formModel[key] = modifyData[key]
          })
          this.formModel = formModel
        }
      })
    },
    // 关闭的回调
    close() {
      this.$emit('update:visible', false)
    },
    // 右上角关闭按钮关闭前的回调
    beforeClose(done) {
      const { beforeClose } = this.dialogOptions
      if (beforeClose === null) {
        done()
      } else {
        beforeClose(this.formModel, this.close, this.sendData())
      }
    },
    // 取消按钮的回调
    cancel() {
      const { callbacks } = this.options
      const cancelEvent = callbacks[0]
      if (cancelEvent === null) {
        this.close()
      } else {
        cancelEvent(this.formModel, this.close, this.sendData())
      }
    },
    // 确定按钮的回调
    confirm() {
      const _this = this
      const parentRef = this.$refs.parentRef
      const formRefName = this.formRef
      if (formRefName) {
        const childRef = parentRef.$refs[formRefName]
        childRef.validate((valid) => {
          if (valid) {
            const { callbacks } = this.options
            const confirmEvent = callbacks[1]
            if (confirmEvent === null) {
              this.close()
            } else {
              confirmEvent(this.formModel, this.close, this.sendData())
            }
          } else {
            console.log('handleConfirm error submit!!')
            return false
          }
        })
      } else {
        const { callbacks } = this.options
        const confirmEvent = callbacks[1]
        if (confirmEvent === null) {
          this.close()
        } else {
          confirmEvent(this.formModel, this.close, this.sendData())
        }
      }
    },
    sendData() {
      const data = this.$refs.parentRef
      data.close = this.close
      return data
    }
  }
}
</script>
