<!-- 
    外包商组件
    type : single 为 单选组件，multiple为 多选组件，
    多选时不需要 index 参数
    多选组件：<wbs v-model="scope.row.wbsid" type="multiple"></wbs>
    单个显示：<wbs v-model="scope.row.wbsid" ></wbs>
    多个显示：<wbs v-model="scope.row.wbsid" :index="scope.row.index"></wbs>
    组件参数
    index : 下标

 -->
<template >
  <span v-if="type === 'single'" :id="wbsSpanId">
    <el-select v-model="wbsId" placeholder="请选择外包商" filterable remote :remote-method="loadMore">
      <el-option v-for="item in wbsOpts" :key="item.id" :label="item.outsourcname" :value="item.id"></el-option>
    </el-select>
  </span>
  <span v-else-if="type === 'multiple'">
    <el-input v-model="wbsNames" placeholder="请选择外包商">
      <el-button slot="append" icon="el-icon-more" @click="onShwoTransfer"></el-button>
    </el-input>
    <el-dialog
      title
      :visible.sync="visibleClose"
      width="40%"
      @close="onDialogClose"
      destroy-on-close
      style="text-align:left"
    >
      <el-transfer
        v-model="waibaoIds"
        :data="wbsOpts"
        :props="props"
        filterable
        @left-check-change="onWbsChecked"
      ></el-transfer>
      <span slot="footer">
        <el-button @click="visibleClose = false">取 消</el-button>
        <el-button type="primary" @click="dialogDetermine">确 定</el-button>
      </span>
    </el-dialog>
  </span>
</template>
<script>
import { getPagerList, getListAll } from "@/api/otherApi/cutsourcingInfo";
import common from "@/api/common";
export default {
  model: {
    event: "changed"
  },
  props: {
    value: {
      type: [String, Number],
      default: ""
    },
    // 下标，用户组件id
    index: {
      type: Number,
      default: 0
    },
    // 组件类型 single为 单选，multiple ：多选
    type: {
      type: String,
      default: "single"
    },
    defaultValue: {
      type: [String, Number],
      default: ""
    }
  },
  data() {
    return {
      wbsOpts: [], // 集合数据
      currentPage: 1, // 当前页数
      total: 0, // 总页数
      searchTitle: "", // 搜索外包商名称
      getAll: false, // 数据是否全部加载完成 默认：false 没有
      wbsSpanId: "", // 组件编号
      wbsId: this.value, // 外包商编号
      waibaoIds: [], // 外包商id，内部临时变量
      wbsNames: "", // 外包商名称，内部临时变量
      props: {
        key: "id",
        label: "outsourcname"
      }, // 数据源别名
      visibleClose: false // dialog是否显示，默认false 不显示
    };
  },
  created() {
    this.wbsSpanId = "outsourcersSelect" + this.index;
  },
  mounted() {
    this.init();
    this.setLoadMroe();
  },
  watch: {
    wbsId(val) {
      this.$emit("changed", val);
    },
    defaultValue(val) {
      const that = this
      this.$nextTick(_ => {
        console.log("valdd", val, this.wbsOpts);
        let data = common.paramsFormat({
        index: this.currentPage,
        outsourcname: val
      });
        getPagerList(data).then(res => {
          console.log("dfsdfafda");
          if (that.wbsOpts.length == 0) {
            that.wbsOpts = res.data.results;
            that.total = res.data.total;
            if (that.total > that.wbsOpts.length) {
              that.getAll = false;
            } else {
              that.getAll = true;
            }
          } else {
            if (that.total === that.wbsOpts.length) {
              that.getAll = true;
            }
            let listInitData = res.data.results;
            that.wbsOpts = listInitData

            that.wbsId = that.wbsOpts[0].outsourcname
          }
        });
      });
    }
  },
  methods: {
    init(params) {
      const that = this
      console.log("currentPage", that.currentPage);
      let data = common.paramsFormat({
        index: this.currentPage,
        outsourcname: this.searchTitle
      });
      that.$nextTick(_ => {
        if (that.type === "single") {
          singleComp(data);
        } else if (that.type === "multiple") {
          multipleComp();
        }
      });
      // 内部函数 - 单选http
      function singleComp(data) {
        getPagerList(data).then(res => {
          console.log("dfsdfafda");
          if (that.wbsOpts.length == 0) {
            that.wbsOpts = res.data.results;
            that.total = res.data.total;
            if (that.total > that.wbsOpts.length) {
              that.getAll = false;
            } else {
              that.getAll = true;
            }
          } else {
            if (that.total === that.wbsOpts.length) {
              that.getAll = true;
            }
            let listInitData = res.data.results;
            that.wbsOpts = that.wbsOpts.concat(listInitData);
          }
        });
      } //  单选http end
      /**
       * 多选
       */
      function multipleComp() {
        getListAll().then(res => {
          if (res.data.status != 200) {
            that.$alert(res.data.message, { type: "error" });
            return false;
          }
          console.log("多选", res.data.results);
          that.wbsOpts = res.data.results;
        });
      }
    },
    /**
     * 点击更多触发事件
     */
    onShwoTransfer() {
      const self = this;
      self.visibleClose = true;
    },
    /**
     * 关闭dialog
     */
    onDialogClose() {
      this.visibleClose = false;
    },
    /**
     * dialog 确定
     */
    dialogDetermine() {
      const self = this;
      let datas = [];
      for (let i in self.waibaoIds) {
        let _datas = self.wbsOpts.filter(item => {
          return item.id === self.waibaoIds[i];
        });
        datas.push(_datas[0]);
      }
      let name = "";
      for (let i in datas) {
        name += datas[i].outsourcname + ",";
      }
      self.wbsNames = name.substring(0, name.length - 1);
      self.wbsId = self.waibaoIds.join(",");
      console.log("wbsId", self.wbsId);
      self.onDialogClose();
    },
    onWbsChecked(item) {
      console.log("item");
    },
    // 分页显示
    loadMore(query) {
      console.log("loadMore", query);
      if (query != "") {
        const that = this;
        that.currentPage = 1;
        that.wbsOpts = [];
        that.searchTitle = query;
        that.init();
      }
    },
    setLoadMroe() {
      const that = this;
      // 获得元素
      let loadSeletctElement = 0;
      try {
        loadSeletctElement = document
          .getElementById(that.wbsSpanId)
          .getElementsByClassName("el-select-dropdown__wrap")[0];
        function scrollLoadData() {
          // 当滚动条高度等于卷入高度+ 可视高度时，说明已经到底了，scrollHeight : 滚动条高度，scrollTop:已滚动不可见的滚动高度
          const scrollDistance =
            loadSeletctElement.scrollHeight -
            loadSeletctElement.scrollTop -
            loadSeletctElement.clientHeight;
          if (that.getAll) {
            return false;
          }
          // 非搜索
          if (~~scrollDistance <= 0) {
            // 页码依次+1
            that.currentPage += 1;
            that.init();
          }
        }
        console.log("loadSeletctElement", loadSeletctElement);
        loadSeletctElement.addEventListener("scroll", scrollLoadData);
      } catch (error) {
        console.warn("无法获取对象滚动条，原因不是同一个组件可忽略");
      }
    }
  }
};
</script>

