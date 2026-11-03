<template>
    <div class="approveBottom">
        <div class="revokeBottoms">
            <button-orange :buttonName="revokeName" :size="size" :fns="revoke"></button-orange>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件
    import { drawback, isDrawback } from '@/api/common/flowApi'
    import common from "@/api/common";
    // import { Loading } from 'element-ui';
    // let loading;
    export default {
        components: { buttonOrange },
        props: {
            // flowItem: {
            //     type: Object,
            //     default: {}
            // },
        },
        data() {
            return {
                item: this.flowItem, //父组件传来的数据
                size: 'mini',  //按钮大小
                revokeName: '提 交',
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
    .approveBottom {
        background-color: #ffffff;
        overflow: hidden;
    }

    .revokeBottoms {
        padding: 15px 25px;
        float: right;
    }
</style>