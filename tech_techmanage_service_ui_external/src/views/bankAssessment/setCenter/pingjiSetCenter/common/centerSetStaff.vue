<!-- 中心-员工 -->
<template>
    <div class="pjsz-container">
        <div class="pjsz-body">
            <div class="pjsz-body-operate">
                <el-button size="small" @click="enterEx">导 入</el-button>
                <el-button size="small" @click="exportEx">导 出</el-button>
            </div>
            <p style="color: red">单击单元格修改</p>
            <div class="pjsz-body-cont">
                <el-table :data="pjszData" @cell-click="tableDbEdit" :key="Math.random()" :border="true">
                    <el-table-column type="index" label="序号" min-width="10%" align="center"></el-table-column>
                    <el-table-column label="中心" prop="centername" min-width="10%" align="center"></el-table-column>
                    <el-table-column :label="item.dictname" prop="dictname" min-width="8%" v-for="(item, index) in level_options"
                        :key="index" align="center">
                        <template slot-scope="scope">
                            <div v-for="(li, seri) in scope.row.persons" :key="seri">
                                <span v-if="item.dictid == li.tlevel">{{li.nums}}</span>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pager-zj">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="size" :currentPage="index" v-model="$attrs"
                        ref="pager" />
                </div>
            </div>
        </div>
        <el-dialog title="上传文件" :visible.sync="ifShowEnDialog" @close="scClose">
            <div>
                <div class="upload-butt">
                    <el-upload action="Fake Action" accept=".xlsx, .xls" :file-list="drList" :show-file-list="true"
                        :http-request="changeUpFile" :on-change="handleChange" :on-remove="handleRemove">
                        <el-button size="small" type="primary">
                            <i class="el-icon-upload2"></i>
                            <span>{{drList.length == 0 ? '上传文件' : '重新上传'}}</span>
                        </el-button>
                    </el-upload>
                </div>

                <div class="dia-bottom">
                    <el-row :gutter="10" type="flex" justify="end">
                        <el-col :span="1.5">
                            <button-common buttonName="取 消" size="mini" :fns="cancelEnter"></button-common>
                        </el-col>
                        <el-col :span="1.5">
                            <button-orange buttonName="确 定" size="mini" :fns="sureEnter"></button-orange>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { queryShowPager, exportData, importExcel, updateOrAdd } from "@/api/hykh/pjsz";
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import { getDicEntry } from "@/api/eosDictEntry";
    import pager from "@/components/Pager";
    export default {
        name: 'centerSetStaff',
        components: { buttonCommon, buttonOrange, pager },
        data() {
            return {
                pjszData: [],
                level_options: [],
                ifShowEnDialog: false, //是否显示导入弹窗
                drFileData: {}, //导入文件信息
                drList: [], //导入列表
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                mask: true
            }
        },
        props: {
            centerid: {
                default: null
            }
        },
        watch: {
            centerid(val) {
                this.centerid = val
            }
        },
        mounted() {
            this.seaTableData();
            this.getDic();
        },
        methods: {
            // 查询业务字典
            getDic() {
                getDicEntry({ dicttypeid: "KPI_LEVEL" }).then(res => {
                    if (res.data.status !== 200) {
                        this.$alert(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    //级别
                    this.level_options = data.filter(item => {
                        return item.dicttypeid == "KPI_LEVEL";
                    })
                });
            },

            // 双击单元格
            tableDbEdit(row, column, cell, event) {
                console.log('双击单元格', row, column, cell, event)
                if (column.label != "中心" && column.label != "序号") {
                    if (this.mask) {
                        this.mask = false;
                        let that = this;
                        event.target.innerHTML = "";
                        let cellInput = document.createElement('input');
                        let cellData = {};
                        row.persons.forEach(li => {
                            if (li.tlevel == column.label) {
                                cellData = li;
                            }
                        })
                        cellInput.value = cellData.nums;
                        cellInput.setAttribute("type", "text");
                        cellInput.style.width = "80%";
                        cell.appendChild(cellInput);
                        cellInput.onblur = function () {
                            cell.removeChild(cellInput);
                            event.target.innerHTML = cellInput.value;
                            cellData.nums = cellInput.value;
                            that.updateTable(cellData);
                            that.mask = true;
                        }
                    }
                }
            },

            // 查询列表数据
            seaTableData() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ otype: 2, role: 'COMM_USER', centerid: this.centerid });
                queryShowPager(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.pjszData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.centerid = this.centerid;
                params.otype = 2;
                params.role = 'COMM_USER';
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                const loading = common.loading(this);
                let data = common.paramsFormat(params);
                queryShowPager(data).then(res => {
                    loading.close();
                    console.log('分页查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.pjszData = res.data.results;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 修改
            updateTable(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                updateOrAdd(data).then(res => {
                    loading.close();
                    console.log("修改单人确定", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('更新成功');
                        this.seaTableData();
                    } else {
                        this.seaTableData();
                        this.$message.error('更新失败')
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 导入
            enterEx() {
                this.ifShowEnDialog = true;
            },

            // 取消导入
            cancelEnter() {
                this.ifShowEnDialog = false;
            },

            // 关闭导入框
            scClose() {
                this.drList = [];
            },

            // 确认导入
            sureEnter() {
                if (this.drList.length == 0) {
                    this.$message.error('请上传文件');
                    return
                }
                const loading = common.loading(this);
                var formData = new FormData();
                formData.append("file", this.drFileData);
                formData.append("otype", 2);
                formData.append("role", 'COMM_USER');
                importExcel(formData).then(res => {
                    loading.close();
                    console.log("确认导入", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('导入成功');
                        this.ifShowEnDialog = false;
                        this.seaTableData();
                    } else {
                        this.$message.error('导入失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 上传变化
            changeUpFile(file) {
                console.log('上传变化', file, this.drList)
                this.drFileData = file.file;
            },

            // on-change
            handleChange(file, fileList) {
                this.drList = [fileList[fileList.length - 1]];
            },

            // on-remove
            handleRemove() {
                this.drList = [];
            },

            // 导出
            exportEx() {
                const loading = common.loading(this);
                let data = common.paramsFormat({ otype: 2, role: 'COMM_USER', centerid: this.centerid });
                const self = this;
                exportData(data).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "员工.xlsx");
                });
            },
        }
    }
</script>
<style scoped>
    .pjsz-container {
        padding: 0 15px 15px 15px;
    }

    .pjsz-body-operate {
        text-align: right;
    }

    .dia-bottom {
        margin-top: 15px;
    }

    .pager-zj {
        text-align: center;
    }
</style>