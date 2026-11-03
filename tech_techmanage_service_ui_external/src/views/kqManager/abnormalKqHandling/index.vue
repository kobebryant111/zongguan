<template>
  <div>
    <div class="typeset-container">
      <!-- 查询部分 -->
      <div class="typeset-head">
        <el-form :model="form">
          <el-row :gutter="10">
            <el-col :span="4">
              <el-form-item label="考勤年月:" label-width="73px">
                <el-date-picker v-model="form.attendanceDateValue" type="month" value-format="yyyy-MM"/>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="系统编号:" label-width="73px">
                <el-input v-model="form.jobId"/>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="姓名:" label-width="73px">
                <el-input v-model="form.userName"/>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="部门:" label-width="73px">
                <el-select v-model="form.deptId" filterable placeholder="请选择部门">
                  <el-option
                    v-for="item in depData"
                    :key="item.id"
                    :value="item.id"
                    :label="item.text"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="是否处理" label-width="73px">
                <el-select v-model="form.whetherHandle" filterable>
                  <el-option
                    v-for="item in dealOption"
                    :key="item.id"
                    :value="item.whetherHandle"
                    :label="item.label"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <div style="height: 36px;line-height: 36px;text-align: right;">
                <clean-btn :size="sizes" :forms="this.form" />
                <buttonOrange :fns="submitBtn" style="margin-left: 10px" button-name="查询" size="mini"/>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 列表部分 -->
      <!-- <div ref="contentBody" class="typeset-body"> -->
      <div class="typeset-body">
        <div class="typeset-body-head">
          <div class="typeset-body-head-left">异常处理(<span style="color: red;width: 200px;">修改异常原因时请填写备注</span>)</div>
          <div>
            <el-button type="warning" @click="btnOk">确 认</el-button>
            <el-button type="warning" @click="brak">异常状态刷新</el-button>
            <el-button v-if="btnBackif" type="warning" @click="btnBack">回 退</el-button>
          </div>
        </div>
        <el-table
          ref="mulTable"
          :data="crewData"
          style="width: 100%"
          stripe
          align="center"
          @selection-change="handleSelection"
        >
          <el-table-column type="selection" width="50" />
          <el-table-column v-if="false" label="序号" align="left" type="index"/>
          <el-table-column prop="attendanceDate" label="考勤日期" width="120" align="center"/>
          <el-table-column prop="deptName" label="部门" width="120" align="center">
            <template slot-scope="scope">
              {{ scope.row.deptName }}
            </template>
          </el-table-column>
          <el-table-column prop="jobId" label="系统编号" width="120" align="center"/>
          <el-table-column prop="userName" label="姓名" align="center" width="120"/>
          <el-table-column prop="amPunchTime" label="上班时间" align="center" width="120"/>
          <el-table-column prop="pmPunchTime" label="下班时间" align="center" width="120">
            <template slot-scope="scope">
              <span v-if="scope.row.whetherOvernight == null || scope.row.whetherOvernight == '0'">{{ scope.row.pmPunchTime }}</span>
              <span v-else>{{ '+' }}</span>
            </template>
          </el-table-column>
          <el-table-column v-if="false" prop="whetherAbnormal" label="是否异常" width="120">
            <template slot-scope="scope">
              <el-select :disabled="scope.row.whetherHandle === '2' || scope.row.whetherHandle === '4'" v-model="scope.row.whetherAbnormal" @change="handleSelectAbnormal(scope.row)">
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="abnormalReason" label="异常原因" width="150">
            <!-- @focus="reasonDisabled(scope.row.abnormalReason)"
                      @change="resonChanage(scope.row)" -->
            <template slot-scope="scope">
              <el-select
                :disabled="scope.row.whetherHandle === '2' || scope.row.whetherHandle === '4' || scope.row.whetherAbnormal === '0' ? true : false "
                v-model="scope.row.abnormalReason"
              >
                <el-option
                  v-for="item in scope.row.abnormalReasons"
                  :key="item.dictid"
                  :label="item.dictname"
                  :value="item.dictid"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="备注" width="300" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" :disabled="scope.row.whetherHandle === '2' || scope.row.whetherAbnormal === '0' || scope.row.whetherHandle === '4' ? true : false "/>
            </template>
          </el-table-column>
          <el-table-column prop="whetherHandle" label="处理结果" width="120">
            <template slot-scope="scope">
              <!-- <span>{{ scope.row.whetherHandle == 1 ? '未处理' : '已处理'}}</span> -->
              <span v-if="scope.row.whetherHandle == '0'">待处理</span>
              <span v-else-if="scope.row.whetherHandle == '1'">未处理</span>
              <span v-else-if="scope.row.whetherHandle == '2'">已处理</span>
              <span v-else-if="scope.row.whetherHandle == '3'">3不处理</span>
            </template>
          </el-table-column>

        </el-table>
        <div class="pager-zj">
          <pager
            ref="pager"
            :fns="getPager"
            :total="totalNumber"
            :page-size="size"
            :current-page="index"
            v-model="$attrs"
          />
        </div>
      </div>
    </div>
  </div>
  <!-- </div> -->
