<!--综合管理部风控征信审核 -->
<template>
  <div>
    <el-form :model="form" ref="form" label-width="80px">
      <el-form-item>
        <fieldset>
          <legend>流程主题名称</legend>
          <table width="100%">
            <tr>
              <td>流程主题名称：</td>
              <td colspan="3">
                <el-input v-model="form.insteancetitle" disabled></el-input>
              </td>
            </tr>
          </table>
        </fieldset>
        <fieldset>
          <legend>个人信息</legend>
          <table width="100%">
            <tr>
              <td>姓名：</td>
              <td>
                <el-input v-model="form.username" placeholder="姓名"></el-input>
              </td>
              <td>性别：</td>
              <td></td>
            </tr>
            <tr>
              <td>身份证类型：</td>
              <td></td>
              <td>证件号码：</td>
              <td>
                <el-input v-model="form.certifnumber" placeholder="证件号码"></el-input>
              </td>
            </tr>
            <tr>
              <td>公司名称：</td>
              <td></td>
              <td>联系电话：</td>
              <td>
                <el-input v-model="form.telephone" placeholder="联系电话"></el-input>
              </td>
            </tr>
            <tr>
              <td>学历：</td>
              <td></td>
              <td>专业信息：</td>
              <td>
                <el-input v-model="form.specialty" placeholder="专业信息"></el-input>
              </td>
            </tr>

            <tr>
              <td>毕业学院：</td>
              <td>
                <el-input v-model="form.school" placeholder="毕业学院"></el-input>
              </td>
              <td>籍贯：</td>
              <td>
                <el-input v-model="form.nativeplace" placeholder="籍贯"></el-input>
              </td>
            </tr>
            <tr>
              <td>配偶姓名：</td>
              <td>
                <el-input v-model="form.partnername" placeholder="配偶姓名"></el-input>
              </td>
              <td>配偶身份证：</td>
              <td>
                <el-input v-model="form.idcardnumber" placeholder="配偶身份证"></el-input>
              </td>
            </tr>
            <tr>
              <td>外包方式：</td>
              <td>
                <el-radio-group v-model="form.outsourcetype">
                  <el-radio label="0">项目外包</el-radio>
                  <el-radio label="1">人力外包</el-radio>
                </el-radio-group>
              </td>
              <td>结算方式：</td>
              <td>
                <el-radio-group v-model="form.usertype">
                  <el-radio label="0">人力</el-radio>
                  <el-radio label="1">项目</el-radio>
                </el-radio-group>
              </td>
            </tr>
            <tr>
              <td>工号：</td>
              <td>
                <el-input v-model="form.jobid" placeholder="工号" disabled></el-input>
              </td>
            </tr>
          </table>
        </fieldset>
      </el-form-item>
      <el-form-item v-if="flowItem.status == 'db'">
        <fieldset>
          <legend>征信和风控查询信息</legend>
          <table>
            <tr>
              <td>征信和风控结果:</td>
              <td>
                <el-select v-model="form.queryresult">
                  <el-option label="通过" value="1"></el-option>
                  <el-option label="不通过" value="0"></el-option>
                </el-select>
              </td>
              <td colspan="2">
                <el-button type="primary">风控排查</el-button>
              </td>
            </tr>
            <tr>
              <td>公安核查:</td>
              <td>
                <el-input v-model="form.policecheck" placeholder="公安核查"></el-input>
              </td>
              <td>法院信息:</td>
              <td>
                <el-input v-model="form.courtinfo" placeholder="法院信息"></el-input>
              </td>
            </tr>
            <tr>
              <td>工商信息:</td>
              <td>
                <el-input v-model="form.businessinfo" placeholder="工商信息"></el-input>
              </td>
              <td>是否重复入职:</td>
              <td>
                <el-input v-model="form.iseverentry" placeholder="是否重复入职"></el-input>
              </td>
            </tr>
            <tr>
              <td>个人征信:</td>
              <td>
                <el-input v-model="form.personcredit" placeholder="个人征信"></el-input>
              </td>
              <td></td>
              <td></td>
            </tr>
          </table>
        </fieldset>
      </el-form-item>
      <el-form-item>
        <fieldset>
          <legend>准入要求</legend>
          <table>
            <tr>
              <td>是否符合准入要求:</td>
              <td>
                <el-select v-model="form.issatisfyrequire">
                  <el-option label="符合" value="0"></el-option>
                  <el-option label="不符合" value="1"></el-option>
                </el-select>
              </td>
            </tr>
          </table>
        </fieldset>
      </el-form-item>

      <el-form-item v-if="flowItem.status == 'db'">
        <fieldset>
          <legend>操作信息</legend>
          <table>
            <tr>
              <td>审核人：</td>
              <td>
                <el-input v-model="auditUserName" placeholder="审核人" disabled></el-input>
              </td>
              <td>审核时间：</td>
              <td>
                <el-date-picker v-model="examine" type="date" placeholder="选择日期时间" disabled></el-date-picker>
              </td>
            </tr>
            <tr>
              <td>审核结果：</td>
              <td>
                <el-radio-group v-model="form.conclusion" @change="changeAudit">
                  <el-radio label="01">同意</el-radio>
                  <el-radio label="02">退回</el-radio>
                </el-radio-group>
              </td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td>审核意见：</td>
              <td colspan="3">
                <el-input type="textarea" v-model="form.suggest" placeholder="审核意见"></el-input>
              </td>
            </tr>
          </table>
        </fieldset>
      </el-form-item>
    </el-form>
    <flow-logs :processInstId="flowItem.processInstID" ref="log"></flow-logs>
    <flow-btn :liuzhuan="liuzhuan" :flow-item="flowItem" :key="new Date().getTime()"></flow-btn>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import flowBtn from "@/views/plugins/flowPlug/FlowBtn";
