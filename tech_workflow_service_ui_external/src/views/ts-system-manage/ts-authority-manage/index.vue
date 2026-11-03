<!--
 * @Author: chenhuocan
 * @Date: 2020-03-30 18:19:20
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-07-22 10:31:32
 * @Description: 权限管理
 -->
<template>
<div class="page-container">
    <el-form ref="paramForm" :inline="true" :model="formInline" class="search-container" size="small">
        <el-form-item label="数据权限模式">
            <ts-multi-select v-model="formInline.modelCode" @change="modelChange" :params="modelParams" placeholder="请选择数据权限类型"></ts-multi-select>
        </el-form-item>
        <el-form-item label="用户">
            <ts-multi-select v-model="formInline.userNum" @change="userChange" :params="usermgParams" placeholder="请选择用户"></ts-multi-select>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="searchClick">查询</el-button>
        <el-button type="primary" icon="el-icon-refresh" @click="refreshClick">重置</el-button>
        <el-button type="primary" :icon="downUpIcon" @click="downUpClick" />
    </el-form>
    <el-form v-if="downUpBoolean" ref="paramForm" :inline="true" :model="formInline" class="search-container" size="small">
        <el-form-item label="角色">
            <ts-multi-select v-model="formInline.roleCode" @change="roleChange" :params="roleParams" placeholder="请选择角色"></ts-multi-select>
        </el-form-item>
        <el-form-item label="场景">
            <ts-multi-select v-model="formInline.bizScene" placeholder="请选择场景" :params="params"></ts-multi-select>
        </el-form-item>
        <el-form-item label="部门" prop="deptName">
            <ts-multi-select v-model="formInline.deptCode" @change="deptChange" :params="deptParams" placeholder="请选择部门"></ts-multi-select>
        </el-form-item>
        <el-form-item label="机构" prop="orgName">
            <ts-multi-select v-model="formInline.orgCode" @change="orgChange" :params="orgParams" placeholder="请选择机构"></ts-multi-select>
        </el-form-item>
    </el-form>
    <el-button-group class="handle-container">
        <el-button type="primary" icon="el-icon-plus" @click="addClick"> 新增</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="modify">修改</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="deleteClick">删除</el-button>
        <el-button type="primary" icon="el-icon-document-copy" @click="copyClick">复制</el-button>
    </el-button-group>

    <ts-multi-table @row-click="selectClick" ref="paramListTable" :query="formInline" :options="tableOptions">
        <el-table-column prop="clntendNm" label="客户端">
            <template v-slot="scope">
                <span>{{ scope.row.clntendNm ?  scope.row.clntendNm : '-' }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="loginName" label="用户">
            <template v-slot="scope">
                <span>{{ scope.row.loginName ?  scope.row.loginName : '-' }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="bizScene" label="场景">
            <template slot-scope="scope">
                {{scope.row.bizScene | tsConvert('BizSceneType')}}
            </template>
        </el-table-column>
        <el-table-column prop="eventName" label="事件"></el-table-column>
        <el-table-column prop="roleName" label="角色">
            <template v-slot="scope">
                <span>{{ scope.row.roleName  ? scope.row.roleName : '-' }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="orgName" label="机构">
            <template v-slot="scope">
                <span>{{ scope.row.orgName ? scope.row.orgName  : '-' }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="deptName" label="部门">
            <template v-slot="scope">
                <span>{{ scope.row.deptName ? scope.row.deptName : '-' }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="modelName" label="数据权限模式"></el-table-column>
    </ts-multi-table>

    <el-dialog width="40%" title="基本信息" :visible.sync="dialogFormVisible">
        <el-form ref="ruleForm" label-width="100px" :rules="rules"  :model="formData">
            <el-form-item label="数据权限模式" prop="modelCode">
                <ts-multi-select v-model="formData.modelCode" @change="modelChange" :params="modelParams" placeholder="请选择数据权限类型"></ts-multi-select>
            </el-form-item>
            <el-form-item label="用户">
                <ts-multi-select v-model="formData.userNum" @change="userChange" :params="usermgParams" placeholder="请选择用户"></ts-multi-select>
            </el-form-item>
            <el-form-item label="角色">
                <ts-multi-select v-model="formData.roleCode" @change="roleChange" :params="roleParams" placeholder="请选择角色"></ts-multi-select>
            </el-form-item>
            <el-form-item label="场景">
                <ts-multi-select v-model="formData.bizScene" placeholder="请选择场景" :params="params"></ts-multi-select>
            </el-form-item>
            <el-form-item label="部门" prop="deptName">
                <ts-multi-select v-model="formData.deptCode" @change="deptChange" :params="deptParams" placeholder="请选择部门"></ts-multi-select>
            </el-form-item>
            <el-form-item label="机构" prop="orgName">
                <ts-multi-select v-model="formData.orgCode" @change="orgChange" :params="orgParams" placeholder="请选择机构"></ts-multi-select>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="cancelClick">取 消</el-button>
            <el-button type="primary" @click="confirmFormClick">确 定</el-button>
        </div>
    </el-dialog>
</div>
</template>

<script>
export default {
  name: 'Dataauthquery',
  components: {},
  data() {
    return {
      params: {
        key: 'BizSceneType',
        width: '100%',
        size: 'small'
      },
      modelParams: {
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        paramMode: 'data', // 非必填 传递参数方式 data|params 默认data
        pagination: true, // 非必填 是否分页
        selectName: '', // 分页情况下 如果有赋默认值时需要手动配置默认选中的name 多选时默认用 , 分割
        remote: true, // 非必填 是否通过远程搜索
        header: ['数据权限模式'], // 表格的头部
        body: ['modelName'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'modelName', // 下拉框显示内容，根据需要修改字段名称
        value: 'modelCode', // 下拉框对应文本的值，根据需要修改字段名称
        alias: 'modelName', // 查询条件的别名
        api: this.$api.dataauthModelSelector, // 接口
        width: '100%',
        size: 'small'
      },
      usermgParams: {
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        paramMode: 'data', // 非必填 传递参数方式 data|params 默认data
        pagination: true, // 非必填 是否分页
        selectName: '', // 分页情况下 如果有赋默认值时需要手动配置默认选中的name 多选时默认用 , 分割
        remote: true, // 非必填 是否通过远程搜索
        header: ['用户名称', '用户编号'], // 表格的头部
        body: ['loginName', 'userNum'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'loginName', // 下拉框显示内容，根据需要修改字段名称
        value: 'userNum', // 下拉框对应文本的值，根据需要修改字段名称
        alias: 'loginName', // 查询条件的别名
        api: this.$api.usermgSelector, // 接口
        width: '100%',
        size: 'small'
      },
      roleParams: {
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        paramMode: 'data', // 非必填 传递参数方式 data|params 默认data
        pagination: true, // 非必填 是否分页
        selectName: '', // 分页情况下 如果有赋默认值时需要手动配置默认选中的name 多选时默认用 , 分割
        remote: true, // 非必填 是否通过远程搜索
        header: ['角色名称', '角色编号'], // 表格的头部
        body: ['roleName', 'roleCode'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'roleName', // 下拉框显示内容，根据需要修改字段名称
        value: 'roleCode', // 下拉框对应文本的值，根据需要修改字段名称
        alias: 'roleName', // 查询条件的别名
        api: this.$api.roleSelector, // 接口
        width: '100%',
        size: 'small'
      },
      deptParams: {
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        paramMode: 'data', // 非必填 传递参数方式 data|params 默认data
        pagination: true, // 非必填 是否分页
        selectName: '', // 分页情况下 如果有赋默认值时需要手动配置默认选中的name 多选时默认用 , 分割
        remote: true, // 非必填 是否通过远程搜索
        header: ['部门名称', '部门编号'], // 表格的头部
        body: ['deptName', 'deptCode'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'deptName', // 下拉框显示内容，根据需要修改字段名称
        value: 'deptCode', // 下拉框对应文本的值，根据需要修改字段名称
        alias: 'deptName', // 查询条件的别名
        api: this.$api.departmentSelector, // 接口
        width: '100%',
        size: 'small'
      },
      orgParams: {
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        paramMode: 'data', // 非必填 传递参数方式 data|params 默认data
        pagination: true, // 非必填 是否分页
        selectName: '', // 分页情况下 如果有赋默认值时需要手动配置默认选中的name 多选时默认用 , 分割
        remote: true, // 非必填 是否通过远程搜索
        header: ['机构名称', '机构编号'], // 表格的头部
        body: ['orgName', 'orgCode'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'orgName', // 下拉框显示内容，根据需要修改字段名称
        value: 'orgCode', // 下拉框对应文本的值，根据需要修改字段名称
        alias: 'orgName', // 查询条件的别名
        api: this.$api.orgSelector, // 接口
        width: '100%',
        size: 'small'
      },
      tableOptions: {
        type: 'radio',
        apiName: this.$api.dataauthQuery,
        paramsOrdata: 'data'
      },
      selectData: {},
      dialogFormVisible: false,
      selected: false, // 选中某一行
      downUpBoolean: false,
      downUpIcon: 'el-icon-arrow-down',
      // 数据权限类型
      modelSelectorList: [{
        modelCode: '',
        modelName: ''
      }],
      // 机构选择框
      orgSelectorList: [{
        orgName: '',
        orgCode: ''
      }],
      // 部门选择框
      deptSelectorList: [{
        deptName: '',
        parentDeptCode: ''
      }],
      // 角色选择框
      roleSelectorList: [{
        roleName: '',
        roleCode: ''
      }],
      // 用户选择框
      usermgSelectorList: [{
        loginName: '',
        userNum: ''
      }],
      // 场景
      options: [{
        value: 'CprsvEnqr',
        label: '综合查询'
      }, {
        value: 'tenantManage',
        label: '租户管理'
      }, {
        value: 'Circle',
        label: '圈链场景'
      }, {
        value: 'RcvbTrs',
        label: '应收账款转让场景'
      }, {
        value: 'Approve',
        label: '审批场景'
      }, {
        value: 'Network',
        label: '项目方案场景'
      },
      {
        value: 'Cst',
        label: '客户管理场景'
      }
      ],
      // 查询下的各个的值
      formInline: {
        modelName: '',
        modelCode: '',
        loginName: '',
        userNum: '',
        roleName: '',
        roleCode: '',
        deptName: '',
        deptCode: '',
        orgName: '',
        orgCode: '',
        bizScene: ''
      },
      formData: {
        modelName: '',
        modelCode: '',
        loginName: '',
        userNum: '',
        roleName: '',
        roleCode: '',
        deptName: '',
        deptCode: '',
        orgName: '请选择机构',
        orgCode: '',
        bizScene: ''
      },
      rules: {
        modelCode: [
          { required: true, message: '请输入数据权限模式', trigger: 'blur' }
        ]
      }
    }
  },
  created() {},
  methods: {
    searchClick() {
      // 查询
      this.selected = false
      const paramData = Object.assign({}, this.formInline)

      if (paramData.modelName === '请选择数据权限模式') {
        paramData.modelName = ''
      }
      if (paramData.loginName === '请选择用户') {
        paramData.loginName = ''
      }
      if (paramData.roleName === '请选择角色') {
        paramData.roleName = ''
      }
      if (paramData.orgName === '请选择机构') {
        paramData.orgName = ''
      }
      if (paramData.deptName === '请选择部门') {
        paramData.deptName = ''
      }
      this.formInline = paramData
      this.$refs['paramListTable'].serchHandel()
    },
    refreshClick() {
      // 重置
      this.formInline = {}
    },
    downUpClick() {
      this.downUpBoolean = !this.downUpBoolean
      this.downUpIcon = this.downUpBoolean ? 'el-icon-arrow-up' : 'el-icon-arrow-down'
    },
    confirmFormClick() {
      // 提交
      const self = this

      if (self.formData.modelName === '请选择数据权限模式') {
        self.$message.warning('请选择数据权限模式')
        self.formData.modelName = ''
        return
      }
      if (self.formData.loginName === '请选择用户') {
        self.formData.loginName = ''
      }
      if (self.formData.roleName === '请选择角色') {
        self.formData.roleName = ''
      }
      if (self.formData.orgName === '请选择机构') {
        self.formData.orgName = ''
      }
      if (self.formData.deptName === '请选择部门') {
        self.formData.deptName = ''
      }

      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.$request({
            url: self.$api.dataauthSave,
            data: Object.assign({}, self.formData)
          }).then((response) => {
            self.$message.success(response.description)
            self.dialogFormVisible = false
            self.resetSelectName()
            self.$refs['paramListTable'].refreshDataSource()
          })
        } else {
          return false
        }
      })
    },
    cancelClick() {
      this.resetSelectName()
      this.dialogFormVisible = false
    },
    resetSelectName() {
      this.modelParams.selectName = ''
      this.usermgParams.selectName = ''
      this.roleParams.selectName = ''
      this.deptParams.selectName = ''
      this.orgParams.selectName = ''
    },
    addClick() {
      this.dialogFormVisible = true
      this.formData = {}
    },
    modify() {
      if (this.selected) {
        this.modelParams.selectName = this.selectData.modelName
        this.usermgParams.selectName = this.selectData.loginName
        this.roleParams.selectName = this.selectData.roleName
        this.deptParams.selectName = this.selectData.deptName
        this.orgParams.selectName = this.selectData.orgName

        this.dialogFormVisible = true
      } else {
        this.$alert('请选择有效数据', '信息', {
          confirmButtonText: '确定'
        })
      }
    },
    deleteClick() {
      const self = this
      if (self.selected) {
        self.$confirm('你确定删除这条记录吗？', '信息', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(() => {
          const param = {
            id: self.formData.id,
            gridRowSign: self.formData.gridRowSign
          }
          debugger
          this.$request({
            url: self.$api.dataauthDelete,
            data: param
          }).then((response) => {
            self.$message.success(response.description)
            this.$refs['paramListTable'].refreshDataSource()
          })
        }).catch(() => {})
      } else {
        this.$alert('请选中一条记录', '信息', {
          confirmButtonText: '确定'
        })
      }
    },
    copyClick() {
      // 复制
      if (this.selected) {
        this.formData = {}

        this.formData.modelName = this.selectData.modelName
        this.formData.modelCode = this.selectData.modelCode
        this.formData.loginName = this.selectData.loginName
        this.formData.userNum = this.selectData.userNum
        this.formData.roleName = this.selectData.roleName
        this.formData.roleCode = this.selectData.roleCode
        this.formData.deptName = this.selectData.deptName
        this.formData.deptCode = this.selectData.deptCode
        this.formData.orgName = this.selectData.orgName
        this.formData.orgCode = this.selectData.orgCode
        this.formData.bizScene = this.selectData.bizScene

        this.modelParams.selectName = this.selectData.modelName
        this.usermgParams.selectName = this.selectData.loginName
        this.roleParams.selectName = this.selectData.roleName
        this.deptParams.selectName = this.selectData.deptName
        this.orgParams.selectName = this.selectData.orgName
        this.dialogFormVisible = true
      } else {
        this.$alert('请选择有效数据', '信息', {
          confirmButtonText: '确定'
        })
      }
    },
    selectClick() {
      const result = this.$refs['paramListTable'].getRadioData()
      console.log('selection:', result)
      if (result) {
        this.selected = true
        this.selectData = result
        this.formData = this.selectData
      }
    },
    modelChange(value, selects) {
      if (selects.length > 0) {
        if (this.dialogFormVisible) {
          this.formData.modelName = selects[0].modelName
          this.formData.modelCode = selects[0].modelCode
        } else {
          this.formInline.modelName = selects[0].modelName
          this.formInline.modelCode = selects[0].modelCode
        }
      }
    },
    userChange(value, selects) {
      if (selects.length > 0) {
        if (this.dialogFormVisible) {
          this.formData.loginName = selects[0].loginName
          this.formData.userNum = selects[0].userNum
        } else {
          this.formInline.loginName = selects[0].loginName
          this.formInline.userNum = selects[0].userNum
        }
      }
    },
    roleChange(value, selects) {
      if (selects.length > 0) {
        if (this.dialogFormVisible) {
          this.formData.roleName = selects[0].roleName
          this.formData.roleCode = selects[0].roleCode
        } else {
          this.formInline.roleName = selects[0].roleName
          this.formInline.roleCode = selects[0].roleCode
        }
      }
    },
    deptChange(value, selects) {
      if (selects.length > 0) {
        if (this.dialogFormVisible) {
          this.formData.deptName = selects[0].deptName
          this.formData.deptCode = selects[0].deptCode
        } else {
          this.formInline.deptName = selects[0].deptName
          this.formInline.deptCode = selects[0].deptCode
        }
      }
    },
    orgChange(value, selects) {
      if (selects.length > 0) {
        if (this.dialogFormVisible) {
          this.formData.orgName = selects[0].orgName
          this.formData.orgCode = selects[0].orgCode
        } else {
          this.formInline.orgName = selects[0].orgName
          this.formInline.orgCode = selects[0].orgCode
        }
      }
    }
  }
}
</script>

