'use strict'
const os = require('os')
const speedMeasurePlugin = require('speed-measure-webpack-plugin')
const threadLoader = require('thread-loader')
const smp = new speedMeasurePlugin() // 速度分析
const StatsPlugin = require('stats-webpack-plugin')
const path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)
}

const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const CSSSplitWebpackPlugin = require('css-split-webpack-plugin').default

// 所有配置说明请看 https://cli.vuejs.org/config/
module.exports = {
  runtimeCompiler: true,
  // 部署生产环境和开发环境下的URL：可对当前环境进行区分
  publicPath: process.env.NODE_ENV === 'production' ? './' : './',
  // publicPath路径需改成服务的绝对路径(发布测试环境时记得改成对应的ip再打包)   未前端改造
  // 输出文件目录
  outputDir: 'dist',
  // 放置生成的静态资源
  assetsDir: 'static',
  //eslint -loader 是否在保存的时候检查
  lintOnSave: false,
  // 生产环境是否生成 sourceMap 文件  设置成true 可以在浏览器看到代码
  productionSourceMap: false,
  // 是否为 Babel 或 TypeScript 使用 thread-loader。该选项在系统的 CPU 有多于一个内核时自动启用，仅作用于生产构建。
  parallel: false,
  devServer: {
    port: process.env.VUE_APP_BASE_PORT,
    open: true,
    headers: {
      'Access-Control-Allow-Origin': '*'
    },
    overlay: {
      warnings: false,
      errors: true
    },
    // before: require('./mock/mock-server.js')
    proxy: {
      [process.env.VUE_APP_BASE_API]: {
        target: process.env.VUE_APP_BASE_HOST,
        // 允许跨域
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: ''
        }
      }
    }
  },
  chainWebpack(config) {
    config.plugins.delete('preload')
    config.plugins.delete('prefetch')

    // set svg-sprite-loader
    config.module
      .rule('svg')
      .exclude.add(resolve('src/assets/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/assets/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
    // set preserveWhitespace
    config.module
      .rule('vue')
      .use('vue-loader')
      .loader('vue-loader')
      .tap(options => {
        options.compilerOptions.preserveWhitespace = true
        return options
      })
      .end()
    // 配置 jquery
    config.module
      .rule('expose1')
      .test(require.resolve('jquery'))
      .use()
      .loader('expose-loader')
      .options('jQuery')
      .end()
    config.module
      .rule('expose2')
      .test(require.resolve('jquery'))
      .use()
      .loader('expose-loader')
      .options('$')
      .end()

    // 分包
    config.optimization.splitChunks({
      chunks: 'all',
      maxSize: 524288, // 1mb
      cacheGroups: {
        libs: {
          name: 'chunk-libs',
          test: /[\\/]node_modules[\\/]/,
          priority: 20,
          chunks: 'initial' // only package third parties that are initially dependent
        },
        elementUI: {
          name: 'chunk-elementUI', // split elementUI into a single package
          priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
          test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
        },
        components: {
          name: 'components',
          test: resolve('src/components'), // can customize your rules
          minChunks: 3, //  minimum common number
          priority: 10,
          reuseExistingChunk: true
        }
      }
    })
    config.optimization.runtimeChunk('single')

    config
      // https://webpack.js.org/configuration/devtool/#development
      .when(process.env.NODE_ENV === 'development',
        config => config.devtool('eval')
      )

    config
      .when(process.env.NODE_ENV === 'production',
        config => {
          config
            .plugin('ScriptExtHtmlWebpackPlugin')
            .after('html')
            .use('script-ext-html-webpack-plugin', [{
              // `runtime` must same as runtimeChunk name. default is `runtime`
              // 打包时生成一个体积很小runtime.xxx.js文件，用作映射其他chunk文件，目的是更新后，以较小的代价利用浏览器缓存机制，提升页面加载速度。
              // 结合script-ext-html-webpack-plugin插件可将runtime文件内联进html文件，减少请求
              // inline: /runtime\..*\.js$/
            }])
            .end()
        }
      )
  },
  configureWebpack: smp.wrap({
    name: process.env.VUE_APP_NAME,
    entry: ['@babel/polyfill', './src/main.js'],
    resolve: {
      alias: {
        '@': resolve('src')
      }
    },
    output: {
      library: 'workflow', // 根据实际的应用名称
      libraryTarget: 'window' // 不用修改
    },
    plugins: process.env.NODE_ENV === 'production' ? [
      new MiniCssExtractPlugin({}),
      new CSSSplitWebpackPlugin({
        size: 5000
      }),
      new StatsPlugin('manifest.json', {
        chunkModules: false,
        entrypoints: true,
        source: false,
        chunks: false,
        modules: false,
        assets: false,
        children: false,
        exclude: [/node_modules/]
      })
    ] : [
      new MiniCssExtractPlugin({}),
      new CSSSplitWebpackPlugin({
        size: 5000
      }),
      new StatsPlugin('manifest.json', {
        chunkModules: false,
        entrypoints: true,
        source: false,
        chunks: false,
        modules: false,
        assets: false,
        children: false,
        exclude: [/node_modules/]
      })
    ],
    // 缓存生成的 webpack 模块和 chunk，来改善构建速度
    cache: true

  }),
  transpileDependencies: ['tsvue'],
  css: {
    // 开启 CSS source maps?
    sourceMap: false,
    extract: true
  }
}
