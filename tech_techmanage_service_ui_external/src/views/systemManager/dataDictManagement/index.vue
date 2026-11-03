<template>
  <div>
    <el-container>
      <el-container>
        <el-aside width="500px">
          <div class="app-container">
            <div class="filter-container">
              <el-form ref="filterForm" :model="filterForm" :inline="true">
                <el-form-item prop="dictTypeId" label="类型代码:">
                  <el-input
                    v-model="filterForm.dictTypeId"
                    clearable
                    style="width: 125px;"
                    class="filter-item"
                    size="mini"
                    @keyup.enter.native="getDictList()"
                  />
                </el-form-item>
                <el-form-item prop="dictTypeName" label="类型名称:">
                  <el-input
                    v-model="filterForm.dictTypeName"
                    clearable
                    style="width: 125px;"
                    class="filter-item"
                    size="mini"
                    @keyup.enter.native="getDictList()"
                  />
                </el-form-item>
              </el-form>
            </div>
            <el-row :gutter="50" style="margin-bottom:20px;">
              <el-col :span="4">
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-search"
                  @click="getDictList()"
                >查询</el-button>
              </el-col>
              <el-col :span="4">
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-refresh"
                  @click="resetFilterAndQuery"
                >重置</el-button>
              </el-col>

              <el-col :span="4">
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-circle-plus-outline"
                  @click="newDicttype"
                >增加</el-button>
              </el-col>
              <el-col :span="4">
                <el-button type="primary" size="mini" icon="el-icon-edit" @click="modifyDict">编辑</el-button>
              </el-col>
              <el-col :span="4">
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete">删除</el-button>
              </el-col>
            </el-row>
            <el-table
              v-loading="tableLoading"
              ref="dictListTable"
              :header-cell-style="{background: 'rgb(222, 223, 224)'}"
              :data="dictList"
              border
              @row-click="rowClike"
              height="410"
              highlight-current-row
              @current-change="getDetails"
            >
              <el-table-column prop="dictTypeId" label="字典类型代码" align="center"/>
              <el-table-column prop="dictTypeName" label="字典类型名称" align="center"/>
            </el-table>

            <!-- Pagination -->
            <div class="pagination-container">
              <el-pagination
                :current-page.sync="currentPage"
                :page-sizes="pageSizes"
                :page-size="pageSize"
                :total="total"
                small
                background
                layout="total, sizes, prev, pager, next, jumper"
                @current-change="currentPageChange"
                @size-change="pageSizeChange"
              />
            </div>
          </div>
        </el-aside>
        <!--字典右侧布局-->
        <el-main>
          <div class="app-container">
            <el-row style="margin-bottom:10px;">
              <el-col :span="2">
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-circle-plus-outline"
                  @click="newDicttypeEntry"
                >增加</el-button>
              </el-col>
              <el-col :span="2">
                <el-button
                  type="primary"
                  size="mini"
                  icon="el-icon-edit"
                  @click="modifyDictEntry"
                >编辑</el-button>
              </el-col>
              <el-col :span="2">
                <el-button
                  type="danger"
                  size="mini"
                  icon="el-icon-delete"
                  @click="handleDeleteEntry"
                >删除</el-button>
              </el-col>
            </el-row>
            <el-table
              v-loading="tableLoading"
              ref="dictEntryListTable"
              :header-cell-style="{background: 'rgb(222, 223, 224)'}"
              :data="dictentryList"
              border
              @row-click="rowClikeEntry"
              height="410"
              highlight-current-row
            >
              <el-table-column prop="dictId" label="字典代码" align="center"/>
              <el-table-column prop="dictName" label="字典名称" align="center"/>
              <el-table-column prop="seqNo" label="排序" align="center"/>
            </el-table>

            <div class="pagination-container">
              <el-pagination
                :current-page.sync="currentPage2"
                :page-sizes="pageSizes2"
                :page-size="pageSize2"
                :total="total2"
                small
                background
                layout="total, sizes, prev, pager, next, jumper"
                @current-change="currentPageChange2"
                @size-change="pageSizeChange2"
              />
            </div>
          </div>

          <!-- 字典类型增改对话框 -->
          <el-dialog
            :close-on-click-modal="false"
            :title="dialogTitle"
            :visible.sync="dialogVisible"
            width="30%"
          >
            <el-form
              ref="form"
              :model="dialogForm"
              :rules="formRules"
              label-width="150px"
              label-position="right"
            >
              <el-form-item label="字典类型代码" prop="dictTypeId">
                <el-input
                  v-model="dialogForm.dictTypeId"
                  :disabled="dialogType == 1"
                  placeholder="请输入字典类型代码"
                  clearable
                  style="width: 200px;"
                  maxlength="30"
                />
              </el-form-item>
              <el-form-item label="字典类型名称" prop="dictTypeName">
                <el-input
                  v-model="dialogForm.dictTypeName"
                  placeholder="字典类型名称"
                  clearable
                  style="width: 200px;"
                  maxlength="100"
                />
              </el-form-item>
              <el-form-item label="字典类型排序" prop="seqNo">
                <el-input
                  v-model="dialogForm.seqNo"
                  placeholder="字典类型排序"
                  clearable
                  style="width: 200px;"
                  maxlength="20"
                  type="number"
                />
              </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer" align="center">
              <el-button type="primary" icon="el-icon-circle-check" @click="handleDialogConfirm">确认</el-button>
              <el-button icon="el-icon-circle-close" @click="handleCancel">取消</el-button>
            </div>
          </el-dialog>

          <!-- 字典增改对话框 -->
          <el-dialog
            :close-on-click-modal="false"
            :title="dialogTitleEntry"
            :visible.sync="dialogVisibleEntry"
            width="30%"
          >
            <el-form
              ref="form"
              :model="dialogFormEntry"
              :rules="formRulesEntry"
              label-width="150px"
              label-position="right"
            >
              <el-form-item label="字典类型代码" prop="dictTypeId">
                <el-input
                  v-model="dialogFormEntry.dictTypeId"
                  :disabled="true"
                  placeholder="请输入字典类型代码"
                  clearable
                  style="width: 200px;"
                  maxlength="30"
                />
              </el-form-item>
              <el-form-item label="字典代码" prop="dictId">
                <el-input
                  v-model="dialogFormEntry.dictId"
                  :disabled="dialogTypeEntry == 1"
                  placeholder="请输入字典代码"
                  clearable
                  style="width: 200px;"
                  maxlength="30"
                />
              </el-form-item>
              <el-form-item label="字典名称" prop="dictName">
                <el-input
                  v-model="dialogFormEntry.dictName"
                  placeholder="字典名称"
                  clearable
                  style="width: 200px;"
                  maxlength="100"
                />
              </el-form-item>
              <el-form-item label="字典排序" prop="seqNo">
                <el-input
                  v-model="dialogFormEntry.seqNo"
                  placeholder="字典排序"
                  clearable
                  style="width: 200px;"
                  maxlength="20"
                  type="number"
                />
              </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer" align="center">
              <el-button
                type="primary"
                icon="el-icon-circle-check"
                @click="handleDialogConfirmEntry"
              >确认</el-button>
              <el-button icon="el-icon-circle-close" @click="handleCancelEntry">取消</el-button>
            </div>
          </el-dialog>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {
  fetchDict,
  createDict,
  deleteDicttype,
  updateDicttype,
  checkDictCode,
  deleteDictentry,
  fetchDictentry,
  saveDictentry,
  checkDictItemCode,
  updateDictEntry
} from "@/api/eosDictEntry";
const Qs = require("qs");

