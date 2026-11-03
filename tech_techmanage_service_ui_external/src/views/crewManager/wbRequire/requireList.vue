<!-- 需求管理列表 -->
<template>
  <div class="list-container requireList">
    <el-form :model="paramsForm" inline>
      <div class="requireList_head">
        <div>
          <el-row>
            <el-col :span="10">
              <el-form-item prop="badge" label="">
                <el-select placeholder="请选择" filterable v-model="paramsForm.depid">
                  <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                  <template slot="prefix">部门：</template>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item prop="empname" label="">
                <el-select placeholder="请选择" v-model="paramsForm.usertype">
                  <el-option v-for="item in peoForm_options" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                  <template slot="prefix">类型：</template>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <div>
          <el-row>
            <el-col :span="12">
              <clean-btn :size="sizes" :forms="this.paramsForm" />
            </el-col>
            <el-col :span="12">
              <div class="requireList_head_right_orange">
                <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-form>

    <div class="requireContainer">
      <div class="requireContainer_head">
        <div class="requireContainer_head_en"></div>
        <el-row type="flex" justify="end">
          <el-col :span="11">
            <button-common :buttonName="buttonExport" :size="sizes" :fns="handelExport"></button-common>
          </el-col>
          <!-- <div class="occupied"></div> -->
          <el-col :span="12">
            <button-orange :buttonName="buttonStart" :size="sizes" :fns="startRequire"></button-orange>
          </el-col>
        </el-row>
      </div>

      <el-table :data="crewDatas" highlight-current-row height="510" @select-all="onSelectTable" @select="onSelectTable">
        <!-- <el-table-column type="selection" width="55" /> -->
        <el-table-column prop="createdate" label="发起时间" width="127" :formatter="createtimeFormat"
          :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="部门" prop="dept" width="140" :formatter="deptFormat" :show-overflow-tooltip="true" />
        <el-table-column label="类型" width="50" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span v-if="scope.row.usertype == '1'">研发</span>
            <span v-else-if="scope.row.usertype == '2'">测试</span>
            <span v-else-if="scope.row.usertype == '3'">运维</span>
            <span v-else-if="scope.row.usertype == '4'">文员</span>
          </template>
        </el-table-column>
        <el-table-column label="人数" prop="usernum" :show-overflow-tooltip="true" width="50" />
        <el-table-column label="发起人" prop="username" width="125" :show-overflow-tooltip="true" />
        <el-table-column label="增加原因" prop="addreason" :show-overflow-tooltip="true" width="120" />
        <el-table-column label="人员要求" prop="personrequire" :show-overflow-tooltip="true" width="130" />
        <el-table-column label="联系招聘" width="76" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span v-if="scope.row.isadvertise == '1'">是</span>
            <span v-else-if="scope.row.isadvertise == '2'">否</span>
          </template>
        </el-table-column>
        <el-table-column label="需求进度" width="100" :show-overflow-tooltip="true" :render-header="showHelp">
          <template slot-scope="scope">
            <span>{{scope.row.nums}}</span>
            <span>/</span>
            <span>{{scope.row.usernum}}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="65" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span v-if="scope.row.status == '0'">进行中</span>
            <span v-else-if="scope.row.status == '1'">已完成</span>
          </template>
        </el-table-column>
        <el-table-column label="推荐供应商" prop="supplynames" :show-overflow-tooltip="true" width="123"></el-table-column>
        <el-table-column label="操作" width="122">
          <template slot-scope="scope">
            <!-- <el-link type="warning" :underline="false" @click="onUserDetail(scope.row.badge)">详情</el-link> -->
            <el-link type="warning" :underline="false" @click="reruireProgress(scope.row.processId)">面试进度</el-link>
            <el-link type="warning" :underline="false" @click="onUser(scope.row.processId)">应聘者</el-link>
          </template>
        </el-table-column>
        <el-table-column align="right" width="34">
          <template slot="header">
            <i class="el-icon-setting"></i>
          </template>
        </el-table-column>
      </el-table>

      <div class="pager_zb">
        <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
      </div>
    </div>

    <el-dialog title="面试进度" :visible.sync="msjdDialog" class="dialog">
      <el-table ref="msjdTable" :data="msjndData" highlight-current-row style="width: 100%">
        <el-table-column prop="msDate" label="日期" width="253" :formatter="msDateFormat"></el-table-column>
        <el-table-column prop="msname" label="面试人员" width="253"></el-table-column>
        <el-table-column prop="tgNum" label="通过人数" width="253"></el-table-column>
      </el-table>
      <div class="cloosePre">
        <button-common :buttonName="buttonClose" :size="sizes" :fns="closeDialog"></button-common>
      </div>
    </el-dialog>

    <el-dialog title="应聘者" :visible.sync="ypzDialog" class="dialog">
      <el-table ref="ypzTable" :data="ypzData" highlight-current-row style="width: 100%">
        <el-table-column prop="msname" label="姓名" width="100"></el-table-column>
        <el-table-column prop="idcard" label="身份证号" width="290"></el-table-column>
        <el-table-column prop="outsourcName" label="供应商" width="290"></el-table-column>
        <el-table-column prop="" label="操作" width="80">
          <template slot-scope="scope">
            <el-link type="warning" :underline="false" @click="lookYpz(scope.row)">查看</el-link>
          </template>
        </el-table-column>
      </el-table>
      <div class="cloosePre">
        <button-common :buttonName="buttonClose" :size="sizes" :fns="closeDialog"></button-common>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { getCrewPager } from "@/api/userApi/bankUser";
  import common from "@/api/common";
  import pager from "@/components/Pager";
  import cleanBtn from "@/components/CleanBtn";
  import crewDateil from "@/components/Busin/crewDateil";
  import buttonOrange from '@/components/eleButton/buttonOrange'; //引入按钮组件Orange
  import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
  import { queryResourceDept } from "@/api/integrated/wbryzrlc";
  import { getDicEntry } from "@/api/eosDictEntry";
  import { queryPagerList, getApplicantsLisr, getInterviewProgress, exportExcel } from "@/api/integrated/xqgl";
  import helpHint from "@/components/helpHint";
  import Moment from 'moment'
  import { queryOutBaseInfos } from "@/api/integrated/wbryxxbg";
  export default {
    name: 'requireList',
    components: { pager, cleanBtn, crewDateil, buttonOrange, helpHint, buttonCommon },
    data() {
      return {
        totalNumber: 0,
        size: 50, // 初始值 默认：50
        index: 1, // 初始值 默认：1
        paramsForm: { // 搜索参数
          usertype: null, //人员类型
          depid: null,  // 部门编号 
        },
        peoForm_options: [], //人员类型
        crewData: [], // 列表分页数据
        crewDatas: [], // 列表分页数据zb
        drawer: false,  // 默认不显示
        peo: "zh",
        buttonStart: '发起需求', //发起需求按钮
        buttonExport: '导 出', //导出按钮
        sizes: 'small', //按钮大小
        buttonSearch: '查 询', //查询按钮
        buttonClose: '关 闭', //关闭按钮
        deptno: '',
        depData: [], // 部门数据
        deptno_options: [],
        msjdDialog: false, //面试进度弹窗
        ypzDialog: false, //应聘者弹窗
        msjndData: [], //面试进度数据
        ypzData: [], //应聘者数据
      };
    },
    created() {
      this.getDic();
      this.filDep();
    },
    mounted() {
      this.loadInit();
    },
    methods: {
      /**
          初始化
         */
      init: function (params) {
        let data = common.paramsFormat(params);
        getCrewPager(data).then(res => {
          this.crewData = res.data.results;
          this.totalNumber = res.data.total;
        });
      }, // 初始化 end

      /**
       * 分页初始化方法
       */
      loadInit: function () {
        let param = {};
        param.size = this.size;
        param.index = this.index;
        // this.init(param); 原来的
        this.getPagerList(param);
        // let wid = document.body.clientWidth;
      }, // end

      // 查询业务字典
      getDic() {
        getDicEntry({
          dicttypeid: "REQUIRE_PEOPLE_TYPE"
        })
          .then(res => {
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

      // 获取部门
      filDep() {
        let params = {};
        params.objectcode = "6000000300";
        params.objectgrade = "2";
        queryResourceDept(params)
          .then(res => {
            var fild = res.data.results[0].children;
            let name = res.data.results[0].text;
            this.depData = fild.filter(tt => {
              tt.text = tt.text.replace(name + "-", "");
              return tt;
            });;
          })
          .catch(error => {
            this.$message.error(error);
          });
      },

      // 获取列表数据
      getPagerList(param) {
        queryPagerList(param).then(res => {
          if (res.data.status == 200) {
            this.crewDatas = res.data.results;
            this.totalNumber = res.data.total;
          }

        })
      },

      //  时间格式化
      createtimeFormat: function (row, cloumn) {
        let time = '';
        if (row.createdate != null) {
          // time = common.dateFormat(row.createdate, "yyyy年MM月dd日");
          time = Moment(row.createdate).format('YYYY-MM-DD')
        }
        return time;
      },

      //  时间格式化
      msDateFormat: function (row, cloumn) {
        let time = '';
        if (row.msDate != null) {
          // time = common.dateFormat(row.createdate, "yyyy年MM月dd日");
          time = Moment(row.msDate).format('YYYY-MM-DD')
        }
        return time;
      },

      // 部门显示内容
      deptFormat(row, cloumn) {
        let dep = '';
        const reg = /([^-]+)$/;
        if (row.dept != null) {
          dep = row.dept.match(reg)[1];
        }
        return dep;
      },

      // 自定义需求进度表头
      showHelp() {
        return this.$createElement('helpHint', {
          props: {
            content: '通过面试人数/已面试人数',
            name: '需求进度'
          }
        })
      },

      /**
       * 搜索事件
       */
      submitBtn: function () {
        let params = {};
        params.depid = this.paramsForm.depid;
        params.usertype = this.paramsForm.usertype;
        this.getPagerList(params) //bin
      }, // 搜索事件 end

      /**
       * 点击table行
       */
      onSelectTable: function (selection, row) {
        // console.log("selection", selection, row);
      },

      /**
       * 分页触发
       */
      getPager: function (size, index) {
        let params = {};
        params.depid = this.paramsForm.depid;
        params.usertype = this.paramsForm.usertype;
        params.size = size;
        params.index = index;
        this.size = size;
        this.index = index;
        // this.init(params);
        this.getPagerList(params);
      }, // end

      /**
       * 行员详情
       */
      onUserDetail: function (userId) {
        this.drawer = true;
        this.$nextTick(_ => {
          let item = this.$refs.cwdt;
          item.userId = userId
          item.init();
        })

      }, // end

      // 应聘者
      onUser(val) {
        this.ypzDialog = true;
        if (val == undefined) return;
        let params = {};
        params.instanceid = val;
        getApplicantsLisr(params)
          .then(res => {
            if (res.data.status == 200) {
              this.ypzData = res.data.results;
            } else {
              let errorMessage = "获取数据失败"
              this.messageError(errorMessage);
            }
          }).catch(error => {
            this.$message.error(error);
          })
      },

      //面试进度
      reruireProgress(val) {
        this.msjdDialog = true;
        if (val == undefined) return;
        getInterviewProgress(val)
          .then(res => {
            if (res.data.status == 200) {
              this.msjndData = res.data.results;
            } else {
              let errorMessage = "获取数据失败"
              this.messageError(errorMessage);
            }
          }).catch(error => {
            this.$message.error(error);
          })
      },

      // 导出
      handelExport: function () {
        var searchData = this.paramsForm;
        searchData.depid = searchData.depid;
        let params = common.paramsFormat(searchData);
        const self = this;
        exportExcel(params).then(res => {
          // this.paramsForm.depid = 'dep' + this.paramsForm.depid;
          // let currentTime = new Date().getTime();
          // let fileName = common.dateFormatStamp(
          //   currentTime,
          //   "yyyyMMddHHmmssS"
          // );
          // common.download(res.data, self, fileName + "999.xls");
          common.download(res.data, self, "人力需求.xls");
        });
      },

      // 查看应聘者
      lookYpz(val) {
        this.$router.push({
          name: 'msDetail',
          params: { ypzData: val }
        })
      },

      //关闭弹窗
      closeDialog: function () {
        this.msjdDialog = false;
        this.ypzDialog = false;
      },

      // 失败消息提示
      messageError(errorMessage) {
        this.$message.error(errorMessage);
      },

      // 发起需求
      startRequire() {
        this.$router.push({
          name: 'wbrlxq',
          query: {
            formScene: "requireApply"
          }
        });
      }
    }
  };
</script>
<style scoped>
  .requireList {
    padding: 15px;
  }

  .requireList_head {
    /* background-color: #ffffff;
    padding: 18px; */
    background-color: #ffffff;
    padding: 18px;
    display: flex;
    justify-content: space-between;
  }

  .requireContainer {
    background-color: #ffffff;
    margin-top: 15px;
    padding: 18px;
  }

  .requireContainer_head {
    float: right;
    margin-bottom: 10px;
  }

  .requireContainer_head_en {
    width: 160px;
  }

  /* 占位 */
  .occupied {
    width: 8px;
    display: inline-block;
  }

  .requireList_head_right {
    float: right;
  }

  .requireList_head_right div {
    display: inline-block;
  }

  .requireList_head_right_clean {
    margin-right: 10px;
  }

  /* 表格推荐供应商 */
  .recGys {
    border: none;
  }

  /* 分页组件位置 */
  .pager_zb {
    width: 100%;
    display: flex;
    flex-direction: row-reverse;
  }


  /* 弹窗关闭按钮 */
  .cloosePre {
    margin: 15px 0;
    float: right;
  }

  .requireList_head_right_orange {
    margin: 0 6px 0 10px;
  }
</style>
<style>
  /* el-input修改 */
  .requireList .el-input--prefix .el-input__inner {
    padding-left: 50px;
  }

  .requireList .el-input__prefix {
    left: 8px;
    color: #262626;
    font-size: 14px;
  }

  /* element表单修改 */
  .requireList .el-form-item {
    margin-bottom: 0px;
  }

  /* 表头背景色 */
  .requireList .el-table th {
    background: #FAFAFA;
  }

  /* 弹窗 */
  .dialog .el-dialog {
    overflow: hidden;
  }
</style>