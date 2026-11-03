<!-- 提前入场申请 -->
<template>
    <div class="warrper">
        <!-- 搜索部分 -->
        <div class="header">
            <el-form v-model="form" label-width="80px">
                    <el-row :gutter="6">
                        <el-col :span='4'>
                            <el-form-item label="姓名">
                                <el-input v-model="form.userName"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span='4'>
                            <el-form-item label="所属部门">
                                <el-select v-model="form.deptNo" filterable>
                                    <el-option
                                        v-for="item in depData"
                                        :key="item.id"
                                        :label="item.text"
                                        :value="item.id"
                                    ></el-option>
                                </el-select>
                        </el-form-item>
                        </el-col>
                        <el-col :span='4'>
                            <el-form-item label="供应商">
                                <el-select v-model="form.wbsId" filterable>
                                    <el-option
                                        v-for="item in outsourceno_options"
                                        :key="item.id"
                                        :label="item.outsourcname"
                                        :value="item.id"
                                    ></el-option>
                                </el-select>
                        </el-form-item>
                        </el-col>
                        <el-col :span='4'>
                            <el-form-item label="申请人">
                                <el-input v-model="form.applyerValue"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-row type="flex" justify="end">
                                <clean-btn :size="sizes" :forms="this.form" />
                                <div class="interviewList_head_right_orange">
                                    <button-orange :buttonName="buttonSearch" :size="sizes" :fns="search"></button-orange>
                                </div>
                                <!-- <el-button style="margin-left:10px" type="primary" @click="search">查询</el-button> -->
                            </el-row>
                        </el-col>
                    </el-row>
            </el-form>
        </div>
        <!-- 列表部分 -->
        <div class="main">
            <div class="main_header">
                <el-button @click="handleExport">导出</el-button>
                <el-button @click="toInitiate">发起提前入场申请</el-button>
            </div>
            <el-table :data="tableData">
                <el-table-column prop="userName" label="姓名"></el-table-column>
                <el-table-column prop="deptName" label="申请人部门"></el-table-column>
                <el-table-column prop="position" label="人员类型"></el-table-column>
                <el-table-column prop="workyears" label="工作年限"></el-table-column>
                <el-table-column prop="suggestprice" label="预计单价"></el-table-column>
                <el-table-column prop="outSourceName" label="供应商"></el-table-column>
                <el-table-column prop="earlyEntryTime" label="提前入场时间" :formatter="dataFormat"></el-table-column>
                <!-- <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-link type="warning" :underline="false" @click="handleDelete(scope)">删除</el-link>
                    </template>
                </el-table-column> -->
            </el-table>
            <div class="pager_zb">
                <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
            </div>
        </div>
        
    </div>
</template>

<script>
import common from "@/api/common";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
import pager from "@/components/Pager";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import { getListAll } from "@/api/common/workflowApi";
import { queryPager, exportExcel } from "@/api/tqrcApplyfor";
import Moment from "moment";

export default {
     components: { cleanBtn, buttonOrange, pager },
    data() {
        return {
            totalNumber: 0,
            size: 10, // 初始值 默认：10
            index: 1, // 初始值 默认：1
            form: {
                userName: "",
                deptNo: "",
                wbsId: "",
                applyerValue: ""
            },
            sizes: "small",
            buttonSearch: '查 询', //查询按钮
            depData: [],
            outsourceno_options: [], //外包商
            // tableData: [{
            //     userName: "张三",
            //     deptName: "移动应用部",
            //     type: "开发",
            //     workTime: '2.5',
            //     money: '10',
            //     outsourceon: "浙江优创",
            //     tqWork: "2022-11-11"
            // }],
            tableData: [],
        }
    },
    created() {
        this.filDep();
        this.getWbCom();
        this.init()
    },
    methods: {
        // 分页初始化方法
        init() {
            let params = {}
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        // 获取列表数据
        getPagerList(params) {
            //console.log(params, 'params')
            let data = common.paramsFormat(params);
            // 此处需更换接口
            queryPager(data).then(res => {
                console.log(res, 'res')
                const { results, status, total } = res.data;
                if(status === 200) {
                    this.tableData = results;
                    this.totalNumber = total;
                    if(this.totalNumber <= 0) {
                        return false
                    }
                }
            })
        },
        // 搜索
        search() {
            let params = this.form;
            for(var k in params) {
                if(params[k] && this.index > 1) {
                    this.index = 1
                }
            }
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        // 分页触发
        getPager: function (size, index) {
            let params = this.form;
            params.size = size;
            params.index = index;
            this.size = size;
            this.index = index;
            this.getPagerList(params);
        } ,
        // 导出
        handleExport() {
            //console.log('handleExport')
            var searchData = this.form;
            searchData.deptNo = searchData.deptNo;
            let params = common.paramsFormat(searchData);
            exportExcel(params).then(res => {
                //console.log(res, 'handleExport')
                common.download(res.data, this, '提前入场申请.xls')
            })
        },
        // 发起提前入场申请
        toInitiate(params) {
            //console.log('toInitiate')
            this.$router.push({
               name: 'tqrcsq',
               query: {
                   formScene: "tqrcsqApply"
               }
            })
        },
        // 删除
        handleDelete(row) {
            //console.log(row, 'handleDelete')
        },
        // 获取部门
        filDep() {
            let params = {}
            params.objectcode = "6000000300";
            params.objectgrade = "2";
            queryResourceDept(params).then((res) => {
                // console.log(res.data.results[0].children)
                let fild = res.data.results[0].children;
                let name = res.data.results[0].text;
                this.depData = fild.filter(item => {
                    item.text = item.text.replace(name + '-', '');
                    return item;
                })
            }).catch(error => {
                this.$message.error(error)
            })
        },
        // 获取供应商
        getWbCom() {
            getListAll().then(res => {
                // console.log(res)
                this.outsourceno_options = res.data.results
            })
        },
        dataFormat: function(row,column) {
            let data = row[column.property]
            return Moment(data).format("YYYY-MM-DD")
        }
    },
}
</script>

<style scoped>
    .warrper {
        padding: 18px;
    }

    .header {
        background-color: #ffffff;
        padding: 15px 15px 0 0;
        margin-bottom: 15px;
    }

    .main {
        background-color: #ffffff;
        padding: 18px;
    }
    .main .main_header {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        margin-bottom: 10px;
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

