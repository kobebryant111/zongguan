<!-- 处理人组件 -->
<template>
  <el-cascader
    ref="cascader"
    :options="clrData"
    :props="props"
    @active-item-change="handleItemChange"
    @change="handlerChange"
    :show-all-levels="false"
  ></el-cascader>
</template>
<script>
import { getHandlerTreeComponent } from "@/api/userApi/bankUser";
export default {
  name: "Handler",
  props : {
      handlerChange : {
          type :Function,
          default : _=> {}
      }
  },
  data() {
    return {
      props: {
        children: "children",
        label: "text",
        value: "attr"
      },
      clrData: [], // 处理人数据
      values : {},
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      let componentItems = JSON.parse(sessionStorage.getItem("store"));
      // 处理人组件 懒加载
      let handlerComp = componentItems.filter(item => {
        return item.compEnName === "handlerComponents";
      });
      if (null != handlerComp) {
        if (handlerComp[0].viewWay === 2) {
          // 懒加载
          getHandlerTreeComponent().then(res => {
            this.clrData = res.data.results;
          });
        }
      }
    },
    handleItemChange: function(val) {
      // 分割
      let len = val.length;
      if (len <= 1) {
        return false;
      }
      let index = Number(len) - 1;
      console.log("val===", val[index], this.clrData);
      let vals = val[index].split("-");
      let params = { id: vals[0], current: vals[1], next: vals[2] };
      getHandlerTreeComponent(params).then(res => {
        // 返回数据进行处理
        // 如果子节点类型为 emp 则 children 为null
        let results = res.data.results;
        if (null != results && results.length > 0) {
          results.forEach(function(item, index) {
            if (item.attr === "emp") {
              item.children = null;
              item.attr = item.id;
            }
          });
        }
        g(this.clrData, val[index], results);
      });
      // 递归函数
      function g(nodes, attr, data) {
        if (null == nodes) {
          return;
        }
        nodes.forEach(function(item, index) {
          if (item.attr == attr) {
            item.children = data;
          } else {
            g(item.children, attr, data);
          }
        });
      }
    },
  }
};
</script>

