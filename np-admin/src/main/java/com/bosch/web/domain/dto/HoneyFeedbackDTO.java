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
 * @TableName honey_feedback
 */

@Data
public class HoneyFeedbackDTO extends PageDomain {
    /**
     * 
     */

    private Long id;

    /**
     * 渠道
     */
    @ApiModelProperty(value = "channel")
    private String channel;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "pro_id")
    private Long proId;

    /**
     * verifyid
     */
    @ApiModelProperty(value = "record_id")
    private Long recordId;

    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String token;



    /**
     * 删除标记1：删除，0:可用
     */
    @ApiModelProperty(value = "delete_flag")
    private Integer deleteFlag;

    /**
     * 图片
     */
    @ApiModelProperty(value = "pic")
    private String pic;

    /**
     * 公司
     */
    @ApiModelProperty(value = "company")
    private String company;

    /**
     * 手机
     */
    @ApiModelProperty(value = "phone")
    private String phone;

    /**
     * 描述
     */
    @ApiModelProperty(value = "des")
    private String des;

    /**
     * 名字
     */
    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "mail")
    private String mail;

    @ApiModelProperty(value = "status")
    private Integer status;

    @ApiModelProperty(value = "remark")
    private String remark;
}