package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

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
    @TableField(value = "material")
    private String material;

    /**
     * 物料类别
     */
    @TableField(value = "material_code")
    private String materialCode;

    /**
     * standard order
     */
    @TableField(value = "so")
    private String so;

    /**
     * 
     */
    @TableField(value = "item_no")
    private Integer itemNo;

    /**
     * 
     */
    @TableField(value = "order_qua")
    private String orderQua;

    /**
     * 
     */
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