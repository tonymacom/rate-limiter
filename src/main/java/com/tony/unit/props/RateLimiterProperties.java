package com.tony.unit.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/26
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Data
@ConfigurationProperties("request.limit")
public class RateLimiterProperties {

    private List<RouteDedinition> routes;

}
