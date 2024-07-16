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
 * 图片
 * @TableName p_images
 */
@TableName(value ="p_images")
@Data
public class PImages  {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * base64
     */
    @TableField(value = "base")
    private String base;




}