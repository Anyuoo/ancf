package com.anyu.uploadservice.resolver;

import com.anyu.common.model.entity.UploadFile;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.uploadservice.service.UploadService;
import graphql.kickstart.tools.GraphQLMutationResolver;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
*上传resolver
* @author Anyu
* @since 2021/5/12
*/
@MutationResolver
public class UploadMutationResolver implements GraphQLMutationResolver {
    @Resource
    private UploadService uploadService;


    public List<UploadFile> uploadPictures(List<Part> pictures) {
        if (pictures == null || pictures.isEmpty()) {
            return null;
        }
        return uploadService.uploadPictures(pictures);
    }

    public UploadFile uploadVideoCover(@NotNull Part cover) {
        return uploadService.uploadVideoCover(cover);
    }

    public UploadFile uploadVideo(Part video) {
        return uploadService.uploadVideo(video);
    }

}
