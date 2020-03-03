package com.tony.unit.core;

import com.tony.unit.config.Configurable;

import java.util.Map;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/27
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public interface StatefulConfigurable<C> extends Configurable<C> {
    Map<String,C> getConfigs();
}
