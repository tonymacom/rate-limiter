package com.tony.unit.core;

import com.alibaba.fastjson.JSON;
import com.tony.unit.config.AbstractLimitPredicateFactory;
import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
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
            String dateId = getUserId(token);

            if (!StringUtils.isEmpty(regexp)) {
                return Pattern.matches(regexp, dateId);
            } else if (!CollectionUtils.isEmpty(userIds)) {
                return userIds.contains(dateId);
            }

            return false;
        };

    }

    public static String getUserId(String token) {
        if (token != null && token.length() != 0) {
            try {
                return JSON.parseObject(decode(URLDecoder.decode(token, "utf-8"))).getString("data");
            } catch (Exception var2) {
                throw new RuntimeException("Token is invalid , Please ReLogin");
            }
        } else {
            return "";
        }
    }

    public static String decode(String str) {
        byte[] bt;
        try {
            bt = Base64.decodeBase64(str);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }

        return new String(bt);
    }

    @Data
    public static class Config {
        private String user_ids;
        private String regexp;
    }

}
