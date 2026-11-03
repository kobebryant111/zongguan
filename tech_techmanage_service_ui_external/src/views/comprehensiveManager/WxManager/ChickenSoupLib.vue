<!-- 鸡汤库管理 -->
<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="paramsForm" inline>
        <el-form-item prop="content" label="关键词">
          <el-input placeholder v-model="paramsForm.content"/>
        </el-form-item>
        <el-form-item prop="style" label="分类">
          <el-input v-model="paramsForm.style" readonly>
            <template slot="append">
              <eos-select
                v-model="paramsForm.style"
                id="SOULSTYLE"
                headValue="全部"
                :clear="eosClear"
              ></eos-select>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearch">搜索</el-button>
          <clean-btn :forms="this.paramsForm"/>
        </el-form-item>
      </el-form>
    </div>
    <div class="button-container">
      <el-button type="primary" class="el-icon-plus" @click="handelToAdd">添加</el-button>
      <el-button type="danger" class="el-icon-delete" @click="handelDeletes">删除</el-button>
      <el-button type="success" class="el-icon-download" @click="handelExport">导出</el-button>
      <ex-upload
        :action="excelUploadAction()"
        :success="excelSuccess"
        :error="excelError"
        :change="importExcel"
        clazz="excel-import"
        ref="excelUpload"
      >
        <el-button type="warning" class="el-icon-upload2">导入</el-button>
      </ex-upload>
    </div>
    <div class="data-container">
      <el-table
        :data="jtkData"
        stripe
        highlight-current-row
        border
        @select-all="onSelectTable"
        @select="onSelectTable"
      >
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column prop="content" label="内容" align="center"/>
        <el-table-column width="100" prop="starttime" label="开始时间" align="center"/>
        <el-table-column width="100" prop="endtime" label="结束时间" align="center"/>
        <el-table-column width="100" prop="style" label="分类" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.style == '鸡汤'">{{scope.row.style}}</el-tag>
            <el-tag v-if="scope.row.style == '老话'" type="success">{{scope.row.style}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="250" label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              type="warning"
              class="el-icon-edit"
              size="mini"
              @click="onShowUpdate(scope.row.id)"
            >修改</el-button>
            <el-button
              type="danger"
              class="el-icon-delete"
              size="mini"
              @click="onDelete(scope.row.id)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
    </div>

    <!-- 添加 修改 dialog-->
    <div>
      <el-dialog
        width="35%"
        :title="title"
        :visible.sync="visibleClose"
        destroy-on-close
        :before-close="onCancel"
      >
        <el-form v-model="jtkForm" ref="jtkForm">
          <table width="100%" border="0" class="jtk-table">
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>分类：
              </td>
              <td class="td-left">
                <el-input v-model="jtkForm.style" placeholder="请选择" readonly>
                  <template slot="append">
                    <eos-select v-model="jtkForm.style" id="SOULSTYLE" :clear="eosClear"/>
                  </template>
                </el-input>
              </td>
            </tr>
            <tr v-if="dialogIsLh">
              <td class="td-right">
                <span class="jkt-tag-red">*</span>时间：
              </td>
              <td class="td-left">
                <el-input
                  type="text"
                  v-model="jtkForm.starttime"
                  placeholder="开始时间"
                  class="jkt-input"
                />&nbsp;至&nbsp;
                <el-input
                  type="text"
                  v-model="jtkForm.endtime"
                  placeholder="结束时间"
                  class="jkt-input"
                />
              </td>
            </tr>
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>内容：
              </td>
              <td class="td-left">
                <el-input type="textarea" v-model="jtkForm.content" rows="5"/>
              </td>
            </tr>
          </table>
          <div class="dialog-buttion-container">
            <el-button type="primary" @click="onSave()">确定</el-button>
            <el-button @click="onCancel()">取消</el-button>
          </div>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import pager from "@/components/Pager";
import request from "@/utils/request";
import common from "@/api/common";
import {
  getPagerData,
  doUpdate,
  getDateil,
  doDelete,
  doExport,
  doDeletes
} from "@/api/otherApi/chickenSoupLib";
import eosSelect from "@/components/EosSelect";
import cleanBtn from "@/components/CleanBtn";
import exUpload from "@/components/Upload/excelUpload";
export default {
  components: {
    pager,
    eosSelect,
    cleanBtn,
    exUpload
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramsForm: {
        // 查询表单
        content: "",
        style: ""
      },
      jtkForm: {
        // 添加||修改表单
        content: "",
        style: "",
        starttime: "",
        endtime: "",
        id: ""
      },
      jtkData: [], // 列表数据
      visibleClose: false, // dialog 是否关闭
      title: "", // dialog 标题
      dialogIsLh: false, // dialong 添加-> 如果是分类隐藏时间
      eosClear: false, // eos-select 是否清空
      delData :[],  //批量删除对象
    };
  },
  mounted() {
    this.packageInit();
  },
  watch: {
    "jtkForm.style": {
      deep: true,
      handler: function(newVal, oldVal) {
        if (newVal == "鸡汤") {
          this.dialogIsLh = true;
        } else {
          this.dialogIsLh = false;
        }
      }
    }
  },
  methods: {
    /**
     * 初始
     */
    init: function(param) {
      console.log("param", param);
      let data = common.paramsFormat(param);
      getPagerData(data).then(res => {
        this.jtkData = res.data.results;
        this.totalNumber = res.data.total;
      });
    }, // 初始 end
    packageInit: function() {
      let param = {};
      param.size = this.size;
      param.index = this.index;
      this.init(param);
    },
    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params.content = this.paramsForm.content;
      if (this.paramsForm.style === "全部") {
        params.style = "";
      } else {
        params.style = this.paramsForm.style;
      }
      params.size = size;
      params.index = index;
      this.size = size;
      this.index = index;
      this.init(params);
    },
    /**
     * 搜索
     */
    onSearch: function() {
      let params = {};
      params.content = this.paramsForm.content;
      if (this.paramsForm.style === "全部") {
        params.style = "";
      } else {
        params.style = this.paramsForm.style;
      }
      params.size = this.size;
      params.index = this.index;
      this.init(params);
    },
    /**
     * 显示添加dialog
     */
    handelToAdd: function() {
      this.visibleClose = true;
      this.eosClear = true;
      this.title = "添加";
    }, // 显示添加dialog end
    /**
     * dialog 确定事件
     */
    onSave: function() {
      let falg = this.vaildate();
      if (!falg) {
        return false;
      }
      let params = common.paramsFormat(this.jtkForm);
      doUpdate(params).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: "error" });
        } else {
          this.$message.success(res.data.message);
          this.packageInit();
          this.visibleClose = false;
          common.cleanFrom(this.jtkForm);
        }
      });
    }, // dialog 确定事件 end
    /**
     * dialog 取消事件
     */
    onCancel: function() {
      common.cleanFrom(this.jtkForm);
      this.visibleClose = false;
      this.eosClear = false;
      this.title = "";
    }, // dialog 取消事件 end
    /**
     * 更新
     */
    onShowUpdate: function(id) {
      this.title = "修改";
      getDateil({ id: id }).then(res => {
        if (res.data.status != 200) {
          this.$message.error(res.data.message);
          return false;
        }
        this.jtkForm = res.data.results;
      });
      this.visibleClose = true;
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
            this.$message.error(res.data.message);
            return false;
          } else {
            this.$message.success(res.data.message);
            this.packageInit();
          }
        });
      });
    },
    /**
     * 表单验证
     */
    vaildate: function() {
      if (this.jtkForm.style == "") {
        this.$message.error("分类不能为空");
        return false;
      }
      // 如果是鸡汤 显示时间
      if (this.dialogIsLh) {
        if (this.jtkForm.starttime == "") {
          this.$message.error("开始时间不能为空");
          return false;
        }
        if (this.jtkForm.endtime == "") {
          this.$message.error("结束时间不能为空");
          return false;
        }
      }

      if (this.jtkForm.style == "老话") {
        if (this.jtkForm.content == "") {
          this.$message.error("老话不能为空");
          return false;
        }
        if (this.jtkForm.content.length > 40) {
          this.$message.error("老话长度不能超过40");
          return false;
        }
      } else {
        if (this.jtkForm.content == "") {
          this.$message.error("鸡汤不能为空");
          return false;
        }
      }

      return true;
    }, // 表单验证 end
    /**
     * 导出
     */
    handelExport: function() {
      let params = common.paramsFormat(this.paramsForm);
      let self = this;
      doExport(params).then(res => {
        let fileName = common.dateFormat(
          new Date().getTime(),
          "yyyyMMddHHmmssS"
        );
        console.log("fileName", fileName);
        common.download(res.data, self, fileName + ".xls");
      });
    },
    /**
     * upload 提交地址
     */
    excelUploadAction: function() {
      return process.env.BASE_API + "/ppmSoulInfo/import";
    },
    /**
     * 导入
     */
    importExcel: function(file) {
      let self = this;
      const types = file.name.split(".")[1];
      const fileType = ["xlsx", "xls", "csv"].some(item => item === types);
      console.log("fileType:", fileType);
      if (!fileType) {
        self.$message.error("上传的文件格式有误，请重新选择");
        return;
      }
      self.$nextTick(() => {
        this.$refs.excelUpload.submitExcel();
      });
    }, // 导入前校验
    /**
     * 导入成功回调
     */
    excelSuccess: function(res, file) {
      if (res.status !== 200) {
        this.$message.error("导入文件失败");
        return false;
      }
      this.$message.success(res.message);
      this.packageInit();
      this.$refs.excelUpload.clearFiles()
    }, // 导入成功回调
    /**
     * 导入异常回调
     */
    excelError: function(error, file) {
      this.$message.error(error);
      this.$refs.excelUpload.clearFiles()
    }, // 导入异常回调 end
    /**
     * 选中tabel行
     */
    onSelectTable: function(selection, row) {
      console.log("selection", selection, row);
      this.delData = selection;
    },
    /**
     * 批量删除
     */
    handelDeletes: function() {
      this.$confirm("此操作为永久删除数据，确定继续吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        doDeletes(this.delData).then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          this.$message.success(res.data.message);
          this.packageInit();
        });
      });
    },  // 批量删除 end
  }
};
</script>
<style lang="scss" scoped>
.button-container {
  margin-bottom: 10px;
}

.jkt-input {
  width: 40%;
}
.jkt-tag-red {
  color: red;
}
.dialog-buttion-container {
  margin-top: 10px;
  text-align: right;
}
.excel-import {
  float: left;
  margin: 0 10px 0 3px;
}
</style>


