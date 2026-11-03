<!-- 人力外包考核申请 审批 -->
<template>
    <div class="wrapper">
        <div class="back_top">
            <div>
                <p class="back_top_title">人力外包考核</p>
            </div>
            <div>
                <el-button size="mini" @click="toClose">关闭</el-button>
            </div>
        </div>
        <div style="margin:15px">
            
            <div class="main">
            <el-form v-model="formData" label-width="140px">
                <div class="title">
                    <h4 class="title_style">永赢金租外包服务考核评分表(人力类)</h4>
                </div>
                <div class="mini_box" style="margin-bottom:10px">
                    <el-row type="flex" justify="space-between">
                        <el-col :span="7">
                            <el-form-item label="考核供应商名称:">
                                <el-input v-model="formData.supplierName" disabled></el-input>
                                <!-- <el-select v-model="formData.supplierId" filterable disabled>
                                    <el-option
                                        v-for="item in outsourceno_options"
                                        :key="item.id"
                                        :label="item.outsourcname"
                                        :value="item.orgcode"
                                    ></el-option>
                                </el-select> -->
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="考核年份:">
                                <el-date-picker
                                  type="year"
                                  v-model="formData.assessYear"
                                  placeholder="选择年份"
                                  value-format="yyyy"
                                  disabled
                                >
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </div>
                <div class="container">
                    <!-- <div style="margin-bottom:10px">
                        <el-row type="flex" justify="start">
                            <el-col :span="5">
                                <el-form-item label="考核项目:">
                                    <el-input v-model="formData.khxm"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div> -->
                    <div>
                        <table class="table_box" border="1" cellpadding='0' cellspacing='0'>
                            <thead style="text-align:center">
                                <th style="padding: 20px;width:200px;height:30px">考核内容</th>
                                <th style="padding: 20px;width:600px;height:30px">考核标准</th>
                                <th style="padding: 20px;width:200px;height:30px">考核得分</th>
                            </thead>
                            <tbody style="text-align:center" class="fontSize">
                                <tr v-for="item in tableData" :key="item.id">
                                    <td style="padding: 0;width:40px;height:80px;">{{ item.code }}</td>
                                    <td style="padding: 0;width:100px;height:80px">{{ item.name }}</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <el-input v-model.number="item.value" disabled></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                                <!-- <tr>
                                    <td style="padding: 0;width:40px;height:80px;">1、任务完成工作量(30分)</td>
                                    <td style="padding: 0;width:100px;height:80px">1、任务难度大，无法替代，工作饱和度高，加班时间长的得27-30分；2、任务难度较大，工作饱和度高，加班时间较长的得24-26分；3、任务难度较大，工作饱和度较高，加班时间一般的得21-23分；4、任务难度一般，工作饱和度一般，加班时间不长的得16-20；5、任务简单，工作饱和度一般，偶有加班的得11-15分；6、任务简单，工作不饱和，无加班的得0-10分。</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <el-input v-model.number="formData.jobDone" placeholder="请输入分值，不大于30分" @change="countScore"></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 0;width:40px;height:80px">2、任务完成质量(20分)</td>
                                    <td style="padding: 0;width:100px;height:80px">1、质量突出，能帮助组织提升质量的得18-20分；2、质量高于一般要求，基本无差错的得15-17分；3、质量基本符合要求的得11-14分，4、质量很差，经常返工，影响进度的得0-10分。</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <el-input v-model.number="formData.rwDone" placeholder="请输入分值，不大于20分" @change="countScore"></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 0;width:40px;height:80px">3、任务完成效率(20分)</td>
                                    <td style="padding: 0;width:100px;height:80px">1、工作任务能提前完成，并超额完成其他任务的得18-20分；2、工作任务能按时完成的得15-17分；3、工作任务偶有延迟的得11-14分；4、工作任务经常延迟的得0-10分。</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <el-input v-model.number="formData.rwxl" placeholder="请输入分值，不大于20分" @change="countScore"></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 0;width:40px;height:80px">4、人员技术水平(10)</td>
                                    <td style="padding: 0;width:100px;height:80px">1、借用人员经验丰富，技术水平较高的得8-10分；2、经验一般，技术水平能满足日常工作需要的得4-7分；3、经验欠缺，技术水平不能完全满足工作需要的得1-3分；4、无相关经验，技术水平不能满足工作需要的得0分。</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <el-input v-model.number="formData.jsrysp" placeholder="请输入分值，不大于10分" @change="countScore"></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 0;width:40px;height:80px">5、人员稳定性(10分)</td>
                                    <td style="padding: 0;width:100px;height:80px">1、人员保持稳定，无离职情况发生的得10分；2、人员保持相对稳定，更换人员占年初总人员比在20%内，且新换人员能满足我行要求的得7-9分；3、人员不太稳定，更换人员占总人员比20%-50%之间，但新换人员能满足我行要求的得4-6分；4、人员不稳定，更换人员超过50%的得1-3分；5、人员不稳定，更换人员超过50%，且新换人员不能满足我行要求得0分。</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <el-input v-model.number="formData.rywd" placeholder="请输入分值，不大于10分" @change="countScore"></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 0;width:40px;height:80px">6、学习态度及能力(5分)</td>
                                    <td style="padding: 0;width:100px;height:80px">1、大部分借用人员都能主动学习，学习态度好，接受能力强的得4-5分；2、大部分人员被动学习，但学习态度较好，接受能力一般的得2-3分，3、大部分人员学习意愿不强，学习态度不好，接受能力较差得0-1分。</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <el-input v-model.number="formData.study" placeholder="请输入分值，不大于5分" @change="countScore"></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 0;width:40px;height:80px">7、日常行为规范(5分)</td>
                                    <td style="padding: 0;width:100px;height:80px">1、完全遵守我行各项行为规范（包括日常礼仪规范、禁烟、刷卡、出勤等），本年度借用人员无任何违规行为的得5分；2、基本遵守我行各项行为规范，本年度借用人员偶有轻微违规行为的得3-4分；3、本年度借用人员经常发生违反相关规定的行为得1-2分；4、借用人员违反规定被总行或科技部查处的得0分。</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <el-input v-model.number="formData.rcxw" placeholder="请输入分值，不大于5分" @change="countScore"></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr> -->
                                <tr>
                                    <td colspan="2" style="padding: 0;width:40px;height:80px">合计</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <span>{{formData.assessScoreSum}}分</span>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="padding: 0;width:40px;height:80px">等级</td>
                                    <td style="padding: 20px;height:80px">
                                        <div class="th_box">
                                            <el-form-item label-width="0">
                                                <span>{{formData.assessLevel}}</span>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </el-form>
        </div>

        <!-- 操作日志 -->
        <div class="czLog">
            <flow-logs :processInstId="flowItem.processInstanceId" ref="log"></flow-logs>
        </div>
        </div>

        <div class="blank"></div>

        <!-- 底部操作栏 -->
        <div class="nav bottom">
          <approve-bottom :proving="proving" :flowItem="flowItem"></approve-bottom>
        </div>
        <!-- <div class="bottom_box">
            <button-orange :buttonName="buttonName" :size="size" :fns="onSubmit"></button-orange>
        </div> -->
        <!-- <div class="nav-bottom" v-if="flowItem.status == 'yb'">
            <yb-revoke :flowItem="flowItem"></yb-revoke>
        </div> -->
    </div>
