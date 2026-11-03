<template>
  <div>
    <el-form :model="form" ref="form" label-width="80px">
      <el-form-item>
        <fieldset v-if="isShowFlowName">
          <legend>流程主题名称</legend>
          <table width="100%">
            <tr>
              <td>流程主题名称：</td>
              <td colspan="3">
                <el-input v-model="flowName" disabled></el-input>
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
              <td>部门：</td>
              <td>
                <el-input v-model="form.deptno" placeholder="部门编号"></el-input>
              </td>
              <td>所属合同：</td>
              <td>
                <el-input v-model="form.ssthid" placeholder="所属合同"></el-input>
              </td>
            </tr>
          </table>
        </fieldset>
      </el-form-item>
    </el-form>
    <flow-btn :liuzhuan="liuzhuan" :save="save" :flow-item="flowItem" :key="new Date().getTime()"></flow-btn>
  </div>
</template>
<script>
import flowBtn from "@/views/plugins/flowPlug/FlowBtn";
import { liuzhuan, save, queryDetail } from "@/api/integrated/wbryzrlc";
import common from "@/api/common";
export default {
  name: "WbryzrAdd",
  components: {  flowBtn },
  props: {
    /**
     * 流程对象
     */
    flowObj: {
      type: Object,
      default: {}
    },
    titleDisabled: {
      type: Boolean
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
        deptno : '1800',   // 部门编号
        ssthid : 'ORGCODE2015021000041_2020',        // 合同编号
        usertype : '',        // 结算方式
        outsourcetype : '',   // 外包方式
      },

      flowName: "外包人员准入申请--(测试123)", // 流程主题名称
      isShowFlowName: this.titleDisabled, // 是否显示流程主题信息块
      flowItem: this.flowObj || {status : 'add'}, //传参对象
    };
  },
  mounted() {},

  methods: {
    /**
     * 初始化 方法
     */
    init: function() {
      // queryDetail({ instanceid: this.flowItem.processInstID })
      //   .then(res => {
      //     if (res.data.status != 200) {
      //       this.$alert(res.data.message, { type: "error" });
      //       return false;
      //     }
      //     console.log("data:111", res.data.results);
      //     this.form = res.data.results;
      //   })
      //   .catch(error => {
      //     this.$alert(error, { type: "error" });
      //   });
    },
    /**
     * 流转
     */
    liuzhuan: function() {
      let data = common.paramsFormat(this.form);
      liuzhuan(data)
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          // 通知父类 关闭dialog
          this.$parent.visible = false
          this.$alert(res.data.message, { type: "success" });
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
    },
    /**
     * 保存
     */
    save: function() {
      let data = common.paramsFormat(this.form);
      save(data)
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(error, { type: "error" });
            return false;
          }
          // 通知父类 关闭dialog
          this.$parent.visible = false
          this.$alert(res.data.message, { type: "success" });
        })
        .catch(error => {
          this.$alert(error, { type: "error" });
        });
    }
  }
};
</script>
