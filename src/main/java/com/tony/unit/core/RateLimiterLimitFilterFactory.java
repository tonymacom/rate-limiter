package com.tony.unit.core;

import com.yamibuy.ec.core.common.YamibuyException;
import com.tony.unit.config.AbstractConfigurable;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/26
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class RateLimiterLimitFilterFactory
        extends AbstractConfigurable<RateLimiterLimitFilterFactory.Config>
        implements LimitFilterFactory<RateLimiterLimitFilterFactory.Config>{

    private final RateLimiter rateLimiter;

    public RateLimiterLimitFilterFactory(RateLimiter rateLimiter) {
        super(Config.class);
        this.rateLimiter = rateLimiter;
    }

    @Override
    public LimitFilter apply(Config config) {

        String routeKey = config.getRouteKey();

        return (request,response, chain) -> {
            RateLimiter.Response allowedResponse = rateLimiter.isAllowed(routeKey);
            if (allowedResponse.isAllowed()) {
                chain.filter(request,response);
            }else{
                throw new YamibuyException(config.getStatusCode().getReasonPhrase());
            }
        };
    }

    @Data
    public static class Config implements HashRouteKey{

        private String routeKey;

        private HttpStatus statusCode = HttpStatus.TOO_MANY_REQUESTS;

        private RateLimiter rateLimiter;
    }

}
