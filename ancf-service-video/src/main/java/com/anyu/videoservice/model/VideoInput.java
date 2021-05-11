package com.anyu.videoservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.Part;


@Getter
@Setter
public class VideoInput {
    private String title;
    private String desc;
    private Part video;
    private Part cover;
}
