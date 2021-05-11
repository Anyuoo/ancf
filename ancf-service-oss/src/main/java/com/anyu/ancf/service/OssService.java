package com.anyu.ancf.service;

import com.aliyun.oss.OSSClientBuilder;
import com.anyu.ancf.config.OSSProperties;
import com.anyu.ancf.model.FileFolder;
import com.anyu.ancf.model.OssFile;
import com.anyu.ancf.model.annotation.FileUploadLogger;
import com.anyu.ancf.util.OssUtils;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.model.entity.UploadFile;
import com.anyu.common.model.enums.FileType;
import com.anyu.common.result.type.FileResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author Anyu
 * @since 2021/01/1
 */
@Service
public class OssService {
    private static final Logger logger = LoggerFactory.getLogger(OssService.class);

    @Resource
    private  OSSProperties ossProperties;
    @Resource
    private UploadFileService uploadFileService;


    /**
     * 上传头像
     *
     * @param avatar   文件
     * @return OSS地址
     */
    @FileUploadLogger(type = FileType.PICTURE)
    public Optional<String> uploadAvatar(Part avatar) {
       return uploadFile(OssFile.build(avatar, FileFolder.FILE_AVATAR));
    }

    @FileUploadLogger(type = FileType.PICTURE)
    public Optional<String> uploadVideoCover(Part cover) {
        return uploadFile(OssFile.build(cover, FileFolder.FILE_VIDEO_COVER));
    }

    /**
     * 文件上传
     * @param file 文件信息
     * @return OSS地址
     */
    private Optional<String> uploadFile(OssFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        final var endpoint = ossProperties.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        final var accessKeyId = ossProperties.getKeyId();
        final var accessKeySecret = ossProperties.getKeySecret();
        final var bucketName = ossProperties.getBucketName();
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        // 创建OSSClient实例。
        final var ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        final var fileUrl = OssUtils.getOssFileUrl(file);
        ossClient.putObject(bucketName, fileUrl, new ByteArrayInputStream(file.getData()));
        // 关闭OSSClient。
        ossClient.shutdown();
        final var ossUrl = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
        logger.info("[OssService {}上传成功] 类型{},大小:{},目标文件夹{},阿里云地址：{}"
                , file.getName(), file.getType(), file.getSize(), file.getFileFolder().getPath(),ossUrl);
        return Optional.of(ossUrl);
    }


}
