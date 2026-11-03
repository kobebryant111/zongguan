<!-- 流程归档 -->
<template>
  <div class="container_gd"> 
    <div class="gd_head">
      <el-form :model="form" ref="form" label-width="50px" inline>
        <div class="home">
          <div>
            <el-row>
              <el-col :span="10">
                <el-form-item label> 
                  <el-select placeholder="请选择" v-model="form.flowid" filterable>
                    <el-option v-for="(item, index) in flowTypeData" :key="index" :label="item.name" :value="item.code"></el-option>
                    <template slot="prefix">类型：</template>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="10">
                <el-form-item label>
                  <el-input v-model="form.flowtitle">
                    <template slot="prefix">名称：</template>
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <div>
            <el-row>
              <el-col :span="12">
                <clean-btn :size="sizes" :forms="this.form" />
              </el-col>
              <el-col :span="12">
                <div class="home_orange">
                  <buttonOrange :buttonName="buttonSearch" :size="sizes" :fns="onSearch"></buttonOrange>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-form>
    </div>

    <div class="gd_content" v-loading="loading">
      <el-scrollbar style="height:100%">
        <div class="db_content_en">
          <el-collapse v-model="activeCollapseNames" class="collapse-pan">
            <el-collapse-item v-for="(item,index) in items" :key="index" :name="item[0].flowname">
              <template slot="title">
                <strong>{{item[0].flowname}} ({{item.length}})</strong>
              </template>

              <!-- content --> 
              <div v-for="(tt, indexs) in item" :key="indexs" class="border-line">
                <a href="javascript:;" @click="toGd(tt)"> 
                  <b>{{tt.flowtitle}}</b>
                  <div class="subtitle">
                    <div>
                      <span>{{tt.creators}}</span>
                      <span>{{tt.createtime | formatDate}}</span>
                    </div>
                  </div>
                </a>
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </el-scrollbar>
    </div>
    <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" />
  </div>
</template>
<script>
  import { getFlowGDData, getFlowTypeAll, getFlowGDDataNew } from "@/api/common/workflowApi";
  import cleanBtn from "@/components/CleanBtn";
  import pager from "@/components/Pager";
  import common from "@/api/common";
  import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
  import { getDicEntry, getDomainName } from "@/api/eosDictEntry";
  import { mapGetters } from "vuex";
  import Moment from 'moment'
  import store from '@/store'
  export default {
    name: "FlowGd",
    components: {
      cleanBtn,
      pager,
      buttonOrange
    },
    props: {
      jumpDb: {
        type: Function,
        requried: true,
        default: _ => { }
      }
    },
    data() {
      return {
        size: 50, // 分页 - 显示条数
        index: 1, //  分页 - 默认第一页
        totalNumber: 0, // 分页总数

        activeCollapseNames: "collapse", // 面板选中的name
        items: [],
        form: {
          flowtitle: "", // 流程中文名称
          flowid: ""
        },
        flowTypeData: [], // 流程类型数据
        peoForm_options: [], //人员类型
        sizes: "small", //按钮大小
        buttonSearch: "查 询", //查询按钮
        items_search: [], //查询数据
        loading : true,   // 数据加载效果控制
      };
    },
    filters: {
      formatDate(time) { 
        return Moment(time).format('YYYY-MM-DD HH:mm');
      }
    },
    computed: {
      ...mapGetters(["userId"])
    },
    created() {
      getFlowTypeAll().then(res => {
        this.flowTypeData = res.data.results;
      });
    },
    mounted() {
      // this.init();
    },
    methods: {
      init() {
        const self = this;
        let params = this.form;
        params.size = this.size;
        params.index = this.index;
        let data = common.paramsFormat(params);
        getFlowGDDataNew(data).then(res => {
          if (res.data.status != 200) { 
            if (res.data.status == 5009) {
              self.$confirm(res.data.message, {
                confirmButtonText: '确定',
                type: 'error'
              }).then(() => { 
                this.$store.dispatch('LogOut').then(() => {
                  location.reload()
                })
              }).catch(error => { })
            } else {
              self.$alert(res.data.message, { type: "error" });
            }
            return false;
          }
          let data = res.data.results;
          self.totalNumber = res.data.total;
          data.forEach((ii, k) => {
            self.flowTypeData.forEach((val, key) => {
              if (ii.flowid === val.flowdraftno) {
                ii.processChName = val.flowname;
              }
            });
          });
          let _data = self.groupBy(data, item => {
            return [item.flowid];
          }); 

          let map = self.groupByDefName(data, item => {
            return item.flowid;
          });
          // 保存到 本地
          sessionStorage.setItem("workflow_gd_data", JSON.stringify(map));
          self.items = _data;
          self.items_search = _data;
          self.loading = false    // 关闭效果
        });
      },
      /**
       * 对数据进行分组
       */
      groupBy(items, f) {
        let groups = {};
        items.forEach(i => {
          let group = JSON.stringify(f(i));
          groups[group] = groups[group] || [];
          groups[group].push(i);
        });
        return Object.keys(groups).map(group => {
          return groups[group];
        });
      },
      groupByDefName(items, call) {
        const map = new Map();
        items.forEach(i => {
          let index = JSON.stringify(call(i)); 
          let name = i.flowid.split(".");
          map.set(index.replace(/\"/g, ""), name[name.length - 1]);
        });
        return map;
      },

      /**
       * 查询
       */
      onSearch() {
        const self = this;
        this.index = 1
        self.init() 
      },
      /**
       * 点击待办触发
       */
      toGd(row) {
        row.status = "gd";
        this.jumpDb(row);
      },
      /**
       * 分页触发
       */
      getPager: function (pageSize, currentPage) {
        console.log("pageSize:",pageSize,"currentPage",currentPage)
        this.size = pageSize;
        this.index = currentPage;
        this.init();
      } //  分页触发 end
    }
  };
</script>
<style scoped>
  .div-clear {
    clear: both;
  }

  .gd_content {
    height: 450px;
  }

  .db_content_en {
    padding: 0 15px;
  }

  .border-line {
    border-bottom: 1px solid #ebeef5;
    padding-bottom: 5px;
    margin-bottom: 5px;
  }

  .subtitle {
    color: #909399;
    margin: 5px 0 0 10px;
    display: flex;
    justify-content: space-between;
  }

  .home {
    background-color: #ffffff;
    padding: 0 10px;
    display: flex;
    justify-content: space-between;
  }

  .home_orange {
    margin: 0 6px 0 10px;
  }
</style>

<style>
  /* el-input修改 */
  .gd_head .el-input--prefix .el-input__inner {
    padding-left: 50px;
  }

  .gd_head .el-input__prefix {
    left: 8px;
    color: #262626;
    font-size: 14px;
  }

  /* 去掉横向滚动 */
  .gd_content .el-scrollbar__wrap {
    overflow-x: hidden;
  }
</style>