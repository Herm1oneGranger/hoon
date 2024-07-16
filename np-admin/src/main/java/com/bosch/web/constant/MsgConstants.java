package com.bosch.web.constant;

import io.jsonwebtoken.Claims;

/**
 * msg常量信息
 * 
 * @author ruoyi
 */
public class MsgConstants
{
    public static final String TEXTUREFAIL = "205";

    public static final String TEXTURESUCCESS = "200";
    public static final String OVERFIVE = "同一标签扫码次数过多。标签累计扫码次数超过5次。";

    public static final String OVERTHREE = "同一标签一个月内在至少3个不同地方被扫。";

    public static final String OVERTWO = "同一标签一天内不同地点不同用户扫码超过2次";

    public static final String SAMEIP = "第一次扫描为真后，7天内同IP同用户通过";
}