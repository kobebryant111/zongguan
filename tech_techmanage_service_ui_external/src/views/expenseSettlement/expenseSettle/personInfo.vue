<!-- 个人信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">个人信息</p>
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

    <!-- 个人信息 -->
    <div class="contentMain">
      <el-form :model="formData" ref="formData" :rules="rules">
        <div class="contentTwo_title">
          <p class="contentTwo_title_p">个人信息</p>
        </div>
        <div class="content">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between"> 
                  <el-col :span="5">
                  <div>
                    <p>工号 *</p>
                    <el-form-item
                      prop="jobNo"
                      :rules="[{ required: true, message: '请选择供应商', trigger: 'change' }]"
                    >
                      <el-input v-model="formData.jobNo"></el-input>
                    </el-form-item>
                  </div>
                </el-col>    
                  <el-col :span="5">
                  <div>
                    <p>所属部门 *</p>
                    <el-form-item
                      prop="total"
                    >
                      <el-input v-model="formData.dept"></el-input>
                    </el-form-item>
                  </div>
                </el-col>        
                <el-col :span="5">
                  <div>
                    <p>姓名 *</p>
                    <el-form-item prop="name">
                      <el-input v-model="formData.name" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>供应商名称 *</p>
                    <el-form-item prop="supplierName">
                      <el-input v-model="formData.supplierName" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              
              <el-row type="flex" justify="space-between">           
                <el-col :span="5">
                  <div>
                    <p>状态 *</p>
                    <el-form-item
                      prop="payAmount"
                      :rules="[{ required: true, message: '请输入实付金额（含税）', trigger: 'change' }]"
                    >
                      <el-input placeholder="请输入实付金额（含税）" v-model="formData.payAmount" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>入场时间 *</p>
                    <el-form-item prop="taxRate">
                      <el-input v-model="formData.totalTax" disabled></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>离场时间 *</p>
                    <el-form-item prop="fileStatus">
                      <el-select v-model="formData.fileStatus" disabled>
                        <el-option
                          v-for="item in deptmentData"
                          :key="item.id"
                          :label="item.text"
                          :value="item.id"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>系统名称/项目名称 *</p>
                    <el-form-item
                      prop="projectManageName"
                      :rules="[{ required: true, message: '请输入项目经理）', trigger: 'change' }]"
                    >
                      <el-input placeholder="请输入项目经理" v-model="formData.projectManageName" ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              
              <el-row type="flex" justify="space-between">
                <el-col :span="5">
                  <div>
                    <p>项目经理 *</p>
                    <el-form-item prop="fileStatus">
                      <el-select v-model="formData.fileStatus" disabled>
                        <el-option
                          v-for="item in deptmentData"
                          :key="item.id"
                          :label="item.text"
                          :value="item.id"
                        />
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>外包方式 *</p>
                    <el-form-item
                      prop="projectManageName"
                      :rules="[{ required: true, message: '请输入项目经理）', trigger: 'change' }]"
                    >
                      <el-input placeholder="请输入项目经理" v-model="formData.projectManageName" ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>忘记打卡次数 *</p>
                    <el-form-item
                      prop="remark"
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>迟到次数 *</p>
                    <el-form-item
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">         
                <el-col :span="5">
                  <div>
                    <p>早退次数 *</p>
                    <el-form-item
                      prop="remark"
                      :rules="[{ required: true, message: '请输入备注）', trigger: 'change' }]"
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>旷工次数 *</p>
                    <el-form-item
                      prop="remark"
                      :rules="[{ required: true, message: '请输入备注）', trigger: 'change' }]"
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>请假次数 *</p>
                    <el-form-item
                      prop="remark"
                      :rules="[{ required: true, message: '请输入备注）', trigger: 'change' }]"
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>当月应出勤 *</p>
                    <el-form-item
                      prop="remark"
                      :rules="[{ required: true, message: '请输入备注）', trigger: 'change' }]"
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
              <el-row type="flex" justify="space-between">         
                <el-col :span="5">
                  <div>
                    <p>实际出勤 *</p>
                    <el-form-item
                      prop="remark"
                      :rules="[{ required: true, message: '请输入备注）', trigger: 'change' }]"
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>当月加班时长 *</p>
                    <el-form-item
                      prop="remark"
                      :rules="[{ required: true, message: '请输入备注）', trigger: 'change' }]"
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5">
                  <div>
                    <p>考勤年月 *</p>
                    <el-form-item
                      prop="remark"
                      :rules="[{ required: true, message: '请输入备注）', trigger: 'change' }]"
                    >
                      <el-input
                        placeholder="请输入备注"
                        v-model="formData.remark"
                      ></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="5"></el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <!-- 考勤信息 -->
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">考勤记录</p>
      </div>
      <div class="content">
        <div class="row_1">
          <div>
            <el-table :data="scoreList" style="width: 100%">
              <el-table-column prop="PjManageName" label="所属部门"></el-table-column>
              <el-table-column prop="contactNo" label="姓名" />
              <el-table-column prop="contactName" label="日期" />
              <el-table-column prop="supplierName" width="200" label="上班时间" />
              <el-table-column prop="payStartTime" label="下班时间" />
              <el-table-column prop="payStartEnd" label="加班开始时间"></el-table-column>
              <el-table-column prop="total" label="加班时长" />
              <el-table-column prop="taxRate" label="项目经理" />
              <el-table-column prop="payAmount" label="是否异常"></el-table-column>
              <el-table-column prop="" label="异常原因">
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>

    </div>
    
  </div>
