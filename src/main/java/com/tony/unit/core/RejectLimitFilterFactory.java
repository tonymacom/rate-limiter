package com.tony.unit.core;

import com.tony.unit.config.AbstractConfigurable;
import com.yamibuy.ec.core.common.YamibuyException;
import com.yamibuy.ec.core.common.YamibuyMessageCode;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/26
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class RejectLimitFilterFactory extends AbstractConfigurable<Void> implements LimitFilterFactory<Void>{

    public RejectLimitFilterFactory() {
        super(Void.class);
    }

    @Override
    public LimitFilter apply(Void config) {
        return (request,response,chain)->{

            throw new YamibuyException(YamibuyMessageCode.NO_PERMISSION.getCode());
        };
    }
}
