<!-- 系统管理列表 -->
<template>
    <div class="list-container interviewList">
        <!-- 修改后 -->
        <el-form inline>
            <div class="interviewList_head">
                <div>                    
                    <el-row>    
                        <el-col :span="12">
                             <el-form-item label="资源编码:" label-width="80px"> 
                                <el-input placeholder="请输入"  size="mini" v-model="searchForm.indexCode"></el-input>
                            </el-form-item>
                        </el-col>                    
                        <el-col :span="12">
                            <el-form-item label="资源名称:" label-width="80px"> 
                                <el-input placeholder="请输入"  size="mini" v-model="searchForm.name"></el-input>
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
            <el-table :data="crewData" highlight-current-row height="510" @select-all="onSelectTable" @select="onSelectTable">
                <el-table-column label="资源唯一编码" prop="indexCode" width="250" :show-overflow-tooltip="true"/> 
                <el-table-column label="资源名称" prop="name" width="150" :show-overflow-tooltip="true" />
                <el-table-column label="资源类型" prop="resourceType" width="100" :show-overflow-tooltip="true" />
                <el-table-column label="区域名称" prop="regionName" width="100" :show-overflow-tooltip="true" />
                <el-table-column label="接入协议" prop="treatyType" width="100" :show-overflow-tooltip="true" />
                <el-table-column label="资源描述" prop="description" width="200" :show-overflow-tooltip="true" />
                <el-table-column label="创建时间" prop="createTime" width="100" :formatter="createTimeFormat" :show-overflow-tooltip="true" />
                <el-table-column label="更新时间" prop="updateTime" width="100" :formatter="updateTimeFormat" :show-overflow-tooltip="true" />
                <el-table-column label="操作" width="122">
                    <template slot-scope="scope">
                        <el-link type="warning" :underline="false" @click="doControlEvent(scope.row,2)">开门</el-link>                   
                        <el-link type="warning" :underline="false" @click="doControlEvent(scope.row,1)">关门</el-link>                   
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
    import { queryPager, doControl} from '@/api/gateManager/manager';
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
                sizes: 'small', //按钮大小
                buttonSearch: '查 询', //查询按钮
                depData: [],  //部门数据  
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
                    }
                })
            },      
            // 门禁反控操作       
            doControlEvent(params,controlType){
                params.controlType = controlType;
                let data = common.paramsFormat(params);
                doControl(data).then(res => {
                    if (res.data.status == 200) {
                        this.$message.success("操作成功！")  
                    }else{                        
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    this.$message.error("操作失败！")
                });
            },
            //  时间格式化
            createTimeFormat: function (row, cloumn) {
                let time = '';
                if (row.createTime != null) { 
                    time = Moment(row.createTime).format('YYYY-MM-DD')
                    return time;
                }
            },
             
            //  时间格式化
            updateTimeFormat: function (row, cloumn) {
                let time = '';              
                if (row.updateTime != null) { 
                    time = Moment(row.updateTime).format('YYYY-MM-DD')
                    return time;
                }
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