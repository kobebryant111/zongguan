// dev sit uat prod
// TODO prod环境需要再修改
export const headers = {
  "env": process.env.ENV_CONFIG
}

export const formDataHeaders = {
  "env": process.env.ENV_CONFIG,
  "Content-Type": "application/x-www-form-urlencoded"
}

export const jsonHeaders = {
  "env": process.env.ENV_CONFIG,
  "Content-Type": "application/json"
}