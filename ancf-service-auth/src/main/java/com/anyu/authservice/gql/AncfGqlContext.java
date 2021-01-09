package com.anyu.authservice.gql;


import com.anyu.authservice.entity.AuthSubject;
import graphql.kickstart.execution.context.GraphQLContext;
import org.dataloader.DataLoaderRegistry;

import javax.security.auth.Subject;
import java.util.Optional;

/**
 * 重写接口，将原有的Subject 替换成自定义的 AncfSubject
 *
 * @author Anyu
 * @since 2020/11/2
 */
public interface AncfGqlContext extends GraphQLContext {
    /**
     * @Deprecated 始终返回为空 请使用{@link #getAuthSubject()}
     */
    @Override
    @Deprecated
    default Optional<Subject> getSubject() {
        return Optional.empty();
    }

    @Override
    Optional<DataLoaderRegistry> getDataLoaderRegistry();

    /**
     * @return 认证主体
     */
    Optional<AuthSubject> getAuthSubject();
}
