<template>
    <div class="app-container">
       <el-tabs v-model="activeName" type="border-card" >
               <el-tab-pane label="设置代办" name="first">
                <config />
               </el-tab-pane>
              <el-tab-pane label="查询代办" name="second">
                <DelegateList />
               </el-tab-pane>
       </el-tabs>
     </div>
</template>
<script>
import config from './config.vue'
import DelegateList from './details.vue'
export default {
  components: {
    config,
    DelegateList
  },
  data() {
    return {
      activeName: 'first'
    }
  },
  methods: {

  }
}
</script>
