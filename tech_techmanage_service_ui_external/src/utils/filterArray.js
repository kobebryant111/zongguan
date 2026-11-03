// 准入页面过滤数组
export function filterArr(ele, prov, error) {  
    var eleArr = [];
    if (prov.length == 0) { 
        prov.push(error);
        return prov;
    } else {
        for (var i = 0; i < prov.length; i++) {
            eleArr.push(prov[i].id);
        } 
        if (eleArr.indexOf(ele) == -1) { 
            prov.push(error);
            return prov;
        } else {
            return prov;
        }
    }

}