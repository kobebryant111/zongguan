<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <brand :collapse="isCollapse"/>
    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      mode="vertical"
      background-color="#1F1F1F"
      text-color="#fff"
      active-text-color="#fff"
      unique-opened
    >
      <sidebar-item v-for="route in permission_routers" :key="route.path" :item="route" :base-path="route.path"/>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import Brand from './Brand'

export default {
  components: { SidebarItem, Brand },
  computed: {
    ...mapGetters([
      'permission_routers',
      'sidebar'
    ]),
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
