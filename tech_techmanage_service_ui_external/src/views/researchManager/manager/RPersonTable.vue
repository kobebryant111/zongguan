<template>
  <div>
    <div class="typeset-container">
      <!-- 查询部分 -->
      <div class="typeset-head">
        <el-form :model="form">
          <el-row :gutter="10">
            <el-col :span="4">
              <el-form-item label="研发部门:" label-width="73px">
                <!-- <el-cascader filterable v-model="form.depid" clearable :options="orgTreeData" :props="defaultProp" @change="orgChange"/> -->
                <el-select v-model="form.depid" filterable clearable placeholder="请选择">
                  <el-option
                    v-for="item in orgDepartment"
                    :key="item.depid"
                    :label="item.location"
                    :value="item.depid">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="姓名:" label-width="73px">
                <el-input v-model="form.empname" placeholder="请输入"/>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="工号:" label-width="73px">
                <el-input v-model="form.badge" placeholder="请输入"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <div style="height: 36px;line-height: 36px;">
                <clean-btn size="small" :forms="this.form" />
                <buttonOrange :fns="submitBtn" style="margin-left: 10px" button-name="查询" size="mini"/>
                <buttonOrange :fns="handleAdd" button-name=" + 新建" size="mini" v-hasFunc="['1314_LICENCE']"/>
                <buttonOrange :fns="submitBtnInfo" button-name="导出" size="mini" v-hasFunc="['1315_LICENCE']"/>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 列表部分 -->
      <!-- <div ref="contentBody" class="typeset-body"> -->
      <div class="typeset-body">
        <div class="typeset-body-head">
          <div class="typeset-body-head-left">研发人员总表</div>
        </div>
        <el-table
          v-loading="loading"
          element-loading-text='数据加载中'
          element-loading-spinner='el-icon-loading'
          element-loading-background='gba(0,0,0,0.8)'
          ref="mulTable"
          :data="crewData"
          style="width: 100%"
          stripe
          align="center"
        >
          <el-table-column label="研发部门" align="center" width="150">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{formatDept(scope.row.chargeName)}}</span>
              <el-input v-if="scope.row.edited" v-model="scope.row.chargeName" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column prop="badge" label="工号" width="120" align="center"/>
          <el-table-column prop="empname" label="姓名" width="120" align="center" />
          <el-table-column prop="depid" v-if="false" label="部门编号" width="120" align="center" />
          <el-table-column prop="userAttr" label="研发人员性质" v-if="!checkFunc(['00000_HIDDEN'])" width="120" align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{getDictName(personAttrDictId,scope.row.nature)}}</span>
              <el-select v-if="scope.row.edited" v-model="scope.row.nature" >
                <el-option
                  v-for="item in getAttrOptions"
                  :key="item.dictId"
                  :label="item.dictName"
                  :value="item.dictId"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="empstatus" label="在岗状态" align="center" width="120">
            <template slot-scope="scope">
              <span v-if="!scope.row.edited">{{ getDictName(empStatusDictId,scope.row.empstatus) }}</span>
              <el-select v-if="scope.row.edited" v-model="scope.row.empstatus">
                <el-option
                  v-for="item in getStatusOptions"
                  :key="item.dictId"
                  :label="item.dictName"
                  :value="item.dictId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column prop="arrivalTime" label="到岗日期" width="120" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.arrivalTime && !scope.row.edited">{{ scope.row.arrivalTime }}</span>
              <span v-if="!scope.row.arrivalTime && !scope.row.edited" class="el-icon-warning red"/>
              <el-date-picker v-if="scope.row.edited" v-model="scope.row.arrivalTime" placeholder="请输入到岗日期" value-format="yyyy-MM-dd"/>
            </template>
          </el-table-column>
          <el-table-column prop="leaveTime" label="离岗日期" width="120" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.leaveTime && !scope.row.edited && scope.row.empstatus !== '0'">{{ scope.row.leaveTime }}</span>
              <span v-if="!scope.row.leaveTime && !scope.row.edited && (scope.row.empstatus !== '0')" class="el-icon-warning red"/>
              <span v-if="!scope.row.edited && (scope.row.empstatus === '0')" class="el-icon-date"/>
              <el-date-picker v-if="scope.row.edited" v-model="scope.row.leaveTime" :picker-options="pickerOptions(scope.row)" type="date" placeholder="请输入离岗日期" value-format="yyyy-MM-dd" />
            </template>
          </el-table-column>
          <el-table-column v-if="checkFunc(['1313_LICENCE'])" label="操作" width="120" >
            <template slot-scope="scope">
              <!-- <el-link type="warning" :underline="false" @click="onUserDetail(scope.row.badge)">详情</el-link> -->
              <el-link
                v-hasFunc="['1311_LICENCE']"
                v-if="!scope.row.edited"
                :underline="false"
                type="warning"
                @click="handleUpdate(scope.row)"
              >
                编辑
              </el-link
              >
              <el-link  v-if="scope.row.edited"
                type="warning"
                :underline="false"
                @click="handleCancel(scope.row)"
                
              >
              取消
              </el-link
              >
              
              <el-link v-if="scope.row.edited"
                type="warning"
                :underline="false"
                @click="handleSave(scope.row)"
                v-hasFunc="['1312_LICENCE']"
              >
              保存
              </el-link
              >
              <el-link
                style="margin-left:10px"
                type="warning"
                :underline="false"
                @click="handleDel(scope.row)"
                v-hasFunc="['1313_LICENCE']"
              >删除</el-link>

              <el-link
                v-hasFunc="['1316_LICENCE']"
                v-if = "!scope.row.depted"
                :underline="false"
                style="margin-left:10px"
                type="warning"
                @click="handleJump(scope.row)"
              >部门调整</el-link>

              <el-link
                :underline="false"
                style="margin-left:10px"
                type="warning"
              >
                <el-select v-if = "scope.row.depted" v-model="scope.row.depid" placeholder="请选择" @change="deptedited(scope.row)">
                  <el-option
                    v-for="item in orgDepartments"
                    :key="item.depid"
                    :label="item.location"
                    :value="item.depid"/>
                </el-select>
              </el-link>

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
import PersonTable from '@/api/researchManager/rpersonTable'
import {getDictName ,getTypeDicts} from '@/utils/dict'

