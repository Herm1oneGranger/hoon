package com.bosch.web.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.core.domain.BaseEntity;
import com.bosch.common.core.page.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName honey_type
 */
@TableName(value ="honey_type")
@Data
public class HoneyTypeDTO extends PageDomain {
    /**
     * 产品ID
     */

    private Long id;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "name")

    private String name;

    /**
     * 产品识别码
     */
    @ApiModelProperty(value = "code")
    private String code;

    /**
     * 图片
     */
    @ApiModelProperty(value = "pic")
    private String pic;

    /**
     * 描述
     */
    @ApiModelProperty(value = "des")
    private String des;

    /**
     * 使用状态（0启用 1停用）
     */
    @ApiModelProperty(value = "status")
    private String status;


    /**
     * 删除标记1：删除，0:可用
     */
    @ApiModelProperty(value = "delete_flag")
    private Integer deleteFlag;

}