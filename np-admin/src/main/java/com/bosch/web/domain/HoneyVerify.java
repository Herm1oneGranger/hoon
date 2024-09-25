package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 校验信息表
 * @TableName honey_verify
 */
@TableName(value ="honey_verify")
@Data
public class HoneyVerify  {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品id
     */
    @TableField(value = "pro_id")
    private Long proId;

    /**
     * 纹理结果
     */
    @TableField(value = "texture_results")
    private String textureResults;

    /**
     * 算法结果
     */
    @TableField(value = "algo_results")
    private String algoResults;

    /**
     * 校验结果
     */
    @TableField(value = "total_results")
    private String totalResults;



    /**
     * 删除标记1：删除，0:可用
     */
    @TableField(value = "delete_flag")
    private Integer deleteFlag;

    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "telephone")
    private String telephone;
    @TableField(value = "app_id")
    private String appId;
    @TableField(value = "gender")
    private String gender;
    @TableField(value = "position")
    private String position;
    @TableField(value = "user_phone_os")
    private String userPhoneOs;
    @TableField(value = "province")
    private String province;
    @TableField(value = "city")
    private String city;
    @TableField(value = "token")
    private String token;

    @TableField(value = "pic")
    private String pic;
    @TableField(value = "model")
    private String model;


    /** 创建者 */
    @ApiModelProperty(value = "创建者")
    @Excel(name = "创建者")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新者")
    @Excel(name = "更新者")
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    @Excel(name = "备注")
    private String remark;
}