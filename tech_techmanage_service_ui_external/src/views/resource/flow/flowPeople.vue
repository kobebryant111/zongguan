<!-- 流程通知管理 -->
<template>
    <div class="app-container">
        <el-form ref="form" :model="form" label-width="100px">
            <el-input v-model="form.nodeId" class="hidden"></el-input>
            <!--节点id-->
            <el-input v-model="form.nodeName" class="hidden"></el-input>
            <!--节点名称-->
            <el-input v-model="form.userId" class="hidden"></el-input>
            <!--人员id-->
            <el-input v-model="form.userName" class="hidden"></el-input>
            <!--人员名称-->

            <el-form-item label="系统部确认">
                <el-tag size="medium" v-for="tag in tags1 = formData.filter((item)=>{
                    return item.NODEID == 'manualActivity1'
                    })" :key="tag.USERID" closable :type="tag.USERID" @close="handleClose(tag)">
                    {{tag.USERNAME}}
                </el-tag>
                <el-button class="button-new-tag" @click="showUserTree(1)" icon="el-icon-edit" size="mini">增加</el-button>
            </el-form-item>
            <el-form-item label="架构部确认">
                <el-tag size="medium" v-for="tag in tags2 = formData.filter((item)=>{
                    return item.NODEID == 'manualActivity2'
                    })" :key="tag.USERID" closable :type="tag.USERID" @close="handleClose(tag)">
                    {{tag.USERNAME}}
                </el-tag>
                <el-button class="button-new-tag" @click="showUserTree(2)" icon="el-icon-edit" size="mini">增加</el-button>
            </el-form-item>
            <el-form-item label="综合部审批">
                <el-tag size="medium" v-for="tag in tags3 = formData.filter((item)=>{
                    return item.NODEID == 'manualActivity3'
                    })" :key="tag.USERID" closable :type="tag.USERID" @close="handleClose(tag)">
                    {{tag.USERNAME}}
                </el-tag>
                <el-button class="button-new-tag" @click="showUserTree(3)" icon="el-icon-edit" size="mini">增加</el-button>
            </el-form-item>
            <el-form-item label="测试部审批">
                <el-tag size="medium" v-for="tag in tags5 = formData.filter((item)=>{
                    return item.NODEID == 'manualActivity5'
                    })" :key="tag.USERID" closable :type="tag.USERID" @close="handleClose(tag)">
                    {{tag.USERNAME}}
                </el-tag>
                <el-button class="button-new-tag" @click="showUserTree(5)" icon="el-icon-edit" size="mini">增加</el-button>
            </el-form-item>
        </el-form>
        <userSelect ref="userSelect"></userSelect>
    </div>
</template>
<script>
import common from '@/api/common'
import { queryFlowPeopleList, insertFlowPeople, delFlowPeople } from '@/api/resource/flow/resourceFlowPeople'
import userSelect from '@/views/resource/flow/userSelect'
export default {
    components: { userSelect },
    data() {
        return {
            tableHeight: (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 205,
            treeFlag: '', //区分节点
            formData: [], //增添人员
            form: {
                nodeId: '',
                nodeName: '',
                userId: '',
                userName: '',
                manualActivity1: '',
                manualActivity2: '',
                manualActivity3: '',
                manualActivity5: ''
            },
            tags1: [],
            tags2: [],
            tags3: [],
            tags5: []
        }
    },
    created() {},
    mounted() {
        this.loadInit()
    },
    methods: {
        loadInit() {
            queryFlowPeopleList().then((res) => {
                if (res.data.status != 200) {
                    this.$message.error(res.data.message)
                    return false
                } else {
                    console.log(res.data)
                    this.formData = res.data.results
                }
            })
        },

        /**
         * 显示用户树
         */
        showUserTree(nodeid) {
            this.treeFlag = nodeid
            this.$refs.userSelect.showUserTree(1)
        },

        saveAuditUser(userNo, userName) {
            if (this.treeFlag == '1') {
                for (let i = 0; i < this.tags1.length; i++) {
                    if (this.tags1[i].USERID.indexOf(userNo) == '0') {
                        this.$alert('当前配置人员已存在', { type: 'error' })
                        return
                    }
                }
                this.form.nodeId = 'manualActivity1'
                this.form.nodeName = '系统部确认'
            } else if (this.treeFlag == '2') {
                for (let i = 0; i < this.tags2.length; i++) {
                    if (this.tags2[i].USERID.indexOf(userNo) == '0') {
                        this.$alert('当前配置人员已存在', { type: 'error' })
                        return
                    }
                }
                this.form.nodeId = 'manualActivity2'
                this.form.nodeName = '架构部确认'
            } else if (this.treeFlag == '3') {
                for (let i = 0; i < this.tags3.length; i++) {
                    if (this.tags3[i].USERID.indexOf(userNo) == '0') {
                        this.$alert('当前配置人员已存在', { type: 'error' })
                        return
                    }
                }
                this.form.nodeId = 'manualActivity3'
                this.form.nodeName = '综合部审批'
            } else if (this.treeFlag == '5') {
                for (let i = 0; i < this.tags5.length; i++) {
                    if (this.tags5[i].USERID.indexOf(userNo) == '0') {
                        this.$alert('当前配置人员已存在', { type: 'error' })
                        return
                    }
                }
                this.form.nodeId = 'manualActivity5'
                this.form.nodeName = '测试部审批'
            }
            this.form.userId = userNo
            if (userName.length > 0) {
                this.form.userName = userName.substring(0, userName.indexOf(')') + 1)
            }
            this.saveRy()
        },
        saveRy() {
            console.log(this.form)
            insertFlowPeople(this.form).then((res) => {
                if (res.data.status != 200) {
                    this.$alert(res.data.message, { type: 'error' })
                } else {
                    this.$message.success(res.data.message)
                    this.loadInit()
                }
            })
        },

        /**
         * 刪除
         */
        handleClose(tag) {
            this.$confirm('确定删除此人员吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then((res) => {
                console.log(tag)
                let param = {}
                param.id = tag.ID
                let data = common.paramsFormat(param)
                delFlowPeople(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$message.error(res.data.message)
                        return false
                    } else {
                        this.$message.success(res.data.message)
                        this.loadInit()
                    }
                })
            })
        }
    }
}
</script>
<style lang="scss">
.el-form-item {
    margin-bottom: 5px;
}
.el-tag + .el-tag {
    margin-left: 10px;
}
.button-new-tag {
    margin-left: 10px;
}
.contentMain .row_require {
    background-color: #ffffff;
    /* padding: 0 20px 15px 20px; */
    margin-top: 15px;
}
.hidden {
    display: none;
}
</style>