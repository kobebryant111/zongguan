<template>
    <div class="pager-container" v-show="isPagerComp">
        <!-- <el-pagination @current-change="handleCurrentChange" @size-change="handleSizeChange" :total="initTotal" layout="total, sizes,prev, pager, next, jumper "
            :current-page.sync="pager.currentPage" :page-sizes="[10,50,100,200]" :page-size="pager.pageSize" background></el-pagination> -->
        <!-- 新分页 -->
        <el-pagination :total="initTotal" @current-change="handleCurrentChange" @size-change="handleSizeChange" layout="total, prev, pager, next, sizes"
            :page-sizes="[10,20,50,100,200]" :page-size="pager.pageSize" :current-page.sync="pager.currentPage" background></el-pagination>
    </div>
</template>
<script>
    export default {
        name: 'Pager',
        props: {
            total: {
                type: Number,
                default: 0
            },
            pageSize: {
                type: Number,
                default: 50
            },
            currentPage: {
                type: Number,
                default: 1
            },
            fns: {
                type: Function
            }
        },
        data() {
            return {
                func: this.fns,
                pager: {
                    totalNumber: 0,
                    pageSize: this.pageSize,
                    currentPage: this.currentPage
                },
                isPagerComp: false
            }
        },
        computed: {
            initTotal: function () {
                return this.total;
            },
        },
        created() {

        },
        watch: {
            total: {
                handler(newV, oldV) {
                    if (newV > 0) {
                        this.isPagerComp = true
                    } else {
                        this.isPagerComp = false
                    }
                },
                immediate: true
            },
            currentPage(newV) {
                this.pager.currentPage = newV;
            }
        },
        methods: {
            // pageSize改变时触发
            handleSizeChange: function (val) {
                this.pager.pageSize = val;
                this.fns(val, this.pager.currentPage)
            },
            // 页数改变触发
            handleCurrentChange: function (val) {
                this.pager.currentPage = val;
                this.fns(this.pager.pageSize, this.pager.currentPage)
            }
        }
    }
</script>

<style lang="scss" scoped>
    .pager-container {
        margin-top: 10px;
    }
</style>

<style>
    /* 分页 */
    /* .pager-container .el-pagination {
        position: relative;
        overflow: hidden;
    }

    .pager-container .el-pagination {
        display: inline-block !important;
        position: absolute !important;
        right: 20px !important;
    } */

    .pager-container .el-pagination .el-pager li:not(.disabled).active {
        background-color: rgba(255, 255, 255, 1);
        color: rgba(251, 164, 47, 1);
        border: 1px solid rgba(251, 164, 47, 1);
    }

    .pager-container .el-pagination__sizes .el-input .el-input__inner:hover {
        border-color: rgba(251, 164, 47, 1);
    }

    .pager-container .el-pagination.is-background .el-pager li:not(.disabled):hover {
        color: rgba(251, 164, 47, 1);
    }
</style>