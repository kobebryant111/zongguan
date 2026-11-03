<!--
 * @Author: your name
 * @Date: 2020-04-08 00:18:15
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-24 10:19:03
 * @Description:
 -->
<template>
  <div>
    <div class="handle-container">
      <el-button-group>
        <el-button type="primary" icon="el-icon-plus" @click="assignOrg">分配所属机构</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="deleteUser">移除所属机构</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="editOrgRel">修改关联状态和默认</el-button>
      </el-button-group>
    </div>

    <ts-multi-table ref="orgRelTable" :query="query" :options="options">
      <el-table-column prop="orgName" label="机构名称" />
      <el-table-column prop="statusCd" label="机构用户关联状态">
        <template v-slot="scope">
          <span>{{scope.row.statusCd | tsConvert('StCd')}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="defaultInd" label="是否默认机构">
        <template v-slot="scope">
          <span>{{scope.row.defaultInd | tsConvert('Indicator')}}</span>
        </template>
      </el-table-column>
    </ts-multi-table>

    <ts-multi-dialog :visible.sync="visible" :options="orgOptions"></ts-multi-dialog>

  </div>
</template>

<script>
import FormContent from '@/views/ts-system-manage/ts-user-manage/form/edit-belong-org'
import { enableSession } from '@/plugins/settings'
export default {
  name: 'UserOrg',
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
        apiName: this.$api.fetchOrgUser,
        apiRequiredParams: {}
      },
      visible: false,
      orgOptions: {
        disabled: false,
        data: {},
        title: '所属机构信息',
        resource: FormContent,
        callbacks: [null, this.assignUserOrgRel]
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

    assignOrg() {
      this.visible = true
      this.orgOptions.data = {}
      // this.orgOptions.disabled = false
      this.orgOptions.callbacks = [null, this.assignUserOrgRel]
    },

    editOrgRel() {
      this.selectData = this.$refs['orgRelTable'].getRadioData()
      if (this.selectData) {
        const { id } = this.selectData
        const obj1 = { url: this.$api.queryUserOrgInfo, data: { id }}
        const obj2 = { type: 'get', paramMode: 'params' }
        const params = enableSession ? obj1 : Object.assign(obj1, obj2)
        this.$request(params).then(res => {
          const data = res.data
          const { orgName, orgCode, statusCd, defaultInd } = data
          this.visible = true
          // this.orgOptions.disabled = true
          this.orgOptions.data = { orgName, orgCode, statusCd, defaultInd }
          this.orgOptions.callbacks = [null, this.editUserOrgRel]
        }).catch(err => {
          this.$tsVue.error('获取数据失败！')
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },

    // 分配所属机构
    assignUserOrgRel(data, done) {
      const { orgName, orgCode, statusCd, defaultInd } = data
      const userNum = this.$route.query.userNum || this.data.userNum
      const params = {
        userNum,
        orgName,
        orgCode,
        statusCd,
        defaultInd
      }
      this.$request({
        url: this.$api.saveUserAndOrgRel,
        data: params
      }).then((response) => {
        this.$tsVue.success('操作成功！', () => {})
        done()
        this.$refs['orgRelTable'].refreshDataSource()
      }).catch(err => {
        done()
        console.log('saveUserAndOrgRel error!!!')
      })
    },

    // 移除所属机构
    deleteUser() {
      this.selectData = this.$refs['orgRelTable'].getRadioData()
      if (this.selectData) {
        this.$tsVue.confirm('您确认要删除该记录吗？', () => {
          const { id } = this.selectData
          this.$request({
            url: this.$api.deleteUserAndOrgRel,
            data: { id }
          }).then(res => {
            this.$tsVue.success('删除成功！', () => {})
            this.$refs['orgRelTable'].refreshDataSource()
          })
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },

    // 修改关联状态和默认
    editUserOrgRel(data, done) {
      const { orgName, orgCode, statusCd, defaultInd } = data
      const params = Object.assign(this.selectData, data)
      this.$request({
        url: this.$api.saveUserAndOrgRel,
        data: params
      }).then(res => {
        this.$tsVue.success('修改成功！', () => {})
        done()
        this.$refs['orgRelTable'].refreshDataSource()
      })
    }

  }
}
</script>

