package com.tony.unit.filter;

import com.tony.unit.core.DefaultLimitFilterChain;
import com.tony.unit.core.Route;
import com.tony.unit.core.RouteLocator;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/15
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Slf4j
@Data
public class RateLimiterFilter implements Filter{


    private RouteLocator routeLocator;

    AntPathMatcher antPathMatcher = new AntPathMatcher(File.separator);

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    public RateLimiterFilter(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }


    @Override
    @SneakyThrows
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.debug("RateLimiterFilter execute");

        HttpServletRequest request = (HttpServletRequest)servletRequest;

        Route route1 = this.routeLocator.getRoutes().stream().filter(route -> {

            // eg: /output
            String requestUri = urlPathHelper.getLookupPathForRequest(request);

            return antPathMatcher.match(route.getUri().getPath(), requestUri);

        }).findFirst().orElse(null);

        if (null != route1) {
            if (route1.getPredicate().test(request)) {
                DefaultLimitFilterChain defaultLimitFilterChain = new DefaultLimitFilterChain(route1.getLimitFilter());
                defaultLimitFilterChain.filter(request,(HttpServletResponse) servletResponse);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

}
