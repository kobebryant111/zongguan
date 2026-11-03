<!-- 人员结算准入申请之简历信息 -->
<template>
    <div class="contentTwo">
        <div class="contentTwo_title">
            <p class="contentTwo_title_p">简历信息</p>
        </div>
        <div class="content">
            <!-- 表第一行 -->
            <el-form :model="jianliData" ref="ruleJianli">
                <div class="row_jianli">
                    <el-row :gutter="50">
                        <el-col :span="8">
                            <div>
                                <p>证件类型 *</p>
                                <el-form-item prop="certifType" :rules="[{ required: true, message: '请选择证件类型', trigger: 'change'}]">
                                    <el-radio-group v-model="jianliData.certifType">
                                        <el-radio v-for="item in certiftype_options" :label="item.dictid" :key="item.dictid">{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div>
                                <p>证件号码 *</p>
                                <el-form-item prop="certifNumber" :rules="[{ required: true, message: '请填写身份证号', trigger: 'blur'},{
                                    pattern: /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入正确的身份证号', trigger: 'blur'
                                }]">
                                    <el-input v-model="jianliData.certifNumber" placeholder="请输入"></el-input>
                                </el-form-item>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div>
                                <p>政治面貌 *</p>
                                <el-form-item prop="politicalStatus" :rules="[{ required: true, message: '请选择政治面貌', trigger: 'change'}]">
                                    <el-radio-group v-model="jianliData.politicalStatus">
                                        <el-radio v-for="item in politiccontenance_options" :label="item.dictid" :key="item.dictid"
                                            size="medium">{{item.dictname}}</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <!-- 表第二行 -->
                <div class="row_jianli">
                    <el-row :gutter="50">
                        <el-col :span="8">
                            <div>
                                <p>当前住址 *</p>
                                <el-form-item prop="address" :rules="[{ required: true, message: '请填写当前住址', trigger: 'blur'}]">
                                    <el-input type="textarea" autosize v-model="jianliData.address" placeholder="请输入"></el-input>
                                </el-form-item>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div>
                                <p>配偶姓名</p>
                                <el-input v-model="jianliData.partnerName" placeholder="请输入"></el-input>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div>
                                <p>配偶证件号码</p>
                                <el-input v-model="jianliData.idcardNumber" placeholder="请输入"></el-input>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <!-- 表第三行 -->
                <div class="row_jianli">
                    <el-row :gutter="50">
                        <el-col :span="8">
                            <div>
                                <p>所获证书</p>
                                <el-input type="textarea" autosize v-model="jianliData.certificate" placeholder="请输入"></el-input>
                            </div>
                        </el-col>
                    </el-row>
                </div>

                <!-- 面试信息 -->
                <div class="row_jianli">
                    <el-row>
                        <el-col :span="24">
                            <div>
                                <p>学习经历(大专/本科起)<span class="add_row" @click="eduExperAdd(1)">新增一行</span></p>
                                <el-table :data="jianliData.learnExperience" style="width: 100%">
                                    <el-table-column prop="studyDate" label="起止时间 *" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item :prop="`learnExperience.${scope.$index}.studyDate`" :rules="[{ required: true, message: '请选择起止时间', trigger: 'change'}]">
                                                <el-date-picker v-model="scope.row.studyDate" type="monthrange"
                                                    range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="schoolName" label="学校 *" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item :prop="`learnExperience.${scope.$index}.schoolName`" :rules="[{ required: true, message: '请填写学校', trigger: 'blur'}]">
                                                <el-input v-model="scope.row.schoolName" placeholder="请输入内容"></el-input>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="professName" label="专业 *" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item :prop="`learnExperience.${scope.$index}.professName`" :rules="[{ required: true, message: '请填写专业', trigger: 'blur'}]">
                                                <el-input v-model="scope.row.professName" placeholder="请输入内容"></el-input>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="eduGenre" label="教育类别 *" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item :prop="`learnExperience.${scope.$index}.eduGenre`" :rules="[{ required: true, message: '请选择教育类别', trigger: 'change'}]">
                                                <el-select v-model="scope.row.eduGenre" placeholder="请选择">
                                                    <el-option v-for="(item,index) in eduGenre_options" :key="index"
                                                        :label="item.dictname" :value="item.dictid"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="studyForm" label="学习形式 *" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item :prop="`learnExperience.${scope.$index}.studyForm`" :rules="[{ required: true, message: '请选择学习形式', trigger: 'change'}]">
                                                <el-select v-model="scope.row.studyForm" placeholder="请选择">
                                                    <el-option v-for="(item,index) in studyForm_options" :key="index"
                                                        :label="item.dictname" :value="item.dictid"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <div class="operation">
                                                <el-button @click.native.prevent="deleteRow(scope.$index, jianliData.learnExperience, scope.row)">
                                                    <i class="el-icon-delete"></i>
                                                </el-button>
                                            </div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-col>
                    </el-row>
                </div>


                <div class="row_jianli">
                    <el-row>
                        <el-col :span="24">
                            <div class="table">
                                <p>主要工作经历(入职前)<span class="add_row" @click="eduExperAdd(2)">新增一行</span></p>
                                <el-table :data="jianliData.mainJobs" style="width: 100%">
                                    <el-table-column prop="workDate" label="起止时间" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item>
                                                <el-date-picker v-model="scope.row.workDate" type="monthrange"
                                                    range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="comName" label="公司名称" width="270">
                                        <template slot-scope="scope">
                                            <el-input v-model="scope.row.comName" placeholder="请输入"></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="mainWork" label="主要工作" width="810">
                                        <template slot-scope="scope">
                                            <el-input type="textarea" autosize v-model="scope.row.mainWork" placeholder="请输入内容"></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <div class="operation">
                                                <el-button @click.native.prevent="deleteRow(scope.$index, jianliData.mainJobs, scope.row)">
                                                    <i class="el-icon-delete"></i>
                                                </el-button>
                                            </div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div class="row_jianli">
                    <el-row>
                        <el-col :span="24">
                            <div class="table">
                                <p>主要项目经历(入职前)<span class="add_row" @click="eduExperAdd(3)">新增一行</span></p>
                                <el-table :data="jianliData.mainItem" style="width: 100%">
                                    <el-table-column prop="projDate" label="起止时间" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item>
                                                <el-date-picker v-model="scope.row.projDate" type="monthrange"
                                                    range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="projName" label="项目名称" width="270">
                                        <template slot-scope="scope">
                                            <el-input type="textarea" autosize v-model="scope.row.projName" placeholder="请输入"></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="mainWork" label="主要工作" width="810">
                                        <template slot-scope="scope">
                                            <el-input type="textarea" autosize v-model="scope.row.mainWork" placeholder="请输入内容"></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <div class="operation">
                                                <el-button @click.native.prevent="deleteRow(scope.$index, jianliData.mainItem, scope.row)">
                                                    <i class="el-icon-delete"></i>
                                                </el-button>
                                            </div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-col>
                    </el-row>
                </div>


                <div class="row_jianli">
                    <el-row>
                        <el-col :span="24">
                            <div class="table">
                                <p>主要奖励记录<span class="add_row" @click="eduExperAdd(4)">新增一行</span></p>
                                <el-table :data="jianliData.mainReward" style="width: 100%">
                                    <el-table-column prop="prizeDate" label="获奖时间" width="270">
                                        <template slot-scope="scope">
                                            <div class="picker_sty">
                                                <el-form-item>
                                                    <el-date-picker v-model="scope.row.prizeDate" type="date"
                                                        placeholder="选择日期"></el-date-picker>
                                                </el-form-item>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="prizeName" label="奖励名称" width="270">
                                        <template slot-scope="scope">
                                            <el-input v-model="scope.row.prizeName" placeholder="请输入"></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="prizeGrade" label="奖励级别" width="270">
                                        <template slot-scope="scope">
                                            <el-input v-model="scope.row.prizeGrade" placeholder="请输入"></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="prizeCause" label="奖励原因" width="540">
                                        <template slot-scope="scope">
                                            <el-input type="textarea" autosize v-model="scope.row.prizeCause"
                                                placeholder="请输入"></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <div class="operation">
                                                <el-button @click.native.prevent="deleteRow(scope.$index, jianliData.mainReward, scope.row)">
                                                    <i class="el-icon-delete"></i>
                                                </el-button>
                                            </div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <div class="row_jianli">
                    <el-row>
                        <el-col :span="24">
                            <div class="table">
                                <p>主要家庭成员<span class="add_row" @click="eduExperAdd(5)">新增一行</span></p>
                                <el-table :data="jianliData.mainFamily" style="width: 100%">
                                    <el-table-column prop="familyName" label="姓名 *" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item :prop="`mainFamily.${scope.$index}.familyName`" :rules="[{ required: true, message: '请填写姓名', trigger: 'blur'}]">
                                                <el-input v-model="scope.row.familyName" placeholder="请输入"></el-input>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>

                                    <el-table-column prop="feminist" label="称谓 *" width="270">
                                        <template slot-scope="scope">
                                            <el-form-item :prop="`mainFamily.${scope.$index}.feminist`" :rules="[{ required: true, message: '请选择称谓', trigger: 'change'}]">
                                                <el-select v-model="scope.row.feminist" placeholder="请选择">
                                                    <el-option v-for="(item,index) in feminist_options" :key="index"
                                                        :label="item.dictname" :value="item.dictid"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="familyBirthday" label="出生年月日 *" width="270">
                                        <template slot-scope="scope">
                                            <div class="picker_sty">
                                                <el-form-item :prop="`mainFamily.${scope.$index}.familyBirthday`"
                                                    :rules="[{ required: true, message: '请选择出生日期', trigger: 'change'}]">
                                                    <el-date-picker v-model="scope.row.familyBirthday" type="date"
                                                        placeholder="选择日期"></el-date-picker>
                                                </el-form-item>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="unit" label="工作单位及职务 *" width="540">
                                        <template slot-scope="scope">
                                            <el-form-item :prop="`mainFamily.${scope.$index}.unit`" :rules="[{ required: true, message: '请填写工作单位及职务', trigger: 'blur'}]">
                                                <el-input type="textarea" autosize v-model="scope.row.unit" placeholder="请输入内容"></el-input>
                                            </el-form-item>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <div class="operation">
                                                <el-button @click.native.prevent="deleteRow(scope.$index, jianliData.mainFamily, scope.row)">
                                                    <i class="el-icon-delete"></i>
                                                </el-button>
                                            </div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </el-form>
        </div>

    </div>

