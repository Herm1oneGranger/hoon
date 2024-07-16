package com.bosch.web.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @ApiModelProperty(value = "material")
    private String material;

    /**
     * 物料类别
     */
    @ApiModelProperty(value = "material_code")
    private String materialCode;

    /**
     * standard order
     */
    @ApiModelProperty(value = "so")
    private String so;

    /**
     * 
     */
    @ApiModelProperty(value = "item_no")
    private Integer itemNo;

    /**
     * 
     */
    @ApiModelProperty(value = "order_qua")
    private String orderQua;

    /**
     * 
     */
    @ApiModelProperty(value = "order_no")
    private String orderNo;

    /**
     * 
     */
    @ApiModelProperty(value = "un")
    private String un;

    /**
     * 
     */
    @ApiModelProperty(value = "des")
    private String des;

    /**
     * 
     */
    @ApiModelProperty(value = "sold_to")
    private String soldTo;

    /**
     * 
     */
    @ApiModelProperty(value = "ship_to")
    private String shipTo;

    /**
     * 
     */
    @ApiModelProperty(value = "po_no")
    private String poNo;

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