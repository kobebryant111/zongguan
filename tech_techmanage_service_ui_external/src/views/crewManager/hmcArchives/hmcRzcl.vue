<!-- 入职材料 -->
<template>
    <div class="archives-container-rzcl">
        <!-- 无权限 -->
        <div class="archives-modular">
            <span class="xkg-color-black archives-title-name">入职材料</span>
            <el-row :gutter="20">
                <el-col :span="12">
                    <div>
                        <p>
                            征信报告
                        </p>
                        <div v-for="(item, index) in creditReport" :key="index">
                            {{item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'zhengxin')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                    <div>
                        <p>
                            保密承诺书、服务提供/外包员工背景调查表/厂商承诺书
                        </p>
                        <div v-for="(item, index) in confidentialityCommitment" :key="index">
                            {{item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'baomi')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                    <div>
                        <p>
                            学历、学位证书
                        </p> 
                        <div v-for="(item, index) in dueDiligenceReport" :key="index">
                            {{item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'jdbg')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                    <div>
                        <p>
                            宁银消金外包人员日常行为规范承诺书
                        </p>
                        <div v-for="(item, index) in letterOfCommitment" :key="index">
                            {{ item.docname }}
                            <span class="preview" @click="openNtkoWindow(item, 'cnsp')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                    <div>
                        <p>
                            案件防控和安全管理目标责任书
                        </p>
                        <div v-for="(item, index) in liabilityStatement" :key="index">
                            {{ item.docname }}
                            <span class="preview" @click="openNtkoWindow(item, 'lsfj')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                </el-col>
                <el-col :span="12"> 
                    <div>
                        <p>
                            社保缴纳证明/劳动合同/纳税证明
                        </p>
                        <div v-for="(item, index) in sociaiSecrityPaymentProve" :key="index">
                            {{item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'shebao')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                    <div>
                        <p>
                            身份证正反面
                        </p>
                        <div v-for="(item, index) in otherFile" :key="index">
                            {{item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'other')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                    <div>
                        <p>
                            其他(无社保缴纳证明、社保代缴、学籍在线报告等)
                        </p>
                        <div v-for="(item, index) in qtFile" :key="index">
                            {{item.docname}}
                            <span  class="preview" @click="openNtkoWindow(item, 'qtfj')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                    <div>
                        <p>
                            体检报告
                        </p>
                        <div v-for="(item, index) in medicalExamination" :key="index">
                            {{ item.docname }}
                            <span class="preview" @click="openNtkoWindow(item, 'mefj')">预览</span>
                            <span class="download" @click="downloadFile(item.id)">下载</span>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        
        <!-- 预览图片弹窗 -->
        <el-dialog title="" :visible.sync="ylDialog">
            <div class="ylDialogCon">
                <el-image :src="ylPhotoUrl" style="width:600px;height:400px" :fit="photoFit" :preview-src-list="ylPhotoUrl.split(',')"/>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import common from "@/api/common";
    import { upload } from "@/api/integrated/newWbrz";
    import { preview } from '@/api/common/flowApi'
    import { updatePpmOutsourcingExt } from "@/api/integrated/hmcDa";
    let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser; // 引入ntko
    export default {
        name: 'hmcRzcl',
        data() {
            return {
                ifAuth: false,
                rzclData: {},
                photoFit:"scale-down",
                zhengxinFileName: '', //征信报告文件名字
                shebaoFileName: '', //社保文件名字
                chengnuoFileName: '', //承诺书文件名字
                jindiaoFileName: '', //学历、学位证书文件名字
                qitaFileName: '', //其他附件文件名字
                otherFileName: '', //其他文件名字
                fileList: [], //文件上传
                otherFile:[],
                creditReport:[],
                dueDiligenceReport: [], // 尽调列表
                letterOfCommitment: [], // 行为规范承诺书
                liabilityStatement: [], // 案件防控和安全管理目标责任书
                medicalExamination: [], // 体检报告
                qtFile: [], //其他附件列表 
                ylDialog: false, //是否显示预览图片弹窗
                ylPhotoUrl: "", //预览图片地址
                sociaiSecrityPaymentProve:[],
                confidentialityCommitment:[],
                ifEdit: true, //是否可编辑
            }
        },
        props: {
            tuozhanDetail: {
                type: Object,
                required: true
            },
            auth: {
                type: String,
                required: true
            },
        },
        watch: {
            tuozhanDetail(val) {
                
                if (
                    val.creditReport != undefined &&
                    val.creditReport != " "
                ) {
                    let creditReport = val.creditReport.split(",");
                    creditReport.forEach(a =>{
                        this.getFileName(a, 'zhengxin');
                    }); 
                }
                if (
                    val.sociaiSecrityPaymentProve != undefined &&
                    val.sociaiSecrityPaymentProve != " "
                ) { 
                    let sociaiSecrityPaymentProve = val.sociaiSecrityPaymentProve.split(",");
                    sociaiSecrityPaymentProve.forEach(a =>{
                        this.getFileName(a, 'shebao');
                    }); 
                }
                if (
                    val.confidentialityCommitment != undefined &&
                    val.confidentialityCommitment != " "
                ) {
                    let confidentialityCommitment = val.confidentialityCommitment.split(",");
                    confidentialityCommitment.forEach(a =>{
                        this.getFileName(a, 'baomi');
                    }); 
                }
                if (
                    val.otherFile != undefined &&
                    val.otherFile != " "
                ) {
                    let otherFile = val.otherFile.split(",");
                    otherFile.forEach(a =>{
                        this.getFileName(a, 'other');
                    });
                }
                if (
                    val.dueDiligenceReport != undefined &&
                    val.dueDiligenceReport != " "
                ) {
                    let dueDiligenceReport = val.dueDiligenceReport.split(",");
                    dueDiligenceReport.forEach(a =>{
                        this.getFileName(a, 'jdbg');
                    }); 
                }
                if (
                    val.qtFile != undefined &&
                    val.qtFile != " "
                ) {
                    let qtFile = val.qtFile.split(",");
                    qtFile.forEach(a =>{
                        this.getFileName(a, 'qtfj');
                    }); 
                }
                // 行为规范承诺书
                            if (
                                val.letterOfCommitment !== undefined &&
                                val.letterOfCommitment !== ' '
                            ) {
                                const letterOfCommitment = val.letterOfCommitment.split(',')
                                letterOfCommitment.forEach(a => {
                                this.getFileName(a, 'cnsp')
                                })
                            }
                            // 案件防控和安全管理目标责任书
                            if (
                                val.liabilityStatement !== undefined &&
                                val.liabilityStatement !== ' '
                            ) {
                                const liabilityStatement = val.liabilityStatement.split(',')
                                liabilityStatement.forEach(a => {
                                this.getFileName(a, 'lsfj')
                                })
                            }
                        // 体检报告
                            if (
                                val.medicalExamination !== undefined &&
                                val.medicalExamination !== ' '
                            ) {
                                const medicalExamination = val.medicalExamination.split(',')
                                medicalExamination.forEach(a => {
                                this.getFileName(a, 'mefj')
                            })
                            }
                this.rzclData = val;
            },
            auth(val) {
                if (val == 'admin') {
                    this.ifAuth = true;
                } else {
                    this.ifAuth = false;
                }
            }
        },
        methods: {
              // 获取文件名称
            getFileName(id, mark) {
                preview(id)
                .then(res => {
                    if (res.data.status == 200) {
                        if (mark == "zhengxin") { 
                            this.creditReport.push({"id":id,"docname": res.data.results.docname, 
                            "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                        } else if (mark == "shebao") {
                            this.sociaiSecrityPaymentProve.push({"id":id,"docname": res.data.results.docname,
                            "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                        } else if (mark == "baomi") {
                            this.confidentialityCommitment.push({"id":id,"docname": res.data.results.docname,
                            "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                        } else if (mark == "other") {
                            this.otherFile.push({"id":id,"docname": res.data.results.docname,
                            "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                        } else if (mark == "jdbg") {
                            this.dueDiligenceReport.push({"id":id,"docname": res.data.results.docname,
                            "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                        } else if (mark == "qtfj") {
                            this.qtFile.push({"id":id,"docname": res.data.results.docname,
                            "tag1": res.data.results.tag1, "tag2": res.data.results.tag2});
                        } else if (mark === 'cnsp') {
                        this.letterOfCommitment.push({ 'id': id, 'docname': res.data.results.docname,
                        'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
                    } else if (mark === 'lsfj') {
                        this.liabilityStatement.push({ 'id': id, 'docname': res.data.results.docname,
                        'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
                    } else if (mark === 'mefj') {
                        this.medicalExamination.push({ 'id': id, 'docname': res.data.results.docname,
                        'tag1': res.data.results.tag1, 'tag2': res.data.results.tag2 })
                    }
                    } else {
                        this.$message.error("获取文件名失败");
                    }
                }).catch(error => {
                    this.$message.error("获取文件名失败");
                });
            },

            // 下载文件
            downloadFile(docId) {
                const self = this;
                common.downloadDoc(docId, self);
            },

            //ntko插件预览
            openNtkoWindow(obj, mark) { 
                let formJudge = obj.docname.split(".")[
                    obj.docname.split(".").length - 1
                ];
                if (formJudge == "jpg" || formJudge == "png" || formJudge == "PNG") {
                this.ylDialog = true;
                let pos = obj.tag1.lastIndexOf("/");
                let str = obj.tag1.substr(0, pos);
                if (obj.tag2 != undefined) {
                    this.ylPhotoUrl = str + "/" + obj.tag2;
                } else {
                    this.ylPhotoUrl = obj.tag1;
                }
                } else if (formJudge == "zip" || formJudge == "rar" ||  formJudge == "txt") {
                    this.$message.error("不能预览压缩文件或txt文件格式");
                } else {
                    this.ntkoYl(obj.id);
                }
            },

            // ntko预览方法
            ntkoYl(objectId) {
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
            // 编辑
            edit() {
                this.ifEdit = false;
            },

            // 保存当前页面
            save() {
                const loading = common.loading(this);
                let data = common.paramsFormat(this.rzclData)
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

    .archives-container-rzcl p {
        font-size: 14px;
    }
   
    /* 预览弹窗内容居中 */
    .ylDialogCon {
        text-align: center;
    }
    .preview,
    .download {
        color: #FBA42F;
        font-size: 14px;
        margin-left: 5px;
        cursor: pointer;
    }

    .againUpLoad {
        font-size: 14px;
        margin-left: 10px;
        color: #faad14;
        display: inline-block;
    }
</style>