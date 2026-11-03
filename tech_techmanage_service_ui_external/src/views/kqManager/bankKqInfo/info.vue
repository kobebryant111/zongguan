<template>
    <div>
        <!-- 头部 -->
        <div class="top">
            <el-row type="flex" justify="space-between" align="center">
                <el-col :span="22">
                    <h2>详情</h2>
                </el-col>
                <el-col :span="2">
                    <el-button style="margin-top: 5px" @click="toClose">关闭</el-button>
                </el-col>
            </el-row>
        </div>
        <!-- 个人信息 -->
        <div class="marginWrapper">
            <h3>个人信息</h3>
            <el-form :model="formData" ref="formData">
                <el-row type="flex" justify="space-between">
                    <el-col :span="4">
                        <el-form-item label="工号">
                            <el-input v-model="formData.jobId" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="所属部门">
                            <el-input v-model="formData.deptName" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="姓名">
                            <el-input v-model="formData.userName" disabled></el-input>
                        </el-form-item>
                    </el-col>
                     <el-col :span="4">
                        <el-form-item label="考勤年月">
                            <el-input v-model="formData.attendanceMonth" disabled></el-input>
                        </el-form-item>
                    </el-col>
                     <el-col :span="4">
                        <el-form-item label="忘记打卡次数">
                            <el-input v-model="formData.forgetClockInNumber" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    
                </el-row>
                <el-row type="flex" justify="space-between">
                  
                    <el-col :span="4">
                        <el-form-item label="迟到次数">
                            <el-input v-model="formData.belateNumber" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="早退次数">
                            <el-input v-model="formData.leaveEarlyNumber" disabled></el-input>
                        </el-form-item>
                    </el-col>
                     <el-col :span="4">
                         <el-form-item label="当月应出勤">
                            <el-input v-model="formData.requiredAttendanceDays" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="实际出勤天数">
                            <el-input v-model="formData.actualAttendanceDays" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="当月工作时长(h)">
                            <el-input v-model="formData.worktimeHours" disabled></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <!-- 考勤记录 -->
        <div class="marginWrapper">
            <h3>考勤记录</h3>
            <el-table :data="tableData" :max-height="clienHeight" :row-class-name="tableRowClassName">
                <!-- <el-table-column prop="deptName" label="所属部门" fixed width="120px" align="center" ></el-table-column> -->
                <el-table-column prop="userName" label="姓名" width="80px" align="center"></el-table-column>
                <el-table-column prop="attendanceDate" label="日期" width="100px" align="center"></el-table-column>
                <el-table-column prop="isHoliday" label="是否节假日" width="90px" align="center">
                    <template slot-scope="scope">
                        {{ scope.row.isHoliday === '0' ? '否' : '是'}}
                    </template>
                </el-table-column>
                <!-- <el-table-column prop="overtimeStartTime" label="加班开始时间" width="200px" align="center"></el-table-column> -->
                <el-table-column prop="workingTime" label="上班时间" width="100px" align="center"></el-table-column>
                <el-table-column prop="closingTime" label="下班时间" width="100px" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.whetherOvernight === '1'">{{scope.row.closingTime + '+'}}</span>
                        <span v-else>{{scope.row.closingTime}}</span>
                    </template>
                </el-table-column>
                <!-- <el-table-column prop="overtimeStartTime" label="加班开始时间" width="105px" align="center">
                    <template slot-scope="scope">
                        <span v-show="scope.row.isHoliday === '0'">{{scope.row.overtimeStartTime}}</span>
                    </template>
                </el-table-column> -->
                <el-table-column prop="worktimeHours" label="工作时长(min)" width="115px" align="center"></el-table-column>
            </el-table>

            <!-- 弹框 -->
            <el-dialog
                title="提示"
                :visible="dialogVisible"
                :before-close="handleClose"
            >
                <span>该异常考勤信息已无法修改</span>
                <span slot="footer" >
                    <el-button @click="dialogVisible=false">取 消</el-button>
                    <el-button @click="dialogVisible=false">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import common from "@/api/common";
import { update, queryDetail } from '@/api/kqManager/empAttendance'
import { getDicEntry, getDictListForStatus } from '@/api/eosDictEntry'

