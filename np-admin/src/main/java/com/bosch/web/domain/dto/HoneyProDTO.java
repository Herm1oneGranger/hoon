package com.bosch.web.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import com.bosch.common.core.page.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 产品信息
 * @TableName honey_pro
 */

@Data
public class HoneyProDTO extends PageDomain {
    /**
     * 产品ID
     */

    private Long id;

    /**
     * 物料名
     */
    @Excel(name = "物料号(Material)")
    @ApiModelProperty(value = "material")
    private String material;

    /**
     * 物料类别
     */
    @Excel(name = "物料类型")
    @ApiModelProperty(value = "material_code")
    private String materialCode;

    /**
     * standard order
     */
    @Excel(name = "订单号 (Sales Order)")
    @ApiModelProperty(value = "so")
    private String so;

    /**
     * 
     */
    @Excel(name = "行号(Item)")
    @ApiModelProperty(value = "item_no")
    private Integer itemNo;

    /**
     * 
     */
    @Excel(name = "订单量(Order Quantity)")
    @ApiModelProperty(value = "order_qua")
    private String orderQua;

    /**
     * 
     */
    @Excel(name = "订单序列号(Order Serial Number)")
    @ApiModelProperty(value = "order_no")
    private String orderNo;

    /**
     * 
     */
    @Excel(name = "un")
    @ApiModelProperty(value = "un")
    private String un;

    /**
     * 
     */
    @Excel(name = "描述")
    @ApiModelProperty(value = "des")
    private String des;

    /**
     * 
     */
    @Excel(name = "sold_to")
    @ApiModelProperty(value = "sold_to")
    private String soldTo;

    @Excel(name = "model")
    @ApiModelProperty(value = "model")
    private String model;

    @Excel(name = "specification")
    @ApiModelProperty(value = "specification")
    private String specification;

    @Excel(name = "power")
    @ApiModelProperty(value = "power")
    private String power;
    /**
     * 
     */
    @Excel(name = "ship_to")
    @ApiModelProperty(value = "ship_to")
    private String shipTo;

    /**
     * 
     */
    @Excel(name = "po_no")
    @ApiModelProperty(value = "po_no")
    private String poNo;

    @Excel(name = "token")
    @ApiModelProperty(value = "token")
    private String token;

    /**
     * 使用状态（0启用 1停用）
     */
    @ApiModelProperty(value = "status")
    private String status;




    /**
     * 删除标记1：删除，0:可用
     */
    @ApiModelProperty(value = "delete_flag")
    private Integer deleteFlag;

}