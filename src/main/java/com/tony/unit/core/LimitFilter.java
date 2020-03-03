package com.tony.unit.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/26
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@FunctionalInterface
public interface LimitFilter {

    void filter(HttpServletRequest request, HttpServletResponse response, LimitFilterChain chain);

}
