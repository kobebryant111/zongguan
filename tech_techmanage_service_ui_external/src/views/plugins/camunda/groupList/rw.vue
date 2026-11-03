<template>
    <div class="container_rw">
        <div class="rw_content" v-loading="loading">
            <el-table :data="rwdata" style="width:100" :show-header="false" @row-click="toRw">
                <el-table-column prop="text">
                    <template slot-scope="scope">
                        <span class="cursor">{{scope.row.text}}</span>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import common from "@/api/common";  
import { queryRecordPager } from '@/api/kqManager/abnormalKqHandling'
import { queryPmPager } from '@/api/tqrcApplyfor/index.js'

export default {
    data() {
        return {
            loading:　false, // 数据加载效果控制
            rwdata: [],
            listLength: null,
            rwnums: 0,

        }
    },
    created() {
    },
    mounted() {
    },
    methods:{
        init() {
                this.rwnums = 0
                this.getAbnormalKq();
                this.getqueryPmPager();
        },
        getAbnormalKq() {
            let params = {}
            params.whetherHandle = '1';
            let data = common.paramsFormat(params);
            queryRecordPager(data).then(async res => {
                // console.log(res, 'rwrwrwrw')
                // this.loading = false
                if(res.data.status === 200) {
                    var exceptions = {
                        type: "KQYCCL",
                        text: "考勤异常处理(" + res.data.total + ")",
                        linkurl: "/abnormalKqHandling"
                    };
                    let total = await res.data.total
                    this.putNoExistItem(
                        this.rwdata,
                        exceptions,
                        total
                    );
                }
            }).catch(error => {
                this.$message.error(error)
            })
        },
        getqueryPmPager() {
            let params = {};
            params.isComplement = 'N';
            let data = common.paramsFormat(params);
            queryPmPager(data).then(async res => {
                // console.log('getqueryPmPager', res);
                if(res.data.status === 200) {
                    var exceptions = {
                        type: "TQRCCLBQ",
                        text: "提前入场材料补全(" + res.data.total + ")",
                        linkurl: "/tqrcAbnormal"
                    };
                    this.putNoExistItem(
                        this.rwdata,
                        exceptions,
                        res.data.total
                    );
                }
            })
        },
        /**
         * 点击待办触发
         */
        toRw(row) {
            // console.log('tow', row);
            this.$router.push({
                path: row.linkurl
            });
        },
        putNoExistItem(rwdata, item, total) {
            //   console.log(rwdata, item, total)
            // console.log(rwdata, item, total) 
            let flag = rwdata.findIndex(rw => rw.type === item.type);
            if (flag == -1) {
            this.rwdata.push(item);
            this.rwnums = this.rwnums + total*1;
            this.$root.Bus.$emit("rwNumber", this.rwnums, total);
        }
    }
    }
}
</script>

<style scoped>
._content {
  height: 508px;
  font-weight: bolder;
}
.cursor:hover {
  cursor: pointer;
}
.container_rw {
    height: 508px;
}
</style>