</template>

<script>
import buttonOrange from '@/components/eleButton/buttonOrange'
import cleanBtn from '@/components/CleanBtn'
import pager from '@/components/Pager'
import common from '@/api/common'
import { queryRecordPager, updateRecordList, cipherRecordList, backRecordList } from '@/api/kqManager/abnormalKqHandling'
import moment from 'moment'
import { queryResourceDept } from '@/api/integrated/wbryzrlc'
import { getDicEntry } from '@/api/eosDictEntry'

export default {
  name: 'AbnormalKqHandling',
  components: {
    buttonOrange,
    cleanBtn,
    pager
  },
  data() {
    return {
      totalNumber: 0,
      size: 10, // 初始值 默认：10
      index: 1, // 初始值 默认：1
      flag: true,
      btnBackif: false,
      crewData: [],
      form: {
        attendanceDateValue: '', // 考勤年月
        jobId: '', // 工号
        userName: '', // 姓名
        deptId: '', // 部门
        whetherHandle: '' // 是否处理
      },
      sizes: 'small',
      depData: [], // 部门数据
      options: [
        {
          id: 0,
          label: '否',
          value: '0'
        },
        {
          id: 1,
          label: '是',
          value: '1'
        }
      ],
      dealOption: [
        {
          id: 1,
          label: '否',
          whetherHandle: '1'
        },
        {
          id: 2,
          label: '是',
          whetherHandle: '2'
        }
      ],
      optionsAbnormalReason: [],
      abnormalReasons: [],
      isDisable: null,
      multipleSelection: []
    }
  },
  created() {
    this.getInit()
    this.filDep()
    this.getDic()
    this.form.whetherHandle = this.dealOption[0].whetherHandle
    // this.reasonDisabled();
  },
  methods: {
    // 分页初始化
    getInit() {
      const params = {}
      params.size = this.size
      params.index = this.index
      params.whetherHandle = 1
      this.getPagerList(params)
    },
    // 获取列表数据
    getPagerList(params) {
      const data = common.paramsFormat(params)
      const self = this
      queryRecordPager(data).then(res => {
        // console.log(res, "cccccccccccccccccccccccccccccc");
        if (res.data.status == 200) {
          // console.log('true')
          self.crewData = res.data.results
          let i = 0
          self.crewData.forEach(item => {
            item.index = ++i
            item.remarkReason = item.abnormalReason
          })
          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            return false
          }
          if (params.whetherHandle == 2) {
            this.btnBackif = true
          } else {
            this.btnBackif = false
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 查询
    submitBtn() {
      console.log(this.form, '查询')
      const params = this.form
      for (var k in params) {
        if (params[k] && this.index > 1) {
          this.index = 1
        }
      }
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
    },
    // 分页
    getPager: function(size, index) {
      // console.log(getPager,'分页')
      const params = this.form
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    // 考勤异常刷新
    brak() {
      if (this.multipleSelection.length === 0) {
        this.$message.error('请至少选中一行')
        return
      }
      this.$confirm('确定刷新人员异常状态？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = this.multipleSelection
        // for (const item of data) {
        //   if (item.WORKING_TIME === '1' && item.abnormalReason.length == '0') {
        //     return this.$message.info('上班时间')
        //   }
        // }
        cipherRecordList(data).then(res => {
          this.$message.success('成功刷新' + this.multipleSelection.length + '条异常状态，状态转为正常的条数为' + res.data.results + '条。')
          const params = this.form
          params.size = this.size
          params.index = this.index
          this.getPagerList(params)
        }).catch(error => {
          this.$message.error(error)
        })
      })
    },
    btnBack() {
      if (this.multipleSelection.length === 0) {
        this.$message.error('请至少选中一行')
        return
      }
      this.$confirm('确认执行操作吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = this.multipleSelection
        for (const item of data) {
          if (item.whetherHandle === '1') {
            return this.$message.info('所选数据包含未处理记录，不允许回退')
          }
        }
        backRecordList(data).then(res => {
          if (res.data.status === 200) {
            this.$message.success('回退成功')
            const params = this.form
            params.size = this.size
            params.index = this.index
            this.getPagerList(params)
          } else {
            this.$message.error(res.data.message)
          }
        })
      })
    },
    btnOk() {
      // debugger
      // console.log('btnOk');
      if (this.multipleSelection.length == 0) {
        this.$message.error('请至少选中一行')
        return
      }
      this.$confirm('确认执行操作吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = this.multipleSelection

        for (const item of data) {
          // console.log("异常原因",item.whetherAbnormal,"--*---*--*--*---*---*",item.abnormalReason);
          if (item.whetherAbnormal === '1' && item.abnormalReason.length == '0') {
            return this.$message.info('请填写异常原因')
          }

          // console.log("备注",item.abnormalReason != item.remarkReason,"&& ",item.remark == undefined);
          if (item.abnormalReason != item.remarkReason && (item.remark == undefined || item.remark.length == '0')) {
            return this.$message.error('异常原因修改时请填写备注')
          }

          if (item.whetherHandle === '2' || item.whetherHandle === '4') {
            return this.$message.error('所选数据包含已处理记录，不允许再次处理')
          }
        }

        for (const item of data) {
          if (item.abnormalReason == '3' || item.abnormalReason == '11' || item.abnormalReason == '15' || item.abnormalReason == '17') {
            item.whetherAbnormal = '0'
          }
        }

        updateRecordList(data).then(res => {
          // console.log(res)
          if (res.data.status == 200) {
            this.$message.success('确认成功')
            const params = this.form
            params.size = this.size
            params.index = this.index
            this.getPagerList(params)
          } else {
            for (const item of data) {
              item.whetherAbnormal = '1'
            }
            this.$message.error(res.data.message)
          }
        }).catch((error) => {
          for (const item of data) {
            item.whetherAbnormal = '1'
          }
          this.$message.error(error)
        })
      }).catch(() => {})
    },
    handleSelection(val) {
      console.log(val)
      this.multipleSelection = val
    },
    // 格式化时间
    // formaData(row, cellValue) {
    //   let s = moment(row.workingTime).format('YYYY-MM-DD')
    //   return s;
    // },
    // 获取部门
    filDep() {
      // console.log('filDep!!!!!!!!!!!!!!!!!!!!!!!!!')
      const params = {}
      params.objectcode = '6000000300'
      params.objectgrade = '2'
      queryResourceDept(params)
        .then(res => {
          var fild = res.data.results[0].children
          const name = res.data.results[0].text
          this.depData = fild.filter(tt => {
            tt.text = tt.text.replace(name + '-', '')
            return tt
          })
        }).catch(error => {
          this.$message.error(error)
        })
    },
    handleSelectAbnormal(row) {
      // console.log(row)
      row.abnormalReasonList = []
    },
    // reasonDisabled(val) {
    //   console.log("val---------",val)
    //   if (val === '0') {
    //     let option = this.optionsAbnormalReason;
    //     console.log("option",option,val)
    //     option.then(res => {
    //       if (res.dictid == 0 || res.dictid == 5){
    //         res.disabled = false;
    //       }else{
    //         res.disabled = true;
    //       }
    //     })
    //     this.abnormalReasons = option;
    //     console.log("this.abnormalReasons",this.abnormalReasons)
    //   }else if (val === '1') {
    //     let option = []
    //     option =  this.optionsAbnormalReason;
    //     console.log("option",option,val)
    //     option.forEach(res => {
    //       if (res.dictid == 1 || res.dictid == 5 || res.dictid == 11){
    //         res.disabled = false;
    //       }else{
    //         res.disabled = true;
    //       }
    //     })
    //     // this.abnormalReasons = option;
    //     this.$set(this,'abnormalReasons',option);
    //     console.log("this.abnormalReasons",this.abnormalReasons)
    //   }else if (val === '2') {
    //     let option = []
    //     option = this.optionsAbnormalReason;
    //     option.forEach(res => {
    //       if (res.dictid == 2 || res.dictid == 5){
    //         res.disabled = false;
    //       }else{
    //         res.disabled = true;
    //       }
    //     })
    //     this.abnormalReasons = option;
    //     console.log("this.abnormalReasons",this.abnormalReasons)
    //   }else if (val == undefined) {
    //     let option = this.optionsAbnormalReason;
    //     console.log("option",option,val)
    //     option.forEach(res => {
    //       res.disabled = false;
    //     })
    //     // this.abnormalReasons = option
    //     this.$set(this,'abnormalReasons',option);
    //     console.log("this.abnormalReasons",this.abnormalReasons)
    //   }

    //   this.$forceUpdate();
    // },
    // 获取字典
    getDic() {
      getDicEntry({ dicttypeid: 'KQ_RECORD_REASON_DICT' }).then(res => {
        console.log(res, '字典')
        const { results, status } = res.data
        if (status == 200) {
          this.optionsAbnormalReason = results
        }
      })
    },
    selected(row, index) {
      if (row.whetherHandle === '1') {
        return true
      }
      if (row.whetherHandle === '2' || row.whetherHandle === '4') {
        return false
      }
    }
  }
}
</script>

<style scoped>
.typeset-container {
  padding: 10px;
}

.typeset-head {
  background-color: #ffffff;
  padding: 10px;
}

.typeset-body {
  padding: 10px 15px 15px 15px;
  background-color: #ffffff;
  /* height: calc(100vh - 262px); */
  border-radius: 4px;
  margin-top: 10px;
}

.typeset-body-head {
  position: relative;
  padding-right: 36px;
  display: flex;
  justify-content: space-between;
  /* margin-bottom: 15px; */
}

.typeset-body-head-left {
  height: 36px;
  line-height: 36px;
  font-weight: 700;
  font-size: 16px;
  color: #48465b;
}

.typeset-body-head-right-icon {
  display: inline-block;
  height: 28px;
  width: 2px;
  position: absolute;
}

.typeset-body-head-right-icon .icon-kqjt {
  height: 100%;
  width: 100%;
  cursor: pointer;
}

.arrowTransform {
  transition: transform 0.5s;
  -moz-transition: -moz-transform 0.5s;
  -webkit-transition: -webkit-transform 0.5s;
  -o-transition: -o-transform 0.5s;
  transform: rotateZ(180deg);
  -moz-transform: rotateZ(180deg);
  -webkit-transform: rotateZ(180deg);
  -o-transform: rotateZ(180deg);
}

.arrowTransformReturn {
  transition: transform 0.5s;
  -moz-transition: -moz-transform 0.5s;
  -webkit-transition: -webkit-transform 0.5s;
  -o-transition: -o-transform 0.5s;
  transform: rotateZ(0deg);
  -moz-transform: rotateZ(0deg);
  -webkit-transform: rotateZ(0deg);
  -o-transform: rotateZ(0deg);
}

.pager-zj {
  text-align: center;
}
</style>

<style>
.typeset-head .el-form-item {
  margin-bottom: 0;
}
</style>
