<!-- 后督信息记录和录入 -->
<template>
  <div>
    <div class="psirae-container requireList">
      <el-form size="mini" :model="paramsForm" inline>
        <div class="requireList_head">
          <div style="width: 90%;" class="requireList_head_input">
            <el-row type="flex">
              <el-col :span="4">
                <el-form-item style="width: 200px;" label="部门:">
                  <el-select
                    style="width: 130px;"
                    filterable
                    placeholder="请选择"
                    v-model="paramsForm.dept"
                  >
                    <el-option
                      v-for="item in depData"
                      :key="item.id"
                      :label="item.text"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="5">
                <el-form-item style="width: 250px;" label="关联人员:">
                  <el-select
                    style="width: 150px;"
                    v-model="paramsForm.relationUserName"
                    filterable
                    remote
                    resreve-keyword
                    :remote-method="remoteMethod"
                    :loading="loading"
                    @change="handelSelectChange"
                  >
                    <el-option
                      v-for="item in relationUser_options"
                      :key="item.pcode"
                      :label="item.text"
                      :value="item.text"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item style="width: 180px;" label="是否合规:">
                  <el-select
                    style="width: 100px;"
                    placeholder="请选择"
                    v-model="paramsForm.isCompliance"
                  >
                    <el-option key="0" value="否"></el-option>
                    <el-option key="1" value="是"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item style="width:350px;" label="申请时间:">
                  <el-date-picker
                    v-model="paramsForm.createDates"
                    value-format="yyyy-MM-dd"
                    type="daterange"
                    range-separator="-"
                    start-placeholde="开始日期"
                    end-placeholde="结束日期"
                    style="width:240px;"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item style="width: 180px;" label="流程状态:">
                  <el-select
                    style="width: 100px;"
                    placeholder="请选择"
                    v-model="paramsForm.flowStatus"
                  >
                    <el-option key="0" value="进行中"></el-option>
                    <el-option key="1" value="已结束"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <div style="width: 10%;">
            <div class="requireList_head_right_orange">
              <clean-btn :size="sizes" :forms="paramsForm"/>
              <button-orange :buttonName="buttonSearch" :size="sizes" :fns="searchData"></button-orange>
            </div>
          </div>
        </div>
      </el-form>
      <div class="tags">
        <el-tabs type="border-card" @tab-click="handleClick">
          <el-tab-pane label="记录">
            <record ref="record" :paramsForm="paramsForm"></record>
          </el-tab-pane>
          <el-tab-pane label="录入" v-if="showEntry" @tab-click="handleClick">
            <entry ref="entry" :paramsForm="paramsForm"></entry>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="nav-bottom"></div>
  </div>
</template>
<script>
import Entry from "@/views/postSupervisionManagement/entry";
import Record from "@/views/postSupervisionManagement/record";

import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import cleanBtn from "@/components/CleanBtn"; //引入重置按钮
import {
  queryEmployeeTree,
  queryResourceDept
} from "@/api/integrated/wbryzrlc";
import { getDicEntry } from "@/api/eosDictEntry";
import { getEmployUser } from "@/api/common/flowApi";
import { mapGetters } from "vuex";

export default {
  name: "postSupInfoRecordAndEntry",
  components: {
    buttonOrange,
    cleanBtn,
    Entry,
    Record
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认:50
      index: 1, // 初始值 默认:1
      sizes: "small", //按钮大小
      buttonSearch: "查 询", //查询按钮
      paramsForm: {
        dept: "", //部门
        isCompliance: "", //是否合规
        relationUser: "", //关联人员id
        relationUserName: "", //关联人员名称
        startDate: "", //申请时间开始时间
        endDate: "", //申请时间结束时间
        createDates: [], //申请时间
        flowStatus: "" //流程状态
      },
      depData: [], //部门数据
      loading: false, //是否正在加载
      relationUser_options: [], //关联人员列表
      relationUser_options_all: [],
      showEntry: false //显示录入页面
    };
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  created() {},
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.getDep();
      this.getKjList();
      this.getPermission();
    },

    getPermission() {
      //后督录入页面权限
      getDicEntry({
        dicttypeid: "NEWKG_HDLRYMQX"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        let hdlrymqx = [];
        hdlrymqx = data.filter(item => {
          return item.dicttypeid == "NEWKG_HDLRYMQX";
        });
        for (var i = 0; i < hdlrymqx.length; i++) {
          if (this.userId == hdlrymqx[i].dictid) {
            this.showEntry = true;
            break;
          } else {
            this.showEntry = false;
          }
        }
        return data;
      });
    },

    // 获取部门
    getDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
          if (res.data.status == 200) {
            var fild = res.data.results[0].children;
            let name = res.data.results[0].text;
            this.depData = fild.filter(tt => {
              tt.id = tt.id.replace("dep", "");
              tt.text = tt.text.replace(name + "-", "");
              return tt;
            });
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //获取行员列表
    remoteMethod(val) {
      if (val != "") {
        this.loading = true;
        this.relationUser_options = [];
        for (var i = 0; i < this.relationUser_options_all.length; i++) {
          if (this.relationUser_options_all[i].text.indexOf(val) != -1) {
            this.relationUser_options.push(this.relationUser_options_all[i]);
          }
        }
        this.loading = false;
      } else {
        this.relationUser_options = [];
      }
    },

    //获取科技部行员列表
    getKjList() {
      let params = "";
      params = "7004"; //130是总行科技部部门编号
      queryEmployeeTree(params)
        .then(res => {
          if (res.data.status == 200) {
            res.data.results[0].children.filter(li => {
              li.children.filter(ol => {
                ol.chargeName = li.text.replace("总行金融科技部-", "");
                this.relationUser_options_all.push(ol);
              });
            });
          } else {
            this.relationUser_options_all = [];
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    //处理
    handelSelectChange(val) {
      var reg = /\(([^)]*)\)/;
      var badge = val.match(reg)[1];
      // var badge = val;
      for (var i = 0; i < this.relationUser_options.length; i++) {
        if (this.relationUser_options[i].pcode == badge) {
          this.paramsForm.relationUser = this.relationUser_options[i].pcode;
          return;
        }
      }
    },

    //查询
    searchData() {
      if (this.paramsForm.createDates.length > 0) {
        this.paramsForm.startDate = this.paramsForm.createDates[0];
        this.paramsForm.endDate = this.paramsForm.createDates[1];
      }
      let params = {};
      params = this.paramsForm;
      params.size = this.size;
      params.index = this.index;
      this.$refs.record.getPsireDatabyParams(params);
      this.$refs.entry.getPsireDatabyParams(params);
    },

    //切换tab执行
    handleClick() {
      this.$refs.record.loadInit();
      this.$refs.entry.loadInit();
    }
  }
};
</script>
<style scoped>
.nav-bottom {
  width: 100%;
  height: 72px;
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  margin-top: 20px;
  padding: 15px 0;
  z-index: 998;
}

.requireList {
  padding: 15px;
}

.requireList_head {
  background-color: #ffffff;
  padding: 18px;
  display: flex;
  justify-content: space-between;
}

.requireList_head_right_orange {
  float: right;
}

.tags {
  padding-top: 15px;
}
</style>

