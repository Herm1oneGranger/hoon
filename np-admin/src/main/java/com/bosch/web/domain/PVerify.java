package com.bosch.web.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName p_verify
 */
@TableName(value ="p_verify")
@Data
public class PVerify implements Serializable {
    private Long id;

    private String userName;

    private String telephone;

    private String appId;

    private String gender;

    private String position;

    private String userPhoneOs;

    private Date checkedTime;

    private String checkedResult;

    private String province;

    private String city;

    private Long productId;

    private String remark;

    private String token;

    private String url;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PVerify other = (PVerify) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getPosition() == null ? other.getPosition() == null : this.getPosition().equals(other.getPosition()))
            && (this.getUserPhoneOs() == null ? other.getUserPhoneOs() == null : this.getUserPhoneOs().equals(other.getUserPhoneOs()))
            && (this.getCheckedTime() == null ? other.getCheckedTime() == null : this.getCheckedTime().equals(other.getCheckedTime()))
            && (this.getCheckedResult() == null ? other.getCheckedResult() == null : this.getCheckedResult().equals(other.getCheckedResult()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getPosition() == null) ? 0 : getPosition().hashCode());
        result = prime * result + ((getUserPhoneOs() == null) ? 0 : getUserPhoneOs().hashCode());
        result = prime * result + ((getCheckedTime() == null) ? 0 : getCheckedTime().hashCode());
        result = prime * result + ((getCheckedResult() == null) ? 0 : getCheckedResult().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", telephone=").append(telephone);
        sb.append(", appId=").append(appId);
        sb.append(", gender=").append(gender);
        sb.append(", position=").append(position);
        sb.append(", userPhoneOs=").append(userPhoneOs);
        sb.append(", checkedTime=").append(checkedTime);
        sb.append(", checkedResult=").append(checkedResult);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", productId=").append(productId);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}