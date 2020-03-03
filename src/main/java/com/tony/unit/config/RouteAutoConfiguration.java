package com.tony.unit.config;

import com.tony.unit.core.*;
import com.tony.unit.filter.RateLimiterFilter;
import com.tony.unit.props.RateLimiterProperties;
import com.tony.unit.util.LogUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RouteAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterProperties rateLimiterProperties() {
        return new RateLimiterProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public HeaderLimitPredicateFactory headerLimitPredicateFactory(){
        return new HeaderLimitPredicateFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    IpLimitPredicateFactory ipLimitPredicateFactory() {
        return new IpLimitPredicateFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserIdLimitPredicateFactory userIdLimitPredicateFactory() {
        return new UserIdLimitPredicateFactory();
    }


    @Bean
    @ConditionalOnMissingBean
    public RateLimiterLimitFilterFactory rateLimiterLimiterFilterFactory(RateLimiter rateLimiter) {
        return new RateLimiterLimitFilterFactory(rateLimiter);
    }

    @Bean
    @ConditionalOnMissingBean
    public RejectLimitFilterFactory rejectLimitFilterFactory() {
        return new RejectLimitFilterFactory();
    }



    @Bean
    @ConditionalOnMissingBean
    RouteDefinitionRouteLocator routeDefinitionRouteLocator() {
        return new RouteDefinitionRouteLocator();
    }

    @Bean
    @ConditionalOnMissingBean(CachingRouteLocator.class)
    RouteLocator cachingRouteLocator(RouteDefinitionRouteLocator definitionRouteLocator) {
        return new CachingRouteLocator(definitionRouteLocator);
    }

    @Bean
    @ConditionalOnMissingBean
    @SuppressWarnings("unchecked")
    public RedisScript redisRequestRateLimiterScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("META-INF/scripts/request_rate_limiter.lua")));
        redisScript.setResultType(List.class);
        return redisScript;
    }

    @Bean(name="rateLimiterRedisTemplate")
    public RedisTemplate<?, ?> rateLimiterRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //ValueSerializer必须使用StringRedisSerializer对象
        template.setValueSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }


    @Bean
    @ConditionalOnMissingBean
    public RedisRateLimiter redisRateLimiter(@Qualifier("rateLimiterRedisTemplate") RedisTemplate<String,String> rateLimiterRedisTemplate,
                                             @Qualifier(RedisRateLimiter.REDIS_SCRIPT_NAME) RedisScript<List<Long>> redisScript) {
        return new RedisRateLimiter(rateLimiterRedisTemplate, redisScript);
    }

    @Bean
    public FilterRegistrationBean rateLimiterFilterFilter(RouteLocator cachingRouteLocator) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RateLimiterFilter(cachingRouteLocator));
        registration.addUrlPatterns("/*");
        registration.setName("rateLimiterFilter");
        LogUtils.info(this,"RateLimiterFilter load success");
        return registration;

    }

}
