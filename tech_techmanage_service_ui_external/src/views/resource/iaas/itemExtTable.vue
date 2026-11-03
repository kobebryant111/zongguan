<!-- iaas资源 -->
<template>
    <div class="app-container">
        <!-- 
            以下情况显示【/】
            物理机：数据库容量
            虚拟机：数据库容量，机房，机柜，机柜位，序列号
            云：操作系统，挂载NAS大小，SAN存储大小，数据库容量（DB云才显示），机房，
            机柜，机柜位，本地Ip，VLAN，网关，掩码，序列号
         -->
        <div class="app-main-container">
            <el-table :data="itemData" row-key="itemId" fit border stripe style="width:100%;">
                <el-table-column prop="nodeName" label="节点名称" align="center"></el-table-column>
                <el-table-column label="类型" prop="modelType" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-for="(item, index) of modelTypeData" :key="index">
                            <span v-if="item.dictid == scope.row.modelType">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="modelConfigName" label="配置" align="center" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="os" label="操作系统" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='3'">/</span>
                        <span v-else v-for="(item, index) of softComboData1 =softComboData.filter((item)=>{
                            return item.id == scope.row.os
                            })" :key="index">
                            <span>{{ item.softname+' '+item.softversion}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="netWorkAreaName" label="网络区域" align="center" :show-overflow-tooltip="true"></el-table-column>
                <!-- <el-table-column prop="isNas" label="是否NAS存储" align="center" :v-if="show">
                    <template slot-scope="scope">
                        <span v-for="(item, index) of isStorageData" :key="index">
                            <span v-if="item.dictid == scope.row.isNas">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column> -->
                <el-table-column prop="nasSize" label="挂载NAS大小(G)" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.nasSize}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="storageSize" label="SAN存储大小(G)" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.storageSize}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="dbSize" label="数据库容量(G)" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='3' && scope.row.nodeType=='3'">{{scope.row.dbSize}}</span>
                        <span v-else>/</span>
                    </template>
                </el-table-column>
                <el-table-column prop="machineRoom" label="机房" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='2' || scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.machineRoom}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="cabinet" label="机柜" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='2' || scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.cabinet}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="cabinetPos" label="机柜位" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='2' || scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.cabinetPos}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="ip" label="本地IP" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.ip}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="serverIp" label="服务IP" align="center" v-if="show" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="vlan" label="VLAN" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.vlan}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="gateway" label="网关" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.gateway}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="mask" label="掩码" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.mask}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="sn" label="序列号" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.modelType =='2' || scope.row.modelType =='3'">/</span>
                        <span v-else>{{scope.row.sn}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="所属中心" prop="owncenter" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-for="(item, index) of owncenterData" :key="index">
                            <span v-if="item.dictid == scope.row.owncenter">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column v-if="isXtb || isJcsc || isWlb" label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button v-if="isXtb" type="primary" size="mini" @click="fileEdit(scope.row, '0')">序列号</el-button>
                        <el-button v-if="isJcsc && scope.row.modelType =='1'" type="primary" size="mini" @click="fileEdit(scope.row, '1')">机 房</el-button>
                        <el-button v-if="isWlb" type="primary" size="mini" @click="fileEdit(scope.row, '2')">I P</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 序列号编辑 -->
        <el-dialog :title="formTitle" :visible.sync="formDialog" center width="30%">
            <el-form :model="form" ref="form" :rules="formRules" inline label-width="120px">
                <el-row>
                    <el-col :span="24" v-if="formType == '0'">
                        <el-form-item label="序列号：" prop="sn">
                            <el-input v-model="form.sn" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- 机房 -->
                    <el-col :span="24" v-if="formType == '1'">
                        <el-form-item label="机房：" prop="machineRoom">
                            <el-input v-model="form.machineRoom" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- 机柜 -->
                    <el-col :span="24" v-if="formType == '1'">
                        <el-form-item label="机柜：" prop="cabinet">
                            <el-input v-model="form.cabinet" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-if="formType == '1'">
                        <el-form-item label="机柜位：" prop="cabinetPos">
                            <el-input v-model="form.cabinetPos" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- IP -->
                    <!-- <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="f5ip" prop="f5ip">
                            <el-input v-model="form.f5ip" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="ip" prop="ip">
                            <el-input v-model="form.ip" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="服务ip" prop="serverIp">
                            <el-input v-model="form.serverIp" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="scanIp" prop="scanIp">
                            <el-input v-model="form.scanIp" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="vcsIp" prop="vcsIp">
                            <el-input v-model="form.vcsIp" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="vip" prop="vip">
                            <el-input v-model="form.vip" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="vlan" prop="vlan">
                            <el-input v-model="form.vlan" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="网关" prop="gateway">
                            <el-input v-model="form.gateway" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-if="formType == '2'">
                        <el-form-item label="掩码" prop="mask">
                            <el-input v-model="form.mask" size="mini" style="width: 100%" clearable></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-check" type="primary" @click="save" size="mini">确 定</el-button>
                <el-button icon="el-icon-close" type="primary" @click="close" size="mini">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import common from '@/api/common'
import { queryItemExtList, saveItemExt } from '@/api/resource/iaas/iaas'
import { querySoftList } from '@/api/resource/config/resconfig'
import { getDicEntry } from '@/api/eosDictEntry'
export default {
    components: {},
    props: {
        // 集合数据
        itemData: {
            type: Array
        },
        // 保存成功的调用
        saveSuccess: {
            type: Function
        },
        // 系统部
        isXtb: {
            typr: Boolean,
            default: false
        },
        // 基础设施部
        isJcsc: {
            typr: Boolean,
            default: false
        },
        // 网络部
        isWlb: {
            typr: Boolean,
            default: false
        }
    },
    data() {
        return {
            show: false,
            // 弹窗标识
            formDialog: false,
            // 弹窗标题
            formTitle: '',
            // 弹窗类型: 0-序列号，1-机房
            formType: '0',
            form: {
                id: '',
                addedItemId: '',
                sn: '',
                machineRoom: '',
                cabinet: '',
                cabinetPos: '',
                f5ip: '',
                ip: '',
                serverIp: '',
                scanIp: '',
                vcsIp: '',
                vip: '',
                vlan: '',
                gateway: '',
                mask: ''
            },
            modelTypeData: [], //选项类型名称
            owncenterData: [], // 所属中心
            softComboData: [], //操作系统数据
            isStorageData: [], //是否挂载存储
            formRules: {
                // sn: [
                //     { required: true, message: '请输入序列号', trigger: 'blur' },
                // ],
                // machineRoom: [
                //     { required: true, message: '请输入机房', trigger: 'blur' }
                // ],
                // cabinet: [
                //     { required: true, message: '请输入机柜', trigger: 'blur' }
                // ],
                // cabinetPos: [
                //     { required: true, message: '请输入机柜位', trigger: 'blur' }
                // ],
                // f5ip: [
                //     { required: true, message: '请输入f5ip', trigger: 'blur' }
                // ],
                // ip: [
                //     { required: true, message: '请输入本地ip', trigger: 'blur' }
                // ],
                // serverIp: [
                //     { required: true, message: '请输入服务ip', trigger: 'blur' }
                // ],
                // scanIp: [
                //     { required: true, message: '请输入scanIp', trigger: 'blur' }
                // ],
                // vcsIp: [
                //     { required: true, message: '请输入vcsIp', trigger: 'blur' }
                // ],
                // vip: [
                //     { required: true, message: '请输入vip', trigger: 'blur' }
                // ],
                // vlan: [
                //     { required: true, message: '请输入vlan', trigger: 'blur' }
                // ],
                // gateway: [
                //     { required: true, message: '请输入gateway', trigger: 'blur' }
                // ],
                // mask: [
                //     { required: true, message: '请输入mask', trigger: 'blur' }
                // ],
            }
        }
    },
    created() {},
    mounted() {
        this.init()
        this.getDict()
        this.getSoftData()
    },
    methods: {
        init() {
            // console.log("tempData", this.tempData);
        },
        save() {
            let data = common.paramsFormat(this.form)
            this.$refs.form.validate((valid) => {
                if (valid) {
                    saveItemExt(data).then((res) => {
                        if (res.data.status != 200) {
                            this.$alert(res.data.message, { type: 'error' })
                            return false
                        }
                        this.formDialog = !this.formDialog
                        this.$refs.form.resetFields()
                        this.saveSuccess()
                    })
                } else {
                    return false
                }
            })
        },
        close() {
            this.formDialog = !this.formDialog
            this.$refs.form.resetFields()
            this.$refs.form.clearValidate()
        },
        // type: 0-序列号，1-机房，2-IP
        fileEdit(row, type) {
            this.formDialog = !this.formDialog
            if ('0' == type) {
                this.formTitle = '序列号编辑'
                this.formType = '0'
            }
            if ('1' == type) {
                this.formTitle = '机房编辑'
                this.formType = '1'
            }
            if ('2' == type) {
                this.formTitle = 'IP编辑'
                this.formType = '2'
                // IP编辑，虚拟机vlan必填
                if ('2' == row.modelType) {
                    this.formRules = {
                        ...this.formRules,
                        vlan: [{ required: true, message: '请输入vlan', trigger: 'blur' }]
                    }
                } else {
                    this.formRules = {
                        ...this.formRules,
                        vlan: []
                    }
                }
            }
            this.setValueToForm(row)
        },
        // 从列表中取值赋值到页面弹窗
        setValueToForm(row) {
            // 直接从父类传递的参数中获取数据，没有值则置空
            // 赋itemId，itemId必须存在
            this.form.addedItemId = row.addedItemId
            // 没有值时置空
            this.form.id = this.checkNull(row.id)
            this.form.sn = this.checkNull(row.sn)
            this.form.machineRoom = this.checkNull(row.machineRoom)
            this.form.cabinet = this.checkNull(row.cabinet)
            this.form.cabinetPos = this.checkNull(row.cabinetPos)
            this.form.f5ip = this.checkNull(row.f5ip)
            this.form.ip = this.checkNull(row.ip)
            this.form.serverIp = this.checkNull(row.serverIp)
            this.form.scanIp = this.checkNull(row.scanIp)
            this.form.vcsIp = this.checkNull(row.vcsIp)
            this.form.vip = this.checkNull(row.vip)
            this.form.vlan = this.checkNull(row.vlan)
            this.form.gateway = this.checkNull(row.gateway)
            this.form.mask = this.checkNull(row.mask)
        },
        // 检查是否为空
        checkNull(value) {
            if (null == value || '' == value || undefined == value) {
                value = ''
            }
            return value
        },
        // 查询业务字典
        getDict() {
            getDicEntry({ dicttypeid: 'RES_MODELTYPE,RES_OWNCENTER,RES_YESORNO' }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //选项类型
                this.modelTypeData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_MODELTYPE'
                })

                //所属中心
                this.owncenterData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_OWNCENTER'
                })
                //是否挂载存储
                this.isStorageData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_YESORNO'
                })
                return data
            })
        },
        // 获取列表数据
        getSoftData() {
            let param = {}
            param.comboOneId = 7
            param.viewFlag = '3'
            let data = common.paramsFormat(param)
            querySoftList(data).then((res) => {
                if (res.data.status == 200) {
                    this.softComboData = res.data.results
                }
            })
        }
    }
}
</script>
<style lang="scss">
.el-col {
    margin-bottom: 10px;
}
</style>