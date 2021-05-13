package com.anyu.uploadservice.resolver;

import com.anyu.common.model.entity.UploadFile;
import com.anyu.common.result.annotation.QueryResolver;
import com.anyu.uploadservice.service.UploadService;
import graphql.kickstart.tools.GraphQLQueryResolver;

import javax.annotation.Resource;
import java.util.List;

@QueryResolver
public class UploadQueryResolver implements GraphQLQueryResolver {
    @Resource
    private UploadService uploadService;

    public List<UploadFile> listCUPictures() {
        return uploadService.listCUPictures();
    }

    public List<UploadFile> listRmdVideos() {
        return uploadService.listRmdVideos();
    }

    public List<UploadFile> listRmdVideoCovers() {
        return uploadService.listRmdVideoCovers();
    }
}
