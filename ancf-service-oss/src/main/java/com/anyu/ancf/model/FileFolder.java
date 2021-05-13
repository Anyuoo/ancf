package com.anyu.ancf.model;


/**
 * Oss 文件夹地址枚举
 *
 * @author Anyu
 * @since 2021/5/10
 */
public enum FileFolder {
    AVATAR("avatar", "头像文件夹"),
    VIDEO_COVER("video-cover", "视频封面文件夹"),
    PICTURE("picture", "图片文件夹"),
    VIDEO("video", "视频文件夹"),
    ;

    /* 路径 */
    private final String path;
    /* 描述 */
    private final String desc;

    FileFolder(String path, String desc) {
        this.path = path;
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public String getDesc() {
        return desc;
    }
}
