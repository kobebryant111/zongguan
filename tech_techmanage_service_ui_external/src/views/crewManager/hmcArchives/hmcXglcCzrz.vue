<!-- 相关流程和操作日志 -->
<template>
    <div class="archives-container-hmcXglcCzrz">
        <div class="archives-modular">
            <div class="archives-title">
                <span class="xkg-color-black archives-title-name">相关流程</span>
            </div>
            <el-table :data="xglcCzrzData" style="width: 100%" @row-dblclick="jumpGd">
                <el-table-column prop="createtime" label="发起时间" width="220" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="flowtitle" label="流程名称" :show-overflow-tooltip="true"></el-table-column>
            </el-table>
        </div>
        <!-- <div class="archives-modular">
            <div class="archives-title archives-title-second">
                <span class="xkg-color-black archives-title-name">操作日志</span>
            </div>
            <el-table :data="xglcCzrzData.list" style="width: 100%">
                <el-table-column prop="" label="操作时间" width="150" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="" label="操作人" width="150" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="" label="操作内容" :show-overflow-tooltip="true"></el-table-column>
            </el-table>
        </div> -->
    </div>
</template>

<script>
    import { getXgFlowData } from "@/api/integrated/hmcDa";
    import { getDomainName } from "@/api/eosDictEntry";
    export default {
        name: 'hmcXglcCzrz',
        data() {
            return {
                xglcCzrzData: []
            }
        },
        props: {
            jibenDetail: {
                type: Object,
                required: true
            },
        },
        watch: {
            jibenDetail(val) {
                this.getXglcData(val.jobId);
            }
        },
        methods: {
            // 查询相关流程数据
            getXglcData(v) {
                getXgFlowData({ jobid: v }).then(res => {
                    if (res.data.status == 200) {
                        this.xglcCzrzData = res.data.results;
                    } else {
                        this.$message.error('查询相关流程数据失败');
                    }
                })
            },

            // 跳转归档
            jumpGd(row) {
                row.status = 'gd';
                row.flowstatus = "已结束";
                let flowidArr = row.flowid.split('.');
                let val = flowidArr[flowidArr.length - 1];
                this.$router.push({
                    name: val,
                    query: row
                });
            }
        }
    }
</script>

<style scoped>
    .archives-title {
        margin-bottom: 15px;
    }

    .archives-title-name {
        font-size: 15px;
        font-weight: 600;
    }

    .archives-title-second {
        margin-top: 15px;
    }
</style>