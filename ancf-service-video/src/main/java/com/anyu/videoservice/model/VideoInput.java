package com.anyu.videoservice.model;

import com.anyu.common.model.entity.Video;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.Part;


@Getter
@Setter
public class VideoInput {
    private String title;
    private String desc;
    private String videoUrl;
    private String coverUrl;

    public  Video toEntity() {
        return new Video()
                .setTitle(title)
                .setDesc(desc)
                .setCoverUrl(coverUrl)
                .setVideoUrl(videoUrl);
    }
}
