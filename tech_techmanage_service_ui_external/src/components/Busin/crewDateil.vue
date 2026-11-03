<template>
  <div class="dateil-container" >
    <div class="header-container">

    </div>
    <div class="content-container">
      <div class="content-left-container">
        <el-timeline>
          <el-timeline-item></el-timeline-item>
          <el-timeline-item icon="el-icon-more" type="warning" size="large">基本信息</el-timeline-item>
          <el-timeline-item>教育信息</el-timeline-item>
          <el-timeline-item>家庭信息</el-timeline-item>
          <el-timeline-item>其他信息</el-timeline-item>
          <el-timeline-item>调动信息</el-timeline-item>
          <el-timeline-item></el-timeline-item>
        </el-timeline>
      </div>
      <div class="content-right-container">
        <div class="user-info-panel border-solid">
          <div class="user-info-title">
            <span>
              <i class="el-icon-caret-bottom"></i>
              <span>基本信息</span>
            </span>
            <el-button
              type="warning"
              @click="onEditClick"
              size="mini"
              class="user-info-title-edit-btn"
            >编辑</el-button>
          </div>
          <table>
            <tr>
              <td class="td-attr-name">姓名</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.empname"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
              <td class="td-attr-name">工号</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.badge"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">性别</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.gender"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
              <td class="td-attr-name">身份证</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.certno"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">出生日期</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.birthday"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
              <td class="td-attr-name">民族</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.nation"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">户籍所在地</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.domicilelocation"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
              <td class="td-attr-name">现居住地</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.xaddress"
                  :disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">招聘方式</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.recruitmodle"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
              <td class="td-attr-name">职级薪档</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.ranksalary"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                />
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">身高</td>
              <td class="td-attr-center">
                <el-input v-model="user.height" :disabled="disabled" size="mini">
                  <template slot="append">CM</template>
                </el-input>
              </td>
              <td class="td-attr-name">体重</td>
              <td class="td-attr-center">
                <el-input v-model="user.weight" :disabled="disabled" size="mini">
                  <template slot="append">KG</template>
                </el-input>
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">婚姻状况</td>
              <td class="td-attr-center">
                <el-select
                  @change="onMaritalstatus"
                  v-model="user.maritalstatus"
                  placeholder="请选择"
                  :value-key="user.maritalstatus"
                  :disabled="disabled"
                  class="el-input-cus"
                >
                  <el-option
                    v-for="item of hyzkData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
              <td class="td-attr-name">政治面貌</td>
              <td class="td-attr-center">
                <el-select
                  @change="onPoliticcontenance"
                  v-model="user.politiccontenance"
                  placeholder="请选择"
                  :disabled="disabled"
                  class="el-input-cus"
                >
                  <el-option
                    v-for="item of zzmmData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
            </tr>
            <tr v-if="isShowPolit">
              <td class="td-attr-name">所属支部</td>
              <td class="td-attr-center">
                <el-select
                  v-model="user.subordinatebranch"
                  placeholder="请选择"
                  :value-key="user.subordinatebranch"
                  :disabled="disabled"
                  class="el-input-cus"
                >
                  <el-option
                    v-for="item of dzbData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
              <td class="td-attr-name">入党时间</td>
              <td class="td-attr-center">
                <el-date-picker
                  v-model="user.joinpartytime"
                  type="date"
                  placeholder="选择日期"
                  :disabled="disabled"
                  class="el-input-cus"
                />
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">入行时间</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.joindate"
                  :disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
              <td class="td-attr-name">参加工作时间</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.worktime"
                  :disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">部门</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.depName"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
              <td class="td-attr-name">岗位</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.jobName"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">组别</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.groupName"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
              <td class="td-attr-name">办公地点</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.officelocation"
                  :disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">技术专长</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.technicalexpertise"
                  :disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
              <td class="td-attr-name">兴趣爱好</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.interest"
                  :disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
            </tr>
          </table>
        </div>
        <!-- 教育信息 -->
        <div class="education-panel border-solid">
          <div class="user-info-title">
            <span>
              <i class="el-icon-caret-bottom"></i>
              <span>教育信息</span>
            </span>
            <el-button
              type="warning"
              @click="onEditClick"
              size="mini"
              class="user-info-title-edit-btn"
            >编辑</el-button>
          </div>
          <table>
            <tr>
              <td class="td-attr-name">最高学历</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.highestedu"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
              <td class="td-attr-name">最高学位</td>
              <td>
                <el-input
                  v-model="user.highestdegree"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">全日制最高学历</td>
              <td class="td-attr-center">
                <el-input
                  v-model="user.highestfulltimeedu"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
              <td class="td-attr-name">全日制最高学位</td>
              <td>
                <el-input
                  v-model="user.highestfulltimedegree"
                  disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
            </tr>
          </table>
          <!-- 教育列表 -->
          <el-table :data="jyData" border stripe>
            <el-table-column
              label="开始时间"
              prop="starttime"
              align="center"
              :formatter="startTimeFormat"
            ></el-table-column>
            <el-table-column label="结束时间" prop="endtime" align="center" :formatter="endTimeFormat"></el-table-column>
            <el-table-column label="教育类别" prop="edutype" align="center"></el-table-column>
            <el-table-column label="学校" prop="school" align="center"></el-table-column>
            <el-table-column label="专业" prop="major" align="center"></el-table-column>
            <el-table-column label="学习形式" prop="learnmodality" align="center"></el-table-column>
            <el-table-column label="学历" prop="education" align="center"></el-table-column>
            <el-table-column label="学位" prop="degree" align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 家庭信息 -->
        <div class="home-panel border-solid">
          <div class="user-info-title">
            <span>
              <i class="el-icon-caret-bottom"></i>
              <span>家庭信息</span>
            </span>
            <el-button
              type="warning"
              @click="onEditClick"
              size="mini"
              class="user-info-title-edit-btn"
            >编辑</el-button>
          </div>
          <el-table :data="jtData" border fit>
            <el-table-column label="亲属名称" width="90" prop="name" align="center"></el-table-column>
            <el-table-column label="与本人关系" width="80" prop="relationship" align="center"></el-table-column>
            <el-table-column label="性别" width="60" prop="gender" align="center"></el-table-column>
            <el-table-column label="身份证号" prop="certno" align="center"></el-table-column>
            <el-table-column label="出生日期" width="100" prop="birthday" align="center"></el-table-column>
            <el-table-column label="工作单位" width="110" prop="workunit" align="center"></el-table-column>
            <el-table-column label="职务" width="60" prop="post" align="center"></el-table-column>
            <el-table-column label="居住地址" prop="address" align="center"></el-table-column>
            <el-table-column label="手机" width="110" prop="phone" align="center"></el-table-column>
            <el-table-column label="是否为紧急联系人" width="80" prop="flag" align="center"></el-table-column>
          </el-table>
        </div>
        <!-- 家庭 end -->
        <!-- 其他信息 -->
        <div class="other-panel border-solid">
          <div class="user-info-title">
            <span>
              <i class="el-icon-caret-bottom"></i>
              <span>其他信息</span>
            </span>
            <el-button
              type="warning"
              @click="onEditClick"
              size="mini"
              class="user-info-title-edit-btn"
            >编辑</el-button>
          </div>
          <table>
            <tr>
              <td class="td-attr-name">行服尺寸</td>
              <td class="td-attr-center">
                <el-select
                  placeholder="请选择"
                  v-model="user.shirtsize"
                  :disabled="disabled"
                  class="el-input-cus"
                >
                  <el-option
                    v-for="item of shirtSizeData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
              <td class="td-attr-name">车牌号</td>
              <td>
                <el-input
                  v-model="user.carno"
                  :disabled="disabled"
                  size="mini"
                  class="td-attr-input"
                ></el-input>
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">洗衣卡</td>
              <td class="td-attr-center">
                <el-select
                  placeholder="请选择"
                  v-model="user.laundaycard"
                  :disabled="disabled"
                  class="td-attr-input"
                >
                  <el-option
                    v-for="item of xykData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
              <td class="td-attr-name">体检医院</td>
              <td>
                <el-select
                  placeholder="请选择"
                  v-model="user.hospital"
                  :disabled="disabled"
                  class="td-attr-input"
                >
                  <el-option
                    v-for="item of tjyyData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
            </tr>
            <tr>
              <td class="td-attr-name">联谊意愿</td>
              <td class="td-attr-center">
                <el-select
                  placeholder="请选择"
                  v-model="user.fraternalwill"
                  :disabled="disabled"
                  class="td-attr-input"
                >
                  <el-option
                    v-for="item of lyyyData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </td>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td class="td-attr-name">座右铭</td>
              <td class="td-attr-center">
                <el-input type="textarea" v-model="user.motto" :disabled="disabled"></el-input>
              </td>
              <td colspan="2"></td>
            </tr>
          </table>
        </div>
        <!-- 其他 end-->
        <div class="prifx-panl"></div>
      </div>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import { getDicEntry } from "@/api/eosDictEntry";
