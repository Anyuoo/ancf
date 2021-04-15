package com.anyu.authservice.gql;

import com.anyu.authservice.model.AuthSubject;
import graphql.kickstart.servlet.context.GraphQLWebSocketContext;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

/**
 * 实现SocketContext
 *
 * @author Anyu
 * @since 2020/11/2
 */
public class AncfGqlSocketContext extends AncfGqlBaseContext implements GraphQLWebSocketContext {
    private static final Logger logger = LoggerFactory.getLogger(AncfGqlContextBuilder.class);
    private final Session session;
    private final HandshakeRequest handshakeRequest;

    public AncfGqlSocketContext(Session session, HandshakeRequest handshakeRequest,
                                AuthSubject authSubject, DataLoaderRegistry dataLoaderRegistry) {

        super(authSubject, dataLoaderRegistry);
        this.session = session;
        this.handshakeRequest = handshakeRequest;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public HandshakeRequest getHandshakeRequest() {
        return handshakeRequest;
    }

    public static class Builder {
        private AuthSubject authSubject;
        private DataLoaderRegistry dataLoaderRegistry;
        private Session session;
        private HandshakeRequest handshakeRequest;

        public AncfGqlSocketContext build() {
            return new AncfGqlSocketContext(session, handshakeRequest, authSubject, dataLoaderRegistry);
        }

        public Builder setAncfSubject(AuthSubject authSubject) {
            this.authSubject = authSubject;
            return this;
        }

        public Builder setDataLoaderRegistry(DataLoaderRegistry dataLoaderRegistry) {
            this.dataLoaderRegistry = dataLoaderRegistry;
            return this;
        }

        public Builder setSession(Session session) {
            this.session = session;
            return this;
        }

        public Builder setHandshakeRequest(HandshakeRequest handshakeRequest) {
            this.handshakeRequest = handshakeRequest;
            return this;
        }
    }
}
