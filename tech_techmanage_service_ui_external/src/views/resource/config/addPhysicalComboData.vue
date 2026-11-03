<template>
    <el-dialog title="物理机套餐新增页面" :visible.sync="dialogVisible" center width="90%">
        <el-button-group>
            <el-button type="success" size="mini" @click="addRow">添 加</el-button>
        </el-button-group>
        <div class="hmd-container">
            <div class="contentMain contentMain_rlxqfa">
                <el-form :model="form" ref="form" :rules="rules">
                    <el-table :data="form.physicalComboData" height="400" border stripe ref="form.physicalComboData">
                        <el-table-column type="index" label="序号" width="50"></el-table-column>
                        <el-table-column label="套餐大类" v-if="false" width="50">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.category"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="套餐小项" width="150">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.subcategory`" :rules="rules.subcategory">
                                    <el-select placeholder="请选择" v-model="scope.row.subcategory">
                                        <el-option v-for="item in subcategory" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="适用场景" width="150">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.applyscene`" :rules="rules.applyscene">
                                    <el-input v-model="scope.row.applyscene"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="物理CPU数量（个）" width="100" prop="cpunum">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.cpunum`" :rules="rules.cpunum">
                                    <el-input type="Number" :min="0" v-model="scope.row.cpunum"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="CPU核数（个）" width="100" prop="cpucore">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.cpucore`" :rules="rules.cpucore">
                                    <el-input type="Number" :min="0" v-model="scope.row.cpucore"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="CPU主频（GHz）" width="100">
                            <template slot-scope="scope">
                                <el-input :min="0" v-model="scope.row.frequency" oninput="if(value<0)value=0"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="内存（GB）" prop="memory" width="120">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.memory`" :rules="rules.memory">
                                    <el-input type="Number" :min="0" v-model="scope.row.memory"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘配置1" v-if="false">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.diskconfig1"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘配置1（GB）" width="170">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.diskconfigid1`" :rules="rules.diskconfigid1">
                                    <el-select placeholder="请选择" v-model="scope.row.diskconfigid1" @change="diskconfigChange1(scope.row)">
                                        <el-option v-for="item in diskConfigList" :key="item.id" :label="item.describe" :value="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘数量1（块）" width="120">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.disknum1`" :rules="rules.disknum1">
                                    <el-input type="Number" :min="0" v-model="scope.row.disknum1"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘配置2" v-if="false">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.diskconfig2"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘配置2（GB）" width="170">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.diskconfigid2`" :rules="rules.diskconfigid2">
                                    <el-select placeholder="请选择" v-model="scope.row.diskconfigid2" @change="diskconfigChange2(scope.row)">
                                        <el-option v-for="item in diskConfigList" :key="item.id" :label="item.describe" :value="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘数量2（块）" width="120" prop="disknum2">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.disknum2`" :rules="rules.disknum2">
                                    <el-input type="Number" :min="0" v-model="scope.row.disknum2"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘配置3" v-if="false">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.diskconfig3"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘配置3（GB）" width="170">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.diskconfigid3`" :rules="rules.diskconfigid3">
                                    <el-select placeholder="请选择" v-model="scope.row.diskconfigid3" @change="diskconfigChange3(scope.row)">
                                        <el-option v-for="item in diskConfigList" :key="item.id" :label="item.describe" :value="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="磁盘数量3（块）" width="120" prop="disknum3">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.disknum3`" :rules="rules.disknum3">
                                    <el-input type="Number" :min="0" v-model="scope.row.disknum3"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="万兆网卡数量（块）" width="120" prop="netcardnum">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.netcardnum`" :rules="rules.netcardnum">
                                    <el-input type="Number" :min="0" v-model="scope.row.netcardnum"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="光纤卡HBA数量（块）" width="120" prop="fibercardnum">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.fibercardnum`" :rules="rules.fibercardnum">
                                    <el-input type="Number" :min="0" v-model="scope.row.fibercardnum"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="GPU卡配置" v-if="false">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.gputype"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="GPU卡型号" width="170">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.gpuid`">
                                    <el-select placeholder="请选择" v-model="scope.row.gpuid" @change="gpuChange(scope.row)">
                                        <el-option v-for="item in gpuList" :key="item.id" :label="item.describe" :value="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="GPU卡数量（块）" width="120" prop="gpunum">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.gpunum`" :rules="rules.gpunum">
                                    <el-input type="Number" :min="0" v-model="scope.row.gpunum"></el-input>
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
                        <el-table-column label="报价（元）" width="120">
                            <template slot-scope="scope">
                                <el-form-item :prop="`physicalComboData.${scope.$index}.price`" :rules="rules.price">
                                    <el-input type="Number" :min="0" v-model="scope.row.price"></el-input>
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
import common from '@/api/common'
import { getDicEntry } from '@/api/eosDictEntry'
import { addConfigCombo, queryPhysicalParts } from '@/api/resource/config/resconfig'
import buttonCommon from '@/components/eleButton/buttonCommon'
import buttonOrange from '@/components/eleButton/buttonOrange'
export default {
    components: { buttonCommon, buttonOrange, common },
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
            diskConfigList: [], // 硬盘
            gpuList: [], // GPU卡
            form: {
                physicalComboData: [] //物理机套餐配置数据
            },
            rules: {
                subcategory: [{ required: true, message: '请选择套餐小项', trigger: 'change' }],
                applyscene: [{ required: true, message: '请填写适用场景', trigger: 'blur' }],
                price: [{ required: true, validator: checkInteger, trigger: 'blur' }],
                cpunum: [{ validator: checkInteger, trigger: 'blur' }],
                cpucore: [{ validator: checkInteger, trigger: 'blur' }],
                memory: [{ validator: checkInteger, trigger: 'blur' }],
                disknum1: [{ validator: checkInteger, trigger: 'blur' }],
                disknum2: [{ validator: checkInteger, trigger: 'blur' }],
                disknum3: [{ validator: checkInteger, trigger: 'blur' }],
                netcardnum: [{ validator: checkInteger, trigger: 'blur' }],
                fibercardnum: [{ validator: checkInteger, trigger: 'blur' }],
                gpunum: [{ validator: checkInteger, trigger: 'blur' }]
            }
        }
    },

    methods: {
        init(dialogVisible) {
            this.dialogVisible = dialogVisible
            this.form.physicalComboData = []
            this.getSubcategory()
            this.getPhysicalParts()
        },

        addRow() {
            let rowData = {
                category: '1',
                cpunum: '0',
                cpucore: '0',
                frequency: '',
                memory: '0',
                disknum1: '0',
                diskconfig1: '',
                disknum2: '0',
                diskconfig2: '',
                disknum3: '0',
                diskconfig3: '',
                netcardnum: '0',
                fibercardnum: '0',
                gpunum: '0',
                gputype: '',
                price: '0',
                isspecial: 0
            }
            this.form.physicalComboData.push(rowData)
        },

        saveData() {
            if (this.form.physicalComboData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    console.log('物理机套餐数据', this.form.physicalComboData)
                    addConfigCombo({ list: this.form.physicalComboData, saveFlag: '1', comboOneId: '1' }).then((res) => {
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
            if (this.form.physicalComboData.length == 0) {
                this.$message({
                    showClose: true,
                    message: '操作失败,请添加至少一条数据',
                    type: 'error'
                })
                return false
            }

            // 集合中包含的专用数据是否唯一
            var isspecialFlag = 0

            for (var i = 0; i < this.form.physicalComboData.length; i++) {
                var isspecial = this.form.physicalComboData[i].isspecial
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
                    addConfigCombo({ list: this.form.physicalComboData, saveFlag: '2', comboOneId: '1', isspecialFlag: isspecialFlag }).then((res) => {
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
            // this.$emit('loadInit')
            this.$parent.loadInit()
        },

        del(index) {
            this.form.physicalComboData.splice(index, 1)
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
                    return item.parentid == '1'
                })
                //是否专用
                this.isspecialData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_YESORNO'
                })
                return data
            })
        },
        // 获取物理机配件
        getPhysicalParts() {
            let param = {}
            let data = common.paramsFormat(param)
            queryPhysicalParts(data).then((res) => {
                if (res.data.status == 200) {
                    let data = res.data.results
                    //硬盘
                    this.diskConfigList = data.filter((item) => {
                        return item.subcategory == '24'
                    })
                    //gpu
                    this.gpuList = data.filter((item) => {
                        return item.subcategory == '25'
                    })
                }
            })
        },
        gpuChange(val) {
            let gpuOne = this.gpuList.filter((item) => {
                return item.id == val.gpuid
            })
            val.gputype = gpuOne[0].describe
        },
        diskconfigChange3(val) {
            let diskconfig = this.diskConfigList.filter((item) => {
                return item.id == val.diskconfigid3
            })
            val.diskconfig3 = diskconfig[0].describe
        },
        diskconfigChange2(val) {
            let diskconfig = this.diskConfigList.filter((item) => {
                return item.id == val.diskconfigid2
            })
            val.diskconfig2 = diskconfig[0].describe
        },
        diskconfigChange1(val) {
            let diskconfig = this.diskConfigList.filter((item) => {
                return item.id == val.diskconfigid1
            })
            val.diskconfig1 = diskconfig[0].describe
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