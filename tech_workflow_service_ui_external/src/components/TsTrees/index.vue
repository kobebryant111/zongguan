<!--
@Author: liuyang
@Date: 2021-10-12
@LastEditors: liuyang
@LastEditTime: 2021-10-12 16:26:30
@Description: tree组件
-->
<template>
  <div>
    <div class="tree-title" v-if="tsOptions.title!==''">{{tsOptions.title}}</div>
    <el-input
      v-if="tsOptions.filterInput"
      class="filter-input"
      prefix-icon="el-icon-search"
      clearable
      size="mini"
      placeholder="输入关键字进行过滤"
      v-model="filterText">
    </el-input>
    <div class="multi-trees" :style="{width:tsOptions.width,height:this.getTreeHeight()}">
      <el-tree
          ref="tree"
          :id="treeId"
          :data="data"
          :props="defaultProps"
          :default-checked-keys="tsOptions.checkedKeys"
          :node-key="tsOptions.nodeKey"
          :filter-node-method="filterNode"
          :highlight-current="tsOptions.highlight"
          :accordion="tsOptions.accordion"
          :show-checkbox="tsOptions.checkbox"
          :check-on-click-node="tsOptions.checkNode"
          :check-strictly="tsOptions.checkStrictly"
          :default-expanded-keys="tsOptions.expandedKeys"
          :default-expand-all="tsOptions.expandAll"
          :expand-on-click-node="tsOptions.expandClickNode"
          :draggable="tsOptions.draggable"
          :allow-drag="tsOptions.allowDrag"
          :allow-drop="tsOptions.allowDrop"
          @node-drag-start="nodeDragStart"
          @node-drag-enter="nodeDragEnter"
          @node-drag-leave="nodeDragLeave"
          @node-drag-over="nodeDragOver"
          @node-drag-end="nodeDragEnd"
          @node-drop="nodeDrop"
          @node-contextmenu="nodeContextmenu"
          @check-change="handleCheckChange"
          @check="handleCheck"
          @node-expand="nodeExpand"
          @node-collapse="nodeCollapse"
          @node-click="handleNodeClick"
          @current-change="currentChange">
        <span class="custom-tree-node" slot-scope="{ node, data }"
              @dragenter="ondragenter($event,data)" @dragleave="ondragleave($event,data)">
          <template v-if="data.isEdit===1">
            <el-input
                ref="input"
                @blur="submitEdit(node,data)"
                v-model="newApiGroupName"></el-input>
          </template>
          <span class="node" v-if="node.isLeaf&&data.isEdit!==1">
              <!-- 子节点  -->
              <i
                v-if="!(tsOptions.leafIcon!=='' || data.icon)"
                :class="[data.isFolder?'el-icon-folder':'el-icon-document','node-img']">
              </i>
              <img class="node-img" v-if="(tsOptions.leafIcon!=='' || data.icon)" :src="getLeafIcon(data)">
              {{ node.label }}
          </span>
          <span class="node" v-if="!node.isLeaf&&data.isEdit!==1">
              <!-- 节点未展开  -->
              <i v-if="!node.expanded && !(tsOptions.closeIcon!=='' || data.icon)" class="el-icon-folder node-img"></i>
              <img
                  class="node-img"
                  v-if="!node.expanded && (tsOptions.closeIcon!=='' || data.icon)"
                  :src="getCloseIcon(data)">
            <!-- 节点展开  -->
              <i
                  v-if="node.expanded && !(tsOptions.openIcon!=='' || data.icon)"
                  class="el-icon-folder-opened node-img"></i>
              <img
                  class="node-img"
                  v-if="node.expanded && (tsOptions.openIcon!=='' || data.icon)"
                  :src="getOpenIcon(data)">
              {{ node.label }}
          </span>
          <span v-if="tsOptions.canEdit">
            <el-button
                type="text"
                size="mini"
                @click="editNode(node,data)">
              编辑
            </el-button>
            <el-button
                type="text"
                size="mini"
                @click="appendNode(node,data)">
              添加
            </el-button>
            <el-button
                type="text"
                size="mini"
                @click="removeNode(node, data)">
              删除
            </el-button>
          </span>
        </span>
      </el-tree>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TsTrees',
  props: {
    value: {
      type: String,
      default: ''
    },
    options: {
      type: Object,
      default() {
        return {}
      }
    },
    mark: {
      default: ''
    }
  },
  data() {
    return {
      tOptions: {
        visibleInit: true, // 初始化是否查询
        apiName: undefined,
        width: 'auto', // 树区域宽
        height: 'auto', // 树区域高
        title: '', // 标题
        params: {}, // 请求参数
        filterOption: ['label'],
        filterInput: true, // 是否展示节点搜索框
        highlight: false, // 是否高亮当前选中行
        accordion: false, // 是否每次只打开一个同级树节点展开
        draggable: false, // 是否开启拖拽节点功能
        checkbox: false, // 节点是否显示checkbox
        checkNode: false, // 选中节点是否选中文本框
        checkStrictly: false, // 在显示复选框的情况下，是否严格的遵循父子不互相关联的做法，默认为 false
        checkedKeys: null, // 默认选中的节点
        nodeKey: 'id', // 每个树节点用来作为唯一标识的属性，整棵树必须是唯一的 默认id
        onlyChildSelected: false, // 只允许选择子节点
        expandedState: true, // updateTree 是否保留选中节点展开状态 默认true
        expandedKeys: null, // 默认展开的节点
        expandAll: false, // 是否默认展开所有节点
        expandClickNode: true, // 是否在点击节点的时候展开或者收缩节点， 默认值为 true，如果为 false，则只有点箭头图标的时候才会展开或者收缩节点。
        defaultProp: { child: 'pId', label: 'name' },
        leafIcon: '', // 叶子节点图标
        openIcon: '', // 展开图标
        closeIcon: '', // 未展开图标
        canEdit: false, // 是否可编辑节点 包括 编辑、添加、删除
        appendNode: null, // 添加节点方法 Function(node, data) 组件已有默认处理方式,可覆盖默认处理方式 参考appendNode 方法
        removeNode: null, // 移除节点方法 Function(node, data) 组件已有默认处理方式,可覆盖默认处理方式 参考removeNode 方法
        allowDrag: (node) => {
        }, // 判断节点能否被拖拽
        allowDrop: (draggingNode, dropNode, type) => {
        }, // 拖拽时判定目标节点能否被放置。type 参数有三种情况：'prev'、'inner' 和 'next'，分别表示放置在目标节点前、插入至目标节点和放置在目标节点后
        callback: (data) => {
        }, // 接口数据返回后回调
        loading: true // 是否显示加载
      },
      data: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      filterText: '',
      inputValue: '',
      newApiGroupName: '',
      checkLock: false,
      currentEl: null,
      node: null,
      nodeKey: null,
      treeId:'',
    }
  },
  created() {
    this.treeId = this.guid()
    if (this.tsOptions.visibleInit === true) {
      this.getDataSource(this.tsOptions.params)
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    },
    value(val) {
      this.inputValue = val
    },
    inputValue(newVal) {
      this.$emit('input', newVal)
    }
  },
  computed: {
    tsOptions() {
      return Object.assign({}, this.tOptions, this.options)
    }
  },
  methods: {
    getTreeHeight() {
      const { filterInput = false, height = 'auto' } = this.tsOptions
      let treeHeight = height
      if (height !== 'auto') {
        treeHeight = filterInput ? (parseInt(height) - 40) + 'px' : height
      }
      return treeHeight
    },
    // 获取树数据
    getDataSource(params) {
      const _this = this
      const apiName = _this.tsOptions.apiName

      let currentKey = null
      let parentKey = null
      let currentNode = null
      if (_this.tsOptions.expandedState && _this.$refs.tree) {
        // 废弃
        // currentNode = _this.getCurrentNode()
        // currentKey = _this.getCurrentKey()
        // 解决 刷新默认 展开节点 1.0.17
        currentNode = _this.node
        currentKey = _this.nodeKey
        parentKey = currentNode !== null ? currentNode[_this.tsOptions.defaultProp.child] : null
      }
      if (typeof (apiName) === 'string' && this.$request) {
        this.$request({
          url: apiName,
          data: params,
          type: _this.tsOptions.method ? _this.tsOptions.method : 'post',
          paramMode: this.tsOptions.paramMode ? this.tsOptions.paramMode : 'data',
          loading: this.tsOptions.loading
        }).then(res => {
          if (res.status == 200) {
            res.data.forEach((item, index) => {
              item.key = index
            })

            _this.setCurrentKey(null)

            // 数据格式转换
            _this.data = _this.$tsVue.tansunArray2Tree(res.data, _this.tsOptions.defaultProp)
            // 回调处理
            if (_this.options.callback && typeof _this.options.callback === 'function') {
              _this.tsOptions.callback(res.data)
            }

            // 有当前节点选择当前节点，否则选中父节点
            if (currentKey !== null) {
              if (_this.$refs.tree.getNode(currentKey) !== null) {
                _this.$nextTick(() => {
                  _this.expandSetCurrent(currentKey)
                })
              } else {
                _this.$nextTick(() => {
                  _this.expandSetCurrent(parentKey)
                })
              }
            }
          }
        }).catch((error) => {
          console.log(error)
        })
      }

      if (typeof (apiName) === 'function') {
        apiName(params).then(res => {
          if (res.status == 200) {
            res.data.forEach((item, index) => {
              item.key = index
            })

            _this.setCurrentKey(null)

            // 数据格式转换
            _this.data = _this.$tsVue.tansunArray2Tree(res.data, _this.tsOptions.defaultProp)
            // 回调处理
            if (_this.options.callback && typeof _this.options.callback === 'function') {
              _this.tsOptions.callback(res.data)
            }

            // 有当前节点选择当前节点，否则选中父节点
            if (currentKey !== null) {
              if (_this.$refs.tree.getNode(currentKey) !== null) {
                _this.$nextTick(() => {
                  _this.expandSetCurrent(currentKey)
                })
              } else {
                _this.$nextTick(() => {
                  _this.expandSetCurrent(parentKey)
                })
              }
            }
          }
        }).catch((error) => {
          console.log(error)
        })
      }
    },
    // 树节点选中回调
    handleNodeClick(ele, node, event) {
      if (!this.tsOptions.checkbox) {
        if (this.tsOptions.onlyChildSelected) {
          // data 中包含 isLeaf 用data中的判断
          if (node.data.hasOwnProperty('isLeaf')) {
            if (node.data.isLeaf) {
              this.inputValue = node.data[this.tsOptions.nodeKey]
              this.$emit('node-click', node.data, this.mark, node)
            }
          } else {
            if (node.isLeaf) {
              this.inputValue = node.data[this.tsOptions.nodeKey]
              this.$emit('node-click', node.data, this.mark, node)
            }
          }
        } else {
          this.inputValue = node.data[this.tsOptions.nodeKey]
          this.$emit('node-click', node.data, this.mark, node)
        }
      }
    },
    // 搜索
    filterNode(value, data) {
      if (!value) return true
			let filterBool = []
	    this.tsOptions.filterOption.forEach((item) =>{
	      if (data[item]) {
          const booleanValue = (data[item].indexOf(value) !== -1)
          filterBool.push(booleanValue)
	      }
	    })
      const haveTrue = filterBool.filter(item => {
        return item
      })
	    if (haveTrue.length >0 ) {
	      return true
	    } else  {
        return false
	    }
    },
    findChildren(selected, current) {
      this.$refs.tree.setChecked(current[this.tsOptions.nodeKey], selected, true)
      const d = current.children
      if (d && d.length > 0) {
        d.forEach(a => {
          this.findChildren(selected, a)
        })
      }
    },
    checkedParent(current) {
      this.$refs.tree.setChecked(current[this.tsOptions.defaultProp.child], true, true)
    },
    handleCheck(checkedNodes, checkedKeys, halfCheckedNodes, halfCheckedKeys) {
      if (this.tsOptions.checkbox) {
        // 多选时才会走这段
        const node = this.$refs.tree.getNode(this.currentEl)
        let nodes = []
        if (this.tsOptions.onlyChildSelected) {
          nodes = this.getCheckedNodes(false, false)
          let selectNodes = []

          // data 中包含 isLeaf 用data中的判断
          if (node.data.hasOwnProperty('isLeaf')) {
            selectNodes = nodes.filter((item) => {
              return this.$refs.tree.getNode(item[this.tsOptions.nodeKey]) !== null && this.$refs.tree.getNode(item[this.tsOptions.nodeKey]).data.isLeaf === true
            })
          } else {
            selectNodes = nodes.filter((item) => {
              return this.$refs.tree.getNode(item[this.tsOptions.nodeKey]) !== null && this.$refs.tree.getNode(item[this.tsOptions.nodeKey]).isLeaf === true
            })
          }

          let values = ''
          selectNodes.forEach(item => {
            values += item[this.tsOptions.nodeKey] + ','
          })
          this.inputValue = values.substring(0, values.length - 1)

          this.$emit('node-click', selectNodes, this.mark, node)
        } else {
          nodes = this.getCheckedNodes(false, true)
          let values = ''
          nodes.forEach(item => {
            values += item[this.tsOptions.nodeKey] + ','
          })
          this.inputValue = values.substring(0, values.length - 1)

          this.$emit('node-click', nodes, this.mark, node)
        }
        this.checkLock = false
      }
    },
    // 复选框选择时回调
    handleCheckChange(el, selected, im) {
      this.$emit('check-change', el, selected, im)

      if (!this.checkLock) {
        this.currentEl = el
        this.checkLock = true
      }

      // if(this.tsOptions.checkStrictly){
      //   if(!this.checkedLock){
      //     this.findChildren(selected,el);
      //   }
      //
      //   if(selected){
      //     this.checkedLock=true;
      //     this.checkedParent(el);
      //   }
      //   if(node.level===1){
      //     this.checkedLock=false;
      //   }
      // }
    },
    // 更新数据
    updateTree() {
      this.getDataSource(this.tsOptions.params)
    },
    setTree(data) {
      this.data = this.$tsVue.tansunArray2Tree(data, this.tsOptions.defaultProp)
      this.resetChecked()
    },
    // 展开节点并选中节点，使用此方法必须设置 node-key 属性
    expandSetCurrent(key) {
      if (key) {
        const keys = this.treeFindPath(this.data, data1 => data1[this.tsOptions.nodeKey] === key)
        keys.forEach((item) => {
          if (item !== key) {
            this.$refs.tree.store.nodesMap[item].expanded = true
          }
        })
        this.setCurrentKey(key)
      }
    },
    // 通过 key 设置某个节点的当前选中状态，使用此方法必须设置 node-key 属性
    setCurrentKey(key) {
      this.$refs.tree.setCurrentKey(key)
      if (key) {
        this.$nextTick(() => {
          if (document.querySelector('#' + this.treeId).querySelector('.is-current')) {
            document.querySelector('#' + this.treeId).querySelector('.is-current').firstChild.click()
          }
          if (!this.$refs.tree.store.nodesMap[key].expanded) {
            this.$refs.tree.store.nodesMap[key].expanded = true
          }
        })
      }
    },
    // 当前选中节点变化时触发的事件	共两个参数，依次为：当前节点的数据，当前节点的 Node 对象
    currentChange(data, node) {
      this.$emit('current-change', data, node)
    },
    // 通过node设置
    setCheckedNodes(node) {
      this.$refs.tree.setCheckedNodes(node)
    },
    // 若节点可被选择（即 show-checkbox 为 true），则返回目前被选中的节点所组成的数组
    // 接收两个 boolean 类型的参数，1. 是否只是叶子节点，默认值为 false 2. 是否包含半选节点，默认值为 false
    getCheckedNodes(leafOnly = false, includeHalfChecked = false) {
      return this.$refs.tree.getCheckedNodes(leafOnly, includeHalfChecked)
    },
    // 若节点可被选择（即 show-checkbox 为 true），则返回目前被选中的节点的 key 所组成的数组
    // 接收两个 boolean 类型的参数，1. 是否只是叶子节点，默认值为 false 2. 是否包含半选节点，默认值为 false
    getCheckedKeys(leafOnly = false, includeHalfChecked = false) {
      return this.$refs.tree.getCheckedKeys(leafOnly, includeHalfChecked)
    },
    // 获取当前被选中节点的 data，若没有节点被选中则返回 null
    getCurrentNode() {
      return this.$refs.tree.getCurrentNode()
    },
    // 获取当前被选中节点的 key，使用此方法必须设置 node-key 属性，若没有节点被选中则返回 null
    getCurrentKey() {
      return this.$refs.tree.getCurrentKey()
    },
    // 通过key设置 多选
    setCheckedKeys(strs) {
      let keys
      if (typeof strs === 'string') {
        keys = strs.split(',')
      } else {
        keys = strs
      }
      this.$refs.tree.setCheckedKeys(keys)
    },
    // 通过key设置 单选
    setCheckedKey(key) {
      const node = this.$refs.tree.getNode(key)
      if (node) {
        this.inputValue = node.key
      }
    },
    nodeExpand(data, node, e) {
      // 节点被展开时触发的事件
      // 解决 刷新默认 展开节点 1.0.17
      this.node = node
      this.nodeKey = node.key
    },
    nodeCollapse(data, node, e) {
      // 节点被关闭时触发的事件
      // 解决 刷新默认 展开节点  1.0.17
      this.node = node.parent
      this.nodeKey = node.parent.key
    },
    // 清空
    resetChecked() {
      this.$refs.tree.setCheckedKeys([])
    },
    // 节点过滤
    treeFilter(val) {
      this.$refs.tree.filter(val)
    },
    // 节点右击事件
    nodeContextmenu(event, data, node, comp) {
      this.$emit('node-contextmenu', event, data, node, comp)
    },
    // 节点开始拖拽时触发的事件	共两个参数，依次为：被拖拽节点对应的 Node、event
    nodeDragStart(node, event) {
      this.$emit('node-drag-start', node, event)
    },
    // 拖拽进入其他节点时触发的事件	共三个参数，依次为：被拖拽节点对应的 Node、当前进入节点对应的 Node、event
    nodeDragEnter(dragNdoe, dropNode, event) {
      this.$emit('node-drag-enter', dragNdoe, dropNode, event)
    },
    // 拖拽离开某个节点时触发的事件	共三个参数，依次为：被拖拽节点对应的 Node、所离开节点对应的 Node、event
    nodeDragLeave(dragNdoe, dropNode, event) {
      this.$emit('node-drag-leave', dragNdoe, dropNode, event)
    },
    // 在拖拽节点时触发的事件（类似浏览器的 mouseover 事件）	共三个参数，依次为：被拖拽节点对应的 Node、当前进入节点对应的 Node、event
    nodeDragOver(dragNdoe, dropNode, event) {
      this.$emit('node-drag-over', dragNdoe, dropNode, event)
    },
    // 拖拽结束时（可能未成功）触发的事件	共四个参数，依次为：被拖拽节点对应的 Node、结束拖拽时最后进入的节点（可能为空）、被拖拽节点的放置位置（before、after、inner）、event
    nodeDragEnd(dragNdoe, dropNode, dropType, event) {
      this.$emit('node-drag-end', dragNdoe, dropNode, dropType, event)
    },
    // 拖拽成功完成时触发的事件 共四个参数，依次为：被拖拽节点对应的 Node、结束拖拽时最后进入的节点、被拖拽节点的放置位置（before、after、inner）、event
    nodeDrop(dragNdoe, dropNode, dropType, event) {
      this.$emit('node-drop', dragNdoe, dropNode, dropType, event)
    },
    append(data, parentNode) {
      this.$refs.tree.append(data, parentNode)
    },
    submitEdit(node, data) {
      if (data.name === this.newApiGroupName) {
        this.newApiGroupName = ''
        this.$set(data, 'isEdit', 0)
      } else {
        this.$set(data, 'label', this.newApiGroupName)
        this.$set(data, this.tsOptions.defaultProp.label, this.newApiGroupName)
        this.newApiGroupName = ''
        this.$set(data, 'isEdit', 0)
      }
      this.$emit('edit-complete', data)
    },
    cancelEdit(node, data) {
      this.$set(data, 'isEdit', 0)
    },
    editNode(node, data) {
      this.$set(data, 'isEdit', 1)
      this.newApiGroupName = data.label
      this.$nextTick(() => {
        this.$refs.input.focus()
      })
    },
    appendNode(node, data) {
      const appendNode = this.tsOptions.appendNode
      if (typeof (appendNode) === 'function') {
        appendNode(node, data)
      } else {
        const timestamp = new Date().getTime()
        const newChild = {
          id: timestamp,
          isEdit: 0,
          children: []
        }
        newChild.label = '新节点'
        newChild[this.tsOptions.defaultProp.label] = '新节点'
        if (!data.children) {
          this.$set(data, 'children', [])
        }
        data.children.push(newChild)
      }
    },
    removeNode(node, data) {
      const removeNode = this.tsOptions.removeNode

      if (typeof (removeNode) === 'function') {
        removeNode(node, data)
      } else {
        const parent = node.parent
        const children = parent.data.children || parent.data
        const index = children.findIndex(d => d.id === data.id)
        children.splice(index, 1)
      }
    },
    onMouseover(data){
      console.log('onMouseover')
      this.$set(data, 'isHover', true)
    },
    onMouseout(data){
      this.$set(data, 'isHover', false)
    },
    ondragenter(e,data){
      let parent = e.fromElement.parentNode
      if(parent.classList.contains('el-tree-node')){
        parent.classList.add('el-tree-node-hover')
      }
      this.$emit('node-dragenter',data)
    },
    ondragleave(e,data){
      if(e.fromElement){
        let parent = e.fromElement.parentNode
        if(parent.classList.contains('el-tree-node')){
          parent.classList.remove('el-tree-node-hover')
        }
        this.$emit('node-dragleave',null)
      } else {
        const rows = document.querySelectorAll('.el-tree-node')
        rows.forEach((row)=>{
          row.classList.remove('el-tree-node-hover')
        })
      }
    },
    getLeafIcon(data) {
      if (data.icon && data.icon !== '') {
        return data.icon
      } else {
        return this.tsOptions.leafIcon
      }
    },
    getOpenIcon(data) {
      if (data.icon && data.icon !== '') {
        return data.icon
      } else {
        return this.tsOptions.openIcon
      }
    },
    getCloseIcon(data) {
      if (data.icon && data.icon !== '') {
        return data.icon
      } else {
        return this.tsOptions.closeIcon
      }
    },
    // 获取某个节点向上所有父节点
    treeFindPath(tree, func, path = []) {
      if (!tree) return []
      for (const data of tree) {
        // 这里按照你的需求来存放最后返回的内容吧
        path.push(data[this.tsOptions.nodeKey])
        if (func(data)) return path
        if (data.children) {
          const findChildren = this.treeFindPath(data.children, func, path)
          if (findChildren.length) return findChildren
        }
        path.pop()
      }
      return []
    },
    guid() {
      return 'tree' + Number(Math.random().toString().substr(3, 3) + Date.now()).toString(36)
    }
  }
}
</script>
<style>
.el-tree-node-hover{
  background: #f0f8ff;
}
</style>

