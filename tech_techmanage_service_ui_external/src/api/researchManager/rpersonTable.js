/**
 *  异常考勤处理
 */
import request from '@/utils/request'


export default {

    /**
     * 列表分页查询
     * @param {*} data
     */
    queryPersonList(data) {
        return request({
            url : '/hrResources/queryPager',
            method : 'post',
            data
        })
    },
    /**
     * 列表查询
     * @param {*} data
     */
    queryList(data) {
        return request({
            url : '/hrResources/queryList',
            method : 'post',
            data
        })
    },
    /**
     * 研发工作管理列表查询
     * @param {*} data
     */
    queryAdministrationList(data) {
        return request({
            url : '/hrResources/queryAdministrationList',
            method : 'post',
            data
        })
    },
    /**
     * 列表分页导出
     * @param {*} data
     */
    queryPersonListInfo(data) {
        return request({
            url : '/hrResources/queryPagerInfo',
            method : 'post',
            data
        })
    },

    /**
     * 可添加列表
     */
    queryAddList(data){
        return request({
            url : '/hrResources/emqueryPager',
            method : 'post',
            data
        })
    },

    /*
    *   添加
    */
    addPerson(data) {
        return request({
            url: "/hrResources/add",
            method: "post",
            data
        })
    },
    /**
     *
     * 编辑
     */
    updatePerson(data) {
        return request({
            url: "hrResources/edit",
            method: "post",
            data
        })
    },
    /**
     * 部门调整
     */
    depidJump(data) {
        return request({
            url: "humanResources/jobTransfer",
            method: "post",
            data
        })
    },
    /**
     * 导出年度考勤
     */
    importYearKq(data) {
        return request({
            url: "humanResources/importYearAll",
            method: "post",
            data
        })
    },

    /*
    *   删除
    */
    deletePerson(data) {
        return request({
            url: "hrResources/delete",
            method: "post",
            data
        })
    },
    /**
     * 考勤期间管理
     */
    queryMonthList(data){

        return request({
            url: "hrResources/atManagement",
            method: "post",
            data

        })
    },
    /**
     * 获取机构树
     */
    queryOrgTree(){
        let data = {}
        return request({
            url: "hrResources/queryDeptTree",
            method: "post",
            data
        })

    },
    /**
     * 获取年份
     */
  currentYear() {
    let data = {}
    return request({
      url: 'humanResources/currentAllYear',
      method: 'post',
      data
    })
  },
    /**
     * 获取部门
     */
    quertOrgDepartment(){
        let data = {}
        return request({
            url: "hrResources/queryDepartment",
            method: "post",
            data
        })
    },
    /**
     * 查询机构下用户
     */
    queryOrgPerson(data){

        return request({
            url: "hrResources/rdQueryDeptTree",
            method: "post",
            data

        })
    },
    /**
     * 根据研发人员查项目
     */
    queryResourceByPerson(data){

        return request({
            url: "hrResources/rdQueryProject",
            method: "post",
            data

        })
    },
    /**
     * 根据研发人员查项目
     */
    queryResourceByPersonInfo(data){

        return request({
            url: "hrResources/rdQueryProjectInfo",
            method: "post",
            data

        })
    },
    /**
     * 删除研发人员参与项目
     * @param {*} data
     * @returns
     */
    deleteResourceByPerson(data){
        return request({
            url: "hrResources/prDelete",
            method: "post",
            data

        })
    },
    /**
     * 查询部门参与的项目
     * @param {*} data
     * @returns
     */
    queryResourceByDept(data){
        return request({
            url: "hrResources/rdProject",
            method: "post",
            data

        })
    },
    /**
     * 增加研发项目
     */
    addResource(data){
        return request({
            url: "hrResources/addProject",
            method: "post",
            data

        })
    },
    /**
     * 获取研发考勤信息
     */
    queryAttendance(data){
        return request({
            url: "hrResources/rdManagementProject",
            method: "post",
            data

        })
    },
    /**
     * 获取研发考勤信息
     */
    queryAttendanceTabulation(data){
        return request({
            url: "hrResources/queryAttendanceTabulation",
            method: "post",
            data

        })
    },
    /**
     * 获取员工考勤信息
     */
    queryPersonAttendance(data){
        return request({
            url: "hrResources/attendanceRecord",
            method: "post",
            data

        })
    },
    /**
     * 获取员工考勤信息
     */
    queryPersonAttendanceTabulation(data){
        return request({
            url: "hrResources/qyeryEmployeeAttendance",
            method: "post",
            data

        })
    },
    /**
     * 更改考勤月状态
     */
    updateMonthStatus(data){
        return request({
            url: "hrResources/atEditManagement",
            method: "post",
            data

        })
    },

    /**
     * 保存考勤
     */
    saveAttendance(data){
        return request({
            url: "hrResources/keep",
            method: "post",
            data

        })
    },
    /**
     * 保存考勤
     */
    submitAttendance(data){
        return request({
            url: "hrResources/submit",
            method: "post",
            data

        })
    },
    /**
     * 获取审核考勤数据
     */
    getApproveAttendance(data){
        return request({
            url: "hrResources/attendanceAudit",
            method: "post",
            data

        })
    },

    infoApproveAttendance(data){
        return request({
            url: "hrResources/attendanceAuditInfo",
            method: "post",
            data

        })
    },

    /**
     * 导出当前考勤信息
     */
    infoApproveCurrent(data){
        return request({
            url: "hrResources/outputCurrent",
            method: "post",
            data

        })
    },
    /**
     * 审批通过
     */
    approvePass(data){

        return request({
            url: "hrResources/submitAudit",
            method: "post",
            data

        })
    },
    /**
     * 审批退回
     */
    approveCancel(data){

        return request({
            url: "hrResources/withdraw",
            method: "post",
            data

        })
    },
    /**
     * 提交造册
     */
    submitEndAttendance(data){
        return request({
            url: "hrResources/submitTabulation",
            method: "post",
            data

        })
    },

  hrResourcesAllocation(data) {
    return request({
      url: 'hrResourcesAllocation/editAttenStatus',
      method: 'post',
      data
    })
  }
}
