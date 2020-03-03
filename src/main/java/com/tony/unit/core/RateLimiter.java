package com.tony.unit.core;

import com.tony.unit.config.Configurable;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/16
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public interface RateLimiter<C> extends Configurable<C> {

    Response isAllowed(String routeKey);

    @Getter
    class Response {
        private final boolean allowed;
        private final long tokensRemaining;
        private final Map<String, String> headers;

        public Response(boolean allowed, Map<String, String> headers) {
            this.allowed = allowed;
            this.tokensRemaining = -1;
            Assert.notNull(headers, "headers may not be null");
            this.headers = headers;
        }

        public Response(boolean allowed, long tokensRemaining) {
            this.allowed = allowed;
            this.tokensRemaining = tokensRemaining;
            this.headers = Collections.emptyMap();
        }

        @Deprecated
        public long getTokensRemaining() {
            return tokensRemaining;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Response{");
            sb.append("allowed=").append(allowed);
            sb.append(", headers=").append(headers);
            sb.append(", tokensRemaining=").append(tokensRemaining);
            sb.append('}');
            return sb.toString();
        }
    }


}
