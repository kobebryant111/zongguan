<template>
    <el-dialog title="存储套餐新增页面" :visible.sync="dialogVisible" center width="80%">
        <el-button-group>
            <el-button type="success" size="mini" @click="addRow">添 加</el-button>
        </el-button-group>
        <div class="hmd-container">
            <div class="contentMain contentMain_rlxqfa">
                <el-form :model="form" ref="form" :rules="rules">
                    <el-table :data="form.memoryComboData" height="300" ref="form.memoryComboData">
                        <el-table-column type="index" label="序号" width="50"></el-table-column>
                        <el-table-column label="套餐大类" v-if="false" width="50">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.category"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="套餐小项">
                            <template slot-scope="scope">
                                <el-form-item :prop="`memoryComboData.${scope.$index}.subcategory`" :rules="rules.subcategory">
                                    <el-select placeholder="请选择" v-model="scope.row.subcategory" @change="onSubcategory(scope.row)">
                                        <el-option v-for="item in subcategory" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>

                        </el-table-column>
                        <el-table-column label="适用场景">
                            <template slot-scope="scope">
                                <el-form-item :prop="`memoryComboData.${scope.$index}.applyscene`" :rules="rules.applyscene">
                                    <el-input type="" v-model="scope.row.applyscene"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>

                        <el-table-column label="存储配置">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.describe"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="比例" width="100">
                            <template slot-scope="scope">
                                <el-form-item :prop="`memoryComboData.${scope.$index}.ratio`" :rules="scope.row.subcategory ==51?rules.ratio:[{required:false}]">
                                    <el-input type="Number" v-model="scope.row.ratio" :disabled="scope.row.subcategory !='51'" min="1"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="是否归档">
                            <template slot-scope="scope">
                                <el-select placeholder="请选择" :disabled="scope.row.subcategory !='51'" v-model="scope.row.isspecial" @change="onIsspecial(scope.row)">
                                    <el-option v-for="item in isrelatefundData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column label="系统运维等级">
                            <template slot-scope="scope">
                                <el-form-item :prop="`memoryComboData.${scope.$index}.devopslevel`" :rules="scope.row.isspecial ==0 && scope.row.subcategory ==51?rules.devopslevel:[{required:false}]">
                                    <el-select placeholder="请选择" :disabled="scope.row.subcategory !='51' || scope.row.isspecial !='0'" v-model="scope.row.devopslevel">
                                        <el-option v-for="(item,index) in devopslevelData" :key="index" :label="item.dictname" :value="item.dictname"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="是否涉及资金交易">
                            <template slot-scope="scope">
                                <el-form-item :prop="`memoryComboData.${scope.$index}.isrelatefund`" :rules="scope.row.isspecial ==0 && scope.row.subcategory ==51?rules.isrelatefund:[{required:false}]">
                                    <el-select placeholder="请选择" :disabled="scope.row.subcategory !='51' || scope.row.isspecial !='0'" v-model="scope.row.isrelatefund">
                                        <el-option v-for="item in isrelatefundData" :key="item.dictid" :label="item.dictname" :value="item.dictname"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>

                        <el-table-column label="价格/TB">
                            <template slot-scope="scope">
                                <el-form-item :prop="`memoryComboData.${scope.$index}.price`" :rules="rules.price">
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
            devopslevelData: [], //系统运维等级
            isrelatefundData: [], //是否涉及资金交易
            form: {
                memoryComboData: [] //存储套餐配置数据
            },
            rules: {
                subcategory: [{ required: true, message: '请选择套餐小项', trigger: 'change' }],
                applyscene: [{ required: true, message: '请填写适用场景', trigger: 'blur' }],
                price: [{ required: true, validator: checkInteger, trigger: 'blur' }],
                devopslevel: [{ required: true, message: '请选择系统运维等级', trigger: 'blur' }],
                isrelatefund: [{ required: true, message: '请选择涉及资金交易', trigger: 'blur' }],
                ratio: [{ required: true, validator: checkInteger, trigger: 'blur' }]
            }
        }
    },

    methods: {
        init(dialogVisible) {
            this.dialogVisible = dialogVisible
            this.form.memoryComboData = []
            this.getSubcategory()
        },

        addRow() {
            let rowData = {
                category: '5',
                price: '0',
                devopslevel: '',
                isrelatefund: '',
                isspecial: 0,
                ratio: ''
            }
            this.form.memoryComboData.push(rowData)
        },

        onSubcategory(row) {
            var subcategory = row.subcategory
            if (subcategory == '52') {
                row.ratio = ''
                row.isspecial = 0
                row.isrelatefund = ''
                row.devopslevel = ''
            }
        },

        onIsspecial(row) {
            var isspecial = row.isspecial
            if (isspecial == '1') {
                row.isrelatefund = ''
                row.devopslevel = ''
            }
        },

        saveData() {
            if (this.form.memoryComboData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    console.log('存储套餐数据', this.form.memoryComboData)
                    addConfigCombo({ list: this.form.memoryComboData, saveFlag: '1', comboOneId: '5' }).then((res) => {
                        if (res.data.status != 200) {
                            this.$alert(res.data.message, { type: 'error' })
                        } else {
                            this.$message.success(res.data.message)
                            this.dialogVisible = false
                            // this.$emit('loadInit')
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
            if (this.form.memoryComboData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            // 集合中包含的归档数据是否唯一
            var isspecialFlag = 0
            let pushArray = []

            for (var i = 0; i < this.form.memoryComboData.length; i++) {
                let ArrayData = []
                var isspecial = this.form.memoryComboData[i].isspecial
                var isrelatefund = this.form.memoryComboData[i].isrelatefund //是否涉及资金交易
                var devopslevel = this.form.memoryComboData[i].devopslevel //系统运维等级

                if (isrelatefund && devopslevel) {
                    ArrayData[i] = isrelatefund + '_' + devopslevel
                    if (pushArray.indexOf(ArrayData[i]) != -1) {
                        this.$message.error('是否涉及资金交易和系统运维等级不能重复')
                        return false
                    } else {
                        pushArray.push(ArrayData[i])
                    }
                }

                if (isspecial == '1') {
                    isspecialFlag++
                }
            }
            if (isspecialFlag > 1) {
                this.$message.error('只能发布一条归档数据')
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    addConfigCombo({ list: this.form.memoryComboData, saveFlag: '2', comboOneId: '5', isspecialFlag: isspecialFlag }).then((res) => {
                        if (res.data.status != 200) {
                            this.$alert(res.data.message, { type: 'error' })
                        } else {
                            this.$message.success(res.data.message)
                            this.dialogVisible = false
                            // this.$emit('loadInit')
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
            // this.$emit('loadInit')
            this.$parent.loadInit()
        },

        del(index) {
            this.form.memoryComboData.splice(index, 1)
        },

        // 获取套餐小项
        getSubcategory() {
            getDicEntry({ dicttypeid: 'RES_SUBCATEGORY,BUSI_SYSTEM_OPERAGRADE,RES_YESORNO' }).then((res) => {
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
                    return item.parentid == '5'
                })

                //系统运维等级
                this.devopslevelData = data.filter((item) => {
                    //item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'BUSI_SYSTEM_OPERAGRADE'
                })

                //是否涉及资金交易
                this.isrelatefundData = data.filter((item) => {
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