package com.anyu.uploadservice.service;

import com.anyu.ancf.model.OssFile;
import com.anyu.ancf.service.OssService;
import com.anyu.ancf.service.UploadFileService;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.model.entity.UploadFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.util.List;
import java.util.stream.Collectors;

/**
*上传服务
* @author Anyu
* @since 2021/5/12
*/
@Service
public class UploadService {

    @Resource
    private OssService ossService;
    @Resource
    private UploadFileService uploadFileService;
    @Resource
    private AuthService authService;

    /**
     * 上传图片
     * @param pictures 图片资源
     * @return 上传成功的图片资源信息
     */
    public List<UploadFile> uploadPictures(List<Part> pictures) {
        var urls = ossService.uploadPictures(pictures).stream()
                .map(OssFile::getUrl)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toUnmodifiableList());
        return uploadFileService.listPicturesByUrls(urls);
    }

    /**
     * 获取当前用户上传图片文件
     */
    public List<UploadFile> listCUPictures() {
        return uploadFileService.listPictures(authService.getValidCUId());
    }

    /**
     * 上传视屏封面
     * @param cover 封面信息
     */
    public UploadFile uploadVideoCover(Part cover) {
        var url = ossService.uploadVideoCover(cover).getUrl();
        return uploadFileService.getUploadFileByUrl(url);
    }

    /**
     * 上传视频
     * @param video 视频
     */
    public UploadFile uploadVideo(Part video) {
        var url = ossService.uploadVideo(video).getUrl();
        return uploadFileService.getUploadFileByUrl(url);
    }

    /**
     *  查询推荐视频封面
     */
    public List<UploadFile> listRmdVideos() {
        return uploadFileService.listRmdVideos(authService.getValidCUId());
    }

    public List<UploadFile> listRmdVideoCovers() {
        return uploadFileService.listRmdVideoCovers(authService.getValidCUId());
    }
}
