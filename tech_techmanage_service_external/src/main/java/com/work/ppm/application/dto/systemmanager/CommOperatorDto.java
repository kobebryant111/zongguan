package com.work.ppm.application.dto.systemmanager;

import com.work.ppm.application.model.systemmanager.CommOperator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommOperatorDto extends CommOperator {
    private String newPassword;
    private String confirmPassword;
}
