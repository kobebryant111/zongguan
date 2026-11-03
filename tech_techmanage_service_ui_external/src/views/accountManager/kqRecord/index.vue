<!-- 非科技外包人员考勤信息-->
<template>
  <div class="record_div">
    <!-- 表单部分 -->
      <div class="form_box">
        <el-form :model="formData" ref="formRef" label-width="80px">
          <el-row type="flex" justify="space-between">
            <el-col :span="10">
              <el-form-item label="考勤日期">
                <el-date-picker type="daterange" v-model="formData.attendance" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" @change="changeAttendance" ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="姓名">
                <el-input v-model="formData.userName" placeholder="请输入姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所属部门">
                <!-- <el-select v-model="form.deptId" placeholder="请选择部门">
                  <el-option v-for="item in depData" :key="item.id" :value="item.id" :label="item.text"></el-option>
                </el-select> -->
                <el-input v-model="formData.deptVal" placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="项目经理">
                <el-input v-model="formData.pmValue" placeholder="请输入项目经理"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-row type="flex" justify="end">
                <clean-btn :size="sizes" :forms="this.formData" />
                <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
              </el-row>
            </el-col>
          </el-row>
        </el-form>
      </div>
        <!-- 表格部分 -->
        <div class="table_box">
          <el-row type="flex" justify="end" class="margin_bottom">
            <button-common :buttonName="buttonExport" :size="sizes_export" :fns="handelExport"></button-common>
          </el-row>
          <el-table :data='recordData' highlight-current-row height="640" @row-dblclick='doubleClickTable'>
            <el-table-column prop="jobId" label="系统编号" align="center" width="200"></el-table-column>
            <el-table-column prop="deptName" label="所属部门" align="center" min-width="300" show-overflow-tooltip></el-table-column>
            <el-table-column prop="userName" label="姓名" align="center" width="200"></el-table-column>
            <el-table-column prop="attendanceDate" label="考勤日期" align="center" width="200"></el-table-column>
            <el-table-column prop="isHoliday" label="是否节假日" align="center" width="200">
              <template slot-scope="scope">
                <span v-show='scope.row.isHoliday == 1'>是</span>
                <span v-show='scope.row.isHoliday == 0'>否</span>
              </template>
            </el-table-column>
            <el-table-column prop="workingTime" label="上班时间" width="200" align="center"></el-table-column>
            <el-table-column prop="closingTime" label="下班时间" width="200" align="center">
              <template slot-scope="scope">
                  <span v-if="scope.row.whetherOvernight === '1'">{{scope.row.closingTime + '+'}}</span>
                  <span v-else>{{scope.row.closingTime}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="proPersonVal" label="项目经理" align="center" width="150"></el-table-column>
          </el-table>
          <!-- 分页 -->
          <div class="pager_zb">
            <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
          </div>
        </div>
    </div>
</template>

<script>
import common from "@/api/common";
import { queryRole} from '@/api/kqManager/wbAttendance';
import { queryPager, exportExcel } from '@/api/accountManager/kqRecord';
import pager from "@/components/Pager";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import Moment from 'moment';
import { getDicEntry } from "@/api/eosDictEntry";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
import { getDownloadUrl } from '@/api/common/flowApi';
export default {
    components: { pager, cleanBtn, buttonOrange, buttonCommon },
    data() {
      return {
        totalNumber: 0, // 总条数
        size: 10, // 每页条数 初始值 默认：5
        index: 1, // 页数 初始值 默认：1
        formData: {
            attendance: [], // 考勤年月
            attendanceStart: '',
            attendanceEnd: '',
            userName: '', // 姓名
            deptVal: '', // 所属部门
            pmValue: '', // 项目经理
        },
        // 部门数据
        depData: [],
        // 表格数据来源
        recordData:[],
        status: '1',
        sizes: 'small', //按钮大小
        buttonSearch: '查 询', //查询按钮
        buttonExport: '导 出', //导出按钮
        sizes_export: 'medium', //按钮大小
        defaultDate: "", // 默认日期
        auth: false,
        kqAuth: false,

      }
    },
    created() {
        // this.getNowTime()
        this.getPermission();
        this.getInit();
    },
    mounted() {
        this.filDep();
    },
    computed: {
    },
    methods: {
    //考勤日期筛选
    changeAttendance(val) {
      console.log('valu',val)
      if (this.formData.attendance != null) {
        this.formData.attendanceStart = this.formData.attendance[0];
        this.formData.attendanceEnd = this.formData.attendance[1];
      } else {
        this.formData.attendanceStart = '';
        this.formData.attendanceEnd = '';
      }
    },
        // 分页初始化
        getInit() {
          let queryParams = [];
          if (window.localStorage.getItem('queryParams') != 'undefined') {
            queryParams = JSON.parse(window.localStorage.getItem('queryParams'));
          }
          console.log('queryParams', queryParams);
          let params = {};
          if(queryParams == null || Object.keys('queryParams').length == 0) {
              params.size = this.size;
              params.index = this.index;
          } else {
              window.localStorage.clear('queryParams')
              this.formData.userName = queryParams.userName;
              this.formData.deptId = queryParams.deptVal;
              this.formData.attendanceStart = queryParams.attendanceStart;
              this.formData.attendanceEnd = queryParams.attendanceEnd;
              this.formData.pmValue = queryParams.pmValue;
              params = queryParams;
          }
          this.getPagerList(params);
        },
        // 获取列表数据
        getPagerList(params) {
          let data = common.paramsFormat(params);
          queryPager(data).then(res => {
            console.log(res, 'res');
            if(res.data.status == 200) {
                this.recordData = res.data.results;
                this.totalNumber = res.data.total;
                if(this.totalNumber <= 0) {
                    return false
                }
            }
          })
        },
        // 查询按钮
        submitBtn() {
            let params = this.formData;
            for(var k in params) {
                if(params[k] && this.index > 1) {
                    this.index = 1
                }
            }
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        /**
         * 分页触发
         */
        getPager: function (size, index) {
            let params = this.formData;
            params.size = size;
            params.index = index;
            this.size = size;
            this.index = index;
            this.getPagerList(params);
        } ,
        //  时间格式化
        createtimeFormat: function (row, cloumn) {
            let time = '';
            if (row.attendanceMonth != null) {
                time = Moment(row.attendanceMonth).format('YYYY-MM')
            }
            return time;
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
        // 导出
        handelExport() {
            var searchData = this.formData;
            // console.log(searchData)
            let params = common.paramsFormat(searchData)
            const self = this;
            // console.log(params,'1212121')
            exportExcel(params).then(res => {
                // console.log(res)
                common.download(res.data, self, '非科技外包人员考勤.xls')
            })
        },
        getFilterData() {
            this.$emit("getFilterData", this.formData)
        },
        // 获取当前年月份
        getNowTime() {
            var now = new Date()
            var year = now.getFullYear()
            var month = now.getMonth() + 1
            month = month.toString().padStart(2, '0')
            this.defaultDate = `${year}-${month}`
            this.$set(this.formData, 'attendance', this.defaultDate)
        },
        // 获取权限
        getPermission() {
            queryRole().then(res => {
                if (res.data.status == 200) {
                    // console.log(res,"获取权限")
                    let roles = res.data.results;
                    let auth = false;
                    let kqAuth = false;
                    roles.forEach(function(item){
                        if(item.roleId == "00000001") {
                            auth = true;
                        }
                        if(item.roleId == 'KQ_YANGUANBU') {
                            kqAuth = true
                        }
                    });
                    this.auth = auth;
                    this.kqAuth = kqAuth;
                    // console.log("auth",this.auth);
                } else {
                    this.$message.error('查询权限失败');
                }
            })
        },
        // 双击表格
        doubleClickTable(val) {
            // console.log(val)
            this.toDetails(val)
      }
    },
}
</script>

<style scoped>
.record_div {
    width: 100%;
    /* height: 100%; */
    padding: 15px;
}
.form_box {
    width: 100%;
    background-color: #ffffff;
    padding: 18px 18px 0 18px;
}
.table_box {
    width: 100%;
    background-color: #ffffff;
    padding: 18px;
}
.margin_bottom {
    margin-bottom: 15px
}
/* 分页组件位置 */
.pager_zb {
    width: 100%;
    display: flex;
    flex-direction: row-reverse;
}
</style>

<style>
.el-button {
    margin-left: 15px
}
.el-row {
    margin-bottom:  15px
}
</style>
