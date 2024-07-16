package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.bosch.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName honey_mail_detail
 */
@TableName(value ="honey_mail_detail")
@Data
public class HoneyMailDetail extends BaseEntity {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户
     */
    @TableField(value = "name")
    private String name;

    /**
     * 邮箱
     */
    @TableField(value = "mail")
    private String mail;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 内容
     */
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