<!-- 定时器管理 -->
<template>
  <div class="app-container">
    <div class="sreach-container">
      <el-form :model="sreachForm" ref="form" inline>
        <el-form-item label="任务名称">
          <el-input v-model="sreachForm.jobName"></el-input>
        </el-form-item>
        <el-form-item label="类名">
          <el-input v-model="sreachForm.className"></el-input>
        </el-form-item>
        <!-- <el-form-item label="状态">
          <el-input v-model="sreachForm.state"></el-input>
        </el-form-item> -->
        <el-form-item label="备注">
          <el-input v-model="sreachForm.des"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSreach">搜索</el-button>
          <clean-btn :forms="this.sreachForm"/>
        </el-form-item>
      </el-form>
    </div>
    <!-- 搜索 end -->
    <div class="table-container">
      <div>
        <el-button type="primary" class="el-icon-plus" @click="onAdd">添加</el-button>
        <!-- <el-button type="danger" class="el-icon-delete" @click="onDeletes">删除</el-button> -->
      </div>
      <!-- table -->
      <el-table :data="pData" border stripe @row-click="onCheckedRow">
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column prop="jobName" label="任务名称"/>
        <el-table-column prop="expresstion" label="时间规则" width="100"></el-table-column>
        <el-table-column prop="className" label="类名" show-overflow-tooltip/>
        <el-table-column prop="state" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.state == 1">正常</el-tag>
            <el-tag type="primary" v-else-if="scope.row.state == 2">停止</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="des" label="备注"></el-table-column>
        <el-table-column label="操作" width="370">
          <template slot-scope="scope">
            <el-button type="primary" @click="onEdit(scope.row)" size="mini" style="margin-left: 0px">编 辑</el-button>
            <el-button type="danger" @click="onDelete(scope.row.id)" size="mini" style="margin-left: 0px">删 除</el-button>
            <el-button type="success" @click="onEnable(scope.row)" size="mini" style="margin-left: 0px">启 用</el-button>
            <el-button type="danger" @click="onDisable(scope.row)" size="mini" style="margin-left: 0px">停 止</el-button>
            <el-button type="warning" @click="onRunNow(scope.row)" size="mini" style="margin-left: 0px">立即执行</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pager
        :fns="getPager"
        :total="pager.totalNumber"
        :pageSize="pager.size"
        v-model="$attrs"
        ref="pager"
      />
    </div>
    <!-- dialog -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="visibleClose"
      width="30%"
      @before-close="onCancel"
    >
      <el-form :model="compForm" ref="compForm" :rules="rules" label-width="100px">
        <el-form-item label="任务名称" prop="jobName">
          <el-input v-model="compForm.jobName" placeholder="任务名称"></el-input>
        </el-form-item>
        <el-form-item label="时间规则" prop="expresstion">
          <el-input v-model="compForm.expresstion" placeholder="时间规则"></el-input>
        </el-form-item>
        <el-form-item label="类名" prop="className">
          <el-input v-model="compForm.className" placeholder="类名"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="des">
          <el-input type="textares" v-model="compForm.des" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer">
        <el-button @click="onCancel()">取 消</el-button>
        <el-button type="primary" @click="onConfirm()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import common from "@/api/common";