export default {
    data() {
        return {
            formData: {},
            tableData: [],
            id: '',
            options: [
                {
                    id: 0,
                    label: '否',
                    value: '0'
                },
                {
                    id: 1,
                    label: '是',
                    value: '1'
                }
            ],
            amAbnormalReasonList: [], // 上午异常原因
            pmAbnormalReasonList: [], // 下午异常原因
            abnormalReasonList: [], //显示对应的异常原因
            dialogVisible: false, // 弹框
            isEdit: false,
            clienHeight: ''
        }
    },
    created() {
        this.id = this.$route.query.id || ''
        this.getDic()
        this.getReasonDict();
        this.getInfo()
        this.clienHeight = document.body.clientHeight - 330;
    },
    methods: {
        getInfo() {
            const loading = common.loading(this);
            queryDetail(this.id).then( res => {
                console.log(res)
                loading.close(); 
                if(res.data.status === 200) {
                    this.$nextTick(() => {
                        this.formData = res.data.results
                        if (this.formData.exemptionNumber == undefined){
                            this.formData.exemptionNumber = '2'
                        }
                        this.tableData = res.data.results.kqRecordDtoList
                    })
                }else {
                    this.$message.error('获取数据失败')
                }
            })
        },
        getDic() {
            getDicEntry({ dicttypeid: "KQ_RECORD_REASON_DICT" }).then(res => {
                // console.log(res,"字典");
                const { results, status } = res.data;
                if (status == 200) {
                    this.abnormalReasonList = results;
                }
            })
        },
        getReasonDict() {
            getDictListForStatus({dicttypeid: "KQ_RECORD_REASON_DICT" ,status: 1}).then(res => {
                const { results, status } = res.data;
                if (status == 200) {
                    this.amAbnormalReasonList = results;
                    this.pmAbnormalReasonList = results;
                }
            })
        },
        getReasonName(val){
            var value = val;
            if (value == undefined){
                return '';
            }
            // console.log("选择的数据:",value)
            if (value.includes("/")){
                let arr = value.split("/");
                console.log("选择的数据成数组:",arr)
                let retVal = '';
                arr.forEach(item =>{
                    for(var i=0;i<this.abnormalReasonList.length;i++){
                        if(this.abnormalReasonList[i].dictid === item){
                            retVal = retVal + ' ' +  this.abnormalReasonList[i].dictname
                            continue;
                        }
                    }
                })
                console.log("返回的值：",retVal)
                return retVal
            }else{
                for(var i=0;i<this.abnormalReasonList.length;i++){
                    if(this.abnormalReasonList[i].dictid === val){
                        return this.abnormalReasonList[i].dictname
                    }
                }
            }
        },

        // 修改按钮
        toModify(row) {
            // console.log(row, '修改按钮')
            if(row.whetherUpdate) {
                this.$message.error('当前时间段已无法修改上个月考勤')
                return
            }
            row.actionType = true
        },
        // 保存按钮
        toSave(row) {
            console.log(row, '保存按钮');
            // 需传ID
            let rowData = JSON.parse(JSON.stringify(row))
            if(rowData.amWhetherAbnormal === '1' && (rowData.amAbnormalReason == undefined || rowData.amAbnormalReason == '')) {
                return this.$message.error('请填写上午异常原因')
            }
            if(rowData.pmWhetherAbnormal === '1' && (rowData.pmAbnormalReason == undefined || rowData.pmAbnormalReason == '')) {
                return this.$message.error('请填写下午异常原因')
            }

            
            let data = common.paramsFormat(rowData);
            update(data).then(res => {
                console.log(res, '66666666666666')
            })
            row.actionType = false;
        },
        
        toClose() {
            this.$router.push({ name: 'bankKq' });
        },
        handleClose: function() {
            this.dialogVisible = false
        },
        handleSelectAbnormal(row, date) {
            // console.log(row, date)
            if(date === 'am') {
                row.amAbnormalReasonList = []
            } else if(date === 'pm') {
                row.pmAbnormalReasonList = []
            }
        }, 
        tableRowClassName({row, rowIndex}) {
            // console.log(row, rowIndex, 'qqqqqqqqqqqqqqqqqqqqqqqq')
            if(row.whetherAbnormal === '1') {
                return'warring-row'
            }
        }
    },
}
</script>

<style scoped>
.margin_bottom {
    margin-bottom: 15px;
}
.marginWrapper {
    margin: 15px;
    background-color: #ffffff;
    padding: 15px;
}
.top { 
    background-color: #ffffff;
    padding: 20px;
}
</style>

<style>
.el-table .warring-row {
    color: #ff590b;
}
</style>
