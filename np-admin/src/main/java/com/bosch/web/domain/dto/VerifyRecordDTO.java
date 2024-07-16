package com.bosch.web.domain.dto;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 
 * 校验记录集合返回对象 printDTO
 */
@Data
public class VerifyRecordDTO extends BaseEntity {
    /**
     * 校验记录ID
     */
    @ApiModelProperty(value = "打印序号")
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
    private String position;

    /**
     * 手机端系统
     */
    @ApiModelProperty(value = "手机端系统")
    private String userPhoneOS;

    /**
     * 校验时间
     */
    @ApiModelProperty(value = "校验时间")
    private Date checkedTime;

    /**
     * 校验结果（0正常 1风险 2未在库内）
     */
    @ApiModelProperty(value = "校验结果（0正常 1风险 2未在库内）")
    private String checkedResult;

    /**
     * 用户的省
     */
    @ApiModelProperty(value = "用户的省")
    private String province;

    /**
     * 用户的城市
     */
    @ApiModelProperty(value = "用户的城市")
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
    private String fd;

    /**
     * 产品序列号
     */
    @ApiModelProperty(value = "产品序列号")
    private String sn;

}