package com.bosch.web.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @TableName p_verify
 */

@Data
public class PVerifyVO {
    private Long id;

    private String userName;

    private String telephone;

    private String appId;

    private String gender;

    private String position;

    private String userPhoneOs;

    private Date checkedTime;

    private String checkedResult;

    private String province;

    private String city;

    private Long productId;

    private String remark;

    private String token;

    private String url;

}