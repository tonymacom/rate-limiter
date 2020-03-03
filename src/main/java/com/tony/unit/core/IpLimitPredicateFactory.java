package com.tony.unit.core;

import com.tony.unit.config.AbstractLimitPredicateFactory;
import com.yamibuy.ec.core.util.IPUtils;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/21
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class IpLimitPredicateFactory extends AbstractLimitPredicateFactory<IpLimitPredicateFactory.Config> {


    public IpLimitPredicateFactory() {
        super(Config.class);
    }

    @Override
    public LimitPredicate apply(Config config) {

        List<String> ips = Arrays.asList(config.getIps().split(","));
        String regexp = config.getRegexp();

        return request -> {
            String ip = IPUtils.getIpAddress(request);

            if (!StringUtils.isEmpty(regexp)) {
                return Pattern.matches(regexp, ip);
            } else if (!CollectionUtils.isEmpty(ips)) {
                return ips.contains(ip);
            }
            return false;
        };

    }

    @Data
    public static class Config{
        private String ips;
        private String regexp;

    }

}
