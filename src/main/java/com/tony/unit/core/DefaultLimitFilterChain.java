package com.tony.unit.core;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/26
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class DefaultLimitFilterChain implements LimitFilterChain {

    private final int index;

    @Getter
    private List<LimitFilter> filters;

    public DefaultLimitFilterChain(List<LimitFilter> filters) {
        this.index = 0;
        this.filters = filters;
    }

    DefaultLimitFilterChain(int index, DefaultLimitFilterChain parentChain) {
        this.index = index;
        this.filters = parentChain.getFilters();
    }


    @Override
    public void filter(HttpServletRequest request, HttpServletResponse response) {
        if (this.index < filters.size()) {
            LimitFilter filter = filters.get(index);
            DefaultLimitFilterChain next = new DefaultLimitFilterChain(index + 1, this);
            filter.filter(request, response, next);
        }
    }
}
