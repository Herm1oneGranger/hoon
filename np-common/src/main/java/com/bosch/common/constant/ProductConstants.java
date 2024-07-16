package com.bosch.common.constant;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/23/2023 6:06 PM
 */
public class ProductConstants {

    /** 产品启用状态 */
    public static final String PRODUCT_BRAND = "博世力士乐";

    /** 产品启用状态 */
    public static final String PRODUCT_ENABLED_STATE = "0";

    /** 产品禁用状态 */
    public static final String PRODUCT_DISABLED_STATE = "1";

    /** 产品不存在状态 */
    public static final String PRODUCT_ABSENCE_STATE = "2";

    /** 产品校验正常状态 */
    public static final String VERIFY_NORMAL = "0";

    /** 产品校验风险状态 */
    public static final String VERIFY_RISK = "1";

    /** 产品未校验状态 */
    public static final String VERIFY_UNCHECK = "2";

    /** 产品校验总数阈值 */
    public static final int VERIFY_THRESHOLD = 100;

    /** 产品校验地址总数阈值 */
    public static final int VERIFY_LOCATION_THRESHOLD = 3;

    /** 产品校验用户总数阈值 */
    public static final int VERIFY_USER_THRESHOLD = 10;

    /** 校验是否唯一的返回标识 */
    public final static boolean UNIQUE = true;
    public final static boolean NOT_UNIQUE = false;
}
