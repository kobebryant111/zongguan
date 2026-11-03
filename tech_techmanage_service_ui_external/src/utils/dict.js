import store from '@/store'

export function getDictName(dictType,dictCode){
    var dicts =  store.getters.dictList[dictType]
    var result = dictCode
    if(!dicts){
        return result
    }
    for(var tmp in dicts){
        if(dicts[tmp].dictId===dictCode){
            result = dicts[tmp].dictName
            break
        }
    }

    return result
}
export function getDictCode(dictType,dictName){
    var dicts =  store.getters.dictList[dictType]
    var result = dictName
    if(!dicts){
        return result
    }
    for(var tmp in dicts){
        if(dicts[tmp].dictName===dictName){
            result = dicts[tmp].dictId
            break
        }
    }

    return result
}
export function  getTypeDicts(dictType){
    return store.getters.dictList[dictType]
}



