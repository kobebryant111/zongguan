<!-- 外包入职管理 -->
<template>
  <div class="wbrzContainer">
    <div class="wbrzContainer-left">
      <about-layout :roleData="deptTreeData" :defaultProp="defaultProp" :nodeKey="nodeKey" :defaultExpandAll="false"
        v-model="$attrs" :fns="getCurrentNode" :filterNodeMethod="getFilterNode" :clickNode="false">
      </about-layout>
    </div>
    <div class="wbrzContainer-right">
      <crew ref="crew" />
    </div>

  </div>
</template>
<script>
  import crew from '@/views/crewManager/wbrz/wbrzCrew';
  import aboutLayout from "@/components/AboutLayout";
  import { getDeptTree } from "@/api/userApi/bankUser";
  export default {
    components: { aboutLayout, crew },
    data() {
      return {
        deptTreeData: [], // 组织树
        employData: [],      // 行员table数据
        defaultProp: {
          children: "children",
          label: "text"
        },
        nodeKey: "id",
        crewData: [],  // 行员数据
      };
    },
    created() {
    },
    mounted() {
      this.init();
    },
    methods: {
      init: function () {
        // 组织树
        getDeptTree().then(res => {
          if (res.data.status == 200) {
            this.deptTreeData = res.data.results[0].children;
          } else {
            this.$message.error("获取部门组织树失败")
          }

        }).catch(error => {
          this.$message.error(error);
        });  // end

      }, // init end

      /**
       * 过滤
       */
      getFilterNode: function (val, data) {
        return data.text.indexOf(val) !== -1;
      },

      // 机构树选中触发
      getCurrentNode: function (row) {
        let type = row.attr;
        let objId = row.id.replace(type, '');
        if("dep" == type){
          this.$refs.crew.depName = row.text;
          this.$refs.crew.paramsForm.dep = '';
          this.$refs.crew.paramsForm.depid = objId;
          this.$refs.crew.loadInit();
        }else{
          this.$refs.crew.depName = row.text;
          this.$refs.crew.paramsForm.depid = '';
          this.$refs.crew.paramsForm.dep = objId;
          this.$refs.crew.loadInit();
        }
      } // 机构树选中触发 end
    }
  };
</script>
<style scoped>
  .wbrzContainer {
    padding: 15px 0 15px 15px;
    display: flex;
  }

  .wbrzContainer-right {
    width: 80%;
    padding: 0 15px;
    display: inline-block;
  }

  .wbrzContainer-left {
    width: 20%;
    height: 100%;
    display: inline-block;
  }
</style>