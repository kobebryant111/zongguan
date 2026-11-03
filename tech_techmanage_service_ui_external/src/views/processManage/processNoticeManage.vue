<!-- 流程通知管理 -->
<template>
  <div class="app-container">
    <div class="app-main-container">
      <el-form :model="form" ref="form" inline>
        <el-form-item label="模板名称" prop="templatename">
          <el-input v-model="form.templatename"></el-input>
        </el-form-item>
        <el-form-item>
          <clean-btn :forms="this.form"/>
          <el-button type="primary" @click="onSearch">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-button type="success" @click="onAdd()">添 加</el-button>
      <el-table :data="tempData" border stripe>
        <el-table-column prop="templatename" label="模板名称"></el-table-column>
        <el-table-column prop="content" label="模板内容" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="primary" @click="onUpdate(scope.row)">修 改</el-button>
            <el-button type="danger" @click="onDelete(scope.row)">刪 除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pager :fns="getPager" :total="page.totalNumber" :pageSize="page.size" v-model="$attrs"/>
    </div>
    <div class="dialog-container">
      <el-dialog width="38%" title :visible.sync="visibleClose" @close="onCancel">
        <el-form :model="formData" ref="formData" :rules="rules" >
          <el-form-item label="模板名称" prop="templatename">
            <el-input v-model="formData.templatename"></el-input>
          </el-form-item>
          <el-form-item label="模板内容" prop="content">
            <el-input type="textarea" v-model="formData.content"></el-input>
          </el-form-item>
          <el-form-item label="提示">
            <div><span style="color:red">@USERNAME@</span>:用户名称</div>
            <div><span style="color:red">@PROCESSINSTNAME@</span>:流程名称</div>
            <div><span style="color:red">@PROCESSNAME@</span>:流程类型</div>
            <div><span style="color:red">@URL@</span>:待办地址</div>
            <div><span style="color:red">@TIME@</span>:时间</div>
          </el-form-item>
        </el-form>

        <span slot="footer">
          <el-button @click="onCancel">取 消</el-button>
          <el-button type="primary" @click="onSave">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import cleanBtn from "@/components/CleanBtn";
import common from "@/api/common";
import pager from "@/components/Pager";
import {
  getTemplateList,
  deleteTemplateForId,
  updateTmeplate,
  getTemplateDetail
} from "@/api/processApi/notice";
export default {
  components: { cleanBtn, pager },
  data() {
    return {
      page: {
        totalNumber: 0,
        size: 50, // 初始值 默认：50
        index: 1 // 初始值 默认：1
      },
      form: {
        templatename: "" // 模板名称
      },
      formData: {
        id: "",
        templatename: "",
        content: "",
        sqlStatus : '',     // add 添加，update:修改
      }, // 提交数据表单
      tempData: [], // 集合数据
      visibleClose: false, // dialog 默认关闭
      // 验证
      rules : {
           templatename : [
               { required: true, message: '模板名称不能为空', trigger: 'blur' }
           ],
           content : [
               { required: true, message: '模板内容不能为空', trigger: 'blur' }
           ]
      }
    };
    
  },
  mounted() {
    this.packageInit();
  },
  methods: {
    /**
     * 初始化
     */
    init(param) {
      console.log("param", param);
      let data = common.paramsFormat(param);
      getTemplateList(data).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.tempData = res.data.results;
        this.page.totalNumber = res.data.total;
      });
    }, // 初始化 end
    /**
     * 公共初始方法
     */
    packageInit: function() {
      let param = {};
      param.size = this.page.size;
      param.index = this.page.index;
      this.init(param);
    },
    /**
     * 分页触发
     */
    getPager: function(size, index) {
      let params = {};
      params.templatename = this.form.templatename;
      console.log("params", params);
      this.page.size = size;
      this.page.index = index;
      this.init(params);
    }, // 分页触发 end
    /**
     * 搜索
     */
    onSearch: function() {
      let params = {};
      params.templatename = this.form.templatename;
      params.size = this.page.size;
      params.index = this.page.index;
      this.init(params);
    }, // 搜索 end
    /**
     * 修改
     */
    onUpdate(row) {
      getTemplateDetail({id:row.id}).then(res => {
        if (res.data.status != 200) {
          this.$alert(res.data.message, { type: "error" });
          return false;
        }
        this.formData = res.data.results
        this.formData.sqlStatus = 'update'
        this.visibleClose = true
      });
    }, // 修改end
    /**
     * 刪除
     */
    onDelete(row) {
      this.$confirm("此操作为永久删除数据，确定继续吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        deleteTemplateForId({ id: row.id }).then(res => {
          if (res.data.status != 200) {
            this.$message.error(res.data.message);
            return false;
          } else {
            this.$message.success(res.data.message);
            this.packageInit();
          }
        });
      });
    }, // 刪除end
    /**
     * 添加
     */
    onAdd() {
      this.visibleClose = true;
      this.formData.sqlStatus = 'add'
    }, // 添加 end
    /**
     * 执行添加
     */
    onSave() {
        console.log('form::', this.formData)
        let data = common.paramsFormat(this.formData);
        this.$refs.formData.validate(vail => {
            console.log('vail', vail)
            if(!vail) {
                return false
            }
            updateTmeplate(data).then(res => {
            if(200 !== res.data.status) {
                this.$alert(res.data.message,{type:'error'})
                return false
            }
            this.$message.success(res.data.message)
            this.onCancel()
            this.packageInit()
        })
        })
        
    }, // 执行添加 end
    /**
     * 取消-关闭
     */
    onCancel() {
      this.visibleClose = false;
      common.cleanFrom(this.formData);
    }
  }
};
</script>

