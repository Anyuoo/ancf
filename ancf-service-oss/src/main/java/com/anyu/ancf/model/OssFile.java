package com.anyu.ancf.model;

import com.anyu.ancf.util.OssUtils;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.result.type.FileResultType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * Oss 上传文件结构
 *
 * @author Anyu
 * @since 2021/5/10
 */
@Getter
@Setter
@Accessors(chain = true)
public class OssFile {
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 文件数据
     */
    private byte[] data;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 上传OSS文件夹
     */
    private FileFolder fileFolder;

    /**
     * 构建OssFile对象
     * @param file 文件
     * @param fileFolder 上传的文件夹
     */
    public static OssFile build(Part file,FileFolder fileFolder) {
        try (InputStream inputStream = file.getInputStream()) {
            final var fileName = file.getSubmittedFileName();
            final var type = fileName.split("\\.")[1];
            return new OssFile()
                    .setName(fileName)
                    .setData(inputStream.readAllBytes())
                    .setSize(file.getSize())
                    .setFileFolder(fileFolder)
                    .setType(type)
                    ;
        } catch ( IOException e) {
            throw GlobalException.causeBy(FileResultType.UPLOAD_ERROR);
        }
    }
}
