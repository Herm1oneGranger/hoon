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
     * 产品类别
     */
    @Excel(name = "产品类别(名称)")
    @TableField(value = "material_code")
    private String materialCode;

    /**
     * Sales Order
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
    @Excel(name = "描述")
    @TableField(value = "des")
    private String des;


    @Excel(name = "型号")
    @TableField(value = "model")
    private String model;

    @Excel(name = "规格")
    @TableField(value = "specification")
    private String specification;

    @Excel(name = "功率")
    @TableField(value = "power")
    private String power;

//    @Excel(name = "token")
//    @TableField(value = "token")
//    private String token;


}