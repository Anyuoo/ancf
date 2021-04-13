package com.anyu.authservice.gql;


import com.anyu.authservice.entity.AuthSubject;
import org.dataloader.DataLoaderRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


/**
 * 实现自定义AncfGqlContext
 *
 * @author Anyu
 * @since 2020/11/2
 */
public class AncfGqlBaseContext implements AncfGqlContext {
    private final AuthSubject authSubject;
    private final DataLoaderRegistry dataLoaderRegistry;

    public AncfGqlBaseContext(AuthSubject authSubject, DataLoaderRegistry dataLoaderRegistry) {
        this.authSubject = authSubject;
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    @NotNull
    @Override
    public DataLoaderRegistry getDataLoaderRegistry() {
        return dataLoaderRegistry;
    }

    @Override
    public Optional<AuthSubject> getAuthSubject() {
        return Optional.ofNullable(authSubject);
    }

}
