<!-- 人员结算准入申请之培训成绩和入职材料 -->
<template>
    <div class="contentPxrz">
        <el-form :model="pxrzData" ref="rulePxrz">
            <div class="contentPxrz_main">
                <div class="contentTwo_title">
                    <p class="contentTwo_title_p">入职材料</p>
                </div>
                <div class="contentTwo_inte">
                    <!-- 表第四行 -->
                    <div class="row_pxrz upload">
                        <el-row :gutter="50">
                            <el-col :span="8">
                                <div>
                                    <p>征信报告 *</p>
                                    <el-form-item prop="creditReport" :rules="[{ required: true, message: '请上传征信报告'}]">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                            :show-file-list="false" :http-request="changeZxFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in zxData" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('zxbg',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                                <div class="upload_1_2">
                                    <p>保密承诺书、服务提供/外包员工背景调查表/厂商承诺书 *</p>
                                    <el-form-item prop="confidentialityCommitment" :rules="[{ required: true, 
                                        message: '请上传保密承诺书、服务提供/外包员工背景调查表/厂商承诺书'}]">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                            :show-file-list="false" :http-request="changeCnFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in cnData" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('cnsq',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                                 <div class="upload_2_d1">
                                    <p>学历、学位证书 *</p>
                                    <el-form-item prop="dueDiligenceReport" :rules="[{ required: true, message: '请上传学历、学位证书'}]">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                            :show-file-list="false" :http-request="changeJdFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in jdData" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('jdbg',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                                <div>
                                    <p>宁银消金外包人员日常行为规范承诺书 *</p>
                                    <el-form-item prop="letterOfCommitment" :rules="[{ required: true, message: '请上传宁银消金外包人员日常行为规范承诺书'}]">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                        :show-file-list="false" :http-request="changeLocFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in gfDate" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('cnsp',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="upload_2_d1">
                                    <p>社保缴纳证明/劳动合同/纳税证明 *</p>
                                    <el-form-item prop="sociaiSecrityPaymentProve" :rules="[{ required: true, message: '请上传社保缴纳证明/劳动合同/纳税证明'}]">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                            :show-file-list="false" :http-request="changeSbFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in sbData" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('sbht',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                                <div class="upload_2_2">
                                    <p>身份证正反面 *</p>
                                    <el-form-item prop="otherFile" :rules="[{ required: true, message: '请上传身份证正反面'}]">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                            :show-file-list="false" :http-request="changeOtherFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in qtData" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('qtwj',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                                 <div class="upload_2_d1">
                                    <p>其他(无社保缴纳证明、社保代缴、学籍在线报告等)</p>
                                    <el-form-item prop="qtFile">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                            :show-file-list="false" :http-request="changeQtFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in qtfjData" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('qtfj',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                                <div>
                                    <p>案件防控和安全管理目标责任书 *</p>
                                    <el-form-item prop="liabilityStatement" :rules="[{ required: true, message: '请上传案件防控和安全管理目标责任书'}]">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                        :show-file-list="false" :http-request="changeLsFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in lsfjData" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('lsfj',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>体检报告 *</p>
                                    <el-form-item prop="medicalExamination" :rules="[{ required: true, message: '请上传体检报告'}]">
                                        <el-upload class="upload-demo" action="Fake Action" :file-list="fileList"
                                        :show-file-list="false" :http-request="changeTjbgFile">
                                            <el-button size="small" type="primary">
                                                <i class="el-icon-upload2"></i>上传文件
                                            </el-button>
                                        </el-upload>
                                        <div v-for="(item, index) in mefjData" :key="index">
                                             {{item.docname}}  <span class="xkg-color-yellow xkg-marginleft-5px delButt" 
                                                @click="delUpload('mefj',item.docid)">删除</span>
                                        </div>
                                    </el-form-item>
                                </div>
                                
                            </el-col>
                            <el-col :span="8">
                                <div>
                                    <p>人脸照片 * <span class="upload_pict">只支持jpg格式，60-200k之间，600*480以上，白底</span></p>
                                    <el-form-item prop="faceimage" :rules="[{ required: true, message: '请上传人脸照片'}]">
                                        <el-upload class="upload-wbrz-picture" action="Fake Action" :file-list="imgLists"
                                            :http-request="changePhoto" :on-preview="photoPreview" :before-upload="beforeUpload"
                                            :show-file-list="true" list-type="picture-card" :on-success="succPhoto"
                                            :on-error="errPhoto" accept="image/jpeg">
                                            <i class="el-icon-plus">
                                                <div class="upload_i">上传照片</div>
                                            </i>
                                        </el-upload>
                                    </el-form-item>
                                    <el-dialog :visible.sync="dialogVisible">
                                        <el-image :src="dialogImageUrl" :preview-src-list="dialogImageUrl.split(',')"/>
                                    </el-dialog>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                </div>
            </div>
        </el-form>
        <div class="blank"></div>
    </div>
</template>
<script>
    import common from "@/api/common";
    import { save, queryWorkItem } from "@/api/integrated/wbryzrlc";
    import { saveDraft, liuzhuan, upload } from "@/api/integrated/newWbrz";
    export default {
        data() {
            return {
                pxrzData: {
                    eitquetteTime: '', //时间
                    eitquetteAchievement: '', //成绩
                    creditReport: [], //征信报告
                    sociaiSecrityPaymentProve: [], //社保
                    confidentialityCommitment: [], // 承诺书
                    dueDiligenceReport: [], //学历、学位证书
                    letterOfCommitment: [], // 行为规范承诺书
                    liabilityStatement: [], // 案件防控和安全管理目标责任书
                    medicalExamination: [], // 体检报告
                    qtFile: [],//其他附件
                    otherFile: [], //其他文件
                    faceimage: '', //人脸照片
                },
                eitquetteTime: '',
                eitquetteAchievement: '',
                fileList: [], //文件上传
                dialogImageUrl: '', //图片上传
                dialogVisible: false,
                activityInstName: '', //流程描述
                processDefName: '', //流程定义名称
                processInstName: '', //流程实例名称
                workItemID: '', //子表 
                processDefID: '', //主表
                processInstID: '', //流程ID
                errored: '', //错误信息
                imgLists: [], //照片上传列表
                zxData: [], //征信报告列表
                cnData: [], //承诺书列表 
                sbData: [], //社保列表 
                qtData: [], //其它文件
                jdData: [], //尽调列表 
                gfDate: [], //规范承诺书
                qtfjData: [], //其他附件列表 
                lsfjData: [], // 案件防控和安全管理目标责任书列表 
                mefjData: [] //体检报告列表
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
            },
        },
        watch: {

        },
        created() {
            console.log("材料页");
        },
        mounted() {

        },
        methods: {
            // 初始化
            init() {
                // this.getWorkItem();
            },

            // 征信报告文件状态改变
            changeZxFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.zxData.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('zxbg');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                });
            },

            // 承诺书文件状态改变
            changeCnFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.cnData.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('cnsq');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                });
            },

            // 社保文件状态改变
            changeSbFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.sbData.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('sbht');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                });
            },
            
            // 宁银消金外包人员日常行为规范承诺书
            changeLocFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.gfDate.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('cnsp');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                })
            },
            // 体检报告
            changeTjbgFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.mefjData.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('mefj');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                })
            },
            // 尽调文件状态改变
            changeJdFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.jdData.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('jdbg');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                });
            },
            // 案件防控和安全管理目标责任书
            changeLsFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.lsfjData.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('lsfj');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                })
            },
            // 其他附件状态改变
            changeQtFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.qtfjData.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('qtfj');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                });
            },
            // 其他文件状态改变
            changeOtherFile(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.qtData.push({"docid":res.data.results,"docname":file.file.name});
                        this.cleanData('qtwj');
                        this.$message.success('上传成功');
                    } else {
                        this.$message.error('上传失败');
                    }
                });
            },
 
            // 图片文件状态改变
            changePhoto(file, fileList) {
                var formData = new FormData();
                formData.append("file", file.file);
                formData.append("type", "AI");
                formData.append("catalogNo", "CATALOG2014091500001");
                let data = formData;
                upload(data).then(res => {
                    if (res.data.status == "200") {
                        this.$set(this.pxrzData, 'faceimage', res.data.results);
                        this.$nextTick(() => {
                            this.$refs['rulePxrz'].validateField('faceimage');
                        });
                        this.$message.success('上传成功');
                        file.onSuccess();
                    } else {
                        this.$message.error('上传失败');
                    }
                });
            },
            // 类型限制
            beforeUpload(file){
                let isJPG = file.type === 'image/jpeg';
                let isPNG = file.type === 'image/png';
                let isLt200K = file.size / 1024 < 200;
                if(!isJPG && !isPNG){
                    this.$message.error('人脸照片只能上传图片！')
                    return false;
                }
                if(!isLt200K) {
                    this.$message.error('照片大小不得大于200k!')
                    return false;
                }
                    return isJPG && isLt200K
            },

            // 图片上传成功
            succPhoto(res, file, fileList) {
                this.imgLists = [fileList[fileList.length - 1]];
            },

            // 图片上传失败
            errPhoto(res, file, fileList) {
            },

            // 图片预览
            photoPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },

            //获取待办工作项详情-单条
            getWorkItem() {
                let params = {};
                params.processInstID = this.instanceId;
                queryWorkItem(params).then(res => {
                    if (res.data.status == 200) {
                        this.activityInstName = res.data.results.activityInstName;
                        this.processDefName = res.data.results.processDefName;
                        this.processInstName = res.data.results.processInstName;
                        this.workItemID = res.data.results.workItemID;
                        this.processDefID = res.data.results.processDefID;
                    } else {
                        thsi.$message.error('获取待办工作项详情失败')
                    }
                });
            },

            // 删除上传
            delUpload(type,docid) {
                if (type == 'zxbg') {
                   let zxData = this.zxData.filter(item => item.docid != docid);
                   this.zxData = zxData;
                   this.cleanData(type);
                } else if (type == 'cnsq') {
                   let cnData = this.cnData.filter(item => item.docid != docid);
                   this.cnData = cnData;
                   this.cleanData(type);
                } else if (type == 'sbht') {
                   let sbData = this.sbData.filter(item => item.docid != docid);
                   this.sbData = sbData;
                   this.cleanData(type);
                } else if (type == 'qtwj') {
                   let qtData = this.qtData.filter(item => item.docid != docid);
                   this.qtData = qtData;
                   this.cleanData(type);
                } else if (type == 'jdbg') {
                   let jdData = this.jdData.filter(item => item.docid != docid);
                   this.jdData = jdData;
                   this.cleanData(type);
                } else if (type == 'qtfj') {
                   let qtfjData = this.qtfjData.filter(item => item.docid != docid);
                   this.qtfjData = qtfjData;
                   this.cleanData(type);
                } else if (type == 'cnsp') {
                   let gfDate = this.gfDate.filter(item => item.docid != docid);
                   this.gfDate = gfDate;
                   this.cleanData(type);
                } else if (type == 'lsfj') {
                   let lsfjData = this.lsfjData.filter(item => item.docid != docid);
                   this.lsfjData = lsfjData;
                   this.cleanData(type);
                } else if (type == 'mefj') {
                   let mefjData = this.mefjData.filter(item => item.docid != docid);
                   this.mefjData = mefjData;
                   this.cleanData(type);
                }
            },
            // 清洗数据
            cleanData(type){
                if (type == 'zxbg') {
                    let creditReport = [];
                    this.zxData.forEach(item => {
                        creditReport.push(item.docid);
                    });
                    this.$set(this.pxrzData, 'creditReport', creditReport);
                    this.$refs['rulePxrz'].validateField('creditReport');
                } else if (type == 'cnsq') {
                    let confidentialityCommitment = [];
                    this.cnData.forEach(item => {
                        confidentialityCommitment.push(item.docid);
                    });
                    this.$set(this.pxrzData, 'confidentialityCommitment', confidentialityCommitment);
                    this.$refs['rulePxrz'].validateField('confidentialityCommitment');
                } else if (type == 'sbht') {
                    let sociaiSecrityPaymentProve = [];
                    this.sbData.forEach(item => {
                        sociaiSecrityPaymentProve.push(item.docid);
                    });
                    this.$set(this.pxrzData, 'sociaiSecrityPaymentProve', sociaiSecrityPaymentProve);
                    this.$refs['rulePxrz'].validateField('sociaiSecrityPaymentProve');
                } else if (type == 'qtwj') {
                    let otherFile = [];
                    this.qtData.forEach(item => {
                        otherFile.push(item.docid);
                    });
                    this.$set(this.pxrzData, 'otherFile', otherFile);
                    this.$refs['rulePxrz'].validateField('otherFile');
                } else if (type == 'jdbg') {
                    let dueDiligenceReport = [];
                    this.jdData.forEach(item => {
                        dueDiligenceReport.push(item.docid);
                    });
                    this.$set(this.pxrzData, 'dueDiligenceReport', dueDiligenceReport);
                    this.$refs['rulePxrz'].validateField('dueDiligenceReport');
                } else if (type == 'qtfj') {
                    let qtFile = [];
                    this.qtfjData.forEach(item => {
                        qtFile.push(item.docid);
                    });
                    this.$set(this.pxrzData, 'qtFile', qtFile);
                    this.$refs['rulePxrz'].validateField('qtFile');
                } else if (type == 'cnsp') {
                    let letterOfCommitment = []
                    this.gfDate.forEach(item => {
                        letterOfCommitment.push(item.docid)
                    })
                    this.$set(this.pxrzData, 'letterOfCommitment', letterOfCommitment)
                    this.$refs['rulePxrz'].validateField('letterOfCommitment');
                } else if (type == 'lsfj') {
                    let liabilityStatement = []
                    this.lsfjData.forEach(item => {
                        liabilityStatement.push(item.docid)
                    })
                    this.$set(this.pxrzData, 'liabilityStatement', liabilityStatement)
                    this.$refs['rulePxrz'].validateField('liabilityStatement');
                } else if (type == 'mefj') {
                    let medicalExamination = []
                    this.mefjData.forEach(item => {
                        medicalExamination.push(item.docid)
                    })
                    this.$set(this.pxrzData, 'medicalExamination', medicalExamination)
                    this.$refs['rulePxrz'].validateField('medicalExamination');
                }
            },

            //保存当前组件信息
            savePxrz() {
                const loading = common.loading(this);
                this.pxrzData.id = this.id;
                this.pxrzData.instanceId = this.instanceId;
                this.pxrzData.bps = this.bps;
                if(this.pxrzData.creditReport){
                    this.pxrzData.creditReport = this.pxrzData.creditReport.join(",");
                }  
                if(this.pxrzData.otherFile){
                    this.pxrzData.otherFile = this.pxrzData.otherFile.join(",");
                }
                if(this.pxrzData.confidentialityCommitment){
                    this.pxrzData.confidentialityCommitment = this.pxrzData.confidentialityCommitment.join(",");
                }
                if(this.pxrzData.sociaiSecrityPaymentProve){
                    this.pxrzData.sociaiSecrityPaymentProve = this.pxrzData.sociaiSecrityPaymentProve.join(",");
                }
                if(this.pxrzData.dueDiligenceReport){
                    this.pxrzData.dueDiligenceReport = this.pxrzData.dueDiligenceReport.join(",");
                }
                if(this.pxrzData.qtFile){
                    this.pxrzData.qtFile = this.pxrzData.qtFile.join(",");
                }
                if (this.pxrzData.letterOfCommitment){
                    this.pxrzData.letterOfCommitment = this.pxrzData.letterOfCommitment.join(",");
                }
                if (this.pxrzData.liabilityStatement){
                    this.pxrzData.liabilityStatement = this.pxrzData.liabilityStatement.join(",");
                }
                if (this.pxrzData.medicalExamination){
                    this.pxrzData.medicalExamination = this.pxrzData.medicalExamination.join(",");
                }
                saveDraft(this.pxrzData).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('保存草稿成功');
                    } else {
                        this.$message.error('保存草稿失败')
                    }
                });
            },

            //流转
            liuzhuanPxrz() {
                const loading = common.loading(this);
                this.pxrzData.id = this.id;
                this.pxrzData.instanceId = this.instanceId;
                this.pxrzData.bps = this.bps;
                if(this.pxrzData.creditReport){
                    this.pxrzData.creditReport = this.pxrzData.creditReport.join(",");
                }  
                if(this.pxrzData.otherFile){
                    this.pxrzData.otherFile = this.pxrzData.otherFile.join(",");
                }
                if(this.pxrzData.confidentialityCommitment){
                    this.pxrzData.confidentialityCommitment = this.pxrzData.confidentialityCommitment.join(",");
                }
                if(this.pxrzData.sociaiSecrityPaymentProve){
                    this.pxrzData.sociaiSecrityPaymentProve = this.pxrzData.sociaiSecrityPaymentProve.join(",");
                }
                if(this.pxrzData.dueDiligenceReport){
                    this.pxrzData.dueDiligenceReport = this.pxrzData.dueDiligenceReport.join(",");
                }
                if(this.pxrzData.qtFile){
                    this.pxrzData.qtFile = this.pxrzData.qtFile.join(",");
                }
                if (this.pxrzData.letterOfCommitment){
                    this.pxrzData.letterOfCommitment = this.pxrzData.letterOfCommitment.join(",");
                }
                if (this.pxrzData.liabilityStatement){
                    this.pxrzData.liabilityStatement = this.pxrzData.liabilityStatement.join(",");
                }
                if (this.pxrzData.medicalExamination){
                    this.pxrzData.medicalExamination = this.pxrzData.medicalExamination.join(",");
                }
                return liuzhuan(this.pxrzData).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        let message = common.getFlowMessgeNew(res.data.message);
                        let messageArr = message.split(",");
                        this.$nextTick(() => {
                            this.messageNotify(messageArr);
                        })

                        return res.data;
                    } else {
                        this.$message.error('操作失败')
                    }
                });
            },

            //验证必填项
            verify() {
                this.$refs['rulePxrz'].validate((valid) => {
                    this.$emit('verifyPxrz', valid);
                })
            },

            //消息提示
            messageNotify(messageVal) {
                this.$notify({
                    title: '提示',
                    dangerouslyUseHTMLString: true,
                    message: '<div><div>' + messageVal[1] + '</div><div>' + messageVal[2] + '</div></div>',
                    duration: 0
                })
            },
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

    .contentPxrz_main {
        margin-top: 10px;
    }

    .contentTwo_inte {
        width: 100%;
        font-size: 16px;
        background-color: #ffffff;
        padding: 15px 20px;
    }

    .contentPxrz .row_pxrz {
        margin-bottom: 20px;
    }

    .upload {
        font-size: 14px;
        color: rgba(38, 38, 38, 1);
        font-weight: 400;
    }

    .upload_2_d1 p {
        display: inline-block;
    }

    .upload .upload_2_d1_p2 {
        margin-left: 20px;
        color: #faad14;
    }

    .upload_1_2,
    .upload_2_2 {
        margin-top: 15px;
    }

    .upload_pict {
        font-size: 14px;
        color: rgba(0, 0, 0, 0.45);
        font-weight: 400;
    }

    .againUpLoad {
        font-size: 14px;
        margin-left: 10px;
        color: #faad14;
        display: inline-block;
    }

    /* 上传照片样式修改 */
    .upload-wbrz-picture {
        display: flex;
        flex-direction: row;
    }

    /* 空格占位 */
    .blank {
        /* width: 100%;
        height: 175px; */
        height: calc(100vh - 615px);
    }

    .delButt {
        cursor: pointer;
    }
</style>

<style>
    .row_pxrz .el-date-editor.el-input {
        width: auto;
        display: block;
    }

    .upload .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }

    /* 照片样式修改 */
    .contentPxrz .el-icon-plus {
        color: rgba(0, 0, 0, 0.45);
    }

    .contentPxrz .el-upload--picture-card {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .contentPxrz .el-upload:focus {
        border-color: #faad14;
        color: #faad14;
    }



    .upload_i {
        font-size: 14px;
        margin-top: 15px;
    }

    .contentPxrz .el-icon-upload2 {
        margin-right: 10px;
    }

    /* 上传图片后隐藏删除按钮 */
    .upload-wbrz-picture .el-upload-list__item-actions:hover .el-upload-list__item-delete {
        display: none;
    }
</style>