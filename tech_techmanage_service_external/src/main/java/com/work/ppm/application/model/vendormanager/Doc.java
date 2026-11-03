package com.work.ppm.application.model.vendormanager;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class Doc implements Serializable {
    String code;
    String name;
}
