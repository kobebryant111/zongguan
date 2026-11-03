<!-- 人力外包考核 -->
<template>
    <div class="warrper">
        <!-- 搜索部分 -->
        <div class="header">
            <el-form v-model="form" label-width="80px">
                    <el-row :gutter="6">
                        <el-col :span='6'>
                            <el-form-item label="项目经理:">
                                <el-input v-model="form.pmValue"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span='6'>
                            <el-form-item label="评估年份:">
                                <el-date-picker
                                  v-model="form.assessYear"
                                  type="year"
                                  placeholder="选择年"
                                  value-format="yyyy"
                                >
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span='6'>
                            <el-form-item label="所属部门">
                                <el-select v-model="form.deptId" filterable>
                                    <el-option
                                        v-for="item in twodepData"
                                        :key="item.id"
                                        :label="item.text"
                                        :value="item.id"
                                    ></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span='6'>
                            <el-form-item label="供应商:">
                                <el-select v-model="form.supplierId" filterable>
                                    <el-option
                                        v-for="item in outsourceno_options"
                                        :key="item.id"
                                        :label="item.outsourcname"
                                        :value="item.orgcode"
                                    ></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span='6'>
                            <el-form-item label="业务名称">
                                <el-input v-model="form.busiName"></el-input>
                                <!-- <el-select v-model="form.assessLevel">
                                    <el-option
                                        v-for="item in assessLevel_oprions"
                                        :key="item.id"
                                        :label="item.value"
                                        :value="item.value"
                                    ></el-option>
                                </el-select> -->
                            </el-form-item>
                        </el-col>
                            <el-col :span="18">
                                <el-row type="flex" justify="space-between">
                                    <el-col :span="21"></el-col>
                                        <clean-btn :size="sizes" :forms="this.form" />
                                        <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
                                </el-row>
                        </el-col>
                    </el-row>
            </el-form>
        </div>
        <!-- 列表部分 -->
        <div class="main">
            <div class="main_header">
                <!-- <el-button @click="handleExport">导出</el-button> -->
                <el-button @click="toInitiate">新增</el-button>
            </div>
            <el-table :data="tableData" tooltip-effect="dark">
                <el-table-column prop="pmName" label="项目经理" align="center"></el-table-column>
                <el-table-column prop="assessYear" label="评估年份" align="center"></el-table-column>
                <el-table-column prop="deptName" label="部门名称" align="center">
                </el-table-column>
                <el-table-column prop="supplierName" label="供应商名称" align="center"></el-table-column>
                <el-table-column prop="busiName" label="外包业务名称" align="center"></el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-link type="warning" :underline="false" @click="toDetil(scope.row)">详情</el-link>
                        <el-link type="warning" :underline="false" @click="handleExport(scope.row)">导出</el-link>
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
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import pager from "@/components/Pager";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import { getListAll } from "@/api/common/workflowApi";
import { queryPager, download, getDeptList } from "@/api/riskAssessment/index";
import Moment from "moment";

export default {
    components: { cleanBtn, buttonOrange, pager },
    data() {
        return {
            totalNumber: 0,
            size: 10, // 初始值 默认：10
            index: 1, // 初始值 默认：1
            form: {
                pmValue: "", // 项目经理
                assessYear: "", // 考核年份
                deptId: "", // 部门编号
                supplierId: "", // 供应商
                // assessLevel: "", //考核等级
                busiName: "", // 业务名称
            },
            sizes: "small",
            buttonSearch: "查 询", //查询按钮
            depData: [], // 部门
            twodepData: [], // 二级部门
            outsourceno_options: [], //外包商
            tableData: [],
            assessLevel_oprions: [
                {
                    id: '0',
                    value: "A",
                },
                {
                    id: '1',
                    value: "B+",
                },
                {
                    id: '2',
                    value: "B",
                },
                {
                    id: '3',
                    value: "C",
                },
                {
                    id: '4',
                    value: "D",
                }
            ],
        }
    },
    created() {
        this.getWbCom();
        this.filOneDep();
        this.filTwoDep();
        this.init()
    },
    methods: {
        // 分页初始化方法
        init() {
            let params = {}
            params.assessType = 'PG01';
            params.state = '-1';
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        // 获取列表数据
        getPagerList(params) {
            // console.log(params, 'params')
            let data = common.paramsFormat(params);
            // 此处需更换接口
            queryPager(data).then(res => {
                console.log('res', res )
                if(res.data.status === 200) {
                    this.tableData = res.data.results;
                    this.totalNumber = res.data.total;
                    if(this.totalNumber <= 0) {
                        return false
                    }
                } else {
                    this.$message.error(res.data.message);
                }
            })
        },
        // 搜索
        submitBtn() {
            let params = this.form;
            for(var k in params) {
                if(params[k] && this.index > 1) {
                    this.index = 1
                }
            }
            params.state = '-1';
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        // 分页触发
        getPager: function (size, index) {
            let params = this.form;
            params.state = '-1';
            params.size = size;
            params.index = index;
            this.size = size;
            this.index = index;
            this.getPagerList(params);
        } ,
        // 导出
        handleExport(row) {
            // console.log('handleExport')
            var params = {};
            params.id = row.id;
            // params.assessType = 'PG01';
            // let data = common.paramsFormat(params);
            download(params).then(res => {
                console.log(res, 'handleExport')
                common.download(res.data, this, '信息科技外包供应商风险评估表.pdf')
            })
        },
        // 发起风险评估申请
        toInitiate(params) {
            console.log('发起风险评估申请')
            this.$router.push({
               name: 'fxpgsq',
               query: {
                   formScene: "fxApplyfor"
               }
            })
        },
        // 详情
        toDetil(row) {
            console.log('详情', row);
            this.$router.push({
                name: 'fxpgsq',
                query: { 
                    ...row, 
                    status: "gd",
                    formScene: "fxYbgd",
                    isJump: "1"
                },
            })
        },
        // 获取一级部门
        filOneDep() {
            let params = {}
            params.depgrade = "003";
            params.isdisabled = "0";
            getDeptList(params).then((res) => {
                console.log('获取部门', res)
                this.depData = res.data.results;
            }).catch(error => {
                this.$message.error(error)
            })
        },
        // 获取二级部门
        filTwoDep() {
            let params = {}
            params.objectcode = "6000000300";
            params.objectgrade = "2";
            let arr = []
            queryResourceDept(params).then((res) => {
                console.log('获取二级部门', res)
                arr = res.data.results[0].children;
                console.log('arr', arr)
                this.twodepData = arr.map(item => {
                    item.id = item.id.replace("dep", '');
                    item.text = item.text.replace("永赢金租金融科技部-", "")
                    return item
                }); 
                console.log('this.twodepData', this.twodepData)
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
        },
        
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
    /* 分页组件位置 */
    .pager_zb {
        width: 100%;
        display: flex;
        flex-direction: row-reverse;
    }
    table{
    border-collapse: collapse;
    border: 1px solid;
    margin: 50px auto;
}
tr, td, th {
    border: 1px solid;
    padding: 20px;
}
thead{
    text-align: center;
}
</style>

