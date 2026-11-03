<!--
 * @Author: chenhuocan
 * @Date: 2020-03-30 18:19:20
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-21 10:14:46
 * @Description: 菜单管理
 -->
<template>
  <div class="app-container">
    <el-row :gutter="25">
      <el-col :span="7">
        <div class="ts-pane-box">
          <el-tabs type="border-card" @tab-click="leftTabClick">
            <div class="handle-bar">
              <i class="iconfont icontianjiawenjian" @click="addMenu" title="新增"></i>
              <i class="iconfont-x icontianjiaziyemian" @click="addChildMenu" title="新增节点"></i>
              <i class="iconfont iconshanchu" @click="deleteMenu" title="删除"></i>
            </div>
            <el-tab-pane label="后台菜单">
              <ts-multi-trees
                ref="mTrees1"
                :options="treesOptions1"
                @node-click="handleNodeClick">
              </ts-multi-trees>
            </el-tab-pane>
            <el-tab-pane label="前台菜单">
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
          <el-tabs v-model="tabChange" @tab-click="rightTabClick">
            <el-tab-pane label="菜单基本信息" name="0">
              <el-form class="single-col-form" :label-width="formLabelWidth" ref="menuData" :model="menuData" :rules="menuRules">
                <el-form-item label="上级功能" prop="parentMenuName">
                  <el-input v-model="menuData.parentMenuName" autocomplete="off" :disabled="true"/>
                </el-form-item>
                <el-form-item label="名称" prop="menuName">
                  <el-input v-model="menuData.menuName" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="状态" prop="statusCd">
                  <ts-multi-radio v-model="menuData.statusCd" :params="radioparams1"></ts-multi-radio>
                </el-form-item>
                <el-form-item label="资源类型" prop="menuTypeCd">
                  <ts-multi-select
                    v-model="menuData.menuTypeCd"
                    :params="radioparams2"
                    @change="menuTypeChange"></ts-multi-select>
                </el-form-item>
                <el-form-item label="资源url" prop="url">
                  <el-input v-model="menuData.url" autocomplete="off" :disabled="urlDisabled"/>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                  <el-input v-model="menuData.sort" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="描述" prop="remark">
                  <el-input v-model="menuData.remark" autocomplete="off"/>
                </el-form-item>
                <el-form-item v-if="!isChild" label="图标" prop="icon">
                  <li class="secmenupic" @click="showIconSelect">
                    <img v-if="iconPath!==''" class="icon" :src="iconPath">
                  </li>
                </el-form-item>
              </el-form>
              <div class="button-box">
                <el-button v-if="menuData.id" type="primary" icon="iconfont iconqueren" @click="saveMenu">保存</el-button>
              </div>
            </el-tab-pane>
            <el-tab-pane v-if="showEvent" name="1" label="按钮事件">
              <el-form ref="searchForm" v-model="searchForm" :inline="true">
                <el-form-item prop="eventNameLike" label="事件名称">
                  <el-input v-model="searchForm.eventNameLike" placeholder="请输入事件名称"/>
                </el-form-item>
                <el-form-item prop="urlLike" label="事件url">
                  <el-input v-model="searchForm.urlLike" placeholder="请输入事件url"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
                  <el-button type="primary" icon="el-icon-refresh" @click="resetSearchFrm">重置</el-button>
                </el-form-item>
              </el-form>
              <el-button-group class="handle-container">
                <el-button type="primary" icon="el-icon-plus" @click="addEvent">新增</el-button>
                <el-button type="primary" icon="el-icon-delete" @click="deleteEvent">删除</el-button>
              </el-button-group>

              <ts-multi-table
                  ref="eventTable"
                  :query="searchForm"
                  :options="tableOptions">
                <el-table-column prop="eventCode" label="事件编号"></el-table-column>
                <el-table-column prop="eventName" label="事件名称"></el-table-column>
                <el-table-column prop="url" label="事件URL"></el-table-column>
              </ts-multi-table>

            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>

    <ts-multi-dialog
        :visible.sync="dialogVisible"
        :options="dialogOptions">
    </ts-multi-dialog>

    <el-dialog
        width="500px"
        title="图标"
        :visible.sync="innerVisible"
        append-to-body
    >
      <div>
        <li v-for="(i,key) in 9" :key="key" class="secmenupic" style="display:inline-block;" @click="iconSelect(i)">
          <img class="icon" :src="require('@/assets/images/menu/menu_icon0'+i+'.png')">
        </li>
      </div>
      <div>
        <li
            v-for="(i,key) in 9"
            :key="key"
            class="secmenupic"
            style="display:inline-block;"
            @click="iconSelect(10+key)"
        >
          <img class="icon" :src="require('@/assets/images/menu/menu_icon1'+key+'.png')">
        </li>
      </div>
    </el-dialog>
    <event-res-dialog
        :menuCode="menuData.menuCode"
        :visible.sync="eventResVisible"
        @addEventRes="addEventRes"
    />
  </div>
