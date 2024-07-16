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
 * @TableName honey_type
 */
@TableName(value ="honey_type")
@Data
public class HoneyType extends BaseEntity {
    /**
     * 产品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 产品识别码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 描述
     */
    @TableField(value = "des")
    private String des;

    /**
     * 使用状态（0启用 1停用）
     */
    @TableField(value = "status")
    private String status;



    /**
     * 删除标记1：删除，0:可用
     */
    @TableField(value = "delete_flag")
    private Integer deleteFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}