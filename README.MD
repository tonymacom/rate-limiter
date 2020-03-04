限流配置

```
        <dependency>
            <groupId>com.tony.unit</groupId>
            <artifactId>rate-limiter</artifactId>
            <version>1.0</version>
        </dependency>
```

```$xslt

# 对/output请求全部拒绝
#request.limit.routes[0].path=/output
#request.limit.routes[0].predicate.type=always
#request.limit.routes[0].filter.type=Reject

# 对/output请求进行限流, 3次每10秒钟.
#request.limit.routes[0].path=/output
#request.limit.routes[0].predicate.type=always
#request.limit.routes[0].filter.type=RateLimiter
#request.limit.routes[0].filter.args.replenish_rate=3
#request.limit.routes[0].filter.args.burst_capacity=3
#request.limit.routes[0].filter.args.time_unit_second=10

# 对请求头中token=aaaaa或者bbbbb的/output请求拒绝
#request.limit.routes[0].path=/output
#request.limit.routes[0].predicate.type=header
#request.limit.routes[0].predicate.args.name=token
#request.limit.routes[0].predicate.args.values=aaaaa, bbbbb
#request.limit.routes[0].filter.type=Reject

# 对解析token中user_id=123或者234的/output请求拒绝
#request.limit.routes[0].path=/output
#request.limit.routes[0].predicate.type=userId
#request.limit.routes[0].predicate.args.user_ids=123,234
#request.limit.routes[0].filter.type=Reject

# 对解析token中user_id可以匹配正则\d{3}的/output请求拒绝
#request.limit.routes[0].path=/output
#request.limit.routes[0].predicate.type=userId
#request.limit.routes[0].predicate.args.regexp=\\d{3}
#request.limit.routes[0].filter.type=Reject

# 对来源ip为0:0:0:0:0:0:0:1或者10.30.10.212的/output请求拒绝
#request.limit.routes[0].path=/output
#request.limit.routes[0].predicate.type=ip
#request.limit.routes[0].predicate.args.ips=0:0:0:0:0:0:0:1,10.30.10.212
#request.limit.routes[0].filter.type=Reject

# 对来源ip正则匹配0:0:0:0:0:0:0:1的/output请求拒绝
request.limit.routes[0].path=/output
request.limit.routes[0].predicate.type=ip
request.limit.routes[0].predicate.args.regexp=0:0:0:0:0:0:0:1
request.limit.routes[0].filter.type=Reject


```