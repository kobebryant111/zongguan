<template>
    <div class="interviewList">
        <!-- 表单部分 -->
        <div class="form_box">
            <el-form :model="paramsForm" ref="formRef" label-width="80px">
                <el-row type="flex" justify="space-between">
                    <el-col :span="6">
                        <el-form-item label="供应商名称" label-width="100px">
                            <el-select
                                placeholder="请选择供应商"
                                v-model="paramsForm.supplierId"
                                filterable
                            >
                                <el-option
                                    v-for="item in outsourceno_options"
                                    :key="item.id"
                                    :label="item.outsourcname"
                                    :value="item.orgcode">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="合同名称">
                            <el-input v-model.trim="paramsForm.contractName" placeholder="请输入合同名称"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="18">
                        <el-row type="flex" justify="end">
                            <clean-btn :size="sizes" :forms="this.paramsForm" />
                            <button-orange :buttonName="buttonSearch" :size="sizes" :fns="submitBtn"></button-orange>
                        </el-row>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <!-- 表格部分 -->
        <div class="table_box">
            <el-row type="flex" justify="end" class="margin_bottom">

                <el-button :size="sizes" @click="addContract">录入新合同</el-button>

                <button-common  buttonName="供应商名单导出"  :size="sizes"  :fns="clickExportButton"></button-common>
            </el-row>
            <el-table
                :data='crewData'
                highlight-current-row height="510"
                @row-dblclick='dbTable'
            >
                <el-table-column
                    prop="contractName"
                    label="合同名称"
                    align="center"
                    width="100"
                ></el-table-column>
                <el-table-column
                    prop="contractNo"
                    label="合同编号"
                    align="center"
                    width="100"
                ></el-table-column>
                <el-table-column
                    prop="supplierName"
                    label="供应商名称"
                    align="center"
                    width="100"
                ></el-table-column>
                <el-table-column
                    prop="outsourceNum"
                    label="关联人数"
                    align="center"
                    width="40"
                >
                </el-table-column>
                <el-table-column
                    label="操作"
                    width="80"
                    align="center"
                >
                    <template slot-scope="scope">
                        <el-link :underline="false" type="warning" @click="detailContract(scope.row)">关联管理</el-link>
                        <el-link :underline="false" type="warning" @click="editContract(scope.row)">编辑</el-link>
                        <el-link :underline="false" type="warning" @click="delRow(scope.row)">删除</el-link>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <div class="pager_zb">
                <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
            </div>

            <!-- 合同录入/编辑弹框 -->
            <el-dialog :visible="dialogVisible" :title="isEdit ? '合同调整' : '录入新外包合同'"  :before-close="handleClose" >
                <el-form :model="contractForm" :rules="rules" ref="contractFormRef"
                 :inline="true" label-position="left" label-width="100px">
                  <el-form-item label="供应商名称" prop="supplierId">
                            <el-select
                                placeholder="请选择供应商"
                                v-model="contractForm.supplierId"
                                filterable
                                style="width: 108%;"
                            >
                                <el-option
                                    v-for="item in outsourceno_options"
                                    :key="item.id"
                                    :label="item.outsourcname"
                                    :value="item.orgcode">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    <el-form-item style="width: 100%; margin: 0;"></el-form-item>
                    <el-form-item label="合同编号" prop="contractNo">
                      <el-input v-model="contractForm.contractNo" placeholder="请输入合同编号" size="medium" style="width: 115%;"></el-input>
                    </el-form-item>
                    <el-form-item style="width: 100%; margin: 0;"></el-form-item>
                    <el-form-item label="合同名称" prop="contractName">
                      <el-input v-model="contractForm.contractName" placeholder="请输入合同名称" size="medium" style="width: 115%;"></el-input>
                    </el-form-item>
                    <el-form-item style="width: 100%; margin: 0;"></el-form-item>
                    <el-form-item label="初级开发人数">
                      <el-input-number v-model="contractForm.juniorNum" :min="0" placeholder="为空限制关联人数" size="medium" style="width: 91%;"/>
                    </el-form-item>
                    <el-form-item label="初级开发单价">
                      <el-input-number v-model="contractForm.juniorPrice" :min="0" :precision="2"  size="medium" style="width: 91%;"/>
                    </el-form-item>
                    <el-form-item style="width: 100%; margin: 0;"></el-form-item>
                    <el-form-item label="中级开发人数">
                      <el-input-number v-model="contractForm.intermediateNum" :min="0"  placeholder="为空限制关联人数" size="medium" style="width: 91%;"/>
                    </el-form-item>
                    <el-form-item label="中级开发单价">
                      <el-input-number v-model="contractForm.intermediatePrice" :min="0" :precision="2"  size="medium" style="width: 91%;"/>
                    </el-form-item>
                    <el-form-item style="width: 100%; margin: 0;"></el-form-item>
                    <el-form-item label="高级开发人数">
                      <el-input-number v-model="contractForm.seniorNum" :min="0"  placeholder="为空限制关联人数" size="medium" style="width: 91%;"/>
                    </el-form-item>
                    <el-form-item label="高级开发单价">
                      <el-input-number v-model="contractForm.seniorPrice" :min="0" :precision="2"  size="medium" style="width: 91%;"/>
                    </el-form-item>
                    <el-form-item style="width: 100%; margin: 0;"></el-form-item>
                    <el-form-item label="资深开发人数">
                      <el-input-number v-model="contractForm.expertNum" :min="0"  placeholder="为空限制关联人数" size="medium" style="width: 91%;"/>
                    </el-form-item>
                    <el-form-item label="资深开发单价">
                      <el-input-number v-model="contractForm.expertPrice" :min="0" :precision="2"  size="medium" style="width: 91%;"/>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="submitClearBtn">重 置</el-button>
                    <el-button type="primary" @click="submitSaveBtn">提 交 保 存</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import common from "@/api/common";
