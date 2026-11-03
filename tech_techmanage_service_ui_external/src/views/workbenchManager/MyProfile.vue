<!-- 我的档案 -->
<template>
  <div>
    <el-row>
      <el-col class="el-col-container">
        <div class="header-div-1">
          <div class="header-conter">
            <div style="width:160px;height:160px;margin: auto;    padding-top: 10px;">
              <img class="profile-image" :src="avatar" alt>
            </div>
            <h1 class="header-conter-name" id="name">{{user.empname}}</h1>
            <div
              class="header-conter-title"
              id="title"
            >{{user.depName.substring(user.depName.indexOf('-')+1,user.depName.length)}}-{{user.jobName}}</div>
            <div class="profile">
              <p id="profile_P" style="word-break:break-all;">{{user.motto}}</p>
            </div>
          </div>
        </div>
      </el-col>
      <el-col class="el-col-container like-address-col">
        <div class="header-div-2">
          <ul class="list-line">
            <li>
              <i class="el-icon-message"></i>
              <a href="javascript:void(0);">{{!user.email ? user.badge + '@example.com' : user.email}}</a>
            </li>
            <li>
              <i class="el-icon-phone"></i>
              <a href="javascript:void(0);">{{user.contel}}</a>
            </li>
          </ul>
        </div>
      </el-col>
      <el-col class="page-nav-space-holder jump-tag">
        <div class="header-div-3">
          <ul class="list-line header-div-3-ul">
            <li @click="jumpTab(0)">
              <a href="javascript:void(0);">基本信息</a>
            </li>
            <li @click="jumpTab(1)">
              <a href="javascript:void(0);">其他信息</a>
            </li>
          </ul>
        </div>
      </el-col>
      <!-- 悬浮条 -->
      <template>
        <el-col v-if="showLevitate" class="levitale-container">
          <ul class="list-line levitale-ul">
            <li :class="activeIndex == 0 ? 'active' : ''" @click="jumpTab(0)">
              <a href="javascript:void(0);">基本信息</a>
            </li>
            <li :class="activeIndex == 1 ? 'active' : ''" @click="jumpTab(1)">
              <a href="javascript:void(0);">其他信息</a>
            </li>
          </ul>
        </el-col>
      </template>
      <!-- dialog 图片上传 begin -->
      <template>
        <el-dialog width="30%" title="头像上传" :visible.sync="dialogImageVisble">
          <el-upload
            :action="uploadImageAction()"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            :auto-upload="false"
            :limit="1"
            ref="upload"
            :data="user"
            :on-success="handleUploadImage"
            accept="image/jpegimage/jpg, image/png"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-button type="primary" @click="uploadImage">点我上传</el-button>
          <el-dialog
            width="auto"
            :visible.sync="dialogImageMiniVisble"
            :modal="false"
            style="text-align:center"
          >
            <img :src="dialogImageUrl" alt>
          </el-dialog>
        </el-dialog>
      </template>
      <!-- dialog 图片上传 end -->
      <!-- 基本信息 -->
      <el-col class="el-col-info">
        <el-card class="base-info-container">
          <div class="base-info-operating">
            <a href="javascript:void(0)" @click.stop="showEidtUser(1)" v-show="isShowEidtUser">
              <i class="el-icon-edit"></i>
              编辑
            </a>
            <a href="javascript:void(0)" v-show="isEditUser" @click.stop="showEidtUser(2)">
              <i class="el-icon-close"></i>
              取消编辑
            </a>
            <a href="javascript:void(0)" v-show="isEditUser" @click.stop="saveUserFile">
              <i class="el-icon-check"></i>
              保存
            </a>
            <a href="javascript:void(0)" v-show="isEditUser" @click.stop="clickUploadImage">
              <i class="el-icon-upload"></i>
              上传图片
            </a>
          </div>
          <div class="card-title">
            <h2>基本信息</h2>
          </div>
          <div class="base-info-conter">
            <table
              style=" table-layout: fixed;width: 85%;border-collapse: separate;border-spacing: 0px 10px;text-align: center;vertical-align: middle;"
            >
              <tr>
                <td class="td-right">员工号&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.badge" disabled="disabled" class="el-input-cus"/>
                </td>
                <td class="td-right">姓名&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.empname" disabled="disabled" class="el-input-cus"/>
                </td>
              </tr>
              <tr>
                <td class="td-right">性别&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.gender" disabled="disabled" class="el-input-cus"/>
                </td>
                <td class="td-right">身份证&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.certno" disabled="disabled" class="el-input-cus"/>
                </td>
              </tr>
              <tr>
                <td class="td-right">出生日期&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.birthday" disabled="disabled" class="el-input-cus"/>
                </td>
                <td class="td-right">民族&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.nation" disabled="disabled" class="el-input-cus"/>
                </td>
              </tr>
              <tr>
                <td class="td-right">户籍所在地&nbsp;</td>
                <td class="td-left">
                  <el-input
                    v-model="user.domicilelocation"
                    disabled="disabled"
                    class="el-input-cus"
                  />
                </td>
                <td class="td-right">现居住地&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.xaddress" :disabled="disabled" class="el-input-cus"/>
                </td>
              </tr>
              <tr>
                <td class="td-right">招聘方式&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.recruitmodle" disabled="disabled" class="el-input-cus"/>
                </td>
                <td class="td-right">职级薪档&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.ranksalary" disabled="disabled" class="el-input-cus"/>
                </td>
              </tr>
              <tr>
                <td class="td-right">身高&nbsp;</td>
                <td class="td-left">
                  <el-input
                    type="text"
                    v-model="user.height"
                    :disabled="disabled"
                    class="el-input-cus"
                  >
                    <slot slot="append">CM</slot>
                  </el-input>
                </td>
                <td class="td-right">体重&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.weight" :disabled="disabled" class="el-input-cus">
                    <slot slot="append">KG</slot>
                  </el-input>
                </td>
              </tr>
              <tr>
                <td class="td-right">婚姻状况&nbsp;</td>
                <td class="td-left">
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
                <td class="td-right">政治面貌&nbsp;</td>
                <td class="td-left">
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
                <td class="td-right">所属支部&nbsp;</td>
                <td class="td-left">
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
                <td class="td-right">入党时间&nbsp;</td>
                <td class="td-left">
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
                <td class="td-right">入行时间&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.joindate" disabled="disabled" class="el-input-cus"/>
                </td>
                <td class="td-right">参加工作时间&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.worktime" disabled="disabled" class="el-input-cus"/>
                </td>
              </tr>
              <tr>
                <td class="td-right">部门&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.depName" disabled="disabled" class="el-input-cus"/>
                </td>
                <td class="td-right">岗位&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.jobName" disabled="disabled" class="el-input-cus"/>
                </td>
              </tr>
              <tr>
                <td class="td-right">组别&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.groupName" disabled="disabled" class="el-input-cus"/>
                </td>
                <td class="td-right">办公地点&nbsp;</td>
                <td class="td-left">
                  <el-input
                    v-model="user.officelocation"
                    :disabled="disabled"
                    class="el-input-cus"
                  />
                </td>
              </tr>
              <tr>
                <td class="td-right">技术专长&nbsp;</td>
                <td class="td-left">
                  <el-input
                    v-model="user.technicalexpertise"
                    :disabled="disabled"
                    class="el-input-cus"
                  />
                </td>
                <td class="td-right">兴趣爱好&nbsp;</td>
                <td class="td-left">
                  <el-input v-model="user.interest" :disabled="disabled" class="el-input-cus"/>
                </td>
              </tr>
            </table>
          </div>
        </el-card>
      </el-col>
      <!-- 教育信息 -->
      <el-col class="el-col-info">
        <el-card class="base-info-container">
          <div class="card-title">
            <h2>教育信息</h2>
          </div>
          <div class="base-info-conter">
            <el-row justify="center" :gutter="20" class="education-row">
              <el-col :span="6" class="bg-purple">
                <h3>
                  <strong>最高学历</strong>
                </h3>
                <el-input v-model="user.highestedu" disabled style="width:100%"/>
              </el-col>
              <el-col :span="6" class="bg-purple">
                <h3>
                  <strong>全日制最高学历</strong>
                </h3>
                <el-input v-model="user.highestfulltimeedu" disabled style="width:100%"/>
              </el-col>
              <el-col :span="6" class="bg-purple">
                <h3>
                  <strong>最高学位</strong>
                </h3>
                <el-input v-model="user.highestdegree" disabled style="width:100%"/>
              </el-col>
              <el-col :span="6" class="bg-purple">
                <h3>
                  <strong>全日制最高学位</strong>
                </h3>
                <el-input v-model="user.highestfulltimedegree" disabled style="width:100%"/>
              </el-col>
            </el-row>
            <el-table :data="jyData" border>
              <el-table-column
                label="开始时间"
                prop="starttime"
                align="center"
                :formatter="startTimeFormat"
              ></el-table-column>
              <el-table-column
                label="结束时间"
                prop="endtime"
                align="center"
                :formatter="endTimeFormat"
              ></el-table-column>
              <el-table-column label="教育类别" prop="edutype" align="center"></el-table-column>
              <el-table-column label="学校" prop="school" align="center"></el-table-column>
              <el-table-column label="专业" prop="major" align="center"></el-table-column>
              <el-table-column label="学习形式" prop="learnmodality" align="center"></el-table-column>
              <el-table-column label="学历" prop="education" align="center"></el-table-column>
              <el-table-column label="学位" prop="degree" align="center"></el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
      <!-- 家庭信息 -->
      <el-col class="el-col-info">
        <el-card class="base-info-container">
          <div class="card-title">
            <h2>家庭信息</h2>
          </div>
          <div class="base-info-conter jump-tag">
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
        </el-card>
      </el-col>
      <!-- 其他信息 -->
      <el-col class="el-col-info">
        <el-card class="base-info-container">
          <div class="card-title">
            <h2>其他信息</h2>
          </div>
          <div class="base-info-conter">
            <el-row class="other-row">
              <el-col :span="7" class="other-dark">
                <h3>衬衫尺寸</h3>
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
              </el-col>
              <el-col :span="8" class="other-dark">
                <h3>车牌号</h3>
                <el-input v-model="user.carno" :disabled="disabled" class="el-input-cus"/>
              </el-col>
              <el-col :span="7" class="other-dark">
                <h3>洗衣卡</h3>
                <el-select
                  placeholder="请选择"
                  v-model="user.laundaycard"
                  :disabled="disabled"
                  class="el-input-cus"
                >
                  <el-option
                    v-for="item of xykData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </el-col>
            </el-row>
            <el-row class="other-row">
              <el-col :span="7" class="other-dark">
                <h3>体检医院</h3>
                <el-select
                  placeholder="请选择"
                  v-model="user.hospital"
                  :disabled="disabled"
                  class="el-input-cus"
                >
                  <el-option
                    v-for="item of tjyyData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </el-col>
              <el-col :span="8" class="other-dark" v-show="isShowLyyyData">
                <h3>联谊意愿</h3>
                <el-select
                  v-model="user.fraternalwill"
                  placeholder="请选择"
                  :disabled="disabled"
                  class="el-input-cus"
                >
                  <el-option
                    v-for="item of lyyyData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid"
                  ></el-option>
                </el-select>
              </el-col>
            </el-row>
            <el-row class="other-row">
              <el-col class="other-dark">
                <h3 style="text-align:left">座右铭</h3>
                <el-input type="textarea" v-model="user.motto" :disabled="disabled"></el-input>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
      <el-col class="el-col-info"></el-col>
    </el-row>
  </div>
