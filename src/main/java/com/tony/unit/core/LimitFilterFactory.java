package com.tony.unit.core;

import com.tony.unit.config.Configurable;
import com.tony.unit.util.NameUtils;

import java.util.function.Consumer;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/26
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public interface LimitFilterFactory<C> extends Configurable<C> {

    LimitFilter apply(C config);

    default LimitFilter apply(Consumer<C> consumer){
        C config = newConfig();
        consumer.accept(config);
        return apply(config);
    }

    default String name(){
        return NameUtils.normalizeRouteFilterName(getClass());
    }

}