</template>
<script>
    import common from "@/api/common";
    import { queryWorkItem } from "@/api/integrated/wbryzrlc";
    import { getDicEntry } from "@/api/eosDictEntry";
    import { saveDraft, getWbrzDetail } from "@/api/integrated/newWbrz";
    import Moment from 'moment';
    export default {
        data() {
            return {
                jianliData: {
                    certifType: '', //证件类型
                    certifNumber: '', //证件号码
                    partnerName: '', //配偶姓名
                    idcardNumber: '', //配偶证件号码
                    address: '', //当前住址
                    certificate: '', //所获证书
                    politicalStatus: '', //政治面貌
                    learnExperience: [{ //学习经历数据
                        studyDate: [], //学习经历日期
                        schoolName: '', //学习经历学校名字
                        professName: '', //专业
                        eduGenre: '', //教育类别
                        studyForm: '', //学习形式
                    }],
                    mainJobs: [{ //主要工作经历
                        workDate: [],
                        comName: '',
                        mainWork: ''
                    }],
                    mainItem: [{ //主要项目经历
                        projDate: [],
                        projName: '',
                        mainWork: ''
                    }],
                    mainReward: [{ //主要奖励记录
                        prizeDate: '',
                        prizeName: '',
                        prizeGrade: '',
                        prizeCause: ''
                    }],
                    mainFamily: [{ //家庭成员
                        familyName: '', //家庭成员姓名
                        feminist: '', // 称谓
                        familyBirthday: '',
                        unit: '' //工作单位及职务
                    }]
                },
                certiftype_options: [],
                politiccontenance_options: [],
                eduGenre_options: [],
                studyForm_options: [],
                feminist_options: [],
            }
        },
        props: {
            bps: {
                type: Object,
                required: true
            },
            id: {
                type: String,
                required: true
            },
            instanceId: {
                type: String,
                required: true
            }
        },
        watch: {

        },
        created() {
        },
        mounted() {

        },
        methods: {
            // // 页面初始化
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

                this.getDatail();
            },

            // 获取详情
            getDatail() {
                getWbrzDetail({ instanceId: this.instanceId }).then(res => {
                    if (res.data.status == 200) {
                        this.jianliData.certifNumber = res.data.results.certifNumber;
                        this.jianliData.certifType = res.data.results.certifType;
                        this.jianliData.address = res.data.results.address;
                        this.jianliData.certificate = res.data.results.certificate;
                        this.jianliData.politicalStatus = res.data.results.politicalStatus;
                        // this.jianliData.learnExperience = JSON.parse(res.data.results.learnExperience);
                        // this.jianliData.mainJobs = JSON.parse(res.data.results.mainJobs);
                        // this.jianliData.mainItem = JSON.parse(res.data.results.mainItem);
                        // this.jianliData.mainReward = JSON.parse(res.data.results.mainReward);
                        // this.jianliData.mainFamily = JSON.parse(res.data.results.mainFamily);

                        if (this.jianliData.learnExperience != undefined) {
                            this.jianliData.learnExperience = JSON.parse(res.data.results.learnExperience).filter(li => {
                                for (var i = 0; i < li.studyDate.length; i++) {
                                    li.studyDate[i] = Moment(li.studyDate[i].toString()).format('YYYY-MM');
                                }
                                return li;
                            });
                        } else {
                            this.jianliData.learnExperience = []
                        }
                        if (this.jianliData.mainJobs != undefined) {
                            this.jianliData.mainJobs = JSON.parse(res.data.results.mainJobs).filter(li => {
                                //var strcheck = new RegExp("[\u4E00-\u9FA5]+");
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
                        } else {
                            this.jianliData.mainJobs = []
                        }
                        if (this.jianliData.mainItem != undefined) {
                            this.jianliData.mainItem = JSON.parse(res.data.results.mainItem).filter(li => {
                                //var strcheck = new RegExp("[\u4E00-\u9FA5]+");
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
                        } else {
                            this.jianliData.mainItem = []
                        }
                        if (this.jianliData.mainReward != undefined) {
                            this.jianliData.mainReward = JSON.parse(res.data.results.mainReward).filter(li => {
                                li.prizeDate = Moment(li.prizeDate.toString()).format('YYYY-MM-DD');
                                return li;
                            });
                        } else {
                            this.jianliData.mainReward = []
                        }
                        if (this.jianliData.mainFamily != undefined) {
                            this.jianliData.mainFamily = JSON.parse(res.data.results.mainFamily).filter(li => {
                                if (Moment(li.familyBirthday.toString()).format('YYYY-MM-DD') == 'Invalid date') {
                                    li.familyBirthday = '';
                                } else {
                                    li.familyBirthday = Moment(li.familyBirthday.toString()).format('YYYY-MM-DD');
                                }
                                return li;
                            });
                        } else {
                            this.jianliData.mainFamily = []
                        }

                    } else {
                        this.$message.error("获取表格数据失败")
                    }
                })
            },


            // 新增一行
            eduExperAdd(v) {
                var list;
                if (v == 1) { //主要学习经历
                    list = {
                        studyDate: [], //学习经历日期
                        schoolName: '', //学习经历学校名字
                        professName: '', //专业
                        eduGenre: '', //教育类别
                        studyForm: '', //学习形式
                    };
                    this.jianliData.learnExperience.push(list);
                } else if (v == 2) {
                    list = { //主要工作经历
                        workDate: [],
                        comName: '',
                        mainWork: ''
                    }
                    this.jianliData.mainJobs.push(list);
                } else if (v == 3) {
                    list = { //主要项目经历
                        projDate: [],
                        projName: '',
                        mainWork: ''
                    }
                    this.jianliData.mainItem.push(list);
                } else if (v == 4) {
                    list = { //主要奖励记录
                        prizeDate: '',
                        prizeName: '',
                        prizeGrade: '',
                        prizeCause: ''
                    }
                    this.jianliData.mainReward.push(list);
                } else if (v == 5) {
                    var list;
                    list = { //家庭成员
                        familyName: '', //家庭成员姓名
                        feminist: '', // 称谓
                        familyBirthday: '',
                        unit: '' //工作单位及职务
                    }
                    this.jianliData.mainFamily.push(list);
                }
            },
            // 删除行
            deleteRow(index, rows, row) {
                rows.splice(index, 1);
            },

            // deleteRow(index, rows, row) {
            //     if (rows.length != 1 && row.id == "") {
            //         rows.splice(index, 1);

            //     } else if (rows.length != 1 && row.id != "") {
            //         this.delTableData(index, rows, row);
            //     }
            //     else {
            //         this.$message.error('不能少于一行');
            //     }

            // },


            //保存当前组件信息
            saveJianli() {
                const loading = common.loading(this);
                // this.jianliData.id = this.id;
                // this.jianliData.instanceId = this.instanceId;
                let params = {};
                let learnExperience = this.jianliData.learnExperience;
                let mainJobs = this.jianliData.mainJobs;
                let mainItem = this.jianliData.mainItem;
                let mainReward = this.jianliData.mainReward;
                let mainFamily = this.jianliData.mainFamily;
                params.learnExperience = JSON.stringify(learnExperience);
                params.mainJobs = JSON.stringify(mainJobs);
                params.mainItem = JSON.stringify(mainItem);
                params.mainReward = JSON.stringify(mainReward);
                params.mainFamily = JSON.stringify(mainFamily);
                params.id = this.id;
                params.instanceId = this.instanceId;
                params.certifType = this.jianliData.certifType;
                params.certifNumber = this.jianliData.certifNumber;
                params.partnerName = this.jianliData.partnerName;
                params.idcardNumber = this.jianliData.idcardNumber;
                params.address = this.jianliData.address;
                params.certificate = this.jianliData.certificate;
                params.politicalStatus = this.jianliData.politicalStatus;
                params.bps = this.bps;
                return saveDraft(params).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('保存草稿成功');

                        return res.data;
                    } else {
                        this.$message.error('保存草稿失败')
                    }
                });
            },

            //验证必填项
            verify() {
                this.$refs['ruleJianli'].validate((valid) => {
                    this.$emit('verifyJianli', valid);
                })
            }
        }
    }
