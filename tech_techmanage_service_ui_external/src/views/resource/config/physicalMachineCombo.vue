<template>
    <div>
        <el-button-group v-if="viewFlag =='0'">
            <el-button type="primary" size="mini" @click="addData">新增</el-button>
            <el-button type="primary" size="mini" @click="upConfigComboStatus(2)">发布</el-button>
            <el-button type="primary" size="mini" @click="upConfigComboStatus(3)">作废</el-button>
            <el-button type="primary" size="mini" @click="upConfigComboStatus(4)">删除</el-button>
            <el-button type="primary" size="mini" @click="copyConfigCombo">复制</el-button>
        </el-button-group>
        <el-table :data="physicalComboGrid" ref="refPhysicalCombo" :height="tableHeight" stripe highlight-current-row border>
            <el-table-column type="selection" width="35" v-if="viewFlag =='0'"></el-table-column>
            <el-table-column prop="id" v-if="show" :show-overflow-tooltip="true" />
            <el-table-column label="套餐小项" prop="subcategory" width="150" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <span v-for="(item, index) of subCategoryData" :key="index">
                        <span v-if="item.dictid == scope.row.subcategory">{{ item.dictname}}</span>
                    </span>
                </template>
            </el-table-column>

            <el-table-column label="适用场景" prop="applyscene" :show-overflow-tooltip="true" width="250" />
            <el-table-column label="资源配置" prop="describe" :show-overflow-tooltip="true" width="250" />
            <el-table-column label="物理CPU数量（个）" prop="cpunum" v-if="show" :show-overflow-tooltip="true" />
            <el-table-column label="CPU核数（个）" prop="cpucore" v-if="show" :show-overflow-tooltip="true" />
            <el-table-column label="CPU主频（GHz）" prop="frequency" v-if="show" :show-overflow-tooltip="true" />
            <el-table-column label="内存（GB）" prop="memory" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="磁盘配置1（GB）" prop="diskconfig1" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="磁盘数量1（块）" prop="disknum1" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="磁盘配置2（GB）" prop="diskconfig2" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="磁盘数量2（块）" prop="disknum2" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="磁盘配置3（GB）" prop="diskconfig3" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="磁盘数量3（块）" prop="disknum3" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="万兆网卡数量（块）" prop="netcardnum" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="光纤卡HBA数量（块）" prop="fibercardnum" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="GPU卡型号" prop="gputype" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="GPU卡数量（块）" prop="gpunum" v-if="show" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="是否专用" prop="isspecial" width="120" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <span v-for="(item, index) of isspecialData" :key="index">
                        <span v-if="item.dictid == scope.row.isspecial">{{ item.dictname}}</span>
                    </span>
                </template>
            </el-table-column>
            <el-table-column label="报价（元）" prop="price" :show-overflow-tooltip="true"></el-table-column>
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
            <AddPhysicalCombo v-if="AddPhysicalComboShow" ref="physicalCombo" />
        </div>

        <el-dialog :title="title" :visible.sync="dialogClose" center width="50%">
            <el-form ref="physicalComboForm" :model="physicalComboForm" :rules="rules" label-width="200px">
                <el-input v-model="physicalComboForm.category" v-if="show" />
                <el-form-item label="套餐小项：">
                    <span v-for="(item, index) of subCategoryData" :key="index">
                        <span v-if="item.dictid == physicalComboForm.subcategory">{{ item.dictname}}</span>
                    </span>
                </el-form-item>

                <el-form-item label="适用场景：" prop="applyscene" :rules="[{ required: true, message: '请填写适用场景', trigger: 'blur'}]">
                    <el-input v-model="physicalComboForm.applyscene"></el-input>
                </el-form-item>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="物理CPU数量（个）：" prop="cpunum">
                            <el-input v-model="physicalComboForm.cpunum" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item label="CPU核数（个）：" prop="cpucore">
                            <el-input v-model="physicalComboForm.cpucore" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="CPU主频（GHz）：">
                            <el-input type="Number" :min="0" v-model="physicalComboForm.frequency" oninput="if(value<0)value=0"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="内存（GB）：" prop="memory">
                            <el-input type="Number" v-model="physicalComboForm.memory" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-input v-model="physicalComboForm.diskconfig1" v-if="show"></el-input>
                    <el-col :span="12">
                        <el-form-item label="磁盘配置1（GB）：" prop="diskconfig1">
                            <el-select placeholder="请选择" v-model="physicalComboForm.diskconfigid1" @change="diskconfigChange1">
                                <el-option v-for="(item,index) in diskConfigList" :key="index" :label="item.describe" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="磁盘数量1（块）：" prop="disknum1">
                            <el-input type="Number" v-model="physicalComboForm.disknum1" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-input v-model="physicalComboForm.diskconfig2" v-if="show"></el-input>
                    <el-col :span="12">
                        <el-form-item label="磁盘配置2（GB）：" prop="diskconfigid2">
                            <el-select placeholder="请选择" v-model="physicalComboForm.diskconfigid2" @change="diskconfigChange2">
                                <el-option v-for="(item,index) in diskConfigList" :key="index" :label="item.describe" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="磁盘数量2（块）：" prop="disknum2">
                            <el-input type="Number" v-model="physicalComboForm.disknum2" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-input v-model="physicalComboForm.diskconfig3" v-if="show"></el-input>
                    <el-col :span="12">
                        <el-form-item label="磁盘配置3（GB）：" prop="diskconfigid3">
                            <el-select placeholder="请选择" v-model="physicalComboForm.diskconfigid3" @change="diskconfigChange3">
                                <el-option v-for="(item,index) in diskConfigList" :key="index" :label="item.describe" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="磁盘数量3（块）：" prop="disknum3">
                            <el-input type="Number" v-model="physicalComboForm.disknum3" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="万兆网卡数量（块）：" prop="netcardnum">
                            <el-input type="Number" v-model="physicalComboForm.netcardnum" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="光纤卡HBA数量（块）：" prop="fibercardnum">
                            <el-input type="Number" v-model="physicalComboForm.fibercardnum" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-input v-model="physicalComboForm.gputype" v-if="show"></el-input>
                    <el-col :span="12">
                        <el-form-item label="GPU卡型号：">
                            <el-select placeholder="请选择" v-model="physicalComboForm.gpuid" @change="gpuChange">
                                <el-option v-for="(item,index) in gpuList" :key="index" :label="item.describe" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="GPU卡数量（块）：" prop="gpunum">
                            <el-input type="Number" v-model="physicalComboForm.gpunum" :min="0" maxlength="20" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="是否专用：">
                            <el-select placeholder="请选择" v-model="physicalComboForm.isspecial">
                                <el-option v-for="item in isspecialData" :key="item.dictid" :label="item.dictname" :value="item.dictid+''"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="报价（元）：" prop="price" :rules="rules.price">
                            <el-input type="Number" :min="0" v-model="physicalComboForm.price" maxlength="20" />
                        </el-form-item>
                    </el-col>
                </el-row>
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
import { queryConfigComboList, upConfigComboData, upConfigStatus, addConfigCombo, queryPhysicalParts } from '@/api/resource/config/resconfig'
import AddPhysicalCombo from './addPhysicalComboData'

