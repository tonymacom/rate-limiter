package com.tony.unit.config;

import com.tony.unit.core.StatefulConfigurable;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/27
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public abstract class AbstractStatefulConfigurable<C> extends AbstractConfigurable<C> implements StatefulConfigurable<C> {

    private Map<String, C> configs = new HashMap<>();

    protected AbstractStatefulConfigurable(Class<C> cClass) {
        super(cClass);
    }

    @Override
    public Map<String, C> getConfigs() {
        return this.configs;
    }

    @SneakyThrows
    protected void invokeConfig(String key, Map<String, Object> args) {
        C c = newConfig();
        org.apache.commons.beanutils.BeanUtils.populate(c,args);
        this.configs.put(key, c);
    }
}
