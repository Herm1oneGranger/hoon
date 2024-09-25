package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.bosch.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 产品信息
 * @TableName honey_pro
 */
@TableName(value ="honey_pro")
@Data
public class HoneyPro  {
    /** 搜索值 */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")

    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新者")
//    @Excel(name = "更新者")
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 产品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 物料名
     */
    @Excel(name = "物料号(Material)")
    @TableField(value = "material")
    private String material;

    /**
     * 物料类别
     */
    @Excel(name = "产品类别(名称)")
    @TableField(value = "material_code")
    private String materialCode;

    /**
     * standard order
     */
    @Excel(name = "订单号 (Sales Order)")
    @TableField(value = "so")
    private String so;

    /**
     * 
     */
    @Excel(name = "行号(Item)")
    @TableField(value = "item_no")
    private Integer itemNo;

    /**
     * 
     */
    @Excel(name = "订单量(Order Quantity)")
    @TableField(value = "order_qua")
    private String orderQua;

    /**
     * 
     */
    @Excel(name = "订单序列号(Order Serial Number)")
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 
     */

    @TableField(value = "un")
    private String un;

    /**
     * 
     */
    @TableField(value = "des")
    private String des;

    /**
     * 
     */

    @TableField(value = "sold_to")
    private String soldTo;

    @Excel(name = "型号")
    @TableField(value = "model")
    private String model;

    @Excel(name = "规格")
    @TableField(value = "specification")
    private String specification;

    @Excel(name = "功率")
    @TableField(value = "power")
    private String power;

    /**
     * 
     */

    @TableField(value = "ship_to")
    private String shipTo;

    /**
     * 
     */

    @TableField(value = "po_no")
    private String poNo;

    @Excel(name = "token")
    @TableField(value = "token")
    private String token;

    /**
     * 使用状态（0启用 1停用）
     */
    @TableField(value = "status")
    private String status;




    /**
     * 删除标记1：删除，0:可用
     */
    @TableField(value = "delete_flag")
    private Integer deleteFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}