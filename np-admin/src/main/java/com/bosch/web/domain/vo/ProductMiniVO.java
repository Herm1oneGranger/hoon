package com.bosch.web.domain.vo;

import com.bosch.common.constant.ProductConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 校验记录信息 printVO
 */
@Data
public class ProductMiniVO{

    /**
     * 产品品牌
     */
    @ApiModelProperty(value = "产品品牌")
    private String productBrand = ProductConstants.PRODUCT_BRAND;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productID;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String productName;

    /**
     * 产品物料号
     */
    @ApiModelProperty(value = "产品物料号")
    private String mnr;

    /**
     * 产品订单号
     */
    @ApiModelProperty(value = "产品订单号")
    private String fd;

    /**
     * 产品序列号
     */
    @ApiModelProperty(value = "产品序列号")
    private String sn;

    /**
     * 产品图片
     */
    @ApiModelProperty(value = "产品图片")
    private String imgPath;

}