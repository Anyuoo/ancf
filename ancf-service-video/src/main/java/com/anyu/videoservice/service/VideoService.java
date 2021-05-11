package com.anyu.videoservice.service;

import com.anyu.common.model.entity.Video;
import com.anyu.videoservice.mapper.VideoMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
*视频服务
* @author Anyu
* @since 2021/5/11
*/
@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> implements IService<Video> {
}
