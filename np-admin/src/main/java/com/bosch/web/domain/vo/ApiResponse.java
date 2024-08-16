package com.bosch.web.domain.vo;

import lombok.Data;

@Data
public class ApiResponse {
    private int code;

    private String message;

    private BodyVO body;
}
