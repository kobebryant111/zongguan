<template>
  <div class="page-container">
    <el-form ref="searchFormRef" v-model="searchForm" :inline="true" size="small" class="search-container">
      <el-form-item label="公告标题">
        <el-input v-model="searchForm.titleLike" clearable>
        </el-input>
      </el-form-item>
      <el-form-item label="文章类型">
        <ts-multi-select v-model="searchForm.type" :params="articleType"></ts-multi-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button type="primary" icon="el-icon-refresh" @click="resetSearchFrm">重置</el-button>
      <el-button type="primary" :icon="isExpanded?'el-icon-arrow-up':'el-icon-arrow-down'" @click="isExpanded=!isExpanded"/>
      <div v-if="isExpanded">
        <el-form-item label="发布日期">
          <el-date-picker
            v-model="searchForm.selectDt"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发布状态">
          <ts-multi-select v-model="searchForm.statusCd" :params="articleStCd"></ts-multi-select>
        </el-form-item>
        <el-form-item label="发布系统" fixed="left">
          <ts-multi-checkbox v-model="searchForm.categoryIdList" :params="PubSystem"></ts-multi-checkbox>
        </el-form-item>
      </div>
    </el-form>

    <el-button-group class="handle-container">
      <el-button type="primary" icon="el-icon-plus" @click="addArt">新增</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="modifyArt">修改</el-button>
      <el-button type="primary" icon="el-icon-search" @click="lookArt">查看</el-button>
      <el-button type="primary" icon="el-icon-delete" @click="deleteArt">删除</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="retract">撤回</el-button>
      <el-button type="primary" icon="el-icon-check" @click="publish">手工发布</el-button>
    </el-button-group>

    <!--列表-->
    <ts-multi-table ref="articleTable" :query="searchForm" :options="tableOptions" @reset-query="refreshQuery">
      <el-table-column prop="articleId" label="文章编号" min-width="120"></el-table-column>
      <el-table-column prop="title" label="文章标题" min-width="180" show-overflow-tooltip></el-table-column>
      <el-table-column prop="type" label="文章类型">
        <template slot-scope="scope" >
          {{scope.row.type | tsConvert('AricleType')}}
        </template>
      </el-table-column>
      <el-table-column prop="statusCd" label="发布状态">
        <template slot-scope="scope">
          {{scope.row.statusCd | tsConvert('ArticleStCd')}}
        </template>
      </el-table-column>
      <el-table-column prop="categoryId" label="发布系统">
        <template slot-scope="scope">
          {{scope.row.categoryId | tsConvert('PubSystem')}}
        </template>
      </el-table-column>
      <el-table-column prop="createUserNm" label="创建人"></el-table-column>
      <el-table-column prop="publishDate" label="发布时间" min-width="100">
        <template slot-scope="scope">
          {{scope.row.publishDate | tsDate}}
        </template>
      </el-table-column>
    </ts-multi-table>
    <el-dialog
        title="撤回选中"
        :visible.sync="retractDialogVisible"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        width="30%"
    >
      <span>是否撤回该数据?</span>
      <span slot="footer">
        <el-button type="primary" size="small" @click="retractClick(1)">仅撤回选中文章</el-button>
        <el-button type="danger" size="small" @click="retractClick(2)">撤回所有同类文章</el-button>
        <el-button size="small" @click="retractDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <el-dialog
        title="发布选中"
        :visible.sync="publishDialogVisible"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        width="30%"
    >
      <span>是否发布该数据?</span>
      <span slot="footer">
        <el-button type="primary" size="small" @click="publishClick(1)">仅发布选中文章</el-button>
        <el-button type="danger" size="small" @click="publishClick(2)">发布所有同类文章</el-button>
        <el-button size="small" @click="publishDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <el-dialog
        title="删除选中"
        :visible.sync="deleteDialogVisible"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        width="30%"
    >
      <span>是否删除该数据?</span>
      <span slot="footer">
        <el-button type="primary" size="small" @click="deleteClick(1)">仅删除选中文章</el-button>
        <el-button type="danger" size="small" @click="deleteClick(2)">删除所有同类文章</el-button>
        <el-button size="small" @click="deleteDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>

    <ts-multi-dialog
        :visible.sync="dialogVisible"
        :options="dialogOptions">
    </ts-multi-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ArticleForm from './form'

