<template>
  <div>
    <el-form ref="searchForm" v-model="searchForm" class="search-container" :inline="true" size="small">
      <el-form-item prop="tenantNameLike" label="租户名称">
        <el-input v-model="searchForm.tenantNameLike" placeholder="请输入租户名称"/>
      </el-form-item>
<!--      <el-form-item prop="orgCode" label="社会信用代码">-->
<!--        <el-input v-model="searchForm.orgCode" placeholder="请输入社会信用代码"/>-->
<!--      </el-form-item>-->
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button type="primary" icon="el-icon-refresh" @click="resetSearchFrm">重置</el-button>
    </el-form>

    <el-button-group class="handle-container">
      <el-button type="primary" icon="el-icon-plus" @click="addTenant">新增</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="modifyTenant">修改</el-button>
      <el-button type="primary" icon="el-icon-delete" @click="deleteTenant">删除</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="maintain">查看</el-button>
      <el-button type="primary" icon="el-icon-close" @click="changeValid">失效</el-button>
    </el-button-group>

    <ts-multi-table ref="tenantTable" :query="searchForm" :options="tableOptions">
      <el-table-column prop="tenantId" label="租户ID"></el-table-column>
      <el-table-column prop="tenantName" label="租户名称"></el-table-column>
<!--      <el-table-column prop="orgCode" label="社会信用代码" show-overflow-tooltip></el-table-column>-->
      <el-table-column prop="statusCd" label="状态">
        <template slot-scope="scope">
          {{scope.row.statusCd | tsConvert('StCd')}}
        </template>
      </el-table-column>
    </ts-multi-table>
  </div>
</template>

<script>

export default {
  name: 'TenantList',
  components: {},
  data() {
    return {
      selectData: {},
      // 搜索
      searchForm: {
        orgCode: '',
        tenantNameLike: ''
      },
      // 表格属性设置
      tableOptions: {
        type: 'radio',
        apiName: this.$api.queryTenant,
        paramsOrdata: 'data'// 必填 ‘params’ 走token ‘data’ 走 sesson
      }
    }
  },
  created() {

  },
  methods: {
    // 用户搜索
    search() {
      this.$refs['tenantTable'].serchHandel()
    },
    resetSearchFrm() {
      this.searchForm = {}
    },
    getTenantList() {
      // 查询
      this.searchForm = {}
      this.$refs['tenantTable'].refreshDataSource()
    },
    addTenant() {
      // 新增
      this.$emit('toAddView', {
        type: 'add'
      })
    },
    modifyTenant() {
      // 修改
      this.selectData = this.$refs['tenantTable'].getRadioData()
      if (this.selectData) {
        this.$emit('toAddView', {
          id: this.selectData.id,
          tenantId: this.selectData.tenantId,
          type: 'modify'
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },
    deleteTenant() {
      // 删除
      this.selectData = this.$refs['tenantTable'].getRadioData()
      if (this.selectData) {
        if (this.selectData.statusCd !== '1') {
          this.$confirm('将会删除该记录，确认删除？', '提示').then(() => {
            this.$request({
              url: this.$api.deleteTenant,
              data: {
                id: this.selectData.id,
                statusCd: this.selectData.statusCd
              }
            }).then((response) => {
              this.$message.success(response.description)
              this.getTenantList()
            })
          }).catch(() => {})
        } else {
          this.$alert('不能删除生效数据！')
        }
      } else {
        this.$alert('请选中一条记录')
      }
    },
    changeValid() {
      // 生效 失效
      this.selectData = this.$refs['tenantTable'].getRadioData()
      if (this.selectData) {
        if (this.selectData.statusCd === '1') {
          this.$confirm('确定失效该记录吗?', '提示').then(() => {
            this.$request({
              url: this.$api.invalidTenant,
              data: { id: this.selectData.id }
            }).then((response) => {
              this.$message.success(response.description)
              this.getTenantList()
            })
          }).catch(() => {
          })
        } else {
          this.$alert('只有生效的数据才能设置失效!')
        }
      } else {
        this.$alert('请选中一条记录')
      }
    },
    maintain() {
      this.selectData = this.$refs['tenantTable'].getRadioData()
      if (this.selectData) {
        this.$emit('maintain', {
          id: this.selectData.id,
          tenantId: this.selectData.tenantId
        })
      } else {
        this.$alert('请选中一条记录')
      }
    }
  }
}
</script>

<style scoped>

</style>