export default {
  name: "dataDictManagement",
  components: {},
  data() {
    return {
      dictList: [], // 字典列表
      dictentryList: [], // 字典
      newDictEntryList: [], //新增字典
      tableLoading: false,
      currentPage: 1, // 当前页
      total: 0, // 总数
      pageSizes: [10, 20, 50, 100],
      pageSize: 10, // 每页数量
      //当前点击的类型行
      currentRowData: "",
      currentRowDataEntry: "",
      currentPage2: 1, // 当前页
      total2: 0,
      pageSizes2: '',
      pageSize2: 10, // 每页数量
      filterForm: {},
      dialogForm: {
        // 弹框表单数据
        dictTypeId: "",
        dictTypeName: "",
        editType: "0",
        seqNo: "0"
      },
      dialogTitle: "", // 弹框标题
      dialogType: 0, // 0 新增角色 1 修改角色
      dialogVisible: false, // 弹框显示
      formRules: {
        // 表单校验
        dictTypeId: [
          { required: true, message: "请输入字典类型id", trigger: "blur" }
        ],
        dictTypeName: [
          { required: true, message: "字典类型名称", trigger: "blur" }
        ]
      },

      dialogFormEntry: {
        // 弹框表单数据
        dictTypeId: "",
        dictId: "",
        dictName: "",
        editType: "0",
        seqNo: "0"
      },
      dialogTitleEntry: "", // 弹框标题
      dialogTypeEntry: 0, // 0 新增角色 1 修改角色
      dialogVisibleEntry: false, // 弹框显示
      formRulesEntry: {
        // 表单校验
        dictId: [{ required: true, message: "请输入字典id", trigger: "blur" }],
        dictName: [{ required: true, message: "字典名称", trigger: "blur" }]
      },
      newDictMap: {}
    };
  },
  created() {
    this.getDictList();
  },
  methods: {
    /**
     * 获取分页字典列表
     */
    getDictList() {
      this.tableLoading = true;
      const query = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        dictTypeName: this.filterForm.dictTypeName,
        dictTypeId: this.filterForm.dictTypeId
      };
      fetchDict(query)
        .then(res => {
          console.log(res.data);
          if (res.data.errorCode === "000000") {
            this.tableLoading = false;
            this.dictList = res.data.data;
            if (this.dictList && this.dictList.length > 0) {
              this.$refs.dictListTable.setCurrentRow(this.dictList[0]);
              this.currentRowData = this.dictList[0];
              this.getDetails(this.dictList[0]);
            }
            this.total = res.data.total;
          } else {
            this.$message({
              message: "获取字典数据失败！",
              type: "error"
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 字典类型的行点击事件
     */
    rowClike(row) {
      console.log("字典类型的行点击事件");
      let rowData = JSON.stringify(row);
      this.currentRowData = JSON.parse(rowData);
      console.log(this.currentRowData, "currentRowData");
    },

    /**
     * 获取字典详情
     */
    getDetails(row) {
      console.log(row, "getDetails");
      const data = {};
      this.dictTypeId = row.dictTypeId;
      this.dictTypeName = row.dictTypeName;
      this.seqNo = row.seqNo;
      data.dictTypeId = row.dictTypeId;
      data.currentPage = 0;
      data.pageSize = 10;
      fetchDictentry(data)
        .then(res => {
          if (res.data.errorCode === "000000") {
            this.tableLoading = false;
            this.dictentryList = res.data.data;
            if (this.dictentryList && this.dictentryList.length > 0) {
              this.$refs.dictEntryListTable.setCurrentRow(
                this.dictentryList[0]
              );
            }
            this.total2 = res.data.total;
          } else {
            this.$message({
              message: "获取字典数据失败！",
              type: "error"
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 分页功能 - 检测当前页变化
     */
    currentPageChange(val) {
      this.currentPage = val;
      this.getDictList();
    },

    /**
     * 分页功能 - 检测PageSize变化
     */
    pageSizeChange(val) {
      this.pageSize = val;
      this.getDictList();
    },

    currentPageChange2(val) {
      this.currentPage2 = val;
      this.getDetails2();
    },
    pageSizeChange2(val) {
      this.pageSize2 = val;
      this.getDetails2();
    },

    getDetails2(row) {
      const data = {
        currentPage: this.currentPage2,
        pageSize: this.pageSize2
      };
      data.dictTypeId = this.currentRowData.dictTypeId;
      fetchDictentry(data)
        .then(res => {
          this.tableLoading = false;
          this.dictentryList = res.data.data;
          if (this.dictentryList && this.dictentryList.length > 0) {
            this.$refs.dictEntryListTable.setCurrentRow(this.dictentryList[0]);
          }
          this.total2 = res.data.total;
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 重置
     */
    resetFilterAndQuery() {
      this.filterForm = {};
      this.getDictList();
    },

    /**
     * 弹框取消按钮
     */
    handleCancel() {
      this.dialogVisible = false;
      this.$refs["form"].resetFields();
    },
    /**
     * 弹框确认按钮
     */
    handleDialogConfirm() {
      if (this.dialogType === 0) {
        if (
          this.dialogForm.dictTypeId == "" ||
          this.dialogForm.dictTypeName == ""
        ) {
          this.$message({
            message: "请输入字典信息",
            type: "warning"
          });
        } else {
          checkDictCode(this.dialogForm.dictTypeId)
            .then(res => {
              if (res.data.errorCode != "000000") {
                //this.$message({
                //  message: "存在重复字典类型代码!",
                //  type: "warning"
                //});
                return;
              } else {
                this.submit();
              }
            })
            .catch(error => {
              console.log(error);
            });
        }
      } else {
        this.modify();
      }
    },

    /**
     * 提交信息
     */
    submit() {
      if (this.dialogForm.seqNo == null || this.dialogForm.seqNo == "") {
        this.dialogForm.seqNo = 0;
      }

      createDict(JSON.stringify(this.dialogForm))
        .then(res => {
          console.log(res);
          if (res.data.errorCode === "000000") {
            this.dialogVisible = false;
            this.$message({
              message: "字典类型创建成功",
              type: "success"
            });
          } else {
            this.$message({
              message: "字典创建失败",
              type: "error"
            });
          }
          this.getDictList();
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 修改字典类型信息
     */
    modify() {
      updateDicttype(JSON.stringify(this.dialogForm))
        .then(res => {
          if (res.data.errorCode === "000000") {
            this.dialogVisible = false;
            this.$message({
              message: "修改成功",
              type: "success"
            });
          } else {
            this.$message({
              message: "修改失败",
              type: "error"
            });
          }
          this.getDictList();
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 创建字典类型
     */
    newDicttype() {
      this.dialogTitle = "新增字典类型";
      this.dialogType = 0;
      this.dialogVisible = true;
      this.editType = 0;
      this.dialogForm.id = "";
      this.dialogForm.dictTypeId = "";
      this.dialogForm.dictTypeName = "";
      this.dialogForm.seqNo = "0";
    },
    /**
     * 修改字典类型
     */
    modifyDict(item) {
      if (this.dictTypeId == null) {
        this.$message({
          message: "请选择要编辑的字典类型代码",
          type: "warning"
        });
        return;
      }
      this.dialogForm.id = this.dictTypeId;
      this.dialogForm.dictTypeId = this.dictTypeId;
      this.dialogForm.dictTypeName = this.dictTypeName;
      this.dialogForm.seqNo = this.seqNo;
      this.dialogTitle = "修改字典类型";
      this.dialogType = 1;
      this.dialogVisible = true;
      this.editType = 0;
    },
    /**
     * 处理删除字典按钮
     */
    handleDelete() {
      if (!this.currentRowData) {
        this.$message({
          message: "请点击行选择要删除的字典类型",
          type: "warning"
        });
        return;
      }
      this.$confirm(
        '确认删除字典类型  "' + this.currentRowData.dictTypeName + '"  ?',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          cancelButtonClass: "comfirm-dialog-btn-cancel",
          type: "warning"
        }
      )
        .then(() => {
          this.deleteDict(this.currentRowData);
          this.getDetails;
        })
        .catch(() => {});
    },
    /**
     * 删除字典
     */
    deleteDict(item) {
      deleteDicttype(item.dictTypeId)
        .then(res => {
          if (res.data.errorCode === "000000") {
            this.$message({
              message: "删除成功",
              type: "success"
            });
            this.currentRowData = "";
            this.getDictList();
          } else {
            this.$message({
              message: "删除失败",
              type: "error"
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 字典的行点击事件
     */
    rowClikeEntry(row) {
      console.log(row, "字典的行点击");
      let rowData = JSON.stringify(row);
      this.currentRowDataEntry = JSON.parse(rowData);
      console.log(this.currentRowDataEntry, "currentRowDataEntry");
    },

    /**
     * 字典弹框取消按钮
     */
    handleCancelEntry() {
      this.dialogVisibleEntry = false;
      this.$refs["form"].resetFields();
    },
    /**
     * 字典弹框确认按钮
     */
    handleDialogConfirmEntry() {
      if (this.dialogTypeEntry === 0) {
        if (
          this.dialogFormEntry.dictId == "" ||
          this.dialogFormEntry.dictName == ""
        ) {
          this.$message({
            message: "请输入字典信息",
            type: "warning"
          });
        } else {
          var parms = {
            dictTypeId: this.dialogFormEntry.dictTypeId,
            dictId: this.dialogFormEntry.dictId
          };
          checkDictItemCode(parms)
            .then(res => {
              if (res.data.errorCode != "000000") {
                return;
              } else {
                this.submitEntry();
              }
            })
            .catch(error => {
              console.log(error);
            });
        }
      } else {
        this.modifyEntry();
      }
    },

    /**
     * 字典提交信息
     */
    submitEntry() {
      if (
        this.dialogFormEntry.seqNo == null ||
        this.dialogFormEntry.seqNo == ""
      ) {
        this.dialogFormEntry.seqNo = 0;
      }

      saveDictentry(JSON.stringify(this.dialogFormEntry))
        .then(res => {
          console.log(res);
          if (res.data.errorCode === "000000") {
            this.dialogVisibleEntry = false;
            this.$message({
              message: "字典创建成功",
              type: "success"
            });
          } else {
            this.$message({
              message: "字典创建失败",
              type: "error"
            });
          }
          this.getDetails2();
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 修改字典信息
     */
    modifyEntry() {
      updateDictEntry(JSON.stringify(this.dialogFormEntry))
        .then(res => {
          if (res.data.errorCode === "000000") {
            this.dialogVisibleEntry = false;
            this.$message({
              message: "修改成功",
              type: "success"
            });
          } else {
            this.$message({
              message: "修改失败",
              type: "error"
            });
          }
          this.getDetails2();
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 创建字典
     */
    newDicttypeEntry() {
      this.dialogTitleEntry = "新增字典";
      this.dialogTypeEntry = 0;
      this.dialogVisibleEntry = true;
      this.editTypeEntry = 0;
      this.dialogFormEntry.dictTypeId = this.currentRowData.dictTypeId;
      this.dialogFormEntry.dictId = "";
      this.dialogFormEntry.dictName = "";
      this.dialogFormEntry.seqNo = "0";
    },
    /**
     * 修改字典
     */
    modifyDictEntry(item) {
      if (!this.currentRowDataEntry) {
        this.$message({
          message: "请选择要编辑的字典代码",
          type: "warning"
        });
        return;
      }
      this.dialogFormEntry.dictTypeId = this.currentRowDataEntry.dictTypeId;
      this.dialogFormEntry.dictId = this.currentRowDataEntry.dictId;
      this.dialogFormEntry.dictName = this.currentRowDataEntry.dictName;
      this.dialogFormEntry.seqNo = this.currentRowDataEntry.seqNo;
      this.dialogTitleEntry = "修改字典";
      this.dialogTypeEntry = 1;
      this.dialogVisibleEntry = true;
      this.editTypeEntry = 0;
    },
    /**
     * 处理删除字典按钮
     */
    handleDeleteEntry() {
      console.log("删除字典按钮");
      console.log(this.currentRowDataEntry);
      if (!this.currentRowDataEntry) {
        this.$message({
          message: "请点击行选择要删除的字典",
          type: "warning"
        });
        return;
      }
      this.$confirm(
        '确认删除字典类型  "' + this.currentRowDataEntry.dictName + '"  ?',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          cancelButtonClass: "comfirm-dialog-btn-cancel",
          type: "warning"
        }
      )
        .then(() => {
          this.deleteDictentry(this.currentRowDataEntry);
          this.getDetails2();
        })
        .catch(() => {});
    },
    /**
     * 删除字典
     */
    deleteDictentry(item) {
      var parms = {
        dictTypeId: item.dictTypeId,
        dictId: item.dictId
      };
      deleteDictentry(parms)
        .then(res => {
          if (res.data.errorCode === "000000") {
            this.$message({
              message: "删除成功",
              type: "success"
            });
            this.currentRowDataEntry = "";
          } else {
            this.$message({
              message: "删除失败",
              type: "error"
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>
<style lang="scss" scoped>
.filter-container {
  position: relative;
  .add-btn {
    position: relative;
    top: 0px;
    right: -10px;
  }
}
.el-form--inline .el-form-item {
  margin: 0 10px 0 0;
}
.app-container .el-table--medium td {
  padding: 5px 0;
}
.app-container .el-tag {
  margin: 0 3px 0 0;
}
.app-container .el-dialog__body {
  height: 400px;
  overflow-y: scroll;
}
.popoverDiv {
  text-align: right;
  margin: 0;
}

.tb-edit .el-input {
  display: none;
}
.tb-edit .current-row .el-input {
  display: block;
}
.tb-edit .current-row .el-input + span {
  display: none;
}
</style>
