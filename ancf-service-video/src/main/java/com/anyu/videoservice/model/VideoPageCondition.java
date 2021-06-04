package com.anyu.videoservice.model;

import com.anyu.common.model.entity.Video;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoPageCondition {
//    热门排行
    private boolean hot;


    public LambdaQueryChainWrapper<Video>  initCondition(LambdaQueryChainWrapper<Video> wrapper) {
//        wrapper.orderByDesc(hot,Video::ge)
        return wrapper;
    }
}
