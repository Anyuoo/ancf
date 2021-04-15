package com.anyu.postservice.service.impl;


import com.anyu.common.model.entity.Post;
import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.PostType;
import com.anyu.common.util.SensitiveFilter;
import com.anyu.postservice.model.condition.PostPageCondition;
import com.anyu.postservice.model.vo.PostVO;
import com.anyu.postservice.mapper.PostMapper;
import com.anyu.postservice.service.LikeService;
import com.anyu.postservice.service.PostService;
import com.anyu.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
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
        var post = lambdaQuery().eq(id != null,Post::getId, id).one();
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
     * @param post 输入参数
     * @return 结果
     */
    @Override
    @Transactional
    public boolean publishPost(@NonNull Post post, int publisherId) {
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
     *
     * @author Anyu
     * @since 2021/2/5 下午2:22
     */
    @Override
    public PostVO convertPostToVO(@NotNull Post post) {
        final var publisher = userService.getUserById(post.getUserId()).orElse(new User());
        return PostVO.getInstance()
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
        if (first < 1)
            first = PAGE_FIRST;
        final var chainWrapper = lambdaQuery();
        return condition.initWapper(chainWrapper)
                .gt(postId != null, Post::getId, postId)
                .orderByDesc(Post::getCreateTime)
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