<template>
  <div class="app-container">
    <el-tabs tab-position="left">
      <el-tab-pane label="安全设置">
        <h2>安全设置</h2>
        <div class="row">
          <div class="meta">
            <p class="title">账户密码</p>
            <p class="desc">设置高强度密码防止账号被他们使用</p>
          </div>
          <div class="action">
            <el-button type="text" @click="mdVisable = true">修改</el-button>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="预留"><h2>预留</h2></el-tab-pane>
    </el-tabs>
   

    <!-- dialog -->
    <el-dialog title="修改密码" :visible.sync="mdVisable">
      <el-form :model="passwordForm" label-width="120px" label-position="right" :rules="rules" ref="passwordForm">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input 
            v-model="passwordForm.oldPassword" 
            type="password"
            placeholder="请输入原密码"
            clearable
            maxlength="20"
            style="width: 500px;"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input 
            v-model="passwordForm.newPassword"
            type="password"
            placeholder="请输入新密码"
            clearable
            maxlength="20"
            style="width: 500px;"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input 
            v-model="passwordForm.confirmPassword"
            type="password"
            placeholder="请再次输入新密码"
            clearable
            auto-complete="off"
            maxlength="20"
            style="width: 500px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
const Qs = require('qs')
import { modifyPassword } from '@/api/userCenter'
export default {
  data() {
    let validatePriority = (rule,value,callback) => {
      if(value != this.passwordForm.newPassword){
        callback(new Error('密码不相同'))
      } else if (value == this.passwordForm.oldPassword){
        callback(new Error('新密码与原密码相同'))
      } else {
        callback()
      }
    }
    return {
      single : {
        userId : '182402',
        redirect : '/workbenchManager/myProfile'
      },
      mdVisable: false,
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      rules: {
        oldPassword: [
          {required: true, message: '原密码不能为空', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '新密码不能为空', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {validator: validatePriority}
        ],
      }
    }
  },
  methods: {

    handleConfirm() {
      this.$refs['passwordForm'].validate((valid) => {
        if (valid) {
          // submit
          let data = {
            oldPassword: this.passwordForm.oldPassword,
            newPassword: this.passwordForm.newPassword
          }
          modifyPassword(Qs.stringify(data)).then(res => {
            if (res.data.code == '200') {
              this.$message({
                type: 'success',
                message: res.data.message
              })
              this.mdVisable = false
              this.passwordForm.oldPassword = ""
              this.passwordForm.newPassword = ""
              this.passwordForm.confirmPassword = ""  
            }else {
              this.$message({
                type: 'error',
                message: res.data.message
              })
            }
          }).catch(err => {
            console.log(err)
          })
        }
      })
    },
    handleCancel() {
      this.mdVisable = false
      this.$refs['passwordForm'].resetFields()
      this.$refs['passwordForm'].clearValidate()
    },
     /**
     * 测试单点
     */
   singleSubmitBtn(){
      
      this.$axios({
        method : 'post',
        url : 'http://localhost:9080/singleLogin',
        data : {
          userId : this.single.userId,
          redirect : this.single.redirect
        }
      }).then(res => {
        console.log(res)
      });
    }
  }
}

 
</script>


<style scoped>
h2 {
  margin: 0;
  color: #262626;
}
.row {
  /* width: 800px; */
  height: 80px;
  border-bottom: 1px solid #e8e8e8;
  overflow: hidden;
}
.row .meta {
  float: left;
  width: 600px;
}
.row .meta .title {
  font-size: 16px;
  font-weight: bold;
  color: #595959;
  margin: 16px 0 5px 0;
}
.row .meta .desc {
  font-size: 14px;
  color: #8c8c8c;
  margin: 0;
}
.row .action {
  float: left;
  height: 80px;
  width: 100px;
}
.row .action button {
  margin: 22px 0;
}
</style>

