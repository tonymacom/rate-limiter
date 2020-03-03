限流配置

```
        <dependency>
            <groupId>com.tony.unit</groupId>
            <artifactId>rate-limiter</artifactId>
            <version>1.0</version>
        </dependency>
```

```$xslt

# header类型的所有请求全部走redis 限流(3个请求/40s)
#request.limit.routes[0].path=/output
#request.limit.routes[0].predicate.type=header
#request.limit.routes[0].filter.type=RateLimiter
# 表示每40s往令牌桶中存放3个请求令牌
#request.limit.routes[0].filter.args.replenish_rate=3
# 表示令牌桶最大容量为3, 即初始给3个请求令牌.
#request.limit.routes[0].filter.args.burst_capacity=3
#request.limit.routes[0].filter.args.time_unit_second=40

# user_ids为123,或者234的用户, 该请求拒绝.
#request.limit.routes[0].path=/output
#request.limit.routes[0].predicate.type=userId
#request.limit.routes[0].predicate.args.user_ids=123,234
#request.limit.routes[0].filter.type=Reject


# ips为10.30.10.211,10.30.10.212的用户走redis限流
#request.limit.routes[1].path=/register
#request.limit.routes[1].predicate.type=ip
#request.limit.routes[1].predicate.args.ips=10.30.10.211,10.30.10.212
#request.limit.routes[1].filter.type=RateLimiter
#request.limit.routes[1].filter.args.replenish_rate=3
#request.limit.routes[1].filter.args.burst_capacity=3
#request.limit.routes[1].filter.args.time_unit_second=30 * 3600

```