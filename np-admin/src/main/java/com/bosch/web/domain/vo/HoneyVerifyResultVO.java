package com.bosch.web.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 校验信息表
 *
 * @TableName honey_verify
 */

@Data
public class HoneyVerifyResultVO extends BaseEntity {
    /**
     *
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "pro_id")
    private Long proId;

    /**
     * 物料名
     */
    @ApiModelProperty(value = "material")
    private String material;

    /**
     * 物料类别
     */
    @ApiModelProperty(value = "material_code")
    private String materialCode;

    /**
     * 纹理结果
     */
    @ApiModelProperty(value = "texture_results")
    private String textureResults;

    /**
     * 算法结果
     */
    @ApiModelProperty(value = "algo_results")
    private String algoResults;

    /**
     * 校验结果
     */
    @ApiModelProperty(value = "total_results")
    private String totalResults;


    /**
     * 删除标记1：删除，0:可用
     */
    @ApiModelProperty(value = "delete_flag")
    private Integer deleteFlag;

    @ApiModelProperty(value = "user_name")
    private String userName;
    @ApiModelProperty(value = "telephone")
    private String telephone;
    @ApiModelProperty(value = "app_id")
    private String appId;
    @ApiModelProperty(value = "gender")
    private String gender;
    @ApiModelProperty(value = "position")
    private String position;
    @ApiModelProperty(value = "user_phone_os")
    private String userPhoneOs;
    @ApiModelProperty(value = "province")
    private String province;
    @ApiModelProperty(value = "city")
    private String city;
    @ApiModelProperty(value = "token")
    private String token;

}