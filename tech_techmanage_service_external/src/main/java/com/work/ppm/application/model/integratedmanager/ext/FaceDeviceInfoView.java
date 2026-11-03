package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.FaceDeviceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 人脸考勤机 视图 实体对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FaceDeviceInfoView extends FaceDeviceInfo {

   /**
   *	名称 NAME
   **/
   private String name;
   /**
    *	设备类型 TYPE
    **/
   private String type;



}
