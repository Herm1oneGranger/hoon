package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName honey_mail_detail
 */
@TableName(value ="honey_mail_detail")
@Data
public class HoneyMailDetail extends BaseEntity {

    /** 创建者 */


    @Excel(name = "创建者")
    private String createBy;

    /** 创建时间 */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    @TableField(fill = FieldFill.UPDATE)

    @Excel(name = "更新者")
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户
     */
    @Excel(name = "用户")
    @TableField(value = "name")
    private String name;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    @TableField(value = "mail")
    private String mail;

    /**
     * 标题
     */
    @Excel(name = "标题")
    @TableField(value = "title")
    private String title;

    /**
     * 状态
     */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    @TableField(value = "status")
    private String status;

    /**
     * 内容
     */
    @Excel(name = "内容")
    @TableField(value = "content")
    private String content;




    /**
     * 删除标记1：删除，0:可用
     */
    @TableField(value = "delete_flag")
    private Integer deleteFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}