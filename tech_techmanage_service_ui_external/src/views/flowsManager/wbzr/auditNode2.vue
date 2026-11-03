<!-- 2级部经理审批 -->
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
          <legend>分管总理审批</legend>
          <table>
            <tr>
              <td>指定分管总经理:</td>
              <td>
                <el-input v-model="form.childmanageruserid" placeholder="分管总经理"></el-input>
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
    <!-- 流程日志 -->
    <flow-logs :processInstId="flowItem.processInstID" ref="log"></flow-logs>
    <flow-btn :liuzhuan="liuzhuan" :flow-item="flowItem" v-if="refresh"></flow-btn>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import store from "@/store";
import flowBtn from "@/views/plugins/flowPlug/FlowBtn";
import {
  liuzhuan,
  save,
  queryDetail,
  getJobid,
  approve2
} from "@/api/integrated/wbryzrlc";
import common from "@/api/common";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
export default {
  name: "auditNode2",
  components: { flowLogs, flowBtn },
  props: {
    /**
     * 流程对象
     */
    flowObj: {
      type: Object,
      default: {}
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
        applyuserid: "",
        insteancetitle: "" // 流程主题
      },
      flowItem: this.flowObj || { status: "" }, //传参对象
      auditUserName: "", // 审核人id
      examine: "", // 审核时间
      refresh: true // 是否刷新组件
    };
  },
  watch: {
    form() {
      this.refresh = false;
      this.$nextTick(_ => {
        this.refresh = true;
      });
    }
  },
  computed: {
    ...mapGetters(["name", "userId"])
  },
  methods: {
    init: function(params) {
      let item = params || this.flowItem;
      this.flowItem = item;
      queryDetail({ instanceid: this.flowItem.processInstID })
        .then(res => {
          console.log("flowItem11", res);
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          console.log("flowItem12", res.data.results);
          res.data.results.suggest = "同意";
          res.data.results.conclusion = "01";
          this.form = res.data.results;

          // 日志
          this.$nextTick(_ => {
            this.$refs.log.init(this.flowItem.processInstID);
          });
          // 获取工号
          getJobid().then(res => {
            this.form.jobid = res.data.results.jobid;
          });
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });

      this.auditUserName = this.name + "(" + this.userId + ")";
      this.examine = new Date().getTime();
    }, // end
    // 流转下一个节点
    liuzhuan: function() {
      // this.flowItem.applydesc = this.flowItem.activityInstName
      let newObje = Object.assign(this.flowItem, this.form);
      let data = common.paramsFormat(newObje);
      approve2(data)
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
    },
    /**
     * 选择审核结果
     */
    changeAudit: function(val) {
      this.form.suggest = "01" === val ? "同意" : "不同意";
    }
  }
};
</script>
