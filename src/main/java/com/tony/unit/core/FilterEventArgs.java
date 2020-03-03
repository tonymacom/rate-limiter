package com.tony.unit.core;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/27
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Getter
public class FilterEventArgs extends ApplicationEvent {

    private final Map<String,Object> args;

    private String routeKey;

    public FilterEventArgs(Object source, String routeKey, Map<String,Object> args) {
        super(source);
        this.routeKey = routeKey;
        this.args = args;
    }
}
