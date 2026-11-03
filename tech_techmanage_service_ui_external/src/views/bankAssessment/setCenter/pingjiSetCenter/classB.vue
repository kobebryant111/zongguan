<!-- 二级部 -->
<template>
    <div class="pjsz-container">
        <div class="center-head">
            <el-form label-position="left" label-width="55px">
                <el-row :gutter="10">
                    <el-col :span="6">
                        <el-form-item label="中心">
                            <el-select v-model="centerid" filterable clearable @change="seaCenterChange">
                                <el-option v-for="item in centerData" :key="item.id" :label="item.centername" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-form-item label="二级部">
                            <el-select filterable clearable v-model="depid">
                                <el-option v-for="item in depData" :key="item.depId" :label="item.depName" :value="item.depId"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-button type="warning" class="xkg-marginleft-10px" @click="search">搜 索</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <div class="pjsz-body">
            <div class="pjsz-body-operate">
                <div>
                    <el-button class="pjsz-body-operate-left-all" size="small">员 工</el-button>
                </div>
                <div>
                    <el-button size="small" @click="enterEx">导 入</el-button>
                    <el-button size="small" @click="exportEx">导 出</el-button>
                </div>
            </div>
            <p style="color: red">单击单元格修改</p>
            <div class="pjsz-body-cont">
                <el-table :data="pjszData" @cell-click="tableDbEdit" :key="Math.random()" :border="true">
                    <el-table-column type="index" label="序号" min-width="10%" align="center"></el-table-column>
                    <el-table-column label="中心" prop="centername" min-width="10%" align="center"></el-table-column>
                    <el-table-column label="二级部" prop="depname" min-width="10%" align="center"></el-table-column>
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
    import { queryList, queryDeptList } from "@/api/integrated/newEmBmfp";
    export default {
        name: 'classB',
        components: { buttonCommon, buttonOrange, pager },
        data() {
            return {
                centerid: null,
                depid: null,
                pjszData: [],
                level_options: [],
                ifShowEnDialog: false, //是否显示导入弹窗
                drFileData: {}, //导入文件信息
                drList: [], //导入列表
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                centerData: [],
                depData: [],
                mask: true
            }
        },
        mounted() {
            let params = {};
            params.otype = 1;
            this.seaTableData(params);
            this.getDic();
            this.getCenter();
            this.getSeaDep();
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

            // 搜索中心改变
            seaCenterChange(val) {
                this.depid = null;
                this.getSeaDep(val);
            },


            // 获取中心
            getCenter() {
                queryList().then(res => {
                    if (res.data.status == 200) {
                        this.centerData = res.data.results.filter(li => {
                            return li.id != 61
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 根据中心获取部门
            getSeaDep(id) {
                const loading = common.loading(this);
                queryDeptList({ centerId: id }).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        var fild = res.data.results;
                        this.depData = fild.filter(tt => {
                            tt.depName = tt.depName.replace('总行金融科技部-', "");
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                });
            },

            // 双击单元格
            tableDbEdit(row, column, cell, event) {
                if (column.label != "中心" && column.label != "二级部" && column.label != "序号") {
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

            // 查询
            search() {
                let params = {};
                params.centerid = this.centerid;
                params.depid = this.depid;
                params.otype = 1;
                this.seaTableData(params)
            },

            // 查询列表数据
            seaTableData(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryShowPager(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        this.pjszData = res.data.results.filter(li => {
                            li.depname = li.depname.replace('总行金融科技部-', "");
                            return li
                        });
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
                params.depid = this.depid;
                params.otype = 1;
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.seaTableData(params);
            },

            // 修改
            updateTable(val) {
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                updateOrAdd(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('更新成功');
                        let params = {};
                        params.centerid = this.centerid;
                        params.depid = this.depid;
                        params.otype = 1;
                        params.size = this.size;
                        params.index = this.index;
                        this.seaTableData(params);
                    } else {
                        let params = {};
                        params.centerid = this.centerid;
                        params.depid = this.depid;
                        params.otype = 1;
                        params.size = this.size;
                        params.index = this.index;
                        this.seaTableData(params);
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
                formData.append("otype", 1);
                importExcel(formData).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('导入成功');
                        this.ifShowEnDialog = false;
                        this.seaTableData({ otype: 1 });
                    } else {
                        this.$message.error('导入失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 上传变化
            changeUpFile(file) {
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
                let data = common.paramsFormat({ otype: 1, centerid: this.centerid, depid: null });
                const self = this;
                exportData(data).then(res => {
                    loading.close();
                    common.download(res.data, self, "二级部.xlsx");
                });
            },
        }
    }
</script>
<style scoped>
    .center-head {
        background: #FFFFFF;
        padding: 22px 10px 0 10px;
        margin-bottom: 15px;
        border-radius: 4px;
    }

    .pjsz-body {
        background: #FFFFFF;
        padding: 10px;
        border-radius: 4px;
    }

    .pjsz-body-operate {
        display: flex;
        justify-content: space-between;
    }

    .dia-bottom {
        margin-top: 15px;
    }

    .pager-zj {
        text-align: center;
    }
</style>
<style>
    .pjsz-body-operate-left-all {
        color: rgba(251, 164, 47, 1);
        border-color: rgba(251, 164, 47, 1);
    }
</style>