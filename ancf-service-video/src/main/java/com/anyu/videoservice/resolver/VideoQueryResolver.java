package com.anyu.videoservice.resolver;

import com.anyu.common.result.CommonPage;
import com.anyu.videoservice.model.VideoPageCondition;
import com.anyu.videoservice.model.VideoVo;
import com.anyu.videoservice.service.VideoService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoQueryResolver implements GraphQLQueryResolver {
    @Resource
    private VideoService videoService;

    public Connection<VideoVo> listVideos(Integer size, String after){
        var videoVos = videoService.listVideoVosAfter(size, CommonPage.decodeCursorWith(after));
        return CommonPage.<VideoVo>build()
                .newConnection(size, after, () -> videoVos.stream()
                        .map(videoVo -> CommonPage.getDefaultEdge(videoVo, videoVo.getId()))
                        .collect(Collectors.toUnmodifiableList())
                );
    }
}
