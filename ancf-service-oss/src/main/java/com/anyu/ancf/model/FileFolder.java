package com.anyu.ancf.model;


/**
 * Oss 文件夹地址枚举
 *
 * @author Anyu
 * @since 2021/5/10
 */
public enum FileFolder {
    FILE_AVATAR("avatar", "头像文件夹"),
    FILE_VIDEO_COVER("video-cover", "视频封面文件夹"),
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
