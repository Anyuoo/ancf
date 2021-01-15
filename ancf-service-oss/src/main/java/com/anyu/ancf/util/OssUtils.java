package com.anyu.ancf.util;


import com.anyu.common.exception.GlobalException;
import com.anyu.common.result.type.FileResultType;
import com.anyu.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OssUtils {
    //上传为头像文件
    public static final String FILE_TYPE_AVATAR = "avatar";
    //背景图片
    public static final String FILE_TYPE_BACKGROUND = "background";
    private static final String FILE_SPLIT = "/";

    public static String getOssFileUrl(String folderName, String original) {
        //构建日期路径：avatar/2019/02/26/文件名
        String filePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        //文件名：uuid.扩展名
        String fileName = CommonUtils.randomString().substring(0, 8);
        if (StringUtils.isBlank(original)) {
            throw GlobalException.causeBy(FileResultType.UPLOAD_NAME_ERROR);
        }
        String fileType = original.substring(original.lastIndexOf("."));
        return folderName + FILE_SPLIT + filePath + FILE_SPLIT + fileName + fileType;
    }
}
