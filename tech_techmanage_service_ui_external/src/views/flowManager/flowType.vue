<!-- 流程调度管理员维护 -->
<template>
    <div>
        <table-tree :data="fData" :columns="tableColumns" :expandAll="true" fit border>
        <el-table-column prop="username" label="流程管理员"></el-table-column>
        <el-table-column  label="操作" width="230" >
           <template slot-scope="scope">
               <el-button type="primary" size="mini" @click="addFlow(scope.row)" v-if="scope.row.parentid == '0' || scope.row.parentid == '1'">添加</el-button>
                <el-button type="warning" size="mini" @click="updateFlow(scope.row)" v-if="scope.row.parentid != '1'">修改</el-button>
                <el-button type="danger" size="mini" @click="deleteConfirmTip(scope.row)" v-if="scope.row.parentid != '1'">删除</el-button>
           </template>
        </el-table-column>
        </table-tree>
        <!-- dialog -->
        <div>
            <template>
                <el-dialog :title="title" :visible.sync="dialogClose" width="30%">
                    <el-form ref="flowForm" :model="flowForm">
                        <table border="0" style="margin:auto;width:100%">
                            <tr>
                                <td class="td-right">流程分类：</td>
                                <td class="td-left">
                                    <el-input v-model="flowForm.parentName" disabled/>
                                </td>
                            </tr>
                            <tr>
                                <td class="td-right">流程名称：</td>
                                <td class="td-left">
                                    <el-input v-model="flowForm.flowname" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td-right">流程编号：</td>
                                <td class="td-left">
                                    <el-input v-model="flowForm.flowdraftno" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td-right">流程管理员：</td>
                                <td class="td-left">
                                    <el-input v-model="flowForm.username" @focus="showUserDialog"/>
                                </td>
                            </tr>
                        </table>
                    </el-form>
                    <div class="dialog-error-div">
                        <samp class="dialog-error">{{error}}</samp>
                    </div>
                    <div class="dialog-footer">
                        <el-button @click="dialogCancel">取消</el-button>
                        <el-button type="primary" @click="dialogAddSubmit">确定</el-button>
                    </div>
                </el-dialog>
                <!-- dialog begin -->
               <div class="dialog-user-container-div">
                <template>
                    <el-dialog title="用户列表" :visible.sync="dialogVisible" width="85%" top="10vh">
                    <employee :roleData="dialogUserData"   ref="employee" ></employee>
                    <div>
                        <el-button type="primary" @click="selectedUser">确定</el-button>
                        <el-button @click="dialogCancelData">取消</el-button>
                    </div>
                    </el-dialog>
                </template>
                </div>
            </template>
        </div>
    </div>
</template>
<script>
import TableTree from '@/components/TableTree';
import request from '@/utils/request';
import common from '@/api/common';
import employee from "./common/Employee";

