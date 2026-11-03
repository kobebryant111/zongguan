<!--
 * @Author: your name
 * @Date: 2020-04-07 09:47:49
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-21 10:11:51
 * @Description:
-->
<template>
  <div>
    <el-form ref="eventFormRef" :disabled="model.formDisabled" :model="model" :rules="rules" class="search-container">
      <el-form-item label="事件编号" prop="eventCode">
        <el-input v-model="model.eventCode" placeholder="请输入事件编号">
        </el-input>
      </el-form-item>
      <el-form-item label="事件url" prop="url">
        <el-input v-model="model.url" placeholder="请输入事件url"></el-input>
      </el-form-item>
      <el-form-item label="事件名称" prop="eventName">
        <el-input v-model="model.eventName" placeholder="请输入事件名称">
        </el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  components: {},
  name: 'EventForm',
  data() {
    return {
      model: {
        eventCode: '', // 事件编号
        url: '', // 事件url
        eventName: ''// 事件名称
      },
      rules: {
        eventCode: [
          { required: true, message: '请输入事件编号', trigger: 'blur' }
        ],
        eventName: [
          { required: true, message: '请输入事件名称', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '请输入事件url', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {}
}
</script>

