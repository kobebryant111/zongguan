<!-- 题库管理 -->
<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="paramsForm" inline>
        <!-- <el-form-item prop="questionno" label="序号">
          <el-input placeholder="序号" v-model="paramsForm.questionno"/>
        </el-form-item> -->
        <el-form-item prop="content" label="题干">
          <el-input placeholder="题干" v-model="paramsForm.content" />
        </el-form-item>
        <el-form-item prop="questiontype" label="分类">
          <eos-select
            v-model="paramsForm.questiontype"
            id="KJXXGL_TMLX"
            headValue="全部"
            :clear="eosClear"
            ref="paramEosSelect"
          ></eos-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSearch">搜索</el-button>
          <clean-btn :forms="this.paramsForm" :call="onFormClean"/>
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
        <el-button type="warning" class="el-icon-upload2" @click="notify">导入</el-button>
      </ex-upload>
    </div>
    <div class="data-container">
      <el-table
        :data="tkData"
        stripe
        highlight-current-row
        border
        @select="onSelectTable"
        @select-all="onSelectTable"
      >
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column width="110" prop="questionno" label="序号" align="center"/>
        <el-table-column prop="content" label="题干" align="center"/>
        <el-table-column prop="options" label="选项" align="center"/>
        <el-table-column width="100" prop="answer" label="正确答案" align="center"/>
        <el-table-column prop="answerexplain" label="答案解析" align="center"/>
        <el-table-column width="110" prop="annotation" label="注释" align="center"/>

        <el-table-column width="200" label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              type="warning"
              class="el-icon-edit"
              size="mini"
              @click="onShowUpdate(scope.row.questionno)"
            >修改</el-button>
            <el-button
              type="danger"
              class="el-icon-delete"
              size="mini"
              @click="onDelete(scope.row.id, scope.row.questionno)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs"/>
    </div>

    <!-- 添加 修改 dialog-->
    <div>
      <el-dialog
        width="38%"
        :title="title"
        :visible.sync="visibleClose"
        destroy-on-close
        :before-close="onCancel"
        @close="$reset('tkForm','tkData','totalNumber','size','index','paramsForm')"
      >
        <el-form v-model="tkForm" ref="tkForm">
          <table width="100%" border="0" class="jtk-table">
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>题目题干：
              </td>
              <td class="td-left">
                <el-input
                  type="textarea"
                  v-model="tkForm.content"
                  placeholder="题目题干 <必填>"
                  rows="5"
                  maxlength="2000"
                />
              </td>
            </tr>
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>题目类型：
              </td>
              <td class="td-left">
                <eos-select v-model="tkForm.questiontype" id="KJXXGL_TMLX" ref="eosSelect"/>
                <div>
                  <el-button class="el-icon-edit" @click="handelAddLine">添加</el-button>
                </div>

                <el-table :data="optData" stripe border show-header>
                  <el-table-column prop="options.optioncode" label="选项" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.optioncode" placeholder="选项"/>
                    </template>
                  </el-table-column>
                  <el-table-column prop="options.optioncontent" label="选项内容" align="center">
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.optioncontent" placeholder="选项内容"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center" width="80">
                    <template slot-scope="scope">
                      <el-button
                        type="danger"
                        @click="delOption(scope.row.id,scope.row.questionno,scope.$index)"
                      >删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </td>
            </tr>
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>正确答案：
              </td>
              <td class="td-left">
                <el-input
                  type="text"
                  v-model="tkForm.answer"
                  placeholder="正确答案<必填>"
                  maxlength="1000"
                />
              </td>
            </tr>
            <tr>
              <td class="td-right">
                <span class="jkt-tag-red">*</span>答案解析：
              </td>
              <td class="td-left">
                <el-input
                  type="textarea"
                  v-model="tkForm.answerexplain"
                  placeholder="答案解析<必填>"
                  maxlength="4000"
                  rows="5"
                />
              </td>
            </tr>
            <tr>
              <td class="td-right">注：</td>
              <td class="td-left">
                <el-input type="text" v-model="tkForm.annotation" maxlength="1000"/>
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
  getPager,
  doUpdate,
  getDateil,
  doDelete,
  doOptionDelete,
  exportExcel,
  batchDeletes
} from "@/api/otherApi/tastBank";
import eosSelect from "@/components/EosSelect";
import cleanBtn from "@/components/CleanBtn";
import exUpload from "@/components/Upload/excelUpload";
const Qs = require("qs");
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
        questiontype: "",
        questionno: ""
      },
      tkForm: {
        // 添加||修改表单
        content: "", // 题干
        questiontype: "", // 选项
        isdel: "", // 是否删除 0为未删除，1为删除，默认为0
        answer: "", // 答案
        id: "", // id
        answerexplain: "", //答案解析
        annotation: "", // 注释
        options: [] // 选项数据
      },
      tkData: [], // 列表数据
      visibleClose: false, // dialog 是否关闭
      title: "", // dialog 标题
      eosClear: false, // eos-select 是否清空
      optData: [], // 题库选项集合
      delData: [], // 需要删除的集合
      stats : 1
    };
  },
  mounted() {
    this.packageInit();
  },

  methods: {
    getNewRowsData: function() {
      return {
        optioncode: "",
        optioncontent: ""
      };
    },
    /**
     * 初始
     */
    init: function(param) {
      console.log("param", param);
      let data = common.paramsFormat(param);
      getPager(data).then(res => {
        this.tkData = res.data.results;
        console.log("tkdata", this.tkData);
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
      if (this.paramsForm.questiontype === "全部") {
        params.questiontype = "";
      } else {
        params.questiontype = this.paramsForm.questiontype;
      }
      console.log("params", params);
      params.size = size;
      params.index = index;
      this.size = size;
      this.index = index;
      this.init(params);
    }, // 分页触发 end
    /**
     * 搜索
     */
    onSearch: function() {
      let params = {};
      params.content = this.paramsForm.content;
      if (this.paramsForm.questiontype === "全部") {
        params.questiontype = "";
      } else {
        params.questiontype = this.paramsForm.questiontype;
      }
      params.questionno = this.paramsForm.questionno;
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
      this.title = "添加题目";
    }, // 显示添加dialog end
    /**
     * dialog 确定事件
     */
    onSave: function() {
      let falg = this.vaildate();
      if (!falg) {
        return false;
      }
      this.tkForm.options = this.optData;
      
      console.log('tkForm', this.tkForm)
      doUpdate(this.tkForm).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: "error" });
        } else {
          this.$message.success(res.data.message);
          this.packageInit();
          this.onCancel();
        }
      });
    }, // dialog 确定事件 end
    /**
     * dialog 取消事件
     */
    onCancel: function() {
      const self = this;
      this.$nextTick(() => {
        this.$refs.eosSelect.svalue = "";
      });
      this.visibleClose = false;
      this.eosClear = false;
      this.title = "";
    }, // dialog 取消事件 end
    /**
     * 更新
     */
    onShowUpdate: function(questionno) {
      const self = this;
      this.title = "修改题目";
      getDateil({ questionno: questionno }).then(res => {
        console.log("res:", res.data);
        if (res.data.status != 200) {
          this.$message.error(res.data.message);
          return false;
        }
        this.tkForm = res.data.results;
        this.optData = res.data.results.options;
        self.$nextTick(() => {
          this.$refs.eosSelect.svalue = self.tkForm.questiontype;
        });
      });

      this.visibleClose = true;
    },
    /**
     * 删除
     */
    onDelete: function(id, no) {
      this.$confirm("此操作为永久删除数据，确定继续吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        console.log("p", id, no);
        doDelete({ id: id, questionno: no }).then(res => {
          if (res.data.status != 200) {
            this.$message.error(res.data.message);
            return false;
          } else {
            this.$message.success(res.data.message);
            this.packageInit();
          }
        });
      });
    }, // 删除 end

    /**
     * 表单验证
     */
    vaildate: function() {
      if (this.tkForm.content == "") {
        this.$message.error("题干不能为空");
        return false;
      }
      if (this.tkForm.questiontype == "") {
        this.$message.error("题目类型不能为空");
        return false;
      }
      
      if (this.tkForm.answer == "") {
        this.$message.error("正确答案不能为空");
        return false;
      }

      if (this.tkForm.answerexplain == "") {
        this.$message.error("答案解析不能为空");
        return false;
      }
      return true;
    }, // 表单验证 end
    /**
     * 导出
     */
    handelExport: function() {
      let params = common.paramsFormat(this.paramsForm);
      const self = this;
      exportExcel(params).then(res => {
        // let fileName = common.dateFormat(
        //   new Date().getTime(),
        //   "yyyyMMddHHmmssS"
        // );
        common.download(res.data, self,   "lllll.xls");
      });
    },
    /**
     * upload 提交地址
     */
    excelUploadAction: function() {
      return process.env.BASE_API + "/ppmQuestionList/import";
    },
    notify: function() {
      const h = this.$createElement;
      this.$notify({
        title: "提示",
        dangerouslyUseHTMLString: true,
        duration: 9000,
        message:
          '<i style="color:red">导入提示:如果是新增题目，导入的序号为空即可;若想更新原已导入题目，请加上对应题目序号。</i><br /><i style="color:red">注:支持文件类型:.xls,默认只读取第一个sheet页内容。</i>'
      });
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
     * 新增行
     */
    handelAddLine: function() {
      this.optData.push(this.getNewRowsData());
    },
    /**
     * 删除行
     */
    delOption: function(id, no, index) {
      this.$confirm("此操作为永久删除数据，确定继续吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        console.log('option', id, no)
        if(typeof id === 'undefined' && typeof no === 'undefined') {
          this.$delete(this.optData, index);
          return false;
        }
        doOptionDelete({ id: id, questionno: no }).then(res => {
          if (res.data.status != 200) {
            this.$message.error(res.data.message);
            return false;
          } else {
            this.$message.success(res.data.message);
            // 删除数组
            this.$delete(this.optData, index);
          }
        });
      });
    }, // 删除行end
    /**
     * 选中table行
     */
    onSelectTable: function(selection, row) {
      this.delData = selection;
      console.log('delData', this.delData)
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
        batchDeletes(this.delData).then(res => {
          if (res.data.status != 200) {
            this.$alert(res.data.message, { type: "error" });
            return false;
          }
          this.$message.success(res.data.message);
          this.packageInit();
        });
      });
    }, // 批量删除 end
    onFormClean : function() {
      this.$refs.paramEosSelect.svalue = ''
    },
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
.td-left {
  text-align: left;
}
.td-right {
  text-align: right;
}
</style>


