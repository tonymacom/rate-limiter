package com.tony.unit.core;

import com.tony.unit.config.AbstractLimitPredicateFactory;
import lombok.Data;

import java.util.List;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/21
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class HeaderLimitPredicateFactory extends AbstractLimitPredicateFactory<HeaderLimitPredicateFactory.Config> {


    public HeaderLimitPredicateFactory() {
        super(Config.class);
    }

    @Override
    public LimitPredicate apply(Config config) {
        return new LimitPredicate.AlwaysNeedLimitPredicate();
    }

    @Data
    public static class Config{
        private String name;
        private List<String> values;
    }

}
