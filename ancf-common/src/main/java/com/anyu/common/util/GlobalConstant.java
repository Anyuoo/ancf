package com.anyu.common.util;

/**
 * @author Anyu
 * @since 2021/1/28 下午3:04
 */
public class GlobalConstant {
    private GlobalConstant(){}
    //分页
    public static final int PAGE_FIRST = 10;

    public static final String PAGE_SQL_LIMIT = "limit ";
    //
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //缓存时间两小时
    public static final  int CACHE_EXPIRED_2H = 7200;

    public static final String ACCOUNT_SUFFIX = "@ancf";

}
