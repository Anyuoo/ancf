package com.anyu.postservice.service.impl;


import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.model.entity.Post;
import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.PostType;
import com.anyu.common.util.SensitiveFilter;
import com.anyu.postservice.entity.condition.PostPageCondition;
import com.anyu.postservice.entity.input.PostInput;
import com.anyu.postservice.entity.vo.PostVO;
import com.anyu.postservice.mapper.PostMapper;
import com.anyu.postservice.service.LikeService;
import com.anyu.postservice.service.PostService;
import com.anyu.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * (Post)表服务实现类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Resource
    private SensitiveFilter sensitiveFilter;
    @Resource
    private UserService userService;
    @Resource
    private LikeService likeService;

    @Override
    public Optional<Post> getPostById(Integer id) {
        if (id == null) return Optional.empty();
        var post = lambdaQuery().eq(Post::getId, id).one();
        if (post == null)
            return Optional.empty();
        var title = HtmlUtils.htmlUnescape(post.getTitle());
        post.setTitle(title);
        var content = HtmlUtils.htmlUnescape(post.getContent());
        post.setContent(content);
        return Optional.of(post);
    }

    /**
     * 发布帖子
     *
     * @param input 输入参数
     * @return 结果
     */
    @Override
    @Transactional
    public boolean publishPost(@NonNull PostInput input,int publisherId) {
        //输入处理
        final var title = sensitiveFilter.filter(input.getTitle());
        input.setTitle(HtmlUtils.htmlEscape(title));
        final var content = sensitiveFilter.filter(input.getContent());
        input.setContent(HtmlUtils.htmlEscape(content));
        final var post = Post.build();
        BeanUtils.copyProperties(input, post);
        post.setUserId(publisherId)
                .setCmtNum(0)
                .setScore(0f)
                .setType(PostType.NORMAL);

        return save(post);
    }

    /**
    *帖子转化vo对象
    * @author Anyu
    * @since 2021/2/5 下午2:22
    */
    @Override
    public PostVO convertPostToVO(@NotNull Post post) {
        final var publisher = userService.getUserById(post.getUserId()).orElse(new User());
        return  PostVO.getInstance()
                .setUserId(publisher.getId())
                .setNickname(publisher.getNickname())
                .setId(post.getId())
                .setTitle(post.getTitle())
                .setContent(post.getContent())
                .setType(post.getType())
                .setCmtNum(post.getCmtNum())
                .setLikeNum((int) likeService.countPostLikeNum(post.getId()))
                .setCreateTime(post.getCreateTime())
                .setModifiedTime(post.getModifiedTime());
    }

    @Override
    public List<Post> listPostAfter(int first, Integer postId, PostPageCondition condition) {
        final var chainWrapper = lambdaQuery();
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

    @Override
    public List<PostVO> listPostVOAfter(int first, Integer postId, PostPageCondition condition) {
        return listPostAfter(first, postId, condition).stream()
                .map(this::convertPostToVO)
                .collect(Collectors.toUnmodifiableList());
    }
}