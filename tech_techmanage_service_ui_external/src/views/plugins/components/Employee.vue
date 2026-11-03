<!-- 
    行员组件 
    前置条件：
    deptId，部门编号，由部门组件 DepartmentTwo
    allowFullSearch : 暂时未使用
-->
<template >
  <el-select v-model="eid" placeholder="请选择面试官" @change="onChange" >
    <el-option v-for="item in empOpts" :key="item.pcode" :label="item.text" :value="item.pcode"></el-option>
  </el-select>
</template>
<script>
import { getdeptIdForUsers } from "@/api/userApi/bankUser";
export default {
  name: "Employee",
  model : {
    event : 'changed'
  },
  props: {
    value : {
      type : [String,Number],
      default : ''
    },
    // value : {
    //   type : Array,
    //   default : ''
    // },

    deptId: {
      type: [String, Number],
      default: ""
    },

    allowFullSearch: {
      type: Boolean,
      default: false
    },
  },
  data() {
    return {
      empOpts: [], // 行员后台数据
      eid: this.value,  // 行员编号
      depId: this.deptId
    };
  },
  created() {
    const self = this;
    self.$root.Bus.$on("send", val => {
      let id = val.replace(/[a-z]+/g, "");
      self.depId = id;
      self.init();
      console.log("测试是否被销毁");
    });
    // self.$root.Bus.$off("send").$on("send", val => {
    //   let id = val.replace(/[a-z]+/g, "");
    //   self.depId = id;
    //   self.init();
    //   console.log("测试是否被销毁");
    // });
  },
  beforeDestroy() {
    // this.offSendListener();
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      if (!this.allowFullSearch) {
        // 如果不允许全查，必须带上部门编号
        if (!this.depId) {
          return false;
        }
        let id = this.depId.replace(/[a-z]/g, "");
        // 面试官
        getdeptIdForUsers(id).then(res => {
          console.log("dept", res.data.results);
          this.empOpts = res.data.results[0].children;
        });
      } else {
        // 允许全查
      }
    },
    onChange(val) {
      this.$emit('changed', val);
    },

    // 移除事件监听器
    offSendListener: function() {
      const self = this;
      self.$root.Bus.$off("send");
    }
  }
};
</script>

