package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 产品信息
 * @TableName honey_pro
 */
@TableName(value ="honey_pro")
@Data
public class HoneyPro extends BaseEntity {
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
    @Excel(name = "un")
    @TableField(value = "un")
    private String un;

    /**
     * 
     */
    @Excel(name = "描述")
    @TableField(value = "des")
    private String des;

    /**
     * 
     */
    @Excel(name = "sold_to")
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
    @Excel(name = "ship_to")
    @TableField(value = "ship_to")
    private String shipTo;

    /**
     * 
     */
    @Excel(name = "po_no")
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