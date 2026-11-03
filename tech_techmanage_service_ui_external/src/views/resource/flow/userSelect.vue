<!-- 资源需求审批 -->
<template>
    <div class="rlxqContent rlxqContentApprove">
        <!-- 用户树 -->
        <el-dialog title="流程审批人" :visible.sync="treeVisible" width="30%" @close="treeDialogClose" v-if="treeVisible">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <el-input v-model="searchUser" placeholder="员工号/员工名称/员工名称拼音"></el-input>
                        </td>
                        <td>
                            <el-button type="primary" size="mini" @click="searchUserBtn">搜索</el-button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="tree">
                <el-tree :data="treeData" :props="defaultProps" lazy show-change node-key="id" check-strictly :load="loadnode" @node-click="nodeClick"></el-tree>
            </div>
            <span slot="footer">
                <el-button @click="treeDialogClose" size="mini">取 消</el-button>
                <el-button type="primary" @click="treeUserBtn" size="mini">确 定</el-button>
            </span>
        </el-dialog>
    </div>

</template>
<script>
import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
import { queryDetail, twoManagerAudit, confirm } from '@/api/integrated/xqgl'
import { getDicEntry } from '@/api/eosDictEntry'
import common from '@/api/common'
import helpHint from '@/components/helpHint'
import flowLogs from '@/views/plugins/flowPlug/FlowLogs'
import approveBottom from '@/views/plugins/flowPlug/approveBottom'
import { userTreeList } from '@/api/systemApi/relateUser'

export default {
    name: 'RequireApprove',
    components: { buttonCommon, helpHint, flowLogs, approveBottom },
    data() {
        return {
            dialogVisible: false, // dialog 默认显示隐藏
            treeVisible: false, // dialog 默认显示隐藏
            buttonClose: '关 闭', //关闭按钮名字
            size: 'mini', //按钮大小
            peoForm_options: [], //人员类型形式
            connect_options: [], //联系招聘下拉内容
            successMessage: '', // 成功消息提示
            errorMessage: '', //失败消息提示
            instanceid: '', //流程ID
            workItemID: '', //工作项id
            processDefName: '', //流程定义名称
            processInstName: '', //流程实例名称
            activityInstName: '', //审批节点描述
            activityInstId: '', //当前活动实例ID
            peoRequireDetail: {}, //需求详情
            headline: '', //技评标题
            url: '', //url
            prepaymentno: '', //预算编号

            id: '',
            activityDefID: '', //当前节点ID
            flowItem: {}, //bps数据
            formData: {
                id: '',
                userno: '',
                relateUserno: '',
                userName: '',
                realteUserName: ''
            }, // 表单
            formDataTemp: {
                id: '',
                userno: '',
                relateUserno: '',
                userName: '',
                realteUserName: ''
            }, // 临时表单
            // ============ 用户树
            treeData: [], // 树对象数据
            defaultProps: {
                // 树对象属性对应关系
                children: 'children',
                label: 'text'
            },
            tag: 0, // 标记当前点击的是 1：科技管理平台 还是:2：OA
            searchUser: '' // 用户树搜索用户的条件
        }
    },

    mounted() {
    },

    methods: {
        /**
         * 用户树dialog 关闭
         */
        treeDialogClose() {
            let self = this
            self.treeVisible = false
            self.searchUser = ''
        }, // end

        /**
         * 用户树-确定事件
         */
        treeUserBtn() {
            let self = this
            var userno = self.formDataTemp.userno || self.formData.userno
            var username= self.formDataTemp.userName || self.formData.userName
            this.$parent.saveAuditUser(userno,username);
            self.treeVisible = false
            self.searchUser = ''
        }, // end

        /**
         * 显示用户树
         */
        showUserTree(index) {
            this.treeVisible = true
            this.tag = index
        }, // end
        /**
         * 加载节点
         */
        loadnode(node, resolve) {
            // 如果展开第一级节点， 从后台加载一级节点列表
            if (node.level === 0) {
                this.loadFirstNode(resolve)
            }
            // 如果展开节第二节点，从treeData数据直接取children
            if (node.level === 1) {
                if (undefined != this.treeData[0].children) {
                    resolve(this.treeData[0].children)
                } else {
                    resolve([])
                }
            }
            // 如果展开其他节点， 动态从后台加载下一级节点列表
            if (node.level > 1) {
                this.loadChildNode(node, resolve)
            }
        }, // end
        /**
         * 第一次加载节点
         */
        loadFirstNode(resolve) {
            userTreeList().then((res) => {
                this.treeData = res.data.results
                console.log('treeData:', this.treeData)
            })
        }, // end
        /**
         *  刷新树组件
         */
        refreshTree() {
            let self = this
            userTreeList().then((res) => {
                this.treeData = res.data.results
            })
        }, // end
        /**
         * 加载节点的子节点集合
         */
        loadChildNode(node, resolve) {
            console.log('loadChildNode:node', node, 'resolve', resolve)
            let params = node.data.attr.split('-')
            console.log('params', params)
            if (params.length < 3) {
                resolve([])
                return false
            }
            let data = {}
            data.id = params[0]
            data.current = params[1]
            data.next = params[2]
            userTreeList(data).then((res) => {
                resolve(res.data.results)
            })
        }, // end
        /**
         * 点击节点上触发事件，传递三个参数，数据对象使用一个参数
         */
        nodeClick(data, dataObj, self1) {
            console.log('nodeClick::', data, dataObj, self1)
            let self = this
            if (data.attr !== 'emp') {
                return false
            }
            if (self.tag === 1) {
                self.formDataTemp.userno = data.id
                self.formDataTemp.userName = data.text
            } else {
                self.formDataTemp.relateUserno = data.id
                self.formDataTemp.realteUserName = data.text
            }
        }, // end
        /**
         * 搜索用户树列表
         */
        searchUserBtn() {
            let self = this
            if (self.searchUser == null || self.searchUser.length <= 0) {
                this.refreshTree();
                return false
            }
            userTreeList({ searchUser: self.searchUser }).then((res) => {
                if (res.data.status !== 200) {
                    self.$alert(res.data.message, { type: 'error' })
                    return false
                }
                self.treeData = res.data.results
            })
        }, // end

        // 查询业务字典
        getDic() {
            getDicEntry({
                dicttypeid: 'REQUIRE_PEOPLE_TYPE,WHETHER_CONTACT_RECRUITMENT'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //人员类型
                this.peoForm_options = data.filter((item) => {
                    return item.dicttypeid == 'REQUIRE_PEOPLE_TYPE'
                })

                return data
            })
        },

        // 关闭按钮
        close() {
            this.$router.push({ name: 'dashboard' })
        },

        // 成功消息提示
        saveSuccess() {
            this.$message({
                showClose: true,
                message: this.successMessage,
                type: 'success'
            })
        },

        // 失败消息提示
        saveError() {
            this.$message({
                showClose: true,
                message: this.errorMessage,
                type: 'error'
            })
        }
    }
}
</script>
<style scoped>
.tree {
    height: 300px;
    display:block;
    overflow-y:scroll;
}

</style>
