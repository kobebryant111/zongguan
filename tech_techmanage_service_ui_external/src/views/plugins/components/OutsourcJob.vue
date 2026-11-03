<!--
    岗位 组件
    前置条件，需要部门组件 plugins/compartmentTwo
    以单个显示，只需<job v-model="scope.row.position" ></job>
    以数组显示，需要加上 depId
    <job v-model="scope.row.position" :dep-id="interviewerInfo.msgdeptno"></job>
    参数
    type： 业务字典编号
-->
<template>
  <el-select v-model="jobId" placeholder="请选择岗位">
    <el-option
      v-for="item in jobOpts"
      :key="item.dictid"
      :label="item.dictname"
      :value="item.dictid"
    ></el-option>
  </el-select>
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
export default {
  name: "OutsourcJob",
  model: {
    event: "chenage"
  },
  props: {
    depId: {
      type: [String, Number],
      default: ""
    },
    // 业务字典编号
    type: {
      type: String,
      defaut: ""
    },
    value: {
      type: [String, Number],
      defaut: ""
    }
  },
  data() {
    return {
      jobOpts: [], // 岗位集合数据
      jobId: '', // 岗位编号
      deptId: "" // 部门编号
    };
  },
  
  watch: {
    jobId(val) {
      this.$emit("chenage", val);
    },
    value(val) {
      this.jobId = val
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      getDicEntry({ dicttypeid: this.type }).then(res => {
        this.jobOpts = res.data.results;
      });
    }
  }
};
</script>

