<template>
  <div style="display: inline-block">
    <!-- 触发按钮 -->
    <buttonOrange :fns="handleUpload" style="margin-left:10px;" button-name="上传" size="small" />
    <!-- 隐藏的文件输入框 -->
    <input
      ref="excelUploadInput"
      type="file"
      accept=".xlsx, .xls"
      style="display: none;"
      @change="handleFileSelect"
    >
  </div>
</template>

<script>
import XLSX from 'xlsx'
import buttonOrange from '@/components/eleButton/buttonOrange'

export default {
  name: 'AttendFile',
  components: {
    buttonOrange
  },
  data() {
    return {
    }
  },
  methods: {
    handleUpload() {
    // 触发隐藏的文件输入框点击
      this.$refs.excelUploadInput.click()
    },
    handleFileSelect(event) {
      const file = event.target.files[0]
      if (!file) return
      this.upload(file)
    },
    upload(rawFile) {
      this.$refs.excelUploadInput.value = null
      if (!this.beforeUpload) {
        this.readerData(rawFile)
        return
      }
      const before = this.beforeUpload(rawFile)
      if (before) {
        this.readerData(rawFile)
      }
    },
    readerData(rawFile) {
      this.loading = true
      return new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.onload = e => {
          const data = e.target.result
          const fixedData = this.fixData(data)
          const workbook = XLSX.read(btoa(fixedData), { type: 'base64' })
          const firstSheetName = workbook.SheetNames[0]
          const worksheet = workbook.Sheets[firstSheetName]
          const jsonData = XLSX.utils.sheet_to_json(worksheet)

          // 校验Excel表
          const requiredCols = ['研发部门', '姓名', '工号', '研发人员性质', '研发项目', '占比']
          const missingCols = requiredCols.filter(col => !(col in jsonData[1]))
          if (missingCols.length > 0) {
            this.$message.error('excel校验无效')
            return
          }
          this.$emit('exData', jsonData)
          this.loading = false
          resolve()
        }
        reader.readAsArrayBuffer(rawFile)
      })
    },
    fixData(data) {
      let o = ''
      let l = 0
      const w = 10240
      for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
      return o
    }

  }
}
</script>

<style scoped>
.excel-upload-input{
  display: none;
  z-index: -9999;
}
</style>