</template>

<script>
import common from "@/api/common";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
// import { getListAll } from "@/api/common/workflowApi";
import { queryPager, relationList, createFlow, initFlow, queryDetail, dhApproval, gmApproval, getListForNotCg, newqueryDetail } from "@/api/supplierAssessment/index";
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
// import ybRevoke from "@/views/plugins/camunda/ybRevoke";
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import approveBottom from "@/views/plugins/camunda/approveBottom";

export default {
    components: { buttonOrange, flowLogs, approveBottom },
    data() {
        return {
            formData: {
                assessYear: "", // 考核日期
                supplierId: "", // 供应商编号
                assessType: 'RL01', // 考核类型: 人力：RL01; 项目：XM01
                // khxm: "", // 考核项目
                // jobDone: "", // 工作完成量
                // rwDone: "", // 任务完成量
                // rwxl: "", // 工作完成效率
                // jsrysp: "", // 人员技术水平
                // rywd: "", // 人员稳定性
                // study: "", // 学习态度和能力
                // rcxw: "", // 日常行为规范
                assessScoreSum: "", // 考核总分
                assessLevel: "", // 考核等级
                // question: "", // 存在问题
            },
            size: "mini",
            buttonName: "提交",
            flowItem: {},
            instanceid: "",
            tableData: [],
            outsourceno_options: [], //外包商
            rlList: [],
            flowItem: {},
            instanceid: '',
            formScene: '', //场景
            taskId: '',
        }
    },
    methods: {
        init(params) {
            console.log('人力项目考核新增');
            this.$nextTick(_ => {
                this.flowItem = params;
                this.instanceid = params.instanceid || params.processInstanceId;
                this.formScene = params.formScene;
                this.taskId = params.taskId;
                this.getWbCom();
                this.getqueryDetail(this.instanceid);
                this.$refs.log.init(this.instanceid);
            })
        },
        getsupplierName(id) {
            newqueryDetail(id).then( res => {
                console.log('getsupplierName', res)
                this.formData.supplierName = res.data.results.outsourcname
                this.formData.supplierId = res.data.results.orgcode
            })
        },
        // 获取详情
        getqueryDetail(id) {
            let params = {};
            params.instanceid = id;
            let data = common.paramsFormat(params);
            queryDetail(data).then(res => {
              console.log('获取详情', res)
              if(res.data.status === 200) {
                this.formData = res.data.results;
                console.log('this.formData', this.formData);
                this.getsupplierName(res.data.results.supplierId)
                this.getInitData();
              } else {
                this.$message.error("获取数据失败")
              }
            })
        },
        // 获取评分表
        getInitData() {
            relationList({type: 'RL01'}).then(res => {
                console.log('人力考核项目评分表', res);
                if(res.data.status === 200) {
                    this.tableData = res.data.results;
                    // this.tableData.assessScoreMap = this.formData.assessScoreMap
                    let arr = this.formData.assessScoreMap
                    this.tableData.forEach((item, index) => {
                        for(let k in arr) {
                            // console.log(k,arr[k])
                            if(item.key === k) {
                                item.value = arr[k];
                            }
                            // return
                        }
                    })
                    res.data.results.forEach(item => {
                        this.rlList.push(item.key);
                    })
                    // console.log(this.rlList)
                } else {
                    this.$message.error(res.data.message);
                }
            })
        },
        // 提交
        onSubmit() {
            console.log('this.form', this.formData)
            let params = {};
            this.formData.assessScoreSum = this.formData.assessScoreSum.toString();
            params = this.formData;
            let list = {}
            for(let i of this.rlList) {
                for(let k in this.tableData) {
                    if(this.tableData[k].key === i) {
                        list[i] = this.tableData[k].value.toString();
                    }
                }
            }
            params.assessScoreMap = list;
            let data = JSON.stringify(params)
            initFlow(data).then(res => {
                console.log('提交表单', res);
                if(res.status === 200) {
                    let message = common.getFlowMessgeNew(res.data.message);
                    let messageArr = message.split(",");
                    this.$nextTick(() => {
                      this.messageNotify(messageArr);
                      // 此处更换路径
                      this.$router.push({ name: 'dashboard' });
                    })
                } else {
                    return this.$message.info(res.data.message);
                }
            })
        },
        // 关闭
        toClose() {
          this.$router.push('/dashboard')
        },
        // 分数计算
        countScore(val, key) {
            console.log('val', val, key);
            let counStr = 0;
            // if(key === 'RL01_001') {
            //     if(parseInt(val) > 30) this.tableData[0].value = 30;
            // };
            // if(key === 'RL01_002') {
            //     if(parseInt(val) > 20) this.tableData[1].value = 20;
            // };
            // if(key === 'RL01_003') {
            //     if(parseInt(val) > 20) this.tableData[2].value = 20;
            // };
            // if(key === 'RL01_004') {
            //     if(parseInt(val) > 10) this.tableData[3].value = 10;
            // };
            // if(key === 'RL01_005') {
            //     if(parseInt(val) > 10) this.tableData[4].value = 10;
            // };
            // if(key === 'RL01_006') {
            //     if(parseInt(val) > 5) this.tableData[5].value = 5;
            // };
            // if(key === 'RL01_007') {
            //     if(parseInt(val) > 5) this.tableData[6].value = 5;
            // };
            for(let k of this.rlList) {
               for(let i in this.tableData) {
                    if(key === k) {
                        if(parseInt(val) > this.tableData[i].score) this.tableData[i].value = this.tableData[i].score;
                    }
               }
            }
            // obj.text1 = this.tableData[0].value*1;
            // obj.text2 = this.tableData[1].value*1;
            // obj.text3 = this.tableData[2].value*1;
            // obj.text4 = this.tableData[3].value*1;
            // obj.text5 = this.tableData[4].value*1;
            // obj.text6 = this.tableData[5].value*1;
            // obj.text7 = this.tableData[6].value*1;
            let obj = {};
            for(let i = 0; i < this.tableData.length; i++) {
                obj[`text${i}`] = this.tableData[i].value;
            }
            for(var k in obj) {
                // console.log(k, obj[k])
                if(obj[k] == '') {
                    delete obj[k];
                }
            }
            // console.log(obj);
            for(var j in obj) {
                counStr += parseInt(obj[j])
            }
            this.formData.assessScoreSum = counStr;
            if(this.formData.assessScoreSum >= 90 && this.formData.assessScoreSum <= 100) { 
                this.formData.assessLevel = 'A'
            } else if (this.formData.assessScoreSum >= 80 && this.formData.assessScoreSum <= 89) {
                this.formData.assessLevel = 'B+'
            } else if(this.formData.assessScoreSum >= 70 && this.formData.assessScoreSum <= 79) {
                this.formData.assessLevel = 'B'
            } else if(this.formData.assessScoreSum >= 60 && this.formData.assessScoreSum <= 69) {
                this.formData.assessLevel = 'C'
            } else if(this.formData.assessScoreSum > 0 && this.formData.assessScoreSum < 60) {
                this.formData.assessLevel = 'D'
            }
        },
        // 验证必填项
      proving(data) {
        // console.log(111111, data)
        let status = new Promise(() => {});
        status = this.liuzhuan(data).then(val => {
          // console.log(val, 'val')
          return val;
        });
        // console.log(status, 'status')
        return status;
      },
      // 流转或者退回
      liuzhuan(data) {
        console.log('data', data)
        // debugger;
        let params = {};
        params = this.formData;
        let bps = {};
        this.flowItem.taskId = this.taskId;
        this.flowItem.processInstId = this.instanceid;
        this.flowItem.conclusion = data.conclusion;
        this.flowItem.suggest = data.suggest;
        bps = this.flowItem;
        // params.applyInfoDtos = this.formData.applyInfoDtos;
        params.bps = bps;

        console.log(params,'params')
        if (this.formScene == "erlrkhApprove") {
          return dhApproval(params).then(res => {
            // loading.close()
            // console.log(res,'二级部经理审批')
            return res.data;
          });
        } else if (this.formScene == "yilrkhApprove"){
          return gmApproval(params).then(res => {
            // loading.close()
            // console.log(res,'一级部经理审批')
            return res.data;
          });
        }
      },
        // 获取供应商
        getWbCom() {
            getListForNotCg().then(res => {
                // console.log('获取供应商', res)
                this.outsourceno_options = res.data.results
            })
        },
        //消息提示
        messageNotify(messageVal) {
          let messageSec = messageVal.slice(2).join("，");
          this.$notify({
            title: '提示',
            dangerouslyUseHTMLString: true,
            message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
            duration: 0
          })
        },
    },
}
</script>

