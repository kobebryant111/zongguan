<!--
 * @Author: chenhuocan
 * @Date: 2020-03-30 18:19:20
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-07-22 10:32:33
 * @Description: 部门管理
 -->
<template>
  <div class="app-container">
    <el-row :gutter="25">
      <el-col :span="7">
        <div class="ts-pane-box">
          <div class="header">部门管理</div>
          <div class="content">
            <div class="handle-bar">
              <i class="iconfont icontianjiawenjian" @click="addTreeNodeClick" title="新增"></i>
              <i class="iconfont-x icontianjiaziyemian" @click="addTreeChildNodeClick" title="增加子部门"></i>
              <i class="iconfont iconshanchu" @click="deleteTreeNode" title="删除"></i>
            </div>
            <ts-multi-trees
              ref="mTrees"
              :options="treesOptions"
              @node-click="handleNodeClick">
            </ts-multi-trees>
          </div>
        </div>
      </el-col>
      <el-col :span="17">
        <div class="ts-tabs-box">
          <el-tabs>
            <el-tab-pane label="部门信息">
              <el-form
                v-show="showDepInfoPanel"
                :model="editDepData"
                ref="editDepData"
                :rules="rules"
                label-width="120px"
                class="single-col-form"
              >
                <el-input type="hidden" v-model="editDepData.id"/>
                <el-input type="hidden" v-model="editDepData.orgDeptRelId"/>
                <el-form-item label="所属机构编号:" prop="orgCode">
                  <el-input v-model="editDepData.orgCode" style="" :disabled="true"/>
                </el-form-item>
                <el-form-item label="所属机构名称:" prop="orgName">
                  <el-input v-model="editDepData.orgName" :disabled="true"/>
                </el-form-item>
                <el-form-item label="上级部门编号:" prop="parentDeptCode">
                  <el-input v-model="editDepData.parentDeptCode" placeholder="-----" :disabled="true"/>
                </el-form-item>
                <el-form-item label="上级部门名称:" prop="parentDeptName">
                  <el-input v-model="editDepData.parentDeptName" placeholder="-----" :disabled="true"/>
                </el-form-item>
                <el-form-item label="部门编号:" prop="deptCode">
                  <el-input v-model="editDepData.deptCode" :disabled="true"/>
                </el-form-item>
                <el-form-item label="部门名称:" prop="deptName">
                  <el-input v-model="editDepData.deptName"/>
                </el-form-item>
                <el-form-item style="display: flex;justify-content: center">
                  <el-button type="primary" icon="el-icon-check" @click="saveEditForm()">确认</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="用户">
              <ts-multi-table ref="userTable" :options="tableOptions">
                <el-table-column prop="userNum" label="用户编号"></el-table-column>
                <el-table-column prop="userLoginName" label="登录名称"></el-table-column>
                <el-table-column prop="userRealname" label="姓名"></el-table-column>
                <el-table-column prop="userSexCd" label="性别">
                  <template slot-scope="scope">
                    {{scope.row.userSexCd | tsConvert('Gnd')}}
                  </template>
                </el-table-column>
                <el-table-column prop="phone" label="电话"></el-table-column>
                <el-table-column prop="stCd" label="状态">
                  <template slot-scope="scope">
                    {{scope.row.stCd | tsConvert('StCd')}}
                  </template>
                </el-table-column>
              </ts-multi-table>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
    <!-- 新增部门表单 -->
    <el-dialog
        title="添加部门"
        :visible.sync="dialogVisible"
        width="50%"
    >
      <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="120px" class="demo-ruleForm">
        <el-form-item label="所属机构编号" prop="orgCode">
          <el-input v-model="ruleForm.orgCode" :disabled="true"/>
        </el-form-item>
        <el-form-item label="所属机构名称" prop="orgName">
          <el-popover v-model="org.orgVisible" placement="bottom-start" width="560">
            <el-input v-model="org.orgInput" suffix-icon="el-icon-search" placeholder="请输入内容" @input="orgInput"/>
            <el-table max-height="200" :data="tableData" style="width: 100%" @row-click="orgRowClick">
              <el-table-column align="center" prop="orgName" label="机构名称"/>
              <el-table-column align="center" prop="orgCode" label="机构编号"/>
            </el-table>
            <div style="text-align: center;margin-top: 20px">
              <el-button-group>
                <el-button type="primary" size="small" icon="el-icon-arrow-left" @click="prevClick">上一页</el-button>
                <el-button type="primary" size="small" @click="nextClick">下一页<i
                    class="el-icon-arrow-right el-icon--right"/></el-button>
              </el-button-group>
            </div>
            <el-button slot="reference" class="el-input__inner" style="text-align: left">{{ ruleForm.orgName }}
            </el-button>
          </el-popover>
        </el-form-item>

        <el-form-item label="上级部门编号" prop="parentDeptCode">
          <el-input v-model="ruleForm.parentDeptCode" :disabled="true"/>
        </el-form-item>
        <el-form-item label="上级部门名称" prop="parentDeptName">
          <el-input v-model="ruleForm.parentDeptName" :disabled="true"/>
        </el-form-item>
        <el-form-item label="部门编号" prop="deptCode">
          <el-input v-model="ruleForm.deptCode" :disabled="true" placeholder="自动生成"/>
        </el-form-item>
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="ruleForm.deptName" @input="departmentInput"/>
        </el-form-item>

        <el-form-item style="display: flex;justify-content: flex-end">
          <el-button type="primary" @click="submitForm()">确认</el-button>
          <el-button @click="resetForm('ruleForm')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <ts-multi-dialog
        :visible.sync="childDialogVisible"
        :options="dialogOptions">
    </ts-multi-dialog>
  </div>
