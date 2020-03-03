package com.tony.unit.core;

import com.tony.unit.config.Configurable;
import com.tony.unit.util.NameUtils;

import java.util.function.Consumer;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/21
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public interface LimitPredicateFactory<C> extends Configurable<C> {

    default LimitPredicate apply(Consumer<C> consumer) {
        C config = newConfig();
        consumer.accept(config);
        return apply(config);
    }

    LimitPredicate apply(C config);

    default String name() {
        return NameUtils.normalizeRoutePredicateName(getClass());
    }

}
