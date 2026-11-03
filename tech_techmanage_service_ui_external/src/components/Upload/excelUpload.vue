<template>
  <el-upload
    :action="action"
    :show-file-list="false"
    :on-success="success"
    :on-error="error"
    :auto-upload="false"
    :on-change="change"
    :limit="1"
    :class="clazz"
    v-model="$attrs"
    ref="parentExcelUpload"
  >
    <slot></slot>
  </el-upload>
</template>
<script>
export default {
  name: "ExUpload",
  props: {
    action: {
      type: String,
      default: ""
    },
    success: {
      type: Function,
      default: function() {}
    },
    error: {
      type: Function,
      default: function() {}
    },
    change: {
      type: Function,
      default: function() {}
    },
    clazz: {
      type: String
    },
    refe: {
      type: String
    }
  },
  data() {
    return {
      msg: "ddddd333"
    };
  },
  methods: {
    submitExcel: function() {
      console.log("this.$refs.parentExcelUpload", this.$refs.parentExcelUpload);
      this.$refs.parentExcelUpload.submit();
    },
    clearFiles: function() {
      this.$refs.parentExcelUpload.clearFiles();
    }
  }
};
</script>

