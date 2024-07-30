package com.bosch.web.domain.dto;

import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @TableName p_verify
 */

@Data
public class PicDTO extends BaseEntity{


    @ApiModelProperty(value = "二维码图")
    @NotBlank(message = "base64二维码图")
    private String imgBase;

}