package com.tony.unit.config;

import org.springframework.beans.BeanUtils;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/18
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public abstract class  AbstractConfigurable<C> implements Configurable<C>{

    private Class<C> configClass;

    protected AbstractConfigurable(Class<C> cClass) {
        this.configClass = cClass;
    }

    @Override
    public Class<C> getConfigClass() {
        return configClass;
    }

    @Override
    public C newConfig() {
        return BeanUtils.instantiateClass(configClass);
    }

}