export default {
  name: 'RPersonTable',
  components: {
    buttonOrange,
    cleanBtn,
    pager
  },
  data() {
    return {
      empStatusDictId: 'NEWKG_RD_EMPSTATUS',
      personAttrDictId: 'NEWKG_RD_NATURE',
      totalNumber: 0,
      loading: true,
      orgTreeData: [],
      orgDepartment: [],
      orgDepartments: [],
      selectedData: [],
      size: 10, // 初始值 默认：10
      index: 1, // 初始值 默认：1
      crewData: [],
      form: {},
      depData: [], // 部门数据
      defaultProp: {
        expandTrigger: 'hover',
        value: 'id',
        label: "text"
      }
    }
  },
  created() {
    let param = {}
    PersonTable.queryOrgTree(param).then(res =>{

        if(res.data.status == 200){
            this.orgTreeData = res.data.results[0].children
        } else {
            this.$message.error(res.data.message)
        }

    })
    PersonTable.quertOrgDepartment().then(res=>{
      if (res.data.status == 200) {
        this.orgDepartment = res.data.results
        this.orgDepartments = res.data.results.map(item => ({
          ...item,
          depid: parseInt(item.depid)
        }))
      } else {
        this.$message.error(res.data.message)
      }
    })
  },
  mounted() {
    this.getInit()
  },
  computed: {
    formatDept(){

      return (deptName) => {
          return deptName
      }
        
    },

    formatStatus(){
      return (status) => {

      }
    },

    getStatusOptions(){
      return getTypeDicts(this.empStatusDictId)
    },

    getAttrOptions(){
      return getTypeDicts(this.personAttrDictId)
    }

  },
  methods: {
    // 日期选择
    pickerOptions(data) {
      return {
        disabledDate(time) {
          return time < new Date(data.arrivalTime)
        }
      }
    },
    // 分页初始化
    getInit() {
      this.getPager(this.size, this.index)
    },

    // 获取列表数据
    getPagerList(params) {
      this.loading = true
      const self = this
      PersonTable.queryPersonList(params).then(res => {
        this.loading = false
        if (res.data.status == 200) {

          self.crewData = res.data.results
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
    submitBtnInfo() {
      this.$confirm('是否导出全部研发人员表？','研发人员表导出确认',{confirmButtonText:'是',cancelButtonText:'否',type:'warning'}).then(_=>{
        const self = this;
        const data = {size: 10, index: 1,_isDown:'Y'}
        PersonTable.queryPersonListInfo(data).then(res => {
        
            common.download(res.data,self,"研发人员表.xls")
        })
      })
    },
    // 查询
    submitBtn() {
      
      const params = {...this.form}
      this.index = 1
      this.getPager(this.size,this.index)
    },
    // 分页
    getPager: function(size, index) {
      
      const params ={...this.form} 
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    handleAdd() {
      this.$router.push({
        name: "RPersonEdit",
      });
    },
    handleUpdate(data) {
      this.$set(data, 'edited', true)
    },
    handleSave(data) {
      this.$set(data, 'edited', false)
      if (data.arrivalTime === undefined || data.arrivalTime === null) {
        this.$message.error('请选择到岗日期')
        this.submitBtn()
        return
      }
      if (data.empstatus !== '0' && (data.leaveTime === undefined || data.leaveTime === null)) {
        this.submitBtn()
        this.$message.error('请选择离岗日期')
        return
      }
      PersonTable.updatePerson(data).then(res => {
        this.$message({ type: 'success', message: '编辑成功！' })
        this.submitBtn()
      }).catch(() => {
        this.$message({ type: 'error', message: '编辑失败！' })
      })
    },
    handleCancel(data){
      this.$set(data,'edited',false)
    },
    handleDel(val){
      
      this.$confirm('是否要删除'+val.empname+'吗？','删除确认',{confirmButtonText:'确定',cancelButtonText:'取消',type:'warning'})
      .then(()=>{

        PersonTable.deletePerson({badge:val.badge}).then(res=>{

          if(res.data.status == 200){
              
            this.$message({type:'success',message:'删除成功！'})
            this.getPager(this.size,this.index)

          } else {

            this.$message.error(res.data.message)

          }

        })

      })
    },
    // 部门调整
    deptedited(val) {
      // const item = this.orgDepartments.find(i => i.depid === val.depid)
      // val.chargeName = item ? item.location : null
      // console.log(val, '********************')
      PersonTable.depidJump(val).then(res => {
        this.$message({ type: 'success', message: '部门调整成功！' })
        this.submitBtn()
      }).catch(() => {
        this.$message({ type: 'error', message: '部门调整失败！' })
      })
    },
    // 部门调整
    handleJump(data) {
      this.$set(data, 'depted', true)
    },
    orgChange(value){

            if(value != null){

                this.form.depid = value[value.length-1]
                
            }
            else {
                this.form.depid = ''
            }
            
            console.log(this.paramForm);

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
