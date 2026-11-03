<template>
    <div>
        <el-button-group v-if="viewFlag =='0'">
            <el-button type="primary" size="mini" @click="addData">新增</el-button>
            <el-button type="primary" size="mini" @click="upConfigComboStatus(2)">发布</el-button>
            <el-button type="primary" size="mini" @click="upConfigComboStatus(3)">作废</el-button>
            <el-button type="primary" size="mini" @click="upConfigComboStatus(4)">删除</el-button>
            <el-button type="primary" size="mini" @click="copyConfigCombo">复制</el-button>
        </el-button-group>

        <el-table :data="paasComboGrid" ref="refPaasCombo" :height="tableHeight" stripe highlight-current-row border>
            <el-table-column type="selection" width="35" v-if="viewFlag =='0'"></el-table-column>
            <el-table-column prop="id" v-if="show" :show-overflow-tooltip="true" />
            <el-table-column label="套餐分类" prop="subcategory" width="120" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <span v-for="(item, index) of subCategoryData" :key="index">
                        <span v-if="item.dictid == scope.row.subcategory">{{ item.dictname}}</span>
                    </span>
                </template>
            </el-table-column>

            <el-table-column label="适用场景" prop="applyscene" :show-overflow-tooltip="true" width="150" />
            <el-table-column label="资源配置" prop="describe" :show-overflow-tooltip="true" />
            <el-table-column label="CPU核数（个）" prop="cpucore" v-if="show" :show-overflow-tooltip="true" />
            <el-table-column label="内存（GB）" prop="memory" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="价格/租户" prop="price" :show-overflow-tooltip="true" width="100"></el-table-column>
            <el-table-column label="磁盘容量(表空间)/TB（单价）" prop="diskcapacity" :show-overflow-tooltip="true" width="220"></el-table-column>
            <el-table-column label="状态" prop="status" :show-overflow-tooltip="true" width="80">
                <template slot-scope="scope">
                    <span v-if="scope.row.iscancel =='1'">作废
                    </span>
                    <span v-else v-for="(item, index) of subStatusData" :key="index">
                        <span v-if="item.dictid == scope.row.status">{{ item.dictname}}</span>
                    </span>
                </template>
            </el-table-column>

            <el-table-column label="操作" width="150" v-if="viewFlag =='0'">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" v-if="scope.row.status ==1 && scope.row.iscancel ==0" @click="editData(scope.row)">编 辑</el-button>
                </template>
            </el-table-column>

        </el-table>

        <div class="pager_zb">
            <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
        </div>
        <div>
            <AddPaasCombo v-if="AddPaasComboShow" ref="paasCombo" />
        </div>
        <el-dialog :title="title" :visible.sync="dialogClose" center width="30%">
            <el-form ref="paasComboForm" :model="paasComboForm" :rules="rules" label-width="200px">

                <el-form-item label="套餐小项：">
                    <span v-for="(item, index) of subCategoryData" :key="index">
                        <span v-if="item.dictid == paasComboForm.subcategory">{{ item.dictname}}</span>
                    </span>
                </el-form-item>

                <el-form-item label="适用场景：" prop="applyscene" :rules="[{ required: true, message: '请填写适用场景', trigger: 'blur'}]">
                    <el-input v-model="paasComboForm.applyscene" maxlength="30" />
                </el-form-item>
                <el-form-item label="资源配置：" prop="describe" :rules="[{required: true, message: '请填写资源配置', trigger: 'blur'}]">
                    <el-input v-model="paasComboForm.describe" maxlength="500" />
                </el-form-item>
                <el-form-item label="CPU核数（个）：" prop="cpucore">
                    <el-input type="Number" v-model="paasComboForm.cpucore" :min="0" maxlength="30" />
                </el-form-item>
                <el-form-item label="内存（GB）：" prop="memory">
                    <el-input type="Number" v-model="paasComboForm.memory" :min="0" maxlength="30" />
                </el-form-item>
                <el-form-item label="磁盘容量(表空间)/TB（单价）：" prop="diskcapacity">
                    <el-input type="Number" v-model="paasComboForm.diskcapacity" :min="0" :disabled="paasComboForm.subcategory !='61'" />
                </el-form-item>
                <el-form-item label="价格/租户：" prop="price" :rules="rules.price">
                    <el-input type="Number" :min="0" v-model="paasComboForm.price" maxlength="20" />
                </el-form-item>
            </el-form>
            <div class="dialog-error-div">
                <samp class="dialog-error">{{error}}</samp>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" size="mini" @click="dialogAddSubmit(1)">保存</el-button>
                <el-button type="primary" size="mini" @click="dialogAddSubmit(2)">发布</el-button>
                <!-- <el-button @click="dialogCancel" size="mini">取消</el-button> -->
            </div>
        </el-dialog>
    </div>
