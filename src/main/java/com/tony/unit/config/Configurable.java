package com.tony.unit.config;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/18
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public interface Configurable<C> {

    C newConfig();

    Class<C> getConfigClass();

}
