package com.bosch.web.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @TableName p_verify
 */

@Data
public class HoneyVerifyVO {

    private String result;

    private String msg;

    private Integer reocrdId;
}