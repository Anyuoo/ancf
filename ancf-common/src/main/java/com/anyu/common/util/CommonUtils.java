package com.anyu.common.util;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * 工具类
 *
 * @author Anyu
 * @since 2020/10/10
 */
public class CommonUtils {
    private static final String CHART_ID_SPLIT = "-";

    /**
     * 生成对话id
     *
     * @param fromId 发消息id
     * @param toId   接收消息id
     * @return 会话id
     */
    public static String createChartId(Long fromId, Long toId) {
        return fromId + CHART_ID_SPLIT + toId;
    }

    /**
     * BASE64 加密
     *
     * @param originalCode 原字符串
     * @return 加密字符串
     */
    public static String base64EncodeWith(String originalCode) {
        return Base64.getEncoder().encodeToString(originalCode.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * BASE64 解密
     *
     * @param base64Code 加密字符串
     * @return 解密字符串
     */
    public static String base64DecodeWith(String base64Code) {
        return new String(Base64.getDecoder().decode(base64Code));
    }

    /**
     * 生成随机字符串
     *
     * @return 字符串
     */
    public static String randomString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * md5 加密 不可逆
     *
     * @param original 原字符串
     * @return 加密后字符串
     */
    public static String md5(String original) {
        return StringUtils.isEmpty(original) ? null : DigestUtils.md5DigestAsHex(original.getBytes());
    }

    /**
     * 生成随机数字字符串
     *
     * @return 随机数字字符串
     */
    public static String randomNumberString() {
        return String.valueOf(System.currentTimeMillis());
    }
}
