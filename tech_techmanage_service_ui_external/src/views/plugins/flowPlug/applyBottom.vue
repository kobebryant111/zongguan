<!-- 发起申请底部栏 -->
<template>
    <div class="apply-bottom">
        <div class="apply-bottom-left">
            <button-delete :buttonName="buttonDel" :size="size" :fns="delBox"></button-delete>
        </div>
        <div class="apply-bottom-right">
            <button-common :buttonName="buttonKeep" :size="size" :fns="saveDraft"></button-common>
            <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
        </div>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange'
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import buttonDelete from '@/components/eleButton/delete'
    import { doDel } from '@/api/common/flowApi'
    import common from "@/api/common";
    export default {
        components: { buttonOrange, buttonCommon, buttonDelete },
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
                buttonDel: '删 除',
                buttonKeep: '存草稿',
                buttonCommit: '提 交',
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
            delBox() {
                this.$confirm('确认删除该流程吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.delPro();
                }).catch(error => {
                    this.$message.error(error);
                })
            },

            delPro() {
                const loading = common.loading(this)
                let params = {};
                params.processInstID = this.flowItem.processInstID;
                params.workItemID = this.flowItem.workItemID;
                params.processDefName = this.flowItem.processDefName;
                let data = common.paramsFormat(params);
                doDel(data).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        this.$message.success('流程删除成功');
                        this.$router.back();
                    } else {
                        this.$message.error('流程删除失败');
                    }

                });
            },

            // 消息提示(多节点)
            messageNotifyMore(messageVal) {
                if (messageVal.length > 3) {
                    messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(")")[0] + ')';
                }
                let messageSec = messageVal.slice(2).join("，");
                this.$notify({
                    title: '提示',
                    dangerouslyUseHTMLString: true,
                    message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
                    duration: 0
                })
            },

        }
    };
</script>

<style scoped>
    .nav-bottom {
        display: flex;
        justify-content: space-between;
        padding: 15px 15px;
        width: 100%;
        background-color: #ffffff;
    }
</style>