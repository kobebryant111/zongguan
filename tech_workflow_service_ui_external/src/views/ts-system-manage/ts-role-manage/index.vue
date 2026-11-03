<template>
  <div class="app-container">
    <el-row :gutter="25">
      <el-col :span="7">
        <div class="ts-pane-box">
          <el-tabs type="border-card" @tab-click="leftTabClick">
            <div class="handle-bar">
              <i class="iconfont icontianjiawenjian" @click="addRole" title="新增"></i>
              <i class="iconfont-x icontianjiaziyemian" @click="addChildRole" title="新增子角色"></i>
              <i class="iconfont iconshanchu" @click="deleteRole" title="删除"></i>
            </div>
            <el-tab-pane label="后台角色">
              <ts-multi-trees
                ref="mTrees1"
                :options="treesOptions1"
                @node-click="handleNodeClick">
              </ts-multi-trees>
            </el-tab-pane>
            <el-tab-pane label="前台角色">
              <ts-multi-trees
                ref="mTrees2"
                :options="treesOptions2"
                @node-click="handleNodeClick">
              </ts-multi-trees>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
      <el-col :span="17">
        <div class="ts-tabs-box">
          <el-tabs @tab-click="rightTabClick">
            <el-tab-pane label="角色信息">
              <el-form class="single-col-form" ref="roleData" :label-width="formLabelWidth" :model="roleData" :rules="roleRules">
                <el-form-item label="上级角色" prop="parentRoleName">
                  <el-input v-model="roleData.parentRoleName" autocomplete="off" :disabled="true"/>
                </el-form-item>
                <el-form-item label="名称" prop="roleName">
                  <el-input v-model="roleData.roleName" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="编号" prop="roleCode">
                  <el-input v-model="roleData.roleCode" autocomplete="off" :disabled="true"/>
                </el-form-item>
                <el-form-item label="状态" prop="statusCd">
                  <ts-multi-radio v-model="roleData.statusCd" :params="radioparams1"></ts-multi-radio>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                  <el-input v-model="roleData.sort" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="描述" prop="remark">
                  <el-input v-model="roleData.remark" autocomplete="off"/>
                </el-form-item>
              </el-form>
              <div class="button-box">
                <el-button v-if="roleData.id" type="primary" icon="iconfont iconqueren" @click="saveRole">确认</el-button>
              </div>
            </el-tab-pane>
            <el-tab-pane label="用户">
              <el-form ref="searchForm" v-model="searchForm" :inline="true" class="search-container" size="small">
                <el-form-item prop="userLoginName" label="登录名">
                  <el-input v-model="searchForm.userLoginName" placeholder="请输入登录名"/>
                </el-form-item>
                <el-form-item prop="userRealname" label="姓名">
                  <el-input v-model="searchForm.userRealname" placeholder="请输入姓名"/>
                </el-form-item>
                <el-button type="primary" size="small" icon="el-icon-search" @click="search">查询</el-button>
                <el-button type="primary" size="small" icon="el-icon-refresh" @click="resetSearchFrm">重置</el-button>
              </el-form>
              <ts-multi-table ref="roleTable" :query="searchForm" :options="tableOptions">
                <el-table-column prop="userNum" label="用户编号"></el-table-column>
                <el-table-column
                    prop="userLoginName"
                    label="登录名称"
                    show-overflow-tooltip></el-table-column>
                <el-table-column prop="roleName" label="姓名" show-overflow-tooltip></el-table-column>
                <el-table-column prop="userSexCd" label="性别" show-overflow-tooltip>
                  <template slot-scope="scope">
                    {{scope.row.userSexCd | tsConvert('Gnd')}}
                  </template>
                </el-table-column>
                <el-table-column prop="userTelephone" label="电话" show-overflow-tooltip></el-table-column>
                <el-table-column prop="statusCd" label="状态">
                  <template slot-scope="scope">
                    {{scope.row.statusCd | tsConvert('StCd')}}
                  </template>
                </el-table-column>
              </ts-multi-table>
            </el-tab-pane>
            <el-tab-pane label="菜单分配">
              <ts-multi-trees
                  ref="menuTree"
                  v-model="menuCodes"
                  :options="treesOptions"></ts-multi-trees>
              <div class="button-box">
                <el-button type="primary" icon="iconfont iconqueren" @click="saveRoleMenu">确认</el-button>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>

    <ts-multi-dialog
      :visible.sync="dialogVisible"
      :options="dialogOptions">
    </ts-multi-dialog>
  </div>
</template>

<script>
import RoleForm from './form'

