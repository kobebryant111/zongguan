package com.work.ppm.application.model.systemmanager;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("COMM_WEAK_PASSWORD")
public class WeakPassword {
    /**
     * id
     */
    @TableId
    private String Id;

    /**
     * 弱口令
     */
    @TableField
    private String weakPassword;
}
