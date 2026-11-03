<!-- 外包面试流程 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">发起面试</p>
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
    <!-- 主体 -->
    <div class="main-app-container">
      <!-- 面试信息 -->
    </div>

    <!-- bin改 -->
    <!-- 发起面试 -->
    <div class="contentMain contentMain_Add">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">面试信息</p>
      </div>
      <div class="content">
        <el-form :model="interviewerInfo" ref="interviewerInfo">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                <el-col :span="7">
                  <div>
                    <p>部门 *</p>
                    <el-form-item prop="msgdeptno" :rules="[{ required: true, message: '请选择部门' }]">
                      <department-two v-model="interviewerInfo.msgdeptno" :objectcode="objectcode" :objectgrade="objectgrade"
                        :onChange="onChangeDept" ref="dept"></department-two>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                  <div>
                    <p>人力需求 *</p>
                    <el-form-item prop="rlId" :rules="[{ required: true, message: '请选择' }]">
                      <el-select v-model="interviewerInfo.rlId" placeholder="请选择人力需求">
                        <el-option v-for="item in mpOpts" :key="item.id" :label="item.rlxqDet" :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                  <div>
                    <p>面试官 *</p>
                    <el-form-item prop="msgid" :rules="[{ required: true, message: '请选择面试官' }]" ref="msgForm">
                      <!-- <el-cascader v-model="interviewerInfo.msgid" :props="propsMsg" :show-all-levels="false" @change="msgChange"
                        collapse-tags ref="interviewMsg"></el-cascader> -->
                      <!-- 无懒加载 -->
                      <el-cascader ref="cascader" :options="requirePeo_options" :props="msgPeoParams" v-model="msgids"
                        :show-all-levels="false" filterable @change="peoChange" collapse-tags></el-cascader>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>

          <!-- 表格 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">应聘者信息<span class="add_row" @click="eduExperAdd(1)">新增一行</span></p>
                  <el-table :data="interviewerInfo.eeList" style="width: 100%" @row-click="rowClick">
                    <el-table-column prop="msname" label="姓名 *" width="120">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.msname`" :rules="[{ required: true, message: '请填写姓名' }]">
                          <el-input v-model="scope.row.msname" placeholder="请输入" />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="sex" label="性别 *" width="110">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.sex`" :rules="[{ required: true, message: '请选择' }]">
                          <el-select v-model="scope.row.sex" placeholder="请选择">
                            <el-option v-for="item in sexOpts" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="sex" label="出生日期 *" width="160">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.birthday`" :rules="[{ required: true, message: '请选择出生日期' }]">
                          <el-date-picker v-model="scope.row.birthday" type="date" placeholder="请选择日期" value-format="yyyy-MM-dd"
                            :editable="false"></el-date-picker>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="wbsid" label="外包商 *" width="210">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.wbsid`" :rules="[{ required: true, message: '请选择外包商'}]">
                          <el-select v-model="scope.row.wbsid" filterable placeholder="请选择">
                            <el-option v-for="item in outsourceno_options" :key="item.id" :label="item.outsourcname"
                              :value="item.id"></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="position" label="人员类型 *" :render-header="rylxHeader" width="135">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.position`" :rules="[{ required: true, message: '请选择人员类型' }]">
                          <el-select v-model="scope.row.position" placeholder="请选择">
                            <el-option v-for="item in peoForm_options" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="researchers" label="人员属性 *" width="135">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.researchers`" :rules="[{ required: true, message: '请选择人员属性'}]">
                          <el-select v-model="scope.row.researchers" placeholder="请选择">
                            <el-option v-for="item in rysx_options" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="workyears" label="工作年限 *" width="120">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.workyears`" :rules="[{ required: true, message: '请选择工作年限', type: 'number' }]">
                          <el-input-number v-model.number="scope.row.workyears" size="medium" controls-position="right"
                            :min="0" :max="50" @change="inputNumber(scope.row.workyears,scope.$index)" :step="0.5"></el-input-number>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="suggestlevel" label="建议等级 *" width="150">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.suggestlevel`" :rules="[{ required: true, message: '请输选择建议等级' }]">
                          <el-select v-model="scope.row.suggestlevel">
                            <el-option value="1" label="1 (1-3年)"></el-option>
                            <el-option value="2" label="2 (3-5年)"></el-option>
                            <el-option value="3" label="3 (5-7年)"></el-option>
                            <el-option value="4" label="4 (7年以上)"></el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="jl" label="简历 *" width="150" :render-header="renderHeader">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.jl`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                          <!-- <el-input v-model="scope.row.jl" placeholder="请输入" :disabled="true" /> -->
                          <el-upload class="upload-demo" action="Fake Action" accept=".xlsx,.xls" :file-list="fileList"
                            :show-file-list="false" :http-request="uploadFile" :on-change="changeFile" v-if="scope.row.jl == '' || scope.row.jl == undefined ? true : false">
                            <el-button size="small" type="primary">
                              <i class="el-icon-upload2"></i>上传文件
                            </el-button>
                            <!-- <el-input v-model="zhengxin" :disabled="true" v-else></el-input> -->
                          </el-upload>
                          <div class="upload_scss" v-else>
                            <div @click="openNtkoWindow(scope.row.jl)">预览</div>
                            <div @click="jlDownliad(scope.row.jl)">下载</div>
                            <el-upload class="upload-demo againUpLoad" action="Fake Action" accept=".xlsx,.xls"
                              :file-list="fileList" :show-file-list="false" :http-request="uploadFile" :on-change="changeFile">
                              重传
                            </el-upload>
                          </div>

                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="msDate" label="面试日期 *" width="160">
                      <template slot-scope="scope">
                        <el-form-item :prop="`eeList.${scope.$index}.msDate`" :rules="[{ required: true, message: '请选择面试日期', trigger: 'blur' }]">
                          <el-date-picker v-model="scope.row.msDate" type="date" placeholder="请选择日期" value-format="yyyy-MM-dd"
                            :editable="false"></el-date-picker>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column prop="operation" label="操作">
                      <template slot-scope="scope">
                        <div class="operation_add">
                          <el-button @click.native.prevent="deleteRow(scope.$index, interviewerInfo.eeList, scope.row)">
                            <i class="el-icon-delete"></i>
                          </el-button>
                        </div>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </div>
    </div>

    <div class="blank"></div>

    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete>
            </el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper_right_bottom_1_zb">
              <!-- 
                <el-col :span="1.5">
                <button-common :buttonName="buttonKeep" :size="size" :fns="saveInterview" :disabled="avoidRepeat"></button-common>
              </el-col>
              -->
            </div>
            <div>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonCommit" :size="size" :fns="proving"></button-orange>
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>

  </div>
</template>
<script>
  import { getDicEntry } from "@/api/eosDictEntry";
  import common from '@/api/common'
  import { getdeptIdForUsers } from "@/api/userApi/bankUser";
  import { doApply, uploadExcel, queryManpowerdemands, saveDraft, getInterviewScore, download, del } from '@/api/integrated/wbms'
  import wbs from "@/views/plugins/components/WaiBaoShang";
  import departmentTwo from "@/views/plugins/components/DepartmentTwo";
  import employee from "@/views/plugins/components/Employee";
  import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
  import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
  import buttonDelete from '@/components/eleButton/delete' //引入删除按钮
  import { getDeptTwo } from "@/api/systemApi/department";
  import { getWbCompany, queryEmployeeTree } from "@/api/integrated/wbryzrlc";
  import { queryWorkItem } from '@/api/common/workflowApi'; 
  import helpHint from "@/components/helpHint"
  import customBr from "@/components/helpHint/customBr"
  import { doDel, getListAll } from '@/api/common/flowApi';
  import XLSX from 'xlsx';
  import { mapGetters } from "vuex";
  import store from "@/store";
  import Moment from 'moment';
  import request from "@/utils/request";
  // 引入ntko
  let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min").ntkoBrowser;
  import { checkedIsBlack } from "@/api/integrated/wbHmd";
  import { checkedExistEntry } from "@/api/integrated/newWbrz";
  export default {
    name: 'Add',
    components: { wbs, departmentTwo, employee, buttonOrange, buttonCommon, buttonDelete, helpHint, customBr },
    data() {
      var self = this;
      return {
        // 面试官表单
        interviewerInfo: {
          msgdeptno: "", // 部门编号
          msgdeptname: "", // 部门名称
          rlId: '', //选择人力需求
          msgname: '', // 面试官名称
          msgid: '', // 面试官编号
          eeList: [{
            msname: '',
            idcard: '', //身份证号
            sex: '',
            birthday: '',
            wbsid: '', //外包商
            position: '', //人员类型
            researchers: '', //人员属性
            workyears: '1', //工作年限
            suggestlevel: '1',
            jl: '', //简历
            msDate: '' //面试日期
          }]
        },
        msgids: [], //面试官绑定值
        peoForm_options: [ //人员类型形式
          {
            dictid: '',
            dictname: ''
          }
        ],
        outsourceno_options: [{ //外包商
          value: '',
          label: ''
        }, {
          value: '',
          label: ''
        }],
        eeList: [], // 面试者表单
        appData: [{ index: 0 }], //面试者数据
        sexOpts: [], // 性别集合
        mpOpts: [], // 人力需求
        deptOpts: [], // 部门数据
        ivOpts: [], // 面试官数据
        depId: '', //选择面试官时的部门编号
        depNum: '', //从部门组件传来的部门编号
        jobOpts: [], // 岗位数据
        msgData: [{}], // 面试官增加行 数据
        msg: '',
        objectcode: "6000000300",
        objectgrade: "2",
        requirePeo_options: [], //面试官数据
        msgPeoParams: { //需求发起人配置信息
          label: 'text',
          value: 'id',
          children: 'children',
          emitPath: false,
          multiple: true
          // leaf: 'id'
        },
        msgNodes: [], // 面试官选中的数据
        msIndex: 0, // 面试官组件下标
        buttonClose: '关 闭', //关闭按钮名字
        size: 'mini',  //按钮大小
        buttonDel: '删 除',
        buttonKeep: '存草稿',
        buttonCommit: '提 交',
        buttonSure: '确 定',
        delBtnIf: false, //删除按钮是否出现
        navName: '', //导航菜单名字
        successMessage: '', //成功提示
        errorMessage: '', //失败提示
        avoidRepeat: false, //避免重复点击存草稿
        ifFinish: true, //是否显示上传文件
        fileList: [], //上传文件列表
        docmId: '', //文件上传成功获得的编号
        currentRow: {}, //当前行
        rules: {
          depno: [
            { required: true, message: '请选择需求发起人', trigger: 'change' }
          ],
          rlId: [
            { required: true, message: '请提至供应商时间', trigger: 'blur' }
          ],
          msname: [
            { required: true, message: '请选择人员类型', trigger: 'blur' }
          ],
          usernum: [
            { required: true, message: '请选择需求人数', trigger: 'change' }
          ],
          isadvertise: [
            { required: true, message: '请选择联系招聘', trigger: 'blur' }
          ],
          addreason: [
            { required: true, message: '请填写增加原因', trigger: 'blur' }
          ],
          personrequire: [
            { required: true, message: '请填写人员要求', trigger: 'blur' }
          ],
          supplynames: [
            { required: true, message: '请选择推荐外包商', trigger: 'blur' }
          ],

        },
        propsMsg: {
          lazy: true,
          multiple: true,
          lazyLoad: this.searchMsg,
          emitPath: false,
        },
        id: '', //存草稿主表Id
        instanceid: '', //流程Id
        workItemID: '', //工作项id
        processDefName: '', //流程定义名称
        userIds: this.userId, //当前登录人Id
        usernames: this.username, //当前登录人名字
        bps: {},
        msgDataChange: false,
        rysx_options: [], //人员属性集合
      };
    },
    computed: {
      ...mapGetters(["name", "userId", "deptId"])
    },
    watch: {
      mpOpts(newVal, oldVal) {
        if (oldVal.length != 0) {
          this.interviewerInfo.rlId = '';
        }
      }
    },
    beforeUpdate() {

    },
    created() {
    },
    mounted() {
      if (this.deptId != undefined) {
        this.interviewerInfo.msgdeptno = "dep" + this.deptId;
      }
      let userId = ["emp" + this.userId];
      this.msgids = userId;
      this.$refs.cascader.dropDownVisible = false;
      this.searchEpmlo();
      this.getAllWbs();
    },

    methods: {
      init() {
        let self = this;
        // 查询业务字典
        getDicEntry({ dicttypeid: "BUSI_OUTSOURCE_GENFER,RLWBXQLX,NEWKG_RYSX" }).then(res => {
          if (res.data.status !== 200) {
            this.alter(res.data.message, { type: "error" });
            return false;
          }
          let data = res.data.results;
          // 性别
          // this.sexOpts = res.data.results;
          this.sexOpts = data.filter(item => {
            return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
          });

          //人员类型
          this.peoForm_options = data.filter(item => {
            return item.dicttypeid == "RLWBXQLX" && item.dictid != "CZ";
          });

          // 人员属性
           this.rysx_options = data.filter(item => {
            return item.dicttypeid == "NEWKG_RYSX";
          });

          return data;
        });
      },

      /**
       * 部门改变事件
       */
      onChangeDept(depid) {
        let deptOn = depid.replace(/[a-z]+/g, "");
        this.getRlxq(deptOn);
      },

      // 获取人力需求
      getRlxq(val) {
        let params = {};
        // params.depid = "1800";
        params.depid = val;
        queryManpowerdemands(params)
          .then(res => {
            // this.mpOpts = res.data.results;
            var reg = /([^-]+)$/;
            this.mpOpts = res.data.results.filter(li => {
              // li.rlxqDet = li.username + li.depname.match(reg)[1] + common.dateFormat(li.supplytime, "yyyy-MM-dd");
              li.rlxqDet = li.username + li.depname.match(reg)[1] + Moment(li.supplytime).format('YYYY-MM-DD') + li.usertypename;
              return li;
            })
          })
          .catch(error => {
            this.$message.error(error)
          });
      },

      /**
       * 增加一行 应聘者
       */
      onAddAppline() {
        let leng = this.appData.length;
        this.appData.push({ index: leng });
      },

      /**
       * 面试官增加行
       */
      onMsAddLine() {
        this.msgData.push({});
      },

      // 获取部门
      getDept() {
        const self = this;
        // 2级部门
        getDeptTwo({
          objectcode: this.objectcode,
          objectgrade: this.objectgrade
        }).then(res => {
          if (res.data.status !== 200) {
            this.alter(res.data.message, { type: "error" });
            return false;
          }
          // 进行过滤
          let data = res.data.results;
          let name = data[0].text;
          self.deptOpts = data[0].children.filter(tt => {
            tt.text = tt.text.replace(name + "-", "");
            return tt;
          });
        }); // end
      },

      // 级联懒加载查询面试官
      searchMsg(node, resolve) {
        const { level } = node;
        const { data } = node;
        // let parentId = data ? data.value : "0";
        let parentId = data != undefined ? data.value : "dep1800";
        this.depId = parentId;
        if (!parentId) {
          return false;
        }
        let id = parentId.replace(/[a-z]/g, "");
        // 面试官
        // getdeptIdForUsers(id).then(res => {
        // let nodes = level === 0 ? [{ value: "id", label: "text" }] : [];
        let nodes = [];
        if (level == 0) {
          const self = this;
          // 2级部门
          getDeptTwo({
            objectcode: this.objectcode,
            objectgrade: this.objectgrade
          }).then(res => {
            if (res.data.status !== 200) {
              this.alter(res.data.message, { type: "error" });
              return false;
            }
            // 进行过滤
            let data = res.data.results;
            let name = data[0].text;
            self.deptOpts = data[0].children.filter(tt => {
              tt.text = tt.text.replace(name + "-", "");
              return tt;
            });

            this.$nextTick(() => {
              if (this.deptOpts && Array.isArray(this.deptOpts)) {
                for (let item of this.deptOpts) {
                  nodes.push({
                    value: item.id,
                    label: item.text,
                    leaf: level >= 1
                  })
                }
                resolve(nodes);
              }

            })
          }); // end

        } else if (level == 1) {
          getdeptIdForUsers(id).then(res => {
            var msgOpts = res.data.results[0].children;
            if (msgOpts && Array.isArray(msgOpts)) {
              // for (let item of msgOpts) {
              //   nodes.push({
              //     value: item.pcode,
              //     label: item.text,
              //     leaf: level >= 1
              //   })
              // }
              nodes = msgOpts.map((item, i) => ({
                value: item.pcode,
                label: item.text,
                leaf: level >= 1
              }))
              resolve(nodes);
            }
          });
        }
        // });
      },

      // 查询行员
      searchEpmlo() {
        let params = '';
        params = "7004";
        queryEmployeeTree(params)
          .then(res => {
            this.organizers_options = res.data.results;

            let data = res.data.results;
            let name = data[0].text;
            this.requirePeo_options = data[0].children.filter(tt => {
              tt.text = tt.text.replace(name + "-", "");
              return tt;
            });
          })
          .catch(error => {
            this.$message.error(error)
          });
      },

      // 面试官变化
      peoChange(node) {
        this.msgDataChange = true;
        if (node.length != 0) {
          // this.msgNodes = this.$refs['cascader'].getCheckedNodes();
          // 2021.1.19改
          let msgNodes = this.$refs['cascader'].getCheckedNodes();
          let msgids = [];
          let magNames = [];
          if (msgNodes.length != 0) {
            msgids = msgNodes.map((value) => {
              return value.data.pcode
            })
            magNames = msgNodes.map((value) => {
              return value.data.text
            })
          }
          this.interviewerInfo.msgid = msgids.join(',');
          this.interviewerInfo.msgname = magNames.join(',');
        } else {
          this.userIds = '';
          this.usernames = '';
          // 2021.1.19改
          this.interviewerInfo.msgid = '';
          this.interviewerInfo.msgname = '';
        }
        this.$refs['interviewerInfo'].validateField('msgid');
      },

      // 查询外包商
      getWbcom() {
        getWbCompany()
          .then(res => {
            this.outsourceno_options = res.data.results;
          })
          .catch(error => {
            this.$message.error(error)
          });
      },

      // 查询所有外包商
      getAllWbs() {
        getListAll()
          .then(res => {
            this.outsourceno_options = res.data.results;
          })
          .catch(error => {
            this.$message.error(error)
          });
      },

      // 计数器内容转换
      inputNumber(currentVal, index) {
        if(currentVal == undefined){
          currentVal = 0;
        }
        var currentVal = String(currentVal);
        var re = /([0-9]+\.[0-9]{1})[0-9]*/
        let workTime = currentVal.replace(re, "$1");
        let point = workTime.split(".");
        if (point[1] == "5") {
          // this.currentRow.workyears = workTime;
          this.interviewerInfo.eeList[index].workyears = workTime;
        } else {
          // this.currentRow.workyears = point[0];
          this.interviewerInfo.eeList[index].workyears = point[0];
        }

        let workTimeNum = Number(this.interviewerInfo.eeList[index].workyears);
        if (workTimeNum >= 1 && workTimeNum < 3) {
          this.interviewerInfo.eeList[index].suggestlevel = "1";
        } else if (workTimeNum >= 3 && workTimeNum < 5) {
          this.interviewerInfo.eeList[index].suggestlevel = "2";
        } else if (workTimeNum >= 5 && workTimeNum < 7) {
          this.interviewerInfo.eeList[index].suggestlevel = "3";
        } else if (workTimeNum >= 7) {
          this.interviewerInfo.eeList[index].suggestlevel = "4";
        } else {
          this.interviewerInfo.eeList[index].suggestlevel = "";
        }
      },

      // excel上传
      // 上传文件
      uploadFile(file) {
      },

      // 上传文件状态改变
      changeFile(file, fileList) {
        let nowDate = new Date();
        // let objectNo = Moment(nowDate.toLocaleString()).format('YYMMDDHH');
        let objectNo = nowDate.getTime();
        var formData = new FormData();
        formData.append("file", file.raw);
        formData.append("objectNo", objectNo);//唯一编号
        formData.append("type", "RLWBXQ");
        formData.append("catalogNo", "CATALOG2019043000001");
        let data = formData;
        const loading = common.loading(this);
        uploadExcel(data).then(res => {
          loading.close();
          this.importfxx(file.raw);
          if (res.data.status == "200") {
            this.ifFinish = false;
            this.docmId = res.data.results;
            this.currentRow.jl = res.data.results;
            this.successMessage = "文件上传成功";
            this.messageSuccess();
          } else {
            this.errorMessage = "文件上传失败";
            this.messageError();
          }
        });
      },

      // 获取上传文件信息
      importfxx(obj) {
        let _this = this;
        var f = obj;
        var reader = new FileReader();
        FileReader.prototype.readAsBinaryString = async function (f) {
          var binary = "";
          var wb;
          var outdata;
          var reader = new FileReader();
          reader.onload = async function (e) {
            var bytes = new Uint8Array(reader.result);
            var length = bytes.byteLength;
            for (var i = 0; i < length; i++) {
              binary += String.fromCharCode(bytes[i])
            }
            wb = XLSX.read(binary, {
              type: 'binary',
            });
            outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]); //生产jsons数据
            var reg = /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9X]$/;
            if (reg.test(outdata[3].__EMPTY_1)) {
              if (common.checkSfzhLast(outdata[3].__EMPTY_1)) {
                _this.currentRow.birthday = common.getBirthdayFromIdCard(outdata[3].__EMPTY_1);
                _this.currentRow.idcard = outdata[3].__EMPTY_1;
                _this.searchMsinfo(outdata[3].__EMPTY_1, outdata[1].__EMPTY_1);
              } else {
                await _this.$message({
                  showClose: true,
                  message: '身份证号错误或格式有误，请检查',
                  type: 'error'
                })
                return
              }
            } else {
              await _this.$message({
                showClose: true,
                message: '身份证号格式有误',
                type: 'error'
              })
              return
            }

            if (outdata[1].__EMPTY_1 != undefined) {
              _this.currentRow.msname = outdata[1].__EMPTY_1;
            } else {
              // await _this.$message.error("简历姓名有误");
              await _this.$message({
                showClose: true,
                message: '简历姓名有误',
                type: 'error'
              })
            }
            if (outdata[1].__EMPTY_3 == "男" && outdata[1].__EMPTY_3 != undefined) {
              _this.currentRow.sex = "0";
            } else if (outdata[1].__EMPTY_3 == "女" && outdata[1].__EMPTY_3 != undefined) {
              _this.currentRow.sex = "1";
            } else {
              // await _this.$message.error("简历性别格式有误");
              await _this.$message({
                showClose: true,
                message: '简历性别格式有误',
                type: 'error'
              })
            }
            if (outdata[5].__EMPTY_5 != undefined && typeof (outdata[5].__EMPTY_5) == 'number') {
              _this.currentRow.workyears = outdata[5].__EMPTY_5;
              let workTimeNum = outdata[5].__EMPTY_5;
              if (workTimeNum >= 1 && workTimeNum < 3) {
                _this.currentRow.suggestlevel = "1";
              } else if (workTimeNum >= 3 && workTimeNum < 5) {
                _this.currentRow.suggestlevel = "2";
              } else if (workTimeNum >= 5 && workTimeNum < 7) {
                _this.currentRow.suggestlevel = "3";
              } else if (workTimeNum >= 7) {
                _this.currentRow.suggestlevel = "4";
              } else {
                _this.currentRow.suggestlevel = "";
              }
            } else {
              // await _this.$message.error("工作年限格式有误");
              await _this.$message({
                showClose: true,
                message: '工作年限格式有误',
                type: 'error'
              })
            }
            _this.interviewerInfo.eeList.splice(0, 0);

          };
          reader.readAsArrayBuffer(f);
        };
        reader.readAsBinaryString(f);
      },

      // 查询面试信息流程ID
      searchMsinfo(id, name) {
        checkedExistEntry(id).then(res => {
          if (res.data.status == 200) {
            this.checkHmd(id, res.data.results, name)
          } else {
            this.$message.error('查询面试信息失败')
          }

        }).catch(error => {
          this.$message.error(error)
        })
      },

      // 检查是否在黑名单中
      checkHmd(id, ifCf, name) {
        checkedIsBlack({ icard: id }).then(res => {
          if (res.data.status == 200) {
            // console.log("黑名单", res.data)
            if (ifCf != undefined && res.data.results == true) {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + '重复面试/已被列入黑名单',
                type: 'error'
              })
            } else if (ifCf != undefined && res.data.results != true) {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + '重复面试',
                type: 'error'
              })
            } else if (ifCf == undefined && res.data.results == true) {
              this.$message({
                showClose: true,
                duration: 0,
                message: name + '已被列入黑名单',
                type: 'error'
              })
            }
          } else {
            this.$message.error('查询黑名单信息失败');
          }
        })
      },

      // 文档下载(改)
      jlDownliad(docId) {
        const self = this;
        common.downloadJl(docId, self);
      },

      // 表格行被点击时触发
      rowClick(row) {
        this.currentRow = row;

      },

      // 新增一行
      eduExperAdd(v) {
        var list;
        list = {
          msname: '',
          idcard: '', //身份证号
          sex: '',
          birthday: '',
          wbsid: '', //外包商
          position: '', //人员类型
          researchers: '', //人员属性
          workyears: '1', //工作年限
          suggestlevel: '1',
          jl: '', //简历
          msDate: '', //面试日期
          id: '' //表ID
        };
        this.interviewerInfo.eeList.push(list);

      },

      // 删除行
      deleteRow(index, rows, row) {
        if (rows.length != 1 && row.id == "") {
          rows.splice(index, 1);
        } else if (rows.length != 1 && row.id != "") {
          this.delTableData(index, rows, row);
        }
        else {
          this.errorMessage = '不能少于一行'
          this.saveError();
        }

      },

      // 删除表中数据
      delTableData(index, rows, row) {
        let params = {};
        params.id = row.id;
        params.instanceid = this.instanceid;
        let data = common.paramsFormat(params);
        del(data).then(res => {
          if (res.data.status == 200) {
            rows.splice(index, 1);
          } else {
            this.errorMessage = '删除失败'
            this.saveError();
          }

        });
      },

      /**
       * 提交
       */
      onSubmit() {
        const loading = common.loading(this);
        const that = this;
        that.interviewerInfo.instanceid = that.instanceid;
        that.interviewerInfo.id = that.id;
        that.interviewerInfo.bps = that.bps;
        that.interviewerInfo.msgdeptno = that.interviewerInfo.msgdeptno.replace(/[a-z]/g, '')
        // console.log('提交表单', that.interviewerInfo)
        if(that.interviewerInfo.msgid.indexOf(',') === -1) {
          loading.close();
          return this.$message.error("请选择两位或两位以上面试官")
        }
        doApply(that.interviewerInfo).then(res => {
          loading.close();
          if (res.data.status == "200") {
            let message = common.getFlowMessgeNew(res.data.message);
            let messageArr = message.split(",");
            this.$nextTick(() => {
              this.messageNotify(messageArr);
              this.$router.push({ name: 'wbmsManager' });
            })
          } else {
            this.errorMessage = "操作失败";
            this.messageError();
          }
        })
      },

      // 存草稿
      saveInterview() {
        const loading = common.loading(this);
        let that = this;
        if (this.msgDataChange == false && this.userId.charAt(0) != 'k' && this.userId.charAt(0) != 'l') {
          this.interviewerInfo.msgid = this.userId;
          this.interviewerInfo.msgname = this.name + '(' + this.userId + ')';
        }
        that.interviewerInfo.msgdeptno = that.interviewerInfo.msgdeptno.replace(/[a-z]/g, '')
        that.interviewerInfo.instanceid = that.instanceid;
        that.interviewerInfo.id = that.id;
        this.$refs['interviewerInfo'].validateField('msgid', (valid) => {
          if (!valid) {
            saveDraft(that.interviewerInfo).then(res => {
              loading.close();
              if (res.data.status == 200) {
                this.instanceid = res.data.results.instanceId;
                this.$nextTick(_ => {
                  this.getInterviewDetail();
                  this.getWorkItem();
                });
                this.successMessage = "保存草稿成功";
                this.messageSuccess();
              } else {
                this.errorMessage = "保存草稿失败";
                this.messageError();
              }
            }).catch();
          } else {
            loading.close();
            this.$message.error('请填写面试官');
            return
          }
        });

      },

      //获取待办工作项详情-单条
      getWorkItem() {
        let params = {};
        params.processInstID = this.instanceid;
        queryWorkItem(params).then(res => {
          this.bps = res.data.results;
          this.processDefName = res.data.results.processDefName;
          this.workItemID = res.data.results.workItemID;
        });
      },

      // 获取保存的需求详细信息
      getInterviewDetail() {
        let params = {};
        params.instanceid = this.instanceid;
        getInterviewScore(params)
          .then(res => {
            var detailData = res.data.results
            if (res.data.status == 200) {
              this.delBtnIf = true;
              detailData.eeList = detailData.eeList.filter(li => {
                if (li.wbsid != undefined) {
                  li.wbsid = Number(li.wbsid);
                  return li;
                } else {
                  return li;
                }
              })
              this.$nextTick(() => {
                this.interviewerInfo.eeList = detailData.eeList.filter(li => {
                  if (li.msname == undefined) {
                    li.msname = '';
                  }
                  if (li.sex == undefined) {
                    li.sex = '';
                  }
                  if (li.position == undefined) {
                    li.position = '';
                  }
                  if (li.researchers == undefined) {
                    li.researchers = '';
                  } else {
                    li.researchers = li.researchers.toString();
                  }
                  if (li.wbsid == undefined) {
                    li.wbsid = '';
                  }
                  if (li.workyears == undefined) {
                    li.workyears = 0;
                  }
                  if (li.msDate == undefined) {
                    li.msDate = '';
                  }
                  if (li.birthday == undefined) {
                    li.birthday = '';
                  }
                  return li;
                });
              })
            } else {
              this.errorMessage = "获取数据失败";
              this.messageError();
            }

          })
          .catch(error => {
            this.$message.error(error)
          });  // end
      },

      // 提交确定并验证
      proving() {
        if (this.msgDataChange == false && this.userId.charAt(0) != 'k' && this.userId.charAt(0) != 'l') {
          // console.log('提交确定并验证', this.userIds)
          this.interviewerInfo.msgid = this.userId;
          this.interviewerInfo.msgname = this.name + '(' + this.userId + ')';
        }
        // else {
        // let msgids = [];
        // let magNames = [];
        // if (this.msgNodes.length != 0) {
        //   msgids = this.msgNodes.map((value) => {
        //     return value.data.pcode
        //   })
        //   magNames = this.msgNodes.map((value) => {
        //     return value.data.text
        //   })
        // }
        // this.interviewerInfo.msgid = msgids.join(',');
        // this.interviewerInfo.msgname = magNames.join(',');
        //}
        // 验证身份证号
        for (var i = 0; i < this.interviewerInfo.eeList.length; i++) {
          if (this.interviewerInfo.eeList[i].idcard == undefined || this.interviewerInfo.eeList[i].idcard == '') {
            this.$message.error("未上传简历或者简历身份证格式有误");
            return;
          }
        }
        this.$refs['interviewerInfo'].validate((valid) => {
          if (valid) {
            this.onSubmit();
          } else {
            this.$message({
              showClose: true,
              message: '操作失败,请填写必填项',
              type: 'error'
            })
            return false;
          }
        })
      },

      // 打开删除messageBox窗口
      delBox() {
        this.$confirm('确认删除该流程吗?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.delPro();

        }).catch(() => {

        })
      },

      // 删除流程
      delPro() {
        const loading = common.loading(this);
        let params = {};
        params.processInstID = this.instanceid;
        params.workItemID = this.workItemID;
        params.processDefName = this.processDefName;
        let data = common.paramsFormat(params);
        doDel(data).then(res => {
          // 关闭loading
          loading.close()
          if (res.data.status == 200) {
            this.successMessage = '流程删除成功'
            this.messageSuccess();
            this.$router.push({ name: 'wbmsManager' });
          } else {
            this.errorMessage = '流程删除失败'
            this.messageError();
          }

        });
      },

      // ntko
      openNtkoWindow(objectId) {
        var url = window.location.href;
        let ntkoed = ntkoBrowser.ExtensionInstalled();
        if (ntkoed) {
          ntkoBrowser.openWindow(
            url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
          );
        } else {
          window.open(
            url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
          );
        }
        // 注册回调方法
        window.ntkoCloseEvent = function () {
          alert("跨浏览器插件应用程序窗口已关闭!");
        };
      },

      // 关闭按钮
      close() {
        this.$router.push({ name: 'wbmsManager' });
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

      // 成功消息提示
      messageSuccess() {
        this.$message({
          message: this.successMessage,
          type: 'success'
        })
      },

      // 失败消息提示
      messageError() {
        this.$message.error(this.errorMessage);
      },

      // 自定义简历右边图标
      renderHeader() {
        return this.$createElement('helpHint', {
          props: {
            content: '导入简历可以填充前面的字段',
            name: '简历 *'
          }
        })
      },

      // 自定义人员类型右边图标
      rylxHeader() {
        return this.$createElement('customBr', {
          props: {
            name: '人员类型 *'
          }
        })
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
    width: 100px;
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

  .content {
    width: 100%;
    font-size: 16px;
    /* background-color: #ffffff; */
    /* padding: 15px 20px; */
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

  .upload_scss div {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
    cursor: pointer;
  }

  /* 重传 */
  .againUpLoad {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
  }

  .wrapper_right_bottom_1_zb {
    margin-right: 10px;
  }
</style>

<style>
  /* 级联选择器 */
  .contentMain_Add .el-cascader {
    display: block;
  }

  /* 下拉框 */
  .contentMain_Add .el-select {
    display: block;
  }

  /* 表格删除按钮 */
  .contentMain_Add .el-icon-delete {
    width: 11px;
    height: 11px;
    color: #f56c6c;
  }

  .operation_add .el-button {
    border: 1px solid #ffffff;
    padding: 0;
  }

  /* table表格修改 */

  .row_2_add .el-table th {
    background: rgba(250, 250, 250, 1);
  }

  .row_2_add .el-table thead {
    font-size: 14px;
    color: rgba(38, 38, 38, 1);
    font-weight: 400;
  }

  .row_2_add .el-table--medium th {
    padding: 0;
  }

  .row_2_add .el-table--medium td {
    padding: 0;
  }


  .row_2_add .el-table th {
    padding: 10px 0;
  }

  /* form表单样式修改 */
  .contentMain_Add .el-form-item {
    margin: 16px 0;
  }

  /* 计数器 */
  .contentMain_Add .el-input-number--medium {
    width: 100px;
  }

  /* 上传文件按钮 */

  .tableCon .el-button--primary {
    color: #000000;
    background-color: #ffffff;
    border-color: rgba(0, 0, 0, 0.15);
  }

  /* 重传 */
  .againUpLoad .el-upload--picture-card:hover {
    color: rgba(251, 164, 47, 1);
  }

  .againUpLoad .el-upload:focus {
    color: rgba(251, 164, 47, 1);
  }
</style>