package com.bosch.web.domain.dto;

import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * 校验记录集合返回对象 printDTO
 */
@Data
public class VerifyNoMnrDTO extends BaseEntity {
    /**
     * 校验记录ID
     */
    @ApiModelProperty(value = "校验记录ID")
    private Long id;

    /**
     * 微信昵称
     */
    @ApiModelProperty(value = "微信昵称")
    private String userName;

    /**
     * 用户电话
     */
    @ApiModelProperty(value = "用户电话")
    private String telephone;

    /**
     * 微信用户唯一标识
     */
    @ApiModelProperty(value = "微信用户唯一标识")
    @NotBlank(message = "微信用户唯一标识不能为空")
    private String appId;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别")
    private String gender;

    /**
     * 经纬度
     */
    @ApiModelProperty(value = "经纬度")
    @NotBlank(message = "经纬度不能为空")
    private String position;

    /**
     * 手机端系统
     */
    @ApiModelProperty(value = "手机端系统")
    private String userPhoneOS;

    /**
     * 校验结果（0正常 1风险）
     */
    @ApiModelProperty(value = "校验结果（0正常 1风险）")
    private String checkedResult;

    /**
     * 用户的省
     */
    @ApiModelProperty(value = "用户的省")
    @NotBlank(message = "用户的省不能为空")
    private String province;

    /**
     * 用户的城市
     */
    @ApiModelProperty(value = "用户的城市")
    @NotBlank(message = "用户的城市不能为空")
    private String city;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private Long productId;

    /**
     * 产品物料号
     */
    @ApiModelProperty(value = "产品物料号")
    private String mnr;

    /**
     * 产品订单号
     */
    @ApiModelProperty(value = "产品订单号")
    @NotBlank(message = "产品订单号不能为空")
    private String fd;

    /**
     * 产品序列号
     */
    @ApiModelProperty(value = "产品序列号")
    @NotBlank(message = "产品序列号不能为空")
    private String sn;

}