<!-- 离场申请-发起 -->
<template>
    <div class="wblc-container">
        <div class="nav">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="3">
                            <p class="nav_name">考勤节假日数据导入</p>
                        </el-col>
                    </div>
                    <div class="wrapper_right">
                        <el-col :span="1.5">
                            <button-common :buttonName="buttonClose" :size="sizes" :fns="close"></button-common>
                        </el-col>
                    </div>
                </div>
            </el-row>
        </div>
        
        <div class="contentMain interviewList"> 
            <!-- 修改后 -->
            <el-form inline>
                <div class="interviewList_head">
                    <div>
                        <el-row>                        
                            <el-col :span="12">
                                <el-form-item label="日期：" label-width="80px"> 
                                    <el-date-picker v-model="searchForm.entryTime"
                                        type="daterange" size="mini" style="width:250px;"
                                        value-format="yyyy-MM-dd"
                                        start-placeholder="开始时间"
                                        end-placeholder="结束时间" 
                                        /> 
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="类型" label-width="80px">
                                    <el-select placeholder="类型" style="width:250px;" 
                                        size="mini" v-model="searchForm.isHoliday">
                                        <el-option v-for="item in ryzt_options" 
                                        :key="item.value" 
                                        :label="item.label" 
                                        :value="item.value"/>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div>
                        <el-row>
                            <el-col :span="12">
                                <clean-btn :size="sizes" :forms="this.searchForm" />
                            </el-col>
                            <el-col :span="12">
                                <div class="interviewList_head_right_orange">
                                    <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                </div>
            </el-form>
            <div class="projectContainer">
                <div class="projectContainer_head">
                    <div class="projectContainer_head_en"></div>
                    <el-row type="flex" justify="end">
                        <el-col :span="12">
                            <el-upload style="display: inline-block;" action="Fake Action" :file-list="fileList"
                            :show-file-list="false" :http-request="importCalendarData">
                            <el-button size="small" type="button">
                                <i class="el-icon-upload2"></i>数据导入
                            </el-button>
                            </el-upload>
                        </el-col>
                    </el-row>
                </div>
                <el-table :data="crewData" highlight-current-row height="510" @select-all="onSelectTable" @select="onSelectTable">
                    <el-table-column label="日期" prop="calendarDate" width="100" :formatter="createtimeFormat" :show-overflow-tooltip="true"/> 
                    <el-table-column label="星期" prop="dayOfWeek" width="100" :formatter="weekDayFormat" :show-overflow-tooltip="true" />
                    <el-table-column label="类型" prop="isHoliday" width="100" :formatter="holidayFormat" :show-overflow-tooltip="true" />
                </el-table>

                <div class="pager_zb">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="pageSize" v-model="$attrs" ref="pager" />
                </div>
            </div>
            <!-- 操作日志 -->
            <div class="czLog">
                <flow-logs ref="log"></flow-logs>
            </div>
        </div>

        <!-- 底部操作栏 -->
        <div class="nav-bottom">
            <calendar-approve :proving="proving" :flowItem="bps"></calendar-approve>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
    import cleanBtn from "@/components/CleanBtn";
    import { queryPager,importExcel,approve } from '@/api/kqManager/calendar';
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import flowLogs from "@/views/plugins/camunda/FlowLogs";
    import calendarApprove from "@/views/plugins/camunda/calendarApprove";
    import Moment from 'moment'
    export default {
        name: 'jjrsp',
        components: {pager,cleanBtn,  buttonOrange, buttonCommon, buttonDelete, flowLogs, calendarApprove },
        data() {
            return {
                totalNumber: 0,
                importFlag: false,
                pageSize: 10, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                searchForm: {}, 
                buttonClose: '关 闭', //取消按钮名字
                sizes: 'small',  //按钮大小 
                buttonSearch: '查 询', //查询按钮
                editData: {}, 
                crewData:[],
                fileList:[],
                ryzt_options:[{
                    value: 'N',
                    label:'工作日'
                },{
                    value: 'Y',
                    label:'节假日'
                }], 
                bps: {}
            };
        },
        computed: {
        },
        methods: {
            // 初始化
            init(item) {
                console.log("考勤节假日数据导入", item);
                this.bps = item;
                this.bps.processInstId = item.processInstanceId;
                this.editData.processInstId = item.processInstanceId;
                this.$refs.log.init(item.processInstanceId);
            },
             
            // 提交本页面数据
            commit(jljy) {
                const loading = common.loading(this);
                let data = this.editData;
                this.bps.conclusion = jljy.conclusion;
                this.bps.suggest = jljy.suggest;
                data.bps = this.bps;
                return approve(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        return res.data;
                    } else {
                        this.$message.error('操作失败');
                    }
                });
            },
            
            //  时间格式化
            createtimeFormat: function (row, cloumn) {
                let time = '';
                if (row.calendarDate != null) { 
                    time = Moment(row.calendarDate).format('YYYY-MM-DD')
                }
                return time;
            },
            //  类型格式化
            holidayFormat: function (row, cloumn) {
                let txt = '';
                if (row.isHoliday != null && row.isHoliday == 'Y') { 
                    txt = '节假日';
                }
                if (row.isHoliday != null && row.isHoliday == 'N') { 
                    txt = '工作日';
                }
                return txt;
            },
            //  日期格式化
            weekDayFormat: function (row, cloumn) {
                let txt = '星期天';
                if (row.dayOfWeek != null && row.dayOfWeek == 2) { 
                    txt = '星期一';
                }else if(row.dayOfWeek != null && row.dayOfWeek == 3){
                     txt = '星期二';
                }else if(row.dayOfWeek != null && row.dayOfWeek == 4){
                     txt = '星期三';
                }else if(row.dayOfWeek != null && row.dayOfWeek == 5){
                     txt = '星期四';
                }else if(row.dayOfWeek != null && row.dayOfWeek == 6){
                     txt = '星期五';
                }else if(row.dayOfWeek != null && row.dayOfWeek == 7){
                     txt = '星期六';
                }
                return txt;
            },
            // 节假日数据导入
            importCalendarData(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                let data = formData;
                importExcel(data).then(res => {
                    if (res.data.status == "200") {
                        this.importFlag = true;
                        this.$message.success('导入成功');
                        let params = {};
                        params.size = this.pageSize;
                        params.index = this.index;                
                        this.getPagerList(params);
                    } else {
                        this.$message.error(res.data.message);
                    }
                });
            },
            // 获取列表数据
            getPagerList(params) {
                let data = common.paramsFormat(params);
                let self = this
                queryPager(data).then(res => {
                    if (res.data.status == 200) {
                        self.crewData = res.data.results;
                        self.totalNumber = res.data.total;
                        if (self.totalNumber <= 0) {
                            return false
                        }
                    }
                })
            },            
            /**
             * 搜索事件
             */
            submitBtn: function () {
                if(!this.importFlag){
                    this.$message.error('请先导入节假日数据！');  
                    return ;                  
                }
                let params = this.searchForm; 
                this.getPagerList(params) //bin
            }, 
            /**
             * 分页触发
             */
            getPager: function (size, index) {
                let params = this.searchForm;
                params.size = size;
                params.index = index;
                this.pageSize = size;
                this.index = index;
                this.getPagerList(params);
            },
            /**
             * 点击table行
             */
            onSelectTable: function (selection, row) {

            },
            // 流转并验证
            proving(data) {
                let status = new Promise(() => { });
                status = this.commit(data).then((val) => {
                    return val;
                });
                return status
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },
        }
    };
