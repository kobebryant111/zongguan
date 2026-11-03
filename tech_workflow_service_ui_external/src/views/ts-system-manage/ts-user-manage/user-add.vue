<!--
 * @Author: your name
 * @Date: 2020-04-07 19:16:55
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-24 10:13:06
 * @Description:
 -->
<template>
  <div class="page-container">
    <ts-multi-step :options="options"></ts-multi-step>
  </div>
</template>

<script>
import UserInfo from './user-infomation'
import UserOrg from './user-belong-org'
import UserDepartment from './user-belong-department'
import UserRole from './user-belong-role'

export default {
  data() {
    return {
      options: {
        data: { // [选填 | Object] 步骤组件各页面间数据通信的容器，子组件可通过$emit('update', {数据})通知更新数据
        },
        createOverView: false, // [选填 | Boolean] 是否在所有步骤完成后生成概览视图，来预览前面所填信息，默认false
        // activeStep: 1, // [选填 | Number] 设置在第N步后，不用等上级步骤保存后亦可跳至下一步(仅当类型为新增'add'时设置有效)
        step: [ // [必填 | Array] 步骤组件数组
          {
            title: '基本信息', // [必填 | String] 步骤标题
            content: UserInfo // [必填 | vueComponent Object] 组件页面
          },
          {
            title: '所属机构',
            content: UserOrg,
            refresh: true
          },
          {
            title: '所属部门',
            content: UserDepartment,
            // customBtn: false, // [选填 | Boolean] 是否自定义按钮，默认false
            refresh: true // [选填 | Boolean] 每次跳转是否刷新该页面 默认false会缓存页面内容
          },
          {
            title: '角色',
            content: UserRole,
            refresh: true
          }
        ]
        // 最好还是写一下
        // back: { // [选填|Object] 返回上级的路径与参数，默认为系统公共方法的back()回退上一页,可以不写
        //   path: '/user/usermg/personMgList',//如果写了back，path 参数必须要写
        //   query: {} //可以不写了，如果写就传自定义需要参数即可
        // },
        // needupdate:'yes',//默认needupdate 只有yes/no， yes是需要刷新 no是不需要刷新
      }
    }
  },
  computed: {

  },
  mounted() {
    // this.getStepArr()
  },
  methods: {
    getStepArr() {
      const _this = this
      setTimeout(function() {
        _this.options.step = [
          {
            title: '基本信息', // [必填 | String] 步骤标题
            content: UserInfo // [必填 | vueComponent Object] 组件页面
          },
          {
            title: '所属机构',
            content: UserOrg
          },
          {
            title: '所属部门',
            content: UserDepartment,
            showBtn: false // [选填 | Boolean] 是否显示保存按钮，默认true
          },
          {
            title: '角色',
            content: UserRole
          }
        ]
      }, 2000)
    }
  }
}
</script>

