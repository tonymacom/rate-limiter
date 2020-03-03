package com.tony.unit.core;

import com.tony.unit.util.LogUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/28
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class CachingRouteLocator implements RouteLocator {

    private static final String CACHE_KEY = "routes";

    private RouteLocator delegate;

    private List<Route> routes;

    private final Map<String, List> cache = new ConcurrentHashMap<>();


    public CachingRouteLocator(RouteLocator delegate) {
        this.delegate = delegate;
        this.routes = Optional.ofNullable(cache.get(CACHE_KEY)).orElse(fetch());
        LogUtils.info(this, "Routes load success, size is "+ routes.size());
    }

    private List<Route> fetch() {
        List<Route> routes = this.delegate.getRoutes();
        cache.put(CACHE_KEY, routes);
        return routes;
    }

    private List<Route> refresh() {
        this.cache.clear();
        return this.routes;
    }


    @Override
    public List<Route> getRoutes() {
        return this.routes;
    }
}
