/*
*   外包人员考勤
*/
import request from '@/utils/request'

/**
 * 列表分页查询
 * @param {*} data
 */
export function queryPager(data) {
    return request({
        url : '/ppmKqInfo/queryPager',
        method : 'post',
        data
    })
}
/**
 * 导入数据
 * @param {*} data
 */
export function importExcel(data) {
    return request({
        url : '/ppmKqInfo/importAttendanceInfo',
        method : 'post',
        data
    })
}
/**
 * 导入数据失败并导出错误信息
 * @param {*} data
 */
export function importErrorExcel(data) {
    return request({
        url : '/ppmKqInfo/exportErrorRecordExport',
        method : 'post',
        headers:{

            "Content-Type": 'application/json;charset=UTF-8',
        },
        responseType: "blob",
        data
    })
}
/*
*   修改异常信息
*/
export function update(data) {
    return request({
        url: "/ppmKqInfo/update",
        method: "post",
        data
    })
}
/**
 * 查询权限
 * @param {*} params
 */
export function queryRole(params) {
    return request({
        url : '/ppmKqCalendar/queryRole',
        method : 'post',
        params
    });
}

/**
 * 考勤excel导出
 * @param {*} data
 */
export function exportExcel(data) {
    return request({
        responseType: 'blob',
        url: '/ppmKqInfo/exportInfo',
        method: 'post',
        data
    })
};

/**
 * 考勤excel导出
 * @param {*} data
 */
export function exportRecordExcel(data) {
    return request({
        responseType: 'blob',
        url: '/ppmKqInfo/exportRecord',
        method: 'post',
        data
    })
};

/**
 * 导出异常考勤未确认
 * @param {*} data
 */
export function exportNoVerify(data) {
    return request({
        responseType: 'blob',
        url: '/ppmKqInfo/exportNoVerify',
        method: 'post',
        data
    })
};

/**
 * 导出工作量明细
 * @param {*} data
 */
export function exportWorkloadExcel(data) {
  return request({
      responseType: 'blob',
      url: '/ppmKqInfo/exportWorkload',
      method: 'post',
      data
  })
};



/*
*   获取加班时间配置
*/
export function queryOvertimeConfig(params) {
    return request({
        url: `/ppmKqInfo/queryOvertimeConfig?status=${params}`,
        method: 'get',
    })
}

/*
*   加班时间配置确认
*/
export function addOvertimeConfig(params) {
    return request({
        url: "/ppmKqInfo/addOvertimeConfig",
        method: 'post',
        params
    })
}

/*
*   数据跑批
*/
export function infoDataCalculateRunBatch() {
    return request({
        url: "/ppmKqInfo/infoDataCalculateRunBatch",
        method: 'post',
    })
}

/*
*   测试 手动跑批增加年假
*/
export function annualVacationBatch() {
    return request({
        url: "/ppmKqInfo/annualVacationBatch",
        method: 'post',
    })
}


/*
 * 详情
 */

export function queryDetail(id) {
    console.log(id, 'zzzzzzzzzzzzzzzzzzzzzzzzzzzzz')
    return request({
        url: `/ppmKqInfo/queryDetail?id=${id}`,
        method: "get",
        type: 'json'
    })
}
