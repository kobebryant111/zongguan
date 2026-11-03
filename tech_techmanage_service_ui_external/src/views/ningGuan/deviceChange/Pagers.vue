<!-- 宁管设备变更 - 分页 -->
<template>
    <div class="app-container">
        <!-- 搜索 -->
        <div class="search-container">
            <el-form :model="form" ref="form" label-width="80px" inline>
                <el-form-item label="流程类型">
                    <el-select v-model="form.processType" placeholder="流程类型">
                        <el-option v-for="item in processTypeData" :key="item.dictid" :label="item.dictname" :value="item.dictid">
                        </el-option>
                    </el-select>

                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.processTitle"></el-input>
                </el-form-item>
                <el-form-item label="创建人">
                    <el-input v-model="form.userName"></el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-date-picker v-model="form.date" type="daterange" unlink-panels start-placeholde="开始时间"
                        end-placeholde="结束时间" range-separator="至" value-format="yyyy-MM-dd">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="form.state" placeholder="流程类型">
                        <el-option v-for="item in stateData" :key="item.dictid" :label="item.dictname" :value="item.dictid">
                        </el-option>
                    </el-select>

                </el-form-item>
                <el-form-item>
                    <buttonOrange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></buttonOrange>
                    <clean-btn :size="sizes" :forms="this.form" />
                </el-form-item>
            </el-form>
        </div>
        <!-- 操作按钮 -->
        <div class="operat-conatiner marginbottom-10px">
            <el-row>
                <el-col :span="20"> &nbsp;&nbsp; </el-col>
                <el-col :span="2">
                    <buttonOrange :buttonName="excleExportText" :size="sizes" :fns="excleExport"></buttonOrange>
                </el-col>
                <el-col :span="2">
                    <buttonOrange :buttonName="initiateProcessText" :size="sizes" :fns="initiateBtn"></buttonOrange>
                </el-col>
            </el-row>
        </div>
        <!-- 分页 -->
        <div class="main1-container">
            <el-table :data="data" border highlight-current-row>
                <el-table-column label="序号" type="index" width="60" />
                <el-table-column label="流程名称" prop="processName" />
                <el-table-column label="标题" prop="processTitle" />
                <el-table-column label="状态" prop="stateName" width="120" />
                <el-table-column label="创建时间" prop="createDate" />
                <el-table-column label="最后操作时间" prop="lastMofiyDate" />
                <el-table-column label="创建人" prop="userName" />
                <el-table-column label="当前处理人" prop="currentToId" />
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="handleShowDateil(scope.row)" size="mini">查看详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
        </div>
        <!-- 发起 dialog-->
        <div class="dialog-container">
            <el-dialog :title="dialogTitle" :visible.sync="visibleClose" :width="dialogWidth" v-if="visibleClose"
                :before-close="handleClose" @close="dialogColse">
                <initiate-todo @refreshDialog="setDialog" @closeDialog="dialogColse" ref="initiate" v-show="!ifReadOnly"></initiate-todo>
                <archive ref="details" v-show="ifReadOnly"></archive>
            </el-dialog>

        </div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import cleanBtn from "@/components/CleanBtn";
    import buttonOrange from '@/components/eleButton/buttonOrange'; //引入按钮组件Orange
    import buttonCommon from '@/components/eleButton/buttonCommon';
    import { pagerList, exportExcel, queryDetail } from '@/api/ningGuanApi/deviceChange'
    import InitiateTodo from '@/views/ningGuan/deviceChange/common/InitiateTodo'
    import Archive from '@/views/ningGuan/deviceChange/Archive'
    import { getDicEntry } from "@/api/eosDictEntry";
    import Moment from 'moment';
    export default {
        name: 'Pagers',
        components: { pager, cleanBtn, buttonOrange, buttonCommon, InitiateTodo, Archive },
        data() {
            return {
                ifReadOnly: false,            // 查看详情     
                processTypeData: [],        // 流程类型集合
                stateData: [],            // 状态集合
                dialogTitle: '',        // dialog 标题
                dialogWidth: '30%',        // dialong 宽度
                visibleClose: false,   // dialog显示默认不显示
                initiateProcessText: '发起流程',       // 按钮文本
                excleExportText: '导 出',          // 按钮文本
                form: {},          // 搜索表单
                buttonSearch: '搜 索',
                sizes: 'size',
                // 分页
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                data: [],      // 分页数据
            }
        },
        mounted() {
            this.init()
        },
        methods: {
            /**
             * 页面初始化加载
             */
            init() {
                let self = this
                let params = self.form
                params.size = self.size
                params.index = self.index
                self.pager(params)
                let eos = { dicttypeid: 'NEW_DEVICE_CHANGE,NEW_DEVICE_CHAGE_STATE' }
                getDicEntry(eos).then(res => {
                    let data = res.data.results
                    // 流程类型 
                    self.processTypeData = data.filter(ii => {
                        return ii.dicttypeid === "NEW_DEVICE_CHANGE";
                    });
                    // 状态
                    self.stateData = data.filter(ii => {
                        return ii.dicttypeid === "NEW_DEVICE_CHAGE_STATE";
                    });
                })      // getDicEntry end
            },
            /**
             * 分页方法
             */
            pager(params) {
                let self = this
                let data = common.paramsFormat(params)
                pagerList(data).then(res => {
                    if (res.data.status !== 200) {
                        self.$alert(res.data.message, { type: 'error' });
                        return false
                    }
                    res.data.results.forEach((v, k) => {
                        v.lastMofiyDate = Moment(v.lastMofiyDate).format('YYYY-MM-DD');
                        v.createDate = Moment(v.createDate).format('YYYY-MM-DD');
                    })
                    self.data = res.data.results
                    self.totalNumber = res.data.total
                }); // pagerList
            },
            /**
             * 搜索
             */
            submitBtn() {
                let self = this
                let params = self.form
                params.index = 1
                self.index = 1
                params.size = self.size
                if (undefined != params.date && '' != params.date && params.date.length > 0) {
                    params.startDate = params.date[0]
                    params.endDate = params.date[1]
                }
                self.pager(params)
            },
            /**
             * 分页
             */
            getPager(size, index) {
                let self = this
                let params = self.form
                params.size = size
                params.index = index
                self.size = size
                self.index = index
                if (undefined != params.date && '' != params.date && params.date.length > 0) {
                    params.startDate = params.date[0]
                    params.endDate = params.date[1]
                }
                self.pager(params)
            },
            /**
             * 发起申请
             */
            initiateBtn() {
                let self = this
                self.visibleClose = true
            },
            /**
             * dialog 关闭
             */
            dialogColse() {
                let self = this
                self.visibleClose = false
                self.dialogTitle = ""
                self.dialogWidth = "30%"
                self.ifReadOnly = false
            },
            /**
             * 点击发起流程赋值dialog
             */
            setDialog(width, title) {
                let self = this
                self.dialogWidth = width
                self.dialogTitle = title
            },
            /**
             * dialog关闭前提示
             */
            handleClose(done) {
                if (!this.ifReadOnly) {
                    this.$confirm('确认关闭?').then(res => {
                        done()
                    }).catch(_ => { })
                } else {
                    done()
                }

            },
            /**
             * 查看详情
             */
            handleShowDateil(item) {
                let self = this
                self.visibleClose = true
                self.$nextTick(_ => {
                    self.ifReadOnly = true
                    self.dialogTitle = "详情"
                    self.dialogWidth = "80%"
                    if (item.state == 2) {
                        item.status = 'gd'
                        item.flowstatus = '已结束'
                    }
                    console.log("item::", item)
                    self.$refs.details.isShowNav = false
                    self.$refs.details.init(item)
                })
            },
            /**
             * excel 导出
             */
            excleExport() {
                let self = this
                // 判断流程类型不能为空
                if (!self.form.processType) {
                    self.$alert('请选择流程类型', { type: 'error' })
                    return false
                }
                let data = common.paramsFormat(self.form)
                exportExcel(data).then(res => {
                    common.download(res.data, self, '网点设备变更流程.xls')
                })
            },
            /**
             * 流程跳转草稿
             */
            flowLoad(params) {
                let self = this
                let _data = { instanceId: params.processInstID }
                let data = common.paramsFormat(_data)
                queryDetail(data).then(res => {
                    let data = res.data.results
                    console.log("initiate--> data", self.$refs)
                    if (null == data || undefined == data) {
                        self.ifReadOnly = true
                        return false;
                    }
                    self.visibleClose = true
                    self.$nextTick(_ => {
                        let initiate = self.$refs.initiate
                        self.ifReadOnly = false
                        self.dialogTitle = "详情"
                        self.dialogWidth = "50%"
                        // 保存
                        initiate.ifShow = true
                        // 删除按钮
                        initiate.ifDelShow = true
                        data.bps = params
                        initiate.formData = data
                        console.log("self.data", data)
                        if (undefined != data.forConfirm && data.forConfirm.length > 0) {
                            initiate.formData.forConfirm = data.forConfirm.split(",")
                        }
                        initiate.handleChangeType(Number(data.changeType))
                        console.log('data:', data)
                    })

                })


            }
        }
    }
</script>
<style scoped>
    .marginbottom-10px {
        margin-bottom: 10px;
    }
</style>