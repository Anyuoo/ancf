package com.anyu.authservice.gql;

import com.anyu.authservice.model.AuthSubject;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.result.type.FileResultType;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 重写HttpContext 实现
 *
 * @author Anyu
 * @since 2020/11/2
 */
public class AncfGqlHttpContext extends AncfGqlBaseContext implements GraphQLServletContext {
    private static final Logger logger = LoggerFactory.getLogger(AncfGqlHttpContext.class);
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public AncfGqlHttpContext(HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse,
                              AuthSubject authSubject,
                              DataLoaderRegistry dataLoaderRegistry) {

        super(authSubject, dataLoaderRegistry);
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    public List<Part> getFileParts() {
        try {
            return httpServletRequest.getParts()
                    .stream()
                    .filter(part -> part.getContentType() != null)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException | ServletException e) {
            throw GlobalException.causeBy(FileResultType.UPLOAD_REQUEST_ERROR);
        }
    }

    /**
     * 获取上传文件
     *
     * @param fileName 文件名
     */
    public Part getFilePart(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        try {
            return httpServletRequest.getPart(fileName);
        } catch (IOException | ServletException e) {
            logger.info("获取上传文件{}失败", fileName);
            throw GlobalException.causeBy(FileResultType.UPLOAD_REQUEST_ERROR);
        }
    }

    @SneakyThrows
    @Override
    public Map<String, List<Part>> getParts() {
        return httpServletRequest.getParts()
                .stream()
                .collect(Collectors.groupingBy(Part::getName));
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    @Override
    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    //使用创造者模式创建对象
    public static class Builder {
        private HttpServletRequest httpServletRequest;
        private HttpServletResponse httpServletResponse;
        private AuthSubject authSubject;
        private DataLoaderRegistry dataLoaderRegistry;

        public AncfGqlHttpContext build() {
            return new AncfGqlHttpContext(httpServletRequest, httpServletResponse, authSubject, dataLoaderRegistry);
        }

        public Builder setHttpServletRequest(HttpServletRequest httpServletRequest) {
            this.httpServletRequest = httpServletRequest;
            return this;
        }

        public Builder setHttpServletResponse(HttpServletResponse httpServletResponse) {
            this.httpServletResponse = httpServletResponse;
            return this;
        }

        public Builder setAncfSubject(AuthSubject authSubject) {
            this.authSubject = authSubject;
            return this;
        }

        public Builder setDataLoaderRegistry(DataLoaderRegistry dataLoaderRegistry) {
            this.dataLoaderRegistry = dataLoaderRegistry;
            return this;
        }
    }
}
