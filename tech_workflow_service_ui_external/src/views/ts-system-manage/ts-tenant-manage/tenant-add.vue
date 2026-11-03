<template>
  <div>
    <el-tabs tab-position="left"  @tab-click="tabClick">
      <el-tab-pane label="租户新增修改" name="0">
        <el-form
            ref="tenantForm"
            :rules="tenantRules"
            :model="tenantForm"
            label-width="120px"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item label="租户名称" prop="tenantName">
                <el-input v-model="tenantForm.tenantName" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
<!--              <el-form-item label="社会信用代码" prop="orgCode">-->
<!--                <el-input v-model="tenantForm.orgCode"/>-->
<!--              </el-form-item>-->
              <el-form-item label="手机号码" prop="mobile">
                <el-input v-model="tenantForm.mobile" clearable/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="管理员名称" prop="managerName">
                <el-input v-model="tenantForm.managerName" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="登录名" prop="loginName">
                <el-input v-model="tenantForm.loginName" clearable/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="密码" prop="password">
                <el-input v-model="tenantForm.password" type="password" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="确认密码" prop="repassword">
                <el-input v-model="tenantForm.repassword" type="password" clearable/>
              </el-form-item>
            </el-col>
          </el-row>
<!--          <el-row>-->
<!--            <el-col :span="12">-->
<!--              <el-form-item label="手机号码" prop="mobile">-->
<!--                <el-input v-model="tenantForm.mobile"/>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="12"/>-->
<!--          </el-row>-->
        </el-form>
        <div style="text-align: center;margin-top: 10px">
          <el-button type="primary" icon="el-icon-circle-check" size="small" @click="effect">生效</el-button>
          <el-button type="primary" icon="el-icon-circle-check" size="small" @click="saveTenant">保存</el-button>
          <el-button type="primary" icon="el-icon-back" size="small" @click="back">返回</el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="分配菜单" name="1">
        <ts-multi-trees
            ref="mTrees"
            v-model="menuCodes"
            :options="treesOptions"></ts-multi-trees>
        <div style="text-align: center;margin-top: 10px">
          <el-button type="primary" icon="el-icon-circle-check" size="mini" @click="saveMenus">确认</el-button>
          <el-button type="primary" icon="el-icon-back" size="mini" @click="back">返回</el-button>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'TenantAdd',
  components: {},
  props: {
    params: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      isFirst: false,
      tenantForm: {
        tenantName: '',
        orgCode: '',
        managerName: '',
        loginName: '',
        mobile: '',
        password: '',
        repassword: ''
      },
      tenantRules: {
        tenantName: [{ required: true, message: '请输入租户名称', trigger: 'blur' }],
        orgCode: [
          { required: true, message: '请输入社会信用代码' }
          // { validator: this.$tsVue.tansunFormValidate('creditCode'), trigger: 'blur' }
        ],

        managerName: [{ required: true, message: '请输入管理员名称', trigger: 'blur' }],
        loginName: [{ required: true, message: '请输入登录名', trigger: 'blur' }],
        mobile: [
          // { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: this.$tsVue.tansunFormValidate('mobile'), trigger: 'blur' }
        ],
        password: [{ validator: this.$tsVue.tansunFormValidate('password'), trigger: 'blur' }],
        repassword: [
          { validator: this.$tsVue.tansunFormValidate('password'), trigger: 'blur' },
          {
            message: '两次输入密码不一致', validator: this.$tsVue.tansunFormValidate('equals', (callback) => {
              callback(this.tenantForm.password)
            }), trigger: 'blur'
          }
        ]
      },

      menuCodes: '',
      treesOptions: {
        height:500,
        visibleInit: false,
        nodeKey: 'id',
        apiName: this.$api.getTenantMenus, // 接口 传url
        params: {},
        checkbox: true, // 节点是否显示checkbox
        checkNode: false, // checkbox为true时使用 非必填，选中节点是否选中文本框
        checkedKeys: [], // 有初始值需要数据反显时，每条数据的key值，返回的数据需要唯一标识字段key
        callback: this.treeCallback
      },

      defaultProps: {
        children: 'children',
        label: 'label'
      },
      roleMenus: [], // 角色菜单list
      roleMenuTree: [], // 角色菜单树
      defaultExpandedKeys: [], // 默认展开的节点
      defaultCheckedKeys: []// 默认选择节点
    }
  },
  computed: {
    ...mapState({
      'userInfo': state => state.user.userInfo
    })
  },
  mounted() {
    let obj = {}
    this.params.type === 'add' ? this.isFirst = true : this.isFirst = false
    if (this.params.type === 'modify') {
      this.getTenant()
      obj = {
        tenantId: this.params.tenantId,
        // roleCode: '',
        clntendId: ''
      }
    }
    this.getTenantMenus(obj)
  },
  methods: {
    tabClick(e){
      if(e.name == '1'){
        let obj = {
          tenantId: this.tenantForm.tenantId,
          clntendId: ''
        }
        this.getTenantMenus(obj)
      }
    },
    getTenant() {
      if (!this.params.id) return

      this.$request({
        url: this.$api.getTenant,
        data: {
          id: this.params.id,
          tenantId: this.params.tenantId
        }
      }).then((response) => {
        this.tenantForm = response.data
        // 将密码置为空
        this.tenantForm.password = ''
        this.tenantForm.repassword = ''
      })
    },
    getTenantMenus(obj) {
      this.treesOptions.params = obj
      this.$refs.mTrees.updateTree()
    },
    treeCallback(data) {
      this.roleMenus = JSON.parse(JSON.stringify(data))
      const newArray = this.filterParentNode(this.roleMenus)
      const defaultArray = []
      for (let i = 0; i < newArray.length; i++) {
        if (newArray[i].checked) {
          defaultArray.push(newArray[i].id)
        }
      }

      this.$refs.mTrees.setCheckedKeys(defaultArray)
    },
    effect() {
      if (this.tenantForm.password !== this.tenantForm.repassword) {
        this.$message.error('两次密码输入不一致，请重新输入！')
        return
      }
      this.$refs.tenantForm.validate(valid => {
        if (valid) {
          delete this.tenantForm.createTime
          delete this.tenantForm.updateTime

          this.$request({
            url: this.$api.effectTenant,
            data: this.tenantForm
          }).then((response) => {
            this.setAddData(response)
            this.$message.success(response.description)
          })
        }
      })
    },
    saveTenant() {
      if (this.tenantForm.password !== this.tenantForm.repassword) {
        this.$message.error('两次密码输入不一致，请重新输入！')
        return
      }

      if(this.isFirst && this.tenantForm.password == ''){
        //新增时 密码没填时默认密码
        this.tenantForm.password = ''
        this.tenantForm.repassword = ''
      }

      this.$refs.tenantForm.validate(valid => {
        if (valid) {
          delete this.tenantForm.createTime
          delete this.tenantForm.updateTime

          this.$request({
            url: this.$api.saveTenant,
            data: this.tenantForm
          }).then((response) => {
            this.setAddData(response)
            this.$message.success(response.description)
          })
        }
      })
    },
    setAddData(response) {
      if (this.isFirst) {
        this.isFirst = false
        this.tenantForm = response.data.tenantMsg
        this.params.id = this.tenantForm.id
        this.params.tenantId = this.tenantForm.tenantId
        // 将密码置为空
        this.tenantForm.password = ''
        this.tenantForm.repassword = ''
      } else {
        this.getTenant()
      }
    },
    saveMenus() {
      if (!this.tenantForm.tenantId || this.tenantForm.tenantId === '') {
        this.$message.warning('请先保存租户信息')
        return
      }

      this.$request({
        url: this.$api.saveTenantMenus,
        data: Object.assign(this.tenantForm, { menuCodes: this.menuCodes })
      }).then((response) => {
        this.$message.success(response.description)
      })
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
    },
    back() {
      this.$emit('back')
    }
  }
}
</script>

<style scoped>

</style>
