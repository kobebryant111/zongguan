<template>
  <div>
    <div class="typeset-container">
      
      <!-- 列表部分 -->
      <!-- <div ref="contentBody" class="typeset-body"> -->
      <div class="typeset-body">
        <div class="typeset-body-head">
          <div class="typeset-body-head-left">考勤期间管理</div>
        </div>
        <el-table
            v-loading="loading"
            element-loading-text='考勤月数据加载中'
            element-loading-spinner='el-icon-loading'
            element-loading-background='gba(0,0,0,0.8)'
            ref="mulTable"
            :data="monthData"
            stripe
            style="width:50%"
            align="center"
        >
          <el-table-column prop="attendanceDate" label="考勤月份" width="120" align="center"/>
          <el-table-column prop="attendanceStatus" label="考勤状态" width="150" align="center">
            <template slot-scope="scope">
                <span v-if="scope.row.attendanceStatus == '2'">已提交造册</span>
                <span v-if="!scope.row.edited && scope.row.attendanceStatus == '0'">未启动</span>
                <span v-if="!scope.row.edited && scope.row.attendanceStatus == '1'">已启动</span>
                <el-select v-if="scope.row.attendanceStatus != '2' && scope.row.edited" v-model="scope.row.attendanceStatus" 
                style="text-align: center; text-align-last: center"
                >
                    <el-option v-for="s in monthStatus" :key="s.id" :label="s.text" :value="s.id"></el-option>
                </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="120">
            <template slot-scope="scope">
              <!-- <el-link type="warning" :underline="false" @click="onUserDetail(scope.row.badge)">详情</el-link> -->
              <el-link v-if="scope.row.attendanceStatus != '0'"
              style="margin-left:10px"
                type="warning"
                :underline="false"
                @click="edit(scope.row)"
                v-hasFunc="['1333_LICENCE']"
              >查看</el-link
              >
              <el-link  v-if="!scope.row.edited && scope.row.attendanceStatus != '2'" 
              style="margin-left:10px"
                type="warning"
                :underline="false"
                @click="handleUpdate(scope.row)"
                v-hasFunc="['1331_LICENCE']"
              >修改</el-link>
              <el-link v-if="scope.row.edited"
              style="margin-left:10px"
                type="warning"
                :underline="false"
                @click="handleSave(scope.row)"
                v-hasFunc="['1332_LICENCE']"
              >
              保存
              </el-link
              >
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
import PersonTable from '@/api/researchManager/rpersonTable'

export default {
  name: 'attendanceMonthMgr',
  components: {
    buttonOrange,
    cleanBtn,
    pager
  },
  data() {
    return {
        loading:false,
      totalNumber: 0,
      size: 10, // 初始值 默认：10
      index: 1, // 初始值 默认：1
      monthData:[
        {
            month:'2024/02',
            status:'1'
        }
      ],
      monthStatus:[
        {'id':"0",'text':'未启动'},
        {'id':"1",'text':'已启动'},
      ]
    }
  },
  created() {
    this.getInit()
  },
  methods: {
    // 分页初始化
    getInit() {
      const params = {}
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
    },
    // 获取列表数据
    getPagerList(params) {
        this.loading = true
      const self = this
      PersonTable.queryMonthList(params).then(res => {
        this.loading = false
        if (res.data.status == 200) {
          // console.log('true')
          self.monthData = res.data.results
          let i = 0
    
          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            return false
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
     handleUpdate(data) {
      this.$set(data,'edited',true)  
    },
    handleSave(data){
      this.$set(data,'edited',false)
      this.statusChange(data)
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
    detail(data){

        // console.log(data)
        this.$router.push({
            name: "attendanceReview",
            params : {date:data.attendanceDate,editable:false},
        });

    },
    edit(data){
        const params = {}
        params.date = data.attendanceDate
        // console.log(data)
        this.$router.push({
            name: "attendanceFillin",
            params
        });

    },
    statusChange(data){
        // if (!data.changeStatus) {
        //     data.changeStatus = true
        //     return false
        // }

        PersonTable.updateMonthStatus(data).then(res=>{
            if (res.data.status == 200) {
                this.$message.success('状态更新成功')
            }
        })

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


.pager-zj {
  text-align: center;
}
</style>

<style>
.typeset-head .el-form-item {
  margin-bottom: 0;
}
</style>