import cleanBtn from "@/components/CleanBtn";
import pager from "@/components/Pager";
import { getDicEntry } from "@/api/eosDictEntry";
import {
  getPager,
  doAdd,
  doUpdate,
  getDetail,
  doDelete,
  doEnable,
  doDisable,
  doRunNowJob
} from "@/api/systemApi/quartzManager";
export default {
  components: { cleanBtn, pager },
  data() {
    return {
      // 分页对象
      pager: {
        totalNumber: 0,
        size: 10,
        index: 1
      },
      // 搜索表单
      sreachForm: {
        jobName: "", // 任务名称
        res: "",
        className: "",
        state: ""
      },
      pData: [], // 分页集合
      visibleClose: false, // dialog 是否关闭
      compForm: {
        id: "",
        jobName: "", // 任務名稱
        des: "", // 备注
        className: "", // 任务类名
        state: "", // 状态
        expresstion: "" //  时间规则
      }, // 组件表单
      dialogTitle: "", // dialog - 标题
      viewWayItems: [], // 显示方式 初始数据
      // 表单验证
      rules: {
        des: [
          { required: true, message: "请输入任务名称", trigger: "blur" },
          { max: 25, message: "长度不能超25个字符", trigger: "blur" }
        ],
        className: [
          { required: true, message: "请输入任务类名", trigger: "blur" },
          { max: 200, message: "长度不能超200个字符", trigger: "blur" }
        ],
        expresstion: [
          { required: true, message: "请输入任务时间规则", trigger: "blur" }
        ]
      }
    };
  },
  mounted() {
    this.init({size: 10, index: 1});
  },
  methods: {
    /**
     * 页面初始化
     */
    init: function(params) {
      let data = common.paramsFormat(params);
      getPager(data)
        .then(res => {
          if (res.data.status != 200) {
            this.$alert(error.message, { type: "error" });
            return false;
          }
          this.pData = res.data.results;
          this.pager.totalNumber = res.data.total;
        })
        .catch(error => {
          this.$alert(error.message, { type: "error" });
        });
    },
    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = this.sreachForm;
      this.pager.size = size;
      this.pager.index = index;
      params.size = size;
      params.index = index;
      this.init(params);
    },
    /**
     * 搜索
     */
    onSreach: function() {
      let params = this.sreachForm;
      params.size = this.pager.size;
      params.index = this.pager.index;
      this.init(params);
    },
    /**
     * 添加
     */
    onAdd: function() {
      let self = this;
      this.$nextTick(_ => {
        self.visibleClose = true;
        self.dialogTitle = "定时器任务添加";
        if (self.$refs.compForm !== undefined) {
          self.$refs.compForm.resetFields();
        }
      });
    },
    /**
     * 编辑
     */
    onEdit: function(row) {
      let self = this;
      let len = Object.keys(row).length;
      if (len <= 0) {
        self.$message.error("请选择一条记录进行操作");
        return false;
      }
      self.compForm.className = row.className;
      self.compForm.createName = row.createName;
      self.compForm.des = row.des;
      self.compForm.expresstion = row.expresstion;
      self.compForm.id = row.id;
      self.compForm.jobName = row.jobName;
      self.compForm.state = row.state;
      self.compForm.triggerName = row.triggerName;
      self.visibleClose = true;
      self.dialogTitle = "定时器任务编辑";
    },
    /**
     * 删除
     */
    onDelete: function(id) {
      this.$confirm("此操作为永久删除数据，确定继续吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        doDelete({ id: id }).then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          } else {
            this.$message.success(res.data.message);
            this.init();
          }
        });
      });
    },
    /**
     * dialog - 确定
     */
    onConfirm: function() {
      let data = common.paramsFormat(this.compForm);
      let http_obj;
      let self = this;
      self.$refs["compForm"].validate(vali => {
        if (vali) {
          if (this.compForm.id != "") {
            http_obj = doUpdate(data);
          } else {
            http_obj = doAdd(data);
          }
          httpClient();
        }
      });

      function httpClient() {
        http_obj
          .then(res => {
            if (res.data.status != 200) {
              self.$alert(res.data.message, { type: "error" });
              return false;
            }
            self.$message.success(res.data.message);
            self.init();
            self.onCancel();
          })
          .catch(error => {
            self.$alert(error, { type: "error" });
          });
      }
    },
    /**
     * dialong - 取消
     */
    onCancel: function() {
      this.visibleClose = false;
      //   common.cleanFrom(this.compForm);
    },
    /**
     * 预加载显示或隐藏预加载数
     */
    onLoadingNumberShow: function() {
      let type = this.compForm.preLoading;
      if (~~type === 1) {
        // 显示
        this.isShowLoadingNumber = true;
      } else {
        // 隐藏
        this.isShowLoadingNumber = false;
        this.compForm.preLoadingNumber = 0;
      }
    },
    /**
     * table 选中行
     */
    onCheckedRow: function(row, colum, event) {
      //   this.checkedRow = row;
    },
    /**
     * 启用
     */
    onEnable(item) {
      if (item.state == 1) {
        this.$message.error("已启用，无需重复操作");
        return false;
      }
      item.state = 1;
      let data = common.paramsFormat(item);
      doEnable(data).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.$message.success(res.data.message);
      });
    },
    /**
     * 停用
     */
    onDisable(item) {
      if (item.state == 2) {
        this.$message.error("已停用，无需重复操作");
        return false;
      }
      item.state = 2;
      let data = common.paramsFormat(item);
      doDisable(data).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.$message.success(res.data.message);
      });
    },
    /**
     * 立即执行
     */
    onRunNow(item) {
      let data = common.paramsFormat(item);
      doRunNowJob(data).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.$message.success(res.data.message);
      });
    } // onRunNow end
  }
};
</script>