<style scoped>
.wrapper {
    /* padding: 15px; */
    width: 100%;
    height: 100%;
}
.main {
    /* margin: 15px; */
    padding: 15px 80px;
    background-color: #ffffff;
    width: 100%;
    height: 100%;
    
}
.title{
    display: flex;
    align-items: center;
    justify-content: center;
}
.title_style {
    font-size: 20px;
    font-weight: 600;
}
.table_box {
    width: 100%;
    height: 100%;
}
.input_box {
    width: 60px;
    border-style: none;
    outline: medium;
}
.el-form-item__content {
    margin-left: 0;
}
.th_box {
    display: flex;
    justify-content: center;
    align-items: center;
}
.el-form-item {
    margin-bottom: 0
}
.data-v-6b752731 {
    padding: 0;
}
.fontSize {
    font-size: 14px;
}
/* 空格占位 */
.blank {
  width: 100%;
  height: 160px;
}
.bottom_box {
  position: absolute;
  bottom: 0;
  /* padding: 15px; */
  background-color: #ffffff;
  width: 100%; 
  padding: 15px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.back_top {
    width: 100%;
    background: #ffffff;
    padding: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}
.wrapper .back_top .back_top_title {
    width: 100px;
    height: 24px;
    font-size: 16px;
    font-weight: 600;
    color: rgba(38, 38, 38, 1);
    line-height: 24px;
}
  .bottom {
    position: absolute;
    bottom: 0;
    padding: 15px 0;
  }
  /* 导航菜单栏 */
  .nav {
    width: 100%;
    /* height: 30px; */
    background-color: #ffffff;
  }
</style>
