/*
@Author: chenhuocan
@Date: 2020-07-06 15:58:22
@LastEditors:
@LastEditTime:
@Description: 新增 修改
*/

<template>
	<div style="padding: 17px 20px 35px 20px;background-color: #F6F7F9">
		<div class="content">
			<div style="height: 41px;background-color: #F5F7FA;display: flex; flex-direction: column;justify-items: center">
				<div style="color: #1A1A1A;font-size: 14px;font-weight: 700;line-height: 41px;margin-left: 24px">基本信息</div>
			</div>
			<div style="display: flex; flex-direction: column;align-items: center;padding-top: 26px">
				<el-form  label-width="110px" label-position="left" ref="paramForm"  :model="searchForm" :rules="rules" size="small">
					<el-form-item prop="modelCode" label="权限类型编号:">
						<el-input style="width:300px" v-model="searchForm.modelCode" :disabled="model" placeholder="请输入权限类型编号" />
					</el-form-item>
					<el-form-item prop="modelName" label="权限类型名称:">
						<el-input style="width:300px" v-model="searchForm.modelName" placeholder="请输入权限类型名称" />
					</el-form-item>
					<el-form-item prop="modelId" label="权限类型:">
						<el-input style="width:300px" v-model="searchForm.modelId" :disabled="model" placeholder="请输入权限类型" />
					</el-form-item>
					<el-form-item  label="备注:">
						<el-input style="width:300px"   type="textarea"  v-model="searchForm.remark" placeholder="请输入备注" />
					</el-form-item>
				</el-form>

				<div v-if="model" style="margin-top: 10px">
					<div>外键过滤字段规则</div>
					<el-button-group style="margin-top: 10px;margin-bottom: 10px">
						<el-button type="primary" size="small" icon="el-icon-plus" @click="addClick"> 新增</el-button>
						<el-button type="primary" size="small" icon="el-icon-edit" @click="modify">修改</el-button>
						<el-button type="primary" size="small" icon="el-icon-delete" @click="deleteClick">删除</el-button>
					</el-button-group>
					<el-table   :data="tableData" height="250"  :row-class-name="rowClassName"  @row-click="handleRowClick">
						<el-table-column  label="选择" width="60" align="center" header-align="center">
							<template slot-scope="scope">
								<el-radio v-model="currentRadioData" :label="scope.row" @change="radioDataChange"><i></i></el-radio>
							</template>
						</el-table-column>
						<el-table-column  prop="fkcolumnId" width="120" label="编号" ></el-table-column>
						<el-table-column prop="fkcolumnStCd"  width="120" label="状态">
							<template slot-scope="scope">
								{{scope.row.fkcolumnStCd | tsConvert('StCd')}}
							</template>
						</el-table-column>
						<el-table-column  prop="fkcolumnResSql"  width="160" label="外键过滤规则" ></el-table-column>
					</el-table>
				</div>
				<el-row style="margin-top: 10px">
					<el-col :span="12">
						<el-button  type="plain" size="medium" icon="el-icon-arrow-left" @click="gobackClick">返回</el-button>
					</el-col>
					<el-col :span="12">
						<el-button style="margin-left: 20px" type="primary" size="medium" icon="el-icon-refresh" @click="saveClick">保存</el-button>
					</el-col>
				</el-row>
			</div>

			<el-dialog
							title="添加过滤规则"
							:visible.sync="centerDialogVisible"
							width="40%"
							center>

				<el-form ref="filterRulesForm" label-position="left" label-width="140px" :model="filterRulesForm" :rules="filterRulesFormRules">
					<el-form-item prop="fkcolumnId" label="外键过滤规则编号">
						<el-input v-model="filterRulesForm.fkcolumnId" placeholder="请输入外键过滤规则编号"></el-input>
					</el-form-item>
					<el-form-item prop="fkcolumnStCd" label="外键过滤字段状态">
						<ts-multi-select v-model="filterRulesForm.fkcolumnStCd" :params="selectParams"></ts-multi-select>
					</el-form-item>
					<el-form-item prop="fkcolumnResSql" label="外键过滤查询语句">
						<el-input  type="textarea" placeholder="请输入外键过滤查询语句，用()括起来" v-model="filterRulesForm.fkcolumnResSql"></el-input>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="filterClick">确 定</el-button>
        </span>
			</el-dialog>

		</div>
	</div>
</template>

