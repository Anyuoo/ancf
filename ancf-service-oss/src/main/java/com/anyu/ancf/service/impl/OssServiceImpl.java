package com.anyu.ancf.service.impl;

import com.aliyun.oss.OSSClientBuilder;
import com.anyu.ancf.config.OSSProperties;
import com.anyu.ancf.service.OssService;
import com.anyu.ancf.util.OssUtils;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.result.type.FileResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class OssServiceImpl implements OssService {
    private static final Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);
    @Autowired
    private OSSProperties ossProperties;

    @Override
    public String uploadAvatar(Part file) {
        return uploadFile(OssUtils.FILE_TYPE_AVATAR, file);
    }

    @Override
    public String uploadBackground(Part file) {
        return uploadFile(OssUtils.FILE_TYPE_BACKGROUND, file);
    }

    /**
     * @param fileType 文件类型，更具类型上传到不同文件夹
     * @param file     文件
     * @return oss中文件相对路径
     */
    private String uploadFile(@NotBlank String fileType, Part file) {
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

        try (InputStream inputStream = file.getInputStream()) {
            final var original = file.getSubmittedFileName();
            final var fileUrl = OssUtils.getOssFileUrl(fileType, original);
            ossClient.putObject(bucketName, fileUrl, new ByteArrayInputStream(inputStream.readAllBytes()));
            // 关闭OSSClient。
            ossClient.shutdown();
            final var AvatarUrl = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
            logger.info("[OssService: {}类型] {},size:{},上传成功,aliyun 地址：{}", fileType, original, file.getSize(), AvatarUrl);
            return AvatarUrl;
        } catch (IOException e) {
            logger.warn("[OssService: {}类型] {}上传失败", fileType, file.getName());
            throw GlobalException.causeBy(FileResultType.UPLOAD_ERROR);
        }
    }

}
