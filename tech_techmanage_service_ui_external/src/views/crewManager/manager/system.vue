<!-- 系统管理列表 -->
<template>
    <div class="list-container interviewList">
        <!-- 修改后 -->
        <el-form inline>
            <div class="interviewList_head">
                <div>
                    <el-row>
                         <el-col :span="8">
                            <el-form-item prop="" label="">
                                <el-input placeholder="请输入" v-model="searchForm.name">
                                    <template slot="prefix">系统名称：</template>
                                </el-input>
                            </el-form-item>
                        </el-col>
                         <el-col :span="8">
                            <el-form-item prop="" label="">
                                <el-input placeholder="请输入" v-model="searchForm.badge">
                                    <template slot="prefix">经理工号：</template>
                                </el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item prop="" label="">
                                <el-input placeholder="请输入" v-model="searchForm.empName">
                                    <template slot="prefix">经理姓名：</template>
                                </el-input>
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
                    <el-col :span="11">
                        <!-- <button-common :buttonName="buttonExport" :size="sizes" :fns="handelExport"></button-common> -->
                    </el-col>
                    <el-col :span="12">
                        <button-orange :buttonName="buttonStart" :size="sizes" :fns="handelEdit"></button-orange>
                    </el-col>
                </el-row>
            </div>
            <el-table :data="crewData" highlight-current-row height="510" @select-all="onSelectTable" @select="onSelectTable">
                <el-table-column label="归属部门" prop="depName" width="150" :formatter="deptFormat" 
                    :show-overflow-tooltip="true" />
                <el-table-column label="系统名称" prop="name" width="150" :show-overflow-tooltip="true"/> 
                <el-table-column label="项目经理" prop="empName" width="250" :formatter="empFormat" 
                    :show-overflow-tooltip="true" /> 
                <el-table-column label="创建人" prop="createBy" width="100" :show-overflow-tooltip="true" />
                <el-table-column label="修改人" prop="updateBy" width="100" :show-overflow-tooltip="true" />
                <el-table-column label="创建时间" prop="createDate" width="100" :formatter="createtimeFormat"
                    :show-overflow-tooltip="true" />
                <el-table-column label="修改时间" prop="updateDate" width="100" :formatter="createtimeFormat"
                    :show-overflow-tooltip="true" />
                <el-table-column label="操作" width="122">
                    <template slot-scope="scope">
                        <el-link type="warning" :underline="false" @click="handelEdit(scope.row)">修改</el-link>
                        <el-link type="warning" :underline="false" @click="delRow(scope.row)">删除</el-link>
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
    import {queryResourceDept} from "@/api/integrated/wbryzrlc";
    import { queryPager,del } from '@/api/manager/system';
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
                searchForm: {
                    code: null, // 系统编号
                    name: null, // 系统名称
                    badge: null, // 系统经理工号
                    empName: null, // 系统经理名称 
                    depId: null, //部门
                }, 
                crewData: [], // 列表分页数据 
                buttonCancel: '取 消',
                buttonSure: '确 定', 
                titleName: "系统信息",
                buttonStart: '新增系统', //发起需求按钮
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
                        let type = self.crewData[0].roleType
                        if (1 === type || 4 === type) {
                            self.isAuthView = true
                        }
                    }
                })
            },
            // 修改
            handelEdit(params){
                this.$router.push({
                    name: 'systemEdit',
                    params: params
                }); 
            },
             // 删除行
            delRow(val) {
                this.$confirm('确认删除吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const loading = common.loading(this);
                    del({ id: val.id }).then(res => {
                        loading.close(); 
                        if (res.data.status == 200) {
                            this.$message.success('删除成功');
                            this.submitBtn();
                        } else {
                            this.$message.error('删除失败')
                        }
                    }).catch(error => {
                        loading.close();
                        this.$message.error(error)
                    })
                }).catch(error => { })
            },

            //  时间格式化
            createtimeFormat: function (row, cloumn) {
                let time = '';
                if (row.createDate != null) { 
                    time = Moment(row.createDate).format('YYYY-MM-DD')
                }
                if (row.updateDate != null) { 
                    time = Moment(row.updateDate).format('YYYY-MM-DD')
                }
                return time;
            },

             // 部门格式化
            deptFormat(row, cloumn) {
                let dep = '';
                const reg = /([^-]+)$/;
                if (row.depName != null) {
                    dep = row.depName.match(reg)[1];
                }
                return dep;
            },
             // 负责人格式化
            empFormat(row, cloumn) {
                let emp = '';
                const reg = /([^-]+)$/;
                if (row.userInfoList != null) {
                    var fild = row.userInfoList;  
                    fild.forEach(function(item,index){
                        emp += item.empName + " " 
                    })
                }
                return emp;
            },

            /**
             * 搜索事件
             */
            submitBtn: function () {
                let params = {}; 
                let depid = this.searchForm.depId;
                if(depid){
                    params.depId = depid.replace("dep", ""); 
                }
                params.code = this.searchForm.code;
                params.name = this.searchForm.name; 
                params.badge = this.searchForm.badge; 
                params.empName = this.searchForm.empName; 
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
                let params = {};
                let depid = this.searchForm.depId;
                if(depid){
                    params.depId = depid.replace("dep", ""); 
                }
                params.code = this.searchForm.code;
                params.name = this.searchForm.name; 
                params.badge = this.searchForm.badge; 
                params.empName = this.searchForm.empName; 
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
        padding: 18px;
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
<style>
    /* el-input修改 */
    .interviewList .el-input--prefix .el-input__inner {
        padding-left: 80px;
    }
 
    .interviewList .el-input__prefix {
        left: 5px;
        color: #262626;
        font-size: 14px;
    }

    /* element表单修改 */
    .interviewList .el-form-item {
        margin-bottom: 0px;
    }

    /* 表头背景色 */
    .interviewList .el-table th {
        background: #FAFAFA;
    }
</style>