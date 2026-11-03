<!-- 风险评估 审批 -->
<template>
    <div class="wrapper">
        <div class="back_top">
            <div>
                <p class="back_top_title">风险评估</p>
            </div>
            <div>
                <el-button size="mini" @click="toClose">关闭</el-button>
            </div>
        </div>
        <div class="padding">
            <div class="container">
            <div class="container_title">
                <p>信息科技外包供应商风险评估表</p>
            </div>
            <div class="container_main">
                <el-form :model="formData">
                    <div class="container_main_top">
                        <el-row type="flex" justify="space-between">
                            <el-col :span="5"></el-col>
                            <el-col :span="5">
                                <el-form-item label="评估日期" label-width="80px">
                                    <el-input v-model="formData.assessYear" disabled></el-input>
                                    <!-- <el-date-picker
                                      type="year"
                                      v-model="formData.assessYear"
                                      placeholder="选择年份"
                                      value-format="yyyy"
                                    >
                                    </el-date-picker> -->
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <table border="1" cellpadding='10' cellspacing='0'>
                        <thead>
                            <!-- <th class="container_main"> -->
                                <th colspan="5">外包业务基本信息</th>
                            <!-- </th> -->
                        </thead>
                        <tbody>
                            <tr>
                                <td>外包业务名称</td>
                                <td>
                                    <el-input v-model="formData.busiName" disabled></el-input>
                                </td>
                                <td>外包业务类型</td>
                                <td colspan="2">
                                    <el-form-item>
                                        <el-radio-group v-model="formData.busiType" disabled>
                                            <el-radio label="01" name="type">研发咨询类</el-radio>
                                            <el-radio label="02" name="type">系统运行维护类</el-radio>
                                            <el-radio label="03" name="type">业务外包中的信息科技活动</el-radio>
                                        </el-radio-group>  
                                    </el-form-item>
                                </td>
                            </tr>
                            <tr>
                                <td>供应商名称</td>
                                <td colspan="4">
                                    <el-form-item width="200">
                                        <el-select v-model="formData.supplierId" filterable placeholder="请选择供应商" disabled>
                                            <el-option
                                                v-for="item in outsourceno_options"
                                                :key="item.id"
                                                :label="item.supplierName"
                                                :value="item.supplierCode"
                                            ></el-option>
                                        </el-select>
                                    </el-form-item>    
                                </td>
                            </tr>
                            <tr>
                                <td>外包服务主要内容</td>
                                <td>
                                    <el-input v-model="formData.impInfo" disabled></el-input>
                                </td>
                                <td>实施方式</td>
                                <td colspan="2">
                                    <el-form-item>
                                        <el-radio-group v-model="formData.executionMode" disabled>
                                            <el-radio label="Y" name="type">驻场</el-radio>
                                            <el-radio label="N" name="type">非驻场</el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </td>
                            </tr>
                            <tr>
                                <td>影响业务内容</td>
                                <td colspan="4">
                                    <el-form-item>
                                        <el-checkbox-group v-model="formData.affectBusiTypeArray" disabled>
                                        <el-row>

                                            <el-col :span="6">
                                                <el-checkbox label="01" name="type">渠道管理类</el-checkbox>
                                            </el-col>
                                            <el-col :span="6">
                                                <el-checkbox label="02" name="type">客户管理类</el-checkbox>
                                            </el-col>
                                            <el-col :span="6">
                                                <el-checkbox label="03" name="type">产品管理类</el-checkbox>
                                            </el-col>   
                                            <el-col :span="6">
                                                <el-checkbox label="04" name="type">财务管理类</el-checkbox>
                                            </el-col>
                                        </el-row>
                                        <el-row>
                                            <el-col :span="6">
                                                <el-checkbox label="05" name="type">决策支持类</el-checkbox>
                                            </el-col>
                                            <el-col :span="6">
                                                <el-checkbox label="06" name="type">共享支持类</el-checkbox>
                                            </el-col>
                                            <el-col :span="6">
                                                <el-checkbox label="07" name="type">其他</el-checkbox>
                                            </el-col>
                                            <el-col :span="6"></el-col>
                                        </el-row>
                                        </el-checkbox-group>
                                    </el-form-item>
                                </td>
                            </tr>
                            <tr>
                                <td>填写部门</td>
                                <td  colspan="4">
                                    <el-input v-model="formData.deptName" disabled></el-input>
                                    <!-- <el-select v-model="formData.deptId" filterable disabled>
                                        <el-option
                                           v-for="item in depData"
                                            :key="item.depid"
                                            :label="item.location"
                                            :value="item.depid"
                                        ></el-option>
                                    </el-select> -->
                                </td>
                            </tr>
                        </tbody>
                        <thead>
                            <th colspan="5">外包业务风险评估</th>
                        </thead>
                        <tbody>
                            <tr>
                                <td>序号</td>
                                <td>风险类别</td>
                                <td>评估内容</td>
                                <td>评估结果</td>
                            </tr>
                            <tr v-for="item in tableData" :key="item.displaySeq">
                                <td>{{ item.displaySeq }}</td>
                                <td>{{ item.code }}</td>
                                <td>{{ item.name }}</td>
                                <td>    
                                    <el-form-item >
                                        <el-radio-group v-model="item.value" disabled>
                                            <el-radio v-model="item.value" v-for="(it) in item.arrayMap" :key="it.id" :value="it.key" :label="`${it.key}`" name="type" @change="handelCheck(it.key)">{{it.label}}</el-radio>
                                        </el-radio-group>
                                        <!-- <el-checkbox label="不合适" name="type"></el-checkbox> -->
                                    </el-form-item>
                                </td>
                            </tr>
                            <tr>
                                <td>评估结果</td>
                                <td colspan="4">
                                    <el-input v-model="formData.assessResult" disabled></el-input>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div>
                        <el-row type="flex" justify="space-between">
                            <el-col :span="6">
                                <el-form-item label="项目经理:" label-width="80px">
                                    <el-input v-model="formData.pmName" disabled></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="所属二级部负责人:" label-width="140px">
                                    <el-input v-model="formData.leaderPmName" disabled></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </el-form>
            </div>
        </div>
        <!-- 操作日志 -->
        <div class="czLog">
            <flow-logs :processInstId="flowItem.processInstanceId" ref="log"></flow-logs>
        </div>
        </div>

        <div class="blank"></div>

        <div class="nav bottom">
          <approve-bottom :proving="proving" :flowItem="flowItem"></approve-bottom>
        </div>

        <!-- <div class="bottom">
            <div>
            </div>
            <div>
                <button-orange :buttonName="buttonName" :size="size" :fns="onSubmit"></button-orange>
            </div>
        </div> -->
    </div>
