package com.anyu.videoservice.service;

import com.anyu.common.model.entity.Video;
import com.anyu.common.util.GlobalConstant;
import com.anyu.videoservice.mapper.VideoMapper;
import com.anyu.videoservice.model.VideoInput;
import com.anyu.videoservice.model.VideoPageCondition;
import com.anyu.videoservice.model.VideoVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
*视频服务
* @author Anyu
* @since 2021/5/11
*/
@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> implements IService<Video> {

    /**
     * 发布视频
     * @param cUId 用户id
     * @param video 视频信息
     */
    public boolean publishVideo(long cUId, Video video) {
        video.setUserId(cUId);
        return save(video);
    }

    /**
     * 分页查询视频
     * @param size 页大小
     * @param videoId 起始视频id
     */
    public List<VideoVo> listVideoVosAfter(int size, Long videoId) {
        if (size < 1)
            size = GlobalConstant.PAGE_FIRST;
        List<Video> videos = lambdaQuery().lt(videoId != null, Video::getId, videoId)
                .orderByDesc(Video::getCreateTime)
                .last(GlobalConstant.PAGE_SQL_LIMIT + size)
                .list();
        if (videos == null || videos.isEmpty())
            return null;
        return videos.stream()
                .map(this::convert2Vo)
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * 转成vo对象
     * @param video 视频信息
     * @return vo对象
     */
    private VideoVo convert2Vo(Video video) {
        return new VideoVo()
                .setId(video.getId())
                .setUserId(video.getUserId())
                .setDesc(video.getDesc())
                .setCoverUrl(video.getCoverUrl())
                .setVideoUrl(video.getVideoUrl())
                .setTitle(video.getTitle())
                .setCreateTime(video.getCreateTime());
    }
}
