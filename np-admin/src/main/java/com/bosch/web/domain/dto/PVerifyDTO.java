package com.bosch.web.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @TableName p_verify
 */

@Data
public class PVerifyDTO extends BaseEntity{
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

    @ApiModelProperty(value = "二维码图")
    @NotBlank(message = "base64二维码图")
    private String imgBase;

}