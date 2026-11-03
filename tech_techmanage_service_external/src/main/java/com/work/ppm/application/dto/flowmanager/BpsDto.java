package com.work.ppm.application.dto.flowmanager;

import com.work.ppm.commons.utils.JsonUtil;

import java.util.Map;

/**
 * bps dto
 */
public class BpsDto {

    /**
     * 工号
     */
    private String users;

    /**
     * 流转状态
     */

    private String status;

    public static BpsDto getInstance() {
        return new BpsDto();
    }

    /**
     * build
     * @return map
     */
    public Map<String,String> build() {
        Map<String,String> map = JsonUtil.objectTOMap(this, BpsDto.class);
        return map;
    }


    public String getUsers() {
        return users;
    }

    /**
     * 工号
     * @param users 工号
     * @return BpsDto
     */
    public BpsDto setUsers(String users) {
        this.users = users;
        return this;
    }

    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     * @return BpsDto
     */
    public BpsDto setStatus(String status) {
        this.status = status;
        return this;
    }

}