import { queryPager, queryDetail, saveOrUpdateContract, delContract, exportSupplier } from '@/api/supplierContract/contractManage';
import pager from "@/components/Pager";
import { getListAll } from "@/api/common/workflowApi";
import cleanBtn from "@/components/CleanBtn";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
export default {
    components: {
        pager,
        cleanBtn,
        buttonOrange,
        buttonCommon
    },
    data() {
        return {
            totalNumber: 0,           // 总条数
            size: 10,                 // 每页条数 初始值 默认：5
            index: 1,                 // 页数 初始值 默认：1
            paramsForm: {
                supplierId: '',       // 供应商名称
                contractName: ''      // 合同名称
            },
            contractForm: {
                id: '',                 // 合同序号
                supplierId: '',         // 供应商编码
                supplierName: '',       // 供应商名称
                contractName: '',       // 合同名称
                juniorNum: '',          // 初级开发人数
                intermediateNum: '',    // 中级开发人数
                seniorNum: '',          // 高级开发人数
                expertNum: ''           // 资深开发人数
            },
            outsourceno_options: [],    // 供应商列表
            crewData:[],                // 表格数据来源
            dialogVisible: false,       // 录入弹框
            sizes: 'small',             //按钮大小
            buttonSearch: '查 询',       //查询按钮
            isEdit: false,
            kqAuth: false,
            // 新增校验规则
            rules: {
                supplierId: [
                    { required: true, message: '请选择供应商', trigger: 'change' }
                ],
                contractNo: [
                    { required: true, message: '请输入合同编号', trigger: 'blur' }
                ],
                contractName: [
                    { required: true, message: '请输入合同名称', trigger: 'blur' }
                ]
            }
        }
    },
    created() {

        // this.getPermission();
        this.getInit();

    },
    mounted() {
        this.getWbcom();
    },
    computed: {
    },
    methods: {

        // 分页初始化
        getInit() {
            let paramsQuery = window.localStorage.getItem('queryParams');
            let queryParams = null;
            if (paramsQuery) {
                queryParams = JSON.parse(paramsQuery);
            }
            let params = {};
            if(queryParams == null || Object.keys('queryParams').length == 0) {
                params.size = this.size;
                params.index = this.index;
            } else {
                window.localStorage.clear('queryParams')
                this.paramsForm = queryParams;
                params = queryParams;
                params.size = this.size;
                params.index = this.index;
            }


            this.getPagerList(params);
            this.$router.go(1);
        },

        // 获取列表数据
        getPagerList(params) {
            params.contractState= 'Y';
            let data = common.paramsFormat(params);
            queryPager(data).then(res => {
                if(res.data.status == 200) {
                    this.crewData = res.data.results;
                    this.totalNumber = res.data.total;
                    if(this.totalNumber <= 0) {
                        return false
                    }
                }
            })
        },

        // 查询按钮
        submitBtn() {
            let params = this.paramsForm;
            for(var k in params) {
                if(params[k] && this.index > 1) {
                    this.index = 1
                }
            }
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        /**
         * 分页触发
         */
        getPager: function (size, index) {
            let params = this.paramsForm;
            params.size = size;
            params.index = index;
            this.size = size;
            this.index = index;
            this.getPagerList(params);
        } ,

        // 新增合同
        addContract() {
            this.isEdit = false
            this.dialogVisible = true
            this.contractForm = {};
            this.$nextTick(() => {
                // 注意：这里调用 validate 不传回调函数，或者传一个空回调，
                // 主要是为了触发表单项的 error 显示状态。
                // 如果 validate 返回 false，说明有错误，UI 上会显示红字。
                this.$refs.contractFormRef.validate((valid) => {

                });
            });
        },
        // 关闭弹窗重置表单
        handleClose() {
            this.dialogVisible = false;
            this.contractForm = {};
        },
        // 合同管理新增重置
        submitClearBtn() {
            this.contractForm = {};
        },
        // 合同管理新增保存
        submitSaveBtn() {
            // 1. 执行表单校验
            this.$refs.contractFormRef.validate((valid) => {
                if (valid) {
                    // 校验通过，执行保存逻辑
                    const keys = ['juniorNum', 'intermediateNum', 'seniorNum', 'expertNum'];
                    keys.forEach(key => {
                        if (!this.contractForm[key]) {
                            this.contractForm[key] = 0;
                        }
                    });
                    let params = this.contractForm;
                    saveOrUpdateContract(params).then(res => {
                        if(res.data.status == 200) {
                          this.contractForm = res.data.results;
                          this.dialogVisible = false;
                          this.submitBtn();
                          this.$message.success(this.isEdit ? "修改成功" : "新增成功")
                        } else {
                          this.$message.error(res.data.message)
                        }
                    }).catch(error => {
                          this.$message.error(error)
                    });
                } else {
                    // 校验不通过，提示用户
                    this.$message.warning('请填写必填项');
                    return false;
                }
            });
        },
        // 供应商名单导出
        clickExportButton() {
          const self = this;
          this.paramsForm.contractState = 'Y';
          exportSupplier(this.paramsForm).then(res => {
            common.download(res.data, self, "供应商名单.xlsx");
          });
        },

        // 关联管理
        detailContract(row) {
            window.localStorage.setItem('queryParams', JSON.stringify(this.paramsForm))
            this.$router.push({
                name: 'relateOutsource',
                query: {
                    id: row.id
                }
            })
        },
        // 编辑合同
        editContract(contract) {
            this.isEdit = true
            this.dialogVisible = true
            // 查询合同详情
            let params = {};
            params.id = contract.id;
            queryDetail(params).then(res => {
                if(res.data.status == 200) {
                  this.contractForm = res.data.results
                  this.$nextTick(() => {
                    this.$refs.contractFormRef.validate((valid) => {
                        // 同样，主要是为了触发 UI 上的错误显示
                    });
                  });
                }
            })
        },
        // 删除合同
        delRow(contract) {

            const outsourceCount = Number(contract.outsourceNum);

            // 校验：确保是有效数字 且 大于 0
            if (!isNaN(outsourceCount) && outsourceCount > 0) {
              this.$message.error('请先解除外包人员关联关系');
              return;
            }

            // 增加二次确认
            this.$confirm('确认要删除该合同吗？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              // 用户点击确定后执行删除逻辑
              let params = {};
              params.id = contract.id;
              delContract(params).then(res => {
                  if(res.data.status == 200) {
                    this.submitBtn();
                    this.$message.success(res.data.message)
                  } else {
                    this.$message.error(res.data.message)
                  }
              })
            }).catch(() => {
              // 用户点击取消或不操作，不做任何处理
              this.$message.info('已取消删除');
            });
        },
        // 查询外包供应商
        getWbcom() {
            getListAll()
                .then(res => {
                    this.outsourceno_options = res.data.results;
                })
                .catch(error => {
                    this.$message.error(error)
                });
        },


        // 获取权限
        getPermission() {
            queryRole().then(res => {
                if (res.data.status == 200) {
                    let roles = res.data.results;
                    let auth = false;
                    let kqAuth = false;
                    roles.forEach(function(item){
                        if(item.roleId == "00000001" ) {
                            auth = true;
                        }
                        if(item.roleId == 'KQ_YANGUANBU' || item.roleId == "PERMANAGE") {
                            kqAuth = true
                        }
                    });
                    this.auth = auth;
                    this.kqAuth = kqAuth;
                } else {
                    this.$message.error('查询权限失败');
                }
            })
        },
        // 双击表格
        dbTable(val) {
            this.detailContract(val)
      }
    },
}
</script>

<style scoped>
.interviewList {
    width: 100%;
    /* height: 100%; */
    padding: 15px;
}
.form_box {
    width: 100%;
    background-color: #ffffff;
    padding: 18px 18px 0 18px;
}
.table_box {
    width: 100%;
    background-color: #ffffff;
    padding: 18px;
}
.margin_bottom {
    margin-bottom: 15px
}
/* 分页组件位置 */
.pager_zb {
    width: 100%;
    display: flex;
    flex-direction: row-reverse;
}
</style>

<style>
.el-button {
    margin-left: 15px
}
.el-row {
    margin-bottom:  15px
}
</style>
