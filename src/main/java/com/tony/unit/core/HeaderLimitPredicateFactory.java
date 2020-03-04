package com.tony.unit.core;

import com.tony.unit.config.AbstractLimitPredicateFactory;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
        String name = config.getName();
        List<String> values = new ArrayList<>();

        if (!StringUtils.isEmpty(config.getValues())) {
            values.addAll(Arrays.asList(config.getValues().replace(" ","").split(",")));
        }

        return request -> {
            if (StringUtils.isEmpty(name) || CollectionUtils.isEmpty(values)) {
                return false;
            }

            String value = request.getHeader(name);
            if (!StringUtils.isEmpty(value) && !CollectionUtils.isEmpty(values) && values.contains(value)) {
                return true;
            }
            return false;
        };
    }

    @Data
    public static class Config{
        private String name;
        private String values;
    }

}
