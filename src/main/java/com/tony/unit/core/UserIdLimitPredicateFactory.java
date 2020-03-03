package com.tony.unit.core;

import com.tony.unit.config.AbstractLimitPredicateFactory;
import com.yamibuy.ec.core.util.TokenUtil;
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
public class UserIdLimitPredicateFactory extends AbstractLimitPredicateFactory<UserIdLimitPredicateFactory.Config> {

    public UserIdLimitPredicateFactory() {
        super(Config.class);
    }

    @Override
    public LimitPredicate apply(Config config) {
        List userIds = Arrays.asList(config.getUser_ids().split(","));
        String regexp = config.getRegexp();

        return request -> {
            String token = request.getHeader("token");
            String dateId = TokenUtil.convert(token).getData();

            if (!StringUtils.isEmpty(regexp)) {
                return Pattern.matches(regexp, dateId);
            } else if (!CollectionUtils.isEmpty(userIds)) {
                return userIds.contains(dateId);
            }

            return false;
        };

    }

    @Data
    public static class Config {
        private String user_ids;
        private String regexp;
    }

}
