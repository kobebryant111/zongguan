<!-- 面试管理列表 -->
<template>
    <div class="list-container interviewList">
        <!-- 修改后 -->
        <el-form inline>
            <div class="interviewList_head">
                <div>
                    <el-row>
                        <el-col :span="5">
                            <el-form-item prop="" label="">
                                <el-input placeholder="请选择" v-model="paramsForm.msname">
                                    <template slot="prefix">姓名：</template>
                                </el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item prop="" label="">
                                <div class="gongyingshang_zb">
                                    <el-select placeholder="请选择" v-model="paramsForm.wbsid" filterable>
                                        <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname"
                                            :value="item.id"></el-option>
                                        <template slot="prefix">供应商：</template>
                                    </el-select>
                                </div>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item prop="" label="">
                                <el-select placeholder="请选择" filterable v-model="paramsForm.deptno">
                                    <el-option v-for="item in depData" :key="item.id" :label="item.text" :value="item.id"></el-option>
                                    <template slot="prefix">部门：</template>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <!-- <el-col :span="11">
                        <el-row type="flex" justify="end" :gutter="70">
                            <el-col :span="1">
                                <clean-btn :size="sizes" :forms="this.paramsForm" />
                            </el-col>
                            <el-col :span="1">
                                <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
                            </el-col>
                        </el-row>
                    </el-col> -->
                    </el-row>
                </div>
                <div>
                    <el-row>
                        <el-col :span="12">
                            <clean-btn :size="sizes" :forms="this.paramsForm" />
                        </el-col>
                        <!-- <el-col :span="2"></el-col> -->
                        <el-col :span="12">
                            <div class="interviewList_head_right_orange">
                                <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
                            </div>
                        </el-col>
                    </el-row>
                </div>

            </div>
        </el-form>

        <div class="requireContainer">
            <div class="requireContainer_head">
                <div class="requireContainer_head_en"></div>
                <el-row type="flex" justify="end">
                    <el-col :span="11">
                        <button-common :buttonName="buttonExport" :size="sizes" :fns="handelExport"></button-common>
                    </el-col>
                    <el-col :span="12">
                        <button-orange :buttonName="buttonStart" :size="sizes" :fns="startInterview"></button-orange>
                    </el-col>
                </el-row>
            </div>
            <el-table :data="crewData" highlight-current-row height="510" @select-all="onSelectTable" @select="onSelectTable">
                <!-- <el-table-column type="selection" width="55" /> -->
                <el-table-column prop="createdate" label="新增时间" width="100" :formatter="createtimeFormat"
                    :show-overflow-tooltip="true"></el-table-column>
                <el-table-column label="姓名" prop="msname" width="100" :show-overflow-tooltip="true" />
                <el-table-column label="身份证号" width="180" prop="idcard" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column label="外包商" prop="outsourcname" :show-overflow-tooltip="true" width="220" />
                <el-table-column label="部门" prop="deptname" width="155" :formatter="deptFormat" :show-overflow-tooltip="true" />
                <el-table-column label="人员类型" width="80" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-if="scope.row.position == 'YF'">研发</span>
                        <span v-else-if="scope.row.position == 'CS'">测试</span>
                        <span v-else-if="scope.row.position == 'YW'">运维</span>
                        <span v-else-if="scope.row.position == 'WY'">文员</span>
                    </template>
                </el-table-column>
                <el-table-column label="人员属性" width="80">
                    <template slot-scope="scope">
                        <!-- <span v-if="scope.row.researchers == 1">人力外包</span>
                      <span v-else>项目外包</span> -->
                        <span v-for="item in rysx_options" :key="item.dictid" v-if="item.dictid == scope.row.researchers">{{item.dictname}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="面试结果" width="76" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-if="scope.row.interviewresult == '0'">不通过</span>
                        <span v-else-if="scope.row.interviewresult == '1'">通过</span>
                        <span v-else></span>
                    </template>
                </el-table-column>
                <el-table-column label="符合要求" width="76" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        <span v-if="scope.row.isrz == '0'">否</span>
                        <span v-else-if="scope.row.isrz == '1'">是</span>
                        <span v-else></span>
                    </template>
                </el-table-column>
                <el-table-column label="年限" width="65" prop="workyears" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column label="等级" prop="suggestlevel" :show-overflow-tooltip="true" width="65"></el-table-column>
                <el-table-column label="单价" prop="suggestprice" :show-overflow-tooltip="true" width="123" v-if="isAuthView"></el-table-column>

                <el-table-column label="操作" width="122">
                    <template slot-scope="scope">
                        <!-- <el-link type="warning" :underline="false" @click="onUserDetail(scope.row.badge)">详情</el-link> -->
                        <el-link type="warning" :underline="false" @click="reruireProgress(scope.row)">进程</el-link>
                        <el-link type="warning" :underline="false" @click="openNtkoWindow(scope.row.jl)">简历</el-link>
                    </template>
                </el-table-column>
                <el-table-column align="right" width="34">
                    <template slot="header" slot-scope="scope">
                        <i class="el-icon-setting"></i>
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
    import { getDicEntry } from "@/api/eosDictEntry";
    import { getCrewPager } from "@/api/userApi/bankUser";
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import cleanBtn from "@/components/CleanBtn";
    import { queryPager, exportExcel } from '@/api/integrated/wbms'
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
    import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
    import { queryResourceDept, getWbCompany } from "@/api/integrated/wbryzrlc";
    import Moment from 'moment'
    import { getFlowData, getListAll } from "@/api/common/workflowApi";
    let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
    export default {
        name: 'Crew',
        components: { pager, cleanBtn, buttonOrange, buttonCommon },
        data() {
            return {
                totalNumber: 0,
                size: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                paramsForm: {
                    msname: null, // 姓名
                    wbsid: null, // 外包商
                    deptno: '', //部门
                },
                crewData: [], // 列表分页数据
                drawer: false,  // 默认不显示
                peo: "zh",
                buttonStart: '发起面试', //发起需求按钮
                sizes: 'small', //按钮大小
                buttonSearch: '查 询', //查询按钮
                deptno: '',
                depData: [],
                deptno_options: [], //部门数据
                outsourceno_options: [], //外包商
                buttonExport: '导 出', //导出按钮
                tem_detp_id: '',       // 临时部门编号
                rysx_options: [], //人员属性集合
                isAuthView: false,     // 是否有权限查看单价 默认没哟
            };
        },
        created() {
            this.filDep();
            this.getWbcom();
        },
        mounted() {
            this.loadInit();
            this.searchEntry();
        },
        methods: {
            /**
                初始化
               */
            init: function (params) {
                let data = common.paramsFormat(params);
                getCrewPager(data).then(res => {
                    this.crewData = res.data.results;
                    this.totalNumber = res.data.total;
                });
            }, // 初始化 end

            // 查询业务字典
            searchEntry() {
                getDicEntry({ dicttypeid: "NEWKG_RYSX" }).then(res => {
                    if (res.data.status !== 200) {
                        this.alter(res.data.message, { type: "error" });
                        return false;
                    }
                    let data = res.data.results;
                    // 人员属性
                    this.rysx_options = data.filter(item => {
                        return item.dicttypeid == "NEWKG_RYSX";
                    });
                    return data;
                });
            },

            /**
             * 分页初始化方法
             */
            loadInit: function () {
                let params = {};
                params.size = this.size;
                params.index = this.index;
                this.getPagerList(params);
            }, // end

            // 获取部门
            filDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params)
                    .then(res => {
                        var fild = res.data.results[0].children;
                        let name = res.data.results[0].text;
                        this.depData = fild.filter(tt => {
                            tt.text = tt.text.replace(name + "-", "");
                            return tt;
                        });;

                    })
                    .catch(error => {
                        this.$message.error(error)
                    });  // end
            },

            // 查询外包商
            getWbcom() {
                getListAll()
                    .then(res => {
                        this.outsourceno_options = res.data.results;
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 获取列表数据
            getPagerList(params) {
                let data = common.paramsFormat(params);
                let self = this
                queryPager(data).then(res => {
                    if (res.data.status == 200) {
                        self.crewData = res.data.results;
                        self.totalNumber = res.data.total;
                        if (self.totalNumber <= 0) {
                            return false
                        }
                        let type = self.crewData[0].roleType
                        if (1 === type || 4 === type) {
                            self.isAuthView = true
                        }
                    }
                })
            },

            //  时间格式化
            createtimeFormat: function (row, cloumn) {
                let time = '';
                if (row.createtime != null) {
                    // time = common.dateFormat(row.createtime, "yyyy年MM月dd日");
                    time = Moment(row.createtime).format('YYYY-MM-DD')
                }
                return time;
            },

            // 部门显示内容
            deptFormat(row, cloumn) {
                let dep = '';
                const reg = /([^-]+)$/;
                if (row.deptname != null) {
                    dep = row.deptname.match(reg)[1];
                }
                return dep;
            },

            /**
             * 搜索事件
             */
            submitBtn: function () {
                let params = {};
                let depid = this.paramsForm.deptno;
                params.deptno = depid.replace("dep", "");
                params.msname = this.paramsForm.msname;
                params.wbsid = this.paramsForm.wbsid;
                // params.size = this.size;
                // params.index = this.index;
                this.getPagerList(params) //bin
            }, // 搜索事件 end

            /**
             * 点击table行
             */
            onSelectTable: function (selection, row) {
            },

            /**
             * 分页触发
             */
            getPager: function (size, index) {
                let params = {};
                let depid = this.paramsForm.deptno;
                params.deptno = depid.replace("dep", "");
                params.msname = this.paramsForm.msname;
                params.wbsid = this.paramsForm.wbsid;
                // params.depid = this.paramsForm.depid;
                // params.usertype = this.paramsForm.usertype;
                params.size = size;
                params.index = index;
                this.size = size;
                this.index = index;
                this.getPagerList(params);
            }, // end

            /**
             * 行员详情
             */

            onUserDetail: function (userId) {
                this.drawer = true;
                this.$nextTick(_ => {
                    let item = this.$refs.cwdt;
                    item.userId = userId
                    item.init();
                })

            }, // end

            // 导出
            handelExport: function () {
                let searchData = {}
                let depid = this.paramsForm.deptno;
                searchData.deptno = depid.replace("dep", "");
                searchData.msname = this.paramsForm.msname;
                searchData.wbsid = this.paramsForm.wbsid;
                let params = common.paramsFormat(searchData);
                const self = this;
                exportExcel(params).then(res => {
                    common.download(res.data, self, "面试人员.xls");
                });
            },

            // ntko
            openNtkoWindow(objectId) {
                var url = window.location.href;
                let ntkoed = ntkoBrowser.ExtensionInstalled();
                if (ntkoed) {
                    ntkoBrowser.openWindow(
                        url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
                    );
                } else {
                    window.open(
                        url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
                    );
                }
                // 注册回调方法
                window.ntkoCloseEvent = function () {
                    alert("跨浏览器插件应用程序窗口已关闭!");
                };
            },

            reruireProgress(val) {
                this.$router.push({
                    name: 'interviewJcDetail',
                    params: { ypzData: val }
                })
            },

            // 分组
            groupBy(items, f) {
                let groups = {};
                items.forEach(i => {
                    let group = JSON.stringify(f(i));
                    groups[group] = groups[group] || [];
                    groups[group].push(i);
                });
                return Object.keys(groups).map(group => {
                    return groups[group];
                });
            },

            // 发起面试
            startInterview() {
                this.$router.push({
                    name: 'wbryms',
                    query: {
                        formScene: "interviewAdd"
                    }
                });
            }
        }
    };
</script>
<style scoped>
    .interviewList {
        padding: 15px;
    }

    .interviewList_head {
        background-color: #ffffff;
        padding: 18px;
        display: flex;
        justify-content: space-between;
    }

    .requireContainer {
        background-color: #ffffff;
        margin-top: 15px;
        padding: 15px;
    }

    .requireContainer_head {
        float: right;
        margin-bottom: 10px;
    }

    .requireContainer_head_en {
        width: 160px;
    }


    .requireList_head_right {
        float: right;
    }

    .requireList_head_right div {
        display: inline-block;
    }

    .requireList_head_right_clean {
        margin-right: 10px;
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
<style>
    /* el-input修改 */
    .interviewList .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    /* 供应商输入框 */
    .gongyingshang_zb .el-input--prefix .el-input__inner {
        padding-left: 65px;
    }

    .interviewList .el-input__prefix {
        left: 8px;
        color: #262626;
        font-size: 14px;
    }

    /* element表单修改 */
    .interviewList .el-form-item {
        margin-bottom: 0px;
    }

    /* 表头背景色 */
    .interviewList .el-table th {
        background: #FAFAFA;
    }
</style>