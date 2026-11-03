<template>
  <div class="ts-pane-box">
    <!--    <div class="header">组织结构树</div>-->
    <el-tabs type="border-card">
      <el-tab-pane label="组织结构树">
        <div class="content">
          <div style="margin: 10px 0">
            <el-input v-model="searchValue" size="mini" placeholder="请输入关键字搜索" clearable>
              <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>
          </div>
          <big-tree
              :style="{height:height}"
              ref="bigTree"
              :data="trees"
              :check-strictly="true"
              :show-checkbox="false"
              :expand-on-click-node="false"
              @node-click="nodeClick"
              highlight-current>
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span class="node" v-if="node.isLeaf">
                  <!-- 子节点  -->
                  <i :class="[data.isFolder?'el-icon-folder':'el-icon-document','node-img']"></i>
                  {{ node.label }}
              </span>
              <span class="node" v-if="!node.isLeaf">
                  <!-- 节点未展开  -->
                  <i v-if="!node.expanded" class="el-icon-folder node-img"></i>
                <!-- 节点展开  -->
                  <i v-if="node.expanded" class="el-icon-folder-opened node-img"></i>
                  {{ node.label }}
              </span>
            </span>
          </big-tree>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import BigTree from './src/tree'

export default {
  name: 'Tree',
  components: {
    BigTree
  },
  props:{
    height:{
      type:String,
      default:'auto'
    },
    type:{
      type:String,
      default:''
    }
  },
  data() {
    return {
      treeClickCount: 0,
      searchValue: '',
      treeList: [],
      trees: [],
      originTree: []
    }
  },
  watch: {
    searchValue(val) {
      let arr = []
      // this.options 为填入tree的数据 this.initOptions 为备份数据只在数据查询时复制后面不做该动 和options一起赋值
      let options = JSON.parse(JSON.stringify(this.originTree))
      this.trees = this.findRecord(options, 'label', val)
      //搜索时展开所有节点
      this.$refs.bigTree.store.defaultExpandAll = val !== ''
    },
    type(val){
      this.initTreeData()
    }
  },
  created() {
    this.initTreeData()
  },
  methods: {
    initTreeData() {
      this.$request({
        url: '/workflow/editor/findUserOrgRoleList', // [必填] 请求地址
        data: {
          tenantId:this.$store.state.user.userInfo.tenantId
        }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        let list = response.data
        this.treeList = list
        //只过滤出一种类型
        if(this.type === 'user'){
          list = list.filter((item)=>{
            return item.type === 'user'
          })
          //去除重复用户
          let arr2 = list.filter((item, index) => {
            let temArr = []
            list.forEach(item2 => temArr.push(item2.id))
            return temArr.indexOf(item.id) == index
          })
          this.treeList = arr2
        }
        if(this.type === 'role'){
          this.treeList = list.filter((item)=>{
            return item.type === 'role' || item.id === '#role'
          })
        }
        if(this.type === 'org'){
          this.treeList = list.filter((item)=>{
            return item.type === 'org' || item.id === '#org'
          })
        }
        this.trees = this.array2Tree(this.treeList)

        //过滤出机构人或角色人
        if (this.type === 'orguser') {
          this.trees = this.trees.filter((item) => {
            return item.id === '#org'
          })
        }
        if (this.type === 'roleuser') {
          this.trees = this.trees.filter((item) => {
            return item.id === '#role'
          })
        }

        this.originTree = this.trees
        this.$emit('treeData', this.originTree, this.treeList)
      })
    },
    nodeClick(data) {
      if (data.id === '#role' || data.id === '#org') {
        return
      }
      this.$emit('node-click', data)
    },
    treeDblClick(callback) {
      //记录点击次数
      this.treeClickCount++;
      //单次点击次数超过2次不作处理,直接返回,也可以拓展成多击事件
      if (this.treeClickCount >= 2) {
        return;
      }
      //计时器,计算300毫秒为单位,可自行修改
      setTimeout(() => {
        if (this.treeClickCount == 1) {
          //把次数归零
          this.treeClickCount = 0;
          //单击事件处理
          // console.log('单击事件,可在此处理对应逻辑')
        } else if (this.treeClickCount > 1) {
          //把次数归零
          this.treeClickCount = 0;
          //双击事件
          // console.log('双击事件,可在此处理对应逻辑')
          callback()
        }
      }, 200);
    },
    //数组转树
    array2Tree(datum, disabled = false, defaultProp = { parent: 'pid', label: 'name', child: 'id' }) {
      const result = []
      const map = {}
      const data = JSON.parse(JSON.stringify(datum))
      const parentKey = defaultProp.parent ? defaultProp.parent : 'pid'
      const labelKey = defaultProp.label ? defaultProp.label : 'name'
      const childKey = defaultProp.child ? defaultProp.child : 'id'
      data.forEach(item => {
        item.disabled = disabled
        if (item[childKey] == '#org' || (item[childKey] == '#role')) {
          item.label = item[labelKey]
          item.disabled = true
          item.isFolder = true
        } else {
          item.label = item[labelKey] + '(id:' + item[childKey] + ')'
        }
        const id = item[childKey]
        map[id] = item
      })
      data.forEach(item => {
        const parent = map[item[parentKey]]
        if (parent && (parent[parentKey] !== item[childKey])) {
          (parent.children || (parent.children = [])).push(item)
        } else {
          result.push(item)
        }
      })
      return result
    },
    findRecord(treeData, key, value, eq = false, notIncluded = true) {
      const arr = []
      for (const node of treeData) {
        let flag = false
        const val = node[key]
        if (Array.isArray(value)) {
          if (eq) {
            if (val && value.includes(val)) {
              flag = true
            }
            if ((!notIncluded) && val && !value.includes(val)) {
              flag = true
            } else if (!notIncluded) {
              flag = false
            }
          } else {
            if (val && value.findIndex(item => item.indexOf(val) > -1) > 0) {
              flag = true
            }
            if ((!notIncluded) && val && value.findIndex(item => item.indexOf(val) === -1) > 0) {
              flag = true
            } else if (!notIncluded) {
              flag = false
            }
          }
        } else {
          if (eq) {
            if (val && val === value) {
              flag = true
            }
            if ((!notIncluded) && val && val !== value) {
              flag = true
            } else if (!notIncluded) {
              flag = false
            }
          } else {
            if (val && val.indexOf(value) > -1) {
              flag = true
            }
            if ((!notIncluded) && val && val.indexOf(value) === -1) {
              flag = true
            } else if (!notIncluded) {
              flag = false
            }
          }
        }
        if (flag) {
          arr.push(node)
        } else if (node.children && node.children.length) {
          const subArr = this.findRecord(node.children, key, value, eq, notIncluded)
          if (subArr && subArr.length > 0) {
            node.children = subArr
            arr.push(node)
          }
        }
      }
      return arr
    }
  }
}
</script>

<style scoped>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.node{
  display: flex;
}

.node-img{
  width: 16px;
  height: 16px;
  margin-right: 3px;
  align-self: center;
}
</style>