import {
  liuzhuan,
  queryDetail,
  getJobid,
  approve1
} from "@/api/integrated/wbryzrlc";
import common from "@/api/common";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
export default {
  components: { flowBtn, flowLogs },
  props : {
    flowObj : {
      type : [Array,Object],
      required : true
    }
  },
  data() {
    return {
      form: {
        id: "", // id
        username: "", // 姓名
        certifnumber: "", // 证件号码
        telephone: "", // 联系电话
        specialty: "", // 专业信息
        school: "", // 毕业学院
        nativeplace: "", // 籍贯
        partnername: "", // 配偶姓名
        idcardnumber: "", // 配偶身份证
        isdel: 0,
        outsourcingcategories: 0,
        education: 0,
        jobid: "", // 工号
        childmanageruserid: "", // 分管总经理
        conclusion: "01", // 审核结果
        suggest: "同意", // 审核意见
        applyuserid: "", //
        issatisfyrequire: "", //是否符合准入要求
        personcredit: "通过", //个人征信
        businessinfo: "无", // 工商信息
        policecheck: "无", // 公安核查
        iseverentry: "无", // 是否重复入职
        courtinfo: "无", //法院信息
        queryresult: "", //征信和风控结果
        insteancetitle : '',  // 主题名称
      },
      flowItem: this.flowObj || { status: "" }, //传参对象
      auditUserName: "", // 审核人id
      examine: "", // 审核时间
      btnKey : 0
    };
  },
  computed: {
    ...mapGetters(["name", "userId"])
  },
  mounted() {
    this.init()
  },
  methods: {
    init: function(params) {
      //
      let item = params || this.flowItem;
      this.flowItem = item;
      queryDetail({ instanceid: item.processInstID })
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          res.data.results.suggest = "同意";
          res.data.results.conclusion = "01";
          this.form = res.data.results;
          this.form.personcredit = "通过";
          this.form.businessinfo = "无";
          this.form.policecheck = "无";
          this.form.iseverentry = "无";
          this.form.courtinfo = "无";

          // 日志
          this.$nextTick(_ => {
            this.$refs.log.init(item.processInstID);
          });
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
      this.auditUserName = this.name + "(" + this.userId + ")";
      this.examine = new Date().getTime();
    }, // end
    /**
     * 选择审核结果
     */
    changeAudit: function(val) {
      this.form.suggest = "01" === val ? "同意" : "不同意";
    },
    liuzhuan: function() {
      this.flowItem.conclusion = this.form.conclusion;
      this.flowItem.suggest = this.form.suggest;
      this.flowItem.id = this.form.id;
      this.flowItem.jobid = this.form.jobid;
      this.form.childmanageruserid = '182430'
      let newObj = Object.assign(this.flowItem, this.form);
      let data = common.paramsFormat(newObj);
      approve1(data)
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          this.$alert(res.data.message, { type: "success" });
          // 通知父类 关闭dialog
          this.$parent.visible = false
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
    }
  }
};
</script>