</template>

<script>
import Form from './form'

export default {
  name: 'Index',
  data() {
    return {
      showDepInfoPanel: false,
      editDepData: {
        id: 0,
        orgDeptRelId: '0',
        orgCode: '',
        orgName: '',
        parentDeptCode: '',
        parentDeptName: '--',
        deptCode: '',
        deptName: ''
      },
      ruleForm: {
        orgCode: '',
        orgName: '',
        parentDeptCode: '--',
        parentDeptName: '--',
        deptCode: '自动生成',
        deptName: ''
      },
      rules: {
        orgCoe: [
          { required: true, message: '请输入所属机构编号', trigger: 'blur' }
        ],
        orgName: [
          { required: true, message: '请输入所属机构名称', trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '部门名称', trigger: 'blur' },
          { min: 3, max: 30, message: '长度在 3 到 30 个字符', trigger: 'blur' }
        ]
      },
      // 表格属性设置
      tableOptions: {
        apiName: this.$api.userAndDeptRelQuery,
        paramsOrdata: 'data', // 必填 ‘params’ 走token ‘data’ 走 sesson
        apiRequiredParams: {
          deptCode: ''
        }
      },
      tableData: [{
        'orgName': '',
        'orgCode': ''
      }],
      org: {
        orgVisible: false,
        orgValue: '',
        orgInput: '',
        pages: 1,
        pageIndex: 1
      },
      //  新增部门表单
      dialogVisible: false,
      addRuleForm: {
        name: '',
        region: ''
      },
      //  新增子部门表单
      childDialogVisible: false,
      dialogOptions: {
        title: '新增子部门',
        resource: Form, // [必填] 在弹框中展示的组件页面
        data: {
          parentDeptCode: '',
          parentDeptName: '',
          deptCode: '',
          deptName: '',
          statusCd: '1'
        },
        beforeClose: (data, done) => { done() },
        callbacks: [(data, done) => { done() }, this.confirmChildDepartmentForm]
      },
      currentNodeData: {},
      // 部门树
      treesOptions: {
        height:500,
        apiName: this.$api.getDepartmentTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        highlight: true// 是否高亮当前选中行
      }
    }
  },
  created() {
  },
  methods: {
    goback() {
      this.$tsVue.tansunBack(this)
    },
    loadingDepTree() {
      this.$refs.mTrees.updateTree()
    },
    addTreeNodeClick() {
      // 新增部门节点
      this.ruleForm.orgCode = ''
      this.ruleForm.orgName = ''
      this.ruleForm.deptName = ''
      this.dialogVisible = true
      this.getOrgSelector(1, '')
    },
    addTreeChildNodeClick() {
      // 新增子部门节点
      if (!this.currentNodeData.id) {
        this.$message.warning('请先选择节点')
        return
      }

      console.log(this.currentNodeData)

      this.dialogOptions.data.parentDeptCode = this.currentNodeData.id
      this.dialogOptions.data.parentDeptName = this.currentNodeData.name
      this.childDialogVisible = true
    },
    deleteTreeNode() {
      // 删除 部门节点
      this.$confirm('删除部门将会删除部门信息, 确认删除?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$request({
          url: this.$api.departmentDelete,
          data: this.editDepData
        }).then((response) => {
          if (response && response.status == 200) {
            this.loadingDepTree()
            // 删除部门
            this.$message({
              type: 'success',
              message: response.description
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleNodeClick(data) {
      this.showDepInfoPanel = true
      this.currentNodeData = data
      this.$refs.editDepData.resetFields()

      this.$request({
        url: this.$api.departmentGet,
        data: { deptCode: data.id }
      }).then((response) => {
        this.editDepData = response.data
        this.getUserList(this.editDepData.deptCode)
      })
    },
    cancel() {
      this.childDialogVisible = false
    },
    confirmChildDepartmentForm(data, done) {
      this.$request({
        url: this.$api.departmentSave,
        data: data
      }).then((response) => {
        if (response && response.status == 200) {
          this.loadingDepTree()
          this.$message.success(response.description)
          this.childDialogVisible = false
        }
      })
    },
    saveEditForm() {
      this.$refs['editDepData'].validate((valid) => {
        if (valid) {
          this.$request({
            url: this.$api.departmentSave,
            data: this.editDepData
          }).then((response) => {
            if (response && response.status === 200) {
              this.$message({
                message: '保存成功',
                type: 'success',
                duration: 3000
              })
              this.loadingDepTree()
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
      const parms = {
        orgCode: this.ruleForm.orgCode,
        orgName: this.ruleForm.orgName,
        deptName: this.ruleForm.deptName
      }
      this.$request({
        url: this.$api.departmentSave,
        data: parms
      }).then((response) => {
        if (response && response.status == 200) {
          this.loadingDepTree()
          this.$message.success('保存成功')
          this.dialogVisible = false
        }
      })
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.dialogVisible = false
    },
    // 上一页
    prevClick() {
      this.org.pageIndex--
      if (this.org.pageIndex < 1) {
        return
      }
      this.getOrgSelector(this.org.pageIndex, '')
    },
    // 下一页
    nextClick() {
      this.org.pageIndex++
      if (this.org.pageIndex > this.org.pages) {
        return
      }
      this.getOrgSelector(this.org.pageIndex, '')
    },
    orgInput(e) {
      this.getOrgSelector(this.org.pageIndex, e)
    },
    orgRowClick(row, column, event) {
      this.ruleForm.orgCode = row.orgCode
      this.ruleForm.orgName = row.orgName
      this.org.orgVisible = false
    },
    departmentInput(e) {
      this.ruleForm.name6 = e
    },
    getOrgSelector(pageNum, orgName) {
      const self = this
      const param = {
        orgName,
        pageNum,
        pageSize: 7
      }
      this.$request({
        url: this.$api.orgSelector,
        data: param
      }).then((response) => {
        self.tableData = response.data.grid.list
        self.org.pages = response.data.grid.pages
      })
    },
    getUserList(deptCode) {
      this.tableOptions.apiRequiredParams.deptCode = deptCode
      this.$refs['userTable'].refreshDataSource()
    }
  }
}
</script>

<style lang=scss scoped>
  .leftBody {
    margin-top: 20px;
    margin-left: 20px;
    margin-right: 20px;
    border: 1px solid #cccccc;
  }

  .rightBody {
    margin-top: 20px;
    margin-right: 20px;
  }
</style>
