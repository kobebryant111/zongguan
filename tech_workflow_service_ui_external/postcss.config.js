// https://github.com/michael-ciniawsky/postcss-load-config
module.exports = {
  'plugins': {
    'autoprefixer': {}
  }
}

// public/index.html在div外包一个div指定class,class的名称和postcss.config.js中设置的return一样。如下
// module.exports = {
//   plugins: {
//     // postcss-selector-namespace: 给所有css添加统一前缀，然后父项目添加命名空间
//     'postcss-selector-namespace': {
//       namespace(css) {
//         // element-ui的样式不需要添加命名空间
//         if (css.includes('element-variables.scss')) return ''
//         return '.workflow-space1' // 返回要添加的类名
//       }
//     }
//   }
// }
