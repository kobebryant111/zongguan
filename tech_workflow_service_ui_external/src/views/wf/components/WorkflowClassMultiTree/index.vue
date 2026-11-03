<!--
 * @Author: zhuofushen
 * @Date: 2020-05-15
 * @LastEditors: zhuofushen
 * @LastEditTime: 2020-05-15
 * @Description: 工作流类型选择框组件
 -->
<template>
  <div>
    <ts-multi-tree
            ref="checkTree"
            v-model="checkValue"
            :options="options"
            :disabled = isDisabled
            @node-click="handleCheckClick">
    </ts-multi-tree>
  </div>
</template>

<script>
export default {
  name: 'WorkflowClassMultiTree',
  props: { 'isMultiSelect': Boolean, 'fatherValue': String, 'notShow': Boolean, 'tenant': String },
  components: {},
  data() {
    return {
      checkValue: '',
      checkLabel: '',
      defaultClasstify:{},
      isDisabled: this.notShow,
      options: {
        apiName: this.$api.classifyTree,
        method: 'post',
        params: { tenantId: this.$store.state.user.userInfo.tenantId },
        paramMode: 'params',
        width: '300',
        size: 'mini',
        onlyChildSelected: true, // 选填，是否只允许选择叶子节点
        checkbox: this.isMultiSelect,
        checkNode: this.isMultiSelect, // true 是多选 false单选
        placement: 'bottom-start',
        offset: 0,
        arrow: true,
        checkedKeys: [],
        defaultProp: { child: 'pId', label: 'name' },
        callback: this.callback
      }
    }
  },
  mounted() {
    
  },
  methods: {
    callback(data) {
      for(var i=0;i<data.length;i++){
        if(data[i].isDefault==1){
          this.fatherValue=data[i].id
          this.checkLabel=data[i].name
        }
      }
      if (this.isMultiSelect) {
        this.resetChecked()
        this.$refs.checkTree.setTree(data)
      }
    },
    resetChecked() {
      this.$refs['checkTree'].resetChecked()
    },
    handleCheckClick(node) {
      if (node === undefined) {
        return
      }
      this.checkLabel = ''
      // 多选情况下需要遍历拼接
      if (this.isMultiSelect) {
        if (node.length === 1) {
          this.checkLabel += node[0].label
        } else {
          for (let i = 0; i < node.length - 1; i++) {
            this.checkLabel += node[i].label + ','
          }
          this.checkLabel += node[node.length - 1].label
        }
      } else {
      // 单选的情况获取节点赋值即可
        this.checkLabel = node.label
      }
    }
  },
  watch: {
    fatherValue: function(newValue) {
      // 监听父组件的数据变化给子组件的数据赋值
      if (newValue == '') {
        this.$refs['checkTree'].resetChecked()
      }
      this.checkValue = newValue
    },
    checkValue: function(newValue, oldValue) {
      // 点击选中后数据变化把值给父组件送出去
      this.$emit('update:fatherValue', newValue)
    },
    checkLabel: function(newValue, oldValue) {
      // 点击选中后数据变化把值给父组件送出去
      this.$emit('update:checkLabel', newValue)
    },
    tenant: function(newValue, oldValue) {
      // 监听父组件的数据变化给子组件的数据赋值
      if (newValue) {
        this.options.params.tenantId = newValue
        this.$refs['checkTree'].resetChecked()
      }
    }
  }
}
</script>

<style lang='scss' scoped >

</style>