</template>
<script>
import common from "@/api/common";
import {
  queryResourceDept,
  queryEmployeeTree
} from "@/api/integrated/wbryzrlc";
import { save } from "@/api/manager/project";
import pager from "@/components/Pager";
import departmentTwo from "@/views/plugins/components/DepartmentTwo";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import buttonCommon from "@/components/eleButton/buttonCommon"; //引入按钮组件2
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import { uploadExcel } from "@/api/integrated/wbms";
import { randomNumber } from '@/utils'
import { getFlowData, getListAll } from "@/api/common/workflowApi";
import Moment from "moment";
export default {
  name: "ProjectInfo",
  components: { departmentTwo, buttonOrange, buttonCommon, FlowLogsGd },
  data() {
    return {
      depId: null,
      formData: this.$route.params,
      managerData: {},
      deptmentData: {},
      size: "mini", //按钮大小
      title: "添加",
      buttonClose: "关 闭", //关闭按钮名字
      buttonDel: "删 除",
      buttonCancel: "取 消",
      buttonCommit: "提 交",
      buttonSure: "确 定",
      outsourceno_options: [], //外包商
      scoreList: [
        {
          PjManageName: "张三",
          contactNo: '1234',
          contactName: '核心01',
          supplierName: '浙江优创信息技术有限公司',
          payStartTime: "2022-10-20",
          payStartEnd: "2022-10-31",
          total: "100000",
          taxRate: '3%',
          price: "800",
          payAmount: '100000',
          payable: "800"
        },
        {
          PjManageName: "李四",
          contactNo: '1235',
          contactName: '推单02',
          supplierName: '浙江优创信息技术有限公司',
          payStartTime: "2022-10-21",
          payStartEnd: "2022-10-31",
          total: "200000",
          taxRate: '3%',
          price: "800",
          payAmount: '200000',
          payable: "800"
        },
      ],
      flowItem: {
        applyType: 1,
        badge: "wb22051",
        formScene: "ybGd",
        status: "gd",
        instanceid: "964d0222-3f0c-11ed-9f81-005056afd944"
      },
      fileData: [
        { key: 0, value: "总办议案" },
        { key: 1, value: "发票" },
        { key: 2, value: "其他" }
      ],
      fkxx: "",
      fFinish: true, //是否显示上传文件
      fileList: [], //上传文件列表
      // 表单校验                       
      rules: {
        supplierName: [
          { required: true, message: "请选择供应商", trigger: "blur" }
        ],
        total: [
          { required: true, message: "请选择应付总金额", trigger: "blur" }
        ],
        payAmount: [
          { required: true, message: "请选择生效日期", trigger: "blur" }
        ],
        projectManageName: [
          { required: true, message: "请选择项目经理", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "请输入备注", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getWbcom();
    // this.getDeptments();
    if (this.formData.depId) {
      this.title = "修改";
      this.depId = this.formData.depId;
      let depId = String(this.formData.depId);
      this.formData.depId = depId;
    }
    this.formData.usedCount = 0;
    this.$nextTick(()=>{
         this.$refs.log.init(this.flowItem);
    })
  },
  methods: {
    // 查询外包商
    getWbcom() {
      getListAll()
        .then(res => {
          this.outsourceno_options = res.data.results;
        })
        .catch(error => {
          this.$message.error(error);
        });
    },
    /** 提交 */
    onSubmit() {
      this.$refs["formData"].validate(valid => {
        if (valid) {
          console.log(this.formData, "form表单数据");
          const loading = common.loading(this);
          save(this.formData).then(res => {
            loading.close();
            if (res.data.status == "200") {
              this.$message.success("操作成功！");
              this.$router.push({ name: "contractManager" });
            } else {
              this.$message.error(res.data.message);
            }
          });
        }
      });
    },
    // 关闭按钮
    close() {
      this.$router.push({ name: "expenseSettle" });
    },
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
<style>
.el-table__footer-wrapper tbody td.el-table__cell{
    background-color: #FFF;
}
</style>