<!-- 流程通知管理 -->
<template>
    <div class="app-container">
        <el-form ref="form" :model="form" label-width="120px">
            <el-input v-model="form.nodeId" class="hidden"></el-input>
            <!--节点id-->
            <el-input v-model="form.nodeName" class="hidden"></el-input>
            <!--节点名称-->
            <el-input v-model="form.userId" class="hidden"></el-input>
            <!--人员id-->
            <el-input v-model="form.userName" class="hidden"></el-input>
            <!--人员名称-->
            <el-form-item label="二级部经理审批" v-if="false">
                <el-tag size="medium" v-for="tag in tags6 = formData.filter((item)=>{
                    return item.NODEID == 'ejbmanage'
                    })" :key="tag.USERID" closable :type="tag.USERID" @close="handleClose(tag)">
                    {{tag.USERNAME}}
                </el-tag>
                <el-button class="button-new-tag" @click="showUserTree(6)" icon="el-icon-edit" size="mini">增加</el-button>
            </el-form-item>
            <el-form-item label="配置管理岗审批">
                <el-tag size="medium" v-for="tag in tags7 = formData.filter((item)=>{
                    return item.NODEID == 'configmanager'
                    })" :key="tag.USERID" closable :type="tag.USERID" @close="handleClose(tag)">
                    {{tag.USERNAME}}
                </el-tag>
                <el-button class="button-new-tag" @click="showUserTree(7)" icon="el-icon-edit" size="mini">增加</el-button>
            </el-form-item>
            <el-form-item label="系统管理员审批" v-if="false">
                <el-tag size="medium" v-for="tag in tags8 = formData.filter((item)=>{
                    return item.NODEID == 'sysmanager'
                    })" :key="tag.USERID" closable :type="tag.USERID" @close="handleClose(tag)">
                    {{tag.USERNAME}}
                </el-tag>
                <el-button class="button-new-tag" @click="showUserTree(8)" icon="el-icon-edit" size="mini">增加</el-button>
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
                ejbmanage: '',
                configmanager: '',
                sysmanager: ''
            },
            tags6: [],
            tags7: [],
            tags8: []
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
            if (this.treeFlag == '6') {
                for (let i = 0; i < this.tags6.length; i++) {
                    if (this.tags6[i].USERID.indexOf(userNo) == '0') {
                        this.$alert('当前配置人员已存在', { type: 'error' })
                        return
                    }
                }
                this.form.nodeId = 'ejbmanage'
                this.form.nodeName = '二级部经理审批'
            } else if (this.treeFlag == '7') {
                for (let i = 0; i < this.tags7.length; i++) {
                    if (this.tags7[i].USERID.indexOf(userNo) == '0') {
                        this.$alert('当前配置人员已存在', { type: 'error' })
                        return
                    }
                }
                this.form.nodeId = 'configmanager'
                this.form.nodeName = '配置管理岗审批'
            } else if (this.treeFlag == '8') {
                for (let i = 0; i < this.tags8.length; i++) {
                    if (this.tags8[i].USERID.indexOf(userNo) == '0') {
                        this.$alert('当前配置人员已存在', { type: 'error' })
                        return
                    }
                }
                this.form.nodeId = 'sysmanager'
                this.form.nodeName = '系统管理员审批'
            }
            this.form.userId = userNo
            if (userName.length > 0) {
                this.form.userName = userName.substring(0, userName.indexOf(')') + 1)
            }
            this.saveRy()
        },
        saveRy() {
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