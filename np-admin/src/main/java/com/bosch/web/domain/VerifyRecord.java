package com.bosch.web.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName p_verify_record
 */
@TableName(value ="p_verify_record")
@Data
public class VerifyRecord implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信昵称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户电话
     */
    @TableField(value = "telephone")
    private String telephone;

    /**
     * 微信用户唯一标识
     */
    @TableField(value = "app_id")
    private String appId;

    /**
     * 用户性别
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 经纬度
     */
    @TableField(value = "position")
    private String position;

    /**
     * 手机端系统
     */
    @TableField(value = "user_phone_os")
    private String userPhoneOS;

    /**
     * 校验时间
     */
    @TableField(value = "checked_time")
    private Date checkedTime;

    /**
     * 校验结果（0正常 1风险）
     */
    @TableField(value = "checked_result")
    private String checkedResult;

    /**
     * 用户的省
     */
    @TableField(value = "province")
    private String province;

    /**
     * 用户的城市
     */
    private String city;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 产品物料号
     */
    private String mnr;

    /**
     * 产品订单号
     */
    private String fd;

    /**
     * 产品序列号
     */
    private String sn;

    /** 备注 */
    private String remark;

}