</template>

<script>
import common from '@/api/common'
import pager from '@/components/Pager'
import cleanBtn from '@/components/CleanBtn'
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
import helpHint from '@/components/helpHint'
import { getDicEntry } from '@/api/eosDictEntry'
import { queryConfigComboList, upConfigComboData, upConfigStatus, addConfigCombo } from '@/api/resource/config/resconfig'
import AddPaasCombo from './addPaasCombo'

export default {
    props: {
        viewFlag: {
            type: String
        }
    },
    name: 'paasCombo',
    components: { pager, cleanBtn, buttonOrange, helpHint, buttonCommon, AddPaasCombo },
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
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 250,
            title: '', // dialog 标题
            dialogClose: false,
            show: false,
            totalNumber: 0,
            size: 50, // 初始值 默认：50
            index: 1, // 初始值 默认：1
            paasComboGrid: [], // 列表分页数据
            drawer: false, // 默认不显示
            buttonAdd: '新增', //导出按钮
            buttonPublish: '发布', //导出按钮
            buttonCancell: '作废', //导出按钮
            buttonDel: '删除', //导出按钮
            buttonCopy: '复制', //导出按钮
            sizes: 'small', //按钮大小
            buttonSearch: '查 询', //查询按钮
            buttonClose: '关 闭', //关闭按钮
            subCategoryData: [], // 套餐小项
            subStatusData: [], // 状态
            AddPaasComboShow: false,
            paasComboForm: {
                // 表单
                id: '',
                subcategory: '', // 套餐小项
                applyscene: '', // 适用场景
                describe: '', // 存储配置
                cpucore: '', // CPU核数（个）
                memory: '', // 内存（GB）
                diskcapacity: '', //磁盘容量(表空间)/T（单价）
                price: '' // 价格/租户
            },
            rules: {
                cpucore: [{ validator: checkInteger, trigger: 'blur' }],
                memory: [{ validator: checkInteger, trigger: 'blur' }],
                price: [{ required: true, validator: checkInteger, trigger: 'blur' }],
                diskcapacity: [{ validator: checkInteger, trigger: 'blur' }]
            },
            error: '' // 错误提示
        }
    },
    created() {},
    mounted() {
        this.loadInit()
        this.getDict()
    },
    methods: {
        /**
          初始化
         */
        init: function (param) {
            this.getPagerList(param)
        }, // 初始化 end

        /**
         * 分页初始化方法
         */
        loadInit: function () {
            let param = {}
            param.size = this.size
            param.index = this.index
            this.init(param)
        },

        // 获取列表数据
        getPagerList(param) {
            param.comboOneId = 6
            param.viewFlag = this.viewFlag
            let data = common.paramsFormat(param)
            queryConfigComboList(data).then((res) => {
                if (res.data.status == 200) {
                    this.paasComboGrid = res.data.results
                    this.totalNumber = res.data.total
                }
            })
        },

        addData() {
            this.AddPaasComboShow = true
            this.$nextTick(() => {
                this.$refs.paasCombo.init(this.AddPaasComboShow)
            })
        },

        /**
         * 分页触发
         */
        getPager: function (size, index) {
            let params = {}
            params.size = size
            params.index = index
            this.size = size
            this.index = index
            this.init(params)
            this.getPagerList(params)
        }, // end

        /**
         * 更新
         */
        editData: function (item) {
            var combodata = this.$refs.refPaasCombo.selection
            if (combodata.lenght > 1) {
                this.$message.error('只能勾选一条数据')
                return false
            }
            this.title = '更新配置信息'
            this.dialogClose = true
            let nowGroup = JSON.parse(JSON.stringify(item))
            this.paasComboForm = nowGroup
        },

        /**
         * 关闭dialog
         */
        dialogCancel: function () {
            this.dialogClose = false
            Object.keys(this.paasComboForm).forEach((key) => {
                this.paasComboForm[key] = ''
            })
            this.error = ''
        },

        /**
         *  提交保存
         */
        dialogAddSubmit: function (saveFlag) {
            this.$refs['paasComboForm'].validate((valid) => {
                if (valid) {
                    this.error = ''
                    this.paasComboForm.saveFlag = saveFlag
                    this.paasComboForm.comboOneId = '6'
                    let datas = common.paramsFormat(this.paasComboForm)
                    console.log('111' + datas)
                    upConfigComboData(datas).then((res) => {
                        if (res.data.status != 200) {
                            this.$alert(res.data.message, { type: 'error' })
                        } else {
                            this.dialogCancel()
                            this.$message({
                                type: 'success',
                                message: res.data.message
                            })
                            this.loadInit()
                        }
                    })
                } else {
                    this.$message.error('操作失败')
                    return false
                }
            })
        },

        /**
         * 状态修改
         */
        upConfigComboStatus: function (upFlag) {
            let combodata = this.$refs.refPaasCombo.selection
            if (combodata.length == 0) {
                this.$message.error('请选中至少一条数据')
                return false
            }
            var id = ''
            console.log(this)

            for (var i = 0; i < combodata.length; i++) {
                var status = combodata[i].status
                var iscancel = combodata[i].iscancel
                if (status == '2' && iscancel == '0' && upFlag == '4') {
                    this.$message.error('已发布的数据不可删除')
                    rteurn
                }
                if (iscancel == '1' && upFlag == '4') {
                    this.$message.error('已作废的数据不可删除')
                    rteurn
                }
                id += combodata[i].id + ','
            }
            if (id.indexOf(',') > -1) {
                id = id.substring(0, id.length - 1)
            }
            var msg = ''
            if (upFlag == '2') {
                msg = '发布'
            } else if (upFlag == '3') {
                msg = '作废'
            } else if (upFlag == '4') {
                msg = '删除'
            }
            let param = {}
            param.comboOneId = '6'
            param.saveFlag = upFlag
            param.id = id
            let params = common.paramsFormat(param)
            this.$confirm('确定' + msg + '吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then((res) => {
                    upConfigStatus(params).then((res) => {
                        if (res.data.status != 200) {
                            this.$message.error(res.data.message)
                            return false
                        } else {
                            this.$message.success(res.data.message)
                            this.loadInit()
                        }
                    })
                })
                .catch((error) => {})
        },

        /**
         * 复制
         */
        copyConfigCombo: function () {
            let combodata = this.$refs.refPaasCombo.selection
            if (combodata.length == 0) {
                this.$message.error('请选中至少一条数据')
                return false
            }
            console.log('选中的物理机套餐数据', combodata)
            addConfigCombo({ list: combodata, saveFlag: '0', comboOneId: '6' }).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                } else {
                    this.$message.success(res.data.message)
                    this.loadInit()
                }
            })
        },

        // 查询业务字典
        getDict() {
            getDicEntry({ dicttypeid: 'RES_SUBCATEGORY,RES_CONFIG_STATUS' }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //套餐小项
                this.subCategoryData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_SUBCATEGORY'
                })

                //状态
                this.subStatusData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_CONFIG_STATUS'
                })
                return data
            })
        },

        // 失败消息提示
        messageError(errorMessage) {
            this.$message.error(errorMessage)
        },

        handelExport() {
            // TODO
        }
    }
}
</script>
<style scoped>
</style>
