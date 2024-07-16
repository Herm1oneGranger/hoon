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
 * @TableName honey_mail
 */

@Data
public class HoneyMailDTO extends PageDomain {
    /**
     * 
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 用户
     */
    @ApiModelProperty(value = "用户")
    private String name;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String mail;





}