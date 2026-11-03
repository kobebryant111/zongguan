
import store from '@/store'

export default{
  inserted(el, binding, vnode) {
    var { value } = binding
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
        el.parentNode && el.parentNode.removeChild(el)
        // const cloneEl = el.cloneNode(true);
        // cloneEl.addEventListener("click",function(){
        //   alert("您没有该操作的权限，请联系系统管理员！");
        // });
        // el.parentNode && el.parentNode.replaceChild(cloneEl , el);
      }
    } else {
      throw new Error(`need roles! Like v-hasFunc="['add','editor']"`)
    }
  }
}
