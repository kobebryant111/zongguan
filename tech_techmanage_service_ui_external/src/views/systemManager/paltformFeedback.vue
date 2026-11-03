<!-- 平台反馈 -->
<template>
    <div>
        <div class="paltForm-container">
            <el-form inline>
                <div class="paltForm-message">
                    <div class="paltForm-message-title">
                        <p>反馈人信息</p>
                    </div>
                    <el-row type="flex" justify="space-around">
                        <el-col :span="6" v-model="fkxx">
                            <el-form-item label="反馈人：">
                                <el-input v-model="fkxx" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="反馈部门：">
                                <el-input v-model="fkxx" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="联系方式：">
                                <el-input v-model="fkxx" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </div>

                <div class="platform-content">
                    <div class="paltForm-message-title">
                        <p>反馈内容</p>
                    </div>
                    <el-row type="flex" justify="space-around">
                        <el-col :span="6" v-model="fkxx">
                            <el-form-item label="反馈平台：">
                                <el-select v-model="fkxx">
                                    <el-option :value="fkxx"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="系统负责人：">
                                <el-input v-model="fkxx" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="联系方式：">
                                <el-input v-model="fkxx" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row type="flex" justify="center">
                        <el-col :span="22" v-model="fkxx">
                            <el-form-item label="反馈内容：" class="fknr">
                                <el-input v-model="fkxx" placeholder="500字以内" type="textarea" :autosize="{ minRows: 12}"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row type="flex" justify="center">
                        <el-col :span="22" v-model="fkxx">
                            <el-form-item label="附件：" class="fknr">
                                <el-upload class="upload-demo againUpLoad" action="Fake Action" accept=".xlsx,.xls"
                                    :file-list="fileList" :show-file-list="false" :http-request="uploadFile" :on-change="changeFile">
                                    <el-button size="small" type="primary">
                                        <i class="el-icon-upload2"></i>上传文件
                                    </el-button>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </div>
            </el-form>
        </div>
        <div class="blank"></div>
        <div class="nav bottom">
            <el-row>
                <div class="wrapper">
                    <div class="wrapper_left">
                        <el-col :span="1.5">
                            <!-- <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete> -->
                        </el-col>
                    </div>
                    <div class="wrapper_right">
                        <div class="wrapper-right-keep">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonKeep" :size="size" :fns="saveDraft"></button-common>
                            </el-col>
                        </div>
                        <div>
                            <el-col :span="1.5">
                                <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
                            </el-col>
                        </div>
                    </div>
                </div>
            </el-row>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
    export default {
        components: { buttonOrange, buttonCommon, buttonDelete },
        data() {
            return {
                fkxx: '调试',
                fileList: [],
                size: 'mini',  //按钮大小
                buttonDel: '删 除',
                buttonKeep: '存草稿',
                buttonCommit: '提 交',
            }
        },
        methods: {
            // 文件上传
            uploadFile() {

            },

            // 文件上传改变
            changeFile() {

            },
            // 存草稿
            saveDraft() {

            },
            // 提交
            proving() {

            },
        }
    }
</script>

<style scoped>
    .paltForm-container {
        margin: 15px 15px 0 15px;
    }

    .paltForm-message {
        background-color: #ffffff;
    }

    .paltForm-message-title p {
        color: #909399;
        font-size: 14px;
        padding: 10px 10px 0 10px;
    }

    .platform-content {
        background-color: #ffffff;
        margin-top: 15px;
        padding-bottom: 40px;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 150px;
        height: 24px;
        font-size: 16px;
        font-weight: 600;
        color: rgba(38, 38, 38, 1);
        line-height: 24px;
        display: inline-block;
    }

    .nav .wrapper {
        margin-left: 25px;
        margin-right: 25px;
        display: flex;
        justify-content: space-between;
    }

    .wrapper_left,
    .wrapper_right {
        display: flex;
        align-items: center;
    }

    .wrapper-right-keep {
        margin-right: 10px;
    }

    .bottom {
        margin-top: 20px;
        padding: 15px 0;
    }

    .blank {
        height: calc(100vh - 739px);
    }

    .fknr {
        width: 100%;
    }
</style>

<style>
    .paltForm-container .el-form-item__label {
        font-weight: 500;
    }


    .fknr .el-form-item__content {
        width: 93%;
    }

    /* 上传文件按钮 */
    .againUpLoad .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }

    .againUpLoad .el-upload--picture-card:hover {
        color: rgba(251, 164, 47, 1);
    }

    .againUpLoad .el-upload:focus {
        color: rgba(251, 164, 47, 1);
    }
</style>