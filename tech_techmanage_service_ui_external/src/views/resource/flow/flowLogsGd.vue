<template>
    <!-- 操作日志 -->
    <div class="contentLog" v-if="isLogShow">
        <div class="contentTwo_title">
            <p class="contentTwo_title_p">操作日志</p>
        </div>
        <div class="content">
            <!-- 表第一行 -->
            <div>
                <el-row :gutter="60">
                    <el-col :span="24">
                        <div class="sex">
                            <el-table :data="fdata" style="width: 100%">
                                <el-table-column prop="createtime" label="操作时间" width="270" :formatter="createtimeFormat"></el-table-column>

                                <el-table-column label="操作人" width="270" v-if="status == 'gd'">
                                    <template slot-scope="scope">
                                        <span style="color:rgba(251, 164, 47, 1)" v-if="scope.row.isfalse == '01'">{{scope.row.userName}}(处理中)</span>
                                        <span v-else>{{scope.row.userName}}</span>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="username" label="操作人" width="270" v-if="status == 'db'">
                                    <template slot-scope="scope">
                                        <span style="color:rgba(251, 164, 47, 1)" v-if="scope.row.isfalse == '01'">
                                            {{scope.row.username}}(处理中)
                                        </span>
                                        <span v-else>
                                            {{scope.row.username}}
                                        </span>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="applydesc" label="流程节点" width="270" :show-overflow-tooltip="true"></el-table-column>
                                <el-table-column prop="conclusion" label="决策" width="140" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                        <span v-if="scope.row.conclusion == '01'">通过</span>
                                        <span v-else-if="scope.row.conclusion == '02'">退回</span>
                                        <span v-else-if="scope.row.conclusion == '03'">授权</span>
                                        <span v-else-if="scope.row.conclusion == '04'">撤回</span>
                                        <span v-else-if="scope.row.conclusion == '05'">调度</span>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="suggest" label="意见" :show-overflow-tooltip="true"></el-table-column>
                            </el-table>
                        </div>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>
<script>
import { getGdLogs, getFlowLog } from '@/api/common/flowApi'
import common from '@/api/common'
import Moment from 'moment'
export default {
    name: 'flowLogsGd',
    props: {
        /**
         * 流程实例id
         */
        processInstId: {
            type: [Number, String]
        }
    },
    data() {
        return {
            fdata: [], // 列表数据
            isLogShow: true, // 是否显示日志 默认：显示
            status: 'db'
        }
    },
    methods: {
        init: function (item) {
            this.isLogShow = true
            if (item.status == 'gd' && (item.flowStatus == 'yjs' || item.flowstatus == '已结束')) {
                this.status = 'gd'
                getGdLogs({ flowcaseno: item.instanceid })
                    .then((res) => {
                        if (res.data.status != 200) {
                            this.$alert(res.data.message, { type: 'error' })
                            return false
                        }
                        this.fdata = res.data.results
                        if (this.fdata.length <= 0) {
                            this.isLogShow = false
                        }
                    })
                    .catch((error) => {
                        this.$alert(error, { type: 'error' })
                    })
            } else {
                this.status = 'db'
                let instid = ''
                if (undefined == item.processInstID) {
                    instid = item.instanceid
                } else {
                    instid = item.processInstID
                }
                getFlowLog({ flowcaseno: instid })
                    .then((res) => {
                        if (res.data.status != 200) {
                            this.$alert(res.data.message, { type: 'error' })
                            return false
                        }
                        this.fdata = res.data.results
                        if (this.fdata.length <= 0) {
                            this.isLogShow = false
                        }
                    })
                    .catch((error) => {
                        this.$alert(error, { type: 'error' })
                    })
            }
        },
        /**
         * 提交时间格式化
         */
        createtimeFormat: function (row, cloumn) {
            let time = ''
            if (null != row.createtime) {
                // time = common.dateFormat(row.createtime, "yyyy-MM-dd HH:mm:ss");
                time = Moment(row.createtime).format('YYYY-MM-DD HH:mm:ss')
            }
            return time
        }
    }
}
</script>

<style scoped>
/* 操作日志 */
.contentLog {
    margin-top: 15px;
    background-color: #ffffff;
}

.contentLog .content {
    font-size: 16px;
    padding: 12px 20px 15px 20px;
}

.contentTwo_title {
    height: 60px;
    line-height: 60px;
    background-color: #ffffff;
    padding: 0 25px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.contentTwo_title_p {
    font-size: 16px;
    color: #262626;
    font-weight: 600;
}
</style>

<style>
/* 表格样式修改 */
.contentLog .el-table th {
    color: #262626;
    background: #fafafa;
}
</style>
