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
 * 校验信息表
 * @TableName honey_verify
 */
@TableName(value ="honey_verify")
@Data
public class HoneyVerify extends BaseEntity {
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
}