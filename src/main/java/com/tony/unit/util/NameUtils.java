package com.tony.unit.util;

import com.tony.unit.core.LimitPredicateFactory;
import com.tony.unit.core.LimitFilterFactory;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/28
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class NameUtils {
    public static String normalizeRoutePredicateName(Class<? extends LimitPredicateFactory> aClass) {
        return aClass.getSimpleName().replace(LimitPredicateFactory.class.getSimpleName(), "").toLowerCase();
    }

    public static String normalizeRouteFilterName(Class<? extends LimitFilterFactory> aClass) {
        return aClass.getSimpleName().replace(LimitFilterFactory.class.getSimpleName(), "").toLowerCase();
    }
}
