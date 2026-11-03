<!-- 流程节点维护 --> 
<template>
  <div class="app-container">
    <div>
      <el-form :model="sreachForm" ref="form" inline>
        <el-form-item label="流程类型">
          <el-cascader
            :options="folwTypeData"
            v-model="flowdraftnos"
            :props="defaultProps"
            :show-all-levels="false"
            filterable
            clearable
          ></el-cascader>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">搜 索</el-button>
        </el-form-item>
      </el-form>
      <el-button type="success" @click="addNode()">添加</el-button>
      <table-tree
        :data="mData"
        :columns="tableColumns"
        :expandAll="true"
        fit
        border
        :show-header="true"
      >
        <el-table-column prop="flownodename" label="节点名称"></el-table-column>
        <el-table-column prop="flownodecode" label="节点编号"></el-table-column>
        <el-table-column prop="isdispatch" label="是否调度">
          <template slot-scope="scope">
            <span v-for="item in eosData" :key="item.dictid">
              <span v-if="scope.row.isdispatch == item.dictid">{{item.dictname}}</span>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="warning"
              size="mini"
              @click="updateNode(scope.row)"
              v-if="scope.row._level == 2"
            >修改</el-button>
            <el-button
              type="danger"
              size="mini"
              @click="deleteNode(scope.row)"
              v-if="scope.row._level == 2"
            >删除</el-button>
          </template>
        </el-table-column>
      </table-tree>
    </div>
    <!-- dialog -->
    <div>
      <el-dialog
        :title="title"
        :visible.sync="visibleClose"
        width="50%"
        :before-close="dialogCancel"
      >
        <el-form :model="pushForm" ref="pushForm" label-width="80px" :rules="rules">
          <el-form-item label="流程类型" prop="flowdraftno">
            <el-cascader
              ref="cascaderFlowType"
              id="cascaderFlowType"
              :options="folwTypeData"
              v-model="pushForm.flowdraftno"
              :props="defaultProps"
              :show-all-levels="false"
              filterable
              clearable
              @change="queryFlowName($event)"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="节点名称" prop="flownodename">
            <el-input v-model="pushForm.flownodename"></el-input>
          </el-form-item>
          <el-form-item label="是否调度" prop="isdispatch">
            <el-radio-group v-model="pushForm.isdispatch">
              <el-radio v-for="item in pushData" :key="item.id" :label="item.id">{{item.text}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="节点编号" prop="flownodecode">
            <el-input v-model="pushForm.flownodecode"></el-input>
          </el-form-item>
          <el-form-item label="推送消息" class="el-form--inline" prop="isclrxtxx">
            <el-radio-group v-model="pushForm.isclrxtxx">
              <el-radio v-for="item in pushData" :key="item.id" :label="item.id">{{item.text}}</el-radio>
            </el-radio-group>
            <label class="el-form-item__label" style="padding-left: 15px;">模板</label>
            <el-select
              v-model="pushForm.clrxtxxmb"
              placeholder
              :disabled="pushForm.isclrxtxx === '0'"
            >
              <el-option
                v-for="item in tempData"
                :key="item.id"
                :label="item.templatename"
                :value="item.id + ''"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="推送邮件" class="el-form--inline" prop="isclryj">
            <el-radio-group v-model="pushForm.isclryj">
              <el-radio v-for="item in pushData" :key="item.id" :label="item.id">{{item.text}}</el-radio>
            </el-radio-group>
            <label class="el-form-item__label" style="padding-left: 15px;">模板</label>
            <el-select v-model="pushForm.clryjmb" placeholder :disabled="pushForm.isclryj === '0'">
              <el-option
                v-for="item in tempData"
                :key="item.id"
                :label="item.templatename"
                :value="item.id + ''"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="推送短信" class="el-form--inline" prop="isclrdx">
            <el-radio-group v-model="pushForm.isclrdx">
              <el-radio v-for="item in pushData" :key="item.id" :label="item.id">{{item.text}}</el-radio>
            </el-radio-group>
            <label class="el-form-item__label" style="padding-left: 15px;">模板</label>
            <el-select v-model="pushForm.clrdxmb" placeholder :disabled="pushForm.isclrdx === '0'">
              <el-option
                v-for="item in tempData"
                :key="item.id"
                :label="item.templatename"
                :value="item.id + ''"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="推送平台" class="el-form--inline" prop="isclrpt">
            <el-radio-group v-model="pushForm.isclrpt">
              <el-radio v-for="item in pushData" :key="item.id" :label="item.id">{{item.text}}</el-radio>
            </el-radio-group>
            <label class="el-form-item__label" style="padding-left: 15px;">模板</label>
            <el-select v-model="pushForm.clrptmb" placeholder :disabled="pushForm.isclrpt === '0'">
              <el-option
                v-for="item in tempData"
                :key="item.id"
                :label="item.templatename"
                :value="item.id + ''"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer">
          <el-button @click="dialogCancel">取 消</el-button>
          <el-button type="primary" @click="saveClick">保 存</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import common from "@/api/common";
import TableTree from "@/components/TableTree";
import cleanBtn from "@/components/CleanBtn";
import { getFlowTree, getAvailableTemplate } from "@/api/processApi/notice";
import {
  getNodeTree,
  getNodeDateil,
  updatedFlowNode,
  deletFlow
} from "@/api/processApi/flowNode";
import { getDicEntry } from "@/api/eosDictEntry";
export default {
  components: { cleanBtn, TableTree },
  data() {
    return {
      // 搜索 表单
      sreachForm: {
        flowdraftno: ""
      },
      // 推送表单
      pushForm: {
        flowname: "",
        flownodename: "",
        isdispatch: "",
        flownodecode: "",
        isclrxtxx: "0",
        isclryj: "0",
        isclrdx: "0",
        isclrpt: "0"
      },
      mData: [], // 数据集合
      tableColumns: [
        {
          text: "节点名称",
          value: "flowname",
          width: "250"
        }
      ],
      folwTypeData: [], // 流程类型搜索列表
      defaultProps: {
        children: "children",
        label: "flowname",
        value: "flowdraftno"
      },
      flowdraftnos: [], // 流程类型选中的数组
      eosData: [], // 业务字典
      visibleClose: false, // dialog显示
      title: "", // dialog 标题
      pushData: [
        // 推送数据
        { id: "1", text: "是" },
        { id: "0", text: "否" }
      ],
      tempData: [], // 模板数据集合
      // 验证
      rules: {
        flowdraftno: [
          { required: true, message: "流程类型不能为空", trigger: "change" }
        ],
        flownodename: [
          { required: true, message: "节点名称不能为空", trigger: "blur" }
        ],
        isdispatch: [
          { required: true, message: "是否调度不能为空", trigger: "change" }
        ],
        flownodecode: [
          { required: true, message: "节点编号不能为空", trigger: "blur" }
        ],
        isclrxtxx: [
          { required: true, message: "推送消息不能为空", trigger: "change" }
        ],
        isclryj: [
          { required: true, message: "推送邮件不能为空", trigger: "change" }
        ],
        isclrdx: [
          { required: true, message: "推送短信不能为空", trigger: "change" }
        ],
        isclrpt: [
          { required: true, message: "推送平台不能为空", trigger: "change" }
        ]
      }
    };
  },
  mounted() {
    this.init();
    getFlowTree().then(res => {
      this.folwTypeData = res.data.results;
      console.log("folwTypeData:", this.folwTypeData);
    });
    // 业务字典
    getDicEntry({ dicttypeid: "GLOBAL_YESNO" }).then(res => {
      this.eosData = res.data.results;
    });
    // 可用模板
    getAvailableTemplate().then(res => {
      this.tempData = res.data.results;
    });
  },
  methods: {
    // 初始加载
    init() {
      let self = this;
      let loading = common.loading(self);
      let data = common.paramsFormat(self.sreachForm);
      getNodeTree(data).then(res => {
        if (res.data.status != 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        self.mData = res.data.results;
        loading.close();
      });
    },
    onSubmit() {
      let length = this.flowdraftnos.length;
      if (length !== 1) {
        let code = this.flowdraftnos[length - 1];
        this.sreachForm.flowdraftno = code;
      }

      this.init();
    },
    updateNode(item) {
      console.log("item::", item);
      let self = this;
      self.title = "编辑";
      self.visibleClose = true;
      getNodeDateil({ id: item.id }).then(res => {
        if (200 !== res.data.status) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }
        self.pushForm = res.data.results;
        self.init();
      });
    },
    deleteNode(item) {
      let self = this;
      console.log("item::", item);
      this.$confirm("确认删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(_ => {
        deletFlow({ id: item.id }).then(res => {
          if (200 !== res.data.status) {
            self.$alert(res.data.message, { type: "error" });
            return false;
          }
          self.$message.success(res.data.message);
          self.init();
        });
      });
    },
    addNode(item) {
      console.log("item::", item);
      let self = this;
      self.visibleClose = true;
      self.title = "添加";
    },
    /**
     * dialog 取消
     */
    dialogCancel() {
      this.visibleClose = false;
      common.cleanFrom(this.pushForm);
    },
    /**
     * dialog 保存 更新
     */
    saveClick() {
      let self = this;
      self.$refs.pushForm.validate(vaid => {
        if (vaid) {
          console.log(
            "pushForm",
            self.pushForm,
            self.pushForm.flowdraftno instanceof Array
          );
          // 处理 流程类型
          if (self.pushForm.flowdraftno instanceof Array) {
            let length = self.pushForm.flowdraftno.length;
            let code = self.pushForm.flowdraftno[length - 1];
            self.pushForm.flowdraftno = code;
          }
          // console.log("pushForm", self.pushForm,length);
          let data = common.paramsFormat(self.pushForm);
          updatedFlowNode(data).then(res => {
            if (200 !== res.data.status) {
              self.$alert(res.data.message, { type: "error" });
              return false;
            }
            self.$message.success(res.data.message);
            self.dialogCancel();
            self.init();
          });
        } else {
          self.$alert("必填项不能为空");
        }
      });
    }, // end
    queryFlowName(item) {
      let self = this;
      let value = document.getElementById("cascaderFlowType").innerText;
      self.$nextTick(_ => {
        let text = self.$refs["cascaderFlowType"].presentText;
        self.pushForm.flowname = text;
        console.log("item::", item, text);
      });
    }
  }
};
</script>

