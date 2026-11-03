package com.work.ppm.application.model.commonmanager;

import lombok.Data;
import oracle.sql.DATE;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Data
public class CommRdDepartment {
    /**
     * 部门代码
     */
    private String depid;
    /**
     * 部门中文名称
     */
    private String location;
    /**
     * 是否失效
     */
    private String isdisabled;
    /**
     *上级部门
     */
    private String adminid;
    /**
     * 备注
     */
    private String remark;
    /**
     * 最后更新时间
     */
    private DATE lastupdatedate;

    public static CommRdDepartment recursiveListToTree(List<CommRdDepartment> list,String node){
        for (CommRdDepartment commRdDepartment : list) {
            if (extisDadNode(commRdDepartment,node)){
                return findDadNode(list,commRdDepartment);
            }
        }
        return null;
    }
    private static CommRdDepartment findDadNode(List<CommRdDepartment> list,CommRdDepartment commRdDepartment){
        if (!ObjectUtils.isEmpty(commRdDepartment.getAdminid())){
            for (CommRdDepartment rdDepartment : list) {
                if(commRdDepartment.getAdminid().equals(rdDepartment.getDepid())){
                    return findDadNode(list,rdDepartment);
                }
            }
        }
        return commRdDepartment;
    }

    private static boolean extisDadNode(CommRdDepartment commRdDepartment,String node){
        return node.equals(commRdDepartment.getDepid());
    }
}
