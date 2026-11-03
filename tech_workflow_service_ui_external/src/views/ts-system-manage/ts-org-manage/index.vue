<template>
  <div class="app-container">
    <el-row :gutter="25">
      <el-col :span="7">
        <div class="ts-pane-box">
          <div class="header">机构树</div>
          <div class="content">
            <div class="handle-bar">
              <i class="iconfont icontianjiawenjian" @click="showParentTable" title="新增"></i>
              <i class="iconfont-x icontianjiaziyemian" @click="showChildTable" title="新增子机构"></i>
              <i class="iconfont iconshanchu" @click="deleteOrg" title="删除"></i>
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
            <el-tab-pane label="机构信息">
              <el-form ref="orgEditForm" class="single-col-form" :label-width="formLabelWidth" :model="orgEditForm" :rules="editFormRules">
                <el-form-item label="上级机构名称" prop="parentOrgName">
                  <el-input v-model="orgEditForm.parentOrgName" autocomplete="off" :disabled="true"/>
                </el-form-item>
                <el-form-item label="上级机构编号" prop="parentOrgCode">
                  <el-input v-model="orgEditForm.parentOrgCode" autocomplete="off" :disabled="true"/>
                </el-form-item>
                <el-form-item label="机构名称" prop="orgName">
                  <el-input v-model="orgEditForm.orgName" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="机构编号" prop="orgCode">
                  <el-input v-model="orgEditForm.orgCode" autocomplete="off" :disabled="true"/>
                </el-form-item>
                <el-form-item label="机构等级" prop="menuName">
                  <ts-multi-select v-model="orgEditForm.orgLevelCd" :params="{ key: 'OrgLevel', width: '100%' }" :disabled="true"></ts-multi-select>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                  <el-input v-model="orgEditForm.sort" autocomplete="off"/>
                </el-form-item>
              </el-form>
              <div class="button-box">
                <el-button v-if="orgEditForm.orgCode!==''" type="primary" icon="iconfont iconqueren" @click="confirmEdit">确认</el-button>
              </div>
            </el-tab-pane>
            <el-tab-pane label="用户">
              <el-form ref="paramForm" :inline="true" :model="listQuery" class="search-container" size="small">
                <el-form-item prop="dctTpNm" label="登录名：">
                  <el-input v-model="listQuery.loginNameLike" placeholder="请输入登录名" />
                </el-form-item>
                <el-form-item prop="dctTpCdLike" label="姓名：">
                  <el-input v-model="listQuery.realname" placeholder="请输入姓名" />
                </el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="handleFilter">查询</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="handleReset">重置</el-button>
              </el-form>

              <ts-multi-table :data="tableData" ref="userListTable" :query="listQuery" :options="tableOptions">
                <el-table-column prop="userNum" label="用户编号"/>
                <el-table-column prop="userLoginName" label="登录名称"/>
                <el-table-column prop="userRealname" label="姓名"/>
                <el-table-column prop="sexCd" label="性别" width="80">
                  <template slot-scope="scope">
                    {{ scope.row.userSexCd | tsConvert('Gnd')}}
                  </template>
                </el-table-column>
                <el-table-column prop="telephone" label="电话" width="150"/>
                <el-table-column prop="userStatusCd" label="用户状态" width="80">
                  <template slot-scope="scope">
                    {{ scope.row.userStatusCd | tsConvert('StCd') }}
                  </template>
                </el-table-column>
                <el-table-column prop="statusCd" label="绑定状态" width="80">
                  <template slot-scope="scope">
                    {{ scope.row.statusCd | tsConvert('StCd') }}
                  </template>
                </el-table-column>
              </ts-multi-table>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>

    <ts-multi-dialog :visible.sync="dialogVisible" :options="dialogOptions"></ts-multi-dialog>

  </div>
</template>

<script>
import orgForm from './form'

