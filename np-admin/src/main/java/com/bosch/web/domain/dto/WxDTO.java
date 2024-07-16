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
    public static final String APP_ID = "wxfd204de8beb428a3";

    /**
     * secret
     */
    public static final String SECRET = "5f695a7785255ee1d6724cb6eb99a6ae";

    /**
     * code
     */
    @ApiModelProperty(value = "code")
    private String code;

}