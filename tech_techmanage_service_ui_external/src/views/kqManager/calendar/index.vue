<!-- 系统管理列表 -->
<template>
    <div class="list-container interviewList">
        <!-- 修改后 -->
        <el-form inline>
            <div class="interviewList_head">
                <div>
                    <el-row>                        
                        <el-col :span="12">
                            <el-form-item label="日期：" label-width="80px"> 
                                <el-date-picker v-model="searchForm.entryTime" type="daterange" style="width:250px;"
                                    value-format="yyyy-MM-dd"
                                    start-placeholder="开始时间"
                                    end-placeholder="结束时间" 
                                    /> 
                            </el-form-item>
                        </el-col>
                         <el-col :span="12">
                            <el-form-item label="类型" label-width="80px">
                                <el-select placeholder="类型" style="width:250px;" v-model="searchForm.isHoliday">
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
                <!-- <div class="projectContainer_head_en"></div> -->
                <!-- <el-row type="flex" justify="flex-end">
                    <el-col :span="24">
                        <el-upload v-if="auth" style="display: inline-block;" action="Fake Action" :file-list="fileList"
                        :show-file-list="false" :http-request="importCalendarData">
                        <el-button size="small" type="button">
                            <i class="el-icon-upload2"></i>数据导入
                        </el-button>
                        </el-upload>
                    </el-col>
                </el-row> -->
                <el-upload v-if="auth" style="display: inline-block;" action="Fake Action" :file-list="fileList"
                        :show-file-list="false" :http-request="importCalendarData">
                        <el-button size="small" type="button">
                            <i class="el-icon-upload2"></i>数据导入
                        </el-button>
                        </el-upload>
            </div>
            <el-table :data="crewData" highlight-current-row height="510" @select-all="onSelectTable" @select="onSelectTable">
                <el-table-column label="日期" prop="calendarDate" :formatter="createtimeFormat" :show-overflow-tooltip="true"/> 
                <el-table-column label="星期" prop="dayOfWeek" :formatter="weekDayFormat" :show-overflow-tooltip="true" />
                <el-table-column label="类型" prop="isHoliday" :formatter="holidayFormat" :show-overflow-tooltip="true" />
                <el-table-column label="操作" width="122">
                    <template slot-scope="scope">
                        <el-link type="warning" :underline="false" @click="handelEdit(scope.row)">修改</el-link>
                    </template>
                </el-table-column>
            </el-table>

            <div class="pager_zb">
                <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
            </div>
        </div>
    </div>
</template>
<script> 
    import common from "@/api/common";  
    import { queryPager,importExcel,queryRole } from '@/api/kqManager/calendar';
    import pager from "@/components/Pager";
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
    import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
    import Moment from 'moment';

    export default {
        name: 'Project',
        components: { pager, cleanBtn, buttonOrange, buttonCommon },
        data() {
            return {
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                auth:false,
                searchForm: {
                    code: null, // 系统编号
                    name: null, // 系统名称
                    badge: null, // 系统经理工号
                    empName: null, // 系统经理名称 
                    depId: null, //部门
                }, 
                ryzt_options:[{
                    value: 'N',
                    label:'工作日'
                },{
                    value: 'Y',
                    label:'节假日'
                }],
                fileList:[],
                crewData: [], // 列表分页数据 
                buttonCancel: '取 消',
                buttonSure: '确 定', 
                titleName: "系统信息",
                sizes: 'small', //按钮大小
                buttonSearch: '查 询', //查询按钮
                depData: [],  //部门数据 
                buttonExport: '导 出', //导出按钮   
            };
        }, 
        created() {
            this.loadInit(); 
        }, 
        methods: {
            /**
             * 分页初始化方法
             */
            loadInit: function () {
                let params = {};
                params.size = this.size;
                params.index = this.index;
                this.getPermission();
                this.getPagerList(params);
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
             // 获取权限
            getPermission() {
                queryRole().then(res => {
                    if (res.data.status == 200) {
                        let roles = res.data.results;
                        let auth = false;
                        roles.forEach(function(item){
                            if(item.roleId == "00000001"){
                                auth = true;
                            }
                        });
                        this.auth = auth;
                        console.log("auth",this.auth);
                    } else {
                        this.$message.error('查询权限失败');
                    }
                })
            },

            // 修改
            handelEdit(params){
                this.$router.push({
                    name: 'calendarEdit',
                    query: params
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
                if (row.dayOfWeek != null && row.dayOfWeek == '2') { 
                    txt = '星期一';
                }else if(row.dayOfWeek != null && row.dayOfWeek == '3'){
                     txt = '星期二';
                }else if(row.dayOfWeek != null && row.dayOfWeek == '4'){
                     txt = '星期三';
                }else if(row.dayOfWeek != null && row.dayOfWeek == '5'){
                     txt = '星期四';
                }else if(row.dayOfWeek != null && row.dayOfWeek == '6'){
                     txt = '星期五';
                }else if(row.dayOfWeek != null && row.dayOfWeek == '7'){
                     txt = '星期六';
                }
                return txt;
            },

            /**
             * 搜索事件
             */
            submitBtn: function () {
                let params = this.searchForm; 
                this.getPagerList(params) //bin
            }, 

            /**
             * 点击table行
             */
            onSelectTable: function (selection, row) {

            },
            // 节假日数据导入
            importCalendarData(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                let data = formData;
                importExcel(data).then(res => {
                    if (res.data.status == "200") {
                        this.$message.success('导入成功');
                    } else {
                        this.$message.error(res.data.message);
                    }
                });
            },
            /**
             * 分页触发
             */
            getPager: function (size, index) {
                let params = this.searchForm;
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.getPagerList(params);
            } 
        }
    };
</script>
<style scoped>
    .interviewList {
        padding: 15px;
    }

    .interviewList_head {
        background-color: #ffffff;
        padding: 15px;
        display: flex;
        justify-content: space-between;
    }

    .projectContainer {
        background-color: #ffffff;
        margin-top: 15px;
        padding: 15px;
    }

    .projectContainer_head {
        /* float: right; */
        margin-bottom: 10px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }

    .projectContainer_head_en {
        width: 160px;
    }


    .requireList_head_right {
        float: right;
    }

    .requireList_head_right div {
        display: inline-block;
    }

    .requireList_head_right_clean {
        margin-right: 10px;
    }

    .interviewList_head_right_orange {
        margin: 0 6px 0 10px;
    }

    /* 分页组件位置 */
    .pager_zb {
        width: 100%;
        display: flex;
        flex-direction: row-reverse;
    }
</style>
<style>
       /* element表单修改 */
    .interviewList .el-form-item {
        margin-bottom: 0px;
    }

    /* 表头背景色 */
    .interviewList .el-table th {
        background: #FAFAFA;
    }
</style>