export default {
  data() {
    return {
      formLabelWidth: '120px',
      tableData: null,
      total: 0,
      tableOptions: {
        apiName: this.$api.fetchOrgUser,
        paramsOrdata: 'data'
      },
      listQuery: {
        orgCode: '',
        loginNameLike: '',
        realname: ''
      },
      currentNodeData: null,
      orgEditForm: {
        orgLevelCd: '',
        orgName: '',
        orgCode: '',
        parentOrgCode: '',
        parentOrgName: '',
        sort: ''
      },
      editFormRules: {
        orgName: [
          { required: true, message: '请输入机构名称', trigger: 'blur' }
        ],
        orgCode: [
          { required: true, message: '请输入机构编号', trigger: 'blur' }
        ],
        orgLevelCd: [
          { required: true, message: '请输入机构等级', trigger: 'blur' }
        ]
      },
      // 表单
      dialogVisible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      dialogOptions: {
        title: '新增机构', // [必填]
        width: '850px', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        resource: orgForm, // [必填] 在弹框中展示的组件页面
        data: {
          orgLevelCd: '',
          orgName: '',
          orgCode: '',
          parentOrgCode: '',
          parentOrgName: '',
          sort: ''
        },
        beforeClose: (data, done) => { done() },
        callbacks: [(data, done) => { done() }, this.confirm] // [必填] 若取消按钮没有回调，可写默认值null 如[null, this.submit]
      },
      treesOptions: {
        height:500,
        apiName: this.$api.fetchOrgTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        highlight: true// 是否高亮当前选中行
      }
    }
  },
  created() {
  },
  methods: {
    getOrgTreeList() {
      this.$refs.mTrees.updateTree()
    },
    confirm(data, done) {
      this.$request({
        url: this.$api.saveOrgTree,
        data: data
      }).then((response) => {
        done()
        this.getOrgTreeList()
        this.$message.success(response.description)
      })
    },
    confirmEdit() {
      const params = { ...this.orgEditForm }

      this.$request({
        url: this.$api.saveOrgTree,
        data: params
      }).then((response) => {
        this.getOrgTreeList()
        this.$message.success(response.description)
      })
    },
    showParentTable() {
      this.dialogOptions.data.orgLevelCd = 'LV00'
      this.dialogOptions.data.parentOrgCode = ''
      this.dialogOptions.data.parentOrgName = ''
      this.dialogVisible = true
    },
    showChildTable() {
      if (!this.alertCheckMsg()) return
      const { orgLevelCd, orgCode, orgName } = this.currentNodeData
      let code = parseInt(orgLevelCd.replace('LV', '')) + 1
      code = code < 10 ? 'LV0' + code : 'LV' + code
      this.dialogOptions.data.orgLevelCd = code
      this.dialogOptions.data.parentOrgCode = orgCode
      this.dialogOptions.data.parentOrgName = orgName
      this.dialogVisible = true
    },
    alertCheckMsg() {
      if (this.currentNodeData === null) {
        this.$alert('请先选中一个模块后再操作！', '提示', {
          confirmButtonText: '确定'
        })
        return false
      } else {
        return true
      }
    },
    deleteOrg() {
      if (!this.alertCheckMsg()) return
      this.$confirm(`将会删除机构信息，确认删除？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = {
          id: this.currentNodeData.id
        }

        this.$request({
          url: this.$api.deleteOrgById,
          data: params
        }).then((response) => {
          this.getOrgTreeList()

          if (response && response.status == 200) {
            this.$message.success('删除成功')
          }
        })
      }).catch(() => {})
    },
    handleNodeClick(data) {
      const params = { orgCode: data.id }
      this.$request({
        url: this.$api.fetchOrgInfo,
        data: params
      }).then((response) => {
        this.currentNodeData = response.data
        this.orgEditForm = response.data
        this.getOrgUserList()
      })
    },
    getOrgUserList() {
      this.listQuery.orgCode = this.currentNodeData.orgCode
      this.listQuery.loginNameLike = this.listQuery.loginNameLike.trim()
      this.listQuery.realname = this.listQuery.realname.trim()
      this.$refs['userListTable'].serchHandel()
    },
    handleFilter() {
      this.$refs['userListTable'].serchHandel()
    },
    handleReset() {
      this.listQuery.loginNameLike = ''
      this.listQuery.realname = ''
    },
    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getOrgUserList()
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val
      this.getOrgUserList()
    }
  }
}
</script>

