<!--
 * @Author: liuyang
 * @Date: 2020-05-28 14:22
 * @LastEditors  : liuyang
 * @LastEditTime : 2020-04-28 14:22
 * @Description: 操作提示管理
-->
<template>
	<div class="app-container">
		<el-row>
			<el-col :span="5">
				<div style="padding: 10px">
					<div style="text-align: center;padding:10px;background: #20a0ff;color: #ffffff">操作提示树</div>
					<el-tree :data="trees" :props="defaultProps" @node-click="handleNodeClick" />
				</div>
			</el-col>
			<el-col :span="19">
				<div class="filter-container">
					<ts-tooltip code="2">
						<el-button
								class="filter-item"
								style="margin-left: 10px;margin-top: 10px"
								type="primary"
								icon="el-icon-plus"
								@click="handleAdd"
						>新增
						</el-button>
					</ts-tooltip>
					<ts-tooltip code="123">
						<el-button size="mini">操作提示测试</el-button>
					</ts-tooltip>
				</div>
				<el-table
						:data="tableData"
						border
						style="width: 100%"
				>
					<el-table-column prop="code" label="提示代码" />
					<el-table-column prop="title" label="提示标题" />
					<el-table-column prop="content" label="提示内容" />
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
							<el-button size="mini" @click="handleDelete(scope.row)">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-col>
		</el-row>

		<el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
			<el-form ref="form" :model="form" :rules="rules">
				<el-form-item label="提示父ID" :label-width="formLabelWidth">
					<el-select v-model="form.pid" placeholder="请选择">
						<el-option
								v-for="item in idOptions"
								:key="item.id"
								:clearable="true"
								:label="item.title"
								:value="item.id"
						>
							<span style="float: left">{{ item.title }}</span>
							<span style="float: right; color: #8492a6; font-size: 13px;margin-left: 10px">{{ item.id }}</span>
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="提示类别" :label-width="formLabelWidth" prop="type">
					<el-select v-model="form.type" placeholder="请选择">
						<el-option
								v-for="item in typeOptions"
								:key="item.value"
								:label="item.label"
								:value="item.value"
						/>
					</el-select>
				</el-form-item>
				<el-form-item label="提示代码" :label-width="formLabelWidth" prop="code">
					<el-input v-model="form.code" autocomplete="off" :disabled="inputType==2" />
				</el-form-item>
				<el-form-item label="提示标题" :label-width="formLabelWidth" prop="title">
					<el-input v-model="form.title" autocomplete="off" />
				</el-form-item>
				<el-form-item label="提示内容" :label-width="formLabelWidth" prop="content">
					<el-input v-model="form.content" autocomplete="off" />
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="submitClick">确 定</el-button>
			</div>
		</el-dialog>
	</div>

</template>

<script>
import { findTipList, saveTipByCode, updateTipByCode, deleteTipByCode } from '@/api/helpCenter'
export default {
  name: 'UserManagementTable',
  components: { },
  data() {
    return {
      formLabelWidth: '120px',
      dialogTitle: '新增操作提示',
      dialogFormVisible: false,
      inputType: 1, // 1新增 2修改
      listQuery: {},
      tableData: null,
      allTableData: null,
      rules: {
        type: [{ required: true, message: '请选择提示类型' }],
        code: [{ required: true, message: '请输入提示代码' }],
        title: [{ required: true, message: '请输入提示标题' }],
        content: [{ required: true, message: '请输入提示内容' }]
      },
      form: {
        type: '', //	提示类型(0=提示类别，1=提示)
        code: '', //	提示代码
        title: '', //	提示标题
        content: '', //	提示内容
        pid: ''//	提示父ID
      },
      typeOptions: [{
        value: '0',
        label: '提示类别'
      }, {
        value: '1',
        label: '提示'
      }],
      idOptions: [],
      trees: [],
      defaultProps: {
        children: 'children',
        label: 'text'
      }
    }
  },
  created() {
    this.getTipList()
  },
  methods: {
    getTipList() {
      findTipList().then(response => {
        this.tableData = JSON.parse(JSON.stringify(response.data))
        this.allTableData = JSON.parse(JSON.stringify(response.data))
        this.trees = this.$tsVue.tansunArray2Tree(response.data)
      })
    },
    handleAdd: function() {
      // 添加
      Object.keys(this.form).forEach(key => (this.form[key] = ''))
      this.idOptions = this.allTableData
      this.dialogTitle = '新增操作提示'
      this.inputType = 1
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.form.clearValidate()
      })
    },
    handleEdit: function(row) {
      // 编辑
      this.form.type = row.type
      this.form.code = row.code
      this.form.title = row.title
      this.form.content = row.content
      this.form.pid = row.pid

      this.idOptions = []
      for (let i = 0; i < this.allTableData.length; i++) {
        if (this.allTableData[i].id !== row.id) {
          this.idOptions.push(this.allTableData[i])
        }
      }

      this.dialogTitle = '修改操作提示'
      this.inputType = 2
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.form.clearValidate()
      })
    },
    submitClick: function() {
      // 确定
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.inputType === 1) {
            // 新增
            saveTipByCode(this.form).then(response => {
              if (response.data !== 0) {
                this.$message({
                  message: '新增成功',
                  type: 'success'
                })
                this.getTipList()
                this.dialogFormVisible = false
              } else {
                this.$message({
                  message: '新增失败',
                  type: 'error'
                })
              }
            })
          } else if (this.inputType === 2) {
            // 修改
            updateTipByCode(this.form).then(response => {
              if (response.data !== 0) {
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
                this.getTipList()
                this.dialogFormVisible = false
              } else {
                this.$message({
                  message: '修改失败',
                  type: 'error'
                })
              }
            })
          }
        }
      })
    },
    handleDelete: function(row) {
      // 删除
      this.form.type = row.type
      this.form.code = row.code
      this.form.title = row.title
      this.form.content = row.content
      this.form.pid = row.pid

      this.$confirm('确认删除？')
        .then(_ => {
          // 确定删除
          deleteTipByCode(this.form).then(response => {
            if (response.data !== 0) {
              this.$message({
                message: '删除成功',
                type: 'success'
              })
              this.getTipList()
            } else {
              this.$message({
                message: '删除失败',
                type: 'error'
              })
            }
          })
        })
        .catch(_ => {
          // 取消
        })
    },
    handleNodeClick: function(data) {
      this.tableData = this.findArrById(this.allTableData, data.id)
    },
    // 找子节点
    findArrById: function(zNodes, id) {
      const ans = []
      for (let i = 0; i < zNodes.length; i++) {
        if (id === zNodes[i].id) {
          ans.push(zNodes[i])
        }
      }
      return ans.concat(this.findC(zNodes, id))
    },
    findC: function(zNodes, id) {
      let ans = []
      for (let i = 0; i < zNodes.length; i++) {
        if (id === zNodes[i].pid) {
          ans.push(zNodes[i])
          ans = ans.concat(this.findC(zNodes, zNodes[i].id))
        }
      }
      return ans
    }
  }
}
</script>
