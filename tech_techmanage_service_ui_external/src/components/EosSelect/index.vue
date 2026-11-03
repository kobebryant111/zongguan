<template>
  <el-select
    v-model="svalue"
    ref="eosSelect"
    v-bind="$attrs"
    :clearable="clear"
  >
    <el-option v-if="hvalue != '' && hvalue != undefined" :label="hvalue" :value="hvalue"></el-option>
    <el-option
      v-for="item in options"
      :key="item.dictid"
      :label="item.dictname"
      :value="item.dictid"
    ></el-option>
  </el-select>
  
</template>
<script>
import { getDicEntry } from "@/api/eosDictEntry";
export default {
  name: "eosSelect",
  props: {
    value: {
      type: [String, Object, Boolean, Number],
      default: ""
    },
    id: {
      type: String,
      required: true
    },
    headValue: {
      type: String
    },
    clear: {
      type: Boolean,
      default: false
    },
  },
  data() {
    return {
      options: [],
      svalue: "",
      type: this.id,
      hvalue: this.headValue,
    };
  },
  watch: {
    svalue(newVal, oldVal) {
      this.svalue = newVal;
      this.$emit("input", newVal);
    }
  },
  mounted() {
    this.svalue = this.value;
    console.log('svalue', this.svalue)
    this.type = this.id;
    this.hvalue = this.headValue;
    this.getData();
  },
  methods: {
    /**
     * 加载数据
     */
    getData: function() {
      this.$nextTick(_ => {
        getDicEntry({ dicttypeid: this.id })
          .then(res => {
            this.options = res.data.results;
          })
          .catch(error => {
            this.$message.error(error);
          });
      });
    },   // 加载数据 end
    
    handelSelect : function(item) {
      this.$forceUpdate();
    }
  }
};
</script>

