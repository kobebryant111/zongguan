<!-- 系统管理列表 -->
<template>
    <div class="list-container interviewList">
        <!-- 修改后 -->
        <el-form inline>
            <div class="interviewList_head">
                <div>              
                    <el-row>         
                        <el-col :span="8">
                            <el-form-item label="人员工号:" label-width="80px"> 
                                <el-input placeholder="请输入"  size="mini" v-model="searchForm.badge"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                             <el-form-item label="人员姓名:" label-width="80px"> 
                                <el-input placeholder="请输入"  size="mini" v-model="searchForm.empname"></el-input>
                            </el-form-item>
                        </el-col>  
                        <el-col :span="8">
                            <el-form-item label="人员状态:" label-width="80px"> 
                                <el-input placeholder="请输入"  size="mini" v-model="searchForm.empstatus"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="所属部门:" label-width="80px"> 
                                <el-select placeholder="所属部门"  size="mini" v-model="searchForm.depid" filterable>
                                    <el-option v-for="item in depData" 
                                    :key="item.id" 
                                    :label="item.text" 
                                    :value="item.id"/>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="申请类型:" label-width="80px"> 
                                <el-select placeholder="申请类型"  size="mini" v-model="searchForm.applyType" filterable>
                                    <el-option v-for="item in applyTypeData" 
                                    :key="item.value" 
                                    :label="item.label" 
                                    :value="item.value"/>
                                </el-select>
                            </el-form-item>
                        </el-col>
                         <el-col :span="8">
                             <el-form-item label="权限状态:" label-width="80px"> 
                                <el-select placeholder="请选择" size="mini" v-model="searchForm.flag" filterable>
                                    <el-option :value="1" label="已生效"/>
                                    <el-option :value="0" label="待生效"/>
                                    <el-option :value="-1" label="已失效"/>
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
                <div>
                    <buttonCommon :buttonName="buttonExport" :size="sizes" class="buttonExport" :fns="handelExport"></buttonCommon> 
                    <buttonOrange :buttonName="applyName" :size="sizes" :fns="startAuthorityApply"></buttonOrange> 
                </div>
            </div>     
            <el-table :data="crewData" highlight-current-row height="510" @select-all="onSelectTable" @select="onSelectTable">
                <el-table-column label="工号" prop="badge" width="100" :show-overflow-tooltip="true"/> 
                <el-table-column label="姓名" prop="empname" width="120" :show-overflow-tooltip="true" />
                <el-table-column label="所属部门" prop="depname" width="200" :show-overflow-tooltip="true" />
                <el-table-column label="人员状态" prop="empstatus" width="100" :show-overflow-tooltip="true" />
                <el-table-column label="楼层权限" prop="zjname" width="200" :show-overflow-tooltip="true" />
                <el-table-column label="申请类型" prop="applyType" width="100" :formatter="applyTypeFormat" :show-overflow-tooltip="true" />
                <el-table-column label="开始时间" prop="beginDate" width="100" :formatter="beginDateTimeFormat" :show-overflow-tooltip="true" />
                <el-table-column label="结束时间" prop="endDate" width="100" :formatter="endDateTimeFormat" :show-overflow-tooltip="true" />
                <el-table-column label="权限状态" prop="flag" width="100" :formatter="applyFlagFormat" :show-overflow-tooltip="true" />                
                <el-table-column label="操作" width="122">
                    <template slot-scope="scope">
                        <el-link type="warning" :underline="false" @click="authorityApplyProgress(scope.row)">进程</el-link>                                          
                        <el-link type="warning" v-if="scope.row.flag == 1" 
                            :underline="false" @click="changeAuthorityFlag(scope.row)">停用</el-link>
                        <el-link type="warning" v-if="scope.row.flag == -1" 
                            :underline="false" @click="changeAuthorityFlag(scope.row)">启用</el-link>                         
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
    import pager from "@/components/Pager";
    import cleanBtn from "@/components/CleanBtn";
    import { queryPager, exportExcel,changeFlag } from '@/api/gateManager/authority';
    import { queryResourceDept } from "@/api/integrated/wbryzrlc";
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
    import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
    import Moment from 'moment';

    export default {
        name: 'authority',
        components: { pager, cleanBtn, buttonOrange, buttonCommon },
        data() {
            return {
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                auth:false,
                searchForm: {}, 
                crewData: [], // 列表分页数据 
                buttonCancel: '取 消',
                buttonSure: '确 定', 
                titleName: "系统信息",
                sizes: 'small', //按钮大小
                buttonSearch: '查 询', //查询按钮
                depData: [],  //部门数据 
                applyName: '权限申请',
                buttonExport: '导 出', //导出按钮   
                applyTypeData:[{
                    value: 0,
                    label: "临时"
                },{
                    value: 1,
                    label: "短期"
                },{
                    value: 2,
                    label: "长期"
                }]
            };
        }, 
        created() {
            this.loadInit(); 
            this.filDep();
        }, 
        methods: {
            /**
             * 分页初始化方法
             */
            loadInit: function () {
                let params = {};
                params.size = this.size;
                params.index = this.index;
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
             // 导出
            handelExport: function () {
                let searchData =  this.searchForm;
                let params = common.paramsFormat(searchData);
                const self = this;
                exportExcel(params).then(res => {
                    common.download(res.data, self, "闸机权限.xls");
                });
            },
            // 获取部门
            filDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params)
                .then(res => {
                    let fild = res.data.results[0].children;
                    let name = res.data.results[0].text;
                    this.depData = fild.filter(tt => {
                    tt.text = tt.text.replace(name + "-", "");
                    tt.id = tt.id.replace('dep', '');
                    return tt;
                    });
                }).catch(error => {
                    this.$message.error(error)
                });
            },
            //  时间格式化
            beginDateTimeFormat: function (row, cloumn) {
                let time = '';
                if (row.beginDate != null) { 
                    time = Moment(row.beginDate).format('YYYY-MM-DD')
                    return time;
                }               
            },
            //  时间格式化
            endDateTimeFormat: function (row, cloumn) {
                let time = '';
                if (row.endDate != null) { 
                    time = Moment(row.endDate).format('YYYY-MM-DD')
                    return time;
                }
            },
            //  申请类型格式化
            applyTypeFormat: function (row, cloumn) {
                let applyType = '临时';
                if (row.applyType == 1) { 
                    applyType = "短期";
                }
                if (row.applyType == 2) { 
                    applyType = "长期";
                }
                return applyType;
            },
            //  状态格式化
            applyFlagFormat: function (row, cloumn) {
                let applyFlag = '已失效';
                if (row.flag == 0) { 
                    applyFlag = "待生效";
                }
                if (row.flag == 1) { 
                    applyFlag = "已生效";
                }
                return applyFlag;
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
            },

             // 发起闸机权限申请
            startAuthorityApply() {
                this.$router.push({
                    name: 'zjqxlc',
                    query: {
                        formScene: "add"
                    }
                });
            },
            // 闸机权限跟踪
            authorityApplyProgress(val) {
                if(val.flag == 1){
                    val.status = "gd";
                    this.$router.push({
                        name: 'zjqxlc',
                        params: val
                    })
                }else{
                    val.formScene = "track";
                    this.$router.push({
                        name: 'zjqxlc',
                        params: val
                    })
                }
                
            },
            changeAuthorityFlag(val){
                let message = "";
                if(val.flag == 1){
                    message = "确定关闭 "+ val.empname +" "+ val.zjname+"的闸机权限吗？";
                }else{
                    message = "确定开启 "+ val.empname +" "+ val.zjname+"的闸机权限吗？";
                }
                this.$confirm(message, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    changeFlag(val).then(res => {
                        this.$message.success("操作成功！")                      
                        let params = this.searchForm; 
                        this.getPagerList(params) 
                    }).catch(error => {
                        this.$message.error(error)
                    });
                })
               
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
        float: right;
        margin-bottom: 10px;
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