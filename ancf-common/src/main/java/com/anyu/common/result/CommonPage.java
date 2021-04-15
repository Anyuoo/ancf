package com.anyu.common.result;


import com.anyu.common.model.entity.User;
import com.anyu.common.util.CommonUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import graphql.relay.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Supplier;

public class CommonPage<T> {

    /**
     * 生成游标ID
     *
     * @param originalId 实体ID
     * @return 游标ID
     */
    public static ConnectionCursor createCursorWith(@NotNull Integer originalId) {
        String encode = CommonUtils.base64EncodeWith(String.valueOf(originalId));
        return new DefaultConnectionCursor(encode);
    }

    /**
     * 游标id得到原有id
     *
     * @param base64Id 游标id
     * @return 原id
     */
    public static Integer decodeCursorWith(String base64Id) {
        return StringUtils.isBlank(base64Id) ? null : Integer.parseInt(CommonUtils.base64DecodeWith(base64Id));
    }

    public static <T> DefaultEdge<T> getDefaultEdge(T vo, Integer voId) {
        return new DefaultEdge<>(vo, CommonPage.createCursorWith(voId));
    }


    public static <T> CommonPage<T> build() {
        return new CommonPage<>();
    }

    /**
     * 分页对象
     *
     * @param first
     * @param after
     * @param action action 创建defaultEdge
     * @return 分页对象
     */
    public DefaultConnection<T> newConnection(int first, String after, Supplier<List<Edge<T>>> action) {
        List<Edge<T>> edges = action.get();
        DefaultPageInfo pageInfo = new DefaultPageInfo(
                this.getFirstCursorFrom(edges),
                this.getLastCursorFrom(edges),
                after != null,
                edges.size() >= first);
        return new DefaultConnection<>(edges, pageInfo);
    }


    /**
     * 第一个node 的游标
     *
     * @param edges
     * @return 第一个node 的游标
     */
    public ConnectionCursor getFirstCursorFrom(List<Edge<T>> edges) {
        return edges.isEmpty() ? null : edges.get(0).getCursor();
    }

    /**
     * 最后node 的游标
     *
     * @param edges
     * @return 最后node 的游标
     */
    public ConnectionCursor getLastCursorFrom(List<Edge<T>> edges) {
        return edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor();
    }

}
