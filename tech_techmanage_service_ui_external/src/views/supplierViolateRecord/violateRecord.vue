<!-- 添加项目信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">违规纪录新增123</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>

    <!-- 项目信息 -->
    <div class="contentMain">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">基本信息</p>
      </div>
      <div class="content">
        <el-form :model="formData" ref="formData">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                 <el-col :span="11">
                  <div>
                    <p>处罚时间 *</p>
                    <el-form-item prop="name" :rules="[{ required: true, message: '请填写项目名称', trigger: 'blur' }]" ref="msgForm">
                       <el-input placeholder="请输入" v-model="formData.name"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>处罚供应商 *</p> 
                     <el-form-item prop="depId" :rules="[{ required: true, message: '请选择部门', trigger: 'change' }]">
                        <el-select v-model="formData.depId" placeholder="请选择部门" @change="getEmployees">
                            <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                        </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>处罚类型 *</p> 
                     <el-form-item prop="depId" :rules="[{ required: true, message: '请选择部门', trigger: 'change' }]">
                        <el-select v-model="formData.depId" placeholder="请选择部门" @change="getEmployees">
                            <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                        </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>处罚原因 *</p>
                    <el-form-item prop="badge" :rules="[{ required: true, message: '请选择项目经理', trigger: 'change' }]">
                        <el-select v-model="formData.badge" placeholder="请选择项目经理">
                            <el-option v-for="item in managerData" :key="item.id" :label="item.text" :value="item.id" />
                        </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">
                <el-col :span="11">
                  <div>
                    <p>处罚类型 *</p> 
                     <el-form-item prop="depId" :rules="[{ required: true, message: '请选择部门', trigger: 'change' }]">
                        <el-select v-model="formData.depId" placeholder="请选择部门" @change="getEmployees">
                            <el-option v-for="item in deptmentData" :key="item.id" :label="item.text" :value="item.id" />
                        </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="11">
                  <div>
                    <p>处罚原因 *</p>
                    <el-form-item prop="badge" :rules="[{ required: true, message: '请选择项目经理', trigger: 'change' }]">
                        <el-select v-model="formData.badge" placeholder="请选择项目经理">
                            <el-option v-for="item in managerData" :key="item.id" :label="item.text" :value="item.id" />
                        </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-form>
      </div>
    </div> 
    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
         <div class="wrapper_left">
            <el-col :span="1.5">
              
            </el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper_right_bottom_1_zb">
              <el-col :span="1.5">
                <button-common :buttonName="buttonCancel" :size="size" :fns="close"/>
              </el-col>
            </div>
            <div>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonCommit" :size="size" :fns="onSubmit" />
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>

  </div>
</template>
<script>
import common from "@/api/common"; 
import {queryResourceDept,queryEmployeeTree} from "@/api/integrated/wbryzrlc";
import {save} from '@/api/manager/project';
import pager from "@/components/Pager"; 
import departmentTwo from "@/views/plugins/components/DepartmentTwo";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
import Moment from 'moment';
export default {
    name: 'ProjectInfo',
    components: {departmentTwo,buttonOrange, buttonCommon },
    data() {
        return {
            depId: null,
            formData:this.$route.params,
            managerData:{},
            deptmentData:{},
            size: 'mini',  //按钮大小
            title: '添加',
            buttonClose: '关 闭', //关闭按钮名字
            buttonDel: '删 除',
            buttonCancel: '取 消',
            buttonCommit: '提 交',
            buttonSure: '确 定', 
        };
    }, 
    created() {   
        this.getDeptments();  
        if(this.formData.depId){ 
            this.title = "修改";
            this.depId = this.formData.depId;
            let depId = String(this.formData.depId);
            this.formData.depId = depId;
            this.getEmployees(depId);
        }
    }, 
    methods: {
        //获取部门
        getDeptments() {
            let params = {};
            params.auth = true;
            params.objectgrade = "2";
            params.allowDept = "80000122";
            params.objectcode = "6000000300";
            queryResourceDept(params).then(res => {
                if (res.data.status == 200) {
                  var fild = res.data.results;
                  if(res.data.results[0].children){
                    var fild = res.data.results[0].children;
                  }
                    var reg = /([^-]+)$/;
                    this.deptmentData = fild.filter(li => {
                        li.id = li.id.replace("dep", "");
                        li.text = li.text.match(reg)[1] + "("+li.id+")";
                        return li;
                    });  
                } else {
                    this.$message.error("获取部门数据失败");
                }
            }).catch(error => {
                this.$message.error("获取部门数据失败");
            }); 
        },
        // 查询部门人员
        getEmployees(depId) {
            if(this.depId && depId != this.depId  ){ 
              this.$set(this.formData,'badge', '');
            }
            queryEmployeeTree(depId).then(res => {
                if (res.data.status == 200) {
                    this.depId = depId; 
                    var fild = res.data.results[0].children;  
                    this.managerData = fild.filter(li => { 
                        li.id = li.id.replace("emp", "");
                        return li;
                    });
                } else {
                    this.$message.error("获取部门数据失败");
                } 
            }).catch(error => {
                this.$message.error("当前部门下面无数据!");
            });
        },

      /**
       * 提交
       */
        onSubmit() {
            this.$refs["formData"].validate(valid => {
            if(valid){
                const loading = common.loading(this); 
                save(this.formData).then(res => {
                    loading.close();
                    if (res.data.status == "200") {
                        this.$message.success("操作成功！");  
                        this.$router.push({ name: 'projectMgr' });
                    } else {
                        this.$message.error(res.data.message); 
                    }
                });
            }
        });
       },
      // 关闭按钮
      close() {
        this.$router.push({ name: 'projectMgr' });
      }
    }
};
</script>
<style scoped>
  /* bin修改 */
  .contentMain {
    width: 100%;
    height: 100%;
    font-size: 16px;
    padding: 10px 20px;
  }

  /* 导航菜单栏 */
  .nav {
    width: 100%;
    /* height: 30px; */
    background-color: #ffffff;
  }

  .nav .nav_name {
    width: 180px;
    height: 24px;
    font-size: 16px;
    font-weight: 600;
    color: rgba(38, 38, 38, 1);
    line-height: 24px;
    display: inline-block;
  }

  .nav .wrapper {
    margin-left: 25px;
    margin-right: 25px;
    display: flex;
    justify-content: space-between;
  }

  .wrapper_left,
  .wrapper_right {
    display: flex;
    align-items: center;
  }

  .wrapper_right_bottom_1_zb {
    margin-right: 10px;
  }
  .content {
    width: 100%;
    font-size: 16px; 
  }

  .content p {
    color: #262626;
    font-size: 14px;
    margin: 12px 0;
  }

  .content .row_2_detail {
    font-size: 16px;
    font-weight: 600;
  }

  .contentTwo_title {
    height: 60px;
    line-height: 60px;
    background-color: #ffffff;
    padding: 0 25px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.15);
  }

  .contentTwo_title_p {
    color: #262626;
    font-weight: 600;
  }

  .content .row_1 {
    background-color: #ffffff;
    /* padding: 0 20px 15px 20px; */
    padding: 0 20px;

  }

  .contentMain .row_2 {
    background-color: #ffffff;
    padding: 0 20px 15px 20px;
    margin-top: 15px;
  }

  .add_row {
    color: #faad14;
    font-size: 14px;
    font-weight: 500;
    margin-left: 10px;
    cursor: pointer;
  }

  /* 空格占位 */
  .blank {
    width: 100%;
    height: 160px;
  }

  .bottom {
    position: absolute;
    bottom: 0;
    padding: 15px 0;
  } 
  
</style>