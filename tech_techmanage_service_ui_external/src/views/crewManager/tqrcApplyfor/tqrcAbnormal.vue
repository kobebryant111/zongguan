<!-- 提前入场申请 材料补全处理 -->
<template>
    <div class="warrper">
        <!-- 搜索部分 -->
        <div class="header">
            <el-form v-model="form" label-width="80px">
                    <el-row :gutter="6">
                        <el-col :span='5'>
                            <el-form-item label="姓名">
                                <el-input v-model="form.userName"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span='5'>
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
                        <el-col :span='5'>
                            <el-form-item label="是否补全">
                                <el-select v-model="form.isComplement">
                                      <el-option 
                                        v-for="item in isComplementOption"
                                        :key="item.id"
                                        :label="item.label"
                                        :value="item.value"
                                      ></el-option>
                                    </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="9">
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
                <!-- <el-button @click="handleExport">导出</el-button> -->
                <!-- <el-button @click="toInitiate">发起提前入场申请</el-button> -->
                <el-button type="warning" @click="btnOk">确 认</el-button>
            </div>
            <el-table 
                :data="tableData" 
                stripe
                style="width: 100%"
                @selection-change="handleSelection"
            >
                <el-table-column type="selection" width="50" :selectable="handleSelect"></el-table-column>
                <el-table-column prop="userName" label="姓名" align="center"></el-table-column>
                <el-table-column prop="outSourceName" label="供应商" align="center"></el-table-column>
                <el-table-column prop="earlyEntryTime" label="提前入场时间" :formatter="dataFormat" align="center"></el-table-column>
                <el-table-column prop="isComplement" label="是否补全入场材料" align="center" width="200">
                    <template slot-scope="scope">
                        <el-select
                            v-model="scope.row.isComplement"
                        >
                            <el-option
                                v-for="item in isComplementOption"
                                :key="item.id"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
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
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
import pager from "@/components/Pager";
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import { getListAll } from "@/api/common/workflowApi";
import { queryPager, updateInfo } from "@/api/tqrcApplyfor";
import Moment from "moment";
import { queryPmPager } from '@/api/tqrcApplyfor/index.js'

export default {
     components: { cleanBtn, buttonOrange, pager },
    data() {
        return {
            totalNumber: 0,
            size: 10, // 初始值 默认：10
            index: 1, // 初始值 默认：1
            form: {
                userName: "",
                // deptNo: "",
                wbsId: "",
                isComplement: "N"
            },
            sizes: "small",
            buttonSearch: '查 询', //查询按钮
            depData: [],
            outsourceno_options: [], //外包商
            tableData: [],
            isComplementOption: [
              {
                id: "0",
                label: '否',
                value: 'N'
              },
              {
                id: "1",
                label: '是',
                value: 'Y'
              }
            ],
            multipleSelection: []
        }
    },
    created() {
        this.getWbCom();
        this.init()
    },
    methods: {
        // 分页初始化方法
        init() {
            let params = {}
            params.isComplement = 'N';
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        // 获取列表数据
        getPagerList(params) {
           // console.log(params, 'params')
            let data = common.paramsFormat(params);
            // 此处需更换接口
            queryPmPager(data).then(res => {
                // console.log(res, 'res')
                const { results, status, total } = res.data;
                if(status === 200) {
                    this.tableData = results;
                    this.totalNumber = total;
                    if(this.totalNumber <= 0) {
                        return false
                    }
                } else {
                    this.$message.error(res.data.message);
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
        handleSelection(val) {
            //console.log(val)
            this.multipleSelection = val;
        },
        btnOk() {
            //console.log('btnOk');
            if(this.multipleSelection.length == 0) {
                this.$message.error("请至少选中一行");
                return;
            }
            this.$confirm("确认执行操作吗?", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(() => {
                let applyInfoDtos = this.multipleSelection;
                //console.log(applyInfoDtos);
                updateInfo(applyInfoDtos).then(res => {
                    //console.log(res)
                    if(res.data.status === 200) {
                        this.$message.success('确认成功');
                        let params = this.form;
                        params.size = this.size;
                        params.index = this.index;
                        this.getPagerList(params);
                    } else {
                        this.$message.error('操作失败，请稍后重试')
                    }
                }).catch(() => {});
            })
           
        },
        // 删除
        handleDelete(row) {
            //console.log(row, 'handleDelete')
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
        handleSelect(row) {
           // console.log('row', row);
            if(row.isUpdate === 'true') {
                return false;
            } else {
                return true;
            }
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

