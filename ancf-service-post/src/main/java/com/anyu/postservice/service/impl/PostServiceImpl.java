package com.anyu.postservice.service.impl;


import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.model.entity.Post;
import com.anyu.common.model.enums.PostType;
import com.anyu.common.util.SensitiveFilter;
import com.anyu.postservice.entity.condition.PostPageCondition;
import com.anyu.postservice.entity.input.PostInput;
import com.anyu.postservice.mapper.PostMapper;
import com.anyu.postservice.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Optional;

/**
 * (Post)表服务实现类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public Optional<Post> getPostById(Long id) {
        if (id == null) return Optional.empty();
        Post post = this.lambdaQuery().eq(Post::getId, id).one();
        Optional.ofNullable(post)
                .ifPresent(one -> {
                    var title = HtmlUtils.htmlUnescape(one.getTitle());
                    post.setTitle(title);
                    var content = HtmlUtils.htmlUnescape(one.getContent());
                    post.setContent(content);
                });
        return Optional.ofNullable(post);
    }

    /**
     * 发布帖子
     *
     * @param input 输入参数
     * @return 结果
     */
    @Override
    @Transactional
    public boolean publishPost(@NonNull PostInput input) {
        //输入处理
        final var title = sensitiveFilter.filter(input.getTitle());
        input.setTitle(HtmlUtils.htmlEscape(title));
        final var content = sensitiveFilter.filter(input.getContent());
        input.setContent(HtmlUtils.htmlEscape(content));
        final var post = Post.build();
        BeanUtils.copyProperties(input, post);
        post.setCmtNum(0)
                .setScore(0f)
                .setType(PostType.NORMAL);

        return this.save(post);
    }

    @Override
    public List<Post> listPostAfter(int first, Long postId, PostPageCondition condition) {
        final var chainWrapper = this.lambdaQuery();
        if (condition != null) {
            chainWrapper.eq(Post::getType, condition.getType())
                    .eq(Post::getUserId, condition.getUserId())
                    .eq(Post::getTitle, condition.getTitle());
        }
        if (postId != null) {
            chainWrapper.gt(Post::getId, postId);
        }
        if (first < 1) first = PAGE_FIRST;
        return chainWrapper.orderByDesc(Post::getCreateTime)
                .last(PAGE_SQL_LIMIT + first)
                .list();
    }

}