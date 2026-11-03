<!-- 面试评价配置表-->
<template>
  <div class="interviewAssessment-container">
    <div class="projectContainer">
      <div key="2" class="contentTwo_inte" >
        <div class="row_pzms">
          <p class="contentTwo_title_p">码值表配置</p>
          <!-- {{ scoreList }} -->
          <el-table :data="scoreList" style="width: 100%">
            <el-table-column prop="projectScale" label="项目规模" width="260">
              <template slot-scope="scope">
                <el-select v-model="scope.row.projectScale" filterable placeholder="请选择" @change="searchEpmloNew(scope.row,1)">
                  <el-option
                    v-for="item in msg_options"
                    :key="item.index"
                    :value="item.projectScale"
                    :label="item.projectScaleName"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column prop="viewRole" label="面试角色" width="260">
              <template slot-scope="scope">
                <el-select v-model="scope.row.viewRole" filterable placeholder="请选择" @change="searchEpmloNew(scope.row,2)">
                  <el-option
                    v-for="item in psg_options"
                    :key="item.index"
                    :value="item.viewRole"
                    :label="item.viewRoleName"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column prop="researchNape" label="考察项" width="260">
              <template slot-scope="scope">
                <el-select v-model="scope.row.researchNape" disabled filterable placeholder="请选择" >
                  <el-option
                    v-for="item in dsg_options"
                    :key="item.index"
                    :value="item.researchNape"
                    :label="item.researchNapeName"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column prop="scoreA" label="A" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.scoreA" :disabled="!scope.row.edited" placeholder="请输入内容" @input="validateInput(scope.row, 'scoreA')"/>
              </template>
            </el-table-column>
            <el-table-column prop="scoreB" label="B" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.scoreB" :disabled="!scope.row.edited" placeholder="请输入内容" @input="validateInput(scope.row, 'scoreB')"/>
              </template>
            </el-table-column>
            <el-table-column prop="scoreC" label="C" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.scoreC" :disabled="!scope.row.edited" placeholder="请输入内容" @input="validateInput(scope.row, 'scoreC')"/>
              </template>
            </el-table-column>
            <el-table-column prop="scoreD" label="D" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.scoreD" :disabled="!scope.row.edited" placeholder="请输入内容" @input="validateInput(scope.row, 'scoreD')"/>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-link :underline="false" type="warning" @click="handleUpdate(scope.row)">修改</el-link>
                <el-link v-if="false" :underline="false" type="warning" @click="deleteProject(scope.row)">删除</el-link>
                <el-link :underline="false" type="warning" @click="detailProject(scope.row)">保存</el-link>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import buttonOrange from '@/components/eleButton/buttonOrange'
import cleanBtn from '@/components/CleanBtn'
import {
  insertInfo,
  keepIhfo,
  deleteIhfo
} from '@/api/InterviewAssessment/pzgl'
export default {
  name: 'InterviewAssessment',
  components: {
    buttonOrange,
    cleanBtn
  },
  data() {
    return {
      form: {},
      scoreList: [
        {
          projectScale: '', // 项目规模
          viewRole: '', // 面试角色
          researchNape: '', // 考察项
          scoreA: null,
          scoreB: null,
          scoreC: null,
          scoreD: null
        }
      ], // 面试打分数据
      scoreListOther: [ // 初始化
        {
          projectScale: '', // 项目规模
          viewRole: '', // 面试角色
          researchNape: '', // 考察项
          scoreA: null,
          scoreB: null,
          scoreC: null,
          scoreD: null
        }
      ],
      // -- A类 及以上大型项目 ： BigAItem
      // -- B类 系统中型项目: MidBItem
      // -- C/D类 系统中型项目: MidC/DItem
      // A/B/C/DItem: A/B/C/DItem
      msg_options: [], // 项目规模
      psg_options: [], // 面试角色
      dsg_options: [], // 考察项
      orgDepartment: [
        {
          depid: 1,
          location: 'da'
        }
      ]
    }
  },
  created() {
    const params = {}
    insertInfo(params)
      .then(res => {
        if (res.data.status === 200) {
          const list = res.data.results
          this.subscribe(list)
        }
      })
  },
  mounted() {},
  methods: {
    // 查询
    searchEpmloNew(index, type) {
      let date = {}
      date = index
      if (type === 1) {
        this.scoreList = [...this.scoreListOther]
        this.scoreList[0].projectScale = index.projectScale
        this.scoreList[0].viewRole = ''
        // this.$set(this, 'scoreList', this.scoreListOther)
        date.viewRole = ''
      }
      insertInfo(date)
        .then(res => {
          if (res.data.status === 200) {
            const list = res.data.results
            if (type === 1) {
              this.psg_options = Array.from(new Set(list
                .map(item => item.viewRole)))
                .map(viewRole => list
                  .find(item => item.viewRole === viewRole))
              if (this.psg_options.length === 1) {
                this.$set(this, 'scoreList', res.data.results)
              } else {
                // this.scoreList = [...this.scoreListOther]
              }
            } else {
              this.$set(this, 'scoreList', res.data.results)
            }
          }
        })
    },
    // 下拉选项
    subscribe(list) {
      this.msg_options = Array.from(new Set(list
        .map(item => item.projectScale)))
        .map(projectScale => list
          .find(item => item.projectScale === projectScale))
      this.dsg_options = Array.from(new Set(list
        .map(item => item.researchNape)))
        .map(researchNape => list
          .find(item => item.researchNape === researchNape))
    },
    validateInput(row, key) {
      let value = row[key]
      if (!/^\d*$/.test(value)) {
        value = value.replace(/[^\d]/g, '')
      }
      if (value < 0) {
        value = 0
      }
      if (value > 100) {
        value = 100
      }
      row[key] = value
    },
    // 修改
    handleUpdate(data) {
      if (!data.pkId) {
        this.$alert('请选择评分项', { type: 'warning' })
        return
      }
      this.$set(data, 'edited', true)
    },
    // 保存
    detailProject(data) {
      if (!data.pkId) {
        this.$alert('请选择评分项', { type: 'warning' })
        return
      }
      keepIhfo(data)
        .then(res => {
          if (res.data.status === 200) {
            this.$message({ type: 'success', message: '保存成功' })
          } else {
            this.$message({ type: 'error', message: '保存失败！' })
          }
        })
      this.$set(data, 'edited', false)
    },
    // 删除
    deleteProject(data) {
      if (!data.pkId) {
        this.$alert('请选择评分项', { type: 'warning' })
        return
      }
      this.$confirm('是否要删除删除确认', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
        .then(() => {
          deleteIhfo(data)
            .then(res => {
              if (res.data.status === 200) {
                this.$message({ type: 'success', message: '删除成功' })
              } else {
                this.$message({ type: 'error', message: '删除失败！' })
              }
            })
        })
    }
  }
}
</script>

<style scoped>
.interviewAssessment-container {
  padding: 10px;
}

.interviewAssessment-head {
  background-color: #ffffff;
  padding: 10px;
}

.projectContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 15px;
  /* line-height: 600px */
}
.contentTwo_inte {
  width: 100%;
  font-size: 16px;
  background-color: #ffffff;
  padding: 15px 20px;
}
.contentPzms .row_pzms {
  margin-bottom: 20px;
}
.contentTwo_title_p {
  color: rgba(38, 38, 38, 1);
  font-size: 16px;
  height: 22px;
  line-height: 22px;
  font-weight: 600;
  margin: 0;
  display: inline-block;
}

</style>
