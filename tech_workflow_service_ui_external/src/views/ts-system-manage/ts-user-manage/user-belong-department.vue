<!--
 * @Author: your name
 * @Date: 2020-04-08 00:18:15
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-24 10:13:58
 * @Description:
 -->
<template>
  <div>
    <div class="handle-container">
      <el-button-group>
        <el-button type="primary" icon="el-icon-plus" @click="assignDepartment">分配所属部门</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="deleteUser">移除所属部门</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="editDepartRel">修改关联状态</el-button>
      </el-button-group>
    </div>

    <ts-multi-table ref="departRelTable" :query="query" :options="options">
      <el-table-column prop="deptName" label="部门名称" />
      <el-table-column prop="orgName" label="机构名称" />
      <el-table-column prop="statusCd" label="部门用户关联状态">
        <template v-slot="scope">
          <span>{{scope.row.statusCd | tsConvert('StCd')}}</span>
        </template>
      </el-table-column>
    </ts-multi-table>

    <ts-multi-dialog :visible.sync="visible" :options="departOptions"></ts-multi-dialog>

  </div>
</template>

<script>
import FormContent from '@/views/ts-system-manage/ts-user-manage/form/edit-belong-department'
import { enableSession } from '@/plugins/settings'

export default {
  name: 'UserDepartment',
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
        apiName: this.$api.userAndDeptRelQuery,
        apiRequiredParams: {}
      },
      visible: false,
      departOptions: {
        disabled: false,
        data: {},
        title: '所属部门信息',
        resource: FormContent,
        callbacks: [null, this.assignUserDepartRel]
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

    assignDepartment() {
      const userNum = this.$route.query.userNum || this.data.userNum
      this.departOptions.data = { userNum }
      // this.departOptions.disabled = false
      this.departOptions.callbacks = [null, this.assignUserDepartRel]
      this.visible = true
    },

    editDepartRel() {
      this.selectData = this.$refs['departRelTable'].getRadioData()
      if (this.selectData) {
        const { id } = this.selectData
        const obj1 = { url: this.$api.queryUserDeptInfo, data: { id }}
        const obj2 = { type: 'get', paramMode: 'params' }
        const params = enableSession ? obj1 : Object.assign(obj1, obj2)
        this.$request(params).then(res => {
          const data = res.data
          const { orgName, orgCode, statusCd, deptCode, deptName } = data
          // this.departOptions.disabled = true
          this.departOptions.data = { orgName, orgCode, statusCd, deptCode, deptName }
          this.departOptions.userNum = this.$route.query.userNum || this.data.userNumthis.data.userNum
          this.departOptions.callbacks = [null, this.editUserOrgRel]
          this.visible = true
        }).catch(err => {
          this.$tsVue.error('获取数据失败！')
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },

    // 分配所属部门
    assignUserDepartRel(data, done) {
      const { orgName, orgCode, statusCd, deptCode, deptName } = data
      const userNum = this.$route.query.userNum || this.data.userNum
      const params = {
        userNum,
        orgName,
        orgCode,
        deptCode,
        deptName,
        statusCd
      }
      this.$request({
        url: this.$api.saveUserAndDeptRel,
        data: params
      }).then((response) => {
        this.$tsVue.success('操作成功！', () => {})
        done()
        this.$refs['departRelTable'].refreshDataSource()
      }).catch(err => {
        done()
        console.log('saveUserAndDepartRel error!!!')
      })
    },

    // 移除所属部门
    deleteUser() {
      this.selectData = this.$refs['departRelTable'].getRadioData()
      if (this.selectData) {
        this.$tsVue.confirm('您确认要删除该记录吗？', () => {
          const { id } = this.selectData
          this.$request({
            url: this.$api.deleteUserAndDeptRel,
            data: { id }
          }).then(res => {
            this.$tsVue.success('删除成功！', () => {})
            this.$refs['departRelTable'].refreshDataSource()
          })
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },

    // 修改关联状态
    editUserOrgRel(data, done) {
      const { orgName, orgCode, statusCd, deptCode, deptName } = data
      const params = Object.assign(this.selectData, data)
      this.$request({
        url: this.$api.saveUserAndDeptRel,
        data: params
      }).then(res => {
        this.$tsVue.success('修改成功！', () => {})
        done()
        this.$refs['departRelTable'].refreshDataSource()
      })
    }

  }
}
</script>

