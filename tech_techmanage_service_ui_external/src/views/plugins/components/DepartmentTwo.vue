<!--
    下拉 二级部门
    onChange : 函数
    objectcode : 参数
    objectgrade ： 参数
-->
<template>
  <el-select v-model="msgdeptno" placeholder="请选择部门" filterable @change="onChangeDept" :disabled="disabled">
    <el-option v-for="item in deptOpts" :key="item.id" :label="item.text" :value="item.id"></el-option>
  </el-select>

</template>
<script>
  import { getDeptTwo } from "@/api/systemApi/department";
  export default {
    name: "DepartmentTwo",
    model: {
      event: "changed"
    },
    props: {
      value: {
        tyep: String,
        default: ""
      },
      disabled: { //是否可点击
        type: Boolean
      },
      onChange: {
        type: Function,
        default: _ => { }
      },
      objectcode: {
        type: [String, Number],
        required: true,
        default: ""
      },
      objectgrade: {
        type: [String, Number],
        required: true,
        default: ""
      }
    },
    data() {
      return {
        msgdeptno: this.value, // 部门编号
        deptOpts: [] // 部门集合
      };
    },
    mounted() {
      this.init();
      // console.log("测试传入数据", this.value);
    },
    watch: {
      msgdeptno(val) {
        this.$emit('changed', val)
        this.msgdeptno = val;
      },
    },
    methods: {
      init() {
        const self = this;
        // 2级部门
        getDeptTwo({
          objectcode: this.objectcode,
          objectgrade: this.objectgrade
        }).then(res => {
          if (res.data.status !== 200) {
            this.alter(res.data.message, { type: "error" });
            return false;
          }
          // 进行过滤
          let data = res.data.results;
          let name = data[0].text;
          console.log("name", name);
          self.deptOpts = data[0].children.filter(tt => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
          this.msgdeptno = this.value;
          this.onChangeDept()
          // console.log(self.deptOpts);
        }); // end
      },
      /**
       * 事件改变时触发
       */
      onChangeDept: function () {
        this.onChange(this.msgdeptno);
        // this.$root.Bus.$emit('send', this.msgdeptno);
        // this.$emit('sendDepId',this.msgdeptno)
      }
    }
  };
</script>