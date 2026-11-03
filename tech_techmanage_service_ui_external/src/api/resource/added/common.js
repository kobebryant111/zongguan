export default {
  // JS乘法精度问题
  accMul(arg1, arg2) {
    var m = 0,
      s1 = arg1.toString(),
      s2 = arg2.toString()
    try {
      m += s1.split('.')[1].length
    } catch (e) {}
    try {
      m += s2.split('.')[1].length
    } catch (e) {}
    return (Number(s1.replace('.', '')) * Number(s2.replace('.', ''))) / Math.pow(10, m)
  },

  //JS减法精度丢失问题
  numSub(num1, num2) {
    var baseNum, baseNum1, baseNum2
    var precision //精度
    try {
      baseNum1 = num1.toString().split('.')[1].length
    } catch (e) {
      baseNum1 = 0
    }
    try {
      baseNum2 = num2.toString().split('.')[1].length
    } catch (e) {
      baseNum2 = 0
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2))
    precision = baseNum1 >= baseNum2 ? baseNum1 : baseNum2
    return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision)
  },

  //JS加法精度丢失问题
  numAdd(num1, num2) {
    var baseNum, baseNum1, baseNum2
    try {
      baseNum1 = num1.toString().split('.')[1].length
    } catch (e) {
      baseNum1 = 0
    }
    try {
      baseNum2 = num2.toString().split('.')[1].length
    } catch (e) {
      baseNum2 = 0
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2))
    return (num1 * baseNum + num2 * baseNum) / baseNum
  },

  //JS除法精度问题
  accDiv(arg1, arg2) {
    var t1 = 0,
      t2 = 0,
      r1,
      r2
    try {
      t1 = arg1.toString().split('.')[1].length
    } catch (e) {}
    try {
      t2 = arg2.toString().split('.')[1].length
    } catch (e) {}
    r1 = Number(arg1.toString().replace('.', ''))
    r2 = Number(arg2.toString().replace('.', ''))
    return (r1 / r2) * Math.pow(10, t2 - t1)
  }
}
