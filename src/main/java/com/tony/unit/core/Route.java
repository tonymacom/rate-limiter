package com.tony.unit.core;

import lombok.Getter;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/26
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Getter
public class Route implements Ordered {

    private final String id;

    private final URI uri;

    private final int order;

    private final Predicate<HttpServletRequest> predicate;

    private final List<LimitFilter> limitFilter;

    public Route(String id, URI uri, int order, Predicate<HttpServletRequest> predicate, List<LimitFilter> limitFilter) {
        this.id = id;
        this.uri = uri;
        this.order = order;
        this.predicate = predicate;
        this.limitFilter = limitFilter;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public static void main(String[] args) {
        System.out.println(Instant.now().getEpochSecond());
    }

}