</script>
<style scoped>
    .wblc-container {
        padding-bottom: 85px;
    }

    .contentMain {
        width: 100%;
        height: 100%;
        font-size: 16px;
        padding: 0 20px;
    }
    .interviewList {
        padding: 15px;
    }

    .interviewList_head {
        background-color: #ffffff;
        padding: 15px;
        display: flex;
        justify-content: space-between;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 150px;
        height: 24px;
        font-size: 16px;
        font-weight: 600;
        color: rgba(38, 38, 38, 1);
        line-height: 24px;
        display: inline-block;
    }

    .nav .wrapper {
        margin-left: 25px;
        margin-right: 25px;
        display: flex;
        justify-content: space-between;
    }
    .projectContainer {
        background-color: #ffffff;
        margin-top: 15px;
        padding: 15px;
    }

    .projectContainer_head {
        float: right;
        margin-bottom: 10px;
    }

    .projectContainer_head_en {
        width: 160px;
    }


    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .wrapper-right-keep {
        margin-right: 10px;
    }

    .grxx-title {
        height: 60px;
        line-height: 60px;
        padding: 0 25px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .grxx-title>p {
        color: #262626;
        font-weight: 600;
    }

    .grxx-content {
        background-color: #ffffff;
    }

    .grxx-table {
        width: 100%;
        padding: 10px;
        table-layout: fixed;
    }

    .grxx-table td {
        width: 100%;
        font-size: 15px;
        padding: 5px 0;
    }

    .grxx-table-tr {
        display: flex;
        justify-content: space-between;
    }

    .grxx-table-tr div {
        display: inline-block;
    }

    .grxx-table-tr td:nth-child(odd) {
        color: #909399;
        display: inline-block;
        white-space: nowrap;
    }

    .grxx-table-tr td:nth-child(even) {
        color: #262626;
        display: inline-block;
    }

    .lcxx-mian {
        padding: 0px 18px;
    }

    .lcxx-mian-cell-title {
        color: #262626;
        font-size: 14px;
    }

    .ckdaBt {
        margin-left: 15px;
    }

    .interviewList_head_right_orange {
        margin: 0 6px 0 10px;
    }
    .nav-bottom {
        width: 100%;
        background-color: #ffffff;
        position: fixed;
        bottom: 0;
    }
</style>