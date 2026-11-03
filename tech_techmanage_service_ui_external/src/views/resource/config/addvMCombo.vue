<template>
    <el-dialog title="虚拟机套餐新增页面" :visible.sync="dialogVisible" center width="70%">
        <el-button-group>
            <el-button type="success" size="mini" @click="addRow">添 加</el-button>
        </el-button-group>
        <div class="hmd-container">
            <div class="contentMain contentMain_rlxqfa">
                <el-form :model="form" ref="form" :rules="rules">
                    <el-table :data="form.vMComboData" height="300" ref="form.vMComboData">
                        <el-table-column type="index" label="序号" width="50"></el-table-column>
                        <el-table-column label="套餐大类" v-if="false" width="50">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.category"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="套餐小项" width="120">
                            <template slot-scope="scope">
                                <el-form-item :prop="`vMComboData.${scope.$index}.subcategory`" :rules="rules.subcategory">
                                    <el-select placeholder="请选择" v-model="scope.row.subcategory">
                                        <el-option v-for="item in subcategory" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>

                        </el-table-column>
                        <el-table-column label="适用场景" width="200">
                            <template slot-scope="scope">
                                <el-form-item :prop="`vMComboData.${scope.$index}.applyscene`" :rules="rules.applyscene">
                                    <el-input v-model="scope.row.applyscene"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="是否专用" width="120">
                            <template slot-scope="scope">
                                <el-select placeholder="请选择" v-model="scope.row.isspecial">
                                    <el-option v-for="item in isspecialData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column label="CPU核数（个）" prop="cpucore">
                            <template slot-scope="scope">
                                <el-form-item :prop="`vMComboData.${scope.$index}.cpucore`" :rules="rules.cpucore">
                                    <el-input type="Number" :min="0" v-model="scope.row.cpucore"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>

                        <el-table-column label="内存（GB）" prop="memory">
                            <template slot-scope="scope">
                                <el-form-item :prop="`vMComboData.${scope.$index}.memory`" :rules="rules.memory">
                                    <el-input type="Number" :min="0" v-model="scope.row.memory"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="系统盘（GB）" prop="diskcapacity">
                            <template slot-scope="scope">
                                <el-form-item :prop="`vMComboData.${scope.$index}.diskcapacity`" :rules="rules.diskcapacity">
                                    <el-input type="Number" :min="0" v-model="scope.row.diskcapacity"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>

                        <el-table-column label="价格/台">
                            <template slot-scope="scope">
                                <el-form-item :prop="`vMComboData.${scope.$index}.price`" :rules="rules.price">
                                    <el-input type="Number" v-model="scope.row.price"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作">
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
            buttonAdd: '新增',
            buttonSave: '保存',
            buttonPublish: '发布',
            buttonClose: '取消',
            subcategory: [], // 套餐小项
            isspecialData: [], // 是否专用
            form: {
                vMComboData: [] //物理机套餐配置数据
            },
            rules: {
                subcategory: [{ required: true, message: '请选择套餐小项', trigger: 'change' }],
                applyscene: [{ required: true, message: '请填写适用场景', trigger: 'blur' }],
                price: [{ required: true, validator: checkInteger, trigger: 'blur' }],
                cpucore: [{ validator: checkInteger, trigger: 'blur' }],
                memory: [{ validator: checkInteger, trigger: 'blur' }],
                diskcapacity: [{ validator: checkInteger, trigger: 'blur' }]
            }
        }
    },

    methods: {
        init(dialogVisible) {
            this.dialogVisible = dialogVisible
            this.form.vMComboData = []
            this.getSubcategory()
        },

        addRow() {
            let rowData = {
                category: '3',
                cpucore: '0',
                memory: '0',
                diskcapacity: '0',
                price: '0',
                isspecial: 0
            }
            this.form.vMComboData.push(rowData)
        },

        saveData() {
            if (this.form.vMComboData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    console.log('虚拟机套餐数据', this.form.vMComboData)
                    addConfigCombo({ list: this.form.vMComboData, saveFlag: '1', comboOneId: '3' }).then((res) => {
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
                        message: '操作失败',
                        type: 'error'
                    })
                    return false
                }
            })
        },

        publishData() {
            if (this.form.vMComboData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            // 集合中包含的专用数据是否唯一
            var isspecialFlag = 0

            for (var i = 0; i < this.form.vMComboData.length; i++) {
                var isspecial = this.form.vMComboData[i].isspecial
                if (isspecial == '1') {
                    isspecialFlag++
                }
            }
            if (isspecialFlag > 1) {
                this.$message.error('只能发布一条专用数据')
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    addConfigCombo({ list: this.form.vMComboData, saveFlag: '2', comboOneId: '3', isspecialFlag: isspecialFlag }).then((res) => {
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
                        message: '操作失败',
                        type: 'error'
                    })
                    return false
                }
            })
        },

        cancelAdd() {
            this.dialogVisible = false
            this.$parent.loadInit()
        },

        del(index) {
            this.form.vMComboData.splice(index, 1)
        },

        // 获取套餐小项
        getSubcategory() {
            getDicEntry({ dicttypeid: 'RES_SUBCATEGORY,RES_YESORNO' }).then((res) => {
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
                    return item.parentid == '3'
                })
                //是否专用
                this.isspecialData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_YESORNO'
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