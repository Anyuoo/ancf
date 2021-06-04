package com.anyu.postservice.service;


import com.anyu.authservice.service.AuthService;
import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.model.entity.Post;
import com.anyu.common.model.enums.PostType;
import com.anyu.common.util.GlobalConstant;
import com.anyu.common.util.SensitiveFilter;
import com.anyu.likeservice.service.LikeService;
import com.anyu.postservice.mapper.PostMapper;
import com.anyu.postservice.model.condition.PostPageCondition;
import com.anyu.postservice.model.vo.PostVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * (Post)表服务接口
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
@Service
public class PostService extends ServiceImpl<PostMapper, Post> implements IService<Post> {
    @Resource
    private SensitiveFilter sensitiveFilter;
    @Resource
    private LikeService likeService;
    @Resource
    private AuthService authService;
    @Resource
    private CacheService cacheService;

    /**
     * 通过id获取帖子信息
     *
     * @param id 忒自id
     */
    public Optional<Post> getPostById(long id) {
        return lambdaQuery()
                .eq(Post::getId, id)
                .oneOpt()
                .map(post -> {
                    var title = HtmlUtils.htmlUnescape(post.getTitle());
                    post.setTitle(title);
                    var content = HtmlUtils.htmlUnescape(post.getContent());
                    post.setContent(content);
                    return Optional.of(post);
                }).orElse(Optional.empty());
    }

    /**
     * 发布帖子
     *
     * @param post 输入参数
     * @return 结果
     */
    public boolean publishPost(@NonNull Post post, long publisherId) {
        //输入处理
        final var title = sensitiveFilter.filter(post.getTitle());
        final var content = sensitiveFilter.filter(post.getContent());

        post.setTitle(HtmlUtils.htmlEscape(title))
                .setContent(HtmlUtils.htmlEscape(content))
                .setUserId(publisherId)
                .setCmtNum(0)
                .setScore(0f)
                .setType(PostType.NORMAL);

        return save(post);
    }

    /**
     * 帖子转化vo对象
     */
    public PostVO convertPostToVO(@NotNull Post post) {
        int postLikeNum = likeService.countPostLikeNum(post.getId());
        boolean postLikeStatus = likeService.getPostLikeStatus(authService.getCUId().orElse(-1L), post.getId());
        int postLookNum = cacheService.getPostLookNum(post.getId());
        return new PostVO()
                .setUserId(post.getUserId())
                .setId(post.getId())
                .setTitle(HtmlUtils.htmlUnescape(post.getTitle()))
                .setContent(HtmlUtils.htmlUnescape(post.getContent()))
                .setType(post.getType())
                .setCmtNum(post.getCmtNum())
                .setCmtStatus(false)
                .setLikeNum(postLikeNum)
                .setLikeStatus(postLikeStatus)
                .setLookNum(postLookNum)
                .setCreateTime(post.getCreateTime())
                .setModifiedTime(post.getModifiedTime());
    }

    /**
     * 查询帖子列表
     *
     * @param size      页大小
     * @param postId    起始帖子id
     * @param condition 条件
     */
    public List<Post> listPostAfter(int size, Long postId, PostPageCondition condition) {
        if (size < 1)
            size = GlobalConstant.PAGE_FIRST;
        final var chainWrapper = lambdaQuery();
        if (condition != null)
            condition.initWrapper(chainWrapper);
        return chainWrapper
                .lt(postId != null, Post::getId, postId)
                .orderByDesc(Post::getCreateTime)
                .last(GlobalConstant.PAGE_SQL_LIMIT + size)
                .list();
    }

    /**
     * 转换列表vo
     */
    public List<PostVO> listPostVOAfter(int size, Long postId, PostPageCondition condition) {
        return listPostAfter(size, postId, condition).stream()
                .map(this::convertPostToVO)
                .collect(Collectors.toUnmodifiableList());
    }
}