export default {
    props: {
        viewFlag: {
            type: String
        }
    },
    name: 'physicalMachineCombo',
    components: { pager, cleanBtn, buttonOrange, helpHint, buttonCommon, AddPhysicalCombo },
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
            physicalComboGrid: [], // 列表分页数据
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
            isspecialData: [], //是否专用
            diskConfigList: [], // 硬盘
            gpuList: [], // GPU卡
            AddPhysicalComboShow: false,
            physicalComboForm: {
                // 表单
                id: '',
                category: '', // 套餐大类
                subcategory: '', // 套餐小项
                applyscene: '', // 适用场景
                cpunum: '', // 物理CPU数量（个）
                cpucore: '', // CPU核数（个）
                frequency: 0, // CPU主频（GHz）
                memory: '', // 内存（GB）
                diskconfig1: '', // SSD磁盘配置（GB）
                disknum1: '', // SSD磁盘数量（块）
                diskconfig2: '', // SAS磁盘配置（GB）
                disknum2: '', // SAS磁盘数量（块）
                diskconfig3: '', // SATA磁盘配置（GB）
                disknum3: '', // SATA磁盘数量（块）
                diskconfigid1: '', // 磁盘配置1（GB）
                diskconfigid2: '', // 磁盘配置2（GB）
                diskconfigid3: '', // 磁盘配置3（GB）
                netcardnum: '', // 万兆网卡数量（块）
                fibercardnum: '', // 光纤卡HBA数量（块）
                gpunum: '', // GPU卡数量（块）
                gputype: '', // GPU卡型号
                gpuid: '', // GPU卡型号id
                isspecial: '', // 是否专用
                price: '' // 报价（元）
            },
            rules: {
                cpunum: [{ validator: checkInteger, trigger: 'blur' }],
                cpucore: [{ validator: checkInteger, trigger: 'blur' }],
                memory: [{ validator: checkInteger, trigger: 'blur' }],
                disknum1: [{ validator: checkInteger, trigger: 'blur' }],
                disknum2: [{ validator: checkInteger, trigger: 'blur' }],
                disknum3: [{ validator: checkInteger, trigger: 'blur' }],
                netcardnum: [{ validator: checkInteger, trigger: 'blur' }],
                fibercardnum: [{ validator: checkInteger, trigger: 'blur' }],
                gpunum: [{ validator: checkInteger, trigger: 'blur' }],
                price: [{ required: true, validator: checkInteger, trigger: 'blur' }]
            },
            error: '' // 错误提示
        }
    },
    created() {},
    mounted() {
        this.loadInit()
        this.getDict()
        this.getPhysicalParts()
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
            param.comboOneId = 1
            param.viewFlag = this.viewFlag
            let data = common.paramsFormat(param)
            queryConfigComboList(data).then((res) => {
                if (res.data.status == 200) {
                    this.physicalComboGrid = res.data.results
                    this.totalNumber = res.data.total
                }
            })
        },

        addData() {
            this.AddPhysicalComboShow = true
            this.$nextTick(() => {
                this.$refs.physicalCombo.init(this.AddPhysicalComboShow)
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
			this.getPhysicalParts()
            var combodata = this.$refs.refPhysicalCombo.selection
            if (combodata.lenght > 1) {
                this.$message.error('只能勾选一条数据')
                return false
            }
            let nowGroup = JSON.parse(JSON.stringify(item))
            this.physicalComboForm = nowGroup
            this.title = '物理机套餐编辑'
            this.dialogClose = true
            this.physicalComboForm.category = '1'
        },

        /**
         * 关闭dialog
         */
        dialogCancel: function () {
            this.dialogClose = false
            Object.keys(this.physicalComboForm).forEach((key) => {
                this.physicalComboForm[key] = ''
            })
            this.error = ''
        },

        /**
         *  提交保存
         */
        dialogAddSubmit: function (saveFlag) {
            this.$refs['physicalComboForm'].validate((valid) => {
                if (valid) {
                    this.error = ''
                    this.physicalComboForm.saveFlag = saveFlag
                    this.physicalComboForm.comboOneId = '1'
                    let datas = common.paramsFormat(this.physicalComboForm)
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
            let combodata = this.$refs.refPhysicalCombo.selection
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
                id += combodata[i].id + '_' + combodata[i].isspecial + ','
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
            param.comboOneId = '1'
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
            let combodata = this.$refs.refPhysicalCombo.selection
            if (combodata.length == 0) {
                this.$message.error('请选中至少一条数据')
                return false
            }
            console.log('选中的物理机套餐数据', combodata)
            addConfigCombo({ list: combodata, saveFlag: '0', comboOneId: '1' }).then((res) => {
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
            getDicEntry({ dicttypeid: 'RES_SUBCATEGORY,RES_CONFIG_STATUS,RES_YESORNO' }).then((res) => {
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
        gpuChange() {
            let gpuOne = this.gpuList.filter((item) => {
                return item.id == this.physicalComboForm.gpuid
            })
            this.physicalComboForm.gputype = gpuOne[0].describe
        },
        diskconfigChange3() {
            let diskconfig = this.diskConfigList.filter((item) => {
                return item.id == this.physicalComboForm.diskconfigid3
            })
            this.physicalComboForm.diskconfig3 = diskconfig[0].describe
        },
        diskconfigChange2() {
            let diskconfig = this.diskConfigList.filter((item) => {
                return item.id == this.physicalComboForm.diskconfigid2
            })
            this.physicalComboForm.diskconfig2 = diskconfig[0].describe
        },
        diskconfigChange1() {
            let diskconfig = this.diskConfigList.filter((item) => {
                return item.id == this.physicalComboForm.diskconfigid1
            })
            this.physicalComboForm.diskconfig1 = diskconfig[0].describe
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