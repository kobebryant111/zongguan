<template>
    <el-dialog title="物理机配件新增页面" :visible.sync="dialogVisible" center width="50%">
        <el-button-group>
            <el-button type="success" size="mini" @click="addRow">添 加</el-button>
        </el-button-group>
        <div class="hmd-container">
            <div class="contentMain contentMain_rlxqfa">
                <el-form :model="form" ref="form" :rules="rules">
                    <el-table :data="form.physicalPartsData" height="300" ref="form.physicalPartsData">
                        <el-table-column type="index" label="序号" width="50">
                        </el-table-column>
                        <el-table-column label="套餐小项" width="120">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalPartsData.${scope.$index}.subcategory`" :rules="rules.subcategory">
                                    <el-select placeholder="请选择" v-model="scope.row.subcategory">
                                        <el-option v-for="item in subcategory" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>

                        </el-table-column>
                        <el-table-column label="使用场景" width="200">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalPartsData.${scope.$index}.applyscene`" :rules="rules.applyscene">
                                    <el-input v-model="scope.row.applyscene"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>

                        <el-table-column label="配置">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalPartsData.${scope.$index}.describe`" :rules="rules.describe">
                                    <el-input v-model="scope.row.describe"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="价格/块" width="120">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalPartsData.${scope.$index}.price`" :rules="rules.price">
                                    <el-input type="Number" v-model="scope.row.price"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="80">
                            <template slot-scope="scope">
                                <el-button size="mini" type="danger" @click="del(scope.$index)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-form>
            </div>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveData" size="mini">保存</el-button>
            <el-button type="primary" @click="publishData" size="mini">发布</el-button>
            <el-button @click="cancelAdd" size="mini">取消</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { getDicEntry } from '@/api/eosDictEntry'
import { addConfigCombo } from '@/api/resource/config/resconfig'
import buttonCommon from '@/components/eleButton/buttonCommon'
import buttonOrange from '@/components/eleButton/buttonOrange'
export default {
    components: { buttonCommon, buttonOrange },
    data() {
        var checkInteger = (rule, value, callback) => {
            const val = /^\+?[0-9][0-9]*$/
            if (!val.test(value)) {
                callback(new Error('请输入正整数!'))
            } else {
                callback()
            }
        }
        return {
            dialogVisible: false,
            show: false,
            buttonAdd: '新增',
            buttonSave: '保存',
            buttonPublish: '发布',
            buttonClose: '取消',
            subcategory: [], // 套餐小项
            form: {
                physicalPartsData: [] //物理机配件配置数据
            },
            rules: {
                subcategory: [{ required: true, message: '请选择套餐小项', trigger: 'change' }],
                applyscene: [{ required: true, message: '请填写适用场景', trigger: 'blur' }],
                describe: [{ required: true, message: '请填写配置', trigger: 'blur' }],
                price: [{ required: true, validator: checkInteger, trigger: 'blur' }]
            }
        }
    },

    methods: {
        init(dialogVisible) {
            this.dialogVisible = dialogVisible
            this.form.physicalPartsData = []
            this.getSubcategory()
        },

        addRow() {
            let rowData = {}
            this.form.physicalPartsData.push(rowData)
        },

        saveData() {
            if (this.form.physicalPartsData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    console.log('物理机配件数据', this.form.physicalPartsData)
                    addConfigCombo({ list: this.form.physicalPartsData, saveFlag: '1', comboOneId: '2' }).then((res) => {
                        if (res.data.status != 200) {
                            this.$alert(res.data.message, { type: 'error' })
                        } else {
                            this.$message.success(res.data.message)
                            this.dialogVisible = false
                            this.$parent.loadInit()
                        }
                    })
                } else {
                    this.$message({
                        showClose: true,
                        message: '操作失败,请填写必填项',
                        type: 'error'
                    })
                    return false
                }
            })
        },

        publishData() {
            if (this.form.physicalPartsData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    addConfigCombo({ list: this.form.physicalPartsData, saveFlag: '2', comboOneId: '2' }).then((res) => {
                        if (res.data.status != 200) {
                            this.$alert(res.data.message, { type: 'error' })
                        } else {
                            this.$message.success(res.data.message)
                            this.dialogVisible = false
                            this.$parent.loadInit()
                        }
                    })
                } else {
                    this.$message({
                        showClose: true,
                        message: '操作失败,请填写必填项',
                        type: 'error'
                    })
                    return false
                }
            })
        },

        cancelAdd() {
            this.dialogVisible = false
            // this.$emit('loadInit')
            this.$parent.loadInit()
        },

        del(index) {
            this.form.physicalPartsData.splice(index, 1)
        },

        // 获取套餐小项
        getSubcategory() {
            getDicEntry({ dicttypeid: 'RES_SUBCATEGORY' }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //套餐小项
                let subCategoryData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_SUBCATEGORY'
                })
                this.subcategory = subCategoryData.filter((item) => {
                    return item.parentid == '2'
                })
                return data
            })
        }
    }
}
</script>

<style scoped>
.hmd-container {
    padding: 15px 15px 0 15px;
}
.contentMain {
    width: 100%;
    height: 100%;
    font-size: 16px;
    padding: 10px 20px;
}
.contentMain_rlxqfa .el-table th {
    background: rgba(250, 250, 250, 1);
}

.contentMain_rlxqfa .el-table thead {
    font-size: 14px;
    color: rgba(38, 38, 38, 1);
    font-weight: 400;
}
.contentMain_rlxqfa .el-icon-delete {
    width: 11px;
    height: 11px;
    color: #f56c6c;
}
.contentMain_rlxqfa .el-table--medium th {
    padding: 0;
}

.contentMain_rlxqfa .el-table--medium td {
    padding: 0;
}

.contentMain_rlxqfa .el-table th {
    padding: 10px 0;
}

/* form表单样式修改 */
.contentMain_rlxqfa .el-form-item {
    margin: 16px 0;
}
</style>