package com.bosch.web.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.core.domain.BaseEntity;
import com.bosch.common.core.page.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName honey_mail_detail
 */

@Data
public class HoneyMailDetailDTO extends PageDomain {
    /**
     * 
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 用户
     */
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "mail")
    private String mail;

    /**
     * 标题
     */
    @ApiModelProperty(value = "title")
    private String title;

    /**
     * 状态
     */
    @ApiModelProperty(value = "status")
    private String status;

    /**
     * 内容
     */
    @ApiModelProperty(value = "content")
    private String content;




    /**
     * 删除标记1：删除，0:可用
     */
    @ApiModelProperty(value = "delete_flag")
    private Integer deleteFlag;

}