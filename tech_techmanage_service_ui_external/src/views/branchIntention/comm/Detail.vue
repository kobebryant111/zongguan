<!-- 详情 -->
<template>
  <div>
    <!-- 基础信息 -->
    <div class="bais-container app-container">
      <p class="font20">
        <strong>基础信息</strong>
      </p>
      <el-form :model="data" ref="form" label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="牵头业务部门">
              <el-input v-model="data.demandDeptName" :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="业务项目经理">
              <el-input v-model="data.busiuserName" :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="意向名称">
              <el-input v-model="data.demandname" :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="意向编号">
              <el-input v-model="data.demandno" :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="优先类型">
              <!-- GLOBAL_PROJECT_3PRIORITY-->
              <el-select v-model="data.priority" placeholder :disabled="disabled">
                <el-option
                  v-for="item in proData"
                  :key="item.dictid"
                  :label="item.dictname"
                  :value="item.dictid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="是否重点项目">
              <el-select v-model="data.majorproject" placeholder :disabled="disabled">
                <el-option
                  v-for="item in focData"
                  :key="item.dictid"
                  :label="item.dictname"
                  :value="item.dictid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="收益类型">
              <!-- GLOBAL_PROJECT_3PRIORITY-->
              <el-input v-model="data.incometype" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="具体收益">
              <el-input v-model="data.mainincomes" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="意向来源">
              <!-- GLOBAL_PROJECT_3PRIORITY-->
              <el-select v-model="data.demandsource" placeholder :disabled="disabled">
                <el-option
                  v-for="item in yxlyItem"
                  :key="item.dictid"
                  :label="item.dictname"
                  :value="item.dictid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="应用类型">
              <el-select v-model="data.apptypes" placeholder :disabled="disabled">
                <el-option
                  v-for="item in appTypeData"
                  :key="item.dictid"
                  :label="item.dictname"
                  :value="item.dictid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="意向发起人">
              <el-input v-model="data.demandUserName" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="提出时间">
              <el-input v-model="data.bringupdate" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="期望上线时间">
              <el-input v-model="data.busiexpect" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">&nbsp;</el-col>
        </el-row>
        <el-row>
          <el-col :span="18">
            <el-form-item label="紧急/重点原因">
              <el-input
                type="textarea"
                v-model="data.majorprojectreason"
                placeholder
                :disabled="disabled"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">&nbsp;</el-col>
        </el-row>
        <el-row>
          <el-col :span="18">
            <el-form-item label="意向描述">
              <el-input type="textarea" v-model="data.demanddesc" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">&nbsp;</el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 系统信息 -->
    <div class="sys-container app-container">
      <p class="font20">
        <strong>系统信息</strong>
      </p>
      <el-form :model="data" ref="form" label-width="150px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="关联主系统">
              <el-input v-model="data.sysName" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="产品经理">
              <el-input v-model="data.prodeuctManagerUserName" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="系统业务主管部门">
              <el-input v-model="data.busiDeptName" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="系统科技主管部门">
              <el-input v-model="data.tectDeptName" placeholder :disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 分摊 -->
    <div class="share-container app-container">
      <p class="font20">
        <strong>科技人力成本分摊</strong>
      </p>

      <el-table :data="shareData" border stripe>
        <el-table-column type="index" :index="indexMethod" label="序号" width="30px"></el-table-column>
        <el-table-column prop="deptname" label="分摊部门" width="150px">
          <template slot-scope="scope">
            <el-input v-model="scope.row.deptname" placeholder v-if="scope.row.falg"></el-input>
            <span v-else>{{scope.row.deptname}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="proportion" label="分摊比例（%）" width="150px">
          <template slot-scope="scope">
            <el-input v-model="scope.row.proportion" placeholder v-if="scope.row.falg"></el-input>
            <span v-else>{{scope.row.proportion}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="managerUserName" label="分摊确认人" width="150px">
          <template slot-scope="scope">
            <el-input v-model="scope.row.managerUserName" placeholder v-if="scope.row.falg"></el-input>
            <span v-else>{{scope.row.managerUserName}}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 预算 -->
    <div class="budget-container app-container">
      <p class="font20">
        <strong>预算信息</strong>
      </p>
      <el-form
        :model="ysForm"
        ref="ysForm"
        label-width="120px"
        :rules='{pretMaterial: [{required: pretMaterialFlag,message: "请选择预审材料",trigger: "change"}],tecworkload: [{ required: true, message: "请填写科技团队工作量", trigger: "blur" },{pattern:/^[1-9][0-9]{0,10}$/, message:"科技团队工作量必须大于0且为正整数",trigger:"blur"}]}'
      >
        <el-row>
          <el-col :span="6">
            <el-form-item label="采购预算">
              <el-input v-model="ysForm.cbudget" placeholder :disabled="disabled">
                <template slot="append">（万元）</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">&nbsp;</el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="业务团队工作量">
              <el-input
                v-model="ysForm.busiworkload"
                placeholder
                class="width100"
                :disabled="disabled"
              >
                <template slot="append">
                  <el-radio-group v-model="ysForm.sworkunit">
                    <el-radio
                      v-for="item in sworkunitData"
                      :key="item.label"
                      :label="item.label"
                      :disabled="disabled"
                    >{{item.title}}</el-radio>
                  </el-radio-group>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="业务人力预算">
              <el-input v-model="ysForm.bbudget" placeholder :disabled="disabled">
                <template slot="append">（万元）</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="科技团队工作量" prop="tecworkload">
              <el-input
                v-model="ysForm.tecworkload"
                placeholder
                class="width100"
                @change="budgetWorkload()"
                :disabled="disabledKJ"
              >
                <template slot="append">
                  <el-radio-group v-model="ysForm.tworkunit">
                    <el-radio
                      v-for="item in sworkunitData"
                      :key="item.label"
                      :label="item.label"
                      :disabled="disabledKJ"
                    >{{item.title}}</el-radio>
                  </el-radio-group>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="科技人力预算">
              <el-input v-model="ysForm.tbudget" placeholder :disabled="disabled">
                <template slot="append">（万元）</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="总工作量">
              <el-input
                v-model="ysForm.sumworkload"
                placeholder
                :disabled="disabled"
                class="width100"
              >
                <template slot="append">
                  <el-radio-group v-model="ysForm.sumworkunit">
                    <el-radio
                      v-for="item in sworkunitData"
                      :key="item.label"
                      :label="item.label"
                      :disabled="disabled"
                    >{{item.title}}</el-radio>
                  </el-radio-group>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6"></el-col>
          <el-col :span="6">&nbsp;</el-col>
          <el-col :span="6">
            <el-form-item label="规模">
              <el-select v-model="ysForm.proscale" placeholder :disabled="disabled">
                <el-option
                  v-for="item in pseData"
                  :key="item.dictid"
                  :label="item.dictname"
                  :value="item.dictid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 预审材料 -->
        <slot name="ysclSlot" v-bind="{ysForm:ysForm}"></slot>
      </el-form>
    </div>
    <!-- 其他 -->
    <div class="other-container app-container">
      <p class="font20">
        <strong>其他信息确认</strong>
      </p>
      <el-form :model="otherForm" ref="otherForm" label-width="520px" label-position="left">
        <el-row>
          <el-col :span="24">
            <el-form-item label prop="issjwbcgxq">
              <label slot="label">
                是否涉及外包采购需求（
                <span style="color:red">该项目涉及外包采购，需在采购系统发起采购申请</span>）
              </label>
              <el-radio-group v-model="otherForm.issjwbcgxq">
                <el-radio
                  v-for="item in issjwbcgxqData"
                  :key="item.label"
                  :label="item.label"
                  :disabled="disabled"
                >{{item.title}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="是否涉及电子印章" prop="issjbhdzyz">
              <el-radio-group v-model="otherForm.issjbhdzyz">
                <el-radio
                  v-for="item in issjwbcgxqData"
                  :key="item.label"
                  :label="item.label"
                  :disabled="disabled"
                >{{item.title}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="是否需要可行性分析" prop="isresearch">
              <el-radio-group v-model="otherForm.isresearch">
                <el-radio
                  v-for="item in isresearchData"
                  :key="item.label"
                  :label="item.label"
                  :disabled="disabled"
                >{{item.title}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 附件 -->
    <div class="file-container app-container">
      <p class="font20">
        <strong>附件信息</strong>
      </p>
      <el-table :data="fileData" border stripe>
        <el-table-column type="index" :index="indexMethod" label="序号" width="50px"></el-table-column>
        <el-table-column prop="docname" label="文件名称"></el-table-column>
        <el-table-column prop="creators" label="上传人"></el-table-column>
        <el-table-column prop="createtime" label="上传时间"></el-table-column>
        <el-table-column prop label="类型" width="150px">
          <template slot-scope="scope">
            <el-tag effcet="dark" v-show="scope.row.objecttype == 'A9'" type="suceess">采购</el-tag>
            <el-tag effcet="dark" v-show="scope.row.objecttype == 'A5'" type="info">可行性</el-tag>
            <el-tag effcet="dark" v-show="scope.row.objecttype == 'A10'" type="warning">电子印章</el-tag>
            <el-tag effcet="dark" v-show="scope.row.objecttype == 'A3'" type="danger">需求</el-tag>
            <el-tag effcet="dark" v-show="scope.row.objecttype == 'A11'" type="primary">预审材料</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="uploadFile(scope.row)">下载</el-button>
            <el-button type="primary" @click="showFile(scope.row)">预览</el-button>
            
            <el-button
              type="primary"
              @click="fileRevmoe(scope)"
              v-if="bps.actionURL == 'yfejbjlsp' && bps.status == 'db' && scope.row.objecttype == 'A11'"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分行科技总经理 -->
    <slot name="kjzjlSlot"></slot>

    <!-- 操作日志 -->
    <div class="app-container" v-show="isShowLog">
      <flow-logs ref="log"></flow-logs>
    </div>
  </div>
</template>
<script>
import {revmoeDocument} from '@/api/branchIntentionApi/fhyx'
import common from "@/api/common";
import { getDicEntry } from "@/api/eosDictEntry";
import flowLogs from "@/views/plugins/flowPlug/FlowLogs";
import Moment from "moment";
import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
let ntkoBrowser = require("@/components/NtkoComponents/ntkobackground2.min")
  .ntkoBrowser;
export default {
  name: "Detail",
  components: { flowLogs, flowLogs, buttonOrange, buttonCommon },
  props: {
    disabledProp: {
      type: Boolean,
      default: true
    },
    isShowLog: {
      type: Boolean,
      default: true
    },
    techFns: {
      type: Function,
      default: () => {}
    }
  },

  data() {
    return {
      data: {},
      yxlyItem: [], // 意向来源
      appTypeData: [], // 应用类型
      proData: [], // 优先类型
      focData: [], // 是否重点项目
      sysData: [], // 关联主系统
      shareData: [], // 成本分摊数据
      pseData: [], // 规模
      fileData: [], // 附件集合

      buttonDel: "删 除", // 按钮值
      buttonKeep: "存草稿", // 按钮值
      buttonCommit: "提交", // 按钮值
      delBtnIf: true, // 是否显示删除按钮 默认不显示
      pretMaterialFlag: true, // 附件上传是否为必填项
      disabledKJ: this.disabledProp, // 科技团队工作量 是否只读，默认：可写
      bps: {}, // 流程对象
      sworkunitData: [
        {
          label: "人天",
          title: "人天"
        },
        {
          label: "人月",
          title: "人月"
        }
      ], // 人天/ 人月
      issjwbcgxqData: [
        {
          label: "02",
          title: "是"
        },
        {
          label: "01",
          title: "否"
        }
      ], // 02=是，01=否 是否涉及外包采购需求
      isresearchData: [
        {
          label: "1",
          title: "是"
        },
        {
          label: "0",
          title: "否"
        }
      ], //  1=是，0=否 是否需要可行性分析
      sworkunit: "", // 人天/ 人月
      disabled: true, // 控件只读
      ysForm: {
        cbudget: "", // 采购预算
        busiworkload: "", // 业务团队工作量
        sworkunit: "", // 人天，月天
        bbudget: "", // 业务人力预算
        tecworkload: "", //科技团队工作量
        tworkunit: "", // 人天，月天
        tbudget: "", // 科技人力预算
        sumworkload: "", // 总工作量
        proscale: "", //规模
        pretMaterial: "" // 预审材料
      }, // 预算表单验证
      otherForm: {
        issjwbcgxq: "", // 是否涉及外包采购需求
        issjbhdzyz: "", //是否涉及电子印章
        isresearch: "0" // 是否需要可行性分析
      } // 其他信息表单验证
    };
  },

  methods: {
    showDateil(initData, bps) {
      let self = this;
      self.bps = bps
      console.log("initData:", initData, "bps:", bps);
      if (self.isShowLog) {
        self.$refs.log.init(bps.processInstID);
      }

      let param = { instanceid: bps.processInstID };
      console.log("bps:", bps);
      console.log("bps", initData);
      self.data = initData;
      self.data.bringupdate = Moment(self.data.bringupdate).format(
        "YYYY-MM-DD HH:mm:ss"
      );
      self.data.busiexpect = Moment(self.data.busiexpect).format("YYYY-MM-DD");
      // 预算
      self.ysForm = self.data;
      // 其他信息
      self.otherForm = self.data;

      self.fileData = self.data.documentList.filter(i => {
        i.createtime = Moment(i.createtime).format("YYYY-MM-DD HH:mm:ss");
        return i;
      });
      self.fileData = self.dataGroupBy(self.fileData, item => {
        return [item.objecttype];
      });
      self.shareData = self.data.sharesList.filter(i => {
        i.falg = false;
        return i;
      });

      // 业务字典
      let eos = {
        dicttypeid:
          "GLOBAL_PROJECT_3PRIORITY,PPM_DEMAND_SOURCE,GLOBAL_PROJECT_DOMAIN,GLOBAL_PROJECT_SCALE,GLOBAL_PROJECT_MAJOR"
      };
      getDicEntry(eos).then(res => {
        const data = res.data.results;
        // 意向来源
        self.yxlyItem = data.filter(ii => {
          return ii.dicttypeid === "PPM_DEMAND_SOURCE";
        });
        // 优先类型
        self.proData = data.filter(ii => {
          return ii.dicttypeid === "GLOBAL_PROJECT_3PRIORITY";
        });
        // 应用类型
        self.appTypeData = data.filter(ii => {
          return ii.dicttypeid === "GLOBAL_PROJECT_DOMAIN";
        });
        // 规模
        self.pseData = data.filter(ii => {
          return ii.dicttypeid === "GLOBAL_PROJECT_SCALE";
        });
        // 是否重点项目
        self.focData = data.filter(ii => {
          return ii.dicttypeid === "GLOBAL_PROJECT_MAJOR";
        });
      });
    }, // end
    /**
     * 删除文件
     */
    fileRevmoe(scope){
      let self = this
      var formData = new FormData();
      console.log('row',scope)
      formData.append("documentno", scope.row.documentno);
      formData.append("isdel", '1');
      console.log('formData',scope.row.documentno)
      revmoeDocument(formData).then(res => {
        console.log('删除文件',res.data)
        // 删除
      self.fileData.splice(scope.$index,
        1
      );
      })
    },
    /**
     * 数据排序
     */
    dataGroupBy(array, f) {
      let groups = {};

      array.forEach((v, k) => {
        let group = JSON.stringify(f(v));
        groups[group] = groups[group] || [];
        groups[group].push(v);
      });
      let map = Object.keys(groups).map(group => {
        return groups[group];
      });

      let arrays = [],
        ys = [],
        kxx = [],
        yz = [],
        cg = [];
      map.forEach((v, k) => {
        // 需求
        if (arrays.length <= 0) {
          arrays = v.filter(ii => {
            return ii.objecttype === "A3";
          });
        }

        // 预审材料
        if (ys.length <= 0) {
          ys = v.filter(ii => {
            return ii.objecttype === "A11";
          });
        }

        // 可行性
        if (kxx.length <= 0) {
          kxx = v.filter(ii => {
            return ii.objecttype === "A5";
          });
        }

        // 电子印章
        if (yz.length <= 0) {
          yz = v.filter(ii => {
            return ii.objecttype === "A10";
          });
        }

        // 采购
        if (cg.length <= 0) {
          cg = v.filter(ii => {
            return ii.objecttype === "A9";
          });
        }
      });
      arrays = arrays.concat(ys, kxx, yz, cg);
      return arrays;
    }, // end
    /**
     * 文件下载
     */
    uploadFile(row) {
      const self = this;
      common.downloadDoc(row.documentno, self);
    },
    /**
     * 预览文件
     */
    showFile(row) {
      this.ntkoYl(row.documentno);
    },
    // ntko预览方法
    ntkoYl(objectId) {
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
      window.ntkoCloseEvent = function() {
        alert("跨浏览器插件应用程序窗口已关闭!");
      };
    },
    /**
     * 序号
     */
    indexMethod(index) {
      return index + 1;
    },
    /**
     * 科技团队工作量
     */
    budgetWorkload() {
      this.techFns();
    }
  }
};
</script>
<style >
.font20 {
  font-size: 18px;
  margin-left: 30px;
}
.width100 {
  width: 300px;
}
.upload-file {
  width: 10%;
  float: left;
}
.upload-file-ts {
  float: left;
  line-height: 30px;
}
</style>

