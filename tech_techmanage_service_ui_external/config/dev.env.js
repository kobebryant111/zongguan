// 开发环境
//module.exports = {
//  NODE_ENV: '"development"',
//  ENV_CONFIG: '"dev"',
//  PORT: '"8000"',
//  //开发环境的前端部署地址，一般为
//}
module.exports = {
  //NODE只有两个选项：development(开发环境) 和 production(生产环境)
  NODE_ENV: '"development"',
  ENV_CONFIG: '"dev"',
  HOST: 'localhost',
  PORT: 9040,
  ASSETS_PUBLIC_PATH: '/',
  //DEV环境的前端部署地址,HOST:PORT/mercury-web-dev,后缀只在部署时有用
  BASE_API: '"/techapi"',
  BASE_URL_EXTEND: '"http://localhost:9011"'//地址可用于后管预览weixinsupport.开始的外部图片
}