</script>
<style scoped>
    .content {
        width: 100%;
        /* height: 600px; */
        font-size: 16px;
        background-color: #ffffff;
        padding: 15px 20px;
    }

    p {
        color: #262626;
        font-size: 14px;
        margin: 8px 0;
    }

    .contentTwo_title {
        height: 60px;
        line-height: 60px;
        background-color: #ffffff;
        padding: 0 25px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
    }

    .contentTwo_title_p {
        color: rgba(38, 38, 38, 1);
        font-size: 16px;
        height: 22px;
        line-height: 22px;
        font-weight: 600;
        margin: 0;
        display: inline-block;
    }

    .contentTwo .row_jianli {
        margin-bottom: 15px;
    }

    .add_row {
        color: #faad14;
        margin-left: 20px;
        cursor: pointer;
    }
</style>

<style>
    .contentTwo .el-icon-delete {
        width: 11px;
        height: 11px;
        color: #f56c6c;
    }

    .operation .el-button {
        border: 1px solid #ffffff;
        padding: 0;
    }

    /* table表格修改 */

    .el-table th {
        background: rgba(250, 250, 250, 1);
    }

    .el-table thead {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }

    .contentTwo .el-table--medium td {
        padding: 0;
    }

    .contentTwo .el-table--medium th {
        padding: 0;
    }

    .el-date-editor--daterange.el-input,
    .el-date-editor--daterange.el-input__inner,
    .el-date-editor--timerange.el-input,
    .el-date-editor--timerange.el-input__inner {
        width: 250px;
    }

    .el-date-table td.end-date span,
    .el-date-table td.start-date span {
        background-color: rgba(251, 164, 47, 1);
    }

    .el-range-editor.is-active,
    .el-range-editor.is-active:hover {
        border-color: rgba(251, 164, 47, 1);
    }

    .el-picker-panel__icon-btn:hover {
        color: rgba(251, 164, 47, 1);
    }

    .el-date-table td.available:hover {
        color: rgba(251, 164, 47, 1);
    }

    .el-date-editor .el-range-separator {
        padding: 0;
        width: 4%;
    }

    /* form表单样式修改 */
    .contentTwo .el-form-item {
        margin: 16px 0;
    }
</style>