package com.tony.unit.core;

import com.tony.unit.config.AbstractConfigurable;
import com.tony.unit.props.HttpResponseStatus;
import com.tony.unit.util.ResponseUtils;
import lombok.Data;

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
                ResponseUtils.sendMessage(response,config.getStatusCode());
            }
        };
    }

    @Data
    public static class Config implements HashRouteKey{

        private String routeKey;

        private HttpResponseStatus statusCode = HttpResponseStatus.TOO_MANY_REQUESTS;

        private RateLimiter rateLimiter;
    }

}
