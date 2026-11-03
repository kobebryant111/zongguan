import store from '@/store'

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/directive.vue
 */
export default function checkPermission(value) {
  if (value && value instanceof Array && value.length > 0) {
    const roles = store.getters && store.getters.roles
    const permissionRoles = value

    const hasPermission = roles.some(role => {
      return permissionRoles.includes(role)
    })

    if (!hasPermission) {
      return false
    }
    return true
  } else {
    console.error(`need roles! Like v-permission="['admin','editor']"`)
    return false
  }
}



/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/directive.vue
 */
export  function checkFunc(value) {
  
  var funcs = store.getters && store.getters.funcs
    if(!funcs){
      funcs = [];
    }
    if(value && typeof value === 'string'){
      value = [value]
    }
    
    if (value && value instanceof Array && value.length > 0) {
      const func = value
      const hasFunc = funcs.some(role => {
        return func.includes(role)
      })
      if (!hasFunc) {
        return false;
      }
      return true;
    } else {
      return false;
    }
}
