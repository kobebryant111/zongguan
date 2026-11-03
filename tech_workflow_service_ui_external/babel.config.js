module.exports = {
  presets: [
    ['@vue/app', {
      useBuiltIns: 'entry',
      plugins: [
        [require('@babel/plugin-proposal-class-properties'), { loose: true }]
      ]
    }]
  ],
  sourceType: 'unambiguous'
}