import { getUserFile, upateUserFile, getImageUrl } from "@/api/userCenter";
export default {
  name: "CrewDateil",
  data() {
    return {
      user: {
        badge: "", // 员工号
        empname: "", // 名称
        gender: "", // 性别
        certno: "", // 身份证
        birthday: "", // 出生日期
        nation: "", // 民族
        domicilelocation: "", // 户籍所在地
        xaddress: "", //现居住地
        recruitmodle: "", //招聘方式
        ranksalary: "保密信息", // 职级薪档
        height: "", // 身高
        weight: "", // 体重
        joindate: "", // 入行时间
        worktime: "", // 参加工作时间
        depName: "", // 
        jobName: "", // 岗位
        groupName: "", // 组别
        officelocation: "", //办公地点
        technicalexpertise: "", //技术专长
        interest: "", //兴趣爱好
        image: "", // 头像
        motto: "" // 座右铭
      },
      disabled: true, // input false 禁用, true 启用
      isShowPolit: false, // 是否显示政治 tr
      dzbData: [], // 党支部数据
      hyzkData: [], // 婚姻状况数据
      zzmmData: [], // 政治面貌
      shirtSizeData: [], //衬衫尺寸
      lyyyData: [], // 联谊意愿
      jyData: [], // 教育数据
      jtData: [], // 家庭数据
      xykData: [], // 洗衣卡数据
      tjyyData: [], // 体检医院
      isShowLyyyData: false, // 是否显示联谊意愿
      avatar: "", // 员工头像
      userId: "182402" // 用户id
    };
  },
  methods: {
    /**
     * 初始数据
     */
    init: function() {
      let self = this;
      // 业务字典查询
      getDicEntry({
        dicttypeid: "ZZMM,SSZB,HYZK,CSCC_M,CSCC_W,XYKLX,TJYY,LYYY"
      })
        .then(res => {
          if (res.data.status !== 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          let data = res.data.results;
          this.initData = data;
          // 所属党支部
          this.dzbData = data.filter(item => {
            return item.dicttypeid == "SSZB";
          });
          // 婚姻状况
          this.hyzkData = data.filter(item => {
            return item.dicttypeid == "HYZK";
          });
          // 政治面貌
          this.zzmmData = data.filter(item => {
            return item.dicttypeid == "ZZMM";
          });
          // 洗衣卡
          this.xykData = data.filter(item => {
            return item.dicttypeid == "XYKLX";
          });
          // 体检医院
          this.tjyyData = data.filter(item => {
            return item.dicttypeid == "TJYY";
          });
          // 联谊意愿
          this.lyyyData = data.filter(item => {
            return item.dicttypeid == "LYYY";
          });

          return data;
        })
        .then(eosData => {
          // 员工档案
          getUserFile(self.userId)
            .then(res => {
              let userData = res.data.results;
              this.user = userData;
              if (null == userData) {
                this.$message.error("您未填写员工档案");
                return false;
              }
              // 生日时间格式化
              userData.birthday = common.dateFormat(
                userData.birthday,
                "yyyy-MM-dd"
              );
              userData.worktime = common.dateFormat(
                userData.worktime,
                "yyyy-MM-dd"
              );
              userData.joindate = common.dateFormat(
                userData.joindate,
                "yyyy-MM-dd"
              );
              // 薪资

              if (this.user.ranksalary == null || this.user.ranksalary === "") {
                this.user.ranksalary = "保密信息";
              }
              // 教育
              this.jyData = res.data.results.jyList;
              // 家庭
              this.jtData = res.data.results.jtList;

              // 政治
              if (
                this.user.politiccontenance == "4" ||
                this.user.politiccontenance == "5"
              ) {
                this.isShowPolit = true;
              } else {
                this.isShowPolit = false;
              }
              // 衬衫尺寸
              this.getShirtSize(eosData, userData);
              // 联谊
              // console.log("this.user.maritalstatus", this.user.maritalstatus);
              if (
                this.user.maritalstatus === "1" ||
                this.user.maritalstatus === "2" ||
                this.user.maritalstatus === "5"
              ) {
                this.isShowLyyyData = false;
              } else {
                this.isShowLyyyData = true;
              }
              // 员工头像地址
              if (null != this.user.image && this.user.image !== "") {
                getImageUrl({ documentno: this.user.image }).then(res => {
                  console.log("员工头像地址", res.data);
                  if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: "error" });
                    return false;
                  }
                  let doc = res.data.results;
                  this.avatar = doc.documentno;
                });
              }
            })
            .catch(err => {
              console.log("error", err);
              this.$message.error("连接超时，请刷新页面1");
            }); //  员工档案 end
        })
        .catch(err => {
          this.$message.error("连接超时，请刷新页面");
        }); // 业务字段查询 end
    }, // 初始数据end
    /**
     * 编辑事件
     */
    onEditClick: function() {},
    /**
     * 衬衫尺寸
     */
    getShirtSize: function(data, user) {
      // 衬衫尺寸
      this.shirtSizeData = data.filter(item => {
        let gender = user.gender == "男" ? "CSCC_M" : "CSCC_W";
        return item.dicttypeid == gender;
      });
    }, // 衬衫尺寸 end
    /**
     * 选择婚姻状况
     */
    onMaritalstatus: function() {
      let state = this.user.maritalstatus;
      if (state === "1" || state === "5" || state === "2") {
        this.isShowLyyyData = false;
      } else {
        this.isShowLyyyData = true;
      }
    },
    /**
     * 选择党员
     */
    onPoliticcontenance: function() {
      let type = this.user.politiccontenance;
      if (type === "4" || type === "5") {
        this.isShowPolit = true;
      } else {
        this.isShowPolit = false;
      }
    },
    /**
     * 时间格式化
     */
    startTimeFormat: function(row, cloumn) {
      let time = common.dateFormat(row.starttime, "yyyy-MM-dd");
      return time;
    },
    endTimeFormat: function(row, cloumn) {
      let time = common.dateFormat(row.endtime, "yyyy-MM-dd");
      return time;
    }
  }
};
</script>
<style >
.content-left-container {
  float: left;
  width: 16.5%;
}
.content-right-container {
  float: right;
  width: 80%;
}
.td-attr-name {
  font-size: 16px;
  color: #909399;
  width: 15%;
  text-align: center;
  height: 50px;
  text-align: left;
}
.td-attr-center {
  width: 30%;
}
.user-info-title {
  margin-top: 10px;
}
.user-info-title-edit-btn {
  float: right;
}
.td-attr-input input {
  border-width: 0px;
  border-radius: 0px;
  width: 200px;
  font-size: 16px;
}
.el-input.is-disabled .el-input__inner {
    color: rgba(0,0,0,0.65);
    
}
.user-info-panel,
.education-panel,
.home-panel,
.other-panel {
  clear: both;
  width: 90%;
}
.home-panel,
.other-panel {
  margin-top: 10px;
}
.border-solid {
  border-top: 1px solid #e9e9e9;
}
.el-drawer__body {
  overflow: auto;
}
.el-drawer__body::-webkit-scrollbar {
  display: none;
}
.prifx-panl {
    margin-top: 20px;
}
</style>

