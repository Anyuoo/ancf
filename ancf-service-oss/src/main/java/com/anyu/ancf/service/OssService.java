package com.anyu.ancf.service;

import javax.servlet.http.Part;

public interface OssService {
    String uploadAvatar(Part file);

    String uploadBackground(Part file);
}
