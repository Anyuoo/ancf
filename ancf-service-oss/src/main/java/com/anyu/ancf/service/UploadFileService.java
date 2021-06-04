package com.anyu.ancf.service;

import com.anyu.ancf.mapper.UploadFileMapper;
import com.anyu.ancf.model.FileFolder;
import com.anyu.common.model.entity.UploadFile;
import com.anyu.common.model.enums.FileType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*用户上传文件记录服务
* @author Anyu
* @since 2021/5/11
*/
@Service
public class UploadFileService extends ServiceImpl<UploadFileMapper, UploadFile> implements IService<UploadFile> {
    /**
     * 查询up那个户上传图片
     * @param uId 用户id
     */
    public List<UploadFile> listPictures(long uId) {
        return lambdaQuery()
                .eq(UploadFile::getUserId,uId)
                .eq(UploadFile::getType, FileType.PICTURE)
                .eq(UploadFile::getFolder, FileFolder.PICTURE.getPath())
                .orderByDesc(UploadFile::getCreateTime)
                .list();
    }

    /**
     * 根据URL查询信息
     * @param urls ossUrls
     */
    public List<UploadFile> listPicturesByUrls(List<String> urls) {
        return lambdaQuery().in(urls!= null && !urls.isEmpty(),UploadFile::getUrl, urls).list();
    }

    /**
     * 根据URL查询信息
     * @param url ossUrl
     */
    public UploadFile getUploadFileByUrl(String url) {
        return lambdaQuery().eq(StringUtils.isNotBlank(url),UploadFile::getUrl, url).one();

    }

    /**
     * 查询推荐视频
     * @param cUId 当前用户
     */
    public List<UploadFile> listRmdVideos(long cUId) {
        return lambdaQuery()
                .eq(UploadFile::getUserId, cUId)
                .eq(UploadFile::getType, FileType.VIDEO)
                .eq(UploadFile::getFolder, FileFolder.VIDEO.getPath())
                .orderByDesc(UploadFile::getCreateTime)
                .last("limit 10")
                .list();
    }

    /**
     * 查询推荐视频封面
     * @param cUId 当前用户
     */
    public List<UploadFile> listRmdVideoCovers(long cUId) {
        return lambdaQuery()
                .eq(UploadFile::getUserId, cUId)
                .eq(UploadFile::getType, FileType.PICTURE)
                .eq(UploadFile::getFolder, FileFolder.VIDEO_COVER.getPath())
                .orderByDesc(UploadFile::getCreateTime)
                .last("limit 10")
                .list();
    }

    public List<UploadFile> listHostPictures() {
        return lambdaQuery()
                .eq(UploadFile::getType, FileType.PICTURE)
                .last("limit 10")
                .list();
    }
}
