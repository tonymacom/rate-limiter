package com.tony.unit.util;

import com.tony.unit.props.HttpResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author www.yamibuy.com
 * @desc :
 * @date 2020/3/3
 * <b>版权所有：</b>版权所有(C) 2018，www.yamibuy.com<br>
 */
public class ResponseUtils {

    /**
     * 发送消息 text/html;charset=utf-8
     * @param response
     * @throws Exception
     */
    public static void sendMessage(HttpServletResponse response, HttpResponseStatus status) {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(status.getValue());
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(status.getReasonPhrase());

            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }


}
