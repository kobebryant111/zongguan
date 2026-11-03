# 工作流控制台工程迁移基础模板指南

### vue 文件迁移
```
src\views\wf  目录下的所有文件夹均为为工作流的页面。
              根据需求在基础工程模板的目录view新建wf文件夹,在复制所需要的文件夹到wf目录下即可
```
### api接口文件迁移
```            
src\api\wf\workflowConfig.js 此文件为工作流api 接口配置文件  
              把此目录下的workflowConfig.js文件直接复制到对应src\api\wf 的目录下，需创建好wf文件夹，在api.js这个文件中在把模块的api注册到总api中       
              // 示例代码 
              import workflowConfig from './wf/workflowConfig'
              const apiData = Object.assign({}, systemManage, workflowConfig)
```

 ### 工作流路由文件迁移
 ```                
src\router\tsmodules\wf\index.js 此文件为工作流的模块路由配置文件。
              角色路由页面根据需求在基础工程模板的目录view新建wf文件夹,在复制index.js文件夹到wf目录下即可,同时要把模块路由追加注册到动态总路由上，文件路径为src\router\tsmodules\index.js
               // 示例代码
              import { workflowRouter } from './wf'
              const routers = Object.assign({},workflowRouter)
               没有权限要求的基页路由，在wf/index 中声明并抛出后，把常量模块路由追加注册到常量总路由上,文件路径为src\router\index.js
              // 示例代码
              import { workflowConstRouter } from './tsmodules/wf/index'
              export const constantRoutes = [
               ..
               workflowConstRouter]
              
```

### 工作流迁移导航栏 
 ```    
迁移导航栏改为引用WfNavbar.vue   
src/layout/components/index.js
              
```

 ###  工作流设计器依赖
 ```                
  在package.json中追加工作流设计器的依赖
  // 示例代码
  "dependencies": {
     ..
     "tansun-wf-designer": "^1.1.3", // 当前最新版本
     }
```
# 基础工程模板

工程说明：

## 项目设置（安装node_modules依赖包，必须运行这个命令）

```
npm install
```

### 编译和热重装以进行开发
```
npm run dev
```

### 为测试环境构建 用来模拟预上线
```
npm run build:stage
```
### 为生产环境构建
```
npm run build:prod
```

### 检查代码规范并自动修复代码规范
```
npm run lint
```

### 自定义配置
请看  [配置参考](https://cli.vuejs.org/config/).

### 项目工程结构介绍
```
TemplateProject
├── public  首页入口文件
├── mock    前后端分离 mock 数据
├── docs    文档
│   ├── common  公共基础方法的使用demo文档
│   ├── request 网络请求的使用demo文档
│   ├── router 路由的使用demo文档
│   ├── svgicon 展示SVG组件使用文档
│   ├── styles 公共主题 的使用demo文档
│   ├── validate 常用校验方法的使用demo文档
├── src 开发目录
│   ├── layout 界面布局框架
│   ├── assets  放置资源
│   │   ├── icons   放置svg资源
│   │   ├── images  放置图片资源
│   ├── api  放置各个模块网络请求方法
│   ├── utils  工具类
│   │   ├── validate.js  验证方法文件
│   │   ├── constant.js  定义全局的变量
│   │   ├── common.js  通用方法的集合处
│   ├── components   全局通用组件目录
│   ├── plugins  管理第三方插件  
│   ├── router  路由配置
│   ├── store   状态管理  
│   ├── styles  公共的样式（项目的主题）
│   ├── views   放置业务开发页面
│   │   ├── error-page  404 页面 401页面
│   │   ├── login  登录模块
│   │   ├── ts-component-demo 天阳组件例子
│   │   ├── ts-help-center  天阳-帮助中心
│   │   ├── ts-other-demo  天阳其他例子
│   │   ├── ts-system-manage  天阳系统管理例子
│   ├── App.vue  项目入口文件 
│   └── main.js  项目核心文件   
├── .eslintignore  使用eslint时 忽视的 文件
├── .eslintrc.js  配置eslint 规则
├── babel.config.js JavaScript 编译器配置文件
├── package.json  项目配置文件
├── package-lock.json
├── .env.development 开发环境下的配置文件（重点用来切换不同的请求服务器）
├── .env.production  生产环境下的配置文件
├── .env.stage  用来模拟预上线 环境配置
├── postcss.config.js  JS插件来对CSS进行转换的工具配置文件
├── vue.config.js  可选的配置文件
└── README.md  项目说明文档
```

### 工程使用介绍
```
0、工程运行环境，安装node。直接到node官网，下载最新的稳定版本的node即可。任选一种前端开发编辑工具，使用webStorm或Visual Studio Code 。
1、拿到基础工程后，必须安装依赖。运行npm install。
2、打包或编译时，需要检查代码规范。使用npm run lint 进行检查代码规范并自动修复代码规范
3、.env.development，.env.production ，.env.stage 是配置 端口和网络请求地址的 入口。请看项目工程结构介绍。
4、src/plugins 管理全局插件，样式，js的入口。只要在这个文件中引入一次插件，样式，js便可在全局中使用。
5、项目模块越来越多，层级越来越多。统一使用文件的方式去层层管理模块，而不是将所有vue文件都放在一个文件夹中。
```

### [引入的库 常用的库](https://blog.csdn.net/weixin_43720095/article/details/88982224)
```
生产环境
"core-js": "^3.6.4", //使用babel-runtime、babel-plugin-transform-runtime 或者 babel-polyfill， 可以间接的引入了 core-js 标准库                                 
"vue": "^2.6.11",//vue框架
"vue-router": "^3.1.5",//路由
"vuex": "^3.1.2",// 状态管理
"axios": "0.18.1",//网络请求
"normalize.css": "7.0.0",//保留有用的浏览器默认样式
"js-cookie": "2.2.0",// 存储数据到Cookie
"nprogress": "0.2.0",//页面加载进度条
"path-to-regexp": "2.4.0"//处理 url 中地址与参数
开发环境
"@vue/cli-plugin-babel": "~4.2.0",
"@vue/cli-plugin-eslint": "~4.2.0",
"@vue/cli-plugin-router": "~4.2.0",
"@vue/cli-plugin-vuex": "~4.2.0",
"@vue/cli-service": "~4.2.0",
"babel-eslint": "^10.0.3",
"eslint": "^6.7.2",
"eslint-plugin-vue": "^6.1.2",
"vue-template-compiler": "^2.6.11",
"autoprefixer": "^9.5.1",//自动管理浏览器前缀的插件
"sass-loader": "^7.1.0"
"node-sass": "^4.9.0",//将.scss文件本地编译为css，
并通过连接中间件自动编译
"chalk": "2.4.2",//chalk是一个颜色的插件
"connect": "3.6.6",
"html-webpack-plugin": "3.2.0",//为html文件中引入的外部资源如script、link动态添加每次compile后的hash，防止引用缓存的外部文件问题
"mockjs": "1.0.1-beta3",//使用mock数据
"script-ext-html-webpack-plugin": "2.1.3",//添加async，defer或module属性的<script>元素，甚至他们内联
"script-loader": "0.7.2",
"serve-static": "^1.13.2",
"svg-sprite-loader": "4.1.3",
"svgo": "1.2.2",
```
