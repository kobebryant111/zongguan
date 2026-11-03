<!-- 花名册基本信息 -->
<template>
  <div class="archives-container-jiben">
    <!-- 无权限 -->
    <div class="archives-main" key="1">
      <div class="archives-title">
        <span class="xkg-color-black archives-title-name">基本信息</span>
      </div>
      <div class="archives-modular">
        <el-row :gutter="30">
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">姓名</p>
            <p>{{jibenData.userName == undefined ? '无' : jibenData.userName}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">工号</p>
            <p>{{jibenData.jobId == undefined ? '无' : jibenData.jobId}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">性别</p>
            <p>{{jibenData.sexname == undefined ? '无' : jibenData.sexname}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">身份证号</p>
            <p>{{jibenData.certificateNumber == undefined ? '无' : jibenData.certificateNumber}}</p>
          </el-col>
        </el-row>
      </div>
      <div class="archives-modular">
        <el-row :gutter="30">
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">联系电话</p>
            <p>{{jibenData.mobile == undefined ? '无' : jibenData.mobile}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">生日</p>
            <p>{{jibenData.birthday == undefined ? '无' : jibenData.birthday}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">婚姻状况</p>
            <p>{{jibenData.maritalStatusname == undefined ? '无' : jibenData.maritalStatusname}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">籍贯</p>
            <p>{{jibenData.hometown == undefined ? '无' : jibenData.hometown}}</p>
          </el-col>
        </el-row>
      </div>
      <div class="archives-modular">
        <el-row :gutter="30">
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">学历</p>
            <p>{{jibenData.educationname == undefined ? '无' : jibenData.educationname}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">毕业院校</p>
            <p>{{jibenData.college == undefined ? '无' : jibenData.college}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">专业信息</p>
            <p>{{jibenData.profession == undefined ? '无' : jibenData.profession}}</p>
          </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">外包商</p>
            <p>{{jibenData.outsourcname == undefined ? '无' : jibenData.outsourcname}}</p>
            <!-- <p class="xkg-color-grey xkg-fontsize-14px">外包方式</p>
            <p>{{jibenData.outsourceTypename == undefined ? '无' : jibenData.outsourceTypename}}</p> -->
          </el-col>
        </el-row>
      </div>
      <div class="archives-modular">
        <el-row :gutter="30">
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">所属部门</p>
            <p>{{jibenData.depname == undefined ? '无' : jibenData.depname}}</p>
          </el-col>
           <el-col :span="6">
              <p class="xkg-color-grey xkg-fontsize-14px">项目经理</p>
              <p>{{jibenData.personInChargename == undefined ? '无' : jibenData.personInChargename}}</p>
           </el-col>
           <el-col :span="6">
              <p class="xkg-color-grey xkg-fontsize-14px">入职时间</p>
              <p>{{jibenData.entryTime == undefined ? '无' : jibenData.entryTime}}</p>
           </el-col>
          <el-col :span="6">
            <p class="xkg-color-grey xkg-fontsize-14px">离场时间</p>
            <p>{{jibenData.leavingTime == undefined ? '无' : jibenData.leavingTime}}</p>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { getDicEntry } from "@/api/eosDictEntry";
import {
  getTree,
  queryResourceDept,
  queryGroup,
  queryEmployeeTree,
  querySystemTree,
  getSystemPeo,
  getProcurements
} from "@/api/integrated/wbryzrlc";
import {
  getListAll,
  getProjectDetail,
  getEmployUser
} from "@/api/common/flowApi";
import { update } from "@/api/integrated/hmcDa";
import common from "@/api/common";
export default {
  name: "hmcJiben",
  data() {
    return {
      ifAuth: false, //是否有权限
      jibenData: {}, //基本数据
      ifEdit: true, //是否可编辑
      sex_options: [], //性别可选项
      marriage_options: [], //婚姻状况可选项
      edu_options: [], //学历可选项
      outsourcetype_options: [], //外包方式可选项
      usertype_options: [], //结算方式可选项
      position_options: [], //岗位可选项
      outsourceno_options: [],
      deptno_options: [],
      outsourcetype_options: [],
      groupid_options: [],
      purchaseItem_options: [],
      projectno_options: [],
      projectnoParams: {
        //项目名称配置信息
        label: "projectname",
        value: "projectno",
        children: "children",
        emitPath: false
      },
      organizers_options: [],
      systemnos_options: [],
      systemParams: {
        //系统名称配置信息
        label: "text",
        value: "pcode",
        children: "children",
        emitPath: false
      },
      xmmcIsEdit: false, //项目名称是否可编辑
      countDep: 0
    };
  },
  props: {
    jibenDetail: {
      type: Object,
      required: true
    },
    auth: {
      type: String,
      required: true
    }
  },
  watch: {
    "jibenData.deptId": function() {
      this.countDep++;
      if (this.countDep > 1) {
        this.jibenData.groupId = "";
        this.jibenData.organizers = "";
      }
      this.searchGroup();
      this.searchEpmlo();
    },
    jibenDetail(val) {
      this.jibenData = val;
    },
    auth(val) {
      if (val == "admin") {
        this.ifAuth = true;
      } else {
        this.ifAuth = false;
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    // 页面初始化
    init: function() {
      getDicEntry({
        dicttypeid:
          "BUSI_OUTSOURCE_GENFER,BUSI_OUTSOURCE_MARITALSTATUS,NEWYY_EDUCATIONEDUCATION,NEWKG_WBFS,NEWKG_JSFS,BUSI_OUTSOURCE_POSITION"
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        let data = res.data.results;
        // 性别
        this.sex_options = data.filter(item => {
          return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
        });
        // 婚姻状况
        this.marriage_options = data.filter(item => {
          return item.dicttypeid == "BUSI_OUTSOURCE_MARITALSTATUS";
        });
        // 学历
        this.edu_options = data.filter(item => {
          return item.dicttypeid == "NEWYY_EDUCATION";
        });

        //外包方式
        this.outsourcetype_options = data.filter(item => {
          return item.dicttypeid == "NEWKG_WBFS";
        });
        //结算方式
        this.usertype_options = data.filter(item => {
          return item.dicttypeid == "NEWKG_JSFS";
        });
        //岗位
        this.position_options = data.filter(item => {
          return item.dicttypeid == "BUSI_OUTSOURCE_POSITION";
        });
        return data;
      });

      this.filDep();
      this.getWbcom();
      // // 加载 采购项目
      // getProcurements().then(res => {
      //   console.log("采购", res.data);
      //   this.purchaseItem_options = res.data.results;
      // });
    },

    //获取所属部门内容
    filDep() {
      let params = {};
      params.objectcode = "6000000300";
      params.objectgrade = "2";
      queryResourceDept(params)
        .then(res => {
            var fild = res.data.results[0].children;
            var reg = /([^-]+)$/;
            this.deptno_options = fild.filter(li => {
              li.text = li.text.match(reg)[1];
              return li;
            });
        }).catch(error => {
          this.$message.error("获取部门数据失败");
        }); // end
    },

    //查询小组名称
    searchGroup() {
      let params = {};
      let depid = this.jibenData.deptId;
      params.depid = depid.replace("dep", "");
      queryGroup(params)
        .then(res => {
          this.groupid_options = res.data.results;
        })
        .catch(error => {
          this.$message.error("查询小组名称失败！");
        });
    },

    // 查询行内负责人
    searchEpmlo() {
      let params = "";
      let depid = this.jibenData.deptId;
      params = depid.replace("dep", "");
      queryEmployeeTree(params)
        .then(res => {
          this.organizers_options = res.data.results[0].children;
        })
        .catch(error => {
          this.$message.error("查询行项目经理失败");
        });
    },

    // 查询所有外包商
    getWbcom() {
      getListAll()
        .then(res => {
          this.outsourceno_options = res.data.results;
        })
        .catch(error => {
          this.$message.error("查询所有外包商失败");
        });
    },

    //没有选择部门时的提示信息
    cueMessage() {
      if (this.jibenData.deptId == "") {
        this.$message("请先选择部门");
      }
    },

    // 获取项目编号
    getProjCode(val) {
      this.searchManager(val);
    },

    // 查询项目经理
    searchManager(val) {
      let params = {};
      params.projectno = val;
      getProjectDetail(params)
        .then(res => {
          if (res.data.status == 200) {
            getEmployUser(res.data.results.manauserid)
              .then(res => {
                if (res.data.status == 200) {
                  this.$set(
                    this.jibenData,
                    "projectmanagername",
                    res.data.results[0].empname
                  );
                  this.$set(
                    this.jibenData,
                    "projectManager",
                    res.data.results[0].badge
                  );
                } else {
                  this.$message.error("查询项目经理失败");
                }
              })
              .catch(error => {
                this.$message.error(error);
              });
          } else {
            this.$message.error("查询项目经理失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 查询系统负责人
    searchSystemPeo(val) {
      let params = {};
      params.sysno = val;
      getSystemPeo(params)
        .then(res => {
          this.$set(
            this.jibenData,
            "systemprincipalnames",
            res.data.results[0].manausername
          );
          this.$set(
            this.jibenData,
            "systemPrincipal",
            res.data.results[0].manauserid
          );
        })
        .catch(error => {
          this.$message.error(error);
        });
    },

    // 岗位变化
    positionChange(val) {
      if (val == "11") {
        this.xmmcIsRequired = false;
      } else {
        this.xmmcIsRequired = true;
      }
    },

    // 项目是否立项
    ifLxChange(val) {
      if (val == true) {
        this.xmmcIsEdit = true;
        // this.jibenData.projectNumber = '';
        this.$set(this.jibenData, "projectNumber", "");
        this.$set(this.jibenData, "projectmanagername", "");
        this.$set(this.jibenData, "projectManager", "");
        // this.jibenData.projectmanagername = '';
      } else {
        this.xmmcIsEdit = false;
      }
    },

    // 系统名称变化
    systemChange(val) {
      this.searchSystemPeo(val);
    },

    // 编辑
    edit() {
      this.ifEdit = false;
    },

    // 保存当前页面
    save() {
      const loading = common.loading(this);
      Object.keys(this.jibenData).forEach(key => {
        if (this.jibenData[key] == undefined) {
          this.jibenData[key] = null;
        }
      });
      let data = common.paramsFormat(this.jibenData);
      update(data)
        .then(res => {
          loading.close();
          if (res.data.status == 200) {
            this.ifEdit = true;
            this.$message.success("保存成功");
          } else {
            this.$message.error("保存失败");
          }
        })
        .catch(error => {
          this.$message.error(error);
        });
    }
  }
};
</script>

<style scoped>
.archives-title-name {
  font-size: 15px;
  font-weight: 600;
}

.archives-title-edit {
  margin-left: 8px;
  cursor: pointer;
}

.archives-title-save {
  cursor: pointer;
}
</style>