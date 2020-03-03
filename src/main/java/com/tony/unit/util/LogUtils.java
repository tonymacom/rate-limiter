package com.tony.unit.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/2/29
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
@Slf4j
public class LogUtils {

    public static void error(Object clazz, String err) throws RuntimeException{
      String e_info = "RateLimiter Error was found, it was occured in [" + clazz.getClass().getName() + "], " + err;
      log.error(e_info);
      throw new RuntimeException(e_info);
    }

    public static void info(Object clazz, String inf) {
        String info = "RateLimiter Info log, it was in [" + clazz.getClass().getName() + "], " + inf;
        log.info(info);
    }

}
