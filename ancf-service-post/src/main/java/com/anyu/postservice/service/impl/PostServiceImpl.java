package com.anyu.postservice.service.impl;


import com.anyu.common.model.CommonResult;
import com.anyu.common.model.entity.Post;
import com.anyu.common.model.enums.PostType;
import com.anyu.postservice.entity.input.PostInput;
import com.anyu.postservice.mapper.PostMapper;
import com.anyu.postservice.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.Optional;

/**
 * (Post)表服务实现类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Override
    public Optional<Post> getPostById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        Post post = this.lambdaQuery().eq(Post::getId, id)
                .one();
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
    public CommonResult publishPost(@NonNull PostInput input) {
        input.setTitle(HtmlUtils.htmlEscape(input.getTitle()));
        input.setContent(HtmlUtils.htmlEscape(input.getContent()));
        Post post = Post.build();
        BeanUtils.copyProperties(input, post);
        post.setCmtNum(0)
                .setScore(0f)
                .setType(PostType.NORMAL);

        return this.save(post) ? CommonResult.success("帖子发布成功！") : CommonResult.failure("帖子发布失败！");
    }


}