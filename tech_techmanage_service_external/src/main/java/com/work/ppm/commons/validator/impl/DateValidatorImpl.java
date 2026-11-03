package com.work.ppm.commons.validator.impl;

import com.work.ppm.commons.validator.DateValidator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 时间校验器实现类
 */
public class DateValidatorImpl implements DateValidator {

    private final String dateFormat;

    public DateValidatorImpl(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean isValid(String dateStr) {
        final DateFormat sdf = new SimpleDateFormat(this.dateFormat);
        sdf.setLenient(false);
        try{
            sdf.parse(dateStr);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
