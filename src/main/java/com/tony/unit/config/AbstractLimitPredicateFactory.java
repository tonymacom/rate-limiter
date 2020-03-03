package com.tony.unit.config;

import com.tony.unit.core.LimitPredicateFactory;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/18
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public abstract class AbstractLimitPredicateFactory<C> extends AbstractConfigurable<C>
        implements LimitPredicateFactory<C> {
    protected AbstractLimitPredicateFactory(Class<C> cClass) {
        super(cClass);
    }
}
