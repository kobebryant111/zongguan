package com.work.ppm.application.model.rdmanager;

import lombok.Data;

import java.util.List;

/**
 * packageName com.work.ppm.application.model.rdmanager
 *
 * @version JDK 8
 * @className CommExportFile
 * @date 2025/2/13
 * @description TODO
 */
@Data
public class CommExportFile {
    /**
     *
     **/
    private String[] entryTime;
    private List<String> projectCode;
    private List<String> competentDept;
    private String depids;
    /**
     *
     **/
    private String entryStartDate;
    /**
     *
     **/
    private String entryEndDate;
}
