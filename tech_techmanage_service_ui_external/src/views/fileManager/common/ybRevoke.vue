<template>
    <div class="approveBottom">
        <div class="revokeBottoms">
            <button-common :buttonName="revokeName" :size="size" :fns="fns"></button-common>
        </div>
    </div>
</template>
<script>
    import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
    import { drawback } from '@/api/common/workflowApi'
    import {  updateFileStatus } from "@/api/supplierManager/fileManage";
    import common from "@/api/common"; 
    export default {
        components: { buttonCommon },
        props: {
            flowItem: {
                type: Object,
                default: {}
            },
            fns: {
                type: Function
            }
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
        methods: {
            revoke() {
                let params = {}; 
                const loading = common.loading(this)
                params.processInstId = this.flowItem.instanceid || this.flowItem.processInstanceId;
                params.conclusion = "04";
                let data = common.paramsFormat(params);
                drawback(data).then(res => {
                    loading.close();
                    console.log(res,'res')
                    if (res.data.status == 200) {
                        console.log('88888877')
                        const data = {
                            instanceId:this.flowItem.instanceid || this.flowItem.processInstanceId,
                            isDel: '1'
                        }
                        updateFileStatus(data).then((res)=>{
                            console.log(res)
                        });
                        let message = res.data.message;
                        this.messageNote = message;
                        this.$nextTick(() => {
                            this.messageNotify();
                            this.$router.push({ name: 'dashboard' });
                        });
                    } else {
                        this.errorMessage = '操作失败'
                        this.messageError();
                    }
                }).catch(error => {
                    this.$message.error(error);
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
            reBackTask(id){
                const data  = {
                    processInstId: id
                }
                reBackTask(data).then((res)=>{
                   console.log(res,'res')
                })
            }
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