<template>
  <div>
    <el-form ref="ecdDtFormRef" :rules="rules" :model="model" label-width="120px">
      <el-form-item label="编码明细编号" prop="ecdDtlId">
        <el-input v-model="model.ecdDtlId" placeholder="请输入编码明细编号" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="编码类型编号" prop="ecdTpId">
        <el-input v-model="model.ecdTpId" placeholder="请输入编码类型编号" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="编码生成规则" prop="ruleSrc">
        <ts-multi-select v-model="model.ruleSrc" placeholder="请选择状态" :params="RuleSrc" @change="onchange"></ts-multi-select>
      </el-form-item>
      <el-form-item label="序号" prop="sn">
        <el-input v-model="model.sn" placeholder="请输入序号"></el-input>
      </el-form-item>
      <el-form-item label="编码值" prop="ecdVal">
        <el-input v-model="model.ecdVal" placeholder="请输入编码值"></el-input>
      </el-form-item>
      <el-form-item label="编码长度" prop="ecdLen">
        <el-input v-model="model.ecdLen" placeholder="请输入编码长度"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="stCd">
        <ts-multi-select v-model="model.stCd" placeholder="请选择编码生成规则" :params="EcdDtStCd"></ts-multi-select>
      </el-form-item>
      <el-form-item label="提示" prop="tip" v-if="showTip">
        <div>
          {{ setRuleTip(model.ruleSrc) }}
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'EcdDtlForm',
  data() {
    return {
      showTip: false,
      EcdDtStCd: { key: 'StCd', group: '1', width: '100%' },
      RuleSrc: { key: 'EcdRuleSrc', width: '100%' },
      model: {
        ecdDtlId: '', // 编码明细编号
        ecdTpId: '', // 编码类型编号
        ruleSrc: '', // 编码生成规则
        sn: '', // 序号
        ecdVal: '', // 编码值
        ecdLen: '', // 编码长度
        stCd: '', // 状态
        id: ''
      },
      rules: {
        ecdDtlId: [
          { required: true, message: '请输入编码明细编号', trigger: 'blur' }
        ],
        ecdTpId: [
          { required: true, message: '请输入编码类型编号', trigger: 'blur' }
        ],
        ruleSrc: [
          { required: true, message: '请选择编码生成规则', trigger: 'blur' }
        ],
        sn: [
          { required: true, message: '请输入序号', trigger: 'blur' },
          { message: '请输入整数格式', pattern: /^[0-9]{1,9}$/ }
        ],
        ecdVal: [
          { required: true, message: '请输入编码值', trigger: 'blur' }
        ],
        ecdLen: [
          { required: false, message: '请输入编码长度' }
        ],
        stCd: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    onchange(value) {
      if (value === 'zeroFill') {
        // 序列
        debugger
        this.rules.ecdLen[0].required = true
      } else if (value === 'dynamicSerialFill') {
        // 动态序列
        this.rules.ecdLen[0].required = true
        debugger
      } else if (value === 'cascadeSerialFill') {
        //
        this.rules.ecdLen[0].required = true
        debugger
      } else {
        this.rules.ecdLen[0].required = false
      }

      this.showTip = this.setRuleTip(value) !== null
    },
    setRuleTip(ruleSrc) {
      let ruleTip = null
      switch (ruleSrc) {
        case 'constantFill':
          ruleTip = '直接使用编码值填充对应位置，如aaa、123'
          break
        case 'variableFill':
          ruleTip = '将编码值作为key，从传入的Map中获取value填充对应位置。'
          break
        case 'dynamicSerialFill':
          ruleTip = '使用编码值指定动态序列的前缀规则，填充对应位置。用于生成TICKET20180101D001、TICKET20180101D002、TICKET20180102D001这一类前缀变化后序列重新开始的编号。案例编号由TICKET + 日期 + D + 动态序列组成，前缀是TICKET + 日期 + D，编码值应填写前缀的位置：{1}{2}{3}。'
          break
        case 'cascadeSerialFill':
          ruleTip = '用于生成ORG001、ORG001001、ORG001001001这一类具有上下级关系的编号。使用时需要传入parentCode，如果不传或传入空值，效果同普通序列。'
          break
        case 'sysDateFill':
          ruleTip = '使用编码值指定日期、时间格式，生成编号时使用当前日期时间格式化后填充对应位置，如yyyyMMdd'
          break
        case 'zeroFill':
          ruleTip = '固定序列，使用递增值填充对应位置，如：000，001'
          break
        default :
          ruleTip = null
          break
      }
      return ruleTip
    }
  }
}
</script>
