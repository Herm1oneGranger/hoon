package com.bosch.web.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 校验记录信息 printVO
 */
@Data
@Accessors(chain = true)
public class VerifyResultVO{

    /**
     * 校验结果（0正常 1风险 2未在库内）
     */
    @ApiModelProperty(value = "校验结果（0正常 1风险 2未在库内）")
    private String checkedResult;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private Long productID;

}