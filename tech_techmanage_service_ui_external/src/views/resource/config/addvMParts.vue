<template>
    <el-dialog title="虚拟机配件新增页面" :visible.sync="dialogVisible" center width="50%">
        <el-button-group>
            <el-button type="success" size="mini" @click="addRow">添 加</el-button>
        </el-button-group>
        <div class="hmd-container">
            <div class="contentMain contentMain_rlxqfa">
                <el-form :model="form" ref="form" :rules="rules">
                    <el-table :data="form.vMPartsData" height="300" ref="form.vMPartsData">
                        <el-table-column label="虚拟机资源类型">
                            <template slot-scope="scope">
                                <el-form-item :prop="`vMPartsData.${scope.$index}.vmtype`" :rules="rules.vmtype">
                                    <el-select placeholder="请选择" v-model="scope.row.vmtype" @change="onVmTypeChange(scope.row)">
                                        <el-option v-for="item in vmtype" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>

                        </el-table-column>
                        <el-table-column label="配置">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.describe" :disabled="true"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="报价（元）">
                            <template slot-scope="scope">
                                <el-form-item :prop="`vMPartsData.${scope.$index}.price`" :rules="rules.price">
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
import { addVMPartsData } from '@/api/resource/config/resconfig'
import buttonCommon from '@/components/eleButton/buttonCommon'
import buttonOrange from '@/components/eleButton/buttonOrange'
import common from '@/api/common'
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
            vmtype: [], // 虚拟机配件分类
            form: {
                vMPartsData: [] //虚拟机配件数据
            },
            rules: {
                vmtype: [{ required: true, message: '请选择虚拟机资源类型', trigger: 'change' }],
                price: [{ required: true, validator: checkInteger, trigger: 'blur' }]
            }
        }
    },

    methods: {
        init(dialogVisible) {
            this.dialogVisible = dialogVisible
            this.form.vMPartsData = []
            this.getVmType()
        },

        addRow() {
            let rowData = {
                describe: '',
                price: '0'
            }
            this.form.vMPartsData.push(rowData)
        },

        saveData() {
            if (this.form.vMPartsData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    console.log('虚拟机套餐数据', this.form.vMPartsData)
                    addVMPartsData({ vmList: this.form.vMPartsData, saveFlag: '1' }).then((res) => {
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

        onVmTypeChange(row) {
            var vmtype = row.vmtype
            if (vmtype == '1') {
                row.describe = '1C'
            } else if (vmtype == '2' || vmtype == '3') {
                row.describe = '1G'
            }
        },

        publishData() {
            if (this.form.vMPartsData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    addVMPartsData({ vmList: this.form.vMPartsData, saveFlag: '2' }).then((res) => {
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
            this.form.vMPartsData.splice(index, 1)
        },

        // 获取虚拟机配件分类
        getVmType() {
            let param = {}
            param.dicttypeid = 'RES_VM_TYPE'
            let params = common.paramsFormat(param)
            getDicEntry(params)
                .then((res) => {
                    console.log('获取虚拟机配件分类', res.data)
                    if (res.data.status == 200) {
                        let data = res.data.results
                        this.vmtype = data
                    }
                })
                .catch((error) => {
                    this.$message.error(error)
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