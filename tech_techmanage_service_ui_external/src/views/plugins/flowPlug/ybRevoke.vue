<template>
    <div class="approveBottom">
        <div class="revokeBottoms">
            <button-common :buttonName="revokeName" :size="size" :fns="revoke"></button-common>
        </div>
    </div>
</template>
<script>
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import { drawback, isDrawback } from '@/api/common/flowApi'
    import common from "@/api/common";
    // import { Loading } from 'element-ui';
    // let loading;
    export default {
        components: { buttonCommon },
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
                isDrawback({
                    workItemId: this.flowItem.activityInstID,
                    processInstId: this.flowItem.processInstID
                }).then(res => {
                    loading.close();
                    console.log("撤回", res.data);
                    if (res.data.status == 200) {
                    if (res.data.results.falg) {
                        const loading = common.loading(this)
                        let params = this.flowItem;
                        params.conclusion = "04";
                        params.drawbackId = res.data.results.drawbackId;
                        params.applydesc = this.flowItem.activityInstName;
                        let data = common.paramsFormat(params);
                        drawback(data).then(res => {
                            loading.close();
                            if (res.data.status == 200) {
                                let message = res.data.message;
                                this.messageNote = message;
                                this.$nextTick(() => {
                                    this.messageNotify();
                                    // this.$router.back();
                                    this.$router.push({ name: 'dashboard' });
                                })

                            } else {
                                this.errorMessage = '操作失败'
                                this.messageError();
                            }
                        }).catch(error => {
                            this.$message.error(error);
                        });
                    } else {
                        loading.close();
                        this.$message.error('流程不能撤回');
                    }
                } else {
                    this.$message.error(res.data.message);
                }
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
    .approveBottom {
        overflow: hidden;
    }

    .revokeBottoms {
        width: 100%;
        background-color: #ffffff;
        padding: 15px 25px;
    }
</style>