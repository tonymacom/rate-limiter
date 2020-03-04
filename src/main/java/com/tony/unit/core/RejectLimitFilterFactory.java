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
public class RejectLimitFilterFactory extends AbstractConfigurable<RejectLimitFilterFactory.Config>
        implements LimitFilterFactory<RejectLimitFilterFactory.Config>{

    public RejectLimitFilterFactory() {
        super(RejectLimitFilterFactory.Config.class);
    }

    @Override
    public LimitFilter apply(Config config) {
        return (request,response,chain)-> ResponseUtils.sendMessage(response,config.getStatusCode());
    }

    @Data
    public static class Config{
        private final HttpResponseStatus statusCode = HttpResponseStatus.NOT_ACCEPTABLE;
    }
}
