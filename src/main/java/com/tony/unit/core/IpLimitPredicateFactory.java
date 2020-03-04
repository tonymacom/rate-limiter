package com.tony.unit.core;

import com.tony.unit.config.AbstractLimitPredicateFactory;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
            String ip = getIpAddress(request) ;

            if (!StringUtils.isEmpty(regexp)) {
                return Pattern.matches(regexp, ip);
            } else if (!CollectionUtils.isEmpty(ips)) {
                return ips.contains(ip);
            }
            return false;
        };

    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("CF-Connecting-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        return ip;
    }

    @Data
    public static class Config{
        private String ips;
        private String regexp;

    }

}