export default {
  name: 'TsArticleManage',
  components: {},
  data() {
    return {
      isExpanded: false,
      articleType: { key: 'AricleType', size: 'small' },
      articleStCd: { key: 'ArticleStCd', size: 'small' },
      PubSystem: { key: 'PubSystem', size: 'small' },
      dialogVisible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      dialogOptions: {
        title: '文章明细', // [必填]
        width: '850px', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        resource: ArticleForm, // [必填] 在弹框中展示的组件页面
        data: {},
        beforeClose: (data, done) => { done() }, // [可不填] 关闭右上角弹框前的回调
        // buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.confirm] // [必填] 若取消按钮没有回调，可写默认值null 如[null, this.submit]
      },
      tableOptions: {
        type: 'radio',
        apiName: this.$api.queryArticle,
        paramsOrdata: 'data'
      },
      searchForm: {
        categoryIdList: '',
        type: '',
        titleLike: '',
        selectDt: '',
        statusCd: ''
      },
      retractDialogVisible: false,
      publishDialogVisible: false,
      deleteDialogVisible: false,
      selected: false,
      selectData: {}
    }
  },
  computed: {
    ...mapState({
      'userInfo': state => state.user.userInfo
    })
  },
  created() {
  },
  methods: {
    // 用户搜索
    search() {
      this.$refs['articleTable'].serchHandel()
    },
    resetSearchFrm() {
      this.$refs['articleTable'].resetData()
    },
    refreshQuery(query) {
      this.searchForm = query
    },
    // 单选
    selectClick(selection) {
      if (selection.length > 0) {
        this.selected = true
        this.selectData = selection
      }
    },
    getArticle(params, callback) {
      // 获取文章信息
      this.$request({
        url: this.$api.getArticle,
        data: params
      }).then((response) => {
        callback(response)
      })
    },
    cancel(data, done) {
      done()
    },
    confirm(data, done) {
      // 新增，修改
      data.categoryIds = data.categoryIdList

      this.$request({
        url: this.$api.saveArticle,
        data: data
      }).then((response) => {
        this.dialogVisible = false
        this.$message.success(response.description)
        this.$refs['articleTable'].serchHandel()
        done()
      })
    },
    addArt() {
      this.getArticle({}, (response) => {
        this.dialogOptions.data = response.data
        this.dialogOptions.data.categoryIdList = []
        this.dialogOptions.data.editType = false
        this.dialogOptions.data.formDisabled = false
        this.dialogVisible = true
      })
    },
    modifyArt() {
      // 修改
      const radioData = this.$refs['articleTable'].getRadioData()
      if (radioData) {
        this.getArticle({
          id: radioData.id,
          articleId: radioData.articleId
        }, (response) => {
          this.dialogOptions.data = response.data
          this.dialogOptions.data.categoryIdList = response.data.categoryIds
          this.dialogOptions.data.editType = true
          this.dialogOptions.data.formDisabled = false
          this.dialogVisible = true
        })
      } else {
        this.$alert('该操作至少选择一条记录')
      }
    },
    lookArt() {
      // 查看
      const radioData = this.$refs['articleTable'].getRadioData()
      if (radioData) {
        this.getArticle({
          id: radioData.id,
          articleId: radioData.articleId
        }, (response) => {
          this.dialogOptions.data = response.data
          this.dialogOptions.data.categoryIdList = (response.data.categoryIds || '').split(',')
          this.dialogOptions.data.editType = true
          this.dialogOptions.data.formDisabled = true
          this.dialogVisible = true
        })
      } else {
        this.$alert('该操作至少选择一条记录')
      }
    },
    deleteArt() {
      // 删除
      this.selectData = this.$refs['articleTable'].getRadioData()
      if (this.selectData) {
        if (this.selectData.statusCd === 1) {
          this.$alert('生效数据无法删除')
        } else {
          this.deleteDialogVisible = true
        }
      } else {
        this.$alert('该操作至少选择一条记录')
      }
    },
    deleteClick(key) {
      const param = {}
      if (key === 1) {
        param.id = this.selectData.id
      }
      if (key === 2) {
        param.articleId = this.selectData.articleId
      }
      this.deleteDialogVisible = false

      this.$request({
        url: this.$api.deleteArticle,
        data: param
      }).then((response) => {
        this.$message.success(response.description)
        this.$refs['articleTable'].serchHandel()
      })
    },
    retract() {
      // 撤回
      this.selectData = this.$refs['articleTable'].getRadioData()
      if (this.selectData) {
        if (this.selectData.statusCd === 1) {
          this.retractDialogVisible = true
        } else {
          this.$alert('已撤回数据无法撤回')
        }
      } else {
        this.$alert('该操作至少选择一条记录')
      }
    },
    retractClick(key) {
      const param = {}
      if (key === 1) {
        param.id = this.selectData.id
      }
      if (key === 2) {
        param.articleId = this.selectData.articleId
      }
      this.retractDialogVisible = false

      this.$request({
        url: this.$api.retractArticle,
        data: param
      }).then((response) => {
        this.$message.success(response.description)
        this.$refs['articleTable'].serchHandel()
      })
    },
    publish() {
      // 发布
      this.selectData = this.$refs['articleTable'].getRadioData()
      if (this.selectData) {
        if (this.selectData.statusCd === 1) {
          this.$alert('已发布数据无法继续发布')
        } else {
          const toDay = new Date()
          if (toDay < new Date(this.selectData.publishDate)) {
            this.$alert('当前日期未到发布日期，不允许发布')
            return
          }
          this.publishDialogVisible = true
        }
      } else {
        this.$alert('该操作至少选择一条记录')
      }
    },
    publishClick(key) {
      const param = {}
      if (key === 1) {
        param.id = this.selectData.id
      }
      if (key === 2) {
        param.articleId = this.selectData.articleId
      }
      this.publishDialogVisible = false

      this.$request({
        url: this.$api.publishArticle,
        data: param
      }).then((response) => {
        this.$message.success(response.description)
        this.$refs['articleTable'].serchHandel()
      })
    }
  }
}
</script>

<style scoped>

</style>
