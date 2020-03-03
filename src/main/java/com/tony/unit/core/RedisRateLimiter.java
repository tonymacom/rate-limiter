package com.tony.unit.core;

import com.tony.unit.config.AbstractStatefulConfigurable;
import com.tony.unit.util.ScriptUtils;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/16
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class RedisRateLimiter extends AbstractStatefulConfigurable<RedisRateLimiter.Config>
        implements RateLimiter<RedisRateLimiter.Config> , ApplicationListener<FilterEventArgs> {

    public static final String REDIS_SCRIPT_NAME = "redisRequestRateLimiterScript";

    private RedisTemplate<String,String> redisTemplate;
    private RedisScript<List<Long>> script;
    private AtomicBoolean initialized = new AtomicBoolean(false);

    public RedisRateLimiter(RedisTemplate<String,String> redisTemplate,
                            RedisScript<List<Long>> script) {
        super(RedisRateLimiter.Config.class);
        this.redisTemplate = redisTemplate;
        this.script = script;
        initialized.compareAndSet(false, true);
    }

    @Override
    public Response isAllowed(String routeKey) {
        if (!this.initialized.get()) {
            throw new IllegalStateException("RedisRateLimiter is not initialized");
        }

        List<String> keys = getKeys(routeKey);

        RedisRateLimiter.Config config = getConfigs().get(routeKey);
        String replenish_rate = ScriptUtils.eval(config.getReplenish_rate());
        String burst_capacity = ScriptUtils.eval(config.getBurst_capacity());
        String time_unit_second = ScriptUtils.eval(config.getTime_unit_second());
        String[] args = new String[]{
                replenish_rate+"",
                burst_capacity+"",
                time_unit_second+"",
                Instant.now().getEpochSecond() + "",
                "1"};

        List<Long> execute = redisTemplate.execute(script, keys, args);

        boolean alowed = CollectionUtils.isEmpty(execute) ? true :execute.get(0) == 1L;
        Response response = new Response(alowed,execute.get(1));
        return response;
    }

    static List<String> getKeys(String id) {

        String prefix = "request_rate_limiter.{" + id;
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    @Override
    public void onApplicationEvent(FilterEventArgs event) {

        Map<String, Object> args = event.getArgs();
        String routeKey = event.getRouteKey();

        invokeConfig(routeKey,args);

    }

    @Data
    public static class Config {
        private String replenish_rate;

        private String burst_capacity = "1";

        private String time_unit_second;

    }

}