</template>
<script>
import request from "@/utils/request";
import common from "@/api/common";
import { getDicEntry } from "@/api/eosDictEntry";
import { getUserFile, upateUserFile, getImageUrl } from "@/api/userCenter";
import { mapGetters } from "vuex";
export default {
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
        depName: "", // 部门
        jobName: "", // 岗位
        groupName: "", // 组别
        officelocation: "", //办公地点
        technicalexpertise: "", //技术专长
        interest: "", //兴趣爱好
        image: "", // 头像
        motto: "" // 座右铭
      },
      initData: [], // 业务初始数据
      dzbData: [], // 党支部数据
      hyzkData: [], // 婚姻状况数据
      zzmmData: [], // 政治面貌
      shirtSizeData: [], //衬衫尺寸
      lyyyData: [], // 联谊意愿
      jyData: [], // 教育数据
      jtData: [], // 家庭数据
      xykData: [], // 洗衣卡数据
      tjyyData: [], // 体检医院
      disabled: true, // input false 禁用, true 启用
      showLevitate: false, //显示基本信息导航 绝对定位
      activeIndex: 0, // 浮动条定位高亮
      scrollTop: 0, // 滚动条高度
      isShowEidtUser: true, // 是否显示 编辑
      isEditUser: false, // 是否编辑用户
      isShowPolit: false, // 是否显示政治 tr
      dialogImageVisble: false, // 上传图片dialog
      dialogImageMiniVisble: false, // 显示图片缩略图
      dialogImageUrl: "", // 图片缩略图地址
      avatar: "", // 员工头像
      isShowLyyyData: false // 是否显示联谊意愿
    };
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
    this.init();
  },
  computed: {
    ...mapGetters(["userId"])
  },
  methods: {
    /**
     * 初始数据
     */
    init: function() {
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
        }).then((eosData)=>{

          // 员工档案
        getUserFile(this.userId)
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
            this.getShirtSize(eosData,userData);
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
            this.$message.error("连接超时，请刷新页面");
          }); //  员工档案 end
        })
        .catch(err => {
          this.$message.error("连接超时，请刷新页面");
        }); // 业务字段查询 end

       
    }, // 初始数据end
    /**
     * 衬衫尺寸
     */
    getShirtSize: function(data,user) {
      // 衬衫尺寸
      this.shirtSizeData = data.filter(item => {
        let gender = user.gender == "男" ? "CSCC_M" : "CSCC_W";
        return item.dicttypeid == gender;
      });
    }, // 衬衫尺寸 end
    /**
     * 鼠标滚动事件
     */
    handleScroll: function() {
      // 滚动条的高度
      let scrollTop =
        document.body.scrollTop || document.documentElement.scrollTop;
      this.scrollTop = scrollTop;
      // 可视区高度
      let clientHeight = document.documentElement.clientHeight;
      //   console.log("scrollTop", scrollTop);
      //   console.log("clientHeight", clientHeight);
      if (scrollTop > 400) {
        this.showLevitate = true;
      } else {
        this.showLevitate = false;
      }
      //
      let article = document.querySelectorAll(".jump-tag");
      article.forEach((item, index) => {
        if (scrollTop >= item.offsetTop) {
          //   console.log("item:", index);
          this.activeIndex = index;
        }
      });
    }, // 鼠标滚动事件 end
    /**
     * 漂浮窗切换
     */
    jumpTab: function(index) {
      this.activeIndex = index;
      let that = this;

      let jump = document.querySelectorAll(".jump-tag");
      jump[index].scrollIntoView();
      let total = jump[index].offsetTop;
      //   console.log("纵坐标", total);
      // this.$emit('viewScroll', total)
      let distnce = this.scrollTop;
      // 平滑滚动 时长 500ms 每10ms-跳 共50跳
      let step = (total / 50) >> 0;
      if (total > distnce) {
        // smoothDown()
      } else {
        let newTotal = distnce - total;
        step = (newTotal / 50) >> 0;
        // smoothUp();
      }
    }, // 漂浮窗切换 end
    /**
     *编辑效果切换
     */
    showEidtUser: function(type) {
      if (type === 1) {
        this.isShowEidtUser = false;
        this.isEditUser = true;

        this.disabled = false;
      } else {
        this.isShowEidtUser = true;
        this.isEditUser = false;
        this.disabled = true;
      }
    }, // 编辑效果切换 end
    /**
     * 保存员工档案信息
     */
    saveUserFile: function() {
      upateUserFile(this.user).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.$message({
          type: "success",
          message: res.data.message,
          center: true
        });
      });
    }, // 保存员工档案信息 end
    /**
     * 点击图片上传
     */
    clickUploadImage: function() {
      this.dialogImageVisble = true;
    }, // 图片上传  end
    /**
     * 图片上传
     */
    uploadImage: function() {
      //   console.log("upload", process.env.BASE_API);
      this.$refs.upload.submit();
    }, // 图片上传 end
    /**
     * 图片放大
     */
    handlePictureCardPreview: function(file) {
      this.dialogImageUrl = file.url;
      this.dialogImageMiniVisble = true;
    },

    uploadImageAction: function() {
      return process.env.BASE_API + "/ppmStaffFile/uploadImage";
    },
    /**
     * 图片删除
     */
    handleRemove: function() {}, // 图片删除 end
    /**
     * 图片上传成功回调
     */
    handleUploadImage: function(respose, file) {
      if (respose.status !== 200) {
        this.$alert(respose.message, { type: "error" });
      }
      let self = this;
      this.user.image = respose.results;
      setTimeout(() => {
        self.dialogImageVisble = false;
        self.$refs.upload.clearFiles();
        this.avatar = file.url;
      }, 1000);
    }, // 图片上传成功回调 end
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
  },
  destroyed() {
    window.removeEventListener("scroll", this.handleScroll);
  }
};
</script>
<style scoped>
.el-col-container {
  color: #ffffff;
  text-align: center;
}
.header-div-1 {
  background-color: #00a5bb;
  height: 360px;
}
.header-conter {
  width: 60%;
  height: 100%;
  margin: auto;
  text-align: center;
}
.header-conter .profile-image {
  width: 160px;
  height: 160px;
  top: -80px;
}
.header-conter-name {
  font-size: 48px;
  letter-spacing: 0.5px;
}
.header-conter-title {
  font-size: 28px;
  font-weight: 300;
  color: rgba(255, 255, 255, 0.8);
}
.like-address-col {
  background-color: #008fa1;
}
.header-div-2 {
  width: 50%;
  margin: auto;
  padding: 8px 8px;
}

