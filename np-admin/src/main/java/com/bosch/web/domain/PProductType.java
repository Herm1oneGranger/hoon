package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName p_product_type
 */
@TableName(value ="p_product_type")
@Data
public class PProductType implements Serializable {
    /**
     * 类别ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品名（产品类别名）
     */
    private String name;

    /**
     * 产品图片地址
     */
    private String imgPath;

}