import store from '@/store'
import { queryAllDict } from '@/api/eosDictEntry'


const dict = {
  state: {
    dictList:{}
  },
  mutations: {
    SET_ALL_DICT: (state, dictList) => {
      state.dictList = dictList
      console.info(state);
    },
    SET_TYPE_DICT: (state, {type,dictList}) => {
        state.dictList[type]=dictList
    },
    
  },
  getters:{
    
  },
  actions: {
    loadAllDict({ commit }) {
      queryAllDict().then(res => {
        if (res.data.status=='200'&&res.data.results) {
          console.info( res.data.results,' res.data.results')
          var dictList = res.data.results;
          var dictMap = {};
          for(var i=0;i<dictList.length;i++ ){
            var dict = dictList[i];
            var list = dictMap[dict.dicttypeid];
            if(!list){
              list = [];
                dictMap[dict.dicttypeid] = list;
            }
            list.push({dictId: dict.dictid,
            dictName: dict.dictname,
            dictTypeId: dict.dicttypeid,
            rank: dict.rank })
          }
          console.info(dictMap)
            commit('SET_ALL_DICT',dictMap)
        } 
      }).catch(error => {
        
      })
    }
  }
}

export default dict
