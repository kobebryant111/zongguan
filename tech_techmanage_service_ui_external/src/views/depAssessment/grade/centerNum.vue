<!-- 级别设置 -->
<template>
    <div class="web-container">
        <div class="web-body xkg-borderradius-4px">
            <div class="web-body-operation">
                <el-button size="small" @click="importEx">导 入</el-button>
                <el-button size="small" @click="exportEx">导 出</el-button>
            </div>
            <div class="web-body-table">
                <el-table :data="tableData" height="500">
                    <el-table-column label="中心" prop="depName"></el-table-column>
                    <el-table-column v-for="item in gradeList" :key="item" :label="item" :prop="item" ed>
                        <template slot-scope="scope">
                            <el-input v-model="scope.row[item].total" placeholder="请输入" class="edit-input" 
                                @blur="updateTotal(scope.row[item], $event)"></el-input>
                        </template>
                    </el-table-column>
                </el-table>
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
    import buttonOrange from '@/components/eleButton/buttonOrange';
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import { queryDepNum, updateDepNum, importExcel, exportGradeTotal } from "@/api/zzkhApi/grade";
    export default {
        name: 'depNum',
        components: { buttonOrange, buttonCommon},
        data() {
            return {
                tableData: [],
                type: 2, // 中心数量
                gradeList: [], //添加数据
                ifShowEnDialog: false, //是否显示导入弹窗
                drFileData: {}, //导入文件信息
                drList: [], //导入列表
            }
        },
        props:{
            sign: Number 
        },
        mounted() {
            this.seaTableData();
        },
        methods: {
            // 查询列表数据
            seaTableData(val) {
                if(!val){
                    val = {};
                }
                val.sign = this.sign;
                val.type = this.type;
                const loading = common.loading(this);
                let data = common.paramsFormat(val);
                queryDepNum(data).then(res => {
                    loading.close();
                    console.log('查询列表数据', res.data)
                    if (res.data.status == 200) {
                        var tableData1 = [];
                        this.gradeList = [];
                        let index = 1;
                        res.data.results.forEach(element => {
                            var tmpObj = {
                                depName: element.centername
                            };
                            element.gradetotallist.forEach(item => {
                                tmpObj[item.grade] = {
                                    gradeid: item.gradeid,
                                    total: item.total,
                                    centerid: element.centerid
                                };
                                if(index == 1){
                                    this.gradeList.push(item.grade)
                                }
                            });
                            index++;
                            tableData1.push(tmpObj);
                        });
                        this.tableData = tableData1;
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 添加确定
            updateTotal(item, e) {
                var params = {};
                params.sign = this.sign;
                params.type = this.type;
                params.gradeid = item.gradeid;
                params.total = item.total;
                params.centerid = item.centerid;
                params = JSON.parse(JSON.stringify(params));
                const loading = common.loading(this);
                let data = common.paramsFormat(params);
                updateDepNum(data).then(res => {
                    loading.close();
                    console.log("修改确定", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('修改成功');
                        this.ifShowDialog = false;
                        this.seaTableData();
                    } else {
                        this.$message.error(res.data.message);
                    }
                }).catch(error => {
                    loading.close();
                    this.$message.error(error)
                })
            },

            // 导入
            importEx() {
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

            // on-change
            handleChange(file, fileList) {
                console.log('on-change', fileList);
                this.drList = [fileList[fileList.length - 1]];
            },

            // on-remove
            handleRemove() {
                this.drList = [];
            },

            // 上传变化
            changeUpFile(file) {
                console.log('上传批次变化', file, this.drList)
                this.drFileData = file.file;
            },

            sureEnter(){
                if (this.drList.length == 0) {
                    this.$message.error('请上传文件');
                    return
                }
                const loading = common.loading(this);
                var formData = new FormData();
                formData.append("file", this.drFileData);
                importExcel(formData).then(res => {
                    loading.close();
                    console.log("确认导入", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('导入成功');
                        this.ifShowEnDialog = false;
                        this.seaTableData({});
                    } else {
                        this.$message.error('导入失败')
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 导出
            exportEx() {
                const loading = common.loading(this);
                let val = {};
                val.sign = this.sign;
                val.type = this.type;
                let params = common.paramsFormat(val);
                const self = this;
                exportGradeTotal(params).then(res => {
                    loading.close();
                    console.log("导出", res.data)
                    common.download(res.data, self, "二级部级别数量配置.xls");
                });
            },
        },
    }
</script>
<style scoped>
    .web-body {
        padding: 15px;
        background: #FFFFFF;
    }

    .web-body-operation {
        text-align: right;
        margin-bottom: 10px;
    }
    .edit-input /deep/input {
        background: none;
        border: none;
        padding: 0;
    }
</style>
