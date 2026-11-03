<template>
    <div class="main1-container">
        <el-table :data="data" @row-dblclick="showDetail" border highlight-current-row>
            <el-table-column label="序号" type="index" width="60"/>
            <el-table-column label="流程名称" prop="flowname"/>
            <el-table-column label="标题" prop="flowtitle"/>
            <el-table-column label="状态" prop="flowstatus" width="120"/>
            <el-table-column label="创建时间" prop="createtime"/>
            <el-table-column label="最后操作时间" prop="lastmodtime"/>
            <el-table-column label="创建人" prop="creators"/>
            <el-table-column label="当前处理人" prop="clr"/>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" @click="showDetail(scope.row)" size="mini">查看详情</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pager :fns="getPager" :total="totalNumber" :pageSize="pageSize" v-model="$attrs" ref="pager"/>
    </div>
</template>

<script>
import pager from "@/components/Pager";
export default {
    components: { pager },
    props: {
        data: {
            default: {}
        },
        totalNumber: {
            default: 0
        },
        size: {
            default: 50
        },
        form: {
            default: {}
        },
        pagerFns:{
            default: _ => {}
        },
        showDetail:{
            default: _ => {}
        },
    },
    data() {
        return {
            pageSize : this.size
        };
    },
    methods: {
        init() {},
        getPager(size, index) {
            let self = this;
            let params = self.form;
            params.size = size;
            params.index = index;
            self.pageSize = size
            self.index = index;
            if (
                undefined != params.date &&
                "" != params.date &&
                params.date.length > 0
            ) {
                params.startDate = params.date[0];
                params.endDate = params.date[1];
            }
            self.pagerFns(params);
        }
    }
};
</script>
<style>
</style>