</template>

<script>
import EventResDialog from './event-res-dialog'
import MenuForm from './menu-form'

export default {
  name: 'TsMenuManage',
  components: { EventResDialog },
  data() {
    return {
      tabChange: '0',
      innerVisible: false,
      eventResVisible: false,
      iconPath: '',
      iconData: {
        iconmenu01: 'menu_icon01',
        iconmenu02: 'menu_icon02',
        iconmenu03: 'menu_icon03',
        iconmenu04: 'menu_icon04',
        iconmenu05: 'menu_icon05',
        iconmenu06: 'menu_icon06',
        iconmenu07: 'menu_icon07',
        iconmenu08: 'menu_icon08',
        iconmenu09: 'menu_icon09',
        iconmenu10: 'menu_icon10',
        iconmenu11: 'menu_icon11',
        iconmenu12: 'menu_icon12',
        iconmenu13: 'menu_icon13',
        iconmenu14: 'menu_icon14',
        iconmenu15: 'menu_icon15',
        iconmenu16: 'menu_icon16',
        iconmenu17: 'menu_icon17',
        iconmenu18: 'menu_icon18'
      },
      showEvent: false,
      formLabelWidth: '120px',

      // 菜单树配置
      treesOptions1: {
        height:500,
        apiName: this.$api.menuTree, // 接口 传url
        params: { clntendId: '10002' },
        method: 'post', // 非必填 请求方式 默认post
        highlight: true, // 是否高亮当前选中行
        callback: this.menuTreeCallback
      },
      treesOptions2: {
        height:500,
        apiName: this.$api.menuTree, // 接口 传url
        params: { clntendId: '10001' },
        method: 'post', // 非必填 请求方式 默认post
        highlight: true, // 是否高亮当前选中行
        callback: this.menuTreeCallback
      },
      menuTreeIndex: '0',
      hasChild: false,
      isChild: false, // 是否子节点
      trees: [],

      selectData: {}, // 选中的
      menuData: {},
      clntendId: '10002', // 客户端(应用)编号 10002后台 , 10001前台

      // 表单
      dialogVisible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      dialogOptions: {
        title: '事件维护', // [必填]
        width: '850px', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        resource: MenuForm, // [必填] 在弹框中展示的组件页面
        data: {
          parentMenuName: '', // 父菜单名称
          parentMenuCode: '', // 父菜单编号
          leafFlagCd: '', // 叶子节点标识(非叶子节点0/叶子节点1)
          preMenuTypeCd: '', // ???
          urlDisabled: '', // url是否禁用，目录时禁用
          menuName: '', // 名称
          menuTypeCd: '', // 菜单类型
          statusCd: '', // 状态(0=无效，1=有效)
          sort: '', // 排序
          remark: '', // 描述
          url: '', // url
          icon: '', // 图标
          clntendId: '',
          index: 0
        },
        beforeClose: (data, done) => { done() },
        callbacks: [this.cancel, this.confirm] // [必填] 若取消按钮没有回调，可写默认值null 如[null, this.submit]
      },
      urlDisabled: false,
      radioparams1: { key: 'StCd', group: '1' },
      radioparams2: { key: 'MenuType' },
      menuRules: {
        parentMenuName: [
          { required: true, message: '请输入上级功能', trigger: 'blur' }
        ],
        menuName: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        menuTypeCd: [
          { required: true, message: '请选择资源类型', trigger: 'blur' }
        ],
        statusCd: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ],
        sort: [
          { required: false, message: '请输入排序', trigger: 'blur' },
          { message: '请输入整数格式', pattern: /^[0-9]{1,9}$/ }
        ]
      },
      // 按钮事件列表搜索
      searchForm: {
        eventNameLike: '',
        urlLike: ''
      },
      // 表格属性设置
      tableOptions: {
        type: 'radio',
        apiName: this.$api.eventRelQuery,
        paramsOrdata: 'data', // 必填 ‘params’ 走token ‘data’ 走 sesson
        apiRequiredParams: {
          canEdit: true,
          menuCode: ''
        }
      },
      selectEvent: null
    }
  },
  created() {
  },
  methods: {
    leftTabClick(data) {
      this.menuTreeIndex = data.index
      switch (data.index) {
        case '0':// 后台树
          this.clntendId = '10002'
          break
        case '1':// 前台树
          this.clntendId = '10001'
          break
        default:
          break
      }
      this.getMenuTree()
    },
    rightTabClick(data) {
      switch (data.index) {
        case '0':// 菜单信息
          break
        case '1':// 按钮事件
          this.getBtnEvents()
          break
        default:
          break
      }
    },
    handleNodeClick(data) {
      this.tabChange = '0'
      this.hasChild = (data.children && data.children.length > 0)
      this.isChild = this.treeFindPath(this.trees, data1 => data1.id === data.id).length > 1
      this.$refs.menuData.clearValidate()
      this.selectData = data

      this.getMenu()
    },
    // 获取菜单树
    getMenuTree() {
      switch (this.menuTreeIndex) {
        case '0':// 后台树
          this.$refs.mTrees1.updateTree()
          break
        case '1':// 前台树
          this.$refs.mTrees2.updateTree()
          break
        default:
          break
      }
    },
    menuTreeCallback(data) {
      this.menuData = {}
      this.showEvent = false
      this.trees = this.$tsVue.tansunArray2Tree(data, { child: 'pId', label: 'name' })
    },
    showIconSelect() {
      this.innerVisible = true
    },
    iconSelect(index) {
      if (index <= 9) {
        this.iconPath = require('@/assets/images/menu/menu_icon0' + index + '.png')
        this.dialogOptions.data.icon = 'iconmenu0' + index
      } else {
        this.iconPath = require('@/assets/images/menu/menu_icon' + index + '.png')
        this.dialogOptions.data.icon = 'iconmenu' + index
      }

      this.innerVisible = false
    },
    // 添加菜单树
    addMenu() {
      this.dialogVisible = true
      Object.keys(this.dialogOptions.data).forEach(key => (this.dialogOptions.data[key] = ''))
      this.dialogOptions.data.clntendId = this.clntendId
      this.dialogOptions.data.parentMenuName = ' '
      this.dialogOptions.data.leafFlagCd = '0'
      this.dialogOptions.data.index = 0
    },
    // 添加子节点
    addChildMenu() {
      // 未选节点，不能添加子节点
      if (this.selectData.menuCode === '') return
      // 按钮不能添加子节点
      if (this.menuData.menuTypeCd === 'click') {
        this.$alert('按钮下不能添加节点')
        return
      }
      this.dialogOptions.data.clntendId = this.clntendId
      this.dialogOptions.data.parentMenuName = this.selectData.name
      this.dialogOptions.data.parentMenuCode = this.selectData.id
      this.dialogOptions.data.icon = ''
      this.dialogOptions.data.leafFlagCd = '1'
      this.dialogOptions.data.index = this.treeFindPath(this.trees, data1 => data1.id === this.menuData.menuCode).length
      this.dialogVisible = true
    },
    // 删除菜单
    deleteMenu() {
      if (!this.menuData.id || this.menuData.id === '') return
      if (this.hasChild) {
        this.$alert('请选择叶子节点')
      } else {
        this.$confirm('将会删除菜单信息，确认删除？', '提示').then(() => {
          this.$request({
            url: this.$api.menuDelete,
            data: this.menuData
          }).then((response) => {
            this.$message.success('删除成功')
            this.getMenuTree()
          })
        }).catch(() => {
        })
      }
    },
    getMenu() {
      this.$request({
        url: this.$api.menuGet,
        data: { menuCode: this.selectData.id }
      }).then((response) => {
        this.menuData = response.data
        this.showEvent = this.menuData.menuTypeCd === 'click'
        this.iconPath = ''
        if (this.menuData.icon && this.iconData[this.menuData.icon]) {
          this.iconPath = require('@/assets/images/menu/' + this.iconData[this.menuData.icon] + '.png')
        }

        this.menuData.menuTypeCd === 'no-source' ? this.urlDisabled = true : this.urlDisabled = false

        if (this.menuData.menuTypeCd === 'click') {
          this.getBtnEvents()
        }
      })
    },
    menuTypeChange(value) {
      if (value === 'no-source') {
        this.urlDisabled = true
        this.menuData.url = ''
      } else {
        this.urlDisabled = false
      }
    },
    cancel(data, done) {
      done()
    },
    // 保存菜单信息
    confirm(data, done) {
      this.$request({
        url: this.$api.menuSave,
        data: data
      }).then((response) => {
        this.$message.success(response.description)
        this.dialogVisible = false
        Object.keys(this.dialogOptions.data).forEach(key => (this.dialogOptions.data[key] = ''))
        done()
        this.getMenuTree()
      })
    },
    // 修改菜单信息
    saveMenu() {
      if (!this.menuData.parentMenuName || this.menuData.parentMenuName === '') this.menuData.parentMenuName = ' '
      this.menuData.icon = this.dialogOptions.data.icon
      this.menuData.urlDisabled = this.urlDisabled

      // 三级不能选目录，四级只能选按钮
      const index = this.treeFindPath(this.trees, data1 => data1.id === this.menuData.menuCode).length
      if (index === 3 && this.menuData.menuTypeCd === 'no-source') {
        this.$message.warning('第 3 层不能选目录')
        return
      }
      if (index === 4 && this.menuData.menuTypeCd !== 'click') {
        this.$message.warning('第 4 层只能选按钮')
        return
      }

      // 有子节点，不能选按钮
      if (this.menuData.menuTypeCd === 'click' && this.hasChild) {
        this.$message.warning('有子节点，不能选按钮')
        return
      }

      delete this.menuData.createTime
      delete this.menuData.updateTime

      this.$refs.menuData.validate((valid) => {
        if (!valid) return

        this.$request({
          url: this.$api.menuSave,
          data: this.menuData
        }).then((response) => {
          this.$message.success(response.description)
          this.$refs.menuData.clearValidate()
          this.getMenuTree()
        })
      })
    },
    // 获取按钮事件列表信息
    getBtnEvents() {
      this.searchForm.eventNameLike = ''
      this.searchForm.urlLike = ''
      this.tableOptions.apiRequiredParams.menuCode = this.menuData.menuCode
      this.selectEvent = null
      if (this.$refs['eventTable']) {
        this.$refs['eventTable'].refreshDataSource()
      }
    },
    addEvent() {
      this.eventResVisible = true
    },
    addEventRes(list) {
      const eventCodes = Array.from(new Set(list)).toString()

      this.$request({
        url: this.$api.eventRelSave,
        data: {
          eventCodes: eventCodes,
          menuCode: this.menuData.menuCode
        }
      }).then((response) => {
        this.$message.success(response.description)
        this.eventResVisible = false
        this.getBtnEvents()
      })
    },
    deleteEvent() {
      // 删除关联按钮事件
      this.selectEvent = this.$refs['eventTable'].getRadioData()
      if (this.selectEvent !== null) {
        this.$confirm('您确定删除这条记录吗', '提示').then(() => {
          this.$request({
            url: this.$api.eventRelDelete,
            data: {
              id: this.selectEvent.id,
              all_turn_field: 'id'
            }
          }).then((response) => {
            this.$message.success(response.description)
            this.getBtnEvents()
          })
        }).catch(() => {
        })
      } else {
        this.$alert('请选择一条记录')
      }
    },
    // 搜索
    search() {
      this.$refs['eventTable'].serchHandel()
    },
    resetSearchFrm() {
      this.searchForm = {}
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
    }

  }
}
</script>

<style type="text/scss" scoped>
  .icon {
    width: 40px;
    height: 28px;
    display: block;
    background-repeat: no-repeat;
    background-position: center bottom;
  }

  .secmenupic {
    display: block;
    background: #8BBBF1;
    width: 40px;
    height: 40px;
    margin-right: 10px;
    padding: 0px;
    border-radius: 8px;
    border: 0px;
  }

</style>

