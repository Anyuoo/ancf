package com.anyu.ancf.util;


import com.anyu.ancf.model.OssFile;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.result.type.FileResultType;
import com.anyu.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OssUtils {
    private static final String FILE_SPLIT = "/";

    public static String getOssFileUrl(OssFile file) {
        //构建日期路径：avatar/2019/02/26/文件名
        String filePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        //文件名：uuid.扩展名
        String fileName = CommonUtils.randomString().substring(0, 8);
        String fileType = file.getType();
        String fileFolder = file.getFileFolder().getPath();
        if (StringUtils.isBlank(fileType) || StringUtils.isBlank(fileFolder)) {
            throw GlobalException.causeBy(FileResultType.UPLOAD_NAME_ERROR);
        }
        return fileFolder + FILE_SPLIT + filePath + FILE_SPLIT + fileName + "."+fileType;
    }


}
