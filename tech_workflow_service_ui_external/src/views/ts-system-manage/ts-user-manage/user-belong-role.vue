<!--
 * @Author: your name
 * @Date: 2020-04-08 00:18:15
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-24 10:19:16
 * @Description:
 -->
<template>
  <div>
    <div class="handle-container">
      <el-button-group>
        <el-button type="primary" icon="el-icon-plus" @click="assignRole">分配所属角色</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="deleteUser">移除所属角色</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="editRoleRel">修改关联状态</el-button>
      </el-button-group>
    </div>

    <ts-multi-table ref="roleRelTable" :query="query" :options="options">
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="statusCd" label="角色用户关联状态">
        <template v-slot="scope">
          <span>{{scope.row.statusCd | tsConvert('StCd')}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="defaultInd" label="是否默认角色">
        <template v-slot="scope">
          <span>{{scope.row.defaultInd | tsConvert('Indicator')}}</span>
        </template>
      </el-table-column>
    </ts-multi-table>

    <ts-multi-dialog :visible.sync="visible" :options="roleOptions"></ts-multi-dialog>

  </div>
</template>

<script>
import FormContent from '@/views/ts-system-manage/ts-user-manage/form/edit-belong-role'
import { enableSession } from '@/plugins/settings'

export default {
  name: 'UserRole',
  props: {
    data: { // 接收组件间通信容器的参数data
      type: Object
    }
  },
  data() {
    return {
      query: {},
      options: {
        type: 'radio',
        apiName: this.$api.queryUserRoleRel,
        apiRequiredParams: {}
      },
      visible: false,
      roleOptions: {
        disabled: false,
        data: {},
        title: '所属角色信息',
        resource: FormContent,
        callbacks: [null, this.assignUserRoleRel]
      }
    }
  },

  created() {
    this.initData()
  },
  methods: {
    initData() {
      const userNum = this.$route.query.userNum || this.data.userNum
      this.query.userNum = userNum
      this.options.apiRequiredParams.userNum = userNum
    },

    assignRole() {
      this.visible = true
      this.roleOptions.data = {}
      // this.roleOptions.disabled = false
      this.roleOptions.callbacks = [null, this.assignUserRoleRel]
    },

    editRoleRel() {
      this.selectData = this.$refs['roleRelTable'].getRadioData()
      if (this.selectData) {
        const { id } = this.selectData
        const obj1 = { url: this.$api.queryUserRoleInfo, data: { id }}
        const obj2 = { type: 'get', paramMode: 'params' }
        const params = enableSession ? obj1 : Object.assign(obj1, obj2)
        this.$request(params).then(res => {
          const data = res.data
          const { roleName, roleCode, statusCd, defaultInd } = data
          // this.roleOptions.disabled = true
          this.roleOptions.data = { roleName, roleCode, statusCd, defaultInd }
          this.roleOptions.callbacks = [null, this.editUserRoleRel]

          console.log(this.roleOptions)

          this.visible = true
        }).catch(err => {
          this.$tsVue.error('获取数据失败！')
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },

    // 分配所属机构
    assignUserRoleRel(data, done) {
      const { orgName, orgCode, roleName, roleCode, statusCd, defaultInd } = data
      const userNum = this.$route.query.userNum || this.data.userNum
      const params = {
        userNum,
        orgName,
        orgCode,
        roleName,
        roleCode,
        statusCd,
        defaultInd
      }
      this.$request({
        url: this.$api.saveUserAndRoleRel,
        data: params
      }).then((response) => {
        this.$tsVue.success('操作成功！', () => {})
        done()
        this.$refs['roleRelTable'].refreshDataSource()
      }).catch(err => {
        done()
        console.log('saveUserAndRoleRel error!!!')
      })
    },

    // 移除所属机构
    deleteUser() {
      this.selectData = this.$refs['roleRelTable'].getRadioData()
      if (this.selectData) {
        this.$tsVue.confirm('您确认要删除该记录吗？', () => {
          const { id } = this.selectData
          this.$request({
            url: this.$api.deleteUserAndRoleRel,
            data: { id }
          }).then(res => {
            this.$tsVue.success('删除成功！', () => {})
            this.$refs['roleRelTable'].refreshDataSource()
          })
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },

    // 修改关联状态和默认
    editUserRoleRel(data, done) {
      const { orgName, orgCode, roleName, roleCode, statusCd, defaultInd } = data
      const params = Object.assign(this.selectData, data)
      this.$request({
        url: this.$api.saveUserAndRoleRel,
        data: params
      }).then(res => {
        this.$tsVue.success('修改成功！', () => {})
        done()
        this.$refs['roleRelTable'].refreshDataSource()
      })
    }

  }
}
</script>
