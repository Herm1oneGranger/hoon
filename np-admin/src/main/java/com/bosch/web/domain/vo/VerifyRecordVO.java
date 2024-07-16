package com.bosch.web.domain.vo;
import com.bosch.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 
 * 校验记录信息 printVO
 */
@Data
public class VerifyRecordVO {
    /**
     * 校验记录ID
     */
    @ApiModelProperty(value = "校验记录序号")
    private Integer id;

    /**
     * 微信昵称
     */
    @ApiModelProperty(value = "微信昵称")
    @Excel(name = "微信昵称")
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
    @Excel(name = "微信用户唯一标识")
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
    @Excel(name = "经纬度")
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
    @Excel(name = "校验时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkedTime;

    /**
     * 校验结果（0正常 1风险）
     */
    @ApiModelProperty(value = "校验结果（0正常 1风险）")
    @Excel(name = "校验结果", readConverterExp = "0=正常,1=风险")
    private String checkedResult;

    /**
     * 用户的省
     */
    @ApiModelProperty(value = "用户的省")
    @Excel(name = "用户的省")
    private String province;

    /**
     * 用户的城市
     */
    @ApiModelProperty(value = "用户的城市")
    @Excel(name = "用户的城市")
    private String city;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * 产品物料号
     */
    @ApiModelProperty(value = "产品物料号")
    @Excel(name = "产品物料号")
    private String mnr;

    /**
     * 产品订单号
     */
    @ApiModelProperty(value = "产品订单号")
    @Excel(name = "产品订单号")
    private String fd;

    /**
     * 产品序列号
     */
    @ApiModelProperty(value = "产品序列号")
    @Excel(name = "产品序列号")
    private String sn;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    @Excel(name = "备注")
    private String remark;

}