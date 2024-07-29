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
 * 
 * @TableName honey_feedback
 */
@TableName(value ="honey_feedback")
@Data
public class HoneyFeedback  {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 渠道
     */
    @TableField(value = "channel")
    private String channel;

    /**
     * 产品id
     */
    @TableField(value = "pro_id")
    private Long proId;

    /**
     * verifyid
     */
    @TableField(value = "record_id")
    private Long recordId;

    /**
     * token
     */
    @TableField(value = "token")
    private String token;



    /**
     * 删除标记1：删除，0:可用
     */
    @TableField(value = "delete_flag")
    private Integer deleteFlag;

    /**
     * 图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 公司
     */
    @TableField(value = "company")
    private String company;

    /**
     * 手机
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 描述
     */
    @TableField(value = "des")
    private String des;

    /**
     * 名字
     */
    @TableField(value = "name")
    private String name;

    @TableField(value = "mail")
    private String mail;

    @TableField(value = "status")
    private Integer status;

    /** 创建者 */
    @ApiModelProperty(value = "创建者")
    @TableField(value = "create_by")
    @Excel(name = "创建者")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    @Excel(name = "备注")
    private String remark;
}