<script>
export default {
  name: 'AddModify',
  components: {},
  data() {
    const fkcolumnResSqlRules = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入外键过滤查询语句，用()括起来'))
      } else if (value.substring(0, 1) !== '(' || value.substring(value.length - 1, value.length) !== ')') {
        callback(new Error('外键过滤查询语句输入格式不正确'))
      } else {
        callback()
      }
    }
    return {
      selectParams: { key: 'StCd', group: '1' },
      centerDialogVisible: false,
      currentRadioData: null,
      model: false, // 新增的模式为false, 修改模式为true
      // 搜索
      searchForm: {
        modelCode: '',
        modelName: '',
        modelId: '',
        remark: ''
      },
      rules: {
        modelCode: [
          { required: true, message: '请输入权限类型编号', trigger: 'blur' }
        ],
        modelName: [
          { required: true, message: '请选择外键过滤字段状态', trigger: 'blur' }
        ],
        modelId: [
          { required: true, message: '请输入权限类型', trigger: 'blur' }
        ]
      },
      selected: false,
      selectData: '',
      tableData: [],
      filterRulesForm: {
        fkcolumnId: '',
	        fkcolumnStCd: '',
        fkcolumnResSql: ''
      },
      filterRulesFormRules: {
        fkcolumnId: [
          { required: true, message: '请输入外键过滤规则编号', trigger: 'blur' }
        ],
        fkcolumnStCd: [
          { required: true, message: '请输入权限类型名称', trigger: 'blur' }
        ],
        fkcolumnResSql: [
          { required: true, validator: fkcolumnResSqlRules, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    if (['change'].includes(this.$route.query.type)) {
      const selectDataJson = localStorage.getItem('tsDataAuthortyModelManage')
      const modelquery = JSON.parse(selectDataJson)
      if (this.$route.query.type === 'change') {
        this.searchForm = modelquery || {}
        this.queryModelFkcolumn()
        this.model = true
      }
    }
  },
  mounted() {
  },
  methods: {
    rowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'FFFFFF'
      } else {
        return 'F7F7F7'
      }
    },
    gobackClick() {
      this.$tsVue.tansunBack(this, '/dataAuthortyModelManage')
    },
    saveClick() {
      this.$refs['paramForm'].validate((valid) => {
        if (valid) {
          const data = Object.assign({}, this.searchForm)
          this.$request({
            url: this.$api.saveAuthModel,
            data: data
          }).then((response) => {
            this.$message.success(response.description)
            this.$tsVue.tansunBack(this, '/dataAuthortyModelManage')
          })
        } else {
          return false
        }
      })
    },
    queryModelFkcolumn() {
      // 查询
      this.$request({
        url: this.$api.queryModelFkcolumn,
        data: { modelCode: this.searchForm.modelCode }
      }).then((response) => {
        console.log('-1232323', response)
        if (response.status === 200) {
          this.tableData = response.data.grid.list
        } else {
          this.$message.success(response.description)
        }
      })
    },
    filterClick() {
      this.$refs['filterRulesForm'].validate((valid) => {
        if (valid) {
          const param = Object.assign({ modelCode: this.searchForm.modelCode }, this.filterRulesForm)
          this.$request({
            url: this.$api.saveModelFkcolumn,
            data: param
          }).then((response) => {
            console.log('-1232323', response)
            if (response.status === 200) {
              this.$message.success('新增成功')
              // 提交
              this.centerDialogVisible = false
              this.queryModelFkcolumn()
            } else {
              this.$message.success(response.description)
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    addClick() {
      // 新增
      this.filterRulesForm = {}
      this.centerDialogVisible = true
    },
    modify() {
      const self = this
      if (this.selectData) {
        this.filterRulesForm = this.selectData
        this.centerDialogVisible = true
      } else {
        self.$alert('请选择有效数据', '信息', { confirmButtonText: '确定' })
      }
    },
    deleteClick() {
      // 删除
      if (this.selectData) {
        this.$confirm('删除该记录，删除后不可恢复，是否继续?').then(() => {
          const param = {
            modelCode: this.searchForm.modelCode,
	            id: this.selectData.id
          }
          this.$request({
            url: this.$api.delModelFkcolumn,
            data: param
          }).then((response) => {
            if (response.status === 200) {
              this.$message.success('删除成功')
              this.queryModelFkcolumn()
            } else {
              this.$message.success(response.description)
            }
          })
        }).catch(() => {})
      } else {
        this.$alert('请选中一条记录')
      }
    },

    radioDataChange(row) {
      this.currentRadioData = row
    },
    // 点击单行
    handleRowClick(row, event, column) {
      this.selectData = row
      this.radioDataChange(row)
    }
  }
}
</script>

<style lang=scss scoped>
	.content{
		width: 100%;
		height: 705px;
		background: #ffffff;
		box-shadow: 0px 3px 10px rgba(0,0,0,0.16);
		opacity: 1;
		border-radius:5px;
		::v-deep .el-form-item__label {
			color: #1a1a1a;
			font-weight: 400;
		}
		::v-deep 	.el-table td{
			padding: 5px 0 5px 0;
		}
		::v-deep .el-table {
			color: #333333;
			font-size: 14px;
		}
		::v-deep .el-table .F7F7F7 {
			background: #F7F7F7;
		}

		::v-deep .el-table .FFFFFF {
			background: #FFFFFF;
		}

	}

</style>
