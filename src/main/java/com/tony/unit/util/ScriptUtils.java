package com.tony.unit.util;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import lombok.SneakyThrows;

import javax.script.ScriptEngineManager;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/3/3
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class ScriptUtils {

    private static NashornScriptEngine engine = (NashornScriptEngine)new ScriptEngineManager().getEngineByName("nashorn");

    /**
     * 表达式运算
     * @param expression
     * @return
     */
    @SneakyThrows
    public static String eval(String expression) {
        return engine.eval(expression).toString();
    }


}
