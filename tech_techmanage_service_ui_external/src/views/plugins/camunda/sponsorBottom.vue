<!-- 发起页底部操作栏 -->
<template>
    <div class="sponsorBottom">
        <div class="sponsorBottom-left">
            <button-common :buttonName="revokeName" :size="size" :fns="revoke"></button-common>
        </div>
        <div>
            <!-- <button-delete :buttonName="liuzhuanName" :size="size" :fns="openLiuzhuanDialog"></button-delete>
            <button-orange :buttonName="liuzhuanName" :size="size" :fns="openLiuzhuanDialog"></button-orange> -->
        </div>
        
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' 
    import buttonCommon from '@/components/eleButton/buttonCommon' 
    import buttonDelete from '@/components/eleButton/delete' 
    import { drawback, isDrawback } from '@/api/common/workflowApi'
    import common from "@/api/common";
    // import { Loading } from 'element-ui';
    // let loading;
    export default {
        components: { buttonCommon, buttonOrange, buttonDelete },
        props: {
            flowItem: {
                type: Object,
                default: {}
            },
        },
        data() {
            return {
                item: this.flowItem, //父组件传来的数据
                size: 'mini',  //按钮大小
                revokeName: '撤 回',
                successMessage: '', // 成功消息提示
                errorMessage: '', //失败消息提示
                messageNote: '', //新版消息提示

            };
        },
        created() {

        },
        mounted() {
            console.log('params', this.item);

        },
        computed: {

        },
        methods: {
            revoke() {
                const loading = common.loading(this)
                // this.startLoading();
                isDrawback({
                    workItemId: this.flowItem.activityInstID,
                    processInstId: this.flowItem.processInstID
                }).then(res => {
                    loading.close();
                    // this.drawbackState = res.data.results.falg;
                    console.log("btn111", res.data.results);

                    let params = this.flowItem;
                    params.conclusion = "04";
                    params.workItemID = res.data.results.workItemId;
                    params.applydesc = this.flowItem.activityInstName;
                    let data = common.paramsFormat(params);
                    drawback(data).then(res => {
                        console.log(res.data);
                        if (res.data.status == 200) {

                            let message = res.data.message;
                            console.log('message', message);
                            this.messageNote = message;
                            this.$nextTick(() => {
                                this.messageNotify();
                                this.$router.back();
                            })

                        } else {
                            this.errorMessage = '操作失败'
                            this.messageError();
                        }
                    })
                });


            },


            // 加载函数
            // startLoading() {
            //     loading = Loading.service({
            //         lock: true,
            //         text: '加载中',
            //         spinner: 'el-icon-loading',
            //         background: 'rgba(0, 0, 0, 0.2)'
            //     })
            // },

            //消息提示
            messageNotify() {
                this.$notify({
                    title: '提示',
                    message: this.messageNote,
                    duration: 0
                })
            },

            // 成功消息提示
            messageSuccess() {
                this.$message({
                    message: this.successMessage,
                    type: 'success'
                })
            },

            // 失败消息提示
            messageError() {
                this.$message.error(this.errorMessage);
            },

        }
    };
</script>

<style scoped>
    .sponsorBottom {
        overflow: hidden;
    }

    /* .revokeBottoms {
        width: 100%;
        background-color: #ffffff;
        padding: 15px 25px;
    } */


</style>