export default {
  name: 'TsRoleManage',
  components: {},
  data() {
    return {
      radioparams1: { key: 'StCd', group: '1' },
      formLabelWidth: '120px',
      // 角色树配置
      treesOptions1: {
        height:500,
        apiName: this.$api.roleTree, // 接口 传url
        params: { clntendId: '10002' },
        nodeKey: 'key',
        method: 'post', // 非必填 请求方式 默认post
        highlight: true, // 是否高亮当前选中行
        callback: this.roleTreeCallback
      },
      treesOptions2: {
        height:500,
        apiName: this.$api.roleTree, // 接口 传url
        params: { clntendId: '10001' },
        nodeKey: 'key',
        method: 'post', // 非必填 请求方式 默认post
        highlight: true, // 是否高亮当前选中行
        callback: this.roleTreeCallback
      },
      roleTreeIndex: '0',
      hasChild: false,
      selectData: {}, // 选中的
      roleData: {},
      clntendId: '10002', // 客户端(应用)编号 10002后台 , 10001前台

      dialogVisible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      dialogOptions: {
        title: '新增角色', // [必填]
        width: '850px', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        resource: RoleForm, // [必填] 在弹框中展示的组件页面
        data: {
          parentRoleCode: '',
          parentRoleName: ' ', // 上级角色
          roleName: '', // 名称
          statusCd: '', // 状态(0=无效，1=有效)
          sort: '', // 排序
          remark: '', // 描述
          clntendId: ''
        },
        beforeClose: (data, done) => { done() }, // [可不填] 关闭右上角弹框前的回调
        callbacks: [this.cancel, this.confirm] // [必填] 若取消按钮没有回调，可写默认值null 如[null, this.submit]
      },
      searchForm: {
        userLoginName: '',
        userRealname: '',
        roleCode: '',
        clntendId: ''
      },
      // 表格属性设置
      tableOptions: {
        apiName: this.$api.roleUser, // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        paramsOrdata: 'data'// 必填 ‘params’ 走token ‘data’ 走 sesson
      },
      //
      roleRules: {
        parentRoleName: [
          { required: true, message: '请输入上级角色', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        roleCode: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        sort: [
          { required: false, message: '请输入排序', trigger: 'blur' },
          { message: '请输入整数格式', pattern: /^[0-9]{1,9}$/ }
        ],
        remark: [
          { required: false, message: '请输入描述', trigger: 'blur' }
        ],
        statusCd: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ]
      },
      menuCodes: '',
      treesOptions: {
        height:450,
        visibleInit: false,
        nodeKey: 'id',
        apiName: this.$api.roleMenu, // 接口 传url
        params: {},
        checkbox: true, // 节点是否显示checkbox
        checkNode: false, // checkbox为true时使用 非必填，选中节点是否选中文本框
        checkedKeys: [], // 有初始值需要数据反显时，每条数据的key值，返回的数据需要唯一标识字段key
        callback: this.treeCallback
      },

      roleMenus: [], // 角色菜单list
      roleMenuTree: [], // 角色菜单树
      defaultExpandedKeys: [], // 默认展开的节点
      defaultCheckedKeys: []// 默认选择节点
    }
  },
  created: function() {

  },
  methods: {
    leftTabClick(data) {
      this.roleTreeIndex = data.index
      switch (data.index) {
        case '0':// 后台角色
          this.clntendId = '10002'
          break
        case '1':// 前台角色
          this.clntendId = '10001'
          break
        default:
          break
      }
      this.getRoleTree()
    },
    rightTabClick(data) {
      switch (data.index) {
        case '0':// 角色信息
          break
        case '1':// 用户
          break
        case '2':// 菜单分配
          break
        default:
          break
      }
    },
    handleNodeClick(data) {
      this.hasChild = (data.children && data.children.length > 0)
      this.$refs.roleData.clearValidate()
      this.selectData = data
      this.getRole()
    },
    // 获取角色树
    getRoleTree() {
      this.roleData = {}
      this.selectData = {}
      switch (this.roleTreeIndex) {
        case '0':// 后台角色
          this.$refs.mTrees1.updateTree()
          break
        case '1':// 前台角色
          this.$refs.mTrees2.updateTree()
          break
        default:
          break
      }
    },
    roleTreeCallback(data) {
    },
    // 添加角色
    addRole() {
      this.dialogVisible = true
      this.dialogOptions.data.parentRoleName = ' '
      this.dialogOptions.data.clntendId = this.clntendId
    },
    // 添加子角色
    addChildRole() {
      if (!this.selectData.id || this.selectData.id === '') return
      this.dialogOptions.data.clntendId = this.clntendId
      this.dialogOptions.data.parentRoleName = this.selectData.name
      this.dialogOptions.data.parentRoleCode = this.selectData.id
      this.dialogVisible = true
    },
    // 删除角色
    deleteRole() {
      if (!this.roleData.id || this.roleData.id === '') return
      if (this.hasChild) {
        this.$alert('请选择叶子节点')
      } else {
        this.$confirm('将会删除角色信息，确认删除？', '提示').then(() => {
          this.$request({
            url: this.$api.roleDelete,
            data: this.roleData
          }).then((response) => {
            this.$message.success('删除成功')
            this.getRoleTree()
          })
        }).catch(() => {
        })
      }
    },
    getRole() {
      this.$request({
        url: this.$api.roleGet,
        data: { roleCode: this.selectData.id }
      }).then((response) => {
        this.roleData = response.data
        this.getRoleMenu()

        this.searchForm.userLoginName = ''
        this.searchForm.userRealname = ''
        this.searchForm.roleCode = this.roleData.roleCode
        this.searchForm.clntendId = this.clntendId

        if (this.$refs['roleTable']) {
          this.searchForm.userLoginName = ''
          this.searchForm.userRealname = ''
          this.$refs['roleTable'].serchHandel()
        }
      })
    },
    // 保存角色信息
    confirm(data, done) {
      this.$request({
        url: this.$api.roleSave,
        data: data
      }).then((response) => {
        this.$message.success(response.description)
        this.dialogVisible = false
        Object.keys(this.dialogOptions.data).forEach(key => (this.dialogOptions.data[key] = ''))
        this.getRoleTree()
        done()
      })
    },
    // 修改角色信息
    saveRole() {
      if (!this.roleData.parentRoleName || this.roleData.parentRoleName === '') this.roleData.parentRoleName = ' '
      this.$refs.roleData.validate((valid) => {
        if (!valid) return
        this.$request({
          url: this.$api.roleSave,
          data: this.roleData
        }).then((response) => {
          this.$message.success(response.description)
          this.roleData = {}
          this.getRoleTree()
        })
      })
    },
    // 获取角色菜单
    getRoleMenu() {
      this.treesOptions.params = {
        clntendId: this.clntendId,
        roleCode: this.roleData.roleCode
      }
      this.$refs.menuTree.updateTree()
    },
    treeCallback(data) {
      this.roleMenus = JSON.parse(JSON.stringify(data))

      this.roleMenuTree = this.$tsVue.tansunArray2Tree(data, { child: 'pId', label: 'name' })

      const newArray = this.filterParentNode(this.roleMenus)
      const defaultArray = []
      for (let i = 0; i < newArray.length; i++) {
        if (newArray[i].checked) {
          defaultArray.push(newArray[i].id)
        }
      }
      this.$refs.menuTree.setCheckedKeys(defaultArray)

      const checkedArray = []
      for (let i = 0; i < this.roleMenus.length; i++) {
        if (this.roleMenus[i].checked) {
          checkedArray.push(this.roleMenus[i].id)
        }
      }
      this.menuCodes = checkedArray.join(',')
    },
    // 保存角色菜单信息
    saveRoleMenu() {
      this.$request({
        url: this.$api.saveRoleMenu,
        data: {
          menuCodes: this.menuCodes,
          roleCode: this.roleData.roleCode
        }
      }).then((response) => {
        this.$message.success(response.description)
      })
    },
    // 用户搜索
    search() {
      this.$refs['roleTable'].serchHandel()
    },
    resetSearchFrm() {
      this.searchForm.userLoginName = ''
      this.searchForm.userRealname = ''
    },
    cancel(data, done) {
      done()
    },
    // 根据id获取对象
    findNodeById(list, id) {
      for (let i = 0; i < list.length; i++) {
        if (id === list[i].id) {
          return list[i]
        }
      }
    },
    // 获取某个节点向上所有父节点
    treeFindPath(tree, func, path = []) {
      if (!tree) return []
      for (const data of tree) {
        // 这里按照你的需求来存放最后返回的内容吧
        path.push(data.id)
        if (func(data)) return path
        if (data.children) {
          const findChildren = this.treeFindPath(data.children, func, path)
          if (findChildren.length) return findChildren
        }
        path.pop()
      }
      return []
    },
    // 筛选出没有子节点的节点
    filterParentNode(array) {
      const arr = []
      for (let i = 0; i < array.length; i++) {
        for (let j = 0; j < array.length; j++) {
          if (array[i].id === array[j].pId) {
            arr.push(array[i])
          }
        }
      }

      return array.filter((index) => {
        return Array.from(new Set(arr)).indexOf(index) < 0
      })
    }
  }
}
</script>
