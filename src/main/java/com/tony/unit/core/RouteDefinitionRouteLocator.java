package com.tony.unit.core;

import com.tony.unit.props.FilterDefinition;
import com.tony.unit.props.PredicateDefinition;
import com.tony.unit.props.RateLimiterProperties;
import com.tony.unit.props.RouteDedinition;
import com.tony.unit.util.LogUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/28
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Slf4j
public class RouteDefinitionRouteLocator implements RouteLocator, ApplicationContextAware, ApplicationEventPublisherAware {

    private AtomicBoolean initialized = new AtomicBoolean(false);

    private ApplicationEventPublisher publisher;
    private RateLimiterProperties properties;

    private final Map<String, LimitPredicateFactory> predicateFactories = new HashMap<>();
    private final Map<String, LimitFilterFactory> filterFactories = new HashMap<>();


    @Override
    @SneakyThrows
    public List<Route> getRoutes() {

        List<Route> result = new ArrayList<>();
        List<RouteDedinition> routes = properties.getRoutes();
        if (!CollectionUtils.isEmpty(routes)) {
            for (int i = 0; i < routes.size(); i++) {
                RouteDedinition routeDedinition = routes.get(i);

                Predicate<HttpServletRequest> predicate = combinePredicates(routeDedinition);
                List<LimitFilter> filterList = loadLimitFilter(routeDedinition);

                Route route = new Route(i+"", new URI(routeDedinition.getPath()),i, predicate, filterList);
                result.add(route);

            }
        }
        return result;
    }

    /**
     * 获取routeKey
     *
     * @param path
     * @return
     */
    private String getRouteKey(String path) {
        return path.replace("/","");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (initialized.compareAndSet(false, true)) {
            this.properties = context.getBean(RateLimiterProperties.class);
            initLimitPredicates(context);
            initLimitFilters(context);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    private void initLimitFilters(ApplicationContext context) {
        Map<String, LimitFilterFactory> mappingBeans  = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, LimitFilterFactory.class);
        mappingBeans.values().forEach(factory->{
            String key = factory.name();
            if (this.filterFactories.containsKey(key)) {
                LogUtils.error(this,"A LimitFilterFactory named "+ key + "already exists, Class: "+ this.filterFactories.get(key) + ", It will be overwritten");
            }
            this.filterFactories.put(key, factory);
        });


    }

    private void initLimitPredicates(ApplicationContext context) {
        Map<String, LimitPredicateFactory> mappingBeans  = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, LimitPredicateFactory.class);
        mappingBeans.values().forEach(factory->{
            String key = factory.name();
            if (this.predicateFactories.containsKey(key)) {
                LogUtils.error(this,"A LimitPredicateFactory named "+ key + "already exists, Class: "+ this.predicateFactories.get(key) + ", It will be overwritten");
            }
            this.predicateFactories.put(key, factory);
        });
    }

    @SneakyThrows
    private List<LimitFilter> loadLimitFilter(RouteDedinition routeDedinition) {

        FilterDefinition filterDefinition = routeDedinition.getFilter();

        LimitFilterFactory factory = this.filterFactories.get(filterDefinition.getType().toLowerCase());

        if (null == factory) {
            List<String> filterNames = this.filterFactories.values().stream().map(LimitFilterFactory::name).collect(Collectors.toList());
            LogUtils.error(this,"System Don't have a LimitPredicateFactory named "+ filterDefinition.getType() + ", you can choose one from ["+ StringUtils.join(filterNames,",")+"]");
        }

        Object config = factory.newConfig();
        BeanUtils.populate(config, filterDefinition.getArgs());

        if (null != filterDefinition.getArgs()) {
            String path = routeDedinition.getPath();
            String routeKey = getRouteKey(path);
            Map<String, Object> args = new HashMap<>();
            args.putAll(filterDefinition.getArgs());
            FilterEventArgs eventArgs = new FilterEventArgs(RouteDefinitionRouteLocator.this, routeKey, args);
            this.publisher.publishEvent(eventArgs);

            if (config instanceof HashRouteKey) {
                ((HashRouteKey) config).setRouteKey(routeKey);
            }
        }

        return Arrays.asList(factory.apply(config));

    }

    @SneakyThrows
    private Predicate<HttpServletRequest> combinePredicates(RouteDedinition routeDedinition) {

        PredicateDefinition predicate = routeDedinition.getPredicate();

        LimitPredicateFactory<Object> factory = this.predicateFactories.get(predicate.getType().toLowerCase());
        if (null == factory) {
            List<String> predicateNames = this.predicateFactories.values().stream().map(LimitPredicateFactory::name).collect(Collectors.toList());
            LogUtils.error(this,"System Don't have a LimitPredicateFactory named "+ predicate.getType() + ", you can choose one from ["+ StringUtils.join(predicateNames,",")+"]");
        }

        Object config = factory.newConfig();
        BeanUtils.populate(config, predicate.getArgs());

        return factory.apply(config);
    }


}
