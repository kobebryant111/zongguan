<!-- 自定义内容表格提示组件 -->
<template>
    <div>
        <span>
            <slot>{{name}}</slot>
        </span>

        <el-tooltip :placement="placement">
            <i class="el-icon-question" style="cursor: pointer;margin-left: 6px;"></i>
            <div slot="content">
                人员岗位划分规则：</br>
                (1)开发岗包括：Java、C、数据库、前端、AS400、安卓、IOS、脚本开发、应用运维等岗位；</br>
                (2)运维岗包括：各部门的系统运维、审计后督，以及操作岗包括一线监控、服务台等岗位；</br>
                (3)测试岗包括：功能测试、性能测试、自动化测试、QA等岗位；</br>
                (4)文员岗包括：综合部派驻至各二级部的文员。</br>
                其它无法分类至上述四个分类的岗位，由具体人员单价对应的人力框架决定。</br>
                人力外包准入标准详见附件：<button class="rlwbzrFj" @click="fjDownload">下载附件</button>
            </div>
        </el-tooltip>
    </div>

</template>
<script>
    import common from '@/api/common';
    import { getDownloadUrl } from '@/api/common/flowApi';
    export default {
        data() {
            return {

            }
        },
        props: {
            placement: {
                default: 'top'
            },
            name: String
        },
        methods: {
            fjDownload() {
                getDownloadUrl({key: 'PPM_NEW_DOCUMENT'}).then(res => {
                    console.log("附件下载", res.data)
                    if (res.data.status ==200) {
                        const self = this;
                        common.downloadJl(res.data.results.documentno, self);
                    } else {
                        this.$message.error("获取下载地址失败");
                    }
                })
                
            }
        }
    }
</script>
<style scoped>
    .rlwbzrFj {
        cursor: pointer;
    }
</style>