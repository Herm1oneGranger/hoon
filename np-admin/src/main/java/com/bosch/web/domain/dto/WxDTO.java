package com.bosch.web.domain.dto;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 *  printDTO
 */
@Data
public class WxDTO extends BaseEntity {

    public static final String WX_LINK = "https://api.weixin.qq.com/sns/jscode2session?";

    /**
     * appID
     */
    //public static final String APP_ID = "wx6f992f653bd01163";
    public static final String APP_ID = "wxb54c27a31ca992f3";
    /**
     * secret
     */
    //public static final String SECRET = "37ba80e30974dd3c78aced760915063f";
    public static final String SECRET = "8191aaf3cdfc03c79d240227d5e4fa83";
    /**
     * code
     */
    @ApiModelProperty(value = "code")
    private String code;

}