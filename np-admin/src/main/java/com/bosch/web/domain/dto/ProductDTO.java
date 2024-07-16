package com.bosch.web.domain.dto;
import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Valid;

/**
 * 
 * 打印集合返回对象 printDTO
 */
@Data
@Accessors(chain = true)
public class ProductDTO extends BaseEntity {

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private Long id;

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

    /**
     * 打印ID
     */
    @ApiModelProperty(value = "打印ID")
    private Long printId;

    @ApiModelProperty(value = "depts")
    @Value("${depts}")
    private String depts;
}