<template>
    <el-dialog title="消息中间件报价新增页面" :visible.sync="dialogVisible" center width="70%">
        <el-button-group>
            <el-button type="success" size="mini" @click="addRow">添 加</el-button>
        </el-button-group>
        <div class="hmd-container">
            <div class="contentMain contentMain_rlxqfa">
                <el-form :model="form" ref="form" :rules="rules">
                    <el-table :data="form.softComboData" height="300" ref="form.softComboData">
                        <el-table-column type="index" label="序号" width="50">
                        </el-table-column>
                        <el-table-column label="分类">
                            <template slot-scope="scope">
                                <el-form-item :prop="`softComboData.${scope.$index}.subtype`" :rules="rules.subtype">
                                    <el-select placeholder="请选择" v-model="scope.row.subtype">
                                        <el-option v-for="item in subtype" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="软件名称" width="250">
                            <template slot-scope="scope">
                                <el-form-item :prop="`softComboData.${scope.$index}.softname`" :rules="rules.subtype">
                                    <el-input v-model="scope.row.softname"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="版本">
                            <template slot-scope="scope">
                                <el-form-item :prop="`softComboData.${scope.$index}.softversion`" :rules="rules.softversion">
                                    <el-input v-model="scope.row.softversion"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="价格/台">
                            <template slot-scope="scope">
                                <el-form-item :prop="`softComboData.${scope.$index}.price`" :rules="rules.price">
                                    <el-input type="Number" v-model="scope.row.price"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="计费模式">
                            <template slot-scope="scope">
                                <el-form-item :prop="`softComboData.${scope.$index}.billingmode`">
                                    <el-select placeholder="请选择" v-model="scope.row.billingmode">
                                        <el-option v-for="item in billingmodeData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                    </el-select>
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
import { addSoftCombo } from '@/api/resource/config/resconfig'
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
            subtype: [], // 套餐小项
            billingmodeData: [], //计费模式
            form: {
                softComboData: [] //消息中间件配置数据
            },
            rules: {
                subtype: [{ required: true, message: '请选择分类', trigger: 'change' }],
                softname: [{ required: true, message: '请填写软件名称', trigger: 'blur' }],
                softversion: [{ required: true, message: '请填写版本', trigger: 'blur' }],
                price: [{ required: true, validator: checkInteger, trigger: 'blur' }]
            }
        }
    },

    methods: {
        init(dialogVisible) {
            this.dialogVisible = dialogVisible
            this.form.softComboData = []
            this.getSubcategory()
        },

        addRow() {
            let rowData = {
                diskcapacity: 0,
                price: 0,
                billingmode: 3
            }
            this.form.softComboData.push(rowData)
        },

        saveData() {
            if (this.form.softComboData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    addSoftCombo({ softList: this.form.softComboData, saveFlag: '1', comboOneId: '10' }).then((res) => {
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
            if (this.form.softComboData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    addSoftCombo({ softList: this.form.softComboData, saveFlag: '2', comboOneId: '10' }).then((res) => {
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
            this.$parent.loadInit()
        },

        del(index) {
            this.form.softComboData.splice(index, 1)
        },

        // 获取套餐小项
        getSubcategory() {
            getDicEntry({ dicttypeid: 'RES_SUBCATEGORY,RES_BILLINGMODE' }).then((res) => {
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
                this.subtype = subCategoryData.filter((item) => {
                    return item.parentid == '10'
                })
                //计费模式
                this.billingmodeData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_BILLINGMODE'
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