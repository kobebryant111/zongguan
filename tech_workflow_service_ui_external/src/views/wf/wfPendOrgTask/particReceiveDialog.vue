<template>
  <div>
    <el-row>
      <el-col :span="10">
        <tree height="350px"
              @node-click="nodeClick"></tree>
      </el-col>
      <el-col :span="4">
        <div class="button-content">
          <el-button size="mini" style="margin: 20px 0" @click="addPar">添加</el-button>
          <el-button size="mini" style="margin: 10px 0" @click="deletePar">删除</el-button>
          <el-button size="mini" style="margin: 10px 0" @click="deleteAll">删除全部</el-button>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="ts-pane-box">
          <el-tabs type="border-card">
            <el-tab-pane label="已选择参与者列表">
              <el-tree style="height: 400px;overflow-y: scroll"
                       highlight-current
                       :data="selects"
                       @node-click="selectedNodeClick"></el-tree>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import tree from '@/components/Tree'

export default {
  components: {
    tree
  },
  data() {
    return {
      selectData: {},//右侧选中的
      selectedData: {},//左侧选中的
      selects: []//已选择参与人list
    }
  },
  created() {

  },
  methods: {
    nodeClick(data) {
      this.selectData = data
    },
    selectedNodeClick(data) {
      this.selectedData = data
    },
    addPar() {
      //添加参与人
      if (this.selectData && this.selectData.type == 'user') {
        let array = this.selects.filter((item) => {
          return item.id == this.selectData.id
        })
        //已选择参与人中已经有了 return
        if (array.length > 0) {
          return
        }
        let data = Object.assign({}, this.selectData)
        delete data.children
        this.selects.push(data)
        this.selectData = null
      }
    },
    deletePar() {
      //删除参与人
      if (this.selectedData) {
        let array = this.selects.filter((item) => {
          return item.id !== this.selectedData.id
        })
        this.selects = array
        this.selectedData = null
      }
    },
    deleteAll() {
      this.selects = []
    }
  }
}
</script>

<style scoped>
.button-content {
  height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
