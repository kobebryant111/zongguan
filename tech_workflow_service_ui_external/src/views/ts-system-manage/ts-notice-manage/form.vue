<template>
  <div>
    <el-form ref="articleFormRef" :disabled="model.formDisabled" :model="model" :rules="rules" label-width="80px">
      <el-col :span="11">
        <el-form-item label="文章标题" prop="title">
          <el-input v-model="model.title" placeholder="请输入文章标题" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="文章编号" prop="articleId">
          <el-input
              v-model="model.articleId"
              placeholder="请输入文章编号"
              :disabled='true'
              clearable
              :style="{width: '100%'}"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="文章类型" prop="type">
          <ts-multi-select v-model="model.type" :params="articleType" placeholder="请选择文章类型" :disabled="true"></ts-multi-select>
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="日期选择" prop="publishDate">
          <el-date-picker
              v-model="model.publishDate"
              type="date"
              :style="{width: '100%'}"
              placeholder="请选择日期"
              clearable></el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="23">
        <el-form-item label="发布系统" prop="categoryIdList">
          <ts-multi-checkbox
              v-model="model.categoryIdList"
              :params="PubSystem"
              @change="checkedCategorysChange"></ts-multi-checkbox>
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="创建人" prop="createUserNm">
          <el-input v-model="model.createUserNm" :disabled="true" :style="{width: '100%'}">
          </el-input>
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="创建时间" prop="createTime">
          <el-input v-model="model.createTime" :disabled="true" :style="{width: '100%'}">
          </el-input>
        </el-form-item>
      </el-col>
      <el-col :span="22" v-if="model.editType">
        <el-form-item label="发布状态" prop="statusCd" :style="{width: '50%'}">
          <ts-multi-select v-model="model.statusCd" :params="articleStCd" :disabled="true">
          </ts-multi-select>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="文章内容" prop="content">
          <ts-tinymce v-if="!model.formDisabled" v-model="model.content" :height="300"></ts-tinymce>
          <div
              v-if="model.formDisabled"
              style="border:1px #999999 solid;width:100%;padding: 3px"
              v-html="model.content"></div>
        </el-form-item>
      </el-col>
    </el-form>
  </div>
</template>

<script>

export default {
  name: 'ArticleForm',
  data() {
    return {
      articleType: { key: 'AricleType', width: '100%', clearable: true },
      articleStCd: { key: 'ArticleStCd', width: '100%' },
      PubSystem: { key: 'PubSystem' },
      model: {
        title: '',
        articleId: '',
        publishDate: '',
        type: 'notice',
        createUserNm: '',
        createTime: '',
        statusCd: 0,
        content: '',
        editType: true,
        formDisabled: false,
        categoryIdList: ''

      },
      rules: {
        title: [
          { required: true, message: '请输入文章标题', trigger: 'blur' }
        ],
        articleId: [
          { required: true, message: '请输入文章编号', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择文章类型', trigger: 'blur' }
        ],
        publishDate: [
          { required: true, message: '请选择发布日期', trigger: 'change' }
        ],
        createTime: [
          { required: true, message: '请选择创建时间', trigger: 'change' }
        ],
        categoryId: [
          { required: true, message: '请选择发布系统', trigger: 'change' }
        ],
        statusCd: [
          { required: true, message: '请选择状态', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    checkedCategorysChange(value) {
      // console.log("categoryIdList",this.model.categoryIdList);
      // console.log("value",value);
    }
  }
}
</script>