.levitale-ul {
  width: 88%;
}
.header-div-2 a {
  color: rgba(255, 255, 255, 0.8);
}
.page-nav-space-holder {
  background-color: #007888;
}
.education-row {
  margin-bottom: 10px;
}
.header-div-3 {
  padding: 10px 0 10px 63px;
  width: 50%;
  margin: auto;
  text-align: center;
}
.header-div-3-ul {
  width: 91%;
}
.header-div-3 a {
  color: rgba(255, 255, 255, 0.6);
}

.other-dark {
  margin-right: 20px;
  margin-bottom: 10px;
}
.el-col-info {
  margin-top: 60px;
}
.base-info-container {
  width: 80%;
  margin: auto;
  border-bottom: solid 5px #008fa1;
}
.base-info-operating,
.card-title {
  color: #00bcd4;
}
.card-title {
  text-align: center;
}
.base-info-operating {
  text-align: right;
}
.td-right {
  text-align: right;
}
.td-left {
  text-align: left;
}
.base-info-conter {
  text-align: center;
}
.el-input-cus {
  width: 300px;
}
.list-line {
  list-style: none;
  padding: 0px;
  margin: 0px;
}
.list-line li {
  display: inline-block;
  margin-right: 30px;
}
.list-line li a:hover {
  color: #ffffff;
}
.levitale-container {
  position: fixed;
  z-index: 9999;
  top: 0;
  width: 100%;
  background: #00bcd4;
  color: rgba(255, 255, 255, 0.6);
  height: 70px;
  line-height: 70px;
  text-align: center;
}
.active {
  color: #ffffff;
}
</style>

