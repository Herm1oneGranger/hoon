package com.bosch.web.domain.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 打印集合返回对象 printDTO
 */
@Data
public class ProductAddDTO {

    /**
     * 产品名
     */
    @ApiModelProperty(value = "产品名")
    private String name;

    /**
     * 物料号
     */
    @ApiModelProperty(value = "物料号")
    private String mnr;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String fd;

    /**
     * 产品序列号
     */
    @ApiModelProperty(value = "产品序列号")
    private String sn;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

}