</template>

<script>
import common from "@/api/common";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
import { queryResourceDept } from "@/api/integrated/wbryzrlc";
import { queryPager, relationList, createFlow, initFlow, queryDetail, dhApproval, gmApproval, getDeptList, queryOneYears } from "@/api/riskAssessment/index";
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import approveBottom from "@/views/plugins/camunda/approveBottom";

export default {
    components: { buttonOrange, flowLogs, approveBottom },
    data() {
        return {
            formData: {
                assessYear: "", // 评估日期
                supplierId: "", // 供应商id
                supplierName: "", // 供应商名称
                assessType: "PG01", // 评估类型
                busiName: "", // 外包业务名称
                busiType: "", // 外包业务类型
                impInfo: "", // 外包服务主要内容
                executionMode: "", // 实施方式
                affectBusiTypeArray: "", // 影响业务内容
                assessResult: "", // 评估结果
                pmId: "", // 项目经理编号
                leaderPmId: "", // 部门负责人编号
                pmName: "", // 项目经理名称
                leaderPmName: "", // 部门负责人名称
                deptId: "", // 部门id
                assessResult: '例：外包风险水平较低，可采取外包开发模式。', // 建议
            },
            buttonName: "提交",
            size: "mini",
            tableData: [
                // {
                //     // arrays: ['成熟', '不成熟'],
                //     arrays: [
                //         {
                //             label: '成熟',
                //             key: 'Y'
                //         },
                //         {
                //             label: '不成熟',
                //             key: 'N'
                //         }
                //     ],
                //     code: "战略风险",
                //     displaySeq: "1",
                //     id: "EF3814163E3CA3B3E0530803670CBED9",
                //     key: "PG01_001",
                //     name: "是否适合永赢租赁发展战略",
                //     type: "PG01",
                //     value: 'N',
                // }
            ],
            outsourceno_options: [], //外包商
            depData: [], // 部门
            pgList: [],
            flowItem: {},
            instanceid: '',
            formScene: '', //场景
            taskId: '',
        }
    },
    methods: {
        init(params) {
            console.log('风险评估', params);
            this.$nextTick(_ => {
                this.flowItem = params;
                this.instanceid = params.instanceid || params.processInstanceId;
                this.formScene = params.formScene;
                this.taskId = params.taskId;
                // this.getInitData();
                this.filOneDep();
                this.getqueryOneYears();
                this.getqueryDetail(this.instanceid);
                this.$refs.log.init(this.instanceid);
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
                this.getInitData();
              } else {
                this.$message.error("获取数据失败")
              }
            })
        },
        // 获取评分表
        getInitData() {
            relationList({type: 'PG01'}).then(res => {
                console.log('获取风险评估表', res);
                if(res.data.status === 200) {
                    this.tableData = res.data.results;
                    let arr = this.formData.assessScoreMap;
                    this.tableData.forEach((item, index) => {
                        for(let k in arr) {
                            if(item.key === k) {
                                item.value = arr[k];
                            }
                        }
                    })
                    res.data.results.forEach(item => {
                        this.pgList.push(item.key);
                    })
                    // console.log(this.pgList)
                } else {
                    this.$message.error(res.data.message);
                }
            })
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
        if (this.formScene == "erfxApprove") {
          return dhApproval(params).then(res => {
            // loading.close()
            // console.log(res,'二级部经理审批')
            return res.data;
          });
        } else if (this.formScene == "yifxApprove"){
          return gmApproval(params).then(res => {
            // loading.close()
            // console.log(res,'一级部经理审批')
            return res.data;
          });
        }
      },
        // 提交
        onSubmit() {
            console.log(this.tableData);
            let params = {};
            params = this.formData;
            let list = {};
            for(let i of this.pgList) {
                console.log('i', i)
                for(let k in this.tableData) {
                    console.log('k', k, this.tableData[k])
                    if(this.tableData[k].key === i) {
                        list[i] = this.tableData[k].value;
                    }
                }
            }
            params.assessScoreMap = list;
            let data = JSON.stringify(params);
            initFlow(data).then(res => {
                console.log('提交表单', res);
                if(res.data.status === 200) {
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
        handelCheck(value) {
            console.log(value);
            // if()
        },
        // 关闭
        toClose() {
          this.$router.push('/dashboard')
        },
        // 获取供应商
        getqueryOneYears() {
            queryOneYears().then(res => {
                // console.log('获取供应商', res)
                this.outsourceno_options = res.data.results
            })
        },
        // 获取一级部门
        filOneDep() {
            let params = {}
            params.depgrade = "003";
            params.isdisabled = "0";
            getDeptList(params).then((res) => {
                console.log('获取部门', res)
                this.depData = res.data.results;
            }).catch(error => {
                this.$message.error(error)
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
.wrapper{ 
    width: 100%;
    height: 100%;
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
.padding {
    padding: 18px;
}
.container {
    background-color: #ffffff;
    width: 100%;
    padding: 15px;
}
.container_title {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 16px;
    font-weight: 600;
}
.container_main {
    display: flex;
    justify-content: center;
    align-items: center;
}
.container_bottom {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
table{
    border-collapse: collapse;
    border: 1px solid;
    margin: 20px auto;
}
tr, td, th {
    border: 1px solid;
    padding: 20px;
}
thead{
    text-align: center;
}
.el-form-item {
    margin-bottom: 0;
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
/* 导航菜单栏 */
  .nav {
    width: 100%;
    /* height: 30px; */
    background-color: #ffffff;
  }
</style>
