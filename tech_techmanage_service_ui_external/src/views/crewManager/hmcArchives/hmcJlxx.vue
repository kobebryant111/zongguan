<!-- 简历信息 -->
<template>
    <div class="archives-container-jlxx">
        <!-- 无权限 -->
        <div class="archives-main">
            <div class="archives-modular">
                <div class="archives-title">
                    <span class="xkg-color-black archives-title-name">简历信息</span>
                </div>
                <el-row :gutter="30">
                    <el-col :span="6">
                        <p class="xkg-color-grey xkg-fontsize-14px">证件类型</p>
                        <p>{{jlxxData.cardTypename == undefined ? '无' : jlxxData.cardTypename}}</p>
                    </el-col>
                    <el-col :span="6">
                        <p class="xkg-color-grey xkg-fontsize-14px">证件号码</p>
                        <p>{{jlxxData.cardId == undefined ? '无' : jlxxData.cardId}}</p>
                    </el-col>
                    <el-col :span="6">
                        <p class="xkg-color-grey xkg-fontsize-14px">政治面貌</p>
                        <p>{{jlxxData.politicalStatusname == undefined ? '无' : jlxxData.politicalStatusname}}</p>
                    </el-col>
                    <el-col :span="6">
                        <p class="xkg-color-grey xkg-fontsize-14px">所获证书</p>
                        <p>{{jlxxData.certificate == undefined ? '无' : jlxxData.certificate}}</p>
                    </el-col>
                </el-row>
                <el-row :gutter="30">
                    <el-col :span="6">
                        <p class="xkg-color-grey xkg-fontsize-14px">配偶姓名</p>
                        <p>{{jlxxData.partnerName == undefined ? '无' : jlxxData.partnerName}}</p>
                    </el-col>
                    <el-col :span="6">
                        <p class="xkg-color-grey xkg-fontsize-14px">配偶证件号码</p>
                        <p>{{jlxxData.idcardNumber == undefined ? '无' : jlxxData.idcardNumber}}</p>
                    </el-col>
                    <el-col :span="6">
                        <p class="xkg-color-grey xkg-fontsize-14px">当前住址</p>
                        <p>{{jlxxData.address == undefined ? '无' : jlxxData.address}}</p>
                    </el-col>
                    <el-col :span="6">

                    </el-col>
                </el-row>
            </div>

            <div class="archives-modular archives-modular-margintop-15px">
                <div class="archives-table-title">
                    <span class="xkg-color-black archives-title-name">学习经历(大专/本科起)</span>
                </div>
                <el-table :data="jlxxData.learnExperience" style="width: 100%">
                    <el-table-column prop="studyDate" label="起止时间" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.studyDate[0]}} - {{scope.row.studyDate[1]}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="schoolName" label="学校" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.schoolName == undefined ? '无' : scope.row.schoolName}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="professName" label="专业" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.professName == undefined ? '无' : scope.row.professName}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="eduGenre" label="教育类别" width="270">
                        <template slot-scope="scope">
                            <div v-if="scope.row.eduGenre == undefined">无</div>
                            <div v-else v-for="(item,index) in eduGenre_options" :key="item.index">
                                <span v-if="item.dictid == scope.row.eduGenre">{{item.dictname}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="studyForm" label="学习形式" width="270">
                        <template slot-scope="scope">
                            <div v-if="scope.row.studyForm == undefined">无</div>
                            <div v-else v-for="(item,index) in studyForm_options" :key="item.index">
                                <span v-if="item.dictid == scope.row.studyForm">{{item.dictname}}</span>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <div class="archives-modular archives-modular-margintop-15px">
                <div class="archives-table-title">
                    <span class="xkg-color-black archives-title-name">主要工作经历(入职前)</span>
                </div>
                <el-table :data="jlxxData.mainJobs" style="width: 100%">
                    <el-table-column prop="workDate" label="起止时间" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.workDate[0]}} - {{scope.row.workDate[1]}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="comName" label="公司名称" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.comName == undefined ? '无' : scope.row.comName}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="mainWork" label="主要工作" width="810">
                        <template slot-scope="scope">
                            <div>{{scope.row.mainWork == undefined ? '无' : scope.row.mainWork}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <div class="archives-modular archives-modular-margintop-15px">
                <div class="archives-table-title">
                    <span class="xkg-color-black archives-title-name">主要项目经历(入职前)</span>
                </div>
                <el-table :data="jlxxData.mainItem" style="width: 100%">
                    <el-table-column prop="projDate" label="起止时间" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.projDate[0]}} - {{scope.row.projDate[1]}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="projName" label="项目名称" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.projName == undefined ? '无' : scope.row.projName}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="mainWork" label="主要工作" width="810">
                        <template slot-scope="scope">
                            <div>{{scope.row.mainWork == undefined ? '无' : scope.row.mainWork}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <div class="archives-modular archives-modular-margintop-15px">
                <div class="archives-table-title">
                    <span class="xkg-color-black archives-title-name">主要奖励记录</span>
                </div>
                <el-table :data="jlxxData.mainReward" style="width: 100%">
                    <el-table-column prop="prizeDate" label="获奖时间" width="270">
                        <template slot-scope="scope">
                            <div class="picker_sty">
                                <div>{{scope.row.prizeDate == undefined ? '无' : scope.row.prizeDate}}</div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="prizeName" label="奖励名称" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.prizeName == undefined ? '无' : scope.row.prizeName}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="prizeGrade" label="奖励级别" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.prizeGrade == undefined ? '无' : scope.row.prizeGrade}}</div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="prizeCause" label="奖励原因" width="540">
                        <template slot-scope="scope">
                            <div>{{scope.row.prizeCause == undefined ? '无' : scope.row.prizeCause}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <div class="archives-modular archives-modular-margintop-15px">
                <div class="archives-table-title">
                    <span class="xkg-color-black archives-title-name">主要家庭成员</span>
                </div>
                <el-table :data="jlxxData.mainFamily" style="width: 100%">
                    <el-table-column prop="familyName" label="姓名" width="270">
                        <template slot-scope="scope">
                            <div>{{scope.row.familyName == undefined ? '无' : scope.row.familyName}}</div>
                        </template>
                    </el-table-column>

                    <el-table-column prop="feminist" label="称谓" width="270">
                        <template slot-scope="scope">
                            <div v-if="scope.row.feminist == undefined">无</div>
                            <div v-else v-for="(item,index) in feminist_options" :key="item.index">
                                <span v-if="item.dictid == scope.row.feminist">{{item.dictname}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="familyBirthday" label="出生年月日" width="270">
                        <template slot-scope="scope">
                            <div class="picker_sty">
                                <div>{{scope.row.familyBirthday == undefined ? '无' : scope.row.familyBirthday}}</div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="unit" label="工作单位及职务" width="540">
                        <template slot-scope="scope">
                            <div>{{scope.row.unit == undefined ? '无' : scope.row.unit}}</div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>

<script>
    import { getDicEntry } from "@/api/eosDictEntry";
    import { updatePpmOutsourcingExt, getExtDetailAll } from "@/api/integrated/hmcDa";
    import common from "@/api/common";
    import Moment from 'moment';
    export default {
        name: 'hmcJlxx',
        data() {
            return {
                ifAuth: false,
                jlxxData: {},
                ifEdit: true, //是否可编辑
                certiftype_options: [],
                politiccontenance_options: [],
                eduGenre_options: [],
                studyForm_options: [],
                feminist_options: [],
            }
        },
        props: {
            auth: {
                type: String,
                required: true
            },
        },
        watch: {
            auth(val) {
                if (val == 'admin') {
                    this.ifAuth = true;
                } else {
                    this.ifAuth = false;
                }
            }
        },
        mounted() {
            this.init();
        },
        methods: {
            //  页面初始化
            init: function () {
                getDicEntry({
                    dicttypeid: "NEWKG_CERTIFTYPE,NEWKG_POLITICAL,,NEWYY_EDUCATION,LEARNMODALITY,NEWKG_CHENGWEI"
                })
                    .then(res => {
                        if (res.data.status !== 200) {
                            this.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        let data = res.data.results;
                        // 证件类型
                        this.certiftype_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_CERTIFTYPE";
                        });
                        //政治面貌
                        this.politiccontenance_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_POLITICAL";
                        });
                        //学历
                        this.eduGenre_options = data.filter(item => {
                            return item.dicttypeid == "NEWYY_EDUCATION";
                        });
                        //学习形式
                        this.studyForm_options = data.filter(item => {
                            return item.dicttypeid == "LEARNMODALITY";
                        });
                        //称谓
                        this.feminist_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_CHENGWEI";
                        });
                        return data;
                    });
            },

            getTuozhanDetail(val) {
                const loading = common.loading(this);
                getExtDetailAll({ userId: val })
                    .then(res => {
                        loading.close();
                        if (res.data.status == 200) {
                            if (res.data.results.learnExperience != undefined) {
                                res.data.results.learnExperience = JSON.parse(res.data.results.learnExperience).filter(li => {
                                    for (var i = 0; i < li.studyDate.length; i++) {
                                        li.studyDate[i] = Moment(li.studyDate[i].toString()).format('YYYY-MM');
                                    }
                                    return li;
                                });
                            }
                            if (res.data.results.mainJobs != undefined) {
                                res.data.results.mainJobs = JSON.parse(res.data.results.mainJobs).filter(li => {
                                    //var strcheck = new RegExp("[\u4E00-\u9FA5]+")
                                    for (var i = 0; i < li.workDate.length; i++) {
                                        if(li.workDate[i] !== null){
                                            if(Moment(li.workDate[i].toString()).format('YYYY-MM') == 'Invalid date'){
                                                li.workDate[i] = '';
                                            }else{
                                                li.workDate[i] = Moment(li.workDate[i].toString()).format('YYYY-MM');
                                            }
                                        }else{
                                            li.workDate[i] = '';
                                        }
                                    }
                                    return li;
                                });
                            }

                            if (res.data.results.mainItem != undefined) {
                                res.data.results.mainItem = JSON.parse(res.data.results.mainItem).filter(li => {
                                    //var strcheck = new RegExp("[\u4E00-\u9FA5]+")
                                    for (var i = 0; i < li.projDate.length; i++) {
                                        if(li.projDate[i] !== null){
                                            if(Moment(li.projDate[i].toString()).format('YYYY-MM') == 'Invalid date'){
                                                li.projDate[i] = '';
                                            }else{
                                                li.projDate[i] = Moment(li.projDate[i].toString()).format('YYYY-MM');
                                            }
                                            //li.projDate[i] = Moment(li.projDate[i].toString()).format('YYYY-MM');
                                        }else{
                                            li.projDate[i] = '';
                                        }
                                    }
                                    return li;
                                });
                            }

                            if (res.data.results.mainReward != undefined) {
                                res.data.results.mainReward = JSON.parse(res.data.results.mainReward).filter(li => {
                                    li.prizeDate = Moment(li.prizeDate.toString()).format('YYYY-MM-DD');
                                    return li;
                                });
                            }

                            if (res.data.results.mainFamily != undefined) {
                                res.data.results.mainFamily = JSON.parse(res.data.results.mainFamily).filter(li => {
                                    li.familyBirthday = Moment(li.familyBirthday.toString()).format('YYYY-MM-DD');
                                    return li;
                                });
                            }
                            this.jlxxData = res.data.results;
                        } else {
                            this.$message.error('查询拓展信息失败');
                        }
                    })
                    .catch(error => {
                        this.$message.error('查询拓展信息失败');
                    });
            },

            // 新增一行
            addRow(v) {
                var list;
                if (v == 'xxjl') { //主要学习经历
                    list = {
                        studyDate: [], //学习经历日期
                        schoolName: '', //学习经历学校名字
                        professName: '', //专业
                        eduGenre: '', //教育类别
                        studyForm: '', //学习形式
                    };
                    this.jlxxData.learnExperience.push(list);
                } else if (v == 'gzjl') {
                    list = { //主要工作经历
                        workDate: [],
                        comName: '',
                        mainWork: ''
                    }
                    this.jlxxData.mainJobs.push(list);
                } else if (v == 'xmjl') {
                    list = { //主要项目经历
                        projDate: [],
                        projName: '',
                        mainWork: ''
                    }
                    this.jlxxData.mainItem.push(list);
                } else if (v == 'jljl') {
                    list = { //主要奖励记录
                        prizeDate: '',
                        prizeName: '',
                        prizeGrade: '',
                        prizeCause: ''
                    }
                    this.jlxxData.mainReward.push(list);
                } else if (v == 'jtcy') {
                    var list;
                    list = { //家庭成员
                        familyName: '', //家庭成员姓名
                        feminist: '', // 称谓
                        familyBirthday: '',
                        unit: '' //工作单位及职务
                    }
                    this.jlxxData.mainFamily.push(list);
                }
            },
            // 删除行
            deleteRow(index, rows, row) {
                rows.splice(index, 1);
            },

            // 编辑
            edit() {
                this.ifEdit = false;
            },

            // 保存当前页面
            save() {
                const loading = common.loading(this);
                let params = {};
                
                let learnExperience = this.jlxxData.learnExperience;
                let mainJobs = this.jlxxData.mainJobs;
                let mainItem = this.jlxxData.mainItem;
                let mainReward = this.jlxxData.mainReward;
                let mainFamily = this.jlxxData.mainFamily;
                params.learnExperience = JSON.stringify(learnExperience);
                params.mainJobs = JSON.stringify(mainJobs);
                params.mainItem = JSON.stringify(mainItem);
                params.mainReward = JSON.stringify(mainReward);
                params.mainFamily = JSON.stringify(mainFamily);
                params.userId = this.jlxxData.userId;
                params.cardType = this.jlxxData.cardType;
                params.cardId = this.jlxxData.cardId;
                params.partnerName = this.jlxxData.partnerName;
                params.idcardNumber = this.jlxxData.idcardNumber;
                params.address = this.jlxxData.address;
                params.certificate = this.jlxxData.certificate;
                params.politicalStatus = this.jlxxData.politicalStatus;
                Object.keys(params).forEach(key => {
                    if(params[key] == undefined) {
                        params[key] = null
                    }
                })
                console.log('params::::',params)
                let data = common.paramsFormat(params)
                updatePpmOutsourcingExt(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.ifEdit = true;
                        this.$message.success('保存成功');
                    } else {
                        this.$message.error('保存失败');
                    }
                }).catch(error => {
                    this.$message.error(error);
                })
            }
        }
    }
</script>

<style scoped>
    .archives-title-name {
        font-size: 15px;
        font-weight: 600;
    }

    .archives-title-edit {
        margin-left: 8px;
        cursor: pointer;
    }

    .archives-title-save {
        cursor: pointer;
    }

    .archives-modular-margintop-15px {
        margin-top: 15px;
    }

    .archives-table-title {
        margin-bottom: 15px;
    }
</style>

<style>
    .archives-container-jlxx .el-icon-delete {
        width: 11px;
        height: 11px;
        color: #f56c6c;
    }

    .archives-container-jlxx .el-button {
        border: none;
        padding: 0;
    }
</style>