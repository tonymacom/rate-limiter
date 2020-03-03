package com.tony.unit.props;

import lombok.Data;

import java.util.Map;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/18
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Data
public class FilterDefinition {

    private String type;
    private Map<String,String> args;
}
