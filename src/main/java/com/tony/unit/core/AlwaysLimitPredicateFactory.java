package com.tony.unit.core;

import com.tony.unit.config.AbstractLimitPredicateFactory;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/21
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class AlwaysLimitPredicateFactory extends AbstractLimitPredicateFactory<Void> {


    public AlwaysLimitPredicateFactory() {
        super(Void.class);
    }

    @Override
    public LimitPredicate apply(Void config) {
        return new LimitPredicate.AlwaysNeedLimitPredicate();
    }

}
