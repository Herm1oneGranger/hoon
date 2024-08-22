package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bosch.common.annotation.Excel;
import lombok.Data;

/**
 * 产品信息
 * @TableName honey_pro
 */

@Data
public class HoneyProExcel {


    /**
     * 物料名
     */
    @Excel(name = "物料号(Material)")
    @TableField(value = "material")
    private String material;

    /**
     * 物料类别
     */
    @Excel(name = "物料类型")
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

    @Excel(name = "model")
    @TableField(value = "model")
    private String model;

    @Excel(name = "specification")
    @TableField(value = "specification")
    private String specification;

    @Excel(name = "power")
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

//    @Excel(name = "token")
//    @TableField(value = "token")
//    private String token;


}