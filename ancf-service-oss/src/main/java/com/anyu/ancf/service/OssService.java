package com.anyu.ancf.service;

import com.aliyun.oss.OSSClientBuilder;
import com.anyu.ancf.config.OSSProperties;
import com.anyu.ancf.model.FileFolder;
import com.anyu.ancf.model.OssFile;
import com.anyu.ancf.model.annotation.FileUploadLogger;
import com.anyu.ancf.util.OssUtils;
import com.anyu.common.model.enums.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Anyu
 * @since 2021/01/1
 */
@Service
public class OssService {
    private static final Logger logger = LoggerFactory.getLogger(OssService.class);

    @Resource
    private  OSSProperties ossProperties;


    /**
     * 上传头像
     *
     * @param avatar   文件
     * @return OSS地址
     */
    @FileUploadLogger(type = FileType.PICTURE)
    public OssFile uploadAvatar(Part avatar) {
       return uploadFile(OssFile.build(avatar, FileFolder.AVATAR));
    }

    /**
     *
     * @param cover 视频封面
     */
    @FileUploadLogger(type = FileType.PICTURE)
    public OssFile uploadVideoCover(Part cover) {
        return uploadFile(OssFile.build(cover, FileFolder.VIDEO_COVER));
    }

    /**
     * 视频上传
     * @param video 视频
     */
    @FileUploadLogger(type = FileType.VIDEO)
    public OssFile  uploadVideo(Part video) {
        return uploadFile(OssFile.build(video, FileFolder.VIDEO));
    }

    /**
     * 批量上传图片
     *
     * @param pictures 图片
     * @return 图片OSS地址
     */
    @FileUploadLogger(type = FileType.PICTURE, more = true)
    public List<OssFile> uploadPictures(List<Part> pictures) {
        if (pictures == null || pictures.isEmpty())
            return new ArrayList<>(1);
        return pictures.stream()
                .map(pic -> uploadFile(OssFile.build(pic, FileFolder.PICTURE)))
                .collect(Collectors.toUnmodifiableList());
    }


    /**
     * 文件上传
     * @param file 文件信息
     * @return OSS地址
     */
    private OssFile uploadFile(final OssFile file) {
        try{
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
            file.setUrl(ossUrl);
        }catch (Exception e){
            logger.info("[OssService {}上传成功] 类型{},大小:{},目标文件夹{},错误信息：{}"
                    , file.getName(), file.getType(), file.getSize(), file.getFileFolder().getPath(),e.getMessage());
        }
        return file;
    }


}
