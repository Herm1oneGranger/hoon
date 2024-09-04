package com.bosch.web.domain.vo;

import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 校验信息表
 *
 * @TableName honey_verify
 */

@Data
public class HoneyVerifyResultVO extends BaseEntity {
    /**
     *
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "pro_id")
    private Long proId;

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

    /**
     * 纹理结果
     */
    @Excel(name = "纹理识别结果")
    @ApiModelProperty(value = "texture_results")
    private String textureResults;

    /**
     * 算法结果
     */
    @Excel(name = "算法校验结果")
    @ApiModelProperty(value = "algo_results")
    private String algoResults;

    /**
     * 校验结果
     */
    @Excel(name = "总结果")
    @ApiModelProperty(value = "total_results")
    private String totalResults;


    /**
     * 删除标记1：删除，0:可用
     */
    @ApiModelProperty(value = "delete_flag")
    private Integer deleteFlag;

    @Excel(name = "用户名")
    @ApiModelProperty(value = "user_name")
    private String userName;

    //@Excel(name = "手机号码")
    @ApiModelProperty(value = "telephone")
    private String telephone;

    @Excel(name = "appId")
    @ApiModelProperty(value = "app_id")
    private String appId;

    @Excel(name = "性别")
    @ApiModelProperty(value = "gender")
    private String gender;

    @Excel(name = "经纬度")
    @ApiModelProperty(value = "position")
    private String position;

    @Excel(name = "手机系统")
    @ApiModelProperty(value = "user_phone_os")
    private String userPhoneOs;

    @Excel(name = "省份")
    @ApiModelProperty(value = "province")
    private String province;

    @Excel(name = "城市")
    @ApiModelProperty(value = "city")
    private String city;

    @Excel(name = "token")
    @ApiModelProperty(value = "token")
    private String token;

    @Excel(name = "图片地址")
    @ApiModelProperty(value = "pic")
    private String pic;

    @Excel(name = "型号")
    @ApiModelProperty(value = "model")
    private String model;
}