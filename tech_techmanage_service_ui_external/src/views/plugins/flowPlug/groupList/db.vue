<!-- 流程待办 -->
<template>
  <div class="container_db">
    <!-- <el-form :model="form" ref="form" label-width="50px" inline>
      <el-form-item label="类型">
        <el-input v-model="form.processDefName"></el-input>
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="form.processInstName"></el-input>
      </el-form-item>
      <el-form-item>
        <clean-btn :forms="this.form"/>
        <el-button type="primary" @click="onSearch">查 询</el-button>
      </el-form-item>
    </el-form>-->
    <div class="db_head" >
      <el-form :model="form" ref="form" label-width="50px" inline>
        <div class="home">
          <div>
            <el-row>
              <el-col :span="10">
                <el-form-item label>
                  <!-- <el-select placeholder="请选择" v-model="form.processDefName">
                    <el-option v-for="item in peoForm_options" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                    <template slot="prefix">类型：</template>
                  </el-select> -->
                  <el-select placeholder="请选择" v-model="form.processDefName" filterable>
                    <el-option v-for="(item, index) in flowTypeData" :key="index" :label="item.flowname" :value="item.flowdraftno"></el-option>
                    <template slot="prefix">类型：</template>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="10">
                <el-form-item label>
                  <el-input v-model="form.processInstName">
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
    <div class="db_content" v-loading="loading">
      <el-scrollbar style="height:100%">
        <div class="db_content_en">
          <el-collapse v-model="activeCollapseNames" class="collapse-pan">
            <el-collapse-item v-for="(item,index) in items" :key="index" :name="item[0].processDefName">
              <template slot="title">
                <strong>{{item[0].processChName}} ({{item.length}})</strong>
              </template>

              <!-- content -->
              <div v-for="tt in item" :key="tt.processInstID" class="border-line">
                <a href="javascript:;" @click="toDb(tt)">
                  <b>{{tt.processInstName}}</b>
                  <div class="subtitle">
                    <div>
                      <span>{{tt.partiName}}</span>
                      <span>{{tt.createTime}}</span>
                    </div>
                    <div class="subtitle_right">计划完成</div>
                  </div>
                </a>
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </el-scrollbar>
    </div>
  </div>
</template>
<script>
  import { getFlowData, getFlowTypeAll } from "@/api/common/flowApi";
  import cleanBtn from "@/components/CleanBtn";
  import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
  import { getDicEntry, getDomainName } from "@/api/eosDictEntry";
  import { mapGetters } from "vuex";
  import common from "@/api/common";
  import store from '@/store'
  export default {
    name: "FlowDb",
    components: {
      cleanBtn,
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
        activeCollapseNames: "collapse", // 面板选中的name
        items: [],
        form: {
          processInstName: "", // 流程中文名称
          processDefName: ""
        },
        peoForm_options: [], //人员类型
        flowTypeData: [], // 流程类型数据
        sizes: "small", //按钮大小
        buttonSearch: "查 询", //查询按钮
        items_search: [], //查询数据
        loading : true,   // 数据加载效果控制
      };
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
      this.init();
      // this.getDic();
    },
    methods: {
      init() {
        const self = this;
        getFlowData("/commonFlowApi/getdb", null).then(res => {
          console.log("待办", res.data);
          if (res.data.status != 200) {
            if (res.data.status == 5009) {
              self.$confirm(res.data.message, {
                confirmButtonText: '确定',
                type: 'error'
              }).then(() => {
                // location.reload()
                // this.$router.push({ path: '/redirect' })
                this.$store.dispatch('LogOut').then(() => {
                  location.reload()
                })
              }).catch(error => { })
            } else {
              self.$alert(res.data.message, { type: "error" });
            }
            return false;
          }
          let data = [];
          if (res.data.results != undefined) {
            data = res.data.results;
          }
          let _data = self.groupBy(data, item => {
            return [item.processDefName];
          });

          let map = self.groupByDefName(data, item => {
            return item.processDefName;
          });
          // 保存到 本地
          // localStorage.setItem('flow_db_data', JSON.stringify(map))
          sessionStorage.setItem("flow_db_data", JSON.stringify(map));
          self.items = _data;
          self.items_search = _data;
          const dbDataMap = JSON.parse(sessionStorage.getItem("flow_db_data"));
          this.$root.Bus.$emit("dbNumber", data.length);
          self.loading = false    // 关系加载效果
        });
      },

      // 查询业务字典
      getDic() {
        getDicEntry({
          dicttypeid: "REQUIRE_PEOPLE_TYPE"
        }).then(res => {
          if (res.data.status !== 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          let data = res.data.results;

          //人员类型
          this.peoForm_options = data.filter(item => {
            return item.dicttypeid == "REQUIRE_PEOPLE_TYPE";
          });

          return data;
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
          let name = i.processDefName.split(".");
          // console.log('index::', index)
          map.set(index.replace(/\"/g, ""), name[name.length - 1]);
        });
        return map;
      },

      /**
       * 查询
       */
      onSearch() {
        console.log("查询", this.form, this.items_search)
        let arras = [];
        const self = this;
        self.items_search.forEach(ii => {
          let data = ii.filter(tt => {
            // console.log("查询2", tt.processInstName.indexOf(processInstName))
            // return tt.processInstName.indexOf(self.form.processInstName) != -1;
            // bin
            if (self.form.processInstName != '' && self.form.processDefName != '') {
              return tt.processDefName.indexOf(self.form.processDefName) != -1 && tt.processInstName.indexOf(self.form.processInstName) != -1;
            } else if (self.form.processInstName == '' && self.form.processDefName != '') {
              return tt.processDefName.indexOf(self.form.processDefName) != -1
            } else if (self.form.processInstName != '' && self.form.processDefName == '') {
              return tt.processInstName.indexOf(self.form.processInstName) != -1
            } else {
              return tt
            }
          });
          arras = arras.concat(data);
        });
        let datas = self.groupBy(arras, i => {
          return [i.processDefName];
        });
        self.items = datas;
      },
      /**
       * 点击待办触发
       */
      toDb(row) {
        row.status = "db";
        // 只有指定的流程可以跳转，否则跳转到老项目
        this.jumpDb(row);
      }
    }
  }
</script>

<style scoped>
  /* .container_db {
    height: 500px;
  } */

  .db_content {
    /* width: 99%; */
    /* margin: 0 1px; */
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
  .db_head .el-input--prefix .el-input__inner {
    padding-left: 50px;
  }

  .db_head .el-input__prefix {
    left: 8px;
    color: #262626;
    font-size: 14px;
  }

  /* 去掉横向滚动 */
  .db_content .el-scrollbar__wrap {
    overflow-x: hidden;
  }

  /* 去掉el-collapse边框 */

  /* .db_content .el-collapse-item__wrap {
  border-bottom: none;
  } */
</style>