export default {
    components : {
        TableTree,
        employee
    },
    data(){
        return {
            title : '',             // dialog 标题
            fData : [],
            tableColumns : [{
                text: '流程列表',
                value: 'flowname',
                width : "250"
            }],
            dialogClose : false,         //dialog 显示
            flowForm : {                // 表单
                flowname : '',          // 流程名称
                flowdraftno : '',       // 流程id
                parentid : '',          // 上级id
                userid : '',            // 流程管理员id
                username: '',           // 流程管理员名称                              
            }, 
           
            dialogUserData: [], // dialog 用户数据
            dialogVisible: false, // dialog 显示 默认不显示 
            error : '',                 // 错误提示     
        }
    },
    mounted() {
        this.init();
    },
    methods:{
        // 页面初始化
        init: function(){
            request({
                url : '/ppmFlowType/loadFlow',
                method : 'get',
                data:{}
            }).then(res=>{
                if(res.data.status != 200) {
                    this.$alter(res.data.message,{type:'error'});
                    return false;
                }
                this.fData = res.data.results;
            }),
            // dialog 用户数据
            request({
                url: "/commOrganization/queryOrgTree",
                method: "get"
            }).then(res => {
                console.log("dsdsdfadfadf", res.data.results)
                this.dialogUserData = res.data.results;
            });
        },      // 页面初始化 end
        /**
         * 添加菜单
         */
        addFlow :function(item) {
            this.title = "添加流程"
            this.dialogClose = true
            this.flowForm.parentid = item.flowdraftno;
            this.flowForm.parentName = item.flowname;
            this.flowForm.flowdraftno = "";
            this.flowForm.username = "";
            this.flowForm.flowname = "";
           
        },          // 添加菜单 end
        /**
         * 添加菜单
         */
        updateFlow : function(item) {
            this.title = "更新流程"
            this.dialogClose = true
            this.flowForm.parentName = item.parent.flowname;
            this.flowForm.flowdraftno = item.flowdraftno
            this.flowForm.flowname = item.flowname
            this.flowForm.userid = item.userid
            this.flowForm.username = item.username
            
            this.flowForm.id = item.id
            
            this.flowForm.parentid = item.parentid
        },          // 添加菜单 end
        /**
         * 显示用户信息
         */
        showUserDialog: function() {
            this.dialogVisible = true;
            var username = this.flowForm.username;
            if(username !=null && username !=""){
                setTimeout(()=>{
                    this.$refs.employee.setSelectData(username);
                },10)
            }else{
                this.$refs.employee.employSelectData = [];
            }
            
            
        }, // 显示用户信息 end
        
        /**
         * 选择dialog 用户
         */
        selectedUser: function() {
           var employSelectData = this.$refs.employee.employSelectData;
           var username ="";
           var userid ="";
           for(var i=0;i<employSelectData.length;i++){
               username += employSelectData[i].empname+"("+employSelectData[i].badge+"),";
               userid += employSelectData[i].badge+",";
            }
            if(username.indexOf(",")>-1){
                username = username.substring(0,username.length-1);
            }
            if(userid.indexOf(",")>-1){
                userid = userid.substring(0,userid.length-1);
            }
            this.flowForm.userid = userid;
            this.flowForm.username = username;
            this.dialogCancelData();
        },// 选择dialog 用户 end
        /**
         *
         */
        dialogCancelData: function() {
        this.dialogVisible = false;
        this.$refs.employee.employSelectData = [];
        
        let userForm = this.$refs.employee.paramForm;
        Object.keys(userForm).forEach(key => {
            userForm[key] = "";
        });
        this.init();
        },
        
        /**
         * 删除
         */
        deleteConfirmTip : function(item) {
            this.$confirm('确认删除当前流程吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                 request({
                    url : '/ppmFlowType/delete',
                    method : 'delete',
                    params:{ id: item.id, parentid:item.parentid}
                }).then(res=>{
                    if(res.data.status != 200) {
                        this.$alert(res.data.message,{type:'error'});
                        return false;
                    } else {
                        this.$alert(res.data.message,{type:'success'});
                        this.init();
                    }
                });
                //this.deleteFlow(row);
            }).catch(() => {})
        },           // 删除提示 end
        /**
         *  提交保存
         */
        dialogAddSubmit : function() {
            let falg = this.vaildateDialogForm(this.flowForm);
            if(!falg) {
                return false;
            }
            this.error = '';
            let datas = common.paramsFormat(this.flowForm);
            request({
                url :'/ppmFlowType/update',
                method : 'post',
                data : datas
            }).then(res=> {
                if(res.data.status != 200) {
                    this.$alert(res.data.message,{type:'error'});
                } else {
                    this.dialogCancel()
                    this.$message({
                        type : 'success',
                        message : res.data.message
                    })
                    this.init();
                }
            })
        },          // 提交保存 end
        /**
         * 关闭dialog
         */
        dialogCancel : function(){
            this.dialogClose = false;
            Object.keys(this.flowForm).forEach(key => {this.flowForm[key] = ''});
            this.error = '';
        },              // 关闭dialog end
        /**
         * 验证form表单
         */
        vaildateDialogForm : function(object) {
            if(!object.flowname) {
                this.error = '* 提示：流程名称不能为空'
                return false;
            }
            if(!object.flowdraftno) {
                this.error = '* 提示：流程编号不能为空'
                return fasle;
            }
            return true;
        },          // 验证form表单 end
    }
}
</script>

