<template>
    <div class="approveBottom">
        <div class="revokeBottoms">
            <button-delete :buttonName="revokeName" :size="size" :fns="delBox"></button-delete>
        </div>
    </div>
</template>
<script>
    import buttonDelete from '@/components/eleButton/delete'
    import { doDel } from '@/api/common/workflowApi'
    import common from "@/api/common";
    export default {
        components: { buttonDelete },
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
                revokeName: '删 除',
            };
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

            // 删除流程
            delPro() {
                let params = {};
                params.processInstId = this.flowItem.instanceid;
                let data = common.paramsFormat(params);
                doDel(data).then(res => {
                    if (res.data.status == 200) {
                        this.$message.success('流程删除成功');
                        this.$router.push({ name: 'dashboard' });
                    } else {
                        this.$message.error('流程删除失败');
                    }

                });
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