<template>
  <div>
    <!-- 头部 -->
    <div class="top">
      <el-row type="flex" justify="space-between" align="center">
        <el-col :span="22">
          <h2>关联管理</h2>
        </el-col>
        <el-col :span="2">
          <el-button style="margin-top: 5px" @click="toClose">关闭</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 合同详情 -->
    <div class="marginWrapper">
      <h3>合同详情</h3>
      <el-form ref="formData" :model="formData">
        <el-row type="flex" justify="space-between">
          <el-col :span="4">
            <el-form-item label="供应商名称">
              <el-input v-model="formData.supplierName" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="合同编号">
              <el-input v-model="formData.contractNo" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="合同名称">
              <el-input v-model="formData.contractName" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4"/>
        </el-row>
        <el-row type="flex" justify="space-between">
          <el-col :span="4">
            <el-form-item label="初级开发人数">
              <el-input v-model="formData.juniorNum" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="中级开发人数">
              <el-input v-model="formData.intermediateNum" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="高级开发人数">
              <el-input v-model="formData.seniorNum" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="资深开发人数">
              <el-input v-model="formData.expertNum" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 关联关系 -->
    <div class="marginWrapper">
      <h3>关联人员</h3>
      <el-row type="flex" justify="end" class="margin_bottom">
        <el-col :span="12">
          <el-button
            :disabled="selectedRows.length === 0"
            size="small"
            type="danger"
            icon="el-icon-delete"
            @click="batchDelete">
            批量删除 ({{ selectedRows.length }})
          </el-button>
        </el-col>
        <el-col :span="12" style="text-align: right;">
          <el-button size="small" @click="saveOutsource">新增关联</el-button>
        </el-col>
      </el-row>
      <el-table ref="multipleTable" :data="tableData" :max-height="clienHeight" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column prop="jobId" label="工号" fixed width="100px" align="center" />
        <el-table-column prop="userName" label="姓名" width="100px" align="center"/>
        <el-table-column prop="levelName" label="级别" width="100px" align="center"/>
        <el-table-column prop="price" label="单价" width="100px" align="center"/>
        <el-table-column prop="workingTime" label="上班时间" width="100px" align="center"/>
        <el-table-column prop="closingTime" label="下班时间" width="100px" align="center"/>
        <el-table-column prop="workBeginDate" label="起始时间" width="105px" align="center"/>
        <el-table-column prop="workEndDate" label="截止时间" width="115px" align="center"/>
        <el-table-column prop="relateState" label="关联状态" width="105px" align="center" >
          <template slot-scope="scope">
            <span >{{ scope.row.relateState === 'Y' ? "有效" : '失效' }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="80"
          align="center"
        >
          <template slot-scope="scope">
            <el-link v-if="scope.row.relateState === 'Y'" :underline="false" type="warning" @click="expireRow(scope.row)">失效</el-link>
            <el-link :underline="false" type="warning" @click="delRow(scope.row)">删除</el-link>
          </template>
        </el-table-column>
      </el-table>

      <!-- 新增外包关联弹框 -->
      <el-dialog :visible="dialogVisible" :before-close="handleClose" title="新建关联外包人员" >
        <el-form
          ref="outsourceFormRef"
          :model="outsourceForm"
          :rules="rules"
          :inline="true"
          label-position="left"
          label-width="100px">
          <el-form-item label="外包人员" prop="jobId">
            <el-select
              v-model="outsourceForm.jobId"
              placeholder="请选择"
              filterable
              style="width: 100%;"
              @change="handlePersonChange"
            >
              <el-option
                v-for="item in outsource_options"
                :label="labelName(item)"
                :key="item.jobId"
                :value="item.jobId">
                {{ item.userName }}({{ item.jobId }})
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item style="width: 100%; margin: 0;"/>
          <el-form-item label="级别" prop="evaluationLevel">
            <el-select v-model="outsourceForm.evaluationLevel" placeholder="请选择级别" value-key="dictid" style="width: 100%;" @change="handleLevelChange">
              <el-option
                v-for="item in level_options"
                :key="item.dictid"
                :label="item.dictname"
                :value="item.dictid"
              />
            </el-select>
          </el-form-item>
          <el-form-item style="width: 100%; margin: 0;"/>
          <el-form-item label="单价">
            <el-input-number v-model="outsourceForm.price" :min="0" :precision="2" size="medium" style="width: 90%;"/>
          </el-form-item>
          <el-form-item style="width: 100%; margin: 0;"/>
          <el-form-item label="工作时间">
            <el-input v-model="outsourceForm.workTime" placeholder="工作时间" size="medium" style="width: 115%;" disabled/>
          </el-form-item>
          <el-form-item style="width: 100%; margin: 0;"/>
          <el-form-item label="起始时间" prop="workBeginDate">
            <el-date-picker
              v-model="outsourceForm.workBeginDate"
              :picker-options="pickerOptions"
              placeholder="请选择开始日期"
              type="date"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>
          <el-form-item label="截止时间">
            <el-date-picker
              v-model="outsourceForm.workEndDate"
              :picker-options="pickerOptions"
              placeholder="请选择结束日期"
              type="date"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>
          <el-form-item style="width: 100%; margin: 0;"/>
          <el-form-item label="关联状态">
            <el-tag :type="outsourceForm.relateState === 'Y' ? 'success' : 'info'">
              {{ outsourceForm.relateState === 'Y' ? '有效' : '失效' }}
            </el-tag>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="submitClearBtn">重 置</el-button>
          <el-button type="primary" @click="submitSaveBtn">提 交 保 存</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { queryOutsourceList, deleteOutsource, addOutsource, removeOutsource } from '@/api/supplierContract/contractManage'
import { getDicEntry } from '@/api/eosDictEntry'
import { presenceInfo } from '@/api/xxbg/index'
import Moment from 'moment'

export default {
  data() {
    return {
      id: '',
      formData: { // 合同详情
        supplierId: '', // 供应商名称
        contractName: '', // 合同名称
        juniorNum: '', // 初级开发人数
        intermediateNum: '', // 中级开发人数
        seniorNum: '', // 高级开发人数
        expertNum: '' // 资深级开发人数
      },
      tableData: [], // 关联关系
      dialogVisible: false, // 弹框
      selectPerson: {}, // 选中外包
      outsource_options: [], // 在场外包人员花名册
      outsourceForm: {
        jobId: '', // 工号
        userName: '', // 姓名
        evaluationLevel: '', // 级别
        price: '', // 级别单价
        workTime: '', // 工作时间
        workBeginDate: '', // 起始时间
        workEndDate: '', // 截止时间
        relateState: 'Y', // 关联状态
        relateId: '', // 关联合同序号
        levelName: '' // 级别名称
      },
      clienHeight: '',
      pickerOptions: '',
      level_options: [], // 动态级别
      all_level_options: [], // 所有级别
      selectedRows: [], // 存储选中的行数据
      rules: { // 校验规则
        jobId: [
          { required: true, message: '请选择开发人员', trigger: 'change' }
        ],
        evaluationLevel: [
          { required: true, message: '请选择级别', trigger: 'change' }
        ],
        workBeginDate: [
          { required: true, message: '请选择起始时间', trigger: 'blur' }
        ]
      }

    }
  },
  computed: {

  },
  created() {
    this.id = this.$route.query.id || ''
    this.getInit()
    this.getpresenceInfo()
    this.clienHeight = document.body.clientHeight - 330
  },
  methods: {
    labelName(item) {
      return item.userName + '(' + item.jobId + ')'
    },
    // 初始化
    getInit() {
      getDicEntry({
        dicttypeid: 'EVALUATION_LEVEL'
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        const data = res.data.results
        // 级别
        this.all_level_options = data.filter(item => {
          return item.dicttypeid == 'EVALUATION_LEVEL'
        })
        // 获取关联列表
        this.getOutsourceList()
        return data
      })
    },
    // 获取合同详情及关联外包人员
    getOutsourceList() {
      this.tableData = []
      const params = {}
      params.id = this.id
      // params.relateState = 'Y';
      queryOutsourceList(params).then(res => {
        if (res.data.status == 200) {
          this.formData = res.data.results

          this.level_options = this.all_level_options
          // 未设置级别人数不展示对应级别选项
          this.level_options = this.level_options.filter(item => {
            // 动态获取对应的人数字段，如果该字段有值则保留
            const numValue = this.formData[item.dictid + 'Num']
            return !!numValue
          })

          if (res.data.results.outsourceList && res.data.results.outsourceList.length > 0) {
            this.tableData = res.data.results.outsourceList.map((item, index) => {
              // 级别映射处理
              const levelValue = this.all_level_options.find(ele => ele.dictid === item.evaluationLevel) || {}
              item.levelName = levelValue.dictname
              // 日期格式处理
              item.workBeginDate = Moment(item.workBeginDate).format('YYYY-MM-DD')
              if (item.workEndDate) {
                item.workEndDate = Moment(item.workEndDate).format('YYYY-MM-DD')
              } else {
                const beginDate = Moment(item.workBeginDate)
                const nowDate = Moment()
                // 当前时间大于等于开始时间
                item.workEndDate = (nowDate.isSameOrAfter(beginDate, 'day')) ? nowDate.format('YYYY-MM-DD') : ''
              }
              // 工作时间处理
              if (item.workTime) {
                const timeArr = item.workTime.split('-')
                item.workingTime = (timeArr[0] || '').trim()
                item.closingTime = (timeArr[1] || '').trim()
              }

              return item
            })
          }
        } else {
          this.$message.error('获取数据失败')
        }
      })
    },
    // 新增关联外包
    saveOutsource() {
      this.dialogVisible = true
      this.$nextTick(() => {
        // 注意：这里调用 validate 不传回调函数，或者传一个空回调，
        // 主要是为了触发表单项的 error 显示状态。
        // 如果 validate 返回 false，说明有错误，UI 上会显示红字。
        this.$refs.outsourceFormRef.validate((valid) => {

        })
      })
    },
    // 关联外包重置
    submitClearBtn() {
      this.outsourceForm = {}
      this.outsourceForm.relateState = 'Y'
    },
    // 获取外包人员变更信息
    getpresenceInfo() {
      presenceInfo({ presenceState: '1' }).then(res => {
        if (res.data.status === 200) {
          this.outsource_options = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 选择外包人员
    handlePersonChange(jobId) {
      this.outsourceForm = {}
      const outsource = this.outsource_options.find(ele => ele.jobId === jobId) || {}
      console.log('选中外包人员关联', outsource)

      if (outsource) {
        // 展示外包人员基本信息
        this.outsourceForm.jobId = outsource.jobId
        this.outsourceForm.userName = outsource.userName
        this.outsourceForm.relateState = 'Y'
        // 工作时间处理
        if (outsource.workTime) {
          const timeStr = String(outsource.workTime)
          const parts = timeStr.split('-')
          if (parts.length === 2) {
            this.outsourceForm.workTime = `${parts[0].trim()} - ${parts[1].trim()}`
          } else {
            this.outsourceForm.workTime = timeStr
          }
        }
      }
    },
    handleLevelChange(level) {
      const priceMap = {
        junior: this.formData.juniorPrice,
        intermediate: this.formData.intermediatePrice,
        senior: this.formData.seniorPrice,
        expert: this.formData.expertPrice
      }
      this.outsourceForm.price = priceMap[level]
    },
    // 合同管理新增保存
    submitSaveBtn() {
      this.outsourceForm.relateId = this.id
      this.$refs.outsourceFormRef.validate((valid) => {
        if (valid) {
          // 校验通过，执行保存逻辑
          if (this.outsourceForm.workBeginDate && this.outsourceForm.workEndDate) {
            const startTimestamp = new Date(this.outsourceForm.workBeginDate).getTime()
            const endTimestamp = new Date(this.outsourceForm.workEndDate).getTime()
            if (startTimestamp > endTimestamp) {
              this.$message.error('起始时间不得晚于截止时间')
              return
            }
          }
          if (this.outsourceForm.workBeginDate) {
            const startTimestamp = new Date(this.outsourceForm.workBeginDate).getTime()
            const nowTimestamp = new Date().getTime()
            if (startTimestamp > nowTimestamp && !this.outsourceForm.workEndDate) {
              this.$message.error('请选择截止时间')
              return
            }
          }
          // 还原时间格式
          if (this.outsourceForm.workTime) {
            this.outsourceForm.workTime = this.outsourceForm.workTime.replace(/\s*-\s*/g, '-')
          }

          addOutsource(this.outsourceForm).then(res => {
            if (res.data.status == 200) {
              this.getOutsourceList()
              this.$message.success(res.data.message)
              this.handleClose()
            } else {
              this.$message.error(res.data.message)
            }
          })
        } else {
          // 校验不通过，提示用户
          this.$message.warning('请填写必填项')
          return false
        }
      })
    },
    // 新增：监听多选变化
    handleSelectionChange(val) {
      this.selectedRows = val
    },
    isRowSelectable(row) {
      return row.relateState === 'Y'
    },
    // 执行失效（公共方法）
    executeRow(items) {
      const params = {
        list: items
      }
      return deleteOutsource(params).then(res => {
        if (res.data.status === 200) {
          this.selectedRows = []
          this.getOutsourceList()
          this.$message.success(res.data.message || '失效成功')
        } else {
          this.$message.error(res.data.message)
        }
      }).catch(error => {
        console.error('失效失败:', error)
        this.$message.error('网络异常，请稍后重试')
      })
    },
    // 执行删除（公共方法）
    executeDelete(items) {
      const params = {
        list: items
      }
      return removeOutsource(params).then(res => {
        if (res.data.status === 200) {
          this.selectedRows = []
          this.getOutsourceList()
          this.$message.success(res.data.message || '失效成功')
        } else {
          this.$message.error(res.data.message)
        }
      }).catch(error => {
        console.error('失效失败:', error)
        this.$message.error('网络异常，请稍后重试')
      })
    },

    // 单个失效
    expireRow(item) {
      this.$confirm(`确定要失效这条关联记录吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.executeRow([item])
      })
    },

    // 批量失效
    batchExpire() {
      if (this.selectedRows.length === 0) {
        this.$message.warning('请至少选择一条数据进行失效')
        return
      }
      this.$confirm(`确定要失效选中的 ${this.selectedRows.length} 条关联记录吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.executeRow(this.selectedRows)
      })
    },

    // 单个删除
    delRow(item) {
      this.$confirm(`确定要删除这条关联记录吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.executeDelete([item])
      })
    },

    // 批量删除
    batchDelete() {
      if (this.selectedRows.length === 0) {
        this.$message.warning('请至少选择一条数据进行删除')
        return
      }
      this.$confirm(`确定要删除选中的 ${this.selectedRows.length} 条关联记录吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.executeDelete(this.selectedRows)
      })
    },

    // 关闭回列表页
    toClose() {
      this.$router.push({ name: 'supplierContract' })
    },
    // 关闭弹窗
    handleClose: function() {
      this.dialogVisible = false
      this.outsourceForm = {}
    }
  }
}
</script>

<style scoped>
.margin_bottom {
    margin-bottom: 15px;
}
.marginWrapper {
    margin: 15px;
    background-color: #ffffff;
    padding: 15px;
}
.top {
    background-color: #ffffff;
    padding: 20px;
}
</style>

<style>
.el-table .warring-row {
    color: #ff590b;
}
</style>
