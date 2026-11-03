<template>
  <div>
    <el-form ref="menuFormRef" :disabled="model.formDisabled" :model="model" :rules="rules" label-width="80px">
      <el-form-item label="上级功能" prop="parentMenuName">
        <el-input v-model="model.parentMenuName" placeholder="请输入上级功能" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="menuName">
        <el-input v-model="model.menuName" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="statusCd">
        <ts-multi-select v-model="model.statusCd" :params="StCdType"></ts-multi-select>
      </el-form-item>
      <el-form-item label="资源类型" prop="menuTypeCd">
        <ts-multi-select v-model="model.menuTypeCd" :params="MenuTypeCd" @change="onchange"></ts-multi-select>
      </el-form-item>
      <el-form-item label="资源url" prop="url">
        <el-input v-model="model.url" placeholder="请输入资源url" :disabled="model.urlDisabled"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="model.sort" placeholder="请输入排序"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input v-model="model.remark" placeholder="请输入描述"></el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon" v-if="model.index===0">
        <div>
          <li class="secmenupic" @click="showIconSelect">
            <img v-if="iconPath!==''" class="icon" :src="iconPath">
          </li>
        </div>
      </el-form-item>
    </el-form>

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
      <div style="margin-top: 10px">
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
  </div>
</template>

<script>

export default {
  components: {},
  name: 'MenuForm',
  data() {
    return {
      innerVisible: false,
      iconPath: '',
      StCdType: { key: 'StCd', group: '1', width: '100%', placeholder: '请选择状态' },
      MenuTypeCd: { key: 'MenuType', width: '100%', placeholder: '请选择资源类型' },
      model: {
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
      rules: {
        parentMenuName: [
          { required: true, message: '请输入上级功能', trigger: 'blur' }
        ],
        menuName: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        menuTypeCd: [
          { required: true, message: '请选择资源类型', trigger: 'change' }
        ],
        statusCd: [
          { required: true, message: '请选择状态', trigger: 'change' }
        ],
        sort: [
          { required: false, message: '请输入排序', trigger: 'blur' },
          { message: '请输入整数格式', pattern: /^[0-9]{1,9}$/ }
        ]
      }
    }
  },
  methods: {
    showIconSelect() {
      this.innerVisible = true
    },
    iconSelect(index) {
      if (index <= 9) {
        this.iconPath = require('@/assets/images/menu/menu_icon0' + index + '.png')
        this.model.icon = 'iconmenu0' + index
      } else {
        this.iconPath = require('@/assets/images/menu/menu_icon' + index + '.png')
        this.model.icon = 'iconmenu' + index
      }
      this.innerVisible = false
    },
    onchange(value) {
      // 资源类型
      // 目录不需要填url
      if (value !== '') {
        if (value === 'no-source') {
          this.model.urlDisabled = true
          this.model.url = ''
        } else {
          this.model.urlDisabled = false
        }
        // 第三级不能建目录，第四级只能是按钮
        if (this.model.index === 2 && value === 'no-source') {
          this.$message.warning('第 3 层不能建目录')
          this.$nextTick(() => {
            this.model.menuTypeCd = ''
          })
        }
        if (this.model.index === 3 && value !== 'click') {
          this.$message.warning('第 4 层只能建按钮')
          this.$nextTick(() => {
            this.model.menuTypeCd = ''
          })
        }
      }
    }
  }
}
</script>
<style type="text/scss" scoped>
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

