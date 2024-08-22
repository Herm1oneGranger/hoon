package com.bosch.web.domain.dto;

import com.bosch.common.core.page.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 校验信息表
 * @TableName honey_verify
 */

@Data
public class HoneyVerifyDTO extends PageDomain {
    @ApiModelProperty(value = "year")
    private String year;

    @ApiModelProperty(value = "dateType")
    private String dateType;

    @ApiModelProperty(value = "material")
    private String material;

    @ApiModelProperty(value = "so")
    private String so;

    @ApiModelProperty(value = "orderNo")
    private String orderNo;

    @ApiModelProperty(value = "orderQua")
    private String orderQua;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "materialCode")
    private String materialCode;


    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "pro_id")
    private Long proId;

    /**
     * 纹理结果
     */
    @ApiModelProperty(value = "texture_results")
    private String textureResults;

    /**
     * 算法结果
     */
    @ApiModelProperty(value = "algo_results")
    private String algoResults;

    /**
     * 校验结果
     */
    @ApiModelProperty(value = "total_results")
    private String totalResults;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "create_by")
    private String createBy;

    /**
     *
     */

    @ApiModelProperty(value = "create_time")
    private Date createTime;


    @ApiModelProperty(value = "create_time")
    private String createTimeFormat;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "remark")
    private String remark;

    /**
     * 删除标记1：删除，0:可用
     */
    @ApiModelProperty(value = "delete_flag")
    private Integer deleteFlag;

    @ApiModelProperty(value = "user_name")
    private String userName;
    @ApiModelProperty(value = "telephone")
    private String telephone;
    @ApiModelProperty(value = "app_id")
    private String appId;
    @ApiModelProperty(value = "gender")
    private String gender;
    @ApiModelProperty(value = "position")
    private String position;
    @ApiModelProperty(value = "user_phone_os")
    private String userPhoneOs;
    @ApiModelProperty(value = "province")
    private String province;
    @ApiModelProperty(value = "city")
    private String city;


    @ApiModelProperty(value = "pic")
    private String pic;

    @ApiModelProperty(value = "model")
    private String model;
}