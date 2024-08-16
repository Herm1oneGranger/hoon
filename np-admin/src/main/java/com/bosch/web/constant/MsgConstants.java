package com.bosch.web.constant;

import io.jsonwebtoken.Claims;

/**
 * msg常量信息
 * 
 * @author ruoyi
 */
public class MsgConstants
{
    public static final String TRUE = "True";
    public static final String FAKE = "Fake";
    public static final String MANUAL = "Manual";

    public static final String IQAFAILED = "204";
    public static final String NOTOKEN = "notoken";

    public static final String NOTOKENDES = "未查询到token";
    public static final String TEXTUREFAIL = "205";

    public static final String IQASUCCESS = "Success";

    public static final String ALOSUCCESS = "aloSuccess";
    public static final String ALOFAIL = "aloFail";
    public static final String ALOTIMEFAIL = "aloTimeFail";
    public static final String ALOSUCCESSTEXT = "算法校验成功";
    public static final String Retry = "Retry";
    public static final String TEXTURESUCCESS = "200";
    public static final String OVERONEYEAR = "超过一年";
    public static final String OVERFIVE = "同一标签扫码次数过多。标签累计扫码次数超过5次。";

    public static final String OVERTHREE = "同一标签一个月内在至少3个不同地方被扫。";

    public static final String OVERTWO = "同一标签一天内不同地点不同用户扫码超过2次";

    public static final String SAMEIP = "第一次扫描为真后，7天内同IP同用户通过";
}
