//生产环境
//module.exports = {
//  NODE_ENV: '"production"',
//  ENV_CONFIG: '"uat1"',
//}

module.exports = {
  //NODE只有两个选项：development(开发环境) 和 production(生产环境)
  NODE_ENV: '"production"',
  ENV_CONFIG: '"uat1"',
  HOST: 'localhost',
  PORT: 9001,
  ASSETS_PUBLIC_PATH: '/',
  //uat1环境的前端部署地址,HOST:PORT/mercury-web-uat1,后缀只在部署时有用
  BASE_API: '"/techapi"',
  BASE_URL_EXTEND:'""'//地址可用于后管预览weixinsupport.开始的外部图片
}
