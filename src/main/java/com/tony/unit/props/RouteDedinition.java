package com.tony.unit.props;

import lombok.Data;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/1/18
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Data
public class RouteDedinition {

    private String path;

    private PredicateDefinition predicate;

    private FilterDefinition filter;


}
