package com.bosch.web.domain.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 打印集合返回对象 printDTO
 */
@Data
public class ProductEditDTO{

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private Long id;

    /**
     * 使用状态（0启用 1停用）
     */
    @ApiModelProperty(value = "使用状态（0启用 1停用）")
    private String status;

    /**
     * 校验状态（0正常 1风险 2未校验）
     */
    @ApiModelProperty(value = "校验状态（0正常 1风险 2未校验）")
    private String verifyStatus;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;
}