package com.bosch.web.domain.vo;

import com.bosch.common.annotation.Excel;
import com.bosch.common.core.page.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName honey_feedback
 */

@Data
public class HoneyFeedbackVO extends PageDomain {
    /**
     * 
     */

    private Long id;

    /**
     * 渠道
     */
    @ApiModelProperty(value = "channel")
    private String channel;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "pro_id")
    private Long proId;

    /**
     * verifyid
     */
    @ApiModelProperty(value = "record_id")
    private Long recordId;

    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String token;



    /**
     * 删除标记1：删除，0:可用
     */
    @ApiModelProperty(value = "delete_flag")
    private Integer deleteFlag;

    /**
     * 图片
     */
    @ApiModelProperty(value = "pic")
    private String pic;

    /**
     * 公司
     */
    @ApiModelProperty(value = "company")
    private String company;

    /**
     * 手机
     */
    @ApiModelProperty(value = "phone")
    private String phone;

    /**
     * 描述
     */
    @ApiModelProperty(value = "des")
    private String des;

    /**
     * 名字
     */
    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "mail")
    private String mail;

    @ApiModelProperty(value = "status")
    private Integer status;

    /**
     * 物料名
     */
    @Excel(name = "物料名")
    @ApiModelProperty(value = "material")
    private String material;

    /**
     * 物料类别
     */
    @Excel(name = "物料类型")
    @ApiModelProperty(value = "material_code")
    private String materialCode;

    @Excel(name = "日志序号")
    @ApiModelProperty(value = "订单号 (Sales Order)")
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
}