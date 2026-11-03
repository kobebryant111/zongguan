<template>
    <div>
      <el-table :data="form">
        <el-table-column prop="branchName" label="机构名称" width="180"></el-table-column>
        <el-table-column prop="userName" label="用户名" width="120"></el-table-column>
        <el-table-column label="用户状态" width="120">
          <template slot-scope="scope">
            <el-tag type="success">
              {{ scope.row.userState | userState}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="角色" width="180">
          <template slot-scope="scope">
            <el-tag v-for="role in scope.row.roles" :key="role.roleId">
              {{ role.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作类型" width="120" align="center">
          <template slot-scope="scope">
            <el-tag type="success">
              {{ scope.row.opType | busType}}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
</template>

<script>
export default {
  props: {
    detail: {
      type: Object
    }
  },
  data() {
    return {
      form: []
    }
  },
  mounted() {
    this.form.push(this.detail)
    // this.getDetail()
  },
  methods: {
   
  },

  filters: {
    busType(val) {
      if (val == 1) {
        return '新增'
      } else if (val == 2) {
        return '删除'
      } else if (val == 3) {
        return '修改'
      } else {
        return ''
      }
    },
    userState(val) {
      switch(val) {
        case '0':
          return '正常'
          break
        case '1':
          return '暂时冻结'
          break
        case '2':
          return '用户停用'
          break
        case '3':
          return '永久冻结'
          break
        default:
          return ''
      }
    }
  }
}
</script>

