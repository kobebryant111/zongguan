<!-- 资产统计报表 -->
<template>
    <el-dialog :title="titleName" :visible.sync="addDialog" center width="80%">
        <el-scrollbar style="height:100%">
            <div style="height:500px">
                <el-form :model="usesysForm" ref="usesysForm" inline>
                    <el-row>
                        <el-col :span="10">
                            <el-form-item label="资源分类：" prop="resclassify" label-width="120px">
                                <el-select placeholder="请选择" v-model="usesysForm.resclassify" size="mini">
                                    <el-option v-for="item in classifyData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="IP：" prop="ip">
                                <el-input v-model="usesysForm.ip" clearable size="mini"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="10">
                            <el-form-item label="序列号：" prop="resclassify" label-width="120px">
                                <el-input v-model="usesysForm.serialno" clearable size="mini" style="width: 100%"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-button icon="el-icon-refresh" @click="onReset" size="mini">重置</el-button>
                            <el-button icon="el-icon-search" type="primary" @click="onSearch" size="mini">搜索</el-button>
                        </el-col>
                    </el-row>
                </el-form>
                <el-button-group>
                    <el-button type="primary" size="mini" @click="confimSelect">确定</el-button>
                </el-button-group>
                <el-table :data="tempData" ref="tempData" border stripe style="width:100%;">
                    <el-table-column type="selection" width="35"></el-table-column>
                    <el-table-column prop="deloradd" label="动作" :show-overflow-tooltip="true" v-if="show"></el-table-column>
                    <el-table-column prop="ciid" label="ciid" v-if="show" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="ciclassifykey" label="分类key" :show-overflow-tooltip="true" align="center" v-if="show"></el-table-column>
                    <el-table-column prop="ciclassifyname" label="分类" :show-overflow-tooltip="true" align="center"></el-table-column>
                    <el-table-column prop="ciname" label="资源名称" :show-overflow-tooltip="true" align="center"></el-table-column>
                    <el-table-column prop="localip" label="本地IP" align="center"></el-table-column>
                    <el-table-column prop="osversion" label="操作系统版本" align="center"></el-table-column>
                    <el-table-column prop="logicalresourcestats" label="逻辑设备状态" align="center">
                        <template slot-scope="scope">
                            <span v-for="(item, index) of logicalStatsData" :key="index">
                                <span v-if="item.dictid == scope.row.logicalresourcestats">{{ item.dictname}}</span>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="physicalresourcestats" label="物理设备状态" align="center">
                        <template slot-scope="scope">
                            <span v-for="(item, index) of physicalStatsData" :key="index">
                                <span v-if="item.dictid == scope.row.physicalresourcestats">{{ item.dictname}}</span>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="usertype" label="用户类型" align="center"></el-table-column>
                    <el-table-column prop="explain" label="特例说明" align="center"></el-table-column>
                    <el-table-column prop="serialno" label="序列号" align="center"></el-table-column>
                </el-table>
                <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs" />
            </div>
        </el-scrollbar>
    </el-dialog>
</template>
<script>
import cleanBtn from '@/components/CleanBtn'
import common from '@/api/common'
import pager from '@/components/Pager'
import { getDicEntry } from '@/api/eosDictEntry'
import { queryUseSysPageList } from '@/api/usesysresourcealter/usesysresourcealter'
export default {
    components: { cleanBtn, pager },
    data() {
        return {
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            page: {
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1 // 初始值 默认：1
            },
            usesysForm: {
                resclassify: 'C_machine',
                ip: '',
                serialno: ''
            },
            addDialog: false, //是否打开发起页面
            show: false,
            titleName: '', //大标题名字
            classifyData: [], // 资源分类
            logicalStatsData: [], //逻辑设备状态
            physicalStatsData: [], //物理设备状态
            delOrAddData: [
                { dictid: 1, dictname: '新增' },
                { dictid: 2, dictname: '删除' },
                { dictid: 3, dictname: '修改' }
            ], //动作
            ids: '',
            tempData: [],
            flowData: [] //
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {},
    methods: {
        init(ids) {
            this.ids = ids
            this.usesysForm = {
                resclassify: 'C_machine',
                ip: '',
                serialno: ''
            }
            this.tempData = []
            this.addDialog = true
            this.onSearch()
        },

        onSearch() {
            let param = {
                ...this.usesysForm,
                ...this.page,
                ids: this.ids
            }
            let data = common.paramsFormat(param)
            queryUseSysPageList(data).then((res) => {
                if (res.data.status == 200) {
                    this.tempData = res.data.results
                    this.page.totalNumber = res.data.total
                } else {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
            })
        },

        // 查询业务字典
        getDictData() {
            getDicEntry({
                dicttypeid: 'RES_CLASSIFY,RES_LOGICAL_STATS,RES_PHYSICAL_STATS'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //资源分类
                this.classifyData = data.filter((item) => {
                    return item.dicttypeid == 'RES_CLASSIFY'
                })
                //逻辑设备状态
                this.logicalStatsData = data.filter((item) => {
                    return item.dicttypeid == 'RES_LOGICAL_STATS'
                })
                //物理设备状态
                this.physicalStatsData = data.filter((item) => {
                    return item.dicttypeid == 'RES_PHYSICAL_STATS'
                })
                return data
            })
        },
        confimSelect() {
            let useSysData = this.$refs.tempData.selection

            if (useSysData.length == 0) {
                this.$message.error('至少选择一条记录')
                return false
            }
            //向父组件添加数据
            for (var i = 0; i < useSysData.length; i++) {
                this.$parent.addUseSysData(useSysData)
            }
            this.addDialog = false
        },
        /**
         * 查询条件清除
         */
        onReset() {
            this.usesysForm = {
                resclassify: 'C_machine',
                ip: '',
                serialno: ''
            }
        },
        /**
         * 分页触发
         */
        getPager: function (size, index) {
            this.page.size = size
            this.page.index = index
            this.onSearch()
        } // end
    }
}
</script>
<style lang="scss">
.el-form-item {
    margin-bottom: 5px;
}
.hidden {
    display: none;
}
</style>

