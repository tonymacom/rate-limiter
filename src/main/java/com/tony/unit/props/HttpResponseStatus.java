package com.tony.unit.props;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/3/4
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Getter
public enum HttpResponseStatus {


    NOT_ACCEPTABLE(900406, "Not Acceptable"),
    TOO_MANY_REQUESTS(900429, "Too Many Requests"),
    ;

    private Integer value;

    private String reasonPhrase;

    HttpResponseStatus(Integer value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public static boolean existsValue(Integer t_value) {
        if (null == t_value) {
            return false;
        }
        return Stream.of(HttpResponseStatus.values()).anyMatch(httpResponseStatus -> httpResponseStatus.value.equals(t_value